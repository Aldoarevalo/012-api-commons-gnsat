package py.com.nsa.api.commons.components.trx.viaje.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.ref.recorrido.repository.RecorridoRepository;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorrido;
import py.com.nsa.api.commons.components.trx.recorrido.service.TrxRecorridoService;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;
import py.com.nsa.api.commons.components.trx.viaje.repository.ViajeRepository;
import py.com.nsa.api.commons.components.ref.rutacab.repository.RutaCabRepository;
import py.com.nsa.api.commons.components.ref.producto.repository.ProductoRepository;
import py.com.nsa.api.commons.components.ref.empresa.repository.EmpresaRepository;
import py.com.nsa.api.commons.components.ref.parvalor.repository.ValorRepository;
import py.com.nsa.api.commons.components.ref.vehiculo.repository.VehiculoRepository;
import py.com.nsa.api.commons.components.cfg.usuario.repository.UsuarioRepository;
import py.com.nsa.api.commons.components.trx.viajechofer.model.ViajeChofer;
import py.com.nsa.api.commons.components.trx.viajechofer.service.ViajeChoferService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    private static final Logger logger = LoggerFactory.getLogger(ViajeService.class);

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private RutaCabRepository rutaCabRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecorridoRepository recorridorepository;

    @Autowired
    private ViajeChoferService viajeChoferService;

    @Autowired
    private TrxRecorridoService trxRecorridoService;

    /**
     * Obtiene todos los viajes
     */
    public Viaje.MensajeRespuesta getViajesAll() {
        try {
            List<Viaje> viajes = viajeRepository.findAll();
            if (viajes.isEmpty()) {
                return new Viaje.MensajeRespuesta(204L, "No se encontraron viajes.", null);
            }
            return new Viaje.MensajeRespuesta(200L, "Viajes obtenidos exitosamente.", viajes);
        } catch (Exception e) {
            logger.error("Error al obtener viajes: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al obtener viajes: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene un viaje por su código
     */
    public Viaje.MensajeRespuesta getViajeByVCod(Integer vCod) {
        try {
            Optional<Viaje> viaje = viajeRepository.findByVCod(vCod);
            if (viaje.isEmpty()) {
                return new Viaje.MensajeRespuesta(204L, "No se encontró el viaje con el código: " + vCod, null);
            }
            return new Viaje.MensajeRespuesta(200L, "Viaje encontrado.", List.of(viaje.get()));
        } catch (Exception e) {
            logger.error("Error al obtener el viaje: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al obtener el viaje: " + e.getMessage(), null);
        }
    }

    /**
     * Obtiene viajes filtrados por criterios
     */
    public Viaje.MensajeRespuesta getViajesFiltered(Viaje filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("vCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vFecha", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("rucCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("proCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vEmpresa", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("vEstado", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<Viaje> example = Example.of(filtro, matcher);
            List<Viaje> viajes = viajeRepository.findAll(example);

            if (viajes.isEmpty()) {
                return new Viaje.MensajeRespuesta(204L, "No se encontraron viajes.", null);
            }
            return new Viaje.MensajeRespuesta(200L, "Viajes encontrados.", viajes);
        } catch (Exception e) {
            logger.error("Error al filtrar viajes: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al filtrar viajes: " + e.getMessage(), null);
        }
    }

    /**
     * Inserta un nuevo viaje con validaciones
     */
    public Viaje.MensajeRespuesta insertarViaje(Viaje viaje) {
        try {
            logger.debug("Intentando insertar viaje: {}", viaje);

            // Asegurarse de que vCod sea nulo para forzar una inserción
            viaje.setVCod(null);

            // Validar campos obligatorios
            if (viaje.getVFecha() == null) {
                return new Viaje.MensajeRespuesta(400L, "La fecha del viaje (vFecha) es obligatoria.", null);
            }
            if (viaje.getRucCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de ruta (rucCod) es obligatorio.", null);
            }
            if (viaje.getProCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }
            if (viaje.getVEmpresa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de empresa (vEmpresa) es obligatorio.", null);
            }
            if (viaje.getVHora() == null) {
                return new Viaje.MensajeRespuesta(400L, "La hora del viaje (vHora) es obligatoria.", null);
            }
            if (viaje.getVEstado() == null) {
                return new Viaje.MensajeRespuesta(400L, "El estado del viaje (vEstado) es obligatorio.", null);
            }
            if (viaje.getVRefuerzo() == null) {
                return new Viaje.MensajeRespuesta(400L, "El indicador de refuerzo (vRefuerzo) es obligatorio.", null);
            }
            if (viaje.getVDia() == null) {
                return new Viaje.MensajeRespuesta(400L, "El día del viaje (vDia) es obligatorio.", null);
            }
            if (viaje.getVDuracion() == null) {
                return new Viaje.MensajeRespuesta(400L, "La duración del viaje (vDuracion) es obligatoria.", null);
            }
            if (viaje.getVServicio() == null) {
                return new Viaje.MensajeRespuesta(400L, "El servicio del viaje (vServicio) es obligatorio.", null);
            }
            if (viaje.getVTipoviaje() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de viaje (vTipoviaje) es obligatorio.", null);
            }
            if (viaje.getUsuCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de usuario (usuCod) es obligatorio.", null);
            }

            // Validar existencia de RutaCab
            if (!rutaCabRepository.existsById(viaje.getRucCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una ruta con el código: " + viaje.getRucCod(), null);
            }

            // Validar existencia de Producto
            if (!productoRepository.existsById(viaje.getProCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un producto con el código: " + viaje.getProCod(), null);
            }

            // Validar existencia de Empresa
            if (!empresaRepository.existsById(viaje.getVEmpresa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una empresa con el código: " + viaje.getVEmpresa(), null);
            }

            // Validar existencia de Valor (Día)
            if (!valorRepository.existsById(viaje.getVDia())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el día: " + viaje.getVDia(), null);
            }

            // Validar existencia de Valor (Servicio)
            if (!valorRepository.existsById(viaje.getVServicio())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el servicio: " + viaje.getVServicio(), null);
            }

            // Validar existencia de Vehículo (si está presente)
            if (viaje.getVVehiculo() != null && !vehiculoRepository.existsById(viaje.getVVehiculo())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un vehículo con el código: " + viaje.getVVehiculo(), null);
            }

            // Validar existencia de Usuario
            if (!usuarioRepository.existsById(viaje.getUsuCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un usuario con el código: " + viaje.getUsuCod(), null);
            }

            // Establecer fecha de última modificación
            viaje.setVUltmodif(new Date());

            // Guardar viaje
            Viaje nuevoViaje = viajeRepository.save(viaje);
            logger.debug("Viaje guardado exitosamente: {}", nuevoViaje);

            return new Viaje.MensajeRespuesta(200L, "Viaje creado exitosamente.", List.of(nuevoViaje));
        } catch (Exception e) {
            logger.error("Error al insertar el viaje: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al insertar el viaje: " + e.getMessage(), null);
        }
    }

    /**
     * Actualiza un viaje existente
     */
    public Viaje.MensajeRespuesta updateViaje(Viaje viaje) {
        try {
            // Validar si el viaje existe
            if (!viajeRepository.existsById(viaje.getVCod())) {
                return new Viaje.MensajeRespuesta(404L, "No se encontró el viaje con el código: " + viaje.getVCod(), null);
            }

            // Validar campos obligatorios
            if (viaje.getVFecha() == null) {
                return new Viaje.MensajeRespuesta(400L, "La fecha del viaje (vFecha) es obligatoria.", null);
            }
            if (viaje.getRucCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de ruta (rucCod) es obligatorio.", null);
            }
            if (viaje.getProCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }
            if (viaje.getVEmpresa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de empresa (vEmpresa) es obligatorio.", null);
            }
            if (viaje.getVHora() == null) {
                return new Viaje.MensajeRespuesta(400L, "La hora del viaje (vHora) es obligatoria.", null);
            }
            if (viaje.getVEstado() == null) {
                return new Viaje.MensajeRespuesta(400L, "El estado del viaje (vEstado) es obligatorio.", null);
            }
            if (viaje.getVRefuerzo() == null) {
                return new Viaje.MensajeRespuesta(400L, "El indicador de refuerzo (vRefuerzo) es obligatorio.", null);
            }
            if (viaje.getVDia() == null) {
                return new Viaje.MensajeRespuesta(400L, "El día del viaje (vDia) es obligatorio.", null);
            }
            if (viaje.getVDuracion() == null) {
                return new Viaje.MensajeRespuesta(400L, "La duración del viaje (vDuracion) es obligatoria.", null);
            }
            if (viaje.getVServicio() == null) {
                return new Viaje.MensajeRespuesta(400L, "El servicio del viaje (vServicio) es obligatorio.", null);
            }
            if (viaje.getVTipoviaje() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de viaje (vTipoviaje) es obligatorio.", null);
            }
            if (viaje.getUsuCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de usuario (usuCod) es obligatorio.", null);
            }

            // Validar existencia de RutaCab
            if (!rutaCabRepository.existsById(viaje.getRucCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una ruta con el código: " + viaje.getRucCod(), null);
            }

            // Validar existencia de Producto
            if (!productoRepository.existsById(viaje.getProCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un producto con el código: " + viaje.getProCod(), null);
            }

            // Validar existencia de Empresa
            if (!empresaRepository.existsById(viaje.getVEmpresa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una empresa con el código: " + viaje.getVEmpresa(), null);
            }

            // Validar existencia de Valor (Día)
            if (!valorRepository.existsById(viaje.getVDia())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el día: " + viaje.getVDia(), null);
            }

            // Validar existencia de Valor (Servicio)
            if (!valorRepository.existsById(viaje.getVServicio())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el servicio: " + viaje.getVServicio(), null);
            }

            // Validar existencia de Vehículo (si está presente)
            if (viaje.getVVehiculo() != null && !vehiculoRepository.existsById(viaje.getVVehiculo())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un vehículo con el código: " + viaje.getVVehiculo(), null);
            }

            // Validar existencia de Usuario
            if (!usuarioRepository.existsById(viaje.getUsuCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un usuario con el código: " + viaje.getUsuCod(), null);
            }

            // Actualizar fecha de última modificación
            viaje.setVUltmodif(new Date());

            // Guardar los cambios
            Viaje viajeActualizado = viajeRepository.save(viaje);
            return new Viaje.MensajeRespuesta(200L, "Viaje actualizado exitosamente.", List.of(viajeActualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar el viaje: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al actualizar el viaje: " + e.getMessage(), null);
        }
    }


    //@Transactional
    public Viaje.MensajeRespuesta insertViajeCompleto(Viaje viaje) {
        try {
            // Validar campos obligatorios del viaje
            if (viaje.getVFecha() == null) {
                return new Viaje.MensajeRespuesta(400L, "La fecha del viaje (vFecha) es obligatoria.", null);
            }
            if (viaje.getRucCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de ruta (rucCod) es obligatorio.", null);
            }
            if (viaje.getProCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }
            if (viaje.getVEmpresa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de empresa (vEmpresa) es obligatorio.", null);
            }
            if (viaje.getVHora() == null) {
                return new Viaje.MensajeRespuesta(400L, "La hora del viaje (vHora) es obligatoria.", null);
            }
            if (viaje.getVEstado() == null) {
                return new Viaje.MensajeRespuesta(400L, "El estado del viaje (vEstado) es obligatorio.", null);
            }
            if (viaje.getVRefuerzo() == null) {
                return new Viaje.MensajeRespuesta(400L, "El indicador de refuerzo (vRefuerzo) es obligatorio.", null);
            }
            if (viaje.getVDia() == null) {
                return new Viaje.MensajeRespuesta(400L, "El día del viaje (vDia) es obligatorio.", null);
            }
            if (viaje.getVDuracion() == null) {
                return new Viaje.MensajeRespuesta(400L, "La duración del viaje (vDuracion) es obligatoria.", null);
            }
            if (viaje.getVServicio() == null) {
                return new Viaje.MensajeRespuesta(400L, "El servicio del viaje (vServicio) es obligatorio.", null);
            }
            if (viaje.getVTipoviaje() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de viaje (vTipoviaje) es obligatorio.", null);
            }
            if (viaje.getUsuCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de usuario (usuCod) es obligatorio.", null);
            }
            if (viaje.getParaTipoTarifa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de tarifa (paraTipoTarifa) es obligatorio.", null);
            }

            // Validar existencia de entidades relacionadas
            if (!rutaCabRepository.existsById(viaje.getRucCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una ruta con el código: " + viaje.getRucCod(), null);
            }
            if (!productoRepository.existsById(viaje.getProCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un producto con el código: " + viaje.getProCod(), null);
            }
            if (!empresaRepository.existsById(viaje.getVEmpresa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una empresa con el código: " + viaje.getVEmpresa(), null);
            }
            if (!valorRepository.existsById(viaje.getVDia())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el día: " + viaje.getVDia(), null);
            }
            if (!valorRepository.existsById(viaje.getVServicio())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el servicio: " + viaje.getVServicio(), null);
            }
            if (viaje.getVVehiculo() != null && !vehiculoRepository.existsById(viaje.getVVehiculo())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un vehículo con el código: " + viaje.getVVehiculo(), null);
            }
            if (!usuarioRepository.existsById(viaje.getUsuCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un usuario con el código: " + viaje.getUsuCod(), null);
            }
            if (!valorRepository.existsById(viaje.getParaTipoTarifa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el tipo de tarifa: " + viaje.getParaTipoTarifa(), null);
            }

            // Validar choferes (primera pasada)
            List<ViajeChofer> choferes = viaje.getChoferes();
            if (choferes != null && !choferes.isEmpty()) {
                for (ViajeChofer chofer : choferes) {
                    if (chofer.getECod() == null) {
                        return new Viaje.MensajeRespuesta(400L, "El código de empleado (eCod) es obligatorio para los choferes.", null);
                    }
                }
            }

            // Establecer fechas
            viaje.setVUltmodif(new Date());
            viaje.setVFechaRegistro(new Date());
            viaje.setVCod(null); // Asegurar inserción nueva

            // Guardar el viaje
            Viaje savedViaje = viajeRepository.save(viaje);
            logger.info("Viaje guardado con vCod: {}", savedViaje.getVCod());

            // Procesar choferes
            List<ViajeChofer> savedChoferes = new ArrayList<>();
            if (choferes != null && !choferes.isEmpty()) {
                List<Long> eCods = choferes.stream()
                        .map(ViajeChofer::getECod)
                        .filter(eCod -> eCod != null)
                        .toList();

                if (eCods.isEmpty()) {
                    return new Viaje.MensajeRespuesta(400L, "Al menos un chofer debe tener un código de empleado (eCod) válido.", null);
                }

                ViajeChofer.MensajeRespuesta choferRespuesta = viajeChoferService.insertarViajeChoferes(
                        savedViaje.getVCod(), eCods);
                if (choferRespuesta.getStatus() != 200L) {
                    throw new RuntimeException("Error al guardar choferes: " + choferRespuesta.getMensaje());
                }
                savedChoferes = choferRespuesta.getViajeChoferes();
            }

            // Procesar recorridos (mantenido comentado como en el original)
        /*List<TrxRecorrido> savedRecorridos = new ArrayList<>();
        if (recorridos != null && !recorridos.isEmpty()) {
            for (TrxRecorrido recorrido : recorridos) {
                recorrido.getId().setVCod(savedViaje.getVCod());
                TrxRecorrido.MensajeRespuesta recorridoRespuesta = trxRecorridoService.insertar(recorrido);
                if (recorridoRespuesta.getStatus() != 201L) {
                    throw new RuntimeException("Error al guardar recorrido: " + recorridoRespuesta.getMensaje());
                }
                savedRecorridos.addAll(recorridoRespuesta.getRecorridos());
            }
        }*/

            // Actualizar el viaje con las listas guardadas
            savedViaje.setChoferes(savedChoferes);
            // savedViaje.setRecorridos(savedRecorridos);

            return new Viaje.MensajeRespuesta(200L, "Viaje insertado exitosamente con choferes.", List.of(savedViaje));
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al insertar viaje completo: {}", e.getMessage());
            return new Viaje.MensajeRespuesta(400L, "Error de validación: " + e.getMessage(), null);
        } catch (RuntimeException e) {
            logger.error("Error al insertar viaje completo: {}", e.getMessage());
            return new Viaje.MensajeRespuesta(500L, "Error al insertar el viaje: " + e.getMessage(), null);
        }
    }

    //@Transactional
    public Viaje.MensajeRespuesta updateViajeCompleto(Viaje viaje) {
        try {
            logger.info("INICIO updateViajeCompleto - Viaje recibido: {}", viaje.toString());
            // Validar existencia del viaje
            if (viaje.getVCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código del viaje (vCod) es obligatorio.", null);
            }
            if (!viajeRepository.existsById(viaje.getVCod())) {
                return new Viaje.MensajeRespuesta(404L, "No se encontró el viaje con el código: " + viaje.getVCod(), null);
            }

            // Validar campos obligatorios del viaje
            if (viaje.getVFecha() == null) {
                return new Viaje.MensajeRespuesta(400L, "La fecha del viaje (vFecha) es obligatoria.", null);
            }
            if (viaje.getRucCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de ruta (rucCod) es obligatorio.", null);
            }
            if (viaje.getProCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de producto (proCod) es obligatorio.", null);
            }
            if (viaje.getVEmpresa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de empresa (vEmpresa) es obligatorio.", null);
            }
            if (viaje.getVHora() == null) {
                return new Viaje.MensajeRespuesta(400L, "La hora del viaje (vHora) es obligatoria.", null);
            }
            if (viaje.getVEstado() == null) {
                return new Viaje.MensajeRespuesta(400L, "El estado del viaje (vEstado) es obligatorio.", null);
            }
            if (viaje.getVRefuerzo() == null) {
                return new Viaje.MensajeRespuesta(400L, "El indicador de refuerzo (vRefuerzo) es obligatorio.", null);
            }
            if (viaje.getVDia() == null) {
                return new Viaje.MensajeRespuesta(400L, "El día del viaje (vDia) es obligatorio.", null);
            }
            if (viaje.getVDuracion() == null) {
                return new Viaje.MensajeRespuesta(400L, "La duración del viaje (vDuracion) es obligatoria.", null);
            }
            if (viaje.getVServicio() == null) {
                return new Viaje.MensajeRespuesta(400L, "El servicio del viaje (vServicio) es obligatorio.", null);
            }
            if (viaje.getVTipoviaje() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de viaje (Restriction of Hazardous Substances Directive) es obligatorio.", null);
            }
            if (viaje.getUsuCod() == null) {
                return new Viaje.MensajeRespuesta(400L, "El código de usuario (usuCod) es obligatorio.", null);
            }
            if (viaje.getParaTipoTarifa() == null) {
                return new Viaje.MensajeRespuesta(400L, "El tipo de tarifa (paraTipoTarifa) es obligatorio.", null);
            }

            // Validar existencia de entidades relacionadas
            if (!rutaCabRepository.existsById(viaje.getRucCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una ruta con el código: " + viaje.getRucCod(), null);
            }
            if (!productoRepository.existsById(viaje.getProCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un producto con el código: " + viaje.getProCod(), null);
            }
            if (!empresaRepository.existsById(viaje.getVEmpresa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe una empresa con el código: " + viaje.getVEmpresa(), null);
            }
            if (!valorRepository.existsById(viaje.getVDia())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el día: " + viaje.getVDia(), null);
            }
            if (!valorRepository.existsById(viaje.getVServicio())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el servicio: " + viaje.getVServicio(), null);
            }
            if (viaje.getVVehiculo() != null && !vehiculoRepository.existsById(viaje.getVVehiculo())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un vehículo con el código: " + viaje.getVVehiculo(), null);
            }
            if (!usuarioRepository.existsById(viaje.getUsuCod())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un usuario con el código: " + viaje.getUsuCod(), null);
            }
            if (!valorRepository.existsById(viaje.getParaTipoTarifa())) {
                return new Viaje.MensajeRespuesta(404L, "No existe un valor de parámetro para el tipo de tarifa: " + viaje.getParaTipoTarifa(), null);
            }

            // Mantener la fecha de registro original
            Viaje existingViaje = viajeRepository.findById(viaje.getVCod())
                    .orElseThrow(() -> new RuntimeException("Viaje no encontrado."));
            viaje.setVFechaRegistro(existingViaje.getVFechaRegistro());

            // Actualizar el viaje
            viaje.setVUltmodif(new Date());
            logger.info("Antes de save - Viaje completo a guardar: {}", viaje.toString());

            viaje.setVFechaRegistro(existingViaje.getVFechaRegistro());

            logger.info("FechaRegistro en viaje actualizado: {}", existingViaje.getVFechaRegistro());

            Viaje updatedViaje = viajeRepository.save(viaje);
            logger.info("Viaje actualizado con vCod: {}", updatedViaje.getVCod());
            logger.info("Viaje actualizado con éxito: {}", updatedViaje.toString());
            // Procesar choferes: Eliminar existentes
            List<ViajeChofer> existingChoferes = viajeChoferService.listarPorViaje(updatedViaje.getVCod()).getViajeChoferes();
            if (existingChoferes != null && !existingChoferes.isEmpty()) {
                for (ViajeChofer existing : existingChoferes) {
                    viajeChoferService.eliminarAsignacion(existing.getVCod(), existing.getECod());
                }
            }

            // Procesar nuevos choferes
            List<ViajeChofer> choferes = viaje.getChoferes();
            List<ViajeChofer> savedChoferes = new ArrayList<>();
            if (choferes != null && !choferes.isEmpty()) {
                List<Long> eCods = choferes.stream()
                        .map(ViajeChofer::getECod)
                        .filter(eCod -> eCod != null)
                        .toList();

                if (eCods.isEmpty()) {
                    return new Viaje.MensajeRespuesta(400L, "Al menos un chofer debe tener un código de empleado (eCod) válido.", null);
                }

                ViajeChofer.MensajeRespuesta choferRespuesta = viajeChoferService.insertarViajeChoferes(
                        updatedViaje.getVCod(), eCods);
                if (choferRespuesta.getStatus() != 200L) {
                    throw new RuntimeException("Error al guardar choferes: " + choferRespuesta.getMensaje());
                }
                savedChoferes = choferRespuesta.getViajeChoferes();
            }

            // Procesar recorridos: Eliminar existentes
            List<TrxRecorrido> existingRecorridos = trxRecorridoService.listarTodos().getRecorridos().stream()
                    .filter(r -> r.getId().getVCod().equals(updatedViaje.getVCod()))
                    .toList();
            for (TrxRecorrido existing : existingRecorridos) {
                trxRecorridoService.eliminar(existing.getId());
            }

            // Nota: La lógica de recorridos nuevos está comentada, así que se mantiene como está

            // Actualizar el viaje con las listas guardadas
            updatedViaje.setChoferes(savedChoferes);
            // updatedViaje.setRecorridos(savedRecorridos); // Descomentado si se activa la lógica de recorridos

            return new Viaje.MensajeRespuesta(200L, "Viaje actualizado exitosamente con choferes.", List.of(updatedViaje));
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al actualizar viaje completo: {}", e.getMessage());
            return new Viaje.MensajeRespuesta(400L, "Error de validación: " + e.getMessage(), null);
        } catch (RuntimeException e) {
            logger.error("Error al actualizar viaje completo: {}", e.getMessage());
            return new Viaje.MensajeRespuesta(500L, "Error al actualizar el viaje: " + e.getMessage(), null);
        }
    }

    /**
     * Elimina un viaje por su código
     */
    public Viaje.MensajeRespuesta deleteViaje(Integer vCod) {
        try {
            if (!viajeRepository.existsById(vCod)) {
                return new Viaje.MensajeRespuesta(404L, "No se encontró el viaje con el código: " + vCod, null);
            }

            try {
                viajeRepository.deleteById(vCod);
                return new Viaje.MensajeRespuesta(200L, "Viaje eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                return new Viaje.MensajeRespuesta(409L, "No se puede eliminar el viaje ya que está siendo utilizado en otros registros.", null);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el viaje: ", e);
            return new Viaje.MensajeRespuesta(500L, "Error al eliminar el viaje: " + e.getMessage(), null);
        }
    }
}