import React from 'react'
import CardItem from './CardItem';
import './Cards.css';

//function for card container
function Cards() {
  return (
    <>
    <div className='heading'>
            <h1>Software Engineering class SFSU </h1>
            <h1>Spring 2023</h1>
            <h1>Section 05</h1>
            <h1>Team 4</h1>
        </div>
    <div className="cards">
        <div className = "cards__container">
            <div className='cards__wrapper'>
                <ul className='cards__items'>
                    <CardItem
                    text="Saru Shrestha"
                    label="Team Lead"
                    path='/saru'/>
                    <CardItem
                    text="Kilian Kistenbroker"
                    label="Frontend Lead"
                    path='/kilian'/>
                    <CardItem
                    text="Elias Abay"
                    label="Backend Lead"
                    path='/elias'/>
                    <CardItem
                    text="Parth Panchal"
                    label="Github Master"
                    path='/parth'/>
                    <CardItem
                    text="Wilson Yin"
                    label="Database Master"
                    path='/wilson'/>
                    <CardItem
                    text="Joshua Del Rosario"
                    label="Database Master and Document Editor"
                    path='/josh'/>
                    <CardItem
                    text="Omar Thiongane"
                    label="Document Editor"
                    path='/omar'/>
                </ul>
            </div>
        </div>
    </div>
    </>
  )
}

export default Cards;