import React from 'react';
import { useParams } from 'react-router-dom';

const ReadPage = () => {
  const { tno } = useParams();
  console.log(tno);
  return <div className="test-3xl">Todo Read Page</div>;
};

export default ReadPage;
