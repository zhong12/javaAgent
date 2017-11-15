package com.javaagent.premain;

import com.javaagent.config.MonitorConfig;
import com.javaagent.config.MonitorConfigImpl;
import com.javaagent.utils.JavaSiteUtils;
import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhongjing on 2017/11/15.
 */
public class MonitorTransformer implements ClassFileTransformer {

    final static String prefix = "\nlong startTime = System.currentTimeMillis();\n try { \n";
    final static String postfix = "\n}finally{ long endTime = System.currentTimeMillis();\n";
    final static Map<String,List<String>> classMethodMap = new HashMap<>();


    public MonitorTransformer() {
        MonitorConfig config;
        try {
            //读取配置文件  
            config = new MonitorConfigImpl();
            String methodStr = config.getStringValue("methodList", null);
            String[] classMethods = methodStr.split(",");
            //将读取的配置文件加入要检测的方法列表  
            if (null != classMethods && classMethods.length > 0) {
                for (int i = 0; i < classMethods.length; i++) {
                    String classMethod = classMethods[i];
                    String className = classMethod.substring(0, classMethod.lastIndexOf("."));
                    String methodName = classMethod.substring(classMethod.lastIndexOf(".") + 1);
                    List<String> list = classMethodMap.get(className);
                    if (list == null) {
                        list = new ArrayList<>();
                        classMethodMap.put(className, list);
                    }
                    list.add(methodName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (null == className || className.length() <= 0 || null == classMethodMap || classMethodMap.size() <= 0) {
            return null;
        }
        //将‘/’替换为‘.’
        className = className.replace("/", ".");
        try {
            if (classMethodMap.containsKey(className)) {
                List<String> methods = classMethodMap.get(className);
                if (null != methods && methods.size() > 0) {
                    CtClass ctclass = JavaSiteUtils.getCtClass(className);
                    for (String method : methods){
                        //判断是否存在该监控的方法
                        if(JavaSiteUtils.isExistMethod(ctclass,method)){
                            String ctClassName = ctclass.getName();
                            String outputStr = "\nSystem.out.println(\"this className : "+ ctClassName + " ,this method：" + method + " cost:\" +(endTime - startTime) +\"ms.\");";
                            //得到这方法实例  
                            CtMethod ctmethod = ctclass.getDeclaredMethod(method);
                            CtClass returnType = ctmethod.getReturnType();
                            String returnTypeName = returnType.getName();
                            //新定义一个方法叫做比如sayHello$impl   
                            String newMethodName = method + "$impl";
                            //原来的方法改个名字   
                            ctmethod.setName(newMethodName);
                            //创建新的方法，复制原来的方法 ，名字为原来的名字  
                            CtMethod newMethod = CtNewMethod.copy(ctmethod, method, ctclass, null);
                            //构建新的方法体  
                            StringBuilder bodyStr = new StringBuilder();
                            bodyStr.append("{");
                            bodyStr.append(prefix);
                            //调用原有代码，类似于method();($$)表示所有的参数
                            if(null != returnTypeName && !returnTypeName.toUpperCase().equals("VOID")){
                                bodyStr.append("\nreturn " + newMethodName + "($$);\n");
                            } else{
                                bodyStr.append(newMethodName + "($$);\n");
                            }
                            bodyStr.append(postfix);
                            bodyStr.append(outputStr);
                            bodyStr.append("\n}\n}");
                            //替换新方法   
                            newMethod.setBody(bodyStr.toString());
                            //增加新方法   
                            ctclass.addMethod(newMethod);
                        }
                    }
                    return ctclass.toBytecode();
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
