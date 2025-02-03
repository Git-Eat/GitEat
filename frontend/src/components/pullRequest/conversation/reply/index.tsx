export function Reply() {
  return (
    <section className="bg-gray-100 my-3 p-5 rounded-xl">
      <header>
        <img
          src="/src/assets/images/user_profile_2.svg"
          alt="user profile"
          className="inline-block w-9 h-9 mr-2"
        />
        <h1 className="inline text-[16px] font-semibold mr-2">USER-02</h1>
        <img
          src="/src/assets/images/suggest.svg"
          alt="suggest"
          className="inline-block mr-2"
        />
        <time>5일 전</time>
      </header>
      <p className="px-10 py-3">
        잘했습니다. 토닥토닥,, 다만 commit 컨벤션 좀 지켜주세요~~
      </p>
    </section>
  );
}
