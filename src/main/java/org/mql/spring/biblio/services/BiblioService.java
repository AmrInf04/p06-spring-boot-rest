package org.mql.spring.biblio.services;

import java.util.List;

import org.mql.spring.biblio.models.Author;

public interface BiblioService {
	public List<Author> getAllAuthors();
	public Author getAuthorById(int id);
	public List<Author> getAuthorByName(String name);
	public boolean addAuthor(Author author);
	public boolean saveAuthor(Author author);
	public Author removeAuthor(int id);
}
