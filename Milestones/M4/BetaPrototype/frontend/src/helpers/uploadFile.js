import axios from "../api/axios";

const uploadFile = async (key, file, setLoadingBar, pathname) => {
  const type = file.name.split(".").pop();
  const adjustedKey = key + "." + type;
  const formData = new FormData();
  formData.append("file", file, adjustedKey);

  const config = {
    headers: {
      "Content-Type": "multipart/form-data",
    },
    onUploadProgress: function (progressEvent) {
      setLoadingBar({
        filename: file.name,
        progress: Math.round(
          (progressEvent.loaded * 100) / progressEvent.total
        ),
        pathname: pathname,
      });
    },
  };

  try {
    if (type === "mp4" || type === "mov") {
      console.log("uploading video file");
      console.log(adjustedKey);
      const res = await axios.post(
        `/streamUploadFile/${adjustedKey}`,
        formData,
        config
      );
    } else {
      const res = await axios.post(
        `/uploadFile/${adjustedKey}`,
        formData,
        config
      );
    }

    return 0;
  } catch (error) {
    return -1;
  }
};

export default uploadFile;
