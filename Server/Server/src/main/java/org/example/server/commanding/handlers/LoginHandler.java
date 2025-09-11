package org.example.server.commanding.handlers;


import org.example.EnvelopeData;
import org.example.passingInformation.entry.LoginPass;
import org.example.passingInformation.exit.LoginRes;
import org.example.sameInfoes.UserPlaceState;
import org.example.server.helpers.AuthService;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;

public final class LoginHandler implements CommandHandler<LoginPass , LoginRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<LoginPass , LoginRes> env) {
        LoginPass pass =  env.getDataPas();
        String u = String.valueOf(pass.username);
        String pw = String.valueOf(pass.password);
        String token = String.valueOf(pass.token);

        var user = ctx.services.users.byUsername(u);
        if (user == null) { ctx.fail(env,"no_user","user not found"); return; }
        var okHash = AuthService.PasswordHasher.hash(pw, user.getSalt());
        if (!okHash.equals(user.getPassHash())) { ctx.fail(env,"bad_credentials","wrong password"); return; }
        String tok = ctx.services.tokens.issue(user.getId(), 24*3600);
        ctx.ok(env, env.dataRec, "login_ok");
        ctx.sessions.attachUser(ctx.session , u);
        ctx.sessions.setPlace( ctx.session , UserPlaceState.MAIN_PANEL);
    }
}
