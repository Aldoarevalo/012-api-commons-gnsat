package py.com.nsa.api.commons.components.ref.empresa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.empresa.repository.EmpresaRepository;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa.MensajeRespuesta getEmpresasAll() {
        try {
            List<Empresa> empresas = empresaRepository.findAll();
            if (empresas.isEmpty()) {
                return new Empresa.MensajeRespuesta(204L, "No se encontraron empresas.", null);
            }
            return new Empresa.MensajeRespuesta(200L, "Empresas obtenidas exitosamente.", empresas);
        } catch (Exception e) {
            return new Empresa.MensajeRespuesta(500L, "Error al obtener las empresas: " + e.getMessage(), null);
        }
    }
    public Empresa.MensajeRespuesta getEmpresasFiltered(Empresa filtro) {
        try {
            // Configura el matcher para ignorar valores nulos y hacer coincidir los campos exactamente
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("emDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("emRuc", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("emDireccion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("emTelefono", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("paCodigo", ExampleMatcher.GenericPropertyMatchers.exact());

            // Crea un Example con el filtro y el matcher
            Example<Empresa> example = Example.of(filtro, matcher);

            // Busca las empresas que coincidan con el filtro
            List<Empresa> empresas = empresaRepository.findAll(example);

            if (!empresas.isEmpty()) {
                return new Empresa.MensajeRespuesta(200L, "Empresas encontradas", empresas);
            } else {
                return new Empresa.MensajeRespuesta(204L, "No se encontraron empresas con los criterios especificados", null);
            }
        } catch (Exception e) {
            return new Empresa.MensajeRespuesta(500L, "Error al filtrar empresas: " + e.getMessage(), null);
        }
    }
    public Empresa.MensajeRespuesta insertarEmpresa(Empresa empresa) {
        try {
            if (empresaRepository.existsByEmDescripcionIgnoreCase(empresa.getEmDescripcion())) {
                return new Empresa.MensajeRespuesta(204L, "Ya existe una empresa con la misma descripción.", null);
            }
            if (empresaRepository.existsByEmRuc(empresa.getEmRuc())) {
                return new Empresa.MensajeRespuesta(204L, "Ya existe una empresa con el mismo RUC.", null);
            }
            Empresa nuevaEmpresa = empresaRepository.save(empresa);
            return new Empresa.MensajeRespuesta(200L, "Empresa creada exitosamente.", List.of(nuevaEmpresa));
        } catch (Exception e) {
            return new Empresa.MensajeRespuesta(500L, "Error al insertar la empresa: " + e.getMessage(), null);
        }
    }

    public Empresa.MensajeRespuesta updateEmpresa(Empresa empresa) {
        try {
            if (empresa.getEmCod() == null || !empresaRepository.existsById(empresa.getEmCod())) {
                return new Empresa.MensajeRespuesta(204L, "Empresa no encontrada.", null);
            }
            if (empresaRepository.existsByEmDescripcionIgnoreCase(empresa.getEmDescripcion())) {
                return new Empresa.MensajeRespuesta(204L, "Ya existe una empresa con la misma descripción.", null);
            }
            if (empresaRepository.existsByEmRuc(empresa.getEmRuc())) {
                return new Empresa.MensajeRespuesta(204L, "Ya existe una empresa con el mismo RUC.", null);
            }
            Empresa updatedEmpresa = empresaRepository.save(empresa);
            return new Empresa.MensajeRespuesta(200L, "Empresa actualizada exitosamente.", List.of(updatedEmpresa));
        } catch (Exception e) {
            return new Empresa.MensajeRespuesta(500L, "Error al actualizar la empresa: " + e.getMessage(), null);
        }
    }

    public Empresa.MensajeRespuesta deleteEmpresa(Long emCod) {
        try {
            if (empresaRepository.existsById(emCod)) {
                empresaRepository.deleteById(emCod);
                return new Empresa.MensajeRespuesta(200L, "Empresa eliminada exitosamente", null);
            } else {
                return new Empresa.MensajeRespuesta(204L, "Empresa con código " + emCod + " no encontrada", null);
            }
        } catch (JpaSystemException e) {
            String mensaje = "No se puede eliminar la empresa porque está referenciada por otros registros";
            return new Empresa.MensajeRespuesta(204L, "Error al eliminar la empresa: " + mensaje, null);
        } catch (Exception e) {
            return new Empresa.MensajeRespuesta(500L, "Error al eliminar la empresa con código " + emCod + ": " + e.getMessage(), null);
        }
    }
}