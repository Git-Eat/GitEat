import { Suspense } from "react";
import { ErrorBoundary } from "../../common/errorBoundery";
import { useGetCommits } from "../../../api/queries/useGetCommit";
import { useParams } from "react-router-dom";
import { Skeleton } from "@mui/material";

export function Commits() {
  const { baseRepoId, prId } = useParams();
  const { data: commits } = useGetCommits(Number(baseRepoId), Number(prId));
  return (
    <div className="flex gap-[35px] justify-between mt-[30px]">
      <div className="mt-4 w-full">
        <ErrorBoundary fallbackComponent={<>ERROR!!</>}>
          <Suspense fallback={<Skeleton width="100%" height="100%" />}>
            <ul className="border border-gray-200 p-4 my-4 rounded-md w-full flex flex-col gap-5">
              {commits?.map((commit) => (
                <li
                  key={commit.commitId}
                  className="  bg-white rounded-xl p-7 flex justify-between hover:bg-gray-200 cursor-pointer"
                >
                  <div className="flex flex-col gap-2">
                    <div className="flex gap-[10px] items-center mb-[10px]">
                      <span className="text-xl font-semibold">
                        {commit.content}
                      </span>
                    </div>
                  </div>
                </li>
              ))}
            </ul>
          </Suspense>
        </ErrorBoundary>
      </div>
    </div>
  );
}
