import { Reply } from "./Reply";

export type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: number;
  imageName: string;
  createAt: string;
  replyList: Reply[];
};

export type FileCommentRequest = {
  fileId: string;
  baseSha: string;
  startSha: string;
  headSha: string;
  oldPath: string;
  newPath: string;
  positionType: string;
  newOrOld: number; // 1: old 기준, 2: new 기준
  oldStartLine: number | null;
  oldEndLine: number | null;
  newStartLine: number | null;
  newEndLine: number | null;
  body: string;
};
