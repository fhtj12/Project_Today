var express = require('express');

var db_location = require('../model/location');
var common_func = require('../function/common_func');

var error = require('../exception/error').errors;

var app = express();

// 위치정보 수집 허가 여부
app.confirm_permission = function(uid, callback) {
    db_location.confirm_permission_db(uid, function(err, result) {
        if(err == null) {
            if(result == 1) {
                callback(null);
            } else {
                err = error.permission_error;
                callback(err);
            }
        } else {
            callback(err);
        }
    });
};

// 위치정보 수집
app.collect_gps = function(req, callback) {
    var uid = req.query.uid;
    var longitude = req.query.longitude;
    var latitude = req.query.latitude;
    var altitude = req.query.altitude;
    var accuracy = req.query.accuracy;
    var ret;
    app.confirm_permission(uid, function(err) {
        if(err == null) {
            var param = [uid, longitude, latitude, altitude, accuracy];
            db_location.collect_gps_db(param, function(err) {
                if(err == null) {
                    ret = {
                        'ret' : 'ok'
                    };
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