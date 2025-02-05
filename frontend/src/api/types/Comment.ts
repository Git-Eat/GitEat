import { ReComment } from "./ReComment";

export type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: 0 | 1 | 2;
  createAt: string;
  position: object;
  reCommentList: ReComment[];
};
