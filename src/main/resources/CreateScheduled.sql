CREATE TABLE SCHEDULED
(
ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
DESCRIPTION VARCHAR(1000) NOT NULL,
START_DATE BIGINT NOT NULL,
END_DATE BIGINT,
NOTIFICATION_DELIVERED BOOLEAN,
PRIMARY KEY(ID),
UNIQUE (START_DATE)
)