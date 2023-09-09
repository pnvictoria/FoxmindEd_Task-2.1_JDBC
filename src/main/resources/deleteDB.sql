UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'university';
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'university';
DROP DATABASE school;
DROP USER user1;