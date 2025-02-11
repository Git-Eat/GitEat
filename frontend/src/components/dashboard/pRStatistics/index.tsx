import React from "react";
import ReactApexChart from "react-apexcharts";

export function BarChartExample() {
  // 차트에 표시할 데이터와 옵션을 state에 정의합니다.
  const [chartData] = React.useState({
    series: [
      {
        name: "user",
        data: [21, 22, 10, 28, 16, 21, 13, 30],
      },
    ],
    options: {
      chart: {
        height: 350,
        type: "bar" as const, // 리터럴 타입으로 지정
        toolbar: {
          show: false, // 메뉴(툴바) 제거
        },
      },
      colors: ["skyblue"],
      plotOptions: {
        bar: {
          columnWidth: "70%",
          distributed: true,
        },
      },
      dataLabels: {
        enabled: false,
      },
      legend: {
        show: false,
      },
      xaxis: {
        categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"],
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
          <span>총 PR 개수</span>
          <span>{9999}개</span>
        </h2>
      </div>
      <ReactApexChart
        series={chartData.series}
        options={chartData.options}
        type="bar"
        height={350}
      />
    </div>
  );
}
