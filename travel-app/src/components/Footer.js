import React, { useState } from 'react';
import './Footer.css';
import { Link } from 'react-router-dom';

function Footer() {
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubscribe = async () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setErrorMessage('Invalid email format');
      return;
    }

    setLoading(true); // Set loading state to true when subscription request starts
  
    try {
      const response = await fetch(`https://armenianspeech.info/subscribe?email=${email}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
      });
      
      if (response.ok) {
        console.log('Subscription successful!');
        setErrorMessage('Successfully subscribed!');
        setEmail('');
      } else if(response.status === 409) {
        console.error('Subscription failed');
        setErrorMessage('Email already subscribed!');
        setEmail('');
      }
      else {
        console.error('Subscription failed');
      }
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false); // Reset loading state when subscription request completes
    }
  };

  const handleChange = (e) => {
    setEmail(e.target.value);
    setErrorMessage(''); // Clear error message when user types in the input
  };

  return (
    <div className='footer-container'>
      <section className='footer-subscription'>
        <p className='footer-subscription-heading'>
          Provide your email address for updates about our WhereToGo project
        </p>
        <p className='footer-subscription-text'>
          You can unsubscribe at any time.
        </p>
        <div className='input-areas'>
          <input
            className={`footer-input ${errorMessage && 'invalid'}`}
            name='email'
            type='email'
            placeholder={errorMessage || 'Your Email'}
            value={email}
            onChange={handleChange}
          />
          <CustomButton onClick={handleSubscribe} disabled={loading}>
            {loading ? 'Subscribing...' : 'Subscribe'}
          </CustomButton>
        </div>
        {errorMessage && <p className='error-message'>{errorMessage}</p>}
      </section>
    
      <section className='social-media'>
        <div className='social-media-wrap'>
          <small className='website-rights'>WhereToGo © 2025 All rights reserved.</small>
          <div className='social-icons'>
          </div>
        </div>
      </section>
    </div>
  );
}

const CustomButton = ({ onClick, disabled, children }) => (
  <button className='custom-button' onClick={onClick} disabled={disabled}>
    {children}
  </button>
);

export default Footer;
