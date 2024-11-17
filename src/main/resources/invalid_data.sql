-- Ali Hassan
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.5, '2023-01-15T10:15:00.000', 'b23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (29, 12345671, 'Lahore, Pakistan', 'ali.hassan@example.com', 'Ali Hassan', '0321-1234567', 'b23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 6, NULL, 'b23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'b23e4567-e89b-12d3-a456-426614174000'),
    (2, uuid_generate_v4(), 'b23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Welding Certification', 'edu1-b23e4567', 'b23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '07f51764-74dd-49f6-a654-b8f8a842ea15', 'b23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '72259e37-eb8a-4777-ac16-770bce656354', 'b23e4567-e89b-12d3-a456-426614174000');

-- Fatima Khan
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.8, '2023-03-20T11:20:00.000', 'c23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (31, 12345672, 'Islamabad, Pakistan', 'fatima.khan@example.com', 'Fatima Khan', '0345-2345678', 'c23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 10, NULL, 'c23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'c23e4567-e89b-12d3-a456-426614174000'),
    (1, uuid_generate_v4(), 'c23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Graphic Design Degree', 'edu1-c23e4567', 'c23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '99f4c90a-e9db-46b4-8d74-5e653cb97139', 'c23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '0bdfc0be-4128-4f83-b76a-d0de48604841', 'c23e4567-e89b-12d3-a456-426614174000');

-- Usman Ahmed
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.6, '2023-06-10T09:30:00.000', 'd23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (28, 12345673, 'Karachi, Pakistan', 'usman.ahmed@example.com', 'Usman Ahmed', '0300-3456789', 'd23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 8, NULL, 'd23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (2, uuid_generate_v4(), 'd23e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), 'd23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Automotive Engineering Diploma', 'edu1-d23e4567', 'd23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '4d85980d-9338-4c90-a104-63ff30bf43dc', 'd23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'f9df3847-9239-4ceb-9778-18f91f6facd3', 'd23e4567-e89b-12d3-a456-426614174000');

-- Sara Malik
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.7, '2023-07-25T12:00:00.000', 'e23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (35, 12345674, 'Faisalabad, Pakistan', 'sara.malik@example.com', 'Sara Malik', '0311-4567890', 'e23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 12, NULL, 'e23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (1, uuid_generate_v4(), 'e23e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), 'e23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Culinary Arts Degree', 'edu1-e23e4567', 'e23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '252c2006-055a-4903-a711-4891742325c5', 'e23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'd537d537-d802-4ebc-baf7-d467d90ede33', 'e23e4567-e89b-12d3-a456-426614174000');

