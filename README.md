# :desktop_computer: Microserviço Ace-Backend
Microserviço desenvolvido para ser consumido pela API Ace-Frontend.

Toda lógica de consulta no banco de dados, assim como rotas de integração com o front, e operações de CRUD do sistema estarão contidas aqui.
<br></br>

## :hammer_and_wrench:  Tecnologias utilizadas 
- Java (versão 17)
- Spring Framework
- Eclipse/ VScode 
- Lombok
<br></br>
## :key: Executando o projeto
Antes de tentar executar o projeto, certifique-se que todas as tecnologias estão devidamente instaladas e funcionando corretamente, cada uma das tecnologias 
descritas precisa ser instalada individualmente. Se a IDE escolhida for o VScode, também será necessário baixar as seguintes extensões: java extension pack;
spring boot extension pack e lombok.

Assim que tudo estiver instalado, basta iniciar o editor de código abrindo a pasta raiz do projeto, e apertar no botão de "play" localizado no canto superior 
do editor, para ligar a aplicação é preciso fazer isso no arquivo que contém a função principal: "Api3DsmApplication.java".
<br></br>
## :game_die: Banco de dados
Por enquanto, o projeto utliza do banco de dados relacional em memória chamado "H2", esse banco vem junto com algumas dependências selecionadas do spring e ele
não necessita de nenhuma configuração, porém, sempre que a aplicação for reiniciada, os dados armazenados serão excluídos.
