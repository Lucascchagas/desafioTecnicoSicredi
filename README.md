
Desafio Técnico Sicredi


Informações Mínimas do Projeto
O projeto desafioTecnicoSicredi abrange uma aplicação de automação de testes de API desenvolvida em Java e TestNG. Objetiva-se garantir a qualidade das API's das aplicação, otimizando a eficiência dos processos de desenvolvimento. Ressalte-se que a IDE utilizada foi o Eclipse.

Como Executar
Para IDE Eclipse, é necessário utilizar o plugin TestNG for Eclipse, do Eclipse Marketplace. Após, executar os testes via: Run as -> run configuration -> TestNG -> Project desafioTecnicoSicredi -> Package sicrediApiTest -> Aba de Arguments e acrescentar o seguinte comando (recomendamamos o primeiro, ambiente de dev) em VM arguments:

-Denv=dev

ou 

-Denv=qa

ou

-Denv=starting

Pré-requisitos
Java JDK (versão 1.8.0_202 ou superior)
Apache Maven (versão 3.9.4 ou superior)

Configuração
Clone o repositório:

git clone https://github.com/usuario/projeto.git
cd projeto

Instale as dependências usando o Maven:

mvn clean install


Execução dos Testes
Para executar os testes de integração contínua, utilize o seguinte comando (o projeto apenas está configurado - AS EXECUÇÕES, ATUALMENTE, SÃO EXCLUSIVAMENTE LOCAIS):

mvn test


Plano de Teste e Estratégia de Testes
Nos pautamos pelo uso do TestNG e de um projeto padronizável, ou seja, as classes java BaseTest, APIPath e HeaderConfigs possuem, respectivamente, a url base e quaisquer endpoints e headers que possam existis nas requisições. 
A classe java APIVerification permite a validação do retorno da requisição: status code, tipo de response body e suas respectivas informações. Outrossim, fornecemos o tempo de execução dos testes (teste de desempenho), mediante o método responseTimeValidation.
A classe java FileandEnv permite que os testes sejam executados em diferentes ambientes.
As requisições GET e POST fornecidas foram agrupadas em classes java próprias, de modo que cada uma possui o cenário de sucesso (status code 200) e os cenários negativos possíveis (status code 400, 401, 403, 404...).


Bugs
[Bug #1 GET /auth/products]: [Eventual token incorreto, ao invés de resultar em status code 401, unauthorized, retorna status code 500, sob a mensagem "invalid token"]
[Bug #2 POST /products/add]: [É preciso validar quais dados do request body são obrigatórios, visto que a retirada de um parâmetro não implica, necessariamente, em Bad Request (foi verificado que apenas o parâmetro "thumbnail" gera Bad Request - os demais retornam status code 200)]
[Bug #3 POST /products/add]: [É preciso validar quais são os tipos aceitos por cada parâmetro do request body, visto que, ao indicar o parâmetro "price" como String, a requisição retornou com sucesso, embora o valor seja "gg"]
[Bug #4 POST /products/add]: [Se o Header Conten-Type = application/json é retirado da request, o response body retorna somente com o id do produto, sem mais nenhuma informação (title, price, stock...). Entendo que devemos deixá-lo como header de uso obrigatório, sob risco de retornar Bad Request eventual requisição sem sua indicação]
[Bug #5 GET /products]:  [Embora a listagem completa do response body da GET /products indique somente 30 id's, se filtrarmos um eventual id 31 na GET /products/31 ou um número superior, continuam a retornar informações no response body. Destarte, não estão retornando todos os id's na listagem completa]

Melhorias
[Melhoria #1 Content-Type]: [As requisições GET /auth/products e GET /products possuem o header Content-Type = application/json, contudo, não se trata de um parâmetro obrigatório, uma vez que, em sua ausência, a requisição continua a retornar com status code 200]