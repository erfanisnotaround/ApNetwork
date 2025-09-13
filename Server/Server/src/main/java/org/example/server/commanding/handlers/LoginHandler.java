package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.LoginPass;
import org.example.passingInformation.exit.LoginRes;
import org.example.sameInfoes.UserPlaceState;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;
import org.example.server.helpers.AuthService;

public final class LoginHandler implements CommandHandler<LoginPass, LoginRes> {

    // choose a TTL or load from config
    private static final int TOKEN_TTL_SECONDS = 24 * 3600;

    @Override
    public void handle(RequestContext ctx, EnvelopeData<LoginPass, LoginRes> env) {
        // 1) Read token either from envelope root or from payload (be flexible)
        String resumeToken = trimToNull(env.getToken());
        LoginPass pass = env.getDataPas();
        if (resumeToken == null && pass != null) {
            resumeToken = trimToNull(pass.token); // if client sent it inside payload
        }

        // 2) Token resume path
        if (resumeToken != null) {
            String userId = ctx.services.tokens.verify(resumeToken);
            if (userId == null) { ctx.fail(env, "unauth", "invalid_or_expired_token"); return; }

            var user = ctx.services.users.byId(userId);
            if (user == null)   { ctx.fail(env, "unauth", "unknown_user"); return; }

            // bind session FIRST (Option A: future commands are session-only)
            ctx.sessions.attachUser(ctx.session, user.getId());
            ctx.sessions.setPlace(ctx.session, UserPlaceState.MAIN_PANEL);

            // issue a fresh token (since you don't have refresh)
            String newToken = ctx.services.tokens.issue(user.getId(), TOKEN_TTL_SECONDS);


            ctx.ok(env, new LoginRes(user.getUsername() , user.getId()), "login_ok_token");
            return;
        }

        // 3) Username/password path
        if (pass == null) { ctx.fail(env, "bad_request", "missing_credentials"); return; }

        String u  = trimOrEmpty(pass.username);
        String pw = trimOrEmpty(pass.password);
        if (u.isEmpty() || pw.isEmpty()) {
            ctx.fail(env, "bad_request", "missing_credentials");
            return;
        }

        var user = ctx.services.users.byUsername(u);
        if (user == null) {
            ctx.fail(env, "no_user", "user not found");
            return;
        }

        var okHash = AuthService.PasswordHasher.hash(pw, user.getSalt());
        if (!okHash.equals(user.getPassHash())) {
            ctx.fail(env, "bad_credentials", "wrong password");
            return;
        }

        // bind session FIRST
        ctx.sessions.attachUser(ctx.session, user.getId());            // use userId, not username
        ctx.sessions.setPlace(ctx.session, UserPlaceState.MAIN_PANEL);

        // issue token for future resume (client may store it)
        String token = ctx.services.tokens.issue(user.getId(), TOKEN_TTL_SECONDS);

        // --- respond ---
        // If LoginRes has token: send it
        // ctx.ok(env, new LoginRes(user.getUsername(), token), "login_ok");
        // If username-only:
        ctx.ok(env, new LoginRes(user.getUsername() , user.getId()), "login_ok");
    }

    // helpers
    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
    private static String trimOrEmpty(String s) {
        return s == null ? "" : s.trim();
    }
}
