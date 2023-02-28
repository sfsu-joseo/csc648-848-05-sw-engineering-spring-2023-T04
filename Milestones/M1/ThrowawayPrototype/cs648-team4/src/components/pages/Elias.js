import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Elias
export default function Elias(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Elias Abay</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p>*I am Elias Abay and I am pursuing an undergraduate degree in Computer Science at SFSU*</p>
        <p>I have also completed a minor in Mathematics in addition to completing my Bachelor's degree</p>
        <p>This is my second to last semester at San Francisco State University</p>
        <p>I hope to enjoy it through making new projects and getting to know my teammates</p>
        </div>
        </>
        );
}
