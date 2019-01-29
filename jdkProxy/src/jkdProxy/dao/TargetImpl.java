package jkdProxy.dao;

/**
 * Created by Kevin on 2019/1/22
 */
public class TargetImpl implements CommonDao {
    @Override
    public void query(String string) {
        System.out.println(string);
    }

    @Override
    public void query() {
        System.out.println("假装查询了数据库");
    }
}
