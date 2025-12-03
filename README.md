<h3 align="center">
Hi there, I'm <a href="https://geekdeep.wordpress.com/" target="_blank" rel="noreferrer">Lucas Brum</a> 👋
</h3>

<h2 align="center">
I'm a Back-end Developer 💻, Guitarrist 🎸, and Fullstack under construction💡!
</h2>

## 💼 Stack

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

### 🤝 Connect with me:

<a href="https://www.linkedin.com/in/lucasbrum/"><img align="left" src="https://raw.githubusercontent.com/yushi1007/yushi1007/main/images/linkedin.svg" alt="Yu Shi | LinkedIn" width="21px"/></a>
<a href="https://instagram.com/lucasbrumguitar"><img align="left" src="https://raw.githubusercontent.com/yushi1007/yushi1007/main/images/instagram.svg" alt="Yu Shi | Instagram" width="21px"/></a>
</br>
- 💬 If you have any question/feedback, please do not hesitate to reach out to me!

---

# MyCollection API

API REST para gerenciamento de coleção de CDs com integração ao Discogs.

## Requisitos

- Java 21
- Maven 3.8+
- Docker e Docker Compose
- PostgreSQL 15+ (via Docker)

## Configuração Rápida

### 1. Iniciar o Banco de Dados

```bash
docker-compose -f docker-compose-db.yml up -d
```

O banco estará disponível em:
- Host: `localhost`
- Porta: `5433`
- Database: `mycollection_db`
- Usuário: `postgres`
- Senha: `postgres`

### 2. Iniciar a Aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8081/mycollection/api`

### 3. Verificar se está funcionando

```bash
curl http://localhost:8081/mycollection/api/artists
```

---

## Integração com Discogs API

