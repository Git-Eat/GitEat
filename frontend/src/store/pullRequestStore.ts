import { create } from "zustand";
import { ChangedFile } from "../api/types/ChangedFile";
import { Comment } from "../api/types/Comment";
import { PullRequest } from "../api/types/PullRequest";

type PRStore = {
  prDetail: PullRequest;
  comments: Comment[];
  files: ChangedFile[];
  setComments: (newComments: Comment[]) => void;
  setFiles: (newFiles: ChangedFile[]) => void; // 파일 배열을 업데이트할 수 있도록 수정
  setPR: (newPR: PullRequest) => void;
};

export const usePRStore = create<PRStore>((set) => ({
  comments: [],
  files: [],
  prDetail: {} as PullRequest,
  setComments: (newComments: Comment[]) => set({ comments: newComments }),
  setFiles: (newFiles: ChangedFile[]) => set({ files: newFiles }),
  setPR: (newPR: PullRequest) => set({ prDetail: newPR }),
}));
