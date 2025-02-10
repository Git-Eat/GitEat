package com.giteat.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "git eat API",
                description = "GITEAT 프로젝트 API 문서"
        ),
        servers = {
                @Server(url = "https://i12b108.p.ssafy.io/api", description = "Production Server") // HTTPS 적용
        }
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi mergeRequestOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("mergeRequest") // 우측 상단 그룹화
                .pathsToMatch("/pr/**") // 엔드포인트 설정
                .build();
    }

    @Bean
    public GroupedOpenApi RepositoryOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("repository") // 우측 상단 그룹화
                .pathsToMatch("/repo/**") // 엔드포인트 설정
                .build();
    }

    @Bean
    public GroupedOpenApi UserOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("user") // 우측 상단 그룹화
                .pathsToMatch("/oauth/**") // 엔드포인트 설정
                .build();
    }
}
