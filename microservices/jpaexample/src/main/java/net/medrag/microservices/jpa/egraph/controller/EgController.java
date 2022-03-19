package net.medrag.microservices.jpa.egraph.controller;

import net.medrag.microservices.jpa.egraph.dto.PostDto;
import net.medrag.microservices.jpa.egraph.dto.UserDto;
import net.medrag.microservices.jpa.egraph.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@RestController
@RequestMapping("/eg")
public class EgController {

    @Autowired
    private DbService dbService;

    @GetMapping("/posts")
    public List<PostDto> getPosts() {
        return dbService.getPosts();
    }

    @GetMapping("/post/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return dbService.getPost(id);
    }

    @GetMapping("/posts-eg")
    public List<PostDto> getPostsWithEg() {
        return dbService.getAllWithGraph();
    }

    @GetMapping("/post-eg/{id}")
    public PostDto getPostWithEg(@PathVariable Long id) {
        return dbService.getWithGraph(id);
    }

    @GetMapping("/user-eg/{id}")
    public UserDto getUserWithEg(@PathVariable Long id) {
        return dbService.getUser(id);
    }
}
