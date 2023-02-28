import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Josh
export default function Josh(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Joshua Del Rosario</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p>Hello! My name is Joshua Del Rosario and I'm the Document Master/Database Master for Team 4.
         I'm currently in my last semester at SFSU so I'm excited to work with my team to create a great website and to finally graduate!
         Some things I enjoy on my free time are playing video games and going to the gym/working out. I also enjoy musicals, I've been addicted to Hamilton songs recently :D. </p>
        </div>
        </>
        );
}
