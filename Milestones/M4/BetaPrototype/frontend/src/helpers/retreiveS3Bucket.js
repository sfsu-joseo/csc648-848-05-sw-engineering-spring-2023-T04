// -- temp. will be retreiving from s3 bucket later on -- //
const AWS = require("aws-sdk");

const retreiveJSON = async (bucketName, key) => {
  const s3 = new AWS.S3();

  try {
    const params = {
      Bucket: bucketName,
      Key: key + ".json",
    };

    const response = await s3.getObject(params).promise();
    const jsonData = JSON.parse(response.Body.toString());
    console.log("JSON data retrieved from S3:", jsonData);
    return jsonData;
  } catch (error) {
    console.error("Error downloading JSON data:", error);
    return -1;
  }
};

export default retreiveJSON;
