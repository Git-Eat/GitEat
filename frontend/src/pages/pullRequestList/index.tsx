import book from "../../assets/images/image.png";
import { PullRequestCard } from "../../components/pullRequestList/pullRequestCard";
import { dummy } from "./dummy";

export function PullRequestList() {
  return (
    <>
      <header className="w-full p-4">
        <div className="flex items-center gap-2 align-center">
          <img src={book} alt="pull request page" className="w-[18px]" />
          <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
            <span>TakeGitEasy</span>
            <span>/</span>
            <span>Git-Eat</span>
          </h1>
        </div>
        <div></div>
      </header>
      <main className=" w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <div className="flex flex-col gap-5 m-auto w-[80%] pt-10">
          {dummy.map((pr) => (
            <PullRequestCard
              key={pr.prId}
              prId={pr.prId}
              title={pr.title}
              description={pr.description}
              createAt={pr.createAt}
              isOpened={pr.isOpened}
            />
          ))}
        </div>
      </main>
    </>
  );
}
