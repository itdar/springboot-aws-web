package com.tistory.itdar.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 어노테이션이 생성될 수 있는 위치를 지정, PARAMETER: 메서드의 파라미터로 선언된 객체에서만 사용할 수 있음
// 이 외에 클래스 선언문에 쓸수 있는 Type 등이 있음
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {   // 이 파일을 어노테이션 클래스서 지정, LoginUser 이름의 어노테이션이 생성된 것으로 보면 됨
}
