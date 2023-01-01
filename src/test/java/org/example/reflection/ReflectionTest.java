package org.example.reflection;

import org.example.reflection.annotaion.Controller;
import org.example.reflection.annotaion.Service;
import org.example.reflection.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
        return beans;
    }

    @Test
    void controllerScan(){
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));
        logger.debug("beans : [{}]", beans);
    }

    @Test
    void showClass(){
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructor: [{}]", Arrays.stream(clazz.getDeclaredConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared method: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @Test
    void load() throws ClassNotFoundException {
        // 힙 영역에 로드되어 있는 클래스 타입의 객체를 가져오는 방법 1
        Class<User> clazz = User.class;

        // 힙 영역에 로드되어 있는 클래스 타입의 객체를 가져오는 방법 2
        // 객체 생성 -> getClass()
        User user = new User("dhseo", "ㅅㄷㅎ");
        Class<? extends User> clazz2 = user.getClass();

        // 힙 영역에 로드되어 있는 클래스 타입의 객체를 가져오는 방법 3
        Class<?> clazz3 = Class.forName("org.example.reflection.model.User");

        logger.debug("clazz : [{}],", clazz);
        logger.debug("clazz2 : [{}],", clazz2);
        logger.debug("clazz3 : [{}],", clazz3);

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz).isTrue();
    }
}
