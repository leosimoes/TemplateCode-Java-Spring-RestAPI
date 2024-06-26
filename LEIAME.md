# TemplateCode - Java - Spring - Rest API
Autor: Leonardo Simões

Modelo de código para uma API Rest na linguagem Java com Spring Framework e Gradle.
A API possui dois recursos: MainResource e AssociatedResource.


## Etapas do desenvolvimento
As etapas de desenvolvimento do projeto foram:

1. Criar projeto (no IntelliJ) com:
- Linguagem Java (17);
- Spring Framework (6.2.3);
- Dependências: Web, DevTools, Lombok, JPA, H2 e Validation.

![Image-01-IntelliJ](images/Image-01-IntelliJ.png)

2. Alterar início da tela de Run com texto (ou imagem) personalizado:
- formatar o texto desejado usando `https://springhow.com/spring-boot-banner-generator/` e baixar `banner.txt`;

![Image-02-BannerGenerator](images/Image-02-BannerGenerator.png)

- colocar o arquivo `banner.txt` em `src/main/resources` (ou outro caminho);
- (opcional) adicionar `${spring.application.name}` e `Spring Boot ${spring-boot.formatted-version}` em `banner.txt`;
- (opcional) configurar o caminho do arquivo `banner.txt` em `application.properties`:
  * `spring.banner.location=classpath:/banner.txt`. 

3. Configurar o banco de dados H2: 
- Em `build.gradle` configurar de acordo com o tipo de uso desejado:
  * `implementation 'com.h2database:h2'`;
  * `runtimeOnly 'com.h2database:h2'`;
  * `testImplementation 'com.h2database:h2'`;
- Em `application.properties`:

```properties
# ======================================================
#               APPLICATION
# ======================================================
spring.application.name=TemplateCode-Java-Spring-RestAPI
# spring.banner.location=classpath:/banner.txt
# ======================================================
#               DATASOURCE - H2 DATABASE
# ======================================================
spring.datasource.url=jdbc:h2:mem:proddb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=leo
spring.datasource.password=senha
spring.h2.console.enabled=true
spring.h2.console.path=/h2
```

![Image-03-Terminal-Run](images/Image-03-Terminal-Run.png)

- testar acesso ao console do H2 em `http://localhost:8080/h2/`:

![Image-04-B-ConsoleH2](images/Image-04-B-ConsoleH2.png)

Obs.: 
- Por padrão, o usename seria "sa" e a senha "", e o console do h2 estaria desativado.

4. Criar classe AssociatedEntity:
- no pacote `entities`;
- com atributos UUID id, String name;
- correspondente a tabela de nome `ASSOCIATEDS`.

5. Criar classe MainEntity:
- no pacote `entities`;
- com atributos UUID id, String name, String description e AssociatedEntity associated;
- correspondente a tabela de nome `MAINS`.

![Image-05-UML-Classes-Entities](images/Image-05-UML-Classes-Entities.png)

Obs.: Ao criar classes que representam entidades do banco de dados:
- anotá-las com `@Entity`, `@Table(name="...")` para mapear entidade; 
- anotá-las com `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` para usar o Lombok;
- adicionar atributo `UUID id` anotado com `@Id` e `@GeneratedValue(strategy = GenerationType.UUID)`;
- configurar relacionamentos com `@ManyToOne`, `@OneToMany` ou `@OneToOne` em atributos que são objetos;
- configurar as outras colunas com `@Column` e talvez algum validador como `@NotBlank` ou `@NotNull`;
- logar no console do H2 e verificar se as tabelas foram criadas corretamente:

![Image-06-ConsoleH2-Tables](images/Image-06-ConsoleH2-Tables.png)

6. Criar tabelas a partir de script SQL (e não do Hibernate):
- executar a aplicação com `spring.jpa.hibernate.ddl-auto=create-drop` em `application.properties`;
- executar o comando `SCRIPT` ou (`SCRIPT TO 'D:/schema.sql'`) para obter código SQL de criação de tabelas;
- colocar o conteúdo do passo anterior em `src/main/resources/schema.sql`;
- executar a aplicação com `spring.jpa.hibernate.ddl-auto=none` e
  `spring.jpa.defer-datasource-initialization=true` em `application.properties`;

```properties
# spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.hibernate.ddl-auto=none
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
```

- (opcional) configurar o caminho do arquivo `schema.sql` em `application.properties`:
  * `spring.sql.init.schema-locations=classpath:/schema.sql`.

