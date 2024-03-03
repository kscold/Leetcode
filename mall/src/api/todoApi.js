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
