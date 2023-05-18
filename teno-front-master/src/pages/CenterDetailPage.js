import { useParams, Link } from "react-router-dom";
import styled from "styled-components";
import { useQuery } from "react-query";
import {
  Row,
  Col,
  Divider,
  Space,
  Form,
  Input,
  Button,
  Typography,
  Avatar,
  Rate,
  PageHeader,
  Spin,
  Empty,
  Comment,
} from "antd";
import { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded } from "react-naver-maps";
import { getCenterInfo, getReivews } from "../api";
import ReservationCalendar from "../components/ReservationCalendar";
import CenterMap from "../components/CenterMap";
import ReviewCard from "../components/ReviewCard";
import ReviewInputForm from "../components/ReviewInputForm";

function CenterDetailPage() {
  const { id } = useParams();

  const [reviews, setReviews] = useState();

  const centerData = useQuery(["centerInfo", id], () => getCenterInfo(id));
  const reviewData = useQuery(["reivews", id], () => getReivews(id));

  useEffect(() => {
    if (!reviewData) {
      setReviews(false);
    }
    setReviews(reviewData.data);
  }, [reviewData]);

  return (
    <Row align="middle">
      {centerData.data && (
        <>
          <Col span={24}>
            <Link to="/">
              <PageTitle
                onBack={() => null}
                title={<TitleText>{centerData.data.name}</TitleText>}
              />
            </Link>
          </Col>

          <InfoDataBox xl={12} lg={12} md={24} xs={24}>
            <ImageBox />
          </InfoDataBox>

          <InfoDataBox xl={12} lg={12} md={24} xs={24}>
            <CenterDataBox direction="vertical" size="middle">
              <div>
                <CenterTitle>
                  {centerData.data.name}({centerData.data.district})
                </CenterTitle>
                <DefaultRate disabled value={centerData.data.averageGrade} />
                <InfoTitle>({centerData.data.reviewCount})</InfoTitle>
              </div>
              <InfoTitle>이용 시간 </InfoTitle>
              <InfoText>
                {centerData.data.centerStartTime} ~{" "}
                {centerData.data.centerEndTime}
              </InfoText>
              <InfoTitle>주소 </InfoTitle>
              <InfoText>{centerData.data.address}</InfoText>
              <CenterBox>
                <RenderAfterNavermapsLoaded
                  ncpClientId={process.env.REACT_APP_NAVER_MAP}
                  error={<p>Maps Load Error</p>}
                  loading={<p>Maps Loading...</p>}
                  submodules={["geocoder"]}
                >
                  <CenterMap centerAddress={centerData.data.address} />
                </RenderAfterNavermapsLoaded>
              </CenterBox>
            </CenterDataBox>
          </InfoDataBox>

          <Divider />
          <Col span={24}>
            <ReservationCalendar centerId={id} />
            <Divider />
            <ReviewTitle level={3}>리뷰</ReviewTitle>
            <ReviewInputForm centerId={id} centerName={centerData?.data.name} />
            <Divider />
            <ReviewWrapper>
              {reviewData.data &&
                reviewData.data.map(data => (
                  <ReviewCard key={data.updateTime} review={data} />
                ))}
              {reviewData.isLoading && (
                <LoadingContainer span={24}>
                  <Spin size="large" />
                </LoadingContainer>
              )}
            </ReviewWrapper>
          </Col>
        </>
      )}
      {centerData.isLoading && (
        <LoadingContainer span={24}>
          <Spin size="large" />
        </LoadingContainer>
      )}

      {!centerData.data && !centerData.isLoading && (
        <LoadingContainer span={24}>
          <Empty
            description={
              <Typography.Title level={4}>결과가 없습니다</Typography.Title>
            }
          />
        </LoadingContainer>
      )}
    </Row>
  );
}

const CenterTitle = styled(Typography.Text)`
  font-size: 24px;
  font-weight: bold;
`;

const LoadingContainer = styled(Col)`
  padding-top: 200px;
  display: flex;
  justify-content: center;
`;

const PageTitle = styled(PageHeader)`
  padding-left: 0;
  padding-top: 0;
`;

const ImageBox = styled.div`
  width: 100%;
  height: 380px;
  background-color: #aaa;

  @media only screen and (max-width: 991px) {
    height: 350px;
  }

  @media only screen and (max-width: 575px) {
    height: 200px;
  }
`;

const TitleText = styled(Typography.Text)`
  line-height: initial;
  font-size: 30px;
`;

const ReviewTitle = styled(Typography.Title)`
  line-height: initial;
  padding: 20px 0;
`;

const CenterDataBox = styled(Space)`
  display: flex;
  border-radius: 15px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.3);
  padding: 30px;
  margin-top: 20px;

  @media only screen and (max-width: 575px) {
    box-shadow: none;
    padding: 30px 0;
  }
`;

const InfoDataBox = styled(Col)`
  padding: 20px;
  @media only screen and (max-width: 575px) {
    padding: 20px 0;
  }
`;

const InfoTitle = styled(Typography.Text)`
  font-size: 1rem;
  font-weight: bold;
  padding-right: 5px;
`;

const InfoText = styled(Typography.Text)`
  font-size: 1rem;
  height: 32px;
`;

const DefaultRate = styled(Rate)`
  padding: 0 10px;
  height: 25.14px;
  line-height: initial;
`;

const CenterBox = styled.div`
  display: flex;
  justify-content: center;
`;

const ReviewWrapper = styled.div`
  padding: 25px 0;
`;

export default CenterDetailPage;
