export type PullRequest = {
  baseSha: null | string;
  createAt: string;
  description: null | string;
  headSha: null | string;
  isOpened: 0 | 1 | 2 | 3;
  prId: number;
  repoId: number;
  sourceBranch: string;
  startSha: null | string;
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
