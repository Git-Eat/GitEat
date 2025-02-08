export type PullRequest = {
  prId: number;
  repoId: number;
  userId: number;
  title: string;
  description: string;
  createAt: string;
  isOpened: 0 | 1 | 2;
  targetBranch: string;
  sourceBranch: "develop";
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
