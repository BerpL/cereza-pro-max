package pe.edu.cibertec.cerezapromax.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pe.edu.cibertec.cerezapromax.model.Usuario;
import pe.edu.cibertec.cerezapromax.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public Optional<Usuario> validar(String correo, String password) {
		return usuarioRepository.findByCorreoAndPassword(correo, password)
				.filter(u -> "ACTIVO".equals(u.getEstado()));
	}
}
