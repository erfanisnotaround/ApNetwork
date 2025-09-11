package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
public final class JsonSupport {
    public static final ObjectMapper M = new ObjectMapper();
    public static <P,R> EnvelopeData<P,R> read(String line, Class<P> pCls, Class<R> rCls){
        try {
            TypeFactory tf = M.getTypeFactory();
            var envType = tf.constructParametricType(EnvelopeData.class, pCls, rCls);
            return M.readValue(line, envType);
        } catch(Exception e){ throw new RuntimeException(e); }
    }
    public static String write(Object o){ try { return M.writeValueAsString(o);} catch(Exception e){ throw new RuntimeException(e);} }
}
