package net.medrag.microservices.jpa.egraph.service;

import net.medrag.microservices.jpa.egraph.dto.PostDto;
import net.medrag.microservices.jpa.egraph.dto.UserDto;
import net.medrag.microservices.jpa.egraph.entity.Post;
import net.medrag.microservices.jpa.egraph.entity.User;
import net.medrag.microservices.jpa.egraph.repository.PostRepository;
import net.medrag.microservices.jpa.egraph.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stanislav Tretyakov
 * 19.03.2022
 */
@Service
public class DbService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public List<PostDto> getPosts() {
        final List<Post> all = postRepository.findAll();
        return all.stream().map(it -> modelMapper.map(it, PostDto.class)).toList();
    }

    @Transactional
    public PostDto getPost(Long id) {
        Post post = postRepository.getById(id);
        return modelMapper.map(post, PostDto.class);
    }

    /**
     * Throws org.hibernate.loader.MultipleBagFetchException (cartesian product)
     * https://www.baeldung.com/java-hibernate-multiplebagfetchexception
     */
    public PostDto getWithGraphInvalid(Long id) {
        Post post = postRepository.getByIdWithGraphAndBagException(id);
        return modelMapper.map(post, PostDto.class);
    }

    public PostDto getWithGraph(Long id) {
        Post post = postRepository.getByIdWithGraph(id);
        Post postComments = postRepository.getCommentsByIdWithGraph(id);
        post.setComments(postComments.getComments());
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllWithGraph() {
        List<Post> posts = postRepository.getAllWithGraph();
        final List<Post> result = postRepository.getCommentsForAllWithGraph(posts);
        return result.stream().map(it -> modelMapper.map(it, PostDto.class)).collect(Collectors.toList());
    }

    public UserDto getUser(Long id) {
        final User user = userRepository.getByIdWithGraph(id);
        return modelMapper.map(user, UserDto.class);
    }
}
