package jkdProxy.util;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Kevin on 2019/1/22
 */
public final class ProxyUtil {
    private ProxyUtil() {
    }

    /**
     * content --->string
     * .java  io
     * .class
     * .new   反射----》class
     *
     * @return
     */
    public static Object getInstance(Object target) {
        Object proxy = null;
        String tab = "\t";
        String line = "\n";
        String content = "";
        String packageContent = "package com.proxy;" + line;
        Class anInterface = target.getClass().getInterfaces()[0];
        String interfaceName = anInterface.getName();
        String interfaceSimpleName = anInterface.getSimpleName();
        String importContent = "import " + interfaceName + ";" + line;
        String clazzFirstLineContent = "public class " + "$Proxy" + " implements " + interfaceSimpleName + " {" + line;
        String fieldContent = tab + "public " + interfaceSimpleName + " target;" + line;
        String constructorContent = tab + "public " + "$Proxy(" + interfaceSimpleName + " " + "target) {" + line;
        constructorContent += tab + tab + "this.target = target;" + line
                + tab + "}" + line;
        String methodContent = "";
        for (Method method : target.getClass().getDeclaredMethods()) {
            String methodName = method.getName();
            Class[] args = method.getParameterTypes();
            String argsContent = "";
            String paramContent = "";
            int flag = 0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();
                argsContent += temp + " " + " v" + flag + ",";
                paramContent += "v" + flag + ",";
                flag++;
            }
            if (argsContent.length() > 0) {
                argsContent = argsContent.substring(0, argsContent.lastIndexOf(",") - 1);
                paramContent = paramContent.substring(0, paramContent.lastIndexOf(",") - 1);
            }
            methodContent += tab + "public " + method.getReturnType().getSimpleName() + " " + method.getName() + "(" + argsContent + ") {" + line
                    + tab + tab + "System.out.println(\"---------log--------\");" + line
                    + tab + tab + "target." + methodName + "(" + paramContent + ");" + line
                    + tab + "}" + line;
        }
            content = packageContent + importContent + clazzFirstLineContent + fieldContent + constructorContent + methodContent + "}";
        File dir = new File("f:\\com\\proxy");
        File file = new File("f:\\com\\proxy\\$Proxy.java");
        try {
            if(!dir.exists()){
                dir.mkdirs();
            }
            //如果文件不存在,自动创建  但不能创建文件夹
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();

            //生成字节码 .class
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);
            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();
            //通过硬盘地址去 new 对象
            URL[] urls = new URL[]{new URL(("file:F:\\\\"))};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.proxy.$Proxy");
            Constructor constructor = clazz.getConstructor(anInterface);
            proxy = constructor.newInstance(target);
        }catch (Exception e){
            e.printStackTrace();
        }

        return proxy;
    }
}
