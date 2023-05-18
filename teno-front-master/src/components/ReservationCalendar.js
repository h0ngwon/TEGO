import { useEffect, useRef, useState } from "react";
import styled from "styled-components";
import { Button, Typography, Row, Col } from "antd";
import moment from "moment";
import "moment/locale/ko";
import Calendar from "@toast-ui/react-calendar";
import { useQuery } from "react-query";
import { useNavigate } from "react-router-dom";
import { getCalendarData } from "../api";

function ReservationCalendar(props) {
  const { centerId } = props;

  /* Variables */
  const navigate = useNavigate();
  const calendarRef = useRef();
  const [month, setMonth] = useState(moment());
  const [schedules, setSchedules] = useState([]);
  const { data } = useQuery(["calendar", month], () =>
    getCalendarData(centerId, month.format("YYYY-MM")),
  );

  /* Functions */
  const handleClickNextButton = () => {
    const calendarInstance = calendarRef.current.getInstance();
    calendarInstance.next();
    setMonth(month.add(1, "month").clone());
  };

  const handleClickPrevButton = () => {
    const calendarInstance = calendarRef.current.getInstance();
    calendarInstance.prev();
    setMonth(month.subtract(1, "month").clone());
  };

  const handleClickSchedule = ev => {
    const getScheduleDate = ev.schedule.start;
    const scheduleYear = getScheduleDate.getFullYear();
    const scheduleMonth =
      getScheduleDate.getMonth() + 1 >= 10
        ? getScheduleDate.getMonth() + 1
        : `0${getScheduleDate.getMonth() + 1}`;

    const scheduleDay =
      getScheduleDate.getDate() >= 10
        ? getScheduleDate.getDate()
        : `0${getScheduleDate.getDate()}`;

    navigate(
      `/centers/${centerId}/timetable?date=${scheduleYear}-${scheduleMonth}-${scheduleDay}`,
    );
  };

  useEffect(() => {
    if (!data) {
      return;
    }

    if (moment().format("YYYY-MM") === month.format("YYYY-MM")) {
      const newSchedules = data
        .filter(schedule => new Date().getDate() < schedule.date)
        .map((scheduleData, index) => {
          return {
            id: index,
            calendarId: "0",
            title: `${scheduleData.reservableCount}/${scheduleData.totalCount}`,
            category: "allday",
            start: `${month.format("YYYY-MM")}-${scheduleData.date}`,
            end: `${month.format("YYYY-MM")}-${scheduleData.date}`,
          };
        });
      setSchedules(newSchedules);
      return;
    }

    const newSchedules = data.map((scheduleData, index) => {
      return {
        id: index,
        calendarId: "0",
        title: `${scheduleData.reservableCount}/${scheduleData.totalCount}`,
        category: "allday",
        start: `${month.format("YYYY-MM")}-${scheduleData.date}`,
        end: `${month.format("YYYY-MM")}-${scheduleData.date}`,
      };
    });
    /* eslint-disable-next-line */
    setSchedules(newSchedules);
  }, [data]);

  return (
    <Row justify="center" align="middle">
      <Col span={24}>
        <ButtonWrapper justify="center" align="middle">
          <FlexBox md={1} xs={2}>
            <Button
              size="large"
              shape="circle"
              disabled={month <= moment()}
              onClick={handleClickPrevButton}
            >
              &lt;
            </Button>
          </FlexBox>
          <Col lg={2} md={6} xs={12}>
            <MonthText strong>{month.format("MMMM")}</MonthText>
          </Col>
          <FlexBox md={1} xs={2}>
            <Button size="large" shape="circle" onClick={handleClickNextButton}>
              &gt;
            </Button>
          </FlexBox>
        </ButtonWrapper>
      </Col>
      <Col span={24}>
        <CalendarWrapper>
          <CalendarBox>
            <Calendar
              ref={calendarRef}
              onClickSchedule={handleClickSchedule}
              calendars={[
                {
                  id: "0",
                  name: "Private",
                  bgColor: "#4E9C81",
                  borderColor: "#4E9C81",
                  color: "#fff",
                },
              ]}
              scheduleView
              disableDblClick
              disableClick={false}
              isReadOnly
              month={{
                startDayOfWeek: 0,
              }}
              // 이벤트
              schedules={schedules}
              timezones={[
                {
                  timezoneOffset: 540,
                  displayLabel: "GMT+09:00",
                  tooltip: "Seoul",
                },
              ]}
              view="month"
            />
          </CalendarBox>
        </CalendarWrapper>
      </Col>
    </Row>
  );
}

const CalendarWrapper = styled.div`
  overflow-x: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const FlexBox = styled(Row)`
  display: flex;
  justify-content: center;
`;

const ButtonWrapper = styled(Row)`
  padding-bottom: 30px;
`;

const MonthText = styled(Typography.Text)`
  line-height: initial;
  font-size: 2rem;
  display: flex;
  justify-content: center;
`;

const CalendarBox = styled.div`
  min-width: 576px;
`;

export default ReservationCalendar;
