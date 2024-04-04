INSERT INTO Location (Location_ID, Location_Name, Location_Type, Address, Capacity) VALUES
(101, 'Main Shelter', 'SHELTER', '101 Shelter Rd', 100),
(102, 'Foster Home #1', 'FOSTER_HOME', '202 Foster Lane', 5),
(103, 'Foster Home #2', 'FOSTER_HOME', '303 Foster Ave', 4);


INSERT INTO Animal (Animal_ID, Animal_Name, Animal_Type, Weight, DOB, Received_Date, Exit_Date, Exit_Code, Location_ID) VALUES
(101, 'Max', 'DOG', 70.5, '2020-03-01', '2024-03-01', '2024-03-15', 'ADOPT', 101),
(102, 'Bella', 'CAT', 10.2, '2020-03-01', '2024-03-05', NULL, NULL, 101),
(103, 'Charlie', 'DOG', 24.7, '2020-03-01', '2024-03-07', NULL, NULL, 102),
(104, 'Luna', 'CAT', 8.6, '2020-03-01', '2024-03-10', NULL, 'IN_TRANSIT', 102),
(105, 'Lucy', 'DOG', 65.0, '2020-03-01', '2024-03-11', NULL, NULL, 103);


INSERT INTO Record (Record_ID, Animal_ID, Update_Date, Record_Type, Details) VALUES
(101, 101, '2024-03-15', 'MEDICAL', 'Healthy, ready for adoption'),
(102, 102, '2024-03-06', 'BEHAVIORAL', 'Under observation, diet'),
(103, 103, '2024-03-08', 'OTHER', 'Training needed, nervous'),
(104, 104, '2024-03-11', 'MEDICAL', 'Medication required, stable'),
(105, 105, '2024-03-12', 'BEHAVIORAL', 'Energetic, needs space');


INSERT INTO Species_Available (Location_ID, Species_Type) VALUES
(101, 'DOG'),
(101, 'CAT'),
(102, 'CAT'),
(102, 'DOG'),
(103, 'DOG');