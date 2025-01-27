export function Header() {
  return (
    <header className="border border-gray-200 w-[calc(100vw-115px)] p-4">
      <div className="flex items-center gap-2 align-center">
        <img
          src="src/assets/images/image.png"
          alt="pull request page"
          className="w-[18px]"
        />
        <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
          <span>TakeGitEasy</span>
          <span>/</span>
          <span>Git-Eat</span>
        </h1>
      </div>
      <div></div>
    </header>
  );
}
