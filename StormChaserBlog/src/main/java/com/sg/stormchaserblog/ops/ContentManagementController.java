/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.ops;

import com.sg.stormchaserblog.dao.BlogDao;
import com.sg.stormchaserblog.model.Author;
import com.sg.stormchaserblog.model.Category;
import com.sg.stormchaserblog.model.Photo;
import com.sg.stormchaserblog.model.Post;
import com.sg.stormchaserblog.model.Tag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author matthewswanberg
 */
@Controller
public class ContentManagementController {
    
    @Autowired
    BlogDao dao;
    
    
    @GetMapping("deletePost")
    public String deletePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deletePost(id);
        
        return "redirect:/content-manager";
    }
    
    @PostMapping("/editPost-in-manager")
    public String editPost(HttpServletRequest request) {
        Post post = dao.getOnePost(Integer.parseInt(request.getParameter("getPost")));

        List<Tag> postTags = new ArrayList<>();
        List<Author> postAuthors = new ArrayList<>();
        List<Category> postCats = new ArrayList<>();

        String[] stringAuthors = request.getParameterValues("blogAuthors");
        String[] postCategories = request.getParameterValues("categories");
        String[] hashTags = request.getParameterValues("blogHashTags");

        for (int i = 0; i < hashTags.length; i++) {
            postTags.add(dao.getOneTag(Integer.parseInt(hashTags[i])));
        }

        for (int i = 0; i < stringAuthors.length; i++) {
            postAuthors.add(dao.getOneAuthor(Integer.parseInt(stringAuthors[i])));
        }

        for (int i = 0; i < postCategories.length; i++) {
            postCats.add(dao.getOneCategory(Integer.parseInt(postCategories[i])));
        }
        Post updatePost = post;
        Photo photo = new Photo();
        photo.setUrl(request.getParameter("blogPhoto"));
        dao.addPhoto(photo);

        post.setDate(LocalDateTime.now());
        post.setText(request.getParameter("blogBody"));
        post.setAuthors(postAuthors);
        post.setTitle(request.getParameter("blogHeader"));
        post.setTags(postTags);
        post.setPhoto(photo);
        post.setCategories(postCats);
        post.setApproved(false);
        post.setStatic(false);
        post.setPublished(false);
        
        
       
            String strIsStatic = request.getParameter("static");
            String strIsPublished = request.getParameter("published");
            String strIsApproved = request.getParameter("approved");
            if (null != strIsApproved && !strIsApproved.equals("")) {
                if (strIsApproved.equalsIgnoreCase("on")) {
                    post.setApproved(true);
                }
            }
            if (null != strIsStatic && !strIsStatic.equals("")) {
                if (strIsStatic.equals("on")) {
                    post.setStatic(true);
                }
            }
            if (null != strIsPublished && !strIsPublished.equals("")) {
                if (strIsPublished.equals("on")) {
                    post.setPublished(true);
                   
                }
            }
        
        
        dao.updatePost(updatePost.getId(), updatePost);
        return "redirect:/content-manager";
    }
    
}
