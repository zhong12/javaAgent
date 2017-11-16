package com.agentmain;

import com.sun.tools.attach.VirtualMachine;

/**
 * Created by zhongjing on 2017/11/16.
 */
public class RunAttach {

    public static void main(String[] args) throws Exception {
        // args[0]传入的是某个jvm进程的pid  
        String targetPid = args[0];
        VirtualMachine vm = VirtualMachine.attach(targetPid);
        vm.loadAgent("E:\\project\\project\\javaAgent\\agentmain\\target\\agentmain-0.0.1.jar",
                "toAgent");
    }
}
