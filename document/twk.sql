drop database twk;
create database twk;
use twk;

-- DB�� drop �� create ���� ��쿡�� table drop�� ���� �߻� --
drop table attach;
drop table reply;
drop table board;
drop table member;
drop table scrap;
drop table recommend;

create table member
(
    uNo bigint primary key auto_increment comment 'ȸ�� ��ȣ',
    uMail varchar(50) not null comment 'ȸ�� �̸���',
    uPW varchar(100) not null comment '��й�ȣ',
    uName varchar(100) not null comment '�г���',
    uInter varchar(5) not null comment '���ɺо�',
    joinDate datetime not null default now() comment '��������',
    uRetire varchar(2) not null default 'U' comment 'Ż�𿩺�',
    uLevel varchar(2) not null default 'U' comment 'ȸ������',
    uRetireDate datetime not null default now() comment 'Ż���Ͻ�'
) comment '�������' ;

create table board
(
    pNo bigint auto_increment primary key comment '�Խù���ȣ',
    uNo bigint comment '�ۼ���',
    pDate datetime default now() comment '�ۼ�����',
    pTitle varchar(100) not null comment '�Խù� ����',
    pContent text comment '�Խù� ����',
    pType varchar(2) not null comment '�Խ��� ����',
    pCnt bigint default 0 comment '��ȸ��',
    pDel varchar(2) comment '��������',
    pDelDate datetime default now() comment '�����Ͻ�',
    pBlind varchar(2) not null default 'n' comment '����ε� ����',
    foreign key (uNo) references member (uNo)
) comment '�Խ���';

create table recommend
(
	rcNo bigint auto_increment primary key comment '��õ������ȣ',
	pNo  bigint comment '�Խù� ��ȣ',
	uNo  bigint comment 'ȸ����ȣ',
	rc   int    comment '��õ' default 0,
	rcDate datetime not null default now() comment '��õ�Ͻ�',
	foreign key (pNo) references board (pNo),
	foreign key (uNo) references member (uNo)
) comment '��õ�ϱ�';

create table scrap
(
    sNo bigint primary key auto_increment comment '��ũ�� ��ȣ',
    uNo bigint comment '��ũ�� ȸ�� ��ȣ',
    pNo bigint comment '��ũ�� �Խù� ��ȣ',
    foreign key (uNo) references member(uNo),
    foreign key (pNo) references board(pNo)
) comment '��ũ�� ���';

create table reply
(
    rNo bigint auto_increment primary key comment '��� ��ȣ',
    pNo bigint comment '�Խñ� ��ȣ',
    uNo bigint comment '��� �ۼ���',
    rContent text comment '��� ����',
    rDate datetime default now() comment '��� �ۼ�����',
    rDel varchar(2) comment '��� ��������',
    rDelDate datetime default now() comment '��� �����Ͻ�',
    foreign key (pNo) references board (pNo),
    foreign key (uNo) references member (uNo)
) comment '��� ���';

create table attach
(
    aNo bigint primary key auto_increment comment '÷������ ��ȣ',
    pNo bigint comment '�Խù� ��ȣ',
    fName varchar(255) comment '�����̸�',
    uqName varchar(255) comment '�����̸�',
    foreign key (pNo) references board(pNo)
) comment '÷������';

create table report 
(
    rpNo bigint primary key auto_increment comment '�Ű� ��ȣ',
    rpDate datetime not null comment '�Ű� �ð�',
    pNo bigint comment '�Ű� ��� �Խù� ��ȣ',
    rpType smallint(1) unsigned not null comment '0 = ����, 1 = ������, 2 = ���ݿ� ���� �ʴ� ��,
												  3 = ������ �弳, 4 = ����, 5 = ��ȸ �ж� ��',
	rp   int   comment '�Ű�' default 0,
    uNo bigint comment '�Ű��� ȸ�� ��ȣ',
    rpNote text not null comment '�Ű� ����',
    foreign key (pNo) references board(pNo),
    foreign key (uNo) references member(uNo)
) comment '�Ű� ���';