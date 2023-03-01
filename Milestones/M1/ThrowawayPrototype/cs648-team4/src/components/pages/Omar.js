import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Omar
export default function Omar(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Omar Thiongane</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p>Hello to all</p>
        <p>I am on my second to last semester and in computer science field </p>
        <p>Before graduating I want to learn more about AI and cybersecurity</p>
        <p>I want to get in to those field because i care about pravicy and the future</p>
        </div>
        </>
        );
}
