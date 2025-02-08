import { FileDiff } from "./fileDiff";
import { FileTree } from "./fileTree";
import { prFiles } from "./dummy";
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
  return (
    <div className="flex gap-[35px] justify-between mt-[30px]">
      <div className="w-1/5 max-w-56 bg-white p-[15px] min-h-[calc(100vh-300px)] h-fit rounded-xl">
        <FileTree />
      </div>
      <div className="w-fit">
        <div className="mt-4">
          <div className="border border-gray-200 p-4 my-4 rounded-md">
            {prFiles.map((file) => (
              <FileDiff key={file.fileName} file={file} />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
