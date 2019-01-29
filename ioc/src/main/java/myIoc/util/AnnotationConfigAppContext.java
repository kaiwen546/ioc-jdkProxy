package myIoc.util;

import myIoc.annotation.MyAutowire;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Kevin on 2019/1/29
 */
public class AnnotationConfigAppContext {

    public void scan(String beanName) throws Exception {
        String path = this.getClass().getResource("/").getPath();
        String basePath = path + beanName.replaceAll("\\.", "\\/");
        System.out.println(basePath);
        File file = new File(basePath);
        String[] arr = file.list();
        for (String str : arr) {
            String s = str.replaceAll(".class", "");
            Class<?> clazz = Class.forName(beanName + "." + s);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(MyAutowire.class)){
                    clazz.newInstance();
                }
            }
        }

    }
}
