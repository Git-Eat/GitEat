import React, { useMemo } from "react";
import ReactApexChart from "react-apexcharts";
import { useGetPrStatistics } from "../../../api/queries/useGetPrStatistics";
import { useParams } from "react-router-dom";

export function BarChartExample() {
  const { repoId } = useParams();
  const { data: statistic } = useGetPrStatistics(repoId as string);

  const prCount = useMemo(() => {
    if (!statistic) return []; // null 대신 빈 배열 반환
    return statistic.userList.map((user) => user.mergeRequestCount);
  }, [statistic]);

  const participants = useMemo(() => {
    if (!statistic) return []; // null 대신 빈 배열 반환
    return statistic.userList.map((user) => user.name);
  }, [statistic]);

  const [chartData] = React.useState({
    series: [
      {
        name: "MR 등록",
        data: prCount,
      },
    ],
    options: {
      chart: {
        // height: 350,
        type: "bar" as const,
        toolbar: {
          show: false, // 메뉴(툴바) 제거
        },
      },
      colors: ["skyblue"],
      plotOptions: {
        bar: {
          columnWidth: `${statistic ? statistic.userList.length * 2.5 : 30}px`,
          distributed: false,
        },
      },
      dataLabels: {
        enabled: false,
      },
      legend: {
        show: false,
      },
      xaxis: {
        categories: participants,
        labels: {
          style: {
            colors: ["#FF4560"],
            fontSize: "12px",
          },
        },
      },
    },
  });

  return (
    <div className="w-full justify-between px-10 py-5 bg-white rounded-lg h-full items-center">
      <div className="mb-10">
        <h2 className="text-xl font-bold flex justify-between">
          <span>총 MR 개수</span>
          <span>{statistic?.totalMergeRequest}개</span>
        </h2>
      </div>
      <ReactApexChart
        series={chartData.series}
        options={chartData.options}
        type="bar"
        // height={}
      />
    </div>
  );
}
