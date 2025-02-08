import add from "../../assets/images/add.svg";
import { RepositoryCard } from "../../components/repositoryList/RepositoryCard";
import { useBooleanState } from "../../hooks/useBooleanState";
import RepositoryAddModal from "../../components/repositoryList/repositoryAddModal";
import { useGetRepositories } from "../../api/queries/useGetRepositories";
import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";

function Repositories() {
  const { data } = useGetRepositories();
  return (
    <>
      {data?.map((repo) => (
        <RepositoryCard
          key={repo.repoId}
          user={`${repo.userId}`}
          title={repo.name}
          repoId={repo.repoId}
          access={"private"}
          description={repo.description}
        />
      ))}
    </>
  );
}
export function RepositoryList() {
  const [isModalOpen, openModal, closeModal] = useBooleanState(false);

  return (
    <>
      <header className="w-full p-4">
        <div className="flex items-center align-center">
          <h1 className="text-[18px] font-semibold flex text-center pb-1">
            프로젝트 목록
          </h1>
        </div>
      </header>

      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="flex flex-col gap-5 m-auto w-[80%] pt-10">
          <button className="flex gap-2 justify-end" onClick={openModal}>
            <span className="hover:cursor-pointer">프로젝트 추가하기 </span>
            <img src={add} alt="add" />
          </button>
          <ErrorBoundary
            fallbackComponent={
              <p className="text-red-500">
                ⚠️ 프로젝트 목록을 불러오는 중 오류 발생
              </p>
            }
          >
            <Suspense fallback={<p>Loading...</p>}>
              <Repositories />
            </Suspense>
          </ErrorBoundary>
        </div>
      </main>

      <RepositoryAddModal closeModal={closeModal} isModalOpen={isModalOpen} />
    </>
  );
}
