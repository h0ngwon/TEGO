import styled from "styled-components";
import { Table, Spin } from "antd";
import { useEffect, useState } from "react";
import moment from "moment";

function TimeTable(props) {
  const { courtsData } = props;
  const { loading } = props;
  const [columns, setColumns] = useState([]);

  const [rows, setRows] = useState([]);

  useEffect(() => {
    if (!courtsData) {
      return;
    }
    const courtSort = courtsData.sort(function (a, b) {
      return a.court - b.court;
    });
    const newColumns = [
      {
        title: "시간",
        dataIndex: "time",
        align: "center",
      },
      ...courtsData.map(courtData => {
        return {
          title: `${courtData.court} 코트`,
          dataIndex: courtData.court,
          align: "center",
          onCell: record => {
            const cell = record[courtData.court];
            return {
              style: {
                backgroundColor: cell && cell.reservable ? "white" : "#ececec",
              },
            };
          },
          render: cell => {
            return cell && cell.reservable ? (
              <a href={cell.url} target="blank">
                예약 가능
              </a>
            ) : (
              <div>예약 불가</div>
            );
          },
        };
      }),
    ];

    const times = [];
    const courtList = [];
    const rowsData = [];

    courtsData.forEach((courtData, courtIndex) => {
      courtData.timeList.forEach((timetable, index) => {
        if (!times.includes(timetable.time)) {
          times.push(timetable.time);
          courtList.push(courtData.court);
          rowsData.push({
            time: moment(timetable.time).format("HH:mm"),
            key: courtIndex + index,
          });
        }

        times.forEach((time, timesIndex) => {
          if (time === timetable.time) {
            rowsData[timesIndex][courtData.court] = {};
            rowsData[timesIndex][courtData.court].reservable =
              timetable.reservable;
            rowsData[timesIndex][courtData.court].url = timetable.url;
          }
        });
      });
    });

    setColumns([...newColumns]);
    setRows([...rowsData]);
  }, [courtsData]);

  return (
    <TimeTableBox
      dataSource={rows}
      columns={columns}
      pagination={false}
      loading={loading ? { indicator: <Spin /> } : false}
    />
  );
}

const TimeTableBox = styled(Table)`
  min-width: 800px;
`;

export default TimeTable;
