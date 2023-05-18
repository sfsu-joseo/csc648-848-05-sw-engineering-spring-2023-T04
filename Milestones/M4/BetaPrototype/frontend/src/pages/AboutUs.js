import Footer from "../components/Footer";
import { Link } from "react-router-dom";

const AboutUs = ({ windowDimension }) => {
  return (
    <div className="page">
      <div
        className="legal"
        // style={{ margin: "auto", marginTop: "200px", marginBottom: "100px" }}
      >
        <h1 className="legal-h1">About Us</h1> <br />
        <h1 className="legal-h2">Team Lead ~ Saru Shrestha</h1>
        <p className="legal-body">
          Hey there! I am Saru Shrestha pursuing an Undergraduate degree in Computer Science from San Francisco State University.
          I completed my Associates Degree in Computer Science and Mathematics from the College of San Mateo.
          It is my last semester here at the SFSU. 
          <br /> <br />
          Connect with me on LinkedIn: {" "}
          <Link to={"https://linkedin.com/in/shresthasaru"} target="_blank">
            www.linkedin.com/in/shresthasaru
          </Link>
          . Don't hesitate to reach out and send me a connection request to
          stay connected and explore professional opportunities together.
        </p>
        <br />
        <h1 className="legal-h2">Backend Lead ~ Elias Abay</h1>
        <p className="legal-body">
          Hello! I'm the Backend Lead for Team 4. I'm a computer science student
          at SFSU and I'm in my second to last semester so I'm almost done!! A
          few things that I enjoy when I have free time is that I like to listen
          to R&B music or go out to hang with friends. Another thing that I
          enjoy is playing video games, especially ones from older consoles.
        </p>
        <br />
        <h1 className="legal-h2">
          Document Master/Database Master ~ Joshua Del Rosario
        </h1>
        <p className="legal-body">
          Hello! I'm the Document Master/Database Master for Team 4. I'm
          currently in my last semester at SFSU and I'm excited to finally
          graduate! Some things I enjoy on my free time are playing video games
          and going to the gym/working out. I also enjoy musicals, I've been
          addicted to Hamilton songs recently :D.
        </p>
        <br />
        <h1 className="legal-h2">GitHub Master ~ Parth Panchal</h1>
        <p className="legal-body">
          Hey! I am the Github Master for Team 4. I'm a computer science grad
          student at SFSU and I'm in my first semester!! After working more than
          2 years as a full stack developer, I decided to go for master's to
          build on my fundamentals and explore different areas of tech. Looking
          forward to a fun time in the bay area! Github:{" "}
          <Link to={"https://parthfloyd.github.io"} target="_blank">
            @parthfloyd
          </Link>
        </p>
        <br />
      </div>

      <Footer windowDimension={windowDimension} />
    </div>
  );
};

export default AboutUs;