![Image-07-ConsoleH2-BackupSQL](images/Image-07-ConsoleH2-BackupSQL.png)

7. Criar interfaces `AssociatedRepository` e `MainRepository`:
- no pacote `repositories`;
- anotadas com `@Repository`;
- extends `JPARepository`;
- possuem declaração dos métodos `Boolean existsByNameEqualsIgnoreCase(String name)` e 
  `Optional<MainEntity> findByNameEqualsIgnoreCase(String name);`.

![Image-08-UML-Interfaces-Repositories](images/Image-08-UML-Interfaces-Repositories.png)

8. Criar classes dtos `AssociatedPayload` e `MainPayload`:
- no pacote `dtos`;
- anotadas com `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` para usar o Lombok;

![Image-09-UML-Classes-DTOs](images/Image-09-UML-Classes-DTOs.png)

9. Criar mappers entre classes dtos ("payloads") e classes entities ("entities"):
- adicionar complementos em `build.gradle`:

```groovy
// compileOnly 'org.projectlombok:lombok'
implementation 'org.projectlombok:lombok'
implementation 'org.mapstruct:mapstruct:1.5.5.Final'
annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
testAnnotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
implementation 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
```

- criar as interfaces `AssociatedMapper` e `MainMapper`:
  * no pacote `mappers`;
  * anotadas com `@Mapper`;
  * com atributo `INSTANCE`;
  * com 4 métodos para conversões entre entidade e payload, e entre lista de entidades e lista de payloads;
  * `MainMapper` tem dois métodos auxiliares:
    * `default AssociatedEntity toAssociated(MainPayload payload)`;
    * `default String map(AssociatedEntity associatedEntity)`;

![Image-10-UML-Interfaces-Mappers](images/Image-10-UML-Interfaces-Mappers.png)

10. Criar camada de Services:
- no pacote `services`;
- adicionar interfaces `MainService` e `AssociatedService`;
- adicionar classes `MainServiceImpl` e `AssociatedServiceImpl`:
  * anotadas com `@Service`;
  * implementam as interfaces;
  * possuem o mapper e repository(ies) da(s) entidade(s);
  * possuem um construtor com todos os atributos e anotado com `@Autowired`;
- os métodos devem ser `create`, `getByName`, `getAll`, `update` e `deleteByName`;

![Image-11-UML-Services](images/Image-11-UML-Services.png)

11. Criar camada de Controllers:
- no pacote `controllers`;
- adicionar classes `MainController` e `AssociatedController`:
  * anotadas com `@RestController` e `@RequestMapping("api/v1/...")`;
  * possuem um construtor com um atributo e anotado com `@Autowired`;
  * os métodos devem ser `create`, `getByName`, `getAll`, `update` e `deleteByName`.

![Image-12-UML-Controllers](images/Image-12-UML-Controllers.png)

12. Criar Excetions:
- no pacote `exceptions`;
- com nomes `AssociatedNotFoundException`, `MainNotFoundException` `AssociatedNameAlreadyRegisteredException` e
  `MainNameAlreadyRegisteredException`;
- herdam de `RuntimeException`;
- possuem um contrutor com argumento `String message` e outro com `String message, Throwable cause` como argumentos;
- usá-las ao invés de `RunTimeException` nas implementações das Services.

13. Criar classe `ResourcesControllerAdvice`:
- no pacote `exceptions`;
- anotada com `@RestControllerAdvice`;
- os métodos:
  * anotados com `@ExceptionHandler`, e recebe alguma exceção; 
  * tem nomes iniciados com "handle";
  * retornan `ResponseEntity<String>`.

![Image-13-UML-Classes-Exceptions](images/Image-13-UML-Classes-Exceptions.png)


## Referências
Spring - Guides - Tutorials - Building REST services with Spring:
https://spring.io/guides/tutorials/rest

Baeldung - Rest with Spring Series:
https://www.baeldung.com/rest-with-spring-series

Baeldung - Spring Boot With H2 Database:
https://www.baeldung.com/spring-boot-h2-database

Baeldung - Quick Guide on Loading Initial Data with Spring Boot:
https://www.baeldung.com/spring-boot-data-sql-and-schema-sql

DZone - That’s How You Can Use MapStruct With Lombok in Your Spring Boot Application:
https://dzone.com/articles/thats-why-you-need-to-use-mapstruct-in-your-spring