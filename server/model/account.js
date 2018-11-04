var express = require('express');
var mysql = require('mysql');

var db_conn = require('../model/db_connection');
var common_func = require('../function/common_func');
var db_pool = mysql.createPool(db_conn);

var error = require('../exception/error').errors;

var app = express();

app.login_db = function(id, pwd, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection : ' + err);
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'SELECT * FROM account WHERE id = ' + mysql.escape(id);
            conn.query(query, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    if(result.length == 0) {
                        err = error.login.id_not_found;
                        conn.release();
                        return callback(err);
                    } else {
                        if(mysql.escape(pwd) == mysql.escape(result[0].pwd)) {
                            conn.release();
                            return callback(null, result[0].uid);
                        } else {
                            err = error.login.invalid_pwd;
                            conn.release();
                            return callback(err);
                        }
                    }
                }
            });
        }
    });
};

app.create_account_db = function(req, uid, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var id = req.query.id; var pwd = req.query.pwd; var address = req.query.address; 
            var email = req.query.email; var birth = req.query.birth;
            var params = [id, mysql.escape(pwd), mysql.escape(uid), mysql.escape(address), mysql.escape(email), mysql.escape(birth)];
            var query = 'INSERT INTO account (id, pwd, uid, address, email, birth) VALUES (?, ?, ?, ?, ?, ?)';

            conn.beginTransaction(function(err) {
                if(err) {
                    console.log('Failed begin Transaction');
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.query(query, params, function(err, rows, fields) {
                        if(err) {
                            console.log('Failed create a Query');
                            conn.rollback(function() {
                                err = error.mysql_db_error;
                                return callback(err);
                            });
                        } else {
                            conn.commit(function(err) {
                                if(err) {
                                    console.log('Failed commit a Query');
                                    conn.rollback(function() {
                                        err = error.mysql_db_error;
                                        return callback(err);
                                    });
                                } else {
                                    return callback(null);
                                }
                            });
                        }
                    });
                }
            });
        }
    });
};

app.update_account_db = function(user, uid, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var update_query = 'UPDATE account SET address=?, email=? WHERE uid=?';
            var param = [mysql.escape(user.query.address), mysql.escape(user.query.email), uid];
            conn.query(update_query, param, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.release();
                    return callback(null);
                }
            });
        }
    });
};

app.delete_account_db = function(uid, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'DELETE FROM account WHERE uid = ' + mysql.escape(uid);
            conn.query(query, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.release();
                    return callback(null);
                }
            });
        }
    });
};

app.update_pwd_db = function(uid, pwd, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var update_query = 'UPDATE account SET pwd=? WHERE uid=?';
            var param = [mysql.escape(pwd), uid];
            conn.query(update_query, param, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.release();
                    return callback(null);
                }
            });
        }
    });
};

app.update_login_time = function(uid, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var update_query = 'UPDATE account SET last_login=? WHERE uid=?';
            var time = null;
            common_func.getTime(function(result) {
                time = result;
            });
            var param = [time, uid];
            conn.query(update_query, param, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    conn.release();
                    return callback(null, time);
                }
            });
        }
    });
};

// 아이디 찾기
app.find_id_db = function(address, birth, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'SELECT * FROM account WHERE address=? AND birth=?';
            var param = [address, birth];
            conn.query(query, param, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    if(result.length == 0) {
                        err = error.find.id_not_found;
                        conn.release();
                        return callback(err, null);
                    } else {
                        conn.release();
                        return callback(null, result[0].id);
                    }
                }
            });
        }
    });
}

// 비밀번호 찾기
app.find_pwd_db = function(id, address, birth, callback) {
    db_pool.getConnection(function(err, conn) {
        if(err) {
            console.log('Failed getConnection');
            err = error.mysql_db_error;
            return callback(err);
        } else {
            var query = 'SELECT * FROM account WHERE id=? AND address=? AND birth=?';
            var param = [id, address, birth];
            conn.query(query, param, function(err, result, fields) {
                if(err) {
                    console.log('Failed create a Query');
                    conn.release();
                    err = error.mysql_db_error;
                    return callback(err);
                } else {
                    if(result.length == 0) {
                        err = error.find.id_not_found;
                        conn.release();
                        return callback(err);
                    } else {
                        conn.release();
                        return callback(null);
                    }
                }
            });
        }
    });
}

module.exports = app;
