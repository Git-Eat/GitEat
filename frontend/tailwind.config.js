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
      },
    },
  },
  plugins: [require("@tailwindcss/typography")],
};
