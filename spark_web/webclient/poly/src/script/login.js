// var token = localStorage.getItem('token');
var token = sessionStorage.getItem('token');
if(!token){
  window.location = "http://localhost:8080/";
}
