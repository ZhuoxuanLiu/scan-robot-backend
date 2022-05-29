SET GLOBAL time_zone = '+8:00';
FLUSH PRIVILEGES;

CREATE DATABASE robot;

CREATE TABLE IF NOT EXISTS infoCard (
    id int(11) NOT NULL AUTO_INCREMENT,
    status varchar(255) NOT NULL DEFAULT 0,
    currentBook varchar(255) NOT NULL DEFAULT 0,
    runTime varchar(255) NOT NULL DEFAULT 0,
    time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id, time)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO infoCard (id, status, currentBook, runTime) VALUES (1, 0, 0, 0);

CREATE TABLE `robot`.`user`  (
    `id` int NOT NULL,
    `email` varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `memo` varchar(255) NULL,
    `region` varchar(255) NULL,
    `province` varchar(255) NULL,
    `city` varchar(255) NULL,
    `phone` varchar(255) NOT NULL,
    PRIMARY KEY (`id`, `email`, `phone`)
) ENGINE = InnoDB CHARACTER SET = utf8;