var ventanaVisibleContacto = false;
var ventanaVisibleQuienesSomos = false;

function alternarDatosContacto() {
  var ventanaEmergenteContacto = document.getElementById("ventanaEmergente");
  var ventanaEmergenteQuienesSomos = document.getElementById("ventanaEmergente2");
  var ventanaEmergenteServicios = document.getElementById("ventanaEmergente3");

  ventanaVisibleContacto = !ventanaVisibleContacto;
  ventanaVisibleQuienesSomos = false; // Se cierra la ventana de Quienes Somos
  ventanaVisibleServicios = false; // Se cierra la ventana de Servicios

  if (ventanaVisibleContacto) {
    ventanaEmergenteContacto.style.display = "block";
    ventanaEmergenteQuienesSomos.style.display = "none";
    ventanaEmergenteServicios.style.display = "none";
  } else {
    ventanaEmergenteContacto.style.display = "none";
  }
}

function alternarDatosQuienesSomos() {
  var ventanaEmergenteContacto = document.getElementById("ventanaEmergente");
  var ventanaEmergenteQuienesSomos = document.getElementById("ventanaEmergente2");
  var ventanaEmergenteServicios = document.getElementById("ventanaEmergente3");

  ventanaVisibleQuienesSomos = !ventanaVisibleQuienesSomos;
  ventanaVisibleContacto = false; // Se cierra la ventana de Contacto
  ventanaVisibleServicios = false; // Se cierra la ventana de Servicios

  if (ventanaVisibleQuienesSomos) {
    ventanaEmergenteQuienesSomos.style.display = "block";
    ventanaEmergenteContacto.style.display = "none";
    ventanaEmergenteServicios.style.display = "none";
  } else {
    ventanaEmergenteQuienesSomos.style.display = "none";
  }
}

function alternarDatosServicios() {
  var ventanaEmergenteContacto = document.getElementById("ventanaEmergente");
  var ventanaEmergenteQuienesSomos = document.getElementById("ventanaEmergente2");
  var ventanaEmergenteServicios = document.getElementById("ventanaEmergente3");

  ventanaVisibleServicios = !ventanaVisibleServicios;
  ventanaVisibleContacto = false; // Se cierra la ventana de Contacto
  ventanaVisibleQuienesSomos = false; // Se cierra la ventana de Quienes Somos

  if (ventanaVisibleServicios) {
    ventanaEmergenteServicios.style.display = "block";
    ventanaEmergenteContacto.style.display = "none";
    ventanaEmergenteQuienesSomos.style.display = "none";
  } else {
    ventanaEmergenteServicios.style.display = "none";
  }
}

var textoImagenes = { //Funcion del home para q salga texto bajo la imagen despues de pinchar en ella
  "Imagen 1": "¿Por qué deberían elegirnos? En nuestra empresa de ITV, nos destacamos por brindar un servicio excepcional y de calidad a nuestros clientes. Contamos con un equipo de profesionales altamente capacitados y apasionados por la seguridad vehicular. Nuestra experiencia y conocimiento nos permiten realizar inspecciones minuciosas y precisas, garantizando que su vehículo cumpla con los estándares de seguridad exigidos. Además, nos esforzamos por ofrecer una atención al cliente personalizada, escuchando sus necesidades y brindando asesoramiento profesional.",
  "Imagen 2": "¿Qué ofrecemos? En nuestra empresa de ITV, ofrecemos una amplia gama de servicios para garantizar la seguridad y el correcto funcionamiento de su vehículo. Realizamos inspecciones técnicas exhaustivas que abarcan aspectos como los sistemas de frenos, iluminación, emisiones, neumáticos, dirección y más. Nuestro objetivo es asegurarnos de que su vehículo cumpla con los requisitos legales y garantizar su seguridad en las carreteras.",
  "Imagen 3": "El % de gente que sale satisfecha.Nos enorgullece decir que un alto porcentaje de nuestros clientes sale satisfecho de nuestras instalaciones después de realizar la inspección técnica de su vehículo. Nuestro compromiso con la calidad y la excelencia en el servicio nos ha permitido ganarnos la confianza y satisfacción de numerosos conductores. Valoramos las opiniones de nuestros clientes y siempre buscamos superar sus expectativas.",
  "Imagen 4": "En cuanto al precio de nuestras inspecciones técnicas, ofrecemos una excelente relación calidad-precio. Nuestros precios suelen ser competitivos y justos, teniendo en cuenta la calidad y precisión de nuestros servicios. Cabe mencionar que el precio puede variar según el tipo de vehículo y la región en la que se realice la inspección. Siempre nos esforzamos por ofrecer una tarifa transparente y acorde a las necesidades de nuestros clientes."
};

