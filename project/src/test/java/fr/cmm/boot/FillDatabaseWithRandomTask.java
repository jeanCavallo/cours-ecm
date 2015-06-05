package fr.cmm.boot;

import fr.cmm.domain.Receipe;
import fr.cmm.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.jongo.MongoCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import static fr.cmm.SpringProfiles.TEST;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FillDatabaseWithRandomTaskConfig.class)
@ActiveProfiles(TEST)
public class FillDatabaseWithRandomTask {
    @Inject
    private MongoCollection receipeCollection;

    @Inject
    private ImageService imageService;

    static int count = 1;

    @Test
    public void fillDbWithRandomReceipes() throws IOException {
        receipeCollection.remove();

        for (int i = 0; i < 100; i++) {
            receipeCollection.insert(randomReceipe());
        }
    }

    private Receipe randomReceipe() throws IOException {
        Receipe receipe = new Receipe();

        receipe.setTitle("La choucroute au lard " + count++);
        receipe.setDate(new Date());
        receipe.setIntro("De la choucroute, du lard et un peu d'espiéglerie");
        receipe.setImageId(randomImage());

        return receipe;
    }

    private String randomImage() throws IOException {
        byte[] data = IOUtils.toByteArray(getClass().getResourceAsStream("/pic" + random(3) + ".jpg"));

        return imageService.save(data, "image/jpeg");
    }

    private int random(int max) {
        return new Random().nextInt(max) + 1;
    }
}
