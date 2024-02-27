import React from 'react';
import { RouterProvider } from 'react-router-dom';
import root from './router/root';

const App = () => {
  return <RouterProvider router={root} />;
};

export default App;
