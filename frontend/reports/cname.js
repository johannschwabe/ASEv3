const fs = require("fs");

const cname_file = "./dist/CNAME";
const url = "bom-builder-v2.polygon-demo.ch";
fs.writeFile(cname_file, url, (err) => console.error(err));
