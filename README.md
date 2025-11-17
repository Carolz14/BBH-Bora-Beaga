# BBH -  Bora Beagá
Bora Beagá é um sistema que visa auxiliar turistas e moradores da cidade de Belo Horizonte (MG), servindo de guia para conhecer as maravilhas da cidade. O objetivo é apresentar sugestões selecionadas dos principais pontos turísticos, parques, restaurantes, bares, hoteis e outros locais de entretenimento, além de mapas e meios de transporte para chegar aos locais.

## Equipe de Desenvolvimento
| Ordem | Nome          |
|:------|:--------------|
| 1     | Artur Dias Alves Pires       |
| 2     | Carolina Moreira Braga      |
| 3     | Leandro Mateus Mendes          |
| 4     | Guilherme Yuri Timoteo Placido    |
| 5     | Ana Flávia Arrudas de Sousa       |
| 6     | Daniel Henrique Antunes        |

## Atores do Sistema
| Ator      | Definição     |
|:----------|:--------------|
| Turista   | Usuário com cargo de Turista, utilizará o sistema para buscar e avaliar locais.          |
| Estabelecimento  | Usuário com cargo de Estabelecimento, utilizará o sistema para promover seu negócio, podendo adicionar publicações e comentários.     |
| Guia Turístico | Usuário com cargo de Guia Turístico, utilizará o sistema para criar roteiros.          |
| Administrador | Usuário com cargo de Administrador, utilizará o sistema para adicionar pontos turísticos, fazer possíveis manutenções simples no sistema e oferecer suporte.           |

## Requisitos Funcionais
| Id     | Ator       | Descrição   |
|:-------|:-----------|:------------|
| REQ001 | Turista | Criar um perfil para o turista, registrando seu nome, naturalidade e e-mail. |
| REQ002 | Turista | Consultar seu perfil, podendo ver, atualizar os dados ou excluir sua conta. |
| REQ003 | Guia Turístico | Criar um perfil para o guia turístico, registrando seu nome e e-mail. |
| REQ004 | Guia Turístico | Consultar seu perfil, podendo ver, atualizar os dados ou excluir sua conta. |
| REQ005 | Estabelecimento | Criar um perfil para o estabelecimento, registrando seu nome, CNPJ, endereço e meios de contato. |
| REQ006 | Estabelecimento | Consultar seu perfil, podendo ver, atualizar os dados ou excluir sua conta. |
| REQ007 | Administrador | Criar um perfil para o estabelecimento, registrando seu nome, CNPJ, endereço e meios de contato. |
| REQ008 | Administrador | Consultar seu perfil, podendo ver, atualizar os dados ou excluir sua conta. |
| REQ009 | Administrador | Poderá adicionar pontos turísticos da cidade, que não sejam necessariamente um estabelecimento. |
| REQ010 | Turista, Guia Turístico | Será possível acessar o perfil dos estabelecimentos, vendo fotos/vídeos dos locais, avaliações, preço e contato. |
| REQ011 | Estabelecimento | Será possível categorizar o estabelecimento por tipo e estilo. |
| REQ012 | Turista, Guia Turístico | Será possível pesquisar estabelecimentos e locais no sistema. |
| REQ013 | Turista | Avaliar os locais e estabelecimentos, com comentários que podem ou não conter fotos e vídeos. |
| REQ014 | Guia Turístico | Criar roteiros turísticos, conjuntos de locais para visitar em um dia. |
| REQ015 | Turista | Avaliar os roteiros feitos pelos guias com estrelas. |
| REQ016 | Turista | Demonstrar interesse nos roteiros turísticos. |
| REQ017 | Turista | Poderá fazer e ver comentários nos roteiros turísticos, estilo fórum de discussão. |
| REQ018 | Turista | Poderá interagir com um mapa que mostre a localização do estabelecimento e monte uma rota de chegada. |
| REQ019 | Estabelecimento | Deve ser possível publicar eventos e atrações no estabelecimento. |
| REQ020 | Turista | Visualizar eventos da agenda, sendo possível demonstrar interesse nos eventos. |
| REQ021 | Sistema | O sistema enviará notificações sobre os eventos e fóruns que o usuário estiver participando. |
| REQ022 | Turista | O turista poderá ver os estabelecimentos/locais e salvá-los em uma lista de interesse. |
| REQ023 | Estabelecimento | O estabelecimento poderá promover seu negócio, adicionando propagandas e promoções para usuários. |
| REQ024 | Sistema | O sistema terá um ranking de estabelecimentos mais bem avaliados. |
| REQ025 | Estabelecimento | Os estabelecimentos poderão ver um relatório sobre a forma que ele está sendo visto no sistema, avaliações, número de visitas ao perfil… |
| REQ026 | Sistema | O sistema armazenará as preferências de usuário, para fazer recomendações mais específicas para cada situação. |
| REQ027 | Estabelecimento e Guia Turístico | Será possível conversar com um suporte do próprio sistema para tirar dúvidas e fazer sugestões. |


