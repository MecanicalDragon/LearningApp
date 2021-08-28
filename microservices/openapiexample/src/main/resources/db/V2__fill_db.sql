insert into UNIT (name, class_type) values ('Oleg', 'WARRIOR');;
insert into UNIT (name, class_type) values ('Viktor', 'WARRIOR');;
insert into UNIT (name, class_type) values ('Aleksandr', 'WARRIOR');;
insert into UNIT (name, class_type) values ('Iruga', 'ROGUE');;
insert into UNIT (name, class_type) values ('Merlin', 'WIZARD');;
insert into UNIT (name, class_type) values ('Dambldor', 'WIZARD');;

insert into SKILL (name, skill_type, duration, power) values ('Punch', 'STRIKE', 0, 5);;
insert into SKILL (name, skill_type, duration, power) values ('Kick', 'STRIKE', 0, 7);;
insert into SKILL (name, skill_type, duration, power) values ('Head hit', 'STRIKE', 0, 3);;

insert into SKILL (name, skill_type, duration, power) values ('Poison', 'PERSISTENT', 3, 2);;
insert into SKILL (name, skill_type, duration, power) values ('Heal', 'STRIKE', 0, 3);;
insert into SKILL (name, skill_type, duration, power) values ('Fireball', 'STRIKE', 0, 10);;
insert into SKILL (name, skill_type, duration, power) values ('Bliss', 'PERSISTENT', 2, 3);;

insert into UNIT_SKILLS values((select id from UNIT where name = 'Oleg'), (select id from SKILL where name = 'Punch'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Oleg'), (select id from SKILL where name = 'Kick'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Viktor'), (select id from SKILL where name = 'Punch'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Viktor'), (select id from SKILL where name = 'Kick'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Viktor'), (select id from SKILL where name = 'Head hit'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Aleksandr'), (select id from SKILL where name = 'Punch'));;

insert into UNIT_SKILLS values((select id from UNIT where name = 'Iruga'), (select id from SKILL where name = 'Poison'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Iruga'), (select id from SKILL where name = 'Punch'));;

insert into UNIT_SKILLS values((select id from UNIT where name = 'Merlin'), (select id from SKILL where name = 'Bliss'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Merlin'), (select id from SKILL where name = 'Fireball'));;

insert into UNIT_SKILLS values((select id from UNIT where name = 'Dambldor'), (select id from SKILL where name = 'Fireball'));;
insert into UNIT_SKILLS values((select id from UNIT where name = 'Dambldor'), (select id from SKILL where name = 'Heal'));;

commit;;

