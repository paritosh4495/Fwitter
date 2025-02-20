import { JSX } from "react";

const MONTHS: string[] = [
    '',
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December',
  ];
  
  export const getMonths = (): JSX.Element[] => {
    return MONTHS.map((month, index) => (
      <option value={index} key={month}>
        {index === 0 ? '' : month}
      </option>
    ));
  };
  
  export const getDays = (): JSX.Element[] => {
    let options: JSX.Element[] = [];
    for (let i = 0; i < 32; i++) {
      if (i === 0) {
        options.push(<option value={0} key={i}></option>);
      } else {
        options.push(<option value={i} key={i}>{i}</option>);
      }
    }
    return options;
  };
  
  export const getYears = (): JSX.Element[] => {
    let options: JSX.Element[] = [];
    for (let i = 2025; i > 1901; i--) {
      if (i === 2025) {
        options.push(<option value={i} key={i}></option>);
      } else {
        options.push(<option value={i} key={i}>{i}</option>);
      }
    }
    return options;
  };
  
  