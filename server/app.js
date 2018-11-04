var express = require('express');
var path = require('path');

var app = express();

// API
var login = require('./function/login');
var account = require('./function/account');
var search = require('./function/search');
var location = require('./function/location');
var test_connect = require('./model/db_connection');

// 로그인
app.get('/login', function (req , res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    login.login(req,
    function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 회원가입
app.get('/create_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.create_account(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 아이디 중복 검사
app.get('/duplicate_id', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.duplicate_id(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 회원정보 수정
app.get('/update_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.update_account(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 회원정보 삭제
app.get('/delete_account', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.delete_account(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 비밀번호 변경
app.get('/update_pwd', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.update_pwd(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 검색
app.get('/search', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    search.search(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 위치정보수집
app.get('/gps', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    location.collect_gps(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 아이디 찾기
app.get('/find_id', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.find_id(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

// 비밀번호 찾기
app.get('/find_pwd', function(req, res) {
    console.log("client ip : " + req.ip + " / request path : " + req.path);
    account.find_pwd(req, function(ret) {
        res.send(ret);
        console.log(ret);
    });
});

app.listen(9503, function () {
    test_connect.connecting_test;
    console.log('app listening on port 9503.');
});

module.exports = app;
