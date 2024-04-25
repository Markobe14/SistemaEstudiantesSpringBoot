package markobe.estudiantes.repositorio;

import markobe.estudiantes.modelo.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstudianteRepositorio extends JpaRepository<Estudiante, Integer> {

}
