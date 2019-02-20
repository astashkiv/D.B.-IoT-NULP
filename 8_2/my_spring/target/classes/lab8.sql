USE db_lab;

DROP DATABASE IF EXISTS electro_goods;
 
CREATE DATABASE electro_goods;
 
USE `electro_goods`;
 

CREATE TABLE provider (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`name` VARCHAR(50) NOT NULL UNIQUE,
	`director` VARCHAR(50) NOT NULL,
	`phone` CHAR(13) NOT NULL,
	CONSTRAINT CK_PROVIDER_PHONE 
	CHECK (phone LIKE ('[0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9]'))
	);

CREATE TABLE good (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`brand` VARCHAR(50) NOT NULL,
	`price` REAL NOT NULL,
	`count` INT NOT NULL,
	CONSTRAINT CK_GOODS_PRICE CHECK (price >= 0)
	);

CREATE TABLE good_provider (
	`id` BIGINT AUTO_INCREMENT UNIQUE KEY,
	`good_id` BIGINT NOT NULL,
	`provider_id` BIGINT NOT NULL,
	CONSTRAINT PK_GOOD_PROVIDER PRIMARY KEY (good_id, provider_id),
	CONSTRAINT FK_GOOD_PROVIDER_GOOD_ID FOREIGN KEY (`good_id`) REFERENCES good (`id`),
	CONSTRAINT FK_GOOD_PROVIDER_PROVIDER_ID FOREIGN KEY (`provider_id`) REFERENCES provider (`id`)
	);

CREATE TABLE invoice (
	`id` BIGINT AUTO_INCREMENT PRIMARY KEY,
	`date` DATE NOT NULL,
	`provider_id` BIGINT NOT NULL,
	CONSTRAINT FK_iNVOICE_PROVIDER_ID FOREIGN KEY (`provider_id`) REFERENCES provider (`id`)
	);

-- ---[provider]--------------------------------------------------------------------------------
INSERT INTO provider (name, director, phone) VALUES('LG', 'Matviychuk Y.M.', '123-45-67-890');
INSERT INTO provider (name, director, phone) VALUES('LENOVO', 'Burkackiy Y.T.', '098-76-54-321');
INSERT INTO provider (name, director, phone) VALUES('SAMSUNG', 'Shahray V.V.', '564-78-32-190');

-- ---[goods]------------------------------------------------------------------------------------
INSERT INTO good (brand, price, count) VALUES('LG', 2330.0, 2);
INSERT INTO good (brand, price, count) VALUES('LENOVO', 5330.0, 1);
INSERT INTO good (brand, price, count) VALUES('SAMSUNG', 9330.0, 0);
INSERT INTO good (brand, price, count) VALUES('LG', 8330.0, 0);

-- --[goods_provider]----------------------------------------------------------------------------
INSERT INTO good_provider (good_id, provider_id) VALUES(1, 1);
INSERT INTO good_provider (good_id, provider_id) VALUES(2, 1);
INSERT INTO good_provider (good_id, provider_id) VALUES(3, 2);
INSERT INTO good_provider (good_id, provider_id) VALUES(4, 3);

-- --[invoice]-----------------------------------------------------------------------------------
INSERT INTO invoice (date, provider_id) VALUES('2018-08-12', 1);
INSERT INTO invoice (date, provider_id) VALUES('2018-08-12', 2);
INSERT INTO invoice (date, provider_id) VALUES('2018-08-12', 3);
INSERT INTO invoice (date, provider_id) VALUES('2018-08-12', 1);
















