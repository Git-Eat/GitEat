import { useState, useRef, useEffect } from "react";
import { getLighthouseResult } from "../statistics";

export const usePollingResult = (repoId: string, pollingInterval: number) => {
  const [isPolling, setIsPolling] = useState(false);
  const [isUpdated, setIsUpdated] = useState(false);
  const lastCreateAtRef = useRef<string | null>(null);
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
      pollingIntervalRef.current = null;
    }
    setIsPolling(false);
  };

  async function checkIfDataIsUpdated(repoId: string): Promise<boolean> {
    const data = await getLighthouseResult(parseInt(repoId));
    const latestCreateAt = data.createAt;
    console.log(
      `latestCreateAt: ${latestCreateAt}, lastCreateAt: ${lastCreateAtRef.current}`
    );

    if (lastCreateAtRef.current === null) {
      lastCreateAtRef.current = latestCreateAt;
      console.log(
        `변경된 latestCreateAt: ${latestCreateAt}, 변경된 lastCreateAtRef: ${lastCreateAtRef.current}`
      );
      return false;
    }

    if (lastCreateAtRef.current && latestCreateAt !== lastCreateAtRef.current) {
      console.log("latestCreateAt와 lastCreateAt 불일치");
      lastCreateAtRef.current = latestCreateAt;
      setIsUpdated(true);
      stopPolling();
      return true;
    }
    return false;
  }

  useEffect(() => {
    return () => stopPolling();
  }, []);

  return {
    isPolling,
    isUpdated,
    startPolling,
    stopPolling,
  };
};
