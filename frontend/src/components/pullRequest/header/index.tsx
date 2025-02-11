import book from "../../../assets/images/image.png";
interface HeaderProps {
  owner: string | undefined;
  title: string | undefined;
}
export function Header({ owner, title }: HeaderProps) {
  return (
    <header className="w-full p-4">
      <div className="flex items-center gap-2 align-center">
        <img src={book} alt="pull request page" className="w-[18px]" />
        <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
          <span>{owner}</span>
          <span>/</span>
          <span>{title}</span>
        </h1>
      </div>
      <div></div>
    </header>
  );
}
