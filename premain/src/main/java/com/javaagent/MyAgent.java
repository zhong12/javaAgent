package com.javaagent;

import com.javaagent.premain.MonitorTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Created by zhongjing on 2017/11/08.
 */
public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("javaAgent init." + agentArgs);
        inst.addTransformer(new MonitorTransformer());
        System.out.println("javaAgent success.");
    }
}
