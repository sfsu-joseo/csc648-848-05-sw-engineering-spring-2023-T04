import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';
import './team.css';

//function for team member - Saru
export default function Saru(){
    return (
    <>
    <div className="team">
    <h1 className = 'member__name'>Saru Shrestha</h1>
    <Link to='/'>
        <h1 className="redirect">Go to Home Page</h1>
    </Link>
    <p>Hey there!</p>
    <p>I am Saru Shrestha pursuing an Undergraduate degree in Computer Science from San Francisco State University.</p>
    <p>I completed my Associates Degree in Computer Science and Mathematics </p>
    <p>from the College of San Mateo.</p>
    <p>It is my last semester here at the SFSU. </p>
    <p>I am looking forward to wrapping up my college days with great projects!</p>
    </div>
    </>
    );
}
