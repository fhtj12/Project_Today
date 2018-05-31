var express = require('express'); // express(웹 프레임워크) 모듈 불러오기
var http = require('http'); // http 모듈(웹 서버 페이지 포맷에 http를 쓸 듯)
var path = require('path');
var mysql = require('mysql');
var async = require('bluebird');
var logger = require('logger').createLogger(); // logging 라이브러리

var app = express(); // express 모듈에 대한 기능 참조의 리턴을 변수 app에 저장

var account_ctl = require('../controller/account');
var errors = require('../errors').errors; // 상대경로로 에러 정의 파일 불러오기

app.get('/new', function(req, res) {
    var id = req.query.id;
    var pwd = req.query.pwd;
    if (id == null || pwd == null) {
        res.json(errors.invalid_parameter);
    } else {
        account_ctl.new_account(id, pwd, req, res, new_account);
    }
});

var new_account = function(err, ret, req, res) {
    if (err) {
        res.json(err);
        return;
    }
    logger.info('create new account: ' + req.query.id);
    res.json(ret);
};

app.use(function(err, req, res, next) {
    logger.error(err);
    res.status(500).send('internal server error');
});

module.exports = app;