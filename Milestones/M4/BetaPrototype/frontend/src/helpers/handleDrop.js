import readDroppedFiles from "./readDroppedFiles";
import uploadJsonToS3 from "./uploadS3Bucket";

const updateParentSize = (node, parsingArr, size) => {
  parsingArr.shift();
  if (parsingArr.length === 0) {
    node.size += size;
    return node;
  }
  node.size += size;
  for (let i = 0; i < node.items.length; i++) {
    if (node.items[i].name === parsingArr[0]) {
      node.items[i] = updateParentSize(node.items[i], parsingArr, size);
      return node;
    }
  }
};

const handleDrop = async (
  e,
  node,
  setDragOver,
  setMoveEffect,
  handleMoveFile,
  setLoading,
  tempFile,
  explorerData,
  setMessage,
  setSplashMsg,
  data,
  owner
) => {
  if (data.user !== owner.user) {
    return;
  }

  e.preventDefault();
  e.stopPropagation();
  setLoading(true);
  setDragOver(false);
  setMoveEffect(false);

  if (tempFile && tempFile.state === "dragging") {
    handleMoveFile(node.pathname);

    setLoading(false);
  } else {
    // uploading here
    console.log("uploading this file");

    const objArr = await readDroppedFiles(e, node);

    if (objArr === -1) {
      console.log("exceeded storage limit");
      // set failed in main message
      setLoading(false);
      setMessage({
        title: "Uh-oh!",
        body: "Looks like this upload request exceeds the available storage space on this account. Try deleting files from trash bin to free up space.",
      });
      return;
    }

    // --- this will update and sort global currDir --- //

    // check if size meets storage limit.
    let size = 0;
    for (let i = 0; i < objArr.length; i++) {
      size += objArr[i].size;
    }

    if (explorerData.size + size > 1e9) {
      console.log("exceeded storage limit w/ : " + explorerData.size + size);
      // set failed in main message
      setLoading(false);
      setMessage({
        title: "Uh-oh!",
        body: "Looks like this upload request exceeds the available storage space on this account. Try deleting files from trash bin to free up space.",
      });
      return;
    }

    for (let i = 0; i < objArr.length; i++) {
      if (node.items.some((item) => item.name === objArr[i].name)) {
        // prompt skip or replace b/c merge is too hard to code :/
      } else {
        node.items.push(objArr[i]);
      }
    }

    node.items.sort((a, b) => {
      let fa = a.name.toLowerCase(),
        fb = b.name.toLowerCase();

      return fa.localeCompare(fb, undefined, { numeric: true });
    });

    // this may never be needed here ...
    let folders = [];
    let files = [];
    for (let i = 0; i < node.items.length; i++) {
      if (node.items[i].type === "Folder") folders.push(node.items[i]);
      else files.push(node.items[i]);
    }
    const updateitems = folders.concat(files);
    node.items = updateitems;

    /* parse through tree using pathname from current directory. 
    then add size to each node in branch */

    let parsingArr = node.pathname.split("/");
    const res = updateParentSize(explorerData, parsingArr, size);

    setLoading(false);

    console.log("snapshot after upload");
    console.log(explorerData);

    const ret = uploadJsonToS3("prototypebucket-1", `${data.id}`, explorerData);
    if (ret === -1) {
      setSplashMsg({
        message: "Upload failed!",
        isShowing: true,
      });
    } else {
      setSplashMsg({
        message: "Upload successful!",
        isShowing: true,
      });
    }
  }
};

export default handleDrop;
