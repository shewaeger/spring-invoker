module.exports = {
    outputDir: '../src/main/resources/static/frontend',
    indexPath: '../../templates/web-socket-tester.html',
    publicPath: '/ws/',
    devServer: {
      watchOptions: {
        poll: 1000,
      },
      proxy: {
        
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
  