drop database twk;
create database twk;
use twk;

-- DB를 drop 후 create 했을 경우에는 table drop시 오류 발생 --
drop table attach;
drop table reply;
drop table board;
drop table member;
drop table scrap;
drop table recommend;

create table member
(
    uNo bigint primary key auto_increment comment '회원 번호',
    uMail varchar(50) not null comment '회원 이메일',
    uPW varchar(100) not null comment '비밀번호',
    uName varchar(100) not null comment '닉네임',
    uInter varchar(5) not null comment '관심분야',
    joinDate datetime not null default now() comment '가입일자',
    uRetire varchar(2) not null default 'U' comment '탈퇴여부',
    uLevel varchar(2) not null default 'U' comment '회원레벨',
    uRetireDate datetime not null default now() comment '탈퇴일시'
) comment '계정목록' ;

create table board
(
    pNo bigint auto_increment primary key comment '게시물번호',
    uNo bigint comment '작성자',
    pDate datetime default now() comment '작성일자',
    pTitle varchar(100) not null comment '게시물 제목',
    pContent text comment '게시물 내용',
    pType varchar(2) not null comment '게시판 종류',
    pCnt bigint default 0 comment '조회수',
    pDel varchar(2) comment '삭제여부',
    pDelDate datetime default now() comment '삭제일시',
    pBlind varchar(2) not null default 'n' comment '블라인드 여부',
    foreign key (uNo) references member (uNo)
) comment '게시판';

create table recommend
(
	rcNo bigint auto_increment primary key comment '추천관리번호',
	pNo  bigint comment '게시물 번호',
	uNo  bigint comment '회원번호',
	rc   int    comment '추천' default 0,
	rcDate datetime not null default now() comment '추천일시',
	foreign key (pNo) references board (pNo),
	foreign key (uNo) references member (uNo)
) comment '추천하기';

create table scrap
(
    sNo bigint primary key auto_increment comment '스크랩 번호',
    uNo bigint comment '스크랩 회원 번호',
    pNo bigint comment '스크랩 게시물 번호',
    foreign key (uNo) references member(uNo),
    foreign key (pNo) references board(pNo)
) comment '스크랩 목록';

create table reply
(
    rNo bigint auto_increment primary key comment '댓글 번호',
    pNo bigint comment '게시글 번호',
    uNo bigint comment '댓글 작성자',
    rContent text comment '댓글 내용',
    rDate datetime default now() comment '댓글 작성일자',
    rDel varchar(2) comment '댓글 삭제여부',
    rDelDate datetime default now() comment '댓글 삭제일시',
    foreign key (pNo) references board (pNo),
    foreign key (uNo) references member (uNo)
) comment '댓글 목록';

create table attach
(
    aNo bigint primary key auto_increment comment '첨부파일 번호',
    pNo bigint comment '게시물 번호',
    fName varchar(255) comment '원본이름',
    uqName varchar(255) comment '저장이름',
    foreign key (pNo) references board(pNo)
) comment '첨부파일';

create table report 
(
    rpNo bigint primary key auto_increment comment '신고 번호',
    rpDate datetime not null comment '신고 시각',
    pNo bigint comment '신고 대상 게시물 번호',
    rpType smallint(1) unsigned not null comment '0 = 스팸, 1 = 음란물, 2 = 성격에 맞지 않는 글,
												  3 = 과도한 욕설, 4 = 광고, 5 = 사회 분란 글',
	rp   int   comment '신고' default 0,
    uNo bigint comment '신고한 회원 번호',
    rpNote text not null comment '신고 내용',
    foreign key (pNo) references board(pNo),
    foreign key (uNo) references member(uNo)
) comment '신고 목록';