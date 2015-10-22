-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2015-10-15 07:34:26.443




-- tables
-- Table: category
CREATE TABLE category (
    id serial  NOT NULL,
    name varchar(30)  NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (id)
);



-- Table: task
CREATE TABLE task (
    id serial  NOT NULL,
    title varchar(50)  NOT NULL,
    description varchar(500)  NOT NULL,
    is_done boolean  NOT NULL,
    category_id serial  NOT NULL,
    CONSTRAINT task_pk PRIMARY KEY (id)
);







-- foreign keys
-- Reference:  task_category (table: task)


ALTER TABLE task ADD CONSTRAINT task_category 
    FOREIGN KEY (category_id)
    REFERENCES category (id)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;






-- End of file.

