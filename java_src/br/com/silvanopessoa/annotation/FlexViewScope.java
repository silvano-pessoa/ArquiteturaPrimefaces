package br.com.silvanopessoa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;

@Scope(value = "view")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlexViewScope {

}
