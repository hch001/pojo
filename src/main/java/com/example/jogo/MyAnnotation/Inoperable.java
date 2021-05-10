package com.example.jogo.MyAnnotation;

import java.lang.annotation.*;

/**
 * Fields which should not be modified by users had better be annotated.
 */
@Documented
@Retention(value = RetentionPolicy.SOURCE)
@Target(value = ElementType.FIELD)
public @interface Inoperable {

}
