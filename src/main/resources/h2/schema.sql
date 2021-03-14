drop table announcement if exists;

create table announcement (
    id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    content TEXT not null,
    title varchar(500) not null,
    writer varchar(255),
    primary key (id)
) engine=InnoDB;