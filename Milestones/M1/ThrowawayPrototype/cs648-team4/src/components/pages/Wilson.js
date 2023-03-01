import React from "react";
import { Link } from "react-router-dom";
import "../../App.css";

//function for team member - Wilson
export default function Wilson() {
  return (
    <>
      <div className="team">
        <h1 className="member__name">Wilson Yin</h1>
        <Link to="/">
          <h1 className="redirect">Go to Home Page</h1>
        </Link>
        <p>
          <br></br>
          Hello nice to meet you, I was born and raised in Southern California!
          When I was 20 years old, I decided to move to San Francisco so pursue
          my passion in software, technology, and innovation.
          <br></br>
          <br></br>
          Since moving to the Bay Area, I have been attending San Francisco
          State University pursuing a degree in Computer Science. In school,
          I've took on leadership positions for SF Hacks. In SF Hacks, I served
          as the Website Lead where I both managed the team and also delivered
          code for the organization's main marketing platform.
          <br></br>
          <br></br>
          In terms of work experience, I interned at Wells Fargo and Gesture
          where I worked in cloud architecture and front end development,
          respectively. From working in big corporate, startup, and club
          organizations, my experiences I've gained strong technical skills
          while also have strong communication and flexibility.
        </p>
      </div>
    </>
  );
}
