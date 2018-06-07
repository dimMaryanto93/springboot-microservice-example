create table users (
  id                int          not null primary key auto_increment,
  email             varchar(100) not null unique,
  password          varchar(150) not null,
  is_enabled        boolean      not null             default false,
  created_date      datetime     not null             default now(),
  created_by        varchar(100) not null,
  last_updated_date datetime,
  last_updated_by   varchar(100)
)
  engine = InnoDB;

create table divisions (
  id   int          not null primary key auto_increment,
  name varchar(100) not null unique
)
  engine = InnoDB;

create table roles (
  id   int not null primary key auto_increment,
  name varchar(100)
)
  engine = InnoDB;

create table user_division (
  id          int not null primary key auto_increment,
  user_id     int not null,
  division_id int not null
)
  engine = InnoDB;

alter table user_division
  add constraint fk_user_id foreign key (user_id) references users (id)
  on update cascade
  on delete cascade;

alter table user_division
  add constraint fk_division_id foreign key (division_id) references divisions (id)
  on update cascade
  on delete cascade;

create table user_division_roles (
  id        int not null primary key auto_increment,
  divisi_id int not null,
  role_id   int not null
)
  engine = InnoDB;

alter table user_division_roles
  add constraint fk_divisi_Id foreign key (divisi_id) references divisions (id)
  on update cascade
  on delete cascade;

alter table user_division_roles
  add constraint fk_role_id foreign key (role_id) references roles (id)
  on update cascade
  on delete cascade;

insert into users (id, email, password, is_enabled, created_by)
values (1, 'admin', 'admin', true, 'migration'), (2, 'user', 'user', true, 'migration');

insert into roles (id, name)
values (1, 'ROLE_ADMIN1'), (2, 'ROLE_ADMIN2'), (3, 'ROLE_ADMIN3'), (4, 'ROLE_USER1'), (5, 'ROLE_USER2');

insert into divisions (id, name) values (1, 'admin_group'), (2, 'user_group');

insert into user_division (user_id, division_id) values (1, 1), (1, 2), (2, 1);

insert into user_division_roles (role_id, divisi_id) values (1, 1), (2, 1), (3, 1), (4, 2), (5, 2);