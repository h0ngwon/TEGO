import styled from "styled-components";
import { Button, Form, Input, Typography, Row, Divider, Col } from "antd";

function ModifyProfile() {
  return (
    <ModifyWrapper justify="center">
      <Col>
        <Form
          labelCol={{
            span: 24,
          }}
          wrapperCol={{
            span: 24,
          }}
        >
          <Form.Item
            label={<LabelText>닉네임</LabelText>}
            rules={[
              { required: true, message: "변경할 닉네임을 입력해주세요" },
            ]}
          >
            <Input placeholder="변경할 닉네임을 입력해주세요" />
          </Form.Item>
          <Form.Item>
            <ModifyButton type="primary" htmlType="submit">
              변경하기
            </ModifyButton>
          </Form.Item>
        </Form>
        <Divider />
        <Form
          labelCol={{
            span: 24,
          }}
          wrapperCol={{
            span: 24,
          }}
        >
          <Form.Item
            label={<LabelText>비밀번호</LabelText>}
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Input.Password
              placeholder="현재 비밀번호"
              visibilityToggle={false}
            />
          </Form.Item>
          <Form.Item
            label={<LabelText>변경할 비밀번호</LabelText>}
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Input.Password
              placeholder="변경할 비밀번호"
              visibilityToggle={false}
            />
          </Form.Item>
          <Form.Item
            label={<LabelText>변경할 비밀번호 확인</LabelText>}
            rules={[{ required: true, message: "Please input your username!" }]}
          >
            <Input.Password
              placeholder="변경할 비밀번호 확인"
              visibilityToggle={false}
            />
          </Form.Item>
          <Form.Item>
            <ModifyButton type="primary" htmlType="submit">
              변경하기
            </ModifyButton>
          </Form.Item>
        </Form>
      </Col>
    </ModifyWrapper>
  );
}

const ModifyWrapper = styled(Row)`
  padding: 30px 0;
`;

const LabelText = styled(Typography.Text)`
  font-size: 1rem;
`;

const ModifyButton = styled(Button)`
  width: 100%;
  background-color: #4e9c81;
  &:hover {
    background-color: rgba(78, 156, 129, 0.7);
  }
  &:focus {
    background-color: #4e9c81;
  }
`;

export default ModifyProfile;
