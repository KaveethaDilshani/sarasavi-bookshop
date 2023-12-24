create database sarasavi;

use sarasavi;

create table Customer
(
    C_id           varchar(5)  not null
        primary key,
    C_name         varchar(25) null,
    C_address      varchar(50) null,
    Contact_number int         null
);
create table Employee
(
    E_name         varchar(25) not null
        primary key,
    E_id           varchar(25) null,
    E_address      varchar(50) null,
    Contact_number int         null
);
create table Items
(
    I_name   varchar(25)    null,
    I_id     int            not null
        primary key,
    price    decimal(10, 2) null,
    category varchar(25)    null,
    qty      int            null
);
create table Orders
(
    O_id   int         not null
        primary key,
    C_id   varchar(5)  null,
    O_Date date        null,
    type   varchar(10) null,
    total  double      null,
    constraint Orders_ibfk_1
        foreign key (C_id) references Customer (C_id)
            on update cascade on delete cascade
);
create index C_id
    on Orders (C_id);
create table Supplier
(
    S_id           int         not null
        primary key,
    S_name         varchar(25) null,
    S_address      varchar(50) null,
    Contact_number int         null
);
create table user
(
    name     varchar(20) not null,
    password varchar(30) null
);
create table orderDetail
(
    I_id      int null,
    O_id      int null,
    Quantity  int null,
    UnitPrice int null,
    constraint orderDetail_ibfk_1
        foreign key (O_id) references Orders (O_id)
            on update cascade on delete cascade,
    constraint orderDetail_ibfk_2
        foreign key (I_id) references Items (I_id)
            on update cascade on delete cascade
);
create index I_id
    on orderDetail (I_id);
create index O_id
    on orderDetail (O_id);

create table itemDetail
(
    S_id int null,
    I_id int null,
    constraint itemDetail_ibfk_1
        foreign key (S_id) references Supplier (S_id)
            on update cascade on delete cascade,
    constraint itemDetail_ibfk_2
        foreign key (I_id) references Items (I_id)
            on update cascade on delete cascade
);

create index I_id
    on itemDetail (I_id);

create index S_id
    on itemDetail (S_id);

create table Income
(
    IN_name varchar(25) null,
    IN_id   int         not null
        primary key,
    Amount  int         null,
    type    varchar(15) null
);

create table Attendes
(
    Id            int auto_increment
        primary key,
    Date          date        null,
    working_hours varchar(20) null,
    Ot_hours      varchar(20) null,
    employeeid    varchar(25) null,
    constraint Attendes_Employee_E_name_fk
        foreign key (employeeid) references Employee (E_name)
            on update cascade on delete cascade
);
