import extern knex;
import knexfile.knexConfig;

let environment = process.env.NODE_ENV || "development";
let config = knexConfig[environment];
export default knex(config);