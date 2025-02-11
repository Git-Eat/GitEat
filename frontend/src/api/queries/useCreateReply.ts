import { useMutation } from "react-query";
import { createReply } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useCreateReply = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: ({
      content,
      discussionId,
    }: {
      content: string;
      discussionId: string;
    }) => {
      return createReply(repoId, prId, discussionId, content);
    },
    onSuccess: (newReply) => {
      setComments(
        comments.map((comment) =>
          comment.disId === newReply.disId
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
