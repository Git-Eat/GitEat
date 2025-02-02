import { useMemo } from "react";
import { Link, useLocation } from "react-router-dom";

function LinkIcon({ to, src, alt }: { to: string; src: string; alt: string }) {
  const location = useLocation();
  const isSelected = useMemo(
    () => location.pathname === to,
    [location.pathname, to]
  );
  console.log(location.pathname);
  return (
    <Link
      to={to}
      className={`rounded-[12px] p-[5px] bg-black ${
        isSelected ? "bg-white" : ""
      }`}
    >
      <img
        className={`w-[30px]`}
        src={`/src/assets/images/${isSelected ? src + "_dark" : src}.svg`}
        alt={alt}
      />
    </Link>
  );
}

export function Header() {
  return (
    <header className="h-[100vh] bg-black w-[115px] flex flex-col justify-between items-center box-border py-[36px] fixed">
      <img
        src="/src/assets/images/default_user.png"
        alt="user profile"
        className="w-[51px]"
      />
      <div className="flex flex-col gap-[50px] items-center">
        <LinkIcon to="/repos" src="home" alt="repository_page" />
        <LinkIcon to="/pulls" src="file" alt="pr_page" />
        <LinkIcon to="/wiki" src="wiki" alt="dashboard_page" />
        <LinkIcon to="/dashboard" src="reports" alt="dashboard_page" />
      </div>
      <img src="/src/assets/images/logo_white.svg" alt="logout" />
    </header>
  );
}
