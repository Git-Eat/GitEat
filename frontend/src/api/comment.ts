import authClient from "./authClient";
import { Comment } from "./types/Comment";
import { Reply } from "./types/Reply";
import { UploadedFile } from "./types/UploadedFile";

export async function getComments(repoId: number, prId: number) {
  try {
    const response = await authClient.get(`/pr/${repoId}/${prId}/comment`);
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export const uploadFile = async (
  repoId: number,
  imageForm: FormData
): Promise<UploadedFile> => {
  try {
    const response = await authClient.post(`/pr/${repoId}/uploads`, imageForm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export async function createComment(
  repoId: number,
  prId: number,
  content: string
): Promise<Comment> {
  try {
    const response = await authClient.post(`/pr/${repoId}/${prId}/comment`, {
      repoId,
      prId,
      content,
    });
    return response.data as Comment;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function updateComment(
  repoId: number,
  prId: number,
  commentId: number,
  content: string
): Promise<Comment> {
  try {
    const response = await authClient.put(
      `/pr/${repoId}/${prId}/comment/${commentId}`,
      { content }
    );
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function deleteComment(
  repoId: number,
  prId: number,
  commentId: number
): Promise<number> {
  try {
    const response = await authClient.delete(
      `/pr/${repoId}/${prId}/comment/${commentId}`
    );
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function createReply(
  repoId: number,
  prId: number,
  disId: string,
  content: string
): Promise<Reply> {
  try {
    const response = await authClient.post(
      `/pr/${repoId}/${prId}/reply/${disId}`,
      { content, disId }
    );
    return response.data as Reply;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function updateReply(
  repoId: number,
  prId: number,
  reCommentId: number,
  content: string
): Promise<Reply> {
  try {
    const response = await authClient.put(
      `/pr/${repoId}/${prId}/reply/${reCommentId}`,
      { content }
    );
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}

export async function deleteReply(
  repoId: number,
  prId: number,
  replyId: number
): Promise<number> {
  try {
    const response = await authClient.delete(
      `/pr/${repoId}/${prId}/reply/${replyId}`
    );
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
}
