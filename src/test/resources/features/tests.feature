# features/tests.feature

Funcionalidade: API de Gerenciamento de Desastres
  Como administrador do sistema
  Eu quero gerenciar usuários, localizações e desastres
  Para manter registros precisos de eventos catastróficos

  Cenário: Criação de usuário com sucesso
    Dado que eu tenho um novo usuário com os seguintes dados:
      | nome          | email          | senha    | telefone     |
      | João da Silva | joao@email.com | senha123 | 11999999999  |
    Quando eu envio uma requisição POST para "/api/users" com esses dados
    Então o status da resposta deve ser 200
    E a resposta deve conter um ID de usuário

  Cenário: Criação de localização com sucesso
    Dado que eu tenho uma nova localização com os seguintes dados:
      | nome      | latitude | longitude | tipo    |
      | São Paulo | -23.5505 | -46.6333  | Urbano  |
    Quando eu envio uma requisição POST para "/localizacoes" com esses dados
    Então o status da resposta deve ser 201
    E a resposta deve conter um ID de localização

  Cenário: Criação de registro de desastre com sucesso
    Dado que existe um usuário cadastrado
    E existe uma localização cadastrada
    Quando eu envio uma requisição POST para "/desastres" com:
      """
      {
        "tipo": "Enchente",
        "data": "2025-05-04",
        "intensidade": 4,
        "duracao": 2,
        "usuarioId": <userId>,
        "localizacaoId": <locationId>
      }
      """
    Então o status da resposta deve ser 201
    E a resposta deve conter um ID de desastre

  Cenário: Tentativa de criação de desastre com dados inválidos
    Dado que existe um usuário cadastrado
    Quando eu envio uma requisição POST para "/desastres" com dados incompletos:
      """
      {
        "tipo": "Enchente",
        "data": "2025-05-04",
        "intensidade": 4,
        "duracao": 2,
        "usuarioId": <userId>
      }
      """
    Então o status da resposta deve ser 500

  Cenário: Consulta de desastres por localização
    Dado que existem desastres registrados para "São Paulo"
    Quando eu envio uma requisição GET para "/desastres/<locationId>"
    Então o status da resposta deve ser 200
    E a resposta deve conter uma lista não vazia de desastres
