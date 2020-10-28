package ru.live.kamaz_cs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DocumentManagerApplicationTests {

    @Autowired
    private DocumentRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(value = false)
    void testInsertDocument() throws IOException {
        File file = new File("D:\\for Maven.txt");

        Document document = new Document();
        document.setName(file.getName());

        byte[] bytes = Files.readAllBytes(file.toPath());
        document.setContent(bytes);
        long fileSize = bytes.length;
        document.setSize(fileSize);
        document.setUploadTime(new Date());

        Document savedDoc = repository.save(document);

        Document existDoc = entityManager.find(Document.class, savedDoc.getId());

        assertThat(existDoc.getSize()).isEqualTo(fileSize);

    }

}
