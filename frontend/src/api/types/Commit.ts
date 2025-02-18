export type Commit = {
  prId: number;
  repoId: number;
  title: string;
  description: string;
  userId: number;
  userName: string;
  userProfile: string;
  createAt: string;
  targetBranch: string;
  sourceBranch: string;
  isOpened: number;
  baseSha: string;
  headSha: string;
  startSha: string;
  prType: number;
};
