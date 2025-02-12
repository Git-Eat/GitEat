import React from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";

// 각 시리즈에 대한 타입 정의
interface ChartSeries {
  name: string;
  data: number[];
}

// 차트 데이터 타입 정의
interface ChartData {
  series: ChartSeries[];
  options: ApexOptions;
}

export function MixedChartByLine() {
  const [chartData] = React.useState<ChartData>({
    series: [
      {
        name: "Line Series 1",
        data: [10, 20, 35, 30, 20, 30, 40],
      },
      {
        name: "Line Series 2",
        data: [15, 25, 30, 25, 15, 35, 45],
      },
      {
        name: "Line Series 3",
        data: [20, 35, 30, 20, 25, 40, 50],
      },
    ],
    options: {
      chart: {
        height: 350,
        type: "line",
        toolbar: {
          show: false, // 메뉴(툴바) 제거
        },
      },
      stroke: {
        width: 2,
        curve: "smooth",
      },
      dataLabels: {
        enabled: false,
      },
      markers: {
        size: 4,
      },
      xaxis: {
        categories: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      tooltip: {
        enabled: true,
        shared: true,
        intersect: false,
      },
      legend: {
        position: "top",
      },
    },
  });

  return (
    <div className="w-full justify-between px-10 py-5 bg-white rounded-lg h-full items-center">
      <h2 className="text-xl font-bold mb-5">3 Line Chart Example</h2>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        type="line"
        height={350}
      />
    </div>
  );
}
