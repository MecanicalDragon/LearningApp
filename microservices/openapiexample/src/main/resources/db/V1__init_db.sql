create table SKILL
(
    id         bigint generated by default as identity primary key,
    name       varchar(255) not null,
    skill_type varchar(255) not null,
    duration   int          not null,
    power      int          not null,
    constraint skill_uk unique (name)
);;

create table UNIT
(
    id         bigint generated by default as identity primary key,
    name       varchar(255) not null,
    class_type varchar(255) not null,
    constraint unit_uk unique (name)
);;

create table UNIT_SKILLS
(
    unit_id  bigint not null,
    skill_id bigint not null,
    constraint uk_us unique (unit_id, skill_id),
    constraint fk_unit foreign key (unit_id) references UNIT (id) on delete cascade,
    constraint fk_skill foreign key (skill_id) references SKILL (id) on delete cascade
);;