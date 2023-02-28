import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Wilson
export default function Wilson(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Wilson Yin</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p>*Your introduction here*</p>
        </div>
        </>
        );
}
