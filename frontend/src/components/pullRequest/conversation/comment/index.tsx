import { Reply } from "../reply";

export function Comment() {
  return (
    <div className="bg-white my-5 p-5 rounded-xl">
      <div className="flex items-center gap-2">
        <img
          src="/src/assets/images/user_profile_1.svg"
          alt="user profile"
          className="max-w-9"
        />
        <h1 className="text-[16px] font-semibold">HAROOL</h1>
      </div>
      <p className="px-11">6일 전</p>
      <hr className="my-4" />
      <div className="px-3">
        <p>API PR 테스트용 PR입니다.</p>
      </div>
      <hr className="my-4" />
      <p className="text-right">3개의 답글</p>
      <Reply />
    </div>
  );
}
