create table price_data.dollar(
    id serial,
    price numeric(5,2),
    uom varchar(64),
    sample_date date,
    sample_time time,
    source varchar(128)
);
