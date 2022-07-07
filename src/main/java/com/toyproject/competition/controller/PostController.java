package com.toyproject.competition.controller;

import com.toyproject.competition.dto.PostDto;
import com.toyproject.competition.dto.PostResponseDto;
import com.toyproject.competition.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 게시판 목록
    @GetMapping("/list")
    public String postList(Model model) {
        List<PostResponseDto> postList = postService.postList();
        model.addAttribute("list", postList);

        return "pages/post";
    }

    // 게시글 입력 페이지
    @GetMapping("/write")
    public String postForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "postForm";
    }

    // 게시글 저장
    @PostMapping("/save")
    public String savePost(@Valid PostDto postDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("postDto", postDto);

            Map<String, String> validatorResult = postService.validateHandling(bindingResult);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "postForm";
        }


        postService.savePost(postDto);

        return "redirect:/post/list";
    }
}
