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


## Referências
Spring - Guides - Tutorials - Building REST services with Spring:
https://spring.io/guides/tutorials/rest

Baeldung - Rest with Spring Series:
https://www.baeldung.com/rest-with-spring-series