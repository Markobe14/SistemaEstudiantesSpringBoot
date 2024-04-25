package markobe.estudiantes;

import markobe.estudiantes.modelo.Estudiante;
import markobe.estudiantes.servicio.EstudianteServicioImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication //Implementando CommandLineRunner le decimos a Spring que la app sera de consola
public class EstudiantesApplication implements CommandLineRunner {

	@Autowired
	private EstudianteServicioImpl estudianteServicio;

	//Logger nos ayuda a mandar información a la consola, en lugar de system.out.print
	private static final Logger logger = LoggerFactory.getLogger(EstudiantesApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando la aplicacion...");
		//Levantar la fabrica de Spring
		SpringApplication.run(EstudiantesApplication.class, args);
		logger.info("Aplicacion finalizada!");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(nl + "Ejecutando metodo run de Spring" + nl);
		var salir = false;
		var consola = new Scanner(System.in);

		while(!salir){
			mostrarMenu();
			salir = ejecutarOpciones(consola);
			logger.info(nl);
		} //Fin While
	}

	private void mostrarMenu(){
		//logger.info(nl);
		logger.info("""
    			*** Sistema de Estudiantes ***
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elige una opcion: """);
	}

	private boolean ejecutarOpciones(Scanner consola){
		var opcion = Integer.parseInt(consola.nextLine());
		var salir = false;

		switch (opcion){
			case 1 ->{
				logger.info(nl + "Listado de Estudiantes..." + nl);
				List<Estudiante> estudiantes = estudianteServicio.listarEstudiantes();
				for (Estudiante estudiante : estudiantes) {
					logger.info(estudiante.toString() + nl);
				}
			}
			case 2 ->{
				logger.info(nl + "Buscar Estudiante por Id..." + nl);
				logger.info("Introduce el id_estudiante a buscar: ");
				var idEstudiante = Integer.parseInt(consola.nextLine());
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(idEstudiante);
				if(estudiante != null)
					logger.info(nl + "Estudiante encontrado: " + estudiante + nl);
				else
					logger.info(nl + "Estudiante NO encontrado con id: " + idEstudiante + nl);
			}
			case 3 ->{
				logger.info(nl + "Agregar Estudiante..." + nl);
				logger.info("Ingresa los datos del estudiante: " + nl);
				logger.info("Nombre: ");
				var nombre = consola.nextLine();
				logger.info("Apellido: ");
				var apellido = consola.nextLine();
				logger.info("Telefono: ");
				var telefono = consola.nextLine();
				logger.info("Email: ");
				var email = consola.nextLine();

				Estudiante estudiante = new Estudiante();
				estudiante.setNombre(nombre);
				estudiante.setApellido(apellido);
				estudiante.setTelefono(telefono);
				estudiante.setEmail(email);
				estudianteServicio.guardarEstudiante(estudiante);
				logger.info("Estudiante agregado correctamente: "  + estudiante + nl);
			}
			//JPA AUTOMATICAMENTE DETECTA SI SE INGRESA EL ID, PARA VER SI ES UNA INSERCION O UNA MODIFICACION
			case 4 ->{
				logger.info(nl + "Modificar Estudiante..." + nl);
				logger.info("Ingresa los nuevos datos del estudiante: " + nl);
				logger.info("Ingresa el Id_estudiante a modificar: ");
				var id = Integer.parseInt(consola.nextLine());
				//Buscamos si existe el estudiante a modificar
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(id);
				if(estudiante != null){
					logger.info("Nombre: ");
					var nombre = consola.nextLine();
					logger.info("Apellido: ");
					var apellido = consola.nextLine();
					logger.info("Telefono: ");
					var telefono = consola.nextLine();
					logger.info("Email: ");
					var email = consola.nextLine();

					estudiante.setNombre(nombre);
					estudiante.setApellido(apellido);
					estudiante.setTelefono(telefono);
					estudiante.setEmail(email);
					estudianteServicio.guardarEstudiante(estudiante);
					logger.info("Estudiante modificado correctamente: "  + estudiante + nl);
				}
				else{
					logger.info("Estudiante NO encontrado con id: " + id + nl);
				}
			}
			case 5 ->{
				logger.info(nl + "Eliminar Estudiante..." + nl);
				logger.info("Ingresa el Id_estudiante a modificar: ");
				var id = Integer.parseInt(consola.nextLine());
				//Buscamos si existe el estudiante a eliminar
				Estudiante estudiante = estudianteServicio.buscarEstudiantePorId(id);
				if(estudiante != null){
					estudianteServicio.eliminarEstudiante(estudiante);
					logger.info("Estudiante Eliminado correctamente: "  + estudiante + nl);
				}
				else{
					logger.info("Estudiante NO encontrado con id: " + id + nl);
				}
			}
			case 6 ->{
				logger.info("Hasta Pronto" + nl + nl);
				salir = true;
			}
			default -> logger.info("Opcion No reconocida, por favor selecciona una opción correcta" + opcion + nl);
		}//Fin del Switch
		return salir;
	}

}
