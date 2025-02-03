import client from "./client";

const API_BASE = import.meta.env.VITE_API_BASE;
export async function getReviewer() {
  try {
    const response = await client.get(API_BASE + "/pr/repoId/prId/reviewer");
    return response.data;
  } catch (error) {
    throw new Error("코드리뷰 참여자 목록 가져오기 실패했습니다." + error);
  }
}
