"use strict"

const root = document.documentElement; //get root of site

let textColor;
let backgroundColor;

function reload() {
  requestData().then(data => {
    parseAndSave(data);
    updateStyle();
    showTime();
  });
}

async function requestData() {
  const reloadRequest = new Request("/dat/get.data", {
    method: "POST",
    headers: {"Content-Type" : "text/plain"},
    body: "PLEASE",
  });

  const response = await fetch(reloadRequest);
  const returnText = await response.text();

  return returnText;
}

function parseAndSave(data) {
  let parsedData = data.split("\n");

  textColor = parsedData[0];
  backgroundColor = parsedData[1];
}

function updateStyle() {
  root.style.setProperty("--textColor", textColor);
  root.style.setProperty("--backgroundColor", backgroundColor);
}

setInterval(reload, 1000); //reload page after a second