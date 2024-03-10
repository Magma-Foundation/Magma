"use strict"

const fs = require("fs");

const value = fs.readFileSync("./index0.js").toString();
fs.writeFileSync("./index.mgs", value);
