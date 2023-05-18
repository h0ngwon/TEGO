import { useEffect, useState } from "react";
import { Row, Col, PageHeader, Typography } from "antd";
import { Link, useParams, useLocation } from "react-router-dom";
import { useQuery } from "react-query";
import styled from "styled-components";
import queryString from "query-string";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { ko } from "date-fns/esm/locale";
import { CalendarOutlined } from "@ant-design/icons";
import { getCourts, getCenterInfo } from "../api";
import TimeTable from "../components/TimeTable";

function TimeTablePage() {
  const { centerId } = useParams();
  const courtDate = queryString.parse(useLocation().search).date;
  const [startDate, setStartDate] = useState(new Date(courtDate));
  const [dateUrl, setDateUrl] = useState(courtDate);

  const center = useQuery(["centerInfo", centerId], () =>
    getCenterInfo(centerId),
  );

  const timetable = useQuery(["courts", centerId, dateUrl], () =>
    getCourts(centerId, dateUrl),
  );

  useEffect(() => {
    if (!startDate) {
      return;
    }
    const getYear = startDate.getFullYear();
    const getMonth =
      startDate.getMonth() + 1 >= 10
        ? startDate.getMonth() + 1
        : `0${startDate.getMonth() + 1}`;

    const getDay =
      startDate.getDate() >= 10
        ? startDate.getDate()
        : `0${startDate.getDate()}`;
    const getDate = `${getYear}-${getMonth}-${getDay}`;
    setDateUrl(getDate);
  }, [startDate]);

  return (
    <Row align="middle">
      <Col span={24}>
        <Link to={`/centers/${centerId}`}>
          <PageTitle
            onBack={() => null}
            title={<TitleText level={2}>{center.data?.name}</TitleText>}
          />
        </Link>
      </Col>
      <Wrapper span={24}>
        <DatePickerWrapper>
          <CalendarOutlined size="large" />
          <TimetableDatepicker
            dateFormat="yyyy년 MM월 dd일"
            selected={startDate}
            onChange={pickDate => setStartDate(pickDate)}
            minDate={new Date()}
            disabledKeyboardNavigation
            locale={ko}
          />
        </DatePickerWrapper>
      </Wrapper>
      <Wrapper span={24}>
        <TimeTableWrapper>
          <TimeTable
            courtsData={timetable.data}
            loading={timetable.isLoading}
          />
        </TimeTableWrapper>
      </Wrapper>
    </Row>
  );
}

const DatePickerWrapper = styled.div`
  display: flex;
  float: right;
  justify-content: flex-end;
  align-items: center;
  border-radius: 5px;
  box-shadow: 0 0.3px 3px rgba(0, 0, 0, 0.3);
  padding: 0 10px;

  @media only screen and (max-width: 575px) {
    float: left;
  }

  .react-datepicker-wrapper {
    display: flex;
    align-items: center;
  }
`;

const Wrapper = styled(Col)`
  padding: 15px 0;
`;

const PageTitle = styled(PageHeader)`
  padding-left: 0;
  padding-top: 0;
`;

const TitleText = styled(Typography.Text)`
  line-height: initial;
  font-size: 30px;
`;

const TimeTableWrapper = styled.div`
  overflow-x: scroll;
  border-radius: 20px;
  box-shadow: 0 0.5px 5px rgba(0, 0, 0, 0.3);
  &::-webkit-scrollbar {
    display: none;
  }
  @media only screen and (max-width: 575px) {
    box-shadow: none;
    padding: 0;
    border-radius: 0;
  }
`;

const TimetableDatepicker = styled(DatePicker)`
  width: 140px;
  border: none;
  text-align: center;
  height: 37px;
  transition: 0.3;

  &:focus {
    outline: none;
  }
`;

export default TimeTablePage;
