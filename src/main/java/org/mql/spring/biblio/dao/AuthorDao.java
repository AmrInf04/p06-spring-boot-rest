package org.mql.spring.biblio.dao;

import java.util.List;

import org.mql.spring.biblio.models.Author;

public interface AuthorDao {
	public List<Author> selectAll();
	public Author selectById(int id);
	public List<Author> selectByName(String name);
	public boolean insert(Author author);
	public boolean update(Author author);
	public Author delete(int id);
	
}
