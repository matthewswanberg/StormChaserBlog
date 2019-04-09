USE stormchaserblogDB;

SELECT * FROM posts
INNER JOIN photos ON posts.photo_id = photos.photo_id 
WHERE is_static = 1
AND title NOT IN ("About Us");

SELECT * FROM map
ORDER BY map_date DESC
LIMIT 1;

SELECT `name`, hashtag_id, COUNT(hashtag_id) AS value_occurrence 
    FROM posts_to_hashtags INNER JOIN hashtags ON posts_to_hashtags.hashtag_id = hashtags.id
    GROUP BY hashtag_id
    ORDER BY value_occurrence DESC
    LIMIT 10;

SELECT * FROM users INNER JOIN authors ON users.author_id = authors.author_id;

SELECT *
FROM authors;
SELECT *
FROM categories;
SELECT *
FROM hashtags;
SELECT *
FROM photos;
SELECT *
FROM posts;
SELECT *
FROM posts_to_authors;
SELECT *
FROM posts_to_categories;
SELECT *
FROM posts_to_hashtags;
SELECT *
FROM users;

USE stormchaserblogDB;
SELECT * FROM posts;
SELECT * FROM categories;
SELECT * FROM posts_to_categories;
SELECT * FROM posts_to_hashtags;
SELECT * FROM photos;
SELECT * FROM hashtags;
SELECT * FROM maps;
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM users_to_roles;

SELECT * FROM posts_to_hashtags 
INNER JOIN posts ON posts_to_hashtags.post_id = posts.post_id 
INNER JOIN photos ON posts.photo_id = photos.photo_id 
WHERE hashtag_id = 49 
ORDER BY date DESC;

SELECT * FROM maps ORDER BY map_date DESC LIMIT 1;


USE stormchaserblogDBtest;
INSERT INTO authors (author_id, author_name) VALUES (1, "name");
INSERT INTO users (user_id, user_name, `password`, author_id, is_admin) VALUES (1, "first", "nother", 1, 1);
UPDATE users SET user_name = "new", `password` = "new", author_id = 1, is_admin = 1 WHERE user_id = 1;

SELECT * FROM roles INNER JOIN users_to_roles ON roles.role_id = users_to_roles.role_id;


INSERT INTO maps (map_description, latitude, longitude, map_date) VALUES ("a map", 2, 3, "2019-02-08 12:00:00");