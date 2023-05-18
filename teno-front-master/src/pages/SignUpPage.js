import { useEffect, useState } from "react";
import { Row, Col, Typography, Form, Input, Button } from "antd";
import styled from "styled-components";
import { useMutation } from "react-query";
import { useForm, Controller } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";
import {
  duplicateCheckNickName,
  duplicateCheckEmail,
  registerRequest,
} from "../api/index";
import { tokenStore } from "../util/token";

function SignUpPage() {
  const [checkedEmail, setCheckedEmail] = useState(false);
  const [checkedNickName, setCheckedNickName] = useState(false);

  const [form] = Form.useForm();

  const setToken = tokenStore.set;

  const schema = yup
    .object({
      userPassword: yup.string().required("비밀번호를 입력해주세요"),
      userPasswordConfirm: yup
        .string()
        .oneOf([yup.ref("userPassword"), null], "비밀번호가 일치하지 않습니다")
        .required("비밀번호를 확인해주세요"),
      nickName: yup.string().required("닉네임을 입력해주세요"),
      email: yup
        .string()
        .email("이메일 형식으로 입력해주세요")
        .required("이메일을 입력해주세요"),
    })
    .required();

  const {
    register,
    handleSubmit,
    control,
    getValues,
    formState: { errors },
  } = useForm({ resolver: yupResolver(schema) });

  const checkNickName = useMutation(duplicateCheckNickName);
  const checkEmail = useMutation(duplicateCheckEmail);
  const signUpRequest = useMutation(registerRequest);

  /* eslint-disable-next-line */
  const regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

  // 이메일 체크
  const emailCheck = () => {
    if (errors.email?.message) {
      return form.setFields([{ name: "user-email", errors: "" }]);
    }

    if (getValues("email") && regEmail.test(getValues("email"))) {
      return checkEmail.mutate(getValues("email"));
    }

    if (getValues("email") && !regEmail.test(getValues("email"))) {
      return form.setFields([
        { name: "user-email", errors: ["이메일 형식을 확인해주세요"] },
      ]);
    }

    if (errors.email?.message) {
      return form.setFields([{ name: "user-email", errors: "" }]);
    }

    return form.setFields([
      { name: "user-email", errors: ["이메일을 확인해주세요"] },
    ]);
  };

  useEffect(() => {
    if (checkEmail.data) {
      setCheckedEmail(true);
      form.setFields([{ name: "user-email", errors: "" }]);
    }

    if (checkEmail.data === 400) {
      setCheckedEmail(false);
      form.setFields([{ name: "user-email", errors: ["중복된 이메일입니다"] }]);
    }
  }, [checkEmail]);

  // 닉네임 체크
  const nickNameCheck = () => {
    if (errors.nickName?.message) {
      return form.setFields([{ name: "nickName", errors: "" }]);
    }

    if (!getValues("nickName")) {
      return form.setFields([
        { name: "user-nickName", errors: ["닉네임을 확인해주세요"] },
      ]);
    }

    return checkNickName.mutate(getValues("nickName"));
  };

  useEffect(() => {
    if (checkNickName.data === undefined) {
      return;
    }

    if (!checkNickName.data) {
      setCheckedNickName(false);
      form.setFields([
        { name: "user-nickName", errors: ["닉네임을 확인해주세요"] },
      ]);
    }

    if (checkNickName.data) {
      setCheckedNickName(true);
      form.setFields([{ name: "user-nickName", errors: "" }]);
    }

    if (checkNickName.data === "Already Exist") {
      setCheckedNickName(false);
      form.setFields([
        { name: "user-nickName", errors: ["중복된 닉네임입니다"] },
      ]);
    }
  }, [checkNickName]);

  // 회원가입
  const registerSubmit = () => {
    if (!checkedEmail) {
      setCheckedEmail(false);
      return form.setFields([
        { name: "user-email", errors: ["이메일을 확인해주세요"] },
      ]);
    }
    if (!checkedNickName) {
      return form.setFields([
        { name: "user-nickName", errors: ["닉네임을 확인해주세요"] },
      ]);
    }

    return signUpRequest.mutate({
      email: getValues("email"),
      nickName: getValues("nickName"),
      userPassword: getValues("userPassword"),
    });
  };

  useEffect(() => {
    if (!signUpRequest.data) {
      return;
    }
    setToken(JSON.stringify(signUpRequest.data));
    window.location.reload();
  }, [signUpRequest]);

  return (
    <Row justify="center">
      <SignUpWrapper>
        <Typography.Title level={3}>회원가입</Typography.Title>
        <Form
          labelCol={{
            span: 24,
          }}
          wrapperCol={{
            span: 24,
          }}
          form={form}
          onFinish={handleSubmit(registerSubmit)}
        >
          <Form.Item label="이메일" name="user-email">
            <Controller
              name="email"
              control={control}
              render={({ field }) => (
                <Input
                  {...register("email")}
                  {...field}
                  placeholder="이메일"
                  status={errors.email ? "error" : ""}
                  type="email"
                  onBlur={emailCheck}
                />
              )}
            />
            {errors.email?.message && (
              <ErrorMessage>{errors.email?.message}</ErrorMessage>
            )}
          </Form.Item>
          <Form.Item label="비밀번호">
            <Controller
              name="userPassword"
              control={control}
              render={({ field }) => (
                <Input.Password
                  {...register("userPassword")}
                  placeholder="비밀번호"
                  status={errors.userPassword ? "error" : ""}
                  visibilityToggle={false}
                  {...field}
                />
              )}
            />
            {errors.userPassword?.message && (
              <ErrorMessage>{errors.userPassword?.message}</ErrorMessage>
            )}
          </Form.Item>
          <Form.Item label="비밀번호 확인">
            <Controller
              name="userPasswordConfirm"
              control={control}
              render={({ field }) => (
                <Input.Password
                  {...register("userPasswordConfirm")}
                  placeholder="비밀번호 확인"
                  status={errors.userPasswordConfirm ? "error" : ""}
                  visibilityToggle={false}
                  {...field}
                />
              )}
            />
            {errors.userPasswordConfirm?.message && (
              <ErrorMessage>{errors.userPasswordConfirm?.message}</ErrorMessage>
            )}
          </Form.Item>
          <Form.Item label="닉네임" name="user-nickName">
            <Controller
              name="nickName"
              control={control}
              render={({ field }) => (
                <Input
                  {...register("nickName")}
                  {...field}
                  placeholder="닉네임"
                  status={errors.nickName ? "error" : ""}
                  onBlur={nickNameCheck}
                />
              )}
            />
            {errors.nickName?.message && (
              <ErrorMessage>{errors.nickName?.message}</ErrorMessage>
            )}
          </Form.Item>

          <Form.Item>
            <SignUpButton
              type="primary"
              htmlType="submit"
              loading={signUpRequest.isLoading}
            >
              가입하기
            </SignUpButton>
          </Form.Item>
        </Form>
      </SignUpWrapper>
    </Row>
  );
}

const SignUpWrapper = styled(Col)`
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

const SignUpButton = styled(Button)`
  background-color: #4e9c81;
  border: none;
  width: 100%;
  height: 40px;
  margin-top: 20px;

  &:hover {
    background-color: rgba(78, 156, 129, 0.7);
  }
  &:focus {
    background-color: #4e9c81;
  }
`;

const ErrorMessage = styled.p`
  color: red;
`;

export default SignUpPage;
