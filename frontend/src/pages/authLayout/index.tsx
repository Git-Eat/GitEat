import { Navigate, Outlet } from "react-router-dom";
import { Header } from "../../components/common/header";
import { Suspense, useEffect } from "react";
import { ErrorBoundary } from "../../components/common/errorBoundery";
import { useLoginStore } from "../../store/loginStore";
import { useGetMe } from "../../api/queries/useGetMe";
import { Skeleton } from "@mui/material";

export function AuthLayout() {
  const { isLogin } = useLoginStore();
  const { setUser } = useLoginStore();

  const { data, isLoading, refetch } = useGetMe();
  useEffect(() => {
    refetch();
  }, []);
  useEffect(() => {
    if (!isLoading && data) {
      setUser(data);
    }
  }, [data, isLoading]);
  return isLogin ? (
    <div>
      <Header />
      <div className="flex justify-end">
        <main className="w-[calc(100vw-250px)]">
          <ErrorBoundary fallbackComponent={<>error!!</>}>
            <Suspense fallback={<Skeleton width="100%" height="100vh" />}>
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
