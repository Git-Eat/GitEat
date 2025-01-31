import { useEffect, useState } from "react";
import { Reply } from "../reply";
import axios from "axios";
import { MarkdownEditor } from "../../../common/markkdownEditor";

interface Comment {
  comment_id: number;
  content: string;
  create_at: string;
}

export function Comments() {
  const [comments, setComments] = useState<Comment[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});

  useEffect(() => {
    axios
      .get("/api/pr_id/comments")
      .then((response) => {
        setComments(response.data);
      })
      .catch((error) => {
        console.log("Error fetching post:", error);
      })
      .finally(() => {
        setLoading(false);
      });
  }, []);

  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div className="bg-white my-5 p-5 rounded-xl">
      <ul>
        {comments.map((comment) => (
          <li key={comment.comment_id} className="mb-8">
            <div className="flex items-center gap-2">
              <img
                src="/src/assets/images/user_profile_1.svg"
                alt="user profile"
                className="max-w-9"
              />
              <h1 className="text-[16px] font-semibold">USER</h1>
            </div>
            <p className="px-11">{comment.create_at}</p>
            <hr className="my-4" />
            <div className="px-3">
              <p>{comment.content}</p>
            </div>
            <p className="text-right">3개의 답글</p>
            <hr className="my-4" />
            <Reply />
            <div className="flex justify-end mt-2">
              <button onClick={() => toggleReplyEditor(comment.comment_id)}>
                {isReplyEditorOpen[comment.comment_id] ? "취소" : "답글 추가"}
              </button>
            </div>
            {isReplyEditorOpen[comment.comment_id] && <MarkdownEditor />}
          </li>
        ))}
      </ul>
    </div>
  );
}
