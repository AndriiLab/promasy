CREATE SCHEMA promasy;
SET SCHEMA 'promasy';

/* function modified from
http://rob.conery.io/2014/05/29/a-better-id-generator-for-postgresql */

CREATE SEQUENCE registrations_left INCREMENT BY -1 MINVALUE 0 MAXVALUE 10000 START WITH 100;

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

INSERT INTO roles
(roles_id, roles_name)
VALUES
  (900, 'Адміністратор'),
  (1000, 'Директор'),
  (2000, 'Заступник директора'),
  (2500, 'Голова тендерного комітету'),
  (3000, 'Головний економіст'),
  (4000, 'Головний бухгалтер'),
  (5000, 'Керівник підрозділу'),
  (6000, 'Матеріально-відповідальна особа'),
  (7000, 'Користувач');

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

INSERT INTO institute
(inst_name)
VALUES
  ('Інститут біохімії ім. О.В. Палладіна Національної академії наук України');

CREATE OR REPLACE FUNCTION def_inst_id(OUT result BIGINT) AS $$
BEGIN
  result := (SELECT inst_id
             FROM institute
             WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України');
END;
$$ LANGUAGE PLPGSQL;

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

INSERT INTO departments
(dep_name, inst_id)
VALUES
  ('Дирекція', def_inst_id()),
  ('Бухгалтерія', def_inst_id()),
  ('Відділ постачання', def_inst_id()),
  ('Відділ молекулярної імунології', def_inst_id()),
  ('Відділ біохімії м`язів', def_inst_id()),
  ('Відділ структури та функції білка', def_inst_id()),
  ('Відділ регуляції обміну речовин', def_inst_id()),
  ('Відділ біохімії вітамінів і коензимів', def_inst_id()),
  ('Відділ біохімії ліпідів', def_inst_id()),
  ('Відділ хімії та біохімії ферментів', def_inst_id()),
  ('Відділ нейрохімії', def_inst_id()),
  ('Відділ молекулярної біології', def_inst_id()),
  ('Відділ науково-технічної інформації', def_inst_id()),
  ('Лабораторія сигнальних механізмів клітини', def_inst_id()),
  ('Експлуатаційно-технічний відділ', def_inst_id()),
  ('Відділ техніки безпеки', def_inst_id()),
  ('Адміністративно-господарський відділ', def_inst_id()),
  ('Бібліотека', def_inst_id());

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

INSERT INTO subdepartments
(subdep_name, dep_id)
VALUES
  ('Лабораторія імунобіології', (SELECT dep_id
                                 FROM departments
                                 WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія імунології клітинних рецепторів', (SELECT dep_id
                                                   FROM departments
                                                   WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія нанобіотехнології', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ молекулярної імунології')),
  ('Лабораторія медичної біохімії', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ біохімії вітамінів і коензимів')),
  ('Лабораторія оптичних методів дослідження', (SELECT dep_id
                                                FROM departments
                                                WHERE dep_name = 'Відділ біохімії м`язів')),
  ('Група хроматографії', (SELECT dep_id
                           FROM departments
                           WHERE dep_name = 'Відділ біохімії ліпідів')),
  ('Група електронної мікроскопії', (SELECT dep_id
                                     FROM departments
                                     WHERE dep_name = 'Відділ хімії та біохімії ферментів'));

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

INSERT INTO amountunits
(am_unit_desc)
VALUES
  ('г'),
  ('кг'),
  ('л'),
  ('мл'),
  ('уп.'),
  ('шт.');

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

INSERT INTO producers (brand_id, brand_name, active) VALUES (0, 'Будь-який', FALSE);

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

INSERT INTO suppliers (supplier_id, supplier_name, supplier_tel, active) VALUES (0, 'Будь-який', '', FALSE);

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

-- Причини вибору конкретного постачальника
CREATE TABLE reasons_for_suppl (
  reason_id     BIGINT      NOT NULL DEFAULT id_gen() CONSTRAINT reasons_for_suppl_pk PRIMARY KEY,
  reason_name   VARCHAR(30) NOT NULL, -- Назва причини вибору
  created_by    BIGINT      NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by   BIGINT,
  modified_date TIMESTAMP,
  active        BOOLEAN     NOT NULL DEFAULT TRUE
);

INSERT INTO reasons_for_suppl (reason_id, reason_name, active) VALUES (0, '', FALSE);

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
  order_id      BIGINT         NOT NULL REFERENCES finance (order_id),
  dep_id        BIGINT         NOT NULL REFERENCES departments (dep_id),
  emp_id        BIGINT         NOT NULL REFERENCES employees (emp_id), -- Керівник теми
  order_amount  DECIMAL(19, 4) NOT NULL, -- Об'єм фінансування
  created_by    BIGINT         NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by   BIGINT,
  modified_date TIMESTAMP,
  active        BOOLEAN        NOT NULL DEFAULT TRUE,
  PRIMARY KEY (order_id, dep_id)
);

-- Статус виконання замовлення
CREATE TABLE statuses (
  status_id     INT         NOT NULL CONSTRAINT status_pk PRIMARY KEY,
  status_desc   VARCHAR(50) NOT NULL,
  created_by    BIGINT      NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by   BIGINT,
  modified_date TIMESTAMP,
  active        BOOLEAN     NOT NULL DEFAULT TRUE
);

INSERT INTO statuses
(status_id, status_desc)
VALUES
  (10, 'Створено'),
  (20, 'Подано'),
  (50, 'Розміщено на Prozorro'),
  (60, 'Отримано'),
  (80, 'Не отримано'),
  (90, 'Відхилено');

-- Дані про замовлення
CREATE TABLE bids (
  bid_id        BIGINT         NOT NULL DEFAULT id_gen() CONSTRAINT bids_pk PRIMARY KEY,
  dep_id        BIGINT         NOT NULL REFERENCES departments (dep_id), -- відділ. від якого створили заявку
  brand_id      BIGINT, -- Можливий виробник
  cat_num       VARCHAR(30), -- Можливий каталожний номер
  bid_desc      TEXT           NOT NULL, -- Опис заявки
  cpv_code      VARCHAR(10)    NOT NULL REFERENCES cpv (cpv_code), -- СРВ код
  one_price     DECIMAL(19, 4) NOT NULL, -- Вартість одиниці
  amount        INT            NOT NULL, -- Кількість одиниць
  am_unit_id    BIGINT         NOT NULL REFERENCES amountunits (am_unit_id), -- Розмірність одиниць
  order_id      BIGINT         NOT NULL REFERENCES finance (order_id), -- Номер теми фінансування
  supplier_id   BIGINT, -- Можливий постачальник
  reason_id     BIGINT REFERENCES reasons_for_suppl (reason_id), --Причина вибору постачальника
  created_by    BIGINT         NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by   BIGINT,
  modified_date TIMESTAMP,
  status_id     INT            NOT NULL REFERENCES statuses (status_id),
  active        BOOLEAN        NOT NULL DEFAULT TRUE
);

-- Дані про статус замовлення
CREATE TABLE bid_status (
  id            BIGINT    NOT NULL DEFAULT id_gen() CONSTRAINT bid_status_pk PRIMARY KEY,
  bid_id        BIGINT    NOT NULL REFERENCES bids (bid_id),
  status_id     INT       NOT NULL REFERENCES statuses (status_id),
  created_by    BIGINT    NOT NULL DEFAULT 1000000000000, -- Створено користувачем з ІН
  created_date  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  modified_by   BIGINT,
  modified_date TIMESTAMP,
  active        BOOLEAN   NOT NULL DEFAULT TRUE
);

CREATE TABLE version
(
  version_allowed VARCHAR(50)
);

CREATE OR REPLACE FUNCTION check_login("user" TEXT, pass TEXT)
  RETURNS BOOLEAN AS $$
DECLARE exists BOOLEAN;
BEGIN
  SELECT (password = $2)
  INTO exists
  FROM employees
  WHERE login = $1;

  RETURN exists;
END;
$$ LANGUAGE plpgsql
SECURITY DEFINER
-- Set a secure search_path: trusted schema(s), then 'pg_temp'.
SET search_path = promasy, pg_temp;