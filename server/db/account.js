var express = require('express');
var mysql = require('mysql');
var logger = require('logger').createLogger();

var db_conn = require('../db/db_conn');
var db_pool = mysql.createPool(db_conn);

var errors = require('../errors').errors;

var app = express();

app.check_id = function(id, conn, callback) {
    var query = 'SELECT id FROM account WHERE id = ' + mysql.escape(id);
    conn.query(query, function(err, result, fields) {
        if (err) {
            err = errors.mysql_db_error;
            return callback(err);
        }
        if (result.length > 0) {
            err = errors.account.duplicate;
            logger.error('error duplicate id: ' + id);
            return callback(err);
        }
        callback();
    });
};

app.new_account = function(id, pwd, uid, conn, callback) {
    var account_param = [[id, pwd, uid, new Date("1970-01-01 00:00:00")]];
    var query = 'INSERT INTO account (id, pwd, uid, last_login) VALUES ?';
    conn.query(query, [account_param], function(err, result) {
        if (result.affectedRows == 0) {
            return callback(errors.mysql_db_error);
        }
        callback();
    });
};

module.exports = app;