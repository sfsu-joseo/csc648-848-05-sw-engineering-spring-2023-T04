import React from "react";
import { Link } from "react-router-dom";
import '../../App.css';

//function for team member - Kilian
export default function Kilian(){
    return (
        <>
        <div className="team">
        <h1 className = 'member__name'>Kilian Kistenbroker</h1>
        <Link to='/'>
            <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p style={{width : "600px", paddingTop : "50px", marginLeft: "50%", transform: "translate(-50%, 0)"}}> 
            <div>
                My name is in the header. I do
                not really have any fun hobbies 
                at the moment, but I am trying
                to finish developing a video game 
                on my free time.
            </div>

            <br></br>

            <div>
                I used to work as an aircraft maintenance
                technician for 2 major airframes for
                several years before quitting to pursue something 
                in CS.
            </div>

            <br></br>
                
            <div>
                My experience in web dev is limited; however,
                I am very accustomed with picking up new
                technologies.
            </div>

            
            
        </p>
        </div>
        </>
        );
}
