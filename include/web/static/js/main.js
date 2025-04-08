"use strict"

const root = document.documentElement; //get root of site
const counter = document.getElementById("counter");

let textColor;
let backgroundColor;

let runCount;

function reload() {
  requestData().then(data => {
    parseAndSave(data);
    updateStyle();
    updateHTML();
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
  runCount = parsedData[2];
}

function updateStyle() {
  root.style.setProperty("--textColor", textColor);
  root.style.setProperty("--backgroundColor", backgroundColor);
}

function updateHTML() {
  counter.innerHTML = `Runs: ${runCount}`;
}

setInterval(reload, 1000); //reload page after a second