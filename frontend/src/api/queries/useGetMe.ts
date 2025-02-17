import { useQuery } from "react-query";
import { getMe } from "../user";
import { useLoginStore } from "../../store/loginStore";

export const useGetMe = () => {
  const { setUser, setLogin } = useLoginStore();
  return useQuery("getMe", getMe, {
    enabled: false,
    onSuccess: (data) => {
      setUser(data);
      setLogin();
    },
    staleTime: 1000 * 60 * 10,
  });
};
