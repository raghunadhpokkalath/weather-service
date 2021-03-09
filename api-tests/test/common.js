var URL = 'http://localhost:8080/weather/';

global.request = require('supertest').agent(URL);
global.expect = require("chai").expect;
