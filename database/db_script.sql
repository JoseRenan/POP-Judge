DROP DATABASE POP_JUDGE;
CREATE DATABASE POP_JUDGE;

USE POP_JUDGE;

CREATE TABLE USER (
	id_user INT AUTO_INCREMENT,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	email VARCHAR(255) NOT NULL,
	full_name VARCHAR(255) NOT NULL,
	city VARCHAR(255) NOT NULL,
	college VARCHAR(255) NOT NULL,
	dir VARCHAR(255) NOT NULL,

	PRIMARY KEY(id_user)
);

CREATE UNIQUE INDEX id_user
ON USER (id_user);

CREATE UNIQUE INDEX username
ON USER (username);

CREATE TABLE PROBLEM (
	id_problem INT AUTO_INCREMENT,
	title VARCHAR(255) NOT NULL,
	score_points INT NOT NULL, 
	time_limit LONG NOT NULL,
	dir VARCHAR(255) NOT NULL,

	PRIMARY KEY(id_problem)
);

CREATE TABLE LANGUAGE (
	id_language INT,
	name VARCHAR(45) NOT NULL,

	PRIMARY KEY(id_language)
);

CREATE TABLE CLARIFICATION (
	id_clarification INT AUTO_INCREMENT,
	id_user INT,
	id_problem INT,
	issue VARCHAR(255) NOT NULL,
	answer VARCHAR(255),
	time_clarification TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),

	PRIMARY KEY(id_clarification),
	
	CONSTRAINT fk_USER_id_userCLARIFICATION_id_user
	FOREIGN KEY (id_user)
	REFERENCES USER(id_user),

	CONSTRAINT fk_PROBLEM_id_problem_CLARIFICATION_id_problem
	FOREIGN KEY (id_problem)
	REFERENCES PROBLEM(id_problem)
);

CREATE TABLE SUBMISSION (
	id_submission INT AUTO_INCREMENT,
	id_user INT, 
	id_problem INT,
	id_language INT,
	file_name VARCHAR(250) NOT NULL,
	time_submission TIMESTAMP NOT NULL,
	veredict VARCHAR(45) NOT NULL,
 
	PRIMARY KEY(id_submission),

	CONSTRAINT fk_PROBLEM_id_problem_SUBMISSION_id_problem
	FOREIGN KEY (id_problem)
	REFERENCES PROBLEM(id_problem),

	CONSTRAINT fk_USER_id_user_SUBMISSION_id_user
	FOREIGN KEY (id_user)
	REFERENCES USER(id_user),

	CONSTRAINT fk_LANGUAGE_id_language_SUBMISSION_id_language
	FOREIGN KEY (id_language)
	REFERENCES LANGUAGE(id_language)
);

CREATE TABLE RANKING (
	username VARCHAR(50),
	id_problem INT NOT NULL,
	tries INT NOT NULL,
	passed_time TIMESTAMP NOT NULL,
	
	PRIMARY KEY(username, id_problem),
	
	CONSTRAINT fk_USER_username_RANKING_username
	FOREIGN KEY (username)
	REFERENCES USER(username)
);

CREATE INDEX username
ON RANKING (username);

INSERT INTO LANGUAGE VALUES(1, 'C');
INSERT INTO LANGUAGE VALUES(2, 'Cpp');
INSERT INTO LANGUAGE VALUES(3, 'Java');
INSERT INTO LANGUAGE VALUES(4, 'Pascal');
INSERT INTO LANGUAGE VALUES(5, 'Python');

INSERT INTO USER(id_user, username, password, email, full_name, city, college, dir) VALUES(0, 'Admin', 'admin123', '', '', '', '', '');
