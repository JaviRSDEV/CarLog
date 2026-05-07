-- auto-generated definition
create table workshop
(
    workshop_id bigint auto_increment
        primary key,
    address     varchar(255) not null,
    icon        varchar(255) null,
    email       varchar(255) null,
    name        varchar(255) not null,
    phone       varchar(255) not null
);

create table users
(
    dni                 varchar(255)                                                not null
        primary key,
    email               varchar(255)                                                not null,
    must_change_psswd   bit                                                         null,
    name                varchar(255)                                                not null,
    password            varchar(255)                                                not null,
    pending_role        enum ('CLIENT', 'CO_MANAGER', 'DIY', 'MANAGER', 'MECHANIC') null,
    phone               varchar(255)                                                null,
    role                enum ('CLIENT', 'CO_MANAGER', 'DIY', 'MANAGER', 'MECHANIC') not null,
    pending_workshop_id bigint                                                      null,
    workshop_id         bigint                                                      null,
    constraint FKjs2jgqpx51qgm2i9800fl372b
        foreign key (pending_workshop_id) references workshop (workshop_id),
    constraint FKsa7003rrc5vwr479ixmkrdkqy
        foreign key (workshop_id) references workshop (workshop_id)
);

-- auto-generated definition
create table vehicles
(
    id                  bigint auto_increment
        primary key,
    brand               varchar(255) null,
    engine              varchar(255) null,
    horsepower          int          null,
    kilometers          bigint       null,
    last_maintenance    date         null,
    model               varchar(255) null,
    plate               varchar(255) not null,
    tires               varchar(255) null,
    torque              int          null,
    owner_id            varchar(255) null,
    pending_workshop_id bigint       null,
    workshop_id         bigint       null,
    constraint UKlle7kf4cbmwh6twthj1tik9us
        unique (plate),
    constraint FK7x977sy79cs22becdc8tn9gg2
        foreign key (workshop_id) references workshop (workshop_id),
    constraint FKi251m7sfpn4k64pif7wk70jlu
        foreign key (owner_id) references users (dni),
    constraint FKnviyuyx1r8pb5qu08l0tpt1hl
        foreign key (pending_workshop_id) references workshop (workshop_id)
);

-- auto-generated definition
create table vehicle_images
(
    vehicle_id bigint       not null,
    image      varchar(255) null,
    constraint FKp6gw8mt61ktmsk5nuc4qid7i8
        foreign key (vehicle_id) references vehicles (id)
);



-- auto-generated definition
create table work_order
(
    id             bigint auto_increment
        primary key,
    closed_at      date                                         null,
    created_at     datetime(6)                                  null,
    description    text                                         null,
    mechanic_notes text                                         null,
    status         enum ('COMPLETED', 'IN_PROGRESS', 'PENDING') null,
    total_amount   double                                       null,
    mechanic_id    varchar(255)                                 not null,
    vehicle_id     bigint                                       not null,
    workshop_id    bigint                                       not null,
    constraint FKglfk5g4gqq4msnlj7m2dba7j9
        foreign key (workshop_id) references workshop (workshop_id),
    constraint FKkv9i5s1j5jd6mxon26d284fd9
        foreign key (vehicle_id) references vehicles (id),
    constraint FKos2g7ck5x27igyouq97tk8hgd
        foreign key (mechanic_id) references users (dni)
);

-- auto-generated definition
create table work_order_line
(
    id             bigint auto_increment
        primary key,
    iva            double       not null,
    concept        varchar(255) not null,
    discount       double       not null,
    price_per_unit double       not null,
    quantity       double       not null,
    sub_total      double       null,
    work_order_id  bigint       not null,
    constraint FKhmdbqxnfcwggq69bckqadkj0r
        foreign key (work_order_id) references work_order (id)
);

CREATE TABLE car_brands (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE car_models (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            brand_id BIGINT NOT NULL,
                            CONSTRAINT fk_model_brand FOREIGN KEY (brand_id) REFERENCES car_brands(id) ON DELETE CASCADE
);




