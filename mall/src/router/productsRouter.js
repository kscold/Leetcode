import { Suspense, lazy } from 'react';
import { Navigate } from 'react-router-dom';

const Loading = <div>Loading...</div>;
const ProductList = lazy(() => import('../pages/products/ListPage'));

const productsRouter = () => {
  return [
    {
      path: 'list',
      element: (
        <Suspense fallback={Loading}>
          <ProductList />
        </Suspense>
      ),
    },
    {
      path: '',
      // /products 기본 경로로 들어오면 /products/list로 리다이렉트
      element: <Navigate replace to={'/products/list'}></Navigate>,
    },
  ];
};

export default productsRouter;
