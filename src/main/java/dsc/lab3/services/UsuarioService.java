package dsc.lab3.services;

import java.util.Optional;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsc.lab3.entities.Usuario;
import dsc.lab3.repositories.UsuarioDAO;

@Service
public class UsuarioService {
	
	@Autowired
	public UsuarioDAO usuarioDAO;
	
	@Autowired
	public JWTService jwtService;
	
	public Usuario addUsuario(Usuario usuario) {
		Usuario usr = new Usuario(usuario.getEmail(), usuario.getNome(), usuario.getSenha());
		return this.usuarioDAO.save(usr);
		
	}
	
	public Usuario getUsuario(String email) {
		Optional<Usuario> optUsuario = this.usuarioDAO.findByEmail(email);
		if(optUsuario.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		return optUsuario.get();
	}
	
	private boolean usuarioTemPermissao(String authorizationHeader, String email) throws ServletException{
		String subject = jwtService.getSujeitoDoToken(authorizationHeader);
		Optional<Usuario> optUsuario = this.usuarioDAO.findByEmail(email);
		return optUsuario.isPresent() && optUsuario.get().getEmail().equals(email);
	}
	
	public Usuario removeUsuario(String email, String authHeader) throws ServletException {
		Usuario usuario = this.getUsuario(email);
		if(this.usuarioTemPermissao(authHeader, email)) {
			usuarioDAO.delete(usuario);
			return usuario;
		}
		
		throw new ServletException("Usuário não tem permissão");
	}
	
	public boolean validaUsuario(Usuario usuario) {
		Optional<Usuario> optUsuario = this.usuarioDAO.findByEmail(usuario.getEmail());
		
		if(optUsuario.isPresent() && optUsuario.get().getSenha().equals(usuario.getSenha())) {
			return true;
		}
		return false;
	}
	
}
