import bodyParser from "body-parser";
import dotenv from "dotenv";
import cors from "cors";
import express from "express";
import knex from "knex";
import fs from "fs/promises";
import paths from "path";
import { Knex }  from "knex";
import { env }  from "./actual";
dotenv.config();
const environment = process.env("NODE_ENV").orElse("development");
let knexConfig: Knex.Config = {
    client: "better-sqlite3",
    connection: {
        filename: "./data.sqlite",
    },
    useNullAsDefault: true
};
let connection = knex(knexConfig);
let port = process.env("PORT")
    .map(I32.tryInto)
    .flatMap(Result.findValue)
    .orElse(3000);
let app = express();
app.use(express.json());
app.use(cors());
app.use(bodyParser.json());
app.get("/", (req, res) => {
    const path = paths.join(process.cwd(), "src", "index.mgs");
const buffer = await fs.readFile(path, {encoding: "utf-8"});
res.setHeader("Content-Type", "text/plain");
res.send(buffer);
});
app.listen(port, () => {
    console.log(`Server is running on port ${port}.`);
});
