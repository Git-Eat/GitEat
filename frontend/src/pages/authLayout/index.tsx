import { Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";
import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";

export function AuthLayout() {
  return (
    <div>
      <Header />
      <div className="flex justify-end">
        <main className="w-[calc(100vw-130px)]">
          <ErrorBoundary fallbackComponent={<>error!!</>}>
            <Suspense fallback={<>loading...</>}>
              <Outlet />
            </Suspense>
          </ErrorBoundary>
        </main>
      </div>
    </div>
  );
}
