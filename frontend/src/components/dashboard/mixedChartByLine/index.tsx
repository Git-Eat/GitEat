import React, { useMemo } from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";
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

interface ChartProps {
  data: {
    userId: number;
    name: string;
    userName: string;
    avatarUrl: string;
    totalCommit: number;
    totalMergeRequest: number;
    totalComment: number;
    weeklyInfo: [
      {
        week: number;
        mergeRequestCount: number;
        commitCount: number;
        commentCount: number;
      },
    ];
  };
  maxValue?: number;
}
export function MixedChartByLine({ data, maxValue }: ChartProps) {
  const weeklyComment = useMemo(() => {
    return data.weeklyInfo.map((info) => info.commentCount);
  }, [data]);
  const weeklyMR = useMemo(() => {
    return data.weeklyInfo.map((info) => info.mergeRequestCount);
  }, [data]);
  const weeklyCommit = useMemo(() => {
    return data.weeklyInfo.map((info) => info.commitCount);
  }, [data]);
  const formattedWeekCategories = useMemo(
    () =>
      data.weeklyInfo.map((info) =>
        formatDate(getDateFromYearWeek(String(info.week)))
      ),
    [data]
  );

  const [chartData] = React.useState<ChartData>({
    series: [
      {
        name: "주간 커밋",
        data: weeklyCommit,
      },
      {
        name: "주간 PR",
        data: weeklyMR,
      },
      {
        name: "주간 코멘트",
        data: weeklyComment,
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
        categories: formattedWeekCategories,
      },
      yaxis: {
        max: maxValue ? maxValue : undefined,
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
      <h2 className="text-xl font-bold mb-5">{data.name}의 주간 기록</h2>
      <ReactApexChart
        options={chartData.options}
        series={chartData.series}
        type="line"
        height={350}
      />
    </div>
  );
}
