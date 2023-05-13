import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";

const AWS = require("aws-sdk");

AWS.config.update({
  accessKeyId: "",
  secretAccessKey: "",
  region: "us-west-1",
});

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
