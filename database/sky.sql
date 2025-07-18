CREATE DATABASE  IF NOT EXISTS `sky_take_out` ;
USE `sky_take_out`;

DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT 'User ID',
  `consignee` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Consignee',
  `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT 'Sex',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT 'Phone',
  `province_code` varchar(12) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'Province code',
  `province_name` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'Province name',
  `city_code` varchar(12) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'City code',
  `city_name` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'City name',
  `district_code` varchar(12) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'District code',
  `district_name` varchar(32) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'District name',
  `detail` varchar(200) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'Detail address',
  `label` varchar(100) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT 'Label',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Default 0 no 1 yes',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Address book';

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int DEFAULT NULL COMMENT 'Type 1 dish category 2 set meal category',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Name',
  `sort` int NOT NULL DEFAULT '0' COMMENT 'Sort',
  `status` int DEFAULT NULL COMMENT 'Status 0: disabled 1: enabled',
  `create_time` datetime DEFAULT NULL COMMENT 'Create time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update time',
  `create_user` bigint DEFAULT NULL COMMENT 'Create user',
  `update_user` bigint DEFAULT NULL COMMENT 'Update user',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Dish and set meal category';

INSERT INTO `category` VALUES (11,1,'Drinks',10,1,'2022-06-09 22:09:18','2022-06-09 22:09:18',1,1);
INSERT INTO `category` VALUES (12,1,'Traditional main dishes',9,1,'2022-06-09 22:09:32','2022-06-09 22:18:53',1,1);
INSERT INTO `category` VALUES (13,2,'Popular set meal',12,1,'2022-06-09 22:11:38','2022-06-10 11:04:40',1,1);
INSERT INTO `category` VALUES (15,2,'Business set meal',13,1,'2022-06-09 22:14:10','2022-06-10 11:04:48',1,1);
INSERT INTO `category` VALUES (16,1,'Sichuan style grilled fish',4,1,'2022-06-09 22:15:37','2022-08-31 14:27:25',1,1);
INSERT INTO `category` VALUES (17,1,'Sichuan style bullfrog',5,1,'2022-06-09 22:16:14','2022-08-31 14:39:44',1,1);
INSERT INTO `category` VALUES (18,1,'Special steamed dishes',6,1,'2022-06-09 22:17:42','2022-06-09 22:17:42',1,1);
INSERT INTO `category` VALUES (19,1,'Fresh vegetables',7,1,'2022-06-09 22:18:12','2022-06-09 22:18:28',1,1);
INSERT INTO `category` VALUES (20,1,'Sichuan style fish',8,1,'2022-06-09 22:22:29','2022-06-09 22:23:45',1,1);
INSERT INTO `category` VALUES (21,1,'Soup',11,1,'2022-06-10 10:51:47','2022-06-10 10:51:47',1,1);

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Name',
  `category_id` bigint NOT NULL COMMENT 'Category ID',
  `price` decimal(10,2) DEFAULT NULL COMMENT 'Price',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Image',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Description',
  `status` int DEFAULT '1' COMMENT '0: disabled 1: enabled',
  `create_time` datetime DEFAULT NULL COMMENT 'Create time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update time',
  `create_user` bigint DEFAULT NULL COMMENT 'Create user',
  `update_user` bigint DEFAULT NULL COMMENT 'Update user',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Dish';

INSERT INTO `dish` VALUES (46,'Wong Lo Kat',11,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png','',1,'2022-06-09 22:40:47','2022-06-09 22:40:47',1,1);
INSERT INTO `dish` VALUES (47,'Arctic Ocean Soda',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png','Still the taste of childhood',1,'2022-06-10 09:18:49','2022-06-10 09:18:49',1,1);
INSERT INTO `dish` VALUES (48,'Snow Beer',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png','',1,'2022-06-10 09:22:54','2022-06-10 09:22:54',1,1);
INSERT INTO `dish` VALUES (49,'Rice',12,2.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png','Selected Wuchang Rice',1,'2022-06-10 09:30:17','2022-06-10 09:30:17',1,1);
INSERT INTO `dish` VALUES (50,'Bun',12,1.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png','High-quality flour',1,'2022-06-10 09:34:28','2022-06-10 09:34:28',1,1);
INSERT INTO `dish` VALUES (51,'Sauerkraut Fish',20,56.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png','Ingredients: soup, grass carp, sauerkraut',1,'2022-06-10 09:40:51','2022-06-10 09:40:51',1,1);
INSERT INTO `dish` VALUES (52,'Stewed Sliced Fish',20,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png','Ingredients: Pickled cabbage, river fish, catfish',1,'2022-06-10 09:46:02','2022-06-10 09:46:02',1,1);
INSERT INTO `dish` VALUES (53,'Water Poached Fish',20,38.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png','Ingredients: Grass carp, soup',1,'2022-06-10 09:48:37','2022-06-10 09:48:37',1,1);
INSERT INTO `dish` VALUES (54,'Stir-fried Bok Choy',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png','Ingredients: Rapeseed',1,'2022-06-10 09:51:46','2022-06-10 09:51:46',1,1);
INSERT INTO `dish` VALUES (55,'Garlic Baby Cabbage',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png','Ingredients: garlic, baby cabbage',1,'2022-06-10 09:53:37','2022-06-10 09:53:37',1,1);
INSERT INTO `dish` VALUES (56,'Stir-fried Broccoli',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png','Ingredients: Broccoli',1,'2022-06-10 09:55:44','2022-06-10 09:55:44',1,1);
INSERT INTO `dish` VALUES (57,'Flash Fried Cabbage',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png','Ingredients: Cabbage',1,'2022-06-10 09:58:35','2022-06-10 09:58:35',1,1);
INSERT INTO `dish` VALUES (58,'Steamed Sea Bass',18,98.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png','Ingredients: Sea bass',1,'2022-06-10 10:12:28','2022-06-10 10:12:28',1,1);
INSERT INTO `dish` VALUES (59,'Dongpo Pork',18,138.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png','Ingredients: Pork elbow stick',1,'2022-06-10 10:24:03','2022-06-10 10:24:03',1,1);
INSERT INTO `dish` VALUES (60,'Braised Pork Belly',18,58.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png','Ingredients: pork, preserved vegetables',1,'2022-06-10 10:26:03','2022-06-10 10:26:03',1,1);
INSERT INTO `dish` VALUES (61,'Steamed Fish Head with Chopped Chili Peppers',18,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png','Ingredients: Silver carp, chopped pepper',1,'2022-06-10 10:28:54','2022-06-10 10:28:54',1,1);
INSERT INTO `dish` VALUES (62,'Bullfrog with Pickled Cabbage',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png','Ingredients: Fresh bullfrog, pickled cabbage',1,'2022-06-10 10:33:05','2022-06-10 10:33:05',1,1);
INSERT INTO `dish` VALUES (63,'Spicy Bullfrog Pot',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png','Ingredients: Fresh bullfrog, lotus root, bamboo shoots',1,'2022-06-10 10:35:40','2022-06-10 10:35:40',1,1);
INSERT INTO `dish` VALUES (64,'Sautéed Bullfrog with Chili Sauce',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png','Ingredients: Fresh bullfrog, loofah, bean sprouts',1,'2022-06-10 10:37:52','2022-06-10 10:37:52',1,1);
INSERT INTO `dish` VALUES (65,'grass carp(1kg)',16,68.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png','Ingredients: Grass carp, soybean sprouts, lotus root',1,'2022-06-10 10:41:08','2022-06-10 10:41:08',1,1);
INSERT INTO `dish` VALUES (66,'Longsnout Catfish(1kg)',16,119.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png','Ingredients: fish, bean sprouts, lotus root',1,'2022-06-10 10:42:42','2022-06-10 10:42:42',1,1);
INSERT INTO `dish` VALUES (67,'Pimelodus(1kg)',16,72.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png','Ingredients: catfish, bean sprouts, lotus root',1,'2022-06-10 10:43:56','2022-06-10 10:43:56',1,1);
INSERT INTO `dish` VALUES (68,'Egg Drop Soup',21,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png','Ingredients: Eggs, seaweed',1,'2022-06-10 10:54:25','2022-06-10 10:54:25',1,1);
INSERT INTO `dish` VALUES (69,'Oyster Mushroom and Tofu Soup',21,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png','Ingredients: Tofu, Oyster Mushroom',1,'2022-06-10 10:55:02','2022-06-10 10:55:02',1,1);

DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dish_id` bigint NOT NULL COMMENT 'Dish ID',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Flavor Name',
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Flavor Data List',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Dish flavor relationship table';

INSERT INTO `dish_flavor` VALUES (40,10,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (41,7,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (42,7,'Temperature','[\"Hot\",\"Normal\",\"Ice-free\",\"Less Ice\",\"More Ice\"]');
INSERT INTO `dish_flavor` VALUES (45,6,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (46,6,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (47,5,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (48,5,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (49,2,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (50,4,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (51,3,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (52,3,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (86,52,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (87,52,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (88,51,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (89,51,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (92,53,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (93,53,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (94,54,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (95,56,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (96,57,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (97,60,'Dislike','[\"No Garlic\",\"No Onion\",\"No Coriander\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (101,66,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (102,67,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');
INSERT INTO `dish_flavor` VALUES (103,65,'Spicy','[\"Not Spicy\",\"Mild\",\"Medium\",\"Hot\"]');

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'Name',
  `username` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'Username',
  `password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'Password',
  `phone` varchar(11) COLLATE utf8_bin NOT NULL COMMENT 'Phone',
  `sex` varchar(2) COLLATE utf8_bin NOT NULL COMMENT 'Sex',
  `id_number` varchar(18) COLLATE utf8_bin NOT NULL COMMENT 'ID Number',
  `status` int NOT NULL DEFAULT '1' COMMENT 'Status 0:Disabled, 1:Enabled',
  `create_time` datetime DEFAULT NULL COMMENT 'Create Time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  `create_user` bigint DEFAULT NULL COMMENT 'Create User',
  `update_user` bigint DEFAULT NULL COMMENT 'Update User',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Employee Information'; 

INSERT INTO `employee` VALUES (1,'admin','admin','e10adc3949ba59abbe56e057f20f883e','01165398254','1','895377254133',1,'2022-02-15 15:51:20','2022-02-17 09:16:20',10,1);

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Name',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Image',
  `order_id` bigint NOT NULL COMMENT 'Order ID',
  `dish_id` bigint DEFAULT NULL COMMENT 'Dish ID',
  `setmeal_id` bigint DEFAULT NULL COMMENT 'Setmeal ID',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Dish Flavor',
  `number` int NOT NULL DEFAULT '1' COMMENT 'Number',
  `amount` decimal(10,2) NOT NULL COMMENT 'Amount',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Order Detail';

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Order Number',
  `status` int NOT NULL DEFAULT '1' COMMENT 'Order Status 1:Pending Payment, 2:Pending Order, 3:Order Accepted, 4:Delivery in Progress, 5:Completed, 6:Cancelled, 7:Refunded',
  `user_id` bigint NOT NULL COMMENT 'User ID',
  `address_book_id` bigint NOT NULL COMMENT 'Address Book ID',
  `order_time` datetime NOT NULL COMMENT 'Order Time',
  `checkout_time` datetime DEFAULT NULL COMMENT 'Checkout Time',
  `pay_method` int NOT NULL DEFAULT '1' COMMENT 'Payment Method 1:Wechat, 2:Alipay',
  `pay_status` tinyint NOT NULL DEFAULT '0' COMMENT 'Payment Status 0:Unpaid, 1:Paid, 2:Refunded',
  `amount` decimal(10,2) NOT NULL COMMENT 'Actual Amount',
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'Remark',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT 'Phone',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Address',
  `user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'User Name',
  `consignee` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Consignee',
  `cancel_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Cancel Reason',
  `rejection_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Rejection Reason',
  `cancel_time` datetime DEFAULT NULL COMMENT 'Cancel Time',
  `estimated_delivery_time` datetime DEFAULT NULL COMMENT 'Estimated Delivery Time',
  `delivery_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Delivery Status 1:Immediate Delivery, 0:Select Specific Time',
  `delivery_time` datetime DEFAULT NULL COMMENT 'Delivery Time',
  `pack_amount` int DEFAULT NULL COMMENT 'Pack Amount',
  `tableware_number` int DEFAULT NULL COMMENT 'Tableware Number',
  `tableware_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Tableware Status 1:Provide by Meal, 0:Select Specific Number',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Order';

DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `category_id` bigint NOT NULL COMMENT 'Category ID',
  `name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT 'Name',
  `price` decimal(10,2) NOT NULL COMMENT 'Price',
  `status` int DEFAULT '1' COMMENT 'Status 0:Disabled, 1:Enabled',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Description',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Image',
  `create_time` datetime DEFAULT NULL COMMENT 'Create Time',
  `update_time` datetime DEFAULT NULL COMMENT 'Update Time',
  `create_user` bigint DEFAULT NULL COMMENT 'Create User',
  `update_user` bigint DEFAULT NULL COMMENT 'Update User',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Setmeal';

DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `setmeal_id` bigint DEFAULT NULL COMMENT 'Setmeal ID',
  `dish_id` bigint DEFAULT NULL COMMENT 'Dish ID',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Dish Name',
  `price` decimal(10,2) DEFAULT NULL COMMENT 'Dish Price',
  `copies` int DEFAULT NULL COMMENT 'Dish Copies',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Setmeal Dish';

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Name',
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT 'Image',
  `user_id` bigint NOT NULL COMMENT 'User ID',
  `dish_id` bigint DEFAULT NULL COMMENT 'Dish ID',
  `setmeal_id` bigint DEFAULT NULL COMMENT 'Setmeal ID',
  `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Dish Flavor',
  `number` int NOT NULL DEFAULT '1' COMMENT 'Number',
  `amount` decimal(10,2) NOT NULL COMMENT 'Amount',
  `create_time` datetime DEFAULT NULL COMMENT 'Create Time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='Shopping Cart';

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `openid` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT 'OpenID',
  `name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'Name',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT 'Phone',
  `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT 'Sex',
  `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT 'ID Number',
  `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT 'Avatar',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin COMMENT='User';