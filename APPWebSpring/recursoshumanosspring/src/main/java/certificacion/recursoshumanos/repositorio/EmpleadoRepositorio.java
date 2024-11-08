package certificacion.recursoshumanos.repositorio;

import certificacion.recursoshumanos.modelo.Empleado;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer>{


}
