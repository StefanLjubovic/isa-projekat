
insert into address (address_id, city, country, latitude, longitude, postal_code, street_name, street_number)
values (2, 'Novi Sad', 'Srbija', 0.0, 0.0, '21000', 'Bulevar Kralja Petra I', '11');

insert into registrated_user (type, reg_user_id, email, enabled, first_name, last_name, last_password_reset_date, password, phone_number, user_status, cancellation_number, client_type, points, short_biography, address_id)
values ('ADMIN', 1, 'mainadmin@gmail.com', true, 'Adam', 'Adamovic', '2021-03-24 16:48:05.591', '123456', '0645656565', 0, null, null, null, null, 1);