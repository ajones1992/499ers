INSERT INTO Person (Person_ID, Person_Name, Phone, Address, Designation) VALUES
(1, 'John Doe', '334-539-7260', '123 Main St', 'ADOPTEE'),
(2, 'Robert Nelson', '741-539-6108', '456 Elm St', 'FOSTER_STAFF'),
(3, 'Antonio Jones', '736-896-4759', '789 Oak St', 'SHELTER_STAFF'),
(4, 'Herbert Thomas', '350-809-3690', '012 Pine St', 'ADOPTEE'),
(5, 'Charles Johnson', '712-322-7899', '345 Birch St', 'SHELTER_STAFF');


INSERT INTO Location (Location_ID, Location_Name, Location_Type, Address, Capacity) VALUES
(1, 'Main Shelter', 'SHELTER', '101 Shelter Rd', 100),
(2, 'Foster Home #1', 'FOSTER_HOME', '202 Foster Lane', 5),
(3, 'Foster Home #2', 'FOSTER_HOME', '303 Foster Ave', 4);


INSERT INTO Animal (Animal_ID, Animal_Name, Animal_Type, Weight, DOB, Received_Date, Exit_Date, Exit_Code, Location_ID) VALUES
(101, 'Max', 'DOG', 70.5, '2020-03-01', '2024-03-01', '2024-03-15', 'ADOPT', 1),
(102, 'Bella', 'CAT', 10.2, '2020-03-01', '2024-03-05', NULL, NULL, 1),
(103, 'Charlie', 'DOG', 24.7, '2020-03-01', '2024-03-07', NULL, NULL, 2),
(104, 'Luna', 'CAT', 8.6, '2020-03-01', '2024-03-10', NULL, 'IN_TRANSIT', 2),
(105, 'Lucy', 'DOG', 65.0, '2020-03-01', '2024-03-11', NULL, NULL, 3);


INSERT INTO Record (Record_ID, Animal_ID, Update_Date, Record_Type, Details) VALUES
(1, 101, '2024-03-15', 'MEDICAL', 'Healthy, ready for adoption'),
(2, 102, '2024-03-06', 'BEHAVIORAL', 'Under observation, diet'),
(3, 103, '2024-03-08', 'OTHER', 'Training needed, nervous'),
(4, 104, '2024-03-11', 'MEDICAL', 'Medication required, stable'),
(5, 105, '2024-03-12', 'BEHAVIORAL', 'Energetic, needs space');


INSERT INTO Species_Available (Location_ID, Species_Type) VALUES
(1, 'DOG'),
(1, 'CAT'),
(2, 'CAT'),
(2, 'DOG'),
(3, 'DOG');