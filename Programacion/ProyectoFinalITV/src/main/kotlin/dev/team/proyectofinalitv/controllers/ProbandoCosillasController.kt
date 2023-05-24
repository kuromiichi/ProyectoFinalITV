package dev.team.proyectofinalitv.controllers

import dev.team.proyectofinalitv.viewmodels.CitaViewModel
import javafx.fxml.FXML
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProbandoCosillasController : KoinComponent {

    private val logger = KotlinLogging.logger {}

    // Datos del estado que serán compartidos entre el ViewModel y el controlador interfaz
    private val citaViewModel: CitaViewModel by inject()

    @FXML
    fun initialize() {
        logger.debug { "Inicializando..." }
    }

    fun onSaveCita() {
        citaViewModel.saveCita()
    }

}
