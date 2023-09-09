UPDATE pg_database SET datallowconn = 'false' WHERE datname = 'university_test';
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'university_test';
DROP DATABASE school;
DROP USER user1;