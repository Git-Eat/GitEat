import { useParams } from "react-router-dom";
import { useGetTotalCommit } from "../../../api/queries/useGetTotalCommits";
import { useMemo } from "react";

export function TotalCommits() {
  const { baseRepoId } = useParams();
  const { data } = useGetTotalCommit(baseRepoId as string);
  const totalCommit = useMemo(() => {
    return data.total_commit.toLocaleString();
  }, [data.totalCommits]);
  return (
    <div className="w-full flex justify-between px-10 py-5 bg-white rounded-lg h-full items-center">
      <span className="text-xl font-bold">Commit 수</span>
      <span className="text-xl font-bold text-green-600">+{totalCommit}개</span>
    </div>
  );
}
