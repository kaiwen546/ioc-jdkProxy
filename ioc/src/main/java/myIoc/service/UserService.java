package myIoc.service;


import myIoc.annotation.MyServicie;
import myIoc.dao.UserDao;

/**
 * Created by Kevin on 2019/1/24
 */
@MyServicie("service")
public class UserService {

    private UserDao dao;

    public void test(){
        dao.query();
        System.out.println("service");
    }

    /**
     * 使用有构造方法注入的时候,这个构造方法必须有  不然会报错
     * 而使用setter方法注入的时候要注释掉
     * 使用setter方法注入的时候不需要setDao方法  field.set(obj,obj) 可以直接注入
     * @param dao
     */
    //public UserService(UserDao dao) {
    //    this.dao = dao;
    //}

}
