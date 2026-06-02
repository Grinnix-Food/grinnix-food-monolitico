# Grinnix Food - Sistema Monolitico

Essa é a implementação do sistema monolitico do Grinnix-food, um sistema interno de um restaurante que gerencia e processa pedidos de diveros clientes simultâneamente enquanto simula pequenos testes de extresse no sistema.

## Arquitetura e fluxo principal do sistema

O sistema monolitico do Grinnix-food é baseado no padrão arquitetural do mvc com a adaptação e uso dos services e repositoris, afim de separar as responsabilidades do model entre service e repositóry. Essa divisão permitiu reduzir o acoplamento entre componentes e tornou a aplicação mais simples de evoluir durante o desenvolvimento.

O fluxo inicia-se com a criação de um pedido pelo usuário. Após sua criação, é possível adicionar itens do cardápio (Products) ao pedido conforme desejado. Quando o usuário conclui a seleção dos produtos, o pedido é fechado, gerando automaticamente uma ordem de pagamento associada.

Em seguida, a ordem de pagamento pode ser processada por meio do endpoint de Payment, responsável por simular a integração com um serviço externo de pagamentos através de um webhook. Após a confirmação do pagamento, o sistema realiza o envio de um e-mail para a cozinha contendo as informações necessárias para o preparo do pedido.

Para fins de demonstração e testes, os e-mails enviados são capturados pelo EmailHog, permitindo a visualização dessa comunicação sem a necessidade de um servidor de e-mail real. O serviço pode ser acessado pela porta 8025.


### Dificuldades no Desenvolvimento e Perspectivas Futuras para o Crescimento do Modulo de Pedidos

Durante o desenvolvimento do sistema, uma das principais dificuldades encontradas esteve relacionada à modelagem inicial do domínio da aplicação. Como uma pequena parte dos requisitos ainda  apresentavam ambiguidades naturais do processo de levantamento, diversas decisões de projeto precisaram ser revistas ao longo da implementação.

Em um primeiro momento, o fluxo de pedidos foi concebido como uma estrutura relativamente simples, responsável apenas por registrar os produtos selecionados por um cliente. Entretanto, conforme o entendimento do problema amadureceu e o surgimento de novos requisitos, afim de tornar o fluxo do sistema  coerente foi  necessário expandir significativamente as responsabilidades desse módulo.

Funcionalidades que inicialmente pareciam independentes passaram a depender diretamente do fluxo de pedidos. O sistema passou a exigir o gerenciamento de itens individuais, controle de quantidades, cálculo automático de valores, geração de ordens de pagamento, processamento de transações financeiras e envio de notificações após a conclusão das operações. Como consequência, o pedido deixou de ser apenas um registro de compra e passou a representar o núcleo operacional da aplicação.

Essa centralização repentina trouxe benefícios importantes, como a simplificação do fluxo operacional e a manutenção de uma visão unificada do processo de compra. Entretanto, também tornou evidente que futuras evoluções deverão ser planejadas com cuidado para evitar o aumento excessivo do acoplamento entre componentes.

## Perspectivas de Crescimento

Considerando um cenário de crescimento da aplicação, é possível prever que o módulo de pedidos continuará sendo a área com maior tendência de expansão. Funcionalidades comuns em sistemas de restaurantes e plataformas de delivery, como cupons de desconto, programas de fidelidade, promoções sazonais, cancelamentos, reembolsos, acompanhamento de status e integração com serviços externos, possuem uma forte dependência desse domínio.

Outro ponto relevante diz respeito ao catálogo de produtos. Embora atualmente ele possua uma estrutura relativamente simples, um aumento na quantidade de itens disponíveis poderá exigir mecanismos adicionais de organização, busca e cache para garantir consultas rápidas e eficientes.

Dessa forma, apesar da arquitetura monolítica atender plenamente aos objetivos propostos para o projeto, a evolução observada durante o desenvolvimento demonstra que o domínio de pedidos possui potencial para se tornar o principal candidato a futuras estratégias de escalabilidade e modularização, caso a aplicação venha a operar em cenários com maior volume de usuários e transações.

Considerando a importância desse fluxo para o funcionamento do sistema, foram realizados testes de desempenho e resiliência em componentes críticos da aplicação, com destaque para o módulo de pagamentos, responsável por intermediar a conclusão dos pedidos.


# teste de lentidão do modulo de pagamento

O objetivo desse teste é simular o atraso do `service de pagamento` enquanto processa o pagamento de algum pedido em especifico, afim de verificar se ocorre algum problema no restante da aplicação por causa desse atraso que pode ocorrer normalmente em qualquer sistema real, sejá por problemas de processamento ou por causa de latência.

O sistema monolitico do grinnix-food por padrão já está com um atraso de `5s` ao realizar o processamento do pagamento de um pedido, para testar esse comportamento foi realizado a criação de um script que fica realizando requisições que simulam vário usuário interagindo com o nosso sistema e afim de aumentar a base de teste, um usuário real continuo realizando requisições enquanto o script ainda estava operante.

Analisando o comportamente do sistema em paralelo com essa lentidão não houve nenhuma falha ou complicação aparanete durante todo o uso do sistema, mesmo com o delay de `5s` no `service de pagamento`, apenas tivmos um acumulo na demora para finalizar os pagamentos na rota de payment.

Caso queira realizar os testes por si mesmo, execute `docker compose up -d --build` para executar o sistema e `docker logs -f k6-load-test` para acompanhar os testes realizados no endpoint nos primeiros 5 minutos no ar 


# Como rodar

O projeto foi planejado para usar a sua infra-estrutura com containers docker, onde o docker compose irá orquestrar para você tanto o banco de dados como o serviço de email ( mailhog ) que captura cada envio de email. O docker compose desse repositório também é responsável por realizar o build da aplicação e executa-la em um container, então para subir toda a infraestrutura e buildar e executar a aplicação do grinnix-food basta executar o seguinte comando:

```
  docker compose up -d -> versão sem a cli do docker compose instalada
  docker-compose up -d -> versão com a cli do docker compose
```
