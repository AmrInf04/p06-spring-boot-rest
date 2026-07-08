package org.mql.spring.biblio.dao;

import java.util.List;
import java.util.Vector;
import org.mql.spring.biblio.models.Author;

public class AuthorDaoMock implements AuthorDao{
	private final List<Author> authors;

    public AuthorDaoMock() {
        this.authors = new Vector<>();
        this.authors.add(new Author(101, "James Gosling", 1955));
        this.authors.add(new Author(102, "Rod Johnson", 1955));
    }

    @Override
    public List<Author> selectAll() {
        return authors;
    }

    @Override
    public Author selectById(int id) {
        for (Author author : authors) {
            if (author.getId() == id) return author;
        }
        return null;
    }

    @Override
    public List<Author> selectByName(String name) {
        List<Author> result = new Vector<>();
        for (Author author : authors) {
            if (author.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(author);
            }
        }
        return result;
    }

    @Override
    public boolean insert(Author author) {
        return authors.add(author);
    }

    @Override
    public boolean update(Author author) {
        Author existing = selectById(author.getId());
        if (existing != null) {
            existing.setName(author.getName());
            existing.setYearBorn(author.getYearBorn());
            return true;
        }
        return false;
    }

    @Override
    public Author delete(int id) {
        Author author = selectById(id);
        if (author != null) {
            authors.remove(author);
            return author;
        }
        return null;
    }
}
