-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER party_owner
WITH PASSWORD 'p@rtyDrinkUp';

GRANT ALL
ON ALL TABLES IN SCHEMA public
TO party_owner;

GRANT ALL
ON ALL SEQUENCES IN SCHEMA public
TO party_owner;

CREATE USER party_appuser
WITH PASSWORD 'p@rtyDrinkUp';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO party_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO party_appuser;
