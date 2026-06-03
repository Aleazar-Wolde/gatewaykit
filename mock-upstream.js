const http = require("http");

const server = http.createServer((req, res) => {
  if (req.url === "/api/users" && req.method === "GET") {
    res.writeHead(200, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ message: "Users service response" }));
    return;
  }

  res.writeHead(404, { "Content-Type": "application/json" });
  res.end(JSON.stringify({ error: "mock_not_found" }));
});

server.listen(3001, () => {
  console.log("Mock upstream running on http://localhost:3001");
});