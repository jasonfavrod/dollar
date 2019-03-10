const dotenv = require('dotenv');
const rp = require('request-promise');

(async () => {
    dotenv.config();

    const res = await rp({
        headers: {
            WRITE_KEY: process.env.WRITE_KEY // WRITE_KEY to be implemented.
        },
        method: 'POST',
        url: 'https://bsw0vp4f48.execute-api.us-west-2.amazonaws.com/default/get-usdx',
    });

    res; // res === 'OK'
})();
