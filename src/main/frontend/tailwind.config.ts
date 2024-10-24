import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./src/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/components/**/*.{js,ts,jsx,tsx,mdx}",
    "./src/app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    colors: {
      primary: '#3D3D4E',
      secondary: '#37B6B8',
      black: '#000000',
      dark: {
        DEFAULT: '#242629',
        light: '#3E444D',
        semi: '#31353B',
      },
      gray: {
        45: '#454545',
        cc: '#CCC',
        d6: '#5D626B',
        d9: '#D9D9D9',
        66: '#666666',
        99: '#999',
        d2: '#D2D2D2',
        da: '#DADADA',
        dc: '#DCDCDC',
        ec: '#ECECEC',
        dark: '#262626',
      },
      red: {
        f1: '#F1416C',
      },
      category: {
        front: "#FF6F61",
      },
      white: '#FFFFFF',
      transparent: 'transparent',
    },
    screens: {
      'sm': '980px',
      'md': '1098px',
      'lg': '1280px',
      'desktop': '1440px',
      'custom-928': '928px',
      'custom-min': '723px',
    },
    extend: {
      maxWidth: {
        'custom': '1400px',
      },
      fontFamily: {
        pretendard: ["pretendard",'sans-serif'],
        dsdigi: ["DS-DIGI"],
        dsdigib: ["DS-DIGIB"],
        dsdigii: ["DS-DIGII"],
        dsdigit: ["DS-DIGIT"],
        spoqa: ["Spoqa Han Sans"],
        spoqab: ["Spoqa Han Sans Bold"],
        spoqal: ["Spoqa Han Sans Light"],
        roboto: ["Roboto"],
        helveticaBold: ["Helvetica-Black",'sans-serif'],
        helvetica:["Helvetica-Bold",'sans-serif'],
        gmarketsansBold:["GmarketSans-Bold",'sans-serif'],
        gmarketsansMedium:["GmarketSans-Medium",'sans-serif'],
        gmarketsansLight:["GmarketSans-Light",'sans-serif'],
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
    require('@tailwindcss/typography'),
    require('@tailwindcss/line-clamp')
  ],
};
export default config;
