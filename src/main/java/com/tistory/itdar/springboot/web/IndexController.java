package com.tistory.itdar.springboot.web;

import com.tistory.itdar.springboot.config.auth.LoginUser;
import com.tistory.itdar.springboot.config.auth.dto.SessionUser;
import com.tistory.itdar.springboot.service.posts.PostsService;
import com.tistory.itdar.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        // Model 은 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장 할 수 있다
        // 여기서는 postsService.findAllDesc() 의 결과를 posts로 index.mustache에 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());

        // legacy
        // 앞서 CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 구현함
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // new
        // 기존에 User httpSession.getAttribute("user")로 가져오던 세션 정보값이 개선됨
        // 이제는 어느 컨트롤러든지 @LoginUser 만 사용하면 세션 정보를 가져올 수 있게 됨
        if (user != null) {
            model.addAttribute(("userName"), user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
