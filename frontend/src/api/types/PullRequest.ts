export type PullRequest = {
  prId: number;
  repoId: number;
  userId: number;
  title: string;
  description: string;
  createAt: number[];
  isOpened: 1 | 2 | 3;
  targetBranch: string;
  sourceBranch: "develop";
};

// export type RepositoryResponse = {

// }