## Regras de Negócio
| Id     | Nome       | Descrição   |
|:-------|:-----------|:------------|
| RN001  | CPF válido  | O CPF deve ter 11 dígitos e ser verdadeiro.    |
| RN002  | CNPJ válido  | O CNPJ deve ter 14 dígitos e estar ativo.    |
| RN003  | E-mail válido e confirmado    | O e-mail deve ser válido e ter sido confirmado pelo usuário.     |
| RN004  | Data válida  | O evento deve ser cadastrada em uma data válida (do futuro)    |
| RN005  | Avaliação em estrelas    | A avaliação deverá ser feita em um sistema de 1 a 5 estrelas   |
| RN006  |Roteiros com distâncias válidas    | Os roteiros de passeio deverão ser feitos com locais que estejam próximos um do outro, sendo possível visitá-los em um dia.    |
| RN007  | E-mail válido e confirmado    | O usuário deverá permitir o envio de notificações    |



## Casos de Uso
| Id     | Nome       | Requisitos     | Regras de Negócio  |
|:-------|:-----------|:---------------|:-------------------|
| CSU01  | Gestão de perfis de usuários              | REQ001, REQ002, REQ003, REQ004, REQ005, REQ006, REQ007, REQ008  | RN001, RN002, RN003  |
| CSU02  | Registro de pontos turísticos             | REQ009 |       |
| CSU03  | Visualização de perfil dos usuários       | REQ010 |       |
| CSU04  | Categorizar localidades                   | REQ011 |       |
| CSU05  | Pesquisa de locais                        | REQ012 |       |
| CSU06  | Avaliação de locais e estabelecimentos    | REQ013 | RN005 |
| CSU07  | Criação de roteiros turísticos            | REQ014 | RN006 |
| CSU08  | Interação com roteiros turísticos         | REQ015, REQ016 | RN005 |
| CSU09  | Fórum de discussão dos roteiros           | REQ017 |       |
| CSU10  | Mapa interativo                           | REQ018 |       |
| CSU11  | Publicação de eventos                     | REQ019 | RN004 |
| CSU12  | Gestão de agenda                          | REQ020 | RN004 |
| CSU13  | Gestão de notificações                    | REQ021 | RN007 |
| CSU14  | Gestão de locais de interess              | REQ022 |       |
| CSU15  | Promoção de estabelecimentos              | REQ023 |       |
| CSU16  | Ranking de estabelecimentos               | REQ024 |       |
| CSU17  | Gestão de relatórios dos estabelecimentos | REQ025 |       |
| CSU18  | Gestão de preferências de usuário         | REQ026 |       |
| CSU19  | Suporte ao estabelecimento e guia         | REQ027 |       |
| CSU20  | Gestão de login                           | REQ001, REQ002, REQ003, REQ004, REQ005, REQ006, REQ007, REQ008  |       |



## Planejamento
| Sprint | Caso de Uso | Desenvolvedor  |
|:-------|:------------|:---------------|
| 1      | CSU01       | 1              |
| 1      | CSU20       | 2              |
| 1      | CSU04       | 3              |
| 1      | CSU05       | 4              |
| 1      | CSU03       | 5              |
| 1      | CSU02       | 6              |
| 2      | CSU15       | 1              |
| 2      | CSU07       | 2              |
| 2      | CSU06       | 3              |
| 2      | CSU11       | 4              |
| 2      | CSU02       | 6              |
| 3      | CSU12 + CSU14   | 1          |
| 3      | CSU08 + CSU09   | 2          |
| 3      | CSU16           | 3          |
| 3      | CSU17 + CSU19   | 4          |
| 3      | CSU10           | 6          |
