CREATE TABLE veiculo (
    id VARCHAR(200) PRIMARY KEY,
    placa VARCHAR(50) UNIQUE,
    preco_anunciado FLOAT,
    preco_fipe FLOAT,
    data_cadastro VARCHAR(20),
    ano int,
    id_marca VARCHAR(200) not null,
    id_modelo VARCHAR(200) not null,
    CONSTRAINT fk_id_marca_veiculo FOREIGN KEY (id_marca) REFERENCES marca(id),
    CONSTRAINT fk_id_modelo_veiculo FOREIGN KEY (id_modelo) REFERENCES modelo(id)
)

