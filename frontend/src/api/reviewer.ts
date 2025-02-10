import authClient from "./authClient";

export async function getReviewer(repoId: number, prId: number) {
  try {
    const response = await authClient.get(`/pr/${repoId}/${prId}/reviewer`);
    return response.data;
  } catch (error) {
    throw new Error("코드리뷰 참여자 목록 가져오기 실패했습니다." + error);
  }
}
