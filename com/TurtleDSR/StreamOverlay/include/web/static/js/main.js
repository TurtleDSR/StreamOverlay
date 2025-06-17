"use strict";

const root = document.documentElement; //get root of site

let textColor;
let textOpacity;
let backgroundColor;
let backgroundOpacity;

function reload() {
  requestStyle().then(data => {
    parseAndSave(data);
    updateStyle();
  });
}

async function requestStyle() {
  const reloadRequest = new Request("/get/style", {
    method: "POST",
    headers: {"Content-Type" : "text/plain"},
    body: `${o.id}`,
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
}

function updateStyle() {
  root.style.setProperty("--textColor", hexadecimal(textColor)(textOpacity * 100));
  root.style.setProperty("--backgroundColor", hexadecimal(backgroundColor)(backgroundOpacity * 100));
}

function hexadecimal(color) {
  return (percentage) => {
    const decimal = `0${Math.round(255 * (percentage / 100)).toString(16)}`.slice(-2).toUpperCase();
    return color + decimal;
  };
}

class StringUtil {
  static parse(input) {
    let out = "";
    for(let i = 0; i < input.length; i++) {
      if(input[i] == '\\') {
        i++;
        if(input[i] == '\\') {
          out += '\\'
        } else if(input[i] == 'n') {
          out += "<br>";
        } else if(input[i] == 's') {
          out += " ";
        }
      } else {
        out += input[i];
      }
    }
    return out;
  }
}