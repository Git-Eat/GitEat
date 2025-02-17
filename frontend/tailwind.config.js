/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        stats: {
          red: "#FE3333",
          yellow: "#FEAA33",
          green: "#00CC66",
        },
        guide: {
          background: "#F3F7FE",
          box: "#F9FAFF",
          light: "#6D9BFF",
          main: "#2469FF",
          text: "#5A5A5A",
        },
      },
      fontFamily: {
        pretendard: ['"Pretendard-Regular"', "sans-serif"],
      },
    },
  },
  plugins: [require("@tailwindcss/typography")],
};
