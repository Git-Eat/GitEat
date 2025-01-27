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
        <br />
        <div>
          <ul className="list-disc list-inside">
            <li>
              pr api test
              <span className="pl-5">69eea8</span>
            </li>
            <li>
              login 기능 구현
              <span className="pl-5">8dda5o</span>
            </li>
            <li>
              회원가입 기능 구현 및 Oauth 추가
              <span className="pl-5">54jh58w</span>
            </li>
          </ul>
        </div>
      </div>
      <hr className="my-4" />
      <p className="text-right">3개의 답글</p>
      <Reply />
    </div>
  );
}
