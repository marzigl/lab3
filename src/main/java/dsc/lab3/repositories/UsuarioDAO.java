package dsc.lab3.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsc.lab3.entities.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, String>{
	Optional<Usuario> findByEmail(String email);

}
