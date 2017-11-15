package com.javaagent.utils;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * Created by zhongjing on 2017/11/14.
 */
public class JavaSiteUtils {

    /**
     * 获取CtClass
     * @param className
     * @return
     * @throws NotFoundException
     */
    public static CtClass getCtClass(String className) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        return pool.get(className);
        
    }


    /**
     * 判断方法是否存在
     * @param ctClass
     * @param method
     * @return
     */
    public static boolean isExistMethod(CtClass ctClass, String method) {
        if (null == ctClass || null == method || method.length() == 0) {
            return false;
        }
        CtMethod[] ctMethod = getMethods(ctClass);
        if(null == ctMethod || ctMethod.length == 0){
            return false;
        }
        for (int i = 0; i < ctMethod.length; i++) {
            if (ctMethod[i].getName().equals(method)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 获取CtClass所有方法
     * @param ctClass
     * @return
     */
    public static CtMethod[] getMethods(CtClass ctClass) {
        if (null == ctClass) {
            return null;
        }
        CtMethod[] ctMethod = ctClass.getMethods();
        return ctMethod;
    }
}
