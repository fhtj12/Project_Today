// 예외처리
var express = require('express');
var path = require('path');
var app = express();

app.errors = {
    // common errors
    invalid_parameter : {
        'err' : 'invalid_parameter'
    },
    mysql_db_error : {
        'err' : 'mysql_db_error'
    },
    internal_error : {
        'err' : 'internal_error'
    },

    // account errors
    account : {
        duplicate : {
            'err' : 'duplicate'
        }
    },

    // login errors
    login : {
        id_not_found : {
            'err' : 'id_not_found'
        },
        invalid_pwd : {
            'err' : 'invalid_passowrd'
        },
        invalid_uid : {
            'err' : 'invalid_uid'
        },
        invalid_session : {
            'err' : 'invalid_session'
        },
        expired_session : {
            'err' : 'expired_session'
        }
    }
};

module.exports = app;