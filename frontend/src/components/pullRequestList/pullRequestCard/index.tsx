import branchOpen from "../../../assets/images/branch_open.svg";
import branchClose from "../../../assets/images/branch_close.svg";
import branchMerge from "../../../assets/images/branch_merge.svg";
import { Link, useParams } from "react-router-dom";
import { getParsedDate } from "../../../utils/getParsedDate";
const BRANCH_STATE_IMAGE = [branchMerge, branchOpen, branchClose];
const STATEMENT = ["Merged", "Opened", "Closed"];
interface PullRequestCardProps {
  prId: number;
  title: string;
  description: null | string;
  createAt: string;
  isOpened: number;
  userName: string;
}

export function PullRequestCard({
  prId,
  title,
  createAt,
  isOpened,
  userName,
}: PullRequestCardProps) {
  const { repoId, owner, title: repoTitle } = useParams();
  return (
    <Link to={`/repos/${repoId}/${prId}/${owner}/${repoTitle}/conversation`}>
      <div className="  bg-white rounded-xl p-7 flex justify-between hover:bg-gray-200 cursor-pointer border">
        <div className="flex flex-col gap-2">
          <div className="flex items-center gap-2">
            <img src={BRANCH_STATE_IMAGE[isOpened - 1]} alt="" />
            <span className="font-light">
              {STATEMENT[isOpened - 1]} {getParsedDate(createAt)}
            </span>
          </div>
          <div className="flex gap-[10px] items-center mb-[10px]">
            <span className="text-xl font-semibold">
              {userName}
              {"님의 "}
              {title}
            </span>
          </div>
        </div>
      </div>
    </Link>
  );
}
