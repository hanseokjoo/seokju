drop database board04;
create database board04;
use board04;

-- DB를 drop 후 create 했을 경우에는 talbe drop시 오류 발생 --
drop table attach;
drop table reply;
drop table board;
drop table fav;
drop table account;

create table account
(
    uNo int primary key auto_increment comment '회원번호',
    uID varchar(50) NOT NULL comment '아이디',
    uPW varchar(100) NOT NULL comment '비밀번호',
    uName varchar(100) NOT NULL comment '이름',
    uGender varchar(2) comment '성별',
    uHobby varchar(2) comment '취미',
    joinDate DATETIME NOT NULL default now() comment '가입일자',
    isRetire varchar(2) NOT NULL default 'U' comment '탈퇴여부',
    uLevel varchar(2) NOT NULL default 'U' comment '회원레벨'
) comment '계정목록' ;

create table fav
(
    fNo int auto_increment primary key comment '관리번호',
    fName varchar(2) comment '관심분야명',
    uNo int comment '회원번호',
    foreign key (uNo) references account (uNo)
) comment '관심분야';

create table board
(
    bNo int auto_increment primary key comment '게시글번호',
    bType varchar(2) comment '게시판종류',
    bTitle varchar(200) comment '제목',
    bNote TEXT comment '내용',
    wDate DATETIME default now() comment '작성일',
    hit int default 0 comment '조회수',
    uNo int comment '게시글작성자',
    foreign key (uNo) references account (uNo)
) comment '게시글목록';

create table reply
(
    rNo int auto_increment primary key comment '댓글번호',
    rNote TEXT comment '댓글내용',
    rDate DATETIME default now() comment '댓글작성일',
    bNo int comment '대상게시글번호',
    uNo int comment '댓글작성자',
    foreign key (bNo) references board (bNo),
    foreign key (uNo) references account (uNo)
) comment '댓글목록';

create table attach
(
    aNo int primary key auto_increment comment '파일번호',
    bNo int comment '게시글번호',
    fileName varchar(255) comment '논리파일명',
    phyName varchar(255) comment '물리파일명',
    foreign key (bNo) references board(bNo)
) comment '첨부파일목록';