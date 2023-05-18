import axiosInstance from "../util/axiosInstance";

// 로그인 요청
export const loginRequest = async loginData => {
  try {
    const data = await axiosInstance.post(`/login`, loginData);
    return data;
  } catch (error) {
    if (error.response.status === 404) {
      return error.response.status;
    }
    return false;
  }
};

// 회원가입요청
export const registerRequest = async registerData => {
  const data = await axiosInstance.post(`/user/register`, registerData);
  if (data.jwtToken === "Empty") {
    return false;
  }
  return data;
};

// 이메일 중복 체크
export const duplicateCheckEmail = async value => {
  try {
    const data = await axiosInstance.get(`/user/validate/email?email=${value}`);
    return data;
  } catch (error) {
    if (error.response.status === 400) {
      return error.response.status;
    }
    return false;
  }
};

// 닉네임 중복 체크
export const duplicateCheckNickName = async value => {
  const data = await axiosInstance.get(
    `/user/validate/nickname?nickname=${value}`,
  );
  return data;
};

// 비밀번호 변경
// export const changePassword = async passwordData => {
//   const data = await axiosInstance.put(`/user/{id}/password`)
// }

// 센터 리스트 조회
export const getCenterList = async () => {
  const data = await axiosInstance.get(`/centers`);
  return data;
};

// 센터 상세 조회
export const getCenterInfo = async id => {
  const data = await axiosInstance.get(`/center/${id}`);
  return data;
};

// 센터 캘린더 조회
export const getCalendarData = async (id, date) => {
  const data = await axiosInstance.get(`/calendar?id=${id}&date=${date}`);
  return data;
};

// 리뷰 조회
export const getReivews = async id => {
  const data = await axiosInstance.get(`/reviews?centerId=${id}`);

  if (Object.keys(data).length === 0) {
    return false;
  }
  return data;
};

// 코트 조회
export const getCourts = async (id, date) => {
  const data = await axiosInstance.get(`/court?centerId=${id}&date=${date}`);
  return data;
};

// 리뷰 등록
export const AddReview = async reviewData => {
  try {
    const data = await axiosInstance.post(`/reivew`, reviewData);
    return data;
  } catch (error) {
    return error;
  }
};
