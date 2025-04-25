package pe.edu.upeu.tresraya.control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.tresraya.model.tresrayaTO;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;

@Controller
public class tresrayaControl {

    String jugador1;
    String jugador2;
    int indexID = 1;
    boolean circulo = true; // Turno actual (true para "O", false para "X")

    @FXML
    Label label_turno; // Muestra el turno actual

    @FXML
    TableView<tresrayaTO> tableView; // Tabla de resultados
    private ObservableList<tresrayaTO> datos;

    @FXML
    TableColumn<tresrayaTO, String> partidox, jugador1x, jugador2x, ganadorx, puntuacionx, estadox;

    @FXML
    Button btn1_1, btn1_2, btn1_3, btn2_1, btn2_2, btn2_3, btn3_1, btn3_2, btn3_3;

    @FXML
    private TextField txt_jugador1, txt_jugador2; // Camp

    @FXML
    Label label_aviso; // Mensajes de aviso

    @FXML
    Button btn_iniciar;

    @FXML
    Label label_nombreJugador1, label_nombreJugador2;

    @FXML
    private void initialize() {
        resetButtons(); // Reinicia el tablero
        btn_iniciar.setDisable(false);

        datos = FXCollections.observableArrayList();
        tableView.setItems(datos); // Enlaza la tabla con los datos

        // Configura las columnas de la tabla
        partidox.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombrePartida())));
        jugador1x.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreJugador1()));
        jugador2x.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreJugador2()));
        ganadorx.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGanador()));
        puntuacionx.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPunto())));
        estadox.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado()));
    }

    private void iniciarJuego() {
        jugador1 = txt_jugador1.getText();
        jugador2 = txt_jugador2.getText();
        label_turno.setText(jugador1);

        // Actualiza los labels con los nombres de los jugadores
        label_nombreJugador1.setText("Jugador 1: " + jugador1);
        label_nombreJugador2.setText("Jugador 2: " + jugador2);

        habilitarBotones(true);
        btn_iniciar.setDisable(true);
    }

    private void resetButtons() {
        // Reinicia los textos y estilos de los botones del tablero
        btn1_1.setText("1");
        btn1_2.setText("2");
        btn1_3.setText("3");
        btn2_1.setText("4");
        btn2_2.setText("5");
        btn2_3.setText("6");
        btn3_1.setText("7");
        btn3_2.setText("8");
        btn3_3.setText("9");

        btn1_1.setStyle("-fx-text-fill: transparent;");
        btn1_2.setStyle("-fx-text-fill: transparent;");
        btn1_3.setStyle("-fx-text-fill: transparent;");
        btn2_1.setStyle("-fx-text-fill: transparent;");
        btn2_2.setStyle("-fx-text-fill: transparent;");
        btn2_3.setStyle("-fx-text-fill: transparent;");
        btn3_1.setStyle("-fx-text-fill: transparent;");
        btn3_2.setStyle("-fx-text-fill: transparent;");
        btn3_3.setStyle("-fx-text-fill: transparent;");

        habilitarBotones(false);
        btn_iniciar.setDisable(false);
        circulo = true; // Reinicia el turno
        label_turno.setText(""); // Limpia el turno
    }

    private void habilitarBotones(boolean habilitar) {
        // Habilita o deshabilita los botones del tablero
        btn1_1.setDisable(!habilitar);
        btn1_2.setDisable(!habilitar);
        btn1_3.setDisable(!habilitar);
        btn2_1.setDisable(!habilitar);
        btn2_2.setDisable(!habilitar);
        btn2_3.setDisable(!habilitar);
        btn3_1.setDisable(!habilitar);
        btn3_2.setDisable(!habilitar);
        btn3_3.setDisable(!habilitar);
    }

    private void actualizarTurno(Button boton) {
        // Actualiza el turno y el estado del botón presionado
        if (boton.getText().matches("\\d")) { // Verifica si el botón no ha sido usado
            boton.setText(circulo ? "O" : "X"); // Asigna "O" o "X" según el turno
            boton.setStyle("-fx-font-size: 36px; -fx-text-fill: " + (circulo ? "red" : "blue")); // Cambia el estilo del texto
            circulo = !circulo; // Cambia el turno
            label_turno.setText(label_turno.getText().equals(jugador1) ? jugador2 : jugador1); // Actualiza el turno
            verificarGanador(); // Verifica si hay un ganador o empate
        }
    }

    private void registrarResultado(String ganador, String estado) {
        // Registra el resultado de la partida en la tabla
        tresrayaTO resultado = new tresrayaTO();
        resultado.setNombrePartida("Partida " + indexID++);
        resultado.setNombreJugador1(jugador1);
        resultado.setNombreJugador2(jugador2);

        // Determina correctamente al ganador
        if (circulo) { // Si el turno cambió a "O", el jugador "X" ganó
            resultado.setGanador(jugador2);
        } else { // Si el turno cambió a "X", el jugador "O" ganó
            resultado.setGanador(jugador1);
        }

        if (ganador.equals("Empate")) { // En caso de empate
            resultado.setGanador("Empate");
        }

        resultado.setPunto(ganador.equals("Empate") ? 0 : 1);
        resultado.setEstado(estado);
        datos.add(resultado);
    }

    private void verificarGanador() {
        // Verifica si hay un ganador o empate
        if (esGanador(btn1_1, btn1_2, btn1_3) || esGanador(btn2_1, btn2_2, btn2_3) || esGanador(btn3_1, btn3_2, btn3_3) ||
            esGanador(btn1_1, btn2_1, btn3_1) || esGanador(btn1_2, btn2_2, btn3_2) || esGanador(btn1_3, btn2_3, btn3_3) ||
            esGanador(btn1_1, btn2_2, btn3_3) || esGanador(btn1_3, btn2_2, btn3_1)) {
            registrarResultado("Ganador", "Finalizado"); // Registra el ganador
            resetButtons();
        } else if (esEmpate()) { // Verifica si hay empate
            registrarResultado("Empate", "Finalizado"); // Registra el empate
            resetButtons();
        }
    }

    private boolean esGanador(Button b1, Button b2, Button b3) {
        // Verifica si tres botones tienen el mismo texto
        return b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText()) && !b1.getText().matches("\\d");
    }

    private boolean esEmpate() {
        // Verifica si todos los botones están ocupados y no hay ganador
        return !btn1_1.getText().matches("\\d") && !btn1_2.getText().matches("\\d") && !btn1_3.getText().matches("\\d") &&
               !btn2_1.getText().matches("\\d") && !btn2_2.getText().matches("\\d") && !btn2_3.getText().matches("\\d") &&
               !btn3_1.getText().matches("\\d") && !btn3_2.getText().matches("\\d") && !btn3_3.getText().matches("\\d");
    }

    @FXML
    private void controlClick(ActionEvent event) {
        // Maneja los clics en los botones
        Button boton = (Button) event.getSource();
        switch (boton.getId()) {
            case "btn_iniciar": // Inicia una nueva partida
                if (!txt_jugador1.getText().isEmpty() && !txt_jugador2.getText().isEmpty()) {
                    iniciarJuego();
                    label_aviso.setText("");
                    label_aviso.setStyle("-fx-background-color: transparent;");
                } else {
                    label_aviso.setText("INGRESAR JUGADORES");
                    label_aviso.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: red;");
                }
                break;
            case "btn_anular": // Anula la partida actual
                registrarResultado("Empate", "Anulado");
                resetButtons();
                break;
            default: // Actualiza el turno al presionar un botón del tablero
                actualizarTurno(boton);
                break;
        }
    }
}
