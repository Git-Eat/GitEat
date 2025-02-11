import { useMutation } from "react-query";
import { createComment } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useCreateComment = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: ({ content }: { content: string }) => {
      return createComment(repoId, prId, content);
    },
    onSuccess: (newComment) => {
      setComments([...comments, newComment]);
    },
  });
};
