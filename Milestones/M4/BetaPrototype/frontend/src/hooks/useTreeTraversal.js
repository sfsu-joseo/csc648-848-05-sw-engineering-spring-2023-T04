// const finalTree = insertNode(currentDirectory, id, name, type);
import uploadJsonToS3 from "../helpers/uploadS3Bucket";

const useTreeTraversal = () => {
  function insertNode(
    explorerData,
    currentDirectory,
    handleSetCurrentDirectory,
    setCurrentDirectory,
    setExplorerData,
    name,
    type,
    data,
    setSplashMsg
  ) {
    if (currentDirectory.items.some((item) => item.name === name)) {
      // prompt skip or replace
    } else {
      let dataUrl = "";

      // convert.com to .url file
      if (type === "url") {
        dataUrl =
          "application/octet-stream;base64," + window.btoa("URL=" + name);
      }

      const regex = new RegExp("/", "g");
      const adjustedForPathname = name.replace(regex, "%");

      // temporary placement
      const time = new Date().getTime();
      console.log(time);

      currentDirectory.items.push({
        id: time, // temporary
        pathname: currentDirectory.pathname + "/" + adjustedForPathname,
        name,
        type,
        size: 0,
        isPinned: false,
        visibility: "Private", // <-default
        permissions: "Only you have access",
        dataUrl: dataUrl,
        items: [],
      });

      currentDirectory.items.sort((a, b) => {
        let fa = a.name.toLowerCase(),
          fb = b.name.toLowerCase();

        return fa.localeCompare(fb, undefined, { numeric: true });
      });

      let folders = [];
      let files = [];
      for (let i = 0; i < currentDirectory.items.length; i++) {
        if (currentDirectory.items[i].type === "Folder")
          folders.push(currentDirectory.items[i]);
        else files.push(currentDirectory.items[i]);
      }
      const updateitems = folders.concat(files);
      currentDirectory.items = updateitems;

      const ret = uploadJsonToS3(
        "prototypebucket-1",
        `${data.id}`,
        explorerData
      );
      // if (ret === -1) {
      //   setSplashMsg({
      //     message: "Upload failed!",
      //     isShowing: true,
      //   });
      // } else {
      //   setSplashMsg({
      //     message: "Upload successful!",
      //     isShowing: true,
      //   });
      // }
    }
  }

  return { insertNode };
};

export default useTreeTraversal;