function toggleTexto(imagen) {
  var textoContenedor = document.getElementById('texto_contenedor');
  var texto = textoImagenes[imagen.alt];
  
  if (textoContenedor.style.display === 'block' && textoContenedor.innerText === texto) {
    // Si el texto ya está mostrado y es el mismo que se hizo clic, se oculta
    
    textoContenedor.style.display = 'none';
  } else {
    // Si el texto está oculto o es de otra imagen, se muestra y se actualiza el texto
    textoContenedor.innerText = texto;
    textoContenedor.style.display = 'block';
    
  }
}
function guardarMatricula() { //Funcion del home para almacenar la matricula en un formato valido
  var matriculaInput = document.getElementById('input_cita');
  var matricula = matriculaInput.value.trim(); // Obtener el valor ingresado y eliminar espacios en blanco

  var formatoValido = /^[0-9]{4}[A-Z]{3}$/; // Expresión regular para el formato 1111AAA

  if (formatoValido.test(matricula)) {
    // El valor ingresado cumple con el formato deseado
    localStorage.setItem('matricula', matricula); // Almacenar la matrícula en localStorage
    window.location.href = 'Formulario.html'; // Redirigir a Formulario2
  } else {
    // El valor ingresado no cumple con el formato deseado
    alert('La matrícula debe tener el formato 1111AAA');
  }
}

window.onload = function() { //Funcion para obtener la matricula y ponerla en el apartado de matricula
  var matriculaInput = document.getElementById('Matricula');
  var matricula = localStorage.getItem('matricula'); // Obtener la matrícula almacenada en localStorage

  if (matricula) {
    matriculaInput.value = matricula; // Establecer el valor de la matrícula en el campo de entrada
  }
};

//Validar primer formulario
function validarFormulario() {
  var dniInput = document.getElementById('DNI');
  var nombreInput = document.getElementById('nombre');
  var apellidosInput = document.getElementById('apellidos');
  var correoInput = document.getElementById('correo');
  var telefonoInput = document.getElementById('telefono');

  var formatoDNI = /^\d{8}[A-Z]$/; // Expresión regular para el formato 12345678A
  var formatoNombre = /^[A-Za-z\s]+$/; // Expresión regular para solo letras y espacios
  var formatoApellido = /^[A-Za-z\s]+$/; // Expresión regular para solo letras y espacios
  var formatoCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Expresión regular para formato de correo electrónico
  var formatoTelefono = /^\d{9}$/; // Expresión regular para 9 números

  if (!formatoDNI.test(dniInput.value.trim())) {
    alert('El DNI debe tener el formato 12345678A');
    return false;
  }

  if (!formatoNombre.test(nombreInput.value.trim())) {
    alert('El nombre solo puede contener letras y espacios');
    return false;
  }

  if (!formatoApellido.test(apellidosInput.value.trim())) {
    alert('Los apellidos solo pueden contener letras y espacios');
    return false;
  }

  if (!formatoCorreo.test(correoInput.value.trim())) {
    alert('El correo electrónico debe tener un formato válido');
    return false;
  }

  if (!formatoTelefono.test(telefonoInput.value.trim())) {
    alert('El teléfono debe tener 9 números');
    return false;
  }
  var tipoVehiculoInput = document.getElementById('tipo_vehiculo');
  if (tipoVehiculoInput.value === '') {
    alert('Debe seleccionar un tipo de vehículo');
    return false;
  }

  // Redirección al formulario2.html
  window.location.href = 'formulario2.html';
  return false; // Evita que el formulario se envíe automáticamente
}

function validarFormulario2() {
  var marcaInput = document.getElementById('Marca');
  var modeloInput = document.getElementById('Modelo');
  var matriculaInput = document.getElementById('Matricula');
  var tipoMotorInput = document.getElementById('tipo_vehiculo');
  var fechaRevisionInput = document.getElementById('FechaRevision');
  var fechaUltimaRevisionInput = document.getElementById('FechaUltimaRevision');

  var formatoMarca = /^[A-Za-z\s]+$/; // Expresión regular para solo letras y espacios
  var formatoModelo = /^[A-Za-z0-9\s]+$/; // Expresión regular para letras y números
  var formatoMatricula = /^\d{4}[A-Za-z]{3}$/; // Expresión regular para el formato 1111AAA

  if (!formatoMarca.test(marcaInput.value.trim())) {
    alert('La marca solo puede contener letras y espacios');
    return false;
  }

  if (!formatoModelo.test(modeloInput.value.trim())) {
    alert('El modelo solo puede contener letras y números');
    return false;
  }

  if (!formatoMatricula.test(matriculaInput.value.trim())) {
    alert('La matrícula debe tener el formato 1111AAA');
    return false;
  }

  if (tipoMotorInput.value === '') {
    alert('Debe seleccionar un tipo de motor');
    return false;
  }

  var fechaRevision = new Date(fechaRevisionInput.value);
  var fechaActual = new Date();
  if (fechaRevision <= fechaActual) {
    alert('La fecha de revisión debe ser posterior a la fecha actual');
    return false;
  }

  var fechaUltimaRevision = new Date(fechaUltimaRevisionInput.value);
  if (fechaUltimaRevision >= fechaActual) {
    alert('La fecha de última revisión debe ser anterior a la fecha actual');
    return false;
  }

  // Redirección al formulario3.html
  window.location.href = 'Reserva.html';
  return false; // Evita que el formulario se envíe automáticamente
}
