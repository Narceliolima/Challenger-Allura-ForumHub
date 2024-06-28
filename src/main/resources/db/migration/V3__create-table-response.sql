create table responses(

    id bigint not null auto_increment,
    message varchar(500) not null,
    user_id bigint not null,
    topic_id bigint not null,
    
    primary key(id),
    constraint fk_responses_topic_id foreign key(topic_id) references topics(id),
    constraint fk_responses_user_id foreign key(user_id) references users(id)

);