class counter {
  constructor(id) {
    this.id = id;
    this.counter = document.getElementById("counter");
  }

  async requestData() {
    const reloadRequest = new Request("/get/widget", {
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
      let parsed = data.split("\n");

      this.count = parsed[0];
      this.label = parsed[1];

      this.counter.innerHTML = `${this.label}${this.count}`;
    });
  }
};

function update() {
  reload();
  o.updateText();
}

setInterval(update, 1000); //reload page after a second