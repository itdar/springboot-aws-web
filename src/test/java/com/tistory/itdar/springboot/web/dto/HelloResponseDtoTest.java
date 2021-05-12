package com.tistory.itdar.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void Lombok_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        // 여기서 assertThat 은 assertj라는 테스트 검증 라이브러리의 검증 메소드, 메서드 체이닝 지원됨
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);

        /**
         * Junit과 assertj를 비교하여 assertj의 장점으로는,
         * 1. CoreMatchers와 달리 추가 라이브러리 필요하지 않음 (ex. JUnit의 assertThat은 is() 처럼 CoreMatchers library 필요함
         * 2. 자동완성 더 확실히 지원됨 (ex. CoreMatchers 와 같은 Matcher 라이브러리 자동완성 지원이 약함
         */
    }

}
