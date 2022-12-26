drop schema insertSimulator;
CREATE SCHEMA insertSimulator;
USE insertSimulator;

CREATE TABLE MainTable (
ID Integer NOT NULL,
Name VARCHAR(200) NOT NULL,
PRIMARY KEY (ID)
);

CREATE TABLE SubTable0 (
EventID Integer NOT NULL,
EvDate DATE NOT NULL,
PRIMARY KEY (EventID),
FOREIGN KEY (EventID) REFERENCES MainTable(ID)
);

CREATE TABLE SubTable1 (
EventID Integer NOT NULL,
Surname VARCHAR(100) NOT NULL,
PRIMARY KEY (EventID),
FOREIGN KEY (EventID) REFERENCES MainTable(ID)
);

CREATE TABLE SubTable2 (
EventID Integer NOT NULL,
PRIMARY KEY (EventID),
FOREIGN KEY (EventID) REFERENCES MainTable(ID)
);