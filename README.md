# Cora Challenge

API REST para listagem e criação de contas bancárias, feita com Kotlin + Spring Boot 3 + H2.

## Como rodar

```bash
./gradlew bootRun
```

O servidor sobe na porta **8081**.

## Endpoints

```
POST /api/v1/accounts
GET  /api/v1/accounts
```

**Criar conta:**

```bash
curl -X POST http://localhost:8081/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{"name": "João Silva", "cpf": "12345678901"}'
```

**Listar contas:**

```bash
curl http://localhost:8081/api/v1/accounts
```

## Banco de dados

H2 em memória. O console fica disponível em:

```
http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vazio)
```

## Observações

- CPF precisa ter exatamente 11 dígitos numéricos
- Não é possível cadastrar dois CPFs iguais — retorna 409
- CORS liberado para qualquer origem (preparado para o frontend da entrevista)
- Java 21

## Seed (dados de teste)

Para facilitar os testes, criei uma rota que popula o banco com 5 contas prontas. Ela só existe quando o perfil
`dev-seed` está ativo — fora disso a rota nem aparece.

**Rodar com o perfil:**

```bash
./gradlew bootRun --args='--spring.profiles.active=dev-seed'
```

**Disparar o seed:**

```bash
curl -X POST http://localhost:8081/api/v1/seed
```

A resposta indica o que foi criado e o que já existia (caso rode mais de uma vez):

```json
{
  "created": [
    "Ana Paula Ferreira",
    "Bruno Carvalho",
    "Carla Mendes",
    "Diego Rocha",
    "Elisa Teixeira"
  ],
  "skipped": [],
  "message": "Seed concluído: 5 criadas, 0 ignoradas (já existiam)"
}
```

A rota é idempotente — pode chamar quantas vezes quiser sem duplicar dados.
