CREATE SCHEMA inst_db;
SET SCHEMA 'inst_db';

-- ALTER TABLE institute ADD COLUMN active BOOLEAN NOT NULL DEFAULT true;

/* function modified from
http://rob.conery.io/2014/05/29/a-better-id-generator-for-postgresql */

CREATE SEQUENCE id_seq;

CREATE OR REPLACE FUNCTION id_gen(OUT result BIGINT) AS $$
DECLARE
    our_epoch BIGINT := 1314220021721;
    seq_id BIGINT;
    now_millis BIGINT;
    -- the id of this DB shard, must be set for each
    -- schema shard you have - you could pass this as a parameter too
    shard_id BIGINT := 1;
BEGIN
    SELECT nextval('id_seq') % 1024 INTO seq_id;

    SELECT FLOOR(EXTRACT(EPOCH FROM clock_timestamp()) * 1000) INTO now_millis;
    result := (now_millis - our_epoch) << 23;
    result := result | (shard_id << 10);
    result := result | (seq_id);
END;
$$ LANGUAGE PLPGSQL;

-- Визначення посад та прав доступу
CREATE TABLE roles (
	roles_id INT NOT NULL CONSTRAINT roles_pk PRIMARY KEY,
	roles_name VARCHAR(100) NOT NULL, -- Посада
	created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Дата створення
	modified_by BIGINT, -- Модифіковано користувачем з ІН
	modified_date TIMESTAMP, -- Дата модифікації
	active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Повна назва інституту/-тів. Використовується в документах
CREATE TABLE institute (
	inst_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT institute_pk PRIMARY KEY,
	inst_name VARCHAR(100) NOT NULL, --Повна назва інститутів
	created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
	created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	modified_by BIGINT,
	modified_date TIMESTAMP,
	active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Повні назви відділів. Використовується в документах
CREATE TABLE departments (
	dep_id bigint not null DEFAULT id_gen() CONSTRAINT departments_pk PRIMARY KEY,
	dep_name VARCHAR(100) NOT NULL, --Повна назва відділів
	inst_id BIGINT NOT NULL REFERENCES institute (inst_id),
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Повні назви лабораторій або підвідділів. Використовується в документах
CREATE TABLE subdepartments (
	subdep_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT subdepartments_pk PRIMARY KEY,
	subdep_name VARCHAR(100) NOT NULL, --Повна назва робочих груп
	dep_id BIGINT NOT NULL REFERENCES departments (dep_id),
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

-- cpv коди з розшифровкою на двох мовах
CREATE TABLE cpv (
	cpv_code CHAR(10) NOT NULL CONSTRAINT cpv_pk PRIMARY KEY, -- Код у форматі XXXXX000-Y
	cpv_ukr VARCHAR(200) NOT NULL, -- Розшифровка українською
	cpv_eng VARCHAR(200) NOT NULL, -- Розшифровка англійською
	cpv_level INT NOT NULL, -- Рівень категорії відносно початкової
	terminal BOOLEAN NOT NULL, -- Чи є кінцевою ланкою в категорї
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Одинці, в яких вимірюється замовлення (кг, пакет, шт., л і т.п.)
CREATE TABLE amountunits (
	am_unit_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT amountunits_pk PRIMARY KEY,
	am_unit_desc VARCHAR(100) NOT NULL, -- Одиниця (кг, пакет, шт., л і т.п.)
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Фірми-виробники продукції
CREATE TABLE producers (
	brand_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT producers_pk PRIMARY KEY,
	brand_name VARCHAR(30) NOT NULL, -- Назва виробника
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Фірми-постачальники продукції
CREATE TABLE suppliers (
	supplier_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT suppliers_pk PRIMARY KEY,
	supplier_name VARCHAR(30) NOT NULL, -- Назва постачальника
	supplier_tel VARCHAR(20) NOT NULL, -- Телефон постачальника
	supplier_comments VARCHAR(500), -- Можливі коментарі до постачальника
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

/* Зв'язок між постачальниками та 
виробниками для пропозицій постачальників*/
CREATE TABLE prod_suppliers (
	supplier_id BIGINT NOT NULL REFERENCES suppliers (supplier_id),
	brand_id BIGINT NOT NULL REFERENCES producers (brand_id),
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (supplier_id, brand_id)
);

--  Дані про користувачів
CREATE TABLE employees (
	emp_id BIGINT NOT NULL DEFAULT id_gen() UNIQUE,
	emp_fname VARCHAR(30) NOT NULL, -- Ім'я
	emp_mname VARCHAR(30), -- По-батькові
	emp_lname VARCHAR(30) NOT NULL, -- Прізвище співробітника
	dep_id BIGINT NOT NULL REFERENCES departments (dep_id),
	subdep_id BIGINT,
	roles_id INT NOT NULL REFERENCES roles (roles_id) ON UPDATE CASCADE,
	login varchar(20) NOT NULL UNIQUE,
	password varchar(64) NOT NULL,
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  salt bigint NOT NULL
);

-- Дані про теми та їх фінансування
CREATE TABLE finance (
	order_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT finance_pk PRIMARY KEY,
	order_number INT, -- Номер теми
	order_name VARCHAR(100) NOT NULL, -- Назва теми
	order_amount DECIMAL(19, 4), -- Об'єм фінансування
	starts_on DATE, -- Початок теми
	due_to DATE, -- Кінцева дата теми
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);


-- Дані про відповідність теми та її фінансування відділам
CREATE TABLE finance_dep (
	order_id BIGINT NOT NULL REFERENCES finance(order_id),
  dep_id BIGINT NOT NULL REFERENCES departments (dep_id),
  emp_id BIGINT NOT NULL REFERENCES employees (emp_id), -- Керівник теми
	order_amount DECIMAL(19, 4) NOT NULL, -- Об'єм фінансування
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (order_id, dep_id)
);

-- Дані про замовлення
CREATE TABLE bids (
	bid_id BIGINT NOT NULL DEFAULT id_gen() CONSTRAINT bids_pk PRIMARY KEY,
	dep_id BIGINT NOT NULL REFERENCES departments (dep_id), -- відділ. від якого створили заявку
	brand_id BIGINT, -- Можливий виробник
	cat_num VARCHAR(30), -- Можливий каталожний номер
	bid_desc TEXT NOT NULL, -- Опис заявки
	cpv_code VARCHAR(10) NOT NULL REFERENCES cpv(cpv_code) , -- СРВ код
	one_price DECIMAL(19, 4) NOT NULL, -- Вартість одиниці
	amount INT NOT NULL, -- Кількість одиниць
	am_unit_id BIGINT NOT NULL REFERENCES amountunits(am_unit_id), -- Розмірність одиниць
	order_id BIGINT NOT NULL REFERENCES finance (order_id), -- Номер теми фінансування
	supplier_id BIGINT, -- Можливий постачальник
	received BOOLEAN NOT NULL, -- Чи був отриманий товар складом
	date_received TIMESTAMP, -- Дата отримання складом
  created_by BIGINT NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by BIGINT,
  modified_date TIMESTAMP,
  active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE inst_db.version
(
  version_allowed VARCHAR(50)
);