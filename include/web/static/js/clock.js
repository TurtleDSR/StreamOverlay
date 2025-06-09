class clock {
  constructor(id) {
    this.id = id;
    this.label = document.getElementById("clock");
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
      this.text = data;

      this.label.innerHTML = `${this.text}`;
    });
  }
};

function update() {
  reload();
  o.updateText();
}

setInterval(update, 500); //reload page after 1/2s