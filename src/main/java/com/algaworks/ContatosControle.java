package com.algaworks;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContatosControle {
	
	private static final ArrayList<Contato> LISTA_CONTATOS = new ArrayList<>();
	
	static {
		LISTA_CONTATOS.add(new Contato("1", "Neo", "00-0000-0000"));
		LISTA_CONTATOS.add(new Contato("2", "Morpheus", "00-0000-0000"));
		LISTA_CONTATOS.add(new Contato("3", "Trinity", "00-0000-0000"));
		LISTA_CONTATOS.add(new Contato("4", "Tank", "00-0000-0000"));
		LISTA_CONTATOS.add(new Contato("5", "Mouse", "00-0000-0000"));
	}
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/contatos")
	public ModelAndView listar() {
		ModelAndView variavelModelAndView = new ModelAndView("listar");
		
		variavelModelAndView.addObject("contatos", LISTA_CONTATOS);
		
		return variavelModelAndView;
	}
	
	@GetMapping("/contatos/novo")
	public ModelAndView novo() {
		ModelAndView variavelModelAndView = new ModelAndView("formulario");
		
		variavelModelAndView.addObject("contato", new Contato());
		
		return variavelModelAndView;
	}
	
	@PostMapping("/contatos")
	public String cadastrar(Contato contato) {
		String id = UUID.randomUUID().toString();
		
		contato.setId(id);
		
		LISTA_CONTATOS.add(contato);
		
		return "redirect:/contatos";
	}
	
	@GetMapping("/contatos/{id}/editar")
	public ModelAndView editar(@PathVariable String id) {
		ModelAndView variavelModelAndView = new ModelAndView("formulario");
		
		Contato contato = procurarContato(id);
				
		variavelModelAndView.addObject("contato", contato);
		
		return variavelModelAndView;
	}
	
	@PutMapping("/contatos/{id}")
	public String atualizar(Contato contato) {
		Integer indice = procurarIndiceContato(contato.getId());

		Contato contatoDesatualizado = LISTA_CONTATOS.get(indice);
		
		LISTA_CONTATOS.remove(contatoDesatualizado);
		
		LISTA_CONTATOS.add(indice, contato);
		
		return "redirect:/contatos";
	}
	
	@DeleteMapping("/contatos/{id}")
	public String remover(@PathVariable String id) {
		Contato contato = procurarContato(id);
		
		LISTA_CONTATOS.remove(contato);
		
		return "redirect:/contatos";
	}
	
	//------------------------------- Métodos Auxiliares
	
	private Contato procurarContato(String id) {
		Integer indice = procurarIndiceContato(id);
		
		if (indice != null) {
			Contato contato = LISTA_CONTATOS.get(indice);
			return contato;
		}
		
		return null;
	}
	
	private Integer procurarIndiceContato(String id) {
		for (int i = 0; i < LISTA_CONTATOS.size(); i++) {
			Contato contato = LISTA_CONTATOS.get(i);
			
			if (contato.getId().equals(id)) {
				return i;				
			}
		}
		
		return null;
	}
	
}





