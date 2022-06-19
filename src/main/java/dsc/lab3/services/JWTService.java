package dsc.lab3.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dsc.lab3.dtos.RespostaLoginDTO;
import dsc.lab3.entities.Usuario;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jwt.FiltroJWT;

@Service
public class JWTService {
	
	@Autowired
	private UsuarioService usuarioService;
	public static final String TOKEN_KEY = "budzaradasha";
	
	public RespostaLoginDTO autheticate(Usuario usuario) {
		
		if (this.usuarioService.validaUsuario(usuario)) {
			String token = this.gerarToken(usuario.getEmail());
			return new RespostaLoginDTO(token);
		}
		
		return new RespostaLoginDTO("Usuário ou senha inválidos. Login não realizado.");
		
	}
	
	public String gerarToken(String email) {
		return Jwts.builder().setHeaderParam("typ", "JWT").setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN_KEY).setExpiration(new Date(System.currentTimeMillis()+ 3 * 60 * 1000)).compact();
				
	}
	
	public String getSujeitoDoToken(String authorizationHeader) {
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new SecurityException("Token inexistente ou mal formatado");
		}
		
		String token = authorizationHeader.substring(FiltroJWT.TOKEN_INDEX);
		String subject = null;
		try {
			subject = Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody().getSubject();
		}catch(SignatureException e){
			throw new SecurityException("Token inválido ou expirado!");
		}
		return subject;
		
	}

}
