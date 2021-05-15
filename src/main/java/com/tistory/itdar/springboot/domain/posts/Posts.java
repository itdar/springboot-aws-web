package com.tistory.itdar.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 편의~ 필수 annotation 순으로 거리를 지정 -> 코틀린 등 언어 전환 시 롬복 어노테이션 등 삭제 용이함.
@Getter             // Getter와 달리 엔티티클래스에서는 Setter은 만들지 않고, 별도로 목적/의도에 맞는 메서드 생성함
@NoArgsConstructor  // 기본생성자 자동추가
@Entity             // 엔티티클래스는 DB테이블과 매칭될 클래스, JPA사용하면 DB에 실제 쿼리 날리기보다는 엔티티클래스 수정해서 작업
public class Posts {
    // 엔티티클래스는, 기본값으로 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름 매칭
    // e.g. SalesManager.java -> sales_manager table

    // 웬만하면 Entity의 PK는 Long 타입의 Auto-increment 추천함 (MySQL기준으로 bigint가 됨)
    // 주민번호 등의 유니크 키나 여러 키 조합 복합키로 PK 하면 난감한 상황 발생 가능함
    //  1. FK 맺을 때, 다른 테이블에서 복합키를 다 가지고 있거나 중간 테이블을 더 둬야하는 상황 발생
    //  2. 인덱스에 좋은 영향 X
    //  3. 유니크한 조건이 변경될 경우 PK 전체 수정해야 하는 일 발생
    // -> 주민번호, 복합키 등은 유니크 키로 별도 추가 추천함.
    @Id                                                     // 해당 테이블의 PK (private key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // PK 생성 규칙, IDENTITY -> auto_increment 하기 위한 옵션
    private Long id;

    @Column(length = 500, nullable = false)                 // 칼럼 표기 안해도 클래스의 필드는 다 컬럼
                                                            // 하지만, 기본값 외 옵션 변경을 위해 사용함. (VARCHAR=255 -> 500)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

                // 빌더를 사용하여 -> 어느 필드에 어느 값을 넣을지 명확하게 할 수 있음
    @Builder    // 빌더패턴 클래스 자동생성, 생성자 상단 선언 시 -> 생성자에 포함된 필드만 빌더에 포함 됨
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
