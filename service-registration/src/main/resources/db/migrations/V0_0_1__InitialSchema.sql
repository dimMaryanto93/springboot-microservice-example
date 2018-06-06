create table registration (
  id           int          not null primary key auto_increment,
  project_name varchar(100) not null,
  user_id      int
);