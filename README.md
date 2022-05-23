# Projeto de teste para vaga de desenvolvedor BackEnd

O teste abaixo tem como objetivo avaliar o conhecimento técnico e pontos fortes do
desenvolvedor.

Para executar o projeto, será necessário instalar os seguintes programas:

JDK 8: Necessário para executar o projeto Java
Maven: Necessário para realizar o build do projeto Java
IntelliJ: Para desenvolvimento do projeto

## DESENVOLVIMENTO

Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretorio de sua preferencia"
https://github.com/fabioPinhos/testePancary.git
```

Importar no IntelliJ/Eclipse o projeto :

```shell
ms-clientes
```


## Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório target com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.

## Configuração

Para executar o projeto, é necessário utilizar o Intellij, para que o mesmo identifique as dependências necessárias para a execução no repositório .m2 do Maven.Uma vez importado o projeto, é necessario executar a classe principal para a execução do projeto.

```shell
MsClientesApplication

http://localhost:8080/swagger-ui.html#/
```


