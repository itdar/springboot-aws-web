package com.tistory.itdar.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit에서 단위 테스트 끝날 때마다 수행되는 메서드
    // 보통 보통 배포 전 전체 테스트 수행 시, 테스트간 데이터 침범을 막기 위해 사용함
    // 여러 테스트 동시 수행시 테스트용 DB인 H2에 데이터가 남아 있어서 테스트 실패 할 수도 있음
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기_Test() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()  // 테이블 posts에 insert/update 쿼리 실행함 (id값 있으면 update, 아니면 insert)
                            .title(title)
                            .content(content)
                            .author("gin@uos.ac.kr")
                            .build());

        // when
        List<Posts> postsList = postsRepository.findAll();  // 테이블 posts에 있는 모든 데이터 조회

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
