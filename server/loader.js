// 서버 시작시 로드해야할 것들

var express = require('express');
var fs = require('fs');
var parse = require('csv-parse');
var transform = require('stream-transform');

var app = express;

var module_loaded = [];

// 데이터들
//app.character = {};
//app.stage = {};
//app.item = {};
//app.novice_equipment = {};
//app.novice_status = {};

app.load = function(callback) {
    // csv 경로
    var path = [
        'data/sample.csv'
    ];

    // transforms
    var transforms = [
        // 캐릭터
        transform(function(record, callback) { app.sample[record.id] = record; })
    ];

    for (var i = 0; i < path.length; i++) {
        module_loaded[path[i]] = false;
    }

    for (var i = 0; i < path.length; i++) {
        var input = fs.createReadStream(path[i]);
        var parser = parse({delimiter: ',', columns: true, auto_parse: true});
        input.pipe(parser).pipe(transforms[i]).on('finish', function() {
            console.log(path + ' ok.');
            input.close();
            module_loaded[i] = true;
            check_module_loaded(callback);
        });
    }
};

var check_module_loaded = function(callback) {
    for (var loaded in module_loaded) {
        if (!loaded) {
            return;
        }
    }
    console.log('module load ok.');
    callback();
}

module.exports = app;