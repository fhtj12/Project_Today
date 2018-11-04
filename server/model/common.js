var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

app.duplicate_id_db = function(id, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'SELECT * FROM account WHERE id = ' + mysql.escape(id);
            conn.query(query, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    if(result.length == 0) {
                        conn.release();
                        return callback(null);
                    } else {
                        err = error.account.duplicate;
                        conn.release();
                        return callback(err);
                    }
                }
            });
        }
    });
};

app.confirm_uid_pwd = function(uid, pwd, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'SELECT * FROM account WHERE uid = ' + mysql.escape(uid);
            conn.query(query, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    if(result.length == 0) {
                        err = error.account.invalid_account;
                        conn.release();
                        return callback(err);
                    } else {
                        if(mysql.escape(pwd) == mysql.escape(result[0].pwd)) {
                            conn.release();
                            return callback(null);
                        } else {
                            err = error.account.invalid_pwd;
                            conn.release();
                            return callback(err);
                        }
                    }
                }
            });
        }
    });
};

module.exports = app;