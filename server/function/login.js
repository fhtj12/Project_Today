var express = require('express');

var db_func = require('../model/account');
var error = require('../exception/error').errors;

var app = express();

app.login = function(req, callback) {
    var id = req.query.id;
    var pwd = req.query.pwd;
    db_func.login_db(id, pwd,
    function(err, uid) {
        var ret = null;
        if(err == null) {
            db_func.update_login_time(uid, function(err, time) {
                if(err == null) {
                    console.log(uid + ' :  2');
                    ret = {
                        'ret' : 'ok',
                        'uid' : uid
                    };
                    console.log(uid + ' : ' + time + ' login');
                    callback(ret);
                } else {
                    ret = {
                        'ret' : err
                    };
                    callback(ret);
                }
            });
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
};

module.exports = app;