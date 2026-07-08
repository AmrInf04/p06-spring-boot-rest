package org.mql.spring.biblio.controllers;

import java.util.List;

import org.mql.spring.biblio.models.Author;
import org.mql.spring.biblio.services.BiblioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Conventions d'une API Rest :
 * 
 * GET	: /authors  : On recupère uneListe/Tableau d'auteurs
 * GET : /authors/{id} : on recupère un auteur par id (id = variable de chemin)= path Variable
 * 
 * POST : /authors   : Insérer un auteur (reçu dans le body)= Request Body
 * PUT : /authors	: Modifier un auteur (reçu dans le body)= Request Body
 * DELETE : /authors/{id} : On supprime un auteur par son id (path Variable)
 * Si on veut envoyer d'autres informations à l'API REST on pourra utiliser  des parametres de requetes : Request Param
 * Une recherche par nom à titre d'exemple pourra se faire comme suit : 
 * GET 	: /authors?name=James
 */

@RestController
@RequestMapping("/api/authors")//Prefix commun pour tous les routes définit dans ce controller
@CrossOrigin("*")//Ajouter les origines accepté par notre Rest API
public class AuthorRestController {
	@Autowired
	private BiblioService service;
	
	public AuthorRestController() {
	}
	
	//End-Points
	@GetMapping
	public List<Author> authorsList(){
		return service.getAllAuthors();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Author>  getAuthor(@PathVariable int id) {
		// =>Entité de réponse constitué de 2 informations : Status et body (objet JSON)
		//Déléger le test metier au front end : 
		Author author = service.getAuthorById(id);
		if(author != null) {
			return ResponseEntity.ok().body(author);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(params = {"name"})
	public List<Author> searchAuthorsByName(@RequestParam String name){
		return service.getAuthorByName(name);
	}
	
	@PostMapping
	public ResponseEntity<Author> addAuthor(@RequestBody Author author){
		boolean result = service.addAuthor(author);
		if(result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(author);
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(author);
		}
	}
}
