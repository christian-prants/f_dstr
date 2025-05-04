# features/tests.feature

Funcionalidade: Gerenciamento de Desastres
  Como administrador do sistema
  Eu quero gerenciar registros de desastres
  Para manter informações atualizadas

  Cenário: Cadastro bem-sucedido de desastre
    Dado que estou autenticado como administrador
    Quando envio uma requisição POST para "/api/disasters" com:
      """
      {
        "name": "Enchente",
        "location": "São Paulo",
        "severity": "HIGH"
      }
      """
    Então a resposta deve ter status 201
    E o corpo da resposta deve conter um ID
    E o banco de dados deve conter o novo registro

  Cenário: Tentativa de cadastro com dados inválidos
    Dado que estou autenticado como administrador
    Quando envio uma requisição POST para "/api/disasters" com:
      """
      {
        "name": "",
        "location": "São Paulo",
        "severity": "INVALID"
      }
      """
    Então a resposta deve ter status 400
    E o corpo da resposta deve conter mensagens de erro

  Cenário: Consulta de desastres por localização
    Dado que existem desastres cadastrados em "São Paulo" e "Rio de Janeiro"
    Quando envio uma requisição GET para "/api/disasters?location=São Paulo"
    Então a resposta deve ter status 200
    E deve retornar apenas desastres de "São Paulo"