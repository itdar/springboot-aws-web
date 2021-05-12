package com.tistory.itdar.springboot.web;

import com.tistory.itdar.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러를 json반환하는 컨트롤러로 만듦, 예전 ResponseBody 어노테이션을 메소드마다 선언하던 것 한번에 사용하게함.
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        // 여기서 @RequestParam 어노테이션은, 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션이다.
        return new HelloResponseDto(name, amount);
    }

}
