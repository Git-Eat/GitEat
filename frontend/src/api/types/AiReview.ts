export type AiReivew = [
  {
    aiReviewId: number;
    repoId: number;
    prId: number;
    arStatusId: number;
    baseSha: string;
    headSha: string;
    content: string;
    createTime: number[];
  },
];
