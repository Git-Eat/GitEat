import { Link } from "react-router-dom";
import logo from "../../../assets/images/logo.svg";
import projects from "../../../assets/images/projects.svg";
import dashboard from "../../../assets/images/dashboard.svg";
import performance from "../../../assets/images/performace.svg";
import flag from "../../../assets/images/flag.svg";
import logout from "../../../assets/images/logout.svg";
import { useState } from "react";

export function Header() {
  const handleLogout = () => {
    localStorage.removeItem("access_token");
    document.cookie =
      "refreshToken" + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    window.location.href = "/";
  };
  const [isSelected, setSelect] = useState("/repos");
  return (
    <header className="h-[100vh]  w-[230px] flex flex-col justify-between items-center box-border py-[36px] fixed border-r">
      <img src={logo} className="w-[48px]" alt="logout" />
      <div className="flex flex-col gap-[50px] ">
        <Link
          to="/repos"
          onClick={() => setSelect("/repos")}
          className={`rounded-[12px] w-full px-[15px] py-[5px] hover:bg-[#dce9ff] transition ease-in-out delay-50 ${isSelected === "/repos" ? "bg-[#dce9ff]" : "bg-white"}`}
        >
          <div className="flex justify-start items-center gap-2 ">
            <img className={`w-[27px]`} src={projects} />
            레포지토리 목록
          </div>
        </Link>
        <Link
          to="/dashboard"
          onClick={() => setSelect("/dashboard")}
          className={`rounded-[12px]  px-[15px] py-[5px] hover:bg-[#dce9ff] transition ease-in-out delay-50 ${isSelected === "/dashboard" ? "bg-[#dce9ff]" : "bg-white"}`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[27px]`} src={dashboard} />
            대시보드
          </div>
        </Link>
        <Link
          to="/report"
          onClick={() => setSelect("/report")}
          className={`rounded-[12px]  px-[15px] py-[5px] hover:bg-[#dce9ff] transition ease-in-out delay-50 ${isSelected === "/report" ? "bg-[#dce9ff]" : "bg-white"}`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[27px]`} src={performance} />
            성능 측정
          </div>
        </Link>
        <Link
          to="/guide"
          onClick={() => setSelect("/guide")}
          className={`rounded-[12px]  px-[15px] py-[5px] hover:bg-[#dce9ff] transition ease-in-out delay-50 ${isSelected === "/guide" ? "bg-[#dce9ff]" : "bg-white"}`}
        >
          <div className="flex items-center gap-2 ">
            <img className={`w-[27px]`} src={flag} />
            이용 가이드
          </div>
        </Link>
        {/* <LinkIcon to="/pulls" src="file" alt="pr_page" >
        <LinkIcon to="/wiki" src="wiki" alt="dashboard_page" />
        <LinkIcon to="/dashboard" src="report" alt="dashboard_page" /> */}
      </div>
      <div
        className="rounded-[12px] p-[5px] hover:bg-[#e5f9c1] transition ease-in-out delay-50 cursor-pointer"
        onClick={handleLogout}
      >
        <div className="flex items-center gap-2">
          <img src={logout} alt="logout" className="w-[27px]" />
          로그아웃
        </div>
      </div>
    </header>
  );
}
