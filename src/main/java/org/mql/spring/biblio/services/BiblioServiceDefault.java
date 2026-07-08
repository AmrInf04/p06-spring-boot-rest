package org.mql.spring.biblio.services;

import java.time.Year;
import java.util.List;
import java.util.Vector;
import org.mql.spring.biblio.dao.AuthorDao;
import org.mql.spring.biblio.models.Author;
import org.springframework.stereotype.Service;

@Service
public class BiblioServiceDefault implements BiblioService {

    private final AuthorDao authorDao;
        
    public BiblioServiceDefault(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.selectAll();
    }

    @Override
    public List<Author> getAuthorByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new Vector<>();
        }
        return authorDao.selectByName(name.trim());
    }

    @Override
    public boolean addAuthor(Author author) {
        if (author == null) {
            return false;
        }
        
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            return false;
        }
        
        int currentYear = Year.now().getValue();
        if (author.getYearBorn() > currentYear || author.getYearBorn() < 0) {
            return false;
        }
 
        Author existing = authorDao.selectById(author.getId());
        if (existing != null) {
            return false; // Conflit d'ID
        }

        return authorDao.insert(author);
    }

    @Override
    public boolean saveAuthor(Author author) {
        if (author == null || authorDao.selectById(author.getId()) == null) {
            return false;
        }
        return authorDao.update(author);
    }

    @Override
    public Author removeAuthor(int id) {
        Author author = authorDao.selectById(id);
        if (author == null) {
            throw new IllegalArgumentException("Cannot delete: No author found with ID : " + id);
        }
        return authorDao.delete(id);
    }

    @Override
    public Author getAuthorById(int id) {
        Author author = authorDao.selectById(id);
        if (author == null) {
            throw new IllegalArgumentException("No author found with ID : " + id);
        }
        return author;
    }
}