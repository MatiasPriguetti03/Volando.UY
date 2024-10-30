/*!
* Start Bootstrap - Modern Business v5.0.7 (https://startbootstrap.com/template-overviews/modern-business)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-modern-business/blob/master/LICENSE)
*/

// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
	'use strict'

	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	var forms = document.querySelectorAll('.needs-validation')

	// Loop over them and prevent submission
	Array.prototype.slice.call(forms)
		.forEach(function (form) {
			form.addEventListener('submit', function (event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
})()


try {
	// Alta Usuario
	const clienteOption = document.getElementById('cliRadio');
	const aerolineaOption = document.getElementById('aerRadio');
	const clienteDiv = document.getElementById('div-cliente');
	const aerolineaDiv = document.getElementById('div-aerolinea');
	const clienteInputs = document.querySelectorAll('.required-cliente');
	const aerolineaInputs = document.querySelectorAll('.required-aerolinea');
	// Función para mostrar u ocultar el inputs
	function toggleInputs() {
		if (clienteOption.checked) {
			clienteDiv.classList.remove('d-none');
			clienteInputs.forEach(input => {
				input.setAttribute('required', true);
			});
			aerolineaDiv.classList.add('d-none');
			aerolineaInputs.forEach(input => {
				input.removeAttribute('required');
			});
		} else if (aerolineaOption.checked) {
			aerolineaDiv.classList.remove('d-none');
			aerolineaInputs.forEach(input => {
				input.setAttribute('required', true);
			});
			clienteDiv.classList.add('d-none');
			clienteInputs.forEach(input => {
				input.removeAttribute('required');
			});
		}
	}

	// Agregar evento change a los radio buttons
	clienteOption.addEventListener('change', toggleInputs);
	aerolineaOption.addEventListener('change', toggleInputs);

	// Ejecutar la función al cargar la página para establecer el estado inicial
	toggleInputs();


	// Validar coincidencia contrasenias
	document.getElementById("alta-usuarioForm").addEventListener("submit", function (event) {
		const contrasenia = document.getElementById("password").value;
		const rptcontrasenia = document.getElementById("rptpassword").value;
		const errorMsg = document.getElementById("error-password");

		if (contrasenia !== rptcontrasenia) {
			// Mostrar mensaje de error si las contraseñas no coinciden
			errorMsg.classList.remove('d-none');
			event.preventDefault();  // Evita el envío del formulario
		} else {
			// Oculta el mensaje de error si las contraseñas coinciden
			errorMsg.classList.add('d-none');
		}
	});
}
catch {

}

try {
	// Reserva Vuelo
	document.getElementById("cant-pasajeros").addEventListener("input", agregarPasajeros);
	document.getElementById("cant-equipaje").addEventListener("input", actualizarCosto);
	document.getElementById("asiento-type").addEventListener("input", actualizarCosto);
	document.getElementById("paquete").addEventListener("input", actualizarCosto);

	function agregarPasajeros() {
		// Obtener el número de pasajeros
		const numPasajeros = document.getElementById("cant-pasajeros").value;

		// Limpiar cualquier formulario existente
		const pasajerosContainer = document.getElementById("pasajeros-container");
		pasajerosContainer.innerHTML = "";

		if (numPasajeros > 1)
			pasajerosContainer.innerHTML = `
				<h5>Acompañantes:</h5>
            `;

		// Crear un formulario para cada pasajero
		for (let i = 1; i <= numPasajeros - 1; i++) {
			// Crear div para cada pasajero
			const pasajeroDiv = document.createElement("div");
			pasajeroDiv.classList.add("row");

			pasajeroDiv.innerHTML = `
                	<div class="col-md-6 mt-1">
						<label for="acomp-nombre" class="form-label">Nombre:</label> <input type="text"
						class="form-control" id="nombre${i}" name="nombre${i}" required>
					</div>
					<div class="col-md-6 mt-1">
						<label for="acomp-apellido" class="form-label">Apellido:</label> <input type="text"
						class="form-control" id="apellido${i}" name="apellido${i}" required>
					</div>
                `;

			// Añadir cada div al contenedor principal
			pasajerosContainer.appendChild(pasajeroDiv);

		}

		actualizarCosto();
	}

	function actualizarCosto() {
		const tipoAsiento = document.getElementById("asiento-type").value;
		const paquete = document.getElementById("paquete").value;

		let descuentoPaquete = 0;
		if (paquete != "") {
			descuentoPaquete = parseInt(document.getElementById(`${paquete}-discount`).innerHTML);
		}

		const numPasajeros = document.getElementById("cant-pasajeros").value;
		const costoTurista = parseFloat(document.getElementById("cost-tur").innerHTML);
		const costoEjecutivo = parseFloat(document.getElementById("cost-eje").innerHTML);
		const costoExtra = parseFloat(document.getElementById("cost-ext").innerHTML);
		const cantExtra = parseInt(document.getElementById("cant-equipaje").value);

		if (tipoAsiento == "turista") {
			document.getElementById("cost").innerHTML = (costoTurista * numPasajeros * (1 - descuentoPaquete / 100) + costoExtra * cantExtra).toFixed(1);
		} else {
			document.getElementById("cost").innerHTML = (costoEjecutivo * numPasajeros * (1 - descuentoPaquete / 100) + costoExtra * cantExtra).toFixed(1);
		}
	}


	// Función de envío del formulario (puedes personalizarla para enviar los datos)
	//document.getElementById("submit-confirmar").addEventListener("click", function() {
	//	alert("Reserva enviada con éxito!");
	//});
}
catch {

}