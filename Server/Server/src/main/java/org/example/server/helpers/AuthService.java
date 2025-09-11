package org.example.server.helpers;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public final class AuthService {
    public static final class PasswordHasher {
        public static String salt(){ byte[] b=new byte[16]; new SecureRandom().nextBytes(b); return Base64.getUrlEncoder().withoutPadding().encodeToString(b); }
        public static String hash(String pw, String salt){
            try { var md = java.security.MessageDigest.getInstance("SHA-256");
                md.update(salt.getBytes()); return Base64.getUrlEncoder().withoutPadding().encodeToString(md.digest(pw.getBytes())); }
            catch (Exception e){ throw new RuntimeException(e); }
        }
    }
    public static final class TokenService {
        private final byte[] key;
        public TokenService(String secret){ this.key = secret.getBytes(); }
        public String issue(String userId, long ttlSec){
            long exp = System.currentTimeMillis()/1000 + ttlSec; String payload = userId+"."+exp;
            return Base64.getUrlEncoder().withoutPadding().encodeToString((payload+"."+sign(payload)).getBytes());
        }
        public String verify(String token){
            try {
                String raw = new String(Base64.getUrlDecoder().decode(token));
                String[] p = raw.split("\\."); if (p.length!=3) return null;
                if (!sign(p[0]+"."+p[1]).equals(p[2])) return null;
                if (System.currentTimeMillis()/1000 > Long.parseLong(p[1])) return null;
                return p[0];
            } catch(Exception e){ return null; }
        }
        private String sign(String s){
            try { Mac mac= Mac.getInstance("HmacSHA256"); mac.init(new SecretKeySpec(key,"HmacSHA256"));
                return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(s.getBytes())); }
            catch(Exception e){ throw new RuntimeException(e); }
        }
    }
}
