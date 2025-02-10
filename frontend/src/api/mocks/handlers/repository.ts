import { http, HttpResponse } from "msw";
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
  //   http.post(`${API_BASE}/repo`, () => {
  //     return HttpResponse.json(MOCK_RESPONSE);
  //   }),
  // http.post(`${API_BASE}/repo`, async () => {
  //   // 3초(3000ms) 지연
  //   console.log("add");
  //   await new Promise((resolve) => setTimeout(resolve, 3000));
  //   return HttpResponse.json(MOCK_RESPONSE);
  // }),

  // 3초 후에 실패하는 핸들러
  // http.post(`${API_BASE}/repo`, async () => {
  //   // 3초(3000ms) 지연
  //   await new Promise((resolve) => setTimeout(resolve, 3000));
  //   // 상태코드 500과 함께 에러 메시지를 반환
  //   return HttpResponse.json(
  //     { error: "Internal Server Error" },
  //     { status: 500 }
  //   );
  // }),
  http.delete(`${API_BASE}/repo/1`, async () => {
    // 3초(3000ms) 지연
    console.log("delete");
    await new Promise((resolve) => setTimeout(resolve, 3000));
    return HttpResponse.json(MOCK_RESPONSE);
  }),
];

export default repositoryHandler;
