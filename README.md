# Ouros — Landing Page

Landing page da plataforma Ouros, que apresenta recursos para acompanhar a eficiência e a sustentabilidade de granjas e produtores integrados.

## O que o site apresenta

- Desafios de gestão e sustentabilidade na produção rural.
- Passo a passo da plataforma para empresas e integrados.
- Funcionalidades, como gerenciamento de lotes, simulador de lucro e ranking.
- Planos, cálculo de valores e perguntas frequentes.
- Página Sobre, com informações sobre a equipe.
- Acesso às páginas de cadastro, login e recuperação de senha.

## Tecnologias

- JSP para renderizar as páginas.
- HTML, CSS e JavaScript.
- Bootstrap 5.3.3 e fonte Poppins carregados por CDN.
- Imagens e ilustrações locais em SVG e PNG.

O descritor `src/main/webapp/WEB-INF/web.xml` usa Jakarta EE 6.1. O repositório não inclui configuração Maven, Gradle ou npm.

## Estrutura

```text
src/main/webapp/
├── landing/
│   ├── index.jsp       # Landing page
│   ├── landing.css     # Estilos da landing page
│   ├── landing.js      # Interações da página
│   ├── sobre.jsp       # Página Sobre
│   └── sobre.css       # Estilos da página Sobre
├── assets/             # Imagens e ilustrações da landing
├── img/                # Imagens usadas nas demais páginas
├── WEB-INF/
│   └── web.xml         # Configuração web da aplicação
├── Cadastro.jsp
├── login.jsp
└── RecuperarSenha.jsp
```

## Executar localmente

1. Configure um servidor JSP/Servlet compatível com Jakarta EE 6.1, como o Tomcat 11.
2. Configure `src/main/webapp` para implantação como aplicação web no servidor.
3. Inicie o servidor e acesse:

   ```text
   http://localhost:8080/<contexto>/landing/index.jsp
   ```

   Substitua `<contexto>` pelo caminho configurado para a aplicação. Por exemplo, se o contexto for `InterModel`, use `http://localhost:8080/InterModel/landing/index.jsp`.

A fonte Poppins e o Bootstrap são carregados por CDN e precisam de conexão com a internet.
