import React, { useMemo } from "react";
import ReactApexChart from "react-apexcharts";
import { useGetCommentStatistics } from "../../../api/queries/useGetCommentStatistics";
import { useParams } from "react-router-dom";
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

export function PieChart() {
  const { repoId } = useParams();
  const { data: commentStatistics } = useGetCommentStatistics(repoId as string);

  // commentStatistics가 준비되었으므로 각 배열 생성
  const commentCounts = useMemo(() => {
    if (!commentStatistics) return [];
    return commentStatistics.userList.map((user) => user.commentCount);
  }, [commentStatistics]);

  const commentUsers = useMemo(() => {
    if (!commentStatistics) return [];
    return commentStatistics.userList.map((user) => user.name);
  }, [commentStatistics]);

  const [state] = React.useState({
    series: commentCounts,
    options: {
      chart: {
        width: "100%",
        height: "100%",
        type: "pie" as const,
        fontFamily: "'Pretendard-Regular', sans-serif",
      },
      labels: commentUsers,
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
        formatter(val: number, opts: FormatterOptions): string[] {
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
          <span>총 코멘트 개수</span>
          <span>{commentStatistics?.totalComment}개</span>
        </h2>
      </div>
      <ReactApexChart
        series={state.series}
        options={state.options}
        type="pie"
        height={350}
      />
    </div>
  );
}
