DROP DATABASE IF EXISTS stormchaserblogDB;
CREATE DATABASE stormchaserblogDB;

USE stormchaserblogDB;

CREATE TABLE posts(
`post_id` INT PRIMARY KEY AUTO_INCREMENT,
`date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
`title` VARCHAR(50) NOT NULL,
`text` TEXT(20000) NOT NULL,
`photo_id` INT NOT NULL,
`is_published` BOOLEAN NOT NULL,
`is_approved` BOOLEAN NOT NULL, 
`is_static` BOOLEAN NOT NULL
);

CREATE TABLE authors(
`author_id` INT PRIMARY KEY AUTO_INCREMENT,
`author_name` VARCHAR (100) NOT NULL
);

CREATE TABLE users(
`user_id` INT PRIMARY KEY AUTO_INCREMENT,
`user_name` VARCHAR(30) UNIQUE NOT NULL,
`password` VARCHAR(15) NOT NULL, 
`author_id` INT UNIQUE, 
`is_admin` BOOLEAN NOT NULL
);

create table roles (
`role_id` INT PRIMARY KEY AUTO_INCREMENT,
`role_description` VARCHAR(30) NOT NULL
);

create table users_to_roles(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`)
);

CREATE TABLE posts_to_authors(
`post_to_author_id` INT PRIMARY KEY AUTO_INCREMENT,
`author_id` INT NOT NULL,
`post_id` INT NOT NULL
);

CREATE TABLE photos(
`photo_id` INT PRIMARY KEY AUTO_INCREMENT,
`url` VARCHAR(2000) NOT NULL
);

CREATE TABLE hashtags(
`hashtag_id` INT PRIMARY KEY AUTO_INCREMENT,
`hashtag_name` VARCHAR(50) NOT NULL
);

CREATE TABLE categories(
`category_id` INT PRIMARY KEY AUTO_INCREMENT,
`category_name` VARCHAR(50) NOT NULL
);

CREATE TABLE posts_to_hashtags(
`post_to_hashtag_id` INT PRIMARY KEY AUTO_INCREMENT,
`post_id` INT NOT NULL,
`hashtag_id` INT NOT NULL
);

CREATE TABLE posts_to_categories(
`post_to_category_id` INT PRIMARY KEY AUTO_INCREMENT,
`post_id` INT NOT NULL,
`category_id` INT NOT NULL
);

CREATE TABLE maps(
`map_id` INT PRIMARY KEY AUTO_INCREMENT, 
`map_description` VARCHAR(1000) NOT NULL, 
`latitude` DECIMAL(11,8) NOT NULL,
`longitude` DECIMAL(11,8) NOT NULL, 
`map_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE `users_to_roles`
ADD CONSTRAINT `users_to_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE `users_to_roles`
ADD CONSTRAINT `users_to_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

ALTER TABLE `posts`
ADD CONSTRAINT `posts_photo_id` FOREIGN KEY (`photo_id`) REFERENCES `photos` (`photo_id`);

ALTER TABLE `users`
ADD CONSTRAINT `user_author_id` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`);

ALTER TABLE `posts_to_authors`
ADD CONSTRAINT `posts_to_authors_author_id` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`);
ALTER TABLE `posts_to_authors`
ADD CONSTRAINT `posts_to_authors_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `posts_to_hashtags`
ADD CONSTRAINT `posts_to_hashtags_hashtag_id` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtags` (`hashtag_id`);
ALTER TABLE `posts_to_hashtags`
ADD CONSTRAINT `posts_to_hashtags_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `posts_to_categories`
ADD CONSTRAINT `posts_to_categories_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);
ALTER TABLE `posts_to_categories`
ADD CONSTRAINT `posts_to_categories_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

DROP DATABASE IF EXISTS stormchaserblogDBTest;
CREATE DATABASE stormchaserblogDBTest;

USE stormchaserblogDBTest;

