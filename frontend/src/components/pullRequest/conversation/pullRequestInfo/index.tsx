import { useGetPullRequest } from "../../../../api/queries/useGetPullRequest";
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";

interface PullRequestInfoProps {
  repoId: number;
  prId: number;
}

export function PullRequestInfo({ repoId, prId }: PullRequestInfoProps) {
  const { data } = useGetPullRequest(repoId, prId);
  return (
    <section className="prose mb-8 bg-white my-5 p-5 rounded-xl">
      <ReactMarkdown remarkPlugins={[remarkGfm]}>
        {data?.description}
      </ReactMarkdown>
    </section>
  );
}
