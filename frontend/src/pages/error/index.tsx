export function Error() {
  return (
    <div className="flex flex-col items-center justify-center h-screen gap-[20px]">
      {/* <img src="/src/assets/images/logo.svg" alt="error" /> */}
      <h1 className="text-4xl">로그인 중 에러가 발생했어요!</h1>
      <a
        className="border bg-black text-white px-[25px] py-[10px] rounded-[20px] hover:bg-gray-100 hover:text-black"
        href="/"
      >
        다시 로그인하기
      </a>
    </div>
  );
}