O MyCollection integra com a [API do Discogs](https://www.discogs.com/developers/) para permitir a importação automática de dados de CDs.

### Configuração do Token Discogs

O token já está configurado no `application.properties`. Para usar seu próprio token:

1. Acesse [Discogs Developers](https://www.discogs.com/settings/developers)
2. Clique em **Generate new token**
3. Configure via variável de ambiente:

```bash
export DISCOGS_TOKEN=seu_token_aqui
./mvnw spring-boot:run
```

---

## Testando a API do Discogs

### Pré-requisitos

1. Banco de dados rodando (`docker-compose -f docker-compose-db.yml up -d`)
2. Aplicação rodando (`./mvnw spring-boot:run`)

### Testes com cURL

#### 1. Buscar por Artista

```bash
curl -X GET "http://localhost:8081/mycollection/api/discogs/search/artist?name=Metallica&page=1&perPage=5"
```

**Resposta esperada:**
```json
{
  "data": {
    "pagination": {
      "page": 1,
      "pages": 100,
      "perPage": 5,
      "items": 500
    },
    "results": [
      {
        "id": 539437,
        "masterId": 6573,
        "title": "Metallica - Master Of Puppets",
        "year": "1986",
        "coverImage": "https://i.discogs.com/...",
        "thumb": "https://i.discogs.com/...",
        "genre": ["Rock"],
        "label": ["Elektra"],
        "format": ["CD", "Album"]
      }
    ]
  },
  "statusCode": 200
}
```

#### 2. Buscar por Álbum

```bash
curl -X GET "http://localhost:8081/mycollection/api/discogs/search/album?title=The%20Dark%20Side%20of%20the%20Moon&page=1&perPage=5"
```

#### 3. Busca Geral

```bash
curl -X GET "http://localhost:8081/mycollection/api/discogs/search?query=Iron%20Maiden&type=release&page=1&perPage=10"
```

#### 4. Obter Detalhes de um Release

Primeiro, pegue um `id` de um resultado da busca, depois:

```bash
curl -X GET "http://localhost:8081/mycollection/api/discogs/releases/539437"
```

**Resposta esperada:**
```json
{
  "data": {
    "id": 539437,
    "masterId": 6573,
    "title": "Master Of Puppets",
    "year": 1986,
    "artists": [
      {"id": 18839, "name": "Metallica"}
    ],
    "labels": [
      {"id": 1866, "name": "Elektra", "catno": "9 60439-2"}
    ],
    "genres": ["Rock"],
    "styles": ["Thrash", "Heavy Metal"],
    "tracklist": [
      {"position": "1", "title": "Battery", "duration": "5:10"},
      {"position": "2", "title": "Master Of Puppets", "duration": "8:38"}
    ],
    "images": [
      {"type": "primary", "uri": "https://i.discogs.com/..."}
    ],
    "extraArtists": [
      {"id": 18839, "name": "James Hetfield", "role": "Vocals, Guitar"}
    ]
  },
  "statusCode": 200
}
```

---

## Testando com Postman

### Importar Collection

Crie uma nova collection no Postman com as seguintes requisições:

| Nome | Método | URL |
|------|--------|-----|
| Search by Artist | GET | `{{baseUrl}}/discogs/search/artist?name=Metallica&page=1&perPage=10` |
| Search by Album | GET | `{{baseUrl}}/discogs/search/album?title=Master of Puppets&page=1&perPage=10` |
| General Search | GET | `{{baseUrl}}/discogs/search?query=Pink Floyd&type=release&page=1&perPage=10` |
| Get Release Details | GET | `{{baseUrl}}/discogs/releases/539437` |

**Variável de ambiente:**
- `baseUrl`: `http://localhost:8081/mycollection/api`

---

## Fluxo de Uso Completo

### Importar um CD do Discogs

```bash
# 1. Buscar o álbum
curl -X GET "http://localhost:8081/mycollection/api/discogs/search/album?title=Nevermind&page=1&perPage=5"

# 2. Anotar o ID do release desejado (ex: 123456)

# 3. Obter detalhes completos
curl -X GET "http://localhost:8081/mycollection/api/discogs/releases/123456"

# 4. Criar o item na coleção usando os dados retornados
curl -X POST "http://localhost:8081/mycollection/api/items" \
  -H "Content-Type: multipart/form-data" \
  -F 'item={
    "title": "Nevermind",
    "releaseYear": 1991,
    "genre": "Rock",
    "artist": {"id": 1},
    "discogsReleaseId": 123456,
    "discogsImageUrl": "https://i.discogs.com/...",
    "labelName": "DGC",
    "tracks": [
      {"position": "1", "title": "Smells Like Teen Spirit", "duration": "5:01", "trackOrder": 1},
      {"position": "2", "title": "In Bloom", "duration": "4:14", "trackOrder": 2}
    ],
    "credits": [
      {"name": "Kurt Cobain", "role": "Vocals, Guitar"},
      {"name": "Butch Vig", "role": "Producer"}
    ]
  };type=application/json'
```

---

## Endpoints da API

### Discogs

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/discogs/search` | Busca geral |
| GET | `/discogs/search/artist` | Busca por artista |
| GET | `/discogs/search/album` | Busca por álbum |
| GET | `/discogs/releases/{id}` | Detalhes do release |

### Items (CDs)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/items` | Listar todos |
| GET | `/items/{id}` | Buscar por ID |
| POST | `/items` | Criar item |
| PUT | `/items/{id}` | Atualizar item |
| DELETE | `/items/{id}` | Deletar item |

### Artists

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/artists` | Listar todos |
| GET | `/artists/{id}` | Buscar por ID |
| POST | `/artists` | Criar artista |
| PUT | `/artists/{id}` | Atualizar artista |
| DELETE | `/artists/{id}` | Deletar artista |

### Categories

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/categorias` | Listar todas |
| POST | `/categorias` | Criar categoria |

---

## Limites da API do Discogs

| Tipo | Limite |
|------|--------|
| Requisições autenticadas | 60 por minuto |
| Requisições não autenticadas | 25 por minuto |

---

## Troubleshooting

### Erro 401 - Token inválido

Verifique se o token do Discogs está configurado corretamente:

```bash
# Ver configuração atual
cat src/main/resources/application.properties | grep discogs

# Testar com variável de ambiente
export DISCOGS_TOKEN=seu_token_aqui
./mvnw spring-boot:run
```

### Erro de conexão com banco

```bash
# Verificar se o container está rodando
docker ps

# Se não estiver, iniciar
docker-compose -f docker-compose-db.yml up -d

# Ver logs do container
docker-compose -f docker-compose-db.yml logs
```

### Erro 429 - Rate limit excedido

Aguarde 1 minuto e tente novamente. A API do Discogs limita a 60 requisições por minuto.

---

## Scripts Úteis

### Testar todos os endpoints do Discogs

```bash
#!/bin/bash

BASE_URL="http://localhost:8081/mycollection/api"

echo "=== Testando API do Discogs ==="

echo -e "\n1. Busca por artista (Metallica):"
curl -s "$BASE_URL/discogs/search/artist?name=Metallica&perPage=3" | jq '.data.results[0].title'

echo -e "\n2. Busca por álbum (Master of Puppets):"
curl -s "$BASE_URL/discogs/search/album?title=Master%20of%20Puppets&perPage=3" | jq '.data.results[0].title'

echo -e "\n3. Detalhes do release 539437:"
curl -s "$BASE_URL/discogs/releases/539437" | jq '{title: .data.title, year: .data.year, tracks: .data.tracklist | length}'

echo -e "\n=== Testes concluídos ==="
```

Salve como `test-discogs.sh` e execute:

```bash
chmod +x test-discogs.sh
./test-discogs.sh
```

---

## Referências

- [Discogs API Documentation](https://www.discogs.com/developers/)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
