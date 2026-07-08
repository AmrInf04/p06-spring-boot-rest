package org.mql.spring.biblio.dao;

import java.util.List;
import java.util.Vector;
import org.mql.spring.biblio.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDaoContext implements AuthorDao {
    
    private List<Author> authors;

    public AuthorDaoContext() {
    }

    @Autowired
    public void setAuthors(List<Author> authorsFromContext) {
        this.authors = new Vector<>(authorsFromContext);
    }
    
    @Override
    public List<Author> selectAll() {
        return authors;
    }

    @Override
    public List<Author> selectByName(String name) {
        String finalName = name.toLowerCase();
        List<Author> result = new Vector<>();
        for (Author author : authors) {
            if (author.getName().toLowerCase().contains(finalName)) {
                result.add(author);
            }
        }
        return result;
    }

    @Override
    public Author selectById(int id) {
        for (Author author : authors) {
            if (author.getId() == id) {
                return author;
            }
        }
        return null;
    }

    @Override
    public boolean insert(Author author) {
        if (author == null) return false;
        
        for (Author a : authors) {
            if (a.getId() == author.getId()) {
                return false; 
            }
        }
        return authors.add(author);
    }

    @Override
    public boolean update(Author author) {
        if (author == null) return false;
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