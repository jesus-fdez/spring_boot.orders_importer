# Spring boot, orders importer.

Proyecto en Spring Boot para importar pedidos desde API Rest. La API está basada en el principio HATEOAS, así que la paginación nos viene dada por link. Basado en arquitectura hexagonal.

## Dependencias

- Spring boot
- Mysql connector
- JPA
- Lombok (provided)
- ModelMapper
- EasyRandomCore

## Launch

1. Instalar dependencias: `mvn clean install`
2. Lanzar aplicación:  `mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xms2048m -Xmx4096m"`
3. Iniciar la importación: Petición GET a `http://127.0.0.1:8081/api/v1/import`
4. Lanzar test suite: `mvn test`
