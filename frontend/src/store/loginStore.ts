import { create } from "zustand";

type LoginStore = {
  isLogin: boolean;
  setLogin: () => void;
  setLogout: () => void;
};

export const useLoginStore = create<LoginStore>()((set) => ({
  isLogin: true,
  setLogin: () => set(() => ({ isLogin: true })),
  setLogout: () => set(() => ({ isLogin: false })),
}));
