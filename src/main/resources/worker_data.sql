-- Worker 1: Muhammad Ali
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.5, '2022-01-15T10:45:00.000', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (30, 12345671, 'Karachi, Pakistan', 'muhammad.ali@example.com', 'Muhammad Ali', '0300-1234567', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 8, NULL, '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES (0, uuid_generate_v4(), '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Diploma in Carpentry', 'edu1-123e4567', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '00eedb76-b1d7-46e5-ade4-a7a0bc846c1d', '123e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '036677ca-97fc-445d-81d0-a6b6c0304d8a', '123e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '07f51764-74dd-49f6-a654-b8f8a842ea15', '123e4567-e89b-12d3-a456-426614174000');

-- Worker 2: Ahmed Khan
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.8, '2021-06-20T14:30:00.000', '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (28, 12345678, 'Lahore, Pakistan', 'ahmed.khan@example.com', 'Ahmed Khan', '0312-2345678', '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 6, NULL, '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES (1, uuid_generate_v4(), '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Certificate in Electrical Engineering', 'edu1-223e4567', '223e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '0bdfc0be-4128-4f83-b76a-d0de48604841', '223e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'f32d5625-1f5c-4c20-b319-feb843109805', '223e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '5dfdb64c-39f1-4dd8-9432-cdbf33d09fbd', '223e4567-e89b-12d3-a456-426614174000');

-- Worker 3: Usman Raza
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.3, '2023-03-12T09:15:00.000', '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (35, 12345673, 'Islamabad, Pakistan', 'usman.raza@example.com', 'Usman Raza', '0321-3456789', '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 10, NULL, '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES (2, uuid_generate_v4(), '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Diploma in Plumbing', 'edu1-323e4567', '323e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '2cb6c665-8b03-456b-b6d3-65020cfdf1cf', '323e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '5a229278-f665-4edb-8fd4-17d275e9ed42', '323e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '72259e37-eb8a-4777-ac16-770bce656354', '323e4567-e89b-12d3-a456-426614174000');

-- Worker 4: Hamza Sheikh
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.7, '2022-07-10T16:45:00.000', '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (32, 12345674, 'Faisalabad, Pakistan', 'hamza.sheikh@example.com', 'Hamza Sheikh', '0333-4567890', '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 7, NULL, '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES (3, uuid_generate_v4(), '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Certification in Masonry', 'edu1-423e4567', '423e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'd537d537-d802-4ebc-baf7-d467d90ede33', '423e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '89ec4955-c486-4bda-8f3e-2db40b530e74', '423e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '93a46ecd-25e9-427b-ae87-13d373c0601c', '423e4567-e89b-12d3-a456-426614174000');

-- Worker 5: Bilal Iqbal
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.6, '2020-05-25T13:00:00.000', '523e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (29, 12345675, 'Rawalpindi, Pakistan', 'bilal.iqbal@example.com', 'Bilal Iqbal', '0345-5678901', '523e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 5, NULL, '523e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES (4, uuid_generate_v4(), '523e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Basic Welding Certification', 'edu1-523e4567', '523e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), 'b67c3963-d860-46ff-b5bb-d01191758269', '523e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'fbd94ed5-cdba-40df-8ccf-89579380ecae', '523e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'c634fd83-f495-409b-8561-f7d23444fc27', '523e4567-e89b-12d3-a456-426614174000');

-- Worker 6: Imran Zafar
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.4, '2021-12-10T11:20:00.000', '623e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (27, 3610112345676, 'Gujranwala, Pakistan', 'imran.zafar@example.com', 'Imran Zafar', '0309-6789012', '623e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 4, NULL, '623e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (0, uuid_generate_v4(), '623e4567-e89b-12d3-a456-426614174000'),
    (1, uuid_generate_v4(), '623e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Advanced Carpentry Techniques', 'edu1-623e4567', '623e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '00eedb76-b1d7-46e5-ade4-a7a0bc846c1d', '623e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '5dfdb64c-39f1-4dd8-9432-cdbf33d09fbd', '623e4567-e89b-12d3-a456-426614174000');

-- Worker 7: Saad Malik
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (3, 4.9, '2023-08-05T15:30:00.000', '723e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (33, 3920112345677, 'Sialkot, Pakistan', 'saad.malik@example.com', 'Saad Malik', '0331-7890123', '723e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 11, NULL, '723e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (2, uuid_generate_v4(), '723e4567-e89b-12d3-a456-426614174000'),
    (3, uuid_generate_v4(), '723e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Electrical and Plumbing Certification', 'edu1-723e4567', '723e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '0bdfc0be-4128-4f83-b76a-d0de48604841', '723e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '2cb6c665-8b03-456b-b6d3-65020cfdf1cf', '723e4567-e89b-12d3-a456-426614174000');

-- Worker 8: Tariq Shah
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 4.2, '2022-02-18T09:50:00.000', '823e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (40, 4140112345678, 'Peshawar, Pakistan', 'tariq.shah@example.com', 'Tariq Shah', '0344-8901234', '823e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 15, NULL, '823e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (3, uuid_generate_v4(), '823e4567-e89b-12d3-a456-426614174000'),
    (4, uuid_generate_v4(), '823e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Master Plumber Certification', 'edu1-823e4567', '823e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '4d85980d-9338-4c90-a104-63ff30bf43dc', '823e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'd537d537-d802-4ebc-baf7-d467d90ede33', '823e4567-e89b-12d3-a456-426614174000');

-- Worker 9: Adeel Rehman
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (2, 4.0, '2021-10-30T14:10:00.000', '923e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (37, 4230112345679, 'Quetta, Pakistan', 'adeel.rehman@example.com', 'Adeel Rehman', '0355-9012345', '923e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 9, NULL, '923e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (4, uuid_generate_v4(), '923e4567-e89b-12d3-a456-426614174000'),
    (0, uuid_generate_v4(), '923e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Masonry Apprenticeship', 'edu1-923e4567', '923e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '87883f6e-d657-4fcc-bec3-585c1ec9ea00', '923e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), 'c70496f4-1316-4cf4-9a14-1b6402bc6b01', '923e4567-e89b-12d3-a456-426614174000');

-- Worker 10: Kamran Javed
INSERT INTO profiles (access, average_rating, registration_date, user_id)
VALUES (1, 3.8, '2023-05-12T17:40:00.000', 'a23e4567-e89b-12d3-a456-426614174000');

INSERT INTO user_details (age, nic, address, email, name, phone, user_id)
VALUES (25, 5120112345680, 'Hyderabad, Pakistan', 'kamran.javed@example.com', 'Kamran Javed', '0317-0123456', 'a23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_profiles (banned, years_of_experience, suspension, user_id)
VALUES (false, 3, NULL, 'a23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_domains (domain, id, user_id)
VALUES
    (1, uuid_generate_v4(), 'a23e4567-e89b-12d3-a456-426614174000'),
    (2, uuid_generate_v4(), 'a23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_education (education, id, user_id)
VALUES ('Electrical Engineering Diploma', 'edu1-a23e4567', 'a23e4567-e89b-12d3-a456-426614174000');

INSERT INTO worker_skills (id, skill, user_id)
VALUES
    (uuid_generate_v4(), '8266961c-559c-471d-86b0-4c374f9707e6', 'a23e4567-e89b-12d3-a456-426614174000'),
    (uuid_generate_v4(), '036677ca-97fc-445d-81d0-a6b6c0304d8a', 'a23e4567-e89b-12d3-a456-426614174000');

