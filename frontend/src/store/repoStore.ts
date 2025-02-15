import { create } from "zustand";

type RepoStore = {
  repoId: number;
  setRepo: (id: number) => void;
};

export const useRepoStore = create<RepoStore>()((set) => ({
  repoId: 0,
  setRepo: (id: number) => set({ repoId: id }),
}));
