package com.example.jogo.MyAnnotation;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.FIELD)
public @interface Inoperable {
    /*
    不允许被用户修改
     */
}
