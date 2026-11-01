# Ouros — CRUD do Primeiro Ano

Sistema web de **monitoramento de dados de granjas avícolas**, desenvolvido como projeto do primeiro ano. O Ouros ajuda administradores a acompanhar granjas, lotes, funcionários, consumo de água e energia e metas de sustentabilidade em um único painel, e a comparar o desempenho de cada granja pelo **CGI (Consumo Geral do Integrado)**.

O repositório reúne o **CRUD (back-end + painel administrativo)** e a **landing page** institucional do projeto.

---

## Funcionalidades

### Autenticação e conta
- Cadastro de administrador com validação de CPF (dígitos verificadores), e-mail, telefone e senha forte.
- Senhas criptografadas com **BCrypt**.
- **Verificação de e-mail** por link com token (válido por 24 h, uso único).
- **Recuperação de senha** por e-mail (token válido por 30 min, uso único; só o pedido mais recente vale).
- Login com sessão autenticada e **filtro de autenticação** que protege todas as rotas privadas.
- Página de **perfil**: edição de nome e telefone, troca de senha e foto de perfil (PNG/JPG até 2 MB, enviada ao **Cloudinary**).

### Painel administrativo
| Módulo | O que faz |
|---|---|
| **Início** | Resumo com totais (granjas, lotes, funcionários, metas), frangos em produção, consumo total de água e energia, metas concluídas e lotes recentes. |
| **Granjas** | CRUD com endereço, empresa responsável, busca, ordenação (nome, capacidade, sustentabilidade) e exibição do CGI. A exclusão remove também os dependentes (lotes, registros, metas, proprietários e endereços). |
| **Lotes** | CRUD com datas de nascimento, chegada e entrega, galinhas recebidas/entregues e ganho. Busca por granja e ordenação. |
| **Funcionários** | CRUD com telefone e empresa, busca filtrada por nome, setor, e-mail ou telefone, e ordenação. |
| **Registros** | Registros de **água** (hidrômetro inicial/final) e **energia** (consumo), em abas separadas. Cada registro recebe um status (*Abaixo / Normal / Acima*) comparado à média dos 3 lotes anteriores da mesma granja. |
| **Metas** | Metas **individuais** (por granja) e **estaduais** (por região/estado), com status *Não iniciada*, *Em andamento* e *Finalizada*. |
| **Perfil** | Perfil com opções de alterar foto de perfil, nome, senha e telefone |

### Recursos gerais
- Paginação (5 itens por página) com busca e ordenação preservadas na navegação.
- Formulários de criar, editar e excluir abertos em **modal** (`<dialog>` + `iframe`).
- Validações no servidor (limites numéricos, datas coerentes, existência de granja/empresa/lote).
- Landing page com seções de apresentação, funcionalidades, passo a passo, ranking e "Sobre nós".

---

## Como o CGI é calculado

O **CGI (Consumo Geral do Integrado)** mede o consumo de recursos por ave entregue:

```
CGI = ((consumo de água / galinhas entregues) × 0,7
     + (consumo de energia / galinhas entregues) × 0,3) × 1000
```

- Consumo de água = hidrômetro final − hidrômetro inicial.
- O CGI de cada granja é a média do CGI dos seus lotes.
- **Quanto menor o CGI, mais eficiente é a granja.**

---

## Tecnologias

- **Java** (Jakarta Servlet) <!-- confirmar a versão; o código usa List.getFirst(), que exige Java 21+ -->
- **JSP + JSTL** (Jakarta Tags) e **CSS** próprio
- **Maven** e **Apache Tomcat 10+**
- **PostgreSQL** com **JDBC** (padrão DAO)
- **jBCrypt** (hash de senhas)
- **Cloudinary** (armazenamento de fotos de perfil)
- **java-dotenv** (leitura do arquivo `.env`)

---

## Estrutura do projeto

