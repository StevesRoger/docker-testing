var express = require("express");
var app = express();
const os = require("os");
var createHttpProxyAgent = require("http-proxy-agent");
var translator = require("@vitalets/google-translate-api");
const agent = createHttpProxyAgent(
  process.env.PROXY_AGENT || "http://125.22.61.95:80"
);

app.set("port", process.env.PORT || 3000);
app.use(express.static(__dirname + "/public"));
app.use(express.json());

app.get("/", function (req, res) {
  var id = os.hostname();
  var obj = { msg: "Hello World", container_id: id };
  var json = JSON.stringify(obj);
  res.writeHead(200, { "Content-Type": "application/json" });
  res.write(json);
  res.end();
});

app.post("/translate", async function (req, res) {
  console.log("===>>> request body ", req.body);
  var words = req.body["words"];
  var strWords = "";
  for (var i = 0; i < words.length; i++) {
    strWords += words[i] + ",";
  }
  strWords = strWords.slice(0, -1);
  console.log("===>>> words ", strWords);
  var obj = {};
  try {
    var data = await translator.translate(strWords, {
      to: "en",
      fetchOptions: { agent },
    });
    console.log("===>>> translate ", data.text);
    var id = os.hostname();
    obj = {
      translate: data.text.split(",").map((v) => v.trim().toLowerCase()),
      container_id: id,
    };
  } catch (e) {
    console.log(e);
    obj = { error: e };
  }
  var json = JSON.stringify(obj);
  res.writeHead(200, { "Content-Type": "application/json" });
  res.write(json);
  res.end();
});

app.listen(app.get("port"), function () {
  console.log("Node app is running at localhost:" + app.get("port"));
  console.log("I'm in container:", os.hostname());
});
