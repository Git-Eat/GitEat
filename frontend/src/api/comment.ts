import authClient from "./authClient";
import client from "./client";
import { Comment } from "./types/Comment";
import { Reply } from "./types/Reply";

export async function getComments(repoId: number, prId: number) {
  try {
    const response = await client.get(`/pr/${repoId}/${prId}/comment`);
    return response.data;
  } catch (error) {
    throw new Error("댓글 가져오기 실패했습니다." + error);
  }
}

export async function createComment(
  repoId: number,
  prId: number,
  content: string,
  commentType: 0 | 1 | 2
) {
  try {
    const response = await authClient.post(`/pr/${repoId}/${prId}/comment`, {
      repoId,
      prId,
      content,
      commentType,
    });
    return response.data as Comment;
  } catch (error) {
    throw new Error("댓글 생성 실패했습니다." + error);
  }
}

export async function deleteComment(
  repoId: number,
  prId: number,
  commentId: number
) {
  try {
    const response = await authClient.delete(
      `/pr/${repoId}/${prId}/comment/${commentId}`
    );
    return response.data;
  } catch (error) {
    throw new Error("댓글 삭제 실패했습니다." + error);
  }
}

export async function createReply(
  repoId: number,
  prId: number,
  discussionId: string,
  content: string,
  replyType: 0 | 1 | 2
) {
  try {
    const response = await authClient.post(
      `/pr/${repoId}/${prId}/reply/${discussionId}`,
      { content, discussionId, replyType }
    );
    return response.data as Reply;
  } catch (error) {
    throw new Error("답글 생성 실패했습니다." + error);
  }
}

export async function deleteReply(
  repoId: number,
  prId: number,
  replyId: number
) {
  try {
    const response = await authClient.delete(
      `/pr/${repoId}/${prId}/reply/${replyId}`
    );
    return response.data;
  } catch (error) {
    throw new Error("답글 삭제 실패했습니다." + error);
  }
}

export async function updateComment(
  repoId: number,
  prId: number,
  commentId: number,
  commentType: 0 | 1 | 2,
  content: string
) {
  try {
    const response = await authClient.put(
      `/pr/${repoId}/${prId}/comment/${commentId}`,
      { commentType, content }
    );
    return response.data;
  } catch (error) {
    throw new Error("댓글 수정 실패했습니다." + error);
  }
}

// export async function updateReply(
//   repoId: number,
//   prId: number,
//   replyId: number
// ) {
//   try {
//     const response = await authClient.put(
//       `/pr/${repoId}/${prId}/reply/${replyId}`
//     );
//     return response.data;
//   } catch (error) {
//     throw new Error("답글 수정 실패했습니다." + error);
//   }
// }
