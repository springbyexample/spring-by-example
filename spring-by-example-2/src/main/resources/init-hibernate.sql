insert into employee (id, first_name, last_name, project) values (1, 'Oleksiy', 'Rezchykov', 'spring-by-example-1');
insert into employee (id, first_name, last_name, project) values (2, 'Eugene', 'Scripnik', 'spring-by-example-1');
insert into project_mates (employee1, employee2) values (1, 2);
insert into project_mates (employee1, employee2) values (2, 1);
insert into user (email, password) values ('oleksiy.rezchykov@gmail.com', 'db25f2fc14cd2d2b1e7af307241f548fb03c312a');