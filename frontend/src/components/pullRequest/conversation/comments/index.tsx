import axios from "axios";
import { useState } from "react";
import { useQuery } from "react-query";
import { Reply } from "../reply";
import { MarkdownEditor } from "../../../common/markdownEditor";
import spinner from "../../../../assets/images/spinner.svg";

interface Comment {
  comment_id: number;
  content: string;
  create_at: string;
}

async function fetchComments() {
  const response = await axios.get("/api/pr_id/comments");
  return response.data;
}

export function Comments() {
  const {
    data: comments,
    isLoading,
    isError,
  } = useQuery<Comment[]>(["comments"], fetchComments);
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});

  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }

  if (isLoading) {
    return <img src={spinner} alt="Loading..." />;
  }

  if (isError) {
    return <p>댓글을 불러오는 중 오류가 발생했습니다.</p>;
  }

  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <ul>
        {comments?.map((comment) => (
          <li key={comment.comment_id} className="mb-8">
            <header>
              <img
                src="/src/assets/images/user_profile_1.svg"
                alt="user profile"
                className="inline-block w-9 h-9 mr-2"
              />
              <h1 className="inline text-[16px] font-semibold">USER-01</h1>
              <time className="block px-11">{comment.create_at}</time>
            </header>
            <article>
              <hr className="my-4" />
              <p className="px-3">{comment.content}</p>
              <p className="text-right">3개의 답글</p>
              <hr className="my-4" />
              <Reply />
            </article>
            <footer className="flex justify-end mt-2">
              <button onClick={() => toggleReplyEditor(comment.comment_id)}>
                {isReplyEditorOpen[comment.comment_id] ? "취소" : "답글 추가"}
              </button>
            </footer>
            {isReplyEditorOpen[comment.comment_id] && <MarkdownEditor />}
          </li>
        ))}
      </ul>
    </section>
  );
}
