package com.springboot.backend.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Api con JWT",
                "Api que permite dar de alta,baja y modificar autos previa auntenticación de usuario.\nLos usuarios estan clasificados entre ADMIN y USER."
                + "El usuario con rol ADMIN puede realizar cualquier acción sobre la aplición mientras que el usuario con rol USER solo puede acceder a ver el listado de"
                + " autos y obtener un auto por id.\nTodas las operaciones requieren de un token para operar.\n"
                + "IMPORTANTE: una vez generado el token,para enviarlo en las peticiones se debe anteponer la palabra 'Bearer' seguida del token generado.Ejemplo:Bearer miToken"
                + "El token tiene una duración de 2 minutos. Transcurrido ese tiempo se debe generar otro token.\n"
                + "Datos para loguearse en la api:\nAdmin->{nombreUsuario:'ricardo',password:'12345'}\nUser->{nombreUsuario:'sebastian',password:'user'}",
                "2.0",
                "Términos y Condiciones",
                new Contact("Ricardo Ledesma", "", "sebastianledesma1992@g,ail.com"),
                "",
                "",
                Collections.emptyList()
        );
    }
}