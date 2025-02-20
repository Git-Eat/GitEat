import { useGetRepositories } from "../../api/queries/useGetRepositories";
import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Link } from "react-router-dom";
import { Skeleton } from "@mui/material";
import { User } from "../../components/common/user";

const ACCESS_GRANT = ["private", "public", "internal"];
function Private() {
  return (
    <span className="px-5 border-yellow-500 border rounded-full text-xs text-yellow-500 flex items-center h-[24px]">
      private
    </span>
  );
}

function Public() {
  return (
    <span className="px-5 border-emerald-500 border rounded-full text-xs text-emerald-500 flex items-center h-[24px]">
      public
    </span>
  );
}
interface RepositoryCardProps {
  title: string;
  description: string;
  access: string;
  ownerName: string;
  repoId: number;
}

function RepositoryCard({
  title,
  description,
  access,
  ownerName,
  repoId,
}: RepositoryCardProps) {
  return (
    <Link to={`/repos/${repoId}/dashboard/${ownerName}/${title}`}>
      <div className="border bg-white rounded-xl p-7 flex justify-between hover:bg-gray-200 cursor-pointer items-top">
        <div>
          <div className="flex gap-[10px] items-center mb-[10px]">
            <span className="text-xl font-semibold">
              {ownerName} / {title}
            </span>
            {access === "public" ? <Public /> : <Private />}
          </div>
          <span>{description}</span>
        </div>
      </div>
    </Link>
  );
}
function Repositories() {
  const { data } = useGetRepositories();
  return (
    <>
      {data?.map((repo) => (
        <RepositoryCard
          key={repo.repoId}
          ownerName={repo.ownerName}
          title={repo.name}
          repoId={repo.repoId}
          access={ACCESS_GRANT[repo.access - 1]}
          description={repo.description}
        />
      ))}
    </>
  );
}
export function DashBoardList() {
  return (
    <>
      <header className="w-full p-4">
        <div className="flex items-center align-center justify-between">
          <h1 className="text-[18px] font-semibold flex text-center pb-1">
            프로젝트 현황 목록
          </h1>
          <User />
        </div>
      </header>

      <main className="w-[98%] m-auto px-8 py-4 bg-stone-50 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="flex flex-col gap-5 m-auto w-[80%] pt-10">
          <ErrorBoundary
            fallbackComponent={
              <p className="text-red-500">
                ⚠️ 프로젝트 목록을 불러오는 중 오류 발생
              </p>
            }
          >
            <Suspense
              fallback={
                <Skeleton variant="rectangular" width="100%" height="100%" />
              }
            >
              <Repositories />
            </Suspense>
          </ErrorBoundary>
        </div>
      </main>
    </>
  );
}
