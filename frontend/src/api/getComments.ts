import client from "./client";

const API_BASE = import.meta.env.VITE_API_BASE;
export async function getComments() {
  try {
    const response = await client.get(API_BASE + "/pr/repoId/prId/comment");
    return response.data;
  } catch (error) {
    throw new Error("댓글 가져오기 실패했습니다." + error);
  }
}
