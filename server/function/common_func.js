var express = require('express');

var error = require('../exception/error').errors;

var app = express();

// 현재시간구하기 + format
app.getTime = function(callback) {
    function getTimeStamp() {
        var d = new Date();
      
        var s =
          leadingZeros(d.getFullYear(), 4) + '-' +
          leadingZeros(d.getMonth() + 1, 2) + '-' +
          leadingZeros(d.getDate(), 2) + ' ' +
      
          leadingZeros(d.getHours(), 2) + ':' +
          leadingZeros(d.getMinutes(), 2) + ':' +
          leadingZeros(d.getSeconds(), 2);
      
        return s;
      }
      
      function leadingZeros(n, digits) {
        var zero = '';
        n = n.toString();
      
        if (n.length < digits) {
          for (i = 0; i < digits - n.length; i++)
            zero += '0';
        }
        return zero + n;
      }
      
      callback(getTimeStamp());
};

module.exports = app;