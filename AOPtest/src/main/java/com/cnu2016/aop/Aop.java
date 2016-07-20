package com.cnu2016.aop;
import java.lang.instrument.Instrumentation;

/**
 * Created by Piyush on 7/20/16.
 */
public class Aop {
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new Loader());
    }
}
