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
        String sql = "SELECT author.*, book.id AS book_id, book.title, book.isbn, book.publisher FROM author" +
                " LEFT JOIN book ON book.author_id = author.id" +
                " WHERE author.id=?";

        return jdbcTemplate.query(sql, new AuthorExtractor(), id);
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
        jdbcTemplate.update(
                "INSERT INTO author (first_name, last_name) VALUES (?,?)",
                author.getFirstName(), author.getLastName()
        );

        Long insertedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getById(insertedId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update(
                "UPDATE author SET first_name=?, last_name=? WHERE id=?",
                author.getFirstName(), author.getLastName(), author.getId()
        );

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM author WHERE id=?", id);
    }

    public RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }
}
