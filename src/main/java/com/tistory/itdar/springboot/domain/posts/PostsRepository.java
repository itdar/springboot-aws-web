package com.tistory.itdar.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 보통 ibatis, MyBatis 등의 쿼리맵퍼에서 Dao 라고 불리는 DB Layer 접근자
 * JPA에서는 Repository라고 부르며, 인터페이스로 생성한다.
 * 단순히 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메서드 자동 생성 됨
 * @Repository 를 추가할 필요도 없음
 * Entity class 와 Entity repository는 함께 위치해야함 -> 도메인 패키지에서 함께 관리함.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메서드 자동 생성

    // 실제 규모 있는 곳에서는 데이터 조회에 대해서 조회용 프레임워크를 추가로 사용함
    // 보통 Querydsl 사용 추천함 (타입 안정성, 레퍼런스 다수)
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")  // 여기서는 그냥 SpringDataJpa 제공 메서드 외에 이것을 직접 썼음
    List<Posts> findAllDesc();
}
