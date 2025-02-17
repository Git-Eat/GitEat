import { useMutation, useQueryClient } from "react-query";
import { deleteLighthouseResult } from "../statistics";

export const useDeleteLighthouseResult = (repoId: number) => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: () => deleteLighthouseResult(repoId),
    onSuccess: () => {
      queryClient.invalidateQueries(["lighthouse", repoId]);
    },
  });
};
