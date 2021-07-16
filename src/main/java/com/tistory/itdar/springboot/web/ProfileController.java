package com.tistory.itdar.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // env.getActiveProfiles()
        // -> 현재 실행중인 ActiveProfile 모두 가져온다
        // -> 즉 real, oauth, real-db 등이 활성화되어있으면, 3개 모두 들어있음
        // -> 여기서 real, real1, real2 모두 배포에 사용될 profile이라서 이 중 하나라도 있으면 그 값 반환
        // -> 이번 무중단 배포에서는 real1 과 real2 만 사용되지만, step2를 다시 사용해 볼수도 있으니 real도 남긴다.
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
