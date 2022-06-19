package dsc.lab3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import dsc.lab3.dtos.ComentarioDTO;
import dsc.lab3.dtos.DisciplinaDTO;
import dsc.lab3.dtos.NotaDTO;
import dsc.lab3.entities.Comentario;
import dsc.lab3.entities.Disciplina;
import dsc.lab3.entities.Nota;
import dsc.lab3.repositories.ComentarioDAO;
import dsc.lab3.repositories.DisciplinaDAO;
import dsc.lab3.repositories.NotaDAO;

@Service
public class DisciplinaService {

	@Autowired
	private DisciplinaDAO disciplinaRepository;
	
	@Autowired
	private NotaDAO notaRepository;
	
	@Autowired
	private ComentarioDAO comentarioRepository;

	public DisciplinaService(DisciplinaDAO disciplinaRepository, NotaDAO notaRepository, ComentarioDAO comentarioRepository) {
		super();
		this.disciplinaRepository = disciplinaRepository;
		this.notaRepository = notaRepository;
		this.comentarioRepository = comentarioRepository;
	}

	public Disciplina add(DisciplinaDTO disciplinaDto) {
		Disciplina disciplina = new Disciplina(disciplinaDto.getNome());
		this.disciplinaRepository.save(disciplina);
		return disciplina;
	}
	
	public List<Disciplina> add(List<Disciplina> disciplinas) {
		this.disciplinaRepository.saveAll(disciplinas);
		return disciplinas;
	}
	
	public List<Disciplina> getAll(){
		return this.disciplinaRepository.findAll();
	}
	
	public Disciplina getDisciplina(Long id) {
		if(this.disciplinaRepository.existsById(id)) {
			return this.disciplinaRepository.findById(id).get();
		}
		System.out.println("Disciplina com o ID " + id + " não existe!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
	}
	
	public Disciplina addLikeInDisciplina(Long id){
		if(this.disciplinaRepository.existsById(id)) {
			Disciplina disciplina = this.disciplinaRepository.findById(id).get();
			disciplina.setCurtidas(disciplina.getCurtidas() + 1);
			return this.disciplinaRepository.save(disciplina);
		}
		System.out.println("Disciplina com o ID " + id + " não existe!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
	}
	
	public Disciplina addNotaToDisciplina(Long id, NotaDTO notaDto){
		if(this.disciplinaRepository.existsById(id)) {
			Disciplina disciplina = this.disciplinaRepository.findById(id).get();
			if(disciplina.getNotas().isEmpty()) {
				disciplina.setNota(notaDto.getNota());
				disciplina.addNota(notaRepository.save(new Nota(notaDto.getNota(), disciplina)));
				disciplinaRepository.save(disciplina);
				return disciplina;
			}
			Nota ultima = disciplina.getNotas().get(disciplina.getNotas().size() - 1);
			double media = (ultima.getNota() + notaDto.getNota()) / 2;
			disciplina.setNota(media);
			disciplina.addNota(notaRepository.save(new Nota(notaDto.getNota(), disciplina)));
			disciplinaRepository.save(disciplina);
			return disciplina;
		}

		System.out.println("Disciplina com o ID " + id + " não existe!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	
	public Disciplina addComentarioToDisciplina(Long id, ComentarioDTO comentarioDto) {
		if(this.disciplinaRepository.existsById(id)) {
			Disciplina disciplina = this.disciplinaRepository.findById(id).get();
			
			disciplina.addComentario(comentarioRepository.save(new Comentario(comentarioDto.getComentario(), disciplina)));
			return this.disciplinaRepository.save(disciplina);
		}
		System.out.println("Disciplina com o ID " + id + " não existe!");
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND); 
	}
	
	public List<Disciplina> orderByNota(){
		return disciplinaRepository.findByOrderByNotaDesc();
	}
	
	public List<Disciplina> orderByLikes(){
		return disciplinaRepository.findByOrderByCurtidasDesc();
	}
	
}
