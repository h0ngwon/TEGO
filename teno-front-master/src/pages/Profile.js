import styled from "styled-components";
import { Row, Col, Typography, Avatar, Tabs } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { tokenStore } from "../util/token";
import ModifyProfile from "../components/ModifyProfile";
import ReviewCard from "../components/ReviewCard";

const review = [
  {
    centerName: "마루공원",
    content: "이 코트 너무너무 좋아요",
    grade: 3,
    id: 3,
    updateTime: "2022-05-21 15:30:21",
    uploadTime: "2022-05-20 14:25:30",
  },
  {
    centerName: "마루공원",
    content: "이 코트 너무너무 좋아요",
    grade: 3,
    id: 1,
    updateTime: "2022-05-21 15:30:21",
    uploadTime: "2022-05-20 14:25:30",
  },
];

function Profile() {
  const getUser = tokenStore.getToken.responseUser;
  const userNickname = getUser.nickname;

  return (
    <Row justify="center">
      <Col span={24}>
        <Typography.Title level={2}>마이페이지</Typography.Title>
      </Col>
      <AvatarBox span={24}>
        <ProfileAvatar size={64} icon={userNickname[0]} />
        <TitleWrapper>
          <NickNameText level={3}>{getUser.nickname}님</NickNameText>
          <EmailText>{getUser.email}</EmailText>
        </TitleWrapper>
      </AvatarBox>
      <TabBox md={18}>
        <Tabs defaultActiveKey="1" centered>
          <Tabs.TabPane tab={<TypoText>프로필 수정</TypoText>} key="1">
            <ModifyProfile />
          </Tabs.TabPane>
          <Tabs.TabPane tab={<TypoText>내가 쓴 리뷰</TypoText>} key="2">
            <ReviewWrapper justify="center">
              <Col>
                {review.map(data => (
                  <ReviewCard review={data} />
                ))}
              </Col>
            </ReviewWrapper>
          </Tabs.TabPane>
        </Tabs>
      </TabBox>
    </Row>
  );
}

const TabBox = styled(Col)`
  padding: 30px 0;
`;

const AvatarBox = styled(Col)`
  padding: 30px 0;
  display: flex;
  justify-content: center;
`;

const ProfileAvatar = styled(Avatar)`
  background-color: #4e9c81;
`;

const TitleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 15px;

  .ant-typography {
    margin: 0;
  }
`;

const NickNameText = styled(Typography.Text)`
  margin-bottom: 10px;
`;

const ReviewWrapper = styled(Row)`
  padding: 30px 0;
`;

const EmailText = styled(Typography.Text)`
  color: #aaa;
`;

const TypoText = styled(Typography.Text)`
  font-size: 1rem;
  padding: 0 10px;
`;

export default Profile;
