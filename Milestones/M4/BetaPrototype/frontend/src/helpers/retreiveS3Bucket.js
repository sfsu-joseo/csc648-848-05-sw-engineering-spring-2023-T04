import axios from "../api/axios";

const retrieveJSON = async (bucketName, key) => {
  const adjustedKey = key + ".json";
  console.log(adjustedKey);
  try {
    const res = await axios.get(`/download/${adjustedKey}`);
    console.log(res.data);
    return res.data;
  } catch (error) {
    console.error("Error downloading JSON data:", error);
    return -1;
  }
};

export default retrieveJSON;
