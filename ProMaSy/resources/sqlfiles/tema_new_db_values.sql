SET SCHEMA 'inst_db';

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

INSERT INTO institute
(inst_name)
VALUES
('Інститут біохімії ім. О.В. Палладіна Національної академії наук України');

SET SCHEMA 'inst_db';
CREATE OR REPLACE FUNCTION check_login ("user" TEXT, pass TEXT) RETURNS BOOLEAN AS $$
DECLARE exists BOOLEAN;
BEGIN
	SELECT (password = $2) INTO exists
	FROM employees
	WHERE login = $1;

	RETURN exists;
END;
$$  LANGUAGE plpgsql
    SECURITY DEFINER
    -- Set a secure search_path: trusted schema(s), then 'pg_temp'.
    SET search_path = inst_db, pg_temp;



CREATE OR REPLACE FUNCTION def_inst_id(OUT result bigint) AS $$
BEGIN
result := (SELECT inst_id FROM institute 
	WHERE inst_name = 'Інститут біохімії ім. О.В. Палладіна Національної академії наук України');
END;
$$ LANGUAGE PLPGSQL;

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

INSERT INTO subdepartments
(subdep_name, dep_id)
VALUES
('Лабораторія імунобіології', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ молекулярної імунології')),
('Лабораторія імунології клітинних рецепторів', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ молекулярної імунології')),
('Лабораторія нанобіотехнології', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ молекулярної імунології')),
('Лабораторія медичної біохімії', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ біохімії вітамінів і коензимів')),
('Лабораторія оптичних методів дослідження', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ біохімії м`язів')),
('Група хроматографії', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ біохімії ліпідів')),
('Група електронної мікроскопії', (SELECT dep_id FROM departments
	WHERE dep_name = 'Відділ хімії та біохімії ферментів'));

INSERT INTO amountunits
(am_unit_desc)
VALUES
('г'),
('кг'),
('л'),
('мл'),
('уп.'),
('шт.');

