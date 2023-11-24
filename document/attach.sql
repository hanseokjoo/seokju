drop database attach_db;
create database attach_db;
use attach_db;
create table board
(bNo int primary key auto_increment comment '�Խñ۹�ȣ', 
bTitle varchar(200) comment '����', 
bNote text comment '����', 
uNo int comment '�ۼ��ڹ�ȣ');
create table attach
( 
aNo int primary key auto_increment comment '���Ϲ�ȣ', 
bNo int comment '�Խñ۹�ȣ', 
fileName varchar(255) comment '�����ϸ�',
phyName varchar(255) comment '�������ϸ�', 
foreign key (bNo) references board(bNo)
);

-- �Խù� ��� -- 
insert into board ( bTitle, bNote, uNo ) values ( '����', '����', 1 );
-- ��� ��ϵ� �Խù� ��ȣ�� �޾ƿ� --
select last_insert_id() as bNo;
-- ÷������ ��� �ÿ� �޾ƿ� �Խù� ��ȣ�� ������ --
insert into attach ( bNo, fileName, phyName ) values ( 1, 'tree.jpg', 'uuid' );

-- ÷�������� �� ���� �̸��� �޾ƿ��� SQL���� --
-- �Խñ� ��ȣ�� 1�� �Խñۿ� ÷�ε� ÷�������� ��� �ҷ����� ���� --
select aNo, fileName, phyName, from attach where bNo = 1;
-- ÷������ ��ȣ�� ���� �ϳ��� �޾ƿ� ��� --
select fileName, phyName, from attach where aNo = 1;
