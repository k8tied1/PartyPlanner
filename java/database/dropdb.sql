-- **************************************************************
-- This script destroys the database and associated users
-- **************************************************************

-- The following line terminates any active connections to the database so that it can be destroyed
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'party_planner';

DROP DATABASE party_planner;

DROP USER party_owner;
DROP USER party_appuser;
