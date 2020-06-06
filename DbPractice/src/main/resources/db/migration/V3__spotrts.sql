create table discipline
(
    id         serial primary key not null,
    name       varchar(255)       not null,
    popularity int
);

create table athlete
(
    id         serial primary key not null,
    name       varchar(255)       not null,
    gender     varchar(1) check (gender in ('m', 'f')),
    discipline bigint references discipline (id),
    score      int
);

insert into discipline
values (1, 'Tennis', 5);
insert into discipline
values (2, 'Ski', 8);
insert into discipline
values (3, 'MotoTrial', 3);
insert into discipline
values (4, 'Run', 5);
insert into discipline
values (5, 'Skate', 10);

insert into athlete (name, gender, discipline, score)
values ('Viktor Rizinkov', 'm', 1, 59);
insert into athlete (name, gender, discipline, score)
values ('Viktor Kram', 'm', 1, 45);
insert into athlete (name, gender, discipline, score)
values ('Petr Aguzov', 'm', 1, 63);
insert into athlete (name, gender, discipline, score)
values ('Semen Slepakov', 'm', 1, 86);
insert into athlete (name, gender, discipline, score)
values ('Billy Boy', 'm', 1, 35);
insert into athlete (name, gender, discipline, score)
values ('Marta Scope', 'f', 1, 45);
insert into athlete (name, gender, discipline, score)
values ('Britney Spears', 'f', 1, 63);
insert into athlete (name, gender, discipline, score)
values ('Lola Zirova', 'f', 1, 76);
insert into athlete (name, gender, discipline, score)
values ('Michelle Moan', 'f', 1, 92);
insert into athlete (name, gender, discipline, score)
values ('Lucy Liu', 'f', 1, 29);

insert into athlete (name, gender, discipline, score)
values ('Stanislav Vasin', 'm', 2, 46);
insert into athlete (name, gender, discipline, score)
values ('God Beyondov', 'm', 2, 67);
insert into athlete (name, gender, discipline, score)
values ('Mik Gordon', 'm', 2, 35);
insert into athlete (name, gender, discipline, score)
values ('Alex Sail', 'm', 2, 43);
insert into athlete (name, gender, discipline, score)
values ('Steve Ostin', 'm', 2, 74);
insert into athlete (name, gender, discipline, score)
values ('Alla Pugacheva', 'f', 2, 86);
insert into athlete (name, gender, discipline, score)
values ('Lena Golovach', 'f', 2, 39);
insert into athlete (name, gender, discipline, score)
values ('Klara Koralla', 'f', 2, 95);
insert into athlete (name, gender, discipline, score)
values ('Cher Goncharova', 'f', 2, 48);
insert into athlete (name, gender, discipline, score)
values ('Svetlana Star', 'f', 2, 73);

-- enumerate rows
select name,
       case when gender = 'm' then 'male' else 'female' end as gender,
       score,
       row_number() over (order by score desc)              as "prize place"
from athlete
where discipline = 1;

-- RANK omits numbers after same places
-- DENSE_RANK does not omit numbers
-- WINDOW can be assigned to a variable as below
with skiers as (select * from athlete where discipline = 1)
select name,
       score,
       row_number()
           over w as "order",
       rank()
           over w as "prize place",
       dense_rank()
           over w as "dense prize place"
from skiers window w as (order by score desc);

-- В качестве функции можно использовать, так сказать, истинные оконные функции из мануала — это row_number(), rank(), lead() и т.д.,
-- а можно использовать функции-агрегаты, такие как: sum(), count() и т.д. Так вот, это важно, агрегатные функции работают слегка по-другому:
-- если не задан ORDER BY в окне, идет подсчет по всей партиции один раз, и результат пишется во все строки (одинаков для всех строк партиции).
-- Если же ORDER BY задан, то подсчет в каждой строке идет от начала партиции до этой строки.
select name,
       discipline,
       score,
       sum(score) over (partition by discipline order by id) as "current total",
       sum(score) over (partition by discipline)             as "total in discipline"
from athlete
order by id;

-- ARRAY_AGG - aggregate several entry values in a single entry cell
select case when a.gender = 'm' then 'male' else 'female' end as gender,
       array_agg(a.name)                                      as athletes
from athlete a
where a.discipline = (select id from discipline where name = 'Tennis')
group by gender;

-- PARTITION BY separates results by specified value (works only with LAG, LEAD, RANK)
select case when a.gender = 'm' then 'male' else 'female' end as gender,
       array_agg(a.name) over (partition by gender)           as athletes
from athlete a
where a.discipline = (select id from discipline where name = 'Tennis');

-- NTH_VALUE returns value from nth row in result (also exits FIRST_VALUE and LAST_VALUE)
-- ORDER BY in window function and global ORDER BY are different orders and can work together
select name,
       discipline,
       gender,
       score,
       nth_value(name, 1) over (partition by gender, discipline order by score desc) as winner
from athlete
order by id;

-- LEAD takes value from next row
with skiers as (select * from athlete where discipline = 2)
select name                                                      as leader,
       gender,
       score,
       lead(name) over (partition by gender order by score desc) as "next pretendent"
from skiers;

-- LAG takes value from previous row
with skiers as (select * from athlete where discipline = 2)
select name                                                                                as "elimination order",
       gender,
       score,
       coalesce(lag(name) over (partition by gender order by score asc), 'FIRST-IN-QUEUE') as "eliminate after"
from skiers;

-- NTILE divides result set to approximately equal parts
with skiers as (select * from athlete where discipline = 2)
select name, ntile(3) over () as "division number"
from skiers;

-- Результат работы оконной функции невозможно отфильтровать в запросе с помощью WHERE, потому что оконные фунции
-- выполняются после всей фильтрации и группировки, т.е. с тем, что получилось. Поэтому чтобы выбрать, например,
-- лидера в каждой группе, надо использовать подзапрос
select *
from (select name, score, rank() over (partition by discipline, gender order by score desc) as position
      from athlete) as leaderboard
where leaderboard.position = 1;