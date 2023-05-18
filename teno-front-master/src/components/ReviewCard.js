import styled from "styled-components";
import { Comment, Rate, Avatar, Typography, Divider, Button } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { useState } from "react";
import { tokenStore } from "../util/token";
import ReviewInputForm from "./ReviewInputForm";

function ReviewCard({ review }) {
  const [openInput, setOpenInput] = useState(false);
  const userId = tokenStore.getToken.responseUser.id;
  const action = [
    <Button onClick={() => setOpenInput(!openInput)}>수정</Button>,
    <Button type="primary" danger>
      삭제
    </Button>,
  ];

  return (
    <>
      <ReviewComment
        actions={review.id === userId ? action : ""}
        author={
          <div>
            <CenterNameText> {review.centerName}</CenterNameText>
            <Rate disabled value={review.grade} />
          </div>
        }
        avatar={<AvatarBox icon={<UserOutlined />} />}
        content={<ContentText>{review.content}</ContentText>}
        datetime={review.updateTime}
      />
      {openInput && (
        <ReviewInputForm
          centerName={review.centerName}
          centerRate={review.grade}
          reviewContent={review.content}
        />
      )}
      <Divider />
    </>
  );
}
const ReviewComment = styled(Comment)`
  padding: 16px;

  .ant-comment-content-detail {
    padding: 15px 0;
  }
`;

const CenterNameText = styled(Typography.Text)`
  font-size: 1rem;
  padding-right: 10px;
`;

const ContentText = styled(Typography.Text)`
  font-size: 1rem;
`;

const AvatarBox = styled(Avatar)`
  background-color: #4e9c81;
`;

export default ReviewCard;
