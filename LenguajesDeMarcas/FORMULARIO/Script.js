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
var textoImagenes = {
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

