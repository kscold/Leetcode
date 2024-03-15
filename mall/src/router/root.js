import { createBrowserRouter } from 'react-router-dom';
import { Suspense, lazy } from 'react';
import todoRouter from './todoRouter';
import productsRouter from './productsRouter';

const Loading = <div className="bg-red-700">Loading...</div>;
const Main = lazy(() => import('../pages/MainPage'));
const About = lazy(() => import('../pages/AboutPage'));

const TodoIndex = lazy(() => import('../pages/todo/IndexPage'));

const ProductsIndex = lazy(() => import('../pages/products/IndexPage'));

const root = createBrowserRouter([
  // ReactRouter설정으로 객체형식으로 패스를 설정
  {
    path: '', // 경로 설정
    element: (
      <Suspense fallback={Loading}>
        <Main />
      </Suspense>
    ),
  },
  {
    path: 'about',
    element: (
      <Suspense fallback={Loading}>
        <About />
      </Suspense>
    ),
  },
  {
    path: 'todo',
    element: (
      <Suspense fallback={Loading}>
        <TodoIndex />
      </Suspense>
    ),
    children: todoRouter(), // todo에 관련된 설정이 복잡해짐에 따라 배열을 반환하는 함수로 만들어 빼줌
  },
  {
    path: 'products',
    element: (
      <Suspense fallback={Loading}>
        <ProductsIndex />
      </Suspense>
    ),
    children: productsRouter(),
  },
]);

export default root;
