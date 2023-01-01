package org.example.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})         // Class, interface, enum
@Retention(RetentionPolicy.RUNTIME) // 유지기간
public @interface Controller {
}
