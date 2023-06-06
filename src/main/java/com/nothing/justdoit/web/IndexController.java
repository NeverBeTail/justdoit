package com.nothing.justdoit.web;


import com.nothing.justdoit.config.auth.LoginUser;
import com.nothing.justdoit.config.auth.dto.SessionUser;
import com.nothing.justdoit.service.PostsService;
import com.nothing.justdoit.web.dto.PostsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;


@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {

            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if (user != null) {
            model.addAttribute("userName", user.getName());
            return "posts-save";
        }else{
            model.addAttribute("posts", postsService.findAllDesc());
            model.addAttribute("login_alert", "로그인 해주세요.");
            return "index";
        }

    }


    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model,@LoginUser SessionUser user){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);
        model.addAttribute("sameUser", false);

        if (user != null) {
            model.addAttribute("userName", user.getName());
            if (Objects.equals(dto.getAuthor(), user.getName())) {
                model.addAttribute("sameUser", true);
            }
        }


        return "posts-update";
    }
}
