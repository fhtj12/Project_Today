var express = require('express');

var hash_func = require('../function/hash');
var db_search = require('../model/search');

var error = require('../exception/error').errors;

var app = express();

app.search = function(req, callback) {
    hash_func.collect_hash(req, function(err) {
        if(err == null) {
            console.log('complete');
        } else {
            console.log(err); // 검색어 수집 완료 여부는 클라이언트에게 안 보여줘도 됨.
        }
    });
    hash_func.parse_hash(req.query.search, function(err, hash) {
        if(err == null) {
            db_search.search_db(hash, function(err, result) {
                if(err == null) {
                    console.log(result);
                    ret = {
                        'ret' : 'ok',
                        'result' : result
                    };
                    callback(ret);
                } else {
                    console.log(err);
                    ret = {
                        'ret' : err
                    };
                    callback(ret);
                }
            });
        } else {
            console.log(err);
            ret = {
                 'ret' : err
            };
            callback(ret);
        }
    });
};

module.exports = app;