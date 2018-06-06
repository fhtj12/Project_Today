CREATE TABLE `account` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `pwd` char(128) COLLATE utf8_unicode_ci NOT NULL,
  `uid` char(40) COLLATE utf8_unicode_ci NOT NULL,
  `last_login` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `char_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `party_char_1` int(11) DEFAULT NULL,
  `party_char_2` int(11) DEFAULT NULL,
  `party_char_3` int(11) DEFAULT NULL,
  `party_char_4` int(11) DEFAULT NULL,
  `party_char_5` int(11) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  UNIQUE KEY `idx_UNIQUE` (`idx`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `uid_UNIQUE` (`uid`),
  UNIQUE KEY `party_char_1_UNIQUE` (`party_char_1`),
  UNIQUE KEY `party_char_2_UNIQUE` (`party_char_2`),
  UNIQUE KEY `party_char_3_UNIQUE` (`party_char_3`),
  UNIQUE KEY `party_char_4_UNIQUE` (`party_char_4`),
  UNIQUE KEY `party_char_5_UNIQUE` (`party_char_5`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `character` (
  `char_id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(40) CHARACTER SET utf8 NOT NULL,
  `level` int(11) DEFAULT '1',
  `stength` int(11) DEFAULT NULL,
  `agility` int(11) DEFAULT NULL,
  `integer` int(11) DEFAULT NULL,
  `luck` int(11) DEFAULT NULL,
  `hp` int(11) NOT NULL,
  `mp` int(11) NOT NULL,
  `ability_reset` tinyint(1) DEFAULT '1',
  `char_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `occupation` varchar(45) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`char_id`,`uid`),
  UNIQUE KEY `char_name_UNIQUE` (`char_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `eqiupment` (
  `char_id` int(11) NOT NULL,
  `uid` char(40) NOT NULL,
  `item_id` int(11) NOT NULL,
  `inven_num` int(11) NOT NULL,
  `strength` int(11) DEFAULT NULL,
  `agility` int(11) DEFAULT NULL,
  `integer` int(11) DEFAULT NULL,
  `wisdom` int(11) DEFAULT NULL,
  `luck` int(11) DEFAULT NULL,
  `attack` int(11) DEFAULT NULL,
  `defense` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `cansale` tinyint(1) DEFAULT '1',
  `equip_condition` tinyint(1) NOT NULL DEFAULT '0',
  `type` varchar(45) NOT NULL,
  `item_num` char(20) DEFAULT NULL,
  PRIMARY KEY (`char_id`,`uid`),
  UNIQUE KEY `item_num_UNIQUE` (`item_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `expendables` (
  `char_id` int(11) NOT NULL,
  `uid` char(40) COLLATE utf8_unicode_ci NOT NULL,
  `item_id` int(11) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `inven_num` int(11) NOT NULL,
  `cansale` tinyint(1) DEFAULT '1',
  `item_num` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`char_id`,`uid`),
  UNIQUE KEY `item_num_UNIQUE` (`item_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `material` (
  `char_id` int(11) NOT NULL,
  `uid` char(40) COLLATE utf8_unicode_ci NOT NULL,
  `item_id` int(11) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `inven_num` int(11) NOT NULL,
  `cansale` tinyint(1) DEFAULT '1',
  `item_num` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`char_id`,`uid`),
  UNIQUE KEY `item_num_UNIQUE` (`item_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `skill` (
  `char_id` int(11) NOT NULL,
  `skill_id` int(11) NOT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`char_id`,`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

