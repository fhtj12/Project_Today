var express = require('express');
var mysql = require('mysql');
var logger = require('logger').createLogger();
var db_conn = require('../db/db_conn');
var db_pool = mysql.createPool(db_conn);
var errors = require('../errors').errors;
var app = express();

app.login = function(id, pwd, conn, callback) {
    var query = 'SELECT uid, pwd FROM account WHERE id = ' + mysql.escape(id);
    conn.query(query, function(err, result, fields) {
        if (err) callback(err);
        if (result.length == 0) {
            return callback(errors.login.id_not_found);
        } else {
            if (mysql.escape(result[0].pwd) == mysql.escape(pwd)) {
                callback(null, result[0].uid);
            } else {
                return callback(errors.login.invalid_pwd);
            }
        }
    });
};

app.update_login = function(uid, session_token, conn, callback) {
    var query = 'UPDATE account SET'
                    + ' last_login = ' + mysql.escape(new Date()) // 마지막 로그인에 현재 시간
                    + ' WHERE uid = ' + mysql.escape(uid);
    conn.query(query, function(err, result) {
        if(err) callback(err);
        if(result.affectedRows == 0) {
            return callback(errors.mysql_db_error);
        }
        callback(null, session_token);
    });
}

module.exports = app;