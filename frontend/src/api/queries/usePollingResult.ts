import { useState, useRef, useEffect } from "react";
import { getLighthouseResult } from "../statistics";

export const usePollingResult = (repoId: string, pollingInterval: number) => {
  const [isPolling, setIsPolling] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const [lastCreateAt, setLastCreateAt] = useState<string | null>(null);
  const pollingIntervalRef = useRef<number | null>(null);

  async function startPolling() {
    setIsPolling(true);
    pollingIntervalRef.current = window.setInterval(async () => {
      const result = await checkIfDataIsUpdated(repoId);
      if (result) {
        clearInterval(pollingIntervalRef.current!);
        setIsPolling(false);
        setIsUpdated(true);
      }
    }, pollingInterval);
  }

  const stopPolling = () => {
    if (pollingIntervalRef.current) {
      clearInterval(pollingIntervalRef.current);
    }
    setIsPolling(false);
  };

  async function checkIfDataIsUpdated(repoId: string): Promise<boolean> {
    const data = await getLighthouseResult(parseInt(repoId));
    const latestCreateAt = data.create_at;

    if (lastCreateAt === null) {
      return false;
    }

    if (lastCreateAt && latestCreateAt !== lastCreateAt) {
      setLastCreateAt(latestCreateAt);
      return true;
    }
    return false;
  }

  useEffect(() => {
    return () => {
      if (pollingIntervalRef.current) {
        clearInterval(pollingIntervalRef.current);
      }
    };
  }, []);

  return {
    isPolling,
    isUpdated,
    startPolling,
    stopPolling,
  };
};
