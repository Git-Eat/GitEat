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
    <section>
      <ReactMarkdown remarkPlugins={[remarkGfm]}>
        {data?.description}
      </ReactMarkdown>
    </section>
  );
}
