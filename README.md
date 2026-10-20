# Ouros Landing

Landing page do projeto Ouros, com as páginas JSP em `src/main/webapp/landing`.

## Requisitos

- JDK 17 ou superior
- Apache Tomcat 11

## IntelliJ IDEA

Abra a pasta do projeto e carregue o `pom.xml` como projeto Maven. Para executar, configure o Tomcat 11 e publique o artefato `ouros-landing:war`.

## Gerar o WAR

Execute `mvn package`. O arquivo será criado em `target/ouros-landing.war`.

A rota inicial redireciona para `landing/index.jsp`. Com o contexto padrão do WAR, acesse `http://localhost:8080/ouros-landing/`.
