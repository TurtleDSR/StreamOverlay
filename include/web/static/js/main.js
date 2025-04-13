"use strict"

const root = document.documentElement; //get root of site
const counter = document.getElementById("counter");

let textColor;
let textOpacity;
let backgroundColor;
let backgroundOpacity;

let runCount;

function reload() {
  requestData("counter", 0).then(data => {
    parseAndSave(data);
    updateStyle();
    updateHTML();
  });
}

async function requestData(widgetType, widgetId) {
  const reloadRequest = new Request(`/dat/get.${widgetType}`, {
    method: "POST",
    headers: {"Content-Type" : "text/plain"},
    body: `${widgetId}`,
  });

  const response = await fetch(reloadRequest);
  const returnText = await response.text();

  return returnText;
}

function parseAndSave(data) {
  let parsedData = data.split("\n");

  textColor = parsedData[0];
  textOpacity = parsedData[1];
  backgroundColor = parsedData[2];
  backgroundOpacity = parsedData[3];
  runCount = parsedData[4];
}

function updateStyle() {
  root.style.setProperty("--textColor", hexadecimal(textColor)(textOpacity * 100));
  root.style.setProperty("--backgroundColor", hexadecimal(backgroundColor)(backgroundOpacity * 100));
}

function updateHTML() {
  counter.innerHTML = `Runs: ${runCount}`;
}

function hexadecimal(color) {
  return (percentage) => {
    const decimal = `0${Math.round(255 * (percentage / 100)).toString(16)}`.slice(-2).toUpperCase();
    return color + decimal;
  };
}

setInterval(reload, 1000); //reload page after a second