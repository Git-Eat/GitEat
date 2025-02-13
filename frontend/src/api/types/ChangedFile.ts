export type ChangedFile = {
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
