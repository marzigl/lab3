package dsc.lab3.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import dsc.lab3.entities.Usuario;
import dsc.lab3.services.UsuarioService;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioService serviceUsuarios;
	
	public UsuarioController(UsuarioService serviceUsuario) {
		super();
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario){
		return new ResponseEntity<Usuario>(this.serviceUsuarios.addUsuario(usuario), HttpStatus.OK);
		
	}
	
	@GetMapping("/usuarios/{email}")
	public ResponseEntity<Usuario> removeUsuario(@PathVariable String email, @RequestHeader String header){
		try {
			return new ResponseEntity<Usuario> (this.serviceUsuarios.removeUsuario(email, header), HttpStatus.OK);
		}catch(IllegalArgumentException iae) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}catch (ServletException e) {
			return new ResponseEntity<Usuario>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	
}
