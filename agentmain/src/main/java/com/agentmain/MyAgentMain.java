package com.agentmain;

import java.lang.instrument.Instrumentation;

/**
 * Created by zhongjing on 2017/11/15.
 */
public class MyAgentMain {
    public static void agentmain(String args, Instrumentation inst) {
        System.out.println("loadAgent after main run.args=" + args);
        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
        }
        System.out.println("agent run completely.");
    }
}
