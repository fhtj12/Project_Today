// 검색 요청시 검색어 저장에 동의 했는지 여부와 동의 요청에는 동의했다고 DB에 저장을 처리하며, 이를 검사한 후에는 검색어를 DB에 저장한다.

var express = require('express');

var db_hash_func = require('../model/hash');

var error = require('../exception/error').errors;

var app = express();

// 해시 태그를 DB에 저장하는 목적을 가진 함수(권한검사, 해시태그 파싱, DB함수 호출)
app.collect_hash = function(req, callback) {
    var ret = null;
    var uid = req.query.uid;
    db_hash_func.confirm_permission(uid, function(err, permission) {
        if(err != null) {
            console.log(err);
            callback(err);
        } else {
            if(permission == 0) {
                err = error.permission_error;
                console.log(err);
                callback(err);
            } else {
                app.parse_hash(req.query.search, function(err, hash) {
                    if(err != null) {
                        console.log(err);
                        callback(err);
                    } else {
                        db_hash_func.collect_hash_db(hash, uid, function(err) {
                            if(err == null) {
                                console.log('hash collecting complete');
                                callback(null);
                            } else {
                                console.log(err);
                                callback(err);
                            }
                        });
                    }
                });
            }
        }
    });
};

// 검색어에서 해시 태그를 분리하는 함수
app.parse_hash = function(search, callback) {
    var hash_string;
    var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;

    try {
        search = search.replace(regExp, "");
        var array = search.split(' ');
        hash_string = '#' + array[0];
        for(var i = 1; i < array.length; i++) {
            hash_string += '#';
            hash_string += array[i];
        }
    } catch(exception) {
        err = error.processing_error;
        callback(err);
    }
    console.log(hash_string);
    callback(null, hash_string);
};

module.exports = app;