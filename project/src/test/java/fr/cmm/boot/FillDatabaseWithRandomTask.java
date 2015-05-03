package fr.cmm.boot;

import java.util.Date;
import java.util.Random;

import javax.inject.Inject;

import org.jongo.MongoCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static fr.cmm.SpringProfiles.TEST;
import fr.cmm.domain.Image;
import fr.cmm.domain.Receipe;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FillDatabaseWithRandomTaskConfig.class)
@ActiveProfiles(TEST)
public class FillDatabaseWithRandomTask {
    @Inject
    private MongoCollection receipeCollection;

    static int count = 1;

    @Test
    public void fillDbWithRandomReceipes() {
        receipeCollection.remove();

        for (int i = 0; i < 100; i++) {
            receipeCollection.insert(randomReceipe());
        }
    }

    private Receipe randomReceipe() {
        Receipe receipe = new Receipe();

        receipe.setTitle("La choucroute au lard " + count++);
        receipe.setDate(new Date());
        receipe.setIntro("De la choucroute, du lard et un peu d'espiéglerie");
        receipe.setImage(randomImage());

        return receipe;
    }

    private Image randomImage() {
        Image image = new Image();

        image.setFilename("pic" + random(3) + ".jpg");

        return image;
    }

    private int random(int max) {
        return new Random().nextInt(max) + 1;
    }
}
