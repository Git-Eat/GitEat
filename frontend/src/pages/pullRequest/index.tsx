import { Outlet, useLocation } from "react-router-dom";
import { Header } from "../../components/pullRequest/header";
import { PrHeader } from "../../components/pullRequest/prHeader";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { Suspense } from "react";

export function PullRequest() {
  const location = useLocation();
  return (
    <div>
      <Header />
      <main className="w-[98%] m-auto px-8 py-4 bg-gray-100 rounded-2xl min-h-[calc(100vh-100px)]">
        <PrHeader />
        <ErrorBoundary key={location.key} fallbackComponent={<>에러 발생!!</>}>
          <Suspense fallback={<>loading...</>}>
            <Outlet />
          </Suspense>
        </ErrorBoundary>
      </main>
    </div>
  );
}
