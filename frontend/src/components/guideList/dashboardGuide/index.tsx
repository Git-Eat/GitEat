import guideimage from "../../../assets/images/guide_dashboard.svg";
import arrow from "../../../assets/images/arrow.svg";

export function DashboardGuide() {
  return (
    <div className="xl:px-20 py-20 md:mx-10 lg:mx-20 my-5 bg-white rounded-xl shadow-sm overflow-hidden">
      <div className="flex flex-wrap justify-center">
        <div className="py-10">
          <p className="pt-1 pb-8 pr-3 font-bold text-sm text-guide-main">
            총 commit 수
          </p>
          <p className="pt-10 pl-7 font-bold text-sm text-guide-main">
            총 MR 수
          </p>
        </div>
        <div className="py-10">
          <img src={arrow} alt="arrow" className="scale-x-[-1] pt-2 w-8" />
          <img src={arrow} alt="arrow" className="scale-x-[-1] pt-20 w-8" />
        </div>
        <img
          src={guideimage}
          alt="example"
          className="w-1/2 max-w-full rounded-lg shadow-xl"
        />
        <div className="py-10">
          <img src={arrow} alt="arrow" className="pt-2 w-8" />
          <img src={arrow} alt="arrow" className="pt-20 pb-20 w-8" />
          <img src={arrow} alt="arrow" className="pt-16 w-8" />
        </div>
        <div className="py-10">
          <p className="pt-1 pb-8 pl-3 font-bold text-sm text-guide-main">
            프로젝트 참여자
          </p>
          <p className="pt-10 pb-20 pl-3 font-bold text-sm text-guide-main">
            총 comment 수
          </p>
          <p className="pt-14 pl-3 font-bold text-sm text-guide-main">
            프로젝트 기여도
          </p>
        </div>
      </div>
    </div>
  );
}
