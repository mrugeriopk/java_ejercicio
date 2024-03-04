// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios( "T");
  $('#usuarios').DataTable();
});

async function cargarUsuarios(opcion) {

  const request = await fetch('/lista/'+opcion, {
    method: 'GET',
    headers: getHeards()
  });
  const usuarios = await request.json();
  console.log(usuarios);
  let listadoHtml = '<tr> <td></td><td></td><td></td><td></td><td></td></tr>';
  if(usuarios!=null){
      for (let usuario of usuarios) {
          let nombre=usuario.nombre;
          if(usuario.apellidop!=null){nombre=nombre+' '+usuario.apellidop;}
          if(usuario.apellidom!=null){nombre=nombre+' '+usuario.apellidom;}
          let botonEliminar = '<a href="#" onclick="eliminarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-danger btn-circle btn-sm" ><i class="fas fa-trash"></i></a>';
          let botonEdicion = '<a href="#" onclick="buscarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-success btn-circle btn-sm " data-toggle="modal" data-target="#myModal1"><i class="fas fa-info"></i> </a>';
          let usuarioHtml = '<tr><td>' + nombre + '</td><td>'+usuario.login+'</td><td>' + usuario.fechaalta + '</td><td>' + usuario.status + '</td><td>' + botonEdicion+botonEliminar + '</td></tr>';
          listadoHtml += usuarioHtml;
      }
  }
  document.querySelector("#usuarios tbody").outerHTML=listadoHtml;
}
async function eliminarUsuario (login){
  if(!confirm('¿Desea eliminar este Usuario?')){
    return;
  }
  const request = await fetch('/baja/'+login, {
    method: 'PATCH',
    headers: getHeards()
  });
  cargarUsuarios("T");
}

async function agregaUsuarios(){
  let datos={};
  datos.login=document.getElementById('usuario').value;
  datos.email=document.getElementById('email').value;
  datos.password=document.getElementById('contra').value;
  datos.nombre=document.getElementById('nombre').value;
  datos.apellidom=document.getElementById('apellidom').value;
  datos.apellidop=document.getElementById('apellidop').value;
  const request = await fetch('/alta', {
    method: 'POST',
    headers: getHeards(),
    body: JSON.stringify(datos)
  });
  $('#myModal').modal('hide');
  cargarUsuarios("T");

}
async function buscarUsuario (login){
  const request = await fetch('/busca/'+login, {
    method: 'GET',
    headers: getHeards()
  });
    const usuario = await request.json();
    document.getElementById('nombre1').value=usuario.nombre;
    document.getElementById('correo').value=usuario.email;
    document.getElementById('apellidop1').value=usuario.apellidop;
    document.getElementById('apellidom1').value=usuario.apellidom;
    document.getElementById('idlogin').value=login;

}

async function editarUsuario (){
    let datos={};
    let login=document.getElementById('idlogin').value;
    datos.login=login;
    datos.password=document.getElementById('password').value;
    datos.nombre=document.getElementById('nombre1').value;
    console.log(datos);
    if(datos.password!=null && datos.nombre!=null){
        datos.apellidom=document.getElementById('apellidom1').value;
        datos.apellidop=document.getElementById('apellidop1').value;
        datos.email=document.getElementById('correo').value;
        const request = await fetch('/actualiza', {
            method: 'POST',
            headers: getHeards(),
            body: JSON.stringify(datos)
        });
        $('#myModal1').modal('hide');
        cargarUsuarios("T");
    }else{
        alert('la contraseña,nombre no puede estar vacios');
    }

}
async function buscarnombre(){
    let datos={};
    datos.nombre=document.getElementById('nombreb').value;
    datos.faltaIni=null;
    datos.faltaFin=null;
    const request = await fetch('/filtros', {
        method: 'POST',
        headers: getHeards(),
        body: JSON.stringify(datos)
    });
    const usuarios = await request.json();
      console.log(usuarios);
      let listadoHtml = '<tr> <td></td><td></td><td></td><td></td><td></td></tr>';
      if(usuarios!=null){
          for (let usuario of usuarios) {
              let nombre=usuario.nombre;
              if(usuario.apellidop!=null){nombre=nombre+' '+usuario.apellidop;}
              if(usuario.apellidom!=null){nombre=nombre+' '+usuario.apellidom;}
              let botonEliminar = '<a href="#" onclick="eliminarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-danger btn-circle btn-sm" ><i class="fas fa-trash"></i></a>';
              let botonEdicion = '<a href="#" onclick="buscarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-success btn-circle btn-sm " data-toggle="modal" data-target="#myModal1"><i class="fas fa-info"></i> </a>';
              let usuarioHtml = '<tr><td>' + nombre + '</td><td>'+usuario.login+'</td><td>' + usuario.fechaalta + '</td><td>' + usuario.status + '</td><td>' + botonEdicion+botonEliminar + '</td></tr>';
              listadoHtml += usuarioHtml;
          }
      }
      document.querySelector("#usuarios tbody").outerHTML=listadoHtml;
}
async function buscarfecha(){
    let datos={};
    datos.nombre=null;
    datos.faltaIni=document.getElementById('faltaIni').value;
    datos.faltaFin=document.getElementById('faltaFin').value;
    console.log(datos.faltaIni);
    console.log(datos.faltaFin);
    const request = await fetch('/filtros', {
        method: 'POST',
        headers: getHeards(),
        body: JSON.stringify(datos)
    });
    const usuarios = await request.json();
      console.log(usuarios);
      let listadoHtml = '<tr> <td></td><td></td><td></td><td></td><td></td></tr>';
      if(usuarios!=null){
          for (let usuario of usuarios) {
              let nombre=usuario.nombre;
              if(usuario.apellidop!=null){nombre=nombre+' '+usuario.apellidop;}
              if(usuario.apellidom!=null){nombre=nombre+' '+usuario.apellidom;}
              let botonEliminar = '<a href="#" onclick="eliminarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-danger btn-circle btn-sm" ><i class="fas fa-trash"></i></a>';
              let botonEdicion = '<a href="#" onclick="buscarUsuario('+"'"+ usuario.login +"'"+ ')" class="btn btn-success btn-circle btn-sm " data-toggle="modal" data-target="#myModal1"><i class="fas fa-info"></i> </a>';
              let usuarioHtml = '<tr><td>' + nombre + '</td><td>'+usuario.login+'</td><td>' + usuario.fechaalta + '</td><td>' + usuario.status + '</td><td>' + botonEdicion+botonEliminar + '</td></tr>';
              listadoHtml += usuarioHtml;
          }
      }
      document.querySelector("#usuarios tbody").outerHTML=listadoHtml;
}
function getHeards(){
    return{
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}