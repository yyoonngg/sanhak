const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080/api',
            changeOrigin: true,
            setupMiddlewares: (middlewares, devServer) => {
                console.log('Custom middleware setup');
                return middlewares;
            }
        })
    );
};
