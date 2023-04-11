process.on("uncaughtException", (error) => {
  console.log("Oh my god, something terrible happened: ", error);
  process.exit(1);
});

process.on("unhandledRejection", (error, promise) => {
  console.log(
    " Oh Lord! We forgot to handle a promise rejection here: ",
    promise
  );
  console.log(" The error was: ", error);
});

var express = require("express");
var app = express();
const os = require("os");
var createHttpProxyAgent = require("http-proxy-agent");
var translator = require("@vitalets/google-translate-api");
const agent = createHttpProxyAgent(
  process.env.PROXY_AGENT || "http://161.117.89.36:8888"
);

app.set("port", process.env.PORT || 3000);
app.use(express.static(__dirname + "/public"));
app.use(express.json());

app.get("/", (req, res) => {
  var obj = { msg: "Hello World", container_id: os.hostname() };
  var json = JSON.stringify(obj);
  res.writeHead(200, { "Content-Type": "application/json" });
  res.write(json);
  res.end();
});

app.post("/translate", async (req, res) => {
  try {
    console.log("===>>> request body ", req.body);
    const chunkSize = 200;
    const reg = new RegExp("\\.", "g");
    var words = req.body["words"];
    var translateData = [];
    var body = {};
    var status = 200;
    for (let i = 0; i < words.length; i += chunkSize) {
      console.log("===>>> chunk array words translate by", chunkSize);
      const chunk = words.slice(i, i + chunkSize);
      var strWord = "";
      for (var j = 0; j < chunk.length; j++) {
        strWord += chunk[j].replace(/\s/g, "").trim() + ", ";
      }
      strWord = strWord.slice(0, -2).trim();
      console.log("===>>> words ", strWord);
      const result = await requestTranslate(strWord);
      translateData = translateData.concat(
        result
          .replace(reg, ",")
          .split(",")
          .filter((v) => {
            return (
              v.toLowerCase().trim() !== "city" &&
              v.toLowerCase().trim() !== "district" &&
              v.toLowerCase().trim() !== "commune" &&
              v.toLowerCase().trim() !== "province" &&
              v.toLowerCase().trim() !== "" &&
              !/^-?\d+$/.test(v.toLowerCase().trim())
            );
          })
          .map((v) => v.trim().toLowerCase())
      );
    }
    body = {
      translate: translateData,
      container_id: os.hostname(),
    };
  } catch (e) {
    console.log(e);
    body = e;
    status = 400;
  }
  res.writeHead(status, { "Content-Type": "application/json" });
  res.write(JSON.stringify(body));
  res.end();
});

async function requestTranslate(strWord) {
  console.log(
    "===>>> agent proxy " + agent.proxy.host + ":" + agent.proxy.port
  );
  var data = await translator.translate(strWord.replace(/\s/g, ""), {
    to: "en",
    fetchOptions: { agent },
  });
  console.log("===>>> translate ", data.text);
  return data.text;
}

app.listen(app.get("port"), () => {
  console.log("Node app is running at localhost:" + app.get("port"));
  console.log("I'm in container:", os.hostname());
});
