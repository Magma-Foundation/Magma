"use strict"

const fs = require("fs");

const value = fs.readFileSync("./index.js");
fs.writeFileSync("./index.mgs", value);
