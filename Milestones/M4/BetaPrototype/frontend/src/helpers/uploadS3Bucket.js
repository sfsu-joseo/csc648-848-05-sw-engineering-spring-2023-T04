import axios from "../api/axios";

const uploadJsonToS3 = async (bucketName, key, jsonData) => {
  const adjustedKey = key + ".json";
  const stringifiedData = JSON.stringify(jsonData);
  const blob = new Blob([stringifiedData], { type: "application/json" });
  const formData = new FormData();
  formData.append("file", blob, adjustedKey);
  try {
    axios.post(`/uploadJson/${adjustedKey}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    return 0;
  } catch (error) {
    return -1;
  }
};

export default uploadJsonToS3;
