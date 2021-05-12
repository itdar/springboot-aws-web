package com.tistory.itdar.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

/**
 * Main class
 * SpringBootApplication 어노테이션 위치부터 설정을 읽어간다.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 내장 WAS를 실행한다. (톰캣 설치없이 스프링부트로 만든 Jar 파일로서버 실행 가능)
        // 즉, 언제 어디서나 같은 환경에서 스프링 부트를 배포 할 수 있다.
        SpringApplication.run(Application.class, args);
    }

}
