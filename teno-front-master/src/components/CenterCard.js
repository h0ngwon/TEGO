import { Link } from "react-router-dom";
import { Col, Card, Image, Typography } from "antd";
import styled from "styled-components";

function CenterCard({ center }) {
  return (
    <ListWrapper xl={6} lg={8} md={12} xs={24} key={center.id}>
      <Link to={`/centers/${center.id}`}>
        <CardWrapper hoverable cover={<ImageBox preview={false} />}>
          <Card.Meta
            title={
              <Typography.Title
                level={5}
              >{`${center.name} (${center.district})`}</Typography.Title>
            }
          />
          <Card.Meta title={`주소 : ${center.address}`} />
          <Card.Meta
            title={`이용시간 : ${center.centerStartTime} ~ ${center.centerEndTime}`}
          />
        </CardWrapper>
      </Link>
    </ListWrapper>
  );
}

const ListWrapper = styled(Col)`
  padding: 32px;

  @media only screen and (max-width: 575px) {
    padding: 32px 0;
  }
`;

const ImageBox = styled(Image)`
  height: 180px;
  background-color: #aaa;
  border: none;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
`;

const CardWrapper = styled(Card)`
  border-radius: 15px;
  border-color: #ddd;
`;

export default CenterCard;
