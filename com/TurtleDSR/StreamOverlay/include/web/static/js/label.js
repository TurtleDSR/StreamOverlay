class label {
  constructor(id) {
    this.id = id;
    this.label = document.getElementById("label");
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

      this.text = StringUtil.parse(parsed[0]);
      this.w = parsed[1];
      this.h = parsed[2];

      this.label.innerHTML = `${this.text}`;

      root.style.setProperty("--width", this.w);
      root.style.setProperty("--height", this.h);
    });
  }
};

function update() {
  reload();
  o.updateText();
}

setInterval(update, 1000); //reload page after a second