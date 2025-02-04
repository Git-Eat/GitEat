import { useMemo } from "react";
import { Link, useLocation } from "react-router-dom";
import defaulProfile from "../../../assets/images/default_user.png";
import whiteLogo from "../../../assets/images/logo_white.svg";
import home from "../../../assets/images/home.svg";
import darkHome from "../../../assets/images/home_dark.svg";
import file from "../../../assets/images/file.svg";
import darkFile from "../../../assets/images/file_dark.svg";
import wiki from "../../../assets/images/wiki.svg";
import darkWiki from "../../../assets/images/wiki_dark.svg";
import report from "../../../assets/images/reports.svg";
import darkReport from "../../../assets/images/reports_dark.svg";

const images: Record<string, string> = {
  home: home,
  darkhome: darkHome,
  file: file,
  darkfile: darkFile,
  wiki: wiki,
  darkwiki: darkWiki,
  report: report,
  darkreport: darkReport,
};

function LinkIcon({ to, src, alt }: { to: string; src: string; alt: string }) {
  const location = useLocation();
  const isSelected = useMemo(
    () => location.pathname.startsWith(to),
    [location.pathname, to]
  );
  return (
    <Link
      to={to}
      className={`rounded-[12px] p-[5px] bg-black ${
        isSelected ? "bg-white" : ""
      }`}
    >
      <img
        className={`w-[30px]`}
        src={isSelected ? images["dark" + src] : images[src]}
        alt={alt}
      />
    </Link>
  );
}

export function Header() {
  return (
    <header className="h-[100vh] bg-black w-[115px] flex flex-col justify-between items-center box-border py-[36px] fixed">
      <img src={defaulProfile} alt="user profile" className="w-[51px]" />
      <div className="flex flex-col gap-[50px] items-center">
        <LinkIcon to="/repos" src="home" alt="repository_page" />
        <LinkIcon to="/pulls" src="file" alt="pr_page" />
        <LinkIcon to="/wiki" src="wiki" alt="dashboard_page" />
        <LinkIcon to="/dashboard" src="report" alt="dashboard_page" />
      </div>
      <img src={whiteLogo} alt="logout" />
    </header>
  );
}
