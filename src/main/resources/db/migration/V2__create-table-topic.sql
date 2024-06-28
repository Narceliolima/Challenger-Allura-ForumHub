create table topics(

    id bigint not null auto_increment,
    title varchar(100) not null unique,
    message varchar(500) not null,
    creation_date datetime not null,
    user_id bigint not null,
    course varchar(100) not null,
    status varchar(100) not null,
    
    primary key(id),
    constraint fk_topics_user_id foreign key(user_id) references users(id)

);