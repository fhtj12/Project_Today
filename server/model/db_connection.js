var mysql = require('mysql');

var connection = {
    host        :   'localhost',
    user        :   'root',
    password    :   'dlawls12',
    port        :   3306,
    database    :   'project_today',
    insecureAuth : true
};

var test_pool = mysql.createPool(connection);

var connecting_test = test_pool.getConnection(function(err, conn) {
	if(err) {
		console.log('mysql connect error : ' + err);
	}
	else {
		conn.query('select 1+1 AS solution', function(error, results) {
			if(error) {
				console.log('mysql query error : ' + error);
			}
			else {
				console.log('mysql load complete');
			}
		});
	}
});

module.exports = connection;
