# Sistema de Gerenciamento de Tarefas

# 📑 Sobre o projeto

Este projeto de **Gerenciamento de Tarefas** foi desenvolvido com uma arquitetura
baseada em **microsserviços** utilizando **Java** e o ecossistema **Spring**. 
O objetivo é fornecer uma plataforma onde os usuários possam criar, organizar e 
gerenciar suas tarefas em diferentes projetos, permitindo também o acompanhamento 
de status e prioridades.

## Contexto do Projeto:

O sistema permite que os usuários criem, visualizem, atualizem e excluam tarefas, 
além de organizá-las por projetos e categorias como prioridade e status. 
Cada funcionalidade principal do sistema foi separada em microsserviços distintos, 
promovendo uma divisão clara de responsabilidades e permitindo uma implementação que 
pode ser escalada e mantida de forma mais eficiente.

**Arquitetura baseada em Microsserviços:**

A aplicação é dividida em três serviços principais:

**Serviço de Usuários (User Service):**

- Responsável pelo gerenciamento dos usuários, incluindo o registro, autenticação e autorização. Utiliza o Spring Security com JWT (JSON Web Token) para garantir a segurança e a privacidade das interações.

**Serviço de Tarefas (Task Service):**

- Gerencia o ciclo de vida das tarefas, permitindo que os usuários realizem operações de CRUD (criação, leitura, atualização e exclusão). As tarefas são categorizadas por status (pendente, em andamento, concluída) e prioridade (baixa, média, alta).

**Serviço de Projetos (Project Service):**

- Permite a criação e o gerenciamento de projetos aos quais as tarefas podem ser associadas. Cada tarefa pode ser vinculada a um projeto específico, facilitando a organização do trabalho. 


# 🛠️ Tecnologias utilizadas

## Back end

- **Spring Boot:** Para construir a estrutura dos microsserviços.
- **Spring Cloud:** Usado para resolver problemas comuns em microsserviços, como descoberta de serviços (Eureka), configuração centralizada (Spring Cloud Config) e roteamento (Zuul ou Spring Cloud Gateway).
- **Spring Security com JWT:** Implementa autenticação e autorização com tokens JWT.
- **Feign Client:** Facilita a comunicação entre microsserviços de forma simples e eficiente.
- **Docker:** Containeriza os microsserviços para facilitar a implantação e o escalonamento.
- **MySQL/PostgreSQL:** Bancos de dados relacionais utilizados para armazenar informações específicas de cada microsserviço.
- **API Gateway:** Implementado com Zuul ou Spring Cloud Gateway, centralizando o roteamento de todas as requisições e facilitando a gestão de microsserviços.
- **Java**

# Banco de Dados Utilizado

- MySQL 🐬
- PostgreSQL 🐘

# ▶️ Como executar o projeto

## Back end
Pré-requisitos: MySQL, PostgreSQL, Java 17+, Maven 

```bash
# Clonar repositório

```
- Para iniciar o projeto deve-se configurar o Banco de Dados MySQL no (UserService) no Arquivo application.properties passando o usuário (username) e a senha (password) conforme mostra na imagem abaixo:

![Configurar Banco De Dados MySQL](https://github.com/douglasonline/Imagens/blob/master/Configurar_Banco_De_Dados_MySQL.png)

- Para iniciar o projeto deve-se configurar o Banco de Dados PostgreSQL no application.properties passando o usuário (username) e a senha (password) conforme mostra na imagem abaixo:

![Configurar Banco De Dados Pedido](https://github.com/douglasonline/Imagens/blob/master/Configurar_Banco_De_Dados_Pedido.png) 

```bash

# Após essas configurações do Banco de Dados pode-se executar o projeto que o Database e as tabelas serão criados automaticamentes 

```

# 📘 Como acessar o Swagger do Back end 

- Coloque no navegador o endereço: http://localhost:8080/swagger-ui/index.html#/

## Com o Swagger podemos ver as requisições que podemos realizar 

![Requisicoes de Pedidos Parte1](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte1.png) 

![Requisicoes de Pedidos Parte2](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte2.png) 

![Requisicoes de Pedidos Parte3](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte3.png) 

![Requisicoes de Pedidos Parte4](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_de_Pedidos_Parte4.png)

# 📝 Pasta com os logs 

![Logs Pedidos](https://github.com/douglasonline/Imagens/blob/master/Logs_Pedidos.png)  

# 🐳 Como utilizar o Docker

- Primeiro deve-se descomentar o código: spring.profiles.active=docker no application.properties

![Desconmentar Codigo](https://github.com/douglasonline/Imagens/blob/master/Desconmentar_Codigo.png)

- Para subir a aplicação no Docker execute o comando abaixo na pasta do projeto

```bash

# docker-compose up

```

# 🍴 Como consumir o projeto

Estou utilizando o Postman para consumir a aplicação

- Primeiro deve-se cadastra um Usuário no Microsserviço de Serviço de Usuários (UserService)
Utilizando o endereço
- http://localhost:8082/api/user/users

![Cadastrando Usuario](https://github.com/douglasonline/Imagens/blob/master/Cadastrando_Usuario.png)

- Após o cadastra de Usuário deve-se fazer login para poder obter o Token de acesso
Utilizando o endereço
- http://localhost:8082/api/user/login

![Login do Usuario](https://github.com/douglasonline/Imagens/blob/master/Login_do_Usuario.png)
   

# 👤 Autor

Douglas

https://www.linkedin.com/in/douglas-j-b2194a232/

