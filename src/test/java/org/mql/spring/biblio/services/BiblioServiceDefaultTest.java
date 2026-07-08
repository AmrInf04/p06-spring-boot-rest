package org.mql.spring.biblio.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mql.spring.biblio.dao.AuthorDao;
import org.mql.spring.biblio.dao.AuthorDaoMock;
import org.mql.spring.biblio.models.Author;

@DisplayName("Unit tests verifying business rules of BiblioServiceDefault")
class BiblioServiceDefaultTest {

    private BiblioServiceDefault biblioService;

    @BeforeEach
    void setUp() {
        // mock physique
        AuthorDao authorDaoMock = new AuthorDaoMock();
        biblioService = new BiblioServiceDefault(authorDaoMock);
    }

    @Test
    @DisplayName("Should successfully return an author when provided a valid registered ID")
    void testGetAuthorByIdSuccess() {
        Author actualAuthor = biblioService.getAuthorById(101);
        
        assertNotNull(actualAuthor);
        assertEquals("James Gosling", actualAuthor.getName());
    }

    @Test
    @DisplayName("Should throw an IllegalArgumentException when the requested ID does not exist in the store")
    void testGetAuthorByIdNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            biblioService.getAuthorById(999);
        });
        
        assertTrue(exception.getMessage().contains("No author found with ID"));
    }

    @Test
    @DisplayName("Should successfully add a valid and unique author to the system")
    void testAddAuthorSuccess() {
        Author newAuthor = new Author(103, "Erich Gamma", 1955);
        boolean isAdded = biblioService.addAuthor(newAuthor);
        
        assertTrue(isAdded);
        assertEquals(3, biblioService.getAllAuthors().size());
    }

    @Test
    @DisplayName("Should reject adding an author if the name property is empty or blank")
    void testAddAuthorValidationFailsForBlankName() {
        Author invalidAuthor = new Author(103, "   ", 1980);
        boolean isAdded = biblioService.addAuthor(invalidAuthor);
        
        assertFalse(isAdded, "Should have failed business rules validation because of blank name");
    }

    @Test
    @DisplayName("Should reject adding an author if the birth year is in the future")
    void testAddAuthorValidationFailsForFutureBirthYear() {
        // 2030 est en future
        Author invalidAuthor = new Author(103, "Future Coder", 2030);
        boolean isAdded = biblioService.addAuthor(invalidAuthor);
        
        assertFalse(isAdded, "Should have failed validation because birth year is in the future");
    }

    @Test
    @DisplayName("Should refuse to add an author if the ID is already taken by another entity")
    void testAddAuthorValidationFailsForDuplicateId() {
        Author duplicateAuthor = new Author(101, "New Author Name", 1990);
        boolean isAdded = biblioService.addAuthor(duplicateAuthor);
        
        assertFalse(isAdded, "Should have blocked insertion to prevent duplicate primary keys");
    }

    @Test
    @DisplayName("Should return an empty list immediately when search criteria is blank without hitting database")
    void testGetAuthorByNameEmptyQuery() {
        List<Author> results = biblioService.getAuthorByName("   ");
        
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}