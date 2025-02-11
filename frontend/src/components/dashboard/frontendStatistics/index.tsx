import test from "../../../assets/images/test.svg";

export function FrontendStatics() {
  return (
    <section className="p-5 bg-white rounded-xl ">
      <header className="mb-5">
        <h1 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
          Performance
        </h1>
        <p className="text-neutral-400">프로젝트의 성능을 확인해 보세요!</p>
      </header>
      <hr />
      <article className="m-5">
        <div className="flex justify-between">
          <h2 className="text-[18px] font-semibold flex gap-2 text-center pb-1">
            FE 성능 측정
          </h2>
          <button className="bg-black text-white font-semibold px-3 py-1 rounded-xl">
            성능 측정
          </button>
        </div>
        <p className="text-neutral-400">
          본 성능 측정은 LightHouse 기준으로 측정되었습니다.
        </p>
        <div className="flex justify-center my-40">
          <img src={test} alt="before_test" />
        </div>
      </article>
    </section>
  );
}