```
src/main/java/primeirobd/
├── controller/   # Servlets (Inicio, Granjas, Lotes, Funcionarios, Registros, Metas,
│                 #           Perfil, Login, Cadastro, Verificar e Recuperar/Redefinir senha)
├── filter/       # AuthenticationFilter (protege as rotas privadas)
├── model/        # Entidades (Administrador, Granja, Lote, Funcionario, Meta, Registro*, CGI...)
├── repository/   # Interfaces dos DAOs
├── service/      # Implementações dos DAOs + ConexaoBancoPrimeiro
└── utils/        # Validador, Paginacao, TokenUtil, EmailService, CloudinaryUtil, CalculoCGI

src/main/webapp/
├── Style.css
├── img/
├── index.jsp / login.jsp / cadastro.jsp   # <!-- ajustar conforme a estrutura atual -->
└── WEB-INF/views/                         # JSPs do painel (listagens e formulários em modal)
```

---

## Como executar

### Pré-requisitos
- JDK 21+ <!-- confirmar -->
- Maven 3.9+
- Apache Tomcat 10.1+ (Jakarta EE)
- PostgreSQL
- Conta no [Cloudinary](https://cloudinary.com) e uma conta Gmail com **senha de app** (para o envio de e-mails)

### Passo a passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Ouros-App/ouros-crud-primeiro.git
   cd ouros-crud-primeiro
   ```

2. **Crie o banco de dados** no PostgreSQL e execute o script do repositótio do banco de dados: https://github.com/Ouros-App/postgres-primeiro-qa-database 

3. **Crie o arquivo `.env`** na raiz do projeto:
   ```env
   # Banco de dados
   URL=jdbc:postgresql://localhost:5432/nome_do_banco
   USER=seu_usuario
   PASSWORD=sua_senha

   # Cloudinary
   CLOUDINARY_CLOUD_NAME=seu_cloud_name
   CLOUDINARY_API_KEY=sua_api_key
   CLOUDINARY_API_SECRET=seu_api_secret
   ```
   > Nunca envie o `.env` para o GitHub. Confirme que ele está no `.gitignore`.

4. **Compile e publique** no Tomcat (pelo IntelliJ ou gerando o `.war`):
   ```bash
   mvn clean package
   ```

5. **Acesse** `http://localhost:8080/` <!-- ajustar o context path -->

---

## Principais rotas

| Rota | Acesso | Descrição |
|---|---|---|
| `/index` | Público | Landing page |
| `/login`, `/cadastro` | Público | Entrar e criar conta |
| `/inicio` | Privado | Dashboard |
| `/granjas`, `/lotes`, `/funcionarios` | Privado | CRUDs |
| `/registros?tipo=agua\|energia` | Privado | Registros de água e energia |
| `/metas?tipo=individual\|estadual` | Privado | Metas |
| `/perfil` | Privado | Perfil do administrador |

---

## Segurança

- Consultas com `PreparedStatement` (proteção contra SQL Injection); colunas de `ORDER BY` restritas por lista de valores permitidos.
- Senhas com hash BCrypt e tokens gerados com `SecureRandom`.
- Tokens de verificação e recuperação com expiração e uso único.
- Rotas privadas protegidas por filtro de autenticação.
- Saída de dados nas JSPs escapada com `<c:out>`.

---

## Equipe

Projeto desenvolvido por estudantes do primeiro ano:

- [@FernandoNascimento1409](https://github.com/FernandoNascimento1409)
- [@fnzaia](https://github.com/fnzaia)
- [@cruz-art](https://github.com/cruz-art)
- [@alicinha838](https://github.com/alicinha838)
- [@Andre-Roger](https://github.com/Andre-Roger)
- [@sofia deolim](https://github.com/sofiadeolim4b-debug)

<!-- adicionar o nome do curso/instituição, se desejarem -->

---

## Licença

MIT License

Copyright (c) 2026 Ouros App

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
