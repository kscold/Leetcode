import axios from 'axios';
import { API_SERVER_HOST } from './todoApi';

const host = `${API_SERVER_HOST}/api/products`;

export const postAdd = async (product) => {
  const header = { headers: { 'Content-Type': 'multipart/form-data' } }; // axios 헤더 설정 파일 멀티파트로 설정

  const res = await axios.post(`${host}/`, product, header);

  return res.data;
};

export const getList = async (pageParam) => {
  const { page, size } = pageParam; // 들어온 객체를 비구조화 할당

  const res = await axios.get(`${host}/list`, {
    // get 요청의 쿼리스트링을 설정
    params: { page: page, size: size },
  });

  return res.data;
};

export const getOne = async (pno) => {
  const res = await axios.get(`${host}/${pno}`);

  return res.data;
};

export const deleteOne = async (pno) => {
  const res = await axios.delete(`${host}/${pno}`);

  return res.data;
};

export const putOne = async (pno, product) => {
  const header = { headers: { 'Content-Type': 'multipart/form-data' } }; // axios 헤더 설정 파일 멀티파트로 설정
  const res = await axios.put(`${host}/${pno}`, product, header);

  return res.data;
};
