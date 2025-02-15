import { useMutation } from "react-query";
import { createReply } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useCreateReply = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: ({ content, disId }: { content: string; disId: string }) => {
      return createReply(repoId, prId, disId, content);
    },
    onSuccess: (newReply) => {
      setComments(
        comments.map((comment) =>
          comment.disId === newReply.discussionId
            ? {
                ...comment,
                reCommentList: [...comment.reCommentList, newReply],
              }
            : comment
        )
      );
    },
  });
};
