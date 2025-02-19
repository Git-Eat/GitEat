import { useMemo } from "react";
import { Link, useLocation } from "react-router-dom";

type LinkKey = "fileChanges" | "commits" | "conversation";
const LINKS = {
  fileChanges: {
    url: "file-changes",
    text: "변경내역",
  },
  commits: {
    url: "commits",
    text: "커밋",
  },
  conversation: {
    url: "conversation",
    text: "리뷰",
  },
};
function LinkIcon({ to }: { to: LinkKey }) {
  const location = useLocation();
  const isSelected = useMemo(
    () => location.pathname.endsWith(LINKS[to].url),
    [location.pathname, to]
  );
  return (
    <Link
      to={LINKS[to].url}
      className={`rounded-full px-5 py-1 font-medium ${isSelected ? "bg-gray-600 text-gray-100" : "bg-white text-gray-600"}`}
    >
      {LINKS[to].text}
    </Link>
  );
}

interface PrHeaderProps {
  targetBranch: string;
  sourceBranch: string;
  userName: string;
  userId: number;
  prId: number;
  title: string;
}
export function PrHeader({
  targetBranch,
  sourceBranch,
  userName,
  prId,
  title,
}: PrHeaderProps) {
  return (
    <header className="w-full ">
      <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
        <span className="text-neutral-500">#{prId}</span>
        <span>{title}</span>
      </h1>
      <span className="block text-neutral-400 text-sm ">
        <span className="text-black">{userName}</span> 님이
        <span className="px-2 border bg-sky-50 text-black rounded-full inline-block align-center">
          {sourceBranch}
        </span>{" "}
        에서{" "}
        <span className="px-2 border bg-sky-50 text-black rounded-full inline-block align-center">
          {targetBranch}
        </span>
        로 병합을 요청하셨습니다🔥
      </span>
      <nav className="flex gap-4 w-full mt-4 bg-white rounded-md">
        <LinkIcon to="conversation" />
        <LinkIcon to="commits" />
        <LinkIcon to="fileChanges" />
      </nav>
    </header>
  );
}
