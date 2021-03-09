var URL = 'http://localhost:8080/weather/';

var request = require('supertest').agent(URL);
const expect = require("chai").expect;

exports.request = request;
exports.expect = expect;
