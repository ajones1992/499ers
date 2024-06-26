CREATE TABLE IF NOT EXISTS Location(
  Location_ID INT PRIMARY KEY,
  Location_Name VARCHAR(64) NOT NULL,
  Location_Type VARCHAR(16) NOT NULL,
  Address VARCHAR(100) NOT NULL,
  Capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Animal(
  Animal_ID INT PRIMARY KEY,
  Animal_Name VARCHAR(64) NOT NULL,
  Animal_Type VARCHAR(16) NOT NULL,
  Weight DOUBLE NOT NULL,
  DOB Date NOT NULL,
  Received_Date DATE NOT NULL,
  Exit_Date DATE,
  Exit_Code VARCHAR(16),
  Location_ID INT,
  foreign key (Location_ID) References Location(Location_ID)
);

CREATE TABLE IF NOT EXISTS Record(
  Record_ID INT PRIMARY KEY,
  Animal_ID INT NOT NULL,
  Update_Date DATE NOT NULL,
  Record_Type VARCHAR(16) NOT NULL,
  Details VARCHAR(100),
  foreign key (Animal_ID) References Animal(Animal_ID)
);

CREATE TABLE IF NOT EXISTS Species_Available(
  Location_ID INT NOT NULL,
  Species_Type VARCHAR(16) NOT NULL,
  primary key (Location_ID, Species_Type),
  foreign key (Location_ID) References Location(Location_ID)
);
