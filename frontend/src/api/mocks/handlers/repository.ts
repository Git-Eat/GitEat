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
        fileId: "file1",
        commitId: "commit1",
        repoId: 1,
        prId: 101,
        fileName: "example1.txt",
        oldPath: "/src/old/example1.txt",
        newPath: "/src/new/example1.txt",
        fileStatus: 1,
        targetBranch: "main",
        sourceBranch: "feature/update-example1",
      },
      {
        fileId: "file2",
        commitId: "commit2",
        repoId: 1,
        prId: 101,
        fileName: "example2.txt",
        oldPath: "/src/old/example2.txt",
        newPath: "/src/new/example2.txt",
        fileStatus: 1,
        targetBranch: "main",
        sourceBranch: "feature/update-example2",
      },
      {
        fileId: "file3",
        commitId: "commit3",
        repoId: 1,
        prId: 101,
        fileName: "example3.txt",
        oldPath: "/src/old/example3.txt",
        newPath: "/src/new/example3.txt",
        fileStatus: 1,
        targetBranch: "main",
        sourceBranch: "feature/update-example3",
      },
      {
        fileId: "file4",
        commitId: "commit4",
        repoId: 1,
        prId: 101,
        fileName: "example4.txt",
        oldPath: "/src/old/example4.txt",
        newPath: "/src/new/example4.txt",
        fileStatus: 1,
        targetBranch: "main",
        sourceBranch: "feature/update-example4",
      },
      {
        fileId: "file5",
        commitId: "commit5",
        repoId: 1,
        prId: 101,
        fileName: "example5.txt",
        oldPath: "/src/old/example5.txt",
        newPath: "/src/new/example5.txt",
        fileStatus: 1,
        targetBranch: "main",
        sourceBranch: "feature/update-example5",
      },
    ]);
  }),

  http.post(`${API_BASE}/pr/:repoId/:prId/file/raw`, () => {
    return HttpResponse.json({
      fileName: "example1.txt",
      comments: [
        {
          commentId: 1,
          prId: 101,
          repoId: 1,
          userId: 303,
          userName: "commentUser",
          avatarUrl: "https://example.com/avatar1.jpg",
          disId: "dis123",
          content: "example1.txt 파일에서 코드 수정이 필요합니다.",
          commentType: 1,
          createAt: "2025-02-10T15:30:00Z",
          position: {
            baseSha: "commit1_baseSha_dummy",
            startSha: "commit1_startSha_dummy",
            headSha: "commit1_headSha_dummy",
            oldPath: "/src/old/example1.txt",
            newPath: "/src/new/example1.txt",
            positionType: "text",
            newLine: 1,
            oldLine: null,
            newStartLine: 10,
            newEndLine: 20,
            oldStartLine: 9,
            oldEndLine: 19,
            lineRange: {
              start: {
                lineCode: "lineCode_start_dummy",
              },
              end: {
                lineCode: "lineCode_end_dummy",
              },
            },
          },
          reCommentList: [
            {
              replyId: 1,
              userId: 404,
              userName: "replyUser",
              avatarUrl: "https://example.com/avatar_reply1.jpg",
              content: "맞습니다, 개선이 필요해 보입니다.",
              createAt: "2025-02-10T16:00:00Z",
            },
            {
              replyId: 2,
              userId: 405,
              userName: "anotherReplyUser",
              avatarUrl: "https://example.com/avatar_reply2.jpg",
              content: "추가 의견: 해당 부분 로직 분리가 좋을 것 같습니다.",
              createAt: "2025-02-10T16:05:00Z",
            },
          ],
        },
        {
          commentId: 2,
          prId: 101,
          repoId: 1,
          userId: 305,
          userName: "anotherCommentUser",
          avatarUrl: "https://example.com/avatar2.jpg",
          disId: "dis456",
          content: "변경된 코드가 성능에 영향을 줄 수 있습니다.",
          commentType: 2,
          createAt: "2025-02-10T17:00:00Z",
          position: null,
          reCommentList: [],
        },
      ],
      oldCode:
        "add;\nnconsole.log('old version of example1');\nfunction foo() { return 'old'; }",
      newCode:
        "add;\nconsole.log('new version of example1');\nfunction foo() { return 'new'; }",
    });
  }),
];

export default repositoryHandler;
