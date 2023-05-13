import { S3Client, GetObjectCommand } from "@aws-sdk/client-s3";

const retrieveJSON = async (bucketName, key) => {
  const s3 = new S3Client({
    region: "us-west-1",
    credentials: {
      accessKeyId: "",
      secretAccessKey: "",
    },
  });

  try {
    const params = {
      Bucket: bucketName,
      Key: key + ".json",
    };

    const command = new GetObjectCommand(params);
    const response = await s3.send(command);

    const responseBuffer = await new Response(response.Body).arrayBuffer();
    const jsonData = JSON.parse(
      new TextDecoder("utf-8").decode(responseBuffer)
    );

    console.log("JSON data retrieved from S3:", jsonData, key + ".json");
    return jsonData;
  } catch (error) {
    console.error("Error downloading JSON data:", error);
    return -1;
  }
};

export default retrieveJSON;
