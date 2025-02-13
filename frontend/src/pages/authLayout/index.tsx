import { Navigate, Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";
import { Suspense } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { useLoginStore } from "../../store/loginStore";

export function AuthLayout() {
  const { isLogin } = useLoginStore();

  return isLogin ? (
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
  ) : (
    <Navigate to="/" replace />
  );
}
