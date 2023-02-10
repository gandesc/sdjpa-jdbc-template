package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM author WHERE id=?", getRowMapper(), id);
    }

    @Override
    public Author findByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM author WHERE first_name=? AND last_name=?",
                getRowMapper(),
                firstName, lastName
        );
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
