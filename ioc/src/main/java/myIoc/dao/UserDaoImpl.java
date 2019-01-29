package myIoc.dao;

/**
 * Created by Kevin on 2019/1/24
 */
public class UserDaoImpl implements UserDao {
    @Override
    public void query() {
        System.out.println("假装请求了数据库");
    }
}
