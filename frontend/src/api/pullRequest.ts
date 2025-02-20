import authClient from "./authClient";
import { AiReivew } from "./types/AiReview";
import { ChangedFile } from "./types/ChangedFile";
import { FileCommentRequest } from "./types/Comment";
import { Commit } from "./types/Commit";
import { PullRequest } from "./types/PullRequest";
import { RawFileResponse } from "./types/RawFile";
import { Repository } from "./types/Repository";

export const getPullRequests = async (
  repoId: number
): Promise<PullRequest[]> => {
  try {
    const res = await authClient.get(`/pr/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getRepsitories = async (): Promise<Repository[]> => {
  try {
    const res = await authClient.get("/repo");
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getPullRequest = async (
  repoId: number,
  prId: number
): Promise<PullRequest> => {
  try {
    const res = await authClient.get(`/pr/${repoId}/${prId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const addRepository = async (repoId: number): Promise<Repository> => {
  try {
    const res = await authClient.post(`/repo`, {
      repoId: repoId,
    });
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const deleteRepository = async <T>(repoId: number): Promise<T> => {
  try {
    const res = await authClient.delete(`/repo/${repoId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getFileChanges = async (
  repoId: number,
  prId: number
): Promise<ChangedFile[]> => {
  try {
    const res = await authClient.get(`/pr/${repoId}/${prId}/file`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getRawFile = async (
  repoId: number,
  prId: number,
  file: ChangedFile
): Promise<RawFileResponse> => {
  try {
    const res = await authClient.post(`/pr/${repoId}/${prId}/file/raw`, file);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const createFileComment = async <T>(
  repoId: number,
  prId: number,
  comment: FileCommentRequest
): Promise<T> => {
  try {
    const res = await authClient.post(
      `/pr/${repoId}/${prId}/file/comment`,
      comment
    );
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getAiReview = async (
  repoId: number,
  prId: number
): Promise<AiReivew> => {
  try {
    const res = await authClient.get(`/ai/review/${repoId}/${prId}`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};

export const getCommits = async (
  repoId: number,
  prId: number
): Promise<Commit[]> => {
  try {
    const res = await authClient.get(`/pr/${repoId}/${prId}/commit`);
    return res.data;
  } catch (e: unknown) {
    if (e instanceof Error) throw new Error(e.message);
    else throw new Error("unknown Error");
  }
};
