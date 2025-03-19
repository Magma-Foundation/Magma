import express from "express";
import knex from "./db";
import bodyParser from "body-parser";
import cors from "cors";

const PORT = process.env.PORT || 3000;

const app = express();
app.use(express.json());
app.use(cors());
app.use(bodyParser());

app.get("/", async (req, res) => {
    res.json();
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}.`);
});
