create schema inst_db;
SET SCHEMA 'inst_db';

-- ALTER TABLE institute ADD COLUMN active BOOLEAN NOT NULL DEFAULT true;

/* function modified from
http://rob.conery.io/2014/05/29/a-better-id-generator-for-postgresql */

create sequence id_seq;

CREATE OR REPLACE FUNCTION id_gen(OUT result bigint) AS $$
DECLARE
    our_epoch bigint := 1314220021721;
    seq_id bigint;
    now_millis bigint;
    -- the id of this DB shard, must be set for each
    -- schema shard you have - you could pass this as a parameter too
    shard_id bigint := 1;
BEGIN
    SELECT nextval('id_seq') % 1024 INTO seq_id;

    SELECT FLOOR(EXTRACT(EPOCH FROM clock_timestamp()) * 1000) INTO now_millis;
    result := (now_millis - our_epoch) << 23;
    result := result | (shard_id << 10);
    result := result | (seq_id);
END;
$$ LANGUAGE PLPGSQL;

-- Визначення посад та прав доступу
create table roles (
	roles_id bigint not null DEFAULT id_gen() CONSTRAINT roles_pk PRIMARY KEY,
	roles_name varchar(100) not null, -- Посада
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Повна назва інституту/-тів. Використовується в документах
create table institute (
	inst_id bigint not null DEFAULT id_gen() CONSTRAINT institute_pk PRIMARY KEY,
	inst_name varchar(100) not null, --Повна назва інститутів
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
	modified_by bigint,
	modified_date TIMESTAMP,
	active boolean not null DEFAULT true
);

-- Повні назви відділів. Використовується в документах
create table departments (
	dep_id bigint not null DEFAULT id_gen() CONSTRAINT departments_pk PRIMARY KEY,
	dep_name varchar(100) not null, --Повна назва відділів
	inst_id bigint not null REFERENCES institute (inst_id),
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Повні назви лабораторій або підвідділів. Використовується в документах
create table subdepartments (
	subdep_id bigint not null DEFAULT id_gen() CONSTRAINT subdepartments_pk PRIMARY KEY,
	subdep_name varchar(100) not null, --Повна назва робочих груп
	dep_id bigint not null references departments (dep_id),
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- cpv коди з розшифровкою на двох мовах
create table cpv (
	cpv_code char(10) not null CONSTRAINT cpv_pk PRIMARY KEY, -- Код у форматі XXXXX000-Y
	cpv_ukr varchar(200) not null, -- Розшифровка українською
	cpv_eng varchar(200) not null, -- Розшифровка англійською
	cpv_level int not null, -- Рівень категорії відносно початкової
	terminal boolean not null, -- Чи є кінцевою ланкою в категорї
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Одинці, в яких вимірюється замовлення (кг, пакет, шт., л і т.п.)
create table amountunits (
	am_unit_id bigint not null DEFAULT id_gen() CONSTRAINT amountunits_pk PRIMARY KEY,
	am_unit_desc varchar(100) not null, -- Одиниця (кг, пакет, шт., л і т.п.)
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Фірми-виробники продукції
create table producers (
	brand_id bigint not null DEFAULT id_gen() CONSTRAINT producers_pk PRIMARY KEY,
	brand_name varchar(30) not null, -- Назва виробника
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Фірми-постачальники продукції
create table suppliers (
	supplier_id bigint not null DEFAULT id_gen() CONSTRAINT suppliers_pk PRIMARY KEY,
	supplier_name varchar(30) not null, -- Назва постачальника
	supplier_tel varchar(20) not null, -- Телефон постачальника
	supplier_comments varchar(500), -- Можливі коментарі до постачальника
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

/* Зв'язок між постачальниками та 
виробниками для пропозицій постачальників*/
create table prod_suppliers (
	supplier_id bigint not null references suppliers (supplier_id),
	brand_id bigint not null references producers (brand_id),
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true,
	PRIMARY KEY (supplier_id, brand_id)
);

--  Дані про користувачів
create table employees (
	emp_id bigint not null DEFAULT id_gen(),
	emp_fname varchar(30) not null, -- Ім'я
	emp_mname varchar(30), -- По-батькові
	emp_lname varchar(30) not null, -- Прізвище співробітника
	dep_id bigint not null references departments (dep_id),
	subdep_id bigint,
	roles_id bigint not null references roles (roles_id),
	login varchar(20) not null,
	password varchar(20) not null,
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true,
	PRIMARY KEY (emp_id, login)
);

-- Дані про теми та їх фінансування
create table finance (
	tema_id bigint not null DEFAULT id_gen() CONSTRAINT finance_pk PRIMARY KEY,
	tema_number varchar(30), -- Номер теми
	tema_name varchar(100) not null, -- Назва теми
	tema_amount money not null, -- Об'єм фінансування
	dep_id bigint not null references departments (dep_id),
	emp_id bigint not null references employees (emp_id), -- Керівник теми
	starts_on date, -- Початок теми
	due_to date, -- Кінцева дата теми
	created_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);

-- Дані про замовлення
create table bids (
	bid_id bigint not null DEFAULT id_gen() CONSTRAINT bids_pk PRIMARY KEY,
	emp_id bigint not null references employees (emp_id), -- ІН, хто створив заявку
	brand_id bigint references producers (brand_id), -- Можливий виробник
	cat_num varchar(30), -- Можливий каталожний номер
	bid_desc varchar(500) not null, -- Опис заявки
	cpv_code varchar(10) not null references cpv(cpv_code) , -- СРВ код
	one_price money not null, -- Вартість одиниці
	amount INT not null, -- Кількість одиниць
	am_unit_id bigint not null references amountunits(am_unit_id), -- Розмірність одиниць
	tema_id bigint not null references finance (tema_id), -- Номер теми фінансування
	supplier_id bigint references suppliers (supplier_id), -- Можливий постачальник
	received boolean not null, -- Чи був отриманий товар складом
	date_received TIMESTAMP, -- Дата отримання складом
	ccreated_by bigint not null DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by bigint, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active boolean not null DEFAULT true
);