var ventanaVisibleContacto = false;
var ventanaVisibleQuienesSomos = false;

function alternarDatosContacto() { //Funcion del encabezado para mostrar los datos de la empresa
  var ventanaEmergenteContacto = document.getElementById("ventanaEmergente");
  var ventanaEmergenteQuienesSomos = document.getElementById("ventanaEmergente2");
  
  ventanaVisibleContacto = !ventanaVisibleContacto;

  if (ventanaVisibleContacto) {
    ventanaEmergenteContacto.style.display = "block";
    ventanaEmergenteQuienesSomos.style.display = "none";
  } else {
    ventanaEmergenteContacto.style.display = "none";
  }
}

function alternarDatosQuienesSomos() { //Funcion del encabezado para mostrar un breve descripción
  var ventanaEmergenteContacto = document.getElementById("ventanaEmergente");
  var ventanaEmergenteQuienesSomos = document.getElementById("ventanaEmergente2");
  
  ventanaVisibleQuienesSomos = !ventanaVisibleQuienesSomos;

  if (ventanaVisibleQuienesSomos) {
    ventanaEmergenteQuienesSomos.style.display = "block";
    ventanaEmergenteContacto.style.display = "none";
  } else {
    ventanaEmergenteQuienesSomos.style.display = "none";
  }
}


  function mostrarInformacionEmpresa() { //Boton de info de la empresa (FormularioHome)
    var botonEmpresa = document.getElementById("boton_empresa");
    botonEmpresa.disabled = true;
  
    var imagenes = [
      "tick.png",
      "QueOfrecemos.png",
      "contento.png",
      "pila-de-monedas.png"
    ];
  
    var imagenesContainer = document.getElementById("imagenes_container");
   
    
    // Eliminar el contenido anterior del contenedor de imágenes
    imagenesContainer.innerHTML = '';
  
    for (var i = 0; i < imagenes.length; i++) {
      var imagen = document.createElement("img");
      imagen.src = imagenes[i];
      imagen.className = "minilogo";
      
      imagenesContainer.appendChild(imagen);
    }
  
    textoEmpresa.style.display = "block";

    
  }


function aceptarCookies() { //Ventana q aparecera siempre q entremos a la pagina web para aceptar la politica de cookies.(FormularioHome)
  document.getElementById("cookie-message").style.display = "none";
}




