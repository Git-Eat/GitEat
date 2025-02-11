import Chart from "react-apexcharts";
import ApexCharts from "apexcharts";

interface RadialBarProps {
  series: number[];
  labels: string[];
}

export function RadialBar({ series, labels }: RadialBarProps) {
  const gradientColors = {
    red: { start: "#FE3333", end: "#FEC2C1" },
    yellow: { start: "#FEAA33", end: "#FFE5C1" },
    // yellow: { start: "#FFE5C1", end: "#FEAA33" },
    green: { start: "#00CC66", end: "#C1F9E0" },
    // green: { start: "#C1F9E0", end: "#00CC66" },
  };

  const selectedColor =
    series[0] <= 49
      ? gradientColors.red
      : series[0] <= 89
        ? gradientColors.yellow
        : gradientColors.green;

  const options: ApexCharts.ApexOptions = {
    chart: {
      type: "radialBar",
    },
    plotOptions: {
      radialBar: {
        hollow: {
          size: "55%",
        },
        dataLabels: {
          name: {
            offsetY: -12,
            color: "#888",
            fontSize: "12px",
          },
          value: {
            offsetY: 3,
            color: "#111",
            fontSize: "24px",
            fontWeight: "bold",
            show: true,
          },
        },
      },
    },
    fill: {
      colors: [selectedColor.end],
      type: "gradient",
      gradient: {
        shade: "dark",
        type: "diagonal2",
        gradientToColors: [selectedColor.start],
        stops: [0, 100],
      },
    },
    stroke: {
      lineCap: "round",
    },
    labels: labels,
  };

  return (
    <Chart
      options={options}
      series={series}
      type="radialBar"
      width={250}
      height={200}
    />
  );
}