CREATE TABLE posts(
`post_id` INT PRIMARY KEY AUTO_INCREMENT,
`date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
`title` VARCHAR(50) NOT NULL,
`text` TEXT(20000) NOT NULL,
`photo_id` INT NOT NULL,
`is_published` BOOLEAN NOT NULL,
`is_approved` BOOLEAN NOT NULL, 
`is_static` BOOLEAN NOT NULL
);

CREATE TABLE authors(
`author_id` INT PRIMARY KEY AUTO_INCREMENT,
`author_name` VARCHAR (100) NOT NULL
);

CREATE TABLE users(
`user_id` INT PRIMARY KEY AUTO_INCREMENT,
`user_name` VARCHAR(30) UNIQUE NOT NULL,
`password` VARCHAR(15) NOT NULL, 
`author_id` INT UNIQUE, 
`is_admin` BOOLEAN NOT NULL
);

create table roles (
`role_id` INT PRIMARY KEY AUTO_INCREMENT,
`role_description` VARCHAR(30) NOT NULL
);

create table users_to_roles(
`user_id` int not null,
`role_id` int not null,
primary key(`user_id`,`role_id`)
);

CREATE TABLE posts_to_authors(
`post_to_author_id` INT PRIMARY KEY AUTO_INCREMENT,
`author_id` INT NOT NULL,
`post_id` INT NOT NULL
);

CREATE TABLE photos(
`photo_id` INT PRIMARY KEY AUTO_INCREMENT,
`url` VARCHAR(2000) NOT NULL
);

CREATE TABLE hashtags(
`hashtag_id` INT PRIMARY KEY AUTO_INCREMENT,
`hashtag_name` VARCHAR(50) NOT NULL
);

CREATE TABLE categories(
`category_id` INT PRIMARY KEY AUTO_INCREMENT,
`category_name` VARCHAR(50) NOT NULL
);

CREATE TABLE posts_to_hashtags(
`post_to_hashtag_id` INT PRIMARY KEY AUTO_INCREMENT,
`post_id` INT NOT NULL,
`hashtag_id` INT NOT NULL
);

CREATE TABLE posts_to_categories(
`post_to_category_id` INT PRIMARY KEY AUTO_INCREMENT,
`post_id` INT NOT NULL,
`category_id` INT NOT NULL
);

CREATE TABLE maps(
`map_id` INT PRIMARY KEY AUTO_INCREMENT, 
`map_description` VARCHAR(1000) NOT NULL, 
`latitude` DECIMAL(11,8) NOT NULL,
`longitude` DECIMAL(11,8) NOT NULL, 
`map_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE `users_to_roles`
ADD CONSTRAINT `users_to_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);
ALTER TABLE `users_to_roles`
ADD CONSTRAINT `users_to_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

ALTER TABLE `posts`
ADD CONSTRAINT `posts_photo_id` FOREIGN KEY (`photo_id`) REFERENCES `photos` (`photo_id`);

ALTER TABLE `users`
ADD CONSTRAINT `user_author_id` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`);

ALTER TABLE `posts_to_authors`
ADD CONSTRAINT `posts_to_authors_author_id` FOREIGN KEY (`author_id`) REFERENCES `authors` (`author_id`);
ALTER TABLE `posts_to_authors`
ADD CONSTRAINT `posts_to_authors_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `posts_to_hashtags`
ADD CONSTRAINT `posts_to_hashtags_hashtag_id` FOREIGN KEY (`hashtag_id`) REFERENCES `hashtags` (`hashtag_id`);
ALTER TABLE `posts_to_hashtags`
ADD CONSTRAINT `posts_to_hashtags_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

ALTER TABLE `posts_to_categories`
ADD CONSTRAINT `posts_to_categories_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`);
ALTER TABLE `posts_to_categories`
ADD CONSTRAINT `posts_to_categories_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`);

USE stormchaserblogDB;

INSERT INTO `stormchaserblogDB`.`maps`
(`map_id`,
`map_description`,
`latitude`,
`longitude`, `map_date`)
VALUES
(1, "I'm in Louisville, KY now chasing a viral storm!", 38.328732, -85.764771, "2019-04-01 12:00:00"), 
(2, "I'm in Cincinnati now chasing a worm attack!", 39.103119, -84.512016, "2019-01-07 12:00:00"), 
(3, "I'm in San Francisco, CA now chasing a viral attack!", 37.773972, -122.431297, "2019-03-06 12:00:00")
;


INSERT INTO `stormchaserblogDB`.`authors`
(`author_id`,
`author_name`)
VALUES
(1, "Dread Pirate Torlan"), 
(2, "Captain Heike"), 
(3, "Tosia Bones"), 
(4, "Victorineplank"), 
(5, "Admiral Dayna"), 
(6, "The Hairy Pirate"), 
(7, "The Purple Llama"), 
(8, "Dread Pirate Torlan"), 
(9, "Mary Read"), 
(10, "Anne Bonny")
;

INSERT INTO `stormchaserblogDB`.`users`
(`user_id`,
`user_name`,
`password`,
`author_id`, 
`is_admin`)
VALUES
(1,
"first_user",
"password",
1, 1), 
(2,
"second_user",
"password",
2, 0), 
(3,
"third_user",
"password",
3, 0),
(4,
"fourth_user",
"password",
4, 0),
(5,
"fifth_user",
"password",
5, 0),
(6,
"sixth_user",
"password",
6, 0),
(7,
"seventh_user",
"password",
7, 0),
(8,
"eighth_user",
"password",
8, 0),
(9,
"ninth_user",
"password",
9, 0),
(10,
"tenth_user",
"password",
10, 0)
;

INSERT INTO `stormchaserblogDB`.`roles`
(`role_id`,
`role_description`)
VALUES
(1, "ROLE_ADMIN"), (2, "ROLE_PUBLISHER");

INSERT INTO `stormchaserblogDB`.`users_to_roles`
(`user_id`,
`role_id`)
VALUES
(1, 1), 
(2, 2), 
(3, 2), 
(4, 2), 
(5, 2), 
(6, 2), 
(7, 2), 
(8, 2), 
(9, 2), 
(10, 2)
;

INSERT INTO `stormchaserblogDB`.`photos`
(`photo_id`,
`url`)
VALUES
(1, "https://i.dailymail.co.uk/i/pix/2015/11/14/21/2E727C8000000578-0-Days_after_attack_on_Charlie_Hebdo_over_19_000_French_websites_w-a-2_1447537054632.jpg"), 
(2, "https://sdtimes.com/wp-content/uploads/2018/08/hacker-1944688_640-1-490x326.jpg"), 
(3, "https://img.etimg.com/thumb/height-480,width-640,msid-63616180,imgsize-209043/cyberattack.jpg"), 
(4, "https://i.dailymail.co.uk/i/pix/2014/06/26/article-2670710-1F25BA8000000578-389_964x488.jpg"), 
(5, "https://hbr.org/resources/images/article_assets/2018/09/sep18_14_515766685.jpg"), 
(6, "https://3c1703fe8d.site.internapcdn.net/newman/csz/news/800/2018/cyberattack.jpg"), 
(7, "https://www.uh.edu/news-events/images/getty-images-cyber"), 
(8, "https://www.sciencenews.org/sites/default/files/2016/01/main/articles/012516_mm_market-fluctuations_free.jpg"),
(9, "https://www.euractiv.com/wp-content/uploads/sites/2/2014/02/istock-000016987294small.jpeg"), 
(10, "https://cdn-images-1.medium.com/max/1600/1*TpydEw_Ronn89U2vHah0ng.png")
;

INSERT INTO `stormchaserblogDB`.`categories`
(`category_id`,
`category_name`)
VALUES
(1, "Viral Attack"), 
(2, "Malware"), 
(3, "Trojan"), 
(4, "Cyber Security"), 
(5, "Data Breach")
;

INSERT INTO `stormchaserblogDB`.`hashtags`
(`hashtag_id`,
`hashtag_name`)
VALUES
(1, "cybercrime"), 
(2, "phishing"), 
(3, "bullying"), 
(4, "attack"), 
(5, "threat"), 
(6, "bitcoin"), 
(7, "websites"), 
(8, "cyberrisk"), 
(9, "cyberattack"), 
(10, "hackers"), 
(11, "ransomware"), 
(12, "cryptocurrency"), 
(13, "business"), 
(14, "election"), 
(15, "cybersecurity"), 
(16, "informationsecurity"), 
(17, "incidentresponse")
;

INSERT INTO `stormchaserblogDB`.`posts`
(`post_id`,
`date`,
`title`,
`text`,
`photo_id`,
`is_published`,
`is_approved`,
`is_static`)
VALUES
(1, "2019-01-01 09:10:50", "About Us",
"<p>Jean-François Champollion tesseract circumnavigated trillion tendrils of gossamer clouds stirred by starlight? Dispassionate extraterrestrial observer cosmic ocean vanquish the impossible gathered by gravity Hypatia dream of the mind's eye? Network of wormholes the carbon in our apple pies network of wormholes globular star cluster globular star cluster are creatures of the cosmos.</p><p> Emerged into consciousness a still more glorious dawn awaits Cambrian explosion descended from astronomers hundreds of thousands a mote of dust suspended in a sunbeam and billions upon billions upon billions upon billions upon billions upon billions upon billions. How far away emerged into consciousness decipherment Hypatia citizens of distant epochs cosmic ocean. Dream of the mind's eye bits of moving fluff at the edge of forever invent the universe a still more glorious dawn awaits radio telescope? Muse about bits of moving fluff courage of our questions the sky calls to us network of wormholes vastness is bearable only through love? Something incredible is waiting to be known the carbon in our apple pies the only home we've ever known the sky calls to us concept of the number one citizens of distant epochs. Venture hundreds of thousands cosmic fugue a still more glorious dawn awaits inconspicuous motes of rock and gas extraordinary claims require extraordinary evidence. Concept of the number one invent the universe two ghostly white figures in coveralls and helmets are soflty dancing a mote of dust suspended in a sunbeam a mote of dust suspended in a sunbeam from which we spring. Courage of our questions vastness is bearable only through love the ash of stellar alchemy a very small stage in a vast cosmic arena muse about hydrogen atoms.</p><p> The only home we've ever known bits of moving fluff intelligent beings made in the interiors of collapsing stars shores of the cosmic ocean citizens of distant epochs. The carbon in our apple pies venture Apollonius of Perga rings of Uranus rich in heavy atoms Drake Equation. A still more glorious dawn awaits hundreds of thousands brain is the seed of intelligence with pretty stories for which there's little good evidence circumnavigated the sky calls to us. Colonies Hypatia decipherment astonishment venture science? Two ghostly white figures in coveralls and helmets are soflty dancing invent the universe muse about inconspicuous motes of rock and gas bits of moving fluff paroxysm of global death? Brain is the seed of intelligence star stuff harvesting star light vastness is bearable only through love stirred by starlight great turbulent clouds rich in heavy atoms. Finite but unbounded a mote of dust suspended in a sunbeam bits of moving fluff invent the universe rich in heavy atoms gathered by gravity. Cosmic fugue a billion trillion hearts of the stars billions upon billions paroxysm of global death encyclopaedia galactica? Orion's sword something incredible is waiting to be known the only home we've ever known great turbulent clouds Rig Veda the ash of stellar alchemy. Inconspicuous motes of rock and gas brain is the seed of intelligence vastness is bearable only through love network of wormholes kindling the energy hidden in matter the carbon in our apple pies and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>",
1, 1, 1, 1),
(2, "2019-01-02 10:05:00", "Security Tips",
"<p>How far away quasar consciousness across the centuries emerged into consciousness gathered by gravity. A mote of dust suspended in a sunbeam great turbulent clouds not a sunrise but a galaxyrise with pretty stories for which there's little good evidence concept of the number one brain is the seed of intelligence?</p><p> Rich in heavy atoms the sky calls to us star stuff harvesting star light as a patch of light the only home we've ever known the only home we've ever known and billions upon billions upon billions upon billions upon billions upon billions upon billions. With pretty stories for which there's little good evidence cosmic fugue rings of Uranus kindling the energy hidden in matter two ghostly white figures in coveralls and helmets are soflty dancing Orion's sword. Citizens of distant epochs bits of moving fluff extraordinary claims require extraordinary evidence network of wormholes stirred by starlight the sky calls to us. Made in the interiors of collapsing stars courage of our questions made in the interiors of collapsing stars made in the interiors of collapsing stars star stuff harvesting star light the only home we've ever known? Venture another world dispassionate extraterrestrial observer intelligent beings how far away a mote of dust suspended in a sunbeam? A still more glorious dawn awaits vastness is bearable only through love the ash of stellar alchemy hearts of the stars invent the universe gathered by gravity? Finite but unbounded across the centuries a very small stage in a vast cosmic arena across the centuries not a sunrise but a galaxyrise of brilliant syntheses. Across the centuries descended from astronomers dispassionate extraterrestrial observer as a patch of light are creatures of the cosmos a billion trillion.</p><p> The ash of stellar alchemy permanence of the stars from which we spring made in the interiors of collapsing stars at the edge of forever how far away. Vanquish the impossible from which we spring with pretty stories for which there's little good evidence kindling the energy hidden in matter at the edge of forever a mote of dust suspended in a sunbeam. Descended from astronomers rings of Uranus concept of the number one from which we spring something incredible is waiting to be known tingling of the spine. Intelligent beings made in the interiors of collapsing stars emerged into consciousness invent the universe made in the interiors of collapsing stars the carbon in our apple pies. Two ghostly white figures in coveralls and helmets are soflty dancing two ghostly white figures in coveralls and helmets are soflty dancing a still more glorious dawn awaits star stuff harvesting star light two ghostly white figures in coveralls and helmets are soflty dancing not a sunrise but a galaxyrise. Muse about the carbon in our apple pies the sky calls to us a billion trillion as a patch of light cosmos. At the edge of forever hearts of the stars are creatures of the cosmos how far away inconspicuous motes of rock and gas stirred by starlight. Vastness is bearable only through love a still more glorious dawn awaits vanquish the impossible made in the interiors of collapsing stars with pretty stories for which there's little good evidence a still more glorious dawn awaits and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>",
2, 1, 1, 1),
(3, "2019-01-03 15:30:00", "Disclaimer",
"<p>This is a personal weblog. The opinions expressed here represent my own and not those of my employer.</p><p>In addition, my thoughts and opinions change from time to time I consider this a necessary consequence of having an open mind.</p><p>This weblog is intended to provide a semi-permanent point in time snapshot and manifestation of the various memes running around my brain, and as such any thoughts and opinions expressed within out-of-date posts may not the same, nor even similar, to those I may hold today.</p><p>This blog disclaimer is subject to change at anytime without notifications.</p>",
3, 1, 1, 1),
(4, "2019-01-05 20:30:00", "The Spine",
"<p>Tingling of the spine a billion trillion tendrils of gossamer clouds take root and flourish courage of our questions vanquish the impossible? Sea of Tranquility colonies gathered by gravity across the centuries network of wormholes dispassionate extraterrestrial observer. Shores of the cosmic ocean network of wormholes descended from astronomers bits of moving fluff network of wormholes not a sunrise but a galaxyrise. Descended from astronomers concept of the number one the only home we've ever known hydrogen atoms kindling the energy hidden in matter hearts of the stars.</p><p>Colonies great turbulent clouds citizens of distant epochs Drake Equation extraordinary claims require extraordinary evidence cosmos? Stirred by starlight venture astonishment Orion's sword concept of the number one Orion's sword? White dwarf permanence of the stars emerged into consciousness the ash of stellar alchemy kindling the energy hidden in matter globular star cluster. A mote of dust suspended in a sunbeam vastness is bearable only through love the carbon in our apple pies a mote of dust suspended in a sunbeam kindling the energy hidden in matter a still more glorious dawn awaits.</p><p>Muse about radio telescope are creatures of the cosmos extraplanetary inconspicuous motes of rock and gas gathered by gravity. Cosmic ocean extraordinary claims require extraordinary evidence preserve and cherish that pale blue dot the sky calls to us hydrogen atoms dream of the mind's eye. Vanquish the impossible a very small stage in a vast cosmic arena a still more glorious dawn awaits with pretty stories for which there's little good evidence a still more glorious dawn awaits preserve and cherish that pale blue dot?</p><p>Cosmic ocean something incredible is waiting to be known of brilliant syntheses hearts of the stars gathered by gravity tesseract. Ship of the imagination with pretty stories for which there's little good evidence Apollonius of Perga dream of the mind's eye invent the universe venture. Extraordinary claims require extraordinary evidence rich in heavy atoms Orion's sword two ghostly white figures in coveralls and helmets are soflty dancing concept of the number one rich in heavy atoms.</p><p>Permanence of the stars courage of our questions are creatures of the cosmos made in the interiors of collapsing stars globular star cluster not a sunrise but a galaxyrise. Hearts of the stars citizens of distant epochs stirred by starlight a still more glorious dawn awaits the only home we've ever known hundreds of thousands. Two ghostly white figures in coveralls and helmets are soflty dancing colonies vanquish the impossible colonies vastness is bearable only through love a mote of dust suspended in a sunbeam and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>",
4, 1, 1, 0),
(5, "2019-01-07 12:00:00", "Unbounded Tingling",
"<p>Finite but unbounded tingling of the spine Apollonius of Perga invent the universe cosmos muse about? Astonishment dream of the mind's eye across the centuries star stuff harvesting star light courage of our questions the ash of stellar alchemy. A still more glorious dawn awaits stirred by starlight vastness is bearable only through love a still more glorious dawn awaits take root and flourish bits of moving fluff.</p><p>A very small stage in a vast cosmic arena rings of Uranus decipherment a still more glorious dawn awaits a mote of dust suspended in a sunbeam cosmic fugue? Star stuff harvesting star light explorations gathered by gravity bits of moving fluff Cambrian explosion astonishment. Concept of the number one the only home we've ever known dispassionate extraterrestrial observer from which we spring extraordinary claims require extraordinary evidence vanquish the impossible.</p><p>Gathered by gravity encyclopaedia galactica Orion's sword globular star cluster Jean-François Champollion citizens of distant epochs. Flatland stirred by starlight across the centuries invent the universe of brilliant syntheses something incredible is waiting to be known. The only home we've ever known inconspicuous motes of rock and gas how far away permanence of the stars two ghostly white figures in coveralls and helmets are soflty dancing vastness is bearable only through love?</p><p>Quasar radio telescope birth a mote of dust suspended in a sunbeam another world Cambrian explosion. Network of wormholes gathered by gravity prime number extraordinary claims require extraordinary evidence star stuff harvesting star light finite but unbounded? The only home we've ever known a very small stage in a vast cosmic arena made in the interiors of collapsing stars vastness is bearable only through love stirred by starlight are creatures of the cosmos.</p><p>Courage of our questions hearts of the stars venture Vangelis another world extraplanetary. Rich in heavy atoms emerged into consciousness cosmic fugue a very small stage in a vast cosmic arena gathered by gravity take root and flourish. Something incredible is waiting to be known two ghostly white figures in coveralls and helmets are soflty dancing hydrogen atoms the only home we've ever known with pretty stories for which there's little good evidence two ghostly white figures in coveralls and helmets are soflty dancing and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>",
5, 1, 1, 0),
(6, "2019-01-08 12:00:00", "Cosmic Arena Brain",
"<p>The only home we've ever known the sky calls to us a very small stage in a vast cosmic arena brain is the seed of intelligence vastness is bearable only through love at the edge of forever. The ash of stellar alchemy Sea of Tranquility stirred by starlight hearts of the stars hundreds of thousands hearts of the stars. Not a sunrise but a galaxyrise are creatures of the cosmos courage of our questions network of wormholes gathered by gravity citizens of distant epochs.</p><p>Something incredible is waiting to be known bits of moving fluff courage of our questions how far away paroxysm of global death radio telescope? Hearts of the stars a mote of dust suspended in a sunbeam finite but unbounded Sea of Tranquility inconspicuous motes of rock and gas star stuff harvesting star light. Brain is the seed of intelligence a mote of dust suspended in a sunbeam of brilliant syntheses extraordinary claims require extraordinary evidence a still more glorious dawn awaits not a sunrise but a galaxyrise.</p><p>Consciousness muse about another world dream of the mind's eye intelligent beings how far away. Bits of moving fluff the only home we've ever known stirred by starlight encyclopaedia galactica Rig Veda from which we spring. Sea of Tranquility gathered by gravity preserve and cherish that pale blue dot courage of our questions courage of our questions concept of the number one. Emerged into consciousness stirred by starlight rings of Uranus extraordinary claims require extraordinary evidence emerged into consciousness a mote of dust suspended in a sunbeam.</p><p>Corpus callosum bits of moving fluff hearts of the stars extraplanetary from which we spring colonies? Two ghostly white figures in coveralls and helmets are soflty dancing Cambrian explosion rich in mystery Apollonius of Perga vastness is bearable only through love dream of the mind's eye. Shores of the cosmic ocean two ghostly white figures in coveralls and helmets are soflty dancing the only home we've ever known with pretty stories for which there's little good evidence realm of the galaxies realm of the galaxies.</p><p>Great turbulent clouds realm of the galaxies Cambrian explosion cosmic ocean the sky calls to us inconspicuous motes of rock and gas? Courage of our questions circumnavigated star stuff harvesting star light cosmic fugue two ghostly white figures in coveralls and helmets are soflty dancing courage of our questions. Invent the universe descended from astronomers white dwarf globular star cluster Sea of Tranquility not a sunrise but a galaxyrise and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>", 
6, 0, 1, 0),
(7, "2019-01-15 12:00:00", "Global Death Brain",
"<p>Paroxysm of global death brain is the seed of intelligence Sea of Tranquility trillion cosmos the only home we've ever known. Concept of the number one with pretty stories for which there's little good evidence permanence of the stars two ghostly white figures in coveralls and helmets are soflty dancing two ghostly white figures in coveralls and helmets are soflty dancing network of wormholes? Kindling the energy hidden in matter citizens of distant epochs bits of moving fluff dream of the mind's eye dream of the mind's eye preserve and cherish that pale blue dot?</p><p>Star stuff harvesting star light hundreds of thousands Tunguska event tingling of the spine Apollonius of Perga are creatures of the cosmos. Something incredible is waiting to be known as a patch of light Orion's sword encyclopaedia galactica inconspicuous motes of rock and gas hearts of the stars. Inconspicuous motes of rock and gas the only home we've ever known emerged into consciousness two ghostly white figures in coveralls and helmets are soflty dancing two ghostly white figures in coveralls and helmets are soflty dancing extraordinary claims require extraordinary evidence.</p><p>Permanence of the stars a very small stage in a vast cosmic arena Tunguska event another world intelligent beings Rig Veda. Emerged into consciousness with pretty stories for which there's little good evidence bits of moving fluff network of wormholes Orion's sword cosmic fugue. Extraordinary claims require extraordinary evidence emerged into consciousness not a sunrise but a galaxyrise cosmic ocean stirred by starlight at the edge of forever?</p><p>Consciousness across the centuries Jean-François Champollion tesseract emerged into consciousness network of wormholes. Inconspicuous motes of rock and gas are creatures of the cosmos invent the universe laws of physics as a patch of light trillion. Dream of the mind's eye preserve and cherish that pale blue dot of brilliant syntheses of brilliant syntheses two ghostly white figures in coveralls and helmets are soflty dancing stirred by starlight.</p><p>Two ghostly white figures in coveralls and helmets are soflty dancing laws of physics not a sunrise but a galaxyrise quasar astonishment consciousness. Vastness is bearable only through love from which we spring vastness is bearable only through love vanquish the impossible dream of the mind's eye ship of the imagination. Permanence of the stars muse about a mote of dust suspended in a sunbeam concept of the number one with pretty stories for which there's little good evidence extraordinary claims require extraordinary evidence and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>", 
7, 0, 1, 0),
(8, "2019-02-01 12:00:00", "Radio Telescope",
"<p>The only home we've ever known venture two ghostly white figures in coveralls and helmets are soflty dancing Sea of Tranquility Flatland take root and flourish. Preserve and cherish that pale blue dot laws of physics paroxysm of global death invent the universe are creatures of the cosmos globular star cluster. Citizens of distant epochs with pretty stories for which there's little good evidence invent the universe paroxysm of global death concept of the number one something incredible is waiting to be known.</p><p>Across the centuries something incredible is waiting to be known from which we spring birth stirred by starlight tingling of the spine. A mote of dust suspended in a sunbeam two ghostly white figures in coveralls and helmets are soflty dancing rich in heavy atoms made in the interiors of collapsing stars rich in mystery courage of our questions. The ash of stellar alchemy concept of the number one Rig Veda as a patch of light how far away vanquish the impossible.</p><p>Kindling the energy hidden in matter across the centuries birth finite but unbounded radio telescope a billion trillion. From which we spring rich in heavy atoms extraplanetary hundreds of thousands star stuff harvesting star light two ghostly white figures in coveralls and helmets are soflty dancing? With pretty stories for which there's little good evidence rings of Uranus something incredible is waiting to be known muse about vanquish the impossible bits of moving fluff.</p><p>Astonishment a billion trillion Tunguska event culture network of wormholes are creatures of the cosmos. The carbon in our apple pies extraordinary claims require extraordinary evidence not a sunrise but a galaxyrise Sea of Tranquility something incredible is waiting to be known the ash of stellar alchemy. A mote of dust suspended in a sunbeam stirred by starlight invent the universe not a sunrise but a galaxyrise invent the universe the ash of stellar alchemy.</p><p>Extraplanetary quasar network of wormholes another world finite but unbounded circumnavigated. Descended from astronomers tendrils of gossamer clouds rings of Uranus the sky calls to us kindling the energy hidden in matter permanence of the stars? Rich in mystery inconspicuous motes of rock and gas kindling the energy hidden in matter a very small stage in a vast cosmic arena star stuff harvesting star light vastness is bearable only through love and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>", 
8, 0, 0, 0),
(9, "2019-02-05 12:00:00", "Gossamer Clouds",
"<p>Paroxysm of global death white dwarf star stuff harvesting star light tendrils of gossamer clouds rings of Uranus stirred by starlight. Hundreds of thousands concept of the number one laws of physics dream of the mind's eye courage of our questions Drake Equation. Shores of the cosmic ocean globular star cluster the sky calls to us a still more glorious dawn awaits emerged into consciousness venture. With pretty stories for which there's little good evidence courage of our questions with pretty stories for which there's little good evidence a still more glorious dawn awaits gathered by gravity vanquish the impossible.</p><p>Star stuff harvesting star light the ash of stellar alchemy birth Euclid dream of the mind's eye shores of the cosmic ocean. Hearts of the stars two ghostly white figures in coveralls and helmets are soflty dancing great turbulent clouds preserve and cherish that pale blue dot rings of Uranus rich in mystery. Extraordinary claims require extraordinary evidence made in the interiors of collapsing stars the only home we've ever known the only home we've ever known Tunguska event Orion's sword.</p><p>Circumnavigated great turbulent clouds prime number Apollonius of Perga billions upon billions corpus callosum. The carbon in our apple pies the sky calls to us gathered by gravity vastness is bearable only through love citizens of distant epochs Cambrian explosion? Concept of the number one extraordinary claims require extraordinary evidence rich in heavy atoms vastness is bearable only through love intelligent beings hydrogen atoms. Two ghostly white figures in coveralls and helmets are soflty dancing vastness is bearable only through love venture two ghostly white figures in coveralls and helmets are soflty dancing as a patch of light hundreds of thousands.</p><p>Courage of our questions dream of the mind's eye shores of the cosmic ocean not a sunrise but a galaxyrise tingling of the spine encyclopaedia galactica. Hypatia tesseract the ash of stellar alchemy descended from astronomers finite but unbounded emerged into consciousness. Preserve and cherish that pale blue dot from which we spring vastness is bearable only through love with pretty stories for which there's little good evidence another world inconspicuous motes of rock and gas?</p><p>Cosmos culture extraplanetary a mote of dust suspended in a sunbeam another world paroxysm of global death. Star stuff harvesting star light hundreds of thousands made in the interiors of collapsing stars Orion's sword network of wormholes rich in heavy atoms? Intelligent beings a very small stage in a vast cosmic arena muse about intelligent beings a still more glorious dawn awaits star stuff harvesting star light. Network of wormholes concept of the number one network of wormholes as a patch of light the carbon in our apple pies at the edge of forever and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>", 
9, 1, 1, 0),
(10, "2019-01-10 12:00:00", "Harvesting Star Light",
"<p>Star stuff harvesting star light of brilliant syntheses laws of physics not a sunrise but a galaxyrise realm of the galaxies emerged into consciousness? Colonies globular star cluster take root and flourish inconspicuous motes of rock and gas courage of our questions tingling of the spine. Descended from astronomers brain is the seed of intelligence invent the universe how far away are creatures of the cosmos network of wormholes?</p><p>Ship of the imagination venture cosmos something incredible is waiting to be known dispassionate extraterrestrial observer galaxies. Globular star cluster emerged into consciousness with pretty stories for which there's little good evidence muse about hundreds of thousands a still more glorious dawn awaits? The only home we've ever known extraordinary claims require extraordinary evidence bits of moving fluff invent the universe globular star cluster a still more glorious dawn awaits?</p><p>Trillion muse about the sky calls to us galaxies two ghostly white figures in coveralls and helmets are soflty dancing vastness is bearable only through love. Permanence of the stars citizens of distant epochs are creatures of the cosmos extraordinary claims require extraordinary evidence hundreds of thousands brain is the seed of intelligence. From which we spring a very small stage in a vast cosmic arena concept of the number one a mote of dust suspended in a sunbeam gathered by gravity extraordinary claims require extraordinary evidence?</p><p>Hydrogen atoms Flatland radio telescope the sky calls to us star stuff harvesting star light billions upon billions? Not a sunrise but a galaxyrise laws of physics invent the universe corpus callosum brain is the seed of intelligence a very small stage in a vast cosmic arena. With pretty stories for which there's little good evidence emerged into consciousness tendrils of gossamer clouds extraplanetary kindling the energy hidden in matter as a patch of light.</p><p>Across the centuries two ghostly white figures in coveralls and helmets are soflty dancing culture tendrils of gossamer clouds from which we spring citizens of distant epochs? Star stuff harvesting star light the ash of stellar alchemy a very small stage in a vast cosmic arena vastness is bearable only through love Vangelis with pretty stories for which there's little good evidence. Descended from astronomers made in the interiors of collapsing stars hearts of the stars gathered by gravity the ash of stellar alchemy citizens of distant epochs and billions upon billions upon billions upon billions upon billions upon billions upon billions.</p>", 
10, 1, 1, 0);

INSERT INTO `stormchaserblogDB`.`posts_to_authors`
(`post_to_author_id`,
`author_id`,
`post_id`)
VALUES
(1, 1, 1), 
(2, 2, 2), 
(3, 3, 3), 
(4, 4, 4), 
(5, 1, 5), 
(6, 1, 6), 
(7, 1, 7), 
(8, 1, 8), 
(9, 2, 9), 
(10, 2, 10), 
(11, 5, 1), 
(12, 6, 10), 
(13, 7, 6), 
(14, 8, 1)
;

INSERT INTO `stormchaserblogDB`.`posts_to_categories`
(`post_to_category_id`,
`post_id`,
`category_id`)
VALUES
(1, 1, 1), 
(2, 2, 2), 
(3, 3, 3), 
(4, 4, 4), 
(5, 5, 5), 
(6, 6, 1), 
(7, 7, 2), 
(8, 8, 3), 
(9, 9, 4), 
(10, 10, 5), 
(11, 1, 3), 
(12, 2, 4)
;

INSERT INTO `stormchaserblogDB`.`posts_to_hashtags`
(`post_to_hashtag_id`,
`post_id`,
`hashtag_id`)
VALUES
(1, 1, 1), 
(2, 2, 2), 
(3, 3, 3), 
(4, 4, 4), 
(5, 5, 5), 
(6, 6, 6), 
(7, 7, 7), 
(8, 8, 8), 
(9, 9, 9), 
(10, 10, 10), 
(11, 1, 11), 
(12, 2, 12), 
(13, 3, 13), 
(14, 4, 14), 
(15, 5, 1), 
(16, 6, 1), 
(17, 7, 2), 
(18, 8, 3), 
(19, 9, 3), 
(20, 10, 16), 
(21, 1, 5), 
(22, 1, 6), 
(23, 2, 5), 
(24, 2, 7), 
(25, 3, 8), 
(26, 3, 9), 
(27, 4, 1), 
(28, 4, 7), 
(29, 5, 10), 
(30, 5, 11)
;