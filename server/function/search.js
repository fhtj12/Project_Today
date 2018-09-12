var express = require('express');
var mysql = require('mysql');

var hash_func = require('../function/hash');
var db_search = require('../model/search');

var error = require('../exception/error').errors;

var app = express();

app.search = function(req, callback) {
    hash_func.collect_hash(req, function(err) {
        if(err == null) {
            console.log('hash tag collecting complete');
        } else {
            console.log(err); // 검색어 수집 완료 여부는 클라이언트에게 안 보여줘도 됨.
        }
    });
    hash_func.parse_hash(req.query.search, function(err, hash) {
        if(err == null) {
            app.parse_search_to_query(hash, function(err, query) {
                if(err == null) {
                    db_search.search_db(query, function(err, result) {
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
        } else {
            console.log(err);
            ret = {
                 'ret' : err
            };
            callback(ret);
        }
    });
};

// 검색가능한 query로 파싱(실제 기능 구현 상에서는 다른 테이블임. 지금은 임시.)
app.parse_search_to_query = function(hash, callback) {
    var word = hash.split('#');
    var hash_string;
    var query;
    if(word != null || word != undefined) {
        query = 'SELECT * FROM hash_tag WHERE hash_string LIKE ';
        hash_string = '';
        for(var i = 0; i < word.length; i++) {
            hash_string += word[i];
            hash_string += '%';
        }
        query += mysql.escape(hash_string);
        console.log(query + '    ' + hash_string);
        callback(null, query);
    } else {
        err = error.parse_error;
        console.log(err);
        callback(err);
    }
};

module.exports = app;