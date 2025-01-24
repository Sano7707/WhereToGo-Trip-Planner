import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';
import { useNavigate } from 'react-router-dom';


function Navbar({ isLoggedIn, userName }) {
  const [click, setClick] = useState(false);
  const navigate = useNavigate();

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

  const handleLogout = () => {
    const confirmLogout = window.confirm("Are you sure you want to log out?");
    if (confirmLogout) {
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('username');
      localStorage.removeItem('travelPlan');
      localStorage.removeItem('travelRecommendations');

      navigate('/');
      window.location.reload();
    }
  };

  return (
    <>
      <nav className='navbar'>
        <div className='navbar-container'>
          <Link to='/' className='navbar-logo' onClick={closeMobileMenu}>
            <img src="/images/logo1.png" alt="pic" style={{ maxWidth: '80px', maxHeight: '40px' }} />
            <h1 style={{color:"white"}}> WhereToGo</h1>
          </Link>
          <div className='menu-icon' onClick={handleClick}>
            <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
          </div>
          <ul className={click ? 'nav-menu active' : 'nav-menu'}>
            <li className='nav-item'>
              <Link to='/' className='nav-links' onClick={closeMobileMenu}>
                Home
              </Link>
            </li>
            {isLoggedIn && (
              <>
               <li className='nav-item'>
                  <Link to='/services' className='nav-links' onClick={closeMobileMenu}>
                Where To Go
                  </Link>
                </li>
                <li className='nav-item'>
                  <Link to='/products' className='nav-links' onClick={closeMobileMenu}>
                    My Trips    
                  </Link>
                </li>
                <li className='nav-item'>
                  <button className='nav-links logout-btn' onClick={handleLogout}>
                    Logout
                  </button>
                </li>
                <li className='nav-item'>
                  <span className='nav-links'>Hi {userName}</span>
                </li>
              </>
            )}
            {!isLoggedIn && (
              <>
                <li className='nav-item'>
                  <Link to='/register' className='nav-links' onClick={closeMobileMenu}>
                    Register
                  </Link>
                </li>
                <li className='nav-item'>
                  <Link to='/login' className='nav-links' onClick={closeMobileMenu}>
                    Login
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </nav>
    </>
  );
}

export default Navbar;