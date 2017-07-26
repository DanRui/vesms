/**
 * @date 2017-07-24
 * @author DanRui
 * @see js获取cookie、设置cookie等操作
 */

/**
 * 设置COOKIE
 * @param name 设置cookie的属性名
 * @param value 设置cookie的属性值
 * @param time  设置cookie的时间
 */
 
function setCookie(name, value , time) {
  time = time ? parseFloat(time) : 0 ;
  var exp = new Date();
  exp.setTime(exp.getTime() + time);
  // escape 这种编码方式过时了 改用 encodeURIComponent
  // document.cookie = name + "=" + escape(value) + ";expires=" + (time ? exp.toGMTString() : 'session');
  document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + (time ? exp.toGMTString() : 'session');
}

/**
 * 获取cookie
 * @param name
 * @returns {null}
 */
 
function getCookie(name) {
  var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
  if (arr = document.cookie.match(reg))
    //unescape这种解码方式好像过时了，可以采用decodeURIComponent解码方式
    //return unescape(arr[2]);
     return decodeURIComponent(arr[2]);
  else
    return null;
}

/**
 * 删除cookie
 * @param name
 */
 
function delCookie(name) {
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval = getCookie(name);
  if (cval != null)
    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}


