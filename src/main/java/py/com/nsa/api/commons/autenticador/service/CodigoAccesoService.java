package py.com.nsa.api.commons.autenticador.service;

import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.autenticador.model.CodigoAcceso;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CodigoAccesoService {

    // Map para almacenar los códigos: key = usuario, value = CodigoAcceso
    private final Map<String, CodigoAcceso> codigosActivos = new ConcurrentHashMap<>();

    public CodigoAcceso.RespuestaAutenticacion generarCodigo(String usuario) {
        try {
            String codigo = generarCodigoAleatorio();
            LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(5);

            CodigoAcceso codigoAcceso = CodigoAcceso.builder()
                    .usuario(usuario)
                    .codigo(codigo)
                    .fechaExpiracion(fechaExpiracion)
                    .build();

            // Almacenar en el Map
            codigosActivos.put(usuario, codigoAcceso);

            return new CodigoAcceso.RespuestaAutenticacion(
                    200L,
                    "Código generado exitosamente. Válido por 5 minutos.",
                    codigo
            );
        } catch (Exception e) {
            return new CodigoAcceso.RespuestaAutenticacion(
                    500L,
                    "Error al generar el código: " + e.getMessage(),
                    null
            );
        }
    }

    public CodigoAcceso.RespuestaAutenticacion verificarCodigo(String usuario, String codigo) {
        try {
            CodigoAcceso codigoAcceso = codigosActivos.get(usuario);

            if (codigoAcceso == null) {
                return new CodigoAcceso.RespuestaAutenticacion(
                        401L,
                        "No existe un código activo para este usuario",
                        null
                );
            }

            if (codigoAcceso.getFechaExpiracion().isBefore(LocalDateTime.now())) {
                codigosActivos.remove(usuario);
                return new CodigoAcceso.RespuestaAutenticacion(
                        401L,
                        "El código ha expirado",
                        null
                );
            }

            if (!codigoAcceso.getCodigo().equals(codigo)) {
                return new CodigoAcceso.RespuestaAutenticacion(
                        401L,
                        "Código inválido",
                        null
                );
            }

            // Eliminar el código usado
            codigosActivos.remove(usuario);

            return new CodigoAcceso.RespuestaAutenticacion(
                    200L,
                    "Código verificado exitosamente",
                    generarJWTToken(usuario)
            );

        } catch (Exception e) {
            return new CodigoAcceso.RespuestaAutenticacion(
                    500L,
                    "Error al verificar el código: " + e.getMessage(),
                    null
            );
        }
    }

    private String generarCodigoAleatorio() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    private String generarJWTToken(String usuario) {
        // Aquí implementarías la generación del token JWT
        // Por ahora retornamos un token de ejemplo
        return "jwt-token-ejemplo-" + usuario + "-" + System.currentTimeMillis();
    }
}