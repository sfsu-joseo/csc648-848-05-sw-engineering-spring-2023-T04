import { S3Client, PutObjectCommand } from "@aws-sdk/client-s3";

const uploadJsonToS3 = async (bucketName, key, jsonData) => {
  const s3 = new S3Client({
    region: "us-west-1",
    credentials: {
      accessKeyId: "",
      secretAccessKey: "",
    },
  });

  const stringifiedData = JSON.stringify(jsonData);

  try {
    const params = {
      Bucket: bucketName,
      Key: key + ".json",
      Body: stringifiedData,
      ContentType: "application/json",
    };

    const command = new PutObjectCommand(params);
    await s3.send(command);

    console.log(`JSON data uploaded to s3://${bucketName}/${key}`);
    return 0;
  } catch (error) {
    console.error("Error uploading JSON data:", error);
    return -1;
  }
};

export default uploadJsonToS3;
