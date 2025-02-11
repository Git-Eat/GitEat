import { RadialBar } from "../radialBar";

export function FrontendStatics() {
  // 임시 데이터
  const PF: number = 92;
  const AB: number = 32;
  const BP: number = 56;
  const SEO: number = 87;
  // const FCP: string = "1.2";
  // const LCP: string = "2.3";
  // const TBT: string = "1.5";
  // const CLS: string = "0.03";
  // const SI: string = "0.5";

  return (
    <section className="p-5 bg-white rounded-xl">
      <header className="mb-5">
        <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
          Performance
        </h1>
        <p className="text-neutral-400 text-sm">
          프로젝트의 성능을 확인해 보세요!
        </p>
      </header>
      <hr />

      <article className="m-5">
        <header className="flex justify-between">
          <h2 className="text-[18px] font-semibold pb-1">FE 성능 측정</h2>
          <button
            className="bg-black text-white font-semibold px-3 py-1 rounded-xl"
            aria-label="성능 측정 시작"
          >
            성능 측정
          </button>
        </header>
        <p className="text-neutral-400 text-sm">
          본 성능 측정은 LightHouse 기준으로 측정되었습니다.
        </p>

        <section className="my-10 flex justify-center">
          <RadialBar series={[PF]} labels={["성능"]} />
          <RadialBar series={[AB]} labels={["접근성"]} />
          <RadialBar series={[BP]} labels={["권장사항"]} />
          <RadialBar series={[SEO]} labels={["검색엔진 최적화"]} />
        </section>
        <section className="my-10 flex justify-center items-center space-x-4">
          <div className="flex items-center space-x-1">
            <div
              className="w-4 h-4 rounded-full"
              style={{ backgroundColor: "#00CC66" }}
            ></div>
            <span className="text-neutral-400 text-sm">우수함 (0 - 49%)</span>
          </div>
          <div className="flex items-center space-x-1">
            <div
              className="w-4 h-4 rounded-full"
              style={{ backgroundColor: "#FEAA33" }}
            ></div>
            <span className="text-neutral-400 text-sm">
              개선이 필요함 (50 - 89%)
            </span>
          </div>
          <div className="flex items-center space-x-1">
            <div
              className="w-4 h-4 rounded-full"
              style={{ backgroundColor: "#FE3333" }}
            ></div>
            <span className="text-neutral-400 text-sm">부족함 (90 - 100%)</span>
          </div>
        </section>
        {/* <section>
          <header>
            <h3 className="text-[15px] font-semibold text-neutral-500">
              측정 항목
            </h3>
          </header>
          <section className="px-10 flex gap-10">
            <article className="bg-gray-100 my-3 p-5 rounded-xl flex items-center justify-between w-1/2">
              <div>
                <span className="text-sm font-bold mr-1">FCP</span>
                <span>(First Contentful Paint)</span>
                <p className="text-sm text-neutral-400">
                  페이지가 로드되기 시작하면서 첫 번째 콘텐츠가 표시되는 시간
                </p>
              </div>
              <p className="text-2xl font-bold">{FCP}초</p>
            </article>
            <article className="bg-gray-100 my-3 p-5 rounded-xl flex items-center justify-between w-1/2">
              <div>
                <span className="text-sm font-bold mr-1">LCP</span>
                <span>(Largest Contentful Paint)</span>
                <p className="text-sm text-neutral-400">
                  페이지에서 가장 큰 콘텐츠 요소가 로드되는 시간
                </p>
              </div>
              <p className="text-2xl font-bold">{LCP}초</p>
            </article>
          </section>
          <section className="px-10 flex gap-10">
            <article className="bg-gray-100 my-3 p-5 rounded-xl flex items-center justify-between w-1/2">
              <div>
                <span className="text-sm font-bold mr-1">TBT</span>
                <span>(Total Blocking Time)</span>
                <p className="text-sm text-neutral-400">
                  페이지가 로드되기 시작하면서 첫 번째 콘텐츠가 표시되는 시간
                </p>
              </div>
              <p className="text-2xl font-bold">{TBT}초</p>
            </article>
            <article className="bg-gray-100 my-3 p-5 rounded-xl flex items-center justify-between w-1/2">
              <div>
                <span className="text-sm font-bold mr-1">CLS</span>
                <span>(Cumulative Layout Shift)</span>
                <p className="text-sm text-neutral-400">
                  페이지 로딩 중 레이아웃 변화의 누적 정도
                </p>
              </div>
              <p className="text-2xl font-bold">{CLS}초</p>
            </article>
          </section>
          <section className="px-10 flex gap-10">
            <article className="bg-gray-100 my-3 p-5 rounded-xl flex items-center justify-between w-1/2">
              <div>
                <span className="text-sm font-bold mr-1">SI</span>
                <span>(Speed Index)</span>
                <p className="text-sm text-neutral-400">
                  페이지가 얼마나 빠르게 전체적으로 콘텐츠가 표시되는지의 지표
                </p>
              </div>
              <p className="text-2xl font-bold">{SI}초</p>
            </article>
          </section>
        </section> */}
      </article>
    </section>
  );
}
