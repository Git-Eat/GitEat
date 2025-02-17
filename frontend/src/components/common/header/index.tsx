import { Link } from "react-router-dom";
import logo from "../../../assets/images/logo.svg";
import darkHome from "../../../assets/images/home_dark.svg";
import darkFile from "../../../assets/images/file_dark.svg";
import darkWiki from "../../../assets/images/wiki_dark.svg";
import darkReport from "../../../assets/images/reports_dark.svg";
import logout from "../../../assets/images/logout.svg";

export function Header() {
  return (
    <header className="h-[100vh]  w-[230px] flex flex-col justify-between items-center box-border py-[36px] fixed border-r">
      <img src={logo} className="w-[48px]" alt="logout" />
      <div className="flex flex-col gap-[50px] ">
        <Link
          to="/repos"
          className={`rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50`}
        >
          <div className="flex justify-start items-center gap-2 ">
            <img className={`w-[30px]`} src={darkHome} />
            레포지토리 목록
          </div>
        </Link>
        <Link
          to="/dashboard"
          className={`rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[30px]`} src={darkFile} />
            대시보드
          </div>
        </Link>
        <Link
          to="/report"
          className={`rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[30px]`} src={darkReport} />
            성능 측정
          </div>
        </Link>
        <Link
          to="/guide"
          className={`rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[30px]`} src={darkWiki} />
            이용 가이드
          </div>
        </Link>
        {/* <LinkIcon to="/pulls" src="file" alt="pr_page" >
        <LinkIcon to="/wiki" src="wiki" alt="dashboard_page" />
        <LinkIcon to="/dashboard" src="report" alt="dashboard_page" /> */}
      </div>
      <div className="rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50 cursor-pointer">
        <div className="flex items-center gap-2">
          <img src={logout} alt="logout" className="w-[30px]" />
          로그아웃
        </div>
      </div>
    </header>
  );
}
