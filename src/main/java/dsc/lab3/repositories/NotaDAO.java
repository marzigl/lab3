package dsc.lab3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dsc.lab3.entities.Nota;

@Repository
public interface NotaDAO extends JpaRepository<Nota, Long> {
	
}
