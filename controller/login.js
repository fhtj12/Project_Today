var express = require('express');
var async = require('async');
var mysql = require('mysql');
var hash = require('object-hash');
var logger = require('logger').createLogger();

var db_conn = require('../db/db_conn');
var db_pool = mysql.createPool(db_conn);

var login_db = require('../db/login');
var redis_cli = require('../db/redis_cli');

var errors = require('../errors').errors;

var app = express();

app.login = function(id, pwd, req, res, router_callback) {
    db_pool.getConnection(function(err, conn) {
        if (err) {
            err = errors.mysql_db_error;
            logger.error(err);
            router_callback(err, null, req, res);
            return;
        }
        async.waterfall([
            async.apply(login_db.login, id, pwd, conn)
            , function(uid, callback) {
                redis_cli.new_session(uid, callback);
            }, function(uid, session_token, callback) {
                login_db.update_login(uid, session_token, conn, callback);
            }, function(session_token, callback) {
                // 응답 생성
                var ret = {
                    'ret' : 'ok',
                    'session_token' : session_token
                };
                callback(null, ret);
            }
        ], function(err, ret) {
            // router로 응답 반환
            conn.release();
            router_callback(err, ret, req, res);
        });
    });
}

module.exports = app;