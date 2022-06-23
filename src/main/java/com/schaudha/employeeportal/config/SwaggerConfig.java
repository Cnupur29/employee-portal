package com.schaudha.employeeportal.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;

    @Value("${applicationName}")
    private String applicationName;

    @Value("${baseUrl}")
    private String appUrl;

    @Value("${contactEmail}")
    private String contactEmail;

    @Value("${host.full.dns.auth.link}")
    private String authLink;

/*
    @Bean
    public Docket api() {

        List<ResponseMessage> list = new java.util.ArrayList<>();
        list.add(new ResponseMessageBuilder().code(500).message("500 message")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(401).message("Unauthorized")
                .responseModel(new ModelRef("Result")).build());
        list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable")
                .responseModel(new ModelRef("Result")).build());

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
                .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
                .globalResponseMessage(RequestMethod.POST, list);

    }



    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("profile", "read all"));
        authorizationScopeList.add(new AuthorizationScope("email", "trust all"));
        authorizationScopeList.add(new AuthorizationScope("openid", "openid"));

        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(authLink + "openid-connect/token?client_id=" + this.clientId + "&client_secret=" + this.clientSecret);

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/user/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(this.applicationName + " Api").description("")
                .termsOfServiceUrl(this.appUrl + "/terms")
                .contact(new Contact(this.applicationName + " Admin", this.appUrl, this.contactEmail))
                .license("MIT").licenseUrl(this.appUrl + "/license").version("1.0.0").build();
    } */
/*
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .host("127.0.0.1")
                .securityContexts(Collections.singletonList(securityContext()));
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().
                securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes().toArray(new AuthorizationScope[scopes().size()])))).
                forPaths(PathSelectors.regex("/*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        return Collections.singletonList(new SecurityReference("oauth2schema", scopes().toArray(new AuthorizationScope[scopes().size()])));
    }

    private SecurityScheme securityScheme() {
        /*GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(authLink, "code"))
                .tokenRequestEndpoint(new TokenRequestEndpoint("http://localhost:8080/realms/techintroductory/protocol/openid-connect/auth",
                        clientId,
                        clientSecret))
                .build(); invalid redirect uri with this grant*/

       /* GrantType grantType = new ClientCredentialsGrant("http://localhost:8080/realms/techintroductory/protocol/openid-connect/token");

        return new OAuthBuilder().name("spring_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(scopes()).build();
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> authorizationScopes = new ArrayList<>();
        authorizationScopes.add(new AuthorizationScope("openid", "read all"));
        authorizationScopes.add(new AuthorizationScope("profile", "trust all"));
        authorizationScopes.add(new AuthorizationScope("email", "write all"));
        return authorizationScopes;
    } */

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("spring_oauth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .description("Oauth2 flow")
                                .scheme("bearer")
                                .flows(new OAuthFlows()
                                        .clientCredentials(new OAuthFlow()
                                                .tokenUrl("http://localhost:8080/realms/techintroductory/protocol/openid-connect/token")
                                                .scopes(new Scopes()
                                                        .addString("openid", "for read operations")
                                                        .addString("profile", "for write operations")
                                                        .addString("email", "for write operations")
                                                ))))
                )
                .security(Arrays.asList(
                        new SecurityRequirement().addList("spring_oauth")))
                .info(new Info()
                        .title("Book Application API")
                        .description("This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                        .termsOfService("terms")
                        .contact(new Contact().email("codersitedev@gmail.com").name("Developer: Moises Gamio"))
                        .license(new License().name("GNU"))
                        .version("2.0")
                );

        /*final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .description(
                                        "Provide the JWT token. JWT token can be obtained from the Login API. For testing, use the credentials <strong>john/password</strong>")
                                .bearerFormat("JWT"))); fill bearer token directly in swagger*/
    }
}
