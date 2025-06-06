import React, {useEffect, useRef} from 'react';
import {Chart, ChartEvent} from "@antv/g2";

const DailyTable: React.FC = () => {
  const container = useRef(null);
  const chart:any = useRef(null);

  let currentValues: [number, number] = [0, 1]; // 存储当前范围

  useEffect(() => {
    if (!chart.current) {
      chart.current = renderBarChart(container.current);
    }
  }, []);

  function renderBarChart(container:any) {
    const chart = new Chart({
      container,
    });


    chart.render();
    return chart;
  }


  return (
    <div className="App">
      <div ref={container}></div>
    </div>
  );
};

export default DailyTable;