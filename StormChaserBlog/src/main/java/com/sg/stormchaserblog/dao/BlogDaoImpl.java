/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.stormchaserblog.dao;

import com.sg.stormchaserblog.model.Author;
import com.sg.stormchaserblog.model.Category;
import com.sg.stormchaserblog.model.Photo;
import com.sg.stormchaserblog.model.Post;
import com.sg.stormchaserblog.model.Tag;
import com.sg.stormchaserblog.model.User;
import com.sg.stormchaserblog.model.MapLocation;
import com.sg.stormchaserblog.model.Role;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ShellyAnneIsaacs
 */
@Repository
public class BlogDaoImpl implements BlogDao {

    @Autowired
    JdbcTemplate heySql;

    //---------------------------special requests---------------------------//
    @Override
    public List<Post> get3RecentBlogs() {
        List<Post> posts = heySql.query("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE is_static = 0 "
                + "ORDER BY date DESC "
                + "LIMIT 3",
                new PostMapper());
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public List<Tag> get10FrequentTags() {
        List<Tag> tags = heySql.query("SELECT hashtags.* ,"
                + "COUNT(hashtags.hashtag_id) AS value_occurrence "
                + "FROM posts_to_hashtags INNER JOIN hashtags "
                + "ON posts_to_hashtags.hashtag_id = hashtags.hashtag_id "
                + "GROUP BY hashtag_id "
                + "ORDER BY value_occurrence DESC "
                + "LIMIT 10", new TagMapper());
        for (Tag tag : tags) {
            this.getOneTag(tag.getId());
        }

        return tags;
    }

    @Override
    public List<Post> getPostsByTagId(int tagId) {
        List<Post> posts = heySql.query("SELECT * FROM posts_to_hashtags "
                + "INNER JOIN posts ON posts_to_hashtags.post_id = posts.post_id "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE hashtag_id = ? "
                + "ORDER BY date DESC",
                new PostMapper(), tagId);
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public List<Post> getPostsByCategoryId(int catId) {
        List<Post> posts = heySql.query("SELECT DISTINCT * FROM posts_to_categories "
                + "INNER JOIN posts ON posts_to_categories.post_id = posts.post_id "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE category_id = ? "
                + "ORDER BY date DESC",
                new PostMapper(), catId);
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public List<Post> getPostsByAuthorId(int authorId) {
        List<Post> posts = heySql.query("SELECT * FROM posts_to_authors "
                + "INNER JOIN posts ON posts_to_authors.post_id = posts.post_id "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE author_id = ? "
                + "ORDER BY date DESC",
                new PostMapper(), authorId);
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public MapLocation getCurrentMap() {
        MapLocation map = heySql.queryForObject("SELECT * FROM maps "
                + "ORDER BY map_date DESC LIMIT 1", new MapMapper());
        return map;
    }

    //---------------------------get all---------------------------//
    @Override
    public List<Post> getAllBlogPostsDesc() {
        List<Post> posts = heySql.query("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE is_static = 0 "
                + "ORDER BY date DESC",
                new PostMapper());
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public List<Post> getAllBlogPostsAsc() {
        List<Post> posts = heySql.query("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE is_static = 0 "
                + "ORDER BY date ASC",
                new PostMapper());
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    @Override
    public Post getAboutUsPost() {
        Post post = heySql.queryForObject("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE title = 'About Us'",
                new PostMapper());
        setPostAuthors(post);
        setPostTags(post);
        setPostCategories(post);
        return post;
    }
    
    @Override
    public List<Post> getStaticPostsNotHomePage(){
        List<Post> posts = heySql.query("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE is_static = 1 "
                + "AND is_published = 1 "
                + "AND title NOT IN ('About Us')",
                new PostMapper());
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }
        return posts;
    }
    
    @Override
    public List<Post> getAllStaticPosts() {
        List<Post> posts = heySql.query("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE is_static = 1 "
                + "ORDER BY date DESC",
                new PostMapper());
        for (Post post : posts) {
            setPostAuthors(post);
            setPostTags(post);
            setPostCategories(post);
        }

        return posts;
    }

    private void setPostAuthors(Post post) {
        List<Author> authors = heySql.query("SELECT * FROM authors "
                + "INNER JOIN posts_to_authors "
                + "ON authors.author_id = posts_to_authors.author_id "
                + "WHERE posts_to_authors.post_id = ?",
                new AuthorMapper(), post.getId());
        post.setAuthors(authors);
    }

    private void setPostTags(Post post) {
        List<Tag> tags = heySql.query("SELECT * FROM hashtags "
                + "INNER JOIN posts_to_hashtags "
                + "ON hashtags.hashtag_id = posts_to_hashtags.hashtag_id "
                + "WHERE posts_to_hashtags.post_id = ?",
                new TagMapper(), post.getId());
        post.setTags(tags);
    }

    private void setPostCategories(Post post) {
        List<Category> categories = heySql.query("SELECT * FROM categories "
                + "INNER JOIN posts_to_categories "
                + "ON categories.category_id = posts_to_categories.category_id "
                + "WHERE posts_to_categories.post_id = ?",
                new CategoryMapper(), post.getId());
        post.setCategories(categories);
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = heySql.query("SELECT * FROM hashtags",
                new TagMapper());
        return tags;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> cats = heySql.query("SELECT * FROM categories",
                new CategoryMapper());
        return cats;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = heySql.query("SELECT * FROM authors",
                new AuthorMapper());
        return authors;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = heySql.query("SELECT * FROM users "
                + "INNER JOIN authors ON users.author_id = authors.author_id",
                new UserMapper());
        for (User user : users) {
            this.setUserRoles(user);
        }
        return users;
    }

    private void setUserRoles(User user) {
        List<Role> returnedRoles = heySql.query("SELECT * FROM roles INNER JOIN users_to_roles "
                + "ON roles.role_id = users_to_roles.role_id WHERE user_id = ?",
                new RoleMapper(), user.getId());
        Set<Role> roles = new HashSet<>();
        for (Role role : returnedRoles) {
            roles.add(role);
        }
        user.setRoles(roles);
    }

    @Override
    public List<Photo> getAllPhotos() {
        List<Photo> photos = heySql.query("SELECT * FROM photos ",
                new PhotoMapper());
        return photos;
    }

    @Override
    public List<MapLocation> getAllMaps() {
        List<MapLocation> maps = heySql.query("SELECT * FROM maps",
                new MapMapper());
        return maps;
    }

    //---------------------------get one---------------------------//
    @Override
    public Post getOnePost(int id) {
        Post post = heySql.queryForObject("SELECT * FROM posts "
                + "INNER JOIN photos ON posts.photo_id = photos.photo_id "
                + "WHERE post_id = ?",
                new PostMapper(), id);
        setPostAuthors(post);
        setPostTags(post);
        setPostCategories(post);
        return post;
    }

    @Override
    public Tag getOneTag(int id) {
        Tag tag = heySql.queryForObject("SELECT * FROM hashtags "
                + "WHERE hashtag_id = ?", new TagMapper(), id);
        return tag;
    }

    @Override
    public Category getOneCategory(int id) {
        Category cat = heySql.queryForObject("SELECT * FROM categories "
                + "WHERE category_id = ?", new CategoryMapper(), id);
        return cat;
    }

    @Override
    public Author getOneAuthor(int id) {
        Author author = heySql.queryForObject("SELECT * FROM authors "
                + "WHERE author_id = ?", new AuthorMapper(), id);
        return author;
    }

    @Override
    public User getOneUser(int id) {
        User user = heySql.queryForObject("SELECT * FROM users "
                + "INNER JOIN authors ON users.author_id = authors.author_id "
                + "WHERE user_id = ?", new UserMapper(), id);
        this.setUserRoles(user);
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = heySql.queryForObject("SELECT * FROM users "
                + "INNER JOIN authors ON users.author_id = authors.author_id "
                + "WHERE user_name = ?", new UserMapper(), username);
        this.setUserRoles(user);
        return user;
    }

    @Override
    public Photo getOnePhoto(int id) {
        Photo photo = heySql.queryForObject("SELECT * FROM photos "
                + "WHERE photo_id = ?", new PhotoMapper(), id);
        return photo;
    }

    @Override
    public MapLocation getOneMap(int id) {
        MapLocation map = heySql.queryForObject("SELECT * FROM maps "
                + "WHERE map_id = ?", new MapMapper(), id);
        return map;
    }

    //---------------------------add one---------------------------//
    //manages the relationships between posts and categories, tags, and authors
    @Override
    @Transactional
    public Post addPost(Post post) {
        Photo photo = post.getPhoto();
        List<Author> authors = post.getAuthors();
        List<Category> cats = post.getCategories();
        List<Tag> tags = post.getTags();
        heySql.update("INSERT INTO posts (date, title, text, photo_id, "
                + "is_published, is_approved, is_static) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                post.getDate(), post.getTitle(), post.getText(),
                photo.getId(), post.isPublished(), post.isApproved(),
                post.isStatic());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        post.setId(newId);
        for (Author author : authors) {
            heySql.update("INSERT INTO posts_to_authors (author_id, post_id) "
                    + "VALUES (?, ?)", author.getId(), post.getId());
        }

        for (Category cat : cats) {
            heySql.update("INSERT INTO posts_to_categories (post_id, category_id) "
                    + "VALUES (?, ?)", post.getId(), cat.getId());
        }

        for (Tag tag : tags) {
            heySql.update("INSERT INTO posts_to_hashtags (post_id, hashtag_id) "
                    + "VALUES (?, ?)", post.getId(), tag.getId());
        }

        return post;
    }

    @Override
    public Tag addTag(Tag tag) {
        heySql.update("INSERT INTO hashtags (hashtag_name) "
                + "VALUES (?)", tag.getName());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        tag.setId(newId);
        return tag;
    }

    @Override
    public Category addCategory(Category category) {
        heySql.update("INSERT INTO categories (category_name) "
                + "VALUES (?)", category.getName());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        category.setId(newId);
        return category;
    }

    @Override
    public Author addAuthor(Author author) {
        heySql.update("INSERT INTO authors (author_name) "
                + "VALUES (?)", author.getName());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        author.setId(newId);
        return author;
    }

    //must add new author before new user associated with the author
    @Override
    public User addUser(User user) {
        Author author = user.getAuthor();
        heySql.update("INSERT INTO users (user_name, password, author_id, "
                + "is_admin) VALUES (?, ?, ?, ?)", user.getName(),
                user.getPassword(), author.getId(), user.isAdmin());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        user.setId(newId);
        return user;
    }

    @Override
    public Photo addPhoto(Photo photo) {
        heySql.update("INSERT INTO photos (url) "
                + "VALUES (?)", photo.getUrl());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        photo.setId(newId);
        return photo;
    }

    @Override
    public MapLocation addMap(MapLocation map) {
        heySql.update("INSERT INTO maps (map_description, latitude, longitude, "
                + "map_date) VALUES (?, ?, ?, ?)", map.getDescription(),
                map.getLatitude(), map.getLongitude(), map.getDate());
        int newId = heySql.queryForObject("SELECT LAST_INSERT_ID()",
                Integer.class);
        map.setId(newId);
        return map;
    }

    //---------------------------update---------------------------//
    @Override
    @Transactional
    public Post updatePost(int postId, Post post) {
        Photo photo = post.getPhoto();
        List<Author> authors = post.getAuthors();
        List<Category> cats = post.getCategories();
        List<Tag> tags = post.getTags();
        heySql.update("UPDATE posts SET date = ?, title = ?, text = ?, "
                + "photo_id = ?, is_published = ?, is_approved = ?, "
                + "is_static = ? WHERE post_id = ?", post.getDate(),
                post.getTitle(), post.getText(), photo.getId(),
                post.isPublished(), post.isApproved(), post.isStatic(),
                postId);
        if (!authors.isEmpty()) {
            heySql.update("DELETE FROM posts_to_authors WHERE post_id = ?",
                    postId);
            for (Author author : authors) {
                heySql.update("INSERT INTO posts_to_authors (author_id, post_id) "
                        + "VALUES (?, ?)", author.getId(), postId);
            }
        }
        if (!cats.isEmpty()) {
            heySql.update("DELETE FROM posts_to_categories WHERE post_id = ?",
                    postId);
            for (Category cat : cats) {
                heySql.update("INSERT INTO posts_to_categories (post_id, category_id) "
                        + "VALUES (?, ?)", postId, cat.getId());
            }
        }
        if (!tags.isEmpty()) {
            heySql.update("DELETE FROM posts_to_hashtags WHERE post_id = ?",
                    postId);
            for (Tag tag : tags) {
                heySql.update("INSERT INTO posts_to_hashtags (post_id, hashtag_id) "
                        + "VALUES (?, ?)", postId, tag.getId());
            }
        }
        return post;
    }

    @Override
    public Tag updateTag(int tagId, Tag tag) {
        heySql.update("UPDATE hashtags SET hashtag_name = ? "
                + "WHERE hashtag_id = ?", tag.getName(), tagId);
        Tag updatedTag = this.getOneTag(tagId);
        return updatedTag;
    }

    @Override
    public Category updateCategory(int catId, Category category) {
        heySql.update("UPDATE categories SET category_name = ? "
                + "WHERE category_id = ?", category.getName(), catId);
        Category updatedCategory = this.getOneCategory(catId);
        return updatedCategory;
    }

    @Override
    public Author updateAuthor(int authorId, Author author) {
        heySql.update("UPDATE authors SET author_name = ? WHERE author_id = ?",
                author.getName(), authorId);
        Author updatedAuthor = this.getOneAuthor(authorId);
        return updatedAuthor;
    }

    @Override
    public User updateUser(int userId, User user) {
        Author author = user.getAuthor();
        heySql.update("UPDATE users SET user_name = ?, password = ?, "
                + "author_id = ?, is_admin = ? WHERE user_id = ?",
                user.getName(), user.getPassword(), author.getId(),
                user.isAdmin(), userId);
        User updatedUser = this.getOneUser(userId);
        return updatedUser;
    }

    @Override
    public Photo updatePhoto(int photoId, Photo photo) {
        heySql.update("UPDATE photos SET url = ? WHERE photo_id = ?",
                photo.getUrl(), photoId);
        Photo updatedPhoto = this.getOnePhoto(photoId);
        return updatedPhoto;
    }

    @Override
    public MapLocation updateMap(int mapId, MapLocation map) {
        heySql.update("UPDATE maps SET map_description = ?, latitude = ?, "
                + "longitude = ?, map_date = ? WHERE map_id = ?", map.getDescription(),
                map.getLatitude(), map.getLongitude(), map.getDate(), mapId);
        MapLocation updatedMap = this.getOneMap(mapId);
        return updatedMap;

    }

    //---------------------------delete---------------------------//
    @Override
    @Transactional
    public void deletePost(int postId) {
        heySql.update("DELETE FROM posts_to_authors WHERE post_id = ?", postId);
        heySql.update("DELETE FROM posts_to_categories WHERE post_id = ?",
                postId);
        heySql.update("DELETE FROM posts_to_hashtags WHERE post_id = ?",
                postId);
        heySql.update("DELETE FROM posts WHERE post_id = ?", postId);
    }

    @Override
    @Transactional
    public void deleteTag(int tagId) {
        heySql.update("DELETE FROM posts_to_hashtags WHERE hashtag_id = ?",
                tagId);
        heySql.update("DELETE FROM hashtags WHERE hashtag_id = ?", tagId);
    }

    @Override
    @Transactional
    public void deleteCategory(int catId) {
        heySql.update("DELETE FROM posts_to_categories WHERE category_id = ?",
                catId);
        heySql.update("DELETE FROM categories WHERE category_id = ?", catId);
    }

    @Override
    @Transactional
    public void deleteAuthor(int authorId) {
        heySql.update("DELETE FROM posts_to_authors WHERE author_id = ?",
                authorId);
        heySql.update("UPDATE users SET author_id = null WHERE author_id = ?",
                authorId);
        heySql.update("DELETE FROM authors WHERE author_id = ?", authorId);
    }

    @Override
    public void deleteUser(int userId) {
        heySql.update("DELETE FROM users WHERE user_id = ?", userId);
    }

    @Override
    @Transactional
    public void deletePhoto(int photoId) {
        heySql.update("UPDATE posts SET photo_id = ? WHERE photo_id = ?",
                null, photoId);
        heySql.update("DELETE FROM photos WHERE photo_id = ?", photoId);
    }

    @Override
    public void deleteMap(int mapId) {
        heySql.update("DELETE FROM maps WHERE map_id = ?", mapId);
    }

    //---------------------------mappers---------------------------//
    public static final class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();
            post.setId(rs.getInt("post_id"));
            post.setDate(rs.getTimestamp("date").toLocalDateTime());
            post.setTitle(rs.getString("title"));
            post.setText(rs.getString("text"));
            post.setPublished(rs.getBoolean("is_published"));
            post.setApproved(rs.getBoolean("is_approved"));
            post.setStatic(rs.getBoolean("is_static"));
            String url = rs.getString("url");
            int photoId = rs.getInt("photo_id");
            Photo photo = new Photo(photoId, url);
            post.setPhoto(photo);
            return post;
        }

    }

    public static final class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setId(rs.getInt("hashtag_id"));
            tag.setName(rs.getString("hashtag_name"));
            return tag;
        }

    }

    public static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            cat.setId(rs.getInt("category_id"));
            cat.setName(rs.getString("category_name"));
            return cat;
        }

    }

    public static final class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            Author author = new Author();
            author.setId(rs.getInt("author_id"));
            author.setName(rs.getString("author_name"));
            return author;
        }

    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setAdmin(rs.getBoolean("is_admin"));
            String authorName = rs.getString("author_name");
            int authorId = rs.getInt("author_id");
            Author author = new Author();
            author.setId(authorId);
            author.setName(authorName);
            user.setAuthor(author);
            return user;
        }

    }

    public static final class PhotoMapper implements RowMapper<Photo> {

        @Override
        public Photo mapRow(ResultSet rs, int i) throws SQLException {
            Photo photo = new Photo();
            photo.setId(rs.getInt("photo_id"));
            photo.setUrl(rs.getString("url"));
            return photo;
        }

    }

    public static final class MapMapper implements RowMapper<MapLocation> {

        @Override
        public MapLocation mapRow(ResultSet rs, int i) throws SQLException {
            MapLocation map = new MapLocation();
            map.setId(rs.getInt("map_id"));
            map.setDescription(rs.getString("map_description"));
            map.setLatitude(rs.getBigDecimal("latitude"));
            map.setLongitude(rs.getBigDecimal("longitude"));
            map.setDate(rs.getTimestamp("map_date").toLocalDateTime());
            return map;
        }

    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setId(rs.getInt("role_id"));
            role.setRole(rs.getString("role_description"));
            return role;
        }
    }
}
