import { DiffViewer } from "../diffViewer";

const file = {
  oldCode:
    'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.0.100"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
  diff: "현재는 임시값",
  newCode:
    'package com.giteat.common.config;\n\nimport org.springframework.context.annotation.Bean;\nimport org.springframework.context.annotation.Configuration;\nimport org.springframework.context.annotation.Profile;\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\nimport static org.springframework.security.config.Customizer.withDefaults;\nimport org.springframework.security.web.SecurityFilterChain;\nimport org.springframework.security.web.util.matcher.RequestMatcher;\nimport jakarta.servlet.http.HttpServletRequest;\n\n@Profile("release") // 배포 환경에서만 활성화\n@Configuration\npublic class ReleaseSecurityConfig {\n    private static final String ALLOWED_IP = "192.168.99.99"; // 허용된 IP 주소\n\n    @Bean\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\n                .authorizeHttpRequests(auth -> auth\n                        .requestMatchers(new RequestMatcher() {\n                            @Override\n                            public boolean matches(HttpServletRequest request) {\n                                String remoteAddr = request.getRemoteAddr();\n                                return ALLOWED_IP.equals(remoteAddr);\n                            }\n                        }).permitAll() // 허용된 IP에서만 요청 허용\n                        .anyRequest().denyAll()); // 나머지 요청 차단\n        return http.build();\n    }\n}\n',
};

export function FileChanges() {
  return (
    <div className="w-full">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold">File Changes</h1>
        <button className="bg-blue-500 text-white px-4 py-2 rounded-md">
          Download
        </button>
      </div>
      <div className="mt-4">
        <div className="border border-gray-200 p-4 my-4 rounded-md">
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-bold">TEST.java</h2>
            <span className="text-sm">ASDF</span>
          </div>
          <div className="flex mt-4">
            <DiffViewer oldCode={file.oldCode} newCode={file.newCode} />
          </div>
        </div>
      </div>
    </div>
  );
}
