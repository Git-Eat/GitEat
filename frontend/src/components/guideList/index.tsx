import { DashboardGuide } from "./dashboardGuide";
import { FrontendStatisticsGuide } from "./frontendStatisticsGuide";
import { MergeRequestGuide } from "./mergeRequestGuide";

interface guidelistProps {
  title: string;
  content: string;
}

export function GuideList({ title, content }: guidelistProps) {
  return (
    <>
      <header className="text-center">
        <h3 className="w-1/3 m-auto my-3 py-2 bg-guide-main text-white font-bold text-2xl rounded-full">
          {title}
        </h3>
        <p className="text-guide-text">{content}</p>
      </header>
      <section>
        {title === "Merge Request" ? (
          <MergeRequestGuide />
        ) : title === "Dashboard" ? (
          <DashboardGuide />
        ) : (
          <FrontendStatisticsGuide />
        )}
      </section>
    </>
  );
}
