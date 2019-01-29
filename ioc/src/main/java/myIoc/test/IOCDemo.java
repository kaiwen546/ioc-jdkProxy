package myIoc.test;


import myIoc.service.UserService;
import myIoc.util.BeanFactory;

/**
 * Created by Kevin on 2019/1/24
 */
public class IOCDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");
        UserService service = (UserService) beanFactory.getBean("service");
        service.test();
    }
}
