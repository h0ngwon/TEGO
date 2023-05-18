import { Link } from "react-router-dom";
import { useMutation } from "react-query";
import { useForm, Controller } from "react-hook-form";
import { Row, Col, Typography, Form, Input, Button } from "antd";
import styled from "styled-components";
import { useEffect } from "react";
import { loginRequest } from "../api/index";
import { tokenStore } from "../util/token";

function LoginPage() {
  const setToken = tokenStore.set;

  const { mutate, isLoading, data } = useMutation(loginRequest);

  const {
    register,
    handleSubmit,
    control,
    getValues,
    formState: { errors },
  } = useForm();

  const [form] = Form.useForm();

  const loginSubmit = () => {
    mutate({
      email: getValues("email"),
      password: getValues("userPw"),
    });
  };

  useEffect(() => {
    if (!data) {
      return;
    }
    if (data === 404) {
      return;
    }
    setToken(JSON.stringify(data));
    window.location.reload();
  }, [data]);

  return (
    <Row justify="center">
      <LoginWrapper>
        <Typography.Title level={3}>로그인</Typography.Title>
        <Form
          labelCol={{
            span: 24,
          }}
          wrapperCol={{
            span: 24,
          }}
          form={form}
          onFinish={handleSubmit(loginSubmit)}
        >
          <FormItem label="이메일">
            <Controller
              name="email"
              control={control}
              defaultValue=""
              render={({ field }) => (
                <Input
                  {...register("email", { required: true })}
                  {...field}
                  type="email"
                  placeholder="이메일"
                  status={errors.email ? "error" : ""}
                />
              )}
            />
            {errors.email?.type === "required" && (
              <ErrorMessage>이메일을 입력해주세요</ErrorMessage>
            )}
          </FormItem>
          <FormItem label="비밀번호">
            <Controller
              name="userPw"
              control={control}
              render={({ field }) => (
                <Input.Password
                  {...register("userPw", { required: true })}
                  placeholder="비밀번호"
                  status={errors.userPw ? "error" : ""}
                  visibilityToggle={false}
                  {...field}
                />
              )}
            />
            {errors.userPw?.type === "required" && (
              <ErrorMessage>비밀번호를 입력해주세요</ErrorMessage>
            )}
          </FormItem>
          <FormButtonItem>
            <SignUpLinkButton type="link">
              <Link to="/signup">회원가입</Link>
            </SignUpLinkButton>
          </FormButtonItem>
          <Form.Item>
            <LoginButton type="primary" htmlType="submit" loading={isLoading}>
              로그인
            </LoginButton>
          </Form.Item>
          {data === 404 && (
            <LoginError>로그인 정보를 다시 확인해주세요</LoginError>
          )}
        </Form>
      </LoginWrapper>
    </Row>
  );
}

const LoginWrapper = styled.div`
  margin-top: 50px;
  border-radius: 20px;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.3);
  padding: 50px;
  text-align: center;

  @media only screen and (max-width: 575px) {
    box-shadow: none;
    padding: 0;
  }
`;

const LoginButton = styled(Button)`
  background-color: #4e9c81;
  border: none;
  width: 100%;
  height: 40px;

  &:hover {
    background-color: rgba(78, 156, 129, 0.7);
  }
  &:focus {
    background-color: #4e9c81;
  }
`;

const SignUpLinkButton = styled(Button)`
  background-color: #fff;
  border: 1px solid #d9d9d9;
  width: 100%;
  height: 40px;
  color: #000;

  &:hover {
    border: 1px solid #d9d9d9;
  }
  &:focus {
    border: 1px solid #d9d9d9;
  }
`;

const FormItem = styled(Form.Item)``;

const FormButtonItem = styled(Form.Item)`
  padding-top: 10px;
`;

const ErrorMessage = styled.div`
  color: red;
`;

const LoginError = styled.div`
  text-align: center;
  color: red;
`;

export default LoginPage;
