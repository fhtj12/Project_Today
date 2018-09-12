var express = require('express');
var app = express();

app.errors = {
    // common errors
    invalid_parameter : {
        'err' : 'invalid_parameter'
    },
    mysql_db_error : {
        'err' : 'mysql_db_error',
    },
    internal_error : {
        'err' : 'internal_error'
    },
    permission_error : {
        'err' : 'permission_error'
    },
    processing_error : {
        'err' : 'processing_error'
    },

    // account errors
    account : {
        duplicate : {
            'err' : 'duplicate'
        },
        invalid_account : {
            'err' : 'invalid_account'
        },
        invalid_pwd : {
            'err' : 'invalid_password'
        },
        invalid_parameter : {
            'err' : 'invalid_parameter'
        }
    },

    // login errors
    login : {
        id_not_found : {
            'err' : 'id_not_found'
        },
        invalid_pwd : {
            'err' : 'invalid_password'
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