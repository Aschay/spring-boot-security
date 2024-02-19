
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);
  
CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX ix_auth_username
  on authorities (username,authority);
  
  
 INSERT INTO users (username, password, enabled)
  values ('admin',
    '$2a$10$yqX47YDNRUfxBWG3dQwCoukJHnPeFmW1z.gwd/gz1yQ2CeflFp29q',
    1);

INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_ADMIN'); 
  
 INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_USER'); 
  
  
   INSERT INTO users (username, password, enabled)
  values ('user',
    '$2a$10$iUOXghHUg1Ry5za4e4F24OulZmqTtxQ87OISPkv3CiEJTNEz50bXm',
    1);

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_USER'); 
  
  