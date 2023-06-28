create schema authone;
create table account
(
    document_id    bigint,
    id             bigint       not null,
    codice_fiscale varchar(255) not null,
    is_enabled     enum ('ACTIVE','DISABLED'),
    primary key (id)
) engine=InnoDB;
create table account_seq
(
    next_val bigint
) engine=InnoDB;
insert into account_seq
values (1);
create table credentials
(
    id       bigint not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;
create table credentials_seq
(
    next_val bigint
) engine=InnoDB;
insert into credentials_seq
values (1);
create table document
(
    id   bigint       not null,
    name varchar(255) not null,
    type varchar(255) not null,
    data tinyblob     not null,
    primary key (id)
) engine=InnoDB;
create table document_seq
(
    next_val bigint
) engine=InnoDB;
insert into document_seq
values (1);
create table user
(
    credentials_id    bigint,
    id                bigint       not null,
    codice_fiscale    varchar(255),
    name              varchar(255) not null,
    notification_mode enum ('EMAIL','SLACK','SMS','WHATSAPP'),
    surname           varchar(255) not null,
    primary key (id)
) engine=InnoDB;
create table user_seq
(
    next_val bigint
) engine=InnoDB;
insert into user_seq
values (1);
alter table account
    add constraint UK_lndgmis8aw377kgp4mtxg4yfy unique (document_id);
alter table account
    add constraint UK_9w92h2it09je19xu13y15kvca unique (codice_fiscale);
alter table credentials
    add constraint UK_l7xhygibdj6cgkpj2ih1jgx14 unique (username);
alter table user
    add constraint UK_7ng0gylcnfxjp4irba3kc37xy unique (credentials_id);
alter table account
    add constraint FKkcyartndld9o9d2980v2bljrk foreign key (document_id) references document (id);
alter table account
    add constraint FKl4p654h8prcxdof9ery25ffxi foreign key (codice_fiscale) references user (codice_fiscale);
alter table user
    add constraint FKh0bgjrro5cx7mfvykky2138na foreign key (credentials_id) references credentials (id);
create table account
(
    document_id    bigint,
    id             bigint       not null,
    codice_fiscale varchar(255) not null,
    is_enabled     enum ('ACTIVE','DISABLED'),
    primary key (id)
) engine=InnoDB;
create table account_seq
(
    next_val bigint
) engine=InnoDB;
insert into account_seq
values (1);
create table credentials
(
    id       bigint not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;
create table credentials_seq
(
    next_val bigint
) engine=InnoDB;
insert into credentials_seq
values (1);
create table document
(
    id   bigint       not null,
    name varchar(255) not null,
    type varchar(255) not null,
    data tinyblob     not null,
    primary key (id)
) engine=InnoDB;
create table document_seq
(
    next_val bigint
) engine=InnoDB;
insert into document_seq
values (1);
create table user
(
    credentials_id    bigint,
    id                bigint       not null,
    codice_fiscale    varchar(255),
    name              varchar(255) not null,
    notification_mode enum ('EMAIL','SLACK','SMS','WHATSAPP'),
    surname           varchar(255) not null,
    primary key (id)
) engine=InnoDB;
create table user_seq
(
    next_val bigint
) engine=InnoDB;
insert into user_seq
values (1);
alter table account
    add constraint UK_lndgmis8aw377kgp4mtxg4yfy unique (document_id);
alter table account
    add constraint UK_9w92h2it09je19xu13y15kvca unique (codice_fiscale);
alter table credentials
    add constraint UK_l7xhygibdj6cgkpj2ih1jgx14 unique (username);
alter table user
    add constraint UK_7ng0gylcnfxjp4irba3kc37xy unique (credentials_id);
alter table account
    add constraint FKkcyartndld9o9d2980v2bljrk foreign key (document_id) references document (id);
alter table account
    add constraint FKl4p654h8prcxdof9ery25ffxi foreign key (codice_fiscale) references user (codice_fiscale);
alter table user
    add constraint FKh0bgjrro5cx7mfvykky2138na foreign key (credentials_id) references credentials (id);
