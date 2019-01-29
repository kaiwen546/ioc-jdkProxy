package jkdProxy.test;

import jkdProxy.dao.CommonDao;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Kevin on 2019/1/22
 */
public class ProxyTest {
    public static void main(String[] args) {
        //CommonDao instance =(CommonDao) ProxyUtil.getInstance(new TargetImpl());
        //instance.query("abc");

        //CommonDao proxyInstance = (CommonDao) Proxy.newProxyInstance(CommonDao.class.getClassLoader(), new Class[]{CommonDao.class},new KevinInvocationHandler(new TargetImpl()));
        //proxyInstance.query();

        byte[] bytes = ProxyGenerator.generateProxyClass("com.proxy.$Proxy0", new Class[]{CommonDao.class}, 17);
        File file = new File("f:\\com\\proxy\\$Proxy0");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
