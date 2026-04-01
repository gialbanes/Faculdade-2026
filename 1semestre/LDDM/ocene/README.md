# Océane API (Kotlin Multiplatform + Ktor)

A Océane é uma loja de produtos de beleza. Nesta atividade, foram escolhidos dois models principais: `Product` e `Category`.

Projeto da atividade com:
- API em Ktor (módulo `server`)
- Banco PostgreSQL (via Docker)
- Migrações com Flyway
- Documentação e testes no Swagger

## Estrutura do projeto

- [server](server): backend Ktor (rotas, repositórios, migrations)
- [shared](shared): modelos e contratos compartilhados
- [composeApp](composeApp): app cliente (Android/Desktop)
- [docker-compose.yaml](docker-compose.yaml): banco PostgreSQL local

## Pré-requisitos

Instalar na máquina:
- JDK 17 ou superior
- Docker Desktop

## 1) Subir o banco de dados

Na raiz do projeto, execute:

```powershell
docker compose up -d
```

Configuração padrão do banco (já no projeto):
- Host: `localhost`
- Porta: `5432`
- Database: `oceane`
- User: `seu-usuario`
- Password: `seua-senha`

## 2) Rodar o servidor

Na raiz do projeto:

```powershell
.\gradlew.bat :server:run
```

Ao iniciar, o servidor:
- conecta no PostgreSQL
- executa migrações Flyway automaticamente
- sobe na porta `8080`

## 3) Acessar a API e documentação

- Health check: http://localhost:8080/health
- Swagger UI: http://localhost:8080/swagger

## Variáveis de ambiente (opcional)

Se quiser alterar conexão de banco, configure:
- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Se não configurar, o projeto usa os valores locais do `docker-compose`.

## Comandos úteis

### Parar banco

```powershell
docker compose down
```

### Parar banco e apagar volume (reset total)

```powershell
docker compose down -v
```

### Compilar projeto inteiro

```powershell
.\gradlew.bat build
```

## Teste rápido (Swagger)

1. Abrir `/swagger`
2. Criar uma categoria em `POST /categories`
3. Criar produtos em `POST /products` usando o `category_id` criado
4. Testar:
   - `GET /products/category/{categoryId}` (deve listar todos da categoria)
   - `PUT /categories/{id}` e `PUT /products/{id}` com atualização parcial (pode enviar só 1 campo)

