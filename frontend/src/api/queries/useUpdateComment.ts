import { useMutation } from "react-query";
import { updateComment } from "../comment";
import { usePRStore } from "../../store/pullRequestStore";

export const useUpdateComment = (repoId: number, prId: number) => {
  const { comments, setComments } = usePRStore();

  return useMutation({
    mutationFn: ({
      commentId,
      content,
    }: {
      commentId: number;
      content: string;
      commentType: number;
    }) => updateComment(repoId, prId, commentId, content),
    onSuccess: (updateComment) => {
      setComments(
        comments.map((comment) =>
          comment.commentId === updateComment.commentId
            ? { ...comment, content: updateComment.content }
            : comment
        )
      );
    },
  });
};
