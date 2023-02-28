import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Parth
export default function Parth(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Parth Panchal</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <br></br>
        <h1>Hello! Welcome to my about page!</h1>
        <p>I just joined this Spring 2023. I've had 2+ years of experience as a full stack developer and looking forward to build on my computer science foundations during my time here in SFSU. I love writing poems📝, listening to music🎶, photography📸, reading philosophy📚 & travelling✈️! </p>
        </div>
        </>
        );
}
