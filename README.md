# VeloCRM 🚀

> **VeloCRM** é um ecossistema de CRM (Customer Relationship Management) de alta performance, modular e distribuído. O projeto utiliza uma arquitetura híbrida (**Java + Go**) estruturada sob as diretrizes de **Arquitetura Hexagonal (Ports & Adapters)**, **Domain-Driven Design (DDD)** e **Arquitetura Orientada a Eventos (EDA)**.

O objetivo principal do VeloCRM é resolver o engessamento dos CRMs tradicionais por meio de **Custom Fields dinâmicos** (armazenados de forma eficiente em bancos relacionais) e inteligência artificial proativa para automação de processos de vendas.

---

## 🏗️ Arquitetura e Visão Geral

O sistema é dividido em microsserviços especializados para garantir isolamento de contexto, escalabilidade e eficiência de recursos:

1. **VeloCRM Core (Java 21 + Spring Boot 3):** Responsável por toda a governança corporativa, consistência relacional das regras de negócio complexas, auditoria imutável e controle de acesso baseado em funções (RBAC) com suporte a multi-empresa (*Multi-tenancy*).
2. **VeloAI Worker (Go):** Um serviço leve, concorrente e de altíssima velocidade responsável pelo consumo assíncrono de eventos, processamento de arquivos/áudio e integração com APIs de LLMs (OpenAI/Gemini) para extração de dados estruturados.
3. **VeloClient (Next.js 14 + Shadcn/ui):** Uma interface administrativa moderna, responsiva e focada na produtividade do vendedor (Painel Kanban dinâmico e gráficos em tempo real).

### O Fluxo Orientado a Eventos (Event-Driven)
```text
[VeloClient Frontend] ──(HTTP REST)──► [Core Java (Spring Boot)]
                                             │
                                      (Grava Postgres)
                                             │
                                      (Publica Evento)
                                             ▼
                                      [ Fila no Redis ]
                                             │
                                      (Consome Evento)
                                             ▼
                                      [ VeloAI Worker (Go) ] ──► [ APIs de LLM ]

```

---

## 🛠️ Tecnologias e Ferramentas

| Camada | Tecnologia | Justificativa Técnica |
| --- | --- | --- |
| **Backend Core** | Java 21 / Spring Boot 3 | Uso de *Virtual Threads* para alta concorrência e *Records* para imutabilidade. |
| **AI/Worker** | Go (Golang) | Concorrência nativa eficiente (*Goroutines*) e baixo consumo de memória. |
| **Frontend** | Next.js 14 / Tailwind CSS | Roteamento baseado em arquivos, renderização otimizada e agilidade de estilização. |
| **UI Components** | Shadcn/ui / Recharts | Componentes prontos de nível premium (Kanban, tabelas com paginação e gráficos). |
| **Banco Principal** | PostgreSQL 16 | Consistência ACID e uso avançado de colunas `JSONB` com **Índices GIN**. |
| **Cache / Broker** | Redis | Cache estratégico para o funil de vendas e mensageria assíncrona leve. |
| **Ambiente** | Docker / Docker-Compose | Orquestração simplificada do ambiente de desenvolvimento local. |

---

## ⚙️ Principais Funcionalidades da Engenharia

### 🧩 1. Custom Fields com PostgreSQL JSONB

Para evitar alterações de schema (`ALTER TABLE`) em produção e manter a flexibilidade de um banco NoSQL dentro de um ecossistema relacional rígido, o VeloCRM armazena os atributos dinâmicos dos clientes em uma coluna `JSONB`. A performance de busca é garantida através da criação de um índice estruturado:

```sql
CREATE INDEX idx_customers_custom_attributes ON customers USING gin (custom_attributes);

```

### 🎯 2. Domínio Rico e Isolado (DDD + Hexagonal)

A camada de aplicação do Core não possui dependência de frameworks externos ou bibliotecas de persistência. A inversão de dependência é garantida através de **Ports (Interfaces)**, permitindo que o banco de dados ou o sistema de mensageria sejam substituídos sem impactar as regras de negócio.

* Entidades ricas contendo lógica de auto-validação (Ex: regras estritas para transição de estágios do Kanban).

### 🔐 3. Multi-Tenancy e Mitigação de IDOR

O sistema foi desenhado nativamente para cenários corporativos multi-empresa. Todo registro possui um vínculo com um `account_id`. Os filtros de segurança interceptam as requisições na camada de aplicação para garantir que um usuário jamais acesse ou altere dados de outro cliente/empresa (prevenção contra *Insecure Direct Object Reference*).

### 🤖 4. Processamento Assíncrono de IA

Os uploads de notas de reuniões ou áudios não bloqueiam a requisição do usuário. O Core Java recebe o dado, despacha um evento para o Redis e libera o frontend. O Worker em Go processa o payload em background, utiliza engenharia de prompt avançada (*Structured Outputs*) para extrair os valores da venda e atualiza o pipeline de forma transparente.

---

## 📦 Estrutura de Pastas do Core (Java)

O projeto adota a divisão clássica da Arquitetura Hexagonal:

```text
com.velocrm.core
│
├── domain               # Regras de Negócio puras (Entidades, Objetos de Valor)
│   ├── model
│   └── exceptions
│
├── application          # Casos de Uso (Services) e Portas de Entrada/Saída
│   ├── ports
│   │   ├── inbound      # UseCases (Interfaces chamadas pelos Controllers)
│   │   └── outbound     # SPIs (Interfaces implementadas pela Infraestrutura)
│   └── services
│
└── infrastructure       # Detalhes tecnológicos (Mundo externo)
    ├── adapters
    │   ├── inbound      # Controllers REST, DTOs (Records) e Mappers
    │   └── outbound     # Implementações de Repositories (JPA/Postgres) e Redis
    └── config           # Configurações do Spring Boot (Security, Cache)

```

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos

* Docker e Docker-Compose instalados.
* Java 21 SDK (para desenvolvimento/alterações no Core).
* Go 1.22+ (para desenvolvimento/alterações no Worker).

### Passo a Passo

1. Clone o repositório:

```bash
git clone [https://github.com/heitor-hsantos/VeloCRM.git](https://github.com/heitor-hsantos/VeloCRM.git)
cd VeloCRM

```

2. Inicialize toda a infraestrutura (PostgreSQL, Redis, Core, Worker e Frontend) com um único comando:

```bash
docker-compose up -d --build

```

3. O sistema estará disponível nos seguintes endereços:

* **Frontend Application:** `http://localhost:3000`
* **Core API (Swagger/Documentation):** `http://localhost:8080/swagger-ui.html`

---

## 📈 Evolução do Aprendizado (Diário de Bordo)

Este projeto foi desenvolvido com o objetivo de consolidar conceitos avançados de engenharia de software corporativa. Toda a jornada de desenvolvimento, tomada de decisões arquiteturais e superação de blocos técnicos foi documentada e compartilhada publicamente:

* [Post #1: Por que escolher uma arquitetura híbrida Java + Go?](https://www.google.com/search?q=link-do-seu-post)
* [Post #2: Implementando a Arquitetura Hexagonal sem sofrimento](https://www.google.com/search?q=link-do-seu-post)
* [Post #3: O poder das colunas JSONB com índices GIN no PostgreSQL](https://www.google.com/search?q=link-do-seu-post)

---

Desenvolvido por **Heitor Henrique dos Santos** 💻

```

```
