
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
