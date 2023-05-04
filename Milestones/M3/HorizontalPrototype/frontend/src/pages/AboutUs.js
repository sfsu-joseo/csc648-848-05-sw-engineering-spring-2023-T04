import Footer from "../components/Footer";

const AboutUs = ({ windowDimension }) => {
  return (
    <div className="page">
      <div style={{ margin: "auto", marginTop: "200px" }}>
        ~ to be implemented ~
      </div>

      <div>
        <header>Joshua Del Rosario</header>
        <p>Hello! I'm the Document Master/Database Master for Team 4.
          I'm currently in my last semester at SFSU and I'm excited to finally graduate!
          Some things I enjoy on my free time are playing video games and going to the gym/working out. I also enjoy musicals, I've been addicted to Hamilton songs recently :D.
        </p>
      </div>
      <Footer windowDimension={windowDimension} />
    </div>
  );
};

export default AboutUs;
