-- Runs after db_clean.sql in the integration-test container.
-- The admin the tests log in as (LoginData); DatabaseCleaner keeps this row between tests.
-- salt must not be NULL: Employee maps it to a primitive long.
INSERT INTO promasy.employees (id, active, version, emp_fname, emp_lname, login, role, salt)
VALUES (-1, true, 0, 'Integration', 'Admin', 'it-admin', 'ADMIN', 0);
