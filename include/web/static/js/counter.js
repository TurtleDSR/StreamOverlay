"use strict"

class counter {
  constructor(id) {
    this.id = id;
    this.counter = document.getElementById(`counter${id}`);
  }

  async requestData() {
    const reloadRequest = new Request("/dat/get.counter", {
      method: "POST",
      headers: {"Content-Type" : "text/plain"},
      body: `${this.id}`,
    });
  
    const response = await fetch(reloadRequest);
    const returnText = await response.text();
  
    return returnText;
  }

  updateText() {
    this.requestData().then(data => {
      this.setText(data);
    });
  }

  setText(text) {
    this.text = text;
    this.counter.innerHTML = text;
  }
};