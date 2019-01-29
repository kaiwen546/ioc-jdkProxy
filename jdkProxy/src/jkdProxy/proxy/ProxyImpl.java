package jkdProxy.proxy;


import jkdProxy.dao.CommonDao;

/**
 * Created by Kevin on 2019/1/22
 * 聚合
 */
public class ProxyImpl implements CommonDao {
    public CommonDao target;

    public ProxyImpl(CommonDao target) {
        this.target = target;
    }


    public void query() {
        System.out.println("---------log--------");
        target.query();
    }

    @Override
    public void query(String string) {
        System.out.println("---------log--------");
        target.query(string);
    }
}
