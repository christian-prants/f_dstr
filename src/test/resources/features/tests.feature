Feature: API de Gerenciamento de Desastres
  Como administrador do sistema
  Eu quero gerenciar usuários, localizações e desastres
  Para manter registros precisos de eventos catastróficos

Scenario: Criação de usuário com sucesso
  Given que o sistema está configurado
  And um novo usuário com:
    | nome          | email          | senha    | telefone     |
    | João da Silva | joao@email.com | senha123 | 11999999999  |
  Then o sistema retorna status 200
  And contém um ID válido

Scenario: Criação de localização com sucesso
  Given que o sistema está configurado
  And uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  Then o sistema retorna status 201
  And contém um ID válido

Scenario: Criação de registro de desastre com sucesso
  Given que o sistema está configurado
  And um novo usuário com:
    | nome          | email             | senha    | telefone    |
    | Maria Santos  | maria@email.com   | senha456 | 11988888888 |
  And uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  When submeto um novo desastre com:
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
  Then o sistema retorna status 201
  And contém um ID válido

Scenario: Tentativa de criação de desastre com Givens inválidos
  Given que o sistema está configurado
  And um novo usuário com:
    | nome         | email            | senha    | telefone     |
    | Carlos Souza | carlos@email.com | senha789 | 11977777777  |
  When submeto um novo desastre com:
    """
    {
      "tipo": "Enchente",
      "data": "2025-05-04",
      "intensidade": 4,
      "duracao": 2,
      "usuarioId": <userId>
    }
    """
  Then o sistema retorna status 500

Scenario: Consulta de desastres por localização
  Given que o sistema está configurado
  And um novo usuário com:
    | nome       | email           | senha    | telefone     |
    | Ana Costa  | ana@email.com   | senha321 | 11966666666  |
  And uma nova localização com:
    | nome      | latitude | longitude | tipo    |
    | São Paulo | -23.5505 | -46.6333  | Urbano  |
  When submeto um novo desastre com:
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
  And busco desastres por localização
  Then o sistema retorna status 200
  And lista de desastres não está vazia
