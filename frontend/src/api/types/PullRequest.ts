export type PullRequest = {
  baseSha: string;
  createAt: string;
  description: string;
  headSha: string;
  isOpened: 0 | 1 | 2 | 3;
  prId: number;
  repoId: number;
  sourceBranch: string;
  startSha: string;
  targetBranch: string;
  title: string;
  userId: number;
  userName: string;
  userProfile: string;
};

export type FileChange = {
  fileId: string;
  commitId: string;
  repoId: number;
  prId: number;
  fileName: string;
  oldPath: string;
  newPath: string;
  fileStatus: number;
  targetBranch: string;
  sourceBranch: string;
};
