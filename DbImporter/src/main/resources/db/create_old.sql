drop sequence developer_id_sequence;
drop sequence skill_id_sequence;
drop table old_skill;
drop table old_developer;

create sequence developer_id_sequence start with 1 increment by 1;
create sequence skill_id_sequence start with 1 increment by 1;

create table old_developer
(
    id          number(20)    NOT NULL,
    dev_name    varchar2(255) NOT NULL,
    dev_surname varchar(255)  NOT NULL,
    dev_age     number(3)     NOT NULL
);

create table old_skill
(
    id          number(20)    NOT NULL,
    skill_name  varchar2(255) NOT NULL,
    skill_level varchar2(255) check (skill_level IN ('NULL', 'LOW', 'AMATEUR', 'ADVANCED', 'PRO', 'GODLIKE',
                                                     'BEYONDGODLIKE')),
    fk_owner_id number(20),
    foreign key (fk_owner_id) references old_developer (id)
);

insert into old_developer
values (developer_id_sequence.nextval, 'Stanislav', 'Tretyakov', 30);
insert into old_skill
values (skill_id_sequence.nextval, 'Java', 'PRO', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'Kotlin', 'AMATEUR', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'Oracle', 'LOW', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'SOFT', 'NULL', developer_id_sequence.currval);

insert into old_developer
values (developer_id_sequence.nextval, 'Vasiliy', 'Pupkin', 35);
insert into old_skill
values (skill_id_sequence.nextval, 'Java', 'GODLIKE', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'ARS', 'ADVANCED', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'Oracle', 'PRO', developer_id_sequence.currval);

insert into old_developer
values (developer_id_sequence.nextval, 'John', 'Snow', 26);
insert into old_skill
values (skill_id_sequence.nextval, 'Java', 'PRO', developer_id_sequence.currval);
insert into old_skill
values (skill_id_sequence.nextval, 'Kotlin', 'GODLIKE', developer_id_sequence.currval);