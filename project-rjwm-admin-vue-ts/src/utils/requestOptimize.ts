import md5 from 'md5';

// According to the request address, method, parameters, uniformly calculate the current request md5 value as the key
const getRequestKey = (config) => {
    if (!config) {
        // If the request related configuration information is not obtained, generate according to the timestamp
        return md5(+new Date());
    }

    const data = typeof config.data === 'string' ? config.data : JSON.stringify(config.data);
    // console.log(config,pending,config.url,md5(config.url + '&' + config.method + '&' + data),'config')
    return md5(config.url + '&' + config.method + '&' + data);
}

// Store key value
const pending = {};
// Check key value
const checkPending = (key) => !!pending[key];
// Delete key value
const removePending = (key) => {
    // console.log(key,'key')
    delete pending[key];
};

export {
    getRequestKey,
    pending,
    checkPending,
    removePending
}
