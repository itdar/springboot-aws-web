package com.tistory.itdar.springboot.web;

import com.tistory.itdar.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 테스트 진행할 때, JUnit에 내장된 실행자 외 다른 실행자 실행, 여기서는 SpringRunner 스프링 실행자 실행
// 즉, 스프링 부트 테스트와 JUnit 사이에서 연결자 역할
@RunWith(SpringRunner.class)
// Web(Spring MVC)에 집중할 수 있는 어노테이션으로, 선언하면 @Controller, @ControllerAdvice 등 사용 가능
// 하지만 @Service, @Component, @Repository 등은 사용 불가
// 여기서는 컨트롤러만 사용하기 때문에 선언함.
// 추가,
// Repository, Service, Component 는 스캔 대상이 아니라서, SecurityConfig 는 읽었지만 그에 필요한
// CustomOAuth2UserService 는 읽을 수 없었음. 그래서 스캔 대상에 SecurityConfig 를 제거한다.
// WebMvcTest 의 secure 옵션은 2.1부터 deprecated 됨, 사용 X
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    // 스프링이 관리하는 Bean을 주입 받는다.
    @Autowired
    private MockMvc mvc; // Web API 테스트할 때 사용, 스프링 MVC테스트의 시작점, 이 클래스를 통해 HTTP GET,POST 등 API 테스트.

    @WithMockUser(roles="USER")
    @Test
    public void Hello가_리턴된다() throws Exception {
        String hello = "hello";

        // MockMvc를 통해 /hello 주소로 HTTP GET 요청을 한다. (chaining 지원함)
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())             // header status 검증 (200, 404, 500 등)
                .andExpect(content().string(hello));    // content (응답 본문) 검증
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name", name)    // param으로 API테스트 할 때 사용될 요청 파라미터 설정함. 단, 값은 String만 허용됨.
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))       // jsonPath:
                .andExpect(jsonPath("$.amount", is(amount)));  // json 응답값을 필드별로 검증할 수 있는 메서드로,
                                                                        // $ 기준으로 필드명을 명시한다.
                                                                        // 여기에서는, name, amount
    }

    @WithMockUser(roles="USER")
    @Test
    public void Return_404Page_Test() throws Exception {
        mvc.perform(get("/not"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles="USER")
    @Test
    public void ReturnSuccessfulPageTest() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().is2xxSuccessful());
    }

    @WithMockUser(roles="USER")
    @Test
    public void ReturnClientErrorPageTest() throws Exception {
        mvc.perform(get("/hell"))
                .andExpect(status().is4xxClientError());
    }
}
