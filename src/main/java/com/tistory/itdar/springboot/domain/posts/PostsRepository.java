package com.tistory.itdar.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 보통 ibatis, MyBatis 등의 쿼리맵퍼에서 Dao 라고 불리는 DB Layer 접근자
 * JPA에서는 Repository라고 부르며, 인터페이스로 생성한다.
 * 단순히 인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메서드 자동 생성 됨
 * @Repository 를 추가할 필요도 없음
 * Entity class 와 Entity repository는 함께 위치해야함 -> 도메인 패키지에서 함께 관리함.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

}
