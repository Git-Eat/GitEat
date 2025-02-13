import { ChangedFile } from "./ChangedFile";
import { Reply } from "./Reply";

export type RawFileRequest = {
  repoId: number;
  prId: number;
  refType: number;
  file: ChangedFile;
};

export type RawFileResponse = {
  fileName: string;
  comments: [
    {
      commentId: number;
      prId: number;
      repoId: number;
      userId: number;
      userName: string;
      avatarUrl: string;
      disId: string;
      content: string;
      commentType: number;
      createAt: string;
      position: {
        baseSha: null | string;
        startSha: null | string;
        headSha: null | string;
        oldPath: string;
        newPath: string;
        positionType: string;
        newLine: number;
        oldLine: number;
        newStartLine: number;
        newEndLine: number;
        oldStartLine: number;
        oldEndLine: number;
        lineRange: {
          start: {
            lineCode: string;
          };
          end: {
            lineCode: string;
          };
        };
      } | null;
      reCommentList: Reply[];
    },
  ];
  oldCode: string;
  newCode: string;
};
