package com.example.jogo.MyAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(value = ElementType.FIELD)
public @interface FieldName {
    String value();
}
