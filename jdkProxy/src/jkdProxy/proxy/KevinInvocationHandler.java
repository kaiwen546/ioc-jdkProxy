package jkdProxy.proxy;


import jkdProxy.dao.TargetImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Kevin on 2019/1/23
 */
public class KevinInvocationHandler implements InvocationHandler {
    TargetImpl target;

    public KevinInvocationHandler(TargetImpl target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(target, args);
        System.out.println("-------------代理打印了日志-------------");
        return invoke;
    }
}
