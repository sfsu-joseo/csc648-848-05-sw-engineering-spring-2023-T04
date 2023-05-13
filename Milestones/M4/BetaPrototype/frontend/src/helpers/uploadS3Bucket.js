const AWS = require("aws-sdk");

const uploadJsonToS3 = async (bucketName, key, jsonData) => {
  const s3 = new AWS.S3();
  const stringifiedData = JSON.stringify(jsonData);

  try {
    const params = {
      Bucket: bucketName,
      Key: key + ".json", // will be the same as user_id
      Body: stringifiedData,
      ContentType: "application/json",
    };

    await s3.upload(params).promise();
    console.log(`JSON data uploaded to s3://${bucketName}/${key}`);
    return 0;
  } catch (error) {
    console.error("Error uploading JSON data:", error);
    return -1;
  }
};
export default uploadJsonToS3;
