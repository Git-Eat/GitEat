import { useMemo } from "react";
import { Link, useLocation } from "react-router-dom";

type LinkKey = "fileChanges" | "commits" | "conversation";
const LINKS = {
  fileChanges: {
    url: "file-changes",
    text: "File Changes",
  },
  commits: {
    url: "commits",
    text: "Commits",
  },
  conversation: {
    url: "conversation",
    text: "Conversation",
  },
};
function LinkIcon({ to }: { to: LinkKey }) {
  const location = useLocation();
  console.log(location.pathname);
  const isSelected = useMemo(
    () => location.pathname === `/pull/${LINKS[to].url}`,
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

export function PrHeader() {
  return (
    <header className="w-full ">
      <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
        <span className="text-neutral-500">#1</span>
        <span>PR-테스트용</span>
      </h1>
      <span className="block text-neutral-400 text-sm ps-11">
        <span className="text-black">test-id</span> wants to merge 4 commit into
        <span className="px-2 border text-black rounded-full inline-block align-center">
          main
        </span>{" "}
        from{" "}
        <span className="px-2 border text-black rounded-full inline-block align-center">
          testbranch
        </span>
      </span>
      <nav className="flex gap-4 w-full mt-4 bg-white rounded-md">
        <LinkIcon to="conversation" />
        <LinkIcon to="commits" />
        <LinkIcon to="fileChanges" />
      </nav>
    </header>
  );
}
