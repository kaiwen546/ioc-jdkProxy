package myIoc.test;

import myIoc.util.AnnotationConfigAppContext;

/**
 * Created by Kevin on 2019/1/29
 */
public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        AnnotationConfigAppContext annotationConfigAppContext = new AnnotationConfigAppContext();
        annotationConfigAppContext.scan("myIoc.service");
    }
}
