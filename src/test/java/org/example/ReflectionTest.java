package org.example;

import org.example.annotaion.Controller;
import org.example.annotaion.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan(){
        // 해당 패키지 하위의 모든 클래스 대상
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        // Controller, Service annotation이 붙어있는 클래스들을 찾아서 저장
        beans.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        beans.addAll(reflections.getTypesAnnotatedWith(Service.class));
        logger.debug("beans : [{}]", beans);
    }
}
