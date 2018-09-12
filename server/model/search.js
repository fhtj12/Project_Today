var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

// DB에 검색
app.search_db = function(search, callback) {
    callback(null, search);
};