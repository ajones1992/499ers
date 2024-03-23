INSERT INTO Person (Person_ID, Person_Name, Phone, Address, Designation) VALUES
(1, 'John Doe', '334-539-7260', '123 Main St', 'Adoptee'),
(2, 'Robert Nelson', '741-539-6108', '456 Elm St', 'FosterStaff'),
(3, 'Antonio Jones', '736-896-4759', '789 Oak St', 'ShelterStaff'),
(4, 'Herbert Thomas', '350-809-3690', '012 Pine St', 'Adoptee'),
(5, 'Charles Johnson', '712-322-7899', '345 Birch St', 'ShelterStaff');


INSERT INTO Location (Location_ID, Location_Name, Location_Type, Address, Capacity) VALUES
(1, 'Main Shelter', 'Shelter', '101 Shelter Rd', 100),
(2, 'Foster Home #1', 'FosterHome', '202 Foster Lane', 5),
(3, 'Foster Home #2', 'FosterHome', '303 Foster Ave', 4);


INSERT INTO Animal (Animal_ID, Animal_Name, Animal_Type, Weight, Received_Date, Exit_Date, Exit_Code, Location_ID) VALUES
(101, 'Max', 'Dog', 70.5, '2024-03-01', '2024-03-15', 'Adopted', 1),
(102, 'Bella', 'Cat', 10.2, '2024-03-05', NULL, NULL, 1),
(103, 'Charlie', 'Dog', 24.7, '2024-03-07', NULL, NULL, 2),
(104, 'Luna', 'Cat', 8.6, '2024-03-10', NULL, 'Transferred', 2),
(105, 'Lucy', 'Dog', 65.0, '2024-03-11', NULL, NULL, 3);


INSERT INTO Record (Record_ID, Animal_ID, Employee_ID, Update_Date, Record_Type, Details) VALUES
(1, 101, 2, '2024-03-15', 'Checkup', 'Healthy, ready for adoption'),
(2, 102, 5, '2024-03-06', 'Status', 'Under observation, diet'),
(3, 103, 2, '2024-03-08', 'Status', 'Training needed, nervous'),
(4, 104, 5, '2024-03-11', 'Checkup', 'Medication required, stable'),
(5, 105, 2, '2024-03-12', 'Status', 'Energetic, needs space');


INSERT INTO Species_Available (Location_ID, Species_Type) VALUES
(1, 'Dog'),
(1, 'Cat'),
(2, 'Cat'),
(2, 'Dog'),
(3, 'Dog');