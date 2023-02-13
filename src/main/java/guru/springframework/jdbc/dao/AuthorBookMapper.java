package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorBookMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        rs.next();

        Author entity = Author.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .books(new ArrayList<>())
                .build();

        if(rs.getString("title") != null) {
            mapBooks(rs, entity);
        }

        return entity;
    }

    private void mapBooks(ResultSet rs, Author entity) throws SQLException {
        do {
            Book book = Book.builder()
                    .id(rs.getLong("book_id"))
                    .title(rs.getString("title"))
                    .isbn(rs.getString("isbn"))
                    .publisher(rs.getString("publisher"))
                    .authorId(rs.getLong("id"))
                    .build();

            entity.getBooks().add(book);
        } while (rs.next());
    }
}
