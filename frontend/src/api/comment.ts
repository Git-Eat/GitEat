import authClient from "./authClient";
import client from "./client";

export async function getComments(repoId: number, prId: number) {
  try {
    const response = await client.get(`/pr/${repoId}/${prId}/comment`);
    return response.data;
  } catch (error) {
    throw new Error("댓글 가져오기 실패했습니다." + error);
  }
}

export async function deleteComment(
  repoId: number,
  prId: number,
  commentId: number
) {
  try {
    const response = await authClient.delete(
      `pr/${repoId}/${prId}/comment/${commentId}`
    );
    return response.data;
  } catch (error) {
    throw new Error("댓글 삭제 실패했습니다." + error);
  }
}
