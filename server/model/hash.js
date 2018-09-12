var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

// 검색어 수집에 동의하였는지 권한 검사하는 함수
app.confirm_permission = function(uid, callback) {
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
                        return callback(null, result[0].search_permission);
                    }
                }
            });
        }
    });
};

// 파싱된 해시태그를 최종적으로 DB에 저장하는 함수
app.collect_hash_db = function(hash, uid, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var params = [hash, uid];
            var query = 'INSERT INTO hash_tag (hash_string, uid) VALUES (?, ?)';

            conn.beginTransaction(function(err) {
                if(err) {
                    console.log('Failed begin Transaction');
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.query(query, params, function(err, rows, fields) {
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