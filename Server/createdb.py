import sqlite3
conn = sqlite3.connect('database.db')
c = conn.cursor()
sql = """
DROP TABLE IF EXISTS students;
CREATE TABLE students (id integer unique primary key autoincrement,name text,pass text);"""
c.executescript(sql)
sql = """
DROP TABLE IF EXISTS faculty;
CREATE TABLE faculty (id integer unique primary key autoincrement,name text,pass text);"""
c.executescript(sql)
sql = """
DROP TABLE IF EXISTS attendlog;
CREATE TABLE attendlog (sno integer unique primary key autoincrement,id integer,facultyid integer,attdate DATE,FOREIGN KEY(id) REFERENCES students(id),FOREIGN KEY(facultyid) REFERENCES faculty(id));"""
c.executescript(sql)
sql = """
DROP TABLE IF EXISTS attreq;
CREATE TABLE attreq (sno integer unique primary key autoincrement,id integer,datereq DATE,FOREIGN KEY(id) REFERENCES faculty(id));"""
c.executescript(sql)
sql="""INSERT INTO SQLITE_SEQUENCE VALUES('students',18000)"""
c.executescript(sql)
sql="""INSERT INTO SQLITE_SEQUENCE VALUES('faculty',16000)"""
c.executescript(sql)
conn.commit()
conn.close() 
