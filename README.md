# Sistema de Gestão de Pedidos com Arquitetura de Microsserviços Assíncrona

Este projeto é uma implementação de um sistema simplificado de Gestão de Pedidos Online, demonstrando uma arquitetura de microsserviços com comunicação assíncrona baseada em eventos. [cite_start]A solução foi desenvolvida para resolver problemas comuns em sistemas distribuídos, como alto acoplamento e lentidão em cascata, que ocorrem em modelos de comunicação síncrona[cite: 3].

## Visão Geral da Arquitetura

[cite_start]O sistema é composto por dois microsserviços independentes que se comunicam através de um broker de mensagens (RabbitMQ), adotando um modelo de comunicação assíncrona baseada em eventos[cite: 6].

1.  [cite_start]**Serviço de Pedidos (`servico-pedidos`)**: Responsável por receber e registrar novos pedidos no sistema[cite: 10]. [cite_start]Ao receber uma nova requisição, ele publica um evento `NovoPedidoCriado` em um tópico[cite: 11].
2.  [cite_start]**Serviço de Faturamento (`servico-faturamento`)**: Consome os eventos `NovoPedidoCriado` da fila[cite: 12]. [cite_start]Ele processa o pagamento do pedido de forma desacoplada, sem que o cliente precise esperar pela conclusão do faturamento para ter seu pedido confirmado[cite: 13, 14].

[cite_start]Esse modelo reduz o acoplamento, aumenta a resiliência e a escalabilidade do sistema[cite: 8].

### Tecnologias Utilizadas

A solução foi construída utilizando as seguintes tecnologias:

* [cite_start]**Microsserviços**: Spring Boot [cite: 16]
* [cite_start]**Comunicação Assíncrona**: Spring Cloud Stream com RabbitMQ [cite: 17]
* [cite_start]**Gerenciador de Dependências**: Maven [cite: 18]
* [cite_start]**Orquestração do Broker**: Docker [cite: 19]
* **Banco de Dados**: Nenhum banco de dados persistente é utilizado nesta demonstração. Os dados de faturamento são mantidos em memória.

## Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas em sua máquina:

* **Java 11** (ou superior)
* **Apache Maven**
* **Docker** e **Docker Compose**

## Como Executar a Aplicação

Siga os passos abaixo para replicar o ambiente e executar a aplicação.

### 1. Estrutura de Arquivos

Organize os arquivos do projeto conforme a estrutura abaixo:

```
gestao-pedidos-online/
|
|-- servico-pedidos/
|   |-- src/
|   `-- pom.xml
|
|-- servico-faturamento/
|   |-- src/
|   `-- pom.xml
|
`-- docker-compose.yml
```

### 2. Iniciar o Message Broker (RabbitMQ)

O RabbitMQ será executado como um contêiner Docker. Na raiz do projeto (`gestao-pedidos-online`), execute o seguinte comando:

```bash
docker-compose up -d
```

Isso iniciará o RabbitMQ em background. O broker estará disponível na porta `5672` e a interface de gerenciamento na porta `15672`.

### 3. Iniciar o Serviço de Faturamento

Abra um **novo terminal**, navegue até a pasta do serviço de faturamento e execute o comando Maven para iniciar a aplicação:

```bash
cd servico-faturamento
./mvnw spring-boot:run
```

Este serviço irá iniciar na porta `8081` e começará a "ouvir" por mensagens na fila do RabbitMQ.

### 4. Iniciar o Serviço de Pedidos

Abra **outro terminal**, navegue até a pasta do serviço de pedidos e inicie a aplicação:

```bash
cd servico-pedidos
./mvnw spring-boot:run
```

Este serviço irá iniciar na porta `8080`.

Ao final desses passos, todo o ambiente estará no ar e pronto para ser utilizado.

## Como Utilizar a Aplicação

A aplicação possui interfaces web simples para facilitar a interação.

### 1. Criar um Novo Pedido

* Abra seu navegador e acesse: **`http://localhost:8080`**
* Você verá um formulário para criar um novo pedido. Preencha os campos e clique em "Enviar Pedido".
* Após o envio, você será redirecionado para uma página de sucesso, confirmando que o pedido foi enviado para processamento.

### 2. Visualizar Pedidos Faturados

* Em outra aba do navegador, acesse: **`http://localhost:8081`**
* Esta página exibe a lista de todos os pedidos que foram consumidos e processados pelo serviço de faturamento.
* A página se atualiza automaticamente a cada 5 segundos para exibir novos pedidos que foram faturados.

### 3. Visualizar a Fila no RabbitMQ

Para observar o comportamento da comunicação assíncrona, você pode inspecionar as filas diretamente no RabbitMQ.

* Acesse a interface de gerenciamento: **`http://localhost:15672`**
* **Login**: `guest`
* **Senha**: `guest`
* Navegue até a aba **"Queues"**. Você encontrará a fila `pedidos-exchange.faturamento-group`.

**Teste de Resiliência:** Para ver a fila em ação, pare o `servico-faturamento`, envie um novo pedido pelo formulário e observe a mensagem aparecer na fila do RabbitMQ. Ao reiniciar o serviço, a mensagem será consumida e processada.