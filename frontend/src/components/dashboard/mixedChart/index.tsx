import React from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";

// 차트 데이터에 대한 타입 정의
interface ChartSeries {
  name: string;
  type: "area" | "bar" | "line";
  data: number[];
}

interface ChartData {
  series: ChartSeries[];
  options: ApexOptions;
}

export function MixedChart() {
  const [chartData] = React.useState<ChartData>({
    series: [
      {
        name: "Area Series",
        type: "area",
        data: [31, 40, 28, 51, 42, 109, 100],
      },
      {
        name: "Bar Series",
        type: "bar",
        data: [11, 32, 45, 32, 34, 52, 41],
      },
      {
        name: "Line Series",
        type: "line",
        data: [15, 11, 32, 18, 9, 24, 11],
      },
    ],
    options: {
      chart: {
        height: 350,
        // mixed chart의 기본 타입을 지정(여기서는 line으로 설정)
        type: "line",
      },
      stroke: {
        // 각 시리즈별 선 두께 (bar 시리즈는 stroke를 사용하지 않습니다)
        width: [2, 0, 2],
        curve: "smooth",
      },
      plotOptions: {
        bar: {
          columnWidth: "50%",
        },
      },
      fill: {
        // area 시리즈의 경우 채우기 옵션을 활성화
        opacity: [0.35, 1, 1],
      },
      xaxis: {
        // 예시 카테고리 (요일 등)
        categories: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
      },
      tooltip: {
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
      <h2 className="text-xl font-bold mb-5">Mixed Chart Example</h2>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        // 기본 타입은 options.chart.type과 맞춰줍니다.
        type="line"
        height={350}
      />
    </div>
  );
}
