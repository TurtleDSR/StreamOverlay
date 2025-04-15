class counter {
  constructor(id) {
    this.id = id;
    this.counter = document.getElementById("counter");
  }

  async requestData() {
    const reloadRequest = new Request("/get/counter", {
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
      this.prenum = parsed[1];

      this.counter.innerHTML = `${this.prenum} ${this.count}`;
    });
  }

  setText(text) {
    this.text = text;
    this.counter.innerHTML = `${this.prenum} ${this.count}`;
  }
};

function update() {
  reload();
  c.updateText();
}

setInterval(update, 1000); //reload page after a second