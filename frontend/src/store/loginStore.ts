import { create } from "zustand";
import { User } from "../api/types/User";

type LoginStore = {
  isLogin: boolean;
  user: User;
  setLogin: () => void;
  setLogout: () => void;
  setUser: (userData: User) => void;
};

export const useLoginStore = create<LoginStore>()((set) => ({
  isLogin: true,
  user: {} as User,
  setLogin: () => set(() => ({ isLogin: true })),
  setLogout: () => set(() => ({ isLogin: false })),
  setUser: (userData: User) => set({ user: userData }),
}));
