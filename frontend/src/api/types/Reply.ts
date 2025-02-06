export type Reply = {
  reCommentId: number;
  userId: number;
  userName: string;
  avatarUrl: string | null;
  disId: string;
  content: string;
  replyType: 0 | 1 | 2;
  imageName: string | null;
  createAt: string | null;
};
