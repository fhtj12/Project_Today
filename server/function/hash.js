// 검색 요청시 검색어 저장에 동의 했는지 여부와 동의 요청에는 동의했다고 DB에 저장을 처리하며, 이를 검사한 후에는 검색어를 DB에 저장한다.

var express = require('express');

var db_hash_func = require('../model/hash');

var app = express();

