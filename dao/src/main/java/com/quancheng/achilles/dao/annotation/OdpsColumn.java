package com.quancheng.achilles.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
@Inherited
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD})
public @interface OdpsColumn {
     String value() ;
}
