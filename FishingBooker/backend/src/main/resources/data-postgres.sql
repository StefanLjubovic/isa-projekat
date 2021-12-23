-- roles
INSERT INTO role(id, name) VALUES (nextval('role_id_seq'), 'ROLE_CLIENT');
INSERT INTO role(id, name) VALUES (nextval('role_id_seq'), 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (nextval('role_id_seq'), 'ROLE_COTTAGE_OWNER');
INSERT INTO role(id, name) VALUES (nextval('role_id_seq'), 'ROLE_SHIP_OWNER');
INSERT INTO role(id, name) VALUES (nextval('role_id_seq'), 'ROLE_INSTRUCTOR');

-- address
INSERT INTO address (address_id, city, country, latitude, longitude, postal_code, street_name, street_number)
VALUES (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Bulevar Kralja Petra I', '11'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 44.1, 19.5, '21000', 'Bulevar oslobodjenja', '21'),
       (nextval('address_address_id_seq'), 'Sabac', 'Srbija', 0.0, 0.0, '15000', 'Janka Veselinovica', '25'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Janka Cmelika', '5a'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Bulevar Despota Stefana', '10'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Safarikova', '1b');

-- users
--passwords 123456
INSERT INTO registered_user (type, reg_user_id, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, user_status, initial_password_changed, cancellation_number, client_type, points, short_biography, address_id, role_id)
VALUES ('ADMIN', nextval('user_seq_gen'), 'zdravkocolic@gmail.com', true, 'Zdravko', 'Colic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0645656565', 0, true, null, null, null, null, 1, 2),
       ('INSTRUCTOR', nextval('user_seq_gen'), 'ana.gavrilovic247@gmail.com', true, 'Ana', 'Gavrilovic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0648623758', 0, null, null, null, null, 'Zavrsila je Sabacku gimnaziju i FTN smer e2.', 3, 5),
       ('COTTAGE_OWNER', nextval('user_seq_gen'), 'marijakljestan@gmail.com', true, 'Marija', 'Kljestan', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0641234567', 0, null, null, null, null, null, 5, 3),
       ('CLIENT', nextval('user_seq_gen'), 'stefanljubovic@gmail.com', true, 'Stefan', 'Ljubovic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0648855959', 0, null, 0, 0, 0, null, 4, 1);

-- system property
INSERT INTO system_property(system_properties_id, key, value)
VALUES (nextval('system_property_system_properties_id_seq'), 'INCOME_PERCENTAGE', '0'),
       (nextval('system_property_system_properties_id_seq'), 'MAIN_ADMIN_ID', '1');

-- cottage
INSERT INTO renting_entity(entity_id, average_grade, cancellation_percentage, description, name, address_id)
VALUES (nextval('entity_seq_gen'), 4.8, 15, 'Prelepa vikendica uz pogled na jezero', 'Marijina vikendica', 2);
INSERT INTO cottage(entity_id, reg_user_id)
VALUES (1, 3);
INSERT INTO room(room_id, bed_number)
VALUES (nextval('room_room_id_seq'), 2), (nextval('room_room_id_seq'), 3);
INSERT INTO cottage_rooms(cottage_entity_id, rooms_room_id)
VALUES (1, 1), (1, 2);
INSERT INTO renting_entity_allowed_behavior(entity_id, allowed_behaviour)
VALUES (1, 'Bringing pets'), (1, 'Music');
INSERT INTO renting_entity_unallowed_behavior(entity_id, unallowed_behaviour)
VALUES (1, 'Smoking');
INSERT INTO pricelist_item(pricelist_item_id, price, service, entity_id)
VALUES (nextval('pricelist_item_pricelist_item_id_seq'), 5000, 'Per night', 1);

-- adventure
INSERT INTO renting_entity(entity_id, average_grade, cancellation_percentage, description, name, address_id)
VALUES (nextval('entity_seq_gen'), 0, 0, 'Beautiful moments with beautiful nature and sun', 'Fishing in the sunset', 6);
INSERT INTO adventure(max_persons, entity_id, reg_user_id)
VALUES (5, 2, 2);
INSERT INTO adventure_fishing_equipment(entity_id, fishing_equipment)
VALUES (2, 'Fishing rod'), (2, 'Hooks'), (2, 'Food');
INSERT INTO renting_entity_allowed_behavior(entity_id, allowed_behaviour)
VALUES (2, 'Bringing pets to the adventure.'), (2, 'Alcohol is allowed!');
INSERT INTO renting_entity_unallowed_behavior(entity_id, unallowed_behaviour)
VALUES (2, 'Smoking');
INSERT INTO pricelist_item(pricelist_item_id, price, service, entity_id)
VALUES (nextval('pricelist_item_pricelist_item_id_seq'), 2000, 'Basic adventure', 2);

INSERT INTO subscriptions(client_id,entity_id)
VALUES (4,1),(4,2);