import { Timeline } from "../timeline";
import guideimage from "../../../assets/images/guide_statistics.svg";

export function FrontendStatisticsGuide() {
  const steps = [
    {
      id: 1,
      title: "빌드 정보 선택",
      description: "프로젝트의 빌드 정보를 선택해주세요.",
      color: false,
    },
    {
      id: 2,
      title: "프로젝트 URL 입력",
      description: "프로젝트 clone 주소를 입력해주세요.",
      color: false,
    },
    {
      id: 3,
      title: "frontend 경로 입력",
      description: "frontend 루트 디렉토리를 입력해주세요.",
      color: false,
    },
    {
      id: 4,
      title: "브랜치명 입력",
      description: "확인하고 싶은 브랜치명을 입력해주세요.",
      color: false,
    },
    {
      id: 5,
      title: "Frontend 성능 확인",
      description: "결과를 통해 프로젝트의 성능을 확인해보세요.",
      color: true,
    },
  ];

  return (
    <div className="p-20 mx-20 my-5 bg-white rounded-xl flex justify-between items-center shadow-sm">
      <div className="w-1/2">
        <img src={guideimage} alt="example" className="rounded-lg shadow-xl" />
      </div>
      <div className="w-1/2 pl-10">
        <Timeline steps={steps} />
      </div>
    </div>
  );
}
