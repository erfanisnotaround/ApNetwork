package org.example.server.commanding.handlers;

import org.example.EnvelopeData;
import org.example.passingInformation.entry.RegisterPass;
import org.example.passingInformation.exit.RegisterRes;
import org.example.server.helpers.AuthService;
import org.example.server.commanding.CommandHandler;
import org.example.server.commanding.RequestContext;

import java.util.Map;

public final class RegisterHandler implements CommandHandler<RegisterPass , RegisterRes> {
    @Override public void handle(RequestContext ctx, EnvelopeData<RegisterPass , RegisterRes> env) {
        RegisterPass pass = env.getDataPas();
        RegisterRes res = env.getDataRec();
        String u = String.valueOf(pass.username);
        String pw = String.valueOf(pass.password);
        var salt = AuthService.PasswordHasher.salt();
        var hash = AuthService.PasswordHasher.hash(pw, salt);
        if (ctx.services.users.byUsername(u) != null) { ctx.fail(env, "user_exists", "username already exists"); return; }
        var user = ctx.services.users.create(u, salt, hash);
        ctx.ok(env, res, "registered");
    }
}