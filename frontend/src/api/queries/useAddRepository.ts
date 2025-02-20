import { useMutation, useQueryClient } from "react-query";
import { addRepository } from "../pullRequest";

export const useAddRepository = () => {
  const queryClient = useQueryClient();
  return useMutation(
    "addRepository",
    (repoId: number) => addRepository(repoId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries("getRepos");
      },
    }
  );
};
