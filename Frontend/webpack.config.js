const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },
  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    moviePage: path.resolve(__dirname, 'src', 'pages', 'moviePage.js'),
    usercreatePage: path.resolve(__dirname, 'src', 'pages', 'usercreatePage.js'),
    usersPage: path.resolve(__dirname, 'src', 'pages', 'usersPage.js'),
    eventsPage: path.resolve(__dirname, 'src', 'pages', 'eventsPage.js'),
    eventPage: path.resolve(__dirname, 'src', 'pages', 'eventPage.js'),
    eventcreatePage: path.resolve(__dirname, 'src', 'pages', 'eventcreatePage.js'),
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 5000,
    open: true,
    openPage: 'http://localhost:5001/index.html',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/movie.html',
      filename: 'movie.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
          template: './src/users.html',
          filename: 'users.html',
          inject: false
    }),
    new HtmlWebpackPlugin({
          template: './src/usercreate.html',
          filename: 'usercreate.html',
          inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/events.html',
      filename: 'events.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/event.html',
      filename: 'event.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
      template: './src/eventcreate.html',
      filename: 'eventcreate.html',
      inject: false
    }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]
}
