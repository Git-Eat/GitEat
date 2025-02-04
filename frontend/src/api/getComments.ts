import client from "./client";

export async function getComments() {
  try {
    const response = await client.get("/pr/repoId/prId/comment");
    return response.data;
  } catch (error) {
    throw new Error("댓글 가져오기 실패했습니다." + error);
  }
}
