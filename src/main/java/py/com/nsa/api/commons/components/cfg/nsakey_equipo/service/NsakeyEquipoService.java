package py.com.nsa.api.commons.components.cfg.nsakey_equipo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.nsakey_equipo.model.NsakeyEquipo;
import py.com.nsa.api.commons.components.cfg.nsakey_equipo.repository.NsakeyEquipoRepository;
import java.util.List;
import java.util.Date;

@Service
public class NsakeyEquipoService {

    @Autowired
    private NsakeyEquipoRepository nsakeyEquipoRepository;

    public NsakeyEquipo.MensajeRespuesta insertarEquipo(NsakeyEquipo equipo) {
        try {
            equipo.setFechaRegistro(new Date());
            NsakeyEquipo nuevoEquipo = nsakeyEquipoRepository.save(equipo);
            return new NsakeyEquipo.MensajeRespuesta(200L,
                    "Equipo registrado exitosamente.", List.of(nuevoEquipo));
        } catch (Exception e) {
            return new NsakeyEquipo.MensajeRespuesta(500L,
                    "Error al registrar el equipo: " + e.getMessage(), null);
        }
    }

    public NsakeyEquipo.MensajeRespuesta buscarEquipo(String cpuModelo, String macAddress) {
        try {
            List<NsakeyEquipo> equipos = nsakeyEquipoRepository
                    .findByModeloAndMacAddress(cpuModelo, macAddress);

            if (equipos.isEmpty()) {
                return new NsakeyEquipo.MensajeRespuesta(204L,
                        "No se encontraron equipos con los parámetros especificados.", null);
            }

            return new NsakeyEquipo.MensajeRespuesta(200L,
                    "Equipos encontrados exitosamente.", equipos);
        } catch (Exception e) {
            return new NsakeyEquipo.MensajeRespuesta(500L,
                    "Error al buscar equipos: " + e.getMessage(), null);
        }
    }
}