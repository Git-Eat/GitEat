import React from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";

// 주(week) 코드(예: "202504")를 받아 해당 주의 ISO 기준 월요일 날짜를 구하는 유틸 함수
function getDateFromYearWeek(yearWeek: string): Date {
  const year = parseInt(yearWeek.slice(0, 4), 10);
  const week = parseInt(yearWeek.slice(4), 10);
  // 연초 날짜에서 (week - 1) * 7일을 더한 후, 그 주의 월요일을 구함 (ISO 기준)
  const simple = new Date(year, 0, 1 + (week - 1) * 7);
  const dow = simple.getDay(); // 0: 일, 1: 월, ... 6: 토
  const ISOWeekStart = new Date(simple);
  // 만약 일 ~ 목(0~4) 사이라면, 해당 주의 월요일을 구하기 위해 현재 요일에서 조정
  if (dow <= 4) {
    ISOWeekStart.setDate(simple.getDate() - simple.getDay() + 1);
  } else {
    // 금, 토인 경우 다음 주 월요일로 보정
    ISOWeekStart.setDate(simple.getDate() + 8 - simple.getDay());
  }
  return ISOWeekStart;
}

// Date 객체를 "YYYY-MM-DD" 형식의 문자열로 변환하는 함수
function formatDate(date: Date): string {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  return `${year}-${month}-${day}`;
}

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
  // 주차 데이터를 문자열로 정의 (예: "202502"는 2025년의 2째주)
  const weekCategories = [
    "202502",
    "202503",
    "202504",
    "202505",
    "202506",
    "202507",
  ];

  // 각 주차 데이터를 유틸 함수를 통해 날짜 문자열(월요일 날짜)로 변환
  const formattedWeekCategories = weekCategories.map((week) =>
    formatDate(getDateFromYearWeek(week))
  );

  // "이해루"의 주간 데이터: 커밋 수, PR 수, 코멘트 수
  const [chartData] = React.useState<ChartData>({
    series: [
      {
        name: "Total Commit",
        data: [10, 13, 10, 11, 9, 12],
      },
      {
        name: "Total PR",
        data: [1, 2, 1, 2, 1, 2],
      },
      {
        name: "Total Comment",
        data: [5, 8, 4, 6, 5, 7],
      },
    ],
    options: {
      chart: {
        height: 350,
        type: "line",
        toolbar: {
          show: false,
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
        categories: formattedWeekCategories,
        title: {
          text: "Week Start Date (Monday)",
        },
      },
      yaxis: {
        title: {
          text: "Count",
        },
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
      <h2 className="text-xl font-bold mb-5">이해루 Weekly Metrics</h2>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        type="line"
        height={350}
      />
    </div>
  );
}
