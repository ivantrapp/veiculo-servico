CREATE TABLE modelo (
    id VARCHAR(200) primary key,
    name VARCHAR(200) unique,
    fipe_id int,
    id_marca VARCHAR(200),
    CONSTRAINT fk_id_marca FOREIGN KEY (id_marca) REFERENCES marca(id)
)