import { Timeline } from "../timeline";
import guideimage from "../../../assets/images/guide_repo.svg";

export function MergeRequestGuide() {
  const steps = [
    {
      id: 1,
      title: "프로젝트 추가",
      description: "gitLab의 project Id를 입력해주세요.",
      color: false,
    },
    {
      id: 2,
      title: "Merge Request 선택",
      description: "확인하고 싶은 MR을 선택해주세요.",
      color: false,
    },
    {
      id: 3,
      title: "코드 리뷰 확인",
      description: "코드 리뷰 참여자와 내역을 확인해보세요.",
      color: true,
    },
    {
      id: 4,
      title: "AI 코드 리뷰",
      description: "코드의 개선 사항을 확인해보세요.",
      color: true,
    },
    {
      id: 5,
      title: "변경된 코드 확인",
      description: "코드 변경사항을 확인해보세요.",
      color: true,
    },
  ];

  return (
    <div className="xl:px-20 px-10 py-20 mx-20 my-5 bg-white rounded-xl flex justify-between items-center shadow-sm">
      <div className="w-1/2">
        <Timeline steps={steps} />
      </div>
      <div className="w-1/2">
        <img src={guideimage} alt="example" className="rounded-lg shadow-xl" />
      </div>
    </div>
  );
}
