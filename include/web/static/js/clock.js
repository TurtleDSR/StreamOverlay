class clock {
  constructor(id) {
    this.id = id;
    this.zone = document.getElementById("zone");
    this.date = document.getElementById("date");
    this.time = document.getElementById("time");
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

      this.zone.innerHTML = parsed[0];
      this.date.innerHTML = parsed[1];
      this.time.innerHTML = parsed[2];
    });
  }
};

function update() {
  reload();
  o.updateText();
}

setInterval(update, 500); //reload page after 1/2s