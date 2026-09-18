# TV Maze API Middleware Proxy

### Ejecución del proyecto
Para ejecutar este API Middleware, solo es necesario iniciar la aplicación Spring Boot desde el IDE o utilizando el comando de Maven (`mvn spring-boot:run`). 

**Nota de Seguridad sobre Credenciales:** 
Las credenciales de conexión a MongoDB Atlas se han dejado intencionalmente expuestas en el archivo `application.properties`. Esto se estructuró como una decisión técnica práctica para facilitar la revisión, ejecución y validación inmediata de esta prueba por parte del equipo evaluador, eliminando la necesidad de solicitar o configurar variables de entorno locales adicionales. 

El clúster configurado es efímero, pertenece a la capa gratuita de Atlas y es de uso exclusivo para la revisión de este ejercicio. En un entorno real de producción, estas credenciales estarían estrictamente protegidas e inyectadas mediante variables de entorno en el pipeline de despliegue o a través de un gestor de secretos (como AWS Secrets Manager o HashiCorp Vault).