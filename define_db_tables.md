# Database Schema
## Frontend
### Document
`
CREATE TABLE Document(
DocID VARCHAR(36) NOT NULL PRIMARY KEY,
Head VARCHAR(100) NOT NULL,
Content TEXT NOT NULL,
Like INTEGER NOT NULL DEFAULT 0,
TypeML CHAR(1) NOT NULL CHECK(TypeML IN ('M', 'L')));;
`

`
INSERT OR REPLACE INTO Document(DocID,Head,Content,Like,TypeML) VALUES ("leohagi1","Computer","Computer is the best invention that human have ever made. To learn computer science is to learn the whole modern scinece.",27,"M");;
INSERT INTO Document(DocID,Head,Content,Like,TypeML) VALUES ("leohagi2","Computer","I don't like computer stuff, they are just pointless. I prefer to listen to music",27,"L");;
INSERT INTO Document(DocID,Head,Content,Like,TypeML) VALUES ("leohagi3","Mathematics","I learned the basic theorem of the group theory, the 'First Isomorphism Theorem'.",2,"M");;
`

### HeadGroup
`
CREATE TABLE HeadGroup(
RecordID VARCHAR(36) NOT NULL PRIMARY KEY,
GroupName VARCHAR(100) NOT NULL,
Like INTEGER DEFAULT 0,
Name VARCHAR(100) NOT NULL,
TypeHG CHAR(1) NOT NULL CHECK(TypeHG IN ('H', 'G')));;
`

## Backend
### Document
`
CREATE TABLE Document(
DocID VARCHAR(36) NOT NULL PRIMARY KEY,
Head VARCHAR(100) NOT NULL,
Content TEXT NOT NULL,
Like INTEGER NOT NULL DEFAULT 0);;
`

### HeadGroup
`
CREATE TABLE HeadGroup(
RecordID VARCHAR(36) NOT NULL PRIMARY KEY,
GroupName VARCHAR(100) NOT NULL,
Like INTEGER DEFAULT 0,
Name VARCHAR(100) NOT NULL,
TypeHG CHAR(1) NOT NULL CHECK(TypeHG IN ('H', 'G')));;
`

### NameSpace
`
CREATE TABLE NameSpace(
Name VARCHAR(100) NOT NULL PRIMARY KEY,
TypeHG CHAR(1) NOT NULL CHECK(TypeHG IN ('H', 'G')));;
`