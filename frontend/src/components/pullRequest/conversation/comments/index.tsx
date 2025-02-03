import { useState } from "react";
import { Reply } from "../reply";
import { MarkdownEditor } from "../../../common/markdownEditor";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
import { useGetComments } from "../../../../api/queries/useGetComments";

export function Comments() {
  const { data } = useGetComments();
  const [isReplyEditorOpen, setIsReplyEditorOpen] = useState<
    Record<number, boolean>
  >({});

  function toggleReplyEditor(commentId: number) {
    setIsReplyEditorOpen((prev) => ({
      ...prev,
      [commentId]: !prev[commentId],
    }));
  }

  return (
    <section className="bg-white my-5 p-5 rounded-xl">
      <ul>
        {data?.map((comment) => (
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
              <div className="px-3 prose prose-lg max-w-none">
                <ReactMarkdown remarkPlugins={[remarkGfm]}>
                  {comment.content}
                </ReactMarkdown>
              </div>
              <p className="text-right">3개의 답글</p>
              <hr className="my-4" />
              <Reply />
            </article>
            <footer className="flex justify-end mt-2">
              <button onClick={() => toggleReplyEditor(comment.comment_id)}>
                {isReplyEditorOpen[comment.comment_id]
                  ? "답글 접기"
                  : "답글 추가"}
              </button>
            </footer>
            {isReplyEditorOpen[comment.comment_id] && <MarkdownEditor />}
          </li>
        ))}
      </ul>
    </section>
  );
}
