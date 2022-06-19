package dsc.lab3.controllers;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dsc.lab3.dtos.RespostaLoginDTO;
import dsc.lab3.entities.Usuario;
import dsc.lab3.services.JWTService;

@RestController
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<RespostaLoginDTO> autentica(@RequestBody Usuario usuario) throws ServletException{
		return new ResponseEntity<RespostaLoginDTO>(jwtService.autheticate(usuario), HttpStatus.OK);
		
	}
	
}
