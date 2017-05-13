--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.0
-- Dumped by pg_dump version 9.6.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: promasy; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS promasy;


ALTER SCHEMA promasy OWNER TO postgres;

SET search_path = promasy, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: addresses; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE addresses (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    building_number character varying(255),
    city character varying(255),
    citytype character varying(255),
    corpus_number character varying(255),
    country character varying(255),
    postal_code character varying(255),
    region character varying(255),
    street character varying(255),
    streettype character varying(255)
);


ALTER TABLE addresses OWNER TO postgres;

--
-- Name: amount_units; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE amount_units (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    amount_unit_desc character varying(255)
);


ALTER TABLE amount_units OWNER TO postgres;

--
-- Name: bid_statuses; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE bid_statuses (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    status character varying(255),
    bid_id bigint
);


ALTER TABLE bid_statuses OWNER TO postgres;

--
-- Name: bids; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE bids (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    amount integer,
    bid_desc character varying(255),
    cat_num character varying(255),
    kekv integer,
    one_price numeric(19,2),
    proc_start_date date,
    type character varying(255),
    am_unit_id bigint,
    cpv_code character varying(255),
    finance_dep_id bigint,
    producer_id bigint,
    reason_id bigint,
    supplier_id bigint
);


ALTER TABLE bids OWNER TO postgres;

--
-- Name: cpv; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE cpv (
    cpv_code character varying(255) NOT NULL,
    cpv_eng character varying(255),
    cpv_level integer,
    terminal boolean,
    cpv_ukr character varying(255)
);


ALTER TABLE cpv OWNER TO postgres;

--
-- Name: departments; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE departments (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    dep_name character varying(255),
    inst_id bigint
);


ALTER TABLE departments OWNER TO postgres;

--
-- Name: employees; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE employees (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    email character varying(255),
    emp_fname character varying(255),
    emp_lname character varying(255),
    emp_mname character varying(255),
    login character varying(255),
    password character varying(255),
    phone_main character varying(255),
    phone_reserve character varying(255),
    role character varying(255),
    salt bigint,
    subdep_id bigint
);


ALTER TABLE employees OWNER TO postgres;

--
-- Name: finance_dep; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE finance_dep (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    total_eqipment numeric(19,2),
    total_materials numeric(19,2),
    total_services numeric(19,2),
    finance_id bigint,
    subdep_id bigint
);


ALTER TABLE finance_dep OWNER TO postgres;

--
-- Name: finances; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE finances (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    due_to date,
    name character varying(255),
    number integer,
    fundtype character varying(255),
    kpkvk integer,
    starts_on date,
    total_equpment numeric(19,2),
    total_materials numeric(19,2),
    total_services numeric(19,2)
);


ALTER TABLE finances OWNER TO postgres;

--
-- Name: hilo_seqeunce; Type: SEQUENCE; Schema: promasy; Owner: postgres
--

CREATE SEQUENCE hilo_seqeunce
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hilo_seqeunce OWNER TO postgres;

--
-- Name: institutes; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE institutes (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    email character varying(255),
    edrpou integer,
    fax_number character varying(255),
    inst_name character varying(255),
    phone_number character varying(255),
    address_id bigint
);


ALTER TABLE institutes OWNER TO postgres;

--
-- Name: producers; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE producers (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    brand_name character varying(255)
);


ALTER TABLE producers OWNER TO postgres;

--
-- Name: reasons_for_suppl; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE reasons_for_suppl (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    reason_name character varying(255)
);


ALTER TABLE reasons_for_suppl OWNER TO postgres;

--
-- Name: registrations; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE registrations (
    id integer NOT NULL,
    registrations_left integer
);


ALTER TABLE registrations OWNER TO postgres;

--
-- Name: subdepartments; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE subdepartments (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    subdep_name character varying(255),
    dep_id bigint
);


ALTER TABLE subdepartments OWNER TO postgres;

--
-- Name: suppliers; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE suppliers (
    id bigint NOT NULL,
    active boolean,
    created_date timestamp without time zone,
    modified_date timestamp without time zone,
    version bigint NOT NULL,
    created_by bigint,
    modified_by bigint,
    supplier_comments character varying(255),
    supplier_name character varying(255),
    supplier_tel character varying(255)
);


ALTER TABLE suppliers OWNER TO postgres;

--
-- Name: version; Type: TABLE; Schema: promasy; Owner: postgres
--

CREATE TABLE version (
    id integer NOT NULL,
    version_allowed character varying(255)
);


ALTER TABLE version OWNER TO postgres;

--
-- Data for Name: addresses; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY addresses (id, active, created_date, modified_date, version, created_by, modified_by, building_number, city, citytype, corpus_number, country, postal_code, region, street, streettype) FROM stdin;
\.


--
-- Data for Name: amount_units; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY amount_units (id, active, created_date, modified_date, version, created_by, modified_by, amount_unit_desc) FROM stdin;
\.


--
-- Data for Name: bid_statuses; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY bid_statuses (id, active, created_date, modified_date, version, created_by, modified_by, status, bid_id) FROM stdin;
\.


--
-- Data for Name: bids; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY bids (id, active, created_date, modified_date, version, created_by, modified_by, amount, bid_desc, cat_num, kekv, one_price, proc_start_date, type, am_unit_id, cpv_code, finance_dep_id, producer_id, reason_id, supplier_id) FROM stdin;
\.


--
-- Data for Name: cpv; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY cpv (cpv_code, cpv_eng, cpv_level, terminal, cpv_ukr) FROM stdin;
\.


--
-- Data for Name: departments; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY departments (id, active, created_date, modified_date, version, created_by, modified_by, dep_name, inst_id) FROM stdin;
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY employees (id, active, created_date, modified_date, version, created_by, modified_by, email, emp_fname, emp_lname, emp_mname, login, password, phone_main, phone_reserve, role, salt, subdep_id) FROM stdin;
\.


--
-- Data for Name: finance_dep; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY finance_dep (id, active, created_date, modified_date, version, created_by, modified_by, total_eqipment, total_materials, total_services, finance_id, subdep_id) FROM stdin;
\.


--
-- Data for Name: finances; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY finances (id, active, created_date, modified_date, version, created_by, modified_by, due_to, name, number, fundtype, kpkvk, starts_on, total_equpment, total_materials, total_services) FROM stdin;
\.


--
-- Name: hilo_seqeunce; Type: SEQUENCE SET; Schema: promasy; Owner: postgres
--

SELECT pg_catalog.setval('hilo_seqeunce', 1, false);


--
-- Data for Name: institutes; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY institutes (id, active, created_date, modified_date, version, created_by, modified_by, email, edrpou, fax_number, inst_name, phone_number, address_id) FROM stdin;
\.


--
-- Data for Name: producers; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY producers (id, active, created_date, modified_date, version, created_by, modified_by, brand_name) FROM stdin;
\.


--
-- Data for Name: reasons_for_suppl; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY reasons_for_suppl (id, active, created_date, modified_date, version, created_by, modified_by, reason_name) FROM stdin;
\.


--
-- Data for Name: registrations; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY registrations (id, registrations_left) FROM stdin;
\.


--
-- Data for Name: subdepartments; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY subdepartments (id, active, created_date, modified_date, version, created_by, modified_by, subdep_name, dep_id) FROM stdin;
\.


--
-- Data for Name: suppliers; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY suppliers (id, active, created_date, modified_date, version, created_by, modified_by, supplier_comments, supplier_name, supplier_tel) FROM stdin;
\.


--
-- Data for Name: version; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY version (id, version_allowed) FROM stdin;
1	0.8.0
\.


--
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (id);


--
-- Name: amount_units amount_units_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY amount_units
    ADD CONSTRAINT amount_units_pkey PRIMARY KEY (id);


--
-- Name: bid_statuses bid_statuses_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bid_statuses
    ADD CONSTRAINT bid_statuses_pkey PRIMARY KEY (id);


--
-- Name: bids bids_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT bids_pkey PRIMARY KEY (id);


--
-- Name: cpv cpv_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY cpv
    ADD CONSTRAINT cpv_pkey PRIMARY KEY (cpv_code);


--
-- Name: departments departments_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: finance_dep finance_dep_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finance_dep
    ADD CONSTRAINT finance_dep_pkey PRIMARY KEY (id);


--
-- Name: finances finances_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finances
    ADD CONSTRAINT finances_pkey PRIMARY KEY (id);


--
-- Name: institutes institutes_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY institutes
    ADD CONSTRAINT institutes_pkey PRIMARY KEY (id);


--
-- Name: producers producers_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY producers
    ADD CONSTRAINT producers_pkey PRIMARY KEY (id);


--
-- Name: reasons_for_suppl reasons_for_suppl_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY reasons_for_suppl
    ADD CONSTRAINT reasons_for_suppl_pkey PRIMARY KEY (id);


--
-- Name: registrations registrations_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY registrations
    ADD CONSTRAINT registrations_pkey PRIMARY KEY (id);


--
-- Name: subdepartments subdepartments_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY subdepartments
    ADD CONSTRAINT subdepartments_pkey PRIMARY KEY (id);


--
-- Name: suppliers suppliers_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY suppliers
    ADD CONSTRAINT suppliers_pkey PRIMARY KEY (id);


--
-- Name: version version_pkey; Type: CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY version
    ADD CONSTRAINT version_pkey PRIMARY KEY (id);


--
-- Name: bids fk3vwxs2blly2fmoapleqlajw81; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fk3vwxs2blly2fmoapleqlajw81 FOREIGN KEY (reason_id) REFERENCES reasons_for_suppl(id);


--
-- Name: employees fk57hc214n1yj124owgi2r897ld; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT fk57hc214n1yj124owgi2r897ld FOREIGN KEY (subdep_id) REFERENCES subdepartments(id);


--
-- Name: institutes fk80nh0w61wfukjr3qxhqikot9q; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY institutes
    ADD CONSTRAINT fk80nh0w61wfukjr3qxhqikot9q FOREIGN KEY (address_id) REFERENCES addresses(id);


--
-- Name: bids fk84ecql7gud3ljmru094ousih7; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fk84ecql7gud3ljmru094ousih7 FOREIGN KEY (supplier_id) REFERENCES suppliers(id);


--
-- Name: finance_dep fk9rtcqbbw7610hlr4f139fac70; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finance_dep
    ADD CONSTRAINT fk9rtcqbbw7610hlr4f139fac70 FOREIGN KEY (subdep_id) REFERENCES subdepartments(id);


--
-- Name: addresses fk_344u93h6cd8jq5x5ckk4hu9f0; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_344u93h6cd8jq5x5ckk4hu9f0 FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: addresses fk_405w0w2rck8317hsoyg3w0flm; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY addresses
    ADD CONSTRAINT fk_405w0w2rck8317hsoyg3w0flm FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: finance_dep fk_5u2k74wihjg53vssj5j6a31jo; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finance_dep
    ADD CONSTRAINT fk_5u2k74wihjg53vssj5j6a31jo FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: bids fk_5wt5tqrim7hrkjlfpnoaa4kwt; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fk_5wt5tqrim7hrkjlfpnoaa4kwt FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: suppliers fk_8c6wn7rus6efwx1i1iv6fh2vo; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY suppliers
    ADD CONSTRAINT fk_8c6wn7rus6efwx1i1iv6fh2vo FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: subdepartments fk_8uvr7ivg6inlao9bxte7uj3sm; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY subdepartments
    ADD CONSTRAINT fk_8uvr7ivg6inlao9bxte7uj3sm FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: finances fk_9dq3rpet3cb8ltnarktkxik04; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finances
    ADD CONSTRAINT fk_9dq3rpet3cb8ltnarktkxik04 FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: employees fk_9m5i22mnb9g9twtgw7c7b69pl; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT fk_9m5i22mnb9g9twtgw7c7b69pl FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: reasons_for_suppl fk_bj9ff672tjt7de6xklmuqdogd; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY reasons_for_suppl
    ADD CONSTRAINT fk_bj9ff672tjt7de6xklmuqdogd FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: institutes fk_crs5g4jqdxfajyqtqn6ip5ll8; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY institutes
    ADD CONSTRAINT fk_crs5g4jqdxfajyqtqn6ip5ll8 FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: reasons_for_suppl fk_e4eplnsbbs7nkirc239g9ortu; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY reasons_for_suppl
    ADD CONSTRAINT fk_e4eplnsbbs7nkirc239g9ortu FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: subdepartments fk_er03pwih7shyvcoatxu0untvf; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY subdepartments
    ADD CONSTRAINT fk_er03pwih7shyvcoatxu0untvf FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: amount_units fk_gfqwxy9v5jdcxp5ldekofktxj; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY amount_units
    ADD CONSTRAINT fk_gfqwxy9v5jdcxp5ldekofktxj FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: finances fk_hrx0g6s871374bq150mjl6lul; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finances
    ADD CONSTRAINT fk_hrx0g6s871374bq150mjl6lul FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: suppliers fk_hwq1en4ch8qvphsxf9lwej0fg; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY suppliers
    ADD CONSTRAINT fk_hwq1en4ch8qvphsxf9lwej0fg FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: institutes fk_irv2623eqwi2icti2ebqmfnth; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY institutes
    ADD CONSTRAINT fk_irv2623eqwi2icti2ebqmfnth FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: finance_dep fk_kcvkj7rnc090xavcgxed3wu13; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finance_dep
    ADD CONSTRAINT fk_kcvkj7rnc090xavcgxed3wu13 FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: producers fk_ku4mlwdnyx7kwbmymvmusspcf; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY producers
    ADD CONSTRAINT fk_ku4mlwdnyx7kwbmymvmusspcf FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: employees fk_l30in7eku2ecbukdy7mkg08i9; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT fk_l30in7eku2ecbukdy7mkg08i9 FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: departments fk_lesoxdijnp38bjbjw904wu43v; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT fk_lesoxdijnp38bjbjw904wu43v FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: amount_units fk_ll8tf0b5oltd31ohhtpobc32e; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY amount_units
    ADD CONSTRAINT fk_ll8tf0b5oltd31ohhtpobc32e FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: producers fk_ownfq9y1sbvs58u39bjxmuli9; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY producers
    ADD CONSTRAINT fk_ownfq9y1sbvs58u39bjxmuli9 FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: departments fk_p6m3fphbw29hv03o3cubeji1e; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT fk_p6m3fphbw29hv03o3cubeji1e FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: bids fk_sa983s0v7kmy5nqrpa4q79qsw; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fk_sa983s0v7kmy5nqrpa4q79qsw FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: bid_statuses fk_so0yugpnbs5r34p9yuvy88w93; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bid_statuses
    ADD CONSTRAINT fk_so0yugpnbs5r34p9yuvy88w93 FOREIGN KEY (modified_by) REFERENCES employees(id);


--
-- Name: bid_statuses fk_t4ptdaybdtml52sl5llarqyd4; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bid_statuses
    ADD CONSTRAINT fk_t4ptdaybdtml52sl5llarqyd4 FOREIGN KEY (created_by) REFERENCES employees(id);


--
-- Name: bids fkcy5ui58j8edka48hh05f7jc26; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fkcy5ui58j8edka48hh05f7jc26 FOREIGN KEY (finance_dep_id) REFERENCES finance_dep(id);


--
-- Name: bid_statuses fkdardqbah9kkqa1u5ywcpje662; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bid_statuses
    ADD CONSTRAINT fkdardqbah9kkqa1u5ywcpje662 FOREIGN KEY (bid_id) REFERENCES bids(id);


--
-- Name: finance_dep fke1f3b0agx8mlhajv4sw2shbpp; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY finance_dep
    ADD CONSTRAINT fke1f3b0agx8mlhajv4sw2shbpp FOREIGN KEY (finance_id) REFERENCES finances(id);


--
-- Name: bids fkf5o6dvp3q0iltpx4u2dau7t8r; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fkf5o6dvp3q0iltpx4u2dau7t8r FOREIGN KEY (cpv_code) REFERENCES cpv(cpv_code);


--
-- Name: bids fkipt88thl0kj9c09jo80dun9g9; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fkipt88thl0kj9c09jo80dun9g9 FOREIGN KEY (producer_id) REFERENCES producers(id);


--
-- Name: departments fkn8e2nupe8aik8xwqad6b5furs; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY departments
    ADD CONSTRAINT fkn8e2nupe8aik8xwqad6b5furs FOREIGN KEY (inst_id) REFERENCES institutes(id);


--
-- Name: subdepartments fkr4pqpc6g3gd4y1dyjnts36pmo; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY subdepartments
    ADD CONSTRAINT fkr4pqpc6g3gd4y1dyjnts36pmo FOREIGN KEY (dep_id) REFERENCES departments(id);


--
-- Name: bids fkth8arq3mim7mm402mv93j21ps; Type: FK CONSTRAINT; Schema: promasy; Owner: postgres
--

ALTER TABLE ONLY bids
    ADD CONSTRAINT fkth8arq3mim7mm402mv93j21ps FOREIGN KEY (am_unit_id) REFERENCES amount_units(id);

--
-- If user 'promasy_user' do not exists - creating it
--

DO
  $body$
  BEGIN
     IF NOT EXISTS (
        SELECT *
        FROM   pg_catalog.pg_user
        WHERE  usename = 'promasy_user') THEN

        CREATE ROLE promasy_user LOGIN ENCRYPTED PASSWORD '@ccessp@ss';
     END IF;
     END
  $body$;

--
-- Name: promasy; Type: ACL; Schema: -; Owner: postgres
--

GRANT USAGE ON SCHEMA promasy TO promasy_user;

--
-- Name: amount_units; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE amount_units TO promasy_user;

--
-- Name: bid_statuses; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE bid_statuses TO promasy_user;

--
-- Name: bids; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE bids TO promasy_user;

--
-- Name: cpv; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE cpv TO promasy_user;

--
-- Name: departments; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE departments TO promasy_user;

--
-- Name: employees; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE employees TO promasy_user;

--
-- Name: finance_dep; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE finance_dep TO promasy_user;

--
-- Name: finances; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE finances TO promasy_user;

--
-- Name: hilo_seqeunce; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE hilo_seqeunce TO promasy_user;

--
-- Name: institutes; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE institutes TO promasy_user;

--
-- Name: producers; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE producers TO promasy_user;

--
-- Name: reasons_for_suppl; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE reasons_for_suppl TO promasy_user;

--
-- Name: registrations; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE registrations TO promasy_user;

--
-- Name: subdepartments; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE subdepartments TO promasy_user;

--
-- Name: suppliers; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE suppliers TO promasy_user;

--
-- Name: version; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE version TO promasy_user;

--
-- Name: addresses; Type: ACL; Schema: promasy; Owner: postgres
--

GRANT SELECT,INSERT,UPDATE ON TABLE addresses TO promasy_user;

--
-- Data for Name: cpv; Type: TABLE DATA; Schema: promasy; Owner: postgres
--

COPY cpv (cpv_code, cpv_eng, cpv_level, terminal, cpv_ukr) FROM stdin;
03000000-1	Agricultural, farming, fishing, forestry and related products	1	f	Сільськогосподарська, фермерська продукція, продукція рибальства, лісівництва та супутня продукція
03100000-2	Agricultural and horticultural products	2	f	Сільськогосподарська продукція та продукція рослинництва
03110000-5	Crops, products of market gardening and horticulture	3	f	Сільськогосподарські культури, продукція товарного садівництва та рослинництва
03111000-2	Seeds	4	f	Насіння
03111100-3	Soya beans	5	t	Зерно сої
03111200-4	Peanuts	5	t	Арахіс
03111300-5	Sunflower seeds	5	t	Насіння соняшника
03111400-6	Cotton seeds	5	t	Насіння бавовнику
03111500-7	Sesame seeds	5	t	Насіння кунжуту
03111600-8	Mustard seeds	5	t	Насіння гірчиці
03111700-9	Vegetable seeds	5	t	Насіння овочів
03111800-0	Fruit seeds	5	t	Насіння плодових культур
03111900-1	Flower seeds	5	t	Насіння квітів
03112000-9	Unmanufactured tobacco	4	t	Необроблений тютюн
03113000-6	Plants used for sugar manufacturing	4	f	Рослини, що використовуються для виробництва цукру
03113100-7	Sugar beet	5	t	Цукровий буряк
03113200-8	Sugar cane	5	t	Цукрова тростина
03114000-3	Straw and forage	4	f	Солома та фураж
03114100-4	Straw	5	t	Солома
03114200-5	Forage	5	t	Фураж
03115000-0	Raw vegetable materials	4	f	Рослинна сировина
03115100-1	Raw vegetable materials used in textile production	5	f	Рослинна сировина, що використовується у текстильній промисловості
03115110-4	Cotton	6	t	Бавовна
03115120-7	Jute	6	t	Джут
03115130-0	Flax	6	t	Льон
03116000-7	Natural rubber and latex, and associated products	4	f	Натуральні каучук і латекс та пов’язана продукція
03116100-8	Natural rubber	5	t	Натуральний каучук
03116200-9	Natural latex	5	t	Натуральний латекс
03116300-0	Latex products	5	t	Вироби з латексу
03117000-4	Plants used in specific fields	4	f	Рослини, що використовуються у специфічних галузях
03117100-5	Plants used in perfumery or pharmacy, or for insecticidal or similar purposes	5	f	Рослини, що використовуються у парфумерії чи фармацевтиці, або рослини інсектицидного чи подібного призначення
03117110-8	Plants used in perfumery	6	t	Рослини, що використовуються у парфумерії
03117120-1	Plants used in pharmacy	6	t	Рослини, що використовуються у фармацевтиці
03117130-4	Plants used for insecticidal purposes	6	t	Рослини інсектицидного призначення
03117140-7	Plants used for fungicidal or similar purposes	6	t	Рослини фунгіцидного чи подібного призначення
03117200-6	Seeds of plants used in specific fields	5	t	Насіння рослин, що використовується у специфічних галузях
03120000-8	Horticultural and nursery products	3	f	Продукція рослинництва, у тому числі тепличного
03121000-5	Horticultural products	4	f	Продукція рослинництва
03121100-6	Live plants, bulbs, roots, cuttings and slips	5	t	Рослини живі, цибулини, корені, прищепи та живці
03121200-7	Cut flowers	5	f	Квіти зрізані
03121210-0	Floral arrangements	6	t	Квіткові композиції
03130000-1	Beverage and spice crops	3	f	Сільськогосподарські культури для виробництва напоїв і прянощі
03131000-8	Beverage crops	4	f	Сільськогосподарські культури для виробництва напоїв
03131100-9	Coffee beans	5	t	Кавові зерна
03131200-0	Tea bushes	5	t	Чайне листя
03131300-1	Maté	5	t	Мате
03131400-2	Cocoa beans	5	t	Какао-боби
03132000-5	Unprocessed spices	4	t	Необроблені прянощі
03140000-4	Animal products and related products	3	f	Продукція тваринництва та супутня продукція
03141000-1	Bulls' semen	4	t	Сперма бугаїв
03142000-8	Animal products	4	f	Продукція тваринництва
03142100-9	Natural honey	5	t	Натуральний мед
03142200-0	Snails	5	t	Равлики
03142300-1	Edible products of animal origin	5	t	Продукти харчування тваринного походження
03142400-2	Waxes	5	t	Воски
03142500-3	Eggs	5	t	Яйця
03143000-5	Products of mixed farming	4	t	Продукція багатогалузевого господарства
03144000-2	Agricultural supplies	4	t	Сільськогосподарські товари
03200000-3	Cereals, potatoes, vegetables, fruits and nuts	2	f	Зернові культури, картопля, овочі, фрукти та горіхи
03210000-6	Cereals and potatoes	3	f	Зернові культури та картопля
03211000-3	Cereals	4	f	Зернові культури
03211100-4	Wheat	5	f	Пшениця
03211110-7	Durum wheat	6	t	Пшениця тверда
03211120-0	Soft wheat	6	t	Пшениця м’яка
03211200-5	Maize	5	t	Кукурудза
03211300-6	Rice	5	t	Рис
03211400-7	Barley	5	t	Ячмінь
03211500-8	Rye	5	t	Жито
03211600-9	Oats	5	t	Овес
03211700-0	Malt	5	t	Солод
03211900-2	Grain products	5	t	Зернопродукти
03212000-0	Potatoes and dried vegetables	4	f	Картопля та сушені овочі
03212100-1	Potatoes	5	t	Картопля
03212200-2	Dried leguminous vegetables and pulses	5	f	Бобові овочі та зерна бобових культур сушені
03212210-5	Dried leguminous vegetables	6	f	Бобові овочі сушені
03212211-2	Lentils	7	t	Сочевиця
03212212-9	Chick peas	7	t	Нут
03212213-6	Dried peas	7	t	Горох сушений
03212220-8	Pulses	6	t	Зерна бобових культур сушені
03220000-9	Vegetables, fruits and nuts	3	f	Овочі, фрукти та горіхи
03221000-6	Vegetables	4	f	Овочі
03221100-7	Root and tuber vegetables	5	f	Коренеплідні та бульбоплідні овочі
03221110-0	Root vegetables	6	f	Коренеплідні овочі
03221111-7	Beetroot	7	t	Буряк
03221112-4	Carrots	7	t	Морква
03221113-1	Onions	7	t	Цибуля
03221114-8	Turnips	7	t	Ріпа
03221120-3	Tuber vegetables	6	t	Бульбоплідні овочі
03221200-8	Fruit vegetables	5	f	Плодові овочі
03221210-1	Beans	6	f	Квасоля
03221211-8	Broad beans	7	t	Біб кінський
03221212-5	Green beans	7	t	Квасоля стручкова
03221213-2	Runner beans	7	t	Квасоля багатоквіткова
03221220-4	Peas	6	f	Горох
03221221-1	Garden peas	7	t	Горох посівний
03221222-8	Mange-tout	7	t	Горох зелений стручковий
03221230-7	Peppers	6	t	Перець овочевий
03221240-0	Tomatoes	6	t	Помідори
03221250-3	Courgettes	6	t	Кабачки
03221260-6	Mushrooms	6	t	Гриби
03221270-9	Cucumbers	6	t	Огірки
03221300-9	Leaf vegetables	5	f	Листкові овочі
03221310-2	Lettuce	6	t	Салат латук
03221320-5	Salad leaves	6	t	Салат листковий
03221330-8	Artichokes	6	t	Артишоки
03221340-1	Spinach	6	t	Шпинат
03221400-0	Cabbage vegetables	5	f	Капустяні овочі
03221410-3	Cabbage	6	t	Капуста качанна
03221420-6	Cauliflower	6	t	Капуста цвітна
03221430-9	Broccoli	6	t	Капуста броколі
03221440-2	Brussels sprouts	6	t	Капуста брюсельська
03222000-3	Fruit and nuts	4	f	Фрукти і горіхи
03222100-4	Tropical fruit and nuts	5	f	Тропічні фрукти і горіхи
03222110-7	Tropical fruit	6	f	Тропічні фрукти
03222111-4	Bananas	7	t	Банани
03222112-1	Pineapples	7	t	Ананаси
03222113-8	Mangoes	7	t	Манго
03222114-5	Dates	7	t	Фініки
03222115-2	Raisins	7	t	Родзинки
03222116-9	Figs	7	t	Інжир
03222117-6	Avocados	7	t	Авокадо
03222118-3	Kiwi fruit	7	t	Ківі
03222120-0	Coconuts	6	t	Кокосові горіхи
03222200-5	Citrus fruit	5	f	Цитрусові фрукти
03222210-8	Lemons	6	t	Лимони
03222220-1	Oranges	6	t	Апельсини
03222230-4	Grapefruit	6	t	Грейпфрути
03222240-7	Tangerines	6	t	Мандарини
03222250-0	Limes	6	t	Лайми
03222300-6	Non-tropical fruit	5	f	Нетропічні фрукти
03222310-9	Berry fruit	6	f	Ягоди
03222311-6	Currants	7	t	Смородина
03222312-3	Gooseberries	7	t	Аґрус
03222313-0	Strawberries	7	t	Полуниця
03222314-7	Raspberries	7	t	Малина
03222315-4	Cranberries	7	t	Журавлина
03222320-2	Apples, pears and quinces	6	f	Яблука, груші, айва
03222321-9	Apples	7	t	Яблука
03222322-6	Pears	7	t	Груші
03222323-3	Quinces	7	t	Айва
03222330-5	Stone fruit	6	f	Кісточкові фрукти
03222331-2	Apricots	7	t	Абрикоси
03222332-9	Peaches	7	t	Персики
03222333-6	Cherries	7	t	Вишні та черешні
03222334-3	Plums	7	t	Сливи
03222340-8	Grapes	6	f	Виноград
03222341-5	Table grapes	7	t	Виноград столовий
03222342-2	Wine grapes	7	t	Виноград винний
03222400-7	Olives	5	t	Оливки та маслини
03300000-2	Farming, hunting and fishing products	2	f	Фермерська продукція, продукція мисливства та рибальства
03310000-5	Fish, crustaceans and aquatic products	3	f	Риба, ракоподібні та продукція водного господарства
03311000-2	Fish	4	f	Риба
03311100-3	Flat fish	5	f	Камбалоподібні
03311110-6	Sole	6	t	Морський язик
03311120-9	Plaice	6	t	Камбала
03311200-4	Fish of the cod family	5	f	Тріскові
03311210-7	Cod	6	t	Тріска
03311220-0	Pollock	6	t	Сайда
03311230-3	Hake	6	t	Хек
03311240-6	Haddock	6	t	Пікша
03311300-5	Herring	5	t	Оселедець
03311400-6	Tuna	5	t	Тунець
03311500-7	Whiting	5	t	Мерлан
03311600-8	Whitebait	5	t	Дрібна риба
03311700-9	Salmon	5	t	Лосось
03312000-9	Crustaceans	4	f	Ракоподібні
03312100-0	Oysters	5	t	Устриці
03312200-1	Shellfish	5	t	Молюски
03312300-2	Aquatic invertebrates	5	t	Водні безхребетні
03313000-6	Aquatic products	4	f	Продукція водного господарства
03313100-7	Corals or similar products	5	t	Корали чи подібна продукція
03313200-8	Natural sponges	5	t	Натуральні губки
03313300-9	Seaweeds	5	f	Морські водорості
03313310-2	Algae	6	t	Водорості
03320000-8	Cattle, livestock and small animals	3	f	Велика рогата та інша свійська худоба, дрібні свійські тварини
03321000-5	Cattle	4	f	Худоба велика рогата
03321100-6	Bovine cattle	5	t	Худоба велика рогата родини бикових
03321200-7	Calves	5	t	Телята
03322000-2	Livestock	4	f	Свійська худоба
03322100-3	Sheep	5	t	Вівці
03322200-4	Goats	5	t	Кози
03322300-5	Horses	5	t	Коні
03323000-9	Pigs	4	t	Свині
03324000-6	Live poultry	4	t	Свійська птиця жива
03325000-3	Small animals	4	f	Дрібні свійські тварини
03325100-4	Rabbits	5	t	Кролі
03325200-5	Hares	5	t	Зайці
03330000-3	Farm animal products	3	f	Продукція фермерського тваринництва
03331000-0	Fresh milk from sheep and goats	4	f	Овече та козине молоко сире
03331100-1	Sheep's milk	5	t	Овече молоко
03331200-2	Goats' milk	5	t	Козине молоко
03332000-7	Wool and animal hair	4	f	Вовна і волос тварин
03332100-8	Shorn wool	5	t	Вовна стрижена
03332200-9	Animal hair	5	t	Волос тварин
03333000-4	Fresh cows' milk	4	t	Коров’яче молоко сире
03340000-6	Animal ear tags	3	f	Вушні бирки для тварин
03341000-3	Bovine ear tags	4	t	Вушні бирки для великої рогатої худоби родини бикових
03400000-4	Forestry and logging products	2	f	Продукція лісівництва та лісозаготівлі
03410000-7	Wood	3	f	Деревина
03411000-4	Coniferous wood	4	t	Деревина хвойних порід
03412000-1	Tropical wood	4	t	Деревина тропічних порід
03413000-8	Fuel wood	4	t	Паливна деревина
03414000-5	Rough wood	4	t	Необроблена деревина
03415000-2	Softwood	4	t	Деревина м’яких порід
03416000-9	Wood waste	4	t	Відходи деревини
03417000-6	Scrap wood	4	f	Деревинна тріска
03417100-7	Sawdust	5	t	Тирса
03418000-3	Logs	4	f	Кругляк
03418100-4	Hardwood	5	t	Деревина твердих порід
03419000-0	Timber	4	f	Лісоматеріали
03419100-1	Timber products	5	t	Вироби з лісоматеріалів
03419200-2	Mining timber	5	t	Кріпильний ліс
03420000-0	Gums	3	f	Смоли
03421000-7	Balsams	4	t	Живиці
03422000-4	Lac	4	t	Шелак
03430000-3	Cork	3	f	Корок
03431000-0	Natural cork	4	t	Натуральний корок
03432000-7	Basketware	4	f	Плетені кошики
03432100-8	Wickerwork	5	t	Плетені вироби
03440000-6	Forestry products	3	f	Продукція лісівництва
03441000-3	Ornamental plants, grasses, mosses or lichens	4	t	Рослини, трави, мохи або лишайники декоративні
03450000-9	Tree-nursery products	3	f	Розсадницька продукція
03451000-6	Plants	4	f	Рослини
03451100-7	Bedding plants	5	t	Саджанці
03451200-8	Flower bulbs	5	t	Цибулини квітів
03451300-9	Shrubs	5	t	Кущі
03452000-3	Trees	4	t	Дерева
03460000-2	Pulp	3	f	Целюлоза
03461000-9	Wood pulp	4	f	Деревинна маса
03461100-0	Chemical wood pulp	5	t	Деревинна маса, оброблена хімічним шляхом
09000000-3	Petroleum products, fuel, electricity and other sources of energy	1	f	Нафтопродукти, паливо, електроенергія та інші джерела енергії
09100000-0	Fuels	2	f	Паливо
09110000-3	Solid fuels	3	f	Тверде паливо
09111000-0	Coal and coal-based fuels	4	f	Вугілля та паливо на вугільній основі
09111100-1	Coal	5	t	Вугілля
09111200-2	Coal-based fuels	5	f	Паливо на вугільній основі
09111210-5	Hard coal	6	t	Антрацит
09111220-8	Briquettes	6	t	Брикетоване вугілля
09111300-3	Fossil fuels	5	t	Викопне паливо
09111400-4	Wood fuels	5	t	Деревне паливо
09112000-7	Lignite and peat	4	f	Буре вугілля і торф
09112100-8	Lignite	5	t	Буре вугілля
09112200-9	Peat	5	t	Торф
09113000-4	Coke	4	t	Кокс
09120000-6	Gaseous fuels	3	f	Газове паливо
09121000-3	Coal gas, mains gas or similar gases	4	f	Вугільний, магістральний чи подібні гази
09121100-4	Coal gas or similar gases	5	t	Вугільний газ чи подібні гази
09121200-5	Mains gas	5	t	Магістральний газ
09122000-0	Propane and butane	4	f	Пропан і бутан
09122100-1	Propane gas	5	f	Пропан
09122110-4	Liquefied propane gas	6	t	Пропан скраплений
09122200-2	Butane gas	5	f	Бутан
09122210-5	Liquefied butane gas	6	t	Бутан скраплений
09123000-7	Natural gas	4	t	Природний газ
09130000-9	Petroleum and distillates	3	f	Нафта і дистиляти
09131000-6	Aviation kerosene	4	f	Авіаційний гас
09131100-7	Kerosene jet type fuels	5	t	Реактивне паливо гасового типу
09132000-3	Petrol	4	f	Бензин
09132100-4	Unleaded petrol	5	t	Неетильований бензин
09132200-5	Leaded petrol	5	t	Етильований бензин
09132300-6	Petrol with ethanol	5	t	Бензин з етанолом
09133000-0	Liquefied Petroleum Gas (LPG)	4	t	Нафтовий газ скраплений
09134000-7	Gas oils	4	f	Газойлі
09134100-8	Diesel oil	5	t	Дизельна олива
09134200-9	Diesel fuel	5	f	Дизельне паливо
09134210-2	Diesel fuel (0,2)	6	t	Дизельне паливо (0,2)
09134220-5	Diesel fuel (EN 590)	6	t	Дизельне паливо (EN 590)
09134230-8	Biodiesel	6	f	Біодизельне паливо
09134231-5	Biodiesel (B20)	7	t	Біодизельне паливо (B20)
09134232-2	Biodiesel (B100)	7	t	Біодизельне паливо (B100)
09135000-4	Fuel oils	4	f	Мазути
09135100-5	Heating oil	5	f	Опалювальний мазут
09135110-8	Low-sulphur combustible oils	6	t	Паливний малосірчистий мазут
09200000-1	Petroleum, coal and oil products	2	f	Нафта, вугілля і нафтопродукти
09210000-4	Lubricating preparations	3	f	Мастильні засоби
09211000-1	Lubricating oils and lubricating agents	4	f	Мастильні оливи та мастильні матеріали
09211100-2	Motor oils	5	t	Моторні оливи
09211200-3	Compressor lube oils	5	t	Компресорні оливи
09211300-4	Turbine lube oils	5	t	Турбінні оливи
09211400-5	Gear oils	5	t	Трансмісійні оливи
09211500-6	Reductor oils	5	t	Редукторні оливи
09211600-7	Oils for use in hydraulic systems and other purposes	5	f	Оливи для гідравлічних систем та іншого призначення
09211610-0	Liquids for hydraulic purposes	6	t	Гідравлічні рідини
09211620-3	Mould release oils	6	t	Формувальні оливи
09211630-6	Anti-corrosion oils	6	t	Антикорозійні оливи
09211640-9	Electrical insulating oils	6	t	Електроізоляційні оливи
09211650-2	Brake fluids	6	t	Гальмівні рідини
09211700-8	White oils and liquid paraffin	5	f	Білі оливи та рідкий парафін
09211710-1	White oils	6	t	Білі оливи
09211720-4	Liquid paraffin	6	t	Рідкий парафін
09211800-9	Petroleum oils and preparations	5	f	Мінеральні оливи та їх суміші
09211810-2	Light oils	6	t	Легкі оливи
09211820-5	Petroleum oils	6	t	Мінеральні оливи
09211900-0	Lubricating traction oils	5	t	Трансмісійні мастила
09220000-7	Petroleum jelly, waxes and special spirits	3	f	Вазелін і парафіни нафтові та спеціальні бензини
09221000-4	Petroleum jelly and waxes	4	f	Вазелін і парафіни нафтові
09221100-5	Petroleum jelly	5	t	Нафтовий вазелін
09221200-6	Paraffin wax	5	t	Твердий парафін
09221300-7	Petroleum wax	5	t	Нафтовий парафін
09221400-8	Petroleum residues	5	t	Залишки нафтопереробки
09222000-1	Special spirits	4	f	Спеціальні бензини
09222100-2	White spirit	5	t	Уайт-спірит
09230000-0	Petroleum (crude)	3	t	Нафта (сира)
09240000-3	Oil and coal-related products	3	f	Продукти переробки нафти та вугілля
09241000-0	Bituminous or oil shale	4	t	Бітумінозні чи нафтоносні сланці
09242000-7	Coal-related products	4	f	Продукти переробки вугілля
09242100-8	Coal oil	5	t	Вугільна нафта
09300000-2	Electricity, heating, solar and nuclear energy	2	f	Електрична, теплова, сонячна та атомна енергія
09310000-5	Electricity	3	t	Електрична енергія
09320000-8	Steam, hot water and associated products	3	f	Пара, гаряча вода та пов’язана продукція
09321000-5	Hot water	4	t	Вода гаряча
09322000-2	Steam	4	t	Пара
09323000-9	District heating	4	t	Централізоване опалення
09324000-6	Long-distance heating	4	t	Центральне опалення
09330000-1	Solar energy	3	f	Сонячна енергія
09331000-8	Solar panels	4	f	Сонячні панелі
09331100-9	Solar collectors for heat production	5	t	Сонячні теплові колектори
09331200-0	Solar photovoltaic modules	5	t	Фотоелектричні сонячні модулі
09332000-5	Solar installation	4	t	Сонячна установка
09340000-4	Nuclear fuels	3	f	Ядерне паливо
09341000-1	Uranium	4	t	Уран
09342000-8	Plutonium	4	t	Плутоній
09343000-5	Radioactive materials	4	t	Радіоактивні матеріали
09344000-2	Radio-isotopes	4	t	Радіоактивні ізотопи
14000000-1	Mining, basic metals and related products	1	f	Гірнича продукція, неблагородні метали та супутня продукція
14200000-3	Sand and clay	2	f	Пісок і глина
14210000-6	Gravel, sand, crushed stone and aggregates	3	f	Гравій, пісок, щебінь і наповнювачі
14211000-3	Sand	4	f	Пісок
14211100-4	Natural sand	5	t	Пісок природний
14212000-0	Granules, chippings, stone powder, pebbles, gravel, broken and crushed stone, stone mixtures, sand-gravel mixtures and other aggregates	4	f	Гранули, кам’яна крихта, кам’яний порошок, галька, гравій, колотий камінь і щебінь, кам’яні суміші, піщано-гравійні суміші та інші наповнювачі
14212100-1	Pebbles and gravel	5	f	Галька та гравій
14212110-4	Pebbles	6	t	Галька
14212120-7	Gravel	6	t	Гравій
14212200-2	Aggregates	5	f	Наповнювачі
14212210-5	Sand-gravel mix	6	t	Піщано-гравійні суміші
14212300-3	Broken and crushed stone	5	f	Колотий камінь і щебінь
14212310-6	Ballast	6	t	Баласт
14212320-9	Crushed granite	6	t	Гранітний щебінь
14212330-2	Crushed basalt	6	t	Базальтовий щебінь
14212400-4	Soil	5	f	Ґрунт
14212410-7	Topsoil	6	t	Гумус
14212420-0	Subsoil	6	t	Надра
14212430-3	Stone chippings	6	t	Кам’яна крихта
14213000-7	Macadam, tarmac and tar sands	4	f	Дорожні щебеневий і щебенево-гудроновий покриви та бітумінозний пісок
14213100-8	Macadam	5	t	Дорожній щебеневий покрив
14213200-9	Tarmac	5	t	Дорожній щебенево-гудроновий покрив
14213300-0	Tar sands	5	t	Бітумінозний пісок
14220000-9	Clay and kaolin	3	f	Глина та каолін
14221000-6	Clay	4	t	Глина
14222000-3	Kaolin	4	t	Каолін
14300000-4	Chemical and fertiliser minerals	2	f	Мінеральна сировина для хімічної промисловості та для виробництва добрив
14310000-7	Fertiliser minerals	3	f	Мінеральна сировина для виробництва добрив
14311000-4	Natural calcium, aluminium calcium phosphate and crude natural potassium salts	4	f	Кальцій природний, алюмінієво-кальцієвий фосфат та природні необроблені калієві солі
14311100-5	Natural calcium	5	t	Кальцій природний
14311200-6	Aluminium calcium phosphates	5	t	Алюмінієво-кальцієві фосфати
14311300-7	Crude natural potassium salts	5	t	Природні необроблені калієві солі
14312000-1	Iron pyrites	4	f	Пірит
14312100-2	Unroasted iron pyrites	5	t	Пірит необпалений
14320000-0	Chemical minerals	3	t	Мінеральна сировина для хімічної промисловості
14400000-5	Salt and pure sodium chloride	2	f	Сіль і чистий хлорид натрію
14410000-8	Rock salt	3	t	Кам’яна сіль
14420000-1	Sea salt	3	t	Морська сіль
14430000-4	Evaporated salt and pure sodium chloride	3	t	Випарена сіль і чистий хлорид натрію
14450000-0	Salt in brine	3	t	Ропа
14500000-6	Related mining and quarrying products	2	f	Продукція гірничо-видобувної промисловості супутня
14520000-2	Precious and semi-precious stones, pumice stone, emery, natural abrasives, other minerals and precious metals	3	f	Коштовне та напівкоштовне каміння, пемза, наждак, природні абразиви, інші мінерали та благородні метали
14521000-9	Precious and semi-precious stones	4	f	Коштовне та напівкоштовне каміння
14521100-0	Precious stones	5	f	Коштовне каміння
14521140-2	Dust or powder of precious stones	6	t	Пил і порошок коштовного каміння
14521200-1	Semi-precious stones	5	f	Напівкоштовне каміння
14521210-4	Dust or powder of semi-precious stones	6	t	Пил і порошок напівкоштовного каміння
14522000-6	Industrial diamonds, pumice stone, emery and other natural abrasives	4	f	Промислові алмази, пемза, наждак та інші природні абразиви
14522100-7	Pumice stone	5	t	Пемза
14522200-8	Industrial diamonds	5	t	Промислові алмази
14522300-9	Emery	5	t	Наждак
14522400-0	Natural abrasives	5	t	Природні абразиви
14523000-3	Related minerals, precious metals and associated products	4	f	Супутні мінерали, благородні метали та пов’язана продукція
14523100-4	Minerals	5	t	Мінерали
14523200-5	Gold	5	t	Золото
14523300-6	Silver	5	t	Срібло
14523400-7	Platinum	5	t	Платина
14600000-7	Metal ores and alloys	2	f	Металеві руди та сплави
14610000-0	Metal ores	3	f	Металеві руди
14611000-7	Iron ores	4	t	Залізні руди
14612000-4	Non-ferrous metal ores	4	f	Руди кольорових металів
14612100-5	Copper ores	5	t	Мідні руди
14612200-6	Nickel ores	5	t	Нікелеві руди
14612300-7	Aluminium ores	5	t	Алюмінієві руди
14612400-8	Precious-metal ores	5	t	Руди благородних металів
14612500-9	Lead ores	5	t	Свинцеві руди
14612600-0	Zinc ores	5	t	Цинкові руди
14612700-1	Tin ores	5	t	Олов’яні руди
14613000-1	Uranium and thorium ores	4	f	Уранові та торієві руди
14613100-2	Uranium ores	5	t	Уранові руди
14613200-3	Thorium ores	5	t	Торієві руди
14614000-8	Miscellaneous ores	4	t	Руди різні
14620000-3	Alloys	3	f	Сплави
14621000-0	Ferro-alloys	4	f	Феросплави
14621100-1	Non-ECSC ferro-alloys	5	f	Феросплави, не класифіковані Європейським об’єднанням вугілля та сталі
14621110-4	Ferro-manganese	6	t	Феромарганець
14621120-7	Ferro-chromium	6	t	Ферохром
14621130-0	Ferro-nickel	6	t	Феронікель
14622000-7	Steel	4	t	Сталь
14630000-6	Slag, dross, ferrous waste and scrap	3	t	Шлак, окалина, відходи та скрап чорних металів
14700000-8	Basic metals	2	f	Неблагородні метали
14710000-1	Iron, lead, zinc, tin and copper	3	f	Залізо, свинець, цинк, олово та мідь
14711000-8	Iron	4	f	Залізо
14711100-9	Pig iron	5	t	Чавун у чушках
14712000-5	Lead	4	t	Свинець
14713000-2	Zinc	4	t	Цинк
14714000-9	Tin	4	t	Олово
14715000-6	Copper	4	t	Мідь
14720000-4	Aluminium, nickel, scandium, titanium and vanadium	3	f	Алюміній, нікель, скандій, титан і ванадій
14721000-1	Aluminium	4	f	Алюміній
14721100-2	Aluminium oxide	5	t	Оксид алюмінію
14722000-8	Nickel	4	t	Нікель
14723000-5	Scandium	4	t	Скандій
14724000-2	Titanium	4	t	Титан
14725000-9	Vanadium	4	t	Ванадій
14730000-7	Chromium, manganese, cobalt, yttrium and zirconium	3	f	Хром, марганець, кобальт, ітрій і цирконій
14731000-4	Chromium	4	t	Хром
14732000-1	Manganese	4	t	Марганець
14733000-8	Cobalt	4	t	Кобальт
14734000-5	Yttrium	4	t	Ітрій
14735000-2	Zirconium	4	t	Цирконій
14740000-0	Molybdenum, technetium, ruthenium and rhodium	3	f	Молібден, технецій, рутеній і родій
14741000-7	Molybdenum	4	t	Молібден
14742000-4	Technetium	4	t	Технецій
14743000-1	Ruthenium	4	t	Рутеній
14744000-8	Rhodium	4	t	Родій
14750000-3	Cadmium, lutetium, hafnium, tantalum and tungsten	3	f	Кадмій, лютецій, гафній, тантал і вольфрам
14751000-0	Cadmium	4	t	Кадмій
14752000-7	Lutetium	4	t	Лютецій
14753000-4	Hafnium	4	t	Гафній
14754000-1	Tantalum	4	t	Тантал
14755000-8	Tungsten	4	t	Вольфрам
14760000-6	Iridium, gallium, indium, thallium and barium	3	f	Іридій, галій, індій, талій і барій
14761000-3	Iridium	4	t	Іридій
14762000-0	Gallium	4	t	Галій
14763000-7	Indium	4	t	Індій
14764000-4	Thallium	4	t	Талій
14765000-1	Barium	4	t	Барій
14770000-9	Caesium, strontium, rubidium and calcium	3	f	Цезій, стронцій, рубідій і кальцій
14771000-6	Caesium	4	t	Цезій
14772000-3	Strontium	4	t	Стронцій
14773000-0	Rubidium	4	t	Рубідій
14774000-7	Calcium	4	t	Кальцій
14780000-2	Potassium, magnesium, sodium and lithium	3	f	Калій, магній, натрій і літій
14781000-9	Potassium	4	t	Калій
14782000-6	Magnesium	4	t	Магній
14783000-3	Sodium	4	t	Натрій
14784000-0	Lithium	4	t	Літій
14790000-5	Niobium, osmium, rhenium and palladium	3	f	Ніобій, осмій, реній і паладій
14791000-2	Niobium	4	t	Ніобій
14792000-9	Osmium	4	t	Осмій
14793000-6	Rhenium	4	t	Реній
14794000-3	Palladium	4	t	Паладій
14800000-9	Miscellaneous non-metallic mineral products	2	f	Неметалеві корисні копалини різні
14810000-2	Abrasive products	3	f	Абразивні вироби
14811000-9	Millstones, grindstones and grinding wheels	4	f	Жорнові камені, точильні камені та круги
14811100-0	Millstones	5	t	Жорнові камені
14811200-1	Grindstones	5	t	Точильні камені
14811300-2	Grinding wheels	5	t	Точильні круги
14812000-6	Abrasive powder or grain	4	t	Абразивні порошок або зерно
14813000-3	Artificial corundum	4	t	Корунд штучний
14814000-0	Artificial graphite	4	t	Графіт штучний
14820000-5	Glass	3	t	Скло
14830000-8	Fibreglass	3	t	Скловолокно
14900000-0	Recovered secondary raw materials	2	f	Вторинна відновлена сировина
14910000-3	Recovered secondary metal raw materials	3	t	Вторинна металева відновлена сировина
14920000-6	Recovered secondary non-metal raw materials	3	t	Вторинна неметалева відновлена сировина
14930000-9	Ash and residues containing metals	3	t	Зола та металовмісні залишки
15000000-8	Food, beverages, tobacco and related products	1	f	Продукти харчування, напої, тютюн та супутня продукція
15100000-9	Animal products, meat and meat products	2	f	Продукція тваринництва, м’ясо та м’ясопродукти
15110000-2	Meat	3	f	М’ясо
15250000-5	Seafood	3	f	Морепродукти
15111000-9	Bovine meat	4	f	М’ясо великої рогатої худоби родини бикових
15111100-0	Beef	5	t	Яловичина
15111200-1	Veal	5	t	Телятина
15112000-6	Poultry	4	f	М’ясо свійської птиці
15112100-7	Fresh poultry	5	f	М’ясо свійської птиці свіже
15112110-0	Geese	6	t	Гусятина
15112120-3	Turkeys	6	t	Індичатина
15112130-6	Chickens	6	t	Курятина
15112140-9	Ducks	6	t	Качатина
15112300-9	Poultry livers	5	f	Печінка свійської птиці
15112310-2	Foie gras	6	t	Фуа-гра
15113000-3	Pork	4	t	Свинина
15114000-0	Offal	4	t	Потрухи
15115000-7	Lamb and mutton	4	f	Ягнятина та баранина
15115100-8	Lamb	5	t	Ягнятина
15115200-9	Mutton	5	t	Баранина
15117000-1	Goat meat	4	t	Козлятина
15118000-8	Horse, ass, mule or hinny meat	4	f	М’ясо коней, віслюків, мулів чи лошаків
15118100-9	Horsemeat	5	t	Конина
15118900-7	Ass, mule or hinny meat	5	t	М’ясо віслюків, мулів чи лошаків
15119000-5	Various meats	4	f	М’ясо різне
15119100-6	Rabbit meat	5	t	Кролятина
15119200-7	Hare meat	5	t	Зайчатина
15119300-8	Game	5	t	Дичина
15119400-9	Frogs' legs	5	t	Жаб’ячі лапки
15119500-0	Pigeons	5	t	Голуби
15119600-1	Fish meat	5	t	М’ясо риби
15130000-8	Meat products	3	f	М’ясопродукти
15131000-5	Meat preserves and preparations	4	f	М’ясні пресерви та вироби
15131100-6	Sausage-meat products	5	f	Вироби з ковбасного фаршу
15131110-9	Sausage meat	6	t	Ковбасний фарш
15131120-2	Sausage products	6	t	Ковбасні вироби
15131130-5	Sausages	6	f	Ковбаси
15131134-3	Black pudding and other blood sausages	7	t	Кров’янка та інші кров’яні ковбаси
15131135-0	Poultry sausages	7	t	Ковбаси з м’яса свійської птиці
15131200-7	Dried, salted, smoked or seasoned meat	5	f	М’ясо сушене, солене, копчене чи приправлене
15131210-0	Gammon	6	t	Свинячий окіст
15131220-3	Bacon	6	t	Бекон
15131230-6	Salami	6	t	Салямі
15131300-8	Liver preparations	5	f	Вироби з печінки
15131310-1	Pâté	6	t	Паштети
15131320-4	Preparations of goose or duck liver	6	t	Вироби з гусячої чи качиної печінки
15131400-9	Pork products	5	f	Продукція зі свинини
15131410-2	Ham	6	t	Шинка
15131420-5	Meatballs	6	t	Фрикадельки
15131490-6	Prepared pork dishes	6	t	Страви зі свинини
15131500-0	Poultry products	5	t	Вироби з м’яса свійської птиці
15131600-1	Beef and veal products	5	f	Вироби з яловичини і телятини
15131610-4	Beef meatballs	6	t	Яловичі фрикадельки
15131620-7	Minced beef	6	t	Яловичий фарш
15131640-3	Beefburgers	6	t	Гамбургери
15131700-2	Meat preparations	5	t	М’ясні вироби
15200000-0	Prepared and preserved fish	2	f	Рибні страви та пресерви
15210000-3	Fish fillets, fish livers and roe	3	f	Рибні філе, печінка та ікра
15211000-0	Fish fillets	4	f	Рибне філе
15211100-1	Fresh fish fillets	5	t	Рибне філе свіже
15212000-7	Fish roe	4	t	Рибна ікра
15213000-4	Fish livers	4	t	Рибна печінка
15220000-6	Frozen fish, fish fillets and other fish meat	3	f	Риба, рибне філе та інше м’ясо риби морожені
15221000-3	Frozen fish	4	t	Морожена риба
15229000-9	Frozen fish products	4	t	Морожені рибопродукти
15230000-9	Dried or salted fish; fish in brine; smoked fish	3	f	Сушена чи солена риба; риба в розсолі; копчена риба
15231000-6	Dried fish	4	t	Сушена риба
15232000-3	Salted fish	4	t	Солена риба
15233000-0	Fish in brine	4	t	Риба в розсолі
15234000-7	Smoked fish	4	t	Копчена риба
15235000-4	Preserved fish	4	t	Рибні пресерви
15240000-2	Canned or tinned fish and other prepared or preserved fish	3	f	Рибні консерви та інші рибні страви і пресерви
15241000-9	Coated, canned or tinned fish	4	f	Панірована риба та рибні консерви
15241100-0	Canned salmon	5	t	Консервований лосось
15241200-1	Prepared or preserved herring	5	t	Страви та пресерви з оселедця
15241300-2	Sardines	5	t	Сардини
15241400-3	Canned tuna	5	t	Консервований тунець
15241500-4	Mackerel	5	t	Скумбрія
15241600-5	Anchovies	5	t	Анчоуси
15241700-6	Fish fingers	5	t	Рибні палички
15241800-7	Coated fish preparations	5	t	Вироби з панірованої риби
15242000-6	Prepared fish dishes	4	t	Рибні страви
15243000-3	Preparations of fish	4	t	Рибні вироби
15244000-0	Caviar and fish eggs	4	f	Ікра осетрових риб солена та ікра інших риб
15244100-1	Caviar	5	t	Ікра осетрових риб солена
15244200-2	Fish eggs	5	t	Рибна ікра
15251000-2	Frozen crustaceans	4	t	Морожені ракоподібні
15252000-9	Prepared or preserved crustaceans	4	t	Страви та пресерви з ракоподібних
15253000-6	Shellfish products	4	t	Вироби з молюсків
15300000-1	Fruit, vegetables and related products	2	f	Фрукти, овочі та супутня продукція
15310000-4	Potatoes and potato products	3	f	Картопля та картопляні вироби
15311000-1	Frozen potatoes	4	f	Морожена картопля
15311100-2	Chips or french fries	5	t	Картопляні чіпси та картопля фрі
15311200-3	Diced, sliced and other frozen potatoes	5	t	Картопля, нарізана кубиками, кружальцями та інші види мороженої картоплі
15312000-8	Potato products	4	f	Картопляні вироби
15312100-9	Instant mashed potatoes	5	t	Картопляне пюре швидкого приготування
15312200-0	Part-fried potato chips	5	t	Напівобсмажені картопляні чіпси
15312300-1	Potato crisps	5	f	Смажена хрустка картопля
15312310-4	Flavoured potato crisps	6	t	Ароматизована смажена хрустка картопля
15312400-2	Potato snacks	5	t	Картопляні закуски
15312500-3	Potato croquettes	5	t	Картопляні крокети
15313000-5	Processed potatoes	4	t	Оброблена картопля
15320000-7	Fruit and vegetable juices	3	f	Фруктові та овочеві соки
15321000-4	Fruit juices	4	f	Фруктові соки
15321100-5	Orange juice	5	t	Апельсиновий сік
15321200-6	Grapefruit juice	5	t	Грейпфрутовий сік
15321300-7	Lemon juice	5	t	Лимонний сік
15321400-8	Pineapple juice	5	t	Ананасовий сік
15321500-9	Grape juice	5	t	Виноградний сік
15321600-0	Apple juice	5	t	Яблучний сік
15321700-1	Mixtures of unconcentrated juices	5	t	Суміші неконцентрованих соків
15321800-2	Concentrated juices	5	t	Концентровані соки
15322000-1	Vegetable juices	4	f	Овочеві соки
15322100-2	Tomato juice	5	t	Томатний сік
15330000-0	Processed fruit and vegetables	3	f	Оброблені фрукти та овочі
15331000-7	Processed vegetables	4	f	Оброблені овочі
15331100-8	Fresh or frozen vegetables	5	f	Свіжі або морожені овочі
15331110-1	Processed root vegetables	6	t	Оброблені коренеплідні овочі
15331120-4	Processed tuber vegetables	6	t	Оброблені бульбоплідні овочі
15331130-7	Beans, peas, peppers, tomatoes and other vegetables	6	f	Квасоля, горох, перець овочевий, помідори та інші овочі
15331131-4	Processed beans	7	t	Оброблена квасоля
15331132-1	Processed peas	7	t	Оброблений горох
15331133-8	Split peas	7	t	Колотий горох
15331134-5	Processed tomatoes	7	t	Оброблені помідори
15331135-2	Processed mushrooms	7	t	Оброблені гриби
15331136-9	Processed peppers	7	t	Оброблений перець овочевий
15331137-6	Soybean sprouts	7	t	Паростки сої
15331138-3	Truffles	7	t	Трюфелі
15331140-0	Leaf and cabbage vegetables	6	f	Листкові та капустяні овочі
15331142-4	Processed cabbage	7	t	Оброблена капуста
15331150-3	Processed pulses	6	t	Оброблені зерна бобових культур сушені
15331170-9	Frozen vegetables	6	t	Морожені овочі
15331400-1	Preserved and/or canned vegetables	5	f	Овочеві пресерви та/або консерви
15331410-4	Beans in tomato sauce	6	f	Квасоля в томатному соусі
15331411-1	Baked beans	7	t	Тушкована квасоля
15331420-7	Preserved tomatoes	6	f	Пресерви з помідорів
15331423-8	Canned tomatoes	7	t	Консервовані помідори
15331425-2	Tomato purée	7	t	Томатне пюре
15331427-6	Concentrated tomato purée	7	t	Концентроване томатне пюре
15331428-3	Tomato sauce	7	t	Томатний соус
15331430-0	Canned mushrooms	6	t	Консервовані гриби
15331450-6	Processed olives	6	t	Оброблені оливки та маслини
15331460-9	Canned vegetables	6	f	Овочеві консерви
15331461-6	Canned sauerkraut	7	t	Консервована квашена капуста
15331462-3	Canned peas	7	t	Консервований горох
15331463-0	Canned shelled beans	7	t	Консервована квасоля
15331464-7	Canned whole beans	7	t	Консервована стручкова квасоля
15331465-4	Canned asparagus	7	t	Консервована спаржа
15331466-1	Canned olives	7	t	Консервовані оливки та маслини
15331470-2	Sweet corn	6	t	Цукрова кукурудза
15331480-5	Provisionally preserved vegetables	6	t	Овочі, оброблені для тимчасового зберігання
15331500-2	Vegetables preserved in vinegar	5	t	Овочі, консервовані в оцті
15332000-4	Processed fruit and nuts	4	f	Оброблені фрукти та горіхи
15332100-5	Processed fruit	5	f	Оброблені фрукти
15332140-7	Processed apples	6	t	Оброблені яблука
15332150-0	Processed pears	6	t	Оброблені груші
15332160-3	Processed bananas	6	t	Оброблені банани
15332170-6	Rhubarb	6	t	Ревінь
15332180-9	Melons	6	t	Баштанні культури
15332200-6	Jams and marmalades; fruit jellies; fruit or nut purée and pastes	5	f	Джеми та мармелади; фруктові желе; фруктові чи горіхові пюре та пасти
15332230-5	Marmalades	6	f	Мармелади
15332231-2	Orange marmalade	7	t	Апельсиновий мармелад
15332232-9	Lemon marmalade	7	t	Лимонний мармелад
15332240-8	Fruit jellies	6	t	Фруктові желе
15332250-1	Fruit pastes	6	t	Фруктові пасти
15332260-4	Nut pastes	6	f	Горіхові пасти
15332261-1	Peanut butter	7	t	Арахісове масло
15332270-7	Fruit purées	6	t	Фруктові пюре
15332290-3	Jams	6	f	Джеми
15332291-0	Apricot jam	7	t	Абрикосовий джем
15332292-7	Blackberry jam	7	t	Ожиновий джем
15332293-4	Blackcurrant jam	7	t	Чорносмородиновий джем
15332294-1	Cherry jam	7	t	Вишневий або черешневий джем
15332295-8	Raspberry jam	7	t	Малиновий джем
15332296-5	Strawberry jam	7	t	Полуничний джем
15332300-7	Processed nuts	5	f	Оброблені горіхи
15332310-0	Roasted or salted nuts	6	t	Підсмажені або підсолені горіхи
15332400-8	Preserved fruits	5	f	Фруктові консерви
15332410-1	Dried fruit	6	f	Сухофрукти
15332411-8	Processed currants	7	t	Оброблена смородина
15332412-5	Processed raisins	7	t	Оброблені родзинки
15332419-4	Sultanas	7	t	Родзинки без кісточок
15333000-1	Vegetable by-products	4	t	Побічні продукти переробки овочів
15400000-2	Animal or vegetable oils and fats	2	f	Олії та тваринні і рослинні жири
15410000-5	Crude animal or vegetable oils and fats	3	f	Сирі олії та тваринні і рослинні жири
15411000-2	Animal or vegetable oils	4	f	Тваринні та рослинні жири
15411100-3	Vegetable oils	5	f	Олії
15411110-6	Olive oil	6	t	Оливкова олія
15411120-9	Sesame oil	6	t	Кунжутна олія
15411130-2	Groundnut oil	6	t	Арахісова олія
15411140-5	Coconut oil	6	t	Коксова олія
15411200-4	Cooking oil	5	f	Харчові жири
15411210-7	Frying oil	6	t	Олія для смаження
15412000-9	Fats	4	f	Жири
15412100-0	Animal fats	5	t	Тваринні жири
15412200-1	Vegetable fats	5	t	Рослинні жири
15413000-6	Solid residues of vegetable fats or oils	4	f	Тверді залишки рослинних жирів та олій
15413100-7	Oilcake	5	t	Макуха
15420000-8	Refined oils and fats	3	f	Рафіновані олії та жири
15421000-5	Refined oils	4	t	Рафіновані олії
15422000-2	Refined fats	4	t	Рафіновані жири
15423000-9	Hydrogenated or esterified oils or fats	4	t	Гідрогенізовані або етерифіковані олії та жири
15424000-6	Vegetable waxes	4	t	Воски рослинного походження
15430000-1	Edible fats	3	f	Харчові жири
15431000-8	Margarine and similar preparations	4	f	Маргарин та подібні продукти
15431100-9	Margarine	5	f	Маргарин
15431110-2	Liquid margarine	6	t	Рідкий маргарин
15431200-0	Reduced or low-fat spreads	5	t	Спреди знежирені або з низьким вмістом жиру
15500000-3	Dairy products	2	f	Молочні продукти
15510000-6	Milk and cream	3	f	Молоко та вершки
15511000-3	Milk	4	f	Молоко
15511100-4	Pasteurised milk	5	t	Пастеризоване молоко
15511200-5	Sterilised milk	5	f	Стерилізоване молоко
15511210-8	UHT milk	6	t	Ультрапастеризоване молоко
15511300-6	Skimmed milk	5	t	Знежирене молоко
15511400-7	Semi-skimmed milk	5	t	Частково знежирене молоко
15511500-8	Full-cream milk	5	t	Незбиране молоко
15511600-9	Condensed milk	5	t	Згущене молоко
15511700-0	Milk powder	5	t	Сухе молоко
15512000-0	Cream	4	f	Вершки
15512100-1	Single cream	5	t	Вершки з низьким вмістом жирів
15512200-2	Double cream	5	t	Жирні вершки
15512300-3	Clotted cream	5	t	Згущені вершки
15512900-9	Whipping cream	5	t	Вершки для збивання
15530000-2	Butter	3	t	Вершкове масло
15540000-5	Cheese products	3	f	Сирні продукти
15541000-2	Table cheese	4	t	Столовий сир
15542000-9	Fresh cheese	4	f	Свіжий сир
15542100-0	Cottage cheese	5	t	Зернений сир
15542200-1	Soft cheese	5	t	М’який сир
15542300-2	Feta cheese	5	t	Фета
15543000-6	Grated, powdered, blue-veined and other cheese	4	f	Сири терті, порошкові, блакитні та інші
15543100-7	Blue cheese	5	t	Блакитні сири
15543200-8	Cheddar cheese	5	t	Сир чеддер
15543300-9	Grated cheese	5	t	Тертий сир
15543400-0	Parmesan cheese	5	t	Сир пармезан
15544000-3	Hard cheese	4	t	Твердий сир
15545000-0	Cheese spreads	4	t	Плавлений сир
15550000-8	Assorted dairy products	3	f	Молочні продукти різні
15551000-5	Yoghurt and other fermented milk products	4	f	Йогурти та інші ферментовані молочні продукти
15551300-8	Yoghurt	5	f	Йогурт
15551310-1	Unflavoured yoghurt	6	t	Натуральний йогурт
15551320-4	Flavoured yoghurt	6	t	Йогурт ароматизований
15551500-0	Buttermilk	5	t	Маслянка
15552000-2	Casein	4	t	Казеїн
15553000-9	Lactose or lactose syrup	4	t	Лактоза чи лактозний сироп
15554000-6	Whey	4	t	Сироватка
15555000-3	Ice cream and similar products	4	f	Морозиво та подібна продукція
15555100-4	Ice cream	5	t	Морозиво
15555200-5	Sorbet	5	t	Сорбе
15600000-4	Grain mill products, starches and starch products	2	f	Продукція борошномельно-круп’яної промисловості, крохмалі та крохмалепродукти
15610000-7	Grain mill products	3	f	Продукція борошномельно-круп'яної промисловості
15611000-4	Husked rice	4	t	Облущений рис
15612000-1	Cereal or vegetable flour and related products	4	f	Борошно зернових та овочевих культур і супутня продукція
15612100-2	Wheat flour	5	f	Борошно пшеничне
15612110-5	Wholemeal flour	6	t	Борошно цільнозернове
15612120-8	Breadmaking flour	6	t	Борошно хлібопекарське
15612130-1	Plain flour	6	t	Борошно загального призначення
15612150-7	Pastry flour	6	t	Борошно кондитерське
15612190-9	Self-raising flour	6	t	Борошно самопіднімальне
15612200-3	Cereal flour	5	f	Борошно інших зернових культур
15612210-6	Corn flour	6	t	Борошно кукурудзяне
15612220-9	Rice flour	6	t	Борошно рисове
15612300-4	Vegetable flour and meal	5	t	Борошно та вичавки з овочевих культур
15612400-5	Mixes for the preparation of baker's wares	5	f	Суміші для хлібопекарських виробів
15612410-8	Cake mixes	6	t	Суміші для кондитерських виробів
15612420-1	Baking mixes	6	t	Суміші для хлібобулочних виробів
15612500-6	Bakery products	5	t	Хлібобулочні вироби
15613000-8	Cereal grain products	4	f	Продукція із зерна зернових культур
15613100-9	Ground oats	5	t	Вівсяна крупа
15613300-1	Cereal products	5	f	Злакові продукти
15613310-4	Prepared breakfast cereals	6	f	Готові сніданки зі злаків
15613311-1	Cornflakes	7	t	Кукурудзяні пластівці
15613313-5	Muesli or equivalent	7	t	Мюслі або подібна продукція
15613319-7	Puffed wheat	7	t	Повітряна пшениця
15613380-5	Rolled oats	6	t	Вівсяні пластівці
15614000-5	Processed rice	4	f	Рис оброблений
15614100-6	Long-grain rice	5	t	Рис довгозернистий
15614200-7	Milled rice	5	t	Рис шліфований
15614300-8	Broken rice	5	t	Рисова січка
15615000-2	Bran	4	t	Висівки
15620000-0	Starches and starch products	3	f	Крохмалі та крохмалепродукти
15621000-7	Corn oil	4	t	Кукурудзяна олія
15622000-4	Glucose and glucose products; fructose and fructose products	4	f	Глюкоза та продукція з глюкози; фруктоза та продукція з фруктози
15622100-5	Glucose and glucose products	5	f	Глюкоза та продукція з глюкози
15622110-8	Glucose	6	t	Глюкоза
15622120-1	Glucose syrup	6	t	Глюкозний сироп
15622300-7	Fructose and fructose products	5	f	Фруктоза та продукція з фруктози
15622310-0	Fructose	6	t	Фруктоза
15622320-3	Fructose preparations	6	f	Вироби з фруктози
15622321-0	Fructose solutions	7	t	Фруктозні розчини
15622322-7	Fructose syrup	7	t	Фруктозний сироп
15623000-1	Starches	4	t	Крохмалі
15624000-8	Tapioca	4	t	Тапіока
15625000-5	Semolina	4	t	Манна крупа
15626000-2	Custard powder	4	t	Заварний крем порошковий
15700000-5	Animal feedstuffs	2	f	Корми для тварин
15710000-8	Prepared animal feeds for farm and other animals	3	f	Готові корми для сільськогосподарських та інших тварин
15711000-5	Fish food	4	t	Корм для риб
15712000-2	Dry fodder	4	t	Сухий корм для худоби
15713000-9	Pet food	4	t	Корм для домашніх тварин
15800000-6	Miscellaneous food products	2	f	Продукти харчування різні
15810000-9	Bread products, fresh pastry goods and cakes	3	f	Хлібопродукти, свіжовипечені хлібобулочні та кондитерські вироби
15811000-6	Bread products	4	f	Хлібопродукти
15811100-7	Bread	5	t	Хліб
15811200-8	Rolls	5	t	Булки
15811300-9	Croissants	5	t	Круасани
15811400-0	Crumpets	5	t	Оладки
15811500-1	Prepared bread products	5	f	Готові хлібопродукти
15811510-4	Sandwiches	6	f	Бутерброди
15811511-1	Prepared sandwiches	7	t	Готові бутерброди
15812000-3	Pastry goods and cakes	4	f	Хлібобулочні та кондитерські вироби
15812100-4	Pastry goods	5	f	Хлібобулочні вироби
15812120-0	Pies	6	f	Пироги
15812121-7	Savoury pies	7	t	Несолодкі пироги
15812122-4	Sweet pies	7	t	Солодкі пироги
15812200-5	Cakes	5	t	Тістечка
15813000-0	Morning goods	4	t	Вироби до сніданку
15820000-2	Rusks and biscuits; preserved pastry goods and cakes	3	f	Сухарі та печиво; пресерви з хлібобулочних і кондитерських виробів
15821000-9	Toasted bread products and pastry goods	4	f	Підсмажені хлібобулочні вироби
15821100-0	Toasted bread products	5	f	Підсмажені хлібні вироби
15821110-3	Toasted bread	6	t	Тости
15821130-9	Crispbread	6	t	Хрусткі хлібці
15821150-5	Rusks	6	t	Сухарі
15821200-1	Sweet biscuits	5	t	Солодке печиво
15830000-5	Sugar and related products	3	f	Цукор і супутня продукція
15831000-2	Sugar	4	f	Цукор
15831200-4	White sugar	5	t	Цукор білий
15831300-5	Maple sugar and maple syrup	5	t	Цукор та сироп кленові
15831400-6	Molasses	5	t	Меляса
15831500-7	Sugar syrups	5	t	Цукрові сиропи
15831600-8	Honey	5	t	Мед
15832000-9	Sugar-manufacture waste	4	t	Відходи цукрового виробництва
15833000-6	Sugar products	4	f	Цукрові продукти
15833100-7	Desserts	5	f	Десерти
15833110-0	Flans	6	t	Відкритий пиріг
15840000-8	Cocoa; chocolate and sugar confectionery	3	f	Какао; шоколад та цукрові кондитерські вироби
15841000-5	Cocoa	4	f	Какао
15841100-6	Cocoa paste	5	t	Какао-паста
15841200-7	Cocoa butter, fat or oil	5	t	Масло, жир чи олія какао
15841300-8	Unsweetened cocoa powder	5	t	Какао-порошок непідсолоджений
15841400-9	Sweetened cocoa powder	5	t	Какао-порошок підсолоджений
15842000-2	Chocolate and sugar confectionery	4	f	Шоколад та цукрові кондитерські вироби
15842100-3	Chocolate	5	t	Шоколад
15842200-4	Chocolate products	5	f	Шоколадні вироби
15842210-7	Drinking chocolate	6	t	Шоколадний напій
15842220-0	Chocolate bars	6	t	Шоколад плитковий
15842300-5	Confectionery	5	f	Солодощі
15842310-8	Boiled sweets	6	t	Карамельні цукерки
15842320-1	Nougat	6	t	Нуга
15842400-6	Fruit, nuts or fruit peel preserved by sugar	5	t	Фрукти, горіхи та фруктові шкірки зацукровані
15850000-1	Pasta products	3	f	Макаронні вироби
15851000-8	Farinaceous products	4	f	Борошняні вироби
15851100-9	Uncooked pasta	5	t	Макаронні вироби сирі
15851200-0	Prepared pasta and couscous	5	f	Приготовані макаронні вироби та кускус
15851210-3	Prepared pasta	6	t	Приготовані макаронні вироби
15851220-6	Stuffed pasta	6	t	Макаронні вироби з начинкою
15851230-9	Lasagne	6	t	Лазанья
15851250-5	Couscous	6	t	Кускус
15851290-7	Canned pasta	6	t	Консервовані макаронні вироби
15860000-4	Coffee, tea and related products	3	f	Кава, чай та супутня продукція
15861000-1	Coffee	4	f	Кава
15861100-2	Roasted coffee	5	t	Смажена зернова кава
15861200-3	Decaffeinated coffee	5	t	Кава без кофеїну
15862000-8	Coffee substitutes	4	t	Замінники кави
15863000-5	Tea	4	f	Чай
15863100-6	Green tea	5	t	Зелений чай
15863200-7	Black tea	5	t	Чорний чай
15864000-2	Preparations of tea or maté	4	f	Напої з чаю та мате
15864100-3	Teabags	5	t	Пакетований чай
15865000-9	Herbal infusions	4	t	Трав’яні настої
15870000-7	Condiments and seasonings	3	f	Заправки та приправи
15871000-4	Vinegar; sauces; mixed condiments; mustard flour and meal; prepared mustard	4	f	Оцет; соуси; приготовані заправки; гірчичний порошок та гірчична макуха; столова гірчиця
15871100-5	Vinegar and vinegar substitutes	5	f	Оцет та замінники оцту
15871110-8	Vinegar or equivalent	6	t	Оцет чи подібні продукти
15871200-6	Sauces, mixed condiments and mixed seasonings	5	f	Соуси, приготовані заправки та суміші приправ
15871210-9	Soya sauce	6	t	Соєвий соус
15871230-5	Tomato ketchup	6	t	Кетчуп
15871250-1	Mustard	6	t	Гірчиця
15871260-4	Sauces	6	t	Соуси
15871270-7	Mixed condiments	6	f	Приготовані заправки
15871273-8	Mayonnaise	7	t	Майонез
15871274-5	Sandwich spreads	7	t	Пасти для бутербродів
15871279-0	Chutney	7	t	Чатні
15872000-1	Herbs and spices	4	f	Трави та спеції
15872100-2	Pepper	5	t	Перець
15872200-3	Spices	5	t	Спеції
15872300-4	Herbs	5	t	Трави
15872400-5	Salt	5	t	Сіль
15872500-6	Ginger	5	t	Імбир
15880000-0	Special nutritional products	3	f	Спеціальні продукти харчування, збагачені поживними речовинами
15881000-7	Homogenised food preparations	4	t	Гомогенізовані продукти харчування
15882000-4	Dietetic products	4	t	Дієтичні продукти
15884000-8	Baby food	4	t	Продукти дитячого харчування
15890000-3	Miscellaneous food products and dried goods	3	f	Продукти харчування та сушені продукти різні
15891000-0	Soups and broths	4	f	Супи та бульйони
15891100-1	Meat soups	5	t	М’ясні супи
15891200-2	Fish soups	5	t	Рибні супи
15891300-3	Mixed soups	5	t	Змішані супи
15891400-4	Soups	5	f	Супи
15891410-7	Soup mixes	6	t	Супові суміші
15891500-5	Broths	5	t	Відвари
15891600-6	Stocks	5	f	Бульйони
15891610-9	Mixes for stocks	6	t	Суміші для бульйонів
15891900-9	Vegetable soups	5	t	Овочеві супи
15892000-7	Vegetable saps, extracts, peptic substances and thickeners	4	f	Рослинні соки, екстракти, пектини та згущувачі
15892100-8	Vegetable saps	5	t	Рослинні соки
15892200-9	Vegetable extracts	5	t	Рослинні екстракти
15892400-1	Thickeners	5	t	Згущувачі
15893000-4	Dry goods	4	f	Сухі продукти
15893100-5	Food mixes	5	t	Харчові суміші
15893200-6	Dessert mixes	5	t	Десертні суміші
15893300-7	Gravy mixes	5	t	Суміші для підлив
15894000-1	Processed food products	4	f	Оброблені продукти харчування
15894100-2	Vegan packs	5	t	Веганські страви
15894200-3	Prepared meals	5	f	Харчування
15894210-6	School meals	6	t	Шкільне харчування
15894220-9	Hospital meals	6	t	Лікарняне харчування
15894300-4	Prepared dishes	5	t	Готові страви
15894400-5	Snacks	5	t	Закуски
15894500-6	Vending-machine ingredients	5	t	Продукти для торгових автоматів
15894600-7	Sandwich fillings	5	t	Начинки для бутербродів
15894700-8	Delicatessen	5	t	Делікатеси
15895000-8	Fast-food products	4	f	Продукти швидкого приготування
15895100-9	Hamburgers	5	t	Гамбургери
15896000-5	Deep-frozen products	4	t	Продукти глибокої заморозки
15897000-2	Canned goods and field rations	4	f	Консерви та пайки
15897100-3	Field rations	5	t	Пайки
15897200-4	Canned goods	5	t	Консерви
15897300-5	Food parcels	5	t	Продуктові набори
15898000-9	Yeast	4	t	Дріжджі
15899000-6	Baking powder	4	t	Пекарний порошок
15900000-7	Beverages, tobacco and related products	2	f	Напої, тютюн та супутня продукція
15910000-0	Distilled alcoholic beverages	3	f	Алкогольні напої дистильовані
15911000-7	Spirituous beverages	4	f	Спиртні напої
15911100-8	Spirits	5	t	Спирти
15911200-9	Liqueurs	5	t	Лікери
15930000-6	Wines	3	f	Вина
15931000-3	Unflavoured wines	4	f	Натуральні вина
15931100-4	Sparkling wine	5	t	Ігристі вина
15931200-5	Table wine	5	t	Столові вина
15931300-6	Port	5	t	Портвейн
15931400-7	Madeira	5	t	Мадера
15931500-8	Grape must	5	t	Виноградне сусло
15931600-9	Sherry	5	t	Херес
15932000-0	Wine lees	4	t	Винний осад
15940000-9	Cider and other fruit wines	3	f	Сидр та інші фруктові вина
15941000-6	Cider	4	t	Сидр
15942000-3	Fruit wines	4	t	Фруктові вина
15950000-2	Non-distilled fermented beverages	3	f	Ферментовані напої недистильовані
15951000-9	Vermouth	4	t	Вермут
15960000-5	Malt beer	3	f	Солодове пиво
15961000-2	Beer	4	f	Пиво
15961100-3	Lager	5	t	Лагер
15962000-9	Brewing or distilling dregs	4	t	Відходи бродіння та дистиляції
15980000-1	Non-alcoholic beverages	3	f	Безалкогольні напої
15981000-8	Mineral water	4	f	Мінеральна вода
15981100-9	Still mineral water	5	t	Мінеральна вода негазована
15981200-0	Sparkling mineral water	5	t	Мінеральна вода газована
15981300-1	Water in solid form	5	f	Вода у твердому стані
15981310-4	Ice	6	t	Лід
15981320-7	Snow	6	t	Сніг
15981400-2	Flavoured mineral waters	5	t	Мінеральна вода ароматизована
15982000-5	Soft drinks	4	f	Інші безалкогольні напої
15982100-6	Fruit squashes	5	t	Фруктові сиропи
15982200-7	Chocolate milk	5	t	Шоколадне молоко
15990000-4	Tobacco, tobacco goods and supplies	3	f	Тютюн, тютюнові вироби та супутні товари
15991000-1	Tobacco goods	4	f	Тютюнові вироби
15991100-2	Cigars	5	t	Сигари
15991200-3	Cigarillos	5	t	Сигарили
15991300-4	Cigarettes	5	t	Сигарети
15992000-8	Tobacco	4	f	Тютюн
15992100-9	Manufactured tobacco	5	t	Оброблений тютюн
15993000-5	Tobacconist supplies	4	t	Супутні товари у тютюновій промисловості
15994000-2	Cigarette paper and filter paper	4	f	Сигаретний та фільтрувальний папір
15994100-3	Cigarette paper	5	t	Сигаретний папір
15994200-4	Filter paper	5	t	Фільтрувальний папір
16000000-5	Agricultural machinery	1	f	Сільськогосподарська техніка
16100000-6	Agricultural and forestry machinery for soil preparation or cultivation	2	f	Сільськогосподарські та лісогосподарські машини для підготовки або обробки ґрунту
16110000-9	Ploughs or disc harrows	3	t	Плуги чи дискові борони
16120000-2	Harrows, scarifiers, cultivators, weeders or hoes	3	t	Борони, скарифікатори, культиватори, прополювачі або розпушувачі
16130000-5	Seeders, planters or transplanters	3	t	Сівалки, саджалки та машини для пересаджування
16140000-8	Manure spreaders	3	f	Розкидачі гною
16141000-5	Fertiliser distributors	4	t	Машини для внесення добрив
16150000-1	Lawn or sports-ground rollers	3	t	Котки для газонів та спортивних майданчиків
16160000-4	Miscellaneous gardening equipment	3	t	Садова техніка різна
16300000-8	Harvesting machinery	2	f	Збиральні машини
16310000-1	Mowers	3	f	Косарки
16311000-8	Lawnmowers	4	f	Газонокосарки
16311100-9	Lawn, park or sports-ground mowers	5	t	Косарки для газонів, парків і спортивних майданчиків
16320000-4	Haymaking machinery	3	t	Сінозбиральні машини
16330000-7	Straw or fodder balers	3	f	Преси для соломи чи сіна
16331000-4	Pick-up balers	4	t	Тюкові преси-підбирачі
16340000-0	Harvesting and threshing machinery	3	t	Збиральні та обмолочувальні машини
16400000-9	Spraying machinery for agriculture or horticulture	2	t	Обприскувальні апарати для використання у сільському господарстві та рослинництві
16500000-0	Self-loading or unloading trailers and semi-trailers for agriculture	2	f	Сільськогосподарські самонавантажувальні та розвантажувальні причепи і напівпричепи
16510000-3	Self-loading trailers for agriculture	3	t	Сільськогосподарські самонавантажувальні причепи
16520000-6	Unloading trailers for agriculture	3	t	Сільськогосподарські розвантажувальні причепи
16530000-9	Self-loading semi-trailers for agriculture	3	t	Сільськогосподарські самонавантажувальні напівпричепи
16540000-2	Unloading semi-trailers for agriculture	3	t	Сільськогосподарські розвантажувальні напівпричепи
16600000-1	Specialist agricultural or forestry machinery	2	f	Сільськогосподарська та лісогосподарська техніка спеціального призначення
16610000-4	Machines for cleaning, sorting or grading eggs, fruit or other produce	3	f	Машини для очищення, сортування чи калібрування яєць, фруктів чи іншої сільськогосподарської продукції
16611000-1	Machines for cleaning produce	4	f	Машини для очищення сільськогосподарської продукції
16611100-2	Machines for cleaning eggs	5	t	Машини для очищення яєць
16611200-3	Machines for cleaning fruit	5	t	Машини для очищення фруктів
16612000-8	Machines for sorting or grading produce	4	f	Машини для сортування чи калібрування сільськогосподарської продукції
16612100-9	Machines for sorting or grading eggs	5	t	Машини для сортування чи калібрування яєць
16612200-0	Machines for sorting or grading fruit	5	t	Машини для сортування чи калібрування фруктів
16613000-5	Machines for cleaning, sorting or grading seed, grain or dried vegetables	4	t	Машини для очищення, сортування чи калібрування насіння, зерна чи сушених овочів
16620000-7	Milking machines	3	t	Доїльні апарати
16630000-0	Machinery for preparing animal feeding stuffs	3	t	Обладнання для приготування кормів для тварин
16640000-3	Bee-keeping machinery	3	t	Обладнання для бджільництва
16650000-6	Poultry-keeping machinery	3	f	Обладнання для птахівництва
16651000-3	Poultry incubators and brooders	4	t	Інкубатори та брудери для свійської птиці
16700000-2	Tractors	2	f	Трактори
16710000-5	Pedestrian-controlled agricultural tractors	3	t	Мотоблоки
16720000-8	Used tractors	3	t	Трактори, що були у використанні
16730000-1	Traction motors	3	t	Тягові електродвигуни
16800000-3	Parts of agricultural and forestry machinery	2	f	Частини для сільськогосподарської та лісогосподарської техніки
16810000-6	Parts of agricultural machinery	3	t	Частини для сільськогосподарської техніки
16820000-9	Parts of forestry machinery	3	t	Частини для лісогосподарської техніки
18000000-9	Clothing, footwear, luggage articles and accessories	1	f	Одяг, взуття, сумки та аксесуари
18100000-0	Occupational clothing, special workwear and accessories	2	f	Формений одяг, спеціальний робочий одяг та аксесуари
18110000-3	Occupational clothing	3	f	Формений одяг
18113000-4	Industrial clothing	4	t	Виробничий одяг
18114000-1	Coveralls	4	t	Робочі комбінезони
18130000-9	Special workwear	3	f	Спеціальний робочий одяг
18132000-3	Flying clothing	4	f	Літний одяг
18132100-4	Flight jackets	5	t	Літні куртки
18132200-5	Flight suits	5	t	Літні комбінезони
18140000-2	Workwear accessories	3	f	Аксесуари до робочого одягу
18141000-9	Work gloves	4	t	Робочі рукавиці
18142000-6	Safety visors	4	t	Захисні козирки
18143000-3	Protective gear	4	t	Захисне спорядження
18200000-1	Outerwear	2	f	Верхній одяг
18210000-4	Coats	3	f	Пальта
18211000-1	Capes	4	t	Накидки
18212000-8	Cloaks	4	t	Плащі
18213000-5	Wind jackets	4	t	Вітрозахисні куртки
18220000-7	Weatherproof clothing	3	f	Штормовий одяг
18221000-4	Waterproof clothing	4	f	Водонепроникний одяг
18221100-5	Waterproof capes	5	t	Водонепроникні накидки
18221200-6	Anoraks	5	t	Анораки
18221300-7	Raincoats	5	t	Дощовики
18222000-1	Corporate clothing	4	f	Корпоративна уніформа
18222100-2	Suits	5	t	Костюми
18222200-3	Ensembles	5	t	Комплекти одягу
18223000-8	Jackets and blazers	4	f	Піджаки і блейзери
18223100-9	Blazers	5	t	Блейзери
18223200-0	Jackets	5	t	Піджаки
18224000-5	Clothing made of coated or impregnated textile fabrics	4	t	Одяг із просочених тканин або з тканин із захисним покриттям
18230000-0	Miscellaneous outerwear	3	f	Верхній одяг різний
18231000-7	Dresses	4	t	Сукні
18232000-4	Skirts	4	t	Спідниці
18233000-1	Shorts	4	t	Шорти
18234000-8	Trousers	4	t	Штани
18235000-5	Pullovers, cardigans and similar articles	4	f	Пуловери, кардигани та подібні вироби
18235100-6	Pullovers	5	t	Пуловери
18235200-7	Cardigans	5	t	Кардигани
18235300-8	Sweatshirts	5	t	Светри
18235400-9	Waistcoats	5	t	Жилети
18300000-2	Garments	2	f	Предмети одягу
18310000-5	Underwear	3	f	Спідня білизна
18311000-2	Slips	4	t	Комбінації
18312000-9	Underpants	4	t	Чоловічі труси
18313000-6	Panties	4	t	Жіночі труси
18314000-3	Bathrobes	4	t	Купальні халати
18315000-0	Stockings	4	t	Панчохи
18316000-7	Tights	4	t	Колготи
18317000-4	Socks	4	t	Шкарпетки
18318000-1	Nightwear	4	f	Нічна білизна
18318100-2	Nightshirts	5	t	Нічні сорочки
18318200-3	Dressing gowns	5	t	Халати
18318300-4	Pyjamas	5	t	Піжами
18318400-5	Vests	5	t	Майки
18318500-6	Nightdresses	5	t	Жіночі нічні сорочки
18320000-8	Brassieres, corsets, suspenders and similar articles	3	f	Бюстгальтери, корсети, підв’язки та подібні вироби
18321000-5	Brassieres	4	t	Бюстгальтери
18322000-2	Corsets	4	t	Корсети
18323000-9	Suspenders	4	t	Підв’язки
18330000-1	T-shirts and shirts	3	f	Футболки та сорочки
18331000-8	T-shirts	4	t	Футболки
18332000-5	Shirts	4	t	Сорочки
18333000-2	Polo shirts	4	t	Сорочки поло
18400000-3	Special clothing and accessories	2	f	Спеціальний одяг та аксесуари
18410000-6	Special clothing	3	f	Спеціальний одяг
18411000-3	Baby clothing	4	t	Дитячий одяг
18412000-0	Sportswear	4	f	Спортивний одяг
18412100-1	Tracksuits	5	t	Спортивні костюми
18412200-2	Sports shirts	5	t	Спортивні футболки
18412300-3	Ski suits	5	t	Лижні комбінезони
18412800-8	Swimwear	5	t	Купальні костюми
18420000-9	Clothing accessories	3	f	Аксесуари для одягу
18421000-6	Handkerchiefs	4	t	Носові хустинки
18422000-3	Scarves	4	t	Шарфи
18423000-0	Ties	4	t	Краватки
18424000-7	Gloves	4	f	Рукавички
18424300-0	Disposable gloves	5	t	Одноразові рукавички
18424400-1	Mittens	5	t	Рукавиці
18424500-2	Gauntlets	5	t	Рукавиці з крагами
18425000-4	Belts	4	f	Ремені
18425100-5	Bandoliers	5	t	Патронташі
18440000-5	Hats and headgear	3	f	Капелюхи та головні убори
18441000-2	Hats	4	t	Капелюхи
18443000-6	Headgear and headgear accessories	4	f	Головні убори та аксесуари
18443100-7	Headbands	5	t	Головні пов’язки
18443300-9	Headgear	5	f	Головні убори
18443310-2	Berets	6	t	Берети
18443320-5	Field caps	6	t	Кепі
18443330-8	Hoods	6	t	Каптури
18443340-1	Caps	6	t	Кепки
18443400-0	Chin straps for headgear	5	t	Підборідні паски до головних уборів
18443500-1	Visors	5	t	Козирки
18444000-3	Protective headgear	4	f	Захисні головні убори
18444100-4	Safety headgear	5	f	Запобіжні головні убори
18444110-7	Helmets	6	f	Шоломи
18444111-4	Crash helmets	7	t	Мотоциклетні шоломи
18444112-1	Bicycle helmets	7	t	Велосипедні каски
18444200-5	Hard hats	5	t	Захисні каски
18450000-8	Fasteners (clothing)	3	f	Застібки (одяг)
18451000-5	Buttons	4	f	Ґудзики
18451100-6	Parts of buttons	5	t	Деталі до ґудзиків
18452000-2	Safety pins	4	t	Англійські шпильки
18453000-9	Zip fasteners	4	t	Застібки-блискавки
18500000-4	Jewellery, watches and related articles	2	f	Ювелірні вироби, годинники та супутні товари
18510000-7	Jewellery and related articles	3	f	Ювелірні вироби та супутні товари
18511000-4	Precious stone for jewellery	4	f	Коштовне каміння для ювелірних виробів
18511100-5	Diamonds	5	t	Діаманти
18511200-6	Rubies	5	t	Рубіни
18511300-7	Emeralds	5	t	Смарагди
18511400-8	Opal stone	5	t	Опал
18511500-9	Quartz stone	5	t	Кварц
18511600-0	Tourmaline stone	5	t	Турмалін
18512000-1	Coins and medals	4	f	Монети та медалі
18512100-2	Coins	5	t	Монети
18512200-3	Medals	5	t	Медалі
18513000-8	Articles of jewellery	4	f	Ювелірні вироби
18513100-9	Pearls	5	t	Перли
18513200-0	Goldsmiths' wares	5	t	Вироби із золота
18513300-1	Articles of precious metal	5	t	Вироби з благородних металів
18513400-2	Articles of precious or semi-precious stones	5	t	Вироби з коштовного чи напівкоштовного каміння
18513500-3	Silversmiths' wares	5	t	Вироби зі срібла
18520000-0	Personal horology	3	f	Персональні хронометри
18521000-7	Watches	4	f	Годинники
18521100-8	Glass for watches	5	t	Годинникове скло
18522000-4	Wristwatches	4	t	Наручні годинники
18523000-1	Stopwatches	4	t	Секундоміри
18530000-3	Presents and rewards	3	t	Подарунки та нагороди
18600000-5	Furs and articles of fur	2	f	Хутра та хутряні вироби
18610000-8	Fur articles	3	f	Хутряні вироби
18611000-5	Fur skins	4	t	Хутрові шкурки
18612000-2	Fur clothing	4	t	Хутровий одяг
18613000-9	Artificial fur articles	4	t	Вироби зі штучного хутра
18620000-1	Furs	3	t	Хутра
18800000-7	Footwear	2	f	Взуття
18810000-0	Footwear other than sports and protective footwear	3	f	Взуття різне, крім спортивного та захисного
18811000-7	Waterproof footwear	4	t	Водонепроникне взуття
18812000-4	Footwear with rubber or plastic parts	4	f	Взуття з гумовими чи пластмасовими вставками
18812100-5	Sandals with uppers of rubber or plastics	5	t	Сандалі з гумовим чи пластмасовим верхом
18812200-6	Rubber boots	5	t	Гумові чоботи
18812300-7	Town footwear with rubber or plastic uppers	5	t	Взуття для міста з гумовим чи пластмасовим верхом
18812400-8	Flip-flops	5	t	В’єтнамки
18813000-1	Footwear with uppers of leather	4	f	Взуття зі шкіряним верхом
18813100-2	Sandals	5	t	Сандалі
18813200-3	Slippers	5	t	Капці
18813300-4	Town footwear	5	t	Взуття для міста
18814000-8	Footwear with uppers of textile materials	4	t	Взуття з тканинним верхом
18815000-5	Boots	4	f	Чоботи
18815100-6	Ankle boots	5	t	Черевики
18815200-7	Calf boots	5	t	Чоботи з халявою три чверті
18815300-8	Knee boots	5	t	Чоботи до колін
18815400-9	Waders	5	t	Болотні чоботи
18816000-2	Galoshes	4	t	Калоші
18820000-3	Sports footwear	3	f	Спортивне взуття
18821000-0	Ski footwear	4	f	Лижне взуття
18821100-1	Cross-country ski footwear	5	t	Взуття для бігових лиж
18822000-7	Training shoes	4	t	Кросівки
18823000-4	Climbing boots	4	t	Альпіністські черевики
18824000-1	Football boots	4	t	Футбольні бутси
18830000-6	Protective footwear	3	f	Захисне взуття
18831000-3	Footwear incorporating a protective metal toecap	4	t	Взуття із захисним металевим підноском
18832000-0	Special footwear	4	f	Спеціальне взуття
18832100-1	Flying footwear	5	t	Літне взуття
18840000-9	Parts of footwear	3	f	Частини взуття
18841000-6	Footwear uppers	4	t	Верх взуття
18842000-3	Soles	4	t	Підошви
18843000-0	Heels	4	t	Підбори
18900000-8	Luggage, saddlery, sacks and bags	2	f	Сумки, шорно-сідельні вироби, мішки та пакети
18910000-1	Saddlery	3	f	Шорно-сідельні вироби
18911000-8	Saddles	4	t	Сідла
18912000-5	Riding crops	4	t	Стеки
18913000-2	Whips	4	t	Батоги
18920000-4	Luggage	3	f	Сумки
18921000-1	Suitcases	4	t	Валізи
18923000-5	Pouches and wallets	4	f	Портмоне і гаманці
18923100-6	Pouches	5	t	Портмоне
18923200-7	Wallets	5	t	Гаманці
18924000-2	Trunks	4	t	Скрині
18925000-9	Water-bottle holders and holsters	4	f	Фляготримачі та кобури
18925100-0	Water-bottle holders	5	t	Фляготримачі
18925200-1	Holsters	5	t	Кобури
18929000-7	Toilet cases	4	t	Несесери
18930000-7	Sacks and bags	3	f	Мішки та пакети
18931000-4	Travel bags	4	f	Дорожні сумки
18931100-5	Rucksacks	5	t	Рюкзаки
18932000-1	Sports bags	4	t	Спортивні сумки
18933000-8	Mail or parcel bags	4	f	Поштові сумки чи сумки через плече
18933100-9	Post pouches	5	t	Поштові мішки
18934000-5	Kitbags	4	t	Сумка для інструментів
18935000-2	Laundry bags	4	t	Мішок для прання
18936000-9	Textile bags	4	t	Тканинні сумки
18937000-6	Goods-packing sacks	4	f	Пакувальні мішки
18937100-7	Goods-packing bags	5	t	Пакувальні пакети
18938000-3	Padded bags	4	t	Бандерольні конверти
18939000-0	Handbags	4	t	Жіночі сумки
19000000-6	Leather and textile fabrics, plastic and rubber materials	1	f	Шкіряні та текстильні, пластмасові та гумові матеріали
19100000-7	Leather	2	f	Шкіра
19110000-0	Chamois leather	3	t	Замша
19120000-3	Leather of bovine or equine animals	3	t	Шкіра зі шкур великої рогатої худоби родини бикових чи шкур тварин родини конячих
19130000-6	Leather of sheep, goats or pigs	3	f	Шкіра зі шкур овець, кіз і свиней
19131000-3	Sheep- or lamb-skin leather	4	t	Шкіра зі шкур овець та ягнят
19132000-0	Goat- or kid-skin leather	4	t	Шкіра зі шкур кіз і козенят
19133000-7	Pig leather	4	t	Шкіра зі шкур свиней
19140000-9	Leather of other animals, composite leather and other leather	3	f	Шкіра зі шкур інших тварин, комбінована шкіра та інші види шкіри
19141000-6	Leather of other animals	4	t	Шкіра зі шкур інших тварин
19142000-3	Composite leather	4	t	Комбінована шкіра
19143000-0	Imitation leather	4	t	Штучна шкіра
19144000-7	Patent leather	4	t	Лакована шкіра
19160000-5	Watch straps	3	t	Ремінці для годинників
19170000-8	Leather articles used in machinery or mechanical appliances	3	t	Шкіряні вироби, що використовуються в техніці чи механічних пристроях
19200000-8	Textile fabrics and related items	2	f	Текстильні вироби та супутні товари
19210000-1	Woven fabrics	3	f	Натуральні тканини
19211000-8	Synthetic woven fabrics	4	f	Синтетичні тканини
19211100-9	Mixed woven fabrics	5	t	Змішані тканини
19212000-5	Woven fabrics of cotton	4	f	Бавовняні тканини
19212100-6	Ticking	5	t	Тік
19212200-7	Denim	5	t	Денім
19212300-8	Canvas	5	f	Полотно
19212310-1	Canvas items	6	t	Полотняні вироби
19212400-9	Poplin	5	t	Поплін
19212500-0	Webbing	5	f	Тасьма
19212510-3	Webbing straps	6	t	Лямки з тасьми
19220000-4	Woollen fabrics	3	t	Вовняні тканини
19230000-7	Linen fabrics	3	f	Лляні тканини
19231000-4	Linen	4	t	Лляні вироби
19240000-0	Special fabrics	3	f	Спеціальні тканини
19241000-7	Pile	4	t	Ворсові тканини
19242000-4	Terry towelling	4	t	Махрова тканина
19243000-1	Upholstery fabrics	4	t	Оббивальні тканини
19244000-8	Curtain fabrics	4	t	Гардинні тканини
19245000-5	Lining fabrics	4	t	Підкладкові тканини
19250000-3	Knitted or crocheted fabrics	3	f	Трикотажні чи в’язані тканини
19251000-0	Knitted fabrics	4	f	Трикотажні тканини
19251100-1	Pile fabrics	5	t	Ворсові трикотажні тканини
19252000-7	Crocheted fabrics	4	t	В’язані тканини
19260000-6	Cloth	3	t	Текстильні матеріали
19270000-9	Non-woven fabrics	3	t	Неткані матеріали
19280000-2	Animal wool, hides and skins	3	f	Вовна, шкури та шкіра тварин
19281000-9	Wool	4	t	Вовна
19282000-6	Animal skins	4	t	Шкіра тварин
19283000-3	Bird skins and feathers	4	t	Пташині шкіра та пір’я
19400000-0	Textile yarn and thread	2	f	Пряжа та текстильні нитки
19410000-3	Natural textile fibres	3	t	Натуральні текстильні волокна
19420000-6	Artificial textile fibres	3	t	Штучні текстильні волокна
19430000-9	Textile yarn and thread of natural fibres	3	f	Пряжа та текстильні нитки з натуральних волокон
19431000-6	Silk yarn	4	t	Шовкова пряжа
19432000-3	Wool yarn	4	t	Вовняна пряжа
19433000-0	Cotton yarn	4	t	Бавовняна пряжа
19434000-7	Flax yarn	4	t	Лляна пряжа
19435000-4	Sewing thread and yarn of natural fibres	4	f	Швейні нитки та пряжа з натуральних волокон
19435100-5	Sewing thread	5	t	Швейні нитки
19435200-6	Knitting yarn	5	t	В’язальна пряжа
19436000-1	Vegetable textile-fibre yarn	4	t	Пряжа рослинного походження
19440000-2	Synthetic yarn or thread	3	f	Синтетичні нитки та пряжа
19441000-9	Synthetic yarn	4	t	Синтетична пряжа
19442000-6	Synthetic thread	4	f	Синтетичні нитки
19442100-7	Synthetic sewing thread	5	t	Синтетичні швейні нитки
19442200-8	Synthetic knitting yarn	5	t	Синтетична трикотажна пряжа
19500000-1	Rubber and plastic materials	2	f	Гумові та пластмасові матеріали
19510000-4	Rubber products	3	f	Гумові вироби
19511000-1	Rubber inner tubes, treads and flaps	4	f	Гумові пневматичні шини, протектори та ободові стрічки
19511100-2	Tyre flaps	5	t	Ободові стрічки
19511200-3	Inner tubes	5	t	Гумові пневматичні шини
19511300-4	Tyre treads	5	t	Шинні протектори
19512000-8	Unvulcanised rubber articles	4	t	Вироби з невулканізованої гуми
19513000-5	Rubberised textile fabrics	4	f	Прогумовані тканини
19513100-6	Tyre-cord fabric	5	t	Кордна тканина для шин
19513200-7	Adhesive tape of rubberised textiles	5	t	Ізоляційна стрічка
19514000-2	Reclaimed rubber	4	t	Регенерована гума
19520000-7	Plastic products	3	f	Пластмасові вироби
19521000-4	Polystyrene products	4	f	Вироби з полістиролу
19521100-5	Polystyrene sheeting	5	t	Листи з полістиролу
19521200-6	Polystyrene slabs	5	t	Пластини з полістиролу
19522000-1	Resins	4	f	Смоли
19522100-2	Epoxy resin	5	f	Епоксидна смола
19522110-5	Epoxy-resin tubing	6	t	Труби з епоксидним покриттям
19600000-2	Leather, textile, rubber and plastic waste	2	f	Шкіряні, текстильні, гумові та пластмасові відходи
19610000-5	Leather waste	3	t	Шкіряні відходи
19620000-8	Textile waste	3	t	Текстильні відходи
19630000-1	Rubber waste	3	t	Гумові відходи
19640000-4	Polythene waste and refuse sacks and bags	3	t	Поліетиленові мішки та пакети для сміття
19700000-3	Synthetic rubber and fibres	2	f	Синтетичний каучук та синтетичні волокна
19710000-6	Synthetic rubber	3	t	Синтетичний каучук
19720000-9	Synthetic fibres	3	f	Синтетичні волокна
19721000-6	Synthetic filament tow	4	t	Синтетичне клоччя
19722000-3	High-tenacity yarn	4	t	Високоміцна пряжа
19723000-0	Single textured yarn	4	t	Моноволокна пряжа
19724000-7	Synthetic monofilament	4	t	Синтетичне моноволокно
19730000-2	Artificial fibres	3	f	Штучні волокна
19731000-9	Artificial staple fibres	4	t	Штучні штапельні волокна
19732000-6	Polypropylene	4	t	Поліпропілен
19733000-3	Artificial textured yarn	4	t	Штучні текстуровані нитки
22000000-0	Printed matter and related products	1	f	Друкована та супутня продукція
22100000-1	Printed books, brochures and leaflets	2	f	Друковані книги, брошури та проспекти
22110000-4	Printed books	3	f	Друковані книги
22111000-1	School books	4	t	Шкільні підручники
22112000-8	Textbooks	4	t	Підручники
22113000-5	Library books	4	t	Бібліотечні книги
22114000-2	Dictionaries, maps, music books and other books	4	f	Словники, мапи, нотні збірки та інші книги
22114100-3	Dictionaries	5	t	Словники
22114200-4	Atlases	5	t	Атласи
22114300-5	Maps	5	f	Мапи
22114310-8	Cadastral maps	6	f	Кадастрові мапи
22114311-5	Blueprints	7	t	Світлокопії
22114400-6	Printed music	5	t	Ноти
22114500-7	Encyclopaedias	5	t	Енциклопедії
22120000-7	Publications	3	f	Видання
22121000-4	Technical publications	4	t	Технічні видання
22130000-0	Directories	3	t	Довідники
22140000-3	Leaflets	3	t	Проспекти
22150000-6	Brochures	3	t	Брошури
22160000-9	Booklets	3	t	Буклети
22200000-2	Newspapers, journals, periodicals and magazines	2	f	Газети, періодичні спеціалізовані та інші періодичні видання і журнали
22210000-5	Newspapers	3	f	Газети
22211000-2	Journals	4	f	Періодичні спеціалізовані видання
22211100-3	Official journals	5	t	Офіційні вісники
22212000-9	Periodicals	4	f	Періодичні видання
22212100-0	Serials	5	t	Серійні видання
22213000-6	Magazines	4	t	Журнали
22300000-3	Postcards, greeting cards and other printed matter	2	f	Поштові та вітальні листівки й інша друкована продукція
22310000-6	Postcards	3	f	Поштові листівки
22312000-0	Pictures	4	t	Малюнки
22313000-7	Transfers	4	t	Перенесені зображення
22314000-4	Designs	4	t	Кресленики
22315000-1	Photographs	4	t	Фотографії
22320000-9	Greeting cards	3	f	Вітальні листівки
22321000-6	Christmas cards	4	t	Новорічні та різдвяні листівки
22400000-4	Stamps, cheque forms, banknotes, stock certificates, trade advertising material, catalogues and manuals	2	f	Марки, чекові бланки, банкноти, сертифікати акцій, рекламні матеріали, каталоги та посібники
22410000-7	Stamps	3	f	Марки
22411000-4	Christmas stamps	4	t	Новорічні та різдвяні марки
22412000-1	New stamps	4	t	Нові марки
22413000-8	Savings stamps	4	t	Ощадні марки
24000000-4	Chemical products	1	f	Хімічна продукція
22414000-5	Mail stamp holders	4	t	Котушки для маркових стрічок
22420000-0	Stamp-impressed paper	3	t	Папір із водяними знаками
22430000-3	Banknotes	3	t	Банкноти
22440000-6	Cheque forms	3	t	Чекові бланки
22450000-9	Security-type printed matter	3	f	Друкована продукція з елементами захисту
22451000-6	Passports	4	t	Паспорти
22452000-3	Postal orders	4	t	Платіжні доручення
22453000-0	Car-tax discs	4	t	Наліпки про сплату податку на транспортний засіб
22454000-7	Driving licences	4	t	Водійські посвідчення
22455000-4	ID cards	4	f	Посвідчення особи
22455100-5	Identity bracelet	5	t	Ідентифікаційні браслети
22456000-1	Permits	4	t	Дозволи
22457000-8	Entrance cards	4	t	Перепустки
22458000-5	Bespoke printed matter	4	t	Друкована продукція на замовлення
22459000-2	Tickets	4	f	Квитки
22459100-3	Advertising stickers and strips	5	t	Рекламні стікери та стрічки
22460000-2	Trade-advertising material, commercial catalogues and manuals	3	f	Рекламні матеріали, каталоги товарів та посібники
22461000-9	Catalogues	4	f	Каталоги
22461100-0	List holders	5	t	Підставка із затискачем для паперу
22462000-6	Advertising material	4	t	Рекламні матеріали
22470000-5	Manuals	3	f	Посібники
22471000-2	Computer manuals	4	t	Інструкції з користування комп’ютерами
22472000-9	Instruction manuals	4	t	Інструкції
22473000-6	Technical manuals	4	t	Технічні інструкції
22500000-5	Printing plates or cylinders or other media for use in printing	2	f	Друкарські форми чи циліндри або інші носії для друку
22510000-8	Offset plates	3	t	Офсетні пластини
22520000-1	Dry-etching equipment	3	f	Обладнання для сухого витравлювання
22521000-8	Embossing equipment	4	t	Штампувальне обладнання
22600000-6	Ink	2	f	Чорнила
22610000-9	Printing ink	3	f	Друкарська фарба
22611000-6	Intaglio ink	4	t	Фарба для глибокого друку
22612000-3	India ink	4	t	Туш
22800000-8	Paper or paperboard registers, account books, binders, forms and other articles of printed stationery	2	f	Паперові чи картонні реєстраційні журнали, бухгалтерські книги, швидкозшивачі, бланки та інші паперові канцелярські вироби
22810000-1	Paper or paperboard registers	3	f	Паперові чи картонні реєстраційні журнали
22813000-2	Account books	4	t	Бухгалтерські книги
22814000-9	Receipt books	4	t	Квитанційні книжки
22815000-6	Notebooks	4	t	Записники
22816000-3	Pads	4	f	Папірці для нотаток
22816100-4	Note pads	5	t	Блокноти
22816200-5	Shorthand notebook	5	t	Блокноти для скоропису
22816300-6	Sticky-note pads	5	t	Стікери
22817000-0	Diaries or personal organisers	4	t	Щоденники та персональні органайзери
22819000-4	Address books	4	t	Адресні книги
22820000-4	Forms	3	f	Бланки
22821000-1	Electoral forms	4	t	Виборчі бюлетені
22822000-8	Business forms	4	f	Формуляри
22822100-9	Continuous business forms	5	t	Нерозрізні формуляри
22822200-0	Non-continuous business forms	5	t	Розрізні формуляри
22830000-7	Exercise books	3	f	Зошити
22831000-4	Refills for school notebooks	4	t	Змінні блоки для записників
22832000-1	Exercise papers	4	t	Зошити із завданнями
22840000-0	Albums for samples	3	f	Альбоми для зразків
22841000-7	Collection albums	4	f	Альбоми для колекцій
22841100-8	Stamp books	5	t	Альбоми для марок
22841200-9	Philatelic binders	5	t	Клясери
22850000-3	Binders and related accessories	3	f	Швидкозшивачі та супутнє приладдя
22851000-0	Binders	4	t	Швидкозшивачі
22852000-7	Folders	4	f	Теки
22852100-8	File covers	5	t	Теки з файлами
22853000-4	File holders	4	t	Лотки для паперів
22900000-9	Miscellaneous printed matter	2	f	Друкована продукція різна
22990000-6	Newsprint, handmade paper and other uncoated paper or paperboard for graphic purposes	3	f	Газетний папір, папір ручного виготовлення та інший некрейдований папір або картон для графічних цілей
22991000-3	Paper for newsprint	4	t	Газетний папір
22992000-0	Hand-made paper or paperboard	4	t	Папір або картон ручного виготовлення
22993000-7	Photosensitive, heat-sensitive or thermographic paper and paperboard	4	f	Фоточутливі, термочутливі та термографічні папір та картон
22993100-8	Photosensitive paper or paperboard	5	t	Фоточутливі папір або картон
22993200-9	Heat-sensitive paper or paperboard	5	t	Термочутливі папір або картон
22993300-0	Thermographic paper or paperboard	5	t	Термографічні папір або картон
22993400-1	Corrugated paper or paperboard	5	t	Гофровані папір або картон
24100000-5	Gases	2	f	Гази
24110000-8	Industrial gases	3	f	Промислові гази
24111000-5	Hydrogen, argon, rare gases, nitrogen and oxygen	4	f	Водень, аргон, інертні гази, азот і кисень
24111100-6	Argon	5	t	Аргон
24111200-7	Rare gases	5	t	Інертні гази
24111300-8	Helium	5	t	Гелій
24111400-9	Neon	5	t	Неон
24111500-0	Medical gases	5	t	Медичні гази
24111600-1	Hydrogen	5	t	Водень
24111700-2	Nitrogen	5	t	Азот
24111800-3	Liquid nitrogen	5	t	Рідкий азот
24111900-4	Oxygen	5	t	Кисень
24112000-2	Inorganic oxygen compounds	4	f	Неорганічні сполуки кисню
24112100-3	Carbon dioxide	5	t	Діоксид вуглецю
24112200-4	Nitrogen oxides	5	t	Оксиди азоту
24112300-5	Gaseous inorganic oxygen compounds	5	t	Газоподібні неорганічні сполуки кисню
24113000-9	Liquid and compressed air	4	f	Скраплене та стиснене повітря
24113100-0	Liquid air	5	t	Скраплене повітря
24113200-1	Compressed air	5	t	Стиснене повітря
24200000-6	Dyes and pigments	2	f	Барвники та пігменти
24210000-9	Oxides, peroxides and hydroxides	3	f	Оксиди, пероксиди та гідроксиди
24211000-6	Zinc oxide and peroxide, titanium oxide, dyes and pigments	4	f	Оксид та пероксид цинку, оксид титану, барвники та пігменти
24211100-7	Zinc oxide	5	t	Оксид цинку
24211200-8	Zinc peroxide	5	t	Пероксид цинку
24211300-9	Titanium oxide	5	t	Оксид титану
24212000-3	Chromium, manganese, magnesium, lead and copper oxides and hydroxides	4	f	Оксиди та пероксиди хрому, марганцю, магнію, свинцю та міді
24212100-4	Chromium oxide	5	t	Оксид хрому
24212200-5	Manganese oxide	5	t	Оксид марганцю
24212300-6	Lead oxide	5	t	Оксид свинцю
24212400-7	Copper oxide	5	t	Оксид міді
24212500-8	Magnesium oxide	5	t	Оксид магнію
24212600-9	Hydroxides for dyes and pigments	5	f	Гідроксиди для виготовлення барвників і пігментів
24212610-2	Chromium hydroxide	6	t	Гідроксид хрому
24212620-5	Manganese hydroxide	6	t	Гідроксид марганцю
24212630-8	Lead hydroxide	6	t	Гідроксид свинцю
24212640-1	Copper hydroxide	6	t	Гідроксид міді
24212650-4	Magnesium hydroxide	6	t	Гідроксид магнію
24213000-0	Hydrated lime	4	t	Гашене вапно
24220000-2	Tanning extracts, dyeing extracts, tannins and colouring matter	3	f	Екстракти дубильних речовин, екстракти барвників, дубильні та фарбувальні речовини
24221000-9	Dyeing extracts	4	t	Екстракти барвників
24222000-6	Tanning extracts	4	t	Екстракти дубильних речовин
24223000-3	Tannins	4	t	Дубильні речовини
24224000-0	Colouring matter	4	t	Фарбувальні речовини
24225000-7	Tanning preparations	4	t	Продукти на основі дубильних речовин
24300000-7	Basic inorganic and organic chemicals	2	f	Основні органічні та неорганічні хімічні речовини
24310000-0	Basic inorganic chemicals	3	f	Основні неорганічні хімічні речовини
24311000-7	Chemical elements, inorganic acids and compounds	4	f	Хімічні елементи, неорганічні кислоти та сполуки
24311100-8	Metalloids	5	f	Металоїди
24311110-1	Phosphides	6	t	Фосфіди
24311120-4	Carbides	6	t	Карбіди
24311130-7	Hydrides	6	t	Гідриди
24311140-0	Nitrides	6	t	Нітриди
24311150-3	Azides	6	t	Азиди
24311160-6	Silicides	6	t	Силіциди
24311170-9	Borides	6	t	Бориди
24311180-2	Refined sulphur	6	t	Очищена сірка
24311200-9	Halogen	5	t	Галогени
24311300-0	Alkali metals	5	f	Лужні метали
24311310-3	Mercury	6	t	Ртуть
24311400-1	Hydrogen chloride, inorganic acids, silicon dioxide and sulphur dioxide	5	f	Хлороводень, неорганічні кислоти, діоксид кремнію та діоксид сірки
24311410-4	Inorganic acids	6	f	Неорганічні кислоти
24311411-1	Sulphuric acid	7	t	Сірчана кислота
24311420-7	Phosphoric acid	6	t	Фосфорна кислота
24311430-0	Polyphosphoric acids	6	t	Поліфосфорні кислоти
24311440-3	Hexafluorosilicic acid	6	t	Гексафторсиліцієва кислота
24311450-6	Sulphur dioxide	6	t	Діоксид сірки
24311460-9	Silicon dioxide	6	t	Діоксид кремнію
24311470-2	Hydrogen chloride	6	t	Хлороводень
24311500-2	Hydroxides as basic inorganic chemicals	5	f	Гідроксиди як основні неорганічні хімічні речовини
24311510-5	Metal oxides	6	f	Оксиди металів
24311511-2	Iron pyrites and iron oxides	7	t	Пірити та оксиди заліза
24311520-8	Sodium hydroxide	6	f	Гідроксид натрію
24311521-5	Caustic soda	7	t	Каустична сода
24311522-2	Liquid soda	7	t	Рідка сода
24311600-3	Sulphur compounds	5	t	Сполуки сірки
24311700-4	Sulphur	5	t	Сірка
24311800-5	Carbon	5	t	Вуглець
24311900-6	Chlorine	5	t	Хлор
24312000-4	Metallic halogenates; hypochlorites, chlorates and perchlorates	4	f	Галогенати металів; гіпохлорити, хлорати та перхлорати
24312100-5	Metallic halogenates	5	f	Галогенати металів
24312110-8	Sodium hexafluorosilicate	6	t	Гексафторсилікат натрію
24312120-1	Chlorides	6	f	Хлориди
24312121-8	Aluminium chloride	7	t	Хлорид алюмінію
24312122-5	Ferric chloride	7	t	Хлорид заліза
24312123-2	Poly aluminium chloride	7	t	Поліхлорид алюмінію
24312130-4	Aluminium chlorohydrate	6	t	Хлоргідрат алюмінію
24312200-6	Hypochlorites and chlorates	5	f	Гіпохлорити та хлорати
24312210-9	Sodium chlorite	6	t	Хлорит натрію
24312220-2	Sodium hypochlorite	6	t	Гіпохлорит натрію
24313000-1	Sulphides, sulphates; nitrates, phosphates and carbonates	4	f	Сульфіди, сульфати; нітрати, фосфати та карбонати
24313100-2	Sulphides, sulphites and sulphates	5	f	Сульфіди, сульфіти та сульфати
24313110-5	Miscellaneous sulphides	6	f	Сульфіди різні
24313111-2	Hydrogen sulphide	7	t	Сірководень
24313112-9	Polysulphides	7	t	Полісульфіди
24313120-8	Sulphates	6	f	Сульфати
24313121-5	Sodium thiosulphate	7	t	Тіосульфат натрію
24313122-2	Ferric sulphate	7	t	Сульфат заліза (ІІІ)
24313123-9	Sulphate of aluminium	7	t	Сульфат алюмінію
24313124-6	Sodium sulphate	7	t	Сульфат натрію
24313125-3	Iron sulphate	7	t	Сульфат заліза (ІІ)
24313126-0	Copper sulphate	7	t	Сульфат міді
24313200-3	Phosphinates, phosphonates, phosphates and polyphosphates	5	f	Фосфінати, фосфонати, фосфати та полі фосфати
24313210-6	Sodium hexametaphosphate	6	t	Гексаметафосфат натрію
24313220-9	Phosphates	6	t	Фосфати
24313300-4	Carbonates	5	f	Карбонати
24313310-7	Sodium carbonate	6	t	Карбонат натрію
24313320-0	Sodium bicarbonate	6	t	Бікарбонат натрію
24313400-5	Nitrates	5	t	Нітрати
24314000-8	Miscellaneous metal acid salts	4	f	Кислі солі металів різні
24314100-9	Potassium permanganate	5	t	Перманганат калію
24314200-0	Oxometallic acid salts	5	t	Кислі солі оксометалів
24315000-5	Miscellaneous inorganic chemicals	4	f	Неорганічні хімічні речовини різні
24315100-6	Heavy water, other isotopes and their compounds	5	t	Важка вода, інші ізотопи та їх сполуки
24315200-7	Cyanide, cyanide oxide, fulminates, cyanates, silicates, borates, perborates, salts of inorganic acids	5	f	Ціанід, оксид ціаніду, фульмінати, ціанати, силікати, борати, перборати, солі неорганічних кислот
24315210-0	Cyanides	6	t	Ціаніди
24315220-3	Cyanide oxide	6	t	Оксид ціаніду
24315230-6	Fulminates	6	t	Фульмінати
24315240-9	Cyanates	6	t	Ціанати
24315300-8	Hydrogen peroxide	5	t	Пероксид водню
24315400-9	Piezo-electric quartz	5	t	П’єзоелектричний кварц
24315500-0	Compounds of rare earth metals	5	t	Сполуки рідкісноземельних металів
24315600-1	Silicates	5	f	Силікати
24315610-4	Sodium silicate	6	t	Силікат натрію
24315700-2	Borates and perborates	5	t	Борати та перборати
24316000-2	Distilled water	4	t	Дистильована вода
24317000-9	Synthetic stones	4	f	Штучне каміння
24317100-0	Synthetic precious stones	5	t	Штучне коштовне каміння
24317200-1	Synthetic semi-precious stones	5	t	Штучне напівкоштовне каміння
24320000-3	Basic organic chemicals	3	f	Основні органічні хімічні речовини
24321000-0	Hydrocarbons	4	f	Вуглеводні
24321100-1	Saturated hydrocarbons	5	f	Насичені вуглеводні
24321110-4	Saturated acyclic hydrocarbons	6	f	Насичені нециклічні вуглеводні
24321111-1	Methane	7	t	Метан
24321112-8	Ethylene	7	t	Етилен
24321113-5	Propene	7	t	Пропілен
24321114-2	Butene	7	t	Бутилен
24321115-9	Acetylene	7	t	Ацетилен
24321120-7	Saturated cyclic hydrocarbons	6	t	Насичені циклічні вуглеводні
24321200-2	Unsaturated hydrocarbons	5	f	Ненасичені вуглеводні
24321210-5	Unsaturated acyclic hydrocarbons	6	t	Ненасичені нециклічні вуглеводні
24321220-8	Unsaturated cyclic hydrocarbons	6	f	Ненасичені циклічні вуглеводні
24321221-5	Benzene	7	t	Бензол
24321222-2	Toluene	7	t	Толуол
24321223-9	O-xylenes	7	t	Ортоксилол
24321224-6	M-xylenes	7	t	Метаксилол
24321225-3	Styrene	7	t	Стирол
24321226-0	Ethylbenzene	7	t	Етилбензол
24321300-3	Other halogenated derivatives of hydrocarbons	5	f	Інші галогенопохідні вуглеводнів
24321310-6	Tetrachloroethylene	6	t	Тетрахлоретилен
24321320-9	Carbon tetrachloride	6	t	Тетрахлорид вуглецю
24530000-8	Primary-form polymers of styrene	3	t	Стиролові полімери у первинній формі
24540000-1	Primary-form of vinyl polymers	3	f	Вінілові полімери у первинній формі
24322000-7	Alcohols, phenols, phenol-alcohols and their halogenated, sulphonated, nitrated or nitrosated derivatives; industrial fatty alcohols	4	f	Спирти, феноли, фенолспирти та їх галогено-, сульфо-, нітро-, нітрозопохідні; жирні промислові спирти
24322100-8	Industrial fatty alcohols	5	t	Жирні промислові спирти
24322200-9	Monohydric alcohols	5	f	Одноатомні спирти
24322210-2	Methanol	6	t	Метанол
24322220-5	Ethanol	6	t	Етанол
24322300-0	Diols, polyalcohols and derivatives	5	f	Діоли, багатоатомні спирти та їх похідні
24322310-3	Ethylene glycol	6	t	Етиленгліколь
24322320-6	Alcohol derivatives	6	t	Похідні спиртів
24322400-1	Phenols and derivatives	5	t	Феноли та їх похідні
24322500-2	Alcohol	5	f	Спирт
24322510-5	Ethyl alcohol	6	t	Етиловий спирт
24323000-4	Industrial monocarboxylic fatty acids	4	f	Промислові монокарбонові жирні кислоти
24323100-5	Acid oils from refining	5	t	Очищені кислі олії
24323200-6	Carboxylic acids	5	f	Карбонові кислоти
24323210-9	Acetic acid	6	t	Оцтова кислота
24323220-2	Peracetic acid	6	t	Пероцтова кислота
24323300-7	Unsaturated monocarboxylic acids and compounds	5	f	Ненасичені монокарбонові кислоти та їх сполуки
24323310-0	Esters of methacrylic acid	6	t	Ефіри метакрилової кислоти
24323320-3	Esters of acrylic acid	6	t	Ефіри акрилової кислоти
24323400-8	Aromatic polycarboxylic and carboxylic acids	5	t	Ароматичні полікарбонові та карбонові кислоти
24324000-1	Organic compounds with nitrogen functions	4	f	Органічні сполуки з азотною групою
24324100-2	Amine function compounds	5	t	Сполуки з аміновою групою
24324200-3	Oxygen-function amino-compounds	5	t	Амінові сполуки з кисневою групою
24324300-4	Ureines	5	t	Уреїди
24324400-5	Compounds with nitrogen functions	5	t	Сполуки з азотною групою
24325000-8	Organo-sulphur compounds	4	t	Сіркоорганічні сполуки
24326000-5	Aldehyde, ketone, organic peroxides and ethers	4	f	Альдегіди, кетони, органічні пероксиди та ефіри
24326100-6	Aldehyde function compounds	5	t	Сполуки з альдегідною групою
24326200-7	Ketone and quinone function compounds	5	t	Сполуки з кетоновою та хіноновою групами
24326300-8	Organic peroxides	5	f	Органічні пероксиди
24326310-1	Ethylene oxide	6	t	Оксид етилену
24326320-4	Ethers	6	t	Ефіри
24327000-2	Miscellaneous organic chemicals	4	f	Органічні хімічні речовини різні
24327100-3	Vegetable derivatives for dyeing	5	t	Барвники рослинного походження
24327200-4	Wood charcoal	5	t	Деревне вугілля
24327300-5	Oils and products of the distillation of high-temperature coal tar, pitch and pitch tar	5	f	Оливи та дистиляти, що утворюються в результаті високотемпературної переробки кам’яновугільної смоли, пеку та дьогтю
24327310-8	Coal tar	6	f	Кам’яновугільна смола
24327311-5	Creosote	7	t	Креозот
24327320-1	Pitch	6	t	Пек
24327330-4	Pitch tar	6	t	Дьоготь
24327400-6	Resin products	5	t	Смоляні продукти
24327500-7	Residual lyes from the manufacture of wood pulp	5	t	Лужні залишки від виробництва целюлози
24400000-8	Fertilisers and nitrogen compounds	2	f	Добрива та сполуки азоту
24410000-1	Nitrogenous fertilisers	3	f	Азотні добрива
24411000-8	Nitric acid and salts	4	f	Азотна кислота та її солі
24411100-9	Sodium nitrate	5	t	Нітрат натрію
24412000-5	Sulphonitric acids	4	t	Сірчано-азотні кислоти
24413000-2	Ammonia	4	f	Аміак
24413100-3	Liquid ammonia	5	t	Рідкий аміак
24413200-4	Ammonium chloride	5	t	Хлорид амонію
24413300-5	Ammonium sulphate	5	t	Сульфат амонію
24420000-4	Phosphatic fertilisers	3	f	Фосфатні добрива
24421000-1	Mineral phosphatic fertilisers	4	t	Фосфатні мінеральні добрива
24422000-8	Chemical phosphatic fertilisers	4	t	Фосфатні хімічні добрива
24430000-7	Animal or vegetal fertilisers	3	t	Добрива тваринного та рослинного походження
24440000-0	Miscellaneous fertilisers	3	t	Добрива різні
24450000-3	Agro-chemical products	3	f	Агрохімічна продукція
24451000-0	Pesticides	4	t	Пестициди
24452000-7	Insecticides	4	t	Інсектициди
24453000-4	Herbicides	4	t	Гербіциди
24454000-1	Plant-growth regulators	4	t	Регулятори росту рослин
24455000-8	Disinfectants	4	t	Дезинфекційні засоби
24456000-5	Rodenticides	4	t	Родентициди
24457000-2	Fungicides	4	t	Фунгіциди
24500000-9	Plastics in primary forms	2	f	Пластмаси у первинній формі
24510000-2	Primary-form polymers of ethylene	3	t	Етиленові полімери у первинній формі
24520000-5	Primary-form polymers of propylene	3	t	Пропіленові полімери у первинній формі
24960000-1	Various chemical products	3	f	Хімічна продукція різна
24541000-8	Primary-form polymers of vinyl acetate	4	t	Вінілацетатні полімери у первинній формі
24542000-5	Primary-form acrylic polymers	4	t	Акрилові полімери у первинній формі
24550000-4	Primary-form of polyesters	3	t	Ефірні полімери у первинній формі
24560000-7	Primary-form polyamides	3	t	Поліаміди у первинній формі
24570000-0	Primary-form urea resins	3	t	Карбамідні смоли у первинній формі
24580000-3	Primary-form amino-resins	3	t	Амінові смоли у первинній формі
24590000-6	Primary-form silicones	3	t	Силікони у первинній формі
24600000-0	Explosives	2	f	Вибухові речовини
24610000-3	Prepared explosives	3	f	Готові вибухові речовини
24611000-0	Propellant powders	4	f	Метальні вибухові речовини
24611100-1	Propergol fuels	5	t	Вибухові суміші на основі проперголю
24612000-7	Miscellaneous explosives	4	f	Вибухові речовини різні
24612100-8	Dynamite	5	t	Динаміт
24612200-9	TNT	5	t	Тринітротолуол
24612300-0	Nitro-glycerine	5	t	Нітрогліцерин
24613000-4	Signalling flares, rain rockets, fog signals and pyrotechnic articles	4	f	Сигнальні петарди, дощові та протитуманні ракети і піротехнічні вироби
24613100-5	Bird-scaring cartridges	5	t	Петарди для відлякування птахів
24613200-6	Fireworks	5	t	Феєрверки
24615000-8	Fuses, caps, igniters and electric detonators	4	t	Вогнепровідні шнури, капсулі-детонатори, запали та електродетонатори
24900000-3	Fine and various chemical products	2	f	Чисті хімічні речовини та різноманітна хімічна продукція
24910000-6	Glues	3	f	Клеї
24911000-3	Gelatines	4	f	Желатини
24911200-5	Adhesives	5	t	Адгезиви
24920000-9	Essential oils	3	t	Ефірні олії
24930000-2	Photographic chemicals	3	f	Фотохімікати
24931000-9	Photographic plates and films	4	f	Фотопластини ти фотоплівки
24931200-1	Emulsions for photographic use	5	f	Фотографічні емульсії
24931210-4	Photographic developers	6	t	Фотографічні проявники
24931220-7	Photographic fixers	6	t	Фотографічні фіксажі
24931230-0	X-ray developers	6	t	Проявники для рентгенівських плівок
24931240-3	X-ray fixers	6	t	Фіксажі для рентгенівських плівок
24931250-6	Culture medium	6	t	Живильні середовища
24931260-9	Image intensifiers	6	t	Підсилювачі зображення
24950000-8	Specialised chemical products	3	f	Спеціалізована хімічна продукція
24951000-5	Greases and lubricants	4	f	Змазки та мастильні матеріали
24951100-6	Lubricants	5	f	Мастильні матеріали
24951110-9	Drilling mud	6	t	Буровий розчин
24951120-2	Silicon grease	6	t	Силіконова змазка
24951130-5	Drilling fluids	6	t	Бурові рідини
24951200-7	Additives for oils	5	f	Оливні присадки
24951210-0	Fire-extinguisher powder	6	t	Вогнегасні порошки
24951220-3	Fire-extinguisher agents	6	t	Вогнегасні речовини
24951230-6	Fire-extinguisher charges	6	t	Заряди для вогнегасників
24951300-8	Hydraulic fluids	5	f	Гідравлічні рідини
24951310-1	De-icing agents	6	f	Протиожеледні реагенти
24951311-8	Anti-freezing preparations	7	t	Антифризні речовини
24951400-9	Chemically-modified fats and oils	5	t	Хімічно модифіковані жири та оливи
24952000-2	Modelling pastes	4	f	Формувальні пасти
24952100-3	Dental wax	5	t	Стоматологічний віск
24953000-9	Finishing agents	4	t	Оздоблювальні матеріали
24954000-6	Activated carbon	4	f	Активоване вугілля
24954100-7	New activated carbon	5	t	Нове активоване вугілля
24954200-8	Regenerated activated carbon	5	t	Відновлене активоване вугілля
24955000-3	Chemical toilets	4	t	Хімічні туалети
24956000-0	Peptones and protein substances	4	t	Пептони та білкові речовини
24957000-7	Chemical additives	4	f	Хімічні добавки
24957100-8	Prepared binders for foundry moulds or cores	5	t	Готові в’язкі речовини для виробництва ливарних форм і стрижнів
24957200-9	Additives for cements, mortars or concretes	5	t	Добавки до цементів, будівельних розчинів або бетонів
24958000-4	Chemical products for the oil and gas industry	4	f	Хімічна продукція для нафтогазової промисловості
24958100-5	Downhole chemicals	5	t	Хімікати для буріння свердловин
24958200-6	Flocculating agents	5	t	Флокулянти
24958300-7	Mud chemicals	5	t	Добавки до бурових розчинів
24958400-8	Gel ampoules for stemming explosives	5	t	Желатинові ампули для вибухових речовин, що використовуються під час бурильних робіт
24959000-1	Aerosols and chemicals in disc form	4	f	Аерозолі та хімічні речовини у вигляді пластин
24959100-2	Aerosols	5	t	Аерозолі
24959200-3	Chemical elements in disc form	5	t	Хімічні речовини у вигляді пластин
24961000-8	Radiator fluids	4	t	Охолоджувальні рідини
24962000-5	Water-treatment chemicals	4	t	Хімічні речовини для обробки води
24963000-2	Anti-corrosion products	4	t	Антикорозійні засоби
24964000-9	Glycerol	4	t	Гліцерин
24965000-6	Enzymes	4	t	Ферменти
30000000-9	Office and computing machinery, equipment and supplies except furniture and software packages	1	f	Офісна та комп’ютерна техніка, устаткування та приладдя, крім меблів та пакетів програмного забезпечення
30100000-0	Office machinery, equipment and supplies except computers, printers and furniture	2	f	Офісні техніка, устаткування та приладдя, крім комп’ютерів, принтерів та меблів
30110000-3	Word-processing machines	3	f	Текстообробні системи
30111000-0	Word processors	4	t	Текстові процесори
30120000-6	Photocopying and offset printing equipment	3	f	Фотокопіювальне та поліграфічне обладнання для офсетного друку
30121000-3	Photocopying and thermocopying equipment	4	f	Фотокопіювальні та термокопіювальні пристрої
30121100-4	Photocopiers	5	t	Фотокопіювальні пристрої
30121200-5	Photocopying equipment	5	t	Фотокопіювальне обладнання
30121300-6	Reproduction equipment	5	t	Копіювально-розмножувальне обладнання
30121400-7	Duplicating machines	5	f	Механічні копіювальні апарати
30121410-0	Faxswitch machines	6	t	Апарати з автоматичним перемиканням режимів телефону/факсу
30121420-3	Digital senders	6	t	Офісні комбайни
30121430-6	Digital duplicators	6	t	Цифрові дуплікатори
30122000-0	Office-type offset printing machinery	4	f	Офісна техніка для офсетного друку
30122100-1	Digital offset systems	5	t	Цифрові системи для офсетного друку
30122200-2	Digital offset equipments	5	t	Цифрове обладнання для офсетного друку
30123000-7	Office and business machines	4	f	Організаційна техніка
30123100-8	Ticket-validation machines	5	t	Валідатори квитків
30123200-9	Automatic cash dispensers	5	t	Банкомати
30123300-0	Stencil duplicating machines	5	t	Обладнання для трафаретного друку
30123400-1	Folding machines	5	t	Фальцювальні апарати
30123500-2	Perforation machines	5	t	Перфорувальні пристрої
30123600-3	Coin-handling machines	5	f	Монетні автомати
30123610-6	Coin-sorting machines	6	t	Автомати для сортування монет
30123620-9	Coin-counting machines	6	t	Автомати для рахування монет
30123630-2	Coin-wrapping machines	6	t	Автомати для пакування монет
30124000-4	Parts and accessories of office machines	4	f	Частини та приладдя до офісної техніки
30124100-5	Fusers	5	f	Термофіксатори
30124110-8	Fuser oil	6	t	Олива для термофіксаторів
30124120-1	Fuser wiper	6	t	Серветка для очищення термофіксаторів
30124130-4	Fuser lamps	6	t	Лампи для термофіксаторів
30124140-7	Fuser cleaning pad	6	t	Очищувальна губка для термофіксаторів
30124150-0	Fuser filters	6	t	Фільтри для термофіксаторів
30124200-6	Fuser kits	5	t	Комплекти термофіксаторів
30124300-7	Drums for office machine	5	t	Барабани для офісної техніки
30124400-8	Staple cartridges	5	t	Картриджі зі скобами
30124500-9	Scanner accessories	5	f	Приладдя до сканерів
30124510-2	Endorsers	6	t	Імпринтери
30124520-5	Scanner document feeders	6	t	Автоподавачі документів для сканерів
30124530-8	Scanner transparency adapters	6	t	Слайд-адаптери для сканерів
30125000-1	Parts and accessories of photocopying apparatus	4	f	Частини та приладдя до фотокопіювальних апаратів
30125100-2	Toner cartridges	5	f	Картриджі з тонером
30125110-5	Toner for laser printers/fax machines	6	t	Тонери для лазерних принтерів/факсів
30125120-8	Toner for photocopiers	6	t	Тонери для фотокопіювальної техніки
30125130-1	Toner for data-processing and research and documentation centres	6	t	Тонери для пристроїв обробки та аналізу даних і документації
30130000-9	Post-office equipment	3	f	Поштова техніка
30131000-6	Mailroom equipment	4	f	Устаткування для експедиції письмової кореспонденції
30131100-7	Paper or envelope folding machines	5	t	Пристрої для фальцювання паперу чи конвертів
30131200-8	Envelope-stuffing machines	5	t	Автомати для розкладання у конверти
30131300-9	Addressing machines	5	t	Адресувальні пристрої
30131400-0	Postage machines	5	t	Франкувальні машини
30131500-1	Mail opening machines	5	t	Конвертовідкривальні пристрої
30131600-2	Mail sealing machines	5	t	Пристрої для заклеювання конвертів
30131700-3	Stamp canceling machines	5	t	Штемпелювальні пристрої
30131800-4	Stamp affixers	5	t	Пристрої для наклеювання марок
30132000-3	Sorting equipment	4	f	Сортувальне обладнання
30132100-4	Mail-sorting equipment	5	t	Обладнання для сортування кореспонденції
30132200-5	Banknote counting machines	5	t	Купюрорахувальні машини
30132300-6	Sorters	5	t	Сортувальні машини
30133000-0	Mailing equipment	4	f	Обладнання для автоматичної обробки письмової кореспонденції
30133100-1	Bulk-mailing equipment	5	t	Обладнання для автоматичної обробки великих обсягів письмової кореспонденції
30140000-2	Calculating and accounting machines	3	f	Лічильна та обчислювальна техніка
30141000-9	Calculating machines	4	f	Лічильні машини
30141100-0	Pocket calculators	5	t	Кишенькові калькулятори
30141200-1	Desktop calculators	5	t	Настільні калькулятори
30141300-2	Printing calculators	5	t	Калькулятори з функцією друку
30141400-3	Adding machines	5	t	Підсумовувальні машини
30142000-6	Accounting machines and cash registers	4	f	Обчислювальна техніка та касові апарати
30142100-7	Accounting machines	5	t	Обчислювальні техніка
30142200-8	Cash registers	5	t	Касові апарати
30144000-0	Calculation-type machines	4	f	Лічильні апарати
30144100-1	Postage-franking machines	5	t	Поштові штемпелювальні пристрої
30144200-2	Ticket-issuing machines	5	t	Пристрої для друку квитків
30144300-3	Vehicle-counting machines	5	t	Обладнання для обліку наявності та руху транспортних засобів
30144400-4	Automatic fare collection	5	t	Системи автоматизованого продажу засобів оплати проїзду
30145000-7	Parts and accessories calculating machines	4	f	Частини та приладдя до лічильних машин
30145100-8	Calculator rolls	5	t	Картриджі для калькуляторів
30150000-5	Typewriters	3	f	Друкарські машинки
30151000-2	Electronic typewriters	4	t	Електронні друкарські машинки
30152000-9	Parts and accessories of typewriters	4	t	Частини та приладдя до друкарських машинок
30160000-8	Magnetic cards	3	f	Магнітні картки
30161000-5	Credit cards	4	t	Кредитні картки
30162000-2	Smart cards	4	t	Смарт-картки
30163000-9	Charge cards	4	f	Платіжні картки
30163100-0	Agency fuel cards	5	t	Паливні картки
30170000-1	Labelling machines	3	f	Етикетувальні машини
30171000-8	Dating or numbering machines	4	t	Машини для датування чи нумерування
30172000-5	Identification ID press machines	4	t	Преси для виготовлення документів, що посвідчують особу
30173000-2	Label applying machines	4	t	Автомати для наклеювання етикеток
30174000-9	Label making machines	4	t	Принтери для друку етикеток
30175000-6	Lettering equipment	4	t	Маркувальне обладнання
30176000-3	Tape embosser	4	t	Стрічкові принтери
30177000-0	Automatic labelling systems	4	t	Автоматичні маркувальні системи
30178000-7	Semi automatic labelling systems	4	t	Напівавтоматичні маркувальні системи
30179000-4	Label dispensers	4	t	Диспенсери етикеток
30180000-4	Check endorsing and writing machines	3	f	Чекові принтери та автомати для видачі чеків
30181000-1	Check endorsing machines	4	t	Чекові принтери
30182000-8	Check writing machines	4	t	Автомати для видачі чеків
30190000-7	Various office equipment and supplies	3	f	Офісне устаткування та приладдя різне
30191000-4	Office equipment except furniture	4	f	Офісне устаткування, крім меблів
30191100-5	Filing equipment	5	f	Архівне устаткування
30191110-8	Card carousel systems	6	t	Обертові картотеки
30191120-1	Magazine racks	6	t	Журнальні стенди
30191130-4	Clipboards	6	t	Тека-планшет із затискачем
30191140-7	Personal identification accessories	6	t	Аксесуари для особистої ідентифікації
30191200-6	Overhead projectors	5	t	Графопроектори
30191400-8	Shredders	5	t	Шредери
30192000-1	Office supplies	4	f	Офісне приладдя
30192100-2	Erasers	5	f	Гумки
30192110-5	Ink products	6	f	Чорнила та пов’язана продукція
30192111-2	Ink pads	7	t	Штемпельні подушки
30192112-9	Ink sources for printing machinery	7	t	Чорнила для друкарського обладнання
30192113-6	Ink cartridges	7	t	Чорнильні картриджі
30192121-5	Ballpoint pens	7	t	Кулькові ручки
30192122-2	Fountain pens	7	t	Перові ручки
30192123-9	Fibre pens	7	t	Ручки-маркери
30192124-6	Felt-tipped pens	7	t	Фломастери
30192125-3	Markers	7	t	Маркери
30192126-0	Technical pens	7	t	Рапідографи
30192127-7	Pen holders	7	t	Підставки для ручок
30192130-1	Pencils	6	f	Олівці
30192131-8	Propelling or sliding pencils	7	t	Механічні олівці чи олівці з висувним стрижнем
30192132-5	Pencil lead refills	7	t	Змінні грифелі для олівців
30192133-2	Pencil sharpeners	7	t	Точила для олівців
30192134-9	Pencil holders	7	t	Підставки для олівців
30192150-7	Date stamps	6	f	Датери
30192151-4	Sealing stamps	7	t	Пломбіратори
30192152-1	Numbering stamps	7	t	Нумератори
30192153-8	Phrase stamps	7	t	Штампи
30192154-5	Replacement stamp pads	7	t	Змінні подушки для штампів
30192155-2	Office stamp holders	7	t	Офісні підставки для печаток і штампів
30192160-0	Correctors	6	t	Коректори
30192170-3	Notice boards	6	t	Дошки для об’яв
30192200-3	Measuring tapes	5	t	Вимірювальна рулетка
30192300-4	Ink ribbons	5	f	Фарбувальна стрічка
30192310-7	Typewriter ribbons	6	t	Стрічка для друкарських машинок
30192320-0	Printer ribbons	6	t	Стрічка для принтерів
30192330-3	Calculator ribbons and drums	6	t	Стрічка та барабани для калькуляторів з функцією друку
30192340-6	Facsimile ribbons	6	t	Факсимільна стрічка
30192350-9	Cash register ribbons	6	t	Касова стрічка
30192400-5	Reprographic supplies	5	t	Копіювально-розмножувальне приладдя
30192500-6	Overhead transparencies	5	t	Прозорі плівки для графопроекторів
30192600-7	Drawing boards	5	t	Креслярські дошки
30192700-8	Stationery	5	t	Канцелярські товари
30192800-9	Self-adhesive labels	5	t	Самоклейні етикетки
30192900-0	Correction media	5	f	Корегувальні засоби
30192910-3	Correction film or tape	6	t	Корегувальна плівка чи стрічка
30192920-6	Correction fluid	6	t	Корегувальна рідина
30192930-9	Correction pens	6	t	Ручки-коректори
30192940-2	Correction pen refills	6	t	Картриджі до ручки-коректора
30192950-5	Electrical erasers	6	t	Електронні коректори
30193000-8	Organisers and accessories	4	f	Органайзери та приладдя до них
30193100-9	Desk drawer organisers	5	t	Розділювачі для шухляд
30193200-0	Desktop trays or organisers	5	t	Настільні лотки та органайзери
30193300-1	Hanging organisers	5	t	Підвісні органайзери
30193400-2	Book ends	5	t	Книготримачі
30193500-3	Literature rack	5	t	Стелажі для літератури
30193600-4	Support for diaries or calendars	5	t	Тримачі для щоденників або календарів
30193700-5	File storage box	5	t	Коробки для паперів
30193800-6	Message holders	5	t	Затискачі для папірців-записок
30193900-7	Copy holders	5	t	Пюпітри для документів
30194000-5	Drafting supplies	4	f	Креслярське приладдя
30194100-6	Curves	5	t	Лекала
30194200-7	Drafting dots, tapes and films	5	f	Точкові стікери, малярна стрічка та креслярська плівка
30194210-0	Drafting dots or tapes	6	t	Точкові стікери та малярна стрічка
30194220-3	Drafting films	6	t	Креслярська плівка
30194300-8	Drafting kits, sets and papers	5	f	Набори, комплекти та папір для креслення
30194310-1	Drafting kits or sets	6	t	Набори або комплекти для креслення
30194320-4	Drafting papers	6	t	Папір для креслення
30194400-9	Drafting table covers	5	t	Накривки для креслярських дощок
30194500-0	Lettering aids	5	t	Допоміжні засоби для шрифтового оформлення
30194600-1	Protractors	5	t	Транспортири
30194700-2	Templates	5	t	Трафарети
30194800-3	T-squares and triangles	5	f	Рейсшини та косинці
30194810-6	T-squares	6	t	Рейсшини
30194820-9	Triangles	6	t	Косинці
30194900-4	Work surface protection covers	5	t	Захисні накривки для робочих поверхонь
30195000-2	Boards	4	f	Дошки
30195100-3	Planning boards or accessories	5	t	Планувальні дошки чи приладдя
30195200-4	Electronic copyboards or accessories	5	t	Електронні дошки чи приладдя
30195300-5	Letter boards or accessories	5	t	Набірне табло чи приладдя
30195400-6	Dry erase boards or accessories	5	t	Маркерні дошки чи приладдя
30195500-7	Chalk boards or accessories	5	t	Дошки для крейди чи приладдя
30195600-8	Bulletin boards or accessories	5	t	Дошки оголошень чи приладдя
30195700-9	Board cleaning kits or accessories	5	t	Набори для очищення дощок чи приладдя
30195800-0	Hanging rails or holders	5	t	Мобільні вішалки чи підвісні тримачі
30195900-1	Whiteboards and magnetic boards	5	f	Дошки для маркерів та магнітні дошки
30195910-4	Whiteboards	6	f	Дошки для маркерів
30195911-1	Whiteboard accessories	7	t	Приладдя до дощок для маркерів
30195912-8	Whiteboard easels	7	t	Дошки-мольберти для маркерів
30195913-5	Flipchart easels	7	t	Стенди для фліпчартів
30195920-7	Magnetic boards	6	f	Магнітні дошки
30195921-4	Erasers for magnetic boards	7	t	Гумки для магнітних дощок
30196000-9	Planning systems	4	f	Планувальні системи
30196100-0	Meeting planners	5	t	Планувальники зустрічей
30196200-1	Appointment books or refills	5	t	Ділові щоденники або змінні блоки до них
30196300-2	Suggestion box	5	t	Скриньки для збору пропозицій
30197000-6	Small office equipment	4	f	Дрібне канцелярське приладдя
30197100-7	Staples, tacks, drawing pins	5	f	Скоби, кнопки, креслярські кнопки
30197110-0	Staples	6	t	Скоби
30197120-3	Tacks	6	t	Кнопки
30197130-6	Drawing pins	6	t	Креслярські кнопки
30197200-8	Ring binders and paper clips	5	f	Теки-реєстратори і канцелярські скріпки
30197210-1	Ring binders	6	t	Теки-реєстратори
30197220-4	Paper clips	6	f	Канцелярські скріпки
30197221-1	Paperclip holder	7	t	Підставки для канцелярських скріпок
30197300-9	Letter openers, staplers and hole punches	5	f	Канцелярські ножі, степлери та діро коли
30197310-2	Letter openers	6	t	Канцелярські ножі
30197320-5	Staplers	6	f	Степлери
30197321-2	Staple removers	7	t	Антистеплери
30197330-8	Hole punches	6	t	Діроколи
30197400-0	Stamp sponge	5	t	Канцелярські губки
30197500-1	Sealing wax	5	f	Сургуч
30197510-4	Sealing wax accessories	6	t	Приладдя для користування сургучем
30197600-2	Processed paper and paperboard	5	f	Оброблені папір і картон
30197610-5	Composite paper and paperboard	6	t	Багатошарові папір і картон
30197620-8	Writing paper	6	f	Папір для письма
30197621-5	Flipchart pad	7	t	Паперові блоки для фліпчартів
30197630-1	Printing paper	6	t	Папір для друку
30197640-4	Self-copy or other copy paper	6	f	Самокопіювальний та інший копіювальний папір
30197641-1	Thermographic paper	7	t	Папір для термографічного друку
30197642-8	Photocopier paper and xerographic paper	7	t	Папір для фотокопіювання та ксерографії
30197643-5	Photocopier paper	7	t	Папір для фотокопіювання
30197644-2	Xerographic paper	7	t	Папір для ксерографії
30197645-9	Card for printing	7	t	Картки для друку
30198000-3	Lottery machines	4	f	Лотерейні автомати
30198100-4	Pulling machines	5	t	Ігрові автомати
30199000-0	Paper stationery and other items	4	f	Паперове канцелярське приладдя та інші паперові вироби
30199100-1	Carbon paper, self-copy paper, paper duplicator stencils and carbonless paper	5	f	Копіювальний та самокопіювальний папір, папір для трафаретного друку та перебивний папір
30199110-4	Carbon paper	6	t	Копіювальний папір
30199120-7	Self-copy paper	6	t	Самокопіювальний папір
30199130-0	Carbonless paper	6	t	Перебивний папір
30199140-3	Paper duplicator stencils	6	t	Папір для трафаретного друку
30199200-2	Envelopes, letter cards and plain postcards	5	f	Конверти, поштові листівки та неілюстровані поштові листівки
30199210-5	Letter cards	6	t	Поштові листівки
30199220-8	Plain postcards	6	t	Неілюстровані поштові листівки
30199230-1	Envelopes	6	t	Конверти
30199240-4	Mailing kit	6	t	Поштові набори
30199300-3	Embossed or perforated paper	5	f	Тиснений або перфорований папір
30199310-6	Embossed or perforated printing paper	6	t	Тиснений або перфорований папір для друку
30199320-9	Embossed or perforated writing paper	6	t	Тиснений або перфорований папір для письма
30199330-2	Continuous paper for computer printers	6	t	Паперова стрічка для комп’ютерних принтерів
30199340-5	Continuous forms	6	t	Нерозрізні бланки
30199400-4	Gummed or adhesive paper	5	f	Прогумований або клейкий папір
30199410-7	Self-adhesive paper	6	t	Самоклейний папір
30199500-5	Box files, letter trays, storage boxes and similar articles	5	t	Сегрегатори, лотки для листів, коробки для зберігання паперів та подібне приладдя
30199600-6	Dividers for stationery	5	t	Розділювачі сторінок
30199700-7	Printed stationery except forms	5	f	Друковані канцелярські вироби, крім бланків
30199710-0	Printed envelopes	6	f	Фірмові конверти
30199711-7	Printed window envelopes	7	t	Фірмові конверти з віконцем
30199712-4	Printed non-window envelopes	7	t	Фірмові конверти без віконця
30199713-1	Printed X-ray envelopes	7	t	Конверти-бланки для рентгенівських плівок
30199720-3	Notepaper	6	t	Пролініяний папір
30199730-6	Business cards	6	f	Візитні картки
30199731-3	Business card holders	7	t	Візитниці
30199740-9	Compliment slips	6	t	Фірмові листівки
30199750-2	Coupons	6	t	Купони
30199760-5	Labels	6	f	Етикетки
30199761-2	Bar-coded labels	7	t	Етикетки зі штрих-кодом
30199762-9	Luggage tags	7	t	Багажні ярлики
30199763-6	Theft protection labels	7	t	Протикрадіжне марковання
30199770-8	Luncheon vouchers	6	t	Талони на харчування
30199780-1	Blotting pads	6	t	Бювари
30199790-4	Timetables	6	f	Розклади
30199791-1	Wall planners	7	t	Настінні щоденники
30199792-8	Calendars	7	t	Календарі
30199793-5	Diary stands	7	t	Підставки для щоденників
30200000-1	Computer equipment and supplies	2	f	Комп’ютерне обладнання та приладдя
30210000-4	Data-processing machines (hardware)	3	f	Машини для обробки даних (апаратна частина)
30211000-1	Mainframe computer	4	f	Мейнфрейм
30211100-2	Super computer	5	t	Суперкомп’ютер
30211200-3	Mainframe hardware	5	t	Апаратне забезпечення мейнфреймів
30211300-4	Computer platforms	5	t	Комп’ютерні платформи
30211400-5	Computer configurations	5	t	Комп’ютерні конфігурації
30211500-6	Central processing unit (CPU) or processors	5	t	Центральні процесорні пристрої або процесори
30212000-8	Minicomputer hardware	4	f	Апаратне забезпечення мінікомп’ютерів
30212100-9	Central processing units for minicomputers	5	t	Центральні процесорні пристрої для мінікомп’ютерів
30213000-5	Personal computers	4	f	Персональні комп’ютери
30213100-6	Portable computers	5	t	Портативні комп’ютери
30213200-7	Tablet computer	5	t	Планшетні комп’ютери
30213300-8	Desktop computer	5	t	Настільні комп’ютери
30213400-9	Central processing units for personal computers	5	t	Центральні процесорні пристрої для персональних комп’ютерів
30213500-0	Pocket computers	5	t	Кишенькові комп’ютери
30214000-2	Workstations	4	t	Робочі станції
30215000-9	Microcomputer hardware	4	f	Апаратне забезпечення мікрокомп’ютерів
30215100-0	Central processing units for microcomputers	5	t	Центральні процесорні пристрої для мікрокомп’ютерів
30216000-6	Magnetic or optical readers	4	f	Магнітні та оптичні зчитувальні пристрої
30216100-7	Optical readers	5	f	Оптичні зчитувальні пристрої
30216110-0	Scanners for computer use	6	t	Комп’ютерні сканери
30216120-3	Optical-character-recognition equipment	6	t	Пристрої для оптичного розпізнавання символів
30216130-6	Barcode readers	6	t	Сканери штрих-кодів
30216200-8	Magnetic card readers	5	t	Зчитувачі магнітних карток
30216300-9	Punchcard readers	5	t	Зчитувачі перфокарт
30220000-7	Digital cartography equipment	3	f	Цифрове картографічне обладнання
30221000-4	Digital cadastral maps	4	t	Електронні кадастрові мапи
30230000-0	Computer-related equipment	3	f	Комп’ютерне обладнання
30231000-7	Computer screens and consoles	4	f	Екрани комп’ютерних моніторів та консолі
30231100-8	Computer terminals	5	t	Комп’ютерні термінали
30231200-9	Consoles	5	t	Консолі
30231300-0	Display screens	5	f	Дисплейні екрани
30231310-3	Flat panel displays	6	t	Плоскопанельні дисплеї
30231320-6	Touch screen monitors	6	t	Монітори з сенсорним екраном
30232000-4	Peripheral equipment	4	f	Периферійне обладнання
30232100-5	Printers and plotters	5	f	Принтери та плотери
30232110-8	Laser printers	6	t	Лазерні принтери
30232120-1	Dot-matrix printers	6	t	Матричні принтери
30232130-4	Colour graphics printers	6	t	Кольорові графічні принтери
30232140-7	Plotters	6	t	Плотери
30232150-0	Inkjet printers	6	t	Струменеві принтери
30232600-0	Encoders	5	t	Кодери
30232700-1	Central controlling unit	5	t	Центральний блок керування
30233000-1	Media storage and reader devices	4	f	Пристрої для зберігання та зчитування даних
30233100-2	Computer storage units	5	f	Комп’ютерні запам’ятовувальні пристрої
30233110-5	Magnetic card storage units	6	t	Накопичувачі на магнітних картках
30233120-8	Magnetic tape storage units	6	t	Накопичувачі на магнітних стрічках
30233130-1	Magnetic disk storage units	6	f	Накопичувачі на магнітних дисках
30233131-8	Floppy-disk drives	7	t	Накопичувачі на гнучких магнітних дисках
30233132-5	Hard-disk drives	7	t	Накопичувачі на твердих магнітних дисках
30233140-4	Direct-access storage devices (DASD)	6	f	Запам’ятовувальні пристрої з прямим доступом (DASD)
30233141-1	Redundant Array of Independent Disk (RAID)	7	t	Надлишковий масив незалежних дисків (RAID)
30233150-7	Optical-disk drives	6	f	Оптичні накопичувачі
30233151-4	Compact disk (CD) reader and/or burner	7	t	Пристрої для читання та/або запису компакт-дисків (CD)
30233152-1	Digital versatile disc (DVD) reader and/or burner	7	t	Пристрої для читання та/або запису універсальних цифрових дисків (DVD)
30233153-8	Compact disk (CD) and digital versatile disk (DVD) reader and/or burner	7	t	Пристрої для читання та/або запису компакт-дисків (CD) та універсальних цифрових дисків (DVD)
30233160-0	Tape streamers	6	f	Стрічкові накопичувачі (стримери)
30233161-7	Cassette-handling equipment	7	t	Касетне обладнання
30233170-3	Carousel units	6	t	Запам’ятовувальні пристрої з карусельною подачею
30233180-6	Flash memory storage devices	6	t	Флеш-накопичувачі
30233190-9	Disk controller	6	t	Дискові контролери
30233300-4	Smart card readers	5	f	Зчитувачі смарт-карток
30233310-7	Fingerprint readers	6	t	Зчитувачі відбитків пальців
30233320-0	Combined smart card and fingerprint readers	6	t	Комбіновані пристрої для зчитування смарт-карток та відбитків пальців
30234000-8	Storage media	4	f	Носії інформації
30234100-9	Magnetic disk	5	t	Магнітні диски
30234200-0	Optical disks	5	t	Оптичні диски
30234300-1	Compact disks (CDs)	5	t	Компакт-диски (CD)
30234400-2	Digital versatile disks (DVDs)	5	t	Універсальні цифрові диски (DVD)
30234500-3	Memory storage media	5	t	Зовнішні запам’ятовувальні пристрої
30234600-4	Flash memory	5	t	Флеш-пам’ять
30234700-5	Magnetic tapes	5	t	Магнітні стрічки
30236000-2	Miscellaneous computer equipment	4	f	Комп’ютерне обладнання різне
30236100-3	Memory-expansion equipment	5	f	Пристрої для розширення об’єму пам’яті
30236110-6	Random access memory (RAM)	6	f	Оперативні запам’ятовувальні пристрої (RAM)
30236111-3	Dynamic random access memory (DRAM)	7	t	Динамічні оперативні запам’ятовувальні пристрої (DRAM)
30236112-0	Static random access memory (SRAM)	7	t	Статичні оперативні запам’ятовувальні пристрої (SRAM)
30236113-7	Synchronous dynamic random access memory (SDRAM)	7	t	Динамічні оперативні запам’ятовувальні пристрої з синхронних доступом (SDRAM)
30236114-4	Rambus dynamic random access memory (RDRAM)	7	t	Динамічні оперативні запам’ятовувальні пристрої стандарту Rambus (RDRAM)
30236115-1	Synchronous graphic random access memory (SGRAM)	7	t	Оперативні запам’ятовувальні пристрої з синхронних доступом (SGRAM), оптимізовані для графічних застосунків
30236120-9	Read only memory (ROM)	6	f	Постійні запам’ятовувальні пристрої (ROM)
30236121-6	Programmable read only memory (PROM)	7	t	Програмовані постійні запам’ятовувальні пристрої (PROM)
30236122-3	Erasable programmable read only memory (EPROM)	7	t	Програмовані постійні запам’ятовувальні пристрої з можливістю перезапису (EPROM)
30236123-0	Electronically erasable programmable read only memory (EEPROM)	7	t	Програмовані енергонезалежні постійні запам’ятовувальні пристрої з можливістю перезапису (EEPROM)
30236200-4	Data-processing equipment	5	t	Обладнання для обробки даних
30237000-9	Parts, accessories and supplies for computers	4	f	Частини, аксесуари та приладдя до комп’ютерів
30237100-0	Parts of computers	5	f	Частини до комп’ютерів
30237110-3	Network interfaces	6	t	Мережеві інтерфейси
30237120-6	Computer ports	6	f	Комп’ютерні порти
30237121-3	Serial infrared ports	7	t	Послідовні інфрачервоні порти
30237130-9	Computer cards	6	f	Перфокарти
30237131-6	Electronic cards	7	t	Електронні плати
30237132-3	Universal Serial Bus (USB) Interfaces	7	t	Інтерфейси універсальних послідовних шин (USB-інтерфейси)
30237133-0	Personal Computer Memory Card International Association (PCMCIA) adaptors and interfaces	7	t	Адаптери та інтерфейси стандарту PCMCIA (Міжнародної асоціації комп’ютерних карт пам’яті)
30237134-7	Graphic accelerator cards	7	t	Графічні акселератори
30237135-4	Network interfaces cards	7	t	Мережеві адаптери
30237136-1	Audio cards	7	t	Аудіокарти
30237140-2	Motherboards	6	t	Материнські плати
30237200-1	Computer accessories	5	f	Комп’ютерні аксесуари
30237210-4	Anti-glare screens	6	t	Антиблікові екрани
30237220-7	Mousepads	6	t	Килимки для комп’ютерної миші
30237230-0	Caches	6	t	Пристрої кеш-пам’яті
30237240-3	Web camera	6	t	Веб-камери
30237250-6	Computer cleaning accessories	6	f	Засоби для очищення комп’ютерного обладнання
30237251-3	Computer cleaning kits	7	t	Набори для очищення комп’ютерного обладнання
30237252-0	Pressurised air dusters	7	t	Пневматичний пилоочисник
30237253-7	Dust covers for computer equipment	7	t	Чохли для комп’ютерного обладнання для захисту від пилу
30237260-9	Monitor wall mount arms	6	t	Настінні кронштейни для моніторів
30237270-2	Portable computer carrying cases	6	t	Сумки для портативних комп’ютерів
30237280-5	Power supply accessories	6	t	Пристрої електроживлення
30237290-8	Keyboard wrist rests	6	f	Підставки для рук до клавіатури
30237295-3	Keyguards	7	t	Накладки на клавіатуру
30237300-2	Computer supplies	5	f	Комп’ютерне приладдя
30237310-5	Font cartridges for printers	6	t	Шрифтові картриджі для принтерів
30237320-8	Diskettes	6	t	Дискети
30237330-1	Digital Audio Tape (DAT) cartridges	6	t	Картриджі цифрової аудіострічки (DAT-картриджі)
30237340-4	Digital Linear Tape (DLT) cartridges	6	t	Картриджі цифрової стрічки з лінійним записом (DLT-картриджі)
30237350-7	Data cartridges	6	t	Картриджі даних
30237360-0	Linear Tape-Open (LTO) cartridges	6	t	Картриджі відкритого стандарту лінійного запису (LTO-картриджі)
30237370-3	Recording cartridges	6	t	Записувальні картриджі
30237380-6	CD-ROM	6	t	Компакт-диски без можливості перезапису (CD-ROM)
30237400-3	Data entry accessories	5	f	Пристрої введення даних
30237410-6	Computer mouse	6	t	Комп’ютерні миші
30237420-9	Joysticks	6	t	Джойстики
30237430-2	Light pens	6	t	Світлові пера
30237440-5	Trackballs	6	t	Трекболи
30237450-8	Graphics tablets	6	t	Графічні планшети
30237460-1	Computer keyboards	6	f	Комп’ютерні клавіатури
30237461-8	Programmable keyboards	7	t	Програмовані клавіатури
30237470-4	Braille pads	6	f	Клавіатури Брайля
30237475-9	Electric sensors	7	t	Електричні сенсори
30237480-7	Input units	6	t	Ввідні блоки
30238000-6	Library automation equipment	4	t	Автоматизоване бібліотечне устаткування
31000000-6	Electrical machinery, apparatus, equipment and consumables; lighting	1	f	Електротехнічне устаткування, апаратура, обладнання та матеріали; освітлювальне устаткування
31100000-7	Electric motors, generators and transformers	2	f	Електродвигуни, генератори та трансформатори
31110000-0	Electric motors	3	f	Електродвигуни
31111000-7	Adapters	4	t	Адаптери
31120000-3	Generators	3	f	Генератори
31121000-0	Generating sets	4	f	Генераторні установки
31121100-1	Generating sets with compression-ignition engines	5	f	Дизель-генераторні установки
31121110-4	Power converters	6	f	Перетворювачі електричної енергії
31121111-1	Electric rotary converters	7	t	Обертальні перетворювачі електричної енергії
31121200-2	Generating sets with spark-ignition engines	5	t	Генераторні установки з двигуном із іскровим запалюванням
31121300-3	Wind-energy generators	5	f	Вітрові генератори
31121310-6	Windmills	6	t	Вітряки
31121320-9	Wind turbines	6	t	Вітрові турбіни
31121330-2	Wind turbine generators	6	f	Вітряні турбогенератори
31121331-9	Turbine rotors	7	t	Ротори для турбін
31121340-5	Wind farm	6	t	Вітрові електростанції
31122000-7	Generator units	4	f	Генераторні блоки
31122100-8	Fuel cells	5	t	Паливні елементи
31124000-1	Steam-turbine generator and related apparatus	4	f	Парові турбогенератори та супутня апаратура
31124100-2	Turbine generator sets	5	t	Турбогенераторні установки
31124200-3	Turbine generator control apparatus	5	t	Апарати керування турбогенераторами
31126000-5	Dynamos	4	t	Динамо-машини
31127000-2	Emergency generator	4	t	Аварійні генератори
31128000-9	Turbogenerator	4	t	Турбогенератори
31130000-6	Alternators	3	f	Генератори змінного струму
31131000-3	Single-phase motors	4	f	Однофазні двигуни
31131100-4	Actuators	5	t	Виконавчі механізми
31131200-5	Anodes	5	t	Аноди
31132000-0	Multi-phase motors	4	t	Багатофазні двигуни
31140000-9	Cooling towers	3	f	Градирні
31141000-6	Water coolers	4	t	Водоохолоджувачі
31150000-2	Ballasts for discharge lamps or tubes	3	f	Баласти для розрядних ламп чи трубок
31151000-9	Static converters	4	t	Статичні перетворювачі
31153000-3	Rectifiers	4	t	Випрямлячі
31154000-0	Uninterruptible power supplies	4	t	Джерела безперебійного живлення
31155000-7	Inverters	4	t	Інвертори
31156000-4	Interruptible power supplies	4	t	Джерела негарантованого живлення
31157000-1	Inductors	4	t	Індуктивні котушки
31158000-8	Chargers	4	f	Зарядні пристрої
31158100-9	Battery chargers	5	t	Зарядні пристрої для акумуляторів
31158200-0	Supercharger	5	t	Нагнітачі
31158300-1	Turbocharger	5	t	Турбонаддуви
31160000-5	Parts of electric motors, generators and transformers	3	f	Частини електродвигунів, генераторів і трансформаторів
31161000-2	Parts for electrical motors and generators	4	f	Частини електродвигунів і генераторів
31161100-3	Excitation systems	5	t	Системи збуджування
31161200-4	Gas cooling systems	5	t	Газоохолоджувальні системи
31161300-5	Generator rotors	5	t	Ротори для генераторів
31161400-6	Primary water systems	5	t	Системи водопостачання першого контуру
31161500-7	Seal oil systems	5	t	Системи оливного ущільнення
31161600-8	Stator cooling water systems	5	t	Системи водяного охолодження статорів
31161700-9	Parts of steam generators	5	t	Частини парових генераторів
31161800-0	Parts of gas generators	5	t	Частини газових генераторів
31161900-1	Voltage-control systems	5	t	Системи контролю напруги
31162000-9	Parts of transformers, inductors and static converters	4	f	Частини трансформаторів, індуктивних котушок і статичних перетворювачів
31162100-0	Parts of condensers	5	t	Частини конденсаторів
31170000-8	Transformers	3	f	Трансформатори
31171000-5	Liquid dielectric transformers	4	t	Трансформатори з рідинним діелектриком
31172000-2	Voltage transformers	4	t	Трансформатори напруги
31173000-9	Instrument transformer	4	t	Вимірювальні трансформатори
31174000-6	Power supply transformers	4	t	Трансформатори живлення
31200000-8	Electricity distribution and control apparatus	2	f	Електророзподільна та контрольна апаратура
31210000-1	Electrical apparatus for switching or protecting electrical circuits	3	f	Електрична апаратура для комутування та захисту електричних кіл
31211000-8	Boards and fuse boxes	4	f	Щитки та шафи з плавкими запобіжниками
31211100-9	Boards for electrical apparatus	5	f	Комутаційні апарати
31211110-2	Control panels	6	t	Щити керування
31211200-0	Fuse boxes	5	t	Шафи з плавкими запобіжниками
31211300-1	Fuses	5	f	Плавкі запобіжники
31211310-4	Cut-outs	6	t	Автоматичні вимикачі
31211320-7	Fuse blocks	6	t	Блоки плавких запобіжників
31211330-0	Fuse wires	6	t	Дріт для плавких запобіжників
31211340-3	Fuse clips	6	t	Клеми для плавких запобіжників
31212000-5	Circuit breakers	4	f	Розмикачі
31212100-6	Overhead circuit breakers	5	t	Розмикачі повітряних ліній електропередачі
31212200-7	Circuit testers	5	t	Тестери для перевірки цілісності електричних схем
31212300-8	Magnetic circuit breakers	5	t	Електромагнітні вимикачі
31212400-9	Miniature circuit breakers	5	t	Мініатюрні автоматичні вимикачі
31213000-2	Distribution equipment	4	f	Розподільне обладнання
31213100-3	Distribution boxes	5	t	Розподільні коробки
31213200-4	Distribution transformers	5	t	Розподільні трансформатори
31213300-5	Cable distribution cabinet	5	t	Кабельні бокси
31213400-6	Distribution system	5	t	Розподільні системи
31214000-9	Switchgear	4	f	Розподільні пристрої
31214100-0	Switches	5	f	Перемикачі
31214110-3	Isolating switches	6	t	Роз’єднувачі
31214120-6	Earthing switch	6	t	Заземлювальні вимикачі
31214130-9	Safety switches	6	t	Захисні вимикачі
31214140-2	Dimmer switches	6	t	Вимикачі світла
31214150-5	Drum switches	6	t	Барабанні перемикачі
31214160-8	Pressure switches	6	t	Пневматичні вимикачі
31214170-1	Toggle switches	6	t	Тумблери
31214180-4	Slide switches	6	t	Повзункові перемикачі
31214190-7	Limit switches	6	t	Кінцеві вимикачі
31214200-1	Switch disconnector	5	t	Вимикачі-роз’єднувачі
31214300-2	Outdoor switching installations	5	t	Зовнішні розподілювальні пристрої
31214400-3	Fuse switch disconnector	5	t	Вимикачі-роз’єднувачі з плавкими запобіжниками
31214500-4	Electric switchboards	5	f	Електричні щити
31214510-7	Distribution switchboards	6	t	Розподільні щити
31214520-0	Medium-voltage switchboards	6	t	Розподільні пристрої середньої напруги
31215000-6	Voltage limiters	4	t	Обмежувачі напруги
31216000-3	Lightning arrestors	4	f	Грозорозрядники
31216100-4	Lightning-protection equipment	5	t	Обладнання для захисту від блискавок
31216200-5	Lightning conductors	5	t	Блискавичники
31217000-0	Surge suppressors	4	t	Обмежувачі перенапруження
31218000-7	Busbars	4	t	Шинопроводи
31219000-4	Protection boxes	4	t	Захисні коробки
31220000-4	Electrical circuit components	3	f	Елементи електричних схем
31221000-1	Electrical relays	4	f	Електричні реле
31221100-2	Power relays	5	t	Силові реле
31221200-3	General purpose relays	5	t	Універсальні реле
31221300-4	Socket relays	5	t	Розеткове реле
31221400-5	Alternating voltage relays	5	t	Реле змінної напруги
31221500-6	Mercury relays	5	t	Ртутні реле
31221600-7	Time relays	5	t	Реле часу
31221700-8	Overload relays	5	t	Реле із захистом від перевантаження
31223000-5	Lamp-holders	4	t	Лампові патрони
31224000-2	Connections and contact elements	4	f	З’єднувачі та контактні елементи
31224100-3	Plugs and sockets	5	t	Вилки та розетки
31224200-4	Coaxial connectors	5	t	Коаксіальні з’єднувачі
31224300-5	Connection boxes	5	t	З’єднувальні коробки
31224400-6	Connection cables	5	t	З’єднувальні кабелі
31224500-7	Terminals	5	t	Затискачі
31224600-8	Dimmers	5	t	Світлорегулятори
31224700-9	Junction boxes	5	t	Монтажні коробки
31224800-0	Cable joining kits	5	f	З’єднувальні комплекти для кабелів
31224810-3	Extension cables	6	t	Подовжувальні кабелі
31230000-7	Parts of electricity distribution or control apparatus	3	t	Частини електророзподільної чи контрольної апаратури
31300000-9	Insulated wire and cable	2	f	Ізольовані дроти та кабелі
31310000-2	Mains	3	f	Мережеві кабелі
31311000-9	Mains connections	4	t	З’єднувачі мережевих кабелів
31320000-5	Power distribution cables	3	f	Електророзподільні кабелі
31321000-2	Electricity power lines	4	f	Лінії електропередачі
31321100-3	Overhead power lines	5	t	Повітряні лінії електропередачі
31321200-4	Low- and medium-voltage cable	5	f	Кабелі низької та середньої напруги
31321210-7	Low-voltage cable	6	t	Кабелі низької напруги
31321220-0	Medium-voltage cable	6	t	Кабелі середньої напруги
31321300-5	High-voltage cable	5	t	Кабелі високої напруги
31321400-6	Underwater cable	5	t	Підводні кабелі
31321500-7	Submarine cable	5	t	Підводні комунікаційні кабелі
31321600-8	Shielded cable	5	t	Екрановані кабелі
31321700-9	Signalling cable	5	t	Кабелі для сигналізації
31330000-8	Coaxial cable	3	t	Коаксіальні кабелі
31340000-1	Insulated cable accessories	3	f	Приладдя до ізольованих кабелів
31341000-8	Insulated cable reels	4	t	Котушки для ізольованих кабелів
31342000-5	Insulated cable junctions	4	t	З’єднувачі для ізольованих кабелів
31343000-2	Insulated cable joints	4	t	Муфти для ізольованих кабелів
31344000-9	Insulated cable glands	4	t	Сальники для ізольованих кабелів
31350000-4	Electric conductors for data and control purposes	3	f	Провідники зі суміщеними потоками керування і даних
31351000-1	Electric conductors for access control systems	4	t	Провідники для систем контролю доступу
31400000-0	Accumulators, primary cells and primary batteries	2	f	Акумулятори, гальванічні елементи та гальванічні батареї
31410000-3	Primary cells	3	f	Гальванічні елементи
31411000-0	Alkaline batteries	4	t	Лужні батареї
31420000-6	Primary batteries	3	f	Гальванічні батареї
31421000-3	Lead batteries	4	t	Свинцеві батареї
31422000-0	Battery packs	4	t	Батарейні блоки
31430000-9	Electric accumulators	3	f	Електричні акумулятори
31431000-6	Lead-acid accumulators	4	t	Свинцево-кислотні акумуляторні батареї
31432000-3	Nickel-cadmium accumulators	4	t	Нікелево-кадмієві акумуляторні батареї
31433000-0	Nickel-iron accumulators	4	t	Залізно-нікелеві акумуляторні батареї
31434000-7	Lithium accumulators	4	t	Літієві акумуляторні батареї
31440000-2	Batteries	3	t	Акумуляторні батареї
31500000-1	Lighting equipment and electric lamps	2	f	Освітлювальне обладнання та електричні лампи
31510000-4	Electric filament lamps	3	f	Електричні лампи розжарення
31511000-1	Sealed-beam lamp units	4	t	Рефлекторні лампи
31512000-8	Tungsten halogen filament lamps	4	f	Галогенні лампи розжарення
31512100-9	Halogen bulbs, linear	5	t	Галогенні лінійні лампи
31512200-0	Halogen bulbs, bi-pin	5	t	Галогенні двоштиреві лампи
31512300-1	Halogen bulbs, dichroic	5	t	Галогенні дихроїчні лампи
31514000-2	Discharge lamps	4	t	Розрядні лампи
31515000-9	Ultraviolet lamps	4	t	Лампи ультрафіолетового світла
31516000-6	Infrared lamps	4	t	Лампи інфрачервоного світла
31517000-3	Arc lamps	4	t	Дугові лампи
31518000-0	Signalling lights	4	f	Сигнальні лампи
31518100-1	Floodlights	5	t	Прожектори заливального світла
31518200-2	Emergency lighting equipment	5	f	Пристрої аварійного освітлення
31518210-5	Stormlights	6	t	Аварійні ліхтарі
31518220-8	Light stick	6	t	Світлодіодні трубки
31518300-3	Rooflights	5	t	Зенітні ліхтарі
31518500-5	Mercury-vapour lamps	5	t	Ртутні лампи
31518600-6	Searchlights	5	t	Пошукові прожектори
31519000-7	Incandescent and neon lamps	4	f	Лампи розжарення та неонові лампи
31519100-8	Incandescent lamps	5	t	Лампи розжарення
31519200-9	Neon lamps	5	t	Неонові лампи
31520000-7	Lamps and light fittings	3	f	Світильники та освітлювальна арматура
31521000-4	Lamps	4	f	Світильники
31521100-5	Desk lamps	5	t	Настільні світильники
31521200-6	Floor-standing lamps	5	t	Торшери
34721100-8	Hang-gliders	5	t	Дельтаплани
31521300-7	Portable electric lamps	5	f	Портативні електричні світильники
31521310-0	Warning lights	6	t	Ліхтарі аварійної сигналізації
31521320-3	Torches	6	t	Портативні ліхтарі
31521330-6	Rechargeable portable electric lamps	6	t	Перезарядні портативні електричні світильники
31522000-1	Christmas tree lights	4	t	Ялинкові електричні гірлянди
31523000-8	Illuminated signs and nameplates	4	f	Підсвічувані вказівники і вивіски
31523100-9	Advertising neon lights	5	t	Неонові рекламні вивіски
31523200-0	Permanent message signs	5	t	Статичні інформаційні табло
31523300-1	Illuminated nameplates	5	t	Підсвічувані вивіски
31524000-5	Ceiling or wall light fittings	4	f	Настельна чи настінна освітлювальна арматура
31524100-6	Ceiling light fittings	5	f	Настельна освітлювальна арматура
31524110-9	Operating-theatre lamps	6	t	Операційні світильники
31524120-2	Ceiling lights	6	t	Настельні світильники
31524200-7	Wall light fittings	5	f	Настінна освітлювальна арматура
31524210-0	Wall lights	6	t	Настінні світильники
31527000-6	Spotlights	4	f	Точкові світильники
31527200-8	Exterior lights	5	f	Світильники зовнішнього освітлення
31527210-1	Lanterns	6	t	Ліхтарі
31527260-6	Lighting systems	6	t	Освітлювальні системи
31527270-9	Platforms lighting	6	t	Платформні освітлювальні системи
31527300-9	Domestic lights	5	t	Побутові освітлювальні прилади
31527400-0	Underwater lights	5	t	Підводні світильники
31530000-0	Parts of lamps and lighting equipment	3	f	Частини до світильників та освітлювального обладнання
31531000-7	Light bulbs	4	f	Лампи
31531100-8	Electric tubes	5	t	Електровакуумні лампи
31532000-4	Parts of lamps and light fittings	4	f	Частини до світильників та освітлювальної арматури
31532100-5	Tube lamps	5	f	Трубчасті лампи
31532110-8	Fluorescent tube lamps	6	t	Люмінісцентні трубчасті лампи
31532120-1	Compact fluorescent tube lamps	6	t	Компактні люмінісцентні трубчасті лампи
31532200-6	Ring lamps	5	f	Кільцеві лампи
31532210-9	Fluorescent ring lamps	6	t	Люмінісцентні кільцеві лампи
31532300-7	Globe lamps	5	f	Кульові лампи
31532310-0	Compact fluorescent globe lamps	6	t	Компактні люмінісцентні кульові лампи
31532400-8	Lamps sockets	5	t	Патрони
31532500-9	Lamp starters	5	f	Стартери
31532510-2	Starters for fluorescent lamps	6	t	Стартери для люмінісцентних ламп
31532600-0	Lamp reactors	5	f	Реактори
31532610-3	Reactors for fluorescent lamps	6	t	Реактори для люмінісцентних ламп
31532700-1	Lamp covers	5	t	Абажури
31532800-2	Lamp arms	5	t	Гнучкі ніжки для світильників
31532900-3	Fluorescent lights	5	f	Люмінісцентні лампи
31532910-6	Fluorescent tubes	6	t	Люмінісцентні трубки
31532920-9	Bulbs and fluorescent lamps	6	t	Лампи розжарення та люмінісцентні лампи
31600000-2	Electrical equipment and apparatus	2	f	Електричні обладнання та апаратура
31610000-5	Electrical equipment for engines and vehicles	3	f	Електричне обладнання для двигунів і транспортних засобів
31611000-2	Wiring sets	4	t	Комплекти дротів
31612000-9	Electrical wiring looms for engines	4	f	Джгути дротів для двигунів
31612200-1	Starter motors	5	t	Стартери
31612300-2	Electrical signalling equipment for engines	5	f	Електросигнальні системи керування двигуном
31612310-5	Blinkers	6	t	Світлосигнальна апаратура
31620000-8	Sound or visual signalling apparatus	3	f	Прилади звукової та візуальної сигналізації
31625000-3	Burglar and fire alarms	4	f	Пристрої охоронної та пожежної сигналізації
31625100-4	Fire-detection systems	5	t	Системи виявлення пожежі
31625200-5	Fire-alarm systems	5	t	Системи пожежної сигналізації
31625300-6	Burglar-alarm systems	5	t	Системи охоронної сигналізації
31630000-1	Magnets	3	t	Магніти
31640000-4	Machines and apparatus with individual functions	3	f	Машини та апарати спеціального призначення
31642000-8	Electronic detection apparatus	4	f	Електронні детектори
31642100-9	Detection apparatus for metal pipes	5	t	Детектори металевих труб
31642200-0	Detection apparatus for mines	5	t	Міношукачі
31642300-1	Detection apparatus for plastics	5	t	Пластмасодетектори
31642400-2	Detection apparatus for non-metallic objects	5	t	Детектори неметалевих об’єктів
31642500-3	Detection apparatus for timber	5	t	Детектори деревини
31643000-5	Particle accelerators	4	f	Прискорювачі заряджених частинок
31643100-6	Linear accelerators	5	t	Лінійні прискорювачі
31644000-2	Miscellaneous data recorders	4	t	Реєстратори даних різні
31645000-9	Pinball machines	4	t	Автомати для гри в пінбол
31650000-7	Insulating fittings	3	f	Ізоляційне приладдя
31651000-4	Electrical tape	4	t	Електроізоляційна стрічка
31660000-0	Carbon electrodes	3	t	Вугільні електроди
31670000-3	Electrical parts of machinery or apparatus	3	f	Електричні частини машин чи апаратів
31671000-0	Glass envelopes and cathode-ray tubes	4	f	Скляні колби та електронно-променеві трубки
31671100-1	Glass envelopes	5	t	Скляні колби
31671200-2	Cathode-ray tubes	5	t	Електронно-променеві трубки
31680000-6	Electrical supplies and accessories	3	f	Електричне приладдя та супутні товари до електричного обладнання
31681000-3	Electrical accessories	4	f	Супутні товари до електричного обладнання
31681100-4	Electrical contacts	5	t	Електричні контакти
31681200-5	Electric pumps	5	t	Електричні насоси
31681300-6	Electrical circuits	5	t	Електричні схеми
31681400-7	Electrical components	5	f	Електричні деталі
31681410-0	Electrical materials	6	t	Електричні матеріали
31681500-8	Rechargers	5	t	Зарядні пристрої
31682000-0	Electricity supplies	4	f	Електричне приладдя
31682100-1	Electricity boxes	5	f	Електрошафи
31682110-4	Electricity box covers	6	t	Накривки для електрошаф
31682200-2	Instrument panels	5	f	Панелі приладів
31682210-5	Instrumentation and control equipment	6	t	Вимірювальне та контрольне обладнання
31682220-8	Mixing panels	6	t	Мікшувальні пульти
31682230-1	Graphic display panels	6	t	Панелі з графічним дисплеєм
31682300-3	Medium-voltage equipment	5	f	Обладнання середньої напруги
31682310-6	Medium-voltage panels	6	t	Панелі середньої напруги
31682400-4	Overhead electrical equipment	5	f	Обладнання для повітряних ліній електропередачі
31682410-7	Overhead cable carriers	6	t	Опори повітряних ліній електропередачі
31682500-5	Emergency electricity equipment	5	f	Обладнання для забезпечення аварійного живлення
31682510-8	Emergency power systems	6	t	Системи аварійного живлення
31682520-1	Emergency shutdown systems	6	t	Системи аварійного вимкнення
31682530-4	Emergency power supplies	6	t	Джерела аварійного живлення
31682540-7	Substation equipment	6	t	Устаткування для підстанцій
31700000-3	Electronic, electromechanical and electrotechnical supplies	2	f	Електронне, електромеханічне та електротехнічне обладнання
31710000-6	Electronic equipment	3	f	Електронне обладнання
31711000-3	Electronic supplies	4	f	Електронне приладдя
31711100-4	Electronic components	5	f	Електронні компоненти
31711110-7	Transceivers	6	t	Приймодавачі
31711120-0	Transducers	6	t	Вимірювальні перетворювачі
31711130-3	Resistors	6	f	Резистори
31711131-0	Electrical resistors	7	t	Електричні резистори
31711140-6	Electrodes	6	t	Електроди
31711150-9	Electrical capacitors	6	f	Електричні конденсатори
31711151-6	Fixed capacitors	7	t	Конденсатори постійної ємності
31711152-3	Variable or adjustable capacitors	7	t	Конденсатори змінної та регульованої ємності
31711154-0	Capacitor banks	7	t	Конденсаторні батареї
31711155-7	Capacitor networks	7	t	Конденсаторні мережі
31711200-5	Electronic scoreboards	5	t	Електронні табло
31711300-6	Electronic timekeeping systems	5	f	Електронні хронометражні системи
31711310-9	System for recording attendance	6	t	Системи обліку відвідувачів
31711400-7	Valves and tubes	5	f	Електронні лампи та трубки
31711410-0	Cathode-ray television picture tubes	6	f	Електронно-променеві трубки
31711411-7	Television camera tubes	7	t	Передавальні телевізійні трубки
31711420-3	Microwave tubes and equipment	6	f	Лампи та пристрої надвисокочастотного діапазону
31711421-0	Magnetrons	7	t	Магнетрони
31711422-7	Microwave equipment	7	t	Пристрої надвисокочастотного діапазону
31711423-4	Microwave radio equipment	7	t	Радіообладнання надвисокочастотного діапазону
31711424-1	Klystrons	7	t	Клістрони
31711430-6	Valve tubes	6	t	Кенотрони
31711440-9	Receiver or amplifier valves and tubes	6	t	Приймально-підсилювальні лампи та трубки
31711500-8	Parts of electronic assemblies	5	f	Частини електронних блоків
31711510-1	Parts of electrical capacitors	6	t	Частини електричних конденсаторів
31711520-4	Parts of electrical resistors, rheostats and potentiometers	6	t	Частини електричних резисторів, реостатів і потенціометрів
31711530-7	Parts of electronic valves and tubes	6	t	Частини електронних ламп і трубок
31712000-0	Microelectronic machinery and apparatus and microsystems	4	f	Мікроелектронні машини й апарати та мікросистеми
31712100-1	Microelectronic machinery and apparatus	5	f	Мікроелектронні машини й апарати
31712110-4	Electronic integrated circuits and microassemblies	6	f	Електронні інтегральні схеми та мікроблоки
31712111-1	Phone cards	7	t	Телефонні картки
31712112-8	SIM cards	7	t	SIM-картки
31712113-5	Cards containing integrated circuits	7	t	Картки з інтегральними схемами
31712114-2	Integrated electronic circuits	7	t	Електронні інтегральні схеми
31712115-9	Microassemblies	7	t	Мікроблоки
31712116-6	Microprocessors	7	t	Мікропроцесори
31712117-3	Integrated circuit packages	7	t	Корпуси інтегральних схем
31712118-0	Integrated circuit sockets or mounts	7	t	Гнізда чи кріплення для інтегральних схем
31712119-7	Integrated circuit lids	7	t	Кришки корпусу інтегральних схем
31712200-2	Microsystems	5	t	Мікросистеми
31712300-3	Printed circuits	5	f	Друковані плати
31712310-6	Populated printed circuit boards	6	t	Змонтовані друковані плати
31712320-9	Unpopulated printed circuit boards	6	t	Незмонтовані друковані плати
31712330-2	Semiconductors	6	f	Напівпровідникові прилади
31712331-9	Photovoltaic cells	7	t	Фотоелементи
31712332-6	Thyristors	7	t	Тиристори
31712333-3	Diacs	7	t	Діаки
31712334-0	Triacs	7	t	Тріаки
31712335-7	Optical coupled isolators	7	t	Оптопари
31712336-4	Crystal oscillators	7	t	Кварцові осцилятори
31712340-5	Diodes	6	f	Діоди
31712341-2	Light-emitting diodes	7	t	Світлодіоди
31712342-9	Microwave or small signal diodes	7	t	Надвисокочастотні діоди або діоди для слабких сигналів
31712343-6	Zener diodes	7	t	Стабілітрони (діоди Зенера)
31712344-3	Schottky diodes	7	t	Діоди Шотткі
31712345-0	Tunnel diodes	7	t	Тунельні діоди
31712346-7	Photosensitive diodes	7	t	Фоточутливі діоди
31712347-4	Power or solar diodes	7	t	Силові діоди чи сонячні елементи з вбудованими діодами
31712348-1	Laser diodes	7	t	Лазерні діоди
31712349-8	Radio frequency (RF) diodes	7	t	Високочастотні діоди
31712350-8	Transistors	6	f	Транзистори
31712351-5	Photo sensitive transistors	7	t	Фоточутливі транзистори
31712352-2	Field effect transistors (FET)	7	t	Польові транзистори
31712353-9	Metal oxide field effect transistors (MOSFET)	7	t	Метал-оксидні польові транзистори (MOSFET)
31712354-6	Transistor chips	7	t	Чипи транзисторів
31712355-3	Bipolar darlington or radio frequency (RF) transistors	7	t	Біполярні транзистори Дарлінгтона чи радіочастотні транзистори
31712356-0	Unijunction transistors	7	t	Одноперехідні транзистори
31712357-7	Insulated gate bipolar transistors (IGBT)	7	t	Біполярні транзистори з ізольованим затвором
31712358-4	Junction field effect transistors (JFET)	7	t	Польові транзистори з керованим переходом
31712359-1	Bipolar junction transistors (BJT)	7	t	Біполярні площинні транзистори
31712360-1	Mounted piezo-electric crystals	6	t	Змонтовані п’єзоелектричні кристали
31720000-9	Electromechanical equipment	3	t	Електромеханічне обладнання
31730000-2	Electrotechnical equipment	3	f	Електротехнічне обладнання
31731000-9	Electrotechnical supplies	4	f	Електротехнічне приладдя
31731100-0	Modules	5	t	Модулі
32000000-3	Radio, television, communication, telecommunication and related equipment	1	f	Радіо-, телевізійна, комунікаційна, телекомунікаційна та супутня апаратура й обладнання
32200000-5	Transmission apparatus for radiotelephony, radiotelegraphy, radio broadcasting and television	2	f	Передавальна апаратура для радіотелефонії, радіотелеграфії, радіомовлення і телебачення
32210000-8	Broadcasting equipment	3	f	Обладнання для телерадіомовлення
32211000-5	Broadcast production equipment	4	t	Виробниче устаткування для телерадіомовлення
32220000-1	Television transmission apparatus without reception apparatus	3	f	Телевізійна передавальна апаратура без приймальних пристроїв
32221000-8	Radio beacons	4	t	Радіомаяки
32222000-5	Video-signal coding machines	4	t	Пристрої кодування відеосигналів
32223000-2	Video transmission apparatus	4	t	Апаратура для передавання відеосигналу
32224000-9	Television transmission apparatus	4	t	Телевізійна передавальна апаратура
32230000-4	Radio transmission apparatus with reception apparatus	3	f	Апаратура для передавання радіосигналу з приймальним пристроєм
32231000-1	Closed-circuit television apparatus	4	t	Апаратура для замкнутих телевізійних систем
32232000-8	Video-conferencing equipment	4	t	Обладнання для відеоконференцій
32233000-5	Radio-frequency booster stations	4	t	Ретрансляційні радіостанції
32234000-2	Closed-circuit television cameras	4	t	Камери відеоспостереження
33621200-1	Antihaemorrhagics	5	t	Кровоспинні засоби
32235000-9	Closed-circuit surveillance system	4	t	Системи відеоспостереження замкнутого типу
32236000-6	Radio telephones	4	t	Радіотелефони
32237000-3	Walkie-talkies	4	t	Портативні радіостанції
32240000-7	Television cameras	3	t	Телевізійні камери
32250000-0	Mobile telephones	3	f	Мобільні телефони
32251000-7	Car telephones	4	f	Автомобільні телефони
32251100-8	Hands-free set	5	t	Гарнітури «вільні руки»
32252000-4	GSM telephones	4	f	Телефони стандарту GSM
32252100-5	Hands-free mobile telephones	5	f	Мобільні телефони з гарнітурою «вільні руки»
32252110-8	Hands-free mobile telephones (wireless)	6	t	Мобільні телефони з бездротовою гарнітурою «вільні руки»
32260000-3	Data-transmission equipment	3	t	Обладнання для передавання даних
32270000-6	Digital transmission apparatus	3	t	Цифрова передавальна апаратура
32300000-6	Television and radio receivers, and sound or video recording or reproducing apparatus	2	f	Радіо- і телевізійні приймачі, апаратура для запису та відтворення аудіо- та відеоматеріалу
32310000-9	Radio broadcast receivers	3	t	Радіоприймачі
32320000-2	Television and audio-visual equipment	3	f	Телевізійне й аудіовізуальне обладнання
32321000-9	Television projection equipment	4	f	Телепроекційне обладнання
32321100-0	Film equipment	5	t	Кіноапаратура
32321200-1	Audio-visual equipment	5	t	Аудіовізуальне обладнання
32321300-2	Audio-visual materials	5	t	Аудіовізуальні матеріали
32322000-6	Multimedia equipment	4	t	Мультимедійне обладнання
32323000-3	Video monitors	4	f	Відеомонітори
32323100-4	Colour video monitors	5	t	Кольорові відеомонітори
32323200-5	Monochrome video monitors	5	t	Монохромні відеомонітори
32323300-6	Video equipment	5	t	Відеообладнання
32323400-7	Video-playback equipment	5	t	Відеовідтворювальне обладнання
32323500-8	Video-surveillance system	5	t	Системи відеоспостереження
32324000-0	Televisions	4	f	Телевізори
32324100-1	Colour televisions	5	t	Кольорові телевізори
32324200-2	Monochrome televisions	5	t	Монохромні телевізори
32324300-3	Television equipment	5	f	Телевізійне обладнання
32324310-6	Satellite antennas	6	t	Супутникові антени
32324400-4	Television aerials	5	t	Телевізійні антени
32324500-5	Video tuners	5	t	Відеотюнери
32324600-6	Digital-TV boxes	5	t	Приймачі цифрового телебачення
32330000-5	Apparatus for sound, video-recording and reproduction	3	f	Апаратура для запису та відтворення аудіо- та відеоматеріалу
32331000-2	Turntables	4	f	Електропрогравачі
32331100-3	Record players	5	t	Програвачі грамофонних платівок
32331200-4	Cassette players	5	t	Касетні програвачі
32331300-5	Sound-reproduction apparatus	5	t	Звуковідтворювальна апаратура
32331500-7	Recorders	5	t	Записувальні прилади
32331600-8	MP3 player	5	t	MP3-плеєри
32332000-9	Magnetic tape recorders	4	f	Магнітофони
32332100-0	Dictating machines	5	t	Диктофони
32332200-1	Telephone-answering machines	5	t	Телефонні автовідповідачі
32332300-2	Sound recorders	5	t	Звукозаписувальні пристрої
32333000-6	Video recording or reproducing apparatus	4	f	Апаратура для відеозапису та відео відтворення
32333100-7	Video recorders	5	t	Відеомагнітофони
32333200-8	Video camcorders	5	t	Камкодери
32333300-9	Video-reproducing apparatus	5	t	Відеовідтворювальна апаратура
32333400-0	Video players	5	t	Відеоплеєри
32340000-8	Microphones and loudspeakers	3	f	Мікрофони та гучномовці
32341000-5	Microphones	4	t	Мікрофони
32342000-2	Loudspeakers	4	f	Гучномовці
32342100-3	Headphones	5	t	Головні гарнітури
32342200-4	Earphones	5	t	Навушники
32342300-5	Microphones and speaker sets	5	t	Мікрофони та звукові колонки
32342400-6	Acoustic devices	5	f	Акустичні пристрої
32342410-9	Sound equipment	6	f	Звукове обладнання
32342411-6	Mini speakers	7	t	Акустичні мініатюрні системи
32342412-3	Speakers	7	t	Динаміки
32342420-2	Studio mixing console	6	t	Студійні мікшувальні пульти
32342430-5	Speech-compression system	6	t	Системи стиснення голосових даних
32342440-8	Voice-mail system	6	t	Системи голосової пошти
32342450-1	Voice recorders	6	t	Пристрої запису усного мовлення
32343000-9	Amplifiers	4	f	Підсилювачі
32343100-0	Audio-frequency amplifiers	5	t	Підсилювачі звукових частот
32343200-1	Megaphones	5	t	Мегафони
32344000-6	Reception apparatus for radiotelephony or radiotelegraphy	4	f	Приймальна апаратура для радіотелефонії чи радіотелеграфії
34928420-8	Road-danger lamps	6	t	Сигнальні ліхтарі
32344100-7	Portable receivers for calling and paging	5	f	Портативні приймачі сигналів виклику і пристрої пейджингового зв’язку
32344110-0	Voice-logging system	6	t	Системи запису телефонних розмов
32344200-8	Radio receivers	5	f	Радіоприймачі
32344210-1	Radio equipment	6	t	Радіообладнання
32344220-4	Radio pagers	6	t	Радіопейджери
32344230-7	Radio stations	6	t	Радіостанції
32344240-0	Radio tower	6	t	Радіовежі
32344250-3	Radio installations	6	t	Радіоустановки
32344260-6	Radio and multiplex equipment	6	t	Радіо- та мультиплексне обладнання
32344270-9	Radio and telephone control system	6	t	Системи радіокерування і телефонного контролю
32344280-2	Portable radios	6	t	Рації
32350000-1	Parts of sound and video equipment	3	f	Частини до аудіо- та відеообладнання
32351000-8	Accessories for sound and video equipment	4	f	Приладдя до аудіо- та відеообладнання
32351100-9	Video-editing equipment	5	t	Відеомонтажне обладнання
32351200-0	Screens	5	t	Екрани
32351300-1	Audio equipment accessories	5	f	Приладдя до аудіообладнання
32351310-4	Audio cassettes	6	t	Аудіокасети
32352000-5	Aerials and reflectors	4	f	Антени та антенні відбивачі
32352100-6	Parts of radio and radar equipment	5	t	Частини радіо- та радіолокаційного обладнання
32352200-7	Radar spare parts and accessories	5	t	Запасні частини та приладдя до радіолокаторів
32353000-2	Sound recordings	4	f	Носії звукозапису
32353100-3	Records	5	t	Грамофонні платівки
32353200-4	Music cassettes	5	t	Музичні касети
32354000-9	Film products	4	f	Плівки
32354100-0	Radiology film	5	f	Радіологічна плівка
32354110-3	X-ray film	6	t	Рентгенівська плівка
32354120-6	Blue diazo film	6	t	Діазоплівка
32354200-1	Cinematographic film	5	t	Кіноплівка
32354300-2	Photographic film	5	t	Фотоплівка
32354400-3	Instant-print film	5	t	Плівка для миттєвої фотографії
32354500-4	Video films	5	t	Відеоплівки
32354600-5	Video cassettes	5	t	Відеокасети
32354700-6	Video tapes	5	t	Відеострічка
32354800-7	Cling film	5	t	Харчова плівка
32360000-4	Intercom equipment	3	t	Переговорне обладнання
32400000-7	Networks	2	f	Мережі
32410000-0	Local area network	3	f	Локальні мережі
32411000-7	Token-ring network	4	t	Кільцеві мережа з маркерним доступом
32412000-4	Communications network	4	f	Комунікаційні мережі
32412100-5	Telecommunications network	5	f	Телекомунікаційні мережі
32412110-8	Internet network	6	t	Мережа Інтернет
32412120-1	Intranet network	6	t	Мережа Інтранет
32413000-1	Integrated network	4	f	Інтегровані мережі
32413100-2	Network routers	5	t	Маршрутизатори
32415000-5	Ethernet network	4	t	Мережа Ethernet
32416000-2	ISDN network	4	f	Цифрові мережі з інтегрованими службами (мережа ISDN)
32416100-3	ISDX network	5	t	Мережі цифрових автоматичних телефонних станцій з інтегрованими службами (мережі ISDX)
32417000-9	Multimedia networks	4	t	Мультимедійні мережі
32418000-6	Radio network	4	t	Радіомережі
32420000-3	Network equipment	3	f	Мережеве обладнання
32421000-0	Network cabling	4	t	Мережеві кабелі
32422000-7	Network components	4	t	Мережеві компоненти
32423000-4	Network hubs	4	t	Мережеві концентратори
32424000-1	Network infrastructure	4	t	Мережеві інфраструктури
32425000-8	Network operating system	4	t	Мережеві операційні системи
32426000-5	Network publishing system	4	t	Мережеві видавничі системи
32427000-2	Network system	4	t	Мережеві системи
32428000-9	Network upgrade	4	t	Модернізація мереж
32429000-6	Telephone network equipment	4	t	Обладнання для телефонних мереж
32430000-6	Wide area network	3	t	Глобальні мережі
32440000-9	Telemetry and terminal equipment	3	f	Телеметричне та термінальне обладнання
32441000-6	Telemetry equipment	4	f	Телеметричне обладнання
32441100-7	Telemetry surveillance system	5	t	Телеметричні системи відеоспостереження
32441200-8	Telemetry and control equipment	5	t	Телеметричне та контрольне обладнання
32441300-9	Telematics system	5	t	Телематичні системи
32442000-3	Terminal equipment	4	f	Термінальне обладнання
32442100-4	Terminal boards	5	t	Контактні плати
32442200-5	Terminal boxes	5	t	Кабельні муфти
32442300-6	Terminal emulators	5	t	Термінальні емулятори
32442400-7	Termination blocks	5	t	Термінальні блоки
32500000-8	Telecommunications equipment and supplies	2	f	Телекомунікаційне обладнання та приладдя
32510000-1	Wireless telecommunications system	3	t	Бездротові телекомунікаційні системи
32520000-4	Telecommunications cable and equipment	3	f	Телекомунікаційні кабелі та обладнання
32521000-1	Telecommunications cable	4	t	Телекомунікаційні кабелі
32522000-8	Telecommunications equipment	4	t	Телекомунікаційне обладнання
32523000-5	Telecommunications facilities	4	t	Телекомунікаційні інфраструктури
32524000-2	Telecommunications system	4	t	Телекомунікаційні системи
32530000-7	Satellite-related communications equipment	3	f	Телекомунікаційне супутникове обладнання
32531000-4	Satellite communications equipment	4	t	Обладнання супутникового зв’язку
32532000-1	Satellite dishes	4	t	Супутникові антени (тарілки)
32533000-8	Satellite earth stations	4	t	Супутникові наземні станції
32534000-5	Satellite platforms	4	t	Супутникові платформи
32540000-0	Switchboards	3	f	Комутаційні щити
32541000-7	Switchboard equipment	4	t	Комутаційне обладнання
32542000-4	Switchboard panels	4	t	Комутаційні панелі
32543000-1	Telephone switchboards	4	t	Телефонні комутатори
32544000-8	PABX equipment	4	t	Обладнання для приватних автоматичних телефонних станцій
32545000-5	PABX systems	4	t	Системи приватних автоматичних телефонних станцій
32546000-2	Digital switching equipment	4	f	Цифрове комутаційне обладнання
32546100-3	Digital switchboards	5	t	Цифрові комутатори
32547000-9	Vacuum switchboards	4	t	Вакуумні комутатори
32550000-3	Telephone equipment	3	f	Телефонне обладнання
32551000-0	Telephone cables and associated equipment	4	f	Телефонні кабелі та супутня продукція
32551100-1	Telephone connections	5	t	Телефонні з’єднувачі
32551200-2	Telephone exchanges	5	t	Автоматичні телефонні станції
32551300-3	Telephone headsets	5	t	Телефонна гарнітура
32551400-4	Telephone network	5	t	Телефонні мережі
32551500-5	Telephone cables	5	t	Телефонні кабелі
32552000-7	Electrical apparatus for line telephony or line telegraphy	4	f	Електрична апаратура для дротової телефонії чи дротової телеграфії
32552100-8	Telephone sets	5	f	Телефони
32552110-1	Cordless telephones	6	t	Бездротові телефони
32552120-4	Emergency telephones	6	t	Телефони аварійного зв’язку
32552130-7	Public telephones	6	t	Таксофони
32552140-0	Payphone equipment	6	t	Таксофонне обладнання
32552150-3	Telephones for visually-impaired	6	t	Телефони для осіб із вадами зору
32552160-6	Telephones for hearing-impaired	6	t	Телефони для осіб із вадами слуху
32552200-9	Teleprinters	5	t	Телетайпи
32552300-0	Telephonic or telegraphic switching apparatus	5	f	Телефонні чи телеграфні комутаційні апарати
32552310-3	Digital telephone exchanges	6	t	Цифрові телефонні станції
32552320-6	Multiplexers	6	t	Мультиплексори
32552330-9	Telephone switching apparatus	6	t	Телефонні комутаційні апарати
32552400-1	Audio-frequency signal conversion apparatus	5	f	Пристрої перетворення сигналів звукових частот
32552410-4	Modems	6	t	Модеми
32552420-7	Frequency converter	6	t	Перетворювачі частоти
32552430-0	Coding equipment	6	t	Кодувальне обладнання
32552500-2	Teletext apparatus	5	f	Телетекстова апаратура
32552510-5	Videotext terminals	6	t	Відеотекстові термінали
32552520-8	Telex equipment	6	t	Телексне обладнання
32552600-3	Entrance telephones	5	t	Домофони
32553000-4	Parts of electrical telephonic or telegraphic apparatus	4	t	Частини електричної телефонної та телеграфної апаратури
32560000-6	Fibre-optic materials	3	f	Оптоволоконні матеріали
32561000-3	Fibre-optic connections	4	t	Оптоволоконні з’єднувачі
32562000-0	Optical-fibre cables	4	f	Оптоволоконні кабелі
32562100-1	Optical-fibre cables for information transmission	5	t	Оптоволоконні кабелі для передачі інформації
32562200-2	Optical telecommunication cables	5	t	Оптичні телекомунікаційні кабелі
32562300-3	Optical-fibre cables for data transmission	5	t	Оптоволоконні кабелі для передачі даних
32570000-9	Communications equipment	3	f	Комунікаційне обладнання
32571000-6	Communications infrastructure	4	t	Комунікаційні інфраструктури
32572000-3	Communications cable	4	f	Комунікаційні кабелі
32572100-4	Communications cable with multiple electrical conductors	5	t	Комунікаційні багатопровідні кабелі
32572200-5	Communications cable with coaxial conductors	5	t	Комунікаційні кабелі з коаксіальними провідниками
32572300-6	Communications cable for special applications	5	t	Спеціальні комунікаційні кабелі
32573000-0	Communications control system	4	t	Системи зв’язкового керування
32580000-2	Data equipment	3	f	Інформаційне обладнання
32581000-9	Data-communications equipment	4	f	Обладнання для передачі даних
32581100-0	Data-transmission cable	5	f	Кабелі для передачі даних
32581110-3	Data-transmission cable with multiple electrical conductors	6	t	Багатопровідні кабелі для передачі даних
32581120-6	Data-transmission cable with coaxial conductors	6	t	Кабелі з коаксіальними провідниками для передачі даних
32581130-9	Data-transmission cable for special applications	6	t	Спеціальні кабелі для передачі даних
32581200-1	Fax equipment	5	f	Факсимільне обладнання
32581210-4	Accessories and components for fax equipment	6	t	Частини факсимільного обладнання та приладдя до нього
32582000-6	Data carriers	4	t	Носії даних
32583000-3	Data and voice media	4	t	Носії для зберігання даних, у тому числі голосових
32584000-0	Data-bearing media	4	t	Носії із записаними даними
33000000-0	Medical equipments, pharmaceuticals and personal care products	1	f	Медичне обладнання, фармацевтична продукція та засоби особистої гігієни
33100000-1	Medical equipments	2	f	Медичне обладнання
33110000-4	Imaging equipment for medical, dental and veterinary use	3	f	Візуалізаційне обладнання для потреб медицини, стоматології та ветеринарної медицини
33111000-1	X-ray devices	4	f	Рентгенологічне обладнання
33111100-2	X-ray table	5	t	Рентгенівські столи
33111200-3	X-ray workstations	5	t	Автоматизовані робочі місця рентгенолога
33111300-4	X-ray processing devices	5	t	Пристрої для проявлення рентгенівської плівки
33111400-5	X-ray fluoroscopy devices	5	t	Рентгеноскопи
33111500-6	Dental X-ray	5	t	Рентгеноскопи для стоматологічної діагностики
33111600-7	Radiography devices	5	f	Радіографічна апаратура
33111610-0	Magnetic resonance unit	6	t	Магнітно-резонансні установки
33111620-3	Gamma cameras	6	t	Гамма-камери
33111640-9	Thermographs	6	t	Термографи
33111650-2	Mammography devices	6	t	Мамографічні апарати
33111660-5	Bone densitometers	6	t	Остеоденсиметри
33111700-8	Angiography room	5	f	Устаткування ангіографічних кабінетів
33111710-1	Angiography supplies	6	t	Ангіографічне приладдя
33111720-4	Angiography devices	6	f	Ангіографічні апарати
33111721-1	Digital angiography devices	7	t	Цифрові ангіографічні апарати
33111730-7	Angioplasty supplies	6	t	Приладдя для ангіопластики
33111740-0	Angioplasty devices	6	t	Обладнання для ангіопластики
33111800-9	Diagnostic X-ray system	5	t	Рентгенодіагностичні системи
33112000-8	Echo, ultrasound and doppler imaging equipment	4	f	Візуалізаційне обладнання з використанням ехографії, ультразвуку чи доплерографії
33112100-9	Ultrasonic heart detector	5	t	Ехокардіографи
33112200-0	Ultrasonic unit	5	t	Ультразвукові установки
33112300-1	Ultrasound scanners	5	f	Ультразвукові сканери
33112310-4	Colour-flow doppler	6	t	Апарати для кольорової доплерографії
33112320-7	Doppler equipment	6	t	Обладнання на основі ефекту Доплера
33112330-0	Echoencephalographs	6	t	Ехоенцефалографи
33112340-3	Echocardiographs	6	t	Ехокардіографи
33113000-5	Magnetic resonance imaging equipment	4	f	Обладнання для магнітно-резонансної томографії
33113100-6	Magnetic resonance scanners	5	f	Магнітно-резонансні сканери
33113110-9	Nuclear magnetic resonance scanners	6	t	Ядерно-магнітно-резонансні сканери
33114000-2	Spectroscopy devices	4	t	Спектроскопічне обладнання
33115000-9	Tomography devices	4	f	Томографічне обладнання
33115100-0	CT scanners	5	t	Сканери для комп’ютерної томографії
33115200-1	CAT scanners	5	t	Сканери для комп’ютерної аксіальної томографії
33120000-7	Recording systems and exploration devices	3	f	Системи реєстрації медичної інформації та дослідне обладнання
33121000-4	Long term ambulatory recording system	4	f	Системи тривалого амбулаторного спостереження
33121100-5	Electro-encephalographs	5	t	Електроенцефалографи
33121200-6	Scintigraphy devices	5	t	Сцинтиграфічні прилади
33121300-7	Electromyographs	5	t	Електроміографи
33121400-8	Audiometers	5	t	Аудіометри
33121500-9	Electrocardiogram	5	t	Електрокардіографи
33122000-1	Ophthalmology equipment	4	t	Офтальмологічне обладнання
33123000-8	Cardiovascular devices	4	f	Обладнання для обстеження серцево-судинної системи
33123100-9	Tensiometer	5	t	Тензіометри
33123200-0	Electrocardiography devices	5	f	Електрокардіографічні прилади
33123210-3	Cardiac-monitoring devices	6	t	Прилади для серцевого моніторингу
33123220-6	Cardio-angiography devices	6	t	Кардіоангіографи
33123230-9	Cardiographs	6	t	Кардіографи
33124000-5	Diagnostics and radiodiagnostic devices and supplies	4	f	Апаратура та приладдя для діагностики і рентгенодіагностики
33124100-6	Diagnostic devices	5	f	Діагностичні прилади
33124110-9	Diagnostic systems	6	t	Діагностичні системи
33124120-2	Diagnostic ultrasound devices	6	t	Ультразвукові діагностичні прилади
33124130-5	Diagnostic supplies	6	f	Діагностичне приладдя
33124131-2	Reagent strips	7	t	Індикаторні смужки
33124200-7	Radiodiagnostic devices	5	f	Рентгенодіагностичні прилади
33124210-0	Radiodiagnostic supplies	6	t	Рентгенодіагностичне приладдя
33125000-2	Urology, exploration devices	4	t	Прилади для урологічного обстеження
33126000-9	Stomatology devices	4	t	Стоматологічні прилади
33127000-6	Immuno-analysis devices	4	t	Прилади для імунного аналізу
33128000-3	Medical laser other than for surgery	4	t	Медичні лазери, крім хірургічних
33130000-0	Dental and subspecialty instruments and devices	3	f	Стоматологічні та вузькоспеціалізовані інструменти та прилади
33131000-7	Dental hand instrument	4	f	Стоматологічні ручні інструменти
33131100-8	Dental surgical instrument	5	f	Стоматологічні хірургічні інструменти
33131110-1	Dental nippers, brushes, retractors and burnishers	6	f	Стоматологічні затискачі, щітки, ретрактори та полірувальні пристрої
33131111-8	Dental nippers	7	t	Стоматологічні затискачі
33131112-5	Dental operative brushes	7	t	Стоматологічні щітки
33131113-2	Dental retractors	7	t	Стоматологічні ретрактори
33131114-9	Dental burnishers	7	t	Стоматологічні полірувальні пристрої
33131120-4	Dental cryosurgical, gauges, elevators and excavators	6	f	Стоматологічні кріохірургічні інструменти, калібратори, елеватори та екскаватори
33131121-1	Dental cryosurgical units	7	t	Стоматологічні кріохірургійні інструменти
33131122-8	Dental depth gauges	7	t	Стоматологічні калібратори
33131123-5	Dental elevators	7	t	Стоматологічні елеватори
33131124-2	Dental excavators	7	t	Стоматологічні екскаватори
33131130-7	Dental fingers protectors and forceps	6	f	Стоматологічні протектори для пальців та щипці
33131131-4	Dental fingers protectors	7	t	Стоматологічні протектори для пальців
33131132-1	Dental forceps	7	t	Стоматологічні щипці
33131140-0	Dental mirrors and reamers	6	f	Стоматологічні дзеркала та каналонаповнювачі
33131141-7	Dental mirrors	7	t	Стоматологічні дзеркала
33131142-4	Dental reamers	7	t	Стоматологічні каналонаповнювачі
33131150-3	Dental root tip picks, scalers and scale	6	f	Щипці для видалення зубних коренів, інструменти для видалення зубного каменю та інструменти з градуйованою шкалою
33131151-0	Dental root tip picks	7	t	Щипці для видалення зубних коренів
33131152-7	Dental scalers	7	t	Інструменти для видалення зубного каменю
33131153-4	Dental scale	7	t	Стоматологічні інструменти з градуйованою шкалою
33131160-6	Dental scissors and knives	6	f	Стоматологічні ножиці та ножі
33131161-3	Dental scissors	7	t	Стоматологічні ножиці
33131162-0	Dental knives	7	t	Стоматологічні ножі
33131170-9	Dental spatulas, tweezers and wax carvers	6	f	Стоматологічні шпателі, пінцети та ножі для воску
33131171-6	Dental spatulas	7	t	Стоматологічні шпателі
33131172-3	Dental tweezers	7	t	Стоматологічні пінцети
33131173-0	Dental wax carvers	7	t	Ножі для стоматологічного воску
33131200-9	Dental suture needle	5	t	Стоматологічні хірургічні голки
33131300-0	Dental disposable instrument	5	t	Стоматологічні інструменти одноразового застосування
33131400-1	Dental probe	5	t	Стоматологічні зонди
33131500-2	Dental extraction instrument	5	f	Інструменти для видалення зубів
33131510-5	Dental drills	6	t	Стоматологічні бури
33131600-3	Dental filling instrument	5	t	Інструменти для пломбування зубів
33132000-4	Dental implant	4	t	Зубні імплантати
33133000-1	Dental impression accessories	4	t	Приладдя для виготовлення зубних зліпків
33134000-8	Endodontics accessories	4	t	Ендодонтичне приладдя
33135000-5	Orthodontic devices	4	t	Ортодонтичні пристрої
33136000-2	Rotary and abrasive instrument	4	t	Обертові та абразивні інструменти
33137000-9	Dental prophylaxis accessories	4	t	Приладдя для стоматологічної профілактики
33138000-6	Prosthodontic and relining products	4	f	Матеріали для протезування зубів та припасовування зубних протезів
33138100-7	Dentures	5	t	Зубні протези
33140000-3	Medical consumables	3	f	Медичні матеріали
33621300-2	Antianaemic preparations	5	t	Протианемічні засоби
33141000-0	Disposable non-chemical medical consumables and haematological consumables	4	f	Медичні матеріали нехімічні та гематологічні одноразового застосування
33141100-1	Dressings; clip, suture, ligature supplies	5	f	Перев’язувальні матеріали; затискачі, шовні матеріали, лігатури
33141110-4	Dressings	6	f	Перев’язувальні матеріали
33141111-1	Adhesive dressings	7	t	Клейкі перев’язувальні матеріали
33141112-8	Plasters	7	t	Пластирі
33141113-4	Bandages	7	t	Бинти
33141114-2	Medical gauze	7	t	Медична марля
33141115-9	Medical wadding	7	t	Медична вата
33141116-6	Dressing packs	7	t	Медичні тампони
33141117-3	Cotton wool	7	t	Бавовняна вата
33141118-0	Wipes	7	t	Медичні серветки
33141119-7	Compresses	7	t	Компреси
33141120-7	Clip, suture, ligature supplies	6	f	Затискачі, шовні матеріали, лігатури
33141121-4	Surgical sutures	7	t	Хірургічні шовні матеріали
33141122-1	Surgical staples	7	t	Хірургічні скоби
33141123-8	Sharps containers	7	t	Контейнери для голок
33141124-5	Sharps pads	7	t	Килимки для голок
33141125-2	Material for surgical sutures	7	t	Матеріали для накладання хірургічних швів
33141126-9	Ligatures	7	t	Лігатури
33141127-6	Absorbable haemostatics	7	t	Розсмоктувальні кровоспинні засоби
33141128-3	Needles for sutures	7	t	Голки для накладання швів
33141200-2	Catheters	5	f	Катетери
33141210-5	Balloon catheters	6	t	Балонні катетери
33141220-8	Cannulae	6	t	Канюлі
33141230-1	Dilator	6	t	Дилятори
33141240-4	Catheter accessories	6	t	Приладдя до катетерів
33141300-3	Venepuncture, blood sampling devices	5	f	Приладдя для венепункції та забору крові
33141310-6	Syringes	6	t	Шприци
33141320-9	Medical needles	6	f	Медичні голки
33141321-6	Anesthesia needles	7	t	Голки для анестезії
33141322-3	Arterial needles	7	t	Артеріальні голки
33141323-0	Biopsy needles	7	t	Голки для біопсії
33141324-7	Dialysis needles	7	t	Голки для діалізу
33141325-4	Fistula needles	7	t	Фістульні голки
33141326-1	Radiology procedural needles	7	t	Голки для радіологічних процедур
33141327-8	Vented needles	7	t	Голки з отворами
33141328-5	Epidural needles	7	t	Епідуральні голки
33141329-2	Amniocentesia needles	7	t	Голки для амніоцентезу
33141400-4	Wire-cutter and bistoury; surgical gloves	5	f	Кусачки та бістурі; хірургічні рукавички
33141410-7	Wire-cutter and bistoury	6	f	Кусачки та бістурі
33141411-4	Scalpels and blades	7	t	Скальпелі та леза
33141420-0	Surgical gloves	6	t	Хірургічні рукавички
33141500-5	Haematological consumables	5	f	Гематологічні матеріали
33141510-8	Blood products	6	t	Продукти переробки крові
33141520-1	Plasma extracts	6	t	Екстракти плазми
33141530-4	Blood coagulants	6	t	Коагулянти крові
33141540-7	Albumin	6	t	Альбумін
33141550-0	Heparin	6	t	Гепарин
33141560-3	Human organs	6	t	Органи людини
33141570-6	Human blood	6	t	Кров людини
33141580-9	Animal blood	6	t	Кров тварин
33141600-6	Collector and collection bags, drainage and kits	5	f	Контейнери та пакети для забору матеріалу для аналізів, дренажі та комплекти
33141610-9	Collection bag	6	f	Пакети для забору матеріалу для аналізів
33141613-0	Blood bags	7	t	Пакети для забору крові
33141614-7	Plasma bags	7	t	Пакети для забору плазми
33141615-4	Bags for urine	7	t	Пакети для забору сечі
33141620-2	Medical kits	6	f	Медичні комплекти
33141621-9	Incontinence kit	7	t	Комплекти для лікування нетримання сечі
33141622-6	AIDS-prevention kits	7	t	Комплекти для профілактики СНІДу
33141623-3	First-aid boxes	7	t	Набори першої допомоги
33141624-0	Administration sets	7	t	Набори для введення лікарських засобів
33141625-7	Diagnostic kits	7	t	Діагностичні набори
33141626-4	Dosage kits	7	t	Дозувальні набори
33141630-5	Blood plasma filters	6	t	Фільтри плазми крові
33141640-8	Drain	6	f	Дренажі
33141641-5	Probes	7	t	Зонди
33141642-2	Drain accessories	7	t	Дренажне приладдя
33141700-7	Orthopaedic supplies	5	f	Ортопедичні засоби
33141710-0	Crutches	6	t	Милиці
33141720-3	Walking aids	6	t	Допоміжні засоби для ходіння
33141730-6	Surgical collars	6	t	Ортопедичні шийні корсети
33141740-9	Orthopaedic footwear	6	t	Ортопедичне взуття
33141750-2	Artificial joints	6	t	Штучні суглоби
33141760-5	Splints	6	t	Шини
33696800-3	X-ray contrast media	5	t	Рентгеноконтрастні засоби
33141770-8	Fracture appliances, pins and plates	6	t	Пристрої для лікування переломів, спиці та пластини
33141800-8	Dental consumables	5	f	Стоматологічні матеріали
33141810-1	Dental filling materials	6	t	Матеріали для пломбування зубів
33141820-4	Teeth	6	f	Зубні протези
33141821-1	Porcelain teeth	7	t	Порцелянові зубні протези
33141822-8	Acrylic teeth	7	t	Акрилові зубні протези
33141830-7	Cement base	6	t	Пломбувальний цемент
33141840-0	Dental haemostatic	6	t	Стоматологічні кровоспинні засоби
33141850-3	Dental hygiene products	6	t	Засоби зубної гігієни
33141900-9	Blood lancets	5	t	Ланцети для забору крові
33150000-6	Radiotherapy, mechanotherapy, electrotherapy and physical therapy devices	3	f	Апаратура для радіотерапії, механотерапії, електротерапії та фізичної терапії
33151000-3	Radiotherapy devices and supplies	4	f	Радіотерапевтичні апарати та приладдя
33151100-4	Gamma therapy devices	5	t	Гамма-терапевтичні апарати
33151200-5	X-ray therapy devices	5	t	Рентгенотерапевтичні апарати
33151300-6	Spectrographs	5	t	Спектрографи
33151400-7	Radiotherapy supplies	5	t	Радіотерапевтичне приладдя
33152000-0	Incubators	4	t	Інкубатори
33153000-7	Lithotripter	4	t	Літотриптори
33154000-4	Mechanotherapy devices	4	t	Механотерапевтичні апарати
33155000-1	Physical therapy devices	4	t	Фізіотерапевтичні апарати
33156000-8	Psychology testing devices	4	t	Прилади для психологічного тестування
33157000-5	Gas-therapy and respiratory devices	4	f	Газотерапевтичні та респіраторні апарати
33157100-6	Medical gas masks	5	f	Медичні газові маски
33157110-9	Oxygen mask	6	t	Кисневі маски
33157200-7	Oxygen kits	5	t	Кисневі набори
33157300-8	Oxygen tents	5	t	Кисневі намети
33157400-9	Medical breathing devices	5	t	Медичні дихальні апарати
33157500-0	Hyperbaric chambers	5	t	Гіпербаричні камери
33157700-2	Blow bottle	5	t	Дихальний тренажер
33157800-3	Oxygen administration unit	5	f	Киснеподавальні пристрої
33157810-6	Oxygen therapy unit	6	t	Установка для кисневої терапії
33158000-2	Electrical, electromagnetic and mechanical treatment	4	f	Електрична, електромагнітна та механічна лікувальна апаратура
33158100-3	Electromagnetic unit	5	t	Електромагнітні пристрої
33158200-4	Electrotherapy devices	5	f	Електротерапевтичні прилади
33158210-7	Stimulator	6	t	Стимулятори
33158300-5	Ultraviolet medical devices	5	t	Апарати для лікування ультрафіолетовим випромінюванням
33158400-6	Mechanical therapy unit	5	t	Механотерапевтичні пристрої
33158500-7	Infrared medical devices	5	t	Апарати для лікування інфрачервоним випромінюванням
33159000-9	Clinical chemistry system	4	t	Системи для клінічних хімічних аналізів
33160000-9	Operating techniques	3	f	Устаткування для операційних блоків
33161000-6	Electrosurgical unit	4	t	Електрохірургічні прилади
33162000-3	Operating theatre devices and instruments	4	f	Апаратура та інструменти для операційних блоків
33162100-4	Operating-theatre devices	5	t	Апаратура для операційних блоків
33162200-5	Operating-theatre instruments	5	t	Інструменти для операційних блоків
33163000-0	Tent for medical use	4	t	Медичні намети
33164000-7	Coelioscopy devices	4	f	Обладнання для лапароскопічних операцій
33164100-8	Colposcope	5	t	Кольпоскопи
33165000-4	Cryosurgical and cryotherapy devices	4	t	Кріохірургічні та кріотерапевтичні інструменти
33166000-1	Dermatological devices	4	t	Дерматологічні інструменти
33167000-8	Surgical lights	4	t	Хірургічні світильники
33168000-5	Endoscopy, endosurgery devices	4	f	Ендоскопічні та ендохірургічні інструменти
33168100-6	Endoscopes	5	t	Ендоскопи
33169000-2	Surgical instruments	4	f	Хірургічні інструменти
33169100-3	Surgical laser	5	t	Хірургічні лазери
33169200-4	Surgical baskets	5	t	Хірургічні кошики
33169300-5	Surgical trays	5	t	Хірургічні лотки
33169400-6	Surgical containers	5	t	Хірургічні контейнери
33169500-7	Surgical tracking and tracing systems	5	t	Системи хірургічного моніторингу та відстежування
33170000-2	Anaesthesia and resuscitation	3	f	Обладнання для анестезії та реанімації
33171000-9	Instruments for anaesthesia and resuscitation	4	f	Анестезійні та реанімаційні інструменти
33171100-0	Instruments for anaesthesia	5	f	Анестезійні інструменти
33171110-3	Anaesthesia mask	6	t	Маски для анестезії
33171200-1	Instruments for resuscitation	5	f	Реанімаційні інструменти
33171210-4	Resuscitation mask	6	t	Маски для реанімації
33171300-2	Epidural kits or packs	5	t	Набори чи комплекти для епідуральної анестезії
33172000-6	Anaesthesia and resuscitation devices	4	f	Апаратура для анестезії та реанімації
33172100-7	Anaesthesia devices	5	t	Анестезійна апаратура
33172200-8	Resuscitation devices	5	t	Реанімаційна апаратура
33180000-5	Functional support	3	f	Апаратура для підтримування фізіологічних функцій організму
33181000-2	Renal support devices	4	f	Апарати для підтримування функції нирок
33181100-3	Haemodialysis devices	5	t	Апарати для гемодіалізу
33181200-4	Dialysis filters	5	t	Діалізні фільтри
33181300-5	Haemodialysis individual monitor	5	t	Індивідуальні пристрої для моніторингу гемодіалізу
33181400-6	Haemodialysis multiposition	5	t	Багатопозиційні апарати для гемодіалізу
33181500-7	Renal consumables	5	f	Матеріали для ниркової терапії
33181510-0	Renal fluid	6	t	Рідина для промивання нирок
33181520-3	Renal dialysis consumables	6	t	Матеріали для ниркового діалізу
33182000-9	Cardiac support devices	4	f	Апарати для підтримування серцевої функції
33182100-0	Defibrillator	5	t	Дефібрилятори
33182200-1	Cardiac stimulation devices	5	f	Апарати для серцевої стимуляції
33182210-4	Pacemaker	6	t	Кардіостимулятори
33182220-7	Cardiac valve	6	t	Штучні серцеві клапани
33182230-0	Ventricle	6	t	Штучні серцеві шлуночки
33182240-3	Parts and accessories for pacemakers	6	f	Частини та приладдя до кардіостимуляторів
33182241-0	Batteries for pacemakers	7	t	Акумуляторні батареї для кардіостимуляторів
33182300-2	Cardiac surgery devices	5	t	Апаратура для серцево-судинної хірургії
33182400-3	Cardiac X-ray system	5	t	Системи рентгенологічного дослідження серцево-судинної системи
33183000-6	Orthopaedic support devices	4	f	Протезно-ортопедичні вироби
33183100-7	Orthopaedic implants	5	t	Ортопедичні імплантати
33183200-8	Orthopaedic prostheses	5	t	Ортопедичні протези
33183300-9	Osteosynthesis devices	5	t	Обладнання для остеосинтезу
33184000-3	Artificial parts of the body	4	f	Протези
33184100-4	Surgical implants	5	t	Хірургічні імплантати
33184200-5	Vascular prostheses	5	t	Судинні протези
33184300-6	Artificial parts of the heart	5	t	Штучні частини серця
33184400-7	Breast prostheses	5	f	Протези молочної залози
33184410-0	Internal breast prostheses	6	t	Ендопротези молочної залози
33184420-3	External breast prostheses	6	t	Екзопротези молочної залози
33184500-8	Coronary endoprostheses	5	t	Коронарні ендопротези
33184600-9	False eyes	5	t	Очні протези
33185000-0	Hearing aids	4	f	Слухові апарати
33185100-1	Parts and accessories for hearing aids	5	t	Частини та приладдя до слухових апаратів
33185200-2	Cochlear implant	5	t	Кохлеарні імплантати
33185300-3	Otolaryngology implant	5	t	Отоларингологічні імплантати
33185400-4	Larynx artificial	5	t	Протези гортані
33186000-7	Extracorporeal circulatory unit	4	f	Апарати штучного кровообігу
33186100-8	Oxygenator	5	t	Оксигенератори
33186200-9	Blood and fluid warming	5	t	Апарати для підігріву крові та рідин
33190000-8	Miscellaneous medical devices and products	3	f	Медичне обладнання та вироби медичного призначення різні
33191000-5	Sterilisation, disinfection and hygiene devices	4	f	Обладнання стерилізаційне, дезінфекційне та санітарно-гігієнічне
33191100-6	Steriliser	5	f	Стерилізатори
33191110-9	Autoclaves	6	t	Автоклави
33192000-2	Medical furniture	4	f	Меблі медичного призначення
33192100-3	Beds for medical use	5	f	Медичні функціональні ліжка
33192110-6	Orthopaedic beds	6	t	Ортопедичні ліжка
33192120-9	Hospital beds	6	t	Лікарняні ліжка
33192130-2	Motorised beds	6	t	Автоматизовані ліжка
33192140-5	Psychiatric couches	6	t	Кушетки для психіатричних кабінетів
33192150-8	Therapy beds	6	t	Терапевтичні ліжка
33192160-1	Stretchers	6	t	Ноші
33192200-4	Medical tables	5	f	Медичні столи
33192210-7	Examination tables	6	t	Діагностичні столи
33192230-3	Operating tables	6	t	Операційні столи
33192300-5	Medical furniture except beds and tables	5	f	Меблі медичного призначення, крім ліжок і столів
33192310-8	Traction or suspension devices for medical beds	6	t	Висувна та підвісна апаратура до медичних функціональних ліжок
33192320-1	Urine-bottle holders	6	t	Тримачі для сечоприймачів
33192330-4	Transfusion pods	6	t	Штативи для переливання крові
33192340-7	Operating theatre furniture except tables	6	t	Меблі для операційних кімнат, окрім столів
33192350-0	Medical cultivation cabinet	6	t	Інкубатори для вирощування клітинних культур
33192400-6	Dental workstations	5	f	Автоматизовані робочі місця стоматолога
33192410-9	Dental chairs	6	t	Стоматологічні крісла
33192500-7	Test tubes	5	t	Пробірки
33192600-8	Lifting equipment for health care sector	5	t	Медичне підіймальне устаткування
33193000-9	Invalid carriages, wheelchairs and associated devices	4	f	Автомобілі для інвалідів, інвалідні візки та супутні пристрої
33193100-0	Invalid carriages and wheelchairs	5	f	Автомобілі для інвалідів та інвалідні візки
33193110-3	Invalid carriages	6	t	Автомобілі для інвалідів
33193120-6	Wheelchairs	6	f	Інвалідні візки
33193121-3	Motorised wheelchairs	7	t	Крісла-візки з мотором
33193200-1	Parts and accessories for invalid carriages and wheelchairs	5	f	Частини та приладдя до автомобілів для інвалідів та інвалідних візків
33193210-4	Parts and accessories for invalid carriages	6	f	Частини та приладдя до автомобілів для інвалідів
33193211-1	Motors for invalid carriages	7	t	Мотори до автомобілів для інвалідів
33193212-8	Steering devices for invalid carriages	7	t	Кермові пристрої до автомобілів для інвалідів
33193213-5	Control devices for invalid carriages	7	t	Пристрої керування до автомобілів для інвалідів
33193214-2	Chassis for invalid carriages	7	t	Шасі до автомобілів для інвалідів
33193220-7	Parts and accessories for wheelchairs	6	f	Частини та приладдя до інвалідних візків
33193221-4	Wheelchair cushions	7	t	Подушки до інвалідних візків
33193222-1	Wheelchair frames	7	t	Рами до інвалідних візків
33193223-8	Wheelchair seats	7	t	Сидіння до інвалідних візків
33193224-5	Wheelchair wheels	7	t	Колеса до інвалідних візків
33193225-2	Wheelchair tyres	7	t	Шини до інвалідних візків
33194000-6	Devices and instruments for transfusion and infusion	4	f	Прилади та інструменти для переливання та вливання крові / розчинів
33194100-7	Devices and instruments for infusion	5	f	Прилади та інструменти для вливання розчинів
33194110-0	Infusion pumps	6	t	Інфузійні насоси
33194120-3	Infusion supplies	6	t	Інфузійне приладдя
33194200-8	Devices and instruments for transfusion	5	f	Пристрої та інструменти для переливання крові
33194210-1	Blood-transfusion devices	6	t	Пристрої для переливання крові
33194220-4	Blood-transfusion supplies	6	t	Приладдя для переливання крові
33195000-3	Patient-monitoring system	4	f	Системи моніторингу стану пацієнта
33195100-4	Monitors	5	f	Монітори
33195110-7	Respiratory monitors	6	t	Монітори дихання
33195200-5	Central monitoring station	5	t	Центральні пульти моніторингу
33196000-0	Medical aids	4	f	Аптечки першої медичної допомоги
33196100-1	Devices for the elderly	5	t	Пристрої для літніх осіб
33196200-2	Devices for the disabled	5	t	Пристрої для людей з обмеженими можливостями
33197000-7	Medical computer equipment	4	t	Комп’ютерне обладнання медичного призначення
33198000-4	Hospital paper articles	4	f	Лікарняні паперові вироби
33198100-5	Paper compresses	5	t	Паперові компреси
33198200-6	Paper sterilisation pouches or wraps	5	t	Паперові стерилізаційні пакети чи обгортки
33199000-1	Medical clothing	4	t	Одяг для медичного персоналу
33600000-6	Pharmaceutical products	2	f	Фармацевтична продукція
33610000-9	Medicinal products for the alimentary tract and metabolism	3	f	Лікарські засоби для лікування захворювань шлунково-кишкового тракту та розладів обміну речовин
33611000-6	Medicinal products for acid related disorders	4	t	Лікарські засоби для нормалізації кислотності
33612000-3	Medicinal products for functional gastrointestinal disorders	4	t	Лікарські засоби для лікування функціональних розладів шлунково-кишкового тракту
33613000-0	Laxatives	4	t	Проносні засоби
33614000-7	Antidiarrhoeals, intestinal anti-inflammatory/anti-infective agents	4	t	Протидіарейні засоби, засоби для лікування шлунково-кишкових запалень / інфекцій
33615000-4	Medicinal products used in diabetes	4	f	Протидіабетичні лікарські засоби
33615100-5	Insulin	5	t	Інсулін
33616000-1	Vitamins	4	f	Вітаміни
33616100-2	Provitamins	5	t	Провітаміни
33617000-8	Mineral supplements	4	t	Мінеральні добавки
33620000-2	Medicinal products for the blood, blood-forming organs and the cardiovascular system	3	f	Лікарські засоби для лікування захворювань крові, органів кровотворення та захворювань серцево-судинної системи
33621000-9	Medicinal products for the blood and blood-forming organs	4	f	Лікарські засоби для лікування захворювань крові й органів кровотворення
33621100-0	Antithrombotic agents	5	t	Протитромбозні засоби
33621400-3	Blood substitutes and perfusion solutions	5	t	Кровозамінники та перфузійні розчини
33622000-6	Medicinal products for the cardiovascular system	4	f	Лікарські засоби для лікування захворювань серцево-судинної системи
33622100-7	Cardiac therapy medicinal products	5	t	Кардіологічні лікарські засоби
33622200-8	Antihypertensives	5	t	Протигіпертонічні засоби
33622300-9	Diuretics	5	t	Сечогінні засоби
33622400-0	Vasoprotectives	5	t	Ангіопротектори
33622500-1	Antihaemorrhoidals for topical use	5	t	Протигеморойні засоби місцевого застосування
33622600-2	Beta-blocking agents	5	t	Бета-блокатори
33622700-3	Calcium channel blockers	5	t	Блокатори кальцієвих каналів
33622800-4	Agents acting on the renin-angiotensin system	5	t	Блокатори ренін-ангіотензинової системи
33630000-5	Medicinal products for dermatology and the musculo-skeletal system	3	f	Лікарські засоби для лікування дерматологічних захворювань та захворювань опорно-рухового апарату
33631000-2	Medicinal products for dermatology	4	f	Лікарські засоби для лікування дерматологічних захворювань
33631100-3	Antifungals for dermatological use	5	f	Протигрибкові засоби для лікування дерматологічних захворювань
33631110-6	Salicylic acids	6	t	Саліцилові кислоти
33631200-4	Emollients and protectives	5	t	Пом’якшувальні та захисні засоби
33631300-5	Antipsoriatics	5	t	Протипсоріазні засоби
33631400-6	Antibiotics and chemotherapeutics for dermatological use	5	t	Антибіотики та хіміотерапевтичні засоби для лікування дерматологічних захворювань
33631500-7	Corticosteroids for dermatological use and dermatological preparations	5	t	Кортикостероїди для лікування дерматологічних захворювань та дерматологічні препарати
33631600-8	Antiseptics and disinfectants	5	t	Антисептичні та дезінфекційні засоби
33631700-9	Anti-acne preparations	5	t	Препарати для лікування акне
33632000-9	Medicinal products for the musculo-skeletal system	4	f	Лікарські засоби для лікування захворювань опорно-рухового апарату
33632100-0	Anti-inflammatory and anti-rheumatic products	5	t	Протизапальні та протиревматичні засоби
33632200-1	Muscle relaxants	5	t	М’язові релаксанти
33632300-2	Antigout preparations	5	t	Протиподагричні препарати
33640000-8	Medicinal products for the genitourinary system and hormones	3	f	Лікарські засоби для лікування захворювань сечостатевої системи та гормони
33641000-5	Medicinal products for the genitourinary system and sex hormones	4	f	Лікарські засоби для лікування захворювань сечостатевої системи та статеві гормони
33641100-6	Gynaecological anti-infectives and antiseptics	5	t	Гінекологічні протиінфекційні та антисептичні засоби
33641200-7	Other gynaecologicals	5	t	Інші гінекологічні засоби
33641300-8	Sex hormones and modulators of the genital system	5	t	Статеві гормони та модулятори статевої системи
33641400-9	Contraceptives	5	f	Засоби контрацепції
33641410-2	Oral contraceptives	6	t	Оральні контрацептиви
33641420-5	Chemical contraceptives	6	t	Хімічні контрацептиви
33642000-2	Systemic hormonal preparations, excl. sex hormones	4	f	Гормональні препарати системної дії, крім статевих гормонів
33642100-3	Pituitary, hypothalamic hormones and analogues	5	t	Гормони гіпофіза, гіпоталамуса та їх аналоги
33642200-4	Corticosteroids for systemic use	5	t	Кортикостероїди для системного застосування
33642300-5	Thyroid therapy medicinal products	5	t	Лікарські засоби для лікування захворювань щитовидної залози
33650000-1	General anti-infectives for systemic use, vaccines, antineoplastic and immunodulating agents	3	f	Загальні протиінфекційні засоби для системного застосування, вакцини, антинеопластичні засоби та імуномодулятори
33651000-8	General anti-infectives for systemic use and vaccines	4	f	Загальні протиінфекційні засоби для системного застосування та вакцини
33651100-9	Antibacterials for systemic use	5	t	Протибактеріальні засоби для системного застосування
33651200-0	Antimycotics for systemic use	5	t	Протигрибкові засоби для системного застосування
33651300-1	Antimycobacterials	5	t	Протимікобактеріальні засоби
33651400-2	Antivirals for systemic use	5	t	Противірусні засоби для системного застосування
33651500-3	Immune sera and immunoglobulins	5	f	Імунні сироватки та імуноглобуліни
33651510-6	Antisera	6	t	Антисироватки
33651520-9	Immunoglobulins	6	t	Імуноглобуліни
33651600-4	Vaccines	5	f	Вакцини
33651610-7	Diphtheria-pertussis-tetanus vaccines	6	t	Вакцини проти дифтерії, коклюшу та правця
33651620-0	Diphtheria-tetanus vaccines	6	t	Вакцини проти дифтерії та правця
33651630-3	BCG vaccines (dried)	6	t	Вакцини БЦЖ (ліофілізовані)
33651640-6	Measles-mumps-rubella vaccines	6	t	Вакцини проти кору, епідемічного паротиту та краснухи
33651650-9	Typhus vaccines	6	t	Вакцини проти висипного тифу
33651660-2	Influenza vaccines	6	t	Вакцини проти грипу
33651670-5	Polio vaccines	6	t	Вакцини проти поліомієліту
33651680-8	Hepatitis B vaccines	6	t	Вакцини проти гепатиту B
33651690-1	Vaccines for veterinary medicine	6	t	Ветеринарні вакцини
33652000-5	Antineoplastic and immunomodulating agents	4	f	Антинеопластичні засоби та імуномодулятори
33652100-6	Antineoplastic agents	5	t	Антинеопластичні засоби
33652200-7	Endocrine therapy medicinal products	5	t	Лікарські засоби для лікування ендокринних захворювань
33652300-8	Immunosuppressive agents	5	t	Імуносупресивні засоби
33660000-4	Medicinal products for the nervous system and sensory organs	3	f	Лікарські засоби для лікування хвороб нервової системи та захворювань органів чуття
33661000-1	Medicinal products for the nervous system	4	f	Лікарські засоби для лікування хвороб нервової системи
33661100-2	Anaesthetics	5	t	Анестетичні засоби
33661200-3	Analgesics	5	t	Анальгетичні засоби
33661300-4	Antiepileptics	5	t	Протиепілептичні засоби
33661400-5	Anti-Parkinson medicinal products	5	t	Лікарські засоби для лікування хвороби Паркінсона
33661500-6	Psycholeptics	5	t	Психолептичні засоби
33661600-7	Psychoanaleptics	5	t	Психоаналептичні засоби
33661700-8	Other nervous system medicinal products	5	t	Інші лікарські засоби для лікування хвороб нервової системи
33662000-8	Medicinal products for sensory organs	4	f	Лікарські засоби для лікування захворювань органів чуття
33662100-9	Ophthalmologicals	5	t	Офтальмологічні засоби
33670000-7	Medicinal products for the respiratory system	3	f	Лікарські засоби для лікування хвороб дихальної системи
33673000-8	Medicinal products for obstructive airway diseases	4	t	Лікарські засоби для лікування обструктивних захворювань дихальних шляхів
33674000-5	Cough and cold preparations	4	t	Препарати проти кашлю та застуди
33675000-2	Antihistamines for systemic use	4	t	Антигістамінні засоби для системного застосування
33680000-0	Pharmaceutical articles	3	f	Фармацевтичні вироби
33681000-7	Teats, nipple shields and similar articles for babies	4	t	Соски, молоковідсмоктувачі та подібні вироби для немовлят
33682000-4	Rubber tiles	4	t	Гумові плитки
33683000-1	Rubber cushioning	4	t	Гумові покриття
33690000-3	Various medicinal products	3	f	Лікарські засоби різні
33691000-0	Antiparasitic products, insecticides and repellents	4	f	Протипаразитні засоби, інсектициди та репеленти
33691100-1	Antiprotozoals	5	t	Протипротозойні засоби
33691200-2	Anthelmintics	5	t	Протигельмінтні засоби
33691300-3	Ectoparasiticides, incl. scabicides, insecticides and repellents	5	t	Засоби проти ектопаразитів, у тому числі засоби проти корости, інсектициди та репеленти
33692000-7	Medical solutions	4	f	Медичні розчини
33692100-8	Infusion solutions	5	t	Інфузійні розчини
33692200-9	Parenteral nutrition products	5	f	Препарати для парентерального харчування
33692210-2	Parenteral feeding solutions	6	t	Розчини для парентерального харчування
33692300-0	Enteral feeds	5	t	Препарати для ентерального харчування
33692400-1	Perfusion solutions	5	t	Перфузійні розчини
33692500-2	Injectable solutions	5	f	Розчини для ін’єкцій
33692510-5	Intravenous fluids	6	t	Рідини для внутрішньовенного введення
33692600-3	Galenical solutions	5	t	Галенові розчини
33692700-4	Glucose solutions	5	t	Розчини глюкози
33692800-5	Dialysis solutions	5	t	Розчини для діалізу
33693000-4	Other therapeutic products	4	f	Інші лікарські засоби
33693100-5	Toxins	5	t	Токсини
33693200-6	Nicotine substitutes	5	t	Замінники нікотину
33693300-7	Addiction treatment	5	t	Засоби для лікування залежностей
33694000-1	Diagnostic agents	4	t	Діагностичні засоби
33695000-8	All other non-therapeutic products	4	t	Продукція медичного призначення, крім лікарських засобів
33696000-5	Reagents and contrast media	4	f	Реактиви та контрастні речовини
33696100-6	Blood-grouping reagents	5	t	Реактиви для визначання групи крові
33696200-7	Blood-testing reagents	5	t	Реактиви для аналізів крові
33696300-8	Chemical reagents	5	t	Хімічні реактиви
33696400-9	Isotopic reagents	5	t	Ізотопи
33696500-0	Laboratory reagents	5	t	Лабораторні реактиви
33696600-1	Reagents for electrophoresis	5	t	Реактиви для електрофорезу
33696700-2	Urological reagents	5	t	Реактиви для аналізів сечі
34328100-3	Test benches	5	t	Випробувальні стенди
33697000-2	Medical preparations excluding dental consumables	4	f	Медичні препарати, крім стоматологічних матеріалів
33697100-3	Enema preparations	5	f	Приладдя для клізм
33697110-6	Bone reconstruction cements	6	t	Цементи для реконструкції кісток
33698000-9	Clinical products	4	f	Вироби для клінічних досліджень / випробувань
33698100-0	Microbiological cultures	5	t	Культури мікроорганізмів
33698200-1	Glands and their extracts	5	t	Залози внутрішньої секреції та їх секрети
33698300-2	Peptic substances	5	t	Пептидні речовини
33700000-7	Personal care products	2	f	Засоби особистої гігієни
33710000-0	Perfumes, toiletries and condoms	3	f	Парфуми, засоби гігієни та презервативи
33711000-7	Perfumes and toiletries	4	f	Парфуми та засоби гігієни
33711100-8	Toilet waters	5	f	Туалетна вода
33711110-1	Deodorants	6	t	Дезодоранти
33711120-4	Antiperspirants	6	t	Антиперспіранти
33711130-7	Colognes	6	t	Одеколони
33711140-0	Fragrances	6	t	Парфуми
33711150-3	Rose water	6	t	Трояндова вода
33711200-9	Make-up preparations	5	t	Засоби декоративної косметики
33711300-0	Manicure or pedicure preparations	5	t	Засоби для манікюру та педикюру
33711400-1	Beauty products	5	f	Косметичні засоби
33711410-4	Cotton buds	6	t	Ватні палички
33711420-7	Makeup kits	6	t	Набори для макіяжу
33711430-0	Disposable personal wipes	6	t	Одноразові гігієнічні серветки
33711440-3	Lip balm	6	t	Бальзами для губ
33711450-6	Tattoos	6	t	Засоби для татуажу
33711500-2	Skin-care products	5	f	Засоби для догляду за шкірою
33711510-5	Sun protection products	6	t	Сонцезахисні засоби
33711520-8	Bath gels	6	t	Гелі для ванн
33711530-1	Shower caps	6	t	Шапочки для душу
33711540-4	Para-pharmaceutical creams or lotions	6	t	Парафармацевтичні креми чи лосьйони
33711600-3	Hair preparations and articles	5	f	Препарати та засоби для догляду за волоссям
33711610-6	Shampoos	6	t	Шампуні
33711620-9	Combs	6	t	Гребені для волосся
33711630-2	Wigs	6	t	Перуки
33711640-5	Vanity kits	6	t	Набори засобів особистої гігієни
33711700-4	Articles and preparations for oral or dental hygiene	5	f	Засоби та препарати для гігієни ротової порожнини та зубів
33711710-7	Toothbrushes	6	t	Зубні щітки
33711720-0	Toothpaste	6	t	Зубні пасти
33711730-3	Toothpicks	6	t	Зубочистки
33711740-6	Mouthwash	6	t	Ополіскувачі ротової порожнини
33711750-9	Mouth fresheners	6	t	Освіжувачі подиху
33711760-2	Dental floss	6	t	Зубна нитка
33711770-5	Infant soother, pacifier and dummy	6	t	Соски та пустушки
33711780-8	Denture cleaning tablets	6	t	Таблетки для очищення зубних протезів
33711790-1	Dental kits	6	t	Набори для догляду за зубами та ротовою порожниною
33711800-5	Shaving preparations	5	f	Засоби для гоління
33711810-8	Shaving creams	6	t	Креми для гоління
33711900-6	Soap	5	t	Мило
33712000-4	Condoms	4	t	Презервативи
33713000-1	Foot care products	4	t	Засоби для догляду за ногами
33720000-3	Razors and manicure or pedicure sets	3	f	Бритви та манікюрні чи педикюрні набори
33721000-0	Razors	4	f	Бритви
33721100-1	Razor blades	5	t	Леза до бритв
33721200-2	Shavers	5	t	Електробритви
33722000-7	Manicure or pedicure sets	4	f	Манікюрні чи педикюрні набори
33722100-8	Manicure sets	5	f	Манікюрні набори
33722110-1	Manicure implements	6	t	Манікюрні інструменти
33722200-9	Pedicure sets	5	f	Педикюрні набори
33722210-2	Pedicure implements	6	t	Педикюрні інструменти
33722300-0	Barrettes	5	t	Аксесуари для волосся
33730000-6	Eye care products and corrective lenses	3	f	Офтальмологічні вироби та коригувальні лінзи
33731000-3	Contact lenses	4	f	Контактні лінзи
33731100-4	Corrective lenses	5	f	Коригувальні лінзи
33731110-7	Intraocular lenses	6	t	Інтраокулярні лінзи
33731120-0	Spectacle lenses	6	t	Окулярні лінзи
33732000-0	Contact lenses lotions	4	t	Розчини для догляду за контактними лінзами
33733000-7	Sunglasses	4	t	Сонцезахисні окуляри
33734000-4	Spectacles	4	f	Окуляри
33734100-5	Frames and mountings for spectacles	5	t	Оправи та кріплення до окулярів
33734200-6	Glass for spectacles	5	t	Окулярне скло
33735000-1	Goggles	4	f	Окуляри-маски
33735100-2	Protective goggles	5	t	Захисні окуляри
33735200-3	Frames and mountings for goggles	5	t	Оправи та кріплення до окулярів-масок
33740000-9	Hand and nails care products	3	f	Засоби для догляду за руками та нігтями
33741000-6	Hand care products	4	f	Засоби для догляду за руками
33741100-7	Hand cleaner	5	t	Засоби для миття рук
33741200-8	Hand or body lotions	5	t	Лосьйони для рук чи тіла
33741300-9	Hand sanitizer	5	t	Антисептичні засоби для рук
33742000-3	Nail care products	4	f	Засоби для догляду за нігтями
33742100-4	Nail clippers	5	t	Щипці для нігтів
33742200-5	Nail polish	5	t	Лаки для нігтів
33750000-2	Baby care products	3	f	Засоби для догляду за малюками
33751000-9	Disposable nappies	4	t	Підгузки
33752000-6	Nursing pad	4	t	Лактаційні вкладиші
33760000-5	Toilet paper, handkerchiefs, hand towels and serviettes	3	f	Туалетний папір, носові хустинки, рушники для рук і серветки
33761000-2	Toilet paper	4	t	Туалетний папір
33762000-9	Paper handkerchiefs	4	t	Паперові носові хустинки
33763000-6	Paper hand towels	4	t	Паперові рушники для рук
33764000-3	Paper serviettes	4	t	Паперові серветки
33770000-8	Paper sanitary	3	f	Папір санітарно-гігієнічного призначення
33771000-5	Sanitary paper products	4	f	Паперові вироби санітарно-гігієнічного призначення
33771100-6	Sanitary towels or tampons	5	t	Гігієнічні прокладки чи тампони
33771200-7	Paper napkin liners	5	t	Щоденні гігієнічні прокладки
33772000-2	Disposable paper products	4	t	Одноразові паперові вироби
33790000-4	Laboratory, hygienic or pharmaceutical glassware	3	f	Скляний посуд лабораторного, санітарно-гігієнічного чи фармацевтичного призначення
33791000-1	Hygienic glassware	4	t	Скляний посуд санітарно-гігієнічного призначення
33792000-8	Pharmaceutical glassware	4	t	Скляний посуд фармацевтичного призначення
33793000-5	Laboratory glassware	4	t	Скляний посуд лабораторного призначення
33900000-9	Post-mortem and mortuary equipment and supplies	2	f	Устаткування та приладдя для моргів і розтинів
33910000-2	Pathology dissection instruments and supplies	3	f	Інструменти та приладдя для патологоанатомічного розтину
33911000-9	Autopsy scissors	4	t	Ножиці для розтину
33912000-6	Autopsy dissection kits	4	f	Набори для розтину
33912100-7	Autopsy dissection forceps	5	t	Анатомічні пінцети для розтину
33913000-3	Autopsy bullet probes	4	t	Зонди для видалення куль із м’яких тканин під час розтину
33914000-0	Post-mortem thread, needles or incision clips	4	f	Шовний матеріал, голки чи скоби для накладання швів після розтину
33914100-1	Post-mortem thread	5	t	Шовний матеріал для накладання швів після розтину
33914200-2	Post-mortem needles	5	t	Голки для накладання швів після розтину
33914300-3	Post-mortem incision clips	5	t	Скоби для накладання швів після розтину
33915000-7	Autopsy vein directors	4	t	Венозні катетери для розтину
33916000-4	Autopsy saw blades or accessories	4	f	Полотна пил для розтину чи супутнє приладдя
33916100-5	Autopsy saws	5	t	Пили для розтину
33917000-1	Dissection boards or pads	4	t	Столи чи килимки для розтину
33918000-8	Cases for post-mortem surgical instruments or accessories	4	t	Футляри для хірургічних інструментів чи приладдя для розтину
33919000-5	Instrument rolls for post-mortem surgical instruments or accessories	4	t	Сумки-чохли для хірургічних інструментів чи приладдя для розтину
33920000-5	Autopsy equipment and supplies	3	f	Обладнання та приладдя для розтину
33921000-2	Bone dust collectors	4	t	Контейнери для кісткового борошна
33922000-9	Cadaver transport bags	4	t	Транспортні мішки для трупів
33923000-6	Autopsy head rests, body boards or hanging scales	4	f	Підголівники, підвісні ваги для розтину або візки для трупів
33923100-7	Autopsy head rests	5	t	Підголівники для розтину
33923200-8	Autopsy body boards	5	t	Візки для трупів
33923300-9	Autopsy hanging scales	5	t	Підвісні ваги для розтину
33924000-3	Autopsy infectious disease kits	4	t	Набори для захисту від інфекційних заражень під час розтину
33925000-0	Post-mortem identification tags or bracelets	4	t	Ідентифікаційні бирки чи браслети для трупів
33926000-7	Autopsy fluid collection vacuum aspirators or tubing	4	t	Вакуумні аспіратори чи трубки для збору рідини під час розтину
33927000-4	Post-mortem rectal thermometers	4	t	Ректальні термометри для патологоанатомічного дослідження
33928000-1	Post-mortem finger straighteners	4	t	Розпрямовувачі для пальців рук трупів
33929000-8	Cadaver tissue builder kits	4	t	Набори для обробки м’яких тканин трупів
33930000-8	Autopsy furniture	3	f	Меблі для розтину
33931000-5	Autopsy grossing workstations or accessories	4	t	Робочі станції чи приладдя для проведення розтину
33932000-2	Autopsy sinks or accessories	4	t	Ємності для розтину чи приладдя до них
33933000-9	Autopsy tables or accessories	4	f	Столи для розтину чи приладдя до них
33933100-0	Autopsy tables	5	t	Столи для розтину
33934000-6	Necropsy tables or accessories	4	t	Столи для некропсії чи приладдя до них
33935000-3	Post-mortem animal dissection tables or accessories	4	t	Столи для розтину тварин чи приладдя до них
33936000-0	Embalming workstations or accessories	4	t	Робочі станції чи приладдя для бальзамування
33937000-7	Autopsy downdraft workstations or accessories	4	t	Робочі станції з витяжною системою для розтинів чи приладдя до них
33940000-1	Cadaver transport and storage equipment and supplies	3	f	Устаткування для транспортування та зберігання трупів та приладдя до нього
33941000-8	Cadaver storage racks	4	t	Комірки для зберігання трупів
33942000-5	Cadaver carriers	4	t	Засоби для транспортування трупів
33943000-2	Cadaver scissor lift trolleys	4	t	Візки з важільним підіймачем для трупів
33944000-9	Morgue cabinet refrigerators or freezers	4	t	Холодильні чи морозильні камери для моргів
33945000-6	Morgue walk in refrigerators	4	t	Холодильні кімнати для моргів
33946000-3	Autopsy carts	4	t	Візки для розтину
33947000-0	Cadaver trays	4	t	Піддони для трупів
33948000-7	Cadaver lifter or transfer devices	4	t	Пристрої для підіймання або перенесення трупів
33949000-4	Body transport containers	4	t	Контейнери для транспортування трупів
33950000-4	Clinical forensics equipment and supplies	3	f	Устаткування та приладдя для клінічної та судової медицини
33951000-1	Post-mortem fingerprint or impression materials	4	t	Матеріали для зняття відбитків пальців чи зліпків під час розтину
33952000-8	Transport equipment and auxiliary products to transportation	4	t	Транспортне обладнання та допоміжне приладдя до нього
33953000-5	Post-mortem blood detection kits or supplies	4	t	Набори чи приладдя для дослідження слідів крові трупів
33954000-2	Biological evidence collection kits	4	t	Набори для забору біологічних матеріалів
33960000-7	Embalming equipment and supplies	3	f	Устаткування та приладдя для бальзамування
33961000-4	Embalming cavity injectors	4	t	Бальзамувальні інжектори
33962000-1	Embalming vein drainage tubes	4	t	Бальзамувальні дренажні трубки для вен
33963000-8	Embalming fluids or chemical treatments	4	t	Бальзамувальні рідини чи хімічні засоби для бальзамування
33964000-5	Embalming injecting tubes	4	t	Бальзамувальні ін’єкційні трубки
33965000-2	Embalming sinks or accessories	4	t	Бальзамувальні ємності чи приладдя до них
33966000-9	Embalming kits	4	t	Бальзамувальні набори
33967000-6	Embalming injector needles	4	t	Голки для бальзамувальних інжекторів
33968000-3	Eye caps	4	t	Накривки для очей
33970000-0	Mortuary equipment and supplies	3	f	Устаткування та приладдя для моргів
33971000-7	Mortuary outfits	4	t	Спеціальний одяг для персоналу моргів
33972000-4	Mortuary packs	4	t	Мішки для моргів
33973000-1	Mortuary wraps	4	t	Плівки для моргів
33974000-8	Mortuary aspirators	4	t	Аспіратори для моргів
33975000-5	Mortuary hardening compounds	4	t	Суміші для затверднення трупів
34000000-7	Transport equipment and auxiliary products to transportation	1	f	Транспортне обладнання та допоміжне приладдя до нього
34100000-8	Motor vehicles	2	f	Мототранспортні засоби
34110000-1	Passenger cars	3	f	Легкові автомобілі
34111000-8	Estate and saloon cars	4	f	Легкові автомобілі типів «універсал» та «седан»
34111100-9	Estate cars	5	t	Легкові автомобілі типу «універсал»
34111200-0	Saloon cars	5	t	Легкові автомобілі типу «седан»
34113000-2	4-wheel-drive vehicles	4	f	Повнопривідні транспортні засоби
34113100-3	Jeeps	5	t	Джипи
34113200-4	All-terrain vehicles	5	t	Мотовсюдиходи
34113300-5	Off-road vehicles	5	t	Позашляхові засоби транспортні
34114000-9	Specialist vehicles	4	f	Транспортні засоби спеціального призначення
34114100-0	Emergency vehicles	5	f	Аварійно-рятувальні транспортні засоби
34114110-3	Rescue vehicles	6	t	Рятувальні транспортні засоби
34114120-6	Paramedic vehicles	6	f	Санітарні транспортні засоби
34114121-3	Ambulances	7	t	Автомобілі швидкої допомоги
34114122-0	Patient-transport vehicles	7	t	Транспортні засоби для перевезення пацієнтів
34114200-1	Police cars	5	f	Поліцейські автомобілі
34114210-4	Prisoner-transport vehicles	6	t	Транспортні засоби для перевезення в’язнів
34114300-2	Welfare vehicles	5	t	Транспортні засоби служб соціальної допомоги
34114400-3	Minibuses	5	t	Мікроавтобуси
34115000-6	Other passenger cars	4	f	Інші легкові автомобілі
34115200-8	Motor vehicles for the transport of fewer than 10 persons	5	t	Мототранспортні засоби для перевезення менше 10 осіб
34115300-9	Second-hand transport vehicles	5	t	Транспортні засоби, що були у використанні
34120000-4	Motor vehicles for the transport of 10 or more persons	3	f	Мототранспортні засоби для перевезення 10 і більше осіб
34121000-1	Buses and coaches	4	f	Міські та туристичні автобуси
34121100-2	Public-service buses	5	t	Громадські автобуси
34121200-3	Articulated buses	5	t	Двосекційні автобуси
34121300-4	Double-decker buses	5	t	Двоповерхові автобуси
34121400-5	Low-floor buses	5	t	Низькопідлогові автобуси
34121500-6	Coaches	5	t	Туристичні автобуси
34130000-7	Motor vehicles for the transport of goods	3	f	Мототранспортні вантажні засоби
34131000-4	Pick-ups	4	t	Пікапи
34132000-1	Motor sledges	4	t	Мотосани
34133000-8	Articulated trucks	4	f	Автопоїзди
34133100-9	Tankers	5	f	Автоцистерни
34133110-2	Fuel-tanker trucks	6	t	Автоцистерни для перевезення паливно-мастильних матеріалів
34134000-5	Flatbed and Tipper trucks	4	f	Евакуатори та самоскиди
34134100-6	Flatbed trucks	5	t	Евакуатори
34134200-7	Tipper trucks	5	t	Самоскиди
34136000-9	Vans	4	f	Фургони
34136100-0	Light vans	5	t	Мініфургони
34136200-1	Panel vans	5	t	Фургони з цільнометалевим кузовом
34137000-6	Second-hand goods vehicles	4	t	Вантажні транспортні засоби, що були у використанні
34138000-3	Road tractor units	4	t	Автотягачі
34139000-0	Chassis	4	f	Шасі
34139100-1	Chassis cabs	5	t	Шасі для кабіни
34139200-2	Chassis bodies	5	t	Шасі для кузова
34139300-3	Complete chassis	5	t	Суцільні шасі
34140000-0	Heavy-duty motor vehicles	3	f	Великовантажні мототранспортні засоби
34142000-4	Crane and dumper trucks	4	f	Автокрани та вантажні автомобілі-самоскиди
34142100-5	Elevator-platforms trucks	5	t	Вантажні автомобілі з підіймальними платформами
34142200-6	Skip loaders	5	t	Скіпові навантажувачі
34142300-7	Dumper trucks	5	t	Вантажні автомобілі-самоскиди
34143000-1	Winter-maintenance vehicles	4	t	Транспортні засоби для утримання доріг узимку
34144000-8	Special-purpose motor vehicles	4	f	Мототранспортні засоби спеціального призначення
34144100-9	Mobile drilling derricks	5	t	Пересувні бурові вежі
34144200-0	Vehicles for the emergency services	5	f	Транспортні засоби аварійних служб
34144210-3	Firefighting vehicles	6	f	Пожежні транспортні засоби
34144211-0	Turntable-ladder trucks	7	t	Пожежні автомобілі з поворотною драбиною
34144212-7	Water-tender vehicles	7	t	Пожежні автоцистерни
34144213-4	Fire engines	7	t	Пожежні автомобілі
34144220-6	Breakdown vehicles	6	t	Транспортні засоби аварiйно-ремонтного призначення
34144300-1	Mobile bridges	5	t	Пересувні мости
34144400-2	Road-maintenance vehicles	5	f	Транспортні засоби для утримання доріг
34144410-5	Gully emptiers	6	t	Автоцистерни для відкачування каналізаційних стоків
34144420-8	Salt spreaders	6	t	Автомобілі для розсипання солі
34144430-1	Road-sweeping vehicles	6	f	Підмітально-прибиральні машини
34144431-8	Suction-sweeper vehicles	7	t	Підмітально-збиральні машини з вакуумно-пневматичною системою
34144440-4	Gritter vehicles	6	t	Снігоприбиральні машини
34144450-7	Sprinkler vehicles	6	t	Поливальні машини
34144500-3	Vehicles for refuse and sewage	5	f	Сміттєвози та асенізаційні машини
34144510-6	Vehicles for refuse	6	f	Сміттєвози
34144511-3	Refuse-collection vehicles	7	t	Сміттєзбиральні машини
34144512-0	Refuse-compaction vehicles	7	t	Сміттєтрамбувальні машини
34144520-9	Sewage tankers	6	t	Асенізаційні автоцистерни
34144700-5	Utility vehicles	5	f	Транспортні засоби для технічного обслуговування
34144710-8	Wheeled loaders	6	t	Кар’єрні навантажувачі
34144730-4	Aircraft-refuelling vehicles	6	t	Авіазаправні машини
34144740-7	Aircraft-towing vehicles	6	t	Вантажні транспортні засоби для буксирування літаків
34144750-0	Cargo carriers	6	f	Вантажоперевізні транспортні засоби
34144751-7	Straddle carriers	7	t	Портові навантажувачі
34144760-3	Mobile library vehicles	6	t	Пересувні бібліотеки
34144800-6	Mobile homes	5	t	Житлові фургони
34144900-7	Electric vehicles	5	f	Транспортні засоби з електричним приводом
34144910-0	Electric buses	6	t	Електробуси
34150000-3	Simulators	3	f	Транспортні симулятори
34151000-0	Driving simulators	4	t	Автомобільні тренажери
34152000-7	Training simulators	4	t	Навчальні симулятори
34200000-9	Vehicle bodies, trailers or semi-trailers	2	f	Кузови, причепи та напівпричепи
34210000-2	Vehicle bodies	3	f	Кузови транспортних засобів
34211000-9	Bus bodies, ambulance bodies and vehicle bodies for goods vehicles	4	f	Кузови автобусів, автомобілів швидкої допомоги та вантажних транспортних засобів
34211100-9	Bus bodies	5	t	Кузови автобусів
34211200-9	Ambulance bodies	5	t	Кузови автомобілів швидкої допомоги
34211300-9	Vehicle bodies for goods vehicles	5	t	Кузови вантажних транспортних засобів
34220000-5	Trailers, semi-trailers and mobile containers	3	f	Причепи, напівпричепи та пересувні контейнери
34221000-2	Special-purpose mobile containers	4	f	Пересувні контейнери спеціального призначення
34221100-3	Mobile incident units	5	t	Пересувні аварійно-рятувальні пункти
34221200-4	Mobile emergency units	5	t	Пересувні пункти оперативного реагування на надзвичайні ситуації
34221300-5	Chemical incident unit	5	t	Пересувні пункти оперативного реагування на хімічні аварії
34223000-6	Trailers and semi-trailers	4	f	Причепи та напівпричепи
34223100-7	Semi-trailers	5	t	Напівпричепи
34223200-8	Bowsers	5	t	Паливозаправники
34223300-9	Trailers	5	f	Причепи
34223310-2	General-purpose trailers	6	t	Причепи загального призначення
34223320-5	Horsebox trailers	6	t	Причепи для перевезення коней
34223330-8	Mobile units on trailers	6	t	Пересувні блоки на причепи
34223340-1	Tanker trailers	6	t	Причепи-цистерни
34223350-4	Turntable-ladder trailers	6	t	Причепи з поворотними драбинами
34223360-7	Refuelling trailers	6	t	Заправні причепи
34223370-0	Tipper trailers	6	t	Причепи-самоскиди
34223400-0	Caravan-type trailers and semi-trailers	5	t	Житлові причепи та напівпричепи
34224000-3	Parts of trailers, semi-trailers and other vehicles	4	f	Частини причепів, напівпричепів та інших транспортних засобів
34224100-4	Parts of trailers and semi-trailers	5	t	Частини причепів та напівпричепів
34224200-5	Parts of other vehicles	5	t	Частини інших транспортних засобів
34300000-0	Parts and accessories for vehicles and their engines	2	f	Частини та приладдя до транспортних засобів і їх двигунів
34310000-3	Engines and engine parts	3	f	Двигуни та їх частини
34311000-0	Engines	4	f	Двигуни
34311100-1	Internal-combustion engines for motor vehicles and motorcycles	5	f	Двигуни внутрішнього згоряння для мототранспортних засобів і мотоциклів
34311110-4	Spark-ignition engines	6	t	Двигуни з іскровим запалюванням
34311120-7	Compression-ignition engines	6	t	Дизельні двигуни
34312000-7	Engine parts	4	f	Частини двигунів
34312100-8	Fan belts	5	t	Вентиляторні паси
34312200-9	Spark plugs	5	t	Свічки запалювання
34312300-0	Vehicle radiators	5	t	Автомобільні радіатори
34312400-1	Pistons	5	t	Поршні
34312500-2	Gaskets	5	t	Ущільнювальні прокладки
34312600-3	Rubber conveyor belts	5	t	Гумові конвеєрні стрічки
34312700-4	Rubber transmission belts	5	t	Гумові приводні паси
34320000-6	Mechanical spare parts except engines and engine parts	3	f	Механічні запасні частини, крім двигунів і частин двигунів
34321000-3	Axles and gearboxes	4	f	Осі та коробки передач
34321100-4	Axles	5	t	Осі
34321200-5	Gearboxes	5	t	Коробки передач
34322000-0	Brakes and brake parts	4	f	Гальма та частини гальм
34322100-1	Brake equipment	5	t	Гальмівне обладнання
34322200-2	Disc brakes	5	t	Дискові гальма
34322300-3	Brake linings	5	t	Гальмові накладки
34322400-4	Brake pads	5	t	Гальмові колодки (дискові)
34322500-5	Brake shoes	5	t	Гальмові колодки (барабанні)
34324000-4	Wheels, parts and accessories	4	f	Колеса, частини та приладдя до них
34324100-5	Wheel-balancing equipment	5	t	Обладнання для балансування коліс
34325000-1	Silencers and exhaust pipes	4	f	Глушники та вихлопні труби
34325100-2	Silencers	5	t	Глушники
34325200-3	Exhaust pipes	5	t	Вихлопні труби
34326000-8	Vehicle jacks, clutches and associated parts	4	f	Домкрати, муфти зчеплення та супутні деталі
34326100-9	Clutches and associated parts	5	t	Муфти зчеплення та супутні деталі
34326200-0	Vehicle jacks	5	t	Домкрати
34327000-5	Steering wheels, columns and boxes	4	f	Керма, кермові колонки та кермові механізми
34327100-6	Steering wheels	5	t	Керма
34327200-7	Columns and boxes	5	t	Кермові колонки та механізми
34328000-2	Test benches, vehicle conversion kits and seat belts	4	f	Випробувальні стенди, набори для переобладнання автомобіля та паси безпеки
34328200-4	Vehicle conversion kits	5	t	Набори для переобладнання автомобіля
34328300-5	Seat belts	5	t	Паси безпеки
34330000-9	Spare parts for goods vehicles, vans and cars	3	t	Запасні частини до вантажних транспортних засобів, фургонів та легкових автомобілів
34350000-5	Tyres for heavy/light-duty vehicles	3	f	Шини для транспортних засобів великої та малої тоннажності
34351000-2	Light-duty tyres	4	f	Шини для транспортних засобів малої тоннажності
34351100-3	Tyres for motor cars	5	t	Автомобільні шини
34352000-9	Heavy-duty tyres	4	f	Шини для транспортних засобів великої тоннажності
34352100-0	Tyres for trucks	5	t	Шини для вантажних автомобілів
34352200-1	Tyres for buses	5	t	Шини для автобусів
34352300-2	Agrarian tyres	5	t	Шини для сільськогосподарської техніки
34360000-8	Seats for civil aircraft	3	t	Сидіння для пасажирських літаків
34370000-1	Seats for motor vehicles	3	t	Сидіння для мототранспортних засобів
34390000-7	Tractor accessories	3	t	Приладдя до тракторів
34400000-1	Motorcycles, bicycles and sidecars	2	f	Мотоцикли, велосипеди та коляски
34410000-4	Motorcycles	3	f	Мотоцикли
34411000-1	Parts and accessories for motorcycles	4	f	Частини та приладдя до мотоциклів
34411100-2	Motorcycle sidecars	5	f	Коляски до мотоциклів
34411110-5	Parts and accessories for motorcycle sidecars	6	t	Частини та приладдя до мотоциклетних колясок
34411200-3	Tyres for motorcycles	5	t	Шини для мотоциклів
34420000-7	Motor scooters and cycles with auxiliary motors	3	f	Моторолери та моторизовані велосипеди
34421000-7	Motor scooters	4	t	Моторолери
34422000-7	Cycles with auxiliary motors	4	t	Моторизовані велосипеди
34430000-0	Bicycles	3	f	Велосипеди
34431000-7	Non-motorised bicycles	4	t	Велосипеди без мотора
34432000-4	Parts and accessories for bicycles	4	f	Частини та приладдя до велосипедів
34432100-5	Tyres for bicycles	5	t	Шини для велосипедів
34500000-2	Ships and boats	2	f	Судна та човни
34510000-5	Ships	3	f	Судна
34511100-3	Marine patrol vessels	5	t	Морські патрульні катери
34512000-9	Ships and similar vessels for the transport of persons or goods	4	f	Пасажирські та вантажні кораблі та подібні судна
34512100-0	Ferry boats	5	t	Пороми
34512200-1	Cruise ships	5	t	Круїзні судна
34512300-2	Bulk carriers	5	t	Балкери
34512400-3	Cargo ships	5	t	Вантажі судна
34512500-4	Goods vessels	5	t	Торгові судна
34512600-5	Container carriers	5	t	Контейнеровози
34512700-6	Ro-Ro vessels	5	t	Ролкери
34512800-7	Tanker ships	5	t	Танкери
34512900-8	Car carriers	5	f	Судна-автомобілевози
34512950-3	Refrigerated vessels	6	t	Судна-рефрижератори
34513000-6	Fishing, emergency and other special vessels	4	f	Риболовні, рятувальні та інші спеціальні судна
34513100-7	Fishing vessels	5	f	Риболовні судна
34513150-2	Factory ships	6	t	Рибопереробні судна
34513200-8	Tug boats	5	f	Буксири
34513250-3	Dredgers	6	t	Драги
34513300-9	Seagoing floating docks	5	f	Морські плавучі доки
34513350-4	Dive-support vessels	6	t	Водолазні судна
34513400-0	Floating cranes	5	f	Плавучі крани
34513450-5	Production vessels	6	t	Промислові судна
34513500-1	Seismic survey vessels	5	f	Сейсморозвідувальні судна
34513550-6	Survey vessels	6	t	Гідрографічні судна
34513600-2	Pollution-control vessels	5	f	Очисні судна
34513650-7	Fire vessels	6	t	Пожежні судна
34513700-3	Rescue vessels	5	f	Рятувальні судна
34513750-8	Light vessels	6	t	Плавучі маяки
34514000-3	Floating or submersible drilling or production platforms	4	f	Плавучі чи занурювані бурові чи промислові платформи
34514100-4	Drillships	5	t	Бурові судна
34514200-5	Jack-up rigs	5	t	Самопідіймальні бурові установки
34514300-6	Platforms drilling rigs	5	t	Установки бурових платформ
34514400-7	Floating drilling platforms	5	t	Плавучі бурові платформи
34514500-8	Floating production facility	5	t	Плавучі промислові установки
34514600-9	Semi-submersible rigs	5	t	Напівзанурювані установки
34514700-0	Mobile platforms	5	t	Пересувні платформи
34514800-1	Offshore platforms	5	t	Стаціонарні морські платформи
34514900-2	Drilling platforms	5	t	Бурові платформи
34515000-0	Floating structures	4	f	Плавучі конструкції
34515100-1	Marker buoys	5	t	Маркерні буї
34515200-2	Inflatable rafts	5	t	Надувні плоти
34516000-7	Marine fenders	4	t	Кранці
34520000-8	Boats	3	f	Човни
34521000-5	Specialised boats	4	f	Човни спеціального призначення
34521100-6	Surveillance boats	5	t	Розвідувальні човни
34521200-7	Customs patrol boats	5	t	Патрульні катери митних служб
34521300-8	Police patrol boats	5	t	Поліцейські патрульні катери
34521400-9	Lifeboats	5	t	Рятувальні човни
34522000-2	Pleasure and sporting boats	4	f	прогулянкові та спортивні човни
34522100-3	Sailing boats	5	f	Вітрильні човни
34522150-8	Catamaran sailing boats	6	t	Вітрильні катамарани
34522200-4	Rescue dinghies	5	f	Рятувальні шлюпки
34522250-9	Sailing dinghies	6	t	Вітрильні шлюпки
34522300-5	Small craft	5	f	Малі судна
34522350-0	Fibreglass dinghies	6	t	Шлюпки зі скловолокна
34522400-6	Semi-rigid dinghies	5	f	Напівтверді шлюпки
34522450-1	Inflatable craft	6	t	Надувні човни
34522500-7	Rubber dinghies	5	f	Гумові шлюпки
34522550-2	Canoes	6	t	Каное
34522600-8	Rowing boats	5	t	Веслові човни
34522700-9	Pedal boats	5	t	Водні велосипеди
34600000-3	Railway and tramway locomotives and rolling stock and associated parts	2	f	Залізничні та трамвайні локомотиви і рейковий рухомий склад та супутні деталі
34610000-6	Rail locomotives and tenders	3	f	Залізничні локомотиви та тендери
34611000-3	Locomotives	4	t	Локомотиви
34612000-0	Locomotive tenders and cable cars	4	f	Локомотивні тендери та вагони канатної дороги
34612100-1	Locomotive tenders	5	t	Локомотивні тендери
34612200-2	Cable cars	5	t	Вагони канатної дороги
34620000-9	Rolling stock	3	f	Рейковий рухомий склад
34621000-6	Railway maintenance or service vehicles, and railway freight wagons	4	f	Транспортні засоби для ремонту чи обслуговування залізничних доріг та вантажні залізничні вагони
34621100-7	Railway freight wagons	5	t	Вантажні залізничні вагони
34621200-8	Railway maintenance or service vehicles	5	t	Транспортні засоби для ремонту та обслуговування залізничних доріг
34622000-3	Railway and tramway passenger coaches, and trolleybuses	4	f	Залізничні та трамвайні пасажирські вагони і тролейбуси
34622100-4	Tramway passenger coaches	5	t	Трамвайні пасажирські вагони
34622200-5	Railway passenger coaches	5	t	Залізничні пасажирські вагони
34622300-6	Trolleybuses	5	t	Тролейбуси
34622400-7	Railway carriages	5	t	Залізничні вагони
34622500-8	Luggage vans and special-purpose vans	5	t	Багажні вагони та вагони спеціального призначення
34630000-2	Parts of railway or tramway locomotives or rolling stock; railways traffic-control equipment	3	f	Частини залізничних або трамвайних локомотивів чи рейкового рухомого складу; обладнання для контролю залізничного руху
34631000-9	Parts of locomotives or rolling stock	4	f	Частини локомотивів чи рухомого складу
34631100-0	Monobloc wheels	5	t	Колеса-моноблоки
34631200-1	Buffers and drawgear	5	t	Буфери та тягове обладнання
34631300-2	Rolling-stock seats	5	t	Сидіння для рейкового рухомого складу
34631400-3	Wheel axles and tyres and other parts of locomotives or rolling stock	5	t	Колісні осі та бандажі й інші частини локомотивів чи рейкового рухомого складу
34632000-6	Railways traffic-control equipment	4	f	Обладнання для контролю залізничного руху
34632100-7	Mechanical signalling	5	t	Механічні сигнальні системи
34632200-8	Electrical signalling equipment for railways	5	t	Електричні сигнальні системи для залізничних доріг
34632300-9	Electrical installations for railways	5	t	Електричні установки для залізничних доріг
34640000-5	Automotive elements	3	t	Самохідні частини
34700000-4	Aircraft and spacecraft	2	f	Повітряні та космічні літальні апарати
34710000-7	Helicopters, aeroplanes, spacecraft and other powered aircraft	3	f	Вертольоти, літаки, космічні та інші літальні апарати з двигуном
34711000-4	Helicopters and aeroplanes	4	f	Вертольоти та літаки
34711100-5	Aeroplanes	5	f	Літаки
34711110-8	Fixed-wing aircrafts	6	t	Повітряні літальні апарати з нерухомими крилами
34711200-6	Non-piloted aircraft	5	t	Безпілотні літальні апарати
34711300-7	Piloted aircraft	5	t	Пілотовані літальні апарати
34711400-8	Special-purpose aircraft	5	t	Літальні апарати спеціального призначення
34711500-9	Helicopters	5	t	Вертольоти
34712000-1	Spacecraft, satellites and launch vehicles	4	f	Космічні літальні апарати, супутники та ракетоносії
34712100-2	Spacecraft	5	t	Космічні літальні апарати
34712200-3	Satellites	5	t	Супутники
34712300-4	Spacecraft launchers	5	t	Пристрої запуску космічних літальних апаратів
34720000-0	Balloons, dirigibles and other non-powered aircraft	3	f	Аеростати, дирижаблі та інші літальні апарати без двигуна
34721000-7	Gliders	4	f	Планери
34722000-4	Balloons and dirigibles	4	f	Аеростати та дирижаблі
34722100-5	Balloons	5	t	Аеростати
34722200-6	Dirigibles	5	t	Дирижаблі
34730000-3	Parts for aircraft, spacecraft and helicopters	3	f	Частини повітряних і космічних літальних апаратів та вертольотів
34731000-0	Parts for aircraft	4	f	Частини повітряних літальних апаратів
34731100-1	Aircraft engines	5	t	Двигуни для повітряних літальних апаратів
34731200-2	Turbojets	5	t	Турбореактивні двигуни
34731300-3	Turbopropellers	5	t	Турбогвинтові двигуни
34731400-4	Jet engines	5	t	Реактивні двигуни
34731500-5	Parts for aircraft engines	5	t	Частини двигунів для повітряних літальних апаратів
34731600-6	Parts for turbojets or turbopropellers	5	t	Частини турбореактивних або турбогвинтових двигунів
34731700-7	Parts for helicopters	5	t	Частини вертольотів
34731800-8	Tyres for aircraft	5	t	Авіаційні шини
34740000-6	Aircraft or spacecraft equipment, trainers, simulators and associated parts	3	f	Обладнання для повітряних і космічних літальних апаратів, тренажери, симулятори та супутні деталі
34741000-3	Aircraft equipment	4	f	Обладнання для повітряних літальних апаратів
34741100-4	Aircraft-launching gear	5	t	Пускове обладнання для повітряних літальних апаратів
34741200-5	Aircraft catapult systems	5	t	Системи катапультування з повітряних літальних апаратів
34741300-6	Deck-arresting gear	5	t	Аерофінішери
34741400-7	Flight simulators	5	t	Авіасимулятори
34741500-8	Ground flying trainer	5	t	Наземні тренажери для льотної підготовки
34741600-9	Air-sickness bags	5	t	Бортові гігієнічні пакети
34900000-6	Miscellaneous transport equipment and spare parts	2	f	Транспортне обладнання та запасні частини різні
34910000-9	Horse or hand-drawn carts, other non-mechanically-propelled vehicles, baggage carts and miscellaneous spare parts	3	f	Гужові чи ручні вози, інші транспортні засоби з немеханічним приводом, багажні вози та різні запасні частини
34911000-6	Horse or hand-drawn carts and other non-mechanically-propelled vehicles	4	f	Гужові та ручні вози, інші транспортні засоби з немеханічним приводом
34911100-7	Trolleys	5	t	Ручні візки
34912000-3	Baggage carts	4	f	Багажні візки
34912100-4	Pushchairs	5	t	Прогулянкові дитячі візки
34913000-0	Miscellaneous spare parts	4	f	Запасні частини різні
34913100-1	Used tyres	5	t	Шини, що були у використанні
34913200-2	Retreaded tyres	5	t	Шини з відновленим протектором
34913300-3	Fenders	5	t	Автомобільні крила
34913400-4	Clocks for vehicles	5	t	Автомобільні годинники
34913500-5	Vehicle locks	5	f	Автомобільні замки
34913510-8	Bicycle locks	6	t	Велосипедні замки
34913600-6	Ship propellers	5	t	Гребні гвинти
34913700-7	Firefighting vehicle ladders	5	t	Драбини для пожежних машин
34913800-8	Anchors	5	t	Якорі
34920000-2	Road equipment	3	f	Дорожнє обладнання
34921000-9	Road-maintenance equipment	4	f	Дорожньо-експлуатаційне обладнання
34921100-0	Road sweepers	5	t	Підмітально-прибиральні машини
34921200-1	Runway sweepers	5	t	Машини для очищення злітно-посадкових смуг
34922000-6	Road-marking equipment	4	f	Обладнання для нанесення дорожньої розмітки
34922100-7	Road markings	5	f	Дорожня розмітка
34922110-0	Glass beads for road marking	6	t	Скляні кульки для дорожньої розмітки
34923000-3	Road traffic-control equipment	4	t	Обладнання для контролю дорожнього руху
34924000-0	Variable message signs	4	t	Динамічні інформаційні табло
34926000-4	Car park control equipment	4	t	Контрольне обладнання для автомобільних стоянок
34927000-1	Toll equipment	4	f	Обладнання для сплати дорожніх зборів
34927100-2	Road salt	5	t	Дорожня сіль
34928000-8	Road furniture	4	f	Облаштування для доріг
34928100-9	Crash barriers	5	f	Бар’єрні огорожі
34928110-2	Road barriers	6	t	Дорожні бар’єри
34928120-5	Barrier components	6	t	Частини бар’єрів
34928200-0	Fences	5	f	Огорожі
34928210-3	Wooden posts	6	t	Дерев’яні стовпи
34928220-6	Fencing components	6	t	Частини огорожі
34928230-9	Noise fence	6	t	Шумоізоляційні огорожі
34928300-1	Safety barriers	5	f	Захисні бар’єри
34928310-4	Safety fencing	6	t	Захисні огорожі
34928320-7	Guardrails	6	t	Колесовідбійні бруси
34928330-0	Paravalanche devices	6	t	Пристрої для протилавинного захисту
34928340-3	Snow fences	6	t	Снігові щити
34928400-2	Urban furniture	5	f	Міські вуличні меблі
34928410-5	Marker posts	6	t	Дорожні вказівники
34928430-1	Beacons	6	t	Семафори
34928440-4	Bus-stop posts	6	t	Знаки автобусної зупинки
34928450-7	Bollards	6	t	Боларди
34928460-0	Road cones	6	t	Дорожні конуси
34928470-3	Signage	6	f	Вказівники
34928471-0	Sign materials	7	t	Матеріали для виготовлення дорожніх знаків і вказівників
34928472-7	Sign posts	7	t	Стовпи-вказівники
34928480-6	Waste and rubbish containers and bins	6	t	Контейнери та урни для відходів і сміття
34928500-3	Street-lighting equipment	5	f	Обладнання для вуличного освітлення
34928510-6	Street-lighting columns	6	t	Опори для вуличного освітлення
34928520-9	Lampposts	6	t	Ліхтарні стовпи
34928530-2	Street lamps	6	t	Вуличні ліхтарі
34929000-5	Highway materials	4	t	Матеріали для автодорожнього будівництва
34930000-5	Marine equipment	3	f	Суднове обладнання
34931000-2	Harbour equipment	4	f	Портове обладнання
34931100-3	Docking installations	5	t	Докові споруди
34931200-4	Passenger boarding bridges for ships	5	t	Телескопічні трапи для суден
34931300-5	Passenger boarding stairs for ships	5	t	Суднові пересувні трапи
34931400-6	Ship bridge simulators	5	t	Симулятори суднових містків
34931500-7	Vessel traffic control equipment	5	t	Обладнання для контролю руху суден
34932000-9	Radar sets	4	t	Радіолокаційні системи
34933000-6	Navigation equipment	4	t	Навігаційне обладнання
34934000-3	Propeller blades	4	t	Гребні гвинти
34940000-8	Railway equipment	3	f	Залізничне обладнання
34941000-5	Rails and accessories	4	f	Рейки та приладдя
34941100-6	Rods	5	t	Стрижні
34941200-7	Track rails	5	t	Рейкові колії
34941300-8	Tramline	5	t	Трамвайні колії
34941500-0	Crossheads	5	t	Крейцкопфи
34941600-1	Crossovers	5	t	Стрілочні переводи
34941800-3	Railway points	5	t	Залізничні стрілки
34942000-2	Signalling equipment	4	f	Сигнальне обладнання
34942100-3	Signal posts	5	t	Сигнальні стовпи
34942200-4	Signalling boxes	5	t	Пульти сигналізації, централізації і блокування
34943000-9	Train-monitoring system	4	t	Системи контролю руху поїздів
34944000-6	Points heating system	4	t	Системи підігрівання стрілок
34945000-3	Track-alignment machinery	4	t	Обладнання для рихтування колій
34946000-0	Railway-track construction materials and supplies	4	f	Конструкційні матеріали та приладдя для залізничних колій
34946100-1	Railway-track construction materials	5	f	Конструкційні матеріали для залізничних колій
34946110-4	Rails	6	t	Рейки
34946120-7	Railway materials	6	f	Матеріали для будівництва залізничних доріг
34946121-4	Fishplates and sole plates	7	t	Рейкові накладки та підкладки
34946122-1	Check rails	7	t	Контррейки
34946200-2	Railway-track construction supplies	5	f	Приладдя для будівництва залізничних колій
34946210-5	Current-conducting rails	6	t	Струмопровідні рейки
34946220-8	Switch blades, crossing frogs, point rods and crossing pieces	6	f	Гостряки стрілочних переводів, хрестовини глухого перетину, перевідні штанги та хрестовини
34946221-5	Switch blades	7	t	Гостряки стрілочних переводів
34946222-2	Crossing frogs	7	t	Хрестовини глухого перетину
34946223-9	Point rods	7	t	Перевідні штанги
34946224-6	Crossing pieces	7	t	Хрестовини
34946230-1	Rail clips, bedplates and ties	6	f	Рейкові клеми, опори та шпали
34946231-8	Rail clips	7	t	Рейкові клеми
34946232-5	Bedplates and ties	7	t	Рейкові опори та шпали
34946240-4	Chairs and chair wedges	6	t	Рейкові подушки та клини
34947000-7	Sleepers and parts of sleepers	4	f	Шпали та частини шпал
34947100-8	Sleepers	5	t	Шпали
34947200-9	Parts of sleepers	5	t	Частини шпал
34950000-1	Loadbearing equipment	3	f	Тримальні конструкції
34951000-8	Access platforms	4	f	Ремонтні платформи
34951200-0	Sludge-handling equipment	5	t	Обладнання для відкачування мулу
34951300-1	Sludge-drier installation	5	t	Установки для висушування мулу
34952000-5	Hydraulic-platforms hoists	4	t	Лебідки для гідравлічних підіймачів
34953000-2	Access ramps	4	f	Рампи
34953100-3	Ferry ramps	5	t	Поромні рампи
34953300-5	Passenger walkway	5	t	Рампи-трапи
34954000-9	Gantries	4	t	Козлові крани
34955000-6	Floating dock	4	f	Плавучі доки
34955100-7	Floating storage unit	5	t	Плавучі нафтосховища
34960000-4	Airport equipment	3	f	Аеропортове обладнання
34961000-1	Baggage-handling system	4	f	Системи обробки багажу
35260000-4	Police signs	3	f	Поліцейські значки
34961100-2	Baggage-handling equipment	5	t	Обладнання для обробки багажу
34962000-8	Air-traffic control equipment	4	f	Обладнання для керування повітряним рухом
34962100-9	Control tower equipment	5	t	Обладнання для командно-диспетчерських пунктів
34962200-0	Air-traffic control	5	f	Диспетчерське обладнання для керування повітряним рухом
34962210-3	Air-traffic control simulation	6	t	Симулятори систем керування повітряним рухом
34962220-6	Air-traffic control systems	6	t	Системи керування повітряним рухом
34962230-9	Air-traffic control training	6	t	Тренажери для підготовки авіаційних диспетчерів
34963000-5	Instrument Landing System (ILS)	4	t	Системи інструментальної посадки літаків
34964000-2	Doppler VHF Omni direction Range (DVOR)	4	t	Всебічно направлений доплерівський азимутальний радіомаяк
34965000-9	Distance Measuring Equipment (DME)	4	t	Всебічно направлений далекомірний радіомаяк
34966000-6	Radio Direction Finder and Non-Directional Beacon	4	f	Радіокомпаси та ненаправлені радіомаяки
34966100-7	Radio Direction Finder (RDF)	5	t	Радіокомпаси
34966200-8	Non-Directional Beacon (NDB)	5	t	Ненаправлені радіомаяки
34967000-3	Airport Communication System (COM)	4	t	Аеропортові системи зв’язку
34968000-0	Airport Surveillance System and Lighting System	4	f	Аеропортові системи спостереження та освітлення
34968100-1	Airport Surveillance System (SUR)	5	t	Аеропортові системи спостереження
34968200-2	Airport Lighting System (PAPI)	5	t	Аеропортові системи освітлення
34969000-7	Passenger boarding bridges and stairs for aircraft	4	f	Літакові телескопічні та пересувні трапи
34969100-8	Passenger boarding bridges for aircraft	5	t	Літакові телескопічні трапи
34969200-9	Passenger boarding stairs for aircraft	5	t	Літакові пересувні трапи
34970000-7	Traffic-monitoring equipment	3	f	Обладнання для моніторингу руху транспорту
34971000-4	Speed camera equipment	4	t	Камери контролю швидкості руху
34972000-1	Traffic-flow measuring system	4	t	Системи вимірювання транспортного потоку
34980000-0	Transport tickets	3	t	Транспортні квитки
34990000-3	Control, safety, signalling and light equipment	3	f	Регулювальне, запобіжне, сигнальне та освітлювальне обладнання
34991000-0	Field operating lights	4	t	Стадіонні прожектори
34992000-7	Signs and illuminated signs	4	f	Вказівники та світлові вказівники
34992100-8	Illuminated traffic signs	5	t	Підсвічувані дорожні знаки
34992200-9	Road signs	5	t	Дорожні знаки
34992300-0	Street signs	5	t	Вуличні знаки
34993000-4	Road lights	4	f	Системи дорожнього освітлення
34993100-5	Tunnel lighting	5	t	Системи освітлення тунелів
34994000-1	Lighting for ship guidance and illumination	4	f	Освітлювальна апаратура для морської навігації та зовнішнього освітлення
34994100-2	Lighting for river guidance and illumination	5	t	Освітлювальна апаратура для річкової навігації та зовнішнього освітлення
34995000-8	Lighting for aircraft guidance and illumination	4	t	Освітлювальна апаратура для повітряної навігації та зовнішнього освітлення
34996000-5	Control, safety or signalling equipment for roads	4	f	Регулювальне, запобіжне чи сигнальне дорожнє обладнання
34996100-6	Traffic lights	5	t	Світлофори
34996200-7	Control, safety or signalling equipment for inland waterways	5	t	Регулювальне, запобіжне чи сигнальне обладнання для внутрішніх водних шляхів
34996300-8	Control, safety or signalling equipment for parking facilities	5	t	Регулювальне, запобіжне чи сигнальне обладнання для автомобільних стоянок
34997000-2	Control, safety or signalling equipment for airports	4	f	Регулювальне, запобіжне чи сигнальне аеропортове обладнання
34997100-3	Flight recorders	5	t	Бортові самописці
34997200-4	Airport lighting	5	f	Аеропортове освітлювальне обладнання
34997210-7	Runway lights	6	t	Системи освітлення злітно-посадкової смуги
34998000-9	Control, safety or signalling equipment for port installations	4	t	Регулювальне, запобіжне чи сигнальне обладнання портових споруд
34999000-6	Signal generators, aerial signal splitters and electroplating machines	4	f	Генератори сигналів, розподільники антенних сигналів та установки для нанесення гальванічного покриття
34999100-7	Signal generators	5	t	Генератори сигналів
34999200-8	Aerial signal splitters	5	t	Розподільники антенних сигналів
34999300-9	Electroplating machines	5	t	Установки для нанесення гальванічного покриття
34999400-0	Scale models	5	f	Масштабовані моделі
34999410-3	Aircraft scale models	6	t	Масштабовані моделі літаків
34999420-6	Boat scale models	6	t	Масштабовані моделі суден
37823600-9	Drawing paper	5	t	Креслярський папір
35000000-4	Security, fire-fighting, police and defence equipment	1	f	Охоронне, протипожежне, поліцейське та оборонне обладнання
35100000-5	Emergency and security equipment	2	f	Аварійне та охоронне обладнання
35110000-8	Firefighting, rescue and safety equipment	3	f	Протипожежне, рятувальне та захисне обладнання
35111000-5	Firefighting equipment	4	f	Протипожежне обладнання
35111100-6	Breathing apparatus for firefighting	5	t	Пожежні респіратори
35111200-7	Firefighting materials	5	t	Засоби пожежогасіння
35111300-8	Fire extinguishers	5	f	Вогнегасники
35111310-1	Foam packages	6	t	Пінні вогнегасники
35111320-4	Portable fire-extinguishers	6	t	Переносні вогнегасники
35111400-9	Fire escape equipment	5	t	Обладнання для пожежної евакуації
35111500-0	Fire suppression system	5	f	Системи пожежогасіння
35111510-3	Fire suppression hand tools	6	t	Ручні інструменти пожежогасіння
35111520-6	Fire suppression foam or similar compounds	6	t	Піна чи подібні суміші для пожежогасіння
35112000-2	Rescue and emergency equipment	4	f	Рятувальне та аварійне обладнання
35112100-3	Emergency training dolls	5	t	Тренувальні манекени для відпрацювання аварійних ситуацій
35112200-4	Emergency shower	5	t	Аварійний душ
35112300-5	Eye shower	5	t	Фонтанчики для промивання очей
35113000-9	Safety equipment	4	f	Захисне обладнання
35113100-0	Site-safety equipment	5	f	Захисне обладнання для атомних електростанцій
35113110-0	Nuclear-reactor protection system	6	t	Системи захисту ядерних реакторів
35113200-1	Nuclear, biological, chemical and radiological protection equipment	5	f	Обладнання для ядерного, біологічного, хімічного та радіологічного захисту
35113210-4	Nuclear safety equipment	6	t	Обладнання для забезпечення ядерної безпеки
35113300-2	Safety installations	5	t	Захисні пристрої
35113400-3	Protective and safety clothing	5	f	Захисний одяг
35113410-6	Garments for biological or chemical protection	6	t	Спеціальний одяг для біологічного та хімічного захисту
35113420-9	Nuclear and radiological protection clothing	6	t	Спеціальний одяг для ядерного та радіологічного захисту
35113430-2	Safety vests	6	t	Захисні жилети
35113440-5	Reflective vests	6	t	Світловідбивні жилети
35113450-8	Protective coats or ponchos	6	t	Захисні плащі та накидки
35113460-1	Protective socks or hosiery	6	t	Захисні шкарпетки чи панчохи
35113470-4	Protective shirts or pants	6	t	Захисні сорочки чи штани
35113480-7	Protective wristbands	6	t	Захисні манжети
35113490-0	Protective frock	6	t	Захисні халати
35120000-1	Surveillance and security systems and devices	3	f	Системи та пристрої нагляду та охорони
35121000-8	Security equipment	4	f	Охоронне обладнання
35121100-9	Buzzers	5	t	Зумери
35121200-0	Detector for forged money	5	t	Детектори банкнот
35121300-1	Security fittings	5	t	Охоронне приладдя
35121400-2	Security bags	5	t	Сумки для безпечного зберігання та перевезення цінностей
35121500-3	Seals	5	t	Пломби
35121600-4	Tags	5	t	Бирки
35121700-5	Alarm systems	5	t	Системи охоронної сигналізації
35121800-6	Convex security mirrors	5	t	Оглядові дзеркала безпеки
35121900-7	Radar detectors	5	t	Детектори радарів
35123000-2	Site-identification equipment	4	f	Обладнання для ідентифікації об’єктів
35123100-3	Magnetic-card system	5	t	Системи на основі магнітних карток
35123200-4	Flexible-working-hours equipment	5	t	Обладнання для обліку робочого часу в умовах гнучкого графіка
35123300-5	Timekeeping system	5	t	Системи обліку часу
35123400-6	Identification badges	5	t	Ідентифікаційні беджі
35123500-7	Video identification systems	5	t	Системи відеоідентифікації
35124000-9	Metal detectors	4	t	Металодетектори
35125000-6	Surveillance system	4	f	Системи стеження
35125100-7	Sensors	5	f	Сенсори
35125110-0	Biometric sensors	6	t	Біометричні сенсори
35125200-8	Time control system or working time recorder	5	t	Системи часового контролю чи засоби обліку робочого часу
35125300-2	Security cameras	5	t	Камери відеоспостереження для охоронних систем
35126000-3	Bar code scanning equipment	4	t	Обладнання для сканування штрих-кодів
35200000-6	Police equipment	2	f	Поліцейське обладнання
35210000-9	Targets for shooting practice	3	t	Тренувальні мішені
35220000-2	Anti-riot equipment	3	f	Обладнання для розгону демонстрантів
35221000-9	Water cannons	4	t	Водомети
35230000-5	Handcuffs	3	t	Кайданки
35240000-8	Sirens	3	t	Сирени
35250000-1	Repellents for canine attack	3	t	Відлякувачі собак
35261000-1	Information panels	4	f	Інформаційні дошки
35261100-2	Changing message indicator panels	5	t	Динамічні індикаторні панелі
35262000-8	Crossing control signalling equipment	4	t	Сигнальне обладнання для регулювання руху на перехрестях
35300000-7	Weapons, ammunition and associated parts	2	f	Зброя, боєприпаси та супутні деталі
35310000-0	Miscellaneous weapons	3	f	Зброя різна
35311000-7	Swords, cutlasses, bayonets and lances	4	f	Мечі, шаблі, багнети та списи
35311100-8	Swords	5	t	Мечі
35311200-9	Cutlasses	5	t	Шаблі
35311300-0	Bayonets	5	t	Багнети
35311400-1	Lances	5	t	Списи
35312000-4	Gas guns	4	t	Газові пістолети
35320000-3	Firearms	3	f	Вогнепальна зброя
35321000-0	Light firearms	4	f	Легка вогнепальна зброя
35321100-1	Hand guns	5	t	Пістолети
35321200-2	Rifles	5	t	Рушниці
35321300-3	Machine guns	5	t	Кулемети
35322000-7	Artillery	4	f	Артилерія
35322100-8	Anti-aircraft	5	t	Зенітна артилерія
35322200-9	Self-propelled artillery	5	t	Самохідна артилерія
35322300-0	Towed artillery	5	t	Буксирувана артилерія
35322400-1	Mortars	5	t	Міномети
35322500-2	Howitzer	5	t	Гаубиці
35330000-6	Ammunition	3	f	Боєприпаси
35331000-3	Ammunition for firearms and warfare	4	f	Боєприпаси для вогнепальної зброї та бойової техніки
35331100-4	Bullets	5	t	Кулі
35331200-5	Shells	5	t	Артилерійські снаряди
35331300-3	Grenades	5	t	Гранати
35331400-7	Land mines	5	t	Наземні міни
35331500-8	Cartridges	5	t	Патрони
35332000-0	Ammunition for naval warfare	4	f	Боєприпаси для військово-морської техніки
35332100-1	Torpedoes	5	t	Торпеди
35332200-2	Sea mines	5	t	Морські міни
35333000-7	Ammunition for aerial warfare	4	f	Боєприпаси для військово-повітряної техніки
35333100-8	Bombs	5	t	Бомби
35333200-9	Rockets	5	t	Ракети
35340000-9	Parts of firearms and ammunition	3	f	Частини вогнепальної зброї та боєприпасів
35341000-6	Parts of light firearms	4	f	Частини легкої вогнепальної зброї
35341100-7	Gunmetal pipe fittings	5	t	Фітинги з гарматного металу
35342000-3	Parts of rocket launchers	4	t	Частини ракетних пускових установок
35343000-0	Parts of mortars	4	t	Частини мінометів
35400000-8	Military vehicles and associated parts	2	f	Транспортні засоби військового призначення та супутнє приладдя
35410000-1	Armoured military vehicles	3	f	Броньовані військові автомобілі
35411000-8	Battle tanks	4	f	Бойові танки
35411100-9	Main battle tanks	5	t	Основні бойові танки
35411200-0	Light battle tanks	5	t	Легкі бойові танки
35412000-5	Armoured combat vehicles	4	f	Броньовані бойові машини
35412100-6	Infantry fighting vehicles	5	t	Бойові машини піхоти
35412200-7	Armoured personnel carriers	5	t	Бронетранспортери
35412300-8	Armoured weapon carriers	5	t	Броньовані машини для перевезення зброї та боєприпасів
35412400-9	Reconnaissance and patrol vehicles	5	t	Розвідувальні та патрульні машини
35412500-0	Command and liaison vehicles	5	t	Командо-штабні машини
35420000-4	Parts of military vehicles	3	f	Частини транспортних засобів військового призначення
35421000-1	Mechanical spare parts for military vehicles	4	f	Механічні запасні частини до транспортних засобів військового призначення
35421100-2	Engines and engine parts for military vehicles	5	t	Двигуни та частини двигунів для транспортних засобів військового призначення
35422000-8	Electronic and electrical spare parts for military vehicles	4	t	Електронні та електричні запасні частини до транспортних засобів військового призначення
35500000-9	Warships and associated parts	2	f	Військові кораблі та супутнє приладдя
35510000-2	Warships	3	f	Військові кораблі
35511000-9	Surface combatant	4	f	Надводні бойові кораблі
35511100-0	Aircraft carriers	5	t	Авіаносці
35511200-1	Destroyers and frigates	5	t	Есмінці та фрегати
35511300-2	Corvettes and patrol boats	5	t	Корвети та патрульні катери
35511400-3	Amphibious crafts and ships	5	t	Десантні судна та кораблі
35512000-6	Submarines	4	f	Підводні човни
35512100-7	Strategic submarine nuclear fuelled	5	t	Атомні стратегічні підводні човни
35512200-8	Attack submarine nuclear fuelled	5	t	Атомні торпедні підводні човни
35512300-9	Attack submarine diesel fuelled	5	t	Дизельні торпедні підводні човни
35512400-0	Unmanned underwater vehicles	5	t	Безпілотні підводні апарати
35513000-3	Mine warfare and auxiliary ships	4	f	Тральники та допоміжні судна
35513100-4	Mine hunter/minesweeper	5	t	Міношукачі / мінні тральники
35513200-5	Auxiliary research vessel	5	t	Допоміжні дослідницькі судна
35513300-6	Auxiliary intelligence collection vessel	5	t	Допоміжні розвідувальні судна
35513400-7	Auxiliary hospital; cargo; tanker; ro-ro vessel	5	t	Допоміжні госпітальні судна; вантажні судна; танкери; ролкери
35520000-5	Parts for warships	3	f	Частини військових суден
35521000-2	Hull and mechanical spare parts for warships	4	f	Корпуси та механічні запасні частини до військових суден
35521100-3	Engines and engine parts for warships	5	t	Двигуни та частини двигунів для військових суден
35522000-9	Electronic and electrical spare parts for warships	4	t	Електронні та електричні запасні частини до військових суден
35600000-0	Military aircrafts, missiles and spacecrafts	2	f	Військові літаки, ракети та космічні апарати
35610000-3	Military aircrafts	3	f	Військові літаки
35611100-1	Fighter aircrafts	5	t	Винищувачі
35611200-2	Fighter-bomber/ground attack aircrafts	5	t	Винищувачі-бомбардувальники / штурмовики
35611300-3	Bomber aircrafts	5	t	Бомбардувальники
35611400-4	Military transport aircrafts	5	t	Військово-транспортні літаки
35611500-5	Training aircrafts	5	t	Навчально-тренувальні літаки
35611600-6	Maritime patrol aircrafts	5	t	Морські патрульні літаки
35611700-7	Tanker aircrafts	5	t	Літаки-заправники
35611800-8	Reconnaissance aircrafts	5	t	Розвідувальні літаки
35612100-8	Combat helicopters	5	t	Бойові вертольоти
35612200-9	Anti submarine warfare helicopters	5	t	Вертольоти протичовнової оборони
35612300-0	Support helicopters	5	t	Вертольоти підтримки
35612400-1	Military transport helicopters	5	t	Військово-транспортні вертольоти
35612500-2	Search and rescue helicopters	5	t	Пошуково-рятувальні вертольоти
35613000-4	Unmanned aerial vehicles	4	f	Безпілотні літальні апарати
35613100-5	Unmanned combat aerial vehicles	5	t	Безпілотні бойові літальні апарати
35620000-6	Missiles	3	f	Ракети
35621000-3	Strategic missiles	4	f	Ракети стратегічного призначення
35621100-4	Strategic anti-ballistic missiles	5	t	Протибалістичні ракети стратегічного призначення
35621200-5	Inter continental ballistic missiles	5	t	Міжконтинентальні балістичні ракети
35621300-6	Submarine launched ballistic missiles	5	t	Балістичні ракети для запуску з підводних човнів
35621400-7	Intermediate range ballistic missiles	5	t	Балістичні ракети середньої дальності
35622000-0	Tactical missiles	4	f	Ракети тактичного призначення
35622100-1	Air-to-air missiles	5	t	Ракети класу «повітря – повітря»
35622200-2	Air-to-ground missiles	5	t	Ракети класу «повітря – земля»
35622300-3	Anti-ship missiles	5	t	Протикорабельні ракети
35622400-4	Anti-submarines rockets	5	t	Протичовнові ракети
35622500-5	Tactical anti-ballistic missiles	5	t	Протибалістичні ракети тактичного призначення
35622600-6	Anti-tank guided missiles	5	t	Протитанкові керовані ракети
35622700-7	Surface-to-air missiles	5	t	Ракети класу «земля – повітря»
35623000-7	Cruise missiles	4	f	Крилаті ракети
35623100-8	Air/ground/sea launched cruise missiles	5	t	Крилаті ракети повітряного / наземного / морського базування
35630000-9	Military spacecrafts	3	f	Військові космічні апарати
35631000-6	Military satellites	4	f	Військові супутники
35631100-7	Communication satellites	5	t	Супутники зв’язку
35631200-8	Observation satellites	5	t	Спостережні супутники
35631300-9	Navigation satellites	5	t	Навігаційні супутники
35640000-2	Parts for military aerospace equipment	3	f	Частини для військового аерокосмічного обладнання
35641000-9	Structure and mechanical spare parts for military aerospace equipment	4	f	Структурні та механічні запасні частини для військового аерокосмічного обладнання
35641100-0	Engines and engine parts for military aerospace equipment	5	t	Двигуни та частини двигунів для військового аерокосмічного обладнання
35642000-7	Electronic and electrical spare parts for military aerospace equipment	4	t	Електронні та електричні запасні частини для військового аерокосмічного обладнання
35700000-1	Military electronic systems	2	f	Військові електронні системи
35710000-4	Command, control, communication and computer systems	3	f	Системи керування, контролю, зв’язку та комп’ютерні системи
35711000-1	Command, control, communication systems	4	t	Системи керування, контролю та зв’язку
35712000-8	Tactical command, control and communication systems	4	t	Системи тактичного керування, контролю та зв’язку
35720000-7	Intelligence, surveillance, target acquisition and reconnaissance	3	f	Системи розвідки, спостереження, виявлення цілі та рекогносцировки
39157000-7	Parts of furniture	4	t	Частини меблів
35721000-4	Electronic intelligence system	4	t	Електронні розвідувальні системи
35722000-1	Radar	4	t	Радари
35723000-8	Air defence radar	4	t	Радар протиповітряної оборони
35730000-0	Electronic warfare systems and counter measures	3	t	Електронні бойові комплекси та засоби радіоелектронного захисту
35740000-3	Battle simulators	3	t	Симулятори бойових дій
35800000-2	Individual and support equipment	2	f	Індивідуальне обмундирування та допоміжне екіпірування
35810000-5	Individual equipment	3	f	Індивідуальне обмундирування
35811100-3	Fire-brigade uniforms	5	t	Пожежна уніформа
35811200-4	Police uniforms	5	t	Поліцейська уніформа
35811300-5	Military uniforms	5	t	Військова уніформа
35812000-9	Combat uniforms	4	f	Бойова уніформа
35812100-0	Camouflage jackets	5	t	Камуфляжні куртки
35812200-1	Combat suits	5	t	Бойові костюми
35812300-2	Combat gear	5	t	Бойове спорядження
35813000-6	Military helmets	4	f	Військові шоломи
35813100-7	Helmet covers	5	t	Маскувальні чохли для шоломів
35814000-3	Gas masks	4	t	Протигази
35815000-0	Garments for anti-ballistic protection	4	f	Куленепробивний одяг
35815100-1	Bullet-proof vests	5	t	Бронежилети
35820000-8	Support equipment	3	f	Допоміжне екіпірування
35821000-5	Flags	4	f	Прапори
35821100-6	Flagpole	5	t	Флагштоки
37000000-8	Musical instruments, sport goods, games, toys, handicraft, art materials and accessories	1	f	Музичні інструменти, спортивні товари, ігри, іграшки, ремісничі, художні матеріали та приладдя
37300000-1	Musical instruments and parts	2	f	Музичні інструменти та їх частини
37310000-4	Musical instruments	3	f	Музичні інструменти
37311000-1	Keyboard instruments	4	f	Клавішні інструменти
37311100-2	Pianos	5	t	Фортепіано
37311200-3	Accordions	5	t	Акордеони
37311300-4	Musical organs	5	t	Органи
37311400-5	Celestas	5	t	Челеста
37312000-8	Brass instruments	4	f	Мідні духові інструменти
37312100-9	Trumpets	5	t	Труби
37312200-0	Trombones	5	t	Тромбони
37312300-1	Sousaphones	5	t	Сузафони
37312400-2	Saxophones	5	t	Саксофони
37312500-3	Whistle	5	t	Свистки
37312600-4	Bugles	5	t	Горни
37312700-5	Saxhorns	5	t	Саксгорни
37312800-6	Mellophones	5	t	Мелофони
37312900-7	Alto, baritone, flugel and French horns	5	f	Альтгорни, баритони, флюгельгорни та валторни
37312910-0	Alto horns	6	t	Альтгорни
37312920-3	Baritone horns	6	t	Баритони
37312930-6	Flugel horns	6	t	Флюгельгорни
37312940-9	French horns	6	t	Валторни
37313000-5	String instruments	4	f	Струнні інструменти
37313100-6	Harpsichords	5	t	Клавесини
37313200-7	Clavichords	5	t	Клавікорди
37313300-8	Guitars	5	t	Гітари
37313400-9	Violins	5	t	Скрипки
37313500-0	Harps	5	t	Арфи
37313600-1	Banjos	5	t	Банджо
37313700-2	Mandolins	5	t	Мандоліни
37313800-3	Violoncellos	5	t	Віолончелі
37313900-4	Basses	5	t	Контрабаси
37314000-2	Wind instruments	4	f	Духові інструменти
37314100-3	Clarinets	5	t	Кларнети
37314200-4	Oboes	5	t	Гобої
37314300-5	Musical cornets and flutes	5	f	Корнети та флейти
37314310-8	Musical cornets	6	t	Корнети
37314320-1	Musical flutes	6	t	Флейти
37314400-6	Piccolos	5	t	Флейти-піколо
37314500-7	Bagpipes	5	t	Волинки
37314600-8	Harmonicas	5	t	Губні гармоніки
37314700-9	Kazoos	5	t	Казу
37314800-0	English horns	5	t	Англійські ріжки
37314900-1	Ocarinas	5	t	Окарини
37315000-9	Electrically amplified musical instruments	4	f	Музичні інструменти з електропідсилювачем
37315100-0	Synthesisers	5	t	Синтезатори
37316000-6	Percussion instruments	4	f	Ударні інструменти
37316100-7	Cymbals	5	t	Цимбали
37316200-8	Bells (instrument)	5	t	Дзвони (інструмент)
37316300-9	Tambourines	5	t	Бубни
37316400-0	Castanets	5	t	Кастаньєти
37316500-1	Drums (instrument)	5	t	Барабани
37316600-2	Xylophones	5	t	Ксилофони
37316700-3	Vibraphones	5	t	Вібрафони
37320000-7	Parts and accessories of musical instruments	3	f	Частини та приладдя до музичних інструментів
37321000-4	Accessories of musical instruments	4	f	Приладдя до музичних інструментів
37321100-5	Metronomes	5	t	Метрономи
37321200-6	Reeds	5	t	Язички
37321300-7	Accessories for stringed instruments	5	t	Приладдя до струнних інструментів
37321400-8	Instrument strings or picks	5	t	Струни чи плектри
39160000-1	School furniture	3	f	Шкільні меблі
37321500-9	Percussion instrument accessory	5	t	Приладдя до ударних інструментів
37321600-0	Musical instrument pouches or cases or accessories	5	t	Чохли, футляри чи приладдя до музичних інструментів
37321700-1	Musical instrument stands or sheet holders	5	t	Підставки для музичних інструментів або пюпітри
37322000-1	Parts of musical instruments	4	f	Деталі музичних інструментів
37322100-2	Tuning pins	5	t	Кілки
37322200-3	Music boxes or mechanisms	5	t	Музичні скриньки чи механізми
37322300-4	Mouthpieces	5	t	Мундштуки
37322400-5	Mutes	5	t	Сурдини
37322500-6	Tuning bars	5	t	Камертони
37322600-7	Conductors' batons	5	t	Диригентські палички
37322700-8	Piccolo pads	5	t	Подушки для флейт-піколо
37400000-2	Sports goods and equipment	2	f	Спортивні товари та інвентар
37410000-5	Outdoor sports equipment	3	f	Інвентар для спортивних ігор на відкритому повітрі
37411000-2	Winter equipments	4	f	Спортивний інвентар для зимових видів спорту
37411100-3	Skiing and snowboarding equipment	5	f	Лижний інвентар та інвентар для сноубордів
37411110-6	Ski boots	6	t	Лижні черевики
37411120-9	Skis	6	t	Лижі
37411130-2	Ski poles	6	t	Лижні палиці
37411140-5	Bindings	6	t	Лижні кріплення
37411150-8	Snowboards	6	t	Сноуборди
37411160-1	Skiing outfits	6	t	Лижні костюми
37411200-4	Skating and ice hockey equipment	5	f	Ковзанярський та хокейний інвентар
37411210-7	Hockey pucks	6	t	Хокейні шайби
37411220-0	Ice skates	6	t	Ковзани
37411230-3	Hockey sticks	6	t	Хокейні ключки
37411300-5	Arctic clothing and equipment	5	t	Спортивні зимові одяг та спорядження
37412000-9	Water-sports equipment	4	f	Інвентар для водних видів спорту
37412100-0	Water skis	5	t	Водні лижі
37412200-1	Scuba and snorkelling gear	5	f	Інвентар для дайвінгу та сноркелінгу
37412210-4	Buoyancy compensators	6	t	Компенсатори плавучості
37412220-7	Scuba tanks	6	t	Балони для дайвінгу
37412230-0	Scuba regulators	6	t	Регулятори для аквалангів
37412240-3	Diving instruments or accessories	6	f	Інструменти чи приладдя для дайвінгу
37412241-0	Breathing apparatus for diving	7	t	Дихальні апарати для дайвінгу
37412242-7	Diving wear	7	t	Водолазні костюми
37412243-4	Immersion suits	7	t	Термогідрокостюми
37412250-6	Masks, fins or snorkels	6	t	Маски, ласти чи дихальні трубки
37412260-9	Wetsuits	6	t	Мокрі гідрокостюми
37412270-2	Dry suits	6	t	Сухі гідрокостюми
37412300-2	Surf and swim equipment and accessories	5	f	Інвентар та приладдя для серфінгу та плавання
37412310-5	Wakeboards, kneeboards or boogieboards	6	t	Вейкборди, ніборди чи бугіборди
37412320-8	Windsurfing equipment	6	t	Інвентар для віндсерфінгу
37412330-1	Surfboards	6	t	Дошки для серфінгу
37412340-4	Swim goggles or swim fins	6	t	Плавальні окуляри чи ласти
37412350-7	Parasailing equipment	6	t	Інвентар для парасейлінгу
37413000-6	Articles for hunting or fishing	4	f	Мисливське чи риболовне спорядження
37413100-7	Fishing tackle	5	f	Риболовні снасті
37413110-0	Fishing rods	6	t	Риболовні вудки
37413120-3	Fishing line	6	t	Риболовна волосінь
37413130-6	Fishing reels	6	t	Котушки для спінінгів
37413140-9	Fishing lures	6	t	Блешні
37413150-2	Fishing bait	6	t	Риболовні наживки
37413160-5	Fishing weights or sinkers	6	t	Риболовні тягарці та грузильця
37413200-8	Hunting products	5	f	Мисливське спорядження
37413210-1	Animal calls	6	t	Манки для полювання
37413220-4	Sporting decoys	6	t	Приманки-опудала
37413230-7	Sporting traps	6	t	Мисливські пастки
37413240-0	Gun barrel	6	t	Стволи зброї
37414000-3	Camping goods	4	f	Спорядження для кемпінгу
37414100-4	Sleeping pads	5	t	Туристичні килимки
37414200-5	Ice chests	5	t	Термоконтейнери
37414300-6	Tent repair kits	5	t	Набори для ремонту наметів
37414600-9	Camping or outdoor stoves	5	t	Туристичні чи вуличні пальники
37414700-0	Drink coolers	5	t	Охолоджувачі напоїв
37414800-1	Survival suits	5	t	Рятувальні костюми
37415000-0	Athletics equipment	4	t	Легкоатлетичний інвентар
37416000-7	Leisure equipment	4	t	Інвентар для відпочинку та дозвілля
37420000-8	Gymnasium equipment	3	f	Гімнастичний інвентар
37421000-5	Gymnasium mats	4	t	Гімнастичні мати
37422000-2	Gymnastic bars or beams	4	f	Гімнастичні бруси чи колоди
37422100-3	Gymnastic bars	5	t	Гімнастичні бруси
37422200-4	Gymnastic beams	5	t	Гімнастичні колоди
37423000-9	Gymnastic ropes or rings or climbing accessories	4	f	Скакалки чи обручі для художньої гімнастики або приладдя до гімнастичних драбин
37423100-0	Gymnastic ropes	5	t	Скакалки для художньої гімнастики
37423200-1	Gymnastic rings	5	t	Обручі для художньої гімнастики
37423300-2	Gymnastic climbing accessories	5	t	Приладдя до гімнастичних драбин
37424000-6	Gymnastic vaulting equipment	4	t	Гімнастичні снаряди для стрибків
37425000-3	Gymnastic trampolines	4	t	Гімнастичні трампліни
37426000-0	Balance equipment	4	t	Снаряди для вправ на рівновагу
37430000-1	Boxing equipment	3	f	Інвентар для боксу
37431000-8	Boxing rings	4	t	Боксерські ринги
37432000-5	Punching bags	4	t	Боксерські груші
37433000-2	Boxing gloves	4	t	Боксерські рукавиці
37440000-4	Fitness equipments	3	f	Інвентар для фітнесу
37441000-1	Aerobic training equipment	4	f	Інвентар для аеробіки
37441100-2	Treadmills	5	t	Бігові доріжки
37441200-3	Stair climbers	5	t	Степери
37441300-4	Stationary bicycles	5	t	Велотренажери
37441400-5	Rowing machines	5	t	Веслувальні тренажери
37441500-6	Jump ropes	5	t	Скакалки
37441600-7	Exercise trampolines	5	t	Спортивні батути
37441700-8	Exercise balls	5	t	Фітболи
37441800-9	Step aerobic equipment	5	t	Інвентар для степ-аеробіки
37441900-0	Cross trainers	5	t	Орбітреки
37442000-8	Weight and resistance training equipment	4	f	Інвентар для силових вправ
37442100-8	Dumbbells	5	t	Гантелі
37442200-8	Barbells	5	t	Штанги
37442300-8	Lower and upper body resistance machines	5	f	Силові тренажери для нижньої та верхньої частин тіла
37442310-4	Lower body resistance machines	6	t	Силові тренажери для нижньої частини тіла
37442320-7	Upper body resistance machines	6	t	Силові тренажери для верхньої частини тіла
37442400-8	Weight benches or racks	5	t	Лавки або стійки для штанг і гантелей
37442500-8	Fitness weights	5	t	Гантелі для фітнесу
37442600-8	Pilates machines	5	t	Тренажери для пілатесу
37442700-8	Grip strengtheners	5	t	Кистьові еспандери
37442800-8	Resistance bands and tubes	5	f	Стрічки-еспандери та еспандери
37442810-9	Resistance bands	6	t	Стрічки-еспандери
37442820-2	Resistance tubes	6	t	Еспандери
37442900-8	Multi gyms	5	t	Багатофункціональні тренажери
37450000-7	Field and court sports equipment	3	f	Спортивний інвентар для полів і кортів
37451000-4	Field sports equipment	4	f	Спортивний інвентар для полів
37451100-5	Baseballs	5	f	Бейсбольний інвентар
37451110-8	Baseball backstops or fences	6	t	Захисні огорожі чи паркани для бейсбольного поля
37451120-1	Baseball bases	6	t	Бейсбольні бази
37451130-4	Baseball bats	6	t	Бейсбольні бити
37451140-7	Baseball batting aids	6	t	Бейсбольні тренажери
37451150-0	Baseball gloves	6	t	Бейсбольні рукавиці
37451160-3	Baseball or softball protective gear	6	t	Захисне спорядження для бейсболу чи софтболу
37451200-6	Field hockey equipment	5	f	Інвентар для хокею на траві
37451210-9	Field hockey balls	6	t	М’ячі для хокею на траві
37451220-2	Field hockey sticks	6	t	Ключки для хокею на траві
37451300-7	Footballs	5	f	Інвентар для американського футболу
37451310-0	Football blocking sleds	6	t	Тренажери для американського футболу
37451320-3	Football kicking tees	6	t	Підставки для м’ячів для американського футболу
37451330-6	Football tackling dummies	6	t	Тренувальні манекени для американського футболу
37451340-9	Flag football gear	6	t	Приладдя для флаг-футболу
37451400-8	Lacrosse balls	5	t	М’ячі для лакросу
37451500-9	Lacrosse sticks	5	t	Ключки для лакросу
37451600-0	Pitching machines	5	t	Машини для подавання м’ячів
37451700-1	Soccer balls	5	f	Футбольні м’ячі
37451710-4	Soccer field marking equipment	6	t	Обладнання для розмітки футбольних полів
37451720-7	Soccer protective equipment	6	t	Захисне екіпірування для гри у футбол
37451730-0	Soccer training aids	6	t	Футбольні тренажери
37451800-2	Softballs	5	f	Інвентар для софтболу
37451810-5	Softball bats	6	t	Бити для софтболу
37451820-8	Softball gloves	6	t	Рукавиці для софтболу
37451900-3	Handballs	5	f	Гандбольний інвентар
37451920-9	Team handball school sets	6	t	Гандбольні набори для шкільних команд
37452000-1	Racquet and court sports equipment	4	f	Інвентар для ракеткових і кортових видів спорту
37452100-2	Badminton equipment	5	f	Бадмінтонний інвентар
37823700-0	Paper for maps	5	t	Картографічний папір
37452110-5	Badminton birdies or shuttlecocks	6	t	Волани з пластмаси або пір’я для бадмінтону
37452120-8	Badminton rackets	6	t	Ракетки для бадмінтону
37452200-3	Basketballs	5	f	Баскетбольний інвентар
37452210-6	Basketball complete game systems	6	t	Баскетбольні щити з кільцем
37452300-4	Floor hockey protective equipment	5	t	Захисне спорядження для хокею на підлозі
37452400-5	Racquetball balls, grips and strings	5	f	М’ячі, руків’я та струни ракеток для ракетболу
37452410-8	Racquetball balls	6	t	М’ячі для ракетболу
37452420-1	Racquetball grips	6	t	Руків’я ракеток для ракетболу
37452430-4	Racquetball strings	6	t	Струни ракеток для ракетболу
37452500-6	Racquetball rackets	5	t	Ракетки для ракетболу
37452600-7	Squash equipment	5	f	Інвентар для сквошу
37452610-0	Squash balls	6	t	М’ячі для сквошу
37452620-3	Squash racquets	6	t	Ракетки для сквошу
37452700-8	Tennis equipment	5	f	Тенісний інвентар
37452710-1	Tennis balls	6	t	Тенісні м’ячі
37452720-4	Tennis court equipment	6	t	Обладнання для тенісних кортів
37452730-7	Tennis racquets	6	t	Тенісні ракетки
37452740-0	Tennis training aids	6	t	Тенісні тренажери
37452800-9	Tether balls and poles	5	f	М’ячі та жердини для тетерболу
37452810-2	Tether balls	6	t	М’ячі для тетерболу
37452820-5	Tether poles	6	t	Жердини для тетерболу
37452900-0	Volleyballs	5	f	Волейбольний інвентар
37452910-3	Volleyball gymnasium standards	6	t	Волейбольні стійки для зали
37452920-6	Volleyball storage for balls or nets	6	t	Приладдя для складання волейбольних м’ячів або сіток
37453000-8	Track sports equipment	4	f	Інвентар для легкої атлетики
37453100-9	Javelins	5	t	Метальні списи
37453200-0	Jumping bars	5	t	Перекладини для стрибків у висоту
37453300-1	Discus	5	t	Диски
37453400-2	Shotputs	5	t	Ядра для штовхання
37453500-3	Vaulting poles	5	t	Жердини для стрибків у висоту
37453600-4	Hurdles	5	t	Бар’єри
37453700-5	Batons	5	t	Естафетні палички
37460000-0	Target and table games and equipments	3	f	Ігри на влучність, настільні ігри та інвентар
37461000-7	Table games and equipment	4	f	Настільні ігри та інвентар
37461100-8	Air hockey tables or accessories	5	t	Столи та приладдя для повітряного хокею
37461200-9	Foosballs	5	f	Інвентар для настільного футболу
37461210-2	Foosball replacement players	6	t	Запасні фігурки гравців для настільного футболу
37461220-5	Foosball tables	6	t	Столи для настільного футболу
37461300-0	Pool cues	5	t	Більярдні киї
37461400-1	Shuffleboard equipment	5	t	Інвентар для шафлборду
37461500-2	Tennis tables	5	f	Столи для настільного тенісу
37461510-5	Table tennis balls	6	t	М’ячі для настільного тенісу
37461520-8	Table tennis paddles	6	t	Ракетки для настільного тенісу
37462000-4	Target games and equipment	4	f	Ігри на влучність та інвентар
37462100-5	Archery equipments	5	f	Інвентар для стрільби з лука
37462110-8	Archery arm guards	6	t	Захисні нарукавники для стрільби з лука
37462120-1	Archery arrows	6	t	Стріли для стрільби з лука
37462130-4	Archery backstops	6	t	Захисні огорожі для стрільби з лука
37462140-7	Archery bow strings	6	t	Тятива для лука
37462150-0	Archery bows	6	t	Луки
37462160-3	Archery gloves	6	t	Рукавиці для стрільби з лука
37462170-6	Archery target stands	6	t	Штативи для мішеней для стрільби з лука
37462180-9	Archery targets	6	t	Мішені для стрільби з лука
37462200-6	Darts	5	f	Дартс
37462210-9	Dart boards	6	t	Мішені для дартсу
37462300-7	Throwing targets	5	t	Мішені для кидання
37462400-8	Trapshooting equipment	5	t	Інвентар для стендової стрільби
37470000-3	Golf and bowling equipments	3	f	Інвентар для гольфу та боулінгу
37471000-0	Golf equipment	4	f	Інвентар для гольфу
37471100-1	Golf bags	5	t	Сумки для гольфу
37471200-2	Golf balls	5	t	М’ячі для гольфу
37471300-3	Golf clubs	5	t	Ключки для гольфу
37471400-4	Golf tees	5	t	Підставки для м’ячів для гольфу
37471500-5	Golf club head covers	5	t	Чохли для головок ключок для гольфу
37471600-6	Golf gloves	5	t	Рукавички для гольфу
37471700-7	Divot fixers	5	t	Вилки для гольфу
37471800-8	Golfscopes	5	t	Далекоміри для гольфу
37471900-9	Golf putting partner	5	t	Подавальні машини для гольфу
37472000-7	Bowling equipments	4	t	Інвентар для боулінгу
37480000-6	Machinery or apparatus for leisure equipment	3	f	Техніка чи апаратура для приладдя для відпочину та дозвілля
37481000-3	Ice maintenance machines	4	t	Машини для шліфування льоду на ковзанках
37482000-0	Sports information billboards	4	t	Дошки для анонсів спортивних заходів
37500000-3	Games and toys; fairground amusements	2	f	Ігри та іграшки; атракціони
37510000-6	Dolls	3	f	Ляльки
37511000-3	Doll houses	4	t	Лялькові будиночки
37512000-0	Doll parts or accessories	4	t	Деталі ляльок чи аксесуари до ляльок
37513000-7	Puppets	4	f	Маріонетки
37513100-8	Puppet theatres	5	t	Лялькові театри
37520000-9	Toys	3	f	Іграшки
37521000-6	Toy musical instruments	4	t	Іграшкові музичні інструменти
37522000-3	Wheeled toys	4	t	Іграшки на колесах
37523000-0	Puzzles	4	t	Пазли
37524000-7	Games	4	f	Ігри
37524100-8	Educational games	5	t	Навчальні ігри
37524200-9	Board games	5	t	Ігри на спеціальній дошці
37524300-0	Classic games	5	t	Класичні ігри
37524400-1	Collaborative games	5	t	Колективні ігри
37524500-2	Strategy games	5	t	Ігри-стратегії
37524600-3	Memory games	5	t	Ігри для тренування пам’яті
37524700-4	Game accessories	5	t	Гральне приладдя
37524800-5	Lotto games	5	f	Лото
37524810-8	Lottery formulary	6	t	Лотерейні білети
37524900-6	Game kits	5	t	Ігрові набори
37525000-4	Toy balloons and balls	4	t	Повітряні кульки на іграшкові м’ячі
37526000-1	Toy pails	4	t	Іграшкові відерця
37527000-8	Toy trains and vehicles	4	f	Іграшкові поїзди та машинки
37527100-9	Toy trains	5	t	Іграшкові поїзди
37527200-0	Toy vehicles	5	t	Іграшкові машинки
37528000-5	Toy weapons	4	t	Іграшкова зброя
37529000-2	Inflatable and riding toys	4	f	Надувні іграшки та іграшки для катання
37529100-3	Inflatable toys	5	t	Надувні іграшки
37529200-4	Riding toys	5	t	Іграшки для катання
37530000-2	Articles for funfair, table or parlour games	3	f	Вироби для парків розваг, настільних або кімнатних ігор
37531000-9	Playing cards	4	t	Гральні карти
37532000-6	Video games	4	t	Відеоігри
37533000-3	Billiards	4	f	Більярд
37533100-4	Billiard balls	5	t	Більярдні кулі
37533200-5	Billiard chalk	5	t	Більярдна крейда
37533300-6	Billiard cue tips	5	t	Наконечники для більярдних київ
37533400-7	Billiard racks	5	t	Більярдні трикутники
37533500-8	Billiard tables	5	t	Більярдні столи
37534000-0	Coin- or disc-operated games	4	t	Ігрові автомати (слоти), що приймають монети чи жетони
37535000-7	Roundabouts, swings, shooting galleries and other fairground amusements	4	f	Каруселі, гойдалки, тири та інші атракціони
37535100-8	Swings	5	t	Гойдалки
37535200-9	Playground equipment	5	f	Обладнання для ігрових майданчиків
37535210-2	Playground swings	6	t	Гойдалки для ігрових майданчиків
37535220-5	Playground climbing apparatus	6	t	Лазальні снаряди для ігрових майданчиків
37535230-8	Playground merry go rounds	6	t	Каруселі для ігрових майданчиків
37535240-1	Playground slides	6	t	Гірки для ігрових майданчиків
37535250-4	Playground see saws	6	t	Гойдалки-балансири для ігрових майданчиків
37535260-7	Playground tunnels	6	t	Тунелі для ігрових майданчиків
37535270-0	Playground sandboxes	6	t	Пісочниці для ігрових майданчиків
37535280-3	Playground bleachers	6	t	Лавки-трибуни для ігрових майданчиків
37535290-6	Wall and rope climbing equipment	6	f	Спорядження для скалодромів і лазіння канатом
37535291-3	Wall climbing equipment	7	t	Спорядження для скалодромів
37535292-0	Rope climbing equipment	7	t	Спорядження для лазіння канатом
37540000-5	Gambling machines	3	t	Ігрові автомати
37800000-6	Handicraft and art supplies	2	f	Приладдя для рукоділля та образотворчого мистецтва
37810000-9	Handicraft supplies	3	t	Приладдя для рукоділля
37820000-2	Art supplies	3	f	Приладдя для образотворчого мистецтва
37821000-9	Artists' brushes	4	t	Пензлі для малювання
37822000-6	Drawing pens	4	f	Рейсфедери
37822100-7	Crayons	5	t	Кольорові олівці
37822200-8	Drawing charcoal	5	t	Вугільні олівці
37822300-9	Chalks	5	t	Кольорова крейда
37822400-0	Pastels	5	t	Пастелі
37823000-3	Greaseproof paper and other paper items	4	f	Пергаментний папір та інші паперові вироби
37823100-4	Greaseproof paper	5	t	Пергаментний папір
37823200-5	Tracing paper	5	t	Калька
37823300-6	Glassine paper	5	t	Пергамін
37823400-7	Transparent or translucent paper	5	t	Прозорий або напівпрозорий папір
37823500-8	Art and craft paper	5	t	Крейдований папір і крафт-папір
37823800-1	Multi-ply paper and paperboard	5	t	Багатошаровий папір і картон
37823900-2	Kraftliner	5	t	Тарний картон
38000000-5	Laboratory, optical and precision equipments (excl. glasses)	1	f	Лабораторне, оптичне та високоточне обладнання (крім лінз)
38100000-6	Navigational and meteorological instruments	2	f	Навігаційні та метеорологічні прилади
38110000-9	Navigational instruments	3	f	Навігаційні прилади
38111000-6	Direction-finding equipment	4	f	Радіопеленгаційні прилади
38111100-7	Compasses	5	f	Компаси
38111110-0	Compass accessories	6	t	Приладдя до компасів
38112000-3	Sextants	4	f	Секстанти
38112100-4	Global navigation and positioning systems (GPS or equivalent)	5	t	Системи глобальної навігації та глобального позиціонування (GPS або аналогічні системи)
38113000-0	Sonars	4	t	Гідролокатори
38114000-7	Echo sounders	4	t	Ехолоти
38115000-4	Radar apparatus	4	f	Радіолокаційна апаратура
38115100-5	Radar surveillance equipment	5	t	Обладнання для радіолокаційного спостереження
38120000-2	Meteorological instruments	3	f	Метеорологічні прилади
38121000-9	Anemometers	4	t	Анемометри
38122000-6	Barometers	4	t	Барометри
38123000-3	Precipitation or evaporation recorders	4	t	Осадкографи та евапорографи
38124000-0	Radiosonde apparatus	4	t	Радіозонди
38125000-7	Rainfall recorders	4	t	Дощоміри
38126000-4	Surface observing apparatus	4	f	Апаратура для приземних метеорологічних спостережень
38126100-5	Precipitation or evaporation surface observing apparatus	5	t	Апаратура для спостережень за опадами та випаровуваннями в приземному шарі
38126200-6	Solar radiation surface observing apparatus	5	t	Апаратура для спостережень за рівнем сонячного випромінювання в приземному шарі
38126300-7	Temperature or humidity surface observing apparatus	5	t	Апаратура для спостереження температури чи вологості в приземному шарі
38126400-8	Wind surface observing apparatus	5	t	Апаратура для спостереження за вітром у приземному шарі
38127000-1	Weather stations	4	t	Метеорологічні станції
38128000-8	Meteorology instrument accessories	4	t	Приладдя до метеорологічних приладів
38200000-7	Geological and geophysical instruments	2	f	Геологічні та геофізичні прилади
38210000-0	Geological compasses	3	t	Геологічні компаси
38220000-3	Geological prospecting apparatus	3	f	Геологорозвідувальна апаратура
38221000-0	Geographic information systems (GIS or equivalent)	4	t	Географічні інформаційні системи (ГІС або аналогічні системи)
38230000-6	Electromagnetic geophysical instruments	3	t	Геофізичні електромагнітні прилади
38240000-9	Gravity geophysical instruments	3	t	Геофізичні гравіметричні прилади
38250000-2	Induced polarization IP geophysical instruments	3	t	Геофізичні прилади для вимірювання індукованої поляризації
38260000-5	Magnetometer geophysical instruments	3	t	Геофізичні магнітометричні прилади
38270000-8	Resistivity geophysical instruments	3	t	Геофізичні прилади для вимірювання опору
38280000-1	Gravimeters	3	t	Гравіметри
38290000-4	Surveying, hydrographic, oceanographic and hydrological instruments and appliances	3	f	Геодезичні, гідрографічні, океанографічні та гідрологічні прилади та пристрої
38291000-1	Telemetry apparatus	4	t	Телеметрична апаратура
38292000-8	Hydrographic instruments	4	t	Гідрографічні прилади
38293000-5	Seismic equipment	4	t	Сейсморозвідувальна апаратура
38294000-2	Theodolites	4	t	Теодоліти
38295000-9	Topography equipment	4	t	Топографічне обладнання
38296000-6	Surveying instruments	4	t	Геодезичні прилади
38300000-8	Measuring instruments	2	f	Вимірювальні прилади
38310000-1	Precision balances	3	f	Високоточні терези
38311000-8	Electronic scales and accessories	4	f	Електронні ваги та приладдя до них
38311100-9	Electronic analytical balances	5	t	Електронні аналітичні ваги
38311200-0	Electronic technical balances	5	f	Електронні технічні ваги
38311210-3	Calibration weights	6	t	Калібрувальні гирі
38320000-4	Drafting tables	3	f	Креслярські столи
38321000-1	Drafting machines	4	t	Креслярські пристрої
38322000-8	Pantographs	4	t	Пантографи
38323000-5	Slide rules	4	t	Логарифмічні лінійки
38330000-7	Hand-held instruments for measuring length	3	f	Ручні прилади для вимірювання відстаней
38331000-4	Squares	4	t	Екери
38340000-0	Instruments for measuring quantities	3	f	Прилади для вимірювання величин
38341000-7	Apparatus for measuring radiation	4	f	Апаратура для вимірювання радіації
38341100-8	Electron-beam recorders	5	t	Електроннопроменеві самописці
38341200-9	Radiation dosimeters	5	t	Дозиметри
38341300-0	Instruments for measuring electrical quantities	5	f	Прилади для вимірювання електричних величин
38341310-3	Ammeters	6	t	Амперметри
38341320-6	Voltmeters	6	t	Вольтметри
38341400-1	Geiger counters	5	t	Лічильники Гейгера
38341500-2	Contamination-monitoring devices	5	t	Прилади для моніторингу рівня зараженості
38341600-3	Radiation monitors	5	t	Радіаційні монітори
38342000-4	Oscilloscopes	4	f	Осцилоскопи
38342100-5	Oscillographs	5	t	Осцилографи
38343000-1	Error-monitoring equipment	4	t	Прилади для виявлення несправностей
38344000-8	Pollution-monitoring devices	4	t	Прилади для моніторингу забруднень
38400000-9	Instruments for checking physical characteristics	2	f	Прилади для перевірки фізичних характеристик
38410000-2	Metering instruments	3	f	Лічильні прилади
38411000-9	Hydrometers	4	t	Гідрометри
38412000-6	Thermometers	4	t	Термометри
38413000-3	Pyrometers	4	t	Пірометри
38414000-0	Hygrometers	4	t	Гігрометри
38415000-7	Psychrometers	4	t	Психометри
38416000-4	pH meters	4	t	pH-метри
38417000-1	Thermocouples	4	t	Термопари
38418000-8	Calorimeters	4	t	Калориметри
38420000-5	Instruments for measuring flow, level and pressure of liquids and gases	3	f	Прилади для вимірювання витрати, рівня та тиску рідин і газів
38421000-2	Flow-measuring equipment	4	f	Прилади для вимірювання витрати рідин і газів
38421100-3	Water meters	5	f	Лічильники води
38421110-6	Flowmeters	6	t	Витратоміри
38422000-9	Level-measuring equipment	4	t	Обладнання для вимірювання рівня рідин і газів
38423000-6	Pressure-measuring equipment	4	f	Обладнання для вимірювання тиску
38423100-7	Pressure gauges	5	t	Прилади для вимірювання тиску
38424000-3	Measuring and control equipment	4	t	Контрольно-вимірювальне обладнання
38425000-0	Fluid mechanics equipment	4	f	Гідромеханічне обладнання
38425100-1	Manometers	5	t	Манометри
38425200-2	Viscosimeters	5	t	Віскозиметри
38425300-3	Depth indicators	5	t	Глибиноміри
38425400-4	Structure estimation apparatus	5	t	Апаратура для конструкційних розрахунків
38425500-5	Strength estimation apparatus	5	t	Апаратура для розрахунку міцності
38425600-6	Pycnometers	5	t	Пікнометри
38425700-7	Surface tension measuring instruments	5	t	Прилади для вимірювання поверхневого натягу
38425800-8	Densitometers	5	t	Денситометри
38426000-7	Coulometers	4	t	Кулометри
38427000-4	Fluxmeters	4	t	Флюксметри
38428000-1	Rheometers	4	t	Реометри
38429000-8	Rotameters	4	t	Ротаметри
38430000-8	Detection and analysis apparatus	3	f	Детектори та аналізатори
38431000-5	Detection apparatus	4	f	Детектори
38431100-6	Gas-detection apparatus	5	t	Детектори газу
38431200-7	Smoke-detection apparatus	5	t	Детектори диму
38431300-8	Fault detectors	5	t	Детектори несправностей
38432000-2	Analysis apparatus	4	f	Аналізатори
38432100-3	Gas-analysis apparatus	5	t	Газоаналізатори
38432200-4	Chromatographs	5	f	Хроматографи
38432210-7	Gas chromatographs	6	t	Газові хроматографи
38432300-5	Smoke-analysis apparatus	5	t	Аналізатори диму
38433000-9	Spectrometers	4	f	Спектрометри
38433100-0	Mass spectrometer	5	t	Мас-спектрометри
38433200-1	Emission measurement equipment	5	f	Обладнання для вимірювання викидів
38433210-4	Emission spectrometer	6	t	Емісійні спектрометри
38433300-2	Spectrum analyser	5	t	Аналізатори спектра
38434000-6	Analysers	4	f	Тестери
38434100-7	Expansion analysers	5	t	Аналізатори розширення
38434200-8	Sound-measuring equipment	5	f	Обладнання для вимірювання акустичних величин
38434210-1	Sonometers	6	t	Сонометри
38434220-4	Sound velocity analyzers	6	t	Аналізатори швидкості звуку
38434300-9	Noise-measuring equipment	5	f	Обладнання для вимірювання шуму
38434310-2	Decibel meter	6	t	Шумоміри
38434400-0	Vibration analysers	5	t	Віброаналізатори
38434500-1	Biochemical analysers	5	f	Біохімічні аналізатори
38434510-4	Cytometers	6	t	Цитометри
38434520-7	Blood analysers	6	t	Аналізатори крові
38434530-0	Milk analysers	6	t	Аналізатори молока
38434540-3	Biomedical equipment	6	t	Біомедичне обладнання
38434550-6	Blood-cell counters	6	t	Лічильники кров’яних тілець
38434560-9	Chemistry analyser	6	t	Хімічні аналізатори
38434570-2	Haematology analysers	6	t	Гематологічні аналізатори
38434580-5	Immunoassay analysers	6	t	Імунологічні аналізатори
38435000-3	Apparatus for detecting fluids	4	t	Детектори рідин
38436000-0	Shakers and accessories	4	f	Шейкери та приладдя до них
38436100-1	Mechanical shakers	5	f	Механічні шейкери
38436110-4	Erlenmeyer flask basket for shakers	6	t	Комплект колб Ерленмеєра для шейкерів
38436120-7	Erlenmeyer flask clamps for shakers	6	t	Затискачі для колб Ерленмеєра для шейкерів
38436130-0	Stands for separating funnels	6	t	Штативи для ділильних лійок
38436140-3	Platform for Erlenmeyer flask clamps for shakers	6	t	Платформа для тримачів колб Ерленмеєра для шейкерів
38436150-6	Petri dish stand for shakers	6	t	Штативи для чашок Петрі для шейкерів
38436160-9	Test tube stand for shakers	6	t	Штативи для пробірок для шейкерів
38436170-2	Flask adaptor for shakers	6	t	Адаптери для колб для шейкерів
38436200-2	Rotating evaporators	5	f	Ротаційні випарники
38436210-5	Protective shield for rotating evaporators	6	t	Захисні плівки для ротаційних випарників
38436220-8	Boiling temperature sensor for rotating evaporators	6	t	Сенсори температури кипіння для ротаційних випарників
38436230-1	Pressure regulator for rotating evaporators	6	t	Регулятори тиску для ротаційних випарників
38436300-3	Incubating shakers	5	f	Інкубатори-шейкери
38436310-6	Heating plates	6	t	Нагрівальні пластини
38436320-9	Heating plates for flasks	6	t	Нагрівальні пластини для колб
38436400-4	Magnetic shakers	5	f	Магнітні шейкери
38436410-7	Thermal regulators for mechanical shakers with heating plates	6	t	Терморегулятори для механічних шейкерів із нагрівальними пластинами
38436500-5	Mechanical stirrers	5	f	Механічні мішалки
38436510-8	Blade impellers for mechanical stirrers	6	t	Лопаті механічних мішалок
38436600-6	Immersion homogenisers	5	f	Імерсійні гомогенізатори
38436610-9	Dispersing instruments for immersion homogenisers	6	t	Розпилювачі для імерсійних гомогенізаторів
38436700-7	Ultrasound disintegrators	5	f	Ультразвукові подрібнювачі
38436710-0	Probes for ultrasound disintegrators	6	t	Зонди для ультразвукових подрібнювачів
38436720-3	Converters for ultrasound disintegrators	6	t	Конвертери для ультразвукових подрібнювачів
38436730-6	Continuous-flow chambers for ultrasound disintegrators	6	t	Проточні камери для ультразвукових подрібнювачів
38436800-8	Rotating blade homogenizers	5	t	Обертові лопатеві гомогенізатори
38437000-7	Laboratory pipettes and accessories	4	f	Лабораторні піпетки та приладдя до них
38437100-8	Pipettes	5	f	Піпетки
38437110-1	Pipette tips	6	t	Наконечники для піпеток
38437120-4	Pipette stands	6	t	Підставки для піпеток
38500000-0	Checking and testing apparatus	2	f	Контрольно-випробувальна апаратура
38510000-3	Microscopes	3	f	Мікроскопи
38511000-0	Electron microscopes	4	f	Електронні мікроскопи
38511100-1	Scanning electron microscopes	5	t	Електронні сканувальні мікроскопи
38511200-2	Transmission electron microscope	5	t	Електронні трансмісійні мікроскопи
38512000-7	Ion and molecular microscopes	4	f	Іонні та молекулярні мікроскопи
38512100-8	Ion microscopes	5	t	Іонні мікроскопи
38512200-9	Molecular microscopes	5	t	Молекулярні мікроскопи
38513000-4	Inverted and metallurgical microscopes	4	f	Інвертовані та металургійні мікроскопи
38513100-5	Inverted microscopes	5	t	Інвертовані мікроскопи
38513200-6	Metallurgical microscopes	5	t	Металургійні мікроскопи
38514000-1	Darkfield and scanning probe microscopes	4	f	Темнопольні та сканувальні зондові мікроскопи
38514100-2	Darkfield microscopes	5	t	Темнопольні мікроскопи
38514200-3	Scanning probe microscopes	5	t	Сканувальні зондові мікроскопи
38515000-8	Fluorescent and polarising microscopes	4	f	Флуоресцентні та поляризаційні мікроскопи
38515100-9	Polarising microscopes	5	t	Поляризаційні мікроскопи
38515200-0	Fluorescent microscopes	5	t	Флуоресцентні мікроскопи
38516000-5	Monocular and/or binocular light compound microscopes	4	t	Монокулярні та/або бінокулярні світлові біологічні мікроскопи
38517000-2	Acoustic and projection microscopes	4	f	Акустичні та проекційні мікроскопи
38517100-3	Acoustic microscopes	5	t	Акустичні мікроскопи
38517200-4	Projection microscopes	5	t	Проекційні мікроскопи
38518000-9	Wide field, stereo or dissecting light microscopes	4	f	Широкопольні, стереоскопічні чи препарувальні світлові мікроскопи
38518100-0	Wide field microscopes	5	t	Широкопольні мікроскопи
38518200-1	Stereo or dissecting light microscopes	5	t	Стереоскопічні чи препарувальні світлові мікроскопи
38519000-6	Miscellaneous compounds for microscopes	4	f	Деталі мікроскопів різні
38519100-7	Illuminators for microscopes	5	t	Джерела світла для мікроскопів
38519200-8	Microscope objectives	5	t	Об’єктиви для мікроскопів
38519300-9	Photo or video attachments for microscopes	5	f	Фото- та відеоприладдя для мікроскопів
38519310-2	Photo attachments for microscopes	6	t	Фотоприладдя для мікроскопів
38519320-5	Video attachments for microscopes	6	t	Відеоприладдя для мікроскопів
38519400-0	Automated microscope stages	5	t	Автоматизовані предметні столики
38519500-1	Laboratory microscope replacement bulbs	5	t	Змінні лампи для лабораторних мікроскопів
38519600-2	Microscope eyepieces, condensers, collectors, tubes, stages and covers	5	f	Окуляри, конденсори, колектори, тубуси, предметні столики та футляри для мікроскопів
38519610-5	Microscope eyepieces	6	t	Окуляри мікроскопів
38519620-8	Microscope condensers	6	t	Конденсори мікроскопів
38519630-1	Microscope collectors	6	t	Колектори мікроскопів
38519640-4	Microscope tubes	6	t	Тубуси мікроскопів
38519650-7	Microscope stages	6	t	Предметні столики
38519660-0	Microscope covers	6	t	Футляри для мікроскопів
38520000-6	Scanners	3	f	Сканери
38521000-3	Pressure scanners	4	t	Сканери тиску
38522000-0	Chromatographic scanners	4	t	Хроматографічні сканери
38527100-6	Ionization chamber dosimeters	5	t	Дозиметри з іонізаційними камерами
38527200-7	Dosimeters	5	t	Дозиметри
38527300-8	Secondary standard dosimetry systems	5	t	Дозиметричні системи вторинного еталону
38527400-9	Phantom dosimeters	5	t	Фантомні дозиметри
38530000-9	Diffraction apparatus	3	t	Дифрактори
38540000-2	Machines and apparatus for testing and measuring	3	f	Випробувальні та вимірювальні пристрої і апарати
38541000-9	Solderability testers	4	t	Тестери паяльності
38542000-6	Servo-hydraulic test apparatus	4	t	Тестери сервогідравлічних характеристик
38543000-3	Gas-detection equipment	4	t	Прилади виявлення газу
38544000-0	Drug detection apparatus	4	t	Детектори наркотичних речовин
38545000-7	Gas-testing kits	4	t	Набори для аналізу газів
38546000-4	Explosives detection system	4	f	Системи виявлення вибухових речовин
38546100-5	Bomb detectors	5	t	Детектори вибухових пристроїв
38547000-1	Dosimetry system	4	t	Дозиметричні системи
38548000-8	Instruments for vehicles	4	t	Автомобільні прилади
38550000-5	Meters	3	f	Лічильники
38551000-2	Energy meters	4	t	Лічильники енергії
38552000-9	Electronic meters	4	t	Електронні лічильники
38553000-6	Magnetic meters	4	t	Магнітні лічильники
38554000-3	Electricity meters	4	t	Лічильники електроенергії
38560000-8	Production counters	3	f	Лічильники продукції
38561000-5	Revolution counters	4	f	Лічильники обертів
38561100-6	Speed indicators for vehicles	5	f	Автомобільні спідометри
38561110-9	Tachometers	6	t	Тахометри
38561120-2	Taxi meters	6	t	Таксометри
38562000-2	Stroboscopes	4	t	Стробоскопи
38570000-1	Regulating and controlling instruments and apparatus	3	f	Регулювальні та контрольні прилади й апаратура
38571000-8	Speed limiters	4	t	Обмежники швидкості
38580000-4	Non-medical equipment based on the use of radiations	3	f	Рентгенологічне та радіологічне обладнання немедичного призначення
38581000-1	Baggage-scanning equipment	4	t	Сканери багажу
38582000-8	X-ray inspection equipment	4	t	Пристрої для рентгенологічного контролю
38600000-1	Optical instruments	2	f	Оптичні прилади
38620000-7	Polarising material	3	f	Поляризаційні матеріали
38621000-4	Fibre-optic apparatus	4	t	Волоконно-оптична апаратура
38622000-1	Mirrors	4	t	Дзеркала
38623000-8	Optical filters	4	t	Оптичні фільтри
38624000-5	Optical aids	4	t	Допоміжні оптичні пристрої
38630000-0	Astronomical and optical instruments	3	f	Астрономічні та оптичні прилади
38631000-7	Binoculars	4	t	Біноклі
38632000-4	Nightglasses	4	t	Прилади нічого бачення
38633000-1	Telescopic sights	4	t	Оптичні приціли
38634000-8	Optical microscopes	4	t	Оптичні мікроскопи
38635000-5	Telescopes	4	t	Телескопи
38636000-2	Specialist optical instruments	4	f	Спеціалізовані оптичні прилади
38636100-3	Lasers	5	f	Лазери
38636110-6	Industrial lasers	6	t	Промислові лазери
38640000-3	Liquid crystal devices	3	f	Рідкокристалічні пристрої
38641000-0	Periscopes	4	t	Перископи
38650000-6	Photographic equipment	3	f	Фотографічне обладнання
38651000-3	Cameras	4	f	Фотоапарати
38651100-4	Camera lenses	5	t	Об’єктиви для фотоапаратів
38651200-5	Camera bodies	5	t	Корпуси фотоапаратів
38651300-6	Cameras for preparing printing plates or cylinders	5	t	Репродукційні камери для виготовлення друкарських пластин або циліндрів
38651400-7	Instant print cameras	5	t	Фотоапаратами з можливістю миттєвого друку фотографій
38651500-8	Cinematographic cameras	5	t	Кінокамери
38651600-9	Digital cameras	5	t	Цифрові фотоапарати
38652000-0	Cinematographic projectors	4	f	Кінопроектори
38652100-1	Projectors	5	f	Проектори
38652110-4	Slide projectors	6	t	Діапроектори
38652120-7	Video projectors	6	t	Відеопроектори
38652200-2	Enlargers	5	t	Збільшувачі
38652300-3	Reducers	5	t	Зменшувачі
38653000-7	Apparatus for photographic laboratories	4	f	Апаратура для фотолабораторій
38653100-8	Flashlights	5	f	Фотоспалахи
38653110-1	Photographic flashbulbs	6	f	Лампи-спалахи
38653111-8	Photographic flashcubes	7	t	Лампи-спалахи кубічної форми
38653200-9	Photographic enlargers	5	t	Фотозбільшувачі
38653300-0	Apparatus and equipment for developing film	5	t	Апаратура та обладнання для проявлення фотоплівок
38653400-1	Projection screens	5	t	Проекційні екрани
38654000-4	Microfilm and microfiche equipment	4	f	Обладнання для роботи з мікрофільмами та мікрофішами
38654100-5	Microfilm equipment	5	f	Обладнання для роботи з мікрофільмами
38654110-8	Microfilm readers	6	t	Пристрої для читання мікрофільмів
38654200-6	Microfiche equipment	5	f	Обладнання для роботи з мікрофішами
38654210-9	Microfiche readers	6	t	Пристрої для читання мікрофіш
38654300-7	Microform equipment	5	f	Обладнання для роботи з мікроформами
38654310-0	Microform readers	6	t	Пристрої для читання мікроформ
38700000-2	Time registers and the like; parking meters	2	f	Таймери та аналогічні лічильники; паркомати
38710000-5	Time registers	3	t	Таймери
38720000-8	Time recorders	3	t	Реєстратори часу
38730000-1	Parking meters	3	f	Паркомати
38731000-8	Token meters	4	t	Жетонні передоплатні автомати
38740000-4	Process timers	3	t	Синхронізатори
38750000-7	Time switches	3	t	Реле часу
38800000-3	Industrial process control equipment and remote-control equipment	2	f	Обладнання для керування виробничими процесами та пристрої дистанційного керування
38810000-6	Industrial process control equipment	3	t	Обладнання для керування виробничими процесами
38820000-9	Remote-control equipment	3	f	Пристрої дистанційного керування
38821000-6	Radio remote-control apparatus	4	t	Пристрої дистанційного радіокерування
38822000-3	Remote-control siren devices	4	t	Звукосигнальні пристрої з дистанційним керуванням
38900000-4	Miscellaneous evaluation or testing instruments	2	f	Оцінювальні чи контрольні пристрої різні
38910000-7	Hygiene monitoring and testing equipment	3	f	Обладнання для санітарно-гігієнічного контролю та перевірки
38911000-4	Manual swab test kits	4	t	Комплекти для забору мазків уручну
38912000-1	Automated swab test kits	4	t	Комплекти для автоматизованого забору мазків
38920000-0	Seed and feed equipment	3	f	Обладнання для дослідження насіння та кормів
38921000-7	Grain analysers	4	t	Аналізатори зерна
38922000-4	Seed counters	4	t	Лічильники насіння
38923000-1	Feed analysers	4	t	Аналізатори кормів
38930000-3	Humidity and moisture measuring instruments	3	f	Пристрої для вимірювання вологості та вологи
38931000-0	Temperature humidity testers	4	t	Тестери температури та вологості
38932000-7	Moisture meters	4	t	Вологоміри
38940000-6	Nuclear evaluation instruments	3	f	Прилади для оцінювання радіологічної обстановки
38941000-7	Alpha counters	4	t	Лічильники альфа-частинок
38942000-7	Alpha beta counters	4	t	Лічильники альфа- та бета-частинок
38943000-7	Beta counters	4	t	Лічильники бета-частинок
38944000-7	Beta gamma counters	4	t	Лічильник бета- та гамма-частинок
38945000-7	Gamma counters	4	t	Лічильники гамма-частинок
38946000-7	KVP meters	4	t	Вимірювачі пікової напруги в кіловольтах
38947000-7	X-ray microanalysers	4	t	Рентгенологічні мікроаналізатори
38950000-9	Polymerase Chain Reaction (PCR) equipment	3	f	Обладнання для полімеразної ланцюгової реакції
38951000-6	Real-time Polymerase Chain Reaction (PCR) equipment	4	t	Обладнання для полімеразної ланцюгової реакції у режимі реального часу
38960000-2	Alcohol ignition lock	3	t	Алкозамки
38970000-5	Research, testing and scientific technical simulator	3	t	Дослідницькі, випробувальні та науково-технічні симулятори
39000000-2	Furniture (incl. office furniture), furnishings, domestic appliances (excl. lighting) and cleaning products	1	f	Меблі (у тому числі офісні меблі), меблево-декоративні вироби, побутова техніка (крім освітлювального обладнання) та засоби для чищення
39100000-3	Furniture	2	f	Меблі
39110000-6	Seats, chairs and related products, and associated parts	3	f	Сидіння, стільці та супутні вироби і частини до них
39111000-3	Seats	4	f	Сидіння
39111100-4	Swivel seats	5	t	Поворотні сидіння
39111200-5	Theatre seats	5	t	Театральні крісла
39111300-6	Ejector seats	5	t	Крісла-катапульти
39112000-0	Chairs	4	f	Стільці
39112100-1	Dining chairs	5	t	Стільці для їдалень
39113000-7	Miscellaneous seats and chairs	4	f	Сидіння та стільці різні
39113100-8	Armchairs	5	t	М’які крісла
39113200-9	Settees	5	t	Дивани-канапе
39113300-0	Bench seats	5	t	Банкетки
39113400-1	Deck chairs	5	t	Шезлонги
39113500-2	Stools	5	t	Табурети
39113600-3	Benches	5	t	Лавки
39113700-4	Footrests	5	t	Підставки для ніг
39114000-4	Parts of seats	4	f	Частини сидінь
39114100-5	Upholstering	5	t	Оббивка
39120000-9	Tables, cupboards, desk and bookcases	3	f	Столи, серванти, письмові столи та книжкові шафи
39121000-6	Desks and tables	4	f	Письмові та інші столи
39121100-7	Desks	5	t	Письмові столи
39121200-8	Tables	5	t	Столи
39122000-3	Cupboards and bookcases	4	f	Серванти та книжкові шафи
39122100-4	Cupboards	5	t	Серванти
39122200-5	Bookcases	5	t	Книжкові шафи
39130000-2	Office furniture	3	f	Офісні меблі
39131000-9	Office shelving	4	f	Офісні стелажі
39131100-0	Archive shelving	5	t	Архівні стелажі
39132000-6	Filing systems	4	f	Шафи-картотеки
39132100-7	Filing cabinets	5	t	Пенали-картотеки
39132200-8	Card-index cabinets	5	t	Багатошухлядні шафи-картотеки
39132300-9	Hanging files	5	t	Картотеки для підвісних файлів
39132400-0	Carousel systems	5	t	Обертові картотеки
39132500-1	Office trolleys	5	t	Офісні візки
39133000-3	Display units	4	t	Етажерки
39134000-0	Computer furniture	4	f	Комп’ютерні меблі
39134100-1	Computer tables	5	t	Комп’ютерні столи
39135000-7	Sorting tables	4	f	Сортувальні столи
39135100-8	Sorting frames	5	t	Стелажі-органайзери
39136000-4	Coat hangers	4	t	Вішалки-плічки
39137000-1	Water softeners	4	t	Пом’якшувачі води
39140000-5	Domestic furniture	3	f	Меблі для дому
39141000-2	Kitchen furniture and equipment	4	f	Кухонні меблі та обладнання
39141100-3	Shelves	5	t	Полиці
39141200-4	Worktops	5	t	Стільниці
39141300-5	Cabinets	5	t	Кухонні гарнітури
39141400-6	Fitted kitchens	5	t	Вбудовані кухонні меблі
39141500-7	Fume cupboards	5	t	Витяжні шафи
39142000-9	Garden furniture	4	t	Садові меблі
39143000-6	Bedroom, dining room and living-room furniture	4	f	Меблі для спальні, їдальні та вітальні
39143100-7	Bedroom furniture	5	f	Меблі для спальні
39143110-0	Beds and bedding and specialist soft furnishings	6	f	Ліжка та спальне приладдя і домашній текстиль
39143111-7	Mattress supports	7	t	Каркаси для ліжок
39143112-4	Mattresses	7	t	Матраци
39143113-1	Specialist soft furnishings	7	t	Домашній текстиль
39143114-8	Electric blankets	7	t	Електричні ковдри
39143115-5	Rubber sheets	7	t	Клейонки
39143116-2	Cots	7	t	Дитячі ліжка
39143120-3	Bedroom furniture, other than beds and beddings	6	f	Меблі для спальні, крім ліжок і спального приладдя
39143121-0	Wardrobes	7	t	Гардеробні шафи
39143122-7	Chests of drawers	7	t	Комоди
39143123-4	Bedside tables	7	t	Нічні столики
39143200-8	Dining-room furniture	5	f	Меблі для їдальні
39143210-1	Dining tables	6	t	Столи для їдальні
39143300-9	Living-room furniture	5	f	Меблі для вітальні
39143310-2	Coffee tables	6	t	Журнальні столики
39144000-3	Bathroom furniture	4	t	Меблі для ванної кімнати
39145000-0	Wine cellar fixtures	4	t	Інвентар для винних льохів
39150000-8	Miscellaneous furniture and equipment	3	f	Меблі та приспособи різні
39151000-5	Miscellaneous furniture	4	f	Меблі різні
39151100-6	Racking	5	t	Стелажі
39151200-7	Work benches	5	t	Верстаки
39151300-8	Modular furniture	5	t	Модульні меблі
39152000-2	Mobile bookshelves	4	t	Пересувні книжкові полиці
39153000-9	Conference-room furniture	4	f	Меблі для конференц-зал
39153100-0	Bookstands	5	t	Книжкові стенди
39154000-6	Exhibition equipment	4	f	Виставкове обладнання
39154100-7	Exhibition stands	5	t	Виставкові стенди
39155000-3	Library furniture	4	f	Бібліотечні меблі
39155100-4	Library equipment	5	t	Бібліотечне обладнання
39156000-0	Lounge and reception-area furniture	4	t	Меблі для кімнат очікування і приймалень
39161000-8	Kindergarten furniture	4	t	Меблі для дитячого садка
39162000-5	Educational equipment	4	f	Приладдя для навчальних закладів
39162100-6	Teaching equipment	5	f	Навчальне обладнання
39162110-9	Teaching supplies	6	t	Навчальне приладдя
39162200-7	Training aids and devices	5	t	Допоміжне навчальне приладдя та пристрої
39170000-4	Shop furniture	3	f	Магазинні меблі
39171000-1	Display cases	4	t	Вітрини
39172000-8	Counters	4	f	Прилавки
39172100-9	Servery counters	5	t	Прилавки обслуговування
39173000-5	Storage units	4	t	Складські меблі
39174000-2	Shop signs	4	t	Вивіски
39180000-7	Laboratory furniture	3	f	Лабораторні меблі
39181000-4	Laboratory benching	4	t	Лабораторні столи
39190000-0	Wallpaper and other coverings	3	f	Шпалери та інші настінні покриття
39191000-7	Paper- or paperboard-based wall coverings	4	f	Настінні покриття на паперовій або картонній основі
39191100-8	Wallpaper	5	t	Шпалери
39192000-4	Textile wall coverings	4	t	Настінні покриття на текстильній основі
39193000-1	Paper- or paperboard-based floor coverings	4	t	Підлогові покриття на паперовій або картонній основі
39200000-4	Furnishing	2	f	Меблева фурнітура
39220000-0	Kitchen equipment, household and domestic items and catering supplies	3	f	Кухонне приладдя, товари для дому та господарства і приладдя для закладів громадського харчування
39221000-7	Kitchen equipment	4	f	Кухонне приладдя
39221100-8	Kitchenware	5	f	Посуд
39221110-1	Crockery	6	t	Посуд
39221120-4	Cups and glasses	6	f	Чашки та склянки
39221121-1	Cups	7	t	Чашки
39221122-8	Cuplets	7	t	Чашки одноразового використання
39221123-5	Drinking glasses	7	t	Стакани
39221130-7	Food containers	6	t	Контейнери для харчових продуктів
39221140-0	Water canteens	6	t	Фляги для води
39221150-3	Vacuum flasks	6	t	Термоси
39221160-6	Trays	6	t	Таці
39221170-9	Drying racks	6	t	Сушарки для посуду
39221180-2	Cooking utensils	6	t	Кухонне начиння
39221190-5	Plate racks	6	t	Стелажі для тарілок
39221200-9	Tableware	5	f	Столовий посуд
39221210-2	Plates	6	t	Тарілки
39221220-5	Dishes	6	t	Блюда
39221230-8	Saucers	6	t	Блюдця
39221240-1	Bowls	6	t	Миски
39221250-4	Decanters	6	t	Графини
39221260-7	Mess tins	6	t	Казанки
39222000-4	Catering supplies	4	f	Приладдя для закладів громадського харчування
39222100-5	Disposable catering supplies	5	f	Приладдя одноразового використання для закладів громадського харчування
39222110-8	Disposable cutlery and plates	6	t	Столові прибори і тарілки одноразового використання
39222120-1	Disposable cups	6	t	Стакани одноразового використання
39222200-6	Food trays	5	t	Таці
39223000-1	Spoons, forks	4	f	Ложки, виделки
39223100-2	Spoons	5	t	Ложки
39223200-3	Forks	5	t	Виделки
39224000-8	Brooms and brushes and other articles of various types	4	f	Мітли, щітки та інше господарське приладдя
39224100-9	Brooms	5	t	Мітли
39224200-0	Brushes	5	f	Щітки
39224210-3	Painters' brushes	6	t	Пензлі для фарбування
39224300-1	Brooms and brushes and other articles for household cleaning	5	f	Мітли, щітки та інше прибиральне приладдя
39224310-4	Toilet brushes	6	t	Туалетні йоржики
39224320-7	Sponges	6	t	Губки
39224330-0	Buckets	6	t	Відра
39224340-3	Bins	6	t	Відра для сміття
39224350-6	Dustpans	6	t	Совки
39225000-5	Lighters, articles of combustible materials, pyrotechnics, matches and liquid or liquefied gas fuels	4	f	Запальнички, вироби із горючих матеріалів, піротехнічні вироби, сірники, рідкий та скраплений газ
39225100-6	Cigarette lighters	5	t	Запальнички для сигарет
39225200-7	Pyrophoric alloys	5	t	Пірофорні сплави
39225300-8	Matches	5	t	Сірники
39225400-9	Gas fuels for lighters	5	t	Газ для заправлення запальничок
39225500-0	Pyrotechnics	5	t	Піротехнічні вироби
39225600-1	Candles	5	t	Свічки
39225700-2	Bottles, jars and phials	5	f	Пляшки, банки та флакони
39225710-5	Bottles	6	t	Пляшки
39225720-8	Jars	6	t	Банки
39225730-1	Phials	6	t	Флакони
39226000-2	Carboys, bottle cases, flasks and bobbins	4	f	Бутлі, футляри для пляшок, фляги та котушки
39226100-3	Bottle cases	5	t	Футляри для пляшок
39226200-4	Carboys and flasks	5	f	Бутлі та фляги
39226210-7	Carboys	6	t	Бутлі
39226220-0	Flasks	6	t	Фляги
39226300-5	Spools or bobbins	5	t	Шпульки чи котушки
39312200-4	Canteen equipment	5	t	Обладнання для їдалень
39227000-9	Sewing and knitting needles, and thimbles	4	f	Швейні голки, в’язальні спиці та наперстки
39227100-0	Sewing needles or knitting needles	5	f	Швейні голки чи в’язальні спиці
39227110-3	Sewing needles	6	t	Швейні голки
39227120-6	Knitting needles	6	t	В’язальні спиці
39227200-1	Thimbles	5	t	Наперстки
39230000-3	Special-purpose product	3	f	Вироби спеціального призначення
39234000-1	Compost boxes	4	t	Ящики для компосту
39235000-8	Tokens	4	t	Жетони
39236000-5	Spray booths	4	t	Фарбувальні кабіни
39237000-2	Snow poles	4	t	Снігомірні рейки
39240000-6	Cutlery	3	f	Різальні інструменти
39241000-3	Knives and scissors	4	f	Ножі та ножиці
39241100-4	Knives	5	f	Ножі
39241110-7	Table knives	6	t	Столові ножі
39241120-0	Cooks' knives	6	t	Кухонні ножі
39241130-3	Utility knives	6	t	Багатофункціональні ножі
39241200-5	Scissors	5	t	Ножиці
39254000-7	Horology	4	f	Пристрої для вимірювання часу
39254100-8	Clocks	5	f	Годинники
39254110-1	Alarm clocks	6	t	Будильники
39254120-4	Wall clocks	6	t	Настінні годинники
39254130-7	Glass for clocks	6	t	Годинникове скло
39260000-2	Delivery trays and desk equipment	3	f	Секційні лотки та канцелярське приладдя
39261000-9	Delivery trays	4	t	Секційні лотки
39263000-3	Desk equipment	4	f	Канцелярське приладдя
39263100-4	Desk sets	5	t	Письмові набори
39264000-0	Fittings for loose-leaf binders or files	4	t	Механізми швидкозшивання
39265000-7	Hooks and eyes	4	t	Гачки та петлі
39270000-5	Religious articles	3	t	Вироби релігійного призначення
39290000-1	Miscellaneous furnishing	3	f	Фурнітура різна
39291000-8	Laundry supplies	4	t	Пральне приладдя
39292000-5	School slates or boards with writing or drawing surfaces or instruments	4	f	Шкільні грифельні чи інші дошки для писання чи малювання або приладдя до них
39292100-6	Blackboards	5	f	Класні дошки
39292110-9	Erasers for blackboards	6	t	Губки для витирання дощок
39292200-7	Writing slates	5	t	Грифельні дошки
39292300-8	Drawing instruments	5	t	Креслярські інструменти
39292400-9	Writing instruments	5	t	Письмове приладдя
39292500-0	Rulers	5	t	Лінійки
39293000-2	Artificial products	4	f	Штучні вироби
39293100-3	Artificial fruit	5	t	Штучні фрукти
39293200-4	Artificial flowers	5	t	Штучні квіти
39293300-5	Artificial grass	5	t	Штучна трава
39293400-6	Artificial lawn	5	t	Штучні газони
39293500-7	Imitation jewellery	5	t	Біжутерія
39294000-9	Apparatus and equipment designed for demonstrational purposes	4	f	Демонстраційні апаратура та обладнання
39294100-0	Information and promotion products	5	t	Інформаційна та рекламна продукція
39295000-6	Umbrellas and sunshades; walking sticks and seat sticks	4	f	Парасолі від дощу та сонця; ціпки та складані стільці-ціпки
39295100-7	Sunshades	5	t	Парасолі від сонця
39295200-8	Umbrellas	5	t	Парасолі від дощу
39295300-9	Seat sticks	5	t	Складані стільці-ціпки
39295400-0	Walking sticks	5	t	Ціпки
39295500-1	Parts, trimmings and accessories of umbrellas, sunshades, walking sticks and similar articles	5	t	Деталі, оздоблення та аксесуари до парасоль від сонця та дощу, ціпків і подібних виробів
39296000-3	Funeral supplies	4	f	Похоронне приладдя
39296100-4	Coffins	5	t	Труни
39297000-0	Barracks supplies	4	t	Казармений інвентар
39298000-7	Statuettes, ornaments; photograph or picture frames, and mirrors	4	f	Статуетки, оздоби; рамки для фотографій і картин та дзеркала
39298100-8	Photograph frames	5	t	Рамки для фотографій
39298200-9	Picture frames	5	t	Рамки для картин
39298300-0	Flower bowls	5	t	Вази для квітів
39298400-1	Statuettes	5	t	Статуетки
39298500-2	Ornaments	5	t	Оздоби
39298600-3	Globes	5	t	Глобуси
39298700-4	Trophies	5	t	Нагороди
39298800-5	Aquariums	5	t	Акваріуми
39298900-6	Miscellaneous decoration items	5	f	Декоративні вироби різні
39298910-9	Christmas tree	6	t	Новорічні ялинки
39299000-4	Glassware	4	f	Скляні вироби
39299100-5	Glass ampoules	5	t	Скляні ампули
39299200-6	Safety glass	5	t	Безпечне скло
39299300-7	Glass mirrors	5	t	Скляні дзеркала
39300000-5	Miscellaneous equipment	2	f	Обладнання різне
39310000-8	Catering equipment	3	f	Обладнання для закладів громадського харчування
39311000-5	Light catering equipment	4	t	Дрібне приладдя для закладів громадського харчування
39312000-2	Food-preparation equipment	4	f	Кулінарне обладнання
39312100-3	Meat slicers	5	t	М’ясорізки
39313000-9	Hotel equipment	4	t	Готельне обладнання
39314000-6	Industrial kitchen equipment	4	t	Промислове кухонне обладнання
39315000-3	Restaurant equipment	4	t	Ресторанне обладнання
39330000-4	Disinfection equipment	3	t	Дезінфекційне обладнання
39340000-7	Gas network equipment	3	f	Обладнання для газових мереж
39341000-4	Gas pressure equipment	4	t	Газове обладнання для роботи під високим тиском
39350000-0	Sewerage works equipment	3	t	Каналізаційне обладнання
39360000-3	Sealing equipment	3	t	Пломбувальне обладнання
39370000-6	Water installations	3	t	Водопровідне обладнання
39500000-7	Textile articles	2	f	Текстильні вироби
39510000-0	Textile household articles	3	f	Вироби домашнього текстилю
39511000-7	Blankets and travelling rugs	4	f	Ковдри та пледи
39511100-8	Blankets	5	t	Ковдри
39511200-9	Travelling rugs	5	t	Пледи
39512000-4	Bed linen	4	f	Постільна білизна
39512100-5	Sheets	5	t	Простирадла
39512200-6	Duvet covers	5	t	Підодіяльники
39512300-7	Mattress covers	5	t	Наматрацники
39512400-8	Eiderdowns	5	t	Пухові ковдри
39512500-9	Pillowcases	5	t	Наволочки
39512600-0	Bolstercases	5	t	Наволочки для подушок-валиків
39513000-1	Table linen	4	f	Столова білизна
39513100-2	Tablecloths	5	t	Скатертини
39513200-3	Table napkins	5	t	Столові серветки
39514000-8	Toilet and kitchen linen	4	f	Туалетна та кухонна білизна
39514100-9	Towels	5	t	Рушники
39514200-0	Tea towels	5	t	Кухонні рушники
39514300-1	Roller towels	5	t	Паперові рулонні рушники
39514400-2	Automatic towel dispensers	5	t	Диспенсери для рушників
39514500-3	Face cloths	5	t	Рушники для обличчя
39515000-5	Curtains, drapes, valances and textile blinds	4	f	Штори, портьєри, кухонні штори та тканинні жалюзі
39515100-6	Curtains	5	f	Штори
39515110-9	Smoke curtains	6	t	Димозахисні штори
39515200-7	Drapes	5	t	Портьєри
39515300-8	Valances	5	t	Кухонні штори
39515400-9	Blinds	5	f	Жалюзі
39515410-2	Interior blinds	6	t	Внутрішні жалюзі
39515420-5	Textile blinds	6	t	Тканинні жалюзі
39515430-8	Venetian blinds	6	t	Горизонтальні жалюзі
39515440-1	Vertical blinds	6	t	Вертикальні жалюзі
39516000-2	Furnishing articles	4	f	Предмети декору
39516100-3	Soft furnishings	5	f	Текстильні предмети декору
39516110-6	Cushions	6	t	Диванні подушки
39516120-9	Pillows	6	t	Подушки
39518000-6	Hospital linen	4	f	Лікарняна білизна
39518100-7	Operating-theatre drapes	5	t	Штори для операційної зали
39518200-8	Operating-theatre sheets	5	t	Простирадла для операційної зали
39520000-3	Made-up textile articles	3	f	Готові текстильні вироби
39522000-7	Tarpaulins, sails for boats, sailboards or land craft, awnings, sunblinds, tents and camping goods	4	f	Брезентові вироби, вітрила для човнів, дощок для віндсерфінгу чи десантної техніки, тенти, маркізи, намети та спорядження для кемпінгу
39522100-8	Tarpaulins, awnings and sunblinds	5	f	Брезентові вироби, тенти та маркізи
39522110-1	Tarpaulins	6	t	Брезентові вироби
39522120-4	Awnings	6	t	Тенти
39522130-7	Sunblinds	6	t	Маркізи
39522200-9	Camouflage covers	5	t	Маскувальні сітки
39522400-1	Sails	5	t	Вітрила
39522500-2	Textile camping goods	5	f	Текстильні вироби для кемпінгу
39522510-5	Pneumatic mattresses	6	t	Надувні матраци
39522520-8	Camp beds	6	t	Похідні ліжка
39522530-1	Tents	6	t	Намети
39522540-4	Sleeping bags	6	f	Спальні мішки
39522541-1	Sleeping bags filled with feathers or down	7	t	Пухові чи пір’яні спальні мішки
39523000-4	Parachutes	4	f	Парашути
39523100-5	Dirigible parachutes	5	t	Керовані парашути
39523200-6	Rotochutes	5	t	Ротошути
39525000-8	Miscellaneous manufactured textile articles	4	f	Готові текстильні вироби різні
39525100-9	Dusters	5	t	Ганчірки для витирання пилу
39525200-0	Filter elements of cloth	5	t	Тканинні фільтрувальні елементи
39525300-1	Life jackets	5	t	Рятувальні жилети
39525400-2	Fire blankets	5	t	Пожежні покривала
39525500-3	Mosquito nets	5	t	Москітні сітки
39525600-4	Dishcloths	5	t	Посудні рушники
39525700-5	Life belts	5	t	Рятувальні пояси
39525800-6	Cleaning cloths	5	f	Ганчірки для прибирання
39525810-9	Polishing cloths	6	t	Полірувальне сукно
39530000-6	Carpets, mats and rugs	3	f	Килимові покриття, килимки та килими
39531000-3	Carpets	4	f	Килимові покриття
39531100-4	Knotted carpeting	5	t	В’язані килими
39531200-5	Woven carpeting	5	t	Ткані килими
39531300-6	Tufted carpeting	5	f	Тафтингові килими
39531310-9	Carpet tiles	6	t	Килимова плитка
39531400-7	Carpeting	5	t	Ковроліни
39532000-0	Mats	4	t	Килимки
39533000-7	Rugs	4	t	Килими
39534000-4	Industrial carpeting	4	t	Промислові ковроліни
39540000-9	Miscellaneous cordage, rope, twine and netting	3	f	Вироби різні з канату, мотузки, шпагату та сітки
39541000-6	Cordage, rope, twine and netting	4	f	Канати, мотузки, шпагати та сітки
39541100-7	Twine, cordage and rope	5	f	Шпагати, канати та мотузки
39541110-0	Rope	6	t	Мотузки
39541120-3	Cordage	6	t	Канати
39541130-6	String	6	t	Шнури
39541140-9	Twine	6	t	Шпагати
39541200-8	Textile nets	5	f	Текстильні сітки
39541210-1	Knotted nets	6	t	В’язані сітки
39541220-4	Slings	6	t	Стропи
39542000-3	Rags	4	t	Рядно
39550000-2	Non-woven articles	3	t	Вироби з нетканих матеріалів
39560000-5	Miscellaneous textile articles	3	f	Текстильні вироби різні
39561000-2	Tulle, lace, narrow-woven fabrics, trimmings and embroidery	4	f	Тюль, мереживо, вузькі тканини, оздоблювальні матеріали та вишиті вироби
39561100-3	Narrow-woven fabrics; trimmings	5	f	Вузькі тканини; оздоблювальні матеріали
39561110-6	Ribbon	6	t	Стрічки
39561120-9	Textile tape	6	t	Текстильна тасьма
39561130-2	Textile labels and badges	6	f	Текстильні етикетки і нашивки
39561131-9	Textile labels	7	t	Текстильні етикетки
39561132-6	Textile badges	7	t	Текстильні нашивки
39561133-3	Insignia	7	t	Знаки розрізнення
39561140-5	Ornamental trimmings	6	f	Декоративні оздоблювальні матеріали
39561141-2	Braids	7	t	Плетена тасьма
39561142-9	Epaulettes	7	t	Еполети
39561200-4	Net fabrics	5	t	Ажурні тканини
39562000-9	Felt	4	t	Фетр
39563000-6	Textile wadding, yarns, fabrics and articles for technical uses	4	f	Текстильні набивки, пряжі, тканини та вироби технічного призначення
39563100-7	Textile wadding	5	t	Текстильні набивки
39563200-8	Metallised yarn	5	t	Металізовані пряжі
39563300-9	Woven fabrics of metal thread	5	t	Тканини з металізованої пряжі
39563400-0	Impregnated, coated or covered textile fabrics	5	t	Текстильні матеріали просочені чи із захисним покриттям
39563500-1	Textile articles for technical uses	5	f	Текстильні вироби технічного призначення
39563510-4	Textile hosepiping	6	t	Шланги з текстильних матеріалів
39563520-7	Textile conveyor belts	6	t	Конвеєрні стрічки з текстильних матеріалів
39563530-0	Ventilation ducting	6	t	Вентиляційні трубопроводи
39563600-2	Quilted textile products	5	t	Стьобані текстильні вироби
39700000-9	Domestic appliances	2	f	Побутова техніка
39710000-2	Electrical domestic appliances	3	f	Електричні побутові прилади
39711000-9	Electrical domestic appliances for use with foodstuffs	4	f	Електричні побутові прилади для обробки продуктів харчування
39711100-0	Refrigerators and freezers	5	f	Холодильники та морозильні камери
39711110-3	Refrigerator-freezers	6	t	Холодильники з морозильною камерою
39711120-6	Freezers	6	f	Морозильні камери
39711121-3	Chest-type freezers	7	t	Морозильні контейнери
39711122-0	Household freezers	7	t	Побутові морозильні камери
39711123-7	Upright-type freezers	7	t	Морозильні шафи
39711124-4	Industrial freezers	7	t	Промислові морозильні камери
39711130-9	Refrigerators	6	t	Холодильники
39711200-1	Food processors	5	f	Кухонні комбайни
39711210-4	Food blenders	6	f	Блендери
39711211-1	Food mixers	7	t	Міксери
39711300-2	Electrothermic appliances	5	f	Електронагрівальні прилади
39711310-5	Electric coffee makers	6	t	Електричні кавоварки
39711320-8	Electric tea makers	6	t	Електричні чаєварки
39711330-1	Electric toasters	6	t	Електричні тостери
39711340-4	Plate warmers	6	t	Підігрівачі тарілок
39711350-7	Waffle irons	6	t	Вафельниці
39711360-0	Ovens	6	f	Печі
39711361-7	Electric ovens	7	t	Електричні печі
39711362-4	Microwave ovens	7	t	Мікрохвильові печі
39711400-3	Roasters, hobs, hotplates and boiling rings	5	f	Жаровні, варильні поверхні, конфорки та електричні плити
39711410-6	Roasters	6	t	Жаровні
39711420-9	Hobs	6	t	Варильні поверхні
39711430-2	Hotplates	6	t	Конфорки
39711440-5	Boiling rings	6	t	Електричні плити
39711500-4	Can openers	5	t	Консервні ножі
39712000-6	Electrical domestic appliances for use with the human body	4	f	Електричні побутові прилади для догляду за тілом
39712100-7	Hair clippers	5	t	Машинки для стрижки волосся
39712200-8	Hairdressing appliances	5	f	Перукарські прилади
39712210-1	Hair dryers	6	t	Фени
39712300-9	Hand-drying apparatus	5	t	Сушарки для рук
39713000-3	Electrical domestic appliances for cleaning; smoothing irons	4	f	Електричні побутові прилади для прибирання; праски
39713100-4	Dishwashing machines	5	t	Посудомийні машини
39713200-5	Clothes-washing and drying machines	5	f	Пральні та сушильні машини
39713210-8	Washer/dryers	6	f	Пральні машини з функцією сушки
39713211-5	Drying and pressing unit	7	t	Сушильні та прасувальні прилади
39713300-6	Rubbish compactors	5	t	Ущільнювачі побутового сміття
39713400-7	Floor-maintenance machines	5	f	Машини для догляду за підлогою
39713410-0	Floor-cleaning machines	6	t	Підлогомийні машини
39713420-3	Floor polishers	6	t	Підлогонатирачі
39713430-6	Vacuum cleaners	6	f	Пилососи
39713431-3	Accessories for vacuum cleaners	7	t	Приладдя до пилососів
39713500-8	Electric irons	5	f	Електричні праски
39713510-1	Steam irons	6	t	Парові праски
39714000-0	Ventilating or recycling hoods	4	f	Кухонні витяжки чи очищувачі повітря
39714100-1	Ventilators	5	f	Вентилятори
39714110-4	Extraction ventilators	6	t	Витяжні вентилятори
39715000-7	Water heaters and heating for buildings; plumbing equipment	4	f	Водонагрівачі та центральні системи опалення; санітарна техніка
39715100-8	Electric instantaneous or storage water heaters and immersion heaters	5	t	Проточні чи акумуляційні водонагрівачі та занурювані електричні нагрівачі
39715200-9	Heating equipment	5	f	Опалювальне обладнання
39715210-2	Central-heating equipment	6	t	Обладнання для центрального опалення
39715220-5	Electric heating resistors	6	t	Резистори для електронагрівальних приладів
39715230-8	Electric soil-heating apparatus	6	t	Електроприлади для обігріву ґрунту
39715240-1	Electric space-heating apparatus	6	t	Електроприлади для обігріву приміщень
39715300-0	Plumbing equipment	5	t	Санітарно-технічне обладнання
39716000-4	Parts of electrical domestic appliances	4	t	Частини побутових електричних приладів
39717000-1	Fans and air-conditioning appliances	4	f	Вентилятори та кондиціонери
39717100-2	Fans	5	t	Вентилятори
39717200-3	Air-conditioning appliances	5	t	Кондиціонери
39720000-5	Non-electric domestic appliances	3	f	Неелектричні побутові прилади
39721000-2	Domestic cooking or heating equipment	4	f	Побутові прилади для готування та підігрівання їжі
39721100-3	Domestic cooking appliances	5	t	Побутові прилади для готування їжі
39721200-4	Gas refrigerators	5	t	Газові холодильники
39721300-5	Non-electric air heaters or hot air-distributors	5	f	Неелектричні повітронагрівачі чи розподільники гарячого повітря
39721310-8	Air heaters	6	t	Повітронагрівачі
39721320-1	Air dryers	6	f	Осушувачі повітря
39721321-8	Compressed-air dryers	7	t	Осушувачі стисненого повітря
39721400-6	Instantaneous or storage non-electric water heaters	5	f	Неелектричні проточні чи акумуляційні водонагрівачі
39721410-9	Gas appliances	6	f	Газові прилади
39721411-6	Gas heaters	7	t	Газові нагрівачі
39722000-9	Parts of stoves, cookers, plate warmers and domestic appliances	4	f	Частини печей, кухонних плит, підігрівачів тарілок та інших побутових приладів
39722100-0	Parts of stoves	5	t	Частини печей
39722200-1	Parts of cookers	5	t	Частини кухонних плит
39722300-2	Parts of plate warmers	5	t	Частини підігрівачів тарілок
39800000-0	Cleaning and polishing products	2	f	Продукція для чищення та полірування
39810000-3	Odoriferous preparations and waxes	3	f	Ароматизатори та воски
39811000-0	Preparations for perfuming or deodorising rooms	4	f	Засоби для ароматизації та дезодорування приміщень
39811100-1	Air freshener	5	f	Освіжувачі повітря
39811110-4	Air freshener dispensers	6	t	Диспенсери для освіження повітря
39811200-2	Air sanitisers	5	t	Засоби дезінфекції повітря
39811300-3	Deodorisers	5	t	Дезодоратори
39812000-7	Polishes and creams	4	f	Поліролі та креми
39812100-8	Floor polishes	5	t	Поліролі для підлоги
39812200-9	Shoe polishes	5	t	Засоби для чищення взуття
39812300-0	Polishing waxes	5	t	Полірувальні воски
39812400-1	Sweeping compounds	5	t	Пиловловлювальні засоби для підмітання підлоги
39812500-2	Sealants	5	t	Герметики
39813000-4	Scouring pastes and powders	4	t	Абразивні пасти і порошки для чищення
39820000-6	Organic surface-active agents	3	f	Органічні поверхнево активні речовини
39821000-3	Ammonia cleaners	4	t	Аміачні очищувачі
39822000-0	Caustic cleaners	4	t	Каустичні очищувачі
39830000-9	Cleaning products	3	f	Продукція для чищення
39831000-6	Washing preparations	4	f	Засоби для прання і миття
39831100-7	Oil dispersant	5	t	Диспергатори
39831200-8	Detergents	5	f	Мийні засоби
39831210-1	Dishwasher detergents	6	t	Мийні засоби для посудомийних машин
39831220-4	Degreasing agents	6	t	Знежирювальні засоби
39831230-7	Grease digesters	6	t	Розчинники жирів
39831240-0	Cleaning compounds	6	t	Засоби для чищення
39831250-3	Rinsing solutions	6	t	Промивальні розчини
39831300-9	Floor cleaners	5	t	Засоби для миття підлоги
39831400-0	Screen cleaners	5	t	Засоби для очищення екранів
39831500-1	Automotive cleaners	5	t	Засоби для чищення і миття автомобілів
39831600-2	Toilet cleaners	5	t	Засоби для чищення туалету
39831700-3	Automatic soap dispensers	5	t	Автоматичні дозатори рідкого мила
39832000-3	Dishwashing products	4	f	Засоби для миття посуду
39832100-4	Dishwashing powder	5	t	Порошки для миття посуду
39833000-0	Anti-dust products	4	t	Протипилові засоби
39834000-7	Jewellery cleaning solutions	4	t	Розчини для чищення ювелірних виробів
41000000-9	Collected and purified water	1	f	Зібрана дощова та очищена вода
41100000-0	Natural water	2	f	Необроблена вода
41110000-3	Drinking water	3	t	Питна вода
41120000-6	Non-drinking water	3	t	Вода для технічних потреб
42000000-6	Industrial machinery	1	f	Промислова техніка
42100000-0	Machinery for the production and use of mechanical power	2	f	Машини, що виробляють та використовують механічну енергію
42110000-3	Turbines and motors	3	f	Турбіни та мотори
42111000-0	Motors	4	f	Мотори
42111100-1	Outboard motors for marine propulsion	5	t	Підвісні мотори для до морських суден
42112000-7	Turbine installations	4	f	Турбінні установки
42112100-8	Steam turbines	5	t	Парові турбіни
42112200-9	Hydraulic turbines	5	f	Гідравлічні турбіни
42112210-2	Water wheels	6	t	Водяні колеса
42112300-0	Gas turbines	5	t	Газові турбіни
42112400-1	Turbine equipment	5	f	Турбінне обладнання
42112410-4	Turbine instruments	6	t	Турбінні прилади
42113000-4	Parts of turbines	4	f	Частини турбін
42113100-5	Parts of steam turbines	5	f	Частини парових турбін
42113110-8	Base plates	6	t	Опорні плити
42113120-1	Casings	6	t	Корпуси
42113130-4	Condenser air-cooling systems	6	t	Конденсувальні системи охолодження повітря
42113150-0	Lubricating oil systems	6	t	Мастильні системи
42113160-3	Moisture separators	6	f	Вологовідділювачі
42113161-0	Dehumidifiers	7	t	Осушувачі
42113170-6	Rotary equipment	6	f	Роторне обладнання
42113171-3	Rotors	7	t	Ротори
42113172-0	Blades	7	t	Лопаті
42113190-2	Turning gear	6	t	Поворотні механізми
42113200-6	Parts of hydraulic turbines	5	t	Частини гідравлічних турбін
42113300-7	Parts of gas turbines	5	f	Частини газових турбін
42113310-0	Air-inlet systems	6	t	Системи впуску повітря
42113320-3	Gas-injection module	6	t	Газонагнітальні модулі
42113390-4	Fuel-gas systems	6	t	Газопаливні системи
42113400-8	Parts of water wheels	5	t	Частини водяних коліс
42120000-6	Pumps and compressors	3	f	Насоси та компресори
42121000-3	Hydraulic or pneumatic power engines and motors	4	f	Гідравлічні чи пневматичні двигуни та мотори
42121100-4	Hydraulic or pneumatic cylinders	5	t	Гідравлічні чи пневматичні циліндри
42121200-5	Hydraulic power engines	5	t	Гідравлічні двигуни
42121300-6	Pneumatic power engines	5	t	Пневматичні двигуни
42121400-7	Hydraulic power motors	5	t	Гідравлічні мотори
42121500-8	Pneumatic power motors	5	t	Пневматичні мотори
42122000-0	Pumps	4	f	Насоси
42122100-1	Pumps for liquids	5	f	Насоси для рідин
42122110-4	Pumps for firefighting	6	t	Насоси для пожежогасіння
42122120-7	Helicopter refuelling package	6	t	Паливозаправні пристрої для вертольотів
42122130-0	Water pumps	6	t	Водяні насоси
42122160-9	Cooling pumps	6	f	Охолоджувальні насоси
42122161-6	Cooling-water pumps	7	t	Насоси водяного охолодження
42122170-2	Lubricating pumps	6	t	Мастильні насоси
42122180-5	Fuel pumps	6	t	Паливні насоси
42122190-8	Concrete pumps	6	t	Бетонні насоси
42122200-2	Reciprocating positive-displacement pumps for liquids	5	f	Поршневі нагнітачі для рідин
42122210-5	Hydraulic power packs	6	t	Гідравлічні агрегати
42122220-8	Sewage pumps	6	t	Каналізаційні насоси
42122230-1	Dosing pumps	6	t	Дозувальні насоси
42122300-3	Pressure boosters for liquids	5	t	Гідравлічні мультиплікатори
42122400-4	Centrifugal pumps and liquid elevators	5	f	Відцентрові насоси та підіймачі рідин
42122410-7	Pumps for medical use	6	f	Насоси медичного призначення
42122411-4	Nutritional pumps	7	t	Насоси для годування
42122419-0	Perfusion pumps	7	t	Перфузійні насоси
42122420-0	Liquid elevators	6	t	Підіймачі рідин
42122430-3	Centrifugal pumps	6	t	Відцентрові насоси
42122440-6	Rotary pumps	6	t	Роторні насоси
42122450-9	Vacuum pumps	6	t	Вакуумні насоси
42122460-2	Air pumps	6	t	Повітряні насоси
42122480-8	Impeller pumps	6	t	Лопатеві насоси
42122500-5	Laboratory pumps and accessories	5	f	Лабораторні насоси та приладдя до них
42122510-8	Peristaltic pumps	6	t	Перистальтичні насоси
42123000-7	Compressors	4	f	Компресори
42123100-8	Gas compressors	5	t	Газові компресори
42123200-9	Rotary compressors	5	t	Роторні компресори
42123300-0	Compressors for refrigerating equipment	5	t	Компресори до холодильного устаткування
42123400-1	Air compressors	5	f	Повітряні компресори
42123410-4	Mounted air compressors	6	t	Вбудовані повітряні компресори
42123500-2	Turbo-compressors	5	t	Турбокомпресори
42123600-3	Reciprocating displacement compressors	5	f	Поршневі об’ємні компресори
42123610-6	Compressed-air package	6	t	Компресорні блоки
42123700-4	Centrifugal compressors	5	t	Відцентрові компресори
42123800-5	Compressors for use in civil aircraft	5	t	Компресори для пасажирських літаків
42124000-4	Parts of pumps, compressors, engines or motors	4	f	Частини насосів, компресорів, двигунів або моторів
42124100-5	Parts of engines or motors	5	f	Частини двигунів або моторів
42124130-4	Parts of pneumatic engines	6	t	Частини пневматичних двигунів
42124150-0	Parts of hydraulic power engines or motors	6	t	Частини гідравлічних двигунів або моторів
42124170-6	Parts of reaction engines	6	t	Частини реактивних двигунів
42124200-6	Parts of pumps or liquid elevators	5	f	Частини насосів і підіймачів рідин
42124210-9	Parts of fuel, hand and concrete pumps	6	f	Частини паливних, ручних і бетонних насосів
42124211-6	Parts of fuel pumps	7	t	Частини паливних насосів
42124212-3	Parts of hand pumps	7	t	Частини ручних насосів
42124213-0	Parts of concrete pumps	7	t	Частини бетонних насосів
42124220-2	Parts of reciprocating positive-displacement pumps	6	f	Частини поршневих нагнітачів
42124221-9	Parts of hydraulic power packs	7	t	Частини гідравлічних агрегатів
42124222-6	Parts of dosing pumps	7	t	Частини дозувальних насосів
42124230-5	Parts of rotary positive-displacement pumps	6	t	Частини роторних нагнітачів
42124290-3	Parts of centrifugal pumps	6	t	Частини відцентрових насосів
42124300-7	Parts of air or vacuum pumps, of air or gas compressors	5	f	Частини повітряних чи вакуумних насосів, повітряних чи газових компресорів
42124310-0	Parts of air pumps	6	t	Частини повітряних насосів
42124320-3	Parts of vacuum pumps	6	t	Частини вакуумних насосів
42124330-6	Parts of air compressors	6	t	Частини повітряних компресорів
42124340-9	Parts of gas compressors	6	t	Частини газових компресорів
42130000-9	Taps, cocks, valves and similar appliances	3	f	Арматура трубопровідна: крани, вентилі, клапани та подібні пристрої
42131000-6	Taps, cocks and valves	4	f	Крани, вентилі та клапани
42131100-7	Valves defined by function	5	f	Арматура, що визначена за функціональними ознаками
42131110-0	Central-heating radiator valves	6	t	Клапани радіаторів центрального опалення
42131120-3	Sluice valves	6	t	Засувки шлюзові
42131130-6	Temperature regulators	6	t	Регулятори температури
42131140-9	Pressure-reducing, control, check or safety valves	6	f	Редукційні, регулювальні, оборотні та запобіжні клапани
42131141-6	Pressure-reducing valves	7	t	Редукційні клапани
42131142-3	Control valves	7	t	Регулювальні клапани
42131143-0	Flood-control valves	7	t	Клапани регулювання паводкових стоків
42131144-7	Process-control valves	7	t	Клапани регулювання процесів
42131145-4	Check valves	7	t	Оборотні клапани
42131146-1	Non-return valves	7	t	Однонаправлені клапани
42131147-8	Safety valves	7	t	Запобіжні клапани
42131148-5	Stop valves	7	t	Запірні клапани
42131150-2	Standpipe valves	6	t	Клапани стояків
42131160-5	Hydrants	6	t	Гідранти
42131170-8	Gas-cylinder outlet valves	6	t	Випускні вентилі газових балонів
42131200-8	Valves defined by construction	5	f	Арматура, що визначена за конструктивними ознаками
42131210-1	Knife valves	6	t	Ножові засувки
42131220-4	Penstock valves	6	t	Клапани напірних трубопроводів
42131230-7	Gate valves	6	t	Шиберні засувки
42131240-0	Globe valves	6	t	Вентилі зі сферичним запірним органом
42131250-3	Needle valves	6	t	Голчасті клапани
42131260-6	Ball valves	6	t	Кульові клапани
42131270-9	Plug valves	6	t	Конічні вентилі
42131280-2	Butterfly valves	6	t	Дискові поворотні клапани
42131290-5	Diaphragm valves	6	f	Мембранні клапани
42131291-2	Sliding valves	7	t	Золотникові клапани
42131292-9	Flap valves	7	t	Відкидні клапани
42131300-9	Christmas trees and other assemblies of valves	5	f	Фонтанна арматура та інші клапанні системи
42131310-2	Oilrig Christmas trees	6	t	Фонтанна арматура
42131320-5	Choke manifolds	6	t	Штуцерні маніфольди
42131390-6	Assemblies of valves	6	t	Клапанні системи
42131400-0	Sanitary taps, cocks	5	t	Крани та вентилі для санітарно-технічного обладнання
42132000-3	Parts of taps and valves	4	f	Частини кранів і вентилів
42132100-4	Valve actuators	5	f	Вентильні приводи
42132110-7	Electric valve actuators	6	t	Електричні вентильні приводи
42132120-0	Hydraulic valve actuators	6	t	Гідравлічні вентильні приводи
42132130-3	Pneumatic valve actuators	6	t	Пневматичні вентильні приводи
42132200-5	Tap parts	5	t	Частини кранів
42132300-6	Valve parts	5	t	Частини клапанів
42140000-2	Gears, gearing and driving elements	3	f	Зубчасті колеса, зубчасті передачі та приводні елементи
42141000-9	Plain gears, gearing and driving elements	4	f	Прямозубі циліндричні зубчасті колеса, зубчасті передачі та приводні елементи
42141100-0	Transmission, cam- and crank- shafts	5	f	Трансмісійні, розподільні та колінчасті вали
42141110-3	Transmission shafts	6	t	Трансмісійні вали
42141120-6	Camshafts	6	t	Розподільні вали
42141130-9	Crankshafts	6	t	Колінчасті вали
42141200-1	Bearing housings	5	t	Корпуси підшипників
42141300-2	Gears and gearing	5	t	Зубчасті колеса та зубчасті передачі
42141400-3	Flywheels and pulleys	5	f	Маховики та шківи
42141410-6	Winches	6	t	Лебідки
42141500-4	Clutches	5	t	Муфти зчеплення
42141600-5	Pulley blocks	5	t	Талі
42141700-6	Shaft couplings	5	t	З’єднувальні муфти для валів
42141800-7	Universal joints	5	t	Карданні шарніри
42142000-6	Parts of gearing and driving elements	4	f	Частини зубчастих передач і приводних елементів
42142100-7	Parts of gearing elements	5	t	Частини зубчастих передач
42142200-8	Parts of driving elements	5	t	Частини приводних елементів
42150000-5	Nuclear reactors and parts	3	f	Ядерні реактори та їх частини
42151000-2	Nuclear reactors	4	t	Ядерні реактори
42152000-9	Parts of nuclear reactors	4	f	Частини ядерних реакторів
42152100-0	Reactor-cooling systems	5	t	Системи охолодження реакторів
42152200-1	Parts of nuclear-reactor vessels	5	t	Деталі корпусів ядерних реакторів
42160000-8	Boiler installations	3	f	Котельні установки
42161000-5	Hot-water boilers	4	t	Водонагрівальні бойлери
42162000-2	Steam-generating boilers	4	t	Парові котли
42163000-9	Steam generators	4	t	Парогенератори
42164000-6	Auxiliary plant for use with boilers	4	t	Допоміжне обладнання для котлів
42165000-3	Steam condensers	4	t	Пароконденсатори
42200000-8	Machinery for food, beverage and tobacco processing and associated parts	2	f	Машини для обробки продуктів харчування, виробництва напоїв та обробки тютюну
42210000-1	Food, beverage and tobacco-processing machinery	3	f	Машини для обробки продуктів харчування, виробництва напоїв та обробки тютюну
42211000-8	Dairy machinery	4	f	Машини для виробництва молочної продукції
42211100-9	Centrifugal cream separators	5	t	Відцентрові сепаратори вершків
42212000-5	Machinery for processing cereals or dried vegetables	4	t	Машини для обробки злакових культур або сушених овочів
42213000-2	Machinery used in the manufacture of alcoholic or fruit beverages	4	t	Машини, що використовуються у виробництві алкогольних або фруктових напоїв
42214000-9	Cooking ovens, dryers for agricultural products and equipment for cooking or heating	4	f	Кухонні печі, сушарки для сільськогосподарських продуктів та обладнання для готування чи підігрівання їжі
42214100-0	Cooking ovens	5	f	Кухонні печі
42214110-3	Grills	6	t	Грилі
42214200-1	Dryers for agricultural products	5	t	Сушарки для сільськогосподарських продуктів
42215000-6	Machinery for the industrial preparation or manufacture of food or drink	4	f	Машини для промислової обробки чи виробництва продуктів харчування чи напоїв
42215100-7	Food-cutting machines	5	f	Машини для нарізання продуктів харчування
42215110-0	Bread-slicing machines	6	t	Хліборізки
42215120-3	Bacon slicers	6	t	Беконорізки
42215200-8	Food-processing machinery	5	t	Машини для обробки продуктів харчування
42215300-9	Pasta-making machines	5	t	Машини для виготовлення макаронних виробів
42216000-3	Machinery for processing tobacco	4	t	Машини для обробки тютюну
42220000-4	Parts of machinery for food, beverage and tobacco processing	3	f	Частини машин для обробки продуктів харчування, виробництва напоїв та обробки тютюну
42221000-1	Parts of machinery for food processing	4	f	Частини машин для обробки продуктів харчування
42221100-2	Parts of dairy machinery	5	f	Частини машин для виробництва молочної продукції
42221110-5	Parts of milking machines	6	t	Частини доїльних апаратів
42222000-8	Parts of machinery for beverage processing	4	t	Частини машин для виробництва напоїв
42223000-5	Parts of machinery for tobacco processing	4	t	Частини машин для обробки тютюну
42300000-9	Industrial or laboratory furnaces, incinerators and ovens	2	f	Промислові чи лабораторні, сміттєспалювальні та пекарські печі
42310000-2	Furnace burners	3	t	Пічні пальники
42320000-5	Waste incinerators	3	t	Сміттєспалювальні печі
42330000-8	Smelting furnaces	3	t	Плавильні печі
42340000-1	Non-domestic ovens	3	f	Печі непобутового призначення
42341000-8	Commercial ovens	4	t	Промислові печі
42350000-4	Cremators	3	t	Кремаційні печі
42390000-6	Parts of furnace burners, furnaces or ovens	3	t	Частини пальників, печей або пекарських печей
42400000-0	Lifting and handling equipment and parts	2	f	Підіймально-транспортувальне обладнання та його частини
42410000-3	Lifting and handling equipment	3	f	Підіймально-транспортувальне обладнання
42411000-0	Pulley tackle and hoists	4	t	Талі та підіймачі
42412000-7	Pithead winding gear, winches for underground use, and capstans	4	f	Шахтні лебідки, лебідки для підземних гірничих робіт і кабестани
42412100-8	Pithead winding gear and winches for use underground	5	f	Шахтові лебідки та лебідки для підземних гірничих робіт
42412110-1	Pithead winding gear	6	t	Шахтні лебідки
42412120-4	Winches for use underground	6	t	Лебідки для підземних гірничих робіт
42412200-9	Capstans	5	t	Кабестани
42413000-4	Jacks and vehicle hoists	4	f	Домкрати та автомобільні підіймачі
42413100-5	Built-in jacking systems	5	t	Інтегровані підіймальні системи
42413200-6	Hydraulic jacks	5	t	Гідравлічні домкрати
42413300-7	Pneumatic jacks	5	t	Пневматичні домкрати
42413400-8	Mechanical jacks	5	t	Механічні домкрати
42413500-9	Vehicle hoists	5	t	Автомобільні підіймачі
42414000-1	Cranes, mobile lifting frames and works trucks fitted with a crane	4	f	Підіймальні крани, пересувні підіймальні рами та автоманіпулятори, оснащені краном
42414100-2	Cranes	5	f	Підіймальні крани
42414110-5	Harbour cranes	6	t	Портові крани
42414120-8	Quayside cranes	6	t	Причальні крани
42414130-1	Stacker cranes	6	t	Крани-штабелери
42414140-4	Container cranes	6	t	Контейнерні крани
42414150-7	Tower cranes	6	t	Баштові крани
42414200-3	Overhead travelling cranes	5	f	Крани-балки
42414210-6	Travelling crane	6	t	Пересувні крани
42414220-9	Traversing bridge	6	t	Мостові крани
42414300-4	Portal jib cranes	5	f	Консольні крани
42414310-7	Mobile lifting frames	6	t	Пересувні підіймальні рами
42414320-0	Pedestal jib cranes	6	t	Консольні крани на платформі
42414400-5	Vehicle-mounted cranes	5	f	Автокрани
42414410-8	Cranes for trucks	6	t	Крани-маніпулятори для вантажних автомобілів
42414500-6	Bridge cranes	5	t	Крани мостового типу
42415000-8	Forklift trucks, works trucks, railway-station platforms tractors	4	f	Вилкові автонавантажувачі, автоманіпулятори, тягачі для обслуговування залізничних станцій
42415100-9	Lifting trucks	5	f	Автонавантажувачі
42415110-2	Forklift trucks	6	t	Вилкові автонавантажувачі
42415200-0	Works trucks	5	f	Автоманіпулятори
42415210-3	Works trucks fitted with handling equipment	6	t	Автоманіпулятори з навантажувально-розвантажувальним обладнанням
42415300-1	Railway-station platforms tractors	5	f	Тягачі для обслуговування залізничних станцій
42415310-4	Free-steered vehicles (FSVS)	6	t	Самохідні візки
42415320-7	Equipment for emergency vehicles	6	t	Обладнання для аварійно-ремонтних машин
42416000-5	Lifts, skip hoists, hoists, escalators and moving walkways	4	f	Підіймальні пристрої, скіпові підіймачі, підіймачі, ескалатори та пасажирські конвеєри
42416100-6	Lifts	5	f	Підіймальні пристрої
42416110-9	Bath lifts	6	t	Крісла-ліфти для ванн
42416120-2	Goods lifts	6	t	Вантажопідіймачі
42416130-5	Mechanical lifts	6	t	Механічні підіймачі
42416200-7	Skip hoists	5	f	Скіпові підіймачі
42416210-0	Bin-lifts	6	t	Підіймачі контейнерів для сміття
42416300-8	Hoists	5	t	Підіймачі
42416400-9	Escalators	5	t	Ескалатори
42416500-0	Moving walkways	5	t	Пасажирські конвеєри
42417000-2	Elevators and conveyors	4	f	Елеватори та конвеєри
42417100-3	Pneumatic elevators or conveyors	5	t	Пневматичні елеватори чи конвеєри
42417200-4	Conveyors	5	f	Конвеєри
42417210-7	Bucket-type, continuous-action elevators or conveyors	6	t	Ковшові елеватори чи конвеєри безперервної дії
42417220-0	Belt-type continuous-action elevators or conveyors	6	t	Стрічкові елеватори чи конвеєри безперервної дії
42417230-3	Armoured-faced conveyors (AFCS) for mining	6	t	Армовані шахтні конвеєри
42417300-5	Conveyor equipment	5	f	Конвеєрне обладнання
42417310-8	Conveyor belts	6	t	Конвеєрні стрічки
42418000-9	Lifting, handling, loading or unloading machinery	4	f	Машини та механізми для підіймання, переміщення, завантаження чи розвантаження
42418100-0	Mine-wagon pushers and locomotive or wagon traversers	5	t	Штовхачі шахтних вагонеток і механізми для переставляння локомотивів або вагонів
42418200-1	Monorail or ski-lift equipment	5	f	Обладнання для монорейкових доріг і гірськолижних підіймачів
42418210-4	Overhead monorail equipment	6	t	Обладнання для підвісних монорейкових доріг
42418220-7	Chairlifts	6	t	Крісельні підіймачі
42418290-8	Ski-lift equipment	6	t	Обладнання для гірськолижних підіймачів
42418300-2	Flare tip removal equipment	5	t	Устаткування для демонтажу факельних наконечників
42418400-3	Carousel storage and retrieval machines	5	t	Механізми карусельного типу для зберігання та подачі
42418500-4	Mechanical handling equipment	5	t	Механічне обладнання для вантажно-розвантажувальних робіт
42418900-8	Loading or handling machinery	5	f	Транспортно-завантажувальні механізми
42418910-1	Loading equipment	6	t	Завантажувальне обладнання
42418920-4	Unloading equipment	6	t	Розвантажувальне обладнання
42418930-7	Sideloaders	6	t	Навантажувачі з бічним висувним вантажопідіймачем
42418940-0	Container-handling equipment	6	t	Устаткування для переміщення контейнерів
42419000-6	Parts of lifting and handling equipment	4	f	Частини підіймально-транспортувального обладнання
42419100-7	Parts of cranes	5	t	Частини підіймальних кранів
42419200-8	Parts of works trucks	5	t	Частини авто маніпуляторів
42419500-1	Parts of lift, skip hoists or escalators	5	f	Частини підіймальних пристроїв, скіпових підіймачів чи ескалаторів
42419510-4	Parts of lifts	6	t	Частини підіймальних пристроїв
42419520-7	Parts of skip hoists	6	t	Частини скіпових підіймачів
42419530-0	Parts of escalators	6	t	Частини ескалаторів
42419540-3	Parts of moving walkways	6	t	Частини пасажирських конвеєрів
42419800-4	Parts of conveyors	5	f	Частини конвеєрів
42419810-7	Parts of belt conveyors	6	t	Частини стрічкових конвеєрів
42419890-1	Parts of bucket conveyors	6	t	Частини ківшових конвеєрів
42419900-5	Parts of winding gear and other lifting or handling equipment	5	t	Частини лебідок та іншого підіймально-транспортувального обладнання
42420000-6	Buckets, shovels, grabs and grips for cranes or excavators	3	t	Ковші, лопати, грейдери та затискачі для підіймальних кранів чи екскаваторів
42500000-1	Cooling and ventilation equipment	2	f	Охолоджувальне та вентиляційне обладнання
42510000-4	Heat-exchange units, air-conditioning and refrigerating equipment, and filtering machinery	3	f	Теплообмінники, кондиціонери повітря, холодильне обладнання та фільтрувальні пристрої
42511000-1	Heat-exchange units and machinery for liquefying air or other gases	4	f	Теплообмінники та обладнання для скраплення повітря чи інших газів
42511100-2	Heat-exchange units	5	f	Теплообмінники
42511110-5	Heat pumps	6	t	Теплові насоси
42511200-3	Machinery for liquefying air or other gases	5	t	Обладнання для скраплення повітря чи інших газів
42512000-8	Air-conditioning installations	4	f	Установки для кондиціювання повітря
42512100-9	Window air-conditioning machines	5	t	Віконні кондиціонери повітря
42512200-0	Wall air-conditioning machines	5	t	Настінні кондиціонери повітря
42512300-1	HVAC packages	5	t	Агрегати для опалення, вентилювання та кондиціювання повітря
42512400-2	Vehicle air conditioners	5	t	Кондиціонери повітря для транспортних засобів
42512500-3	Parts of air-conditioning machines	5	f	Частини кондиціонерів повітря
42512510-6	Dampers	6	t	Зволожувачі
42512520-9	Louvres	6	t	Вентиляційні жалюзійні ґрати
42513000-5	Refrigerating and freezing equipment	4	f	Холодильне та морозильне обладнання
42513100-6	Freezing equipment	5	t	Морозильне обладнання
42513200-7	Refrigerating equipment	5	f	Холодильне обладнання
42513210-0	Refrigerated showcases	6	t	Холодильні вітрини
42513220-3	Refrigerated counters	6	t	Холодильні прилавки
42513290-4	Commercial refrigerating equipment	6	t	Промислове холодильне обладнання
42514000-2	Machinery and apparatus for filtering or purifying gases	4	f	Машини та апарати для фільтрування чи очищення газів
42514200-4	Electrostatic air and gas cleaners	5	t	Електростатичні повітро- та газоочищувачі
42514300-5	Filtering apparatus	5	f	Фільтрувальна апаратура
42514310-8	Air filters	6	t	Повітряні фільтри
42514320-1	Gas filters	6	t	Газові фільтри
42515000-9	District heating boiler	4	t	Котли для централізованого опалення
42520000-7	Ventilation equipment	3	f	Вентиляційне обладнання
42521000-4	Smoke-extraction equipment	4	t	Димовитяжне обладнання
42522000-1	Non-domestic fans	4	f	Вентилятори непобутового призначення
42522100-2	Parts of fans	5	t	Частини вентиляторів
42530000-0	Parts of refrigerating and freezing equipment and heat pumps	3	f	Частини холодильного та морозильного обладнання і теплових насосів
42531000-7	Parts of refrigerating equipment	4	t	Частини холодильного обладнання
42532000-4	Parts of freezing equipment	4	t	Частини морозильного обладнання
42533000-1	Parts of heat pumps	4	t	Частини теплових насосів
42600000-2	Machine tools	2	f	Верстати
42610000-5	Machine tools operated by laser and machining centres	3	f	Лазерні верстати та обробні центри
42611000-2	Special-purpose machine tools	4	t	Верстати спеціального призначення
42612000-9	Machining centre	4	f	Обробні центри
42612100-0	Horizontal-spindle machining centre	5	t	Обробні центри з горизонтальним шпинделем
42612200-1	Vertical-spindle machining centre	5	t	Обробні центри з вертикальним шпинделем
42620000-8	Lathes, boring and milling machine tools	3	f	Токарні, розточувальні та фрезерувальні верстати
42621000-5	Lathes	4	f	Токарні верстати
42621100-6	CNC lathe	5	t	Токарні верстати з числовим програмним керуванням
42622000-2	Threading or tapping machines	4	t	Верстати для нарізання зовнішньої та внутрішньої нарізі
42623000-9	Milling machines	4	t	Фрезерувальні верстати
42630000-1	Metal-working machine tools	3	f	Металообробні верстати
42631000-8	Machine tools for finishing metals	4	t	Верстати для завершальної обробки металів
42632000-5	Numerically-controlled machines for metal	4	t	Металообробні верстати з числовим програмник керуванням
42633000-2	Bending, folding, straightening or flattening machines	4	t	Машини згинальні, фальцювальні, рихтувальні чи правильні
42634000-9	Forging machines	4	t	Кувальні машини
42635000-6	Die-stamping machines	4	t	Штампувальні машини
42636000-3	Presses	4	f	Преси
42636100-4	Hydraulic presses	5	t	Гідравлічні преси
42637000-0	Machine tools for drilling, boring or milling metal	4	f	Верстати для свердління, розточування чи фрезерування металів
42637100-1	Machine tools for drilling metal	5	t	Верстати для свердління металів
42637200-2	Machine tools for boring metal	5	t	Верстати для розточування металів
42637300-3	Machine tools for milling metal	5	t	Верстати для фрезерування металу
42638000-7	Metal-working machining centre	4	t	Металообробні центри
42640000-4	Machine tools for working hard materials except metals	3	f	Верстати для обробки твердих матеріалів, окрім металів
42641000-1	Machine tools for working stone, ceramics, concrete or glass	4	f	Верстати для обробки каменю, кераміки, бетону чи скла
42641100-2	Machine tools for working stone	5	t	Верстати для обробки каменю
42641200-3	Machine tools for working ceramics	5	t	Верстати для обробки кераміки
42641300-4	Machine tools for working concrete	5	t	Верстати для обробки бетону
42641400-5	Machine tools for working glass	5	t	Верстати для обробки скла
42642000-8	Machine tools for working wood, bone, cork, hard rubber or hard plastics	4	f	Верстати для обробки деревини, кістки, корку, ебоніту чи твердих пластмас
42642100-9	Machine tools for working wood	5	t	Верстати для обробки деревини
42642200-0	Machine tools for working bone	5	t	Верстати для обробки кістки
42642300-1	Machine tools for working cork	5	t	Верстати для обробки корку
42642400-2	Machine tools for working hard rubber	5	t	Верстати для обробки ебоніту
42642500-3	Machine tools for working hard plastics	5	t	Верстати для обробки твердих пластмас
42650000-7	Pneumatic or motorised hand tools	3	f	Ручні інструменти пневматичні чи моторизовані
42651000-4	Pneumatic hand tools	4	t	Пневматичні ручні інструменти
42652000-1	Hand-held electromechanical tools	4	t	Електромеханічні ручні інструменти
42660000-0	Soldering, brazing and welding tools, surface tempering and hot-spraying machines and equipment	3	f	Інструменти для паяння м’яким і твердим припоєм та для зварювання, машини та устаткування для поверхневої термообробки і гарячого напилювання
42661000-7	Soldering and brazing equipment	4	f	Обладнання для паяння м’яким і твердим припоєм
42661100-8	Soldering equipment	5	t	Обладнання для паяння м’яким припоєм
42661200-9	Brazing equipment	5	t	Обладнання для паяння твердим припоєм
42662000-4	Welding equipment	4	f	Зварювальне обладнання
42662100-5	Electric welding equipment	5	t	Обладнання для електрозварювання
42662200-6	Non-electric welding equipment	5	t	Обладнання для неелектричного зварювання
42663000-1	Surface tempering machines	4	t	Машини для поверхневої термообробки
42664000-8	Fusion equipment	4	f	Обладнання для сплавлення
42664100-9	Fusion equipment for plastics	5	t	Обладнання для сплавлення пластмас
42665000-5	Metal-spraying machinery	4	t	Машини для металевого напилення
42670000-3	Parts and accessories of machine tools	3	f	Частини та приладдя до верстатів
42671000-0	Tool holders	4	f	Тримачі для інструментів
42671100-1	Laboratory tool carriers	5	f	Тримачі лабораторних інструментів
42671110-4	Test tube racks for baths	6	t	Штативи для пробірок для занурення
42672000-7	Machine-tool work holders	4	t	Кріпильні механізми для верстатів
42673000-4	Machine-tool dividing special attachments	4	t	Ділильні голівки для верстатів
42674000-1	Parts and accessories for metal-working machine tools	4	t	Частини та приладдя до металообробних верстатів
42675000-8	Parts and accessories for hard material-working machine tools	4	f	Частини та приладдя до верстатів для обробки твердих матеріалів
42675100-9	Parts of chain saws	5	t	Частини ланцюгових пил
42676000-5	Parts of hand tools	4	t	Частини ручних інструментів
42677000-2	Parts of pneumatic tools	4	t	Частини пневматичних інструментів
42700000-3	Machinery for textile, apparel and leather production	2	f	Машини для виробництва текстильних виробів, одягу та шкіряних виробів
42710000-6	Machinery for textiles	3	f	Машини для виробництва текстильних виробів
42711000-3	Machines for processing man-made textile materials	4	t	Машини для обробки штучних текстильних матеріалів
42712000-0	Textile spinning machines	4	t	Прядильні машини
42713000-7	Weaving machines	4	t	Ткацькі верстати
42714000-4	Knitting machines	4	t	В’язальні машини
42715000-1	Sewing machines	4	t	Швейні машини
42716000-8	Laundry washing, dry-cleaning and drying machines	4	f	Пральні машини, машини для сухого чищення та сушильні машини
42716100-9	Washing installation	5	f	Пральні установки
42716110-2	Washing equipment	6	t	Пральне устаткування
42716120-5	Washing machines	6	t	Пральні машини
42716130-8	Dry-cleaning machines	6	t	Машини для сухого чищення
42716200-0	Drying machines	5	t	Сушильні машини
42717000-5	Linen ironing and folding equipment	4	f	Обладнання для прасування та складання білизни
42717100-6	Linen folding equipment	5	t	Обладнання для складання білизни
42718000-2	Textile-finishing machinery	4	f	Устаткування для апретування тканин
42718100-3	Ironing machines	5	t	Прасувальні машини
42718200-4	Ironing presses	5	t	Прасувальні преси
42720000-9	Parts of machinery for textile and apparel production	3	t	Частини машин для виробництва текстильних виробів та одягу
42800000-4	Machinery for paper or paperboard production	2	f	Машини для виробництва паперу чи картону
42810000-7	Parts of machinery for paper or paperboard production	3	t	Частини машин для виробництва паперу чи картону
42900000-5	Miscellaneous general and special-purpose machinery	2	f	Універсальні та спеціалізовані машини різні
42910000-8	Distilling, filtering or rectifying apparatus	3	f	Апарати для дистилювання, фільтрування чи ректифікації
42912000-2	Liquid filtering or purifying machinery and apparatus	4	f	Машини та апарати для фільтрування чи очищення рідин
42912100-3	Machinery and apparatus for filtering liquids	5	f	Машини та апарати для фільтрування рідин
42912110-6	Drilling-mud filtering apparatus	6	t	Апарати для фільтрування бурових розчинів
42912120-9	Hydrocyclone machinery	6	t	Гідроциклони
42943500-3	Recirculating coolers	5	t	Рециркуляційні охолоджувачі
42912130-2	Apparatus for filtering or purifying beverages	6	t	Апарати для фільтрування чи очищення напоїв
42912300-5	Machinery and apparatus for filtering or purifying water	5	f	Машини та апарати для фільтрування чи очищення води
42912310-8	Water filtration apparatus	6	t	Апарати для фільтрування води
42912320-1	De-aeration apparatus	6	t	Деаераційні апарати
42912330-4	Water-purifying apparatus	6	t	Апарати для очищення води
42912340-7	Desalination apparatus	6	t	Опріснювальні апарати
42912350-0	Filtration plant equipment	6	t	Обладнання для фільтрувальних установок
42913000-9	Oil, petrol and air-intake filters	4	f	Оливні, бензинові та повітрозабірні фільтри
42913300-2	Oil filters	5	t	Оливні фільтри
42913400-3	Petrol filters	5	t	Бензинові фільтри
42913500-4	Air-intake filters	5	t	Повітрозабірні фільтри
42914000-6	Recycling equipment	4	t	Обладнання для переробки відходів
42920000-1	Machinery for cleaning bottles, packing and weighing and spraying machinery	3	f	Машини для миття пляшок, пакування, зважування та розпилювання
42921000-8	Machinery for cleaning, filling, packing or wrapping bottles or other containers	4	f	Машини для миття, наповнювання, пакування чи обгортання пляшок або інших посудин
42921100-9	Machinery for cleaning or drying bottles or other containers	5	t	Машини для миття чи сушіння пляшок або інших посудин
42921200-0	Machinery for filling or closing bottles, cans or other containers	5	t	Машини для наповнювання чи закупорювання пляшок, бляшаних банок або інших посудин
42921300-1	Packing or wrapping machinery	5	f	Пакувальні чи обгортальні машини
42921310-4	Strapping machines	6	t	Стрічкообв’язувальні машини
42921320-7	Packaging machines	6	t	Пакувальні машини
42921330-0	Wrapping machines	6	t	Обгортальні машини
42923000-2	Weighing machinery and scales	4	f	Зважувальні машини та ваги
42923100-3	Weighing machinery	5	f	Зважувальні машини
42923110-6	Balances	6	t	Терези
42923200-4	Scales	5	f	Ваги
42923210-7	Shop scales	6	t	Магазинні ваги
42923220-0	Scales for continuous weighing of goods	6	t	Ваги для безперервного зважування
42923230-3	Checkweigher scales	6	t	Контрольні ваги
42924200-1	Steam or sand blasting machines	5	t	Пароструминні та піскоструминні машини
42924300-2	Spraying equipment	5	f	Розпилювальне обладнання
42924310-5	Spray guns	6	t	Пістолети-розпилювачі
42924700-6	Mechanical appliances for projecting, dispersing or spraying	5	f	Механічні пристрої для направленого нагнітання, розсіювання чи розпилювання
42924710-9	Gas-dispersing apparatus	6	t	Газорозпилювальні апарати
42924720-2	Decontamination equipment	6	t	Дезінфекційне обладнання
42924730-5	Pressurised water cleaning apparatus	6	t	Апарати для миття водою під тиском
42924740-8	High-pressure cleaning apparatus	6	t	Очисні апарати високого тиску
42924790-3	Odour-masking apparatus	6	t	Дезодорувальні апарати
42930000-4	Centrifuges, calendering or vending machines	3	f	Центрифуги, вальцювальні машини чи торгові автомати
42931000-1	Centrifuges	4	f	Центрифуги
42931100-2	Laboratory centrifuges and accessories	5	f	Лабораторні центрифуги та приладдя до них
42931110-5	Floor-model centrifuges	6	t	Підлогові центрифуги
42931120-8	Tabletop centrifuges	6	t	Настільні центрифуги
42931130-1	Inserts for centrifuges	6	t	Вкладки для центрифуг
42931140-4	Rotary equipment for centrifuges	6	t	Обертові механізми для центрифуг
42932000-8	Calendering machines	4	f	Вальцювальні машини
42932100-9	Rolling machines	5	t	Валкові машини
42933000-5	Vending machines	4	f	Торгові автомати
42933100-6	Sanitary vending machines	5	t	Автомати для продажу засобів гігієни
42933200-7	Stamp-vending machines	5	t	Автомати для продажу марок
42933300-8	Automatic goods-vending machines	5	t	Автомати для продажу товарів
42940000-7	Machinery for the heat treatment of materials	3	f	Машини для термічної обробки матеріалів
42941000-4	Machinery for the heat treatment of gas	4	t	Машини для термічної обробки газів
42942000-1	Ovens and accessories	4	f	Печі та приладдя до них
42942200-3	Vacuum ovens	5	t	Вакуумні печі
42943000-8	Thermostatic baths and accessories	4	f	Термостатичні бані та приладдя до них
42943100-9	Refrigeration coils	5	t	Радіатори до холодильного обладнання
42943200-0	Ultrasonic baths	5	f	Ультразвукові ванни
42943210-3	Immersion thermostats	6	t	Занурювані термостати
42943300-1	Immersion coolers	5	t	Занурювані охолоджувачі
42943400-2	Refrigerated and refrigerated/heating circulators	5	t	Циркуляційні установки для охолодження / нагрівання
44112700-5	Beams	5	t	Балки
42943600-4	High-temperature circulators	5	t	Високотемпературні циркулятори
42943700-5	Heating circulators	5	f	Нагрівальні циркулятори
42943710-8	Bath covers	6	t	Кришки для ванн
42950000-0	Parts of general-purpose machinery	3	f	Частини універсальних машин
42952000-4	Parts of centrifuges	4	t	Частини центрифуг
42953000-1	Parts of calendering machines	4	t	Частини вальцювальних машин
42954000-8	Parts of rolling machines	4	t	Частини валкових машин
42955000-5	Parts of filtering machinery	4	t	Частини фільтрувальних машин
42956000-2	Parts of purifying machinery	4	t	Частини очисних машин
42957000-9	Parts of spraying machines	4	t	Частини розпилювальних машин
42958000-6	Weights for weighing machines	4	t	Гирі до зважувальних машин
42959000-3	Non-domestic dishwashing machines	4	t	Непобутові посудомийні машини
42960000-3	Command and control system, printing, graphics, office automation and information-processing equipment	3	f	Системи керування та контролю, друкарське і графічне обладнання та обладнання для автоматизації офісу й обробки інформації
42961000-0	Command and control system	4	f	Системи керування та контролю
42961100-1	Access control system	5	t	Системи контролю доступу
42961200-2	Scada or equivalent system	5	t	Системи диспетчерського керування та збору даних (SCADA) чи еквівалентні системи
42961300-3	Vehicle location system	5	t	Системи визначання місцезнаходження транспортних засобів
42961400-4	Dispatch system	5	t	Диспетчерські системи
42962000-7	Printing and graphics equipment	4	f	Друкарське та графічне обладнання
42962100-8	Film printing system	5	t	Системи друку на плівках
42962200-9	Printing press	5	t	Друкарські верстати
42962300-0	Graphics workstations	5	t	Графічні робочі станції
42962400-1	Hectographs	5	t	Гектографи
42962500-2	Engraving machines	5	t	Гравірувальні верстати
42963000-4	Coin press	4	t	Карбувальні преси
42964000-1	Office automation equipment	4	t	Обладнання для автоматизації офісу
42965000-8	Information-processing equipment	4	f	Обладнання для обробки інформації
42965100-9	Warehouse management system	5	f	Системи управління складами
42965110-2	Depot system	6	t	Складські системи
42967000-2	Controller unit	4	f	Блоки керування
42967100-3	Digital remote-control unit	5	t	Цифрові пристрої дистанційного керування
42968000-9	Dispensers	4	f	Диспенсери
42968100-0	Drinks dispensers	5	t	Дозатори напоїв
42968200-1	Sanitary dispensing machines	5	t	Диспенсери засобів гігієни
42968300-2	Toilet-roll dispenser system	5	t	Диспенсери туалетного паперу
42970000-6	Parts of dishwashing machines and of machines for cleaning, filling, packing or wrapping	3	f	Частини посудомийних машин та машин для чищення, наповнювання, пакування чи обгортання
42971000-3	Parts of dishwashing machines	4	t	Частини посудомийних машин
42972000-0	Parts of cleaning machines	4	t	Частини очисних машин
42973000-7	Parts of filling machines	4	t	Частини машин для наповнювання
42974000-4	Parts of packing machines	4	t	Частини пакувальних машин
42975000-1	Parts of wrapping machines	4	t	Частини обгортальних машин
42980000-9	Gas generators	3	f	Газогенератори
42981000-6	Ozone generators	4	t	Озоногенератори
42990000-2	Miscellaneous special-purpose machinery	3	f	Машини спеціального призначення різні
42991000-9	Paper, printing and bookbinding machinery and parts	4	f	Машини для виробництва паперу, друку, палітурування та їх частини
42991100-0	Bookbinding machinery	5	f	Палітурні машини
42991110-3	Book-sewing machinery	6	t	Брошурувальні машини
42991200-1	Printing machinery	5	f	Друкарські машини
42991210-4	Offset printing machinery	6	t	Офсетні друкарські машини
42991220-7	Typesetting machinery	6	t	Типографські машини
42991230-0	Ticket printers	6	t	Принтери для друку квитків
42991300-2	Photocomposing system	5	t	Фотонабірні системи
42991400-3	Dryers for wood, paper pulp, paper or paperboard	5	t	Сушарки для деревини, паперової маси, паперу чи картону
42991500-4	Parts of printing or bookbinding machinery	5	t	Частини друкарських або палітурних машин
42992000-6	Special-purpose electrical goods	4	f	Електротовари спеціального призначення
42992100-7	Faraday cage	5	t	Клітки Фарадея
42992200-8	Anechoic chamber	5	t	Безлунні камери
42992300-9	Electromagnetic absorbent material	5	t	Матеріали-поглиначі електромагнітного опромінювання
42993000-3	Chemical industry machinery	4	f	Машини для хімічної промисловості
42993100-4	Chlorinators	5	t	Хлоратори
42993200-5	Dosing plant	5	t	Дозувальні установки
44540000-7	Chain	3	f	Ланцюги
42994000-0	Machinery for working rubber or plastics	4	f	Машини для обробки гуми чи пластмас
42994100-1	Machines for the production of plastic windows and frames	5	t	Машини для виробництва пластикових вікон і рам
42994200-2	Machinery for working plastics	5	f	Машини для обробки пластмас
42994220-8	Lamination accessories	6	t	Приладдя для ламінації
42994230-1	Laminators	6	t	Ламінатори
42995000-7	Miscellaneous cleaning machines	4	f	Очисні машини різні
42995100-8	Tunnel washer	5	t	Мийні машини тунельного типу
42995200-9	Beach cleaning machines	5	t	Машини для прибирання пляжів
42996000-4	Machinery for the treatment of sewage	4	f	Машини для очищення стічних вод
42996100-5	Comminutors	5	f	Подрібнювачі твердих частинок у стічних водах
42996110-8	Macerators for the treatment of sewage	6	t	Мацератори для стічних вод
42996200-6	Sewage presses	5	t	Фільтр-преси для очищення стічних вод
42996300-7	Scrapers	5	t	Шкребла
42996400-8	Mixer units	5	t	Змішувачі
42996500-9	Sewage screens	5	t	Ґрати для фільтрування стічних вод
42996600-0	Oxygenation equipment	5	t	Обладнання для насичення киснем
42996700-1	Precipitators	5	t	Осаджувачі
42996800-2	Sedimentation beds	5	t	Басейни-відстійники
42996900-3	Sludge-processing equipment	5	t	Обладнання для обробки мулу
42997000-1	Pipeline machinery	4	f	Обладнання для обслуговування трубопроводів
42997100-2	Machines for inspecting the internal surface of pipelines	5	t	Машини для перевірки внутрішніх поверхонь трубопроводів
42997200-3	Machines for cleaning the internal surface of pipelines	5	t	Машини для очищення внутрішніх поверхонь трубопроводів
42997300-4	Industrial robots	5	t	Промислові роботи
42998000-8	Pallet-picking system	4	f	Системи автоматизованої подачі піддонів
42998100-9	Pallet-retrieving system	5	t	Системи автоматизованого повернення піддонів
42999000-5	Non-domestic vacuum cleaners and floor polishers	4	f	Промислові пилососи та підлогонатирачі
42999100-6	Non-domestic vacuum cleaners	5	t	Промислові пилососи
42999200-7	Non-domestic floor polishers	5	t	Промислові підлогонатирачі
42999300-8	Parts of non-domestic vacuum cleaners	5	t	Частини промислових пилососів
42999400-9	Parts of non-domestic floor polishers	5	t	Частини промислових підлогонатирачів
43000000-3	Machinery for mining, quarrying, construction equipment	1	f	Гірничодобувне та будівельне обладнання
43100000-4	Mining equipment	2	f	Гірниче обладнання
43120000-0	Coal or rock cutters and tunnelling machinery, and boring or sinking machinery	3	f	Врубові та тунелепрохідні, бурильні чи прохідницькі машини для добування вугілля чи гірських порід
43121000-7	Well-drilling machinery	4	f	Машини для буріння свердловин
43121100-8	Wellhead running tools	5	t	Спускові інструменти
43121200-9	Riser connector apparatus	5	t	Муфти водовіддільних колон
43121300-0	Well-completion equipment	5	t	Обладнання для освоєння свердловини
43121400-1	Well-intervention equipment	5	t	Устаткування для виконання робіт усередині свердловини
43121500-2	Well-testing equipment	5	t	Устаткування для випробування свердловин
43121600-3	Blowout prevention (BOP) apparatus	5	t	Протифонтанне обладнання
43122000-4	Coal or rock-cutting machinery	4	t	Врубові машини для добування вугілля чи гірських порід
43123000-1	Tunnelling machinery	4	t	Тунелепрохідні машини
43124000-8	Boring machinery	4	f	Бурильні машини
43124100-9	Moles	5	t	Прохідні комбайни
43124900-7	Rock-drilling equipment	5	t	Обладнання для буріння гірських порід
43125000-5	Sinking machinery	4	t	Прохідні машини
43130000-3	Drilling equipment	3	f	Бурове обладнання
43131000-0	Offshore production platforms	4	f	Морські добувні платформи
43131100-1	Offshore equipment	5	t	Устаткування для розробки морських родовищ
43131200-2	Offshore drilling unit	5	t	Морські бурові установки
43132000-7	Oil drilling equipment	4	f	Устаткування для буріння нафтових свердловин
43132100-8	Drilling machinery	5	t	Бурильна техніка
43132200-9	Drilling rig	5	t	Бурильні установки
43132300-0	Drills	5	t	Бури
43132400-1	Line equipment	5	t	Обсадне обладнання
43132500-2	Liner hangers	5	t	Підвіски обсадних колон
43133000-4	Oil platform equipment	4	f	Устаткування нафтовидобувних платформ
43133100-5	Skid units	5	t	Блок-бокси
43133200-6	Skid-mounted modules	5	t	Модулі блок-боксів
43134000-1	Oil-field machinery	4	f	Нафтопромислова техніка
43134100-2	Submersible pumps	5	t	Занурювані насоси
43135000-8	Subsea equipment	4	f	Підводне обладнання
43135100-9	Subsea control systems	5	t	Підводні системи керування
43136000-5	Downhole equipment	4	t	Устаткування для внутрішньосвердловинних робіт
43140000-6	Mobile hydraulic-powered mine roof supports	3	t	Пересувні опорні колони для шахт
43200000-5	Earthmoving and excavating machinery, and associated parts	2	f	Машини для земляних і землерийних робіт та їх частини
43210000-8	Earthmoving machinery	3	f	Машини для земляних робіт
43211000-5	Bulldozers	4	t	Бульдозери
43212000-2	Angle-dozers	4	t	Бульдозери з поворотним відвалом
43220000-1	Graders and levellers	3	f	Грейдери та планувальники
43221000-8	Road graders	4	t	Дорожні грейдери
43230000-4	Scraper machines	3	t	Скрепери
43240000-7	Tamping machines	3	t	Трамбувальні машини
43250000-0	Front-end shovel loaders	3	f	Фронтальні ковшові навантажувачі
43251000-7	Front-end shovel loaders with backhoe	4	t	Фронтальні ковшові навантажувачі зі зворотною лопатою
43252000-4	Front-end shovel loaders without backhoe	4	t	Фронтальні ковшові навантажувачі без зворотної лопати
43260000-3	Mechanical shovels, excavators and shovel loaders, and mining machinery	3	f	Механічні лопати, екскаватори та ковшові навантажувачі, гірнича техніка
43261000-0	Mechanical shovels	4	f	Механічні лопати
43261100-1	Mechanical shovel loaders	5	t	Механічні ковшові навантажувачі
43262000-7	Excavating machinery	4	f	Землерийні машини
43262100-8	Mechanical excavators	5	t	Механічні екскаватори
43300000-6	Construction machinery and equipment	2	f	Будівельна техніка та обладнання
43310000-9	Civil engineering machinery	3	f	Машини для цивільного будівництва
43311000-6	Pile drivers	4	t	Копери
43312000-3	Road-surfacing machinery	4	f	Машини для укладання дорожнього покриття
43312100-4	Planers	5	t	Планувальник ґрунту
43312200-5	Chippers	5	t	Відбійні молотки
43312300-6	Paving machinery	5	t	Мостильні машини
43312400-7	Road rollers	5	t	Дорожні котки
43312500-8	Mechanical rollers	5	t	Механічні котки
43313000-0	Snowploughs and snowblowers	4	f	Плужні та роторні снігоочисники
43313100-1	Snowploughs	5	t	Плужні снігоочисники
43313200-2	Snowblowers	5	t	Роторні снігоочисники
43314000-7	Pile extractors	4	t	Пристрої для витягування паль
43315000-4	Compacting machinery	4	t	Ущільнювальні машини
43316000-1	Cable-laying machinery	4	t	Машини для укладання кабелів
43320000-2	Construction equipment	3	f	Будівельне обладнання
43321000-9	Bridge-suspension equipment	4	t	Обладнання для будівництва підвісних мостів
43322000-6	Dismantling equipment	4	t	Демонтажне обладнання
43323000-3	Irrigation equipment	4	t	Зрошувальне обладнання
43324000-0	Drainage equipment	4	f	Дренажне обладнання
43324100-1	Equipment for swimming pools	5	t	Обладнання для плавальних басейнів
43325000-7	Park and playground equipment	4	f	Обладнання для парків та ігрових майданчиків
43325100-8	Grounds-maintenance equipment	5	t	Обладнання для експлуатації майданчиків
43327000-1	Prefabricated equipment	4	t	Збірне обладнання
43328000-8	Hydraulic installations	4	f	Гідравлічні установки
43328100-9	Hydraulic equipment	5	t	Гідравлічне обладнання
43329000-5	Equipment sets	4	t	Комплекти обладнання
43400000-7	Mineral-processing and foundry mould-forming machinery	2	f	Машини для обробки мінералів та ливарно-формувальні машини
43410000-0	Mineral-processing machinery	3	f	Машини для обробки мінералів
43411000-7	Sorting and screening machines	4	t	Сортувальні та просіювальні машини
43412000-4	Machines for mixing gravel with bitumen	4	t	Машини для змішування гравію з бітумом
43413000-1	Concrete or mortar mixers	4	f	Бетоно- та розчиномішалки
43413100-2	Cement mixers	5	t	Мішалки для цементного розчину
43414000-8	Grinding machines	4	f	Дробильні машини
43414100-9	Coal pulverising mills	5	t	Вугільні млини
43415000-5	Foundry moulds	4	t	Ливарні форми
43420000-3	Foundry mould-forming machinery	3	t	Машини для виготовлення ливарних форм
43500000-8	Track-laying vehicles	2	t	Гусеничні транспортні засоби
43600000-9	Parts of machinery for mining, quarrying and construction	2	f	Частини гірничодобувного та будівельного обладнання
43610000-2	Parts for boring machinery	3	f	Частини бурильних машин
43611000-9	Parts for well-drilling machinery	4	f	Частини машин для буріння свердловин
43611100-0	Bridge plugs	5	t	Пакер-пробки
43611200-1	Industrial drill bits	5	t	Промислові бурові головки
43611300-2	Drilling jars	5	t	Бурильні яси
43611400-3	Iron roughnecks	5	t	Автоматизовані бурові ключі
43611500-4	Rotary tables	5	t	Столи ротора бурової установки
43611600-5	Mudline suspension equipment	5	t	Обладнання для донних підвісок
43611700-6	Tieback equipment	5	t	Обладнання для подовження обсадної колони
43612000-6	Parts of well-extraction machinery	4	f	Частини свердловинних виймальних машин
43612100-7	Casing hangers	5	t	Підвісні хомути для обсадних колон
43612200-8	Liner hanger equipment	5	t	Обладнання для підвісок обсадних колон
43612300-9	Production riser tensioners	5	t	Системи натягу експлуатаційних кайзерів
43612400-0	Wellheads	5	t	Горловини свердловин
43612500-1	Production riser tieback equipment	5	t	Обладнання для з’єднання експлуатаційних райзерів
43612600-2	Wellhead control system	5	t	Системи керування свердловинними горловинами
43612700-3	Wellhead equipment	5	t	Обладнання свердловинних горловин
43612800-4	Rig-jacking systems	5	t	Системи підіймання бурильних установок
43613000-3	Parts of coal or rock cutting machinery	4	f	Частини врубових машин для добування вугілля чи гірських порід
43613100-4	Parts of coal cutting machinery	5	t	Частини врубових машин для добування вугілля
43613200-5	Parts of rock cutting machinery	5	t	Частини врубових машин для добування гірських порід
43614000-0	Parts of tunnelling machinery	4	t	Частини тунелепрохідних машин
43620000-5	Parts of mineral-processing machinery	3	t	Частини машин для обробки мінералів
43630000-8	Parts of sinking machinery	3	t	Частини прохідних машин
43640000-1	Parts of excavating machinery	3	t	Частини екскаваторів
43700000-0	Machinery for metallurgy and associated parts	2	f	Машини для металургійної промисловості та їх частини
43710000-3	Metal-rolling machinery	3	f	Металопрокатні машини
43711000-0	Parts of metal-rolling machinery	4	t	Частини металопрокатних машин
43720000-6	Casting machines	3	f	Ливарні машини
43721000-3	Parts of casting machines	4	t	Частини ливарних машин
43800000-1	Workshop equipment	2	f	Цехове обладнання
43810000-4	Woodworking equipment	3	f	Деревообробне обладнання
43811000-1	Sanding machines	4	t	Піскоструминні апарати
43812000-8	Sawing equipment	4	t	Пиляльне обладнання
43820000-7	Shoe-making equipment	3	t	Обладнання для пошиття взуття
43830000-0	Power tools	3	t	Електричні інструменти
43840000-3	Blacksmiths' equipment	3	t	Ковальське обладнання
44000000-0	Construction structures and materials; auxiliary products to construction (except electric apparatus)	1	f	Конструкції та конструкційні матеріали; допоміжна будівельна продукція (крім електроапаратури)
44100000-1	Construction materials and associated items	2	f	Конструкційні матеріали та супутні вироби
44110000-4	Construction materials	3	f	Конструкційні матеріали
44111000-1	Building materials	4	f	Будівельні матеріали
44111100-2	Bricks	5	t	Цегла
44111200-3	Cement	5	f	Цемент
44111210-6	Drilling cement	6	t	Цемент для свердловин
44111300-4	Ceramics	5	t	Керамічні вироби
44111400-5	Paints and wallcoverings	5	t	Фарби та шпалери
44111500-6	Insulators and insulating fittings	5	f	Ізолятори та ізоляційне приладдя
44111510-9	Insulators	6	f	Ізолятори
44111511-6	Electrical insulators	7	t	Електроізолятори
44111520-2	Thermal insulating material	6	t	Термоізоляційні матеріали
44111530-5	Electrical insulating fittings	6	t	Електроізоляційне приладдя
44111540-8	Insulating glass	6	t	Ізоляційне скло
44111600-7	Blocks	5	t	Блоки
44111700-8	Tiles	5	t	Кахель
44111800-9	Mortar (construction)	5	t	Розчини (будівельні)
44111900-0	Ceramic flags	5	t	Глазурований декоративний кахель
44112000-8	Miscellaneous building structures	4	f	Будівельні конструкції різні
44112100-9	Shelters	5	f	Навіси
44112110-2	Shelter parts	6	t	Частина навісів
44112120-5	Profile sections	6	t	Профільний прокат
44112200-0	Floor coverings	5	f	Підлогове покриття
44112210-3	Solid flooring	6	t	Тверде підлогове покриття
44112220-6	False floors	6	t	Фальш-підлоги
44112230-9	Linoleum	6	t	Лінолеум
44112240-2	Parquet	6	t	Паркет
44112300-1	Partitions	5	f	Перегородки
44112310-4	Partition walls	6	t	Стіни-перегородки
44112400-2	Roof	5	f	Покрівля
44112410-5	Roof frames	6	t	Крокв’яні ферми
44112420-8	Roof supports	6	t	Опорні крокви
44112430-1	Roof trusses	6	t	Ферми
44112500-3	Roofing materials	5	f	Покрівельні матеріали
44112510-6	Shingles	6	t	Гнучка черепиця
44112600-4	Sound insulation	5	t	Звукоізоляційні матеріали
44113000-5	Road-construction materials	4	f	Матеріали для будівництва доріг
44113100-6	Paving materials	5	f	Матеріали для мощення доріг
44113120-2	Paving slabs	6	t	Тротуарна плитка
44113130-5	Paving stones	6	t	Кам’яна бруківка
44113140-8	Roadstone	6	t	Каміння для мощення доріг
44113200-7	Flagstones	5	t	Плитняк
44113300-8	Coated materials	5	f	Бітумні матеріали
44113310-1	Coated road materials	6	t	Бітумні матеріали для покриття доріг
44113320-4	Coated roadstone	6	t	Гравій, оброблений бітумом
44113330-7	Coating materials	6	t	Матеріали для дорожнього покриття
44113500-0	Glass beads	5	t	Скляні кульки для дорожньої розмітки
44113600-1	Bitumen and asphalt	5	f	Бітум та асфальт
44113610-4	Bitumen	6	t	Бітум
44113620-7	Asphalt	6	t	Асфальт
44113700-2	Road-repair materials	5	t	Матеріали для ремонту дорожнього покриття
44113800-3	Road-surfacing materials	5	f	Матеріали для влаштування дорожнього покриття
44113810-6	Surface dressing	6	t	Матеріали для поверхневої обробки дорожнього покриття
44113900-4	Road-maintenance materials	5	f	Матеріали для технічного обслуговування доріг
44113910-7	Winter-maintenance materials	6	t	Матеріали для зимової експлуатації доріг
44114000-2	Concrete	4	f	Бетон
44114100-3	Ready-mixed concrete	5	t	Бетонні суміші
44114200-4	Concrete products	5	f	Бетонні вироби
44114210-7	Concrete piles	6	t	Бетонні палі
44114220-0	Concrete pipes and fittings	6	t	Бетонні труби та арматура
44114250-9	Concrete slabs	6	t	Бетонні плити
44115000-9	Building fittings	4	f	Будівельна фурнітура
44115100-0	Ducting	5	t	Повітроводи
44115200-1	Plumbing and heating materials	5	f	Матеріали для водопровідних та опалювальних систем
44115210-4	Plumbing materials	6	t	Матеріали для водопровідних систем
44115220-7	Heating materials	6	t	Матеріали для опалювальних систем
44115310-5	Roller-type shutters	6	t	Ролети
44115400-3	Skylights	5	t	Світлові люки
44115500-4	Sprinkler systems	5	t	Спринклерні системи
44115600-5	Stairlifts	5	t	Сходові підіймачі
44115700-6	Exterior blinds	5	f	Зовнішні жалюзі
44115710-9	Canopies	6	t	Тенти
44115800-7	Building internal fittings	5	f	Внутрішньобудинкове приладдя
44115810-0	Curtain rails and hangings	6	f	Карнизи для тканинних і ниткових штор
44115811-7	Curtain rails	7	t	Карнизи для штор
44115900-8	Sun protection devices	5	t	Сонцезахисні пристрої
44130000-0	Sewer mains	3	f	Каналізаційні системи
44131000-7	Sewage chambers	4	t	Каналізаційні шахти
44132000-4	Culvert elements	4	t	Елементи дренажних труб
44133000-1	Ductile-iron end caps	4	t	Чавунні заглушки
44134000-8	Bends	4	t	Коліна для каналізаційних труб
44140000-3	Products related to construction materials	3	f	Продукція, пов’язана з конструкційними матеріалами
44141000-0	Conduit	4	f	Гнучкі труби
44141100-1	Electric conduit	5	t	Кабельні короби
44142000-7	Frames	4	t	Рами
44143000-4	Pallets	4	t	Піддони
44144000-1	Posts	4	t	Стовпи
44160000-9	Pipeline, piping, pipes, casing, tubing and related items	3	f	Магістралі, трубопроводи, труби, обсадні труби, тюбінги та супутні вироби
44161000-6	Pipelines	4	f	Магістральні трубопроводи
44161100-7	Gas pipelines	5	f	Магістральні газопроводи
44161110-0	Gas-distribution network	6	t	Газорозподільні мережі
44161200-8	Water mains	5	t	Водогінні труби
44161400-0	Underwater pipeline	5	f	Підводні магістральні трубопроводи
44161410-3	Subsea pipelines	6	t	Морські підводні магістральні трубопроводи
44161500-1	High-pressure pipeline	5	t	Магістральні трубопроводи високого тиску
44161600-2	Low-pressure pipeline	5	t	Магістральні трубопроводи низького тиску
44161700-3	Pipeline pigs	5	f	Шкребла для чищення труб
44161710-6	Pig launchers	6	t	Пускові пристрої для шкребел
44161720-9	Pig receivers	6	t	Приймальні пристрої для шкребел
44161730-2	Pig traps	6	t	Пристрої для ловлі шкребел
44162000-3	Piping	4	f	Трубопроводи
44162100-4	Piping supplies	5	t	Приладдя для трубопроводів
44162200-5	Distribution piping	5	t	Розподільні трубопроводи
44162300-6	Outfall piping	5	t	Дренажні трубопроводи
44162400-7	Vitrified clay manholes	5	t	Оглядові колодязі з обпеченої глини
44162500-8	Drinking-water piping	5	t	Трубопроводи питної води
44163000-0	Pipes and fittings	4	f	Труби та арматура
44163100-1	Pipes	5	f	Труби
44163110-4	Drainage pipes	6	f	Дренажні труби
44163111-1	Drain pipes	7	t	Стічні труби
44163112-8	Drainage system	7	t	Дренажні системи
44163120-7	Distance-heating pipes	6	f	Труби централізованих опалювальних систем
44163121-4	Heating pipes	7	t	Опалювальні труби
44163130-0	Sewer pipes	6	t	Каналізаційні труби
44163140-3	Steam and water pipes	6	t	Паропровідні та водопровідні труби
44163150-6	Low-pressure pipes	6	t	Труби низького тиску
44163160-9	Distribution pipes and accessories	6	t	Розподільні труби та приладдя
44163200-2	Pipe fittings	5	f	Трубна арматура
44163210-5	Pipe clamps	6	t	Трубні хомути
44163230-1	Pipe connectors	6	t	З’єднувачі труб
44163240-4	Pipe joints	6	f	Трубні муфти
44163241-1	Insulated joints	7	t	Ізоляційні прокладки
44164000-7	Casing and tubing	4	f	Обсадні труби та тюбінги
44164100-8	Casing	5	t	Обсадні труби
44164200-9	Tubing	5	t	Тюбінги
44164300-0	Tubular goods	5	f	Трубні вироби
44164310-3	Tubes and fittings	6	t	Трубки та арматура
44165000-4	Hoses, risers and sleeves	4	f	Шланги, стояки та рукави
44165100-5	Hoses	5	f	Шланги
44165110-8	Drilling hoses	6	t	Бурові шланги
44165200-6	Risers	5	f	Стояки
44165210-9	Flexible risers	6	t	Гнучкі стояки
44165300-7	Sleeves	5	t	Рукави
44166000-1	Oil country tubular goods	4	t	Трубні вироби для нафтової промисловості
44167000-8	Various pipe fittings	4	f	Трубна арматура різна
44167100-9	Couplings	5	f	Муфти
44167110-2	Flanges	6	f	Фланці
44167111-9	Flange adaptors	7	t	Фланцеві перехідники
44167200-0	Repair clamps and collars	5	t	Ремонтні хомути та манжети
44167300-1	Bends, tees and pipe fittings	5	t	Коліна, трійники та арматура до труб
44167400-2	Elbows	5	t	Патрубки
44170000-2	Plates, sheets, strip and foil related to construction materials	3	f	Плити, листи, стрічки та фольга, пов’язані з конструкційними матеріалами
44171000-9	Plates (construction)	4	t	Плити (будівельні)
44172000-6	Sheets (construction)	4	t	Листи (будівельні)
44173000-3	Strip	4	t	Стрічки
44174000-0	Foil	4	t	Фольга
44175000-7	Panels	4	t	Панелі
44176000-4	Film	4	t	Плівки
44190000-8	Miscellaneous construction materials	3	f	Конструкційні матеріали різні
44191000-5	Miscellaneous construction materials in wood	4	f	Дерев’яні конструкційні матеріали різні
44191100-6	Plywood	5	t	Фанера
44191200-7	Laminated wood	5	t	Шарувата деревина
44191300-8	Particle board	5	t	Деревностружкові плити
44191400-9	Fibreboard	5	t	Деревноволоконні плити
44191500-0	Densified wood	5	t	Пресована деревина
44191600-1	Parquet panels	5	t	Паркетна дошка
44192000-2	Other miscellaneous construction materials	4	f	Інші різні конструкційні матеріали
44192100-3	PVC foam	5	t	Полівінілхлоридні пінопласти
44192200-4	Nails	5	t	Цвяхи
44200000-2	Structural products	2	f	Конструкційні вироби
44210000-5	Structures and parts of structures	3	f	Конструкції та їх частини
44211000-2	Prefabricated buildings	4	f	Збірні споруди
44211100-3	Modular and portable buildings	5	f	Модульні та переносні споруди
44211110-6	Cabins	6	t	Котеджі
44211200-4	Cubicles	5	t	Офісні кубікли
44211300-5	Field hospital	5	t	Польові госпіталі
44211400-6	Field kitchens	5	t	Польові кухні
44211500-7	Glasshouses	5	t	Оранжереї
44212000-9	Structural products and parts except prefabricated buildings	4	f	Конструкційні вироби та їх частини, крім збірних споруд
44212100-0	Bridge	5	f	Мости
44212110-3	Bridge sections	6	t	Мостові секції
44212120-6	Structures of bridges	6	t	Мостові конструкції
44212200-1	Towers, lattice masts, derricks and pylons	5	f	Вежі, ґратчасті щогли, вишки та опори ліній електропередачі
44212210-4	Derricks	6	f	Вишки
44212211-1	Drilling derricks	7	t	Бурові вишки
44212212-8	Static drilling derricks	7	t	Стаціонарні бурові вишки
44212220-7	Pylons, poles and pickets	6	f	Вежі-пілони, стовпи та опори
44212221-4	Pylons	7	t	Вежі-пілони
44212222-1	Electricity transmission pylons	7	t	Вежі-пілони ліній електропередачі
44212223-8	Pylon supports	7	t	Опори веж-пілонів
44212224-5	Poles for carrying overhead lines	7	t	Стовпи повітряних ліній електропередачі
44212225-2	Poles	7	t	Стовпи
44212226-9	Electricity poles	7	t	Стовпи для електромереж
44212227-6	Pickets	7	t	Опори
44212230-0	Towers	6	f	Вежі
44212233-1	Water towers	7	t	Водонапірні башти
44212240-3	Girders	6	t	Балки
44212250-6	Masts	6	t	Щогли
44212260-9	Radio or television masts	6	f	Радіощогли та телевізійні щогли
44212261-6	Radio masts	7	t	Радіощогли
44212262-3	Television masts	7	t	Телевізійні щогли
44212263-0	Lattice masts	7	t	Ґратчасті щогли
44212300-2	Structures and parts	5	f	Конструкції та їх частини
44212310-5	Scaffolding	6	f	Риштовання
44212311-2	Arch struts	7	t	Дугоподібні ферми
44212312-9	Arch stilts	7	t	Аркові опори
44212313-6	Supports	7	t	Підпори
44212314-3	Pipe-hanging brackets	7	t	Кронштейни для труб
44212315-0	Equipment for scaffolding	7	t	Обладнання для риштування
44212316-7	Arch clips	7	t	Аркові затискачі
44212317-4	Scaffolding structures	7	t	Конструкції риштовань
44212318-1	Pipeline supports	7	t	Опори магістральних трубопроводів
44212320-8	Miscellaneous structures	6	f	Конструкції різні
44212321-5	Bus shelters	7	t	Навіси для автобусних зупинок
44212322-2	Telephone booths	7	t	Телефонні будки
44212329-1	Security screens	7	t	Захисні ґрати
44212380-6	Parts of structures	6	f	Частини конструкцій
44212381-3	Cladding	7	t	Обшивки
44212382-0	Floodgates	7	t	Водоспускні споруди
44212383-7	Lock gates	7	t	Шлюзи судноплавних каналів
44212390-9	Sluices	6	f	Шлюзи
44212391-6	Sluice gates	7	t	Шлюзові ворота
44212400-3	Piling	5	f	Палі
44212410-6	Sheet piling	6	t	Шпунтові палі
44212500-4	Angles and sections	5	f	Кутові та камерні профілі
44212510-7	Angles	6	t	Кутові профілі
44212520-0	Sections	6	t	Камерні профілі
44220000-8	Builders' joinery	3	f	Столярні вироби
44221000-5	Windows, doors and related items	4	f	Вікна, двері та супутні вироби
44221100-6	Windows	5	f	Вікна
44221110-9	Window frames	6	f	Віконні рами
44221111-6	Double-glazed units	7	t	Склопакети
44221120-2	French windows	6	t	Французькі вікна
44221200-7	Doors	5	f	Двері
44221210-0	Door blanks	6	f	Дверні полотна
44221211-7	Door frames	7	t	Дверні коробки
44221212-4	Door screens	7	t	Дверні ґрати
44221213-1	Door thresholds	7	t	Дверні пороги
44221220-3	Fire doors	6	t	Протипожежні двері
44221230-6	Sliding doors	6	t	Розсувні двері
44221240-9	Garage doors	6	t	Гаражні двері
44221300-8	Gates	5	f	Ворота
44221310-1	Access gates	6	t	В’їзні ворота
44221400-9	Shutters	5	t	Віконниці
44221500-0	Thresholds	5	t	Пороги
44230000-1	Builders' carpentry	3	f	Теслярські вироби
44231000-8	Made-up fencing panels	4	t	Збірні панельні огорожі
44232000-5	Timber roof trusses	4	t	Дерев’яні крокви
44233000-2	Staircases	4	t	Сходи
44300000-3	Cable, wire and related products	2	f	Кабелі, дроти та супутня продукція
44310000-6	Wire products	3	f	Вироби з дроту
44311000-3	Stranded wire	4	t	Металеві троси
44312000-0	Fencing wire	4	f	Дріт для огорож
44312300-3	Barbed wire	5	t	Колючий дріт
44313000-7	Metal netting	4	f	Металеві сітки
44313100-8	Wire-mesh fencing	5	t	Огорожі з дротяної сітки
44313200-9	Wire cloth	5	t	Плетені металеві сітки
44315000-1	Wire rods	4	f	Катанки
44315100-2	Welding accessories	5	t	Зварювальне приладдя
44315200-3	Welding materials	5	t	Зварювальні матеріали
44315300-4	Soldering or brazing materials	5	f	Матеріали для паяння м’яким чи твердим припоєм
44315310-7	Soldering materials	6	t	Матеріали для паяння м’яким припоєм
44315320-0	Brazing materials	6	t	Матеріали для паяння твердим припоєм
44316000-8	Ironmongery	4	f	Ковальські пристрої
44316100-9	Anvils	5	t	Ковадла
44316200-0	Portable forge	5	t	Переносні горна
44316300-1	Grilles	5	t	Решітки
44316400-2	Hardware	5	t	Дрібні залізні вироби
44316500-3	Smiths' wares	5	f	Ковані вироби
44316510-6	Blacksmiths' wares	6	t	Слюсарні вироби
44317000-5	Iron or steel slings	4	t	Залізні чи сталеві замки-карабіни
44318000-2	Conductors	4	t	Електричні проводи
44320000-9	Cable and related products	3	f	Кабелі та супутня продукція
44321000-6	Cable	4	t	Кабелі
44322000-3	Cable accessories	4	f	Кабельне приладдя
44322100-4	Cable conduits	5	t	Кабельні канали
44322200-5	Cable connectors	5	t	Кабельні роз’єми
44322300-6	Cable ducts	5	t	Кабельні оболонки
44322400-7	Cable fasteners	5	t	Кабельні хомути
44330000-2	Bars, rods, wire and profiles used in construction	3	f	Будівельні прути, стрижні, дроти та профілі
44331000-9	Bars	4	t	Прути
44332000-6	Rods (construction)	4	t	Стрижні (будівельні)
44333000-3	Wire	4	t	Дріт
44334000-0	Profiles	4	t	Профілі
44400000-4	Miscellaneous fabricated products and related items	2	f	Готова продукція різних видів та супутні вироби
44410000-7	Articles for the bathroom and kitchen	3	f	Вироби для ванної кімнати та кухні
44411000-4	Sanitary ware	4	f	Санітарна техніка
44411100-5	Taps	5	t	Водопровідні крани
44411200-6	Baths	5	t	Ванни
44411300-7	Washbasins	5	t	Умивальники
44411400-8	Shower basins	5	t	Душові піддони
44411600-0	Bidets	5	t	Біде
44411700-1	Lavatory seats, covers, bowls and cisterns	5	f	Сидіння для унітазів, кришки для унітазів, унітази та зливні бачки
44411710-4	Lavatory seats	6	t	Сидіння для унітазів
44411720-7	Lavatory covers	6	t	Кришки для унітазів
44411740-3	Lavatory bowls	6	t	Унітази
44411750-6	Lavatory cisterns	6	t	Зливні бачки
44411800-2	Urinals	5	t	Пісуари
44420000-0	Goods used in construction	3	f	Будівельні товари
44421000-7	Armoured or reinforced safes, strongboxes and doors	4	f	Броньовані чи армовані сейфи, банківські сейфи та двері
44421300-0	Safes	5	t	Сейфи
44421500-2	Armoured or reinforced doors	5	t	Броньовані чи армовані двері
44421600-3	Safe-deposit lockers	5	t	Депозитні сейфові скриньки
44421700-4	Boxes and lockers	5	f	Шухляди та шафки із замком
44421710-7	Left-luggage lockers	6	t	Камери зберігання багажу
44421720-0	Lockers	6	f	Шафи із замком
44421721-7	Safe-deposit boxes	7	t	Депозитні комірки
44421722-4	Safety cases	7	t	Кейси із замком
44421780-8	Deed boxes	6	t	Сейфи для документів
44421790-1	Cash boxes	6	t	Касові шухляди
44422000-4	Letterboxes	4	t	Поштові скриньки
44423000-1	Miscellaneous articles	4	f	Вироби різні
44423100-2	Bells	5	t	Дзвони
44423200-3	Ladders	5	f	Драбини
44423220-9	Folding steps	6	t	Розкладні драбини
44423230-2	Step stools	6	t	Стільці-драбини
44423300-4	Goods-handling equipment	5	f	Вантажно-розвантажувальне обладнання
44423330-3	Platforms for handling goods	6	t	Вантажно-розвантажувальні платформи
44423340-6	Wire-rope accessories for handling goods	6	t	Дротяно-канатне приладдя для вантажно-розвантажувальних робіт
44423400-5	Signs and related items	5	f	Вказівники та супутні вироби
44423450-0	Nameplates	6	t	Іменні таблички
44423460-3	Address plates	6	t	Адресні вивіски
44423700-8	Manhole elements	5	f	Елементи оглядових колодязів
44423710-1	Surface boxes	6	t	Каналізаційні люки
44423720-4	Boundary boxes	6	t	Захисні бокси
44423730-7	Manhole frames	6	t	Рами оглядових колодязів
44423740-0	Manhole covers	6	t	Кришки оглядових колодязів
44423750-3	Drain covers	6	t	Решітки ливнестоків
44423760-6	Access covers	6	t	Технологічні люки
44423790-5	Manhole step irons	6	t	Скоби-сходи оглядових колодязів
44423800-9	Metal stamps	5	f	Металеві пломби
44423810-2	Rubber stamps	6	t	Гумові пломби
44423850-4	Shapes	6	t	Пломбіратори
44423900-0	Sacrificial anodes	5	t	Захисні аноди
44424000-8	Meter-housing boxes and tape	4	f	Коробки для побутових лічильників та стрічки
44424100-9	Meter-housing boxes	5	t	Коробки для побутових лічильників
44424200-0	Adhesive tape	5	t	Клейкі стрічки
44424300-1	Reflective tape	5	t	Світловідбивні стрічки
44425000-5	Rings, seals, bands, sticks and grout packers	4	f	Кільця, прокладки, смужки, вставки та ущільнювачі
44425100-6	Elastic rings	5	f	Еластичні кільця
44425110-9	Self-adhesive reinforcement rings	6	t	Самоклейні кільця-ущільнювачі
44425200-7	Rubber seals	5	t	Гумові прокладки
44425300-8	Rubber bands	5	t	Гумові смужки
44425400-9	Grout packers	5	t	Ущільнювачі
44425500-0	Plastic sticks	5	t	Пластикові вставки
44430000-3	Armour plating	3	f	Армована обшивка
44431000-0	Cover plates	4	t	Накладки
44440000-6	Bearings	3	f	Вальниці
44441000-3	Microbeads	4	t	Мікрокульки
44442000-0	Roller bearings	4	t	Роликові вальниці
44450000-9	Mild steel	3	f	М’яка сталь
44451000-6	Coin blanks	4	t	Монетні заготовки
44452000-3	Coin boxes	4	t	Коробки для монет
44460000-2	Props and mining struts	3	f	Стояки та розпірки для шахт
44461000-9	Props	4	f	Стояки
44461100-0	Mine props	5	t	Стояки для шахт
44462000-6	Mining struts	4	t	Розпірки для шахт
44464000-0	Caterpillar tracks	4	t	Гусеничні рушії
44470000-5	Cast-iron products	3	t	Чавунні вироби
44480000-8	Miscellaneous fire-protection equipment	3	f	Протипожежне обладнання різне
44481000-5	Platforms ladders	4	f	Драбини з платформою
44481100-6	Fire ladders	5	t	Пожежні драбини
44482000-2	Fire-protection devices	4	f	Пристрої для пожежогасіння
44482100-3	Fire hoses	5	t	Пожежні шланги
44482200-4	Fire hydrants	5	t	Пожежні гідранти
44500000-5	Tools, locks, keys, hinges, fasteners, chain and springs	2	f	Знаряддя, замки, ключі, петлі, кріпильні деталі, ланцюги та пружини
44510000-8	Tools	3	f	Знаряддя
44511000-5	Hand tools	4	f	Ручні знаряддя
44511100-6	Spades and shovels	5	f	Штикові та совкові лопати
44511110-9	Spades	6	t	Штикові лопати
44511120-2	Shovels	6	t	Совкові лопати
44511200-7	Gardening forks	5	t	Садові вила
44511300-8	Mattocks, picks, hoes, rakes and beach rakes	5	f	Мотики, кайла, сапи, граблі та пляжні граблі
44511310-1	Mattocks	6	t	Мотики
44511320-4	Picks	6	t	Кайла
44511330-7	Hoes	6	t	Сапи
44511340-0	Rakes	6	f	Граблі
44511341-7	Beach rakes	7	t	Пляжні граблі
44511400-9	Axes	5	t	Сокири
44511500-0	Hand saws	5	f	Ручні пилки
44511510-3	Handsaw blades	6	t	Полотна до ручних пилок
44512000-2	Miscellaneous hand tools	4	f	Ручні інструменти різні
44512100-3	Chisels	5	t	Долото
44512200-4	Pliers	5	f	Щипці
44512210-7	Crimping pliers	6	t	Обтискні щипці
44512300-5	Hammers	5	t	Молотки
44512400-6	Wire grips	5	t	Монтажні затискачі
44512500-7	Spanners	5	t	Гайкові ключі
44512600-8	Roadworking tools	5	f	Інструменти для дорожніх робіт
44512610-1	Spikes for piercing road surfaces	6	t	Ломи для пробивання дорожнього покриття
44512700-9	Files or rasps	5	t	Напилки чи рашпілі
44512800-0	Screwdrivers	5	t	Викрутки
44512900-1	Drill bits, screwdriver bits and other accessories	5	f	Свердла, жала до викруток та інше приладдя
44512910-4	Drill bits	6	t	Свердла
44512920-7	Screwdriver bits	6	t	Жала до викруток
44512930-0	Tool carriers	6	t	Руків’я інструментів
44512940-3	Tool kits	6	t	Набори інструментів
44513000-9	Treadle-operated tools	4	t	Інструменти з педальним приводом
44514000-6	Tool handles and tool parts	4	f	Руків’я та деталі інструментів
44514100-7	Tool handles	5	t	Руків’я інструментів
44514200-8	Tool parts	5	t	Деталі інструментів
44520000-1	Locks, keys and hinges	3	f	Замки, ключі та петлі
44521000-8	Miscellaneous padlocks and locks	4	f	Навісні та врізні замки різні
44521100-9	Locks	5	f	Врізні замки
44521110-2	Door locks	6	t	Дверні замки
44521120-5	Electronic security lock	6	t	Електронні кодові замки
44521130-8	Enhanced security lock	6	t	Замки з високим ступенем захисту та посиленим кріпленням
44521140-1	Furniture locks	6	t	Замки до меблів
44521200-0	Padlocks and chains	5	f	Навісні замки та ланцюжки
44521210-3	Padlocks	6	t	Навісні замки
44522000-5	Clasps, lock parts and keys	4	f	Клямки, деталі замків та ключі
44522100-6	Clasps	5	t	Клямки
44522200-7	Keys	5	t	Ключі
44522300-8	Parts of padlocks	5	t	Частини навісних замків
44522400-9	Parts of locks	5	t	Частини врізних замків
44523000-2	Hinges, mountings and fittings	4	f	Петлі, монтажна арматура та фурнітура
44523100-3	Hinges	5	t	Петлі
44523200-4	Mountings	5	t	Монтажна арматура
44523300-5	Fittings	5	t	Фурнітура
44530000-4	Fasteners	3	f	Кріпильні деталі
44531000-1	Threaded fasteners	4	f	Кріпильні деталі з наріззю
44531100-2	Wood screws	5	t	Шурупи
44531200-3	Screw hooks or screw rings	5	t	Шурупи з гачком або кільцем
44531300-4	Self-tapping screws	5	t	Саморізи
44531400-5	Bolts	5	t	Болти
44531500-6	Flange jointing sets	5	f	Комплекти фланцевих з’єднань
44531510-9	Bolts and screws	6	t	Болти та шурупи
44531520-2	Coach bolts and screws	6	t	Болти та шурупи із шестигранним шліцем
44531600-7	Nuts	5	t	Гайки
44531700-8	Iron or steel threaded articles	5	t	Залізні чи сталеві вироби з наріззю
44532000-8	Non-threaded fasteners	4	f	Кріпильні деталі без нарізі
44532100-9	Rivets	5	t	Заклепки
44532200-0	Washers	5	t	Шайби
44532300-1	Cotter pins	5	t	Шплінти
44532400-2	Steel fishplates	5	t	Сталеві стикові накладки
44533000-5	Copper fasteners	4	t	Мідні кріпильні деталі
44541000-4	Articulated chain	4	t	Шарнірні ланцюги
44542000-1	Parts of chain	4	t	Елементи ланцюгів
44550000-0	Springs	3	t	Пружини
44600000-6	Tanks, reservoirs and containers; central-heating radiators and boilers	2	f	Цистерни, резервуари та контейнери; радіатори та котли для центрального опалення
44610000-9	Tanks, reservoirs, containers and pressure vessels	3	f	Цистерни, резервуари, контейнери та посудини високого тиску
44611000-6	Tanks	4	f	Цистерни
44611100-7	Air cylinders	5	f	Балони для повітря
44611110-0	Compressed-air cylinders	6	t	Балони для стисненого повітря
44611200-8	Breathing apparatus	5	t	Дихальні апарати
44611400-0	Storage tanks	5	f	Цистерни-сховища
44611410-3	Oil-storage tanks	6	t	Цистерни для зберігання нафтопродуктів
44611420-6	Sludge-storage tanks	6	t	Цистерни для мулу
44611500-1	Water tanks	5	t	Цистерни для води
44611600-2	Reservoirs	5	t	Резервувари
44612000-3	Liquefied-gas containers	4	f	Балони для скрапленого газу
44612100-4	Gas cylinders	5	t	Балони для газу
44612200-5	Gas tanks	5	t	Цистерни для газу
44613000-0	Large containers	4	f	Великогабаритні контейнери
44613110-4	Silos	6	t	Силоси
44613200-2	Refrigerated containers	5	f	Холодильні контейнери
44613210-5	Water chambers	6	t	Баки для води
44613300-3	Standard freight containers	5	t	Стандартні вантажні контейнери
44613400-4	Storage containers	5	t	Контейнери для зберігання
44613500-5	Water containers	5	t	Контейнери для води
44613600-6	Wheeled containers	5	t	Колісні контейнери
44613700-7	Refuse skips	5	t	Контейнери для сміття
44613800-8	Containers for waste material	5	t	Контейнери для відходів
44614000-7	Casks	4	f	Діжки
44614100-8	Storage bins	5	t	Накопичувальні бункери
44614300-0	Container storage system	5	f	Контейнерні системи
44614310-3	Stacking machinery	6	t	Штабелювальні механізми
44615000-4	Pressure vessels	4	f	Посудини високого тиску
44615100-5	Steel pressure vessels	5	t	Сталеві посудини високого тиску
44616000-1	Drums	4	f	Бочки
44616200-3	Waste drums	5	t	Бочки для відходів
44617000-8	Boxes	4	f	Коробки
44617100-9	Cartons	5	t	Картонні коробки
44617200-0	Meter housings	5	t	Корпуси лічильників
44617300-1	Prefabricated boxes	5	t	Збірні ящики
44618000-5	Light containers, corks, tops for containers, vats and lids	4	f	Легкі контейнери, корки, кришки контейнерів, чани та кришки
44618100-6	Light containers	5	t	Легкі контейнери
44618300-8	Corks, stoppers, tops for containers and lids	5	f	Корки, пробки, кришки контейнерів та інші кришки
44618310-1	Corks	6	t	Корки
44618320-4	Stoppers	6	t	Пробки
44618330-7	Tops for containers	6	t	Кришки контейнерів
44618340-0	Lids	6	t	Кришки
44618350-3	Plastic caps	6	t	Пластикові пробки
44618400-9	Cans	5	f	Металеві банки
44618420-5	Food cans	6	t	Банки для консервування
44618500-0	Vats	5	t	Чани
44619000-2	Other containers	4	f	Інші контейнери
44619100-3	Cases	5	t	Чохли
44619200-4	Cable drums	5	t	Кабельні барабани
44619300-5	Crates	5	t	Ящики
44619400-6	Barrels	5	t	Дерев’яні діжки
44619500-7	Pallet boxes	5	t	Ящики-піддони
44620000-2	Central-heating radiators and boilers and parts	3	f	Радіатори і котли для систем центрального опалення та їх деталі
44621000-9	Radiators and boilers	4	f	Радіатори та котли
44621100-0	Radiators	5	f	Радіатори
44621110-3	Central-heating radiators	6	f	Радіатори для систем центрального опалення
44621111-0	Non-electrically-heated central-heating radiators	7	t	Неелектричні радіатори для систем центрального опалення
44621112-7	Parts of central-heating radiators	7	t	Частини радіаторів систем центрального опалення
44621200-1	Boilers	5	f	Котли
44621210-4	Water boilers	6	t	Водяні котли
44621220-7	Central-heating boilers	6	f	Котли для систем центрального опалення
44621221-4	Parts of central-heating boilers	7	t	Частини котлів для систем центрального опалення
44622000-6	Heat-recovery systems	4	f	Системи рекуперації тепла
44622100-7	Heat-recovery equipment	5	t	Обладнання для рекуперації тепла
44800000-8	Paints, varnishes and mastics	2	f	Фарби, лаки, друкарська фарба та мастики
44810000-1	Paints	3	f	Фарби
44811000-8	Road paint	4	t	Фарби для дорожньої розмітки
44812000-5	Artists' paints	4	f	Художні фарби
44812100-6	Enamels and glazes	5	t	Емалі та глазурі
44812200-7	Oil and water paints	5	f	Олійні та водні фарби
44812210-0	Oil paints	6	t	Олійні фарби
44812220-3	Water paints	6	t	Водні фарби
44812300-8	Students' paints	5	f	Фарби для дитячої творчості
44812310-1	Colours in sets	6	t	Набори фарб
44812320-4	Signboard painters' colours	6	t	Фарби для оформлення вивісок
44812400-9	Decorating supplies	5	t	Оздоблювальні матеріали
44820000-4	Varnishes	3	t	Лаки
44830000-7	Mastics, fillers, putty and solvents	3	f	Мастики, шпаклівки, замазки та розчинники
44831000-4	Mastics, fillers, putty	4	f	Мастики, шпаклівки, замазки
44831100-5	Mastics	5	t	Мастики
44831200-6	Fillers	5	t	Шпаклівки
44831300-7	Putty	5	t	Замазки
44831400-8	Grout	5	t	Цементне тісто
44832000-1	Solvents	4	f	Розчинники
44832100-2	Paint stripper	5	t	Розчинники лаків і фарб
44832200-3	Thinners	5	t	Розріджувачі
44900000-9	Stone for construction, limestone, gypsum and slate	2	f	Будівельний камінь, вапняк, гіпс і сланець
44910000-2	Stone for construction	3	f	Будівельний камінь
44911000-9	Marble and calcarous building stone	4	f	Мармур та будівельний вапняковий камінь
44911100-0	Marble	5	t	Мармур
44911200-1	Travertine	5	t	Травертин
44912000-6	Miscellaneous building stone	4	f	Будівельний камінь різний
44912100-7	Granite	5	t	Граніт
44912200-8	Sandstone	5	t	Пісковик
44912300-9	Basalt	5	t	Базальт
44912400-0	Kerbstones	5	t	Бордюрний камінь
44920000-5	Limestone, gypsum and chalk	3	f	Вапняк, гіпс і крейда
44921000-2	Limestone and gypsum	4	f	Вапняк і гіпс
44921100-3	Gypsum	5	t	Гіпс
44921200-4	Lime	5	f	Вапно
44921210-7	Powdered lime	6	t	Вапняний порошок
44921300-5	Limestone	5	t	Вапняк
44922000-9	Chalk and dolomite	4	f	Крейда та доломіт
44922100-0	Chalk	5	t	Крейда
44922200-1	Dolomite	5	t	Доломіт
44930000-8	Slate	3	t	Сланець
45000000-7	Construction work	1	f	Будівельні роботи
45100000-8	Site preparation work	2	f	Підготовка будівельного майданчика
45110000-1	Building demolition and wrecking work and earthmoving work	3	f	Руйнування та знесення будівель і земляні роботи
45111000-8	Demolition, site preparation and clearance work	4	f	Руйнування будівель, підготовки і розчищання будівельного майданчика
45111100-9	Demolition work	5	t	Руйнівні роботи
45111200-0	Site preparation and clearance work	5	f	Підготовка і розчищання будівельного майданчика
45111210-3	Blasting and associated rock-removal work	6	f	Підривні роботи та супутні роботи з виймання гірських порід
45111211-0	Blasting work	7	t	Підривні роботи
45111212-7	Rock-removal work	7	t	Виймання гірських порід
45111213-4	Site-clearance work	7	t	Розчищання будівельного майданчика
45111214-1	Blast-clearing work	7	t	Струминне очищення
45111220-6	Scrub-removal work	6	t	Абразивне очищення
45111230-9	Ground-stabilisation work	6	t	Стабілізування ґрунту
45111240-2	Ground-drainage work	6	t	Дренування ґрунту
45111250-5	Ground investigation work	6	t	Дослідження ґрунту
45111260-8	Site-preparation work for mining	6	t	Підготовка будівельного майданчика до проведення гірничих робіт
45111290-7	Primary works for services	6	f	Підготовчі роботи
45111291-4	Site-development work	7	t	Улаштування будівельного майданчика
45111300-1	Dismantling works	5	f	Демонтажні роботи
45111310-4	Dismantling works for military installations	6	t	Демонтаж військових об’єктів
45111320-7	Dismantling works for security installations	6	t	Демонтаж охоронних об’єктів
45112000-5	Excavating and earthmoving work	4	f	Землерийні та інші земляні роботи
45112100-6	Trench-digging work	5	t	Риття траншей
45112200-7	Soil-stripping work	5	f	Знімання шару ґрунту
45112210-0	Topsoil-stripping work	6	t	Знімання верхнього шару ґрунту
45112300-8	Infill and land-reclamation work	5	f	Зворотне засипання та рекультивація ґрунту
45112310-1	Infill work	6	t	Зворотне засипання ґрунту
45112320-4	Land-reclamation work	6	t	Рекультивація ґрунту
45112330-7	Site-reclamation work	6	t	Рекультивація ґрунту в межах ділянки забудови
45112340-0	Soil-decontamination work	6	t	Очищення забрудненого ґрунту
45112350-3	Reclamation of waste land	6	t	Рекультивація пустирів
45112360-6	Land rehabilitation work	6	t	Відновлення земель
45112400-9	Excavating work	5	f	Землерийні роботи
45112410-2	Grave-digging work	6	t	Викопування могил
45112420-5	Basement excavation work	6	t	Риття котлованів для фундаменту
45112440-1	Terracing of hillsides	6	f	Терасування схилів
45112441-8	Terracing work	7	t	Терасування
45112450-4	Excavation work at archaeological sites	6	t	Землерийні роботи на місцях археологічних розкопок
45112500-0	Earthmoving work	5	t	Земляні роботи
45112600-1	Cut and fill	5	t	Виймання і засипання землі
45112700-2	Landscaping work	5	f	Ландшафтні роботи
45112710-5	Landscaping work for green areas	6	f	Благоустрій озеленених територій
45112711-2	Landscaping work for parks	7	t	Благоустрій парків
45112712-9	Landscaping work for gardens	7	t	Благоустрій садів
45112713-6	Landscaping work for roof gardens	7	t	Благоустрій садів на даху
45112714-3	Landscaping work for cemeteries	7	t	Благоустрій кладовищ
45112720-8	Landscaping work for sports grounds and recreational areas	6	f	Благоустрій спортивних майданчиків і зон відпочинку
45112721-5	Landscaping work for golf courses	7	t	Благоустрій полів для гольфу
45112722-2	Landscaping work for riding areas	7	t	Благоустрій полів для верхової їзди
45112723-9	Landscaping work for playgrounds	7	t	Благоустрій ігрових майданчиків
45112730-1	Landscaping work for roads and motorways	6	t	Благоустрій доріг і шосе
45112740-4	Landscaping work for airports	6	t	Благоустрій аеропортів
45113000-2	Siteworks	4	t	Роботи на будівельному майданчику
45120000-4	Test drilling and boring work	3	f	Розвідувальне свердління та буріння
45121000-1	Test drilling work	4	t	Розвідувальне свердління
45122000-8	Test boring work	4	t	Розвідувальне буріння
45200000-9	Works for complete or part construction and civil engineering work	2	f	Роботи, пов’язані з об’єктами завершеного чи незавершеного будівництва та об’єктів цивільного будівництва
45210000-2	Building construction work	3	f	Будівництво будівель
45211000-9	Construction work for multi-dwelling buildings and individual houses	4	f	Будівництво багатоквартирних житлових та індивідуальних будинків
45211100-0	Construction work for houses	5	t	Будівництво житлових будинків
45211200-1	Sheltered housing construction work	5	t	Будівництво житла для соціально незахищених категорій населення
45211300-2	Houses construction work	5	f	Будівництво будинків
45211310-5	Bathrooms construction work	6	t	Будівництво санітарних вузлів
45211320-8	Porches construction work	6	t	Будівництво ґанків
45211340-4	Multi-dwelling buildings construction work	6	f	Будівництво багатоквартирних житлових будинків
45211341-1	Flats construction work	7	t	Будівництво квартир
45211350-7	Multi-functional buildings construction work	6	t	Будівництво багатофункційних комплексів
45211360-0	Urban development construction work	6	t	Містобудівельні роботи
45211370-3	Construction works for saunas	6	t	Будівництво саун
45212000-6	Construction work for buildings relating to leisure, sports, culture, lodging and restaurants	4	f	Будівництво закладів дозвілля, спортивних, культурних закладів, закладів тимчасового розміщення та ресторанів
45212100-7	Construction work of leisure facilities	5	f	Будівництво об’єктів дозвілля
45212110-0	Leisure centre construction work	6	t	Будівництво центрів дозвілля
45212120-3	Theme park construction work	6	t	Будівництво тематичних парків
45212130-6	Amusement park construction work	6	t	Будівництво парків розваг
45212140-9	Recreation installation	6	t	Встановлення об’єктів дозвілля
45212150-2	Cinema construction work	6	t	Будівництво кінотеатрів
45212160-5	Casino construction work	6	t	Будівництво казино
45212170-8	Entertainment building construction work	6	f	Будівництво розважальних закладів
45212171-5	Entertainment centre construction work	7	t	Будівництво розважальних центрів
45212172-2	Recreation centre construction work	7	t	Будівництво центрів відпочинку
45212180-1	Ticket offices construction work	6	t	Будівництво квиткових кас
45212190-4	Sun-protection works	6	t	Зведення сонцезахисних конструкцій
45212200-8	Construction work for sports facilities	5	f	Будівництво спортивних об’єктів
45212210-1	Single-purpose sports facilities construction work	6	f	Будівництво спеціалізованих спортивних об’єктів
45212211-8	Ice rink construction work	7	t	Будівництво ковзанок
45212212-5	Construction work for swimming pool	7	t	Будівництво плавальних басейнів
45212213-2	Sport markings works	7	t	Нанесення розмітки на спортивних об’єктах
45212220-4	Multi-purpose sports facilities construction work	6	f	Будівництво багатоцільових спортивних об’єктів
45212221-1	Construction work in connection with structures for sports ground	7	t	Зведення конструкцій на спортивних майданчиках
45212222-8	Gymnasium construction work	7	t	Будівництво гімнастичних зал
45212223-5	Winter-sports facilities construction work	7	t	Будівництво спортивних об’єктів для зимових видів спорту
45212224-2	Stadium construction work	7	t	Будівництво стадіонів
45212225-9	Sports hall construction work	7	t	Будівництво спортивних зал
45212230-7	Installation of changing rooms	6	t	Улаштування кімнат для перевдягання
45212290-5	Repair and maintenance work in connection with sports facilities	6	t	Ремонт і технічне обслуговування спортивних об’єктів
45212300-9	Construction work for art and cultural buildings	5	f	Будівництво мистецьких і культурних закладів
45212310-2	Construction work for buildings relating to exhibitions	6	f	Будівництво виставкових приміщень
45212311-9	Art gallery construction work	7	t	Будівництво художніх галерей
45212312-6	Exhibition centre construction work	7	t	Будівництво виставкових центрів
45212313-3	Museum construction work	7	t	Будівництво музеїв
45212314-0	Historical monument or memorial construction work	7	t	Будівництво історичних пам’ятників або меморіалів
45212320-5	Construction work for buildings relating to artistic performances	6	f	Будівництво будівель, призначених для показу творів виконавського мистецтва
45212321-2	Auditorium construction work	7	t	Будівництво глядацьких зал
45212322-9	Theatre construction work	7	t	Будівництво театрів
45212330-8	Library construction work	6	f	Будівництво бібліотек
45212331-5	Multimedia library construction work	7	t	Будівництво мультимедійних бібліотек
45212340-1	Lecture hall construction work	6	t	Будівництво конференц-зал
45212350-4	Buildings of particular historical or architectural interest	6	f	Будівництво будівель особливої історичної чи архітектурної цінності
45212351-1	Prehistoric monument construction work	7	t	Будівництво доісторичних пам’яток
45212352-8	Industrial monument construction work	7	t	Будівництво пам’яток промислової архітектури
45212353-5	Palace construction work	7	t	Будівництво палаців
45212354-2	Castle construction work	7	t	Будівництво замків
45212360-7	Religious buildings construction work	6	f	Будівництво будівель і споруд релігійного призначення
45212361-4	Church construction work	7	t	Будівництво церков
45212400-0	Accommodation and restaurant buildings	5	f	Будівництво засобів розміщення та ресторанів
45212410-3	Construction work for lodging buildings	6	f	Будівництво закладів тимчасового розміщення
45212411-0	Hotel construction work	7	t	Будівництво готелів
45212412-7	Hostel construction work	7	t	Будівництво хостелів / гуртожитків
45212413-4	Short-stay accommodation construction work	7	t	Будівництво засобів тимчасового розміщування
45212420-6	Construction work for restaurants and similar facilities	6	f	Будівництво ресторанів і подібних закладів
45212421-3	Restaurant construction work	7	t	Будівництво ресторанів
45212422-0	Canteen construction work	7	t	Будівництво їдалень
45212423-7	Cafeteria construction work	7	t	Будівництво кафе
45212500-1	Kitchen or restaurant conversion	5	t	Перебудова кухонь або ресторанів
45212600-2	Pavilion construction work	5	t	Будівництво павільйонів
45213000-3	Construction work for commercial buildings, warehouses and industrial buildings, buildings relating to transport	4	f	Будівництво торгових будівель, складів і промислових будівель, об’єктів транспортної інфраструктури
45213100-4	Construction work for commercial buildings	5	f	Будівництво торгових будівель
45213110-7	Shop buildings construction work	6	f	Будівництво магазинів
45213111-4	Shopping centre construction work	7	t	Будівництво торгових центрів
45213112-1	Shop units construction work	7	t	Будівництво торгових об’єктів
45213120-0	Post office construction work	6	t	Будівництво поштових відділень
45213130-3	Bank construction work	6	t	Будівництво банків
45213140-6	Market construction work	6	f	Будівництво ринків
45213141-3	Covered market construction work	7	t	Будівництво критих ринків
45213142-0	Open-air market construction work	7	t	Будівництво ринків просто неба
45213150-9	Office block construction work	6	t	Будівництво офісних комплексів
45213200-5	Construction work for warehouses and industrial buildings	5	f	Будівництво складів і промислових будівель
45213210-8	Cold-storage installations	6	t	Будівництво складів-холодильників
45213220-1	Construction work for warehouses	6	f	Будівництво складів
45213221-8	Warehouse stores construction work	7	t	Будівництво складів-магазинів
45213230-4	Abattoir construction work	6	t	Будівництво боєнь
45213240-7	Agricultural buildings construction work	6	f	Будівництво будівель сільськогосподарського призначення
45213241-4	Barn construction work	7	t	Будівництво комор
45213242-1	Cowsheds construction work	7	t	Будівництво хлівів
45213250-0	Construction work for industrial buildings	6	f	Будівництво промислових будівель
45213251-7	Industrial units construction work	7	t	Будівництво промислових об’єктів
45213252-4	Workshops construction work	7	t	Будівництво майстерень
45213260-3	Stores depot construction work	6	t	Будівництво товарних складів
45213270-6	Construction works for recycling station	6	t	Будівництво заводів з переробки відходів
45213280-9	Construction works for compost facility	6	t	Будівництво компостувальних заводів
45213300-6	Buildings associated with transport	5	f	Будівництво об’єктів транспортної інфраструктури
45213310-9	Construction work for buildings relating to road transport	6	f	Будівництво об’єктів інфраструктури дорожнього транспорту
45213311-6	Bus station construction work	7	t	Будівництво автобусних станцій
45213312-3	Car park building construction work	7	t	Будівництво автомобільних стоянок
45213313-0	Service-area building construction work	7	t	Будівництво станцій технічного обслуговування
45213314-7	Bus garage construction work	7	t	Будівництво автобусних парків
45213315-4	Bus-stop shelter construction work	7	t	Будівництво критих автобусних зупинок
45213316-1	Installation works of walkways	7	t	Будівництво пішохідних доріжок
45213320-2	Construction work for buildings relating to railway transport	6	f	Будівництво об’єктів інфраструктури залізничного транспорту
45213321-9	Railway station construction work	7	t	Будівництво залізничних станцій
45213322-6	Rail terminal building construction work	7	t	Будівництво залізничних вокзалів
45213330-5	Construction work for buildings relating to air transport	6	f	Будівництво об’єктів інфраструктури повітряного транспорту
45213331-2	Airport buildings construction work	7	t	Будівництво аеропортів
45213332-9	Airport control tower construction work	7	t	Будівництво командно-диспетчерських пунктів
45213333-6	Installation works of airport check-in counters	7	t	Встановлення стійок реєстрації пасажирів в аеропортах
45213340-8	Construction work for buildings relating to water transport	6	f	Будівництво об’єктів інфраструктури водного транспорту
45213341-5	Ferry terminal building construction work	7	t	Будівництво поромних вокзалів
45213342-2	Ro-ro terminal construction work	7	t	Будівництво вантажно-розвантажувальних терміналів
45213350-1	Construction work for buildings relating to various means of transport	6	f	Будівництво об’єктів інфраструктури різних видів транспорту
45213351-8	Maintenance hangar construction work	7	t	Будівництво ангарів для технічного обслуговування
45213352-5	Service depot construction work	7	t	Будівництво експлуатаційних депо
45213353-2	Installation works of passenger boarding bridges	7	t	Монтаж телескопічних трапів
45213400-7	Installation of staff rooms	5	t	Улаштування службових приміщень
45214000-0	Construction work for buildings relating to education and research	4	f	Будівництво освітніх та науково-дослідних закладів
45214100-1	Construction work for kindergarten buildings	5	t	Будівництво дитячих садків
45214200-2	Construction work for school buildings	5	f	Будівництво шкіл
45214210-5	Primary school construction work	6	t	Будівництво початкових шкіл
45214220-8	Secondary school construction work	6	t	Будівництво середніх загальноосвітніх шкіл
45214230-1	Special school construction work	6	t	Будівництво спеціалізованих шкіл
45214300-3	Construction work for college buildings	5	f	Будівництво коледжів
45214310-6	Vocational college construction work	6	t	Будівництво професійних училищ
45214320-9	Technical college construction work	6	t	Будівництво технічних училищ
45214400-4	Construction work for university buildings	5	f	Будівництво університетів
45214410-7	Polytechnic construction work	6	t	Будівництво політехнічних інститутів
45214420-0	Lecture theatre construction work	6	t	Будівництво лекційних аудиторій
45214430-3	Language laboratory construction work	6	t	Будівництво лінгафонних кабінетів
45214500-5	Construction work for buildings of further education	5	t	Будівництво закладів післядипломної освіти
45214600-6	Construction work for research buildings	5	f	Будівництво науково-дослідних закладів
45214610-9	Laboratory building construction work	6	t	Будівництво лабораторій
45214620-2	Research and testing facilities construction work	6	t	Будівництво науково-дослідних і випробувальних закладів
45214630-5	Scientific installations	6	f	Спорудження наукових об’єктів
45214631-2	Installation works of cleanrooms	7	t	Влаштування чистих приміщень
45214640-8	Meteorological stations construction work	6	t	Будівництво метеорологічних станцій
45214700-7	Construction work for halls of residence	5	f	Будівництво студентських гуртожитків
45214710-0	Entrance hall construction work	6	t	Будівництво вестибюлів
45214800-8	Training facilities building	5	t	Будівництво навчальних закладів
45216250-1	Trench defences construction work	6	t	Риття оборонних траншей
45215000-7	Construction work for buildings relating to health and social services, for crematoriums and public conveniences	4	f	Будівництво закладів охорони здоров’я та будівель соціальних служб, крематоріїв і громадських убиралень
45215100-8	Construction work for buildings relating to health	5	f	Будівництво закладів охорони здоров’я
45215110-1	Spa construction work	6	t	Будівництво спа-курортів
45215120-4	Special medical building construction work	6	t	Будівництво спеціалізованих медичних закладів
45215130-7	Clinic construction work	6	t	Будівництво клінік
45215140-0	Hospital facilities construction work	6	f	Будівництво закладів лікарняного типу
45215141-7	Operating theatre construction work	7	t	Будівництво операційних блоків
45215142-4	Intensive-care unit construction work	7	t	Будівництво відділень інтенсивної терапії
45215143-1	Diagnostic screening room construction work	7	t	Будівництво кабінетів діагностичного обстеження
45215144-8	Screening rooms construction work	7	t	Будівництво оглядових кабінетів
45215145-5	Fluoroscopy room construction work	7	t	Будівництво рентгенологічних кабінетів
45215146-2	Pathology room construction work	7	t	Будівництво кабінетів патологічних досліджень
45215147-9	Forensic room construction work	7	t	Будівництво кабінетів судової медицини
45215148-6	Catheter room construction work	7	t	Будівництво кабінетів катетеризації
45215200-9	Construction work for social services buildings	5	f	Будівництво будівель соціальних служб
45215210-2	Construction work for subsidised residential accommodation	6	f	Будівництво соціального житла
45215212-6	Retirement home construction work	7	t	Будівництво будинків пристарілих
45215213-3	Nursing home construction work	7	t	Будівництво стаціонарних соціально-медичних установ
45215214-0	Residential homes construction work	7	t	Будівництво будинків-інтернатів
45215215-7	Children's home construction work	7	t	Будівництво дитячих будинків-інтернатів
45215220-5	Construction work for social facilities other than subsidised residential accommodation	6	f	Будівництво об’єктів соціальної сфери, крім соціального житла
45215221-2	Daycare centre construction work	7	t	Будівництво центрів денного догляду
45215222-9	Civic centre construction work	7	t	Будівництво громадських центрів
45215300-0	Construction work for crematoriums	5	t	Будівництво крематоріїв
45215400-1	Cemetery works	5	t	Кладовищні роботи
45215500-2	Public conveniences	5	t	Будівництво громадських убиралень
45216000-4	Construction work for buildings relating to law and order or emergency services and for military buildings	4	f	Будівництво будівель правоохоронних органів та органів охорони громадського порядку чи служб надзвичайних ситуацій і будівель та приміщень військового призначення
45216100-5	Construction work for buildings relating to law and order or emergency services	5	f	Будівництво будівель правоохоронних органів та органів охорони громадського порядку чи служб надзвичайних ситуацій
45216110-8	Construction work for buildings relating to law and order	6	f	Будівництво будівель правоохоронних органів та органів охорони громадського порядку
45216111-5	Police station construction work	7	t	Будівництво поліцейських відділень
45216112-2	Court building construction work	7	t	Будівництво будівель судів
45216113-9	Prison building construction work	7	t	Будівництво пенітенціарних закладів
45216114-6	Parliament and public assembly buildings	7	t	Будівництво будівель парламенту та будівель для громадських зібрань
45216120-1	Construction work for buildings relating to emergency services	6	f	Будівництво будівель служб надзвичайних ситуацій
45216121-8	Fire station construction work	7	t	Будівництво пожежних станцій
45216122-5	Ambulance station construction work	7	t	Будівництво пунктів швидкої медичної допомоги
45216123-2	Mountain-rescue building construction work	7	t	Будівництво будівель гірських пошуково-рятувальних служб
45216124-9	Lifeboat station construction work	7	t	Будівництво рятувальних станцій водних на водних об’єктах
45216125-6	Emergency-services building construction work	7	t	Будівництво будівель аварійних служб
45216126-3	Coastguard building construction work	7	t	Будівництво пунктів берегової охорони
45216127-0	Rescue-service station construction work	7	t	Будівництво аварійно-рятувальних станцій
45216128-7	Lighthouse construction work	7	t	Будівництво маяків
45216129-4	Protective shelters	7	t	Будівництво сховищ
45216200-6	Construction work for military buildings and installations	5	f	Будівництво будівель і споруд військового призначення
45216220-2	Military bunker construction work	6	t	Будівництво військових бункерів
45216230-5	Military shelter construction work	6	t	Будівництво військових укриттів
45217000-1	Inflatable buildings construction work	4	t	Зведення надувних конструкцій
45220000-5	Engineering works and construction works	3	f	Інженерні та будівельні роботи
45221000-2	Construction work for bridges and tunnels, shafts and subways	4	f	Будівництво мостів і тунелів, шахт і метрополітенів
45221100-3	Construction work for bridges	5	f	Будівництво мостів
45221110-6	Bridge construction work	6	f	Мостобудівельні роботи
45221111-3	Road bridge construction work	7	t	Будівництво автодорожніх мостів
45221112-0	Railway bridge construction work	7	t	Будівництво залізничних мостів
45221113-7	Footbridge construction work	7	t	Будівництво пішохідних мостів
45221114-4	Construction work for iron bridges	7	t	Будівництво залізних мостів
45221115-1	Construction work for steel bridges	7	t	Будівництво сталевих мостів
45221117-5	Weighbridge construction work	7	t	Спорудження автомобільних ваг
45221118-2	Pipeline-carrying bridge construction work	7	t	Будівництво мостів-трубопроводів
45221119-9	Bridge renewal construction work	7	t	Реконструкція мостів
45221120-9	Viaduct construction work	6	f	Будівництво мостів-віадуків
45221121-6	Road viaduct construction work	7	t	Будівництво транспортних мостів-віадуків
45221122-3	Railway viaduct construction work	7	t	Будівництво залізничних мостів-віадуків
45221200-4	Construction work for tunnels, shafts and subways	5	f	Будівництво тунелів, шахт і метрополітенів
45221210-7	Covered or partially-covered excavations	6	f	Будівництво траншей відкритого чи закритого типу
45221211-4	Underpass	7	t	Будівництво підземних переходів
45221213-8	Covered or partially-covered railway excavations	7	t	Будівництво залізничних тунелів відкритого чи закритого типу
45221214-5	Covered or partially-covered road excavations	7	t	Будівництво автомобільних тунелів відкритого чи закритого типу
45221220-0	Culverts	6	t	Укладення дренажних труб
45221230-3	Shafts	6	t	Будівництво шахт
45221240-6	Construction work for tunnels	6	f	Будівництво тунелів
45221241-3	Road tunnel construction work	7	t	Будівництво автомобільних тунелів
45221242-0	Railway tunnel construction work	7	t	Будівництво залізничних тунелів
45221243-7	Pedestrian tunnel construction work	7	t	Будівництво пішохідних тунелів
45221244-4	Canal tunnel construction work	7	t	Будівництво підводних тунелів
45221245-1	Under-river tunnel construction work	7	t	Будівництво тунелів під річками
45221246-8	Undersea tunnel construction work	7	t	Будівництво тунелів під морським дном
45221247-5	Tunnelling works	7	t	Прокладання тунелів
45221248-2	Tunnel linings construction work	7	t	Укріплення тунелів
45221250-9	Underground work other than tunnels, shafts and subways	6	t	Підземні роботи, крім прокладання тунелів, шахт і метрополітенів
45222000-9	Construction work for engineering works except bridges, tunnels, shafts and subways	4	f	Зведення інженерних споруд, окрім мостів, тунелів, шахт і метрополітенів
45222100-0	Waste-treatment plant construction work	5	f	Будівництво заводів з переробки відходів
45222110-3	Waste disposal site construction work	6	t	Будівництво пунктів утилізації відходів
45222200-1	Engineering work for military installations	5	t	Проектування військових об’єктів
45222300-2	Engineering work for security installations	5	t	Проектування охоронних об’єктів
45223000-6	Structures construction work	4	f	Спорудження конструкцій
45223100-7	Assembly of metal structures	5	f	Монтаж металевих конструкцій
45223110-0	Installation of metal structures	6	t	Встановлення металевих конструкцій
45223200-8	Structural works	5	f	Конструкційні роботи
45223210-1	Structural steelworks	6	t	Зведення тримальних сталевих конструкцій
45223220-4	Structural shell work	6	t	Влаштування тримальних оболонок конструкцій
45223300-9	Parking lot construction work	5	f	Будівництво автомобільних стоянок
45223310-2	Underground car park construction work	6	t	Будівництво підземних автомобільних стоянок
45223320-5	Park-and-ride facility construction work	6	t	Будівництво перехоплювальних автомобільних стоянок
45223400-0	Radar station construction work	5	t	Будівництво радіолокаційних станцій
45223500-1	Reinforced-concrete structures	5	t	Зведення залізобетонних конструкцій
45223600-2	Dog kennels construction work	5	t	Будівництво собачих розплідників
45223700-3	Service area construction work	5	f	Будівництво станцій обслуговування
45223710-6	Motorway service area construction work	6	t	Будівництво станцій технічного обслуговування мототранспортних засобів
45223720-9	Petrol/gas stations construction work	6	t	Будівництво бензозаправних / газозаправних станцій
45223800-4	Assembly and erection of prefabricated structures	5	f	Монтаж і зведення збірних конструкцій
45223810-7	Prefabricated constructions	6	t	Монтаж збірних конструкцій
45223820-0	Prefabricated units and components	6	f	Монтаж збірних блоків та елементів
45223821-7	Prefabricated units	7	t	Монтаж збірних блоків
45223822-4	Prefabricated components	7	t	Монтаж збірних елементів
45230000-8	Construction work for pipelines, communication and power lines, for highways, roads, airfields and railways; flatwork	3	f	Будівництво трубопроводів, ліній зв’язку та електропередач, шосе, доріг, аеродромів і залізничних доріг; вирівнювання поверхонь
45231000-5	Construction work for pipelines, communication and power lines	4	f	Будівництво трубопроводів, ліній зв’язку та електропередач
45231100-6	General construction work for pipelines	5	f	Загальні трубопровідні роботи
45231110-9	Pipelaying construction work	6	f	Будівництво трубопроводів
45231111-6	Pipeline lifting and relaying	7	t	Піднімання та заміна трубопроводів
45231112-3	Installation of pipe system	7	t	Встановлення трубопровідних систем
45231113-0	Pipeline relaying works	7	t	Заміна трубопроводів
45231200-7	Construction work for oil and gas pipelines	5	f	Будівництво нафтових і газових трубопроводів
45231210-0	Construction work for oil pipelines	6	t	Будівництво нафтових трубопроводів
45231220-3	Construction work for gas pipelines	6	f	Будівництво газових трубопроводів
45231221-0	Gas supply mains construction work	7	t	Будівництво магістральних газопроводів
45231222-7	Gasholder works	7	t	Спорудження газгольдерів
45231223-4	Gas distribution ancillary work	7	t	Допоміжні газорозподільні роботи
45231300-8	Construction work for water and sewage pipelines	5	t	Роботи з прокладання водопроводів та каналізаційних трубопроводів
45231400-9	Construction work for electricity power lines	5	t	Прокладання ліній електропередач
45231500-0	Compressed-air pipeline work	5	f	Прокладання трубопроводів для пневматичних систем
45231510-3	Compressed-air pipeline work for mailing system	6	t	Прокладання трубопроводів для систем пневмопошти
45231600-1	Construction work for communication lines	5	t	Прокладання ліній зв’язку
45232000-2	Ancillary works for pipelines and cables	4	f	Допоміжні роботи з прокладання трубопроводів і кабелів
45232100-3	Ancillary works for water pipelines	5	f	Допоміжні роботи з прокладання водопроводів
45232120-9	Irrigation works	6	f	Зрошувальні роботи
45232121-6	Irrigation piping construction work	7	t	Прокладання зрошувальних трубопроводів
45232130-2	Storm-water piping construction work	6	t	Прокладання трубопровідних систем відводу дощової води
45232140-5	District-heating mains construction work	6	f	Будівництво магістральних теплових мереж
45232141-2	Heating works	7	t	Будівництво опалювальних систем
45232142-9	Heat-transfer station construction work	7	t	Будівництво теплорозподільних пунктів
45232150-8	Works related to water-distribution pipelines	6	f	Роботи, пов’язані з прокладанням водорозподільних трубопроводів
45232151-5	Water-main refurbishment construction work	7	t	Відновлення водопровідних магістралей
45232152-2	Pumping station construction work	7	t	Будівництво насосних станцій
45232153-9	Construction work for water towers	7	t	Спорудження водонапірних башт
45232154-6	Construction work of elevated tanks for drinking water	7	t	Спорудження водонапірних резервуарів з питною водою
45232200-4	Ancillary works for electricity power lines	5	f	Допоміжні роботи з прокладання ліній електропередач
45232210-7	Overhead line construction	6	t	Прокладання повітряних ліній електропередач
45232220-0	Substation construction work	6	f	Будівництво підстанцій
45232221-7	Transformer substation	7	t	Будівництво трансформаторних підстанцій
45232300-5	Construction and ancillary works for telephone and communication lines	5	f	Прокладання ліній телефонного та інших видів зв’язку і допоміжні роботи
45232310-8	Construction work for telephone lines	6	f	Прокладання ліній телефонного зв’язку
45232311-5	Roadside emergency telephone lines	7	t	Прокладання придорожніх ліній аварійного телефонного зв’язку
45232320-1	Cable broadcasting lines	6	t	Прокладання ліній мереж кабельного телерадіомовлення
45232330-4	Erection of aerials	6	f	Встановлення антен
45232331-1	Ancillary works for broadcasting	7	t	Допоміжні роботи з організації телерадіомовлення
45232332-8	Ancillary works for telecommunications	7	t	Допоміжні роботи у сфері телекомунікацій
45232340-7	Mobile-telephone base-stations construction work	6	t	Будівництво базових станцій мобільного телефонного зв’язку
45232400-6	Sewer construction work	5	f	Будівництво каналізаційних колекторів
45232410-9	Sewerage work	6	f	Будівництво каналізаційних трубопроводів
45232411-6	Foul-water piping construction work	7	t	Будівництво трубопроводів для відведення побутових стічних вод
45232420-2	Sewage work	6	f	Каналізаційні роботи
45232421-9	Sewage treatment works	7	t	Очищення стічних вод
45232422-6	Sludge-treatment works	7	t	Обробка мулу
45232423-3	Sewage pumping stations construction work	7	t	Будівництво каналізаційних насосних станцій
45232424-0	Sewage outfall construction work	7	t	Влаштування точок скидання стічних вод
45232430-5	Water-treatment work	6	f	Водоочисні роботи
45232431-2	Wastewater pumping station	7	t	Будівництво насосних станцій для відведення побутових стічних вод
45232440-8	Construction work for sewage pipes	6	t	Прокладання каналізаційних трубопроводів
45232450-1	Drainage construction works	6	f	Будівництво дренажних систем
45232451-8	Drainage and surface works	7	t	Дренажні та поверхневі роботи
45232452-5	Drainage works	7	t	Спорудження дренажних конструкцій
45232453-2	Drains construction work	7	t	Будівництво водостоків
45232454-9	Rain-water basin construction work	7	t	Будівництво колекторів дощової води
45232460-4	Sanitary works	6	t	Санітарно-технічні роботи
45232470-7	Waste transfer station	6	t	Станції перекачування стічних вод
45233000-9	Construction, foundation and surface works for highways, roads	4	f	Будівництво, влаштовування фундаменту та покриття шосе, доріг
45233100-0	Construction work for highways, roads	5	f	Будівництво шосе, доріг
45233110-3	Motorway construction works	6	t	Будівництво автомагістралей
45233120-6	Road construction works	6	f	Будівництво доріг
45233121-3	Main road construction works	7	t	Будівництво магістральних доріг
45233122-0	Ring road construction work	7	t	Будівництво кільцевих доріг
45233123-7	Secondary road construction work	7	t	Будівництво другорядних доріг
45233124-4	Trunk road construction work	7	t	Будівництво автострад
45233125-1	Road junction construction work	7	t	Будівництво дорожньо-транспортних вузлів
45233126-8	Grade-separated junction construction work	7	t	Будівництво багаторівневих дорожньо-транспортних вузлів
45233127-5	T-junction construction work	7	t	Будівництво Т-подібних перехресть
45233128-2	Roundabout construction work	7	t	Будівництво кругових перехресть
45233129-9	Crossroad construction work	7	t	Будівництво перехресть
45233130-9	Construction work for highways	6	f	Будівництво шосе
45233131-6	Construction work for elevated highways	7	t	Будівництво естакадних шосе
45233139-3	Highway maintenance work	7	t	Технічне обслуговування шосе
45233140-2	Roadworks	6	f	Дорожні роботи
45233141-9	Road-maintenance works	7	t	Технічне обслуговування доріг
45233142-6	Road-repair works	7	t	Ремонт доріг
45233144-0	Overpass construction work	7	t	Будівництво естакад
45233150-5	Traffic-calming works	6	t	Встановлення засобів примусового зниження швидкості дорожнього руху
45233160-8	Paths and other metalled surfaces	6	f	Будівництво доріжок та інших поверхонь із твердим покриттям
45233161-5	Footpath construction work	7	t	Будівництво тротуарів
45233162-2	Cycle path construction work	7	t	Будівництво велосипедних доріжок
45233200-1	Various surface works	5	f	Влаштування різних видів дорожнього покриття
45233210-4	Surface work for highways	6	t	Влаштування шосейного покриття
45233220-7	Surface work for roads	6	f	Влаштування дорожнього покриття
45233221-4	Road-surface painting work	7	t	Нанесення дорожньої розмітки
45233222-1	Paving and asphalting works	7	t	Брукування та асфальтування
45233223-8	Carriageway resurfacing works	7	t	Відновлення покриття проїжджої частини доріг
45233224-5	Dual carriageway construction work	7	t	Будівництво доріг із двома смугами для руху
45233225-2	Single carriageway construction work	7	t	Будівництво доріг із однією смугою для руху
45233226-9	Access road construction work	7	t	Будівництво під’їзних доріг
45233227-6	Slip road construction work	7	t	Будівництво об’їзних доріг
45233228-3	Surface coating construction work	7	t	Поверхнева обробка дорожнього покриття
45233229-0	Verge maintenance work	7	t	Технічне обслуговування узбіч
45233250-6	Surfacing work except for roads	6	f	Влаштування покриття, крім дорожнього
45233251-3	Resurfacing works	7	t	Відновлення покриття
45233252-0	Surface work for streets	7	t	Влаштування вуличного покриття
45233253-7	Surface work for footpaths	7	t	Влаштування тротуарного покриття
45233260-9	Pedestrian ways construction work	6	f	Будівництво пішохідних доріг
45233261-6	Pedestrian overpass construction work	7	t	Будівництво пішохідних мостів
45233262-3	Pedestrian zone construction work	7	t	Будівництво пішохідних зон
45233270-2	Parking-lot-surface painting work	6	t	Нанесення розмітки автомобільних стоянок
45233280-5	Erection of road-barriers	6	t	Спорудження дорожніх бар’єрів
45233290-8	Installation of road signs	6	f	Встановлення дорожніх знаків
45233291-5	Installation of bollards	7	t	Встановлення болардів
45233292-2	Installation of safety equipment	7	t	Встановлення запобіжного обладнання
45233293-9	Installation of street furniture	7	t	Встановлення вуличних меблів
45233294-6	Installation of road signals	7	t	Встановлення дорожнього сигнального обладнання
45233300-2	Foundation work for highways, roads, streets and footpaths	5	f	Влаштування фундаменту шосе, доріг, вулиць і тротуарів
45233310-5	Foundation work for highways	6	t	Влаштовування фундаменту шосе
45233320-8	Foundation work for roads	6	t	Влаштовування фундаменту доріг
45233330-1	Foundation work for streets	6	t	Влаштування фундаменту вулиць
45233340-4	Foundation work for footpaths	6	t	Влаштування фундаменту тротуарів
45234000-6	Construction work for railways and cable transport systems	4	f	Будівництво залізничних і канатних транспортних систем
45234100-7	Railway construction works	5	f	Будівництво залізничних доріг
45234110-0	Intercity railway works	6	f	Будівництво міжміських залізничних доріг
45234111-7	City railway construction work	7	t	Будівництво міських залізничних доріг
45234112-4	Railway depot construction work	7	t	Будівництво залізничних депо
45234113-1	Demolition of tracks	7	t	Демонтаж залізничних колій
45234114-8	Railway embankment construction work	7	t	Зведення залізничних насипів
45234115-5	Railway signalling works	7	t	Встановлення залізничного сигнального обладнання
45234116-2	Track construction works	7	t	Прокладання колій
45234120-3	Urban railway works	6	f	Прокладання колій міських залізничних доріг
45234121-0	Tramway works	7	t	Прокладання трамвайних колій
45234122-7	Underground railway works	7	t	Прокладання підземних залізничних колій
45234123-4	Partially underground railway works	7	t	Прокладання частково підземних залізничних колій
45234124-1	Underground passenger railway transport	7	t	Будівництво підземної пасажирської залізничної транспортної системи
45234125-8	Underground railway station	7	t	Будівництво станцій метро
45234126-5	Tramline construction works	7	t	Прокладання трамвайних маршрутів
45234127-2	Tramway depot construction work	7	t	Будівництво трамвайних депо
45234128-9	Tramway platforms construction work	7	t	Будівництво трамвайних платформ
45234129-6	Urban railway track construction works	7	t	Прокладання маршрутів міських залізничних колій
45234130-6	Ballast construction works	6	t	Насипання баласту
45234140-9	Level crossing construction works	6	t	Будівництво залізничних переїздів
45234160-5	Catenary's construction works	6	t	Будівництво контактної мережі
45234170-8	Locomotive-substations construction works	6	t	Будівництво локомотивних підстанцій
45234180-1	Construction work for railways workshop	6	f	Будівництво залізничних цехів
45234181-8	Construction work for rail track sectioning cabins	7	t	Будівництво цехів для нарізання рейок
45234200-8	Cable-supported transport systems	5	f	Будівництво канатних транспортних систем
45234210-1	Cable-supported transport systems with cabins	6	t	Будівництво канатних транспортних систем із кабінами
45234220-4	Construction work for ski lifts	6	t	Будівництво гірськолижних підіймачів
45234230-7	Construction work for chair lifts	6	t	Будівництво крісельних підіймачів
45234240-0	Funicular railway system	6	t	Будівництво фунікулерної залізничної системи
45234250-3	Teleferic construction work	6	t	Будівництво канатних доріг
45235000-3	Construction work for airfields, runways and manoeuvring surfaces	4	f	Будівництво аеродромів, злітно-посадкових і маневрувальних смуг
45235100-4	Construction work for airports	5	f	Будівництво аеропортів
45235110-7	Construction work for airfields	6	f	Будівництво аеродромів
45235111-4	Airfield pavement construction work	7	t	Влаштування аеродромного покриття
45235200-5	Runway construction works	5	f	Будівництво злітно-посадкових смуг
45235210-8	Runway resurfacing	6	t	Відновлення покриття злітно-посадкових смуг
45235300-6	Construction work for aircraft-manoeuvring surfaces	5	f	Будівництво маневрувальних смуг
45235310-9	Taxiway construction work	6	f	Будівництво руліжних доріжок
45235311-6	Taxiway pavement construction work	7	t	Влаштування покриття руліжних доріжок
63734000-3	Hangar services	4	t	Послуги ангарів
45235320-2	Construction work for aircraft aprons	6	t	Будівництво майданчиків для стоянки літаків
45236000-0	Flatwork	4	f	Вирівнювання поверхонь
45236100-1	Flatwork for miscellaneous sports installations	5	f	Вирівнювання поверхонь спортивних об’єктів
45236110-4	Flatwork for sports fields	6	f	Вирівнювання поверхонь спортивних полів
45236111-1	Flatwork for golf course	7	t	Вирівнювання поверхонь полів для гольфу
45236112-8	Flatwork for tennis court	7	t	Вирівнювання поверхонь тенісних кортів
45236113-5	Flatwork for racecourse	7	t	Вирівнювання поверхонь іподромних доріжок
45236114-2	Flatwork for running tracks	7	t	Вирівнювання поверхонь бігових доріжок
45236119-7	Repair work on sports fields	7	t	Ремонт спортивних полів
45236200-2	Flatwork for recreation installations	5	f	Вирівнювання поверхонь зон відпочинку
45236210-5	Flatwork for children's play area	6	t	Вирівнювання поверхонь дитячих ігрових майданчиків
45236220-8	Flatwork for zoo	6	t	Вирівнювання поверхонь зоопарків
45236230-1	Flatwork for gardens	6	t	Вирівнювання поверхонь садів
45236250-7	Flatwork for parks	6	t	Вирівнювання поверхонь парків
45236290-9	Repair work on recreational areas	6	t	Ремонт покриття зон відпочинку
45236300-3	Flatwork for cemeteries	5	t	Вирівнювання поверхонь кладовищ
45237000-7	Stage construction works	4	t	Будівництво сцен
45240000-1	Construction work for water projects	3	f	Будівництво гідротехнічних об’єктів
45241000-8	Harbour construction works	4	f	Будівництво портів
45241100-9	Quay construction work	5	t	Будівництво пристаней
45241200-0	Offshore terminal in situ construction work	5	t	Будівництво на місцях рейдових причалів
45241300-1	Pier construction work	5	t	Будівництво пірсів
45241400-2	Dock construction work	5	t	Будівництво доків
45241500-3	Wharf construction work	5	t	Будівництво верфей
45241600-4	Installation of port lighting equipment	5	t	Встановлення портового освітлювального обладнання
45242000-5	Waterside leisure facilities construction work	4	f	Будівництво прибережних об’єктів дозвілля
45242100-6	Water-sports facilities construction work	5	f	Будівництво спортивних об’єктів для водних видів спорту
45242110-9	Launchway construction work	6	t	Будівництво спускових доріжок
45242200-7	Marina construction work	5	f	Будівництво стоянок для малих суден
45242210-0	Yacht harbour construction work	6	t	Будівництво яхтових стоянок
45243000-2	Coastal-defence works	4	f	Укріплення берегових ліній
45243100-3	Cliff-protection works	5	f	Зміцнення схилів
45243110-6	Cliff-stabilisation works	6	t	Стабілізація зсувних схилів
45243200-4	Breakwater construction work	5	t	Будівництво хвилерізів
45243300-5	Sea wall construction work	5	t	Будівництво хвилевідбійних стін
45243400-6	Beach-consolidation works	5	t	Захист пляжів від розмиву
45243500-7	Sea defences construction work	5	f	Будівництво берегозахисних морських споруд
45243510-0	Embankment works	6	t	Спорудження набережних
45243600-8	Quay wall construction work	5	t	Будівництво стін набережних
45244000-9	Marine construction works	4	f	Будівництво морських споруд
45244100-0	Marine installations	5	t	Зведення конструкцій для стоянок для малих суден
45244200-1	Jetties	5	t	Будівництво молів
45245000-6	Dredging and pumping works for water treatment plant installations	4	t	Драгування та відкачування води для спорудження водоочисних станцій
45246000-3	River regulation and flood control works	4	f	Регулювання річкового стоку та протипаводкові роботи
45246100-4	River-wall construction	5	t	Будівництво річкових дамб
45246200-5	Riverbank protection works	5	t	Укріплення берегів річок
45246400-7	Flood-prevention works	5	f	Профілактика паводків
45246410-0	Flood-defences maintenance works	6	t	Технічне обслуговування протипаводкових споруд
45246500-8	Promenade construction work	5	f	Будівництво променадів
45246510-1	Boardwalk construction work	6	t	Будівництво дощаних пішохідних доріжок
45247000-0	Construction work for dams, canals, irrigation channels and aqueducts	4	f	Будівництво гребель, каналів, зрошувальних каналів та акведуків
45247100-1	Construction work for waterways	5	f	Прокладання водних шляхів
45247110-4	Canal construction	6	f	Будування каналів
45247111-1	Irrigation channel construction work	7	t	Будівництво зрошувальних каналів
45247112-8	Drainage canal construction work	7	t	Будівництво дренажних каналів
45247120-7	Waterways except canals	6	t	Роботи з прокладання водних шляхів, окрім каналів
45247130-0	Aqueduct construction work	6	t	Будівництво акведуків
45247200-2	Construction work for dams and similar fixed structures	5	f	Будівництво гребель і подібних стаціонарних споруд
45247210-5	Dam construction work	6	f	Будівництво гребель
45247211-2	Dam wall construction work	7	t	Зведення стін гребель
45247212-9	Dam-reinforcement works	7	t	Укріплення гребель
45247220-8	Weir construction work	6	t	Будівництво загат
45247230-1	Dyke construction work	6	t	Будівництво канав
45247240-4	Static barrage construction work	6	t	Будівництво стаціонарних загат
45247270-3	Reservoir construction works	6	t	Будівництво водосховищ
45248000-7	Construction work for hydro-mechanical structures	4	f	Спорудження гідромеханічних конструкцій
45248100-8	Canal locks construction work	5	t	Будівництво шлюзів судноплавних каналів
45248200-9	Dry docks construction work	5	t	Будівництво сухих доків
45248300-0	Construction work for floating docks	5	t	Будівництво плавучих доків
45248400-1	Landing stages construction work	5	t	Будівництво плавучих причалів
45248500-2	Movable barrages construction work	5	t	Будівництво пересувних загат
45250000-4	Construction works for plants, mining and manufacturing and for buildings relating to the oil and gas industry	3	f	Будівництво заводів / установок, гірничодобувних і переробних об’єктів та об’єктів нафтогазової інфраструктури
45251000-1	Construction works for power plants and heating plants	4	f	Будівництво електростанцій і теплових станцій
45251100-2	Construction work for power plant	5	f	Будівництво електростанцій
45251110-5	Nuclear-power station construction work	6	f	Будівництво атомних електростанцій
45251111-2	Construction work for nuclear reactors	7	t	Будівництво атомних реакторів
45251120-8	Hydro-electric plant construction work	6	t	Будівництво гідроелектростанцій
45251140-4	Thermal power plant construction work	6	f	Будівництво теплових електростанцій
45251141-1	Geothermal power station construction work	7	t	Будівництво геотермальних електростанцій
45251142-8	Wood-fired power station construction work	7	t	Будівництво електростанцій, що працюють на деревному паливі
45251143-5	Compressed-air generating plant construction work	7	t	Будівництво установок для виробництва стисненого повітря
45251150-7	Construction work for cooling towers	6	t	Будівництво градирень
45251160-0	Wind-power installation works	6	t	Будівництво вітрових електростанцій
45251200-3	Heating plant construction work	5	f	Будівництво теплових станцій
45251220-9	Cogeneration plant construction work	6	t	Будівництво когенераційних установок
45251230-2	Steam-generation plant construction work	6	t	Будівництво парогенераторних установок
45251240-5	Landfill-gas electricity generating plant construction work	6	t	Будівництво електростанцій, що працюють на біогазі
45251250-8	District-heating plant construction work	6	t	Будівництво опалювальних районних котелень
45252000-8	Construction works for sewage treatment plants, purification plants and refuse incineration plants	4	f	Будівництво споруд для очищення стічних вод, водоочисних і сміттєспалювальних заводів
45252100-9	Sewage-treatment plant construction work	5	f	Будівництво станцій очищення стічних вод
45252110-2	Mobile plant construction work	6	t	Будівництво пересувних станцій
45252120-5	Water-treatment plant construction work	6	f	Будівництво водоочисних станцій
45252121-2	Sedimentation installations	7	t	Монтаж седиментаційних перегородок
45252122-9	Sewage digesters	7	t	Будівництво басейнів для ферментації стічних вод
45252123-6	Screening installations	7	t	Встановлення механічних фільтрів
45252124-3	Dredging and pumping works	7	t	Роботи з драгування та відкачування
45252125-0	Rock-dumping work	7	t	Насипання породи
45252126-7	Drinking-water treatment plant construction work	7	t	Будівництво станцій очищення питної води
45252127-4	Wastewater treatment plant construction work	7	t	Будівництво станцій очищення стічної води
45252130-8	Sewage plant equipment	6	t	Устаткування станцій очищення стічних вод
45252140-1	Sludge-dewatering plant construction work	6	t	Будівництво установок зі зневоднення мулу
45252150-4	Coal-handling plant construction work	6	t	Будівництво систем вуглеподачі
45252200-0	Purification plant equipment	5	f	Устаткування очисних заводів
45252210-3	Water purification plant construction work	6	t	Будівництво водоочисних станцій
45252300-1	Refuse-incineration plant construction work	5	t	Будівництво сміттєспалювальних заводів
45253000-5	Construction work for chemical-processing plant	4	f	Будівництво заводів хімічної переробки
45253100-6	Demineralisation plant construction work	5	t	Будівництво установок демінералізації
45253200-7	Desulphurisation plant construction work	5	t	Будівництво установок десульфуризації
45253300-8	Distilling or rectifying plant construction work	5	f	Будівництво дистиляційних та ректифікаційних установок
45253310-1	Water-distillation plants construction work	6	t	Будівництво установок для дистилювання води
45253320-4	Alcohol-distillation plants construction work	6	t	Будівництво установок для дистилювання спиртів
45253400-9	Construction work for petrochemical plant	5	t	Будівництво нафтохімічних заводів
45253500-0	Construction work for pharmaceutical plant	5	t	Будівництво фармацевтичних заводів
45253600-1	Deionisation plant construction work	5	t	Будівництво установок деіонізації
45253700-2	Digestion plant construction work	5	t	Будівництво бродильних установок
45253800-3	Composting plant construction work	5	t	Будівництво компостувальних установок
45254000-2	Construction work for mining and manufacturing	4	f	Будівництво гірничодобувних і переробних об’єктів
45254100-3	Construction work for mining	5	f	Будівництво гірничодобувних об’єктів
45254110-6	Pithead construction work	6	t	Будівництво надшахтних споруд
45254200-4	Construction work for manufacturing plant	5	t	Будівництво переробних заводів
45255000-9	Construction work for the oil and gas industry	4	f	Будівництво об’єктів нафтогазової інфраструктури
45255100-0	Construction work for production platforms	5	f	Будівництво промислових платформ
45255110-3	Wells construction work	6	t	Будівництво свердловин
45255120-6	Platforms facilities construction work	6	f	Будівництво платформних споруд
45255121-3	Topside facilities construction work	7	t	Будівництво надземних споруд
45255200-1	Oil refinery construction work	5	f	Будівництво нафтоочисних заводів
45255210-4	Oil terminal construction work	6	t	Будівництво нафтових терміналів
45255300-2	Gas terminal construction work	5	t	Будівництво газових терміналів
45255400-3	Fabrication work	5	f	Монтаж конструкцій
45255410-6	Offshore fabrication work	6	t	Монтаж конструкцій у відкритому морі
45255420-9	Onshore fabrication work	6	t	Монтаж конструкцій на суші
45255430-2	Demolition of oil platforms	6	t	Демонтаж нафтових платформ
45255500-4	Drilling and exploration work	5	t	Буріння та розвідка
45255600-5	Coiled-tubing wellwork	5	t	Колтюбінгове буріння свердловин
45255700-6	Coal-gasification plant construction work	5	t	Будівництво заводів з газифікації вугілля
45255800-7	Gas-production plant construction work	5	t	Будівництво газодобувних заводів
45259000-7	Repair and maintenance of plant	4	f	Ремонт і технічне обслуговування установок
45259100-8	Wastewater-plant repair and maintenance work	5	t	Ремонт і технічне обслуговування станцій очищення стічних вод
45259200-9	Purification-plant repair and maintenance work	5	t	Ремонт і технічне обслуговування водоочисних станцій
45259300-0	Heating-plant repair and maintenance work	5	t	Ремонт і технічне обслуговування теплових станцій
45259900-6	Plant upgrade work	5	t	Модернізація установок
45260000-7	Roof works and other special trade construction works	3	f	Покрівельні роботи та інші спеціалізовані будівельні роботи
45261000-4	Erection and related works of roof frames and coverings	4	f	Зведення крокв’яних ферм і покривання дахів та пов’язані роботи
45261100-5	Roof-framing work	5	t	Зведення каркасів дахів
45261200-6	Roof-covering and roof-painting work	5	f	Покривання і фарбування дахів
45261210-9	Roof-covering work	6	f	Покривання дахів
45261211-6	Roof-tiling work	7	t	Покривання дахів черепицею
45261212-3	Roof-slating work	7	t	Покривання дахів шифером
45261213-0	Metal roof-covering work	7	t	Покривання дахів металом
45261214-7	Bituminous roof-covering work	7	t	Покривання дахів бітумом
45261215-4	Solar panel roof-covering work	7	t	Встановлення на дахах сонячних панелей
45261220-2	Roof-painting and other coating work	6	f	Фарбування дахів та інші види обробки дахового покриття
45261221-9	Roof-painting work	7	t	Фарбування дахів
45261222-6	Cement roof-coating work	7	t	Цементування дахів
45261300-7	Flashing and guttering work	5	f	Гідроізоляція дахів і влаштовування водостоків
45261310-0	Flashing work	6	t	Гідроізоляційні роботи
45261320-3	Guttering work	6	t	Влаштовування водостоків
45261400-8	Sheeting work	5	f	Настилання дахів
45261410-1	Roof insulation work	6	t	Ізолювання дахового покриття
45261420-4	Waterproofing work	6	t	Гідроізолювання
45261900-3	Roof repair and maintenance work	5	f	Ремонт і технічне обслуговування дахів
45261910-6	Roof repair	6	t	Ремонт дахів
45261920-9	Roof maintenance work	6	t	Технічне обслуговування дахів
45313210-9	Travelator installation work	6	t	Монтаж пасажирських конвеєрів
45262000-1	Special trade construction works other than roof works	4	f	Спеціалізовані будівельні роботи, крім покрівельних
45262100-2	Scaffolding work	5	f	Монтаж риштовання
45262110-5	Scaffolding dismantling work	6	t	Демонтаж риштовання
45262120-8	Scaffolding erection work	6	t	Зведення риштувальних конструкцій
45262200-3	Foundation work and water-well drilling	5	f	Влаштовування фундаментів і буріння водних свердловин
45262210-6	Foundation work	6	f	Влаштовування фундаментів
45262211-3	Pile driving	7	t	Занурення паль
45262212-0	Trench sheeting work	7	t	Укріплення стін котлованів
45262213-7	Diaphragm wall technique	7	t	Влаштовування фундаментів типу «стіна в ґрунті»
45262220-9	Water-well drilling	6	t	Буріння водних свердловин
45262300-4	Concrete work	5	f	Бетонні роботи
45262310-7	Reinforced-concrete work	6	f	Залізобетонні роботи
45262311-4	Concrete carcassing work	7	t	Зведення бетонних каркасів
45262320-0	Screed works	6	f	Вирівнювальні роботи
45262321-7	Floor-screed works	7	t	Вирівнювання підлоги
45262330-3	Concrete repair work	6	t	Ремонт бетонних конструкцій
45262340-6	Grouting work	6	t	Заливання цементним розчином
45262350-9	Unreinforced-concrete work	6	t	Роботи з неармованим бетоном
45262360-2	Cementing work	6	t	Цементування
45262370-5	Concrete-coating work	6	t	Бетонування
45262400-5	Structural steel erection work	5	f	Монтаж металевих конструкцій
45262410-8	Structural steel erection work for buildings	6	t	Монтаж металевих конструкцій будівель
45262420-1	Structural steel erection work for structures	6	f	Монтаж металевих конструкцій споруд
45262421-8	Offshore mooring work	7	t	Влаштовування рейдових причалів
45262422-5	Subsea drilling work	7	t	Підводні бурильні роботи
45262423-2	Deck-fabrication work	7	t	Монтаж терас
45262424-9	Offshore-module fabrication work	7	t	Монтаж модульних конструкцій у відкритому морі
45262425-6	Jacket-fabrication work	7	t	Ізоляційні роботи
45262426-3	Pile-fabrication work	7	t	Монтаж паль
45262500-6	Masonry and bricklaying work	5	f	Мурування цеглою і каменем
45262510-9	Stonework	6	f	Кам’яна кладка
45262511-6	Stone carving	7	t	Обробка каменю
45262512-3	Dressed stonework	7	t	Мурування тесаним каменем
45262520-2	Bricklaying work	6	f	Мурування цеглою
45262521-9	Facing brickwork	7	t	Мурування личкувальною цеглою
45262522-6	Masonry work	7	t	Мурування каменем
45262600-7	Miscellaneous special-trade construction work	5	f	Спеціалізовані будівельні роботи різні
45262610-0	Industrial chimneys	6	t	Монтаж промислових димарів
45262620-3	Supporting walls	6	t	Зведення тримальних стін
45262630-6	Construction of furnaces	6	t	Будування печей
45262640-9	Environmental improvement works	6	t	Меліоративні роботи
45262650-2	Cladding works	6	t	Личкування
45262660-5	Asbestos-removal work	6	t	Вилучення азбесту
45262670-8	Metalworking	6	t	Робота з металом
45262680-1	Welding	6	t	Зварювання
45262690-4	Refurbishment of run-down buildings	6	t	Реставрація занедбаних будівель
45262700-8	Building alteration work	5	f	Перебудовування будівель
45262710-1	Fresco maintenance work	6	t	Догляд за фресками
45262800-9	Building extension work	5	t	Розширення будівель
45262900-0	Balcony work	5	t	Балконні роботи
45300000-0	Building installation work	2	f	Будівельно-монтажні роботи
45310000-3	Electrical installation work	3	f	Електромонтажні роботи
45311000-0	Electrical wiring and fitting work	4	f	Монтаж електропроводки та електроарматури
45311100-1	Electrical wiring work	5	t	Монтаж електропроводки
45311200-2	Electrical fitting work	5	t	Монтаж електроарматури
45312000-7	Alarm system and antenna installation work	4	f	Встановлення систем аварійної сигналізації та антен
45312100-8	Fire-alarm system installation work	5	t	Встановлення систем пожежної сигналізації
45312200-9	Burglar-alarm system installation work	5	t	Встановлення систем охоронної сигналізації
45312300-0	Antenna installation work	5	f	Встановлення антен
45312310-3	Lightning-protection works	6	f	Встановлення обладнання для захисту від блискавок
45312311-0	Lightning-conductor installation work	7	t	Встановлення блискавичників
45312320-6	Television aerial installation work	6	t	Встановлення телевізійних антен
45312330-9	Radio aerial installation work	6	t	Встановлення радіоантен
45313000-4	Lift and escalator installation work	4	f	Монтаж ліфтів та ескалаторів
45313100-5	Lift installation work	5	t	Монтаж ліфтів
45313200-6	Escalator installation work	5	f	Монтаж ескалаторів
45314000-1	Installation of telecommunications equipment	4	f	Встановлення телекомунікаційного обладнання
45314100-2	Installation of telephone exchanges	5	f	Встановлення телефонних станцій
45314120-8	Installation of switchboards	6	t	Встановлення комутаторів
45314200-3	Installation of telephone lines	5	t	Монтаж ліній телефонного зв’язку
45314300-4	Installation of cable infrastructure	5	f	Монтаж кабельної інфраструктури
45314310-7	Installation of cable laying	6	t	Укладання кабелів
45314320-0	Installation of computer cabling	6	t	Укладання комп’ютерних кабелів
45315000-8	Electrical installation work of heating and other electrical building-equipment	4	f	Встановлення електричних систем опалення та іншого побутового електричного обладнання
45315100-9	Electrical engineering installation works	5	t	Встановлення електротехнічного обладнання
45315200-0	Turbine works	5	t	Роботи, пов’язані з турбінами
45315300-1	Electricity supply installations	5	t	Монтаж систем електроживлення
45315400-2	High voltage installation work	5	t	Монтаж обладнання високої напруги
45315500-3	Medium-voltage installation work	5	t	Монтаж обладнання середньої напруги
45315600-4	Low-voltage installation work	5	t	Монтаж обладнання низької напруги
45315700-5	Switching station installation work	5	t	Монтаж комутаційних станцій
45316000-5	Installation work of illumination and signalling systems	4	f	Монтаж систем освітлення і сигналізації
45316100-6	Installation of outdoor illumination equipment	5	f	Монтаж обладнання зовнішнього освітлення
45316110-9	Installation of road lighting equipment	6	t	Монтаж обладнання дорожнього освітлення
45316200-7	Installation of signalling equipment	5	f	Встановлення сигналізаційного обладнання
45316210-0	Installation of traffic monitoring equipment	6	f	Встановлення обладнання для моніторингу руху транспорту
45316211-7	Installation of illuminated road signs	7	t	Встановлення підсвічуваних дорожніх знаків
45316212-4	Installation of traffic lights	7	t	Встановлення світлофорів
45316213-1	Installation of traffic guidance equipment	7	t	Встановлення обладнання для регулювання дорожнього руху
45316220-3	Installation of airport signalling equipment	6	t	Встановлення сигнального обладнання для аеропортів
45316230-6	Installation of port signalling equipment	6	t	Встановлення портового сигнального обладнання
45317000-2	Other electrical installation work	4	f	Інші електромонтажні роботи
45317100-3	Electrical installation work of pumping equipment	5	t	Електромонтажні роботи зі встановлення насосного обладнання
45317200-4	Electrical installation work of transformers	5	t	Електромонтажні роботи зі встановлення трансформаторів
45317300-5	Electrical installation work of electrical distribution apparatus	5	t	Електромонтажні роботи зі встановлення електророзподільної апаратури
45317400-6	Electrical installation work of filtration equipment	5	t	Електромонтажні роботи зі встановлення фільтрувального обладнання
45320000-6	Insulation work	3	f	Ізоляційні роботи
45321000-3	Thermal insulation work	4	t	Термоізоляційні роботи
45323000-7	Sound insulation work	4	t	Шумоізоляційні роботи
45324000-4	Plasterboard works	4	t	Монтаж гіпсокартонних конструкцій
45330000-9	Plumbing and sanitary works	3	f	Водопровідні та санітарно-технічні роботи
45331000-6	Heating, ventilation and air-conditioning installation work	4	f	Встановлення опалювальних, вентиляційних систем і систем кондиціонування повітря
45331100-7	Central-heating installation work	5	f	Встановлення систем центрального опалення
45331110-0	Boiler installation work	6	t	Встановлення котлів
45331200-8	Ventilation and air-conditioning installation work	5	f	Встановлення вентиляційних систем і систем кондиціонування повітря
45331210-1	Ventilation installation work	6	f	Встановлення вентиляційних систем
45331211-8	Outdoor ventilation installation work	7	t	Встановлення зовнішніх вентиляційних систем
45331220-4	Air-conditioning installation work	6	f	Встановлення систем кондиціонування повітря
45331221-1	Partial air-conditioning installation work	7	t	Встановлення систем кондиціонування повітря в окремих приміщеннях
45331230-7	Installation work of cooling equipment	6	f	Встановлення охолоджувального обладнання
45331231-4	Installation work of refrigeration equipment	7	t	Встановлення холодильного обладнання
45332000-3	Plumbing and drain-laying work	4	f	Водопровідні роботи та влаштування водостоків
45332200-5	Water plumbing work	5	t	Водопровідні роботи
45332300-6	Drain-laying work	5	t	Влаштування водостоків
45332400-7	Sanitary fixture installation work	5	t	Встановлення санітарно-технічного обладнання
45442100-8	Painting work	5	f	Малярні роботи
45333000-0	Gas-fitting installation work	4	f	Встановлення газового обладнання
45333100-1	Gas regulation equipment installation work	5	t	Встановлення пристроїв регулювання подачі газу
45333200-2	Gas meter installation work	5	t	Встановлення лічильників газу
45340000-2	Fencing, railing and safety equipment installation work	3	f	Зведення огорож, монтаж поручнів і захисних засобів
45341000-9	Erection of railings	4	t	Монтаж поручнів
45342000-6	Erection of fencing	4	t	Зведення огорож
45343000-3	Fire-prevention installation works	4	f	Встановлення протипожежного обладнання
45343100-4	Fireproofing work	5	t	Роботи із забезпечення вогнезахисту
45343200-5	Firefighting equipment installation work	5	f	Встановлення обладнання для пожежогасіння
45343210-8	CO2 fire-extinguishing equipment installation work	6	t	Встановлення вуглекислотних засобів пожежогасіння
45343220-1	Fire-extinguishers installation work	6	t	Встановлення вогнегасників
45343230-4	Sprinkler systems installation work	6	t	Встановлення спринклерних систем
45350000-5	Mechanical installations	3	f	Механо-монтажні роботи
45351000-2	Mechanical engineering installation works	4	t	Встановлення механічного обладнання
45400000-1	Building completion work	2	f	Завершальні будівельні роботи
45410000-4	Plastering work	3	t	Штукатурні роботи
45420000-7	Joinery and carpentry installation work	3	f	Столярні та теслярні роботи
45421000-4	Joinery work	4	f	Столярні роботи
45421100-5	Installation of doors and windows and related components	5	f	Встановлення дверей, вікон і пов’язаних конструкцій
45421110-8	Installation of door and window frames	6	f	Встановлення дверних і віконних рам
45421111-5	Installation of door frames	7	t	Встановлення дверних рам
45421112-2	Installation of window frames	7	t	Встановлення віконних рам
45421120-1	Installation of thresholds	6	t	Встановлення порогів
45421130-4	Installation of doors and windows	6	f	Встановлення дверей і вікон
45421131-1	Installation of doors	7	t	Встановлення дверей
45421132-8	Installation of windows	7	t	Встановлення вікон
45421140-7	Installation of metal joinery except doors and windows	6	f	Монтаж металевих профільних конструкцій, окрім дверей і вікон
45421141-4	Installation of partitioning	7	t	Монтаж перегородок
45421142-1	Installation of shutters	7	t	Монтаж віконниць
45421143-8	Installation work of blinds	7	t	Монтаж жалюзі
45421144-5	Installation work of awnings	7	t	Монтаж тентів
45421145-2	Installation work of roller blinds	7	t	Монтаж ролет
45421146-9	Installation of suspended ceilings	7	t	Монтаж підвісних стель
45421147-6	Installation of grilles	7	t	Встановлення ґрат
45421148-3	Installation of gates	7	t	Встановлення воріт
45421150-0	Non-metal joinery installation work	6	f	Встановлення неметалевих профільних конструкцій
45421151-7	Installation of fitted kitchens	7	t	Монтаж убудованих кухонь
45421152-4	Installation of partition walls	7	t	Монтаж стін-перегородок
45421153-1	Installation of built-in furniture	7	t	Монтаж убудованих меблів
45421160-3	Ironmongery work	6	t	Монтаж віконної і дверної фурнітури
45422000-1	Carpentry installation work	4	f	Теслярі роботи
45422100-2	Woodwork	5	t	Роботи з деревом
45430000-0	Floor and wall covering work	3	f	Покривання підлоги та стін
45431000-7	Tiling work	4	f	Укладання кахлю
45431100-8	Floor-tiling work	5	t	Укладання підлогового кахлю
45431200-9	Wall-tiling work	5	t	Укладання настінного кахлю
45432000-4	Floor-laying and covering, wall-covering and wall-papering work	4	f	Монтаж і покривання підлоги, покривання та обклеювання шпалерами стін
45432100-5	Floor laying and covering work	5	f	Монтаж і покривання підлоги
45432110-8	Floor-laying work	6	f	Монтаж підлоги
45432111-5	Laying work of flexible floor coverings	7	t	Укладання м’якого підлогового покриття
45432112-2	Laying of paving	7	t	Мощення підлоги
45432113-9	Parquet flooring	7	t	Укладання паркету
45432114-6	Wood flooring work	7	t	Укладання дерев’яної підлоги
45432120-1	False floor installation work	6	f	Монтаж фальшпідлоги
45432121-8	Computer floors	7	t	Монтаж фальшпідлоги для прокладення під нею комунікацій
45432130-4	Floor-covering work	6	t	Покривання підлоги
45432200-6	Wall-covering and wall-papering work	5	f	Покривання та обклеювання шпалерами стін
45432210-9	Wall-covering work	6	t	Покривання стін
45432220-2	Wall-papering work	6	t	Обклеювання стін шпалерами
45440000-3	Painting and glazing work	3	f	Фарбування та скління
45441000-0	Glazing work	4	t	Скління
45442000-7	Application work of protective coatings	4	f	Нанесення захисного покриття
45442110-1	Painting work of buildings	6	t	Фарбування будівель
45442120-4	Painting and protective-coating work of structures	6	f	Фарбування конструкцій і нанесення на них захисного покриття
45442121-1	Painting work of structures	7	t	Фарбування конструкцій
45442180-2	Repainting work	6	t	Перефарбовувальні роботи
45442190-5	Paint-stripping work	6	t	Видалення лакофарбового покриття
45442200-9	Application work of anti-corrosive coatings	5	f	Нанесення антикорозійного покриття
45442210-2	Galvanizing works	6	t	Гальванувальні роботи
45442300-0	Surface-protection work	5	t	Захист поверхонь
45443000-4	Facade work	4	t	Фасадні роботи
45450000-6	Other building completion work	3	f	Інші завершальні будівельні роботи
45451000-3	Decoration work	4	f	Декорувальні роботи
45451100-4	Ornamentation fitting work	5	t	Оздоблювальні роботи
45451200-5	Panelling work	5	t	Оббивальні / обшивальні роботи
45451300-6	Interior gardens	5	t	Улаштування зимових садів
45452000-0	Exterior cleaning work for buildings	4	f	Очищення зовнішньої частини будівель
45452100-1	Blast cleaning work for building exteriors	5	t	Струминне очищення зовнішніх частин будівель
45453000-7	Overhaul and refurbishment work	4	f	Капітальний ремонт і реставрація
45453100-8	Refurbishment work	5	t	Реставраційні роботи
45454000-4	Restructuring work	4	f	Перебудовування
45454100-5	Restoration work	5	t	Відновлювальні роботи
45500000-2	Hire of construction and civil engineering machinery and equipment with operator	2	f	Прокат техніки та обладнання з оператором для виконання будівельних робіт та цивільного будівництва
45510000-5	Hire of cranes with operator	3	t	Прокат підіймальних кранів із оператором
45520000-8	Hire of earthmoving equipment with operator	3	t	Прокат обладнання з оператором для виконання земляних робіт
48000000-8	Software package and information systems	1	f	Пакети програмного забезпечення та інформаційні системи
48100000-9	Industry specific software package	2	f	Пакети галузевого програмного забезпечення
48110000-2	Point of sale (POS) software package	3	t	Пакети програмного забезпечення для автоматизованих точок продажу
48120000-5	Flight control software package	3	f	Пакети програмного забезпечення для автоматизованих систем управління польотами
48121000-2	Air traffic control software package	4	t	Пакети програмного забезпечення для систем керування повітряним рухом
48130000-8	Aviation ground support and test software package	3	f	Пакети програмного забезпечення для наземних засобів забезпечення польотів та випробувань авіаційної техніки
48131000-5	Aviation ground support software package	4	t	Пакети програмного забезпечення для наземних засобів забезпечення польотів
48132000-2	Aviation test software package	4	t	Пакети програмного забезпечення для випробувань авіаційної техніки
48140000-1	Railway traffic control software package	3	t	Пакети програмного забезпечення для систем контролю залізничного руху
48150000-4	Industrial control software package	3	f	Пакети програмного забезпечення для керування виробничими процесами
48151000-1	Computer control system	4	t	Системи комп’ютерного керування
48160000-7	Library software package	3	f	Пакети програмного забезпечення для бібліотек
48161000-4	Library management system	4	t	Системи управління бібліотечними фондами
48170000-0	Compliance software package	3	t	Пакети програмного забезпечення для контролю відповідності
48180000-3	Medical software package	3	t	Пакети медичного програмного забезпечення
48190000-6	Educational software package	3	t	Пакети освітнього програмного забезпечення
48200000-0	Networking, Internet and intranet software package	2	f	Пакети мережевого програмного забезпечення, а також програмного забезпечення для мереж Інтернет та Інтранет
48210000-3	Networking software package	3	f	Пакети мережевого програмного забезпечення
48211000-0	Platform interconnectivity software package	4	t	Пакети програмного забезпечення для забезпечення міжплатформної взаємодії
48212000-7	Optical jukebox server software package	4	t	Пакети програмного забезпечення для серверів для роботи з оптичними накопичувачами
48213000-4	Operating system enhancement software package	4	t	Пакети програмного забезпечення для оптимізації операційних систем
48214000-1	Network operating system software package	4	t	Пакети програмного забезпечення для мережевих операційних систем
48215000-8	Networking developers' software package	4	t	Пакети програмного забезпечення для розробників мереж
48326100-0	Digital mapping system	5	t	Цифрові системи картографування
48216000-5	Network connectivity terminal emulation software package	4	t	Пакети емуляторів мережевих терміналів
48217000-2	Transaction-processing software package	4	f	Пакети програмного забезпечення для обробки транзакцій
48217100-3	Mainframe transaction processing software package	5	t	Пакети програмного забезпечення для обробки транзакцій для мейнфреймів
48217200-4	Minicomputer transaction processing software package	5	t	Пакети програмного забезпечення для обробки транзакцій для мінікомп’ютерів
48217300-5	Microcomputer transaction processing software package	5	t	Пакети програмного забезпечення для обробки транзакцій для мікрокомп’ютерів
48218000-9	License management software package	4	t	Пакети програмного забезпечення для управління ліцензіями
48219000-6	Miscellaneous networking software package	4	f	Пакети програмного забезпечення для різних видів мереж
48219100-7	Gateway software package	5	t	Пакети програмного забезпечення для керування шлюзами
48219200-8	Compact disk (CD) server software package	5	t	Пакети програмного забезпечення для серверів для роботи з масивами компакт-дисків
48219300-9	Administration software package	5	t	Пакети програмного забезпечення для адміністрування
48219400-0	Transaction server software package	5	t	Пакети програмного забезпечення для серверів транзакцій
48219500-1	Switch or router software package	5	t	Пакети програмного забезпечення для комутаторів або маршрутизаторів
48219600-2	Multiplexer software package	5	t	Пакети програмного забезпечення для мультиплексорів
48219700-3	Communications server software package	5	t	Пакети програмного забезпечення для комунікаційних серверів
48219800-4	Bridge software package	5	t	Пакети програмного забезпечення для мережевих мостів
48220000-6	Internet and intranet software package	3	f	Пакети програмного забезпечення для мереж Інтернет та Інтранет
48221000-3	Internet browsing software package	4	t	Пакети програмного забезпечення для роботи з Інтернетом
48222000-0	Web server software package	4	t	Пакети програмного забезпечення для веб-серверів
48223000-7	Electronic mail software package	4	t	Пакети програмного забезпечення для роботи з електронною поштою
48224000-4	Web page editing software package	4	t	Пакети програмного забезпечення для редагування веб-сторінок
48300000-1	Document creation, drawing, imaging, scheduling and productivity software package	2	f	Пакети програмного забезпечення для роботи з документами, графікою, зображеннями, планування часу та офісного програмного забезпечення
48310000-4	Document creation software package	3	f	Пакети програмного забезпечення для створення документів
48311000-1	Document management software package	4	f	Пакети програмного забезпечення для систем управління документообігом
48311100-2	Document management system	5	t	Системи управління документообігом
48312000-8	Electronic publishing software package	4	t	Пакети програмного забезпечення для електронних видавництв
48313000-5	Optical-character-recognition (OCR) software package	4	f	Пакети програмного забезпечення для оптичного розпізнавання символів
48313100-6	Optical reading system	5	t	Системи оптичного зчитування інформації
48314000-2	Voice recognition software package	4	t	Пакети програмного забезпечення для розпізнавання голосу
48315000-9	Desktop-publishing software package	4	t	Пакети програмного забезпечення для комп’ютерної верстки
48316000-6	Presentation software package	4	t	Пакети програмного забезпечення для роботи з презентаціями
48317000-3	Word-processing software package	4	t	Пакети програмного забезпечення для редагування текстів
48318000-0	Scanner software package	4	t	Пакети програмного забезпечення для сканерів
48319000-7	Spell checkers	4	t	Програми перевірки орфографії
48320000-7	Drawing and imaging software package	3	f	Пакети програмного забезпечення для роботи з графікою та зображеннями
48321000-4	Computer-aided design (CAD) software package	4	f	Пакети програмного забезпечення для автоматизованих систем проектування
48321100-5	Computer-aided design (CAD) system	5	t	Системи автоматизованого проектування
48322000-1	Graphics software package	4	t	Пакети графічного програмного забезпечення
48323000-8	Computer-aided manufacturing (CAM) software package	4	t	Пакети програмного забезпечення для автоматизації виробництва
48324000-5	Charting software package	4	t	Пакети програмного забезпечення для роботи з діаграмами
48325000-2	Form-making software package	4	t	Пакети програмного забезпечення для створення форм / бланків
48326000-9	Mapping software package	4	f	Пакети картографічного програмного забезпечення
48327000-6	Drawing and painting software package	4	t	Пакети програмного забезпечення для креслення та малювання
48328000-3	Image-processing software package	4	t	Пакети програмного забезпечення для обробки зображень
48329000-0	Imaging and archiving system	4	t	Системи обробки зображень та архівування
48330000-0	Scheduling and productivity software package	3	f	Пакети програмного забезпечення для планування часу та офісного програмного забезпечення
48331000-7	Project management software package	4	t	Пакети програмного забезпечення для управління проектами
48332000-4	Scheduling software package	4	t	Пакети програмного забезпечення для планування часу
48333000-1	Contact management software package	4	t	Пакети програмного забезпечення для управління договірною діяльністю
48400000-2	Business transaction and personal business software package	2	f	Пакети програмного забезпечення для управління діловими операціями та електронних особистих органайзерів
48410000-5	Investment management and tax preparation software package	3	f	Пакети програмного забезпечення для управління інвестиціями та підготовки податкової звітності
48411000-2	Investment management software package	4	t	Пакети програмного забезпечення для управління інвестиціями
48412000-9	Tax preparation software package	4	t	Пакети програмного забезпечення для підготовки податкової звітності
48420000-8	Facilities management software package and software package suite	3	f	Пакети програмного забезпечення для адміністративно-господарського управління та пакети утиліт для розробки програмного забезпечення
48421000-5	Facilities management software package	4	t	Пакети програмного забезпечення для адміністративно-господарського управління
48422000-2	Software package suites	4	t	Пакети утиліт для розробки програмного забезпечення
48430000-1	Inventory management software package	3	t	Пакети програмного забезпечення для управління матеріально-технічними ресурсами
48440000-4	Financial analysis and accounting software package	3	f	Пакети програмного забезпечення для фінансового аналізу та бухгалтерського обліку
48441000-1	Financial analysis software package	4	t	Пакети програмного забезпечення для фінансового аналізу
48442000-8	Financial systems software package	4	t	Пакети програмного забезпечення для фінансових систем
48443000-5	Accounting software package	4	t	Пакети програмного забезпечення для бухгалтерського обліку
48444000-2	Accounting system	4	f	Системи бухгалтерського обліку
48444100-3	Billing system	5	t	Білінгові системи
48445000-9	Customer Relation Management software package	4	t	Пакети програмного забезпечення для управління відносинами з клієнтами
48450000-7	Time accounting or human resources software package	3	f	Пакети програмного забезпечення для обліку часу чи людських ресурсів
48451000-4	Enterprise resource planning software package	4	t	Пакети програмного забезпечення для планування ресурсів підприємства
48460000-0	Analytical, scientific, mathematical or forecasting software package	3	f	Пакети аналітичного, наукового, математичного чи прогнозувального програмного забезпечення
48461000-7	Analytical or scientific software package	4	t	Пакети аналітичного чи наукового програмного забезпечення
48462000-4	Mathematical or forecasting software package	4	t	Пакети математичного чи прогнозувального програмного забезпечення
48463000-1	Statistical software package	4	t	Пакети статистичного програмного забезпечення
48470000-3	Auction software package	3	t	Пакети програмного забезпечення для проведення аукціонів
48480000-6	Sales, marketing and business intelligence software package	3	f	Пакети програмного забезпечення для продажу та реалізації продукції і бізнес-аналітики
48481000-3	Sales or marketing software package	4	t	Пакети програмного забезпечення для продажу чи реалізації продукції
48482000-0	Business intelligence software package	4	t	Пакети програмного забезпечення для бізнес-аналітики
48490000-9	Procurement software package	3	t	Пакети закупівельного програмного забезпечення
48500000-3	Communication and multimedia software package	2	f	Пакети комунікаційного та мультимедійного програмного забезпечення
48510000-6	Communication software package	3	f	Пакети комунікаційного програмного забезпечення
48511000-3	Desktop communications software package	4	t	Пакети програмного забезпечення для відображення повідомлень на робочому столі
48512000-0	Interactive voice response software package	4	t	Пакети програмного забезпечення для систем інтерактивної голосової відповіді
48513000-7	Modem software package	4	t	Пакети програмного забезпечення для модемів
48514000-4	Remote access software package	4	t	Пакети програмного забезпечення для роботи з віддаленим доступом
48515000-1	Video conferencing software package	4	t	Пакети програмного забезпечення для відеоконференцій
48516000-8	Exchange software package	4	t	Пакети програмного забезпечення для обміну інформацією
48517000-5	IT software package	4	t	Пакети програмного забезпечення для роботи з інформаційними технологіями
48518000-2	Emulation software package	4	t	Пакети емуляторів
48519000-9	Memory-management software package	4	t	Пакети програмного забезпечення для керування пам’яттю
48520000-9	Multimedia software package	3	f	Пакети мультимедійного програмного забезпечення
48521000-6	Music or sound editing software package	4	t	Пакети програмного забезпечення для редагування музики чи звуку
48522000-3	Virtual keyboard software package	4	t	Пакети програмного забезпечення для віртуальних клавіатур
48600000-4	Database and operating software package	2	f	Пакети програмного забезпечення для баз даних та операційних систем
48610000-7	Database systems	3	f	Системи баз даних
48611000-4	Database software package	4	t	Пакети програмного забезпечення для баз даних
48612000-1	Database-management system	4	t	Системи керування базами даних
48613000-8	Electronic data management (EDM)	4	t	Електронні системи управління даними
48614000-5	Data-acquisition system	4	t	Системи збору даних
48620000-0	Operating systems	3	f	Операційні системи
48621000-7	Mainframe operating system software package	4	t	Пакети програмного забезпечення для операційних систем для мейнфреймів
48622000-4	Minicomputer operating system software package	4	t	Пакети програмного забезпечення для операційних систем для мінікомп’ютерів
48623000-1	Microcomputer operating system software package	4	t	Пакети програмного забезпечення для операційних систем для мікрокомп’ютерів
48624000-8	Personal computer (PC) operating system software package	4	t	Пакети програмного забезпечення для операційних систем для персональних комп’ютерів
48625000-5	Open systems operating systems	4	t	Операційні системи для відкритих систем
48626000-2	Clustering software package	4	t	Пакети програмного забезпечення для кластеризації
48627000-9	Real-time operating system software package	4	t	Пакети програмного забезпечення для операційних систем реального часу
48628000-9	Micro-channel architecture	4	t	Архітектура мікроканалів
48700000-5	Software package utilities	2	f	Утиліти для розробки програмного забезпечення
48710000-8	Backup or recovery software package	3	t	Пакети програмного забезпечення для резервного копіювання чи відновлення даних
48720000-1	Bar coding software package	3	t	Пакети програмного забезпечення для сканування штрих-кодів
48730000-4	Security software package	3	f	Пакети програмного забезпечення для забезпечення безпеки
48731000-1	File security software package	4	t	Пакети програмного забезпечення для захисту файлів
48732000-8	Data security software package	4	t	Пакети програмного забезпечення для захисту даних
48740000-7	Foreign language translation software package	3	t	Пакети перекладацького програмного забезпечення
48750000-0	Storage media loading software package	3	t	Пакети програмного забезпечення для запису на носії інформації
48760000-3	Virus protection software package	3	f	Пакети програмного забезпечення для захисту від вірусів
48761000-0	Anti-virus software package	4	t	Пакети антивірусного програмного забезпечення
48770000-6	General, compression and print utility software package	3	f	Пакети службового програмного забезпечення загального призначення, для стиснення даних та друку
48771000-3	General utility software package	4	t	Пакети службового програмного забезпечення загального призначення
48772000-0	Compression utilities	4	t	Утиліти для стиснення даних
48773000-7	Print utility software package	4	f	Пакети службового програмного забезпечення для друку
48773100-8	Print-spooling software package	5	t	Пакети програмного забезпечення для виведення документів на друк
48780000-9	System, storage and content management software package	3	f	Пакети програмного забезпечення для управління системами, запам’ятовувальними пристроями та контентом
48781000-6	System management software package	4	t	Пакети програмного забезпечення для управління системами
48782000-3	Storage management software package	4	t	Пакети програмного забезпечення для управління запам’ятовувальними пристроями
48783000-0	Content management software package	4	t	Пакети програмного забезпечення для управління контентом
48790000-2	Version checker software package	3	t	Пакети програмного забезпечення для перевірки версій
48800000-6	Information systems and servers	2	f	Інформаційні системи та сервери
48810000-9	Information systems	3	f	Інформаційні системи
48811000-6	E-mail system	4	t	Системи електронної пошти
48812000-3	Financial information systems	4	t	Фінансові інформаційні системи
48813000-0	Passenger information system	4	f	Пасажирські інформаційні системи
48813100-1	Electronic bulletin boards	5	t	Електронні інформаційні стенди
48813200-2	Real-time passenger information system	5	t	Системи інформування пасажирів у режимі реального часу
48814000-7	Medical information systems	4	f	Медичні інформаційні системи
48814100-8	Nursing information system	5	t	Інформаційні системи у сфері догляду за пацієнтами
48814200-9	Patient-administration system	5	t	Системи обліку пацієнтів
48814300-0	Theatre management system	5	t	Системи управління операційними блоками
48814400-1	Clinical information system	5	t	Клінічні інформаційні системи
48814500-2	Casemix system	5	t	Системи діагностично споріднених груп
48820000-2	Servers	3	f	Сервери
48821000-9	Network servers	4	t	Мережеві сервери
48822000-6	Computer servers	4	t	Комп’ютерні сервери
48823000-3	File servers	4	t	Файлові сервери
48824000-0	Printer servers	4	t	Сервери друку
48825000-7	Web servers	4	t	Веб-сервери
48900000-7	Miscellaneous software package and computer systems	2	f	Пакети програмного забезпечення різного призначення та різні комп’ютерні системи
48910000-0	Computer game software package, family titles and screen savers	3	f	Пакети програмного забезпечення для створення комп’ютерних ігор, побудови генеалогічного дерева та створення екранних заставок
48911000-7	Computer game software package	4	t	Пакети програмного забезпечення для створення комп’ютерних ігор
48912000-4	Family titles	4	t	Генеалогічні дерева
48913000-1	Screen savers	4	t	Екранні заставки
48920000-3	Office automation software package	3	f	Пакети програмного забезпечення для автоматизації офісу
48921000-0	Automation system	4	t	Системи автоматизації
48930000-6	Training and entertainment software package	3	f	Пакети навчального та розважального програмного забезпечення
48931000-3	Training software package	4	t	Пакети навчального програмного забезпечення
48932000-0	Entertainment software package	4	t	Пакети розважального програмного забезпечення
48940000-9	Pattern design and calendar software package	3	f	Пакети програмного забезпечення для створення шаблонів і календарів
48941000-6	Pattern design software package	4	t	Пакети програмного забезпечення для створення шаблонів
48942000-3	Calendar software package	4	t	Пакети програмного забезпечення для створення календарів
48950000-2	Boat-location and public address system	3	f	Системи визначення координат місцезнаходження суден та системи гучномовного зв’язку
48951000-9	Boat-location system	4	t	Системи визначення координат місцезнаходження суден
48952000-6	Public address systems	4	t	Системи гучномовного зв’язку
48960000-5	Drivers and system software package	3	f	Пакети драйверів і системного програмного забезпечення
48961000-2	Ethernet drivers	4	t	Драйвери для мережі Ethernet
48962000-9	Graphics card drivers	4	t	Драйвери для відеокарт
48970000-8	Print shop software package	3	f	Пакети типографського програмного забезпечення
48971000-5	Address book making software package	4	t	Пакети програмного забезпечення для створення адресних книг
48972000-2	Label making software package	4	t	Пакети програмного забезпечення для створення дизайну етикеток
48980000-1	Programming languages and tools	3	f	Мови та засоби програмування
48981000-8	Compiling software packages	4	t	Пакети компілювального програмного забезпечення
48982000-5	Configuration management software package	4	t	Пакети програмного забезпечення для керування конфігураціями
48983000-2	Development software package	4	t	Пакети програмного забезпечення для розробок
48984000-9	Graphical user interface (GUI) tools	4	t	Інструменти графічного інтерфейсу користувача
48985000-6	Programming languages	4	t	Мови програмування
48986000-3	Program testing software package	4	t	Пакети програмного забезпечення для тестування програм
48987000-0	Debugging software package	4	t	Пакети зневаджувального програмного забезпечення
48990000-4	Spreadsheets and enhancement software package	3	f	Пакети програмного забезпечення для роботи з таблицями та вдосконалення програмного продукту
48991000-1	Spreadsheet software package	4	t	Пакети програмного забезпечення для роботи з таблицями
50000000-5	Repair and maintenance services	1	f	Послуги з ремонту і технічного обслуговування
50100000-6	Repair, maintenance and associated services of vehicles and related equipment	2	f	Послуги з ремонту, технічного обслуговування транспортних засобів і супутнього обладнання та супутні послуги
50110000-9	Repair and maintenance services of motor vehicles and associated equipment	3	f	Послуги з ремонту і технічного обслуговування мототранспортних засобів і супутнього обладнання
50111000-6	Fleet management, repair and maintenance services	4	f	Послуги з управління, ремонту та експлуатації автотранспортних парків
50111100-7	Vehicle-fleet management services	5	f	Послуги з управління автотранспортними парками
50111110-0	Vehicle-fleet-support services	6	t	Послуги з утримання автотранспортних парків
50112000-3	Repair and maintenance services of cars	4	f	Послуги з ремонту і технічного обслуговування автомобілів
50112100-4	Car repair services	5	f	Послуги з ремонту автомобілів
50112110-7	Body-repair services for vehicles	6	f	Послуги з ремонту кузовів
50112111-4	Panel-beating services	7	t	Послуги з рихтування
50112120-0	Windscreen replacement services	6	t	Послуги із заміни вітрового скла
50112200-5	Car maintenance services	5	t	Послуги з технічного обслуговування автомобілів
50112300-6	Car-washing and similar services	5	t	Послуги з миття автомобілів та подібні послуги
50113000-0	Repair and maintenance services of buses	4	f	Послуги з ремонту і технічного обслуговування автобусів
50113100-1	Bus repair services	5	t	Послуги з ремонту автобусів
50113200-2	Bus maintenance services	5	t	Послуги з технічного обслуговування автобусів
50114000-7	Repair and maintenance services of trucks	4	f	Послуги з ремонту і технічного обслуговування вантажних автомобілів
50114100-8	Truck repair services	5	t	Послуги з ремонту вантажних автомобілів
50114200-9	Truck maintenance services	5	t	Послуги з технічного обслуговування вантажних автомобілів
50115000-4	Repair and maintenance services of motorcycles	4	f	Послуги з ремонту і технічного обслуговування мотоциклів
50115100-5	Motorcycle repair services	5	t	Послуги з ремонту мотоциклів
50115200-6	Motorcycle maintenance services	5	t	Послуги з технічного обслуговування мотоциклів
50116000-1	Maintenance and repair services related to specific parts of vehicles	4	f	Послуги з ремонту і технічного обслуговування окремих частин транспортних засобів
50116100-2	Electrical-system repair services	5	t	Послуги з ремонту електричних систем
50116200-3	Repair and maintenance services of vehicle brakes and brake parts	5	t	Послуги з ремонту і технічного обслуговування гальмівних систем транспортних засобів та їх частин
50116300-4	Repair and maintenance services of vehicle gearboxes	5	t	Послуги з ремонту і технічного обслуговування коробок передач транспортних засобів
50116400-5	Repair and maintenance services of vehicle transmissions	5	t	Послуги з ремонту і технічного обслуговування трансмісій транспортних засобів
50116500-6	Tyre repair services, including fitting and balancing	5	f	Шиноремонтні послуги, у тому числі шиномонтажні послуги та послуги з балансування коліс
50116510-9	Tyre-remoulding services	6	t	Послуги з відновлення шин
50116600-7	Repair and maintenance services of starter motors	5	t	Послуги з ремонту і технічного обслуговування стартерів
50117000-8	Vehicle conversion and reconditioning services	4	f	Послуги з переобладнання та реставрації транспортних засобів
50117100-9	Motor vehicle conversion services	5	t	Послуги з переобладнання мототранспортних засобів
50117200-0	Ambulance conversion services	5	t	Послуги з переобладнання автомобілів швидкої допомоги
50117300-1	Reconditioning services of vehicles	5	t	Послуги з реставрації транспортних засобів
50118000-5	Automobile emergency road services	4	f	Аварійно-евакуаційні послуги
50118100-6	Breakdown and recovery services for cars	5	f	Послуги з аварійного ремонту і відновлення автомобілів
50118110-9	Vehicle towing-away services	6	t	Послуги з евакуації транспортних засобів
50118200-7	Breakdown and recovery services for commercial vehicles	5	t	Послуги з аварійного ремонту і відновлення вантажних транспортних засобів
50118300-8	Breakdown and recovery services for buses	5	t	Послуги з аварійного ремонту і відновлення автобусів
50118400-9	Breakdown and recovery services for motor vehicles	5	t	Послуги з аварійного ремонту і відновлення мототранспортних засобів
50118500-0	Breakdown and recovery services for motorcycles	5	t	Послуги з аварійного ремонту і відновлення мотоциклів
50190000-3	Demolition services of vehicles	3	t	Послуги з демонтування транспортних засобів
50200000-7	Repair, maintenance and associated services related to aircraft, railways, roads and marine equipment	2	f	Послуги з ремонту, технічного обслуговування повітряного, залізничного, морського транспорту і пов’язаного обладнання та дорожньої інфраструктури, а також супутні послуги
50210000-0	Repair, maintenance and associated services related to aircraft and other equipment	3	f	Послуги з ремонту, технічного обслуговування повітряного транспорту та пов’язаного обладнання і супутні послуги
50211000-7	Repair and maintenance services of aircraft	4	f	Послуги з ремонту і технічного обслуговування літальних апаратів
50211100-8	Aircraft maintenance services	5	t	Послуги з технічного обслуговування літальних апаратів
50211200-9	Aircraft repair services	5	f	Послуги з ремонту літальних апаратів
50211210-2	Repair and maintenance services of aircraft engines	6	f	Послуги з ремонту і технічного обслуговування авіаційних двигунів
50211211-9	Aircraft-engine maintenance services	7	t	Послуги з технічного обслуговування авіаційних двигунів
50211212-6	Aircraft-engine repair services	7	t	Послуги з ремонту авіаційних двигунів
50211300-0	Reconditioning services of aircraft	5	f	Послуги з реставрації літальних апаратів
50211310-3	Reconditioning services of aircraft engines	6	t	Послуги з реставрації авіаційних двигунів
50212000-4	Repair and maintenance services of helicopters	4	t	Послуги з ремонту і технічного обслуговування вертольотів
50220000-3	Repair, maintenance and associated services related to railways and other equipment	3	f	Послуги з ремонту, технічного обслуговування залізничного транспорту і пов’язаного обладнання та супутні послуги
50221000-0	Repair and maintenance services of locomotives	4	f	Послуги з ремонту і технічного обслуговування локомотивів
50221100-1	Repair and maintenance services of locomotive gearboxes	5	t	Послуги з ремонту і технічного обслуговування коробок передач локомотивів
50221200-2	Repair and maintenance services of locomotive transmissions	5	t	Послуги з ремонту і технічного обслуговування трансмісій локомотивів
50221300-3	Repair and maintenance services of locomotive wheelsets	5	t	Послуги з ремонту і технічного обслуговування колісних пар локомотивів
50221400-4	Repair and maintenance services of locomotive brakes and brake parts	5	t	Послуги з ремонту і технічного обслуговування гальмівних систем локомотивів та їх частин
50222000-7	Repair and maintenance services of rolling stock	4	f	Послуги з ремонту і технічного обслуговування рухомого складу
50222100-8	Repair and maintenance services of dampers	5	t	Послуги з ремонту і технічного обслуговування амортизаторів
50223000-4	Reconditioning services of locomotives	4	t	Послуги з реставрації локомотивів
50224000-1	Reconditioning services of rolling stock	4	f	Послуги з реставрації рухомого складу
50224100-2	Reconditioning services of rolling stock seats	5	t	Послуги з реставрації сидінь рухомого складу
50224200-3	Reconditioning services of passenger coaches	5	t	Послуги з реставрації пасажирських вагонів
50225000-8	Railway-track maintenance services	4	t	Послуги з технічного обслуговування залізничних колій
50229000-6	Demolition of rolling stock	4	t	Демонтування рухомого складу
50230000-6	Repair, maintenance and associated services related to roads and other equipment	3	f	Послуги з ремонту, технічного обслуговування дорожньої інфраструктури і пов’язаного обладнання та супутні послуги
50232000-0	Maintenance services of public-lighting installations and traffic lights	4	f	Послуги з технічного обслуговування систем освітлення вулиць і громадських місць та світлофорів
50232100-1	Street-lighting maintenance services	5	f	Послуги з технічного обслуговування систем вуличного освітлення
50232110-4	Commissioning of public lighting installations	6	t	Уведення в експлуатацію систем освітлення громадських місць
50232200-2	Traffic-signal maintenance services	5	t	Послуги з технічного обслуговування світлофорів
50240000-9	Repair, maintenance and associated services related to marine and other equipment	3	f	Послуги з ремонту, технічного обслуговування морського транспорту і пов’язаного обладнання та супутні послуги
50241000-6	Repair and maintenance services of ships	4	f	Послуги з ремонту і технічного обслуговування суден
50241100-7	Vessel repair services	5	t	Послуги з ремонту суден
50241200-8	Ferry repair services	5	t	Послуги з ремонту поромів
50242000-3	Conversion services of ships	4	t	Послуги з переобладнання суден
50243000-0	Demolition services of ships	4	t	Послуги з демонтування суден
50244000-7	Reconditioning services of ships or boats	4	t	Послуги з реставрації суден або човнів
50245000-4	Upgrading services of ships	4	t	Послуги з модернізації суден
50246000-1	Harbour equipment maintenance services	4	f	Послуги з технічного обслуговування портового обладнання
50246100-2	Dry-docking services	5	t	Послуги сухих доків
50246200-3	Buoy maintenance services	5	t	Послуги з технічного обслуговування буїв
50246300-4	Repair and maintenance services of floating structures	5	t	Послуги з ремонту і технічного обслуговування плавучих конструкцій
50246400-5	Repair and maintenance services of floating platforms	5	t	Послуги з ремонту і технічного обслуговування плавучих платформ
50300000-8	Repair, maintenance and associated services related to personal computers, office equipment, telecommunications and audio-visual equipment	2	f	Ремонт, технічне обслуговування персональних комп’ютерів, офісного, телекомунікаційного та аудіовізуального обладнання, а також супутні послуги
50310000-1	Maintenance and repair of office machinery	3	f	Технічне обслуговування і ремонт офісної техніки
50311000-8	Maintenance and repair of office accounting machinery	4	f	Технічне обслуговування і ремонт офісної обчислювальної техніки
50311400-2	Maintenance and repair of calculators and accounting machinery	5	t	Технічне обслуговування і ремонт калькуляторів та обчислювальної техніки
50312000-5	Maintenance and repair of computer equipment	4	f	Технічне обслуговування і ремонт комп’ютерного обладнання
50312100-6	Maintenance and repair of mainframe computers	5	f	Технічне обслуговування і ремонт мейнфреймів
50312110-9	Maintenance of mainframe computers	6	t	Технічне обслуговування мейнфреймів
50312120-2	Repair of mainframe computers	6	t	Ремонт мейнфреймів
50312200-7	Maintenance and repair of minicomputers	5	f	Технічне обслуговування і ремонт мінікомп’ютерів
50312210-0	Maintenance of minicomputers	6	t	Технічне обслуговування мінікомп’ютерів
50312220-3	Repair of minicomputers	6	t	Ремонт мінікомп’ютерів
50312300-8	Maintenance and repair of data network equipment	5	f	Технічне обслуговування і ремонт обладнання мереж для передачі даних
50312310-1	Maintenance of data network equipment	6	t	Технічне обслуговування обладнання мереж для передачі даних
50312320-4	Repair of data network equipment	6	t	Ремонт обладнання мереж для передачі даних
50312400-9	Maintenance and repair of microcomputers	5	f	Технічне обслуговування і ремонт мікрокомп’ютерів
50312410-2	Maintenance of microcomputers	6	t	Технічне обслуговування мікрокомп’ютерів
50312420-5	Repair of microcomputers	6	t	Ремонт мікрокомп’ютерів
50312600-1	Maintenance and repair of information technology equipment	5	f	Технічне обслуговування і ремонт обладнання інформаційних технологій
50312610-4	Maintenance of information technology equipment	6	t	Технічне обслуговування обладнання інформаційних технологій
50312620-7	Repair of information technology equipment	6	t	Ремонт обладнання інформаційних технологій
50313000-2	Maintenance and repair of reprographic machinery	4	f	Технічне обслуговування і ремонт копіювально-розмножувальної техніки
50313100-3	Photocopier repair services	5	t	Послуги з ремонту фотокопіювальних пристроїв
50313200-4	Photocopier maintenance services	5	t	Послуги з технічного обслуговування фотокопіювальних пристроїв
50314000-9	Repair and maintenance services of facsimile machines	4	t	Послуги з ремонту і технічного обслуговування факсимільних апаратів
50315000-6	Repair and maintenance services of telephone-answering machines	4	t	Послуги з ремонту і технічного обслуговування телефонних автовідповідачів
50316000-3	Maintenance and repair of ticket-issuing machinery	4	t	Технічне обслуговування і ремонт пристроїв для друку квитків
50317000-0	Maintenance and repair of ticket-validation machinery	4	t	Технічне обслуговування і ремонт валідаторів квитків
50320000-4	Repair and maintenance services of personal computers	3	f	Послуги з ремонту і технічного обслуговування персональних комп’ютерів
50321000-1	Repair services of personal computers	4	t	Послуги з ремонту персональних комп’ютерів
50322000-8	Maintenance services of personal computers	4	t	Послуги з технічного обслуговування персональних комп’ютерів
50323000-5	Maintenance and repair of computer peripherals	4	f	Ремонт і технічне обслуговування комп’ютерних периферійних пристроїв
50323100-6	Maintenance of computer peripherals	5	t	Технічне обслуговування комп’ютерних периферійних пристроїв
50323200-7	Repair of computer peripherals	5	t	Ремонт комп’ютерних периферійних пристроїв
50324000-2	Support services of personal computers	4	f	Послуги технічної підтримки користувачів персональних комп’ютерів
50324100-3	System maintenance services	5	t	Послуги з технічного обслуговування систем
50324200-4	Preventive maintenance services	5	t	Послуги з профілактичного обслуговування
50330000-7	Maintenance services of telecommunications equipment	3	f	Послуги з технічного обслуговування телекомунікаційного обладнання
50331000-4	Repair and maintenance services of telecommunications lines	4	t	Послуги з ремонту і технічного обслуговування ліній телекомунікацій
50332000-1	Telecommunications-infrastructure maintenance services	4	t	Послуги з технічного обслуговування телекомунікаційної інфраструктури
50333000-8	Maintenance services of radio-communications equipment	4	f	Послуги з технічного обслуговування обладнання радіозв’язку
50333100-9	Repair and maintenance services of radio transmitters	5	t	Послуги з ремонту і технічного обслуговування радіопередавачів
50333200-0	Repair and maintenance services of radiotelephony apparatus	5	t	Послуги з ремонту і технічного обслуговування радіотелефонної апаратури
50334000-5	Repair and maintenance services of line telephony and line telegraphy equipment	4	f	Послуги з ремонту і технічного обслуговування обладнання ліній телефонного і телеграфного зв’язку
50334100-6	Repair and maintenance services of line telephony equipment	5	f	Послуги з ремонту і технічного обслуговування обладнання ліній телефонного зв’язку
50334110-9	Telephone network maintenance services	6	t	Послуги з технічного обслуговування телефонних мереж
50334120-2	Upgrade services of telephone switching equipment	6	t	Послуги з модернізації телефонного комутаційного обладнання
50334130-5	Repair and maintenance services of telephone switching apparatus	6	t	Послуги з ремонту і технічного обслуговування телефонної комутаційної апаратури
50334140-8	Repair and maintenance services of telephone sets	6	t	Послуги з ремонту і технічного обслуговування телефонних апаратів
50334200-7	Repair and maintenance services of line telegraphy equipment	5	t	Послуги з ремонту і технічного обслуговування обладнання ліній телеграфного зв’язку
50334300-8	Repair and maintenance services of line telex equipment	5	t	Послуги з ремонту і технічного обслуговування обладнання ліній телексного зв’язку
50334400-9	Communications system maintenance services	5	t	Послуги з технічного обслуговування комунікаційних систем
50340000-0	Repair and maintenance services of audio-visual and optical equipment	3	f	Послуги з ремонту і технічного обслуговування аудіовізуального та оптичного обладнання
50341000-7	Repair and maintenance services of television equipment	4	f	Послуги з ремонту і технічного обслуговування телевізійного обладнання
50341100-8	Repair and maintenance services of videotext equipment	5	t	Послуги з ремонту і технічного обслуговування обладнання для передачі відеотексту
50341200-9	Repair and maintenance services of television transmitters	5	t	Послуги з ремонту і технічного обслуговування телепередавальних пристроїв
50342000-4	Repair and maintenance services of audio equipment	4	t	Послуги з ремонту і технічного обслуговування аудіообладнання
50343000-1	Repair and maintenance services of video equipment	4	t	Послуги з ремонту і технічного обслуговування відеообладнання
50344000-8	Repair and maintenance services of optical equipment	4	f	Послуги з ремонту і технічного обслуговування оптичного обладнання
50344100-9	Repair and maintenance services of photographic equipment	5	t	Послуги з ремонту і технічного обслуговування фотографічного обладнання
50344200-0	Repair and maintenance services of cinematographic equipment	5	t	Послуги з ремонту і технічного обслуговування кіноапаратури
50400000-9	Repair and maintenance services of medical and precision equipment	2	f	Послуги з ремонту і технічного обслуговування медичного і високоточного обладнання
50410000-2	Repair and maintenance services of measuring, testing and checking apparatus	3	f	Послуги з ремонту і технічного обслуговування вимірювальних, випробувальних і контрольних приладів
50411000-9	Repair and maintenance services of measuring apparatus	4	f	Послуги з ремонту і технічного обслуговування вимірювальних приладів
50411100-0	Repair and maintenance services of water meters	5	t	Послуги з ремонту і технічного обслуговування лічильників води
50411200-1	Repair and maintenance services of gas meters	5	t	Послуги з ремонту і технічного обслуговування лічильників газу
50411300-2	Repair and maintenance services of electricity meters	5	t	Послуги з ремонту і технічного обслуговування лічильників електроенергії
50411400-3	Repair and maintenance services of tachometers	5	t	Послуги з ремонту і технічного обслуговування тахометрів
50411500-4	Repair and maintenance services of industrial time-measuring equipment	5	t	Послуги з ремонту і технічного обслуговування промислових приладів вимірювання часу
51313000-9	Installation services of sound equipment	4	t	Послуги зі встановлення аудіоапаратури
50412000-6	Repair and maintenance services of testing apparatus	4	t	Послуги з ремонту і технічного обслуговування випробувальних приладів
50413000-3	Repair and maintenance services of checking apparatus	4	f	Послуги з ремонту і технічного обслуговування контрольних приладів
50413100-4	Repair and maintenance services of gas-detection equipment	5	t	Послуги з ремонту і технічного обслуговування приладів виявляння газу
50413200-5	Repair and maintenance services of firefighting equipment	5	t	Послуги з ремонту і технічного обслуговування протипожежного обладнання
50420000-5	Repair and maintenance services of medical and surgical equipment	3	f	Послуги з ремонту і технічного обслуговування медичного та хірургічного обладнання
50421000-2	Repair and maintenance services of medical equipment	4	f	Послуги з ремонту і технічного обслуговування медичного обладнання
50421100-3	Repair and maintenance services of wheelchairs	5	t	Послуги з ремонту і технічного обслуговування інвалідних візків
50421200-4	Repair and maintenance services of X-ray equipment	5	t	Послуги з ремонту і технічного обслуговування рентгенологічного обладнання
50422000-9	Repair and maintenance services of surgical equipment	4	t	Послуги з ремонту і технічного обслуговування хірургічного обладнання
50430000-8	Repair and maintenance services of precision equipment	3	f	Послуги з ремонтування і технічного обслуговування високоточного обладнання
50431000-5	Repair and maintenance services of watches	4	t	Послуги з ремонту і технічного обслуговування наручних годинників
50432000-2	Repair and maintenance services of clocks	4	t	Послуги з ремонту і технічного обслуговування настінних та інших годинників
50433000-9	Calibration services	4	t	Послуги з калібрування
50500000-0	Repair and maintenance services for pumps, valves, taps and metal containers and machinery	2	f	Послуги з ремонту і технічного обслуговування насосів, клапанів, кранів, металевих контейнерів і техніки
50510000-3	Repair and maintenance services of pumps, valves, taps and metal containers	3	f	Послуги з ремонту і технічного обслуговування насосів, клапанів, кранів і металевих контейнерів
50511000-0	Repair and maintenance services of pumps	4	f	Послуги з ремонту і технічного обслуговування насосів
50511100-1	Repair and maintenance services of liquid pumps	5	t	Послуги з ремонту і технічного обслуговування рідинних насосів
50511200-2	Repair and maintenance services of gas pumps	5	t	Послуги з ремонту і технічного обслуговування газових насосів
50512000-7	Repair and maintenance services of valves	4	t	Послуги з ремонту і технічного обслуговування клапанів
50513000-4	Repair and maintenance services of taps	4	t	Послуги з ремонту і технічного обслуговування кранів
50514000-1	Repair and maintenance services of metal containers	4	f	Послуги з ремонту і технічного обслуговування металевих контейнерів
50514100-2	Repair and maintenance services of tanks	5	t	Послуги з ремонту і технічного обслуговування цистерн
50514200-3	Repair and maintenance services of reservoirs	5	t	Послуги з ремонту і технічного обслуговування резервуарів
50514300-4	Sleeving repair services	5	t	Послуги з ремонту муфт
50530000-9	Repair and maintenance services of machinery	3	f	Послуги з ремонту і технічного обслуговування техніки
50531000-6	Repair and maintenance services for non-electrical machinery	4	f	Послуги з ремонту і технічного обслуговування неелектричної техніки
50531100-7	Repair and maintenance services of boilers	5	t	Послуги з ремонту і технічного обслуговування котлів
50531200-8	Gas appliance maintenance services	5	t	Послуги з технічного обслуговування газових приладів
50531300-9	Repair and maintenance services of compressors	5	t	Послуги з ремонту і технічного обслуговування компресорів
50531400-0	Repair and maintenance services of cranes	5	t	Послуги з ремонту і технічного обслуговування підіймальних кранів
50531500-1	Repair and maintenance services of derricks	5	f	Послуги з ремонту і технічного обслуговування щоглових кранів
50531510-4	Derrick-dismantling services	6	t	Послуги з демонтування щоглових кранів
50532000-3	Repair and maintenance services of electrical machinery, apparatus and associated equipment	4	f	Послуги з ремонту і технічного обслуговування електричної техніки, апаратури та супутнього обладнання
50532100-4	Repair and maintenance services of electric motors	5	t	Послуги з ремонту і технічного обслуговування електродвигунів
50532200-5	Repair and maintenance services of transformers	5	t	Послуги з ремонту і технічного обслуговування трансформаторів
50532300-6	Repair and maintenance services of generators	5	t	Послуги з ремонту і технічного обслуговування генераторів
51312000-2	Installation services of television equipment	4	t	Послуги зі встановлення телевізійної апаратури
50532400-7	Repair and maintenance services of electrical distribution equipment	5	t	Послуги з ремонту і технічного обслуговування електророзподільного обладнання
50600000-1	Repair and maintenance services of security and defence materials	2	f	Послуги з ремонту і технічного обслуговування захисних і оборонних ресурсів
50610000-4	Repair and maintenance services of security equipment	3	t	Послуги з ремонту і технічного обслуговування захисного обладнання
50620000-7	Repair and maintenance services of firearms and ammunition	3	t	Послуги з ремонту і технічного обслуговування вогнепальної зброї та боєприпасів
50630000-0	Repair and maintenance services of military vehicles	3	t	Послуги з ремонту і технічного обслуговування транспортних засобів військового призначення
50640000-3	Repair and maintenance services of warships	3	t	Послуги з ремонту і технічного обслуговування військових кораблів
50650000-6	Repair and maintenance services of military aircrafts, missiles and spacecrafts	3	t	Послуги з ремонту і технічного обслуговування військових літаків, ракет і космічних апаратів
50660000-9	Repair and maintenance services of military electronic systems	3	t	Послуги з ремонту і технічного обслуговування військових електронних систем
50700000-2	Repair and maintenance services of building installations	2	f	Послуги з ремонту і технічного обслуговування будівельних конструкцій
50710000-5	Repair and maintenance services of electrical and mechanical building installations	3	f	Послуги з ремонту і технічного обслуговування електричного і механічного устаткування будівель
50711000-2	Repair and maintenance services of electrical building installations	4	t	Послуги з ремонту і технічного обслуговування електричного устаткування будівель
50712000-9	Repair and maintenance services of mechanical building installations	4	t	Послуги з ремонту і технічного обслуговування механічного устаткування будівель
50720000-8	Repair and maintenance services of central heating	3	f	Послуги з ремонту і технічного обслуговування систем центрального опалення
50721000-5	Commissioning of heating installations	4	t	Введення в експлуатацію опалювальних установок
50730000-1	Repair and maintenance services of cooler groups	3	t	Послуги з ремонту і технічного обслуговування охолоджувальних установок
50740000-4	Repair and maintenance services of escalators	3	t	Послуги з ремонту і технічного обслуговування ескалаторів
50750000-7	Lift-maintenance services	3	t	Послуги з технічного обслуговування ліфтів
50760000-0	Repair and maintenance of public conveniences	3	t	Ремонт і технічне обслуговування громадських вбиралень
50800000-3	Miscellaneous repair and maintenance services	2	f	Послуги з різних видів ремонту і технічного обслуговування
50810000-6	Repair services of jewellery	3	t	Послуги з ремонту ювелірних виробів
50820000-9	Repair services of leather personal goods	3	f	Послуги з ремонту шкіряних виробів
50821000-6	Repair services of boots	4	t	Послуги з ремонту чобіт
50822000-3	Repair services of shoes	4	t	Послуги з ремонту інших видів взуття
50830000-2	Repair services of garments and textiles	3	t	Послуги з ремонту одягу та текстильних виробів
50840000-5	Repair and maintenance services of weapons and weapon systems	3	f	Послуги з ремонту і технічного обслуговування зброї та систем озброєння
50841000-2	Repair and maintenance services of weapons	4	t	Послуги з ремонту і технічного обслуговування зброї
50842000-9	Repair and maintenance services of weapon systems	4	t	Послуги з ремонту і технічного обслуговування систем озброєння
50850000-8	Repair and maintenance services of furniture	3	t	Послуги з ремонту і технічного обслуговування меблів
50860000-1	Repair and maintenance services of musical instruments	3	t	Послуги з ремонту і технічного обслуговування музичних інструментів
50870000-4	Repair and maintenance services of playground equipment	3	t	Послуги з ремонту і технічного обслуговування обладнання для ігрових майданчиків
50880000-7	Repair and maintenance services of hotel and restaurant equipment	3	f	Послуги з ремонту і технічного обслуговування готельного і ресторанного обладнання
50881000-4	Repair and maintenance services of hotel equipment	4	t	Послуги з ремонту і технічного обслуговування готельного обладнання
50882000-1	Repair and maintenance services of restaurant equipment	4	t	Послуги з ремонту і технічного обслуговування ресторанного обладнання
50883000-8	Repair and maintenance services of catering equipment	4	t	Послуги з ремонту і технічного обслуговування обладнання для кейтерингу
50884000-5	Repair and maintenance services of camping equipment	4	t	Послуги з ремонту і технічного обслуговування обладнання для кемпінгу
51000000-9	Installation services (except software)	1	f	Послуги зі встановлення (крім програмного забезпечення)
51100000-3	Installation services of electrical and mechanical equipment	2	f	Послуги зі встановлення електричного та механічного обладнання
51110000-6	Installation services of electrical equipment	3	f	Послуги зі встановлення електричного обладнання
51111000-3	Installation services of electric motors, generators and transformers	4	f	Послуги зі встановлення електродвигунів, генераторів і трансформаторів
51111100-4	Installation services of electric motors	5	t	Послуги зі встановлення електродвигунів
51111200-5	Installation services of generators	5	t	Послуги зі встановлення генераторів
51111300-6	Installation services of transformers	5	t	Послуги зі встановлення трансформаторів
51112000-0	Installation services of electricity distribution and control equipment	4	f	Послуги зі встановлення електричного розподільного та контрольного обладнання
51112100-1	Installation services of electricity distribution equipment	5	t	Послуги зі встановлення електричного розподільного обладнання
51112200-2	Installation services of electricity control equipment	5	t	Послуги зі встановлення електричного контрольного обладнання
51120000-9	Installation services of mechanical equipment	3	f	Послуги зі встановлення механічного обладнання
51121000-6	Installation services of fitness equipment	4	t	Послуги зі встановлення устаткування для фітнесу
51122000-3	Installation services of flagpoles	4	t	Послуги зі встановлення флагштоків
51130000-2	Installation services of steam generators, turbines, compressors and burners	3	f	Послуги зі встановлення парових генераторів, турбін, компресорів і пальників
51131000-9	Installation services of steam generators	4	t	Послуги зі встановлення парових генераторів
51133000-3	Installation services of turbines	4	f	Послуги зі встановлення турбін
51133100-4	Installation services of gas turbines	5	t	Послуги зі встановлення газових турбін
51134000-0	Installation services of compressors	4	t	Послуги зі встановлення компресорів
51135000-7	Installation services of furnaces	4	f	Послуги зі встановлення печей
51135100-8	Installation services of burners	5	f	Послуги зі встановлення пальників
51135110-1	Installation services of waste incinerators	6	t	Послуги зі встановлення сміттєспалювальних печей
51140000-5	Installation services of engines	3	f	Послуги зі встановлення двигунів
51141000-2	Installation services of petrol engines	4	t	Послуги зі встановлення бензинових двигунів
51142000-9	Installation services of diesel engines	4	t	Послуги зі встановлення дизельних двигунів
51143000-6	Installation services of railway engines	4	t	Послуги зі встановлення тягових двигунів
51144000-3	Installation services of vehicle engines	4	t	Послуги зі встановлення автомобільних двигунів
51145000-0	Installation services of marine engines	4	t	Послуги зі встановлення суднових двигунів
51146000-7	Installation services of aircraft engines	4	t	Послуги зі встановлення авіаційних двигунів
51200000-4	Installation services of equipment for measuring, checking, testing and navigating	2	f	Послуги зі встановлення вимірювального, контрольного, випробувального і навігаційного обладнання
51210000-7	Installation services of measuring equipment	3	f	Послуги зі встановлення вимірювального обладнання
51211000-4	Installation services of time-measuring equipment	4	t	Послуги зі встановлення приладів вимірювання часу
51212000-1	Installation services of time register equipment	4	t	Послуги зі встановлення пристроїв реєстрації часу
51213000-8	Installation services of time recorder equipment	4	t	Послуги зі встановлення пристроїв обліку робочого часу
51214000-5	Installation services of parking meter equipment	4	t	Послуги зі встановлення паркоматів
51215000-2	Installation services of meteorological equipment	4	t	Послуги зі встановлення метеорологічного обладнання
51216000-9	Installation services of geological equipment	4	t	Послуги зі встановлення геологічного обладнання
51220000-0	Installation services of checking equipment	3	f	Послуги зі встановлення контрольного обладнання
51221000-7	Installation services of automatic airport check-in devices	4	t	Послуги зі встановлення автоматичних пристроїв реєстрації пасажирів аеропортів
51230000-3	Installation services of testing equipment	3	t	Послуги зі встановлення випробувального обладнання
51240000-6	Installation services of navigating equipment	3	t	Послуги зі встановлення навігаційного обладнання
51300000-5	Installation services of communications equipment	2	f	Послуги зі встановлення комунікаційного обладнання
51310000-8	Installation services of radio, television, sound and video equipment	3	f	Послуги зі встановлення радіо-, телевізійної, аудіо- та відеоапаратури
51311000-5	Installation services of radio equipment	4	t	Послуги зі встановлення радіоапаратури
51314000-6	Installation services of video equipment	4	t	Послуги зі встановлення відеоапаратури
51320000-1	Installation services of radio and television transmitters	3	f	Послуги зі встановлення радіо- та телевізійних передавачів
51321000-8	Installation services of radio transmitters	4	t	Послуги зі встановлення радіопередавачів
51322000-5	Installation services of television transmitters	4	t	Послуги зі встановлення телевізійних передавачів
51330000-4	Installation services of radiotelephony apparatus	3	t	Послуги зі встановлення радіотелефонної апаратури
51340000-7	Installation services of line telephony equipment	3	t	Послуги зі встановлення обладнання для дротового телефонного зв’язку
51350000-0	Installation services of line telegraphy equipment	3	t	Послуги зі встановлення обладнання для дротового телеграфного зв’язку
51400000-6	Installation services of medical and surgical equipment	2	f	Послуги зі встановлення медичного та хірургічного обладнання
51410000-9	Installation services of medical equipment	3	f	Послуги зі встановлення медичного обладнання
51411000-6	Installation services of imaging equipment	4	t	Послуги зі встановлення візуалізаційного обладнання
51412000-3	Installation services of dental and subspecialty equipment	4	t	Послуги зі встановлення стоматологічного та вузькоспеціалізованого обладнання
51413000-0	Installation services of radiotherapy equipment	4	t	Послуги зі встановлення радіотерапевтичного обладнання
51414000-7	Installation services of mechanotherapy equipment	4	t	Послуги зі встановлення обладнання для механотерапії
51415000-4	Installation services of electrotherapy equipment	4	t	Послуги зі встановлення електротерапевтичного обладнання
51416000-1	Installation services of physical therapy equipment	4	t	Послуги зі встановлення обладнання для фізіотерапії
51420000-2	Installation services of surgical equipment	3	t	Послуги зі встановлення хірургічного обладнання
51430000-5	Installation services of laboratory equipment	3	t	Послуги зі встановлення лабораторного обладнання
51500000-7	Installation services of machinery and equipment	2	f	Послуги зі встановлення машин та обладнання
51510000-0	Installation services of general-purpose machinery and equipment	3	f	Послуги зі встановлення універсальних машин та обладнання
51511000-7	Installation services of lifting and handling equipment, except lifts and escalators	4	f	Послуги зі встановлення підіймально-транспортувального обладнання, крім ліфтів та ескалаторів
51511100-8	Installation services of lifting equipment	5	f	Послуги зі встановлення підіймального обладнання
51511110-1	Installation services of cranes	6	t	Послуги зі встановлення підіймальних кранів
51511200-9	Installation services of handling equipment	5	t	Послуги зі встановлення вантажно-розвантажувального обладнання
51511300-0	Installation services of suspended access equipment	5	t	Послуги зі встановлення фасадних люльок
51511400-1	Installation services of special conveying systems	5	t	Послуги зі встановлення спеціальних конвеєрних систем
51514000-8	Installation services of miscellaneous general-purpose machinery	4	f	Послуги зі встановлення різних універсальних машин
51514100-9	Installation services of liquid filtering or purifying machinery and apparatus	5	f	Послуги зі встановлення машин та апаратури для фільтрування чи очищення рідин
51514110-2	Installation services of machinery and apparatus for filtering or purifying water	6	t	Послуги зі встановлення машин та апаратури для фільтрування чи очищення води
51520000-3	Installation services of agricultural and forestry machinery	3	f	Послуги зі встановлення сільськогосподарської та лісівничої техніки
51521000-0	Installation services of agricultural machinery	4	t	Послуги зі встановлення сільськогосподарської техніки
51522000-7	Installation services of forestry machinery	4	t	Послуги зі встановлення лісівничої техніки
51530000-6	Installation services of machine tools	3	t	Послуги зі встановлення верстатів
51540000-9	Installation services of special-purpose machinery and equipment	3	f	Послуги зі встановлення машин та обладнання спеціального призначення
51541000-6	Installation services of mining, quarrying, construction and metallurgy machinery	4	f	Послуги зі встановлення гірничої, видобувної, будівельної та металургійної техніки
51541100-7	Installation services of mining machinery	5	t	Послуги зі встановлення гірничої техніки
51541200-8	Installation services of quarrying machinery	5	t	Послуги зі встановлення видобувної техніки
51541300-9	Installation services of construction machinery	5	t	Послуги зі встановлення будівельної техніки
51541400-0	Installation services of metallurgy machinery	5	t	Послуги зі встановлення металургійної техніки
55260000-0	Sleeping-car services	3	t	Послуги спальних вагонів
63733000-6	Aircraft refuelling services	4	t	Послуги із заправлення повітряного транспорту
51542000-3	Installation services of food-, beverage- and tobacco-processing machinery	4	f	Послуги зі встановлення машин для обробки продуктів харчування, виробництва напоїв та обробки тютюну
51542100-4	Installation services of food-processing machinery	5	t	Послуги зі встановлення машин для обробки продуктів харчування
51542200-5	Installation services of beverage-processing machinery	5	t	Послуги зі встановлення машин для виробництва напоїв
51542300-6	Installation services of tobacco-processing machinery	5	t	Послуги зі встановлення машин для обробки тютюну
51543000-0	Installation services of textile-, clothing- and leather-production machinery	4	f	Послуги зі встановлення машин для виробництва тканин, одягу та шкіри
51543100-1	Installation services of textile-production machinery	5	t	Послуги зі встановлення машин для виробництва тканин
51543200-2	Installation services of clothing-production machinery	5	t	Послуги зі встановлення машин для виробництва одягу
51543300-3	Installation services of leather-production machinery	5	t	Послуги зі встановлення машин для виробництва шкіри
51543400-4	Installation services of laundry washing, dry-cleaning and drying machines	5	t	Послуги зі встановлення машин для прання, сухого чищення та сушіння
51544000-7	Installation services of paper- and paperboard-production machinery	4	f	Послуги зі встановлення машин для виробництва паперу та картону
51544100-8	Installation services of paper-production machinery	5	t	Послуги зі встановлення машин для виробництва паперу
51544200-9	Installation services of paperboard-production machinery	5	t	Послуги зі встановлення машин для виробництва картону
51545000-4	Installation services of street mailboxes	4	t	Послуги зі встановлення вуличних поштових скриньок
51550000-2	Installation services of weapon systems	3	t	Послуги зі встановлення систем озброєння
51600000-8	Installation services of computers and office equipment	2	f	Послуги зі встановлення комп’ютерної та офісної техніки
51610000-1	Installation services of computers and information-processing equipment	3	f	Послуги зі встановлення комп’ютерної техніки та обладнання для обробки інформації
51611000-8	Installation services of computers	4	f	Послуги зі встановлення комп’ютерів
51611100-9	Hardware installation services	5	f	Послуги зі встановлення комп’ютерного апаратного забезпечення
51611110-2	Installation services of airport real-time departures and arrival display screens or boards	6	t	Послуги зі встановлення екранів чи табло для відображення інформації про вильоти та прибуття літаків у режимі реального часу
51611120-5	Installation services of railway real-time departures and arrival display screens or boards	6	t	Послуги зі встановлення екранів чи табло для відображення інформації про вильоти та прибуття поїздів у режимі реального часу
51612000-5	Installation services of information-processing equipment	4	t	Послуги зі встановлення обладнання для обробки інформації
51620000-4	Installation services of office equipment	3	t	Послуги зі встановлення офісного обладнання
51700000-9	Installation services of fire protection equipment	2	t	Послуги зі встановлення протипожежного устаткування
51800000-0	Installation services of metal containers	2	f	Послуги зі встановлення металевих контейнерів
51810000-3	Installation services of tanks	3	t	Послуги зі встановлення цистерн
51820000-6	Installation services of reservoirs	3	t	Послуги зі встановлення резервуарів
51900000-1	Installation services of guidance and control systems	2	t	Послуги зі встановлення систем наведення і контролю
55000000-0	Hotel, restaurant and retail trade services	1	f	Готельні, ресторанні послуги та послуги з роздрібної торгівлі
55100000-1	Hotel services	2	f	Готельні послуги
55110000-4	Hotel accommodation services	3	t	Послуги з розміщення у готелях
55120000-7	Hotel meeting and conference services	3	t	Послуги з організації зустрічей і конференцій у готелях
55130000-0	Other hotel services	3	t	Інші готельні послуги
55200000-2	Camping sites and other non-hotel accommodation	2	f	Послуги з розміщення на майданчиках для кемпінгу та інших закладах неготельного типу
55210000-5	Youth hostel services	3	t	Послуги молодіжних хостелів / туристичних баз
55220000-8	Camping-site services	3	f	Послуги з оренди місць на майданчиках для кемпінгу
55221000-5	Caravan-site services	4	t	Послуги з оренди житлових причепів
55240000-4	Holiday centre and holiday home services	3	f	Послуги центрів і будинків відпочинку
55241000-1	Holiday centre services	4	t	Послуги центрів відпочинку
55242000-8	Holiday home services	4	t	Послуги будинків відпочинку
55243000-5	Children's holiday-camp services	4	t	Послуги дитячих таборів
55250000-7	Letting services of short-stay furnished accommodation	3	t	Послуги з надання в короткострокову оренду умебльованого житла
55270000-3	Services provided by bed and breakfast establishments	3	t	Послуги закладів, що пропонують ночівлю та сніданок
55300000-3	Restaurant and food-serving services	2	f	Ресторанні послуги та послуги офіціантів
55310000-6	Restaurant waiter services	3	f	Послуги з ресторанного обслуговування
55311000-3	Restricted-clientele restaurant waiter services	4	t	Послуги з обслуговування у ресторанах закритого типу
55312000-0	Unrestricted-clientele restaurant waiter services	4	t	Послуги з обслуговування у ресторанах відкритого типу
55320000-9	Meal-serving services	3	f	Послуги офіціантів
55321000-6	Meal-preparation services	4	t	Послуги з готування їжі
55322000-3	Meal-cooking services	4	t	Послуги кухарів
55330000-2	Cafeteria services	3	t	Послуги кафе
55400000-4	Beverage-serving services	2	f	Послуги з подавання напоїв
55410000-7	Bar management services	3	t	Послуги адміністраторів барів
55500000-5	Canteen and catering services	2	f	Послуги їдалень та кейтерингові послуги
55510000-8	Canteen services	3	f	Послуги їдалень
55511000-5	Canteen and other restricted-clientele cafeteria services	4	t	Послуги їдалень та інших кафе закритого типу
55512000-2	Canteen management services	4	t	Послуги адміністраторів їдалень
55520000-1	Catering services	3	f	Кейтерингові послуги
55521000-8	Catering services for private households	4	f	Послуги з доставки їжі додому
55521100-9	Meals-on-wheels services	5	t	Послуги пересувних кухонь
55521200-0	Meal delivery service	5	t	Послуги з доставки їжі
55522000-5	Catering services for transport enterprises	4	t	Кейтерингові послуги для транспортних підприємств
55523000-2	Catering services for other enterprises or other institutions	4	f	Кейтерингові послуги для інших підприємств або установ
55523100-3	School-meal services	5	t	Послуги з організації шкільного харчування
55524000-9	School catering services	4	t	Кейтерингові послуги для шкіл
55900000-9	Retail trade services	2	t	Послуги з роздрібної торгівлі
60000000-8	Transport services (excl. Waste transport)	1	f	Транспортні послуги (крім транспортування відходів)
60100000-9	Road transport services	2	f	Послуги з автомобільних перевезень
60112000-6	Public road transport services	4	t	Послуги громадського автомобільного транспорту
60120000-5	Taxi services	3	t	Послуги таксі
60130000-8	Special-purpose road passenger-transport services	3	t	Послуги спеціалізованих автомобільних перевезень пасажирів
60140000-1	Non-scheduled passenger transport	3	t	Нерегулярні пасажирські перевезення
60150000-4	Passenger transport by animal-drawn vehicles	3	t	Перевезення пасажирів гужовим транспортом
60160000-7	Mail transport by road	3	f	Перевезення пошти автомобільним транспортом
60161000-4	Parcel transport services	4	t	Послуги з перевезення пакунків
60170000-0	Hire of passenger transport vehicles with driver	3	f	Прокат пасажирських транспортних засобів із водієм
60171000-7	Hire of passenger cars with driver	4	t	Прокат легкових автомобілів із водієм
60172000-4	Hire of buses and coaches with driver	4	t	Прокат автобусів і туристичних автобусів із водієм
60180000-3	Hire of goods-transport vehicles with driver	3	f	Прокат вантажних транспортних засобів із водієм для перевезення товарів
60181000-0	Hire of trucks with driver	4	t	Прокат вантажних автомобілів із водієм
60182000-7	Hire of industrial vehicles with driver	4	t	Прокат промислових транспортних засобів із водієм
60183000-4	Hire of vans with driver	4	t	Прокат фургонів із водієм
60200000-0	Railway transport services	2	f	Послуги із залізничних перевезень
60210000-3	Public transport services by railways	3	t	Послуги з перевезень громадським залізничним транспортом
60220000-6	Mail transport by railway	3	t	Перевезення пошти залізничним транспортом
60300000-1	Pipeline transport services	2	t	Послуги з транспортування трубопроводами
60400000-2	Air transport services	2	f	Послуги з авіаційних перевезень
60410000-5	Scheduled air transport services	3	f	Послуги з регулярних авіаційних перевезень
60411000-2	Scheduled airmail transport services	4	t	Послуги з перевезення пошти регулярними авіаційними рейсами
60420000-8	Non-scheduled air transport services	3	f	Послуги з нерегулярних авіаційних перевезень
60421000-5	Non-scheduled airmail transport services	4	t	Послуги з перевезення пошти нерегулярними авіаційними рейсами
60423000-9	Air-charter services	4	t	Послуги з чартерних авіаційних перевезень
60424000-6	Hire of air transport equipment with crew	4	f	Прокат авіаційного транспортного устаткування з екіпажем
60424100-7	Hire of aircraft with crew	5	f	Прокат літальних апаратів із екіпажем
60424110-0	Hire of fixed-wing aircraft with crew	6	t	Прокат літальних апаратів із нерухомими крилами із екіпажем
60424120-3	Hire of helicopters with crew	6	t	Прокат вертольотів із екіпажем
60440000-4	Aerial and related services	3	f	Послуги у сфері повітряного сполучення та супутні послуги
60441000-1	Aerial spraying services	4	t	Послуги з обприскування з повітря
60442000-8	Aerial forest-firefighting services	4	t	Послуги з гасіння лісових пожеж із повітря
60443000-5	Air-rescue services	4	f	Послуги з проведення авіаційно-рятувальних операцій
60443100-6	Air-sea rescue services	5	t	Послуги з проведення авіаційно-рятувальних операцій у морі
60444000-2	Aircraft-operation services	4	f	Послуги з експлуатації літальних апаратів
60444100-3	Pilot services	5	t	Послуги з пілотування
60445000-9	Aircraft operating services	4	t	Послуги з обслуговування літальних апаратів
60500000-3	Space transport services	2	f	Послуги з космічних перевезень
60510000-6	Satellite launch services	3	t	Послуги із запуску супутників
60520000-9	Experimental payload services	3	t	Послуги з експериментального корисного навантаження
60600000-4	Water transport services	2	f	Послуги з водних перевезень
60610000-7	Ferry transport services	3	t	Послуги з перевезення поромним транспортом
60620000-0	Transport by water of mail	3	t	Перевезення пошти водним транспортом
60630000-3	Cable-laying ship services	3	t	Послуги суден з укладання кабелів
60640000-6	Shipping operations	3	t	Перевезення вантажів морським транспортом
60650000-9	Hire of water transport equipment with crew	3	f	Прокат водного транспортного устаткування з екіпажем
60651000-6	Hire of vessels with crew	4	f	Прокат суден із екіпажем
60651100-7	Hire of sea-going vessels with crew	5	t	Прокат морських суден із екіпажем
60651200-8	Hire of inland waterway vessels with crew	5	t	Прокат суден внутрішнього плавання з екіпажем
60651300-9	Anti-pollution ship services	5	t	Послуги очисних суден
60651400-0	Heavy-lift ship services	5	t	Послуги суден великої тоннажності
60651500-1	Standby ship services	5	t	Послуги резервних суден
60651600-2	Offshore supply ship services	5	t	Послуги суден-постачальників із обслуговування морських платформ
60653000-0	Hire of boats with crew	4	t	Прокат човнів із екіпажем
63000000-9	Supporting and auxiliary transport services; travel agencies services	1	f	Додаткові та допоміжні транспортні послуги; послуги туристичних агентств
63100000-0	Cargo handling and storage services	2	f	Послуги з обробки та зберігання вантажів
63110000-3	Cargo handling services	3	f	Послуги з обробки вантажів
63111000-0	Container handling services	4	t	Послуги з обробки вантажів у контейнерах
63112000-7	Baggage handling services	4	f	Послуги з обробки багажу
63112100-8	Passenger baggage handling services	5	f	Послуги з обробки пасажирського багажу
63112110-1	Baggage collection services	6	t	Послуги з приймання багажу
63120000-6	Storage and warehousing services	3	f	Послуги зберігання та складування
63121000-3	Storage and retrieval services	4	f	Послуги зберігання та видачі
63121100-4	Storage services	5	f	Послуги зберігання
63121110-7	Gas storage services	6	t	Послуги зі зберігання газу
63122000-0	Warehousing services	4	t	Послуги складування
63500000-4	Travel agency, tour operator and tourist assistance services	2	f	Послуги туристичних агентств, туристичних операторів і туристичної підтримки
63510000-7	Travel agency and similar services	3	f	Послуги туристичних агентств та подібні послуги
63511000-4	Organisation of package tours	4	t	Організація пакетних турів
63512000-1	Sale of travel tickets and package tours services	4	t	Послуги з продажу квитків і пакетних турів
63513000-8	Tourist information services	4	t	Послуги з надання туристичної інформації
63514000-5	Tourist guide services	4	t	Послуги туристичних гідів
63515000-2	Travel services	4	t	Туристичні послуги
63516000-9	Travel management services	4	t	Послуги з управління подорожами
63520000-0	Transport agency services	3	f	Послуги транспортних агентств
63521000-7	Freight transport agency services	4	t	Послуги агентств вантажних перевезень
63522000-4	Ship brokerage services	4	t	Послуги суднових брокерів
63523000-1	Port and forwarding agency services	4	t	Послуги портових та експедиційних агентств
63524000-8	Transport document preparation services	4	t	Послуги з оформлення транспортної документації
63700000-6	Support services for land, water and air transport	2	f	Послуги з обслуговування наземних, водних або повітряних видів транспорту
63710000-9	Support services for land transport	3	f	Послуги з обслуговування наземних видів транспорту
63711000-6	Support services for railway transport	4	f	Послуги з обслуговування залізничного транспорту
63711100-7	Train monitoring services	5	t	Послуги з моніторингу руху поїздів
63711200-8	Moving workshops services	5	t	Послуги пересувних цехів
63712000-3	Support services for road transport	4	f	Послуги з обслуговування автомобільного транспорту
63712100-4	Bus station services	5	t	Послуги автовокзалів
63712200-5	Highway operation services	5	f	Послуги з експлуатації автомобільних доріг
63712210-8	Highway toll services	6	t	Послуги зі стягнення плати за користування автомобільними дорогами
63712300-6	Bridge and tunnel operation services	5	f	Послуги з експлуатації мостів і тунелів
63712310-9	Bridge operating services	6	f	Послуги з експлуатації мостів
63712311-6	Bridge toll services	7	t	Послуги зі стягнення плати за користування мостами
63712320-2	Tunnel operation services	6	f	Послуги з експлуатації тунелів
63712321-9	Tunnel toll services	7	t	Послуги зі стягнення плати за користування тунелями
63712400-7	Parking services	5	t	Паркувальні послуги
63712500-8	Weighbridge services	5	t	Послуги операторів автомобільних ваг
63712600-9	Vehicle refuelling services	5	t	Послуги із заправлення транспортних засобів
63712700-0	Traffic control services	5	f	Послуги з керування дорожнім рухом
63712710-3	Traffic monitoring services	6	t	Послуги з моніторингу дорожнього руху
63720000-2	Support services for water transport	3	f	Послуги з обслуговування водних видів транспорту
63721000-9	Port and waterway operation services and associated services	4	f	Послуги з експлуатації портів і водних шляхів та супутні послуги
63721100-0	Bunkering services	5	t	Послуги з бункерування
63721200-1	Port operation services	5	t	Послуги з експлуатації портів
63721300-2	Waterway operation services	5	t	Послуги з експлуатації водних шляхів
63721400-3	Ship refuelling services	5	t	Послуги із заправлення суден
63721500-4	Passenger terminal operation services	5	t	Послуги з експлуатації пасажирських терміналів
63722000-6	Ship-piloting services	4	t	Послуги лоцманів
63723000-3	Berthing services	4	t	Послуги з причалювання
63724000-0	Navigation services	4	f	Навігаційні послуги
63724100-1	Offshore positioning services	5	f	Послуги з позиціонування суден у відкритому морі
63724110-4	Lightship positioning services	6	t	Послуги з позиціонування пливучих маяків
63724200-2	Lightship services	5	t	Послуги пливучих маяків
63724300-3	Buoy positioning services	5	f	Послуги з позиціонування буїв
63724310-6	Buoy marking services	6	t	Послуги з позначення за допомогою буїв
63724400-4	Lighthouse services	5	t	Послуги маяків
63725000-7	Salvage and refloating services	4	f	Послуги з рятування суден і знімання суден з мілини
63725100-8	Vessel-salvaging services	5	t	Послуги з рятування суден
63725200-9	Standby vessel services	5	t	Послуги резервних суден
63725300-0	Vessel refloating services	5	t	Послуги зі знімання суден з мілини
63726000-4	Miscellaneous water transport support services	4	f	Послуги з обслуговування водного транспорту різні
63726100-5	Vessel registration services	5	t	Послуги з реєстрації суден
63726200-6	Ice-breaking services	5	t	Послуги з ламання криги
63726300-7	Vessel storage services	5	t	Послуги зі зберігання суден
63726400-8	Ship chartering services	5	t	Послуги з фрахтування суден
63726500-9	Vessel laying-up services	5	t	Послуги зі швартування суден
63726600-0	Ship-operating services	5	f	Послуги з експлуатації суден
63726610-3	Ship-launching services	6	t	Послуги зі спускання суден на воду
63726620-6	ROV services	6	t	Послуги транспортних засобів із дистанційним керуванням
63726700-1	Fishing-vessel services	5	t	Послуги риболовних суден
63726800-2	Research vessel services	5	t	Послуги науково-дослідницьких суден
63726900-3	Anchor handling services	5	t	Послуги з якорування
63727000-1	Towing and pushing services of ships	4	f	Послуги з буксирування та штовхання суден
63727100-2	Towing services	5	t	Послуги з буксирування суден
63727200-3	Pushing services	5	t	Послуги зі штовхання суден
63730000-5	Support services for air transport	3	f	Послуги з обслуговування повітряного транспорту
63731000-2	Airport operation services	4	f	Послуги з експлуатації аеропортів
63731100-3	Airport slot coordination services	5	t	Послуги з координування слотів аеропортів
63732000-9	Air-traffic control services	4	t	Послуги з керування повітряним рухом
64000000-6	Postal and telecommunications services	1	f	Поштові та телекомунікаційні послуги
64100000-7	Post and courier services	2	f	Поштові та кур’єрські послуги
64110000-0	Postal services	3	f	Поштові послуги
64111000-7	Postal services related to newspapers and periodicals	4	t	Поштові послуги з доставки газет і періодичних видань
64112000-4	Postal services related to letters	4	t	Поштові послуги з доставки письмової кореспонденції
64113000-1	Postal services related to parcels	4	t	Поштові послуги з доставки пакунків
64114000-8	Post office counter services	4	t	Послуги поштових відділень
64115000-5	Mailbox rental	4	t	Оренда поштових скриньок
64116000-2	Post-restante services	4	t	Поштові послуги «до запитання»
64120000-3	Courier services	3	f	Кур’єрські послуги
64121000-0	Multi-modal courier services	4	f	Кур’єрські послуги за мультимодальною системою
64121100-1	Mail delivery services	5	t	Послуги з доставки пошти
64121200-2	Parcel delivery services	5	t	Послуги з доставки пакунків
64122000-7	Internal office mail and messenger services	4	t	Послуги з доставки пошти і повідомлень у межах організації
64200000-8	Telecommunications services	2	f	Телекомунікаційні послуги
64210000-1	Telephone and data transmission services	3	f	Послуги телефонного зв’язку та передачі даних
64211000-8	Public-telephone services	4	f	Послуги громадського телефонного зв’язку
64211100-9	Local telephone services	5	t	Послуги міського телефонного зв’язку
64211200-0	Long distance telephone services	5	t	Послуги міжміського телефонного зв’язку
64212000-5	Mobile-telephone services	4	f	Послуги мобільного телефонного зв’язку
64212100-6	Short Message Service (SMS) services	5	t	Послуги служби коротких повідомлень (SMS)
64212200-7	Enhanced Messaging Service (EMS) services	5	t	Послуги служби розширених повідомлень (EMS)
64212300-8	Multimedia Message Service (MMS) services	5	t	Послуги служби мультимедійних повідомлень (MMS)
64212400-9	Wireless Application Protocol (WAP) services	5	t	Послуги передачі даних за протоколом бездротового доступу (WAP)
64212500-0	General Packet Radio Services (GPRS) services	5	t	Послуги загального сервісу пакетної радіопередачі (GMRS)
64212600-1	Enhanced Data for GSM Evolution (EDGE) services	5	t	Послуги передачі великих обсягів даних у мережі мобільного зв’язку за технологією EDGE
64212700-2	Universal Mobile Telephone System (UMTS) services	5	t	Послуги універсальної мобільної системи телефонії (UMTS)
64212800-3	Pay phone provider services	5	t	Послуги провайдерів таксофонного зв’язку
64212900-4	Pre-paid phone card provider services	5	t	Послуги провайдерів передоплатного телефонного зв’язку за телефонними картками
64213000-2	Shared-business telephone network services	4	t	Послуги офісних міні-АТС
64214000-9	Dedicated-business telephone network services	4	f	Послуги офісних міні-АТС спеціального призначення
64214100-0	Satellite circuit rental services	5	t	Послуги з оренди каналу супутникового зв’язку
64214200-1	Telephone switchboard services	5	t	Послуги телефонних комутаторів
64214400-3	Communication land-line rental	5	t	Оренда наземних ліній зв’язку
64215000-6	IP telephone services	4	t	Послуги ІР-телефонії
64216000-3	Electronic message and information services	4	f	Послуги систем електронної передачі електронних повідомлень та інформації
64216100-4	Electronic message services	5	f	Послуги систем електронної передачі електронних повідомлень
64216110-7	Electronic data exchange services	6	t	Послуги систем електронного обміну даними
64216120-0	Electronic mail services	6	t	Послуги електронної пошти
64216130-3	Telex services	6	t	Послуги телексного зв’язку
64216140-6	Telegraph services	6	t	Послуги телеграфного зв’язку
64216200-5	Electronic information services	5	f	Електронні інформаційні послуги
64216210-8	Value-added information services	6	t	Додаткові інформаційні послуги
64216300-6	Teletext services	5	t	Послуги телетекстового зв’язку
64220000-4	Telecommunication services except telephone and data transmission services	3	f	Телекомунікаційні послуги, крім послуг телефонного зв’язку і передачі даних
64221000-1	Interconnection services	4	t	Послуги з під’єднання
64222000-8	Teleworking services	4	t	Послуги, пов’язані з дистанційною роботою
64223000-5	Paging services	4	t	Послуги пейджингового зв’язку
64224000-2	Teleconferencing services	4	t	Послуги зв’язку з режимі телеконференції
64225000-9	Air-to-ground telecommunications services	4	t	Послуги телекомунікаційного зв’язку каналом «повітря – земля»
64226000-6	Telematics services	4	t	Телематичні послуги
64227000-3	Integrated telecommunications services	4	t	Інтегровані телекомунікаційні послуги
64228000-0	Television and radio broadcast transmission services	4	f	Послуги з транслювання теле- та радіопередач
64228100-1	Television broadcast transmission services	5	t	Послуги з транслювання телепередач
64228200-2	Radio broadcast transmission services	5	t	Послуги з транслювання радіопередач
65000000-3	Public utilities	1	f	Комунальні послуги
65100000-4	Water distribution and related services	2	f	Послуги з розподілу води та супутні послуги
65110000-7	Water distribution	3	f	Розподіл води
65111000-4	Drinking-water distribution	4	t	Розподіл питної води
65120000-0	Operation of a water-purification plant	3	f	Експлуатація водоочищувальних станцій
65121000-7	Water demineralisation services	4	t	Послуги з демінералізації води
65122000-0	Water desalination services	4	t	Послуги з опріснення води
65123000-3	Water softening services	4	t	Послуги з пом’якшення води
65130000-3	Operation of water supplies	3	t	Експлуатування систем водопостачання
65200000-5	Gas distribution and related services	2	f	Послуги з розподілу газу та супутні послуги
65210000-8	Gas distribution	3	t	Розподіл газу
65300000-6	Electricity distribution and related services	2	f	Розподіл електричної енергії та супутні послуги
65310000-9	Electricity distribution	3	t	Розподіл електричної енергії
65320000-2	Operation of electrical installations	3	t	Експлуатація електричних установок
65400000-7	Other sources of energy supplies and distribution	2	f	Інші джерела постачання та розподілу енергії
65410000-0	Operation of a power plant	3	t	Експлуатація енергетичних станцій
65500000-8	Meter reading service	2	t	Послуги з реєстрації показів лічильників
66000000-0	Financial and insurance services	1	f	Фінансові та страхові послуги
66100000-1	Banking and investment services	2	f	Банківські та інвестиційні послуги
66110000-4	Banking services	3	f	Банківські послуги
66111000-1	Central bank services	4	t	Послуги центральних банків
66112000-8	Deposit services	4	t	Депозитні послуги
66113000-5	Credit granting services	4	f	Послуги з кредитування
66113100-6	Micro-credit granting services	5	t	Послуги з мікрокредитування
66114000-2	Financial leasing services	4	t	Послуги у сфері фінансового лізингу
66115000-9	International payment transfer services	4	t	Послуги з міжнародних розрахунків
66120000-7	Investment banking services and related services	3	f	Послуги у сфері банківських інвестицій і супутні послуги
66121000-4	Mergers and acquisition services	4	t	Послуги, пов’язані зі злиттями і поглинаннями
66122000-1	Corporate finance and venture capital services	4	t	Послуги у сфері корпоративного фінансування та венчурного капіталу
66130000-0	Brokerage and related securities and commodities services	3	f	Брокерські послуги та пов’язані послуги на ринках цінних паперів і товарів
66131000-7	Security brokerage services	4	f	Брокерські послуги на ринках цінних паперів
66131100-8	Pension investment services	5	t	Послуги у сфері пенсійного інвестування
66132000-4	Commodity brokerage services	4	t	Брокерські послуги на ринках товарів
66133000-1	Processing and clearing services	4	t	Послуги з митного оформлення та розмитнення
66140000-3	Portfolio management services	3	f	Послуги з управління портфелями активів
66141000-0	Pension fund management services	4	t	Послуги з управління пенсійними фондами
66150000-6	Financial markets administration services	3	f	Послуги з управління фінансовими ринками
66151000-3	Financial market operational services	4	f	Послуги з оперативного управління фінансовими ринками
66151100-4	Electronic marketplace retailing services	5	t	Послуги з роздрібної торгівлі на електронних ринках
66152000-0	Financial market regulatory services	4	t	Послуги з регулювання фінансових ринків
66160000-9	Trust and custody services	3	f	Послуги з довірчого управління та депозитарного зберігання
66161000-6	Trust services	4	t	Послуги з довірчого управління
66162000-3	Custody services	4	t	Послуги з депозитарного зберігання
66170000-2	Financial consultancy, financial transaction processing and clearing-house services	3	f	Послуги з надання фінансових консультацій, обробки фінансових транзакцій і клірингові послуги
66171000-9	Financial consultancy services	4	t	Послуги з надання фінансових консультацій
66172000-6	Financial transaction processing and clearing-house services	4	t	Послуги з обробки фінансових транзакцій і клірингові послуги
66180000-5	Foreign exchange services	3	t	Послуги з обміну іноземних валют
66190000-8	Loan brokerage services	3	t	Послуги кредитних брокерів
66500000-5	Insurance and pension services	2	f	Страхові та пенсійні послуги
66510000-8	Insurance services	3	f	Страхові послуги
66511000-5	Life insurance services	4	t	Послуги зі страхування життя
66512000-2	Accident and health insurance services	4	f	Послуги зі страхування від нещасних випадків і страхування здоров’я
66512100-3	Accident insurance services	5	t	Послуги зі страхування від нещасних випадків
66512200-4	Health insurance services	5	f	Послуги зі страхування здоров’я
66512210-7	Voluntary health insurance services	6	t	Послуги з добровільного страхування здоров’я
66512220-0	Medical insurance services	6	t	Послуги з медичного страхування
66513000-9	Legal insurance and all-risk insurance services	4	f	Послуги зі страхування юридичних витрат і страхування від усіх ризиків
66513100-0	Legal expenses insurance services	5	t	Послуги зі страхування юридичних витрат
66513200-1	Contractor's all-risk insurance services	5	t	Послуги зі страхування підрядників від усіх ризиків
66514000-6	Freight insurance and Insurance services relating to transport	4	f	Послуги зі страхування вантажів та послуги з транспортного страхування
66514100-7	Insurance related to Transport	5	f	Транспортне страхування
66514110-0	Motor vehicle insurance services	6	t	Послуги зі страхування транспортних засобів
66514120-3	Marine, aviation and other transport insurance services	6	t	Послуги зі страхування морського, повітряного та інших видів транспорту
66514130-6	Railway insurance services	6	t	Послуги зі страхування залізничного транспорту
66514140-9	Aircraft insurance services	6	t	Послуги зі страхування повітряного транспорту
66514150-2	Vessel insurance services	6	t	Послуги зі страхування суден
66514200-8	Freight insurance services	5	t	Послуги зі страхування вантажів
66515000-3	Damage or loss insurance services	4	f	Послуги зі страхування від шкоди чи збитків
66515100-4	Fire insurance services	5	t	Послуги зі страхування від пожежі
66515200-5	Property insurance services	5	t	Послуги зі страхування майна
66515300-6	Weather and financial loss insurance services	5	t	Послуги зі страхування від збитків внаслідок впливу погодних умов і фінансових збитків
66515400-7	Weather-related insurance services	5	f	Послуги зі страхування від збитків внаслідок впливу погодних умов
66515410-0	Financial loss insurance services	6	f	Послуги зі страхування від фінансових збитків
66515411-7	Pecuniary loss insurance services	7	t	Послуги зі страхування від грошових збитків
66516000-0	Liability insurance services	4	f	Послуги зі страхування цивільної відповідальності
66516100-1	Motor vehicle liability insurance services	5	t	Послуги зі страхування цивільної відповідальності власників автомобільного транспорту
66516200-2	Aircraft liability insurance services	5	t	Послуги зі страхування цивільної відповідальності власників повітряного транспорту
66516300-3	Vessel liability insurance services	5	t	Послуги зі страхування цивільної відповідальності власників суден
66516400-4	General liability insurance services	5	t	Послуги зі страхування загальної цивільної відповідальності
66516500-5	Professional liability insurance services	5	t	Послуги зі страхування професійної відповідальності
66517000-7	Credit and surety insurance services	4	f	Послуги зі страхування кредитів і гарантійних зобов’язань
66517100-8	Credit insurance services	5	t	Послуги зі страхування кредитів
66517200-9	Surety insurance services	5	t	Послуги зі страхування гарантійних зобов’язань
66517300-0	Risk management insurance services	5	t	Страхові послуги у сфері управління ризиками
66518000-4	Insurance brokerage and agency services	4	f	Послуги страхових брокерів і страхових агентств
66518100-5	Insurance brokerage services	5	t	Послуги страхових брокерів
66518200-6	Insurance agency services	5	t	Послуги страхових агентств
66518300-7	Insurance claims adjustment services	5	t	Послуги з урегулювання страхових вимог
66519000-1	Engineering, auxiliary, average, loss, actuarial and salvage insurance services	4	f	Послуги зі страхування технічного обладнання, від аварій, збитків, актуарних ризиків та вартості врятованого майна, а також додаткові послуги у сфері страхування
66519100-2	Oil or gas platforms insurance services	5	t	Послуги зі страхування нафтових або газових платформ
66519200-3	Engineering insurance services	5	t	Послуги зі страхування технічного обладнання
66519300-4	Auxiliary insurance services	5	f	Додаткові послуги у сфері страхування
66519310-7	Insurance consultancy services	6	t	Консультаційні послуги з питань страхування
75110000-0	General public services	3	f	Загальні державні послуги
66519400-5	Average adjustment services	5	t	Послуги зі врегулювання вимог щодо страхового відшкодування збитків унаслідок аварій
66519500-6	Loss adjustment services	5	t	Послуги зі врегулювання вимог щодо страхового відшкодування збитків
66519600-7	Actuarial services	5	t	Актуарні послуги
66519700-8	Salvage administration services	5	t	Послуги з управління порятунком майна
66520000-1	Pension services	3	f	Пенсійні послуги
66521000-8	Individual pension services	4	t	Послуги з індивідуального пенсійного забезпечення
66522000-5	Group pension services	4	t	Послуги з колективного пенсійного забезпечення
66523000-2	Pension fund consultancy services	4	f	Консультаційні послуги з питань пенсійного забезпечення
66523100-3	Pension fund administration services	5	t	Послуги з розпорядження пенсійними фондами
66600000-6	Treasury services	2	t	Казначейські послуги
66700000-7	Reinsurance services	2	f	Послуги з перестрахування
66710000-0	Life reinsurance services	3	t	Послуги з перестраховування життя
66720000-3	Accident and health reinsurance services	3	t	Послуги з перестрахування від нещасних випадків і перестрахування здоров’я
70000000-1	Real estate services	1	f	Послуги у сфері нерухомості
70100000-2	Real estate services with own property	2	f	Послуги, пов’язані з власною нерухомістю
70110000-5	Development services of real estate	3	f	Послуги забудовників
70111000-2	Development of residential real estate	4	t	Будівництво житлової нерухомості
70112000-9	Development of non-residential real estate	4	t	Будівництво нежитлової нерухомості
70120000-8	Buying and selling of real estate	3	f	Купівля і продаж нерухомості
70121000-5	Building sale or purchase services	4	f	Послуги з продажу чи купівлі будівель
70121100-6	Building sale services	5	t	Послуги з продажу будівель
70121200-7	Building purchase services	5	t	Послуги з купівлі будівель
70122000-2	Land sale or purchase services	4	f	Послуги з продажу чи купівлі земельних ділянок
70122100-3	Land sale services	5	f	Послуги з продажу земельних ділянок
70122110-6	Vacant-land sale services	6	t	Послуги з продажу незайнятих земель
70122200-4	Land purchase services	5	f	Послуги з купівлі земельних ділянок
70122210-7	Vacant-land purchase services	6	t	Послуги з купівлі незайнятих земель
70123000-9	Sale of real estate	4	f	Продаж нерухомості
70123100-0	Sale of residential real estate	5	t	Продаж житлової нерухомості
70123200-1	Sale of non-residential estate	5	t	Продаж нежитлової нерухомості
70130000-1	Letting services of own property	3	t	Послуги зі надання в оренду власної нерухомості
70200000-3	Renting or leasing services of own property	2	f	Послуги з надання в оренду чи лізингу власної нерухомості
70210000-6	Residential property renting or leasing services	3	t	Послуги з надання в оренду чи лізингу житлової нерухомості
70220000-9	Non-residential property renting or leasing services	3	t	Послуги з надання в оренду чи лізингу нежитлової нерухомості
70300000-4	Real estate agency services on a fee or contract basis	2	f	Послуги агентств нерухомості, надавані на платній основі чи на договірних засадах
70310000-7	Building rental or sale services	3	f	Послуги з надання в оренду чи продажу будівель
70311000-4	Residential building rental or sale services	4	t	Послуги з надання в оренду чи продажу житлових будівель
70320000-0	Land rental or sale services	3	f	Послуги з надання в оренду чи продажу земельних ділянок
70321000-7	Land rental services	4	t	Послуги з надання в оренду земельних ділянок
70322000-4	Vacant-land rental or sale services	4	t	Послуги з надання в оренду чи продажу незайнятих земель
70330000-3	Property management services of real estate on a fee or contract basis	3	f	Послуги з управління нерухомістю, надавані на платній основі чи на договірних засадах
70331000-0	Residential property services	4	f	Послуги у сфері житлової власності
70331100-1	Institution management services	5	t	Послуги з інституційного управління
70332000-7	Non-residential property services	4	f	Послуги у сфері нежитлової власності
70332100-8	Land management services	5	t	Послуги з управління земельними ресурсами
70332200-9	Commercial property management services	5	t	Послуги з управління комерційною нерухомістю
70332300-0	Industrial property services	5	t	Послуги, пов’язані з промисловою нерухомістю
70333000-4	Housing services	4	t	Послуги у сфері житлового господарства
70340000-6	Time-sharing services	3	t	Послуги з надавання нерухомості у спільне користування в режимі розподілу часу
71314310-8	Heating engineering services for buildings	6	t	Послуги у сфері будівельної теплотехніки
71000000-8	Architectural, construction, engineering and inspection services	1	f	Архітектурні, будівельні, інженерні та інспекційні послуги
71200000-0	Architectural and related services	2	f	Архітектурні та супутні послуги
71210000-3	Advisory architectural services	3	t	Консультаційні послуги у сфері архітектури
71220000-6	Architectural design services	3	f	Послуги з архітектурного проектування
71221000-3	Architectural services for buildings	4	t	Послуги у сфері архітектури будівель
71222000-0	Architectural services for outdoor areas	4	f	Послуги у сфері архітектури відкритих просторів
71222100-1	Urban areas mapping services	5	t	Послуги з картування міських зон
71222200-2	Rural areas mapping services	5	t	Послуги з картування сільської місцевості
71223000-7	Architectural services for building extensions	4	t	Послуги у сфері архітектури прибудов
71230000-9	Organisation of architectural design contests	3	t	Організація конкурсів архітектурних проектів
71240000-2	Architectural, engineering and planning services	3	f	Архітектурні, інженерні та планувальні послуги
71241000-9	Feasibility study, advisory service, analysis	4	t	Техніко-економічне обґрунтування, консультаційні послуги, аналіз
71242000-6	Project and design preparation, estimation of costs	4	t	Підготовка проектів та ескізів, оцінювання витрат
71243000-3	Draft plans (systems and integration)	4	t	Проекти планів (систем та інтеграції)
71244000-0	Calculation of costs, monitoring of costs	4	t	Розрахунок витрат, моніторинг витрат
71245000-7	Approval plans, working drawings and specifications	4	t	Плани, робочі креслення та специфікації для затвердження
71246000-4	Determining and listing of quantities in construction	4	t	Здійснення та документування необхідних будівельних розрахунків
71247000-1	Supervision of building work	4	t	Нагляд за будівельними роботами
71248000-8	Supervision of project and documentation	4	t	Технічний нагляд за проектами та документацією
71250000-5	Architectural, engineering and surveying services	3	f	Архітектурні, інженерні та геодезичні послуги
71251000-2	Architectural and building-surveying services	4	t	Геодезичні послуги у галузях архітектури та будівництва
71300000-1	Engineering services	2	f	Інженерні послуги
71310000-4	Consultative engineering and construction services	3	f	Консультаційні послуги у галузях інженерії та будівництва
71311000-1	Civil engineering consultancy services	4	f	Консультаційні послуги у галузі цивільного будівництва
71311100-2	Civil engineering support services	5	t	Додаткові послуги у галузі цивільного будівництва
71311200-3	Transport systems consultancy services	5	f	Консультаційні послуги у сфері транспортних систем
71311210-6	Highways consultancy services	6	t	Консультаційні послуги у галузі будівництва автомобільних доріг
71311220-9	Highways engineering services	6	t	Послуги з проектування автомобільних доріг
71311230-2	Railway engineering services	6	t	Послуги з проектування залізничних доріг
71311240-5	Airport engineering services	6	t	Послуги з проектування аеропортів
71311300-4	Infrastructure works consultancy services	5	t	Консультаційні послуги з питань інфраструктурних робіт
71312000-8	Structural engineering consultancy services	4	t	Консультаційні послуги з питань проектування будівель і споруд
71313000-5	Environmental engineering consultancy services	4	f	Консультаційні послуги з питань екологічної інженерії
71313100-6	Noise-control consultancy services	5	t	Консультаційні послуги з питань шумозахисту
71313200-7	Sound insulation and room acoustics consultancy services	5	t	Консультаційні послуги з питань звукової ізоляції та акустики приміщень
71313400-9	Environmental impact assessment for construction	5	f	Послуги з оцінювання впливу будівництва на довкілля
71313410-2	Risk or hazard assessment for construction	6	t	Оцінювання ризиків або небезпек, пов’язаних із будівництвом
71313420-5	Environmental standards for construction	6	t	Екологічні стандарти у галузі будівництва
71313430-8	Environmental indicators analysis for construction	6	t	Аналіз екологічних показників будівельної галузі
71313440-1	Environmental Impact Assessment (EIA) services for construction	6	t	Послуги з оцінювання впливу будівництва на довкілля
71313450-4	Environmental monitoring for construction	6	t	Екологічний моніторинг у галузі будівництва
71314000-2	Energy and related services	4	f	Енергетичні та супутні послуги
71314100-3	Electrical services	5	t	Електротехнічні послуги
71314200-4	Energy-management services	5	t	Послуги з енергетичного менеджменту
71314300-5	Energy-efficiency consultancy services	5	f	Консультаційні послуги з питань енергоефективності
71315000-9	Building services	4	f	Будівельні послуги
71315100-0	Building-fabric consultancy services	5	t	Консультаційні послуги з питань будівельних матеріалів
71315200-1	Building consultancy services	5	f	Консультаційні послуги у галузі будівництва
71315210-4	Building services consultancy services	6	t	Консультаційні послуги з питань будівельних послуг
71315300-2	Building surveying services	5	t	Геодезичні послуги в галузі будівництва
71315400-3	Building-inspection services	5	f	Послуги з проведення перевірок у галузі будівництва
71315410-6	Inspection of ventilation system	6	t	Перевірка вентиляційних систем
71316000-6	Telecommunication consultancy services	4	t	Консультаційні послуги з питань телекомунікацій
71317000-3	Hazard protection and control consultancy services	4	f	Консультаційні послуги з питань попередження та контролю небезпек
71317100-4	Fire and explosion protection and control consultancy services	5	t	Консультаційні послуги з питань попередження пожеж і вибухів та захисту від них
71317200-5	Health and safety services	5	f	Послуги у сфері охорони праці та техніки безпеки
71317210-8	Health and safety consultancy services	6	t	Консультаційні послуги у сфері охорони праці та техніки безпеки
71318000-0	Advisory and consultative engineering services	4	f	Дорадчі та консультаційні послуги в галузі інженерії
71318100-1	Artificial and natural lighting engineering services for buildings	5	t	Послуги з проектування систем штучного та природного освітлення будівель
71319000-7	Expert witness services	4	t	Експертні послуги
71320000-7	Engineering design services	3	f	Послуги з інженерного проектування
71321000-4	Engineering design services for mechanical and electrical installations for buildings	4	f	Послуги з інженерного проектування механічних та електричних установок для будівель
71321100-5	Construction economics services	5	t	Послуги у галузі економіки будівництва
71321200-6	Heating-system design services	5	t	Послуги з проектування опалювальних систем
71321300-7	Plumbing consultancy services	5	t	Консультаційні послуги з питань санітарно-технічних робіт
71321400-8	Ventilation consultancy services	5	t	Консультаційні послуги з питань вентиляції
71322000-1	Engineering design services for the construction of civil engineering works	4	f	Послуги з інженерного проектування в галузі цивільного будівництва
71322100-2	Quantity surveying services for civil engineering works	5	t	Послуги з оцінювання обсягів робіт у галузі цивільного будівництва
71322200-3	Pipeline-design services	5	t	Послуги з проектування трубопроводів
71322300-4	Bridge-design services	5	t	Послуги з проектування мостів
71322400-5	Dam-design services	5	t	Послуги з проектування гребель
71322500-6	Engineering-design services for traffic installations	5	t	Послуги з інженерного проектування дорожньої інфраструктури
71323000-8	Engineering-design services for industrial process and production	4	f	Послуги з інженерного проектування промислових і виробничих процесів
71323100-9	Electrical power systems design services	5	t	Послуги з проектування електроенергетичних систем
71323200-0	Plant engineering design services	5	t	Послуги з інженерного проектування заводів
71324000-5	Quantity surveying services	4	t	Послуги з оцінювання обсягів робіт
71325000-2	Foundation-design services	4	t	Послуги з проектування фундаментів
71326000-9	Ancillary building services	4	t	Допоміжні будівельні послуги
71327000-6	Load-bearing structure design services	4	t	Послуги з проектування тримальних конструкцій
71328000-3	Verification of load-bearing structure design services	4	t	Послуги з перевірки проектів тримальних конструкцій
71330000-0	Miscellaneous engineering services	3	f	Інженерні послуги різні
71331000-7	Drilling-mud engineering services	4	t	Інженерні послуги, пов’язані з буровими розчинами
71332000-4	Geotechnical engineering services	4	t	Послуги у галузі геотехнічної інженерної
71333000-1	Mechanical engineering services	4	t	Послуги у галузі машинобудування
71334000-8	Mechanical and electrical engineering services	4	t	Послуги у галузях машинобудування та електротехніки
71335000-5	Engineering studies	4	t	Інженерні дослідження
71336000-2	Engineering support services	4	t	Допоміжні інженерні послуги
71337000-9	Corrosion engineering services	4	t	Інженерні послуги у сфері протикорозійного захисту
71340000-3	Integrated engineering services	3	t	Комплексні інженерні послуги
71350000-6	Engineering-related scientific and technical services	3	f	Науково-технічні послуги в галузі інженерії
71351000-3	Geological, geophysical and other scientific prospecting services	4	f	Послуги з геологічного, геофізичного та інших видів наукової розвідки
71351100-4	Core preparation and analysis services	5	t	Послуги з забору й аналізу керна
71351200-5	Geological and geophysical consultancy services	5	f	Консультаційні послуги в галузях геології та геофізики
71351210-8	Geophysical consultancy services	6	t	Консультаційні послуги в галузі геофізики
71351220-1	Geological consultancy services	6	t	Консультаційні послуги в галузі геології
71351300-6	Micropalaeontological analysis services	5	t	Послуги з мікропалеонтологічного аналізу
71351400-7	Petrophysical interpretation services	5	t	Послуги з інтерпретації петрофізичних даних
71351500-8	Ground investigation services	5	t	Послуги з дослідження ґрунтів
71351600-9	Weather-forecasting services	5	f	Послуги з прогнозування погоди
71351610-2	Meteorology services	6	f	Метеорологічні послуги
71351611-9	Climatology services	7	t	Кліматологічні послуги
71351612-6	Hydrometeorology services	7	t	Гідрометеорологічні послуги
71351700-0	Scientific prospecting services	5	f	Послуги з наукової розвідки
71351710-3	Geophysical prospecting services	6	t	Послуги з геофізичної розвідки
71351720-6	Geophysical surveys of archaeological sites	6	t	Геофізичне дослідження місць археологічних розкопок
71351730-9	Geological prospecting services	6	t	Послуги з геологічної розвідки
71351800-1	Topographical and water divining services	5	f	Топографічні послуги та послуги з розвідування джерел водних запасів
71351810-4	Topographical services	6	f	Топографічні послуги
71351811-1	Topographical surveys of archaeological sites	7	t	Топографічні дослідження місць археологічних розкопок
71351820-7	Water divining services	6	t	Послуги з розвідування джерел водних запасів
71351900-2	Geology, oceanography and hydrology services	5	f	Послуги у сферах геології, океанографії та гідрології
71351910-5	Geology services	6	f	Геологічні послуги
71351911-2	Photogeology services	7	t	Фотогеологічні послуги
71351912-9	Stratigraphic geology services	7	t	Стратиграфічні послуги
71351913-6	Geological exploration services	7	t	Послуги у сфері геологічних досліджень
71351914-3	Archaeological services	7	t	Археологічні послуги
71351920-2	Oceanography and hydrology services	6	f	Океанографічні та гідрологічні послуги
71351921-2	Estuarine oceanography services	7	t	Послуги у сфері океанографії естуаріїв
71351922-2	Physical oceanography services	7	t	Послуги у сфері фізичної океанографії
71351923-2	Bathymetric surveys services	7	t	Послуги у сфері батиметричних досліджень
71351924-2	Underwater exploration services	7	t	Послуги у сфері підводної розвідки
71352000-0	Subsurface surveying services	4	f	Послуги у сфері розвідки надр
71352100-1	Seismic services	5	f	Послуги, пов’язані із сейсмічними даними
71352110-4	Seismographic surveying services	6	t	Послуги у сфері сейсмографічних досліджень
71352120-7	Seismic data acquisition services	6	t	Послуги з реєстрації сейсмічних даних
71352130-0	Seismic data collection services	6	t	Послуги зі збору сейсмічних даних
71352140-3	Seismic processing services	6	t	Послуги з обробки сейсмічних даних
71352300-3	Magnetometric surveying services	5	t	Послуги з магнітометричної розвідки
71353000-7	Surface surveying services	4	f	Послуги з дослідження земної поверхні
71353100-8	Hydrographic surveying services	5	t	Послуги з гідрографічної розвідки
71353200-9	Dimensional surveying services	5	t	Послуги з просторової розвідки
71354000-4	Map-making services	4	f	Картографічні послуги
71354100-5	Digital mapping services	5	t	Послуги з цифрового картографування
71354200-6	Aerial mapping services	5	t	Послуги з топографічної аерофотозйомки
71354300-7	Cadastral surveying services	5	t	Послуги з кадастрової зйомки
71354400-8	Hydrographic services	5	t	Гідрографічні послуги
71354500-9	Marine survey services	5	t	Послуги з гідрографічної зйомки
71355000-1	Surveying services	4	f	Геодезичні послуги
71355100-2	Photogrammetry services	5	t	Фотограмметричні послуги
71355200-3	Ordnance surveying	5	t	Послуги з топографічної зйомки
71356000-8	Technical services	4	f	Технічні послуги
71356100-9	Technical control services	5	t	Послуги з технічного контролю
71356200-0	Technical assistance services	5	t	Послуги з технічної допомоги
71356300-1	Technical support services	5	t	Послуги з технічної підтримки
71356400-2	Technical planning services	5	t	Послуги з технічного планування
71400000-2	Urban planning and landscape architectural services	2	f	Послуги у сфері містобудування та ландшафтної архітектури
71410000-5	Urban planning services	3	t	Послуги у сфері містобудування
71420000-8	Landscape architectural services	3	f	Послуги у сфері ландшафтної архітектури
71421000-5	Landscape gardening services	4	t	Послуги у сфері садово-паркової архітектури
71500000-3	Construction-related services	2	f	Послуги, пов’язані з будівництвом
71510000-6	Site-investigation services	3	t	Послуги у сфері інженерно-геологічних вишукувань
71520000-9	Construction supervision services	3	f	Послуги з нагляду за виконанням будівельних робіт
71521000-6	Construction-site supervision services	4	t	Послуги з нагляду за будівельними майданчиками
71530000-2	Construction consultancy services	3	t	Консультаційні послуги в галузі будівництва
71540000-5	Construction management services	3	f	Послуги з керування будівництвом
71541000-2	Construction project management services	4	t	Послуги з управління будівельними проектами
71550000-8	Blacksmith services	3	t	Ковальські послуги
71600000-4	Technical testing, analysis and consultancy services	2	f	Послуги з технічних випробувань, аналізу та консультування
71610000-7	Composition and purity testing and analysis services	3	t	Послуги з випробувань та аналізу складу і чистоти
71620000-0	Analysis services	3	f	Аналітичні послуги
71621000-7	Technical analysis or consultancy services	4	t	Послуги з технічного аналізу чи консультаційні послуги
71630000-3	Technical inspection and testing services	3	f	Послуги з технічного огляду та випробовувань
71631000-0	Technical inspection services	4	f	Послуги з технічного огляду
71631100-1	Machinery-inspection services	5	t	Послуги з технічного огляду обладнання
71631200-2	Technical automobile inspection services	5	t	Послуги з технічного огляду автомобілів
71631300-3	Technical building-inspection services	5	t	Послуги з технічного огляду будівель
71631400-4	Technical inspection services of engineering structures	5	f	Послуги з технічного огляду інженерних споруд
71631420-0	Maritime safety inspection services	6	t	Послуги з перевірки безпеки на морі
71631430-3	Leak-testing services	6	t	Послуги з випробувань на герметичність
71631440-6	Flow-monitoring services	6	t	Послуги з контролю потоку/витрат
71631450-9	Bridge-inspection services	6	t	Послуги з технічного огляду мостів
71631460-2	Dam-inspection services	6	t	Послуги з технічного огляду гребель
71631470-5	Railway-track inspection services	6	t	Послуги з технічного огляду залізничних колій
71631480-8	Road-inspection services	6	t	Послуги технічного огляду доріг
71631490-1	Runway-inspection services	6	t	Послуги технічного огляду злітно-посадкових смуг
71632000-7	Technical testing services	4	f	Послуги з технічних випробувань
71632100-8	Valve-testing services	5	t	Послуги з випробовування вентилів / клапанів
71632200-9	Non-destructive testing services	5	t	Послуги з неруйнівних випробувань
71700000-5	Monitoring and control services	2	f	Послуги з моніторингу та контролю
71730000-4	Industrial inspection services	3	f	Послуги промислового контролю
71731000-1	Industrial quality control services	4	t	Послуги з контролю якості на виробництві
71800000-6	Consulting services for water-supply and waste consultancy	2	t	Консультаційні послуги у сферах водопостачання та відходів
71900000-7	Laboratory services	2	t	Лабораторні послуги
72000000-5	IT services: consulting, software development, Internet and support	1	f	Послуги у сфері інформаційних технологій: консультування, розробка програмного забезпечення, послуги мережі Інтернет і послуги з підтримки
72100000-6	Hardware consultancy services	2	f	Консультаційні послуги з питань апаратного забезпечення
72110000-9	Hardware selection consultancy services	3	t	Консультаційні послуги з питань підбору апаратного забезпечення
72120000-2	Hardware disaster-recovery consultancy services	3	t	Консультаційні послуги з питань аварійного відновлення апаратного забезпечення
72130000-5	Computer-site planning consultancy services	3	t	Консультаційні послуги з питань планування розташування комп’ютерного обладнання
72140000-8	Computer hardware acceptance testing consultancy services	3	t	Консультаційні послуги з питань приймальних випробувань комп’ютерного апаратного забезпечення
72150000-1	Computer audit consultancy and hardware consultancy services	3	t	Консультаційні послуги з питань комп’ютерного аудиту та комп’ютерного апаратного забезпечення
72200000-7	Software programming and consultancy services	2	f	Послуги з програмування та консультаційні послуги з питань програмного забезпечення
72210000-0	Programming services of packaged software products	3	f	Послуги з розробки пакетів програмного забезпечення
72211000-7	Programming services of systems and user software	4	t	Послуги з розробки системного та користувацького програмного забезпечення
72212000-4	Programming services of application software	4	f	Послуги з розробки прикладного програмного забезпечення
72212100-0	Industry specific software development services	5	f	Послуги з розробки галузевого програмного забезпечення
72212110-3	Point of sale (POS) software development services	6	t	Послуги з розробки програмного забезпечення для автоматизованих точок продажу
72212120-6	Flight control software development services	6	f	Послуги з розробки програмного забезпечення для автоматизованих систем управління польотами
72212121-3	Air traffic control software development services	7	t	Послуги з розробки програмного забезпечення для систем керування повітряним рухом
72212130-9	Aviation ground support and test software development services	6	f	Послуги з розробки програмного забезпечення для наземних засобів забезпечення польотів та випробувань авіаційної техніки
72212131-6	Aviation ground support software development services	7	t	Послуги з розробки програмного забезпечення для наземних засобів забезпечення польотів
72212132-3	Aviation test software development services	7	t	Послуги з розробки програмного забезпечення для випробувань авіаційної техніки
72212140-2	Railway traffic control software development services	6	t	Послуги з розробки програмного забезпечення для систем контролю залізничного руху
72212150-5	Industrial control software development services	6	t	Послуги з розробки програмного забезпечення для керування виробничими процесами
72212160-8	Library software development services	6	t	Послуги з розробки програмного забезпечення для бібліотек
72212170-1	Compliance software development services	6	t	Послуги з розробки програмного забезпечення для контролю відповідності
72212180-4	Medical software development services	6	t	Послуги з розробки медичного програмного забезпечення
72212190-7	Educational software development services	6	t	Послуги з розробки освітнього програмного забезпечення
72212200-1	Networking, Internet and intranet software development services	5	f	Послуги з розробки мережевого програмного забезпечення, а також програмного забезпечення для мереж Інтернет та Інтранет
72212210-4	Networking software development services	6	f	Послуги з розробки мережевого програмного забезпечення
72212211-1	Platform interconnectivity software development services	7	t	Послуги з розробки програмного забезпечення для забезпечення міжплатформної взаємодії
72212212-8	Optical jukebox server software development services	7	t	Послуги з розробки програмного забезпечення для серверів для роботи з оптичними накопичувачами
72212213-5	Operating system enhancement software development services	7	t	Послуги з розробки програмного забезпечення для оптимізації операційних систем
72212214-2	Network operating system software development services	7	t	Послуги з розробки програмного забезпечення для мережевих операційних систем
72212215-9	Networking developers software development services	7	t	Послуги з розробки програмного забезпечення для розробників мереж
72212216-6	Network connectivity terminal emulation software development services	7	t	Послуги з розробки емуляторів мережевих терміналів
72212217-3	Transaction-processing software development services	7	t	Послуги з розробки програмного забезпечення для обробки транзакцій
72212218-0	License management software development services	7	t	Послуги з розробки програмного забезпечення для управління ліцензіями
72212219-7	Miscellaneous networking software development services	7	t	Послуги з розробки програмного забезпечення для різних видів мереж
72212220-7	Internet and intranet software development services	6	f	Послуги з розробки програмного забезпечення для мереж Інтернет та Інтранет
72212221-4	Internet browsing software development services	7	t	Послуги з розробки програмного забезпечення для роботи з Інтернетом
72212222-1	Web server software development services	7	t	Послуги з розробки програмного забезпечення для веб-серверів
72212223-8	Electronic mail software development services	7	t	Послуги з розробки програмного забезпечення для роботи з електронною поштою
72212224-5	Web page editing software development services	7	t	Послуги з розробки програмного забезпечення для редагування веб-сторінок
72212300-2	Document creation, drawing, imaging, scheduling and productivity software development services	5	f	Послуги з розробки програмного забезпечення для роботи з документами, графікою, зображеннями, планування часу та офісного програмного забезпечення
72212310-5	Document creation software development services	6	f	Послуги з розробки програмного забезпечення для створення документів
72260000-5	Software-related services	3	f	Послуги, пов’язані з програмним забезпеченням
72212311-2	Document management software development services	7	t	Послуги з розробки програмного забезпечення для систем управління документообігом
72212312-9	Electronic publishing software development services	7	t	Послуги з розробки програмного забезпечення для електронних видавництв
72212313-6	Optical-character-recognition (OCR) software development services	7	t	Послуги з розробки програмного забезпечення для оптичного розпізнавання символів
72212314-3	Voice recognition software development services	7	t	Послуги з розробки програмного забезпечення для розпізнавання голосу
72212315-0	Desktop-publishing software development services	7	t	Послуги з розробки програмного забезпечення для комп’ютерної верстки
72212316-7	Presentation software development services	7	t	Послуги з розробки програмного забезпечення для роботи з презентаціями
72212317-4	Word-processing software development services	7	t	Послуги з розробки програмного забезпечення для редагування текстів
72212318-1	Scanner software development services	7	t	Послуги з розробки програмного забезпечення для сканерів
72212320-8	Drawing and imaging software development services	6	f	Послуги з розробки програмного забезпечення для роботи з графікою та зображеннями
72212321-5	Computer-aided design (CAD) software development services	7	t	Послуги з розробки програмного забезпечення для автоматизованих систем проектування
72212322-2	Graphics software development services	7	t	Послуги з розробки графічного програмного забезпечення
72212323-9	Computer-aided manufacturing (CAM) software development services	7	t	Послуги з розробки програмного забезпечення для автоматизації виробництва
72212324-6	Charting software development services	7	t	Послуги з розробки програмного забезпечення для роботи з діаграмами
72212325-3	Form making software development services	7	t	Послуги з розробки програмного забезпечення для створення форм і бланків
72212326-0	Mapping software development services	7	t	Послуги з розробки картографічного програмного забезпечення
72212327-7	Drawing and painting software development services	7	t	Послуги з розробки програмного забезпечення для креслення та малювання
72212328-4	Image-processing software development services	7	t	Послуги з розробки програмного забезпечення для обробки зображень
72212330-1	Scheduling and productivity software development services	6	f	Послуги з розробки програмного забезпечення для планування часу та офісного програмного забезпечення
72212331-8	Project management software development services	7	t	Послуги з розробки програмного забезпечення для управління проектами
72212332-5	Scheduling software development services	7	t	Послуги з розробки програмного забезпечення для планування часу
72212333-2	Contact management software development services	7	t	Послуги з розробки програмного забезпечення для управління договірною діяльністю
72212400-3	Business transaction and personal business software development services	5	f	Послуги з розробки програмного забезпечення для управління діловими операціями та електронних особистих органайзерів
72212410-6	Investment management and tax preparation software development services	6	f	Послуги з розробки програмного забезпечення для управління інвестиціями та підготовки податкової звітності
72212411-3	Investment management software development services	7	t	Послуги з розробки програмного забезпечення для управління інвестиціями
72212412-0	Tax preparation software development services	7	t	Послуги з розробки програмного забезпечення для підготовки податкової звітності
72212420-9	Facilities management software development services and software development services suite	6	f	Послуги з розробки програмного забезпечення для адміністративно-господарського управління та комплекси послуг з розробки програмного забезпечення
72212421-6	Facilities management software development services	7	t	Послуги з розробки програмного забезпечення для адміністративно-господарського управління
72212422-3	Software development services suites	7	t	Комплекси послуг з розробки програмного забезпечення
72212430-2	Inventory management software development services	6	t	Послуги з розробки програмного забезпечення для управління матеріально-технічними ресурсами
72212440-5	Financial analysis and accounting software development services	6	f	Послуги з розробки програмного забезпечення для фінансового аналізу та бухгалтерського обліку
72212441-2	Financial analysis software development services	7	t	Послуги з розробки програмного забезпечення для фінансового аналізу
72212442-9	Financial systems software development services	7	t	Послуги з розробки програмного забезпечення для фінансових систем
72212443-6	Accounting software development services	7	t	Послуги з розробки програмного забезпечення для бухгалтерського обліку
72212445-0	Customer Relation Management software development services	7	t	Послуги з розробки програмного забезпечення для управління відносинами з клієнтами
72212450-8	Time accounting or human resources software development services	6	f	Послуги з розробки програмного забезпечення для обліку часу чи людських ресурсів
72212451-5	Enterprise resource planning software development services	7	t	Послуги з розробки програмного забезпечення для планування ресурсів підприємства
72212460-1	Analytical, scientific, mathematical or forecasting software development services	6	f	Послуги з розробки аналітичного, наукового, математичного чи прогнозувального програмного забезпечення
72212461-8	Analytical or scientific software development services	7	t	Послуги з розробки аналітичного чи наукового програмного забезпечення
72212462-5	Mathematical or forecasting software development services	7	t	Послуги з розробки математичного чи прогнозувального програмного забезпечення
72212463-2	Statistical software development services	7	t	Послуги з розробки статистичного програмного забезпечення
72212470-4	Auction software development services	6	t	Послуги з розробки програмного забезпечення для проведення аукціонів
72212480-7	Sales, marketing and business intelligence software development services	6	f	Послуги з розробки програмного забезпечення для продажу та реалізації продукції і бізнес-аналітики
72212481-4	Sales or marketing software development services	7	t	Послуги з розробки програмного забезпечення для продажу чи реалізації продукції
72212482-1	Business intelligence software development services	7	t	Послуги з розробки програмного забезпечення для бізнес-аналітики
72212490-0	Procurement software development services	6	t	Послуги з розробки закупівельного програмного забезпечення
72212500-4	Communication and multimedia software development services	5	f	Послуги з розробки комунікаційного та мультимедійного програмного забезпечення
72212510-7	Communication software development services	6	f	Послуги з розробки комунікаційного програмного забезпечення
72212511-4	Desktop communications software development services	7	t	Послуги з розробки програмного забезпечення для відображення повідомлень на робочому столі
72212512-1	Interactive voice response software development services	7	t	Послуги з розробки програмного забезпечення для систем інтерактивної голосової відповіді
72212513-8	Modem software development services	7	t	Послуги з розробки програмного забезпечення для модемів
72212514-5	Remote access software development services	7	t	Послуги з розробки програмного забезпечення для роботи з віддаленим доступом
72212515-2	Video conferencing software development services	7	t	Послуги з розробки програмного забезпечення для відеоконференцій
72212516-9	Exchange software development services	7	t	Послуги з розробки програмного забезпечення для обміну інформацією
72212517-6	IT software development services	7	t	Послуги з розробки програмного забезпечення для роботи з інформаційними технологіями
72212518-3	Emulation software development services	7	t	Послуги з розробки емуляторів
72212519-0	Memory-management software development services	7	t	Послуги з розробки програмного забезпечення для керування пам’яттю
72212520-0	Multimedia software development services	6	f	Послуги з розробки мультимедійного програмного забезпечення
72212521-7	Music or sound editing software development services	7	t	Послуги з розробки програмного забезпечення для редагування музики чи звуку
72212522-4	Virtual keyboard software development services	7	t	Послуги з розробки програмного забезпечення для віртуальних клавіатур
72212600-5	Database and operating software development services	5	f	Послуги з розробки програмного забезпечення для баз даних та операційних систем
72212610-8	Database software development services	6	t	Послуги з розробки програмного забезпечення для баз даних
72212620-1	Mainframe operating system software development services	6	t	Послуги з розробки програмного забезпечення для операційних систем для мейнфреймів
72212630-4	Minicomputer operating system software development services	6	t	Послуги з розробки програмного забезпечення для операційних систем для мінікомп’ютерів
72212640-7	Microcomputer operating system software development services	6	t	Послуги з розробки програмного забезпечення для операційних систем для мікрокомп’ютерів
72212650-0	Personal computer (PC) operating system software development services	6	t	Послуги з розробки програмного забезпечення для операційних систем для персональних комп’ютерів
85121292-6	Urologist services	7	t	Послуги урологів
72212660-3	Clustering software development services	6	t	Послуги з розробки програмного забезпечення для кластеризації
72212670-6	Real time operating system software development services	6	t	Послуги з розробки програмного забезпечення для операційних систем реального часу
72212700-6	Software development services utilities	5	f	Набори утиліт для розробки програмного забезпечення
72212710-9	Backup or recovery software development services	6	t	Послуги з розробки програмного забезпечення для резервного копіювання чи відновлення даних
72212720-2	Bar coding software development services	6	t	Послуги з розробки програмного забезпечення для сканування штрих-кодів
72212730-5	Security software development services	6	f	Послуги з розробки програмного забезпечення для забезпечення захисту
72212731-2	File security software development services	7	t	Послуги з розробки програмного забезпечення для захисту файлів
72212732-9	Data security software development services	7	t	Послуги з розробки програмного забезпечення для захисту даних
72212740-8	Foreign language translation software development services	6	t	Послуги з розробки перекладацького програмного забезпечення
72212750-1	Storage media loading software development services	6	t	Послуги з розробки програмного забезпечення для запису на носії інформації
72212760-4	Virus protection software development services	6	f	Послуги з розробки програмного забезпечення для захисту від вірусів
72212761-1	Anti-virus software development services	7	t	Послуги з розробки антивірусного програмного забезпечення
72212770-7	General, compression and print utility software development services	6	f	Послуги з розробки службового програмного забезпечення загального призначення, для стиснення даних та друку
72212771-4	General utility software development services	7	t	Послуги з розробки службового програмного забезпечення загального призначення
72212772-1	Print utility software development services	7	t	Послуги з розробки службового програмного забезпечення для друку
72212780-0	System, storage and content management software development services	6	f	Послуги з розробки програмного забезпечення для управління системами, запам’ятовувальними пристроями та контентом
72212781-7	System management software development services	7	t	Послуги з розробки програмного забезпечення для управління системами
72212782-4	Storage management software development services	7	t	Послуги з розробки програмного забезпечення для управління запам’ятовувальними пристроями
72212783-1	Content management software development services	7	t	Послуги з розробки програмного забезпечення для управління контентом
72212790-3	Version checker software development services	6	t	Послуги з розробки програмного забезпечення для перевірки версій
72212900-8	Miscellaneous software development services and computer systems	5	f	Послуги з розробки програмного забезпечення різного призначення та комп’ютерних систем
72212910-1	Computer game software development services, family titles and screen savers	6	f	Послуги з розробки програмного забезпечення для створення комп’ютерних ігор, побудови генеалогічного дерева та створення екранних заставок
72212911-8	Computer game software development services	7	t	Послуги з розробки програмного забезпечення для створення комп’ютерних ігор
72212920-4	Office automation software development services	6	t	Послуги з розробки програмного забезпечення для автоматизації офісу
72212930-7	Training and entertainment software development services	6	f	Послуги з розробки навчального та розважального програмного забезпечення
72212931-4	Training software development services	7	t	Послуги з розробки навчального програмного забезпечення
72212932-1	Entertainment software development services	7	t	Послуги з розробки розважального програмного забезпечення
72212940-0	Pattern design and calendar software development services	6	f	Послуги з розробки програмного забезпечення для створення шаблонів і календарів
72212941-7	Pattern design software development services	7	t	Послуги з розробки програмного забезпечення для створення шаблонів
72212942-4	Calendar software development services	7	t	Послуги з розробки програмного забезпечення для створення календарів
72212960-6	Drivers and system software development services	6	t	Послуги з розробки драйверів і системного програмного забезпечення
72212970-9	Print shop software development services	6	f	Послуги з розробки типографського програмного забезпечення
72212971-6	Address book making software development services	7	t	Послуги з розробки програмного забезпечення для створення адресних книг
75100000-7	Administration services	2	f	Адміністративні послуги
72212972-3	Label making software development services	7	t	Послуги з розробки програмного забезпечення для створення дизайну етикеток
72212980-2	Programming languages and tools development services	6	f	Послуги з розробки мов та засобів програмування
72212981-9	Compiling software development services	7	t	Послуги з розробки компілювального програмного забезпечення
72212982-6	Configuration management software development services	7	t	Послуги з розробки програмного забезпечення для керування конфігураціями
72212983-3	Development software development services	7	t	Послуги з розробки програмного забезпечення для розробок
72212984-0	Program testing software development services	7	t	Послуги з розробки програмного забезпечення для тестування програм
72212985-7	Debugging software development services	7	t	Послуги з розробки зневаджувального програмного забезпечення
72212990-5	Spreadsheets and enhancement software development services	6	f	Послуги з розробки програмного забезпечення для роботи з таблицями та вдосконалення програмного продукту
72212991-2	Spreadsheet software development services	7	t	Послуги з розробки програмного забезпечення для роботи з таблицями
72220000-3	Systems and technical consultancy services	3	f	Консультаційні послуги з питань систем та з технічних питань
72221000-0	Business analysis consultancy services	4	t	Консультаційні послуги з питань аналізу господарської діяльності
72222000-7	Information systems or technology strategic review and planning services	4	f	Послуги зі стратегічного аналізу та планування інформаційних систем або технологій
72222100-8	Information systems or technology strategic review services	5	t	Послуги зі стратегічного аналізу інформаційних систем або технологій
72222200-9	Information systems or technology planning services	5	t	Послуги з планування інформаційних систем або технологій
72222300-0	Information technology services	5	t	Послуги у сфері інформаційних технологій
72223000-4	Information technology requirements review services	4	t	Послуги з аналізу вимог до інформаційних технологій
72224000-1	Project management consultancy services	4	f	Консультаційні послуги з питань управління проектами
72224100-2	System implementation planning services	5	t	Послуги з планування впроваджування систем
72224200-3	System quality assurance planning services	5	t	Послуги з планування забезпечення якості систем
72225000-8	System quality assurance assessment and review services	4	t	Послуги з оцінювання та аналізу ефективності забезпечення якості систем
72226000-5	System software acceptance testing consultancy services	4	t	Консультаційні послуги з питань приймальних випробовувань системного програмного забезпечення
72227000-2	Software integration consultancy services	4	t	Консультаційні послуги з питань інтеграції програмного забезпечення
72228000-9	Hardware integration consultancy services	4	t	Консультаційні послуги з питань інтеграції апаратного забезпечення
72230000-6	Custom software development services	3	f	Послуги з розробки програмного забезпечення на замовлення
72231000-3	Development of software for military applications	4	t	Розробка програмного забезпечення військового призначення
72232000-0	Development of transaction processing and custom software	4	t	Розробка програмного забезпечення для обробки транзакцій і програмного забезпечення на замовлення
72240000-9	Systems analysis and programming services	3	f	Послуги з аналізу та програмування систем
72241000-6	Critical design target specification services	4	t	Послуги з визначення критичних значень для цільових показників
72242000-3	Design-modelling services	4	t	Послуги з проектного моделювання
72243000-0	Programming services	4	t	Послуги з програмування
72244000-7	Prototyping services	4	t	Послуги зі створення дослідних зразків
72245000-4	Contract systems analysis and programming services	4	t	Послуги з аналізу та програмування контрактних систем
72246000-1	Systems consultancy services	4	t	Консультаційні послуги з питань систем
72250000-2	System and support services	3	f	Послуги, пов’язані із системами та підтримкою
72251000-9	Disaster recovery services	4	t	Послуги з аварійного відновлення
72252000-6	Computer archiving services	4	t	Послуги зі створення комп’ютерних архівів
72253000-3	Helpdesk and support services	4	f	Послуги з підтримки користувачів та з технічної підтримки
72253100-4	Helpdesk services	5	t	Послуги з підтримки користувачів
72253200-5	Systems support services	5	t	Послуги з підтримки систем
72254000-0	Software testing	4	f	Тестування програмного забезпечення
72254100-1	Systems testing services	5	t	Послуги з тестування систем
72261000-2	Software support services	4	t	Послуги з обслуговування програмного забезпечення
72262000-9	Software development services	4	t	Послуги з розробки програмного забезпечення
72263000-6	Software implementation services	4	t	Послуги зі впровадження програмного забезпечення
72264000-3	Software reproduction services	4	t	Послуги з відтворення програмного забезпечення
72265000-0	Software configuration services	4	t	Послуги з конфігурування програмного забезпечення
72266000-7	Software consultancy services	4	t	Консультаційні послуги з питань програмного забезпечення
72267000-4	Software maintenance and repair services	4	f	Послуги з профілактичного обслуговування та відновлення програмного забезпечення
72267100-0	Maintenance of information technology software	5	t	Обслуговування програмного забезпечення
72267200-1	Repair of information technology software	5	t	Відновлення програмного забезпечення
72268000-1	Software supply services	4	t	Послуги з постачання програмного забезпечення
72300000-8	Data services	2	f	Послуги у сфері управління даними
72310000-1	Data-processing services	3	f	Послуги з обробки даних
72311000-8	Computer tabulation services	4	f	Послуги, пов’язані з роботою з електронними таблицями
72311100-9	Data conversion services	5	t	Послуги з перетворення даних
72311200-0	Batch processing services	5	t	Послуги з пакетної обробки даних
72311300-1	Computer time-sharing services	5	t	Комп’ютерні послуги в режимі розподілу часу
72312000-5	Data entry services	4	f	Послуги зі введення даних
72312100-6	Data preparation services	5	t	Послуги з підготовки даних
72312200-7	Optical character recognition services	5	t	Послуги з оптичного розпізнавання символів
72313000-2	Data capture services	4	t	Послуги з накопичення даних
72314000-9	Data collection and collation services	4	t	Послуги зі збирання та зіставлення даних
72315000-6	Data network management and support services	4	f	Послуги з керування мережами даних і з підтримки мереж даних
72315100-7	Data network support services	5	t	Послуги з підтримки мереж даних
72315200-8	Data network management services	5	t	Послуги з керування мережами даних
72316000-3	Data analysis services	4	t	Послуги з аналізу даних
72317000-0	Data storage services	4	t	Послуги зі зберігання даних
72318000-7	Data transmission services	4	t	Послуги з передачі даних
72319000-4	Data supply services	4	t	Послуги з постачання даних
72320000-4	Database services	3	f	Послуги, пов’язані з базами даних
72321000-1	Added-value database services	4	t	Додаткові послуги, пов’язані з базами даних
72322000-8	Data management services	4	t	Послуги з управління даними
72330000-2	Content or data standardization and classification services	3	t	Послуги зі стандартизації та класифікації контенту та даних
72400000-4	Internet services	2	f	Інтернет-послуги
72410000-7	Provider services	3	f	Послуги провайдерів
72411000-4	Internet service providers ISP	4	t	Постачальники Інтернет-послуг
72412000-1	Electronic mail service provider	4	t	Постачальники послуг електронної пошти
72413000-8	World wide web (www) site design services	4	t	Послуги з розробки веб-сайтів
72414000-5	Web search engine providers	4	t	Провайдери пошукових систем
72415000-2	World wide web (www) site operation host services	4	t	Постачальники послуг з веб-хостингу
72416000-9	Application service providers	4	t	Постачальники прикладних послуг
72417000-6	Internet domain names	4	t	Імена доменів
72420000-0	Internet development services	3	f	Послуги у сфері розвитку Інтернету
72421000-7	Internet or intranet client application development services	4	t	Послуги з розробки клієнтських застосунків для роботи в мережах Інтернет та Інтранет
72422000-4	Internet or intranet server application development services	4	t	Послуги з розробки застосунків для роботи з серверами через мережі Інтернет та Інтранет
72500000-0	Computer-related services	2	f	Послуги, пов’язані з комп’ютерними технологіями
72510000-3	Computer-related management services	3	f	Управлінські послуги, пов’язані з комп’ютерними технологіями
72511000-0	Network management software services	4	t	Послуги у сфері програмного забезпечення для керування мережами
72512000-7	Document management services	4	t	Послуги з управління документообігом
72513000-4	Office automation services	4	t	Послуги з автоматизації офісів
72514000-1	Computer facilities management services	4	f	Послуги з керування комп’ютерним обладнанням
72514100-2	Facilities management services involving computer operation	5	t	Послуги з керування обладнанням з використанням комп’ютерних технологій
72514200-3	Facilities management services for computer systems development	5	t	Послуги з керування засобами для розробки комп’ютерних систем
72514300-4	Facilities management services for computer systems maintenance	5	t	Послуги з керування засобами технічного обслуговування комп’ютерних систем
72540000-2	Computer upgrade services	3	f	Послуги з модернізації комп’ютерів
72541000-9	Computer expansion services	4	f	Послуги з розширення можливостей комп’ютерів
72541100-0	Memory expansion services	5	t	Послуги з розширення пам’яті
72590000-7	Computer-related professional services	3	f	Професійні послуги у комп’ютерній сфері
72591000-4	Development of service level agreements	4	t	Розробка договорів про рівень обслуговування
72600000-6	Computer support and consultancy services	2	f	Послуги з комп’ютерної підтримки та консультаційні послуги з питань роботи з комп’ютерами
72610000-9	Computer support services	3	f	Послуги з комп’ютерної підтримки
72611000-6	Technical computer support services	4	t	Послуги технічного обслуговування комп’ютерів
72700000-7	Computer network services	2	f	Послуги у сфері комп’ютерних мереж
72710000-0	Local area network services	3	t	Послуги у сфері локальних мереж
72720000-3	Wide area network services	3	t	Послуги у сфері глобальних мереж
72800000-8	Computer audit and testing services	2	f	Послуги з комп’ютерного аудиту та тестування комп’ютерного обладнання
72810000-1	Computer audit services	3	t	Послуги з комп’ютерного аудиту
72820000-4	Computer testing services	3	t	Послуги з тестування комп’ютерного обладнання
72900000-9	Computer back-up and catalogue conversion services	2	f	Послуги з резервного копіювання та перетворення каталогів
72910000-2	Computer back-up services	3	t	Послуги з резервного копіювання
72920000-5	Computer catalogue conversion services	3	t	Послуги з перетворення каталогів
73000000-2	Research and development services and related consultancy services	1	f	Послуги у сфері НДДКР та пов’язані консультаційні послуги
73100000-3	Research and experimental development services	2	f	Послуги у сфері наукових досліджень та експериментальних розробок
73110000-6	Research services	3	f	Дослідницькі послуги
73111000-3	Research laboratory services	4	t	Послуги дослідних лабораторій
73112000-0	Marine research services	4	t	Послуги у сфері морських досліджень
73120000-9	Experimental development services	3	t	Послуги у сфері експериментальних розробок
73200000-4	Research and development consultancy services	2	f	Консультаційні послуги у сфері НДДКР
73210000-7	Research consultancy services	3	t	Консультаційні послуги у сфері наукових досліджень
73220000-0	Development consultancy services	3	t	Консультаційні послуги у сфері розробок
73300000-5	Design and execution of research and development	2	t	Проектування та виконання НДДКР
73400000-6	Research and Development services on security and defence materials	2	f	Послуги з проведення НДДКР у сфері безпеки та оборони
73410000-9	Military research and technology	3	t	Військові дослідження та технології
73420000-2	Pre-feasibility study and technological demonstration	3	f	Техніко-економічне обґрунтування та технологічна демонстрація
73421000-9	Development of security equipment	4	t	Розробка охоронного обладнання
73422000-6	Development of firearms and ammunition	4	t	Розробка вогнепальної зброї та боєприпасів
73423000-3	Development of military vehicles	4	t	Розробка транспортних засобів військового призначення
73424000-0	Development of warships	4	t	Розробка військових кораблів
73425000-7	Development of military aircrafts, missiles and spacecrafts	4	t	Розробка військових літаків, ракет і космічних апаратів
73426000-4	Development of military electronic systems	4	t	Розробка електронних військових систем
73430000-5	Test and evaluation	3	f	Випробування та оцінювання
73431000-2	Test and evaluation of security equipment	4	t	Випробування та оцінювання охоронного обладнання
73432000-9	Test and evaluation of firearms and ammunition	4	t	Випробування та оцінювання вогнепальної зброї та боєприпасів
73433000-6	Test and evaluation of military vehicles	4	t	Випробування та оцінювання транспортних засобів військового призначення
73434000-3	Test and evaluation of warships	4	t	Випробування та оцінювання військових кораблів
73435000-0	Test and evaluation of military aircrafts, missiles and spacecrafts	4	t	Випробування та оцінювання військових літаків, ракет і космічних апаратів
73436000-7	Test and evaluation of military electronic systems	4	t	Випробування та оцінювання військових електронних систем
75000000-6	Administration, defence and social security services	1	f	Адміністративні, оборонні послуги та послуги у сфері соціального захисту
75111000-7	Executive and legislative services	4	f	Послуги виконавчих і законодавчих органів влади
75111100-8	Executive services	5	t	Послуги виконавчих органів влади
75111200-9	Legislative services	5	t	Послуги законодавчих органів влади
75112000-4	Administrative services for business operations	4	f	Адміністративні послуги для суб’єктів підприємницької діяльності
75112100-5	Administrative development project services	5	t	Адміністративні послуги, пов’язані із проектами розвитку
75120000-3	Administrative services of agencies	3	f	Адміністративні послуги державних установ
75121000-0	Administrative educational services	4	t	Адміністративні послуги у сфері освіти
75122000-7	Administrative healthcare services	4	t	Адміністративні послуги у сфері охорони здоров’я
75123000-4	Administrative housing services	4	t	Адміністративні послуги у сфері житлового господарства
75124000-1	Administrative recreational, cultural and religious services	4	t	Адміністративні послуги у сфері відпочинку, культури та релігії
75125000-8	Administrative services related to tourism affairs	4	t	Адміністративні послуги у сфері туризму
75130000-6	Supporting services for the government	3	f	Допоміжні послуги для урядових органів державного управління
75131000-3	Government services	4	f	Урядові послуги
75131100-4	General personnel services for the government	5	t	Загальні кадрові послуги для урядових органів державного управління
75200000-8	Provision of services to the community	2	f	Надання громадських послуг
75210000-1	Foreign affairs and other services	3	f	Послуги у сфері закордонних справ та подібні послуги
75211000-8	Foreign-affairs services	4	f	Послуги у сфері закордонних справ
75211100-9	Diplomatic services	5	f	Дипломатичні послуги
75211110-2	Consular services	6	t	Консульські послуги
75211200-0	Foreign economic-aid-related services	5	t	Послуги, пов’язані з економічною допомогою іноземних держав
75211300-1	Foreign military-aid-related services	5	t	Послуги, пов’язані з військовою допомогою іноземних держав
75220000-4	Defence services	3	f	Оборонні послуги
75221000-1	Military defence services	4	t	Послуги у сфері військової оборони
75222000-8	Civil defence services	4	t	Послуги у сфері цивільної оборони
75230000-7	Justice services	3	f	Послуги у сфері юстиції
75231000-4	Judicial services	4	f	Судові послуги
75231100-5	Law-courts-related administrative services	5	t	Адміністративні послуги судів загального права
75231200-6	Services related to the detention or rehabilitation of criminals	5	f	Послуги, пов’язані із затриманням осіб, що скоїли злочин, чи їх реабілітацією
75231210-9	Imprisonment services	6	t	Послуги з ув’язнення
75231220-2	Prisoner-escort services	6	t	Послуги із забезпечення конвою в’язнів
75231230-5	Prison services	6	t	Пенітенціарні послуги
75231240-8	Probation services	6	t	Послуги з пробації
75240000-0	Public security, law and order services	3	f	Послуги із забезпечення громадської безпеки, охорони правопорядку та громадського порядку
75241000-7	Public security services	4	f	Послуги із забезпечення громадської безпеки
75241100-8	Police services	5	t	Поліцейські послуги
75242000-4	Public law and order services	4	f	Послуги з охорони правопорядку та громадського порядку
75242100-5	Public-order services	5	f	Послуги з охорони громадського порядку
75242110-8	Bailiff services	6	t	Послуги судових виконавців
75250000-3	Fire-brigade and rescue services	3	f	Послуги пожежних і рятувальних служб
75251000-0	Fire-brigade services	4	f	Послуги пожежних служб
75251100-1	Firefighting services	5	f	Послуги з пожежогасіння
75251110-4	Fire-prevention services	6	t	Послуги з протипожежного захисту
75251120-7	Forest-firefighting services	6	t	Послуги з гасіння лісових пожеж
75252000-7	Rescue services	4	t	Рятувальні послуги
75300000-9	Compulsory social security services	2	f	Послуги у сфері обов’язкового соціального страхування
75310000-2	Benefit services	3	f	Послуги з виплати соціальної допомоги
75311000-9	Sickness benefits	4	t	Допомога у зв’язку з хворобою
75312000-6	Maternity benefits	4	t	Допомога у зв’язку з вагітністю та пологами
75313000-3	Disability benefits	4	f	Допомога у зв’язку з інвалідністю
75313100-4	Temporary disablement benefits	5	t	Допомога у зв’язку з тимчасовою непрацездатністю
75314000-0	Unemployment compensation benefits	4	t	Допомога у зв’язку з безробіттям
75320000-5	Government employee pension schemes	3	t	Програми пенсійного забезпечення державних службовців
75330000-8	Family allowances	3	t	Допомога малозабезпеченим сім’ям
75340000-1	Child allowances	3	t	Допомога сім’ям із дітьми
76000000-3	Services related to the oil and gas industry	1	f	Послуги, пов’язані з нафтогазовою промисловістю
76100000-4	Professional services for the gas industry	2	f	Професійні послуги у сфері газової промисловості
76110000-7	Services incidental to gas extraction	3	f	Послуги, пов’язані з видобуванням газу
76111000-4	Regasification services	4	t	Послуги з регазифікування
76120000-0	Diving services incidental to gas extraction	3	f	Послуги з виконання водолазних робіт, пов’язаних із видобуванням газу
76121000-7	Subsea well diving services	4	t	Послуги з виконання водолазних робіт на морських свердловинах
76200000-5	Professional services for the oil industry	2	f	Професійні послуги у сфері нафтової промисловості
76210000-8	Services incidental to oil extraction	3	f	Послуги, пов’язані з видобуванням нафти
76211000-5	Liner-hanger services	4	f	Послуги з прокладання підвісок обсадних колон
76211100-6	Lining services	5	f	Послуги з обсадження свердловин
76211110-9	Test pit lining services	6	t	Послуги з обсадження розвідувальних свердловин
76211120-2	Well site pit lining services	6	t	Послуги з обсадження бурових свердловин
76211200-7	Mudlogging services	5	t	Послуги з геолого-технологічних досліджень
76300000-6	Drilling services	2	f	Послуги з виконання бурових робіт
76310000-9	Drilling services incidental to gas extraction	3	t	Послуги з виконання бурових робіт, пов’язаних із видобуванням газу
76320000-2	Offshore drilling services	3	t	Послуги з виконання бурових робіт у відкритому морі
76330000-5	Turbine drilling services	3	f	Послуги з турбінного буріння
76331000-2	Coiled turbine drilling services	4	t	Послуги колтюбінгового турбінного буріння
76340000-8	Core drilling	3	t	Послуги з колонкового буріння
76400000-7	Rig-positioning services	2	f	Послуги з позиціонування бурильного устаткування
76410000-0	Well-casing and tubing services	3	f	Послуги з обсадження свердловин та монтажу обсадних труб
76411000-7	Well-casing services	4	f	Послуги з обсадження свердловин
76411100-8	Well-casing crew services	5	t	Послуги бригад із обсадження свердловин
76411200-9	Well-casing planning services	5	t	Послуги з планування обсадження свердловин
76411300-0	Well-casing milling services	5	t	Послуги з фрезерування при обсадженні свердловин
76411400-1	Well-casing completion services	5	t	Послуги із завершення обсадження свердловин
76420000-3	Well-cementing services	3	f	Послуги з цементування свердловин
76421000-0	Liner cementing services	4	t	Послуги з цементування обсадної колони-хвостовика
76422000-7	Plug cementing services	4	t	Послуги з цементування пробок
76423000-4	Foam cementing services	4	t	Послуги цементування піноцементним розчином
76430000-6	Well-drilling and production services	3	f	Послуги з буріння та експлуатації свердловин
76431000-3	Well-drilling services	4	f	Послуги з буріння свердловин
76431100-4	Well-drilling control services	5	t	Послуги з керування процесом буріння свердловин
76431200-5	Well-drilling pickup services	5	t	Послуги з формування дренажних отворів під час буріння свердловин
76431300-6	Well-drilling laydown services	5	t	Послуги з укладання труб під час буріння свердловин
76431400-7	Rathole well-drilling services	5	t	Послуги риття шурфів під час буріння свердловин
76431500-8	Well-drilling supervision services	5	t	Послуги з технічного нагляду за процесом буріння свердловин
76431600-9	Well-drilling rig monitor services	5	t	Послуги з моніторингу стану бурового устаткування під час буріння свердловин
76440000-9	Well-logging services	3	f	Каротажні послуги
76441000-6	Cased hole logging services	4	t	Послуги з проведення геофізичного дослідження обсаджених свердловин
76442000-3	Open hole logging services	4	t	Послуги з проведення геофізичного дослідження необсаджених свердловин
76443000-0	Other logging services	4	t	Інші каротажні послуги
76450000-2	Well-management services	3	t	Послуги з управління свердловинами
76460000-5	Well-support services	3	t	Послуги, пов’язані з інфраструктурою свердловин
76470000-8	Well-testing services	3	f	Послуги з випробування свердловин
76471000-5	Well fracture testing services	4	t	Послуги з випробувань на утворення тріщин у процесі буріння свердловин
76472000-2	Well site inspection or testing services	4	t	Послуги з огляду чи випробування свердловин
76473000-9	Well equipment testing services	4	t	Послуги з випробування свердловинного обладнання
76480000-1	Tubing services	3	t	Послуги з укладання труб
76490000-4	Well-completion services	3	f	Послуги з освоєння свердловин
76491000-1	Well-plugging services	4	t	Послуги з тампонування свердловин
76492000-8	Well-positioning services	4	t	Послуги з позиціонування свердловин
76500000-8	Onshore and offshore services	2	f	Послуги з проведення наземних робіт і робіт у відкритому морі
76510000-1	Onshore services	3	t	Послуги з проведення наземних робіт
76520000-4	Offshore services	3	f	Послуги з проведення робіт у відкритому морі
76521000-1	Offshore installation services	4	t	Послуги зі встановлення конструкцій у відкритому морі
76522000-8	Offshore supply-vessel services	4	t	Послуги суден-постачальників із обслуговування морських платформ
76530000-7	Downhole services	3	f	Послуги з проведення внутрішньосвердловинних робіт
76531000-4	Downhole logging services	4	t	Послуги з проведення промислових геофізичних досліджень
76532000-1	Downhole pumping services	4	t	Послуги з проведення внутрішньосвердловинних насосних робіт
76533000-8	Downhole recording services	4	t	Послуги з реєстрування свердловинних параметрів
76534000-5	Downhole underreaming services	4	t	Послуги з розбурювання стовбура свердловини
76535000-2	Downhole hole opening services	4	t	Послуги з розширення стовбура свердловини
76536000-9	Downhole vibration control services	4	t	Послуги з контролю вібрації у свердловині
76537000-6	Downhole tool services	4	f	Послуги, пов’язані зі свердловинним інструментарієм
76537100-7	Downhole oilifield tools services	5	t	Послуги, пов’язані зі свердловинним нафтопромисловим інструментарієм
76600000-9	Pipeline-inspection services	2	t	Послуги з інспектування трубопроводів
77000000-0	Agricultural, forestry, horticultural, aquacultural and apicultural services	1	f	Послуги у сфері сільського господарства, лісівництва, рослинництва, водного господарства та бджільництва
77100000-1	Agricultural services	2	f	Послуги у сфері сільського господарства
77110000-4	Services incidental to agricultural production	3	f	Послуги, пов’язані з виробництвом сільськогосподарської продукції
77111000-1	Hire of agricultural machinery with operator	4	t	Оренда сільськогосподарської техніки з оператором
77112000-8	Hire of mowers or agricultural equipment with operator	4	t	Оренда косарок та сільськогосподарського обладнання з оператором
77120000-7	Composting services	3	t	Послуги з компостування
77200000-2	Forestry services	2	f	Послуги у сфері лісівництва
77210000-5	Logging services	3	f	Лісозаготівельні послуги
77211000-2	Services incidental to logging	4	f	Послуги, пов’язані з лісозаготівлею
77211100-3	Timber harvesting services	5	t	Послуги із заготівлі деревини
77211200-4	Transport of logs within the forest	5	t	Транспортування колод територією лісу
77211300-5	Tree-clearing services	5	t	Послуги з видалення дерев
77211400-6	Tree-cutting services	5	t	Послуги з вирубування дерев
77211500-7	Tree-maintenance services	5	t	Послуги з догляду за деревами
77211600-8	Tree seeding	5	t	Вирощування дерев з насіння
77220000-8	Timber-impregnation services	3	t	Послуги з просочування деревини
77230000-1	Services incidental to forestry	3	f	Послуги, пов’язані з лісівництвом
77231000-8	Forestry management services	4	f	Послуги з управління лісовим господарством
77231100-9	Forest resources management services	5	t	Послуги з управління лісовими ресурсами
77231200-0	Forest pest control services	5	t	Послуги з боротьби зі шкідниками лісу
77231300-1	Forest administration services	5	t	Послуги з лісоуправління
77231400-2	Forest inventory services	5	t	Послуги з інвентаризації лісів
77231500-3	Forest monitoring or evaluation services	5	t	Послуги з моніторингу чи оцінювання стану лісів
77231600-4	Afforestation services	5	t	Послуги з лісонасадження
77231700-5	Forestry extension services	5	t	Послуги з розширення лісів
77231800-6	Forest nursery management services	5	t	Послуги з управління лісорозсадниками
77231900-7	Forest sectoral planning services	5	t	Послуги з галузевого планування лісового господарства
77300000-3	Horticultural services	2	f	Послуги у сфері рослинництва
77310000-6	Planting and maintenance services of green areas	3	f	Послуги з озеленення територій та утримання зелених насаджень
77311000-3	Ornamental and pleasure gardens maintenance services	4	t	Послуги з утримання декоративних садів і парків відпочинку
77312000-0	Weed-clearance services	4	f	Послуги з видалення бур’янів
77312100-1	Weed-killing services	5	t	Послуги з винищування бур’янів
77313000-7	Parks maintenance services	4	t	Послуги з утримання парків
77314000-4	Grounds maintenance services	4	f	Послуги з утримання територій
77314100-5	Grassing services	5	t	Послуги із засівання газонів
77315000-1	Seeding services	4	t	Засівальні послуги
77320000-9	Sports fields maintenance services	3	t	Послуги з утримання спортивних полів
77330000-2	Floral-display services	3	t	Послуги з проведення виставок квітів
77340000-5	Tree pruning and hedge trimming	3	f	Підрізання дерев і живих огорож
77341000-2	Tree pruning	4	t	Підрізання дерев
77342000-9	Hedge trimming	4	t	Підрізання живих огорож
77400000-4	Zoological services	2	t	Зоологічні послуги
77500000-5	Animal husbandry services	2	f	Послуги тваринних господарств
77510000-8	Game-propagation services	3	t	Послуги з розведення дичини
77600000-6	Hunting services	2	f	Мисливські послуги
77610000-9	Trapping services	3	t	Послуги з відлову тварин
77700000-7	Services incidental to fishing	2	t	Послуги, пов’язані з рибальством
77800000-8	Aquaculture services	2	f	Послуги у сфері водного господарства
77810000-1	Mariculture services	3	t	Послуги у сфері морського господарства
77820000-4	Ostreiculture services	3	t	Послуги з розведення устриць
77830000-7	Shellfish culture services	3	t	Послуги з розведення молюсків
77840000-0	Shrimp farming services	3	t	Послуги розведення креветок
77850000-3	Fish farming services	3	t	Послуги риборозведення
77900000-9	Apiculture services	2	t	Послуги у сфері бджільництва
79000000-4	Business services: law, marketing, consulting, recruitment, printing and security	1	f	Ділові послуги: юридичні, маркетингові, консультаційні, кадрові, поліграфічні та охоронні
79100000-5	Legal services	2	f	Юридичні послуги
79110000-8	Legal advisory and representation services	3	f	Послуги з юридичного консультування та юридичного представництва
79111000-5	Legal advisory services	4	t	Послуги з юридичного консультування
79112000-2	Legal representation services	4	f	Послуги з юридичного представництва
79112100-3	Stakeholders representation services	5	t	Послуги з представництва зацікавлених сторін
79120000-1	Patent and copyright consultancy services	3	f	Консультаційні послуги з питань патентування та з авторського права
79121000-8	Copyright consultancy services	4	f	Консультаційні послуги з авторського права
79121100-9	Software copyright consultancy services	5	t	Консультаційні послуги з питань авторських прав на програмне забезпечення
79130000-4	Legal documentation and certification services	3	f	Юридичні послуги, пов’язані з оформленням і засвідченням документів
79131000-1	Documentation services	4	t	Послуги з оформлення документів
79132000-8	Certification services	4	f	Послуги із засвідчення документів
79132100-9	Electronic signature certification services	5	t	Послуги із засвідчення справжності електронних підписів
79140000-7	Legal advisory and information services	3	t	Послуги з юридичної консультації та правового інформування
79200000-6	Accounting, auditing and fiscal services	2	f	Бухгалтерські, аудиторські та податкові послуги
79210000-9	Accounting and auditing services	3	f	Бухгалтерські та аудиторські послуги
79211000-6	Accounting services	4	f	Бухгалтерські послуги
79211100-7	Bookkeeping services	5	f	Послуги з ведення бухгалтерського обліку
79211110-0	Payroll management services	6	t	Послуги з розрахунку, обліку та нарахування заробітної плати
79211120-3	Sales and purchases recording services	6	t	Послуги з реєстрації операцій купівлі-продажу
79211200-8	Compilation of financial statements services	5	t	Послуги з підготовки фінансової звітності
79212000-3	Auditing services	4	f	Аудиторські послуги
79212100-4	Financial auditing services	5	f	Послуги з перевірки фінансового-господарської діяльності
79212110-7	Corporate governance rating services	6	t	Послуги з визначення рейтингу корпоративного управління
79212200-5	Internal audit services	5	t	Послуги з проведення внутрішньої аудиторської перевірки
79212300-6	Statutory audit services	5	t	Послуги з проведення обов’язкової аудиторської перевірки
79212400-7	Fraud audit services	5	t	Послуги з перевірки фінансових зловживань
79212500-8	Accounting review services	5	t	Послуги з перевірки бухгалтерської звітності
79220000-2	Fiscal services	3	f	Податкові послуги
79221000-9	Tax consultancy services	4	t	Консультаційні послуги з питань оподаткування
79222000-6	Tax-return preparation services	4	t	Послуги з підготовки податкових декларацій
79223000-3	Custom broker services	4	t	Послуги митних брокерів
79300000-7	Market and economic research; polling and statistics	2	f	Ринкові та економічні дослідження; опитування та статистика
79310000-0	Market research services	3	f	Послуги з проведення ринкових досліджень
79311000-7	Survey services	4	f	Послуги у сфері опитувань
79311100-8	Survey design services	5	t	Послуги з розробки програм опитування
79311200-9	Survey conduction services	5	f	Послуги з проведення опитувань
79311210-2	Telephone survey services	6	t	Послуги з проведення телефонних опитувань
79311300-0	Survey analysis services	5	t	Послуги з аналізу результатів опитування
79311400-1	Economic research services	5	f	Послуги з проведення економічних досліджень
79311410-4	Economic impact assessment	6	t	Оцінювання економічних наслідків
79312000-4	Market-testing services	4	t	Послуги з проведення ринкових випробувань
79313000-1	Performance review services	4	t	Послуги з аналізу ефективності роботи персоналу
79314000-8	Feasibility study	4	t	Техніко-економічне обґрунтування
79315000-5	Social research services	4	t	Послуги у сфері соціальних досліджень
79320000-3	Public-opinion polling services	3	t	Послуги з опитування громадської думки
79330000-6	Statistical services	3	t	Статистичні послуги
79340000-9	Advertising and marketing services	3	f	Рекламні та маркетингові послуги
79341000-6	Advertising services	4	f	Рекламні послуги
79341100-7	Advertising consultancy services	5	t	Консультаційні послуги з питань реклами
79341200-8	Advertising management services	5	t	Послуги з управління рекламною діяльністю
79341400-0	Advertising campaign services	5	t	Послуги з проведення рекламних кампаній
79341500-1	Aeral advertising services	5	t	Послуги з організації повітряної реклами
79342000-3	Marketing services	4	f	Маркетингові послуги
79342100-4	Direct marketing services	5	t	Послуги прямого маркетингу
79342200-5	Promotional services	5	t	Промоційні послуги
79342300-6	Customer services	5	f	Обслуговування клієнтів
79342310-9	Customer survey services	6	f	Послуги з опитування споживачів
79342311-6	Customer satisfaction survey	7	t	Дослідження рівня задоволеності споживачів
79342320-2	Customer-care services	6	f	Послуги з підтримки клієнтів
79342321-9	Customer-loyalty programme	7	t	Програми лояльності для клієнтів
79342400-7	Auction services	5	f	Послуги з проведення аукціонів
79342410-4	Electronic auction services	6	t	Послуги з проведення електронних аукціонів
79400000-8	Business and management consultancy and related services	2	f	Консультаційні послуги з питань підприємницької діяльності та управління і супутні послуги
79410000-1	Business and management consultancy services	3	f	Консультаційні послуги з питань підприємницької діяльності та управління
79411000-8	General management consultancy services	4	f	Консультаційні послуги з питань загального управління
79411100-9	Business development consultancy services	5	t	Консультаційні послуги з питань залучення нових клієнтів
79412000-5	Financial management consultancy services	4	t	Консультаційні послуги з питань управління фінансами
79413000-2	Marketing management consultancy services	4	t	Консультаційні послуги з питань управління маркетингом
79414000-9	Human resources management consultancy services	4	t	Консультаційні послуги з питань управління людськими ресурсами
79415000-6	Production management consultancy services	4	f	Консультаційні послуги з питань управління виробничим процесом
79415200-8	Design consultancy services	5	t	Консультаційні послуги з питань проектування
79416000-3	Public relations services	4	f	Послуги з організації та підтримки зв’язків із громадськістю
79416100-4	Public relations management services	5	t	Послуги з управління зв’язками з громадськістю
79416200-5	Public relations consultancy services	5	t	Консультаційні послуги з питань зв’язків із громадськістю
79417000-0	Safety consultancy services	4	t	Консультаційні послуги з питань безпеки
79418000-7	Procurement consultancy services	4	t	Консультаційні послуги з питань закупівель
79419000-4	Evaluation consultancy services	4	t	Консультаційні послуги з питань оцінювання
79420000-4	Management-related services	3	f	Управлінські послуги
79421000-1	Project-management services other than for construction work	4	f	Послуги з управління проектами, крім будівельних
79421100-2	Project-supervision services other than for construction work	5	t	Послуги з нагляду за виконанням проектів, крім будівельних
79421200-3	Project-design services other than for construction work	5	t	Послуги з розробки проектів, крім будівельних
79422000-8	Arbitration and conciliation services	4	t	Арбітражні та примирювальні послуги
79430000-7	Crisis management services	3	t	Послуги з управління в умовах кризи
79500000-9	Office-support services	2	f	Послуги із забезпечення роботи офісу
79510000-2	Telephone-answering services	3	f	Послуги з приймання телефонних дзвінків
79511000-9	Telephone operator services	4	t	Послуги оператора-телефоніста
79512000-6	Call centre	4	t	Кол-центри
79520000-5	Reprographic services	3	f	Копіювально-розмножувальні послуги
79521000-2	Photocopying services	4	t	Послуги з фотокопіювання
79530000-8	Translation services	3	t	Послуги з письмового перекладу
79540000-1	Interpretation services	3	t	Послуги з усного перекладу
79550000-4	Typing, word-processing and desktop publishing services	3	f	Послуги з набору та обробки текстів і комп’ютерної верстки
79551000-1	Typing services	4	t	Послуги з набору текстів
79552000-8	Word-processing services	4	t	Послуги з обробки текстів
79553000-5	Desktop publishing services	4	t	Послуги з комп’ютерної верстки
79560000-7	Filing services	3	t	Послуги з ведення картотек
79570000-0	Mailing-list compilation and mailing services	3	f	Послуги зі складання адресних списків і розсилання кореспонденції
79571000-7	Mailing services	4	t	Послуги з розсилання кореспонденції
79600000-0	Recruitment services	2	f	Послуги з підбору персоналу
79610000-3	Placement services of personnel	3	f	Послуги з розміщення персоналу
79611000-0	Job search services	4	t	Послуги з пошуку роботи
79612000-7	Placement services of office-support personnel	4	t	Послуги з розміщення допоміжного персоналу
79613000-4	Employee relocation services	4	t	Послуги з переведення працівників
79620000-6	Supply services of personnel including temporary staff	3	f	Послуги із забезпечення персоналом, у тому числі тимчасовим
79621000-3	Supply services of office personnel	4	t	Послуги із забезпечення офісним персоналом
79622000-0	Supply services of domestic help personnel	4	t	Послуги з підбору домашньої прислуги
79623000-7	Supply services of commercial or industrial workers	4	t	Послуги із забезпечення торговим і промисловим персоналом
79624000-4	Supply services of nursing personnel	4	t	Послуги із забезпечення молодшим медичним персоналом
79625000-1	Supply services of medical personnel	4	t	Послуги із забезпечення медичним персоналом
79630000-9	Personnel services except placement and supply services	3	f	Кадрові послуги, крім розміщення персоналу та забезпечення персоналом
79631000-6	Personnel and payroll services	4	t	Кадрові послуги та послуги з розрахунку, обліку та нараховування заробітної плати
79632000-3	Personnel-training services	4	t	Послуги з підготовки персоналу
79633000-0	Staff development services	4	t	Послуги з підвищування кваліфікації персоналу
79634000-7	Career guidance services	4	t	Послуги з професійної орієнтації
79635000-4	Assessment centre services for recruitment	4	t	Послуги центрів оцінювання укомплектованості особового складу
79700000-1	Investigation and security services	2	f	Послуги у сфері розслідувань та охорони
79710000-4	Security services	3	f	Охоронні послуги
79711000-1	Alarm-monitoring services	4	t	Послуги з моніторингу сигналів тривоги, що надходять з пристроїв охоронної сигналізації
79713000-5	Guard services	4	t	Послуги з охорони об’єктів та особистої охорони
79714000-2	Surveillance services	4	f	Послуги зі спостереження
79714100-3	Tracing system services	5	f	Послуги з розшуку
79714110-6	Absconder-tracing services	6	t	Послуги з розшуку осіб, що переховуються від правосуддя
79715000-9	Patrol services	4	t	Послуги з патрулювання
79716000-6	Identification badge release services	4	t	Послуги з видачі службових перепусток
79720000-7	Investigation services	3	f	Послуги з проведення розслідувань
79721000-4	Detective agency services	4	t	Послуги детективних агентств
79722000-1	Graphology services	4	t	Послуги з проведення графологічної експертизи
79723000-8	Waste analysis services	4	t	Послуги з аналізу відходів
79800000-2	Printing and related services	2	f	Друкарські та супутні послуги
79810000-5	Printing services	3	f	Друкарські послуги
79811000-2	Digital printing services	4	t	Послуги з цифрового друку
79812000-9	Banknote printing services	4	t	Послуги з друку банкнот
79820000-8	Services related to printing	3	f	Послуги, пов’язані з друком
79821000-5	Print finishing services	4	f	Послуги з післядрукової обробки
79821100-6	Proofreading services	5	t	Редакторські послуги
79822000-2	Composition services	4	f	Послуги з типографського набору
79822100-3	Print-plate making services	5	t	Послуги з виготовляння друкарських форм
79822200-4	Photogravure services	5	t	Послуги з фотогравірування
79822300-5	Typesetting services	5	t	Послуги з набору текстів
79822400-6	Lithographic services	5	t	Послуги з виготовлення літографій
79822500-7	Graphic design services	5	t	Послуги графічних дизайнерів
79823000-9	Printing and delivery services	4	t	Послуги з друку та доставки надрукованої продукції
79824000-6	Printing and distribution services	4	t	Послуги з друку та розповсюджування надрукованої продукції
79900000-3	Miscellaneous business and business-related services	2	f	Різні ділові та пов’язані з діловою сферою послуги
79910000-6	Management holdings services	3	t	Послуги холдингів
79920000-9	Packaging and related services	3	f	Пакувальні та супутні послуги
79921000-6	Packaging services	4	t	Пакувальні послуги
79930000-2	Specialty design services	3	f	Професійні дизайнерські послуги
79931000-9	Interior decorating services	4	t	Послуги з декорування інтер’єру
79932000-6	Interior design services	4	t	Послуги з оформлення інтер’єру
79933000-3	Design support services	4	t	Додаткові дизайнерські послуги
79934000-0	Furniture design services	4	t	Послуги з проектування меблів
79940000-5	Collection agency services	3	f	Послуги колекторських агентств
79941000-2	Toll-collection services	4	t	Послуги зі збору мита
79950000-8	Exhibition, fair and congress organisation services	3	f	Послуги з організації виставок, ярмарок і конгресів
79951000-5	Seminar organisation services	4	t	Послуги з організації семінарів
79952000-2	Event services	4	f	Послуги з організації заходів
79952100-3	Cultural event organisation services	5	t	Послуги з організації культурних заходів
79953000-9	Festival organisation services	4	t	Послуги з організації фестивалів
79954000-6	Party organisation services	4	t	Послуги з організації вечірок
79955000-3	Fashion shows organisation services	4	t	Послуги з організації модних показів
79956000-0	Fair and exhibition organisation services	4	t	Послуги з організації ярмарок і виставок
79957000-7	Auction organisation services	4	t	Послуги з організації аукціонів
79960000-1	Photographic and ancillary services	3	f	Послуги фотографів і супутні послуги
79961000-8	Photographic services	4	f	Послуги фотографів
79961100-9	Advertising photography services	5	t	Послуги у сфері рекламної фотографії
79961200-0	Aerial photography services	5	t	Послуги з аерофотозйомки
79961300-1	Specialised photography services	5	f	Послуги зі спеціалізованої фотозйомки
79961310-4	Downhole photography services	6	t	Послуги зі фотозйомки всередині свердловин
79961320-7	Underwater photography services	6	t	Послуги з підводної фотозйомки
79961330-0	Microfilming services	6	t	Послуги з мікрофільмування
79961340-3	X-ray photography services	6	t	Послуги з рентгенографії
79961350-6	Studio photography services	6	t	Послуги зі студійної фотозйомки
79962000-5	Photograph processing services	4	t	Послуги з обробки фотографій
79963000-2	Photograph restoration, copying and retouching services	4	t	Послуги з реставрування, копіювання та ретушування фотографій
79970000-4	Publishing services	3	f	Видавничі послуги
79971000-1	Bookbinding and finishing services	4	f	Палітурні послуги та послуги з післядрукової обробки книг
79971100-2	Book finishing services	5	t	Послуги з післядрукової обробки книг
79971200-3	Bookbinding services	5	t	Палітурні послуги
79972000-8	Language dictionary publishing services	4	f	Послуги з видавництва мовних словників
79972100-9	Regional language dictionary publishing services	5	t	Послуги з видавництва словників регіональних мов
79980000-7	Subscription services	3	t	Послуги з передплати друкованих видань
79990000-0	Miscellaneous business-related services	3	f	Різні послуги, пов’язані з діловою сферою
79991000-7	Stock-control services	4	t	Послуги з інвентаризації
79992000-4	Reception services	4	t	Приймальні послуги
79993000-1	Building and facilities management services	4	f	Послуги з домоуправління та адміністративно-господарського управління
79993100-2	Facilities management services	5	t	Послуги з адміністративно-господарського управління
79994000-8	Contract administration services	4	t	Послуги з управління договірною діяльністю
79995000-5	Library management services	4	f	Послуги з управління бібліотечними фондами
79995100-6	Archiving services	5	t	Архівні послуги
79995200-7	Cataloguing services	5	t	Послуги з каталогізації
79996000-2	Business organisation services	4	f	Послуги з організації підприємницької діяльності
79996100-3	Records management	5	t	Управління документообігом
79997000-9	Business travel services	4	t	Послуги з організації відряджень
79998000-6	Coaching services	4	t	Тренерські послуги
79999000-3	Scanning and invoicing services	4	f	Послуги зі сканування та виписування рахунків
79999100-4	Scanning services	5	t	Послуги зі сканування
79999200-5	Invoicing services	5	t	Послуги з виписування рахунків
80000000-4	Education and training services	1	f	Послуги у сфері освіти та навчання
80100000-5	Primary education services	2	f	Послуги у сфері початкової освіти
80110000-8	Pre-school education services	3	t	Послуги у сфері дошкільної освіти
80200000-6	Secondary education services	2	f	Послуги у сфері загальної середньої освіти
80210000-9	Technical and vocational secondary education services	3	f	Послуги у сфері середньої технічної та професійної освіти
80211000-6	Technical secondary education services	4	t	Послуги у сфері середньої технічної освіти
80212000-3	Vocational secondary education services	4	t	Послуги у сфері середньої професійної освіти
80300000-7	Higher education services	2	f	Послуги у сфері вищої освіти
80310000-0	Youth education services	3	t	Послуги у сфері освіти для молоді
80320000-3	Medical education services	3	t	Послуги у сфері медичної освіти
80330000-6	Safety education services	3	t	Послуги у сфері освіти в галузі безпеки
80340000-9	Special education services	3	t	Послуги у сфері спеціальної освіти
80400000-8	Adult and other education services	2	f	Послуги у сфері освіти для дорослих та інші освітні послуги
80410000-1	Various school services	3	f	Послуги різних навчальних закладів
80411000-8	Driving-school services	4	f	Послуги автошкіл
80411100-9	Driving-test services	5	t	Послуги з екзаменування на право на керування транспортними засобами
80411200-0	Driving lessons	5	t	Курси водіїв
80412000-5	Flying-school services	4	t	Послуги авіаційних шкіл
80413000-2	Sailing-school services	4	t	Послуги морехідних шкіл
80414000-9	Diving-school services	4	t	Послуги шкіл дайвінгу
80415000-6	Ski-training services	4	t	Послуги лижних інструкторів
80420000-4	E-learning services	3	t	Послуги у сфері електронної освіти
80430000-7	Adult-education services at university level	3	t	Послуги у сфері університетської освіти для дорослих
80490000-5	Operation of an educational centre	3	t	Утримання освітніх центрів
80500000-9	Training services	2	f	Навчальні послуги
80510000-2	Specialist training services	3	f	Послуги з професійної підготовки спеціалістів
80511000-9	Staff training services	4	t	Послуги з навчання персоналу
80512000-6	Dog training services	4	t	Послуги з дресирування собак
80513000-3	Horse riding school services	4	t	Послуг шкіл верхової їзди
80520000-5	Training facilities	3	f	Навчальні засоби
80521000-2	Training programme services	4	t	Послуги, пов’язані з навчальними програмами
80522000-9	Training seminars	4	t	Навчальні семінари
80530000-8	Vocational training services	3	f	Послуги у сфері професійної підготовки
80531000-5	Industrial and technical training services	4	f	Послуги у сфері професійної підготовки фахівців у галузі промисловості та техніки
80531100-6	Industrial training services	5	t	Послуги у сфері професійної підготовки фахівців у галузі промисловості
80531200-7	Technical training services	5	t	Послуги у сфері професійної підготовки технічних фахівців
80532000-2	Management training services	4	t	Послуги з підготовки керівних кадрів
80533000-9	Computer-user familiarisation and training services	4	f	Послуги з ознайомлювального та професійного навчання роботі з комп’ютером
80533100-0	Computer training services	5	t	Послуги з професійного навчання роботі з комп’ютером
80533200-1	Computer courses	5	t	Курси комп’ютерної грамотності
80540000-1	Environmental training services	3	t	Послуги з професійної підготовки у сфері охорони довкілля
80550000-4	Safety training services	3	t	Послуги з професійної підготовки у сфері безпеки
80560000-7	Health and first-aid training services	3	f	Послуги з професійної підготовки у сфері охорони здоров’я та надання першої медичної допомоги
80561000-4	Health training services	4	t	Послуги з професійної підготовки у сфері охорони здоров’я
80562000-1	First-aid training services	4	t	Послуги з професійної підготовки у сфері надання першої медичної допомоги
80570000-0	Personal development training services	3	t	Послуги з професійної підготовки у сфері підвищення кваліфікації
80580000-3	Provision of language courses	3	t	Викладання мовних курсів
80590000-6	Tutorial services	3	t	Послуги репетиторів
80600000-0	Training services in defence and security materials	2	f	Послуги професійної підготовки у галузі оборони та засобів безпеки
80610000-3	Training and simulation in security equipment	3	t	Навчання роботі з охоронним обладнанням, у тому числі з використанням методу моделювання
80620000-6	Training and simulation in firearms and ammunition	3	t	Навчання поводження з вогнепальною зброєю та боєприпасами, в тому числі з використанням методу моделювання
80630000-9	Training and simulation in military vehicles	3	t	Навчання керуванню військовими транспортними засобами, в тому числі з використанням методу моделювання
80640000-2	Training and simulation in warships	3	t	Навчання керуванню військовими кораблями, в тому числі з використанням методу моделювання
80650000-5	Training and simulation in aircrafts, missiles and spacecrafts	3	t	Навчання керуванню літальними та космічними апаратами і ракетами, в тому числі з використанням методу моделювання
80660000-8	Training and simulation in military electronic systems	3	t	Навчання роботі з електронними системами військового призначення, в тому числі з використанням методу моделювання
85000000-9	Health and social work services	1	f	Послуги у сфері охорони здоров’я та соціальної допомоги
85100000-0	Health services	2	f	Послуги у сфері охорони здоров’я
85110000-3	Hospital and related services	3	f	Послуги лікувальних закладів та супутні послуги
85111000-0	Hospital services	4	f	Послуги лікувальних закладів
85111100-1	Surgical hospital services	5	t	Послуги хірургічних стаціонарних закладів
85111200-2	Medical hospital services	5	t	Послуги терапевтичних стаціонарних закладів
85111300-3	Gynaecological hospital services	5	f	Послуги гінекологічних стаціонарних закладів
85111310-6	In vitro fertilisation services	6	t	Послуги з екстракорпорального запліднення
85111320-9	Obstetrical hospital services	6	t	Послуги акушерських стаціонарних закладів
85111400-4	Rehabilitation hospital services	5	t	Послуги реабілітаційних стаціонарних закладів
85111500-5	Psychiatric hospital services	5	t	Послуги психіатричних стаціонарних закладів
85111600-6	Orthotic services	5	t	Ортопедичні послуги
85111700-7	Oxygen-therapy services	5	t	Послуги з оксигенотерапії
85111800-8	Pathology services	5	f	Послуги з патологоанатомічних досліджень
85111810-1	Blood analysis services	6	t	Послуги з проведення аналізів крові
85111820-4	Bacteriological analysis services	6	t	Послуги з проведення бактеріологічних досліджень
85111900-9	Hospital dialysis services	5	t	Послуги з проведення діалізу в умовах стаціонару
85112000-7	Hospital support services	4	f	Послуги, пов’язані з лікарняною інфраструктурою
85112100-8	Hospital-bedding services	5	t	Послуги, пов’язані з лікарняною білизною
85112200-9	Outpatient care services	5	t	Послуги з надання амбулаторної медичної допомоги
85120000-6	Medical practice and related services	3	f	Лікарська практика та супутні послуги
85121000-3	Medical practice services	4	f	Послуги у сфері лікарської практики
85121100-4	General-practitioner services	5	t	Послуги лікарів загальної практики
85121200-5	Medical specialist services	5	f	Послуги вузькоспеціалізованих лікарів
85121210-8	Gyneacologic or obstetric services	6	t	Послуги гінекологів чи акушерів
85121220-1	Nephrology or nervous system specialist services	6	t	Послуги нефрологів або неврологів
85121230-4	Cardiology services or pulmonary specialists services	6	f	Послуги кардіологів або пульмонологів
85121231-1	Cardiology services	7	t	Послуги кардіологів
85121232-8	Pulmonary specialists services	7	t	Послуги пульмонологів
85121240-7	ENT or audiologist services	6	t	Послуги оториноларингологів чи аудіологів
85121250-0	Gastroenterologist and geriatric services	6	f	Послуги гастроентерологів або геріатрів
85121251-7	Gastroenterologist services	7	t	Послуги гастроентерологів
85121252-4	Geriatric services	7	t	Послуги геріатрів
85121270-6	Psychiatrist or psychologist services	6	f	Послуги психіатрів або психологів
85121271-3	Home for the psychologically disturbed services	7	t	Послуги з догляду за пацієнтами з психологічними розладами вдома
85121280-9	Ophthalmologist, dermatology or orthopedics services	6	f	Послуги офтальмологів, дерматологів чи ортопедів
85121281-6	Ophthalmologist services	7	t	Послуги офтальмологів
85121282-3	Dermatology services	7	t	Послуги дерматологів
85121283-0	Orthopaedic services	7	t	Послуги ортопедів
85121290-2	Paediatric or urologist services	6	f	Послуги педіатрів чи урологів
85121291-9	Paediatric services	7	t	Послуги педіатрів
85121300-6	Surgical specialist services	5	t	Послуги хірургів
85130000-9	Dental practice and related services	3	f	Стоматологічні та супутні послуги
85131000-6	Dental-practice services	4	f	Стоматологічні послуги
85131100-7	Orthodontic services	5	f	Послуги ортодонтів
85131110-0	Orthodontic-surgery services	6	t	Послуги хірургів-ортодонтів
85140000-2	Miscellaneous health services	3	f	Послуги у сфері охорони здоров’я різні
85141000-9	Services provided by medical personnel	4	f	Послуги медичного персоналу
85141100-0	Services provided by midwives	5	t	Послуги акушерів
85141200-1	Services provided by nurses	5	f	Послуги молодшого медичного персоналу
85141210-4	Home medical treatment services	6	f	Послуги з надання медичної допомоги вдома
85141211-1	Dialysis home medical treatment services	7	t	Послуги з проведення діалізу вдома
85141220-7	Advisory services provided by nurses	6	t	Консультаційні послуги молодшого медичного персоналу
85142000-6	Paramedical services	4	f	Парамедичні послуги
85142100-7	Physiotherapy services	5	t	Фізіотерапевтичні послуги
85142200-8	Homeopathic services	5	t	Гомеопатичні послуги
85142300-9	Hygiene services	5	t	Санітарно-гігієнічні послуги
85142400-0	Home delivery of incontinence products	5	t	Доставка додому товарів для пацієнтів, що страждають на нетримання сечі
85143000-3	Ambulance services	4	t	Послуги швидкої медичної допомоги
85144000-0	Residential health facilities services	4	f	Послуги позалікарняної медичної допомоги
85144100-1	Residential nursing care services	5	t	Послуги стаціонарних соціально-медичних установ
85145000-7	Services provided by medical laboratories	4	t	Послуги медичних лабораторій
85146000-4	Services provided by blood banks	4	f	Послуги банків крові
85146100-5	Services provided by sperm banks	5	t	Послуги банків сперми
85146200-6	Services provided by transplant organ banks	5	t	Послуги банків органів для трансплантації
85147000-1	Company health services	4	t	Послуги у сфері медицини праці
85148000-8	Medical analysis services	4	t	Послуги з проведення медичних аналізів
85149000-5	Pharmacy services	4	t	Аптечні послуги
85150000-5	Medical imaging services	3	t	Послуги діагностичної візуалізації
85160000-8	Optician services	3	t	Послуги оптик
85170000-1	Acupuncture and chiropractor services	3	f	Послуги фахівців із акупунктури та хіропрактики
85171000-8	Acupuncture services	4	t	Послуги фахівців із акупунктури
85172000-5	Chiropractor services	4	t	Послуги фахівців із хіропрактики
85200000-1	Veterinary services	2	f	Ветеринарні послуги
85210000-3	Domestic animal nurseries	3	t	Розплідники домашніх тварин
85300000-2	Social work and related services	2	f	Послуги з надання соціальної допомоги та супутні послуги
85310000-5	Social work services	3	f	Послуги з надання соціальної допомоги
85311000-2	Social work services with accommodation	4	f	Послуги з надання соціальної допомоги із забезпеченням проживання
85311100-3	Welfare services for the elderly	5	t	Послуги з надання соціальної допомоги особам похилого віку
85311200-4	Welfare services for the handicapped	5	t	Послуги з надання соціальної допомоги особам із обмеженими фізичними можливостями
85311300-5	Welfare services for children and young people	5	t	Послуги з надання соціальної допомоги дітям і молоді
85312000-9	Social work services without accommodation	4	f	Послуги з надання соціальної допомоги без забезпечення проживання
85312100-0	Daycare services	5	f	Послуги закладів денного догляду
85312110-3	Child daycare services	6	t	Послуги закладів денного догляду за дітьми
85312120-6	Daycare services for handicapped children and young people	6	t	Послуги з денного догляду за дітьми та молоддю з обмеженими фізичними можливостями
85312200-1	Homedelivery of provisions	5	t	Доставка продуктів харчування додому
85312300-2	Guidance and counselling services	5	f	Послуги з професійної орієнтації та надання психологічної допомоги
85312310-5	Guidance services	6	t	Послуги з професійної орієнтації
85312320-8	Counselling services	6	t	Послуги з надання психологічної допомоги
85312330-1	Family-planning services	6	t	Послуги з надання допомоги у плануванні сім’ї
85312400-3	Welfare services not delivered through residential institutions	5	t	Послуги у сфері соціального забезпечення, надавані не спеціалізованими стаціонарними закладами
85312500-4	Rehabilitation services	5	f	Реабілітаційні послуги
85312510-7	Vocational rehabilitation services	6	t	Послуги з професійної реабілітації
85320000-8	Social services	3	f	Соціальні послуги
85321000-5	Administrative social services	4	t	Адміністративні соціальні послуги
85322000-2	Community action programme	4	t	Програми громадської дії
85323000-9	Community health services	4	t	Соціально орієнтовані послуги у сфері охорони здоров’я
90000000-7	Sewage, refuse, cleaning and environmental services	1	f	Послуги у сферах поводження зі стічними водами та сміттям, послуги у сферах санітарії та охорони довкілля
90400000-1	Sewage services	2	f	Послуги у сфері водовідведення
90410000-4	Sewage removal services	3	t	Послуги з відкачування стічних вод
90420000-7	Sewage treatment services	3	t	Послуги з очищення стічних вод
90430000-0	Sewage disposal services	3	t	Послуги з відведення стічних вод
90440000-3	Treatment services of cesspools	3	t	Послуги у сфері поводження з вигрібними ямами
90450000-6	Treatment services of septic tanks	3	t	Послуги у сфері поводження із септиками
90460000-9	Cesspool or septic tank emptying services	3	t	Послуги зі спорожнення вигрібних ям і септиків
90470000-2	Sewer cleaning services	3	t	Послуги з чищення каналізаційних колекторів
90480000-5	Sewerage management services	3	f	Послуги з управління каналізаційною системою
90481000-2	Operation of a sewage plant	4	t	Утримання станцій очищення стічних вод
90490000-8	Sewer survey and sewage treatment consultancy services	3	f	Послуги з інспектування каналізаційних колекторів і консультаційні послуги з питань очищення стічних вод
90491000-5	Sewer survey services	4	t	Послуги з інспектування каналізаційних колекторів
90492000-2	Sewage treatment consultancy services	4	t	Консультаційні послуги з питань очищення стічних вод
90500000-2	Refuse and waste related services	2	f	Послуги у сфері поводження зі сміттям та відходами
90510000-5	Refuse disposal and treatment	3	f	Утилізація сміття та поводження зі сміттям
90511000-2	Refuse collection services	4	f	Послуги зі збирання сміття
90511100-3	Urban solid-refuse collection services	5	t	Послуги зі збирання сміття з урн і контейнерів у громадських місцях
90511200-4	Household-refuse collection services	5	t	Послуги зі збирання побутових відходів
90511300-5	Litter collection services	5	t	Послуги зі збирання розкиданого сміття
90511400-6	Paper collecting services	5	t	Послуги зі збирання макулатури
90512000-9	Refuse transport services	4	t	Послуги з перевезення сміття
90513000-6	Non-hazardous refuse and waste treatment and disposal services	4	f	Послуги з поводження із безпечними сміттям і відходами та їх утилізація
90513100-7	Household-refuse disposal services	5	t	Послуги з утилізації побутових відходів
90513200-8	Urban solid-refuse disposal services	5	t	Послуги з утилізації сміття з урн і контейнерів у громадських місцях
90513300-9	Refuse incineration services	5	t	Послуги зі спалення сміття
90513400-0	Ash disposal services	5	t	Послуги з утилізації попелу та золи
90513500-1	Treatment and disposal of foul liquids	5	t	Поводження з побутовими стічними водами та їх утилізація
90513600-2	Sludge removal services	5	t	Послуги з відкачування мулу
90513700-3	Sludge transport services	5	t	Послуги з вивезення мулу
90513800-4	Sludge treatment services	5	t	Послуги з очищення мулу
90513900-5	Sludge disposal services	5	t	Послуги з утилізації мулу
90514000-3	Refuse recycling services	4	t	Послуги з переробки сміття
90520000-8	Radioactive-, toxic-, medical- and hazardous waste services	3	f	Послуги у сфері поводження з радіоактивними, токсичними, медичними та небезпечними відходами
90521000-5	Radioactive waste treatment services	4	f	Послуги у сфері поводження з радіоактивними відходами
90521100-6	Collection of radioactive waste	5	t	Збирання радіоактивних відходів
90521200-7	Radioactive waste storage services	5	t	Послуги зі зберігання радіоактивних відходів
90521300-8	Disposal of radioactive waste	5	t	Утилізація радіоактивних відходів
90521400-9	Transport of radioactive waste	5	f	Транспортування радіоактивних відходів
90521410-2	Transportation of low level nuclear waste	6	t	Транспортування низькоактивних ядерних відходів
90521420-5	Transportation of intermediate level nuclear waste	6	t	Транспортування середньоактивних ядерних відходів
90521500-0	Packaging of radioactive waste	5	f	Пакування радіоактивних відходів
90521510-3	Packaging of low level nuclear waste	6	t	Пакування низькоактивних ядерних відходів
90521520-6	Packaging of intermediate level nuclear waste	6	t	Пакування середньоактивних ядерних відходів
90522000-2	Services relating to contaminated soil	4	f	Послуги, пов’язані з забрудненими ґрунтами
90522100-3	Removal of contaminated soil	5	t	Вилучення забруднених ґрунтів
90522200-4	Disposal of contaminated soil	5	t	Утилізація забруднених ґрунтів
90522300-5	Contaminated-soil treatment services	5	t	Послуги з обробки забруднених ґрунтів
90522400-6	Cleaning and treatment of soil	5	t	Очищування та обробка ґрунтів
90523000-9	Toxic waste disposal services except radioactive waste and contaminated soil	4	f	Послуги з утилізації токсичних відходів, окрім радіоактивних відходів і забруднених ґрунтів
90523100-0	Weapons and ammunition disposal services	5	t	Послуги з утилізації зброї та боєприпасів
90523200-1	Bomb-disposal services	5	t	Послуги з утилізації бомб
90523300-2	Mine sweeping services	5	t	Послуги розмінування
90524000-6	Medical waste services	4	f	Послуги у сфері поводження з медичними відходами
90524100-7	Clinical-waste collection services	5	t	Послуги зі збирання медичних відходів
90524200-8	Clinical-waste disposal services	5	t	Послуги з утилізації медичних відходів
90524300-9	Removal services of biological waste	5	t	Послуги з вивезення біологічних відходів
90524400-0	Collection, transport and disposal of hospital waste	5	t	Збирання, транспортування та утилізація відходів лікарень
90530000-1	Operation of a refuse site	3	f	Утримання сміттєзвалищ
90531000-8	Landfill management services	4	t	Послуги з управління полігонами твердих побутових відходів
90532000-5	Coal-tip management services	4	t	Послуги з управління териконами
90533000-2	Waste-tip management services	4	t	Послуги з управління сміттєзвалищами
90600000-3	Cleaning and sanitation services in urban or rural areas, and related services	2	f	Послуги з прибирання й асенізації для міських і сільських громад та супутні послуги
90610000-6	Street-cleaning and sweeping services	3	f	Послуги з прибирання та підмітання вулиць
90611000-3	Street-cleaning services	4	t	Послуги з прибирання вулиць
90612000-0	Street-sweeping services	4	t	Послуги з підмітання вулиць
90620000-9	Snow-clearing services	3	t	Послуги з прибирання снігу
90630000-2	Ice-clearing services	3	t	Послуги з прибирання льоду
90640000-5	Gully cleaning and emptying services	3	f	Послуги з очищення та спорожнення стічних канав
90641000-2	Gully cleaning services	4	t	Послуги з очищення стічних канав
90642000-9	Gully emptying services	4	t	Послуги зі спорожнення стічних канав
90650000-8	Asbestos removal services	3	t	Послуги з вилучення азбесту
90660000-1	Deleading services	3	t	Послуги з вилучення свинцю
90670000-4	Disinfecting and exterminating services in urban or rural areas	3	t	Послуги з дезінфікування та дератизування міських і сільських територій
90680000-7	Beach cleaning services	3	t	Послуги з прибирання пляжів
90690000-0	Graffiti removal services	3	t	Послуги з видалення графіті
90700000-4	Environmental services	2	f	Послуги у сфері охорони довкілля
90710000-7	Environmental management	3	f	Екологічний менеджмент
90711000-4	Environmental impact assessment other than for construction	4	f	Оцінювання впливу на довкілля різних галузей, окрім будівництва
90711100-5	Risk or hazard assessment other than for construction	5	t	Оцінювання ризиків або небезпек, пов’язаних із різними галузями, крім будівництва
90711200-6	Environmental standards other than for construction	5	t	Екологічні стандарти у різних галузях, окрім будівництва
90711300-7	Environmental indicators analysis other than for construction	5	t	Аналіз екологічних показників різних галузей, окрім будівництва
90711400-8	Environmental Impact Assessment (EIA) services other than for construction	5	t	Послуги з оцінювання впливу на довкілля різних галузей, окрім будівництва
90711500-9	Environmental monitoring other than for construction	5	t	Екологічний моніторинг різних галузей, окрім будівництва
90712000-1	Environmental planning	4	f	Екологічне планування
90712100-2	Urban environmental development planning	5	t	Планування розвитку міського середовища
90712200-3	Forest conservation strategy planning	5	t	Планування стратегії збереження лісів
90712300-4	Marine conservation strategy planning	5	t	Планування стратегії збереження морського середовища
90712400-5	Natural resources management or conservation strategy planning services	5	t	Послуги з планування стратегії управління природними ресурсами та їх збереження
90712500-6	Environmental institution building or planning	5	t	Інституційний розвиток і планування у сфері охорони довкілля
90713000-8	Environmental issues consultancy services	4	f	Консультаційні послуги з питань охорони довкілля
90713100-9	Consulting services for water-supply and waste-water other than for construction	5	t	Консультаційні послуги з питань водопостачання та поводження зі стічними водами у різних галузях, окрім будівництва
90714000-5	Environmental auditing	4	f	Екологічний аудит
90714100-6	Environmental information systems	5	t	Системи екологічної інформації
90714200-7	Corporate environmental auditing services	5	t	Послуги з екологічного аудиту підприємств
90714300-8	Sectoral environmental auditing services	5	t	Послуги з екологічного аудиту галузей
90714400-9	Activity specific environmental auditing services	5	t	Послуги з екологічного аудиту окремих видів господарської діяльності
90714500-0	Environmental quality control services	5	t	Послуги з контролю за якістю довкілля
90714600-1	Environmental security control services	5	t	Послуги з контролю екологічної безпеки
90715000-2	Pollution investigation services	4	f	Послуги з дослідження забруднень
90715100-3	Chemicals and oil pollution investigation services	5	f	Послуги з дослідження забруднень хімікатами та нафтопродуктами
90715110-6	Gasworks site investigation	6	t	Дослідження газодобувних ділянок
90715120-9	Chemical works or oil refinery waste site investigation	6	t	Дослідження звалищ відходів хімічного виробництва чи нафтопереробних заводів
90715200-4	Other pollution investigation services	5	f	Послуги з дослідження інших видів забруднень
90715210-7	Oil depot or terminal site investigation	6	t	Досліджування нафтосховищ або нафтотерміналів
90715220-0	Industrial site investigation	6	t	Дослідження промислових об’єктів
90715230-3	Industrial waste site investigation	6	t	Дослідження звалищ промислових відходів
90715240-6	Wood treatment plant site investigation	6	t	Дослідження деревообробних заводів
90715250-9	Dry cleaning plants site investigation	6	t	Дослідження майстерень хімічного чищення
90715260-2	Foundry site investigation	6	t	Дослідження об’єктів ливарної галузі
90715270-5	Recycling plant site investigation	6	t	Дослідження об’єктів переробної галузі
90715280-8	Food processing plant site investigation	6	t	Дослідження заводів із обробки продуктів харчування
90720000-0	Environmental protection	3	f	Захист довкілля
90721000-7	Environmental safety services	4	f	Послуги із забезпечення екологічної безпеки
90721100-8	Landscape protection services	5	t	Послуги із захисту ландшафтів
90721200-9	Ozone protection services	5	t	Послуги із захисту озонового шару
90721300-0	Food or feed contamination protection services	5	t	Послуги із захисту продуктів харчування та кормів від зараження
90721400-1	Genetic resources protection services	5	t	Послуги із захисту генетичних ресурсів
90721500-2	Toxic substances protection services	5	t	Послуги із захисту токсичних речовин
90721600-3	Radiation protection services	5	t	Послуги з радіаційного захисту
90721700-4	Endangered species protection services	5	t	Послуги із захисту видів, яким загрожує вимирання
90721800-5	Natural risks or hazards protection services	5	t	Послуги із захисту від природних ризиків і небезпек
90722000-4	Environmental rehabilitation	4	f	Відновлення довкілля
90722100-5	Industrial site rehabilitation	5	t	Відновлення промислових територій
90722200-6	Environmental decontamination services	5	t	Послуги зі знезараження об’єктів довкілля
90722300-7	Land reclamation services	5	t	Послуги з меліорації
90730000-3	Pollution tracking and monitoring and rehabilitation	3	f	Відстеження, моніторинг забруднень і відновлення
90731000-0	Services related to air pollution	4	f	Послуги, пов’язані із проблемою забруднення повітря
90731100-1	Air quality management	5	t	Управління якістю повітря
90731200-2	Transboundary air pollution management or control services	5	f	Послуги з управління та контролю у сфері боротьби з транскордонним забрудненням повітря
90731210-5	Purchase of CO2 emission credits	6	t	Придбання квот на викиди діоксиду вуглецю
90731300-3	Air pollution protection services	5	t	Послуги із захисту повітря від забруднення
90731400-4	Air pollution monitoring or measurement services	5	t	Послуги з моніторингу та вимірювання рівня забруднення повітря
90731500-5	Toxic gas detection services	5	t	Послуги з виявлення токсичних газів
90731600-6	Methane monitoring	5	t	Моніторинг концентрації метану
90731700-7	Carbon dioxide monitoring services	5	t	Послуги з моніторингу концентрації діоксиду вуглецю
90731800-8	Airborne particle monitoring	5	t	Моніторинг концентрації твердих частинок у повітрі
90731900-9	Ozone depletion monitoring services	5	t	Послуги з моніторингу товщини озонового шару
90732000-7	Services related to soil pollution	4	f	Послуги, пов’язані із проблемою забруднення ґрунту
90732100-8	Soil pollution protection services	5	t	Послуги із захисту ґрунтів від забруднення
90732200-9	Polluted soil removal services	5	t	Послуги із вилучення забрудненого ґрунту
90732300-0	Polluted soil treatment or rehabilitation	5	t	Обробка та відновлення забрудненого ґрунту
90732400-1	Soil pollution advisory services	5	t	Дорадчі послуги з питань забруднення ґрунту
90732500-2	Soil pollution mapping	5	t	Картографування забруднених ґрунтів
90732600-3	Soil pollution measurement or monitoring	5	t	Вимірювання чи моніторинг рівня забрудненості ґрунтів
90732700-4	Organic fertilizer pollution assessment	5	t	Оцінювання рівня забрудненості ґрунтів органічними добривами
90732800-5	Pesticides pollution assessment	5	t	Оцінювання забруднення пестицидами
90732900-6	Nitrates and phosphates pollution assessment	5	f	Оцінювання забруднення нітратами та фосфатами
90732910-9	Nitrates pollution assessment	6	t	Оцінювання забруднення нітратами
90732920-2	Phosphates pollution assessment	6	t	Оцінювання забруднення фосфатами
90733000-4	Services related to water pollution	4	f	Послуги, пов’язані з проблемою забруднення води
90733100-5	Surface water pollution monitoring or control services	5	t	Послуги з моніторингу чи контролю рівня забруднення поверхневих вод
90733200-6	Surface water pollution rehabilitation services	5	t	Послуги з відновлення у разі забруднення поверхневих вод
90733300-7	Surface water pollution protection services	5	t	Послуги із захисту поверхневих вод від забруднення
90733400-8	Surface water treatment services	5	t	Послуги з обробки поверхневих вод
90733500-9	Surface water pollution drainage services	5	t	Дренажні послуги на випадок забруднення поверхневих вод
90733600-0	Transboundary water pollution management or control services	5	t	Послуги з управління чи контролю у сфері боротьби з транскордонним забрудненням водних ресурсів
90733700-1	Groundwater pollution monitoring or control services	5	t	Послуги з моніторингу чи контролю рівня забруднення підземних вод
90733800-2	Groundwater pollution drainage services	5	t	Послуги з дренажу на випадок забруднення підземних вод
90733900-3	Groundwater pollution treatment or rehabilitation	5	t	Обробка чи відновлення у разі забруднення підземних вод
90740000-6	Pollutants tracking and monitoring and rehabilitation services	3	f	Послуги з відстеження, моніторингу забруднювачів і відновлення
90741000-3	Services related to oil pollution	4	f	Послуги, пов’язані з проблемою нафтових забруднень
90741100-4	Oil spillage monitoring services	5	t	Послуги з моніторингу розливань нафти
90741200-5	Oil spillage control services	5	t	Послуги з контролю розливань нафти
90741300-6	Oil spillage rehabilitation services	5	t	Послуги з відновлення у разі розливання нафти
90742000-0	Services related to noise pollution	4	f	Послуги, пов’язані з проблемою шумового забруднення
90742100-1	Noise control services	5	t	Послуги з контролю рівня зашумлення
90742200-2	Noise pollution protection services	5	t	Послуги із захисту від шумового забруднення
90742300-3	Noise pollution monitoring services	5	t	Послуги з моніторингу рівня шумового забруднення
90742400-4	Noise pollution advisory services	5	t	Дорадчі послуги з питань шумового забруднення
90743000-7	Services related to toxic substances pollution	4	f	Послуги, пов’язані з проблемою забруднення токсичними речовинами
90743100-8	Toxic substances monitoring services	5	t	Послуги з моніторингу концентрації токсичних речовин
90743200-9	Toxic substances rehabilitation services	5	t	Послуги з відновлення у разі забруднення токсичними речовинами
90900000-6	Cleaning and sanitation services	2	f	Послуги з прибирання та санітарно-гігієнічні послуги
90910000-9	Cleaning services	3	f	Послуги з прибирання
90911000-6	Accommodation, building and window cleaning services	4	f	Послуги з прибирання житла, будівель і миття вікон
90911100-7	Accommodation cleaning services	5	t	Послуги з прибирання житла
90911200-8	Building-cleaning services	5	t	Послуги з прибирання будівель
90911300-9	Window-cleaning services	5	t	Послуги з миття вікон
90912000-3	Blast-cleaning services for tubular structures	4	t	Послуги з дробоочищення трубчастих конструкцій
90913000-0	Tank and reservoir cleaning services	4	f	Послуги з очищення цистерн і резервуарів
90913100-1	Tank-cleaning services	5	t	Послуги з очищення цистерн
90913200-2	Reservoir cleaning services	5	t	Послуги з очищення резервуарів
90914000-7	Car park cleaning services	4	t	Послуги з прибирання автостоянок
90915000-4	Furnace and chimney cleaning services	4	t	Послуги з чищення печей і димарів
90916000-1	Cleaning services of telephone equipment	4	t	Послуги з чищення телефонного обладнання
90917000-8	Cleaning services of transport equipment	4	t	Послуги з чищення транспортного обладнання
90918000-5	Bin-cleaning services	4	t	Послуги з очищення урн для сміття
90919000-2	Office, school and office equipment cleaning services	4	f	Послуги з прибирання офісних і шкільних приміщень та чищення офісного обладнання
90919100-3	Cleaning services of office equipment	5	t	Послуги з чищення офісного обладнання
90919200-4	Office cleaning services	5	t	Послуги з прибирання офісних приміщень
90919300-5	School cleaning services	5	t	Послуги з прибирання шкільних приміщень
90920000-2	Facility related sanitation services	3	f	Послуги із санітарно-гігієнічної обробки приміщень
90921000-9	Disinfecting and exterminating services	4	t	Послуги з дезінфікування та витравлювання
90922000-6	Pest-control services	4	t	Послуги з боротьби зі шкідниками
90923000-3	Rat-disinfestation services	4	t	Послуги з дератизації
90924000-0	Fumigation services	4	t	Послуги з фумігації
92000000-1	Recreational, cultural and sporting services	1	f	Послуги у сфері відпочинку, культури та спорту
92100000-2	Motion picture and video services	2	f	Послуги у сфері виробництва кіно- та відеопродукції
92110000-5	Motion picture and video tape production and related services	3	f	Послуги з виробництва кіноплівки та відеокасет і супутні послуги
92111000-2	Motion picture and video production services	4	f	Послуги з виробництва кіно- та відеопродукції
92111100-3	Training-film and video-tape production	5	t	Виробництво навчальних фільмів і навчальних відеоматеріалів
92111200-4	Advertising, propaganda and information film and video-tape production	5	f	Виробництво рекламних, пропагандистських та інформаційних фільмів і відеоматеріалів
92111210-7	Advertising film production	6	t	Виробництво рекламних фільмів
92111220-0	Advertising video-tape production	6	t	Виробництво рекламних відеоматеріалів
92111230-3	Propaganda film production	6	t	Виробництво пропагандистських фільмів
92111240-6	Propaganda video-tape production	6	t	Виробництво пропагандистських відеоматеріалів
92111250-9	Information film production	6	t	Виробництво інформаційних фільмів
92111260-2	Information video-tape production	6	t	Виробництво інформаційних відеоматеріалів
92111300-5	Entertainment film and video-tape production	5	f	Виробництво розважальних фільмів і відеоматеріалів
92111310-8	Entertainment film production	6	t	Виробництво розважальних фільмів
92111320-1	Entertainment video-tape production	6	t	Виробництво розважальних відеоматеріалів
92112000-9	Services in connection with motion-picture and video-tape production	4	t	Послуги, пов’язані з виробництвом кіно- та відеопродукції
92120000-8	Motion-picture or video-tape distribution services	3	f	Послуги з розповсюдження кіно- та відеопродукції
92121000-5	Video-tape distribution services	4	t	Послуги з розповсюдження відеопродукції
92122000-2	Motion picture distribution services	4	t	Послуги з розповсюдження кінопродукції
92130000-1	Motion picture projection services	3	t	Послуги з показу кінопродукції
92140000-4	Video-tape projection services	3	t	Послуги з показу відеопродукції
92200000-3	Radio and television services	2	f	Послуги теле- та радіомовлення
92210000-6	Radio services	3	f	Послуги радіомовлення
92211000-3	Radio production services	4	t	Послуги радіовиробництва
92213000-7	Small scale radio systems services	4	t	Послуги радіосистем малого радіусу дії
92214000-4	Radio studio or equipment services	4	t	Послуги радіостудій або у сфері радіоустаткування
92215000-1	General Mobile Radio Services (GMRS)	4	t	Послуги загальної пересувної радіослужби (GMRS)
92216000-8	Family Radio Services (FRS)	4	t	Послуги сімейного радіо (FRS)
92217000-5	General Mobile Radio Services/Family Radio Services (GMRS/FRS)	4	t	Послуги загальної пересувної радіослужби / Послуги сімейного радіо (GMRS/FRS)
92220000-9	Television services	3	f	Телевізійні послуги
92221000-6	Television production services	4	t	Послуги телевізійного виробництва
92222000-3	Closed circuit television services	4	t	Послуги замкнутих телевізійних систем
92224000-7	Digital television	4	t	Послуги цифрового телебачення
92225000-4	Interactive television	4	f	Послуги інтерактивного телебачення
92225100-7	Film-on-demand television	5	t	Послуги відео на замовлення
92226000-1	Teleprogrammation	4	t	Телепрограмування
92230000-2	Radio and television cable services	3	f	Послуги кабельних мереж теле- та радіомовлення
92231000-9	International bilateral services and international private leased lines	4	t	Міжнародні двосторонні послуги та послуги міжнародних приватних виділених ліній
92232000-6	Cable TV	4	t	Послуги кабельного телебачення
92300000-4	Entertainment services	2	f	Розважальні послуги
92310000-7	Artistic and literary creation and interpretation services	3	f	Послуги зі створювання та інтерпретування мистецьких і літературних творів
92311000-4	Works of art	4	t	Витвори мистецтва
92312000-1	Artistic services	4	f	Мистецькі послуги
92312100-2	Theatrical producers', singer groups', bands' and orchestras' entertainment services	5	f	Розважальні послуги театральних продюсерів, співочих і музичних гуртів та оркестрів
92312110-5	Theatrical producer entertainment services	6	t	Розважальні послуги театральних продюсерів
92312120-8	Singer group entertainment services	6	t	Розважальні послуги співочих гуртів
92312130-1	Band entertainment services	6	t	Розважальні послуги музичних гуртів
92312140-4	Orchestral entertainment services	6	t	Розважальні послуги оркестрів
92312200-3	Services provided by authors, composers, sculptors, entertainers and other individual artists	5	f	Послуги письменників, композиторів, скульпторів, естрадних та інших окремих артистів
92312210-6	Services provided by authors	6	f	Послуги письменників
92312211-3	Writing agency services	7	t	Послуги копірайтингових агентств
92312212-0	Services related to the preparation of training manuals	7	t	Послуги, пов’язані з розробкою навчальних посібників
92312213-7	Technical author services	7	t	Послуги технічних авторів
92312220-9	Services provided by composers	6	t	Послуги композиторів
92312230-2	Services provided by sculptors	6	t	Послуги скульпторів
92312240-5	Services provided by entertainers	6	t	Послуги естрадних артистів
92312250-8	Services provided by individual artists	6	f	Послуги окремих артистів
92312251-5	Disk-jockey services	7	t	Послуги ді-джеїв
92320000-0	Arts-facility operation services	3	t	Послуги з утримання закладів мистецтва
92330000-3	Recreational-area services	3	f	Послуги відпочивально-розважальних комплексів
92331000-0	Fair and amusement park services	4	f	Послуги ярмарок і парків розваг
92331100-1	Fair services	5	t	Послуги ярмарок
92331200-2	Amusement park services	5	f	Послуги парків розваг
92331210-5	Children animation services	6	t	Послуги анімації для дітей
92332000-7	Beach services	4	t	Пляжні послуги
92340000-6	Dance and performance entertainment services	3	f	Розважальні послуги, пов’язані з танцями та шоу
92341000-3	Circus services	4	t	Послуги цирків
92342000-0	Dance-instruction services	4	f	Послуги з навчання танцям
92342100-1	Ballroom dance-instruction services	5	t	Послуги з навчання бальним танцям
92342200-2	Discotheque dance-instruction services	5	t	Послуги з навчання клубним танцям
92350000-9	Gambling and betting services	3	f	Послуги гральних закладів і тоталізаторів
92351000-6	Gambling services	4	f	Послуги гральних закладів
92351100-7	Lottery operating services	5	t	Послуги з утримання лотерейних закладів
92351200-8	Casino operating services	5	t	Послуги з утримання казино
92352000-3	Betting services	4	f	Послуги тоталізаторів
92352100-4	Totalisator operating services	5	t	Послуги з утримання тоталізаторів
92352200-5	Bookmaking services	5	t	Букмекерські послуги
92360000-2	Pyrotechnic services	3	t	Послуги піротехніків
92370000-5	Sound technician	3	t	Послуги звукооператорів
92400000-5	News-agency services	2	t	Послуги інформаційних агентств
92500000-6	Library, archives, museums and other cultural services	2	f	Послуги бібліотек, архівів, музеїв та інших закладів культури
92510000-9	Library and archive services	3	f	Послуги бібліотек і архівів
92511000-6	Library services	4	t	Послуги бібліотек
92512000-3	Archive services	4	f	Послуги архівів
92512100-4	Archive destruction services	5	t	Послуги з утилізації архівів
92520000-2	Museum services and preservation services of historical sites and buildings	3	f	Послуги музеїв та послуги зі збереження історичних пам’яток і будівель
92521000-9	Museum services	4	f	Послуги музеїв
92521100-0	Museum-exhibition services	5	t	Послуги у сфері музейних виставок
92521200-1	Preservation services of exhibits and specimens	5	f	Послуги зі збереження експонатів і зразків
92521210-4	Preservation services of exhibits	6	t	Послуги зі збереження експонатів
92521220-7	Preservation services of specimens	6	t	Послуги зі збереження зразків
92522000-6	Preservation services of historical sites and buildings	4	f	Послуги зі збереження історичних пам’яток і будівель
92522100-7	Preservation services of historical sites	5	t	Послуги зі збереження історичних пам’яток
92522200-8	Preservation services of historical buildings	5	t	Послуги зі збереження історичних будівель
92530000-5	Botanical and zoological garden services and nature reserve services	3	f	Послуги ботанічних та зоологічних садів і природних заповідників
92531000-2	Botanical garden services	4	t	Послуги ботанічних садів
92532000-9	Zoological garden services	4	t	Послуги зоологічних садів
92533000-6	Nature reserve services	4	t	Послуги природних заповідників
92534000-3	Wildlife preservation services	4	t	Послуги зі збереження дикої природи
92600000-7	Sporting services	2	f	Послуги у сфері спорту
92610000-0	Sports facilities operation services	3	t	Послуги з утримання спортивних закладів
92620000-3	Sport-related services	3	f	Послуги, пов’язані зі спортом
92621000-0	Sports-event promotion services	4	t	Послуги з популяризації спортивних заходів
92622000-7	Sports-event organisation services	4	t	Послуги з організації спортивних заходів
92700000-8	Cybercafé services	2	t	Послуги Інтернет-кафе
98000000-3	Other community, social and personal services	1	f	Інші громадські, соціальні та особисті послуги
98100000-4	Membership organisation services	2	f	Послуги членських організацій
98110000-7	Services furnished by business, professional and specialist organisations	3	f	Послуги підприємницьких, професійних та спеціалізованих організацій
98111000-4	Services furnished by business organisations	4	t	Послуги підприємницьких організацій
98112000-1	Services furnished by professional organisations	4	t	Послуги професійних організацій
98113000-8	Services furnished by specialist organisations	4	f	Послуги спеціалізованих організацій
98113100-9	Nuclear safety services	5	t	Послуги у сфері ядерної безпеки
98120000-0	Services furnished by trade unions	3	t	Послуги професійних спілок
98130000-3	Miscellaneous membership organisations services	3	f	Послуги різних членських організацій
98131000-0	Religious services	4	t	Релігійні послуги
98132000-7	Services furnished by political organisations	4	t	Послуги політичних організацій
98133000-4	Services furnished by social membership organisations	4	f	Послуги соціальних членських організацій
98133100-5	Civic betterment and community facility support services	5	f	Послуги з підтримки розвитку громадськості та громадських ресурсів
98133110-8	Services provided by youth associations	6	t	Послуги молодіжних об’єднань
98200000-5	Equal opportunities consultancy services	2	t	Консультаційні послуги з питань забезпечення рівних можливостей
98300000-6	Miscellaneous services	2	f	Послуги різні
98310000-9	Washing and dry-cleaning services	3	f	Послуги з прання і сухого чищення
98311000-6	Laundry-collection services	4	f	Послуги зі збирання білизни для прання
98311100-7	Laundry-management services	5	t	Послуги з управління пральнями
98311200-8	Laundry-operation services	5	t	Послуги з утримання пралень
98312000-3	Textile-cleaning services	4	f	Послуги з чищення текстильних виробів
98312100-4	Textile-impregnation services	5	t	Послуги з просочування текстильних матеріалів
98313000-0	Fur-products cleaning services	4	t	Послуги з чищення хутрових виробів
98314000-7	Colouring services	4	t	Послуги з офарбовування
98315000-4	Pressing services	4	t	Послуги з прасування
98316000-1	Dyeing services	4	t	Послуги з фарбування
98320000-2	Hairdressing and beauty treatment services	3	f	Послуги перукарень і салонів краси
98321000-9	Hairdressing services	4	f	Послуги жіночого перукаря
98321100-0	Barbers' services	5	t	Послуги чоловічого перукаря
98322000-6	Beauty treatment services	4	f	Послуги салонів краси
98322100-7	Cosmetic treatment, manicuring and pedicuring services	5	f	Косметичні, манікюрні та педикюрні послуги
98322110-0	Cosmetic treatment services	6	t	Косметичні послуги
98322120-3	Manicuring services	6	t	Манікюрні послуги
98322130-6	Pedicuring services	6	t	Педикюрні послуги
98322140-9	Make-up services	6	t	Послуги візажиста
98330000-5	Physical well-being services	3	f	Послуги у сфері фітнесу та догляду за тілом
98331000-2	Turkish bath services	4	t	Послуги турецьких лазень
98332000-9	Spa services	4	t	Спа-послуги
98333000-6	Massage services	4	t	Масажні послуги
98334000-3	Wellness services	4	t	Велнес-послуги
98336000-7	Training, workout or aerobic services	4	t	Послуги у сфері спортивних тренувань, фізичних вправ та аеробіки
98340000-8	Accommodation and office services	3	f	Послуги з тимчасового розміщення (проживання) та офісні послуги
98341000-5	Accommodation services	4	f	Послуги з тимчасового розміщення (проживання)
98341100-6	Accommodation management services	5	f	Управлінські послуги у сфері тимчасового розміщення (проживання)
98341110-9	Housekeeping services	6	t	Послуги з ведення домашнього господарства
98341120-2	Portering services	6	t	Послуги консьєржів
98341130-5	Janitorial services	6	t	Послуги з прибирання
98341140-8	Caretaker services	6	t	Послуги з доглядання за будинками
98342000-2	Work environment services	4	t	Послуги у сфері організації робочого середовища
98350000-1	Civic-amenity services	3	f	Послуги громадських пунктів збору та переробки сміття
98351000-8	Car park management services	4	f	Послуги з управління автостоянками
98351100-9	Car park services	5	f	Послуги автостоянок
98351110-2	Parking enforcement services	6	t	Послуги з контролю оплати послуг автостоянок
98360000-4	Marine services	3	f	Послуги у сфері морських перевезень
98361000-1	Aquatic marine services	4	t	Послуги, пов’язані з використанням морської акваторії
98362000-8	Port management services	4	f	Послуги з управління портами
98362100-9	Marine-base support services	5	t	Послуги з обслуговування морських баз
98363000-5	Diving services	4	t	Послуги водолазів
98370000-7	Funeral and related services	3	f	Поховальні та супутні послуги
98371000-4	Funeral services	4	f	Поховальні послуги
98371100-5	Cemetery services and cremation services	5	f	Послуги кладовищ і крематоріїв
98371110-8	Cemetery services	6	f	Послуги кладовищ
98371111-5	Cemetery maintenance services	7	t	Послуги з обслуговування кладовищ
98371120-1	Cremation services	6	t	Послуги крематоріїв
98371200-6	Undertaking services	5	t	Ритуальні послуги
98380000-0	Dog kennel services	3	t	Послуги собачих розплідників
98390000-3	Other services	3	f	Інші послуги
98391000-0	Decommissioning services	4	t	Послуги виведення з експлуатації
98392000-7	Relocation services	4	t	Послуги з передислокування
98393000-4	Tailoring services	4	t	Кравецькі послуги
98394000-1	Upholstering services	4	t	Послуги з оббивання
98395000-8	Locksmith services	4	t	Слюсарські послуги
98396000-5	Instrument tuning services	4	t	Послуги з настроювання музичних інструментів
98500000-8	Private households with employed persons	2	f	Послуги приватних домогосподарств із найманим працівниками
98510000-1	Services of commercial and industrial workers	3	f	Послуги працівників у сфері торгівлі та промисловості
98511000-8	Services of commercial workers	4	t	Послуги працівників у сфері торгівлі
98512000-5	Services of industrial workers	4	t	Послуги працівників у сфері промисловості
98513000-2	Manpower services for households	4	f	Послуги із забезпечення домогосподарств робочою силою
98513100-3	Agency staff services for households	5	t	Послуги агентств з підбору персоналу для домогосподарств
98513200-4	Clerical staff services for households	5	t	Послуги конторського персоналу для домогосподарств
98513300-5	Temporary staff for households	5	f	Послуги тимчасового персоналу для домогосподарств
98513310-8	Home-help services	6	t	Послуги хатніх робітниць
98514000-9	Domestic services	4	t	Побутові послуги
98900000-2	Services provided by extra-territorial organisations and bodies	2	f	Послуги іноземних організацій та органів
98910000-5	Services specific to international organisations and bodies	3	t	Послуги міжнародних організацій та органів
\.

\echo 'All done!'

--
-- PostgreSQL database dump complete
--
