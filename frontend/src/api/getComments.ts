import authClient from "./authClient";
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

export const updateComment = async (
  repoId: number,
  prId: number,
  commentId: number,
  comment: string
): Promise<Comment> => {
  try {
    const response = await authClient.put(
      `/pr/${repoId}/${prId}/comment/${commentId}`,
      {
        content: comment,
      }
    );
    return response.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const deleteComment = async (
  repoId: number,
  prId: number,
  commentId: number
): Promise<number> => {
  try {
    const response = await authClient.delete(
      `/pr/${repoId}/${prId}/comment/${commentId}`
    );
    return response.status;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
