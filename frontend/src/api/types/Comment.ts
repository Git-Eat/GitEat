import { Reply } from "./Reply";

export type Comment = {
  commentId: number;
  prId: number;
  repoId: number;
  userId: number;
  userName: string | null;
  avatarUrl: string;
  disId: string;
  content: string;
  commentType: 0 | 1 | 2;
  createAt: string | null;
  position: object;
  replyList: Reply[];
};
