import { createBrowserRouter } from 'react-router-dom';
import { Suspense, lazy } from 'react';

const Loading = <div className="bg-red-700">Loading...</div>;
const Main = lazy(() => import('../pages/MainPage'));
const About = lazy(() => import('../pages/AboutPage'));

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
      <Suspense fallback={About}>
        <About />
      </Suspense>
    ),
  },
]);

export default root;
