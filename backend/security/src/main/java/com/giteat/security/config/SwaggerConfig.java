package com.giteat.security.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("mergeRequest") // 우측상단 그룹화
                .pathsToMatch("/api/pr/**") // end point
                .addOpenApiCustomizer(
                        openApi ->
                                openApi
                                        .setInfo(
                                                new Info()
                                                        .title("mergeRequest API") //제목
                                                        .description("mergeRequest 관련 API 모음") //설명

                                        )
                )
                .build();
    }
}