Funcionalidade: API de Gerenciamento de Desastres
  Como administrador do sistema
  Eu quero gerenciar usuários, localizações e desastres
  Para manter registros precisos de eventos catastróficos

Cenário: Criação de usuário com sucesso
  Dado que o sistema está configurado
  E um novo usuário com:
    | nome          | email          | senha    | telefone     |
    | João da Silva | joao@email.com | senha123 | 11999999999  |
  Então o sistema retorna status 200
  E contém um ID válido

Cenário: Criação de localização com sucesso
  Dado que o sistema está configurado
  E uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  Então o sistema retorna status 201
  E contém um ID válido

Cenário: Criação de registro de desastre com sucesso
  Dado que o sistema está configurado
  E um novo usuário com:
    | nome          | email             | senha    | telefone    |
    | Maria Santos  | maria@email.com   | senha456 | 11988888888 |
  E uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  Quando submeto um novo desastre com:
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
  Então o sistema retorna status 201
  E contém um ID válido

Cenário: Tentativa de criação de desastre com dados inválidos
  Dado que o sistema está configurado
  E um novo usuário com:
    | nome         | email            | senha    | telefone     |
    | Carlos Souza | carlos@email.com | senha789 | 11977777777  |
  Quando submeto um novo desastre com:
    """
    {
      "tipo": "Enchente",
      "data": "2025-05-04",
      "intensidade": 4,
      "duracao": 2,
      "usuarioId": <userId>
    }
    """
  Então o sistema retorna status 500

Cenário: Consulta de desastres por localização
  Dado que o sistema está configurado
  E um novo usuário com:
    | nome       | email           | senha    | telefone     |
    | Ana Costa  | ana@email.com   | senha321 | 11966666666  |
  E uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  Quando submeto um novo desastre com:
    """
    {
      "tipo": "Deslizamento",
      "data": "2025-05-04",
      "intensidade": 3,
      "duracao": 1,
      "usuarioId": <userId>,
      "localizacaoId": <locationId>
    }
    """
  E busco desastres por localização
  Então o sistema retorna status 200
  E lista de desastres não está vazia
