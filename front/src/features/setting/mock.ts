export const data = [
  // 2023年6月 (30天)
  { date: '2023-06-01', count: 2 }, { date: '2023-06-02', count: 0 }, { date: '2023-06-03', count: 1 },
  { date: '2023-06-04', count: 3 }, { date: '2023-06-05', count: 4 }, { date: '2023-06-06', count: 0 },
  { date: '2023-06-07', count: 2 }, { date: '2023-06-08', count: 1 }, { date: '2023-06-09', count: 0 },
  { date: '2023-06-10', count: 5 }, { date: '2023-06-11', count: 0 }, { date: '2023-06-12', count: 3 },
  { date: '2023-06-13', count: 2 }, { date: '2023-06-14', count: 1 }, { date: '2023-06-15', count: 4 },
  { date: '2023-06-16', count: 0 }, { date: '2023-06-17', count: 2 }, { date: '2023-06-18', count: 3 },
  { date: '2023-06-19', count: 1 }, { date: '2023-06-20', count: 0 }, { date: '2023-06-21', count: 4 },
  { date: '2023-06-22', count: 2 }, { date: '2023-06-23', count: 5 }, { date: '2023-06-24', count: 0 },
  { date: '2023-06-25', count: 1 }, { date: '2023-06-26', count: 3 }, { date: '2023-06-27', count: 2 },
  { date: '2023-06-28', count: 0 }, { date: '2023-06-29', count: 4 }, { date: '2023-06-30', count: 1 },

  // 2023年7月 (31天)
  { date: '2023-07-01', count: 0 }, { date: '2023-07-02', count: 3 }, { date: '2023-07-03', count: 2 },
  { date: '2023-07-04', count: 5 }, { date: '2023-07-05', count: 1 }, { date: '2023-07-06', count: 0 },
  { date: '2023-07-07', count: 4 }, { date: '2023-07-08', count: 2 }, { date: '2023-07-09', count: 3 },
  { date: '2023-07-10', count: 1 }, { date: '2023-07-11', count: 0 }, { date: '2023-07-12', count: 2 },
  { date: '2023-07-13', count: 5 }, { date: '2023-07-14', count: 3 }, { date: '2023-07-15', count: 0 },
  { date: '2023-07-16', count: 1 }, { date: '2023-07-17', count: 4 }, { date: '2023-07-18', count: 2 },
  { date: '2023-07-19', count: 0 }, { date: '2023-07-20', count: 3 }, { date: '2023-07-21', count: 1 },
  { date: '2023-07-22', count: 5 }, { date: '2023-07-23', count: 2 }, { date: '2023-07-24', count: 0 },
  { date: '2023-07-25', count: 4 }, { date: '2023-07-26', count: 3 }, { date: '2023-07-27', count: 1 },
  { date: '2023-07-28', count: 2 }, { date: '2023-07-29', count: 0 }, { date: '2023-07-30', count: 5 },
  { date: '2023-07-31', count: 2 },

  // 2023年8月 (31天) - 略（类似模式）
  // ...（中间月份数据省略，按2-5次/天随机生成）

  // 2023年11月 (30天) - 高密度区间
  { date: '2023-11-01', count: 6 }, { date: '2023-11-02', count: 7 }, { date: '2023-11-03', count: 5 },
  { date: '2023-11-04', count: 8 }, { date: '2023-11-05', count: 4 }, { date: '2023-11-06', count: 9 },
  { date: '2023-11-07', count: 6 }, { date: '2023-11-08', count: 7 }, { date: '2023-11-09', count: 5 },
  { date: '2023-11-10', count: 8 }, { date: '2023-11-11', count: 3 }, { date: '2023-11-12', count: 7 },
  { date: '2023-11-13', count: 6 }, { date: '2023-11-14', count: 9 }, { date: '2023-11-15', count: 8 }, // 高密度
  { date: '2023-11-16', count: 7 }, { date: '2023-11-17', count: 6 }, { date: '2023-11-18', count: 5 },
  { date: '2023-11-19', count: 8 }, { date: '2023-11-20', count: 9 }, { date: '2023-11-21', count: 7 },
  { date: '2023-11-22', count: 6 }, { date: '2023-11-23', count: 8 }, { date: '2023-11-24', count: 5 },
  { date: '2023-11-25', count: 7 }, { date: '2023-11-26', count: 9 }, { date: '2023-11-27', count: 6 },
  { date: '2023-11-28', count: 8 }, { date: '2023-11-29', count: 7 }, { date: '2023-11-30', count: 9 },

  // 2023年12月 (31天) - 持续高密度
  { date: '2023-12-01', count: 8 }, { date: '2023-12-02', count: 6 }, { date: '2023-12-03', count: 7 },
  { date: '2023-12-04', count: 9 }, { date: '2023-12-05', count: 8 }, { date: '2023-12-06', count: 7 },
  { date: '2023-12-07', count: 10 }, { date: '2023-12-08', count: 6 }, { date: '2023-12-09', count: 8 },
  { date: '2023-12-10', count: 7 }, { date: '2023-12-11', count: 9 }, { date: '2023-12-12', count: 8 },
  { date: '2023-12-13', count: 6 }, { date: '2023-12-14', count: 10 }, { date: '2023-12-15', count: 7 },
  { date: '2023-12-16', count: 8 }, { date: '2023-12-17', count: 9 }, { date: '2023-12-18', count: 7 },
  { date: '2023-12-19', count: 8 }, { date: '2023-12-20', count: 6 }, { date: '2023-12-21', count: 9 },
  { date: '2023-12-22', count: 10 }, { date: '2023-12-23', count: 8 }, { date: '2023-12-24', count: 7 },
  { date: '2023-12-25', count: 9 }, { date: '2023-12-26', count: 6 }, { date: '2023-12-27', count: 8 },
  { date: '2023-12-28', count: 10 }, { date: '2023-12-29', count: 7 }, { date: '2023-12-30', count: 9 },
  { date: '2023-12-31', count: 8 },

  // 2024年1月 (31天) - 回归常规密度
  { date: '2024-01-01', count: 3 }, { date: '2024-01-02', count: 5 }, { date: '2024-01-03', count: 2 },
  // ...（常规密度数据，类似6-10月模式）

  // 2024年5月 (31天)
  { date: '2024-05-28', count: 4 }, { date: '2024-05-29', count: 2 }, { date: '2024-05-30', count: 5 },
  { date: '2024-05-31', count: 3 }  // 结束日
];

