var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

// 위치정보 수집 허가 여부 확인
app.confirm_permission_db = function(uid, callback) {
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
                        conn.release();
                        return callback(null, result[0].gps_permission);
                    }
                }
            });
        }
    });
};

app.collect_gps_db = function(location, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'INSERT INTO user_gps_log (uid, longitude, latitude, altitude, accuracy) VALUES (?, ?, ?, ?, ?)';
            conn.beginTransaction(function(err) {
                if(err) {
                    console.log('Failed begin Transaction');
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.query(query, location, function(err, rows, fields) {
                        if(err) {
                            console.log('Failed create a Query');
                            conn.rollback(function() {
                                err = error.mysql_db_error;
                                return callback(err);
                            });
                        } else {
                            conn.commit(function(err) {
                                if(err) {
                                    console.log('Failed commit a Query');
                                    conn.rollback(function() {
                                        err = error.mysql_db_error;
                                        return callback(err);
                                    });
                                } else {
                                    return callback(null);
                                }
                            });
                        }
                    });
                }
            });
        }
    });
};

module.exports = app;