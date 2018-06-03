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

create table roles (
  id   int not null primary key auto_increment,
  name varchar(100)
)
  engine = InnoDB;

create table management_user (
  id      int not null primary key auto_increment,
  user_id int not null references users (id)
    on update cascade
    on delete cascade,
  role_id int not null references roles (id)
    on update cascade
    on delete cascade
)
  engine = InnoDB;

insert into users (id, email, password, created_by)
values (1, 'engineer.dimmaryanto@outlook.com', 'password', 'migration'),
  (2, 'dimmaryanto@gmail.com', 'password', 'migration');

insert into roles (id, name) values (1, 'ROLE_ADMIN'), (2, 'ROLE_USER'), (3, 'ROLE_OPERATOR');

insert into management_user (user_id, role_id) values (1, 1), (1, 2), (2, 1), (1, 3);