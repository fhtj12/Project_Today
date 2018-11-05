var express = require('express');
var hash = require('object-hash');

var db_func = require('../model/account');
var common_db = require('../model/common');

var app = express();

app.create_account = function(req, callback) {
    var id = req.query.id;
    var pwd = req.query.pwd;
    var uid = hash(id + pwd + new Date(), {algorithm : 'sha1'});

    common_db.duplicate_id_db(id, function(err) {
        if(err == null) {
            db_func.create_account_db(req, uid, function(err) {
                if(err == null) {
                    ret = {
                        'ret' : 'ok'
                    };
                } else {
                    ret = {
                        'ret' : err
                    };
                }
                callback(ret);
            });
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
};

app.duplicate_id = function(req, callback) {
    var id = req.query.id;
    common_db.duplicate_id_db(id, function(err) {
        if(err == null) {
            ret = {
                'ret' : 'ok'
            };
        } else {
            ret = {
                'ret' : err
            };
        }
        callback(ret);
    });
};

app.update_account = function(req, callback) {
    var uid = req.query.uid;
    var pwd = req.query.pwd;
    if(uid == null || uid == undefined) {
        callback(err.login.invalid_session);
    }
    common_db.confirm_uid_pwd(uid, pwd, function(err) {
        if(err == null) {
            db_func.update_account_db(req, uid, function(err) {
                if(err == null) {
                    ret = {
                        'ret' : 'ok'
                    };
                } else {
                    ret = {
                        'ret' : err
                    };
                }
                callback(ret);
            });
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
};

app.delete_account = function(req, callback) {
    var uid = req.query.uid;
    var pwd = req.query.pwd;
    if(uid == null || uid == undefined) {
        callback(err.login.invalid_session);
    }
    common_db.confirm_uid_pwd(uid, pwd, function(err) {
        if(err == null) {
            db_func.delete_account_db(uid, function(err) {
                if(err == null) {
                    ret = {
                        'ret' : 'ok'
                    };
                } else {
                    ret = {
                        'ret' : err
                    };
                }
                callback(ret);
            });
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
};

app.update_pwd = function(req, callback) {
    var uid = req.query.uid;
    var pwd = req.query.pwd;
    var new_pwd = req.query.new_pwd;
    if(uid == null || uid == undefined) {
        callback(err.login.invalid_session);
    }
    if(pwd == null || pwd == undefined) {
        callback(err.account.invalid_pwd);
    }

    common_db.confirm_uid_pwd(uid, pwd, function(err) {
        if(err == null) {   
            db_func.update_pwd_db(uid, new_pwd, function(err) {
                if(err == null) {
                    ret = {
                        'ret' : 'ok'
                    };
                    callback(ret);
                } else {
                    ret = {
                        'ret' : err
                    };
                    callback(ret);
                }
            });
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
};

// 아이디 찾기
app.find_id = function(req, callback) {
    var email = req.query.email;
    var birth = req.query.birth;

    if(email == null || email == undefined) {
        callback(err.find.invalid_parameter);
    }
    db_func.find_id_db(email, birth, function(err, id) {
        if(err == null) {
            ret = {
                'ret' : 'ok',
                'id' : id
            };
            callback(ret);
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
}

// 비밀번호 찾기
app.find_pwd = function(req, callback) {
    var id = req.query.id;
    var email = req.query.email;
    var birth = req.query.birth;

    if(id == null || id == undefined || email == null || email == undefined) {
        callback(err.find.invalid_parameter);
    }
    db_func.find_pwd_db(id, email, birth, function(err) {
        if(err == null) {
            ret = {
                'ret' : 'ok'
            };
            callback(ret);
        } else {
            ret = {
                'ret' : err
            };
            callback(ret);
        }
    });
}

module.exports = app;