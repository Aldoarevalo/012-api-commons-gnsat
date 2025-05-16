package py.com.nsa.api.commons.components.cfg.shipments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.shipments.model.Shipment;
import py.com.nsa.api.commons.components.cfg.shipments.model.ShipmentId;
import py.com.nsa.api.commons.components.cfg.shipments.model.ShipmentWithDetailsDTO;
import py.com.nsa.api.commons.components.cfg.shipments.repository.ShipmentRepository;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDet;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.service.ShipmentDetService;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.repository.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentDetService shipmentDetService;

    @Autowired
    private ShipmentDetRepository shipmentDetRepository;

    public Shipment.MensajeRespuesta getShipmentAll(String tmsWhseid, String tmsStorerkey, String tmsExternorderkey) {
        try {
            List<Shipment> shipments;

            // Verificar si hay al menos un parámetro de filtro válido
            boolean hasWhseid = tmsWhseid != null && !tmsWhseid.isEmpty();
            boolean hasStorerkey = tmsStorerkey != null && !tmsStorerkey.isEmpty();
            boolean hasExternorderkey = tmsExternorderkey != null && !tmsExternorderkey.isEmpty();

            if (!hasWhseid && !hasStorerkey && !hasExternorderkey) {
                // Si no hay parámetros, devolver todos
                shipments = shipmentRepository.findAll();
            } else {
                // Usar parámetros disponibles para filtrar
                shipments = shipmentRepository.getListaShipmentParcial(
                        hasWhseid ? tmsWhseid : null,
                        hasStorerkey ? tmsStorerkey : null,
                        hasExternorderkey ? tmsExternorderkey : null
                );
            }

            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontraron envíos.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envíos obtenidos exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener los envíos: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Shipment.MensajeRespuesta insertShipment(Shipment shipment) {
        try {
            // Establecer fecha de creación si no está definida
            if (shipment.getTmsAdddate() == null) {
                // Crear fecha en zona horaria de Paraguay (GMT-3)
                ZoneId paraguayZone = ZoneId.of("America/Asuncion");
                ZonedDateTime paraguayDateTime = ZonedDateTime.now(paraguayZone);

                // Convertir a Date para compatibilidad
                shipment.setTmsAdddate(Date.from(paraguayDateTime.toInstant()));
            }

            // Si no se especifica el estado de procesado, establecerlo como no procesado
            if (shipment.getTmsProcesado() == null || shipment.getTmsProcesado().isEmpty()) {
                shipment.setTmsProcesado("N");
            }

            Shipment savedShipment = shipmentRepository.save(shipment);
            return new Shipment.MensajeRespuesta(200L, "Envío creado exitosamente.", List.of(savedShipment));
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al insertar el envío: " + e.getMessage(), null);
        }
    }


    @Transactional
    public Shipment.MensajeRespuesta insertarEnvioConDetalles(ShipmentWithDetailsDTO envioConDetalles) {
        try {
            // Validaciones
            validarCamposRequeridos(envioConDetalles);
            validarDetalles(envioConDetalles.getOrderdetails());
            procesarFechas(envioConDetalles);

            // Convertir y guardar la cabecera
            Shipment shipment = envioConDetalles.toShipment();
            Shipment envioGuardado = shipmentRepository.save(shipment);

            // Guardar los detalles
            List<ShipmentDet> detallesGuardados = guardarDetalles(envioConDetalles.getOrderdetails(), envioGuardado);

            return new Shipment.MensajeRespuesta(200L,
                    "Envío y detalles creados exitosamente.",
                    List.of(envioGuardado));

        } catch (Exception e) {
            throw new RuntimeException("Error al insertar el envío con detalles: " + e.getMessage(), e);
        }
    }

    private void validarCamposRequeridos(ShipmentWithDetailsDTO envio) {
        if (envio.getTmsWhseid() == null || envio.getTmsStorerkey() == null ||
                envio.getTmsExternorderkey() == null) {
            throw new IllegalArgumentException("Los campos Whseid, Storerkey y Externorderkey son obligatorios");
        }

        if (envio.getTmsConsigneekey() == null || envio.getTmsCompany() == null ||
                envio.getTmsRazonSocial() == null || envio.getTmsRuc() == null) {
            throw new IllegalArgumentException("Los campos Consigneekey, Company, RazonSocial y Ruc son obligatorios");
        }
    }

    private void validarDetalles(List<ShipmentWithDetailsDTO.DetalleOrdenDTO> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un detalle de envío");
        }

        for (ShipmentWithDetailsDTO.DetalleOrdenDTO detalle : detalles) {
            if (detalle.getTmsExternlineno() == null || detalle.getTmsSku() == null ||
                    detalle.getTmsDescsku() == null || detalle.getTmsUom() == null) {
                throw new IllegalArgumentException("Todos los campos del detalle son obligatorios");
            }
        }
    }

    private void procesarFechas(ShipmentWithDetailsDTO envio) {
        try {
            if (envio.getTmsInvoiceDate() == null || envio.getTmsOrderDate() == null) {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                if (envio.getTmsInvoiceDate() == null) {
                    Date invoiceDate = formatoFecha.parse(envio.getTmsInvoiceDate().toString());
                    envio.setTmsInvoiceDate(invoiceDate);
                }
                if (envio.getTmsOrderDate() != null) {
                    String fechaStr = formatoFecha.format(envio.getTmsOrderDate());
                    Date fechaFormateada = formatoFecha.parse(fechaStr);
                    envio.setTmsOrderDate(fechaFormateada);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en el formato de las fechas: " + e.getMessage());
        }
    }

    private List<ShipmentDet> guardarDetalles(List<ShipmentWithDetailsDTO.DetalleOrdenDTO> detalles, Shipment envio) {
        List<ShipmentDet> detallesGuardados = new ArrayList<>();
        for (ShipmentWithDetailsDTO.DetalleOrdenDTO detalle : detalles) {
            ShipmentDet shipmentDet = detalle.toShipmentDet(envio);
            ShipmentDet detalleGuardado = shipmentDetRepository.save(shipmentDet);
            detallesGuardados.add(detalleGuardado);
        }
        return detallesGuardados;
    }


    @Transactional
    public Shipment.MensajeRespuesta updateShipment(Shipment shipment) {
        try {
            ShipmentId id = new ShipmentId(shipment.getTmsWhseid(), shipment.getTmsStorerkey(), shipment.getTmsExternorderkey());

            // Buscar el envío existente
            Optional<Shipment> existingShipmentOpt = shipmentRepository.findById(id);

            if (existingShipmentOpt.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontró el envío para actualizar.", null);
            }

            Shipment existingShipment = existingShipmentOpt.get();

            // Actualizar TODOS los campos relevantes
            if (shipment.getTmsConsigneekey() != null) {
                existingShipment.setTmsConsigneekey(shipment.getTmsConsigneekey());
            }
            if (shipment.getTmsCompany() != null) {
                existingShipment.setTmsCompany(shipment.getTmsCompany());
            }
            if (shipment.getTmsRazonSocial() != null) {
                existingShipment.setTmsRazonSocial(shipment.getTmsRazonSocial());
            }
            if (shipment.getTmsRuc() != null) {
                existingShipment.setTmsRuc(shipment.getTmsRuc());
            }
            if (shipment.getTmsAddress1() != null) {
                existingShipment.setTmsAddress1(shipment.getTmsAddress1());
            }
            if (shipment.getTmsContact1() != null) {
                existingShipment.setTmsContact1(shipment.getTmsContact1());
            }
            if (shipment.getTmsPhone1() != null) {
                existingShipment.setTmsPhone1(shipment.getTmsPhone1());
            }
            if (shipment.getTmsEmail1() != null) {
                existingShipment.setTmsEmail1(shipment.getTmsEmail1());
            }
            if (shipment.getTmsCity() != null) {
                existingShipment.setTmsCity(shipment.getTmsCity());
            }
            if (shipment.getTmsState() != null) {
                existingShipment.setTmsState(shipment.getTmsState());
            }
            if (shipment.getTmsCountry() != null) {
                existingShipment.setTmsCountry(shipment.getTmsCountry());
            }
            if (shipment.getTmsLatitud() != null) {
                existingShipment.setTmsLatitud(shipment.getTmsLatitud());
            }
            if (shipment.getTmsLongitud() != null) {
                existingShipment.setTmsLongitud(shipment.getTmsLongitud());
            }
            if (shipment.getTmsExternorderkey2() != null) {
                existingShipment.setTmsExternorderkey2(shipment.getTmsExternorderkey2());
            }


            if (shipment.getTmsOrderDate() != null) {
                existingShipment.setTmsOrderDate(shipment.getTmsOrderDate());
            }

            if (shipment.getTmsInvoiceDate() != null) {
                existingShipment.setTmsInvoiceDate(shipment.getTmsInvoiceDate());
            }

            if (shipment.getTmsOrderTrip() != null) {
                existingShipment.setTmsOrderTrip(shipment.getTmsOrderTrip());
            }
            if (shipment.getTmsInvoiceDate() != null) {
                existingShipment.setTmsInvoiceDate(shipment.getTmsInvoiceDate());
            }
            if (shipment.getTmsInvoiceType() != null) {
                existingShipment.setTmsInvoiceType(shipment.getTmsInvoiceType());
            }
            if (shipment.getTmsInvoiceAmount() != null) {
                existingShipment.setTmsInvoiceAmount(shipment.getTmsInvoiceAmount());
            }
            if (shipment.getTmsCurrency() != null) {
                existingShipment.setTmsCurrency(shipment.getTmsCurrency());
            }
            if (shipment.getTmsProcesado() != null) {
                existingShipment.setTmsProcesado(shipment.getTmsProcesado());
            }
            if (shipment.getTmsEmail2() != null) {
                existingShipment.setTmsEmail2(shipment.getTmsEmail2());
            }
            if (shipment.getTmsNotes2() != null) {
                existingShipment.setTmsNotes2(shipment.getTmsNotes2());
            }



            if (shipment.getTmsShipmentorderid() != null) {
                existingShipment.setTmsShipmentorderid(shipment.getTmsShipmentorderid());
            }

            if (shipment.getTmsDoor() != null) {
                existingShipment.setTmsDoor(shipment.getTmsDoor());
            }

            // Guardar la entidad actualizada
            Shipment updatedShipment = shipmentRepository.save(existingShipment);

            return new Shipment.MensajeRespuesta(200L, "Envío actualizado exitosamente.", List.of(updatedShipment));
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al actualizar el envío: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Shipment.MensajeRespuesta deleteShipment(String tmsWhseid, String tmsStorerkey, String tmsExternorderkey) {
        try {
            ShipmentId id = new ShipmentId(tmsWhseid, tmsStorerkey, tmsExternorderkey);
            if (shipmentRepository.existsById(id)) {
                shipmentRepository.deleteById(id);
                return new Shipment.MensajeRespuesta(200L, "Envío eliminado exitosamente.", null);
            } else {
                return new Shipment.MensajeRespuesta(204L, "No se encontró el envío para eliminar.", null);
            }
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al eliminar el envío: " + e.getMessage(), null);
        }
    }





    public Shipment.MensajeRespuesta getByDateRange(Date startDate, Date endDate) {
        try {
            List<Shipment> shipments = shipmentRepository.findByOrderDateBetween(startDate, endDate);
            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontraron envíos en el rango de fechas especificado.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envíos obtenidos exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener los envíos por rango de fechas: " + e.getMessage(), null);
        }
    }

    public Shipment.MensajeRespuesta getByInvoiceDateRange(Date startDate, Date endDate) {
        try {
            List<Shipment> shipments = shipmentRepository.findByInvoiceDateBetween(startDate, endDate);
            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontraron envíos en el rango de fechas de factura especificado.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envíos obtenidos exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener los envíos por rango de fechas de factura: " + e.getMessage(), null);
        }
    }

    public Shipment.MensajeRespuesta getMostRecentByStorerkey(String tmsStorerkey) {
        try {
            List<Shipment> shipments = shipmentRepository.findMostRecentByStorerkey(tmsStorerkey, PageRequest.of(0, 1));
            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontró un envío reciente para el StorerKey especificado.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envío más reciente obtenido exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener el envío más reciente: " + e.getMessage(), null);
        }
    }

    public Shipment.MensajeRespuesta getByProcesado(String tmsProcesado) {
        try {
            List<Shipment> shipments = shipmentRepository.findByTmsProcesado(tmsProcesado);
            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontraron envíos con el estado de procesamiento especificado.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envíos obtenidos exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener los envíos por estado de procesamiento: " + e.getMessage(), null);
        }
    }

    public Shipment.MensajeRespuesta getByConsigneekey(String tmsConsigneekey) {
        try {
            List<Shipment> shipments = shipmentRepository.findByTmsConsigneekey(tmsConsigneekey);
            if (shipments.isEmpty()) {
                return new Shipment.MensajeRespuesta(204L, "No se encontraron envíos para el destinatario especificado.", null);
            }
            return new Shipment.MensajeRespuesta(200L, "Envíos obtenidos exitosamente.", shipments);
        } catch (Exception e) {
            return new Shipment.MensajeRespuesta(500L, "Error al obtener los envíos por destinatario: " + e.getMessage(), null);
        }
    }
}