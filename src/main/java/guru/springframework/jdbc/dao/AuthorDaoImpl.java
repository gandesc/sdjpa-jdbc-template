package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {
        //todo impl
    }

    public RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
