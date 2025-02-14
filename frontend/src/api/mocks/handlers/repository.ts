import { http, HttpResponse } from "msw";
import { Pullrequests } from "../dummies/repository";
const MOCK_RESPONSE = {
  repoId: 3,
  userId: 1,
  name: "MobileAppX",
  description: "Mobile app development for both Android and iOS platforms.",
  githubUrl: "https://github.com/mobile/appx",
  gitlabUrl: "https://gitlab.com/mobile/appx",
  createAt: [2025, 1, 31, 8, 13, 35],
};
const API_BASE = import.meta.env.VITE_API_BASE;
const repositoryHandler = [
  http.get(`${API_BASE}/repo`, () => {
    return HttpResponse.json([
      {
        repoId: 888788,
        name: "S12P11B108",
        description: "",
        githubUrl: null,
        gitlabUrl: "https://lab.ssafy.com/s12-webmobile2-sub1/S12P11B108",
        createAt: "2025-01-13T15:12:17.095+09:00",
        ownerName: "s12-webmobile2-sub1",
        access: 1,
      },
    ]);
  }),
  http.get(`${API_BASE}/pr/:repoId`, () => {
    return HttpResponse.json(Pullrequests);
  }),
  http.post(`${API_BASE}/repo`, async () => {
    // 3초(3000ms) 지연
    console.log("add");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json(MOCK_RESPONSE);
  }),

  http.delete(`${API_BASE}/repo/1`, async () => {
    // 3초(3000ms) 지연
    console.log("delete");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json(MOCK_RESPONSE);
  }),

  http.get(`${API_BASE}/pr/:repoId/:prId`, () => {
    console.log("pr get");
    return HttpResponse.json({
      baseSha: "46edebc6b129916f4e0f2b485add074ccb8f385a",
      createAt: "2025-02-10T15:30:00Z",
      description: "테스트를 위한 더미 풀리퀘스트입니다.",
      headSha: "480eebd9a3e4fbf50e6b046a589c370e35caa8b6",
      isOpened: 1,
      prId: 101,
      repoId: 202,
      sourceBranch: "feature/dummy-feature",
      startSha: "46edebc6b129916f4e0f2b485add074ccb8f385a",
      targetBranch: "main",
      title: "더미 기능 추가",
      userId: 303,
      userName: "dummyUser",
      userProfile: "https://example.com/profiles/dummyUser.jpg",
    });
  }),

  http.get(`${API_BASE}/pr/:repoId/:prId/file`, () => {
    return HttpResponse.json([
      {
        fileId: "04586714c95c899ad1586d536fdc0d2cc67af8e3",
        repoId: 888788,
        prId: 32,
        fileName: "yarn.lock",
        oldPath: "frontend/yarn.lock",
        newPath: "frontend/yarn.lock",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "37083060967a471c011a16651e22bdc2ade41759",
        repoId: 888788,
        prId: 32,
        fileName: "package.json",
        oldPath: "frontend/package.json",
        newPath: "frontend/package.json",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "564dfef6109621cdfbd189b6babb4ee0c3a7d0e6",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/components/pullRequest/conversation/index.tsx",
        newPath: "frontend/src/components/pullRequest/conversation/index.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "809f9e3430dfcad1380bf902c9afd29209973874",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/components/common/header/index.tsx",
        newPath: "frontend/src/components/common/header/index.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "8c47ef0c41da681feb401575186635fb49ba1031",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/pages/pullRequestList/index.tsx",
        newPath: "frontend/src/pages/pullRequestList/index.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "8f2bdc70a62ab237098bf2bc7270147bf84339b9",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/components/pullRequest/diffViewer/index.tsx",
        newPath: "frontend/src/components/pullRequest/diffViewer/index.tsx",
        fileStatus: 1,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "91e522be11677dbeaea3b85493b4868d2728cb6c",
        repoId: 888788,
        prId: 32,
        fileName: "index.css",
        oldPath: "frontend/src/index.css",
        newPath: "frontend/src/index.css",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "a109f76d4b7d7f71f4283fce95cb02f8a5ee4b07",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/components/pullRequest/fileChanges/index.tsx",
        newPath: "frontend/src/components/pullRequest/fileChanges/index.tsx",
        fileStatus: 1,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "a17f88fe2e1e2e3f38951398beb000494d6fa79d",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath: "frontend/src/pages/authLayout/index.tsx",
        newPath: "frontend/src/pages/authLayout/index.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "b9c5384815ed26f7912bd4d34cab45985cb9a0cd",
        repoId: 888788,
        prId: 32,
        fileName: "App.tsx",
        oldPath: "frontend/src/App.tsx",
        newPath: "frontend/src/App.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
      {
        fileId: "e86ffbefe9c6f0a75d932b1e9f06c81badbb5957",
        repoId: 888788,
        prId: 32,
        fileName: "index.tsx",
        oldPath:
          "frontend/src/components/pullRequest/conversation/comments/index.tsx",
        newPath:
          "frontend/src/components/pullRequest/conversation/comments/index.tsx",
        fileStatus: 2,
        targetBranch: "develop",
        sourceBranch: "FE/feature/PullRequest_fileChanges",
      },
    ]);
  }),

  http.post(`${API_BASE}/pr/:repoId/:prId/file/raw`, () => {
    return HttpResponse.json({
      fileName: "index.tsx",
      comments: [
        {
          commentId: 1879447,
          prId: 32,
          repoId: 888788,
          userId: 21879,
          fileId: "a109f76d4b7d7f71f4283fce95cb02f8a5ee4b07",
          userName: "최이화",
          avatarUrl:
            "https://secure.gravatar.com/avatar/c1cfcd69bd0f4f338373504e930553ebbef699aa0fb6c3c7ddf2cec52d94e76c?s=80&d=identicon",
          disId: "f83b0660317d1f6f4131c7ecc81a73da8ab25416",
          content:
            "이 부분이 파일명만 보여주는 건지 파일 경로를 보여주는 건지 알고 싶습니다.\n제 생각에는 현재처럼 파일명을 보여주고, 아래에 추가로 파일 경로를 기입하는 것도 괜찮을 것 같습니다.",
          commentType: 0,
          createAt: "2025-02-02T16:25:26.918+09:00",
          position: {
            baseSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
            startSha: "5b7a6146752b83f400e07854dfe27bf7000cf058",
            headSha: "74523366418dcf66994fb3c319344be2bc2c0533",
            oldPath:
              "frontend/src/components/pullRequest/fileChanges/index.tsx",
            newPath:
              "frontend/src/components/pullRequest/fileChanges/index.tsx",
            positionType: null,
            newLine: 75,
            oldLine: 0,
            newStartLine: 74,
            newEndLine: 74,
            oldStartLine: 0,
            oldEndLine: 0,
            lineRange: null,
          },
          reCommentList: [],
        },
      ],
      oldCode: null,
      newCode:
        'import { SimpleTreeView, TreeItem } from "@mui/x-tree-view";\nimport { DiffViewer } from "../diffViewer";\nimport { Box, Accordion, AccordionSummary } from "@mui/material";\nimport AccordionDetails from "@mui/material/AccordionDetails";\nimport ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";\nimport { ErrorBoundery } from "../../common/errorBoundery";\nconst files = [\n  {\n    filePath: "1",\n    oldCode:\n      \'package com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n    diff: "현재는 임시값",\n    newCode:\n      \'package com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n  },\n  {\n    filePath: "2",\n    oldCode:\n      \'ackage com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.99"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n    diff: "현재는 임시값",\n    newCode:\n      \'package com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.99"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n  },\n  {\n    filePath: "3",\n    oldCode:\n      \'package com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n    diff: "현재는 임시값",\n    newCode:\n      \'ackage com.giteat.common.config;\\n\\nimport org.springframework.context.annotation.Bean;\\nimport org.springframework.context.annotation.Configuration;\\nimport org.springframework.context.annotation.Profile;\\nimport org.springframework.security.config.annotation.web.builders.HttpSecurity;\\nimport static org.springframework.security.config.Customizer.withDefaults;\\nimport org.springframework.security.web.SecurityFilterChain;\\nimport org.springframework.security.web.util.matcher.RequestMatcher;\\nimport jakarta.servlet.http.HttpServletRequest;\\n\\n@Profile("release") // 배포 환경에서만 활성화\\n@Configuration\\npublic class ReleaseSecurityConfig {\\n    private static final String ALLOWED_IP = "192.168.99.100"; // 허용된 IP 주소\\n\\n    @Bean\\n    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\\n        http.csrf(withDefaults()) // CSRF 기본 설정 유지\\n                .authorizeHttpRequests(auth -> auth\\n                        .requestMatchers(new RequestMatcher() {\\n                            @Override\\n                            public boolean matches(HttpServletRequest request) {\\n                                String remoteAddr = request.getRemoteAddr();\\n                                return ALLOWED_IP.equals(remoteAddr);\\n                            }\\n                        }).permitAll() // 허용된 IP에서만 요청 허용\\n                        .anyRequest().denyAll()); // 나머지 요청 차단\\n        return http.build();\\n    }\\n}\\n\',\n  },\n];\n\nexport function FileChanges() {\n  return (\n    <div className="flex gap-[35px] justify-between mt-[30px]">\n      <div className="w-1/5 max-w-56 bg-white p-[15px] min-h-[calc(100vh-300px)] max-h-[calc(100vh-300px)] rounded-xl">\n        <Box>\n          <SimpleTreeView>\n            <TreeItem itemId="grid" label="Data Grid">\n              <TreeItem itemId="grid-community" label="@mui/x-data-grid" />\n              <TreeItem itemId="grid-pro" label="@mui/x-data-grid-pro" />\n              <TreeItem\n                itemId="grid-premium"\n                label="@mui/x-data-grid-premium"\n              />\n            </TreeItem>\n            <TreeItem itemId="pickers" label="Date and Time Pickers">\n              <TreeItem\n                itemId="pickers-community"\n                label="@mui/x-date-pickers"\n              />\n              <TreeItem itemId="pickers-pro" label="@mui/x-date-pickers-pro" />\n            </TreeItem>\n            <TreeItem itemId="charts" label="Charts">\n              <TreeItem itemId="charts-community" label="@mui/x-charts" />\n            </TreeItem>\n            <TreeItem itemId="tree-view" label="Tree View">\n              <TreeItem itemId="tree-view-community" label="@mui/x-tree-view" />\n            </TreeItem>\n          </SimpleTreeView>\n        </Box>\n      </div>\n      <div className="w-fit">\n        <div className="mt-4">\n          <div className="border border-gray-200 p-4 my-4 rounded-md">\n            {files.map((file) => (\n              <Accordion key={file.filePath} defaultExpanded>\n                <div className="flex justify-between items-center">\n                  <AccordionSummary\n                    expandIcon={<ArrowDropDownIcon />}\n                    aria-controls="panel1-content"\n                    id="panel1-header"\n                  >\n                    <h2 className="text-lg font-bold">TEST.java</h2>\n                  </AccordionSummary>\n                </div>\n                <div className="flex mt-4">\n                  <AccordionDetails>\n                    <ErrorBoundery>\n                      <DiffViewer\n                        oldCode={file.oldCode}\n                        newCode={file.newCode}\n                      />\n                    </ErrorBoundery>\n                  </AccordionDetails>\n                </div>\n              </Accordion>\n            ))}\n          </div>\n        </div>\n      </div>\n    </div>\n  );\n}\n',
    });
  }),
];

export default repositoryHandler;
