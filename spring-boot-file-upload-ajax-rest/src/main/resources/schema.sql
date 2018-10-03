CREATE SCHEMA IF NOT EXISTS POSTGRES;

CREATE TABLE IF NOT EXISTS POSTGRES.FILE_MODEL_TABLE (
  name          varchar(100),
	date_duration timestamp,
	token         varchar(100),
	id            serial not null constraint file_manager_pk primary key);

ALTER TABLE POSTGRES.FILE_MODEL_TABLE
  owner to POSTGRES;

GRANT ALL PRIVILEGES ON TABLE POSTGRES.FILE_MODEL_TABLE TO POSTGRES;