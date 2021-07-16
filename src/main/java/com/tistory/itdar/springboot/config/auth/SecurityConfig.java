package com.tistory.itdar.springboot.config.auth;

import com.tistory.itdar.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity      // SpringSecurity 설정들을 활성화 시켜준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // csrf disable, headers frame options disable -> h2-console 화면을 사용하기 위해서 해당 옵션들 disable 한다.
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점, 이게 선언되어야 antMatches 옵션 사용 가능함.
                // antMatches는 권한 관리 대상을 지정하는 옵션, URL, HTTP 메서드별로 관리가 가능함
                // / 등 지정 URL들은 permitAll() 옵션을 통해 전체 열람 권한
                    .antMatchers(
                            "/",
                            "/css/**",
                            "/images/**",
                            "/js/**",
                            "/h2-console/**",
                            "/profile").permitAll()
                // /api/v1/** 주소를 가진 API는 USER 권한을 가진 사람만 가능
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                // anyRequest 설정된 값 이외 나머지 URL들 -> 여기서는 인증된 사용자들만 허용(즉, 로그인한 사용자들)
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                    .userInfoEndpoint()
                // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록함
                // 리소스 서버 (즉, 소셜서비스들) 에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시한다.
                    .userService(customOAuth2UserService);
    }

}
