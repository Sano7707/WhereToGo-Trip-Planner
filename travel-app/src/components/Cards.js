import React from 'react';
import './Cards.css';
import CardItem from './CardItem';

function Cards() {
  return (
    <div className='cards'>
      <h1>Check out what you can do!</h1>
      <p style={{textAlign:"center"}}>Login now and discover it!</p>
      <div className='cards__container'>
        <div className='cards__wrapper'>
          <ul className='cards__items'>
            <CardItem
              src='images/places.jpeg'
              text='Suggestions where to visit while travelling'
              label='Places'
              path='/services'
            />
            <CardItem
              src='images/memory_book.jpg'
              text='Save your trips for a memory book'
              label='Memories'
              path='/services'
            />
          </ul>
          <ul className='cards__items'>
            <CardItem
              src='images/travel_assistant.png'
              text='Virtual Assistant '
              label='AI-Assistant'
              path='/services'
            />
            <CardItem
              src='images/must_see_places.jpg'
              text='"Must-See" Lists'
              label='Moments'
              path='/services'
            />
            <CardItem
              src='images/hidden_gems.png'
              text='Find out hidden gems'
              label='Discoveries'
              path='/services'
            />
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Cards;
