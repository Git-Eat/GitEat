export function Reply() {
  return (
    <div className="bg-gray-100 my-3 p-5 rounded-xl">
      <div className="flex items-center gap-2">
        <img
          src="/src/assets/images/user_profile_2.svg"
          alt="user profile"
          className="max-w-9"
        />
        <h1 className="text-[16px] font-semibold">Lilyoung</h1>
        <img src="/src/assets/images/suggest.svg" alt="suggest" />
        <p>5일 전</p>
      </div>
      <p className="px-10 py-3">
        잘했습니다. 토닥토닥,, 다만 commit 컨벤션 좀 지켜주세요~~
      </p>
    </div>
  );
}
