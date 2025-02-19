import { useGetPullRequest } from "../../../../api/queries/useGetPullRequest";
import ReactMarkdown from "react-markdown";
import sadCat from "../../../../assets/images/sad_cat.svg";
import remarkGfm from "remark-gfm";

interface PullRequestInfoProps {
  repoId: number;
  prId: number;
}

export function PullRequestInfo({ repoId, prId }: PullRequestInfoProps) {
  const { data } = useGetPullRequest(repoId, prId);
  return (
    <section className=" mb-8 bg-white my-5 p-5 rounded-xl ">
      {data?.description !== "" && (
        <div className="prose">
          <ReactMarkdown remarkPlugins={[remarkGfm]}>
            {data?.description}
          </ReactMarkdown>
        </div>
      )}
      {data?.description === "" && (
        <>
          <div className="py-20 opacity-50 m-auto flex flex-col justify-center items-center gap-5 flex flex-col justify-center items-center">
            <img src={sadCat} alt="noData" />
            <span>MR 설명이 없어요! 적극적인 설명이 필요해요....</span>
          </div>
        </>
      )}
    </section>
  );
}
