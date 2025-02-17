import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { LightHouseResult } from "../../components/dashboard/lighthouseResult";
import { Header } from "../../components/pullRequest/header";
import { Skeleton } from "@mui/material";
import { useParams } from "react-router-dom";
import { useLoginStore } from "../../store/loginStore";

export function FrontendStatistics() {
  const { user } = useLoginStore();
  const { repoId } = useParams();
  const numericRepoId = Number(repoId);
  const { title, owner } = useParams();
  return (
    <>
      <Header title={title} owner={owner} />

      <main className="w-[98%] m-auto px-8 py-4 bg-stone-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <h1 className=" text-2xl font-semibold flex gap-4 text-center pb-1">
          <span>Performance</span>
        </h1>
        <span className="block text-neutral-400 text-sm">
          <span className="text-black">{user.name}</span> 님의 프로젝트 성능을
          측정해보세요!
        </span>
        <section className="my-5">
          <ErrorBoundary fallbackComponent={<>error occured</>}>
            <Suspense fallback={<Skeleton width="100%" height={500} />}>
              <LightHouseResult repoId={numericRepoId} />
            </Suspense>
          </ErrorBoundary>
        </section>
      </main>
    </>
  );
}
