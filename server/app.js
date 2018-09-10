var express = require('express');
var path = require('path');

var app = express();

// API
var login = require('./function/login');
var account = require('./function/account');

// 로그인
app.get('/login', function (req , res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    login.login(req,
    function(ret) {
        res.send(ret);
    });
});

// 회원가입
app.get('/create_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.create_account(req, function(ret) {
        res.send(ret);
    });
});

// 아이디 중복 검사
app.get('/find_id', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.find_id(req, function(ret) {
        res.send(ret);
    });
});

// 회원정보 수정
app.get('/update_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.update_account(req, function(ret) {
        res.send(ret);
    });
});

// 회원정보 삭제
app.get('/delete_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.delete_account(req, function(ret) {
        res.send(ret);
    });
});

// 비밀번호 변경
app.get('/update_pwd', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.update_pwd(req, function(ret) {
        res.send(ret);
    });
});

app.listen(8080, function () {
    console.log('app listening on port 8080.');
});

module.exports = app;