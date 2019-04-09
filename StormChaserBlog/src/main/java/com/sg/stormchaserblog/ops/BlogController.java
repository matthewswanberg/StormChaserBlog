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
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ShellyAnneIsaacs
 */
@Controller
public class BlogController {

    @Autowired
    BlogDao dao;

    @GetMapping("/createblog")
    public String displayTiny(Model model) {
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();
//        List<User> users = dao.getAllUsers();

        model.addAttribute("categories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        return "createBlog";
    }

    @GetMapping("/post")
    public String displayTinyPost(Integer id, Model model) {
        List<Author> authors = dao.getAllAuthors();
        List<Tag> tags = dao.getAllTags();
        List<Category> cats = dao.getAllCategories();
//        List<User> users = dao.getAllUsers();

        model.addAttribute("blogCategories", cats);
        model.addAttribute("blogHashTags", tags);
        model.addAttribute("blogAuthors", authors);

        Post post = dao.getOnePost(id);
        model.addAttribute("postid", post.getId());
        model.addAttribute("recentPosts", dao.get3RecentBlogs());
        model.addAttribute("postBody", post.getText());
        model.addAttribute("postAuthor", post.getAuthors());
        LocalDateTime date = post.getDate();
        String stringDate = date.format(
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        model.addAttribute("postDate", stringDate);
        model.addAttribute("postHeader", post.getTitle());
        model.addAttribute("postPhoto", post.getPhoto());
        model.addAttribute("postHashTags", post.getTags());
        model.addAttribute("postCategories", post.getCategories());
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        return "post";
    }

    @PostMapping("/editPost")
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
        dao.updatePost(updatePost.getId(), updatePost);
        return "redirect:/post";
    }

    public boolean getIsAdmin(Authentication auth) {
        boolean isAdmin = false;
        for (GrantedAuthority keycard : auth.getAuthorities()) {
            if (keycard.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @PostMapping("/blogprocess")
    public String submitTinyForm(Authentication auth, HttpServletRequest request, Model model) {
        Post post = new Post();

        List<Tag> postTags = new ArrayList<>();
        List<Author> postAuthors = new ArrayList<>();
        List<Category> postCats = new ArrayList<>();

        String authorName = request.getParameter("blogAuthor");
        List<Author> authors = dao.getAllAuthors();
        boolean uniqueAuthor = false;
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            if (author.getName().equals(authorName)) {
                uniqueAuthor = true;
                break;
            }
        }
        if (!uniqueAuthor) {
            Author author = new Author();
            author.setName(authorName);
            dao.addAuthor(author);
            postAuthors.add(author);
        }

        String newTags = request.getParameter("newblogHashTags");
        newTags = newTags.replace(" ", "");
        String[] newTagsArray = newTags.split("#");
        List<Tag> tags = dao.getAllTags();
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        for (int i = 0; i < newTagsArray.length; i++) {
            String currentTagName = newTagsArray[i];
            if (!tagNames.contains(currentTagName)) {
                Tag tag = new Tag();
                tag.setName(currentTagName);
                dao.addTag(tag);
                postTags.add(tag);
            }
        }

        String[] stringAuthors = request.getParameterValues("blogAuthors");
        String[] postCategories = request.getParameterValues("categories");
        String[] hashTags = request.getParameterValues("blogHashTags");

        if (null != hashTags) {
            for (int i = 0; i < hashTags.length; i++) {
                postTags.add(dao.getOneTag(Integer.parseInt(hashTags[i])));
            }
        }
        if (null != stringAuthors) {
            for (int i = 0; i < stringAuthors.length; i++) {
                postAuthors.add(dao.getOneAuthor(Integer.parseInt(stringAuthors[i])));
            }
        }
        if (null != postCategories) {
            for (int i = 0; i < postCategories.length; i++) {
                postCats.add(dao.getOneCategory(Integer.parseInt(postCategories[i])));
            }
        }
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
        post.setPublished(false);
        post.setStatic(false);
        boolean isAdmin = getIsAdmin(auth);
        if (isAdmin) {
            post.setApproved(true);
            String strIsStatic = request.getParameter("isStatic");
            String strIsPublished = request.getParameter("isPublished");
            if (null != strIsStatic && !strIsStatic.equals("")) {
                int isStatic = Integer.parseInt(strIsStatic);
                if (isStatic == 1) {
                    post.setStatic(true);
                }
            }
            if (null != strIsPublished && !strIsPublished.equals("")) {
                int isPublished = Integer.parseInt(strIsPublished);
                if (isPublished == 1) {
                    post.setPublished(true);
                }
            }
        }
        Post addedPost = dao.addPost(post);
        if (addedPost.isPublished()) {
            return "redirect:/post?id=" + addedPost.getId();
        } else {
            return "home";
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("staticPosts", dao.getStaticPostsNotHomePage());
        return "login";
    }

//    @GetMapping("/getPost")
//    public String getPost(HttpServletRequest request) {
//        String retrievedPostid = request.getParameter("getPost");
//        return "redirect:/post?getPost=" + retrievedPostid;
//    }
    @Controller
    public class ErrorController {

        @RequestMapping(value = "errors", method = RequestMethod.GET)
        public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

            ModelAndView errorPage = new ModelAndView("errorPage");
            String errorMsg = "";
            int httpErrorCode = getErrorCode(httpRequest);

            switch (httpErrorCode) {
                case 400: {
                    errorMsg = "Http Error Code: 400. Bad Request";
                    break;
                }
                case 401: {
                    errorMsg = "Http Error Code: 401. Unauthorized";
                    break;
                }
                case 404: {
                    errorMsg = "Http Error Code: 404. Resource not found";
                    break;
                }
                case 500: {
                    errorMsg = "Http Error Code: 500. Internal Server Error";
                    break;
                }
            }
            errorPage.addObject("errorMsg", errorMsg);
            return errorPage;
        }

        private int getErrorCode(HttpServletRequest httpRequest) {
            return (Integer) httpRequest
                    .getAttribute("javax.servlet.error.status_code");
        }
    }
}
