CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company_id(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values (1, 'Apple'), (2, 'Samsung'), (3, 'Google'), (4, 'Facebook'), (5, 'Tesla');
insert into person(id, name, company_id) values (1, 'qqq', 1), (2, 'www', 2), (3, 'eee', 3), (4, 'rrr', 4), (5, 'ttt', 4), (6, 'yyy', 5);

select c.name , p.name from company as c left join person p on c.id = p.company_id where c.id != 5;
select  c.name , count(p.id) as number from company as c left join person p on c.id = p.company_id group by c.id order by number desc LIMIT 1;