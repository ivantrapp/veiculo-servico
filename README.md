Projeto feito com DDD - Domain Driven Architecture.

Foi utilizado Java com Spring Boot, Postgresql, RabbitMQ para mensageria e versionamento de scripts com Flyway.

Para rodar o projeto é necessário buildar o Dockerfile do container da aplicação Spring.

docker build -t spring-app .

Garantir que as seguintes portas estejam livres no ambiente local:
5672:5672 - Rabbitmq
8080:8080 - Spring-app
6000:5432 - Postgresql

Após build feito, subir o docker compose!

docker compose up -d 

Endpoints da aplicação:

Cadastrar veículo na aplicação
POST - http://localhost:8080/veiculo/cadastrar
Payload: {
  "placa": "ASaa1234",
  "precoAnunciado": 45000.0,
  "idMarca":26,
  "idModelo": 4403,
  "ano": 2011
}

Retorno: "Adicionado para fila de cadastros"

Buscar veículo por marca (paginado)
GET - http://localhost:8080/veiculo/veiculos/{idMarcaInterno}?page=0&size=10&sort=id
idMarcaInterno: 07b58e76-a95d-4b25-9d59-b03f6e8b9361

Retorno: {
{
    "content": [
        {
            "id": "44b56030-ee50-4b5f-9bff-bb7900493600",
            "idMarca": 26,
            "idModelo": 4403,
            "precoAnunciado": 45000.0,
            "precoFipe": 46296.0,
            "placa": "ASaa1234",
            "ano": 2011,
            "dataCadastro": "2025-07-29"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": [
            {
                "direction": "ASC",
                "property": "id",
                "ignoreCase": false,
                "nullHandling": "NATIVE",
                "ascending": true,
                "descending": false
            }
        ],
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 10,
    "number": 0,
    "sort": [
        {
            "direction": "ASC",
            "property": "id",
            "ignoreCase": false,
            "nullHandling": "NATIVE",
            "ascending": true,
            "descending": false
        }
    ],
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
}

Buscar todas marcas cadastradas e com quantidade de modelos atrelado
GET - http://localhost:8080/marcas

Retorno : {
        "id": "ca43ec74-5bb0-4288-ab11-5df094ca4dc4",
        "name": "FIAT",
        "total_modelos": 3
    }

Buscar veiculo por placa
GET - http://localhost:8080/veiculo?placa=ASaa1234
Retorno : {
    "id": "44b56030-ee50-4b5f-9bff-bb7900493600",
    "idMarca": 26,
    "idModelo": 4403,
    "precoAnunciado": 45000.0,
    "precoFipe": 46296.0,
    "placa": "ASaa1234",
    "ano": 2011,
    "dataCadastro": "2025-07-29"
}

Pontos de melhora:
- Adicionar camada de cache(Redis) para performance em geral.
- Gerenciar dead letter da mensageria.
- Testes unitários (Utilizando BDD, Dado X, quando Y, então Z).
- Melhorar lógica de cadastro de veículo com dead letter.
- Melhorar tratamento de exceptions.
- Adicionar swagger para facilitar documentação de API.
- Melhorar visibilidade de logs para monitoramento (Criar logs para entrada de métodos de controladores, serviços e repositórios)
