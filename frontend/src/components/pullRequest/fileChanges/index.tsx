import { SimpleTreeView, TreeItem } from "@mui/x-tree-view";
import { DiffViewer } from "../diffViewer";
import { Box, Accordion, AccordionSummary } from "@mui/material";
import AccordionDetails from "@mui/material/AccordionDetails";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { ErrorBoundery } from "../../common/errorBoundery";
const files = [
  {
    filePath: "1",
    oldCode:
      'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
    diff: "현재는 임시값",
    newCode:
      'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
  },
  {
    filePath: "2",
    oldCode:
      'ackage com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.99"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
    diff: "현재는 임시값",
    newCode:
      'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.99"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
  },
  {
    filePath: "3",
    oldCode:
      'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
    diff: "현재는 임시값",
    newCode:
      'ackage com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
  },
];

export function FileChanges() {
  return (
    <div className="flex gap-[35px] justify-between mt-[30px]">
      <div className="w-1/5 max-w-56 bg-white p-[15px] min-h-[calc(100vh-300px)] max-h-[calc(100vh-300px)] rounded-xl">
        <Box>
          <SimpleTreeView>
            <TreeItem itemId="grid" label="Data Grid">
              <TreeItem itemId="grid-community" label="@mui/x-data-grid" />
              <TreeItem itemId="grid-pro" label="@mui/x-data-grid-pro" />
              <TreeItem
                itemId="grid-premium"
                label="@mui/x-data-grid-premium"
              />
            </TreeItem>
            <TreeItem itemId="pickers" label="Date and Time Pickers">
              <TreeItem
                itemId="pickers-community"
                label="@mui/x-date-pickers"
              />
              <TreeItem itemId="pickers-pro" label="@mui/x-date-pickers-pro" />
            </TreeItem>
            <TreeItem itemId="charts" label="Charts">
              <TreeItem itemId="charts-community" label="@mui/x-charts" />
            </TreeItem>
            <TreeItem itemId="tree-view" label="Tree View">
              <TreeItem itemId="tree-view-community" label="@mui/x-tree-view" />
            </TreeItem>
          </SimpleTreeView>
        </Box>
      </div>
      <div className="w-fit">
        <div className="mt-4">
          <div className="border border-gray-200 p-4 my-4 rounded-md">
            {files.map((file) => (
              <Accordion key={file.filePath} defaultExpanded>
                <div className="flex justify-between items-center">
                  <AccordionSummary
                    expandIcon={<ArrowDropDownIcon />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                  >
                    <h2 className="text-lg font-bold">TEST.java</h2>
                  </AccordionSummary>
                </div>
                <div className="flex mt-4">
                  <AccordionDetails>
                    <ErrorBoundery>
                      <DiffViewer
                        oldCode={file.oldCode}
                        newCode={file.newCode}
                      />
                    </ErrorBoundery>
                  </AccordionDetails>
                </div>
              </Accordion>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
