# eiv

Modificaciones en el esquema de base de datos:

* Se agregó AUTO_INCREMENT a las columnas id de las tablas: tipos_documentos, provincias, localidades
* Se eliminó la restricción de valor único a la columna region de la tabla provincias, para permitir que dos provincias distintas pertenezcan a la misma region.

Los datos producidos y consumidos por el API serán siempre en JSON, excepto para la foto de la entidad persona que deberá ser de tipo jpeg.
Se deben agregar el header "Accept: application/json" o "Content-Type: application/json" según corresponda y para las fotos "Accept: image/jpeg" o "Content-Type: application/jpeg".

Para acceder al sistema hay que loguearse con la siguiente petición:

POST http://localhost:8080/eiv/signin
    header: 
        "Content-Type: application/json"
    body: 
        {
            "nombre": "admin",
            "pwd": "admin"
        }

Esto devolverá un token que se usará para las consultas posteriores. El token tiene un tiempo de expiración de 1 hora.
Para el primer ingreso se puede usar usuario y password admin. A esta conbinación se le permite el acceso por código (tal usuario y contraseña no están guardados en base de datos).

Para realizar el resto de las consultas y modificaciones se debe pasar el token recibido en el header como en el siguiente ejemplo:

GET http://localhost:8080/eiv/personas/all
  header:
        "Accept: application/json"
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZmVkZXJlciIsImlhdCI6MTU1MDExNTY0NiwiZXhwIjoxNTUwMTE5MjQ2fQ.2yIvPTywDx8JdGqo-sG6AlpW7uRWNq3ydW53ibQ34wE"

Esta consulta traerá la información de todas las personas creadas.

En los siguientes ejemplos se omite el header de autenticación para simplificar. 

Para acceder a los recursos se utilizan los siguientes métodos:
    
    GET http://localhost:8080/eiv/recurso/{id} 
        - Para obtener una instancia de la entidad. El recurso puede ser: provincias, localidades, tiposdocumentos.
        
    GET http://localhost:8080/eiv/recurso?tipodoc=DNI&numdoc=35152588 
        - Para obtener una instancia de persona o usuario se le pasa como parámetros la abreviatura del tipo de documento y el número de documento. El recurso puede ser: personas, usuarios. En el caso de personas, no se obtiene la imagen. Para obtener la misma se usa el ejemplo siguiente.
        
    GET http://localhost:8080/eiv/personas/foto?tipodoc=DNI&numdoc=35152588
        - Además se debe pasar en el header "Accept: image/jpeg"
        
    GET http://localhost:8080/eiv/recurso/all 
        - Para obtener una lista de todas las instancias del recurso. Sirve para todos los recursos.
        
    POST http://localhost:8080/eiv/recurso
        - Se usa para dar de alta nuevas instancias de todas las entidades. 
        - En el recurso personas no se pasa el campo foto_cara al JSON. Si bien se podría haber hecho con una codificación por ejemplo en base64, se decidió usar el método PATCH para agregar la foto posteriormente, cuando la instancia de persona ya está creada.
    
    PUT/PATCH/DELETE http://localhost:8080/eiv/recurso/{id}
        - Para actualizar completamente/actualizar parcialmente/eliminar un recurso. El recurso puede ser: provincias, localidades, tiposdocumentos.
        
    PUT/PATCH/DELETE http://localhost:8080/eiv/recurso?tipodoc=DNI&numdoc=35152588 
        - Análogo para los recursos de personas y usuarios.
        
    PATCH http://localhost:8080/eiv/personas/foto?tipodoc=DNI&numdoc=123456789
        - Para añadir una foto a la persona. Además se debe pasar en el header "Content-Type: image/jpeg"

Los passwords se guardan codificados en base de datos utilizando la función de hashing bcrypt.

