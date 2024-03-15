package persistence.sql.dml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteQueryBuilderTest {

    @Test
    @DisplayName("DeleteQuery를 만들 수 있다.")
    void buildDeleteQuery() {
        //given
        Person person = new Person(1L, "name", 1, "email", 1);
        DeleteQueryBuilder deleteQueryBuilder = DeleteQueryBuilder.getInstance();

        //when
        String query = deleteQueryBuilder.build(person);

        //then
        assertThat(query).isEqualTo("DELETE FROM users WHERE id = 1");
    }
}