-- Hassan Raza
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.3, '2023-09-30T14:45:00.000', 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (26, 12345675, 'Multan, Pakistan', 'hassan.raza@example.com', 'Hassan Raza', '0322-5678901', 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 5, NULL, 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'f23e4567-e89b-12d3-a456-426614174000'),
    (2, uuid_generate_v4(), 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('IT Support Certification', 'edu1-f23e4567', 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '99f4c90a-e9db-46b4-8d74-5e653cb97139', 'f23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'a7eb5203-4d4a-4dc4-9460-4f15d5391531', 'f23e4567-e89b-12d3-a456-426614174000');

-- Worker 11: Ahmed Khan
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.6, '2023-04-20T10:30:00.000', 'b33e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (29, 12345670, 'Islamabad, Pakistan', 'ahmed.khan@example.com', 'Ahmed Khan', '0311-1234567', 'b33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 6, NULL, 'b33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'b33e4567-e89b-12d3-a456-426614174000'),
    (1, uuid_generate_v4(), 'b33e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), 'b33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Construction Management Diploma', 'edu1-b33e4567', 'b33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '93a46ecd-25e9-427b-ae87-13d373c0601c', 'b33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '8266961c-559c-471d-86b0-4c374f9707e6', 'b33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '07f51764-74dd-49f6-a654-b8f8a842ea15', 'b33e4567-e89b-12d3-a456-426614174000');

-- Worker 12: Bilal Ahmed
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.8, '2022-11-15T08:15:00.000', 'c33e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (31, 67890123, 'Karachi, Pakistan', 'bilal.ahmed@example.com', 'Bilal Ahmed', '0341-2345678', 'c33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 8, NULL, 'c33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (2, uuid_generate_v4(), 'c33e4567-e89b-12d3-a456-426614174000'),
    (4, uuid_generate_v4(), 'c33e4567-e89b-12d3-a456-426614174000'),
    (1, uuid_generate_v4(), 'c33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Welding and Fabrication Certificate', 'edu1-c33e4567', 'c33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '4d85980d-9338-4c90-a104-63ff30bf43dc', 'c33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'bb59b08c-cbac-4c50-a66f-959bda084c38', 'c33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '0bdfc0be-4128-4f83-b76a-d0de48604841', 'c33e4567-e89b-12d3-a456-426614174000');

-- Worker 13: Farhan Yousaf
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.4, '2023-01-11T13:45:00.000', 'd33e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (28, 12345671, 'Lahore, Pakistan', 'farhan.yousaf@example.com', 'Farhan Yousaf', '0304-3456789', 'd33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 5, NULL, 'd33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (2, uuid_generate_v4(), 'd33e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), 'd33e4567-e89b-12d3-a456-426614174000'),
    (4, uuid_generate_v4(), 'd33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Carpentry Skills Training', 'edu1-d33e4567', 'd33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '5a229278-f665-4edb-8fd4-17d275e9ed42', 'd33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '87883f6e-d657-4fcc-bec3-585c1ec9ea00', 'd33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'ef9d2ba7-6ca6-487f-ae89-ac9a379521bc', 'd33e4567-e89b-12d3-a456-426614174000');

-- Worker 14: Kashif Malik
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.7, '2022-06-30T09:00:00.000', 'e33e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (35, 23456789, 'Faisalabad, Pakistan', 'kashif.malik@example.com', 'Kashif Malik', '0312-5678901', 'e33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 10, NULL, 'e33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'e33e4567-e89b-12d3-a456-426614174000'),
    (2, uuid_generate_v4(), 'e33e4567-e89b-12d3-a456-426614174000'),
    (1, uuid_generate_v4(), 'e33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Diploma in Electrical Technology', 'edu1-e33e4567', 'e33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'f32d5625-1f5c-4c20-b319-feb843109805', 'e33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'a7eb5203-4d4a-4dc4-9460-4f15d5391531', 'e33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '252c2006-055a-4903-a711-4891742325c5', 'e33e4567-e89b-12d3-a456-426614174000');

-- Worker 15: Omar Javed
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.5, '2023-07-22T14:30:00.000', 'f33e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (32, 90123456, 'Multan, Pakistan', 'omar.javed@example.com', 'Omar Javed', '0335-6789012', 'f33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 7, NULL, 'f33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (4, uuid_generate_v4(), 'f33e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), 'f33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Bachelor in Mechanical Engineering', 'edu1-f33e4567', 'f33e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'fbd94ed5-cdba-40df-8ccf-89579380ecae', 'f33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'bb59b08c-cbac-4c50-a66f-959bda084c38', 'f33e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'f9df3847-9239-4ceb-9778-18f91f6facd3', 'f33e4567-e89b-12d3-a456-426614174000');

-- Worker 11: Aamir Khan
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.6, '2023-09-10T10:15:00.000', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (29, 12345681, 'Islamabad, Pakistan', 'aamir.khan@example.com', 'Aamir Khan', '0341-2345678', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 7, NULL, 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c'),
    (3, uuid_generate_v4(), 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c'),
    (4, uuid_generate_v4(), 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Mechanical Engineering Degree', 'edu1-f1e32b67', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'ef9d2ba7-6ca6-487f-ae89-ac9a379521bc', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c'),
    (uuid_generate_v4(), '89ec4955-c486-4bda-8f3e-2db40b530e74', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c'),
    (uuid_generate_v4(), 'c70496f4-1316-4cf4-9a14-1b6402bc6b01', 'f1e32b67-bf2a-49a0-93ef-4845c2d7d28c');

-- Worker 12: Zain Ali
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.8, '2023-06-25T12:30:00.000', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (32, 12345682, 'Lahore, Pakistan', 'zain.ali@example.com', 'Zain Ali', '0334-3456789', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 10, NULL, 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (4, uuid_generate_v4(), 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176'),
    (3, uuid_generate_v4(), 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176'),
    (0, uuid_generate_v4(), 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Information Technology Degree', 'edu1-d7f1c7c8', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'b67c3963-d860-46ff-b5bb-d01191758269', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176'),
    (uuid_generate_v4(), 'f9df3847-9239-4ceb-9778-18f91f6facd3', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176'),
    (uuid_generate_v4(), '00eedb76-b1d7-46e5-ade4-a7a0bc846c1d', 'd7f1c7c8-b9f3-46c2-8b85-2f97a4f24176');

-- Worker 13: Usman Saeed
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.4, '2023-03-15T11:45:00.000', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (36, 12345683, 'Karachi, Pakistan', 'usman.saeed@example.com', 'Usman Saeed', '0322-4567890', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 12, NULL, 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (4, uuid_generate_v4(), 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f'),
    (3, uuid_generate_v4(), 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f'),
    (2, uuid_generate_v4(), 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Civil Engineering Degree', 'edu1-e8c9f0e3', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'ef9d2ba7-6ca6-487f-ae89-ac9a379521bc', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f'),
    (uuid_generate_v4(), '89ec4955-c486-4bda-8f3e-2db40b530e74', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f'),
    (uuid_generate_v4(), '4d85980d-9338-4c90-a104-63ff30bf43dc', 'e8c9f0e3-62c1-46bc-b73a-f8a229f7717f');
