package pe.edu.upeu.tresraya.servicio;

import org.springframework.stereotype.Service;
import pe.edu.upeu.tresraya.model.tresrayaTO;

import java.util.List;
import java.util.logging.Logger;

@Service
public class tresrayaServicioImp implements tresrayaServicioI {
    private static final Logger logger = Logger.getLogger(tresrayaServicioImp.class.getName());

    @Override
    public void save(tresrayaTO to) {
        logger.info("Guardando partida: " + to.getNombrePartida());
    }

    @Override
    public List<tresrayaTO> findAll() {
        logger.info("Obteniendo todas las partidas");
        return List.of();
    }

    @Override
    public tresrayaTO findById(int index) {
        logger.info("Buscando partida con ID: " + index);
        return null;
    }

    @Override
    public void update(tresrayaTO to, int index) {
        logger.info("Actualizando partida con ID: " + index);
    }

    @Override
    public void delete(tresrayaTO to) {
        logger.info("Eliminando partida: " + to.getNombrePartida());
    }

    @Override
    public void deleteById(int index) {
        logger.info("Eliminando partida con ID: " + index);
    }
}
