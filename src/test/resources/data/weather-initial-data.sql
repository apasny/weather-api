delete from weather;
delete from weather_temperatures;
ALTER SEQUENCE weather_sequence RESTART WITH 1;

insert into weather (id, date, lat, lon, city, state, temperatures)
values (nextval('weather_sequence'), '2021-11-10', -12.54, 21.23, 'London', 'London', null);
insert into weather (id, date, lat, lon, city, state, temperatures)
values (nextval('weather_sequence'), '2020-11-10', -32.54, 51.23, 'Minsk', 'Minsk', null);

insert into weather_temperatures (weather_id, temperature)
values (1, 2.1);
insert into weather_temperatures (weather_id, temperature)
values (1, 3.5);
insert into weather_temperatures (weather_id, temperature)
values (2, 3.1);
insert into weather_temperatures (weather_id, temperature)
values (2, 4.5);