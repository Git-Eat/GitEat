import { create } from "zustand";
import { ChangedFile } from "../api/types/ChangedFile";
import { Comment } from "../api/types/Comment";

type PRStore = {
  comments: Comment[];
  files: ChangedFile[];
  setComments: (newComments: Comment[]) => void;
  setFiles: (newFiles: ChangedFile[]) => void; // 파일 배열을 업데이트할 수 있도록 수정
};

export const usePRStore = create<PRStore>((set) => ({
  comments: [],
  files: [],
  setComments: (newComments: Comment[]) => set({ comments: newComments }),
  setFiles: (newFiles: ChangedFile[]) => set({ files: newFiles }),
}));
