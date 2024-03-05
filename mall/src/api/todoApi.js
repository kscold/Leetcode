import axios from 'axios';

export const API_SERVER_HOST = 'http://localhost:8080';

const prefix = `${API_SERVER_HOST}/api/todo`;

export const getOne = async (tno) => {
  // 하나의 데이터를 조회
  const res = await axios.get(`${prefix}/${tno}`);

  return res.data;
};

export const getList = async (pageParams) => {
  const { page, size } = pageParams; // 비구조화 할당

  const res = await axios.get(`${prefix}/list`, { params: { page, size } });

  return res.data;
};

export const postAdd = async (todoObj) => {
  // 만약 axios라이브러리를 이용하지 않고 fetch함수를 이용했다면 JSON.stringify(obj)를 사용하여 문자열을 JSON 형식으로 변환했어야 했음
  const res = await axios.post(`${prefix}/`, todoObj);

  return res.data;
};
