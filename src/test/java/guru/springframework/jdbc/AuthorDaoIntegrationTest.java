package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {
        Author author = authorDao.getById(1L);

        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveNewAuthor() {
        Author author = Author.builder()
                .firstName("John")
                .firstName("Thompson")
                .build();

        Author savedEntity = authorDao.saveNewAuthor(author);

        assertThat(savedEntity).isNotNull();
        assertThat(savedEntity.getFirstName()).isEqualTo(savedEntity.getFirstName());
    }

    @Test
    void testUpdateExistingAuthor() {
        Author author = Author.builder()
                .firstName("John")
                .lastName("T")
                .build();

        Author savedEntity = authorDao.saveNewAuthor(author);

        savedEntity.setLastName("Thomson");

        Author updatedEntity = authorDao.updateAuthor(savedEntity);

        assertThat(updatedEntity.getLastName()).isEqualTo(savedEntity.getFirstName());
    }

    @Test
    void testDeleteAuthor() {
        Author author = Author.builder()
                .firstName("John")
                .lastName("Thomson")
                .build();

        Author savedEntity = authorDao.saveNewAuthor(author);

        assertThat(savedEntity).isNotNull();

        authorDao.deleteAuthorById(savedEntity.getId());

        assertThat(authorDao.getById(savedEntity.getId())).isNull();
    }
}
