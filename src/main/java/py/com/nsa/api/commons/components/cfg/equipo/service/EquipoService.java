package py.com.nsa.api.commons.components.cfg.equipo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;
import py.com.nsa.api.commons.components.cfg.equipo.repository.EquipoRepository;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    public Equipo.MensajeRespuesta getEquiposAll(String agCod) {
        try {
            List<Equipo> equipos;
            if (agCod == null || agCod.isEmpty()) {
                equipos = equipoRepository.findAll();
            } else {
                equipos = equipoRepository.getListaEquipos(agCod);
            }
            if (equipos.isEmpty()) {
                return new Equipo.MensajeRespuesta(200L, "No se encontraron equipos.", null);
            }
            return new Equipo.MensajeRespuesta(200L, "Equipos obtenidos exitosamente.", equipos);
        } catch (Exception e) {
            System.err.println("Error al obtener los Equipos: " + e.getMessage());
            e.printStackTrace();
            return new Equipo.MensajeRespuesta(500L, "Error al obtener los equipos: " + e.getMessage(), null);
        }
    }
    public Equipo.MensajeRespuesta insertarEquipo(Equipo equipo) {
        try {
            // Crear el objeto Empleado y asignar los datos necesarios
            Long nextequCodigo = equipoRepository.getNextCodEquipo();
            equipo.setEquCodigo(nextequCodigo);
            Equipo nuevoEquipo = equipoRepository.save(equipo);
            return new Equipo.MensajeRespuesta(200L, "Equipo creado exitosamente.", List.of(nuevoEquipo));
        } catch (Exception e) {
            return new Equipo.MensajeRespuesta(500L, "Error al insertar el equipo: " + e.getMessage(), null);
        }
    }

    public Equipo.MensajeRespuesta updateEquipo(Equipo equipo) {
        try {
            if (equipo.getEquCodigo() == null || !equipoRepository.existsById(equipo.getEquCodigo())) {
                return new Equipo.MensajeRespuesta(204L, "Equipo no encontrado.", null);
            }
            Equipo updatedEquipo = equipoRepository.save(equipo);
            return new Equipo.MensajeRespuesta(200L, "Equipo actualizado exitosamente.", List.of(updatedEquipo));
        } catch (Exception e) {
            return new Equipo.MensajeRespuesta(500L, "Error al actualizar el equipo: " + e.getMessage(), null);
        }
    }

    public Equipo.MensajeRespuesta deleteEquipo(Long codigo) {
        try {
            if (equipoRepository.existsById(codigo)) {
                equipoRepository.deleteById(codigo);
                return new Equipo.MensajeRespuesta(200L, "Equipo con código " + codigo + " eliminado exitosamente", null);
            } else {
                return new Equipo.MensajeRespuesta(204L, "Equipo con código " + codigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Equipo porque está referenciada por otros registros"; // Mensaje
            // personalizado
            return new Equipo.MensajeRespuesta(204L,
                    "Error al eliminar el Equipo: " + mensaje, null);
        } catch (Exception e) {
            return new Equipo.MensajeRespuesta(500L, "Error al eliminar el equipo con código " + codigo + ": " + e.getMessage(), null);
        }
    }
}