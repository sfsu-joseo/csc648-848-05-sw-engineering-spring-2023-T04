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
        <p>*Your introduction here*</p>
        </div>
        </>
        );
}
