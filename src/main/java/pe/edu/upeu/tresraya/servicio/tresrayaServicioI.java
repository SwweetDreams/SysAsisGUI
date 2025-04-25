package pe.edu.upeu.tresraya.servicio;

import pe.edu.upeu.tresraya.model.tresrayaTO;

import java.util.List;

public interface tresrayaServicioI {

    //C = CREATE (CREAR)
    public void save(tresrayaTO to);

    //R = READ (LEER)
    public List<tresrayaTO> findAll();
    public tresrayaTO findById(int index);

    //U = UPDATE (ACTUALIZAR)
    public void update(tresrayaTO to, int index);

    //D = DELETE (ELIMINAR)
    public void delete(tresrayaTO to);
    public void deleteById(int index);

}
