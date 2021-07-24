var express = require('express')
var app = express();
const os = require("os")

app.set('port', (process.env.PORT || 5000))
app.use(express.static(__dirname + '/public'))

app.get('/', function(request, response) {
  var id = os.hostname()
  var obj = { msg: 'Hello World', container_id: id }
  var json = JSON.stringify(obj)
  response.writeHead(200, {'Content-Type': 'application/json'})
  response.write(json)
  response.end()
})

app.listen(app.get('port'), function() {	
  console.log("Node app is running at localhost:" + app.get('port'))
  console.log("I'm in container:", os.hostname())
})
