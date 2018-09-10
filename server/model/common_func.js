var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

app.find_id_db = function(id, callback) {
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

// 현재시간구하기 + format
app.getTime = function(callback) {
    function getTimeStamp() {
        var d = new Date();
      
        var s =
          leadingZeros(d.getFullYear(), 4) + '-' +
          leadingZeros(d.getMonth() + 1, 2) + '-' +
          leadingZeros(d.getDate(), 2) + ' ' +
      
          leadingZeros(d.getHours(), 2) + ':' +
          leadingZeros(d.getMinutes(), 2) + ':' +
          leadingZeros(d.getSeconds(), 2);
      
        return s;
      }
      
      function leadingZeros(n, digits) {
        var zero = '';
        n = n.toString();
      
        if (n.length < digits) {
          for (i = 0; i < digits - n.length; i++)
            zero += '0';
        }
        return zero + n;
      }
      
      callback(getTimeStamp());
};

module.exports = app;