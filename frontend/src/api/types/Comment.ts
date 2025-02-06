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
