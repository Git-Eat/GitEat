import { useMutation } from "react-query";
import { addRepository } from "../pullRequest";

export const useAddRepository = () => {
  return useMutation(
    "addRepository",
    (repoId: number) => addRepository(repoId),
    {}
  );
};
