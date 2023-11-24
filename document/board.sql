drop database board04;
create database board04;
use board04;

-- DB�� drop �� create ���� ��쿡�� talbe drop�� ���� �߻� --
drop table attach;
drop table reply;
drop table board;
drop table fav;
drop table account;

create table account
(
    uNo int primary key auto_increment comment 'ȸ����ȣ',
    uID varchar(50) NOT NULL comment '���̵�',
    uPW varchar(100) NOT NULL comment '��й�ȣ',
    uName varchar(100) NOT NULL comment '�̸�',
    uGender varchar(2) comment '����',
    uHobby varchar(2) comment '���',
    joinDate DATETIME NOT NULL default now() comment '��������',
    isRetire varchar(2) NOT NULL default 'U' comment 'Ż�𿩺�',
    uLevel varchar(2) NOT NULL default 'U' comment 'ȸ������'
) comment '�������' ;

create table fav
(
    fNo int auto_increment primary key comment '������ȣ',
    fName varchar(2) comment '���ɺо߸�',
    uNo int comment 'ȸ����ȣ',
    foreign key (uNo) references account (uNo)
) comment '���ɺо�';

create table board
(
    bNo int auto_increment primary key comment '�Խñ۹�ȣ',
    bType varchar(2) comment '�Խ�������',
    bTitle varchar(200) comment '����',
    bNote TEXT comment '����',
    wDate DATETIME default now() comment '�ۼ���',
    hit int default 0 comment '��ȸ��',
    uNo int comment '�Խñ��ۼ���',
    foreign key (uNo) references account (uNo)
) comment '�Խñ۸��';

create table reply
(
    rNo int auto_increment primary key comment '��۹�ȣ',
    rNote TEXT comment '��۳���',
    rDate DATETIME default now() comment '����ۼ���',
    bNo int comment '���Խñ۹�ȣ',
    uNo int comment '����ۼ���',
    foreign key (bNo) references board (bNo),
    foreign key (uNo) references account (uNo)
) comment '��۸��';

create table attach
(
    aNo int primary key auto_increment comment '���Ϲ�ȣ',
    bNo int comment '�Խñ۹�ȣ',
    fileName varchar(255) comment '�����ϸ�',
    phyName varchar(255) comment '�������ϸ�',
    foreign key (bNo) references board(bNo)
) comment '÷�����ϸ��';