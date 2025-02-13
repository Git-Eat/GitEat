import { Navigate, Outlet, useNavigate } from "react-router-dom";
import { Header } from "../../components/common/header";
import { Suspense, useEffect } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { useLoginStore } from "../../store/loginStore";
import { useGetMe } from "../../api/queries/useGetMe";

export function AuthLayout() {
  const { isLogin } = useLoginStore();
  const { setUser } = useLoginStore();
  const { data, isLoading } = useGetMe();
  useEffect(() => {
    if (!isLoading && data) {
      setUser(data);
    }
  }, [data, isLoading]);
  if (isLoading) return <>loading</>;
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
