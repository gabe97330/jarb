package org.jarb.sample.controller;

import javax.validation.Valid;

import org.jarb.constraint.BeanConstraintMetadata;
import org.jarb.constraint.BeanConstraintMetadataGenerator;
import org.jarb.sample.domain.Post;
import org.jarb.sample.domain.PostTitleAlreadyExistsException;
import org.jarb.sample.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("posts")
public class PostingController {

    @Autowired
    private PostingService postingService;
    
    @Autowired
    private BeanConstraintMetadataGenerator constraintMetadataGenerator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("posts/index");
        mav.addObject("posts", postingService.getAllPosts());
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody PostCreateResult post(@Valid Post post) {
        boolean success = false;
        String message = null;
        try {
            post = postingService.createPost(post);
            message = "Post was created succesfully!";
            success = true;
        } catch (PostTitleAlreadyExistsException e) {
            message = "Post title '" + post.getTitle() + "' already exists";
        }
        PostCreateResult result = new PostCreateResult();
        result.success = success;
        result.message = message;
        result.post = post;
        return result;
    }
    
    public static class PostCreateResult {
        private boolean success;
        private String message;
        private Post post;
        
        public boolean isSuccess() {
            return success;
        }
        
        public Post getPost() {
            return post;
        }
        
        public String getStatus() {
            return message;
        }
    }
    
    @RequestMapping(value = "structure", method = RequestMethod.GET)
    public BeanConstraintMetadata<Post> structure() {
        return constraintMetadataGenerator.describe(Post.class);
    }

}
