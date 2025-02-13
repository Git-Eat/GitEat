import { useMutation, useQueryClient } from "react-query";
import { deleteRepository } from "../pullRequest";

export const useDeleteRepository = (repoId: number) => {
  const queryClient = useQueryClient();
  return useMutation("deleteRepository", () => deleteRepository(repoId), {
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: "getRepos",
      });
    },
  });
};
