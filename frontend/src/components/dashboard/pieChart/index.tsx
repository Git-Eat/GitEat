import React from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";

// formatter 함수에서 사용하는 opts 객체의 타입 정의
interface FormatterOptions {
  seriesIndex: number;
  dataPointIndex: number;
  w: {
    globals: {
      labels: string[];
      // 필요에 따라 다른 속성들을 추가할 수 있습니다.
    };
  };
}

interface ChartData {
  series: number[];
  options: ApexOptions;
}

export function PieChart() {
  const [chartData] = React.useState<ChartData>({
    series: [25, 15, 44, 55, 41, 17],
    options: {
      chart: {
        width: "100%",
        height: "100%",
        type: "pie" as const,
        toolbar: {
          show: false, // 메뉴(툴바) 제거
        },
      },
      labels: [
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ],
      theme: {
        monochrome: {
          enabled: true,
        },
      },
      plotOptions: {
        pie: {
          dataLabels: {
            offset: -5,
          },
        },
      },
      grid: {
        padding: {
          top: 0,
          bottom: 0,
          left: 0,
          right: 0,
        },
      },
      dataLabels: {
        formatter: function (val: number, opts: FormatterOptions): string[] {
          const name = opts.w.globals.labels[opts.seriesIndex];
          return [name, val.toFixed(1) + "%"];
        },
      },
      legend: {
        show: false,
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
        type="pie"
        height={350}
      />
    </div>
  );
}
