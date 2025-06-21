import React, {useEffect, useRef} from 'react';
import * as echarts from 'echarts';
const DailyTable: React.FC = () => {
  const container = useRef(null);
  const chart:any = useRef(null);
  useEffect(() => {
    if (!chart.current) {
      chart.current = renderBarChart(container.current);
    }
  }, []);

  function renderBarChart(container:any) {
    const chart = echarts.init(container, 'dark');

    const option = {
      legend: {
        // Try 'horizontal'
        orient: 'vertical',
        type: 'scroll',
        right: -30,
        top: 'center',
      },
      visualMap: [
        {
          right: 5,
          type: 'continuous',
          min: 10,
          max: 240,
          dimension: 1, // series.data 的第2个维度（即 value[1]）被映射
          seriesIndex: 1, // 对第2个系列进行映射。
          inRange: {
            // 选中范围中的视觉配置
            color: ['blue', '#e14d4d', 'red'], // 定义了图形颜色映射的颜色列表，
            // 数据最小值映射到'blue'上，
            // 最大值映射到'red'上，
            // 其余自动线性计算。
            symbolSize: [0, 90] // 定义了图形尺寸的映射范围，
            // 数据最小值映射到0上，
            // 最大值映射到100上，
            // 其余自动线性计算。
          },
          outOfRange: {
            // 选中范围外的视觉配置
            symbolSize: [0, 100],
          }
        },
        {
          // 第二个 visualMap 组件
          type: 'piecewise', // 定义为分段型 visualMap
          pieces: [         // 自定义分段规则（核心配置）
            { min: 0, max: 10, color: 'green' },
            { min: 10, max: 30, color: 'yellow' },
            { min: 30, color: 'red' } // 无 max 表示到最大值
          ],
          orient: 'horizontal',
          center: ['90%', '20%'],
          min: 0,
          max: 50,
          dimension: 2,
          seriesIndex: 2,
        }
        //    ...
      ],
      dataset: [
        {
          // 这个 dataset 的 index 是 `0`。
          source: [
            ['Product', 'Sales', 'Price', 'Year'],
            ['Cake', 123, 32, 2011],
            ['Cereal', 231, 14, 2011],
            ['Tofu', 235, 5, 2011],
            ['Dumpling', 341, 25, 2011],
            ['Biscuit', 122, 29, 2011],
            ['Cake', 143, 30, 2012],
            ['Cereal', 201, 19, 2012],
            ['Tofu', 255, 7, 2012],
            ['Dumpling', 241, 27, 2012],
            ['Biscuit', 102, 34, 2012],
            ['Cake', 153, 28, 2013],
            ['Cereal', 181, 21, 2013],
            ['Tofu', 395, 4, 2013],
            ['Dumpling', 281, 31, 2013],
            ['Biscuit', 92, 39, 2013],
            ['Cake', 223, 29, 2014],
            ['Cereal', 211, 17, 2014],
            ['Tofu', 345, 3, 2014],
            ['Dumpling', 211, 35, 2014],
            ['Biscuit', 72, 24, 2014]
          ]
          // id: 'a'
        },
        {
          // 这个 dataset 的 index 是 `1`。
          // 这个 `transform` 配置，表示，此 dataset 的数据，来自于此 transform 的结果。
          transform: {
            type: 'filter',
            config: {dimension: 'Year', value: 2011}
          }
          // 我们还可以设置这些可选的属性： `fromDatasetIndex` 或 `fromDatasetId`。
          // 这些属性，指定了，transform 的输入，来自于哪个 dataset。例如，
          // `fromDatasetIndex: 0` 表示输入来自于 index 为 `0` 的 dataset 。又例如，
          // `fromDatasetId: 'a'` 表示输入来自于 `id: 'a'` 的 dataset。
          // 当这些属性都不指定时，默认认为，输入来自于 index 为 `0` 的 dataset 。
        },
        {
          // 这个 dataset 的 index 是 `2`。
          // 同样，这里因为 `fromDatasetIndex` 和 `fromDatasetId` 都没有被指定，
          // 那么输入默认来自于 index 为 `0` 的 dataset 。
          transform: {
            // 这个类型为 "filter" 的 transform 能够遍历并筛选出满足条件的数据项。
            type: 'filter',
            // 每个 transform 如果需要有配置参数的话，都须配置在 `config` 里。
            // 在这个 "filter" transform 中，`config` 用于指定筛选条件。
            // 下面这个筛选条件是：选出维度（ dimension ）'Year' 中值为 2012 的所有
            // 数据项。
            config: {dimension: 'Year', value: 2012}
          }
        },
        {
          // 这个 dataset 的 index 是 `3`。
          transform: {
            type: 'filter',
            config: {dimension: 'Year', value: 2013}
          }
        }
      ],
      series: [
        {
          type: 'pie',
          radius: 90,
          center: ['25%', '50%'],
          // 这个饼图系列，引用了 index 为 `1` 的 dataset 。也就是，引用了上述
          // 2011 年那个 "filter" transform 的结果。
          datasetIndex: 0,
          encode: {
            itemName: 'Product', // X轴维度（产品）
            value: 'Price'       // Y轴维度（销售额）
          },
          label: {
            show: true,
            position: 'outside',
            // formatter: '{b}: {c} ({d}%)' // 显示产品名称、数值及占比
          }
        },
        {
          type: 'pie',
          radius: 50,
          center: ['50%', '50%'],
          datasetIndex: 2
        },
        {
          label: {
            show: false,
            position: 'center'
          },
          labelLine: {
            show: false
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '30',
              fontWeight: 'bold'
            }
          },
          type: 'pie',
          radius: [50,60],
          center: ['75%', '50%'],
          datasetIndex: 3,
          roseType: 'area'
        }
      ]
    };

    chart.setOption(option);

    window.addEventListener('resize', () => chart.resize())

    return chart;
  }


  return (
    <div>
      <div id="main" ref={container} style={{width: '100%', height: 400}}></div>
    </div>
  );
};

export default DailyTable;