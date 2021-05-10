package com.example.jogo.MyAnnotation;

import java.lang.annotation.*;

/**
 * Fields whose value had to be unique should be annotated.
 */
@Documented
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.SOURCE)
public @interface Unique {
}
