import React from 'react';
import {
  createSearchParams,
  useNavigate,
  useParams,
  useSearchParams,
} from 'react-router-dom';

const ReadPage = () => {
  const navigate = useNavigate(); // 원하는 동작이 들어왔을 때 라우팅을 하기 위하여 useNavigate를 사용
  const { tno } = useParams();

  const [queryParams] = useSearchParams();

  const page = queryParams.get('page') ? parseInt(queryParams.get('page')) : 1;
  const size = queryParams.get('size') ? parseInt(queryParams.get('size')) : 10;

  const queryStr = createSearchParams({ page, size }).toString(); // shotyhand property 사용 쿼리스트링을 객체형식으로 만들어줌 이후 문자열로 만들어서 저장

  console.log(tno);

  const moveToModify = (tno) => {
    navigate({ pathname: `/todo/modify/${tno}`, search: queryStr });
  };

  const moveToList = () => {
    navigate({ pathname: '/todo/list', search: queryStr });
  };

  return (
    <div className="test-3xl">
      Todo Read Page {tno}
      <div>
        <button onClick={() => moveToModify(tno)}>Test Modify</button>
        <button onClick={() => moveToList}>Test Modify</button>
      </div>
    </div>
  );
};

export default ReadPage;
