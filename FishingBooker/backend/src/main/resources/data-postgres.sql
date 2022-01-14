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
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Safarikova', '1b'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Kralja Aleksandra', '12'),
       (nextval('address_address_id_seq'), 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Petra Drapsina', '2'),
       (nextval('address_address_id_seq'), 'Sabac', 'Srbija', 0.0, 0.0, '15000', 'dr Andre Jovanovica', '6');

-- users
-- passwords 123456
INSERT INTO registered_user (type, reg_user_id, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, user_status, initial_password_changed, cancellation_number, client_type, points, short_biography, address_id, role_id)
VALUES ('ADMIN', nextval('user_seq_gen'), 'zdravkocolic@gmail.com', true, 'Zdravko', 'Colic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0645656565', 0, true, null, null, null, null, 1, 2),
       ('INSTRUCTOR', nextval('user_seq_gen'), 'ana.gavrilovic247@gmail.com', true, 'Ana', 'Gavrilovic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0648623758', 0, null, null, null, null, 'Zavrsila je Sabacku gimnaziju i FTN smer e2.', 3, 5),
       ('COTTAGE_OWNER', nextval('user_seq_gen'), 'marijakljestan@gmail.com', true, 'Marija', 'Kljestan', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0641234567', 0, null, null, null, null, null, 5, 3),
       ('CLIENT', nextval('user_seq_gen'), 'ljubovicstefan@gmail.com', true, 'Stefan', 'Ljubovic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0648855959', 0, null, 0, 0, 0, null, 4, 1),
       ('SHIP_OWNER', nextval('user_seq_gen'), 'pantictamara@gmail.com', true, 'Tamara', 'Pantic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '066225883', 0, null, null, null, null, null, 8, 4),
       ('ADMIN', nextval('user_seq_gen'), 'zg.gavrilovic@gmail.com', true, 'Zoran', 'Gavrilovic', '2021-03-24 16:48:05.591', '$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW', '0645656565', 0, true, null, null, null, null, 9, 2);

-- system property
INSERT INTO system_property(system_properties_id, key, value, version)
VALUES (nextval('system_property_system_properties_id_seq'), 'INCOME_PERCENTAGE', '15', 0),
       (nextval('system_property_system_properties_id_seq'), 'MAIN_ADMIN_ID', '1', 0);

-- cottage
INSERT INTO renting_entity(entity_id, average_grade, cancellation_percentage, description, name, version,address_id)
VALUES (nextval('entity_seq_gen'), 4.8, 15, 'Prelepa vikendica uz pogled na jezero', 'Marijina vikendica',0 ,2);
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
VALUES (nextval('pricelist_item_pricelist_item_id_seq'), 5000, 'Standard offer', 1),
       (nextval('pricelist_item_pricelist_item_id_seq'), 1000, 'Music', 1);

-- adventure
INSERT INTO renting_entity(entity_id, average_grade, cancellation_percentage, description, name,version, address_id)
VALUES (nextval('entity_seq_gen'), 0, 0, 'Beautiful moments with beautiful nature and sun', 'Fishing in the sunset',0, 6);
INSERT INTO adventure(max_persons, entity_id, reg_user_id)
VALUES (5, 2, 2);
INSERT INTO adventure_fishing_equipment(entity_id, fishing_equipment)
VALUES (2, 'Fishing rod'), (2, 'Hooks'), (2, 'Food');
INSERT INTO renting_entity_allowed_behavior(entity_id, allowed_behaviour)
VALUES (2, 'Bringing pets to the adventure.'), (2, 'Alcohol is allowed!');
INSERT INTO renting_entity_unallowed_behavior(entity_id, unallowed_behaviour)
VALUES (2, 'Smoking');
INSERT INTO pricelist_item(pricelist_item_id, price, service, entity_id)
VALUES (nextval('pricelist_item_pricelist_item_id_seq'), 2000, 'Standard offer', 2);

-- ship
INSERT INTO renting_entity(entity_id, average_grade, cancellation_percentage, description, name,version, address_id)
VALUES (nextval('entity_seq_gen'), 0, 0, 'Beautiful moments with beautiful nature and sun', 'Anin brod',0, 7);
INSERT INTO ship(type, length, engine_number, engine_power, max_speed, capacity, entity_id, reg_user_id)
VALUES ('Fishing ship', 550.5, 16, 250, 50, 15, 3, 5);
INSERT INTO ship_navigation_equipment(entity_id, navigation_equipment)
VALUES (3, 0), (3, 1), (3, 2);
INSERT INTO ship_fishing_equipment(entity_id, fishing_equipment)
VALUES (3, 'Fishing rod'), (3, 'Hooks');
INSERT INTO renting_entity_allowed_behavior(entity_id, allowed_behaviour)
VALUES (3, 'Music is allowed'), (3, 'Alcohol is allowed!');
INSERT INTO renting_entity_unallowed_behavior(entity_id, unallowed_behaviour)
VALUES (3, 'Smoking');
INSERT INTO pricelist_item(pricelist_item_id, price, service, entity_id)
VALUES (nextval('pricelist_item_pricelist_item_id_seq'), 2000, 'Standard offer', 3);

INSERT INTO subscriptions(client_id,entity_id)
VALUES (4,2);


-- reservation
INSERT INTO reservation(reservation_id,date_time,duration_in_hours,is_canceled,max_persons,price,reg_user_id,entity_id)
VALUES  (nextval('reservation_seq_gen'),'2022-01-05 00:00',72,false,7,4000,4,1),
        (nextval('reservation_seq_gen'),'2021-11-05 00:00',48,false,10,8000,4,2),
        (nextval('reservation_seq_gen'),'2022-01-07 08:00:00',5,false,11,7000,4,2),
        (nextval('reservation_seq_gen'),'2021-10-05',72,false,11,7000,4,2),
        (nextval('reservation_seq_gen'),'2020-07-25', 2,false,15,7500,4,3),
        (nextval('reservation_seq_gen'),'2022-01-13', 72,false,5,7500,4,1),
        (nextval('reservation_seq_gen'),Date(now()) +7, 72,false,15,8500,4,3);

-- sales
INSERT INTO sale(sale_id,date_time_from,duration_in_hours,expire_date_time,maximum_persons,price,entity_id)
VALUES (nextval('sale_sale_id_seq'),'2022-01-11 08:00:00',4,'2022-01-08 08:00:00',8,4000,2),
       (nextval('sale_sale_id_seq'),'2022-01-14 08:00:00',24,'2022-01-08 08:00:00',8,10000,1),
       (nextval('sale_sale_id_seq'),'2022-01-11 08:00:00',4,'2022-01-08 08:00:00',8,10000,3);

-- instructor - unavailable period
INSERT INTO unavailable_period(period_id,from_date_time,to_date_time)
VALUES (nextval('unavailable_period_period_id_seq'),'2022-01-04 08:00:00','2022-01-04 12:30:00');

INSERT INTO registered_user_unavailable_periods(fishing_instructor_reg_user_id, unavailable_periods_period_id)
VALUES (2, 1);

-- revision
INSERT INTO revision(revision_id, content, is_approved, mark, reservation_id)
VALUES (nextval('revision_revision_id_seq'), 'Super!', false, 4, 3),
       (nextval('revision_revision_id_seq'), 'Nije lose!', false, 3, 2);

-- complaints
INSERT INTO complaint(complaint_id, content, reg_user_id, entity_id)
VALUES (nextval('complaint_complaint_id_seq'), 'Ne svidja mi se avantura.', 4, 2),
       (nextval('complaint_complaint_id_seq'), 'Ne svidja mi se vikendica.', 4, 1);

-- delete requests
INSERT INTO delete_request(delete_req_id, content, reg_user_id)
VALUES (nextval('delete_request_delete_req_id_seq'), 'zelim', 2);
