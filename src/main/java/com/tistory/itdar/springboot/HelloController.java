package com.tistory.itdar.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 json반환하는 컨트롤러로 만듦, 예전 ResponseBody 어노테이션을 메소드마다 선언하던 것 한번에 사용하게함.
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
