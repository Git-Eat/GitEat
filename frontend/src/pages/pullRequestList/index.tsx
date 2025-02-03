import { Outlet } from "react-router-dom";
import { Header } from "../../components/pullRequest/header";
import { PrHeader } from "../../components/pullRequest/prHeader";

export function PullRequests() {
  return (
    <div className="w-[calc(100vw-115px)]">
      <Header />
      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl">
        <PrHeader />
        <Outlet />
      </main>
    </div>
  );
}
