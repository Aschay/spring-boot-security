

INSERT INTO `secureapp`.`users` (`id`, `email`, `password`, `username`,`roles`) 
VALUES ('1', 'admin@gmail.com', '$2a$10$o6waKda/zukybLm9dIur3uTZRxByKGSRQqeewKF5SMJ6mLLLdA/eC', 'admin','ROLE_ADMIN,ROLE_USER');


INSERT INTO `secureapp`.`users` (`id`, `email`, `password`, `username`,`roles`) 
VALUES ('2', 'user@gmail.com', '$2a$10$iUOXghHUg1Ry5za4e4F24OulZmqTtxQ87OISPkv3CiEJTNEz50bXm', 'user','ROLE_USER');
