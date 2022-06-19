package dsc.lab3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsc.lab3.entities.Comentario;

@Repository
public interface ComentarioDAO extends JpaRepository<Comentario, Long> {
	List<Comentario> findByDisciplinaId(Long id);
}
