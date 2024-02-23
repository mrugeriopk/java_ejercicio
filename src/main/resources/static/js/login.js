async function iniciarSesion (){
  let datos ={};
  datos.login=document.getElementById('login').value;
  datos.pass=document.getElementById('pass').value;
  const request = await fetch('/login',{
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body : JSON.stringify(datos)

  });
  const response = await request.json();
  console.log(response);
  if(response && response.length > 0){
    window.location.href = "tables.html";
  }else{
    alert('contraseña o usuario incorrecto');
  }

}