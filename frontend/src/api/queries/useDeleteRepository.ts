import { useMutation } from "react-query";
import { deleteRepository } from "../pullRequest";

export const useDeleteRepository = (repoId: number) => {
  return useMutation("deleteRepository", () => deleteRepository(repoId), {});
};
