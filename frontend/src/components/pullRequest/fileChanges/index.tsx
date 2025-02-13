import { Suspense } from "react";
import { usePRStore } from "../../../store/pullRequestStore";
import { ErrorBoundary } from "../../common/errorBoundery";
import { FileDiff } from "./fileDiff";
import { FileTree } from "./fileTree";
import { useParams } from "react-router-dom";
/**
 * DONE
 * - 트리 렌더링
 * - 파일 렌더링
 * - diff 렌더링
 *
 * TODO
 * - 파일 트리 렌더링용 파싱 데이터
 * - API 연동
 * - ADD 로직
 * - READ 로직
 * - DELETE 로직
 * - UPDATE 로직
 */

export function FileChanges() {
  const { baseRepoId, prId } = useParams();
  const { files } = usePRStore();
  return (
    <div className="flex gap-[35px] justify-between mt-[30px]">
      <div className="w-1/5 max-w- bg-white p-[15px] min-h-[calc(100vh-300px)] h-fit rounded-xl">
        <FileTree />
      </div>
      <div className="w-4/5">
        <div className="mt-4 w-full">
          <div className="border border-gray-200 p-4 my-4 rounded-md w-full">
            {files.map((file) => (
              <ErrorBoundary key={file.fileId} fallbackComponent={<>ERROR!!</>}>
                <Suspense fallback={<>loading...</>}>
                  <FileDiff
                    file={file}
                    repoId={Number(baseRepoId)}
                    prId={Number(prId)}
                  />
                </Suspense>
              </ErrorBoundary>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
