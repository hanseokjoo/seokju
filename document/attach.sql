drop database attach_db;
create database attach_db;
use attach_db;
create table board
(bNo int primary key auto_increment comment '게시글번호', 
bTitle varchar(200) comment '제목', 
bNote text comment '내용', 
uNo int comment '작성자번호');
create table attach
( 
aNo int primary key auto_increment comment '파일번호', 
bNo int comment '게시글번호', 
fileName varchar(255) comment '논리파일명',
phyName varchar(255) comment '물리파일명', 
foreign key (bNo) references board(bNo)
);

-- 게시물 등록 -- 
insert into board ( bTitle, bNote, uNo ) values ( '제목', '내용', 1 );
-- 방금 등록된 게시물 번호를 받아옴 --
select last_insert_id() as bNo;
-- 첨부파일 등록 시에 받아온 게시물 번호를 참조함 --
insert into attach ( bNo, fileName, phyName ) values ( 1, 'tree.jpg', 'uuid' );

-- 첨부파일의 논리 물리 이름을 받아오는 SQL구문 --
-- 게시글 번호가 1인 게시글에 첨부된 첨부파일을 모두 불러오는 구문 --
select aNo, fileName, phyName, from attach where bNo = 1;
-- 첨부파일 번호로 파일 하나만 받아올 경우 --
select fileName, phyName, from attach where aNo = 1;
