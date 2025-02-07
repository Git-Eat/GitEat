import { Link, useParams } from "react-router-dom";
interface PullRequestCardProps {
  prId: number;
  title: string;
  description: string;
  createAt: number[];
  isOpened: number;
}

export function PullRequestCard({
  prId,
  title,
  description,
  createAt,
  isOpened,
}: PullRequestCardProps) {
  const { repoId } = useParams();
  console.log(repoId + " = > repoid");
  return (
    <Link to={`/repos/${repoId}/${prId}`}>
      <div className=" bg-white rounded-xl p-7 flex justify-between hover:bg-gray-200 cursor-pointer">
        <div>
          <div className="flex gap-[10px] items-center mb-[10px]">
            <span className="text-xl font-semibold">{title}</span>
          </div>
          <div className="flex flex-col">
            <div>{description}</div>
            <div>{createAt}</div>
            <div>{isOpened}</div>
          </div>
        </div>
      </div>
    </Link>
  );
}
