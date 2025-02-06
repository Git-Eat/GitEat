import { useQuery } from "react-query";
import { getRepsitories } from "../pullRequest";
import { Repository } from "../types/Repository";

export const useGetRepositories = () => {
  return useQuery<Repository[]>("getRepos", getRepsitories, {
    staleTime: 1000 * 60 * 5,
    retry: 0,
    suspense: true,
    useErrorBoundary: true,
  });
};
