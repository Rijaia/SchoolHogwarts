CREATE TABLE person
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50),
    age INTEGER,
    got_licence BOOLEAN,
    car_id INTEGER REFERENCES car(id)
);
CREATE  TABLE car
(
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50),
    model VARCHAR(50),
    price DECIMAL
);