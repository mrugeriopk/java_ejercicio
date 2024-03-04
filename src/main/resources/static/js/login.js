async function iniciarSesion (){
  let datos ={};
  datos.login=document.getElementById('login').value;
  datos.pass=document.getElementById('pass').value;
  console.log(datos);
  const request = await fetch('/login',{
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body : JSON.stringify(datos)

  });

  console.log(request);
  const respuesta = await request.text();
  if(respuesta!=null && respuesta.length > 0){
    localStorage.token = respuesta;
    localStorage.user = datos.login;
    alert("Inicio exitoso");
    window.location.href = "tables.html";
  }else{
    alert('contraseña o usuario incorrecto');
  }

}
