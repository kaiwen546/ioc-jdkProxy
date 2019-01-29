package myIoc.util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Kevin on 2019/1/24
 */
public class BeanFactory {
    Map<String, Object> map = new HashMap<String, Object>();

    public BeanFactory(String xml) {
        parseXml(xml);
    }

    public void parseXml(String xml) {
        //F:\IdeaProjects\spring-reading\iocImpl\target\classes\spring.xml
        String path = this.getClass().getResource("/").getPath() + xml;
        File file = new File(path);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();
            String autoValue = rootElement.attribute("default-autowire").getValue();
            boolean flag = false;
            if(autoValue.equals("byType")){
                flag = true;
            }
            for (Iterator<Element> it = rootElement.elementIterator(); it.hasNext();) {
                Element firstChild = it.next();
                Attribute idAttr = firstChild.attribute("id");
                String beanName = idAttr.getValue();
                Attribute classAttr = firstChild.attribute("class");
                String qualifiedName = classAttr.getValue();
                Class clazz = Class.forName(qualifiedName);
                Object object = null;
                /**
                 * 维护依赖关系
                 * 看这个对象有没有依赖(判断是否有property属性 或者判断类是否有属性:判断UserService中有没有对应的属性,有则注入
                 * 如果有则注入
                 */
                for (Iterator<Element> secNode = firstChild.elementIterator(); secNode.hasNext();) {
                    Element secondChild = secNode.next();
                    if(secondChild.getName().equals("property")){

                        /**
                         * 得到ref的值,通过ref的值得到对象(从map中获取
                         * 得到name的值,然后根据值获取一个Field的对象
                         * 通过field的set方法 set那个对象
                         */
                        object = clazz.newInstance();
                        String refValue = secondChild.attribute("ref").getValue();
                        Object injectObject = map.get(refValue);
                        String nameValue = secondChild.attribute("name").getValue();
                        Field field = clazz.getDeclaredField(nameValue);
                        //由于dao属性是私有的,所以要去设置权限值,允许我们去设置
                        field.setAccessible(true);
                        field.set(object,injectObject);
                    }else{
                        String refValue = secondChild.attribute("ref").getValue();
                        Object injectObject = map.get(refValue);
                        Class<?> anInterface = injectObject.getClass().getInterfaces()[0];
                        //如果没有构造方法可以直接 clazz.newInstance() 此时是通过构造方法去new对象 所以不能直接去new
                        Constructor<?> constructor = clazz.getConstructor(anInterface);
                        object = constructor.newInstance(injectObject);
                    }
                }
                if(flag){
                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (Field field : declaredFields) {
                        Class<?> injectObjectType = field.getType();
                        Object injectObject = null;
                        int count = 0;

                        for (String key : map.keySet()) {
                            Class<?> temp = map.get(key).getClass().getInterfaces()[0];
                            if(temp.getName().equals(injectObjectType.getName())){
                                injectObject = map.get(key);
                                count++;
                            }
                        }
                        if(count > 1){
                            throw new NotOnlyFeildException("有多个个对象");
                        }else {
                            object = clazz.newInstance();
                            field.setAccessible(true);
                            field.set(object,injectObject);
                        }
                    }
                }

                if(object == null){
                    object = clazz.newInstance();
                }

                map.put(beanName,object);
            }
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName){
        return map.get(beanName);
    }
}
