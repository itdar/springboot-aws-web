package com.tistory.itdar.springboot.config.auth.dto;

import com.tistory.itdar.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 만약 SessionUser 클래스를 새로 만들지 않고, User 엔티티 클래스를 그대로 사용하면
 * 직렬화를 엔티티 클래스에 넣어야함. 다른 엔티티와 관계가 형성될지 모르는데 직렬화 대상에 다 포함이 되면 다른 성능 이슈, 부수 효과 가
 * 발생할 확률이 높아짐. 그래서 직렬화 기능을 가진 세션 Dto를 하나 추가로 만드는 것임
 */

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
