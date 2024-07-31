# language: en
Feature: Gestionar mascotas

  @AgregarMascotasNuevas
  Scenario: Agregar mascotas nuevas en el sistema
    Given el cliente tiene los datos de la nueva mascota
    """
        {
      "id": 44,
      "name": "doggie43",
      "category": {
        "id": 1,
        "name": "Dogs"
      },
      "photoUrls": [
        "string"
      ],
      "tags": [
        {
          "id": 0,
          "name": "string"
        }
      ],
      "status": "available"
    }
    """
    When el cliente realiza una peticion POST a "/pet" con los detalles de la nueva mascota
    Then el servidor debe de responder con status 200
    And el cuerpo de la respuesta debe contener los detalles del nuevo tipo de mascota registrado

  @ActualizarMascotas
  Scenario: Actualizar mascotas en el sistema
    Given el cliente tiene los datos de la mascota a actualizar
    """
        {
      "id": 44,
      "name": "doggiee",
      "category": {
        "id": 1,
        "name": "Dogs"
      },
      "photoUrls": [
        "string"
      ],
      "tags": [
        {
          "id": 0,
          "name": "string"
        }
      ],
      "status": "available"
    }
    """
    When el cliente realiza una peticion PUT a "/pet" con los detalles de la mascota actualizada
    Then el servidor debe de responder con status 200
    And el cuerpo de la respuesta debe contener los detalles de la mascota actualizada

  @ListarMascotasPorStatus
  Scenario Outline: Listar todas las mascotas por estatus
    Given el cliente configura la URI base
    When el cliente realiza una peticion GET a <uri>
    Then el servidor debe de responder con status <statusCode>
    And el cuerpo de la respuesta contiene la propiedad status con el valor <petStatus>
    And el cuerpo de la respuesta contiene la propiedad id con el valor <petId>
    Examples:
      | uri       | statusCode | petStatus   | petId |
      | "/pet/44" | 200        | "available" | 44    |

  @ListarMascotasPorId
  Scenario Outline: Listar mascota por ID
    Given el cliente configura la URI base
    When el cliente realiza una peticion GET a <uri>
    Then el servidor debe de responder con status <statusCode>
    And el cuerpo de la respuesta contiene la propiedad id con el valor <petId>
    Examples:
      | uri       | statusCode | petId |
      | "/pet/44" | 200        | 44    |

  @EliminarMascotaExistente
  Scenario: Eliminar una mascota existente
    Given el cliente configura la URI base
    When el cliente realiza una peticion DELETE a "/pet/{id}" con id tipo de mascota eliminado 44 y apiKey 44
    Then el servidor debe de responder con status 200

  @MascotasPorIdNoEncontrada
  Scenario Outline: Mostrar mascota buscada por ID no encontrada
    Given el cliente configura la URI base
    When el cliente realiza una peticion GET a <uri>
    Then el servidor debe de responder con status <statusCode>
    Examples:
      | uri        | statusCode |
      | "/pet/100" | 404        |
#
