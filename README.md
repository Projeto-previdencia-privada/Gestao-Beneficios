# Gestão de Contribuições

A gestão de beneficios é uma API REST de uma sistema de gerenciamento de beneficios cedidos de uma previdência privada, sendo sua função receber solicitações,analisa-las e cadastra-las no banco de dados caso permitido.

## Tecnologias Utilizadas

1. Spring Boot
2. PostgreSQL
3. Docker-compose

## Criação do .env

O projeto precisa de um arquivo criado com o nome de .env na raiz do projeto com a seguinte estrutura:
```
POSTGRES_USER=<usuario_postgres>
POSTGRES_PASSWORD=<senha_user>
PGPORT=<porta_postgres>
```
Atente-se ao fato que pode-se mudar a porta do banco de dados para uma a sua escolha, caso não tenha interesse coloque em PGPORT a porta 5432 sendo a mesma a padrão do postgres. 

## Execução do projeto

Utilize o seguinte na raiz do projeto comando para subir o docker-compose com todos os projetos

```
docker compose up -d
```

## Chamadas API

Para chamar a API e ver o projeto funcionando é preciso ter o [Postman](https://www.postman.com) ou outro software parecido e executar as chamadas API descritas na [Assinatura do projeto](https://github.com/Projeto-previdencia-privada/Documentacao/blob/main/Gestão%20de%20Beneficios%20-%20Documentação/Assinatura_API.yaml) atentando-se que algumas chamadas são dependentes das outras API´s para funcionarem logo para testa-las é necessário te-las rodando na máquina.




