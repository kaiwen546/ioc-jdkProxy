package myIoc.dao;

/**
 * Created by Kevin on 2019/1/25
 */
public class TestDaoImpl implements UserDao {

    @Override
    public void query() {
        System.out.println("test dao impl");
    }
}
