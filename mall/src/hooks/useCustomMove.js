import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from 'react-router-dom';

const getNum = (param, defaultValue) => {
  if (!param) {
    // param가 없으면 defaultValue 반환
    return defaultValue;
  }
  return parseInt(param); // param가 있으면 Int로 설정
};

const useCustomMove = () => {
  const navigate = useNavigate();

  const [queryParams] = useSearchParams(); // 쿼리스트링

  const page = getNum(queryParams.get('page'), 1); // 쿼리스트링에서 page 데이터를 뽑음
  const size = getNum(queryParams.get('size'), 10); // 쿼리스트링에서 size 데이터를 뽑음

  // page=3&size=10
  const queryDefault = createSearchParams({ page, size }).toString(); // page=3&size=10 형태 문자열로 추출

  const moveToList = (pageParam) => {
    let queryStr = '';

    if (pageParam) {
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);

      queryStr = createSearchParams({
        page: pageNum,
        size: sizeNum,
      }).toString(); // 쿼리스트링 형식 생성
    } else {
      queryStr = queryDefault;
    }

    navigate({ pathname: '../list', search: queryStr }); // url를 /todo/read에서 /todo/list로 바꾸고, 뒤에 ?page=3&size=10를 추가
  };

  return { moveToList }; // 객체 스타일로 반환
};

export default useCustomMove;
