export function Loading() {
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <img src="/src/assets/images/spinner.svg" alt="loading" />
      <span className="text-gray-400">인증중입니다. 잠시만 기다려주세요!</span>
    </div>
  );
}
