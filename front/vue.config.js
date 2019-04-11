module.exports = {
    outputDir: '../src/main/resources/static/frontend',
    indexPath: '../../templates/index.html',
    baseUrl: '/',
    devServer: {
      watchOptions: {
        poll: 1000,
      },
      proxy: {
        '^/web-socket-api': {
          target: 'http://localhost:8080',
        },
      },
      overlay: {
        warnings: true,
        errors: true,
      },
    },
    css: {
      loaderOptions: {
        sass: {
          data: `
            @import "@/assets/scss/utilities/_variables.scss";
            @import "@/assets/scss/utilities/_mixins.scss";
          `,
        },
      },
    },
  };
  