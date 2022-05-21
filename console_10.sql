create table supply_center
(
    id   serial primary key,
    name varchar(45)
);

create table staff
(
    id   serial primary key,
    name          varchar(30),
    age           int,
    gender        varchar(30),
    number        int,
    supply_center varchar(45),
    mobile_number char(11),
    type          varchar(20)
);

create table enterprise
(
    id   serial primary key,
    name          varchar(60),
    country       varchar(35),
    city          varchar(15),
    supply_center varchar(45),
    industry      varchar(40)
);

create table model
(
    id   serial primary key,
    number     char(7),
    model      varchar(100),--产品的型号
    name       varchar(100),--产品名
    unit_price int
);

create table contract
(
    id   serial primary key,
    contract_num            char(10) unique, --合同的编号
    enterprise              varchar(60),
    contract_manager        int,      --经理的number
    contract_date           date,
    estimated_delivery_date date,
    lodgement_date          date,
    contract_type           varchar(20)
);


create table orders
(
    id   serial primary key,
    contract_num            char(10),     --合同的编号
    enterprise              varchar(60),
    product_model           varchar(100), --model
    quantity                int,
    contract_manager        int,          --经理的number
    contract_date           date,
    estimated_delivery_date date,
    lodgement_date          date,
    salesman_num            int,          --售货员的number
    contract_type           varchar(20)
);

create table inventory
(
    id   serial primary key,
    supply_center  varchar(45),
    product_model  varchar(100),
    supply_staff   int,--职工编号number
    date           date,--入库时间
    purchase_price int,
    quantity       int,--存当前库存数量
    sales          int--存累计销量
);