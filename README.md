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

A aplicação é dividida conforme abaixo é **deve** ser executada nas respetivas ordens:

**eureka**

**config-server**

**gateway**

É quadro serviços principais:

**Serviço de Usuários (UserService):**

- Responsável pelo gerenciamento dos usuários, incluindo o registro, autenticação e autorização. Utiliza o Spring Security com JWT (JSON Web Token) para garantir a segurança e a privacidade das interações.

**Serviço de Projetos (ProjectService):**

- Permite a criação e o gerenciamento de projetos aos quais as tarefas podem ser associadas. Cada tarefa pode ser vinculada a um projeto específico, facilitando a organização do trabalho. 

**Serviço de Tarefas (TaskService):**

- Gerencia o ciclo de vida das tarefas, permitindo que os usuários realizem operações de CRUD (criação, leitura, atualização e exclusão). As tarefas são categorizadas por status (pendente, em andamento, concluída) e prioridade (baixa, média, alta)
quando uma tarefa é atualizada para COMPLETED ele utiliza a **Fila** com **RabbitMQ**

**Serviço de Notificação (Notification):**

- Envia as Notificações de uma tarefa COMPLETED lida da Fila do **RabbitMQ**


# 🛠️ Tecnologias utilizadas

## Back end

- **Spring Boot:** Para construir a estrutura dos microsserviços.
- **Spring Cloud:** Usado para resolver problemas comuns em microsserviços, como descoberta de serviços (Eureka), configuração centralizada (Spring Cloud Config) e roteamento (Spring Cloud Gateway).
- **Spring Security com JWT:** Implementa autenticação e autorização com tokens JWT.
- **Feign Client:** Facilita a comunicação entre microsserviços de forma simples e eficiente.
- **Docker:** Containeriza os microsserviços para facilitar a implantação e o escalonamento.
- **MySQL:** Bancos de dados relacionais utilizados para armazenar informações específicas de cada microsserviço.
- **API Gateway:** Implementado com Spring Cloud Gateway, centralizando o roteamento de todas as requisições e facilitando a gestão de microsserviços.
- **Java**

# Banco de Dados Utilizado

- MySQL 🐬

# ▶️ Como executar o projeto

## Back end
Pré-requisitos: MySQL, Java 17+, Maven 

```bash
# Clonar repositório

```
- Para iniciar o projeto deve-se configurar o Banco de Dados MySQL no (UserService, ProjectService, TaskService, Notification) no Arquivo application.properties passando o usuário (username) e a senha (password) conforme mostra na imagem abaixo:

![Configurar Banco De Dados MySQL](https://github.com/douglasonline/Imagens/blob/master/Configurar_Banco_De_Dados_MySQL.png)

```bash

# Após essas configurações do Banco de Dados pode-se executar o projeto que o Database e as tabelas serão criados automaticamentes 

```

# 🚀 Iniciando os Microsserviços

- Primeiro deve-se ligar o **eureka**

- Segundo tem que configurar uma pasta no seu GitHub em um repositório privado com as credenciais da chave secreta para o JWT 

![Exemplo Senha JWT](https://github.com/douglasonline/Imagens/blob/master/Exemplo_Senha_JWT.png)

E tem que configurar o seu username é a sua password (Token) no **config-server** 

![Token Config Server](https://github.com/douglasonline/Imagens/blob/master/Token_Config_Server.png)

# 📘 Como acessar o Swagger  

- Para visualizar o Swagger após o Eureka iniciar clique em um dos microsserviços (UserService, ProjectService, TaskService, Notification) na porta que for aberta é substitua a porta no código: http://localhost:8080/swagger-ui/index.html#/

Primeiro clique um microsserviços (UserService, ProjectService, TaskService, Notification)

![Ver Porta Swagger](https://github.com/douglasonline/Imagens/blob/master/Ver_Porta_Swagger.png) 

Após abrir a porta

![Visualizar Porta](https://github.com/douglasonline/Imagens/blob/master/Visualizar_Porta.png)

Substitua no código: http://localhost:55471/swagger-ui/index.html#/

![Swagger Porta](https://github.com/douglasonline/Imagens/blob/master/Swagger_Porta.png)


## Com o Swagger podemos ver as requisições que podemos realizar 

- UserService

![Requisicoes UserService](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_UserService.png) 

- ProjectService

![Requisicoes ProjectService](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_ProjectService.png) 

- TaskService

![Requisicoes TaskService](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_TaskService.png) 

- Notification

![Requisicoes Notification](https://github.com/douglasonline/Imagens/blob/master/Requisicoes_Notification.png)


# 🐳 Como utilizar o Docker

- Para iniciar o Docker Basta Baixar o projeto é extrair o arquivo config-server-0.0.1-SNAPSHOT.rar que está na pasta do config-server na pasta target coloque o arquivo .jar que você extraiu dentro da pasta target

![Extrair Rar](https://github.com/douglasonline/Imagens/blob/master/Extrair_Rar.png)

- Para subir a aplicação no Docker execute o comando abaixo na pasta Raiz do Projeto

```bash

# docker-compose up

```

# 🍴 Como consumir o projeto

Estou utilizando o Postman para consumir a aplicação

- Primeiro deve-se cadastra um Usuário no Microsserviço Serviço de Usuários (UserService):
Utilizando o endereço
- http://localhost:9999/UserService/create

![Cadastrando UserService](https://github.com/douglasonline/Imagens/blob/master/Cadastrando_UserService.png)

- Após o cadastra de Usuário deve-se fazer login para poder obter o Token de acesso
Utilizando o endereço
- http://localhost:9999/UserService/auth

![UserService Token](https://github.com/douglasonline/Imagens/blob/master/UserService_Token.png)

- Para cadastra um Projeto no ProjectService devemos passar o Token para isso
Utilizamos o endereço
- http://localhost:9999/ProjectService/create

![Cadastrar ProjectService](https://github.com/douglasonline/Imagens/blob/master/Cadastrar_ProjectService.png)

![Cadastrando ProjectService](https://github.com/douglasonline/Imagens/blob/master/Cadastrando_ProjectService.png)

   

# 👤 Autor

Douglas

https://www.linkedin.com/in/douglas-j-b2194a232/

