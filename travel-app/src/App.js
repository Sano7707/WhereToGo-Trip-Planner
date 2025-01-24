import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Home from './components/pages/Home';
import Services from './components/pages/Services';
import Products from './components/pages/Products';
import Registration from './components/pages/Registration';
import LoginPage from './components/pages/LoginPage';
import axios from 'axios'

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem('jwtToken') !== null);
  const [userName, setUserName] = useState(localStorage.getItem('username'));



  const isTokenExpired = (token) => {
    try {
      const decodedToken = JSON.parse(atob(token.split('.')[1]));
      return decodedToken.exp * 1000 < Date.now();
    } catch (error) {
      return true;
    }
  };

  useEffect(() => {
    const jwtToken = localStorage.getItem('jwtToken');
    const storedUserName = localStorage.getItem('username');

    if (jwtToken && !isTokenExpired(jwtToken)) {
      setIsLoggedIn(true);
      setUserName(storedUserName);
    } else {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('username');
      setIsLoggedIn(false);
      setUserName('');
    }
  }, []);

  return (
    <Router>
      <Navbar isLoggedIn={isLoggedIn} userName={userName} />
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/services' element={<Services />} />
        <Route path='/products' element={<Products />} />
        <Route path='/register' element={<Registration />} />
        <Route
          path='/login'
          element={<LoginPage onLogin={() => setIsLoggedIn(true)} />}
        />
      </Routes>
    </Router>
  );
}

export default App;
