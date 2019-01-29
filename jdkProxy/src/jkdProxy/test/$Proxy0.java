package jkdProxy.test;

import jkdProxy.dao.CommonDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * Created by Kevin on 2019/1/23
 */
public final class $Proxy0 extends Proxy implements CommonDao {
    private static Method m1;
    private static Method m4;
    private static Method m3;
    private static Method m2;
    private static Method m0;

    public $Proxy0(InvocationHandler var1) {
        super(var1);
    }


    public final void query(String var1) {
        try {
            super.h.invoke(this, m4, new Object[]{var1});

        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    public final void query() {
        try {
            super.h.invoke(this, m3, (Object[]) null);

        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }
}


