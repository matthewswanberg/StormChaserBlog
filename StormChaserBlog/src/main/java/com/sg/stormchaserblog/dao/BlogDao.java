/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.dao;

import com.sg.stormchaserblog.model.Author;
import com.sg.stormchaserblog.model.Category;
import com.sg.stormchaserblog.model.MapLocation;
import com.sg.stormchaserblog.model.Photo;
import com.sg.stormchaserblog.model.Post;
import com.sg.stormchaserblog.model.Tag;
import com.sg.stormchaserblog.model.User;
import java.util.List;

/**
 *
 * @author ShellyAnneIsaacs
 */
public interface BlogDao {
    
    //special requests
    
    Post getAboutUsPost();
    
    List<Post> getStaticPostsNotHomePage();
    
    List<Post> get3RecentBlogs();
    
    List<Tag> get10FrequentTags();
    
    List<Post> getPostsByTagId(int tagId);
    
    List<Post> getPostsByCategoryId(int catId);
    
    List<Post> getPostsByAuthorId(int authorId);
    
    MapLocation getCurrentMap();
    
    User getUserByUsername(String username);
    
    //get all
    List<Post> getAllBlogPostsDesc();
    
    List<Post> getAllBlogPostsAsc();
    
    List<Post> getAllStaticPosts();
    
    List<Tag> getAllTags();
    
    List<Category> getAllCategories();
    
    List<Author> getAllAuthors();
    
    List<User> getAllUsers();
    
    List<Photo> getAllPhotos();
    
    List<MapLocation> getAllMaps();
    
    //get one
    Post getOnePost(int id);
    
    Tag getOneTag(int id);
    
    Category getOneCategory(int id);
    
    Author getOneAuthor(int id);
    
    User getOneUser(int id);
    
    Photo getOnePhoto(int id);
    
    MapLocation getOneMap(int id);
    
    //add one
    Post addPost(Post post);
    
    Tag addTag(Tag tag);
    
    Category addCategory(Category category);
    
    Author addAuthor(Author author);
    
    User addUser(User user);
    
    Photo addPhoto(Photo photo);
    
    MapLocation addMap(MapLocation map);
    
    //edit one
    Post updatePost(int postId, Post post);
    
    Tag updateTag(int tagId, Tag tag);
    
    Category updateCategory(int catId, Category category);
    
    Author updateAuthor(int authorId, Author author);
    
    User updateUser(int userId, User user);
    
    Photo updatePhoto(int photoId, Photo photo);
    
    MapLocation updateMap(int mapId, MapLocation map);
    
    //delete one
    
    void deletePost(int postId);
    
    void deleteTag(int tagId);
    
    void deleteCategory(int catId);
    
    void deleteAuthor(int authorId);
    
    void deleteUser(int userId);
    
    void deletePhoto(int photoId);
    
    void deleteMap(int mapId);
    
}
