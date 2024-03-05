import React, { useState } from 'react';
import ResultModal from '../common/ResultModal';
import { postAdd } from '../../api/todoApi';
import useCustomMove from '../../hooks/useCustomMove';

const initState = {
  title: '',
  writer: '',
  dueDate: '',
};

const AddComponent = () => {
  const [todo, setTodo] = useState({ ...initState });

  const [result, setResult] = useState(null);

  const { moveToList } = useCustomMove();

  const handleChangeTodo = (e) => {
    console.log(e.target.name, e.target.value);

    // todo[title]
    todo[e.target.name] = e.target.value; // 객체 속성의 state를 유동적으로 바꿔서 데이터를 받기 위해 e.target.name을 사용

    setTodo({ ...todo });
  };

  const handleClickAdd = () => {
    // console.log(todo);
    // CREATE api
    postAdd(todo).then((result) => {
      // {TNO: 104}
      setResult(result.TNO); // TNO 값을 결과 state에 저장
      setTodo({ ...initState }); // TNO 값을 저장했으므로 초기화
    });
  };

  const closeModal = () => {
    // closeModal을 호출하면 TNO 저장 값을 초기화
    setResult(null);
    moveToList();
  };

  return (
    <div className="border-2 border-sky-200 mt-10 m-2 p-4">
      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">TITLE</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="title"
            type="text"
            value={todo.title}
            onChange={handleChangeTodo}
          ></input>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">TITLE</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="writer"
            type="text"
            value={todo.writer}
            onChange={handleChangeTodo}
          ></input>
        </div>
      </div>

      <div className="flex justify-center">
        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
          <div className="w-1/5 p-6 text-right font-bold">TITLE</div>
          <input
            className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
            name="dueDate"
            type="date"
            value={todo.dueDate}
            onChange={handleChangeTodo}
          ></input>
        </div>
      </div>
      <div className="flex justify-end">
        <div className="relative mb-4 flex p-4 flex-wrap items-stretch">
          <button
            type="button"
            className="rounded p-4 w-36 bg-blue-500 text-xl text-white"
            onClick={handleClickAdd}
          >
            ADD
          </button>
        </div>
      </div>

      {result ? ( // result 값이 있으면 모달이 뜨도록 없으면 모달이 뜨지 않도록 설정
        <ResultModal
          title="Add Result"
          content={`New ${result} Added`}
          callbackFn={closeModal}
        />
      ) : (
        <></>
      )}
    </div>
  );
};

export default AddComponent;
