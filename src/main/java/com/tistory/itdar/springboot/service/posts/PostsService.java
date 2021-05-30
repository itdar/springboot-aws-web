package com.tistory.itdar.springboot.service.posts;

import com.tistory.itdar.springboot.domain.posts.Posts;
import com.tistory.itdar.springboot.domain.posts.PostsRepository;
import com.tistory.itdar.springboot.web.dto.PostsListResponseDto;
import com.tistory.itdar.springboot.web.dto.PostsResponseDto;
import com.tistory.itdar.springboot.web.dto.PostsSaveRequestDto;
import com.tistory.itdar.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional(readOnly = true) // readOnly true는, 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회속도 개선됨
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

}
