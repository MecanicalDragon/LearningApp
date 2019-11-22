drop sequence developer_id_sequence;
drop sequence skill_id_sequence;
drop table new_skill;
drop table new_developer;

create sequence developer_id_sequence start with 1 increment by 1;
create sequence skill_id_sequence start with 1 increment by 1;

create table new_developer
(
    id          number(20)    NOT NULL,
    name    varchar2(255) NOT NULL,
    surname varchar(255)  NOT NULL,
    age     number(3)     NOT NULL
);

create table new_skill
(
    id          number(20)                                             NOT NULL,
    new_name  varchar2(255)                                          NOT NULL,
    sk_level varchar2(255) check (sk_level IN ('NULL', 'LOW', 'AMATEUR', 'ADVANCED', 'PRO', 'GODLIKE',
                                                     'BEYONDGODLIKE')),
    fk_developer_id number(20),
    foreign key (fk_developer_id) references new_developer (id)
);