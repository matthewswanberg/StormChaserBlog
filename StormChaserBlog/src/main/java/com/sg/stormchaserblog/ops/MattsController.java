/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.ops;

import com.sg.stormchaserblog.dao.BlogDao;
import com.sg.stormchaserblog.model.Author;
import com.sg.stormchaserblog.model.Category;
import com.sg.stormchaserblog.model.MapLocation;
import com.sg.stormchaserblog.model.Tag;
import java.math.BigDecimal;
import com.sg.stormchaserblog.model.Post;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author matthewswanberg
 */
@Controller
public class MattsController {

    @Autowired
    BlogDao dao;

    // These pages are currently all static, but will eventually have dynamic content
    // home page
    @GetMapping({"/home", "/"})
    public String displayhome(Model model) {
        model.addAttribute("recentPosts", dao.get3RecentBlogs());
        model.addAttribute("tags", dao.get10FrequentTags());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        return "home";
    }

    // about us page
    @GetMapping("/about-us")
    public String displayabout(Model model) {
        model.addAttribute("post", dao.getAboutUsPost());
        model.addAttribute("recentPosts", dao.get3RecentBlogs());
        model.addAttribute("mapLocation", dao.getCurrentMap());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        return "AboutUs";
    }

    // content manager
    @GetMapping({"/content-manager", "/content-manager-desc"})
    public String displayManager(Model model) {
        model.addAttribute("posts", dao.getAllBlogPostsDesc());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "contentManager";
    }

    @GetMapping("/content-manager-asc")
    public String displayManagerAscending(Model model) {
        model.addAttribute("posts", dao.getAllBlogPostsAsc());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "contentManager";
    }

    @GetMapping({"/content-manager-static", "/content-manager-static-desc"})
    public String displayManagerStatic(Model model) {
        model.addAttribute("posts", dao.getAllStaticPosts());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "contentManager";
    }

    @GetMapping("/content-manager-static-asc")
    public String displayManagerStaticAscending(Model model) {
        model.addAttribute("posts", dao.getAllStaticPosts());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "contentManager";
    }

    // view posts
    @GetMapping({"/posts", "/posts-descending"})
    public String displayPosts(Model model) {
        model.addAttribute("posts", getPublishedPostsDesc());
        model.addAttribute("categories", dao.getAllCategories());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "posts";
    }

    @GetMapping("/posts-ascending")
    public String displayPostsAscending(Model model) {
        model.addAttribute("posts", getPublishedPostsAsc());
        model.addAttribute("categories", dao.getAllCategories());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "posts";
    }

    @GetMapping("/posts-by-category")
    public String displayPostsByCategory(Integer id, Model model) {
        model.addAttribute("posts", getCatFilteredPosts(id));
        model.addAttribute("categories", dao.getAllCategories());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "posts";
    }

    @GetMapping("/posts-by-tag")
    public String displayPostsByTag(Integer id, Model model) {
        model.addAttribute("posts", this.getTagFilteredPosts(id));
        model.addAttribute("categories", dao.getAllCategories());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        
        return "posts";
    }

    private List<Post> getPublishedPostsDesc() {
        List<Post> allPosts = dao.getAllBlogPostsDesc();
        List<Post> publishedPosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.isPublished()){
                publishedPosts.add(post);
            }
        }
        return publishedPosts;
    }
    
    private List<Post> getPublishedPostsAsc() {
        List<Post> allPosts = dao.getAllBlogPostsAsc();
        List<Post> publishedPosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.isPublished()){
                publishedPosts.add(post);
            }
        }
        return publishedPosts;
    }
    
    private List<Post> getTagFilteredPosts(int id) {
        List<Post> allPosts = dao.getPostsByTagId(id);
        List<Post> publishedPosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.isPublished()){
                publishedPosts.add(post);
            }
        }
        return publishedPosts;
    }
    
    private List<Post> getCatFilteredPosts(int id) {
        List<Post> allPosts = dao.getPostsByCategoryId(id);
        List<Post> publishedPosts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.isPublished()){
                publishedPosts.add(post);
            }
        }
        return publishedPosts;
    }
    
    // view posts
}
