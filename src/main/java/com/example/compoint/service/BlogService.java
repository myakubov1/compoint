package com.example.compoint.service;

import com.example.compoint.dtos.BlogRequest;
import com.example.compoint.dtos.BlogResponse;
import com.example.compoint.entity.BlogEntity;
import com.example.compoint.entity.UserEntity;
import com.example.compoint.exception.BlogNotFound;
import com.example.compoint.exception.UserNotFound;
import com.example.compoint.mappers.BlogMapper;
import com.example.compoint.repository.BlogRepo;
import com.example.compoint.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlogRepo blogRepo;

    public BlogRequest create(Long userid, BlogRequest blogRequest) throws UserNotFound {
        Optional<UserEntity> userOptional = userRepo.findById(userid);
        UserEntity user = userOptional.orElseThrow(() -> new UserNotFound("User not found"));
        BlogEntity blogEntity = new BlogEntity();

        blogEntity.setUser(user);
        blogEntity.setContent(blogRequest.getContent());
        blogRepo.save(blogEntity);

        return BlogMapper.INSTANCE.blogEntityToBlogRequest(blogEntity);
    }

    public List<BlogResponse> getAll() {
        List<BlogEntity> blogs = new ArrayList<>();
        blogRepo.findAll().forEach(blogs::add);

        return blogs.stream()
                .map(BlogMapper.INSTANCE::blogEntityToBlogReposnse)
                .collect(Collectors.toList());
    }

    public String delete(Long id) throws BlogNotFound {
        Optional<BlogEntity> optionalBlog = blogRepo.findById(id);
        if (optionalBlog.isPresent()) {
            blogRepo.delete(optionalBlog.get());
        } else {
            throw new BlogNotFound("Comment not found");
        }
        return "Comment deleted";
    }
}
