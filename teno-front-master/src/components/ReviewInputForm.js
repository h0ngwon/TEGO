import styled from "styled-components";
import { useMutation } from "react-query";
import { Form, Button, Input, Avatar, Rate, Comment, Typography } from "antd";
import { UserOutlined } from "@ant-design/icons";
import { useEffect } from "react";
import { AddReview } from "../api/index";

function ReviewInputForm({ centerName, centerRate, reviewContent }) {
  // const reviewAdd = useMutation(AddReview);

  // const reviewSubmit = () => {
  //   reviewAdd.mutate({
  //     centerId: 1,
  //     content: "좋아요",
  //     grade: 5,
  //     userId: "1",
  //   });
  // };

  // useEffect(() => {
  //   console.log(reviewAdd.data);
  // }, [reviewAdd.data]);

  return (
    <ReviewComment
      actions={[<WriteButton type="primary">등록</WriteButton>]}
      author={
        <div>
          <CenterNameText>{centerName}</CenterNameText>
          <Rate defaultValue={centerRate} />
        </div>
      }
      avatar={<ReviewAvatar icon={<UserOutlined />} />}
      content={
        <ReviewTextArea
          placeholder="리뷰를 작성해주세요. (300자 제한)"
          maxLength={300}
          rows={3}
          defaultValue={reviewContent}
        />
      }
    />
  );
}

const ReviewComment = styled(Comment)`
  padding: 16px;
  border-radius: 10px;

  .ant-comment-content-detail {
    padding-top: 20px;
  }
`;

const CenterNameText = styled(Typography.Text)`
  font-size: 1rem;
  padding-right: 10px;
`;

const ReviewAvatar = styled(Avatar)`
  background-color: #4e9c81;
`;

const ReviewTextArea = styled(Input.TextArea)`
  padding: 15px;
`;

const WriteButton = styled(Button)`
  background-color: #4e9c81;
  border: none;
  float: right;

  &:hover {
    background-color: rgba(78, 156, 129, 0.7);
  }

  &:focus {
    background-color: #4e9c81;
  }

  @media only screen and (max-width: 575px) {
    height: 40px;
  }
`;

export default ReviewInputForm;
