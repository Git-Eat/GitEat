import { useQuery } from "react-query";
import { getRepsitories } from "../pullRequest";

export const useGetRepositories = () => {
  return useQuery("getRepos", getRepsitories, {
    staleTime: 1000 * 60 * 5,
  });
};
