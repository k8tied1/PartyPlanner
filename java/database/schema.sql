BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$10$phZ4Xj2wDx0.NrXq944Cre.9zh0ufPo1DSbdCJq255vUQ1uOuolNC','ROLE_ADMIN');

DROP TABLE IF EXISTS parties;
CREATE TABLE parties (
	party_id SERIAL,
	party_name varchar(50) NOT NULL,
	owner_id int NOT NULL,
	CONSTRAINT PK_party_id PRIMARY KEY (party_id),
	CONSTRAINT FK_party_owner FOREIGN KEY (owner_id) REFERENCES users(user_id)
);

--This will mean that Chris's party will always have party id 1 so I can be lazy fn on the front end and just hard-code it for now
INSERT INTO parties (party_name,owner_id) VALUES ('Chis''s Thirthieth Birthday Party', (SELECT user_id FROM users WHERE username='admin'));

DROP TABLE IF EXISTS messages;
CREATE TABLE messages (
	message_id SERIAL,
	party_id int NOT NULL,
	from_name varchar(50) DEFAULT 'anonymous chris fan',
	message varchar(200) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT PK_messages PRIMARY KEY (message_id),
	CONSTRAINT FK_message_party FOREIGN KEY (party_id) REFERENCES parties(party_id)
);

DROP TABLE IF EXISTS rsvps;
CREATE TABLE rsvps (
	rsvp_id SERIAL,
	party_id int NOT NULL,
	name varchar(50) NOT NULL,
	numAttending int DEFAULT 1,
	email varchar(100) NULL,
	deets varchar(100) ,
	CONSTRAINT PK_rsvps PRIMARY KEY (rsvp_id),
	CONSTRAINT FK_rsvps_party FOREIGN KEY (party_id) REFERENCES parties(party_id)
);

COMMIT TRANSACTION;
