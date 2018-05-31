var express = require('express');
var async = require('async');
var mysql = require('mysql');
var hash = require('object-hash');
var logger = require('logger').createLogger();

var db_conn = require('../db/db_conn');
var db_pool = mysql.createPool(db_conn);

var account_db = require('../db/account');

var errors = require('../errors').errors;

var app = express();

app.new_account = function(id, pwd, req, res, router_callback) {
    db_pool.getConnection(function(err, conn) {
        if (err) {
            err = errors.mysql_db_error;
            logger.error(err);
            conn.release();
            return router_callback(err, null, req, res);
        }

        async.waterfall([
            // id 중복 검사
            async.apply(account_db.check_id, id, conn),
            function(callback) {
                // 실제 id 생성
                var uid = hash(id + pwd + new Date(), {algorithm : 'sha1'});
                account_db.new_account(id, pwd, uid, conn, callback);
            }, function(callback) {
                // 응답 생성
                callback(null, ret);
            }
        ], function(err, ret) {
            // router로 응답 반환
            router_callback(err, ret, req, res);
        });
    });
}

module.exports = app;