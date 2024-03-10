"use strict"

const fs = require("fs");

const lines = fs.readFileSync("./index0.js")
    .toString()
    .split("\n");

let output = lines
    .map(line => line.trim())
    .filter(line => line.length !== 0)
    .flatMap(line => {
    if(line === "\"use strict\"") {
        return [];
    } else {
        return [line];
    }
});

console.log(output);
fs.writeFileSync("./index.mgs", output.join("\n"));
