package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", getRowMapper(), id);
    }

    @Override
    public Book findByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title=?", getRowMapper(), title);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update(
                "INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId()
        );


        Long lastInsertId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return getById(lastInsertId);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update(
                "UPDATE book SET title=?, isbn=?, publisher=? WHERE id=?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getId()
        );

        return getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public RowMapper<Book> getRowMapper() {
        return new BookMapper();
    }
}
