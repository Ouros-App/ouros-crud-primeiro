<<<<<<< HEAD
# ouros-crud-primeiro
CRUD do primeiro ano
=======
# Ouros — Landing Page

Site estático que apresenta a plataforma Ouros e seus recursos para acompanhar a eficiência e a sustentabilidade de granjas e produtores integrados.

A versão publicada está em [ouros-app.github.io/ouros-landing](https://ouros-app.github.io/ouros-landing/).

## Páginas

- `html/index.html`: landing page principal.
- `html/landing/sobre.html`: página Sobre.
- `html/acesso/login.html`: tela de login.
- `html/acesso/cadastro.html`: tela de cadastro.
- `html/acesso/recuperar-senha.html`: tela de recuperação de senha.

As páginas de acesso são visuais. Sem um backend, elas não autenticam usuários, não enviam formulários e não salvam dados.

## Recursos

- Apresentação de desafios, funcionalidades, funcionamento, planos, ranking e perguntas frequentes.
- Menu adaptado para telas menores.
- Interações de FAQ e carrossel.
- Estimativa de preço calculada no navegador, sem salvar os dados.

## Tecnologias

- HTML, CSS e JavaScript.
- Bootstrap 5.3.3 e fonte Poppins carregados por CDN.
- Imagens e ilustrações locais em PNG e SVG.

Bootstrap e Poppins precisam de conexão com a internet para carregar.

## Estrutura

```text
.github/
└── workflows/
    └── pages.yml       # Publicação pelo GitHub Actions

html/
├── index.html
├── Style.css
├── acesso/
│   ├── cadastro.html
│   ├── login.html
│   └── recuperar-senha.html
├── assets/             # Imagens da landing page
├── img/                # Imagens das telas de acesso
└── landing/
    ├── landing.css
    ├── landing.js
    ├── sobre.css
    └── sobre.html
```

## Executar localmente

1. Abra o repositório no VS Code.
2. Abra `html/index.html`.
3. Inicie o Live Server para visualizar o site no navegador.

## Publicar no GitHub Pages

O workflow `.github/workflows/pages.yml` publica o conteúdo da pasta `html/` no GitHub Pages. A publicação é estática e não executa JSP, Servlets ou código Java no servidor.

Para que a publicação funcione, a origem do GitHub Pages deve estar configurada para **GitHub Actions** nas configurações do repositório.
>>>>>>> a5768b059818be60b7c371ac30ae86d1a651980c
