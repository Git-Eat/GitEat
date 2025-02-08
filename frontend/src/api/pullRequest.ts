import authClient from "./authClient";
import { ChangedFile } from "./types/ChangedFile";
import { PullRequest } from "./types/PullRequest";
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
