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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Shelly
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:testApplication.properties")
public class BlogDaoImplTest {

    @Autowired
    JdbcTemplate heySql;

    @Autowired
    BlogDao dao;

    private Tag tag1 = new Tag();
    private Tag tag2 = new Tag();
    private Category cat1 = new Category();
    private Category cat2 = new Category();
    private MapLocation map1 = new MapLocation();
    private MapLocation map2 = new MapLocation();
    private Photo photo1 = new Photo();
    private Photo photo2 = new Photo();
    private Post post1 = new Post();
    private Post post2 = new Post();
    private Post post3 = new Post();
    private Author author1 = new Author();
    private Author author2 = new Author();
    private User user1 = new User();
    private User user2 = new User();

    public BlogDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tag1.setId(1);
        tag1.setName("virus");
        tag2.setId(2);
        tag2.setName("attack");
        cat1.setId(1);
        cat1.setName("cyber");
        cat2.setId(2);
        cat2.setName("security");
        map1.setId(1);
        map1.setDescription("I am a map");
        BigDecimal lat = new BigDecimal("38.328732");
        BigDecimal lon = new BigDecimal("-85.764771");
        map1.setLatitude(lat);
        map1.setLongitude(lon);
        map1.setDate(LocalDateTime.now());
        map2.setId(2);
        map2.setDescription("I am also a map");
        map2.setLatitude(lat);
        map2.setLongitude(lon);
        map2.setDate(LocalDateTime.now().plusDays(1));
        photo1.setId(1);
        photo1.setUrl("www.test1.com");
        photo2.setId(2);
        photo2.setUrl("www.test2.com");
        author1.setId(1);
        author1.setName("Allen");
        author2.setId(2);
        author2.setName("Austyn");
        user1.setId(1);
        user1.setName("AllenUser");
        user1.setPassword("password1");
        user1.setAuthor(author1);
        user1.setAdmin(true);
        user2.setId(2);
        user2.setName("AustynUser");
        user2.setPassword("password2");
        user2.setAuthor(author2);
        user2.setAdmin(false);
        List<Author> authors1 = new ArrayList<>();
        List<Author> authors2 = new ArrayList<>();
        List<Category> cats1 = new ArrayList<>();
        List<Category> cats2 = new ArrayList<>();
        List<Tag> tags1 = new ArrayList<>();
        List<Tag> tags2 = new ArrayList<>();
        authors1.add(author1);
        cats1.add(cat1);
        tags1.add(tag1);
        authors2.add(author1);
        cats2.add(cat1);
        tags2.add(tag1);
        authors2.add(author2);
        cats2.add(cat2);
        tags2.add(tag2);
        post1.setId(1);
        post1.setDate(LocalDateTime.now());
        post1.setPhoto(photo1);
        post1.setText("This is the body of the blog post.");
        post1.setTitle("This is the title of the blog.");
        post1.setAuthors(authors1);
        post1.setCategories(cats1);
        post1.setTags(tags1);
        post1.setApproved(true);
        post1.setPublished(true);
        post1.setStatic(true);
        post2.setId(2);
        post2.setDate(LocalDateTime.now());
        post2.setPhoto(photo2);
        post2.setText("This is the body of the blog post.");
        post2.setTitle("This is the title of the blog.");
        post2.setAuthors(authors2);
        post2.setCategories(cats2);
        post2.setTags(tags2);
        post2.setApproved(true);
        post2.setPublished(true);
        post2.setStatic(false);
        post3.setId(3);
        post3.setDate(LocalDateTime.now().plusDays(1));
        post3.setPhoto(photo2);
        post3.setText("This is the body of the blog post.");
        post3.setTitle("This is the title of the blog.");
        post3.setAuthors(authors2);
        post3.setCategories(cats2);
        post3.setTags(tags2);
        post3.setApproved(true);
        post3.setPublished(true);
        post3.setStatic(false);
        heySql.update("DELETE FROM posts_to_authors");
        heySql.update("DELETE FROM posts_to_hashtags");
        heySql.update("DELETE FROM posts_to_categories");
        heySql.update("DELETE FROM users");
        heySql.update("DELETE FROM authors");
        heySql.update("DELETE FROM maps");
        heySql.update("DELETE FROM hashtags");
        heySql.update("DELETE FROM categories");
        heySql.update("DELETE FROM posts");
        heySql.update("DELETE FROM photos");
    }

    @After
    public void tearDown() {
    }

    //test add and get individual entities from database
    
    @Test
    public void testAddGetCategory() {
        Category addedCat = dao.addCategory(cat2);
        Assert.assertNotNull(addedCat);
        Category returnedCat = dao.getOneCategory(addedCat.getId());
        Assert.assertNotNull(returnedCat);
        Assert.assertEquals("retrieved category should be the one expected",
                addedCat, returnedCat);
    }

    
    @Test
    public void testAddGetTag() {
        Tag addedTag = dao.addTag(tag2);
        Assert.assertNotNull(addedTag);
        Tag returnedTag = dao.getOneTag(addedTag.getId());
        Assert.assertNotNull(returnedTag);
        Assert.assertEquals("retrieved tag should be the one expected",
                addedTag, returnedTag);
    }

    
    @Test
    public void testAddGetPhoto() {
        Photo addedPhoto = dao.addPhoto(photo2);
        Assert.assertNotNull(addedPhoto);
        Photo returnedPhoto = dao.getOnePhoto(addedPhoto.getId());
        Assert.assertNotNull(returnedPhoto);
        Assert.assertEquals("retrieved photo should be the one expected",
                addedPhoto, returnedPhoto);
    }

    
    @Test
    public void testAddGetMap() {
        MapLocation addedMap = dao.addMap(map2);
        Assert.assertNotNull(addedMap);
        MapLocation returnedMap = dao.getOneMap(addedMap.getId());
        Assert.assertNotNull(returnedMap);
        Assert.assertEquals("retrieved map should be the one expected",
                addedMap, returnedMap);
    }

    
    @Test
    public void testAddGetAuthor() {
        Author addedAuthor = dao.addAuthor(author2);
        Assert.assertNotNull(addedAuthor);
        Author returnedAuthor = dao.getOneAuthor(addedAuthor.getId());
        Assert.assertNotNull(returnedAuthor);
        Assert.assertEquals("retrieved author should be the one expected",
                addedAuthor, returnedAuthor);
    }

    //test and get complex entities from database
    @Ignore
    @Test
    public void testAddGetUser() {
        dao.addAuthor(author2);
        User addedUser = dao.addUser(user2);
        Assert.assertNotNull(addedUser);
        User returnedUser = dao.getOneUser(addedUser.getId());
        Assert.assertNotNull(returnedUser);
        Assert.assertEquals("retrieved user should be the one expected",
                addedUser, returnedUser);
    }

    
    @Test
    public void testAddGetPost() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo2);
        Post addedPost = dao.addPost(post2);
        Assert.assertNotNull(addedPost);
        Post returnedPost = dao.getOnePost(addedPost.getId());
        Assert.assertNotNull(returnedPost);
        Assert.assertEquals("retrieved post should be the one expected",
                addedPost, returnedPost);
    }

    @Test
    public void getAllDeleteCategories() {
        Category addedCat1 = dao.addCategory(cat1);
        Category addedCat2 = dao.addCategory(cat2);
        List<Category> cats = dao.getAllCategories();
        Assert.assertFalse("List of cats should not be empty", cats.isEmpty());
        Assert.assertEquals("Cat list should be expected length", 2,
                cats.size());
        Assert.assertTrue("List should contain the added categories",
                cats.contains(addedCat1));
        Assert.assertTrue("List should contain the added categories",
                cats.contains(addedCat2));
        dao.deleteCategory(addedCat2.getId());
        List<Category> catsWithDeletion = dao.getAllCategories();
        Assert.assertFalse("Shorter list of cats should not be empty",
                catsWithDeletion.isEmpty());
        Assert.assertEquals("Shorter cat list should be expected length", 1,
                catsWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining category",
                catsWithDeletion.contains(addedCat1));
        Assert.assertFalse("List should not contain the deleted category",
                catsWithDeletion.contains(addedCat2));
    }

    @Test
    public void getAllDeleteTags() {
        Tag addedTag1 = dao.addTag(tag1);
        Tag addedTag2 = dao.addTag(tag2);
        List<Tag> tags = dao.getAllTags();
        Assert.assertFalse("List of tags should not be empty", tags.isEmpty());
        Assert.assertEquals("tags list should be expected length", 2,
                tags.size());
        Assert.assertTrue("List should contain the added tags",
                tags.contains(addedTag1));
        Assert.assertTrue("List should contain the added tags",
                tags.contains(addedTag2));
        dao.deleteTag(addedTag2.getId());
        List<Tag> tagsWithDeletion = dao.getAllTags();
        Assert.assertFalse("Shorter list of tags should not be empty",
                tagsWithDeletion.isEmpty());
        Assert.assertEquals("Shorter tags list should be expected length", 1,
                tagsWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining tags",
                tagsWithDeletion.contains(addedTag1));
        Assert.assertFalse("List should not contain the deleted tags",
                tagsWithDeletion.contains(addedTag2));
    }

    @Test
    public void getAllDeleteAuthors() {
        Author addedAuthor1 = dao.addAuthor(author1);
        Author addedAuthor2 = dao.addAuthor(author2);
        List<Author> authors = dao.getAllAuthors();
        Assert.assertFalse("List of author should not be empty", authors.isEmpty());
        Assert.assertEquals("author list should be expected length", 2,
                authors.size());
        Assert.assertTrue("List should contain the added author",
                authors.contains(addedAuthor1));
        Assert.assertTrue("List should contain the added author",
                authors.contains(addedAuthor2));
        dao.deleteAuthor(addedAuthor2.getId());
        List<Author> authorWithDeletion = dao.getAllAuthors();
        Assert.assertFalse("Shorter list of author should not be empty",
                authorWithDeletion.isEmpty());
        Assert.assertEquals("Shorter author list should be expected length", 1,
                authorWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining author",
                authorWithDeletion.contains(addedAuthor1));
        Assert.assertFalse("List should not contain the deleted author",
                authorWithDeletion.contains(addedAuthor2));
    }

    @Ignore
    @Test
    public void getAllDeleteUsers() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        User addedUser1 = dao.addUser(user1);
        User addedUser2 = dao.addUser(user2);
        List<User> users = dao.getAllUsers();
        Assert.assertFalse("List of users should not be empty", users.isEmpty());
        Assert.assertEquals("users list should be expected length", 2,
                users.size());
        Assert.assertTrue("List should contain the added users",
                users.contains(addedUser1));
        Assert.assertTrue("List should contain the added users",
                users.contains(addedUser2));
        dao.deleteUser(addedUser2.getId());
        List<User> usersWithDeletion = dao.getAllUsers();
        Assert.assertFalse("Shorter list of users should not be empty",
                usersWithDeletion.isEmpty());
        Assert.assertEquals("Shorter users list should be expected length", 1,
                usersWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining users",
                usersWithDeletion.contains(addedUser1));
        Assert.assertFalse("List should not contain the deleted users",
                usersWithDeletion.contains(addedUser2));
    }

    @Test
    public void getAllDeleteMaps() {
        MapLocation addedMap1 = dao.addMap(map1);
        MapLocation addedMap2 = dao.addMap(map2);
        List<MapLocation> maps = dao.getAllMaps();
        Assert.assertFalse("List of map should not be empty", maps.isEmpty());
        Assert.assertEquals("map list should be expected length", 2,
                maps.size());
        Assert.assertTrue("List should contain the added map",
                maps.contains(addedMap1));
        Assert.assertTrue("List should contain the added map",
                maps.contains(addedMap2));
        dao.deleteMap(addedMap2.getId());
        List<MapLocation> mapsWithDeletion = dao.getAllMaps();
        Assert.assertFalse("Shorter list of map should not be empty",
                mapsWithDeletion.isEmpty());
        Assert.assertEquals("Shorter map list should be expected length", 1,
                mapsWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining map",
                mapsWithDeletion.contains(addedMap1));
        Assert.assertFalse("List should not contain the deleted map",
                mapsWithDeletion.contains(addedMap2));

    }

    @Test
    public void getAllDeletePhotos() {
        Photo addedPhoto1 = dao.addPhoto(photo1);
        Photo addedPhoto2 = dao.addPhoto(photo2);
        List<Photo> photos = dao.getAllPhotos();
        Assert.assertFalse("List of photo should not be empty", photos.isEmpty());
        Assert.assertEquals("photo list should be expected length", 2,
                photos.size());
        Assert.assertTrue("List should contain the added photo",
                photos.contains(addedPhoto1));
        Assert.assertTrue("List should contain the added photo",
                photos.contains(addedPhoto2));
        dao.deletePhoto(addedPhoto2.getId());
        List<Photo> photosWithDeletion = dao.getAllPhotos();
        Assert.assertFalse("Shorter list of photo should not be empty",
                photosWithDeletion.isEmpty());
        Assert.assertEquals("Shorter photo list should be expected length", 1,
                photosWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining photo",
                photosWithDeletion.contains(addedPhoto1));
        Assert.assertFalse("List should not contain the deleted photo",
                photosWithDeletion.contains(addedPhoto2));
    }

    @Test
    public void getAllDeleteBlogPostsDescAsc() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost1 = dao.addPost(post2);
        Post addedPost2 = dao.addPost(post3);
        List<Post> posts = dao.getAllBlogPostsDesc();
        List<Post> postsAsc = dao.getAllBlogPostsAsc();
        Assert.assertFalse("List of posts should not be empty", posts.isEmpty());
        Assert.assertEquals("posts list should be expected length", 2,
                posts.size());
        Assert.assertTrue("List should contain the added posts",
                posts.contains(addedPost1));
        Assert.assertTrue("List should contain the added posts",
                posts.contains(addedPost2));
        Assert.assertFalse("List of posts should not be empty", postsAsc.isEmpty());
        Assert.assertEquals("posts list should be expected length", 2,
                postsAsc.size());
        Assert.assertTrue("List should contain the added posts",
                postsAsc.contains(addedPost1));
        Assert.assertTrue("List should contain the added posts",
                postsAsc.contains(addedPost2));
        Assert.assertEquals("descending posts should start with addedpost2",
                addedPost2, posts.get(0));
        Assert.assertEquals("ascending posts should start with addedpost1",
                addedPost1, postsAsc.get(0));
        dao.deletePost(addedPost2.getId());
        List<Post> postsWithDeletion = dao.getAllBlogPostsDesc();
        Assert.assertFalse("Shorter list of posts should not be empty",
                postsWithDeletion.isEmpty());
        Assert.assertEquals("Shorter posts list should be expected length", 1,
                postsWithDeletion.size());
        Assert.assertTrue("Shorter list should contain the remaining posts",
                postsWithDeletion.contains(addedPost1));
        Assert.assertFalse("List should not contain the deleted posts",
                postsWithDeletion.contains(addedPost2));
    }

    @Test
    public void getAllDeleteStaticPosts() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost1 = dao.addPost(post1);
        Post addedPost2 = dao.addPost(post2);
        List<Post> posts = dao.getAllStaticPosts();
        Assert.assertFalse("List of posts should not be empty", posts.isEmpty());
        Assert.assertEquals("posts list should be expected length", 1,
                posts.size());
        Assert.assertTrue("List should contain the static posts",
                posts.contains(addedPost1));
        Assert.assertFalse("List should nost contain the added non-static posts",
                posts.contains(addedPost2));
        dao.deletePost(addedPost1.getId());
        List<Post> postsWithDeletion = dao.getAllStaticPosts();
        Assert.assertTrue("Shorter list of posts should not be empty",
                postsWithDeletion.isEmpty());
    }

    @Test
    public void getPostsByTag() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        Tag addedTag1 = dao.addTag(tag1);
        Tag addedTag2 = dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost1 = dao.addPost(post1);
        Post addedPost2 = dao.addPost(post2);
        List<Post> postsMultiple = dao.getPostsByTagId(addedTag1.getId());
        List<Post> postsSingle = dao.getPostsByTagId(addedTag2.getId());
        Assert.assertFalse("list is not empty", postsSingle.isEmpty());
        Assert.assertEquals("List should be expected length", 1, postsSingle.size());
        Assert.assertTrue("List should contain post associated with tag",
                postsSingle.contains(addedPost2));
        Assert.assertFalse("List should not contain other post",
                postsSingle.contains(addedPost1));
        Assert.assertFalse("list is not empty", postsMultiple.isEmpty());
        Assert.assertEquals("List should be expected length", 2, postsMultiple.size());
        Assert.assertTrue("List should contain post associated with tag",
                postsMultiple.contains(addedPost2));
        Assert.assertTrue("List should contain other post",
                postsMultiple.contains(addedPost1));
    }

    @Test
    public void getPostsByCat() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        Category addedCat1 = dao.addCategory(cat1);
        Category addedCat2 = dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost1 = dao.addPost(post1);
        Post addedPost2 = dao.addPost(post2);
        List<Post> postsMultiple = dao.getPostsByCategoryId(addedCat1.getId());
        List<Post> postsSingle = dao.getPostsByCategoryId(addedCat2.getId());
        Assert.assertFalse("list is not empty", postsSingle.isEmpty());
        Assert.assertEquals("List should be expected length", 1, postsSingle.size());
        Assert.assertTrue("List should contain post associated with cat",
                postsSingle.contains(addedPost2));
        Assert.assertFalse("List should not contain other post",
                postsSingle.contains(addedPost1));
        Assert.assertFalse("list is not empty", postsMultiple.isEmpty());
        Assert.assertEquals("List should be expected length", 2, postsMultiple.size());
        Assert.assertTrue("List should contain post associated with cat",
                postsMultiple.contains(addedPost2));
        Assert.assertTrue("List should contain other post",
                postsMultiple.contains(addedPost1));
    }

    @Test
    public void getPostsByAuthor() {
        Author addedAuthor1 = dao.addAuthor(author1);
        Author addedAuthor2 = dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost1 = dao.addPost(post1);
        Post addedPost2 = dao.addPost(post2);
        List<Post> postsMultiple = dao.getPostsByAuthorId(addedAuthor1.getId());
        List<Post> postsSingle = dao.getPostsByAuthorId(addedAuthor2.getId());
        Assert.assertFalse("list is not empty", postsSingle.isEmpty());
        Assert.assertEquals("List should be expected length", 1, postsSingle.size());
        Assert.assertTrue("List should contain post associated with author",
                postsSingle.contains(addedPost2));
        Assert.assertFalse("List should not contain other post",
                postsSingle.contains(addedPost1));
        Assert.assertFalse("list is not empty", postsMultiple.isEmpty());
        Assert.assertEquals("List should be expected length", 2, postsMultiple.size());
        Assert.assertTrue("List should contain post associated with author",
                postsMultiple.contains(addedPost2));
        Assert.assertTrue("List should contain other post",
                postsMultiple.contains(addedPost1));
    }

    @Test
    public void getMostRecentMap() {
        MapLocation addedMap1 = dao.addMap(map1);
        MapLocation addedMap2 = dao.addMap(map2);
        MapLocation returnedMap = dao.getCurrentMap();
        Assert.assertNotNull(returnedMap);
        Assert.assertEquals("Map should match expected", addedMap2, returnedMap);
    }
    
    @Test
    public void testUpdateCategory() {
        Category addedCat = dao.addCategory(cat1);
        dao.updateCategory(addedCat.getId(), cat2);
        cat2.setId(addedCat.getId());
        Category updatedCat = dao.getOneCategory(addedCat.getId());
        Assert.assertNotNull(updatedCat);
        Assert.assertEquals(cat2, updatedCat);
    }
   
    @Test
    public void testUpdateTag() {
        Tag addedTag = dao.addTag(tag1);
        dao.updateTag(addedTag.getId(), tag2);
        tag2.setId(addedTag.getId());
        Tag updatedTag = dao.getOneTag(addedTag.getId());
        Assert.assertNotNull(updatedTag);
        Assert.assertEquals(tag2, updatedTag);
    }

    
    @Test
    public void testUpdatePhoto() {
        Photo addedPhoto = dao.addPhoto(photo1);
        dao.updatePhoto(addedPhoto.getId(), photo2);
        photo2.setId(addedPhoto.getId());
        Photo updatedPhoto = dao.getOnePhoto(addedPhoto.getId());
        Assert.assertNotNull(updatedPhoto);
        Assert.assertEquals(photo2, updatedPhoto);
    }

    
    @Test
    public void testUpdateMap() {
        MapLocation addedMap = dao.addMap(map1);
        dao.updateMap(addedMap.getId(), map2);
        map2.setId(addedMap.getId());
        MapLocation updatedMap = dao.getOneMap(addedMap.getId());
        Assert.assertNotNull(updatedMap);
        Assert.assertEquals(map2, updatedMap);
    }

   
    @Test
    public void testUpdateAuthor() {
        Author addedAuthor = dao.addAuthor(author1);
        dao.updateAuthor(addedAuthor.getId(), author2);
        author2.setId(addedAuthor.getId());
        Author updatedAuthor = dao.getOneAuthor(addedAuthor.getId());
        Assert.assertNotNull(updatedAuthor);
        Assert.assertEquals(author2, updatedAuthor);
    }

    
    @Ignore
    @Test
    public void testUpdateUser() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        User addedUser = dao.addUser(user1);
        dao.updateUser(addedUser.getId(), user2);
        user2.setId(addedUser.getId());
        User updatedUser = dao.getOneUser(addedUser.getId());
        Assert.assertNotNull(updatedUser);
        Assert.assertEquals(user2, updatedUser);
    }

  
    @Test
    public void testUpdatePost() {
        dao.addAuthor(author1);
        dao.addAuthor(author2);
        dao.addCategory(cat1);
        dao.addCategory(cat2);
        dao.addTag(tag1);
        dao.addTag(tag2);
        dao.addPhoto(photo1);
        dao.addPhoto(photo2);
        Post addedPost = dao.addPost(post1);
        dao.updatePost(addedPost.getId(), post2);
        post2.setId(addedPost.getId());
        Post updatedPost = dao.getOnePost(addedPost.getId());
        Assert.assertNotNull(updatedPost);
        Assert.assertEquals(post2, updatedPost);
    }
    
    //get most frequent tags
    //getUserByUsername
    
}
