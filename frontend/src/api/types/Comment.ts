import { Reply } from "./Reply";

export type Position = {
  baseSha: string;
  startSha: string;
  headSha: string;
  oldPath: string;
  newPath: string;
  positionType: null | string;
  newLine: null | number;
  oldLine: null | number;
  newStartLine: null | number;
  newEndLine: null | number;
  oldStartLine: null | number;
  oldEndLine: null | number;
  lineRange: null | number;
};

export type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: number;
  createAt: string;
  position: Position | null;
  reCommentList: Reply[];
  fileId: string;
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
