import { useGetLighthouseResult } from "../../../api/queries/useGetLightHouseResult";
import { RadialBar } from "../radialBar";
import test from "../../../assets/images/test.svg";
import testError from "../../../assets/images/test_error.svg";
import { useBooleanState } from "../../../hooks/useBooleanState";
import LighthouseResultModal from "./lighthouseResultModal";
import { useDeleteLighthouseResult } from "../../../api/queries/useDeleteLighthouseResult";
import { useMemo, useEffect } from "react";
interface LightHouseResultProps {
  repoId: number;
}

export function LightHouseResult({ repoId }: LightHouseResultProps) {
  const { mutate: deleteLighthouseResult } = useDeleteLighthouseResult(repoId);
  const { data, refetch } = useGetLighthouseResult(repoId);
  const [isModalOpen, openModal, closeModal] = useBooleanState(false);
  const isResultZero = useMemo(() => {
    return (
      data?.performance === 0 &&
      data?.accessibility === 0 &&
      data?.bestPractices === 0 &&
      data?.seo === 0 &&
      data?.fcp === 0 &&
      data?.lcp === 0 &&
      data?.tbt === 0 &&
      data?.cls === 0 &&
      data?.si === 0
    );
  }, [data]);

  useEffect(() => {
    if (isResultZero) {
      deleteLighthouseResult();
    }
  }, [isResultZero, deleteLighthouseResult]);

  return (
    <>
      <section className="p-5 bg-white rounded-xl">
        <header className="mb-5">
          <div className="flex justify-between">
            <h2 className="text-[18px] font-semibold pb-1">FE 성능 측정</h2>
            <button
              className="bg-black text-white font-semibold px-3 py-1 rounded-xl"
              onClick={openModal}
            >
              성능 측정
            </button>
          </div>
          <p className="text-neutral-400 text-sm">
            본 성능 측정은 LightHouse 기준으로 측정되었습니다.
          </p>
        </header>
        <hr />

        <article className="m-5">
          {data ? (
            isResultZero ? (
              <div className="m-40 flex justify-center">
                <img src={testError} alt="result_error" />
              </div>
            ) : (
              <>
                <section className="my-10 flex flex-wrap justify-center">
                  <RadialBar series={[data.performance]} labels={["성능"]} />
                  <RadialBar
                    series={[data.accessibility]}
                    labels={["접근성"]}
                  />
                  <RadialBar
                    series={[data.bestPractices]}
                    labels={["권장사항"]}
                  />
                  <RadialBar series={[data.seo]} labels={["검색엔진 최적화"]} />
                </section>
                <section className="flex justify-center items-center space-x-4">
                  <article className="flex items-center space-x-1">
                    <div className="w-4 h-4 rounded-full bg-stats-red"></div>
                    <span className="text-neutral-400 text-sm">
                      부족함 (0 - 49)
                    </span>
                  </article>
                  <article className="flex items-center space-x-1">
                    <div className="w-4 h-4 rounded-full bg-stats-yellow"></div>
                    <span className="text-neutral-400 text-sm">
                      개선이 필요함 (50 - 89)
                    </span>
                  </article>
                  <article className="flex items-center space-x-1">
                    <div className="w-4 h-4 rounded-full bg-stats-green"></div>
                    <span className="text-neutral-400 text-sm">
                      우수함 (90 - 100)
                    </span>
                  </article>
                </section>

                <section className="my-10">
                  <hr className="mx-5 mb-5" />
                  <header className="m-10">
                    <span className="text-[19px] font-semibold">측정 항목</span>
                  </header>

                  <section className="px-10 grid lg:grid-cols-2 gap-10">
                    <article className="bg-stone-100 px-5 py-8 rounded-xl flex items-center justify-between col-span-1">
                      <div>
                        <span className="text-lg font-bold mr-1">FCP</span>
                        <span>(First Contentful Paint)</span>
                        <p className="mt-1 text-sm text-neutral-400">
                          페이지가 로드될 때 첫 번째 콘텐츠가 표시되는 시간
                        </p>
                      </div>
                      <p
                        className={`text-2xl font-bold ${
                          data.fcp <= 1.8
                            ? "text-stats-green"
                            : data.fcp <= 3.0
                              ? "text-stats-yellow"
                              : "text-stats-red"
                        }`}
                      >
                        {data.fcp}s
                      </p>
                    </article>
                    <article className="bg-stone-100 px-5 py-8 rounded-xl flex items-center justify-between col-span-1">
                      <div>
                        <span className="text-lg font-bold mr-1">LCP</span>
                        <span>(Largest Contentful Paint)</span>
                        <p className="mt-1 text-sm text-neutral-400">
                          페이지에서 가장 큰 콘텐츠 요소가 로드되는 시간
                        </p>
                      </div>
                      <p
                        className={`text-2xl font-bold ${
                          data.lcp <= 2.5
                            ? "text-stats-green"
                            : data.lcp <= 4.0
                              ? "text-stats-yellow"
                              : "text-stats-red"
                        }`}
                      >
                        {data.lcp}s
                      </p>
                    </article>
                    <article className="bg-stone-100 px-5 py-8 rounded-xl flex items-center justify-between col-span-1">
                      <div>
                        <span className="text-lg font-bold mr-1">TBT</span>
                        <span>(Total Blocking Time)</span>
                        <p className="mt-1 text-sm text-neutral-400">
                          페이지 로딩 동안 사용자 입력을 차단한 총 시간
                        </p>
                      </div>
                      <p
                        className={`text-2xl font-bold ${
                          data.tbt <= 200
                            ? "text-stats-green"
                            : data.tbt <= 600
                              ? "text-stats-yellow"
                              : "text-stats-red"
                        }`}
                      >
                        {data.tbt}ms
                      </p>
                    </article>
                    <article className="bg-stone-100 px-5 py-8 rounded-xl flex items-center justify-between col-span-1">
                      <div>
                        <span className="text-lg font-bold mr-1">CLS</span>
                        <span>(Cumulative Layout Shift)</span>
                        <p className="mt-1 text-sm text-neutral-400">
                          페이지 로딩 중 레이아웃 변화의 누적 정도
                        </p>
                      </div>
                      <p
                        className={`text-2xl font-bold ${
                          data.cls <= 0.1
                            ? "text-stats-green"
                            : data.cls <= 0.25
                              ? "text-stats-yellow"
                              : "text-stats-red"
                        }`}
                      >
                        {data.cls}s
                      </p>
                    </article>
                    <article className="bg-stone-100 px-5 py-8 rounded-xl flex items-center justify-between col-span-1">
                      <div>
                        <span className="text-lg font-bold mr-1">SI</span>
                        <span>(Speed Index)</span>
                        <p className="mt-1 text-sm text-neutral-400">
                          화면의 시각적 콘텐츠가 표시되는 속도
                        </p>
                      </div>
                      <p
                        className={`text-2xl font-bold ${
                          data.si <= 3.4
                            ? "text-stats-green"
                            : data.si <= 5.8
                              ? "text-stats-yellow"
                              : "text-stats-red"
                        }`}
                      >
                        {data.si}s
                      </p>
                    </article>
                  </section>
                </section>
              </>
            )
          ) : (
            <div className="m-40 flex justify-center">
              <img src={test} alt="result_default" />
            </div>
          )}
        </article>
      </section>

      <LighthouseResultModal
        closeModal={closeModal}
        isModalOpen={isModalOpen}
        refetch={refetch}
      />
    </>
  );
}
