package fr.cmm.service;

import fr.cmm.domain.Recipe;
import fr.cmm.helper.PageQuery;
import org.jongo.MongoCollection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import static fr.cmm.SpringProfiles.INTEG;
import static java.util.Arrays.asList;
import static java.util.stream.StreamSupport.stream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ImageServiceTestConfig.class)
@ActiveProfiles(INTEG)
public class RecipeServiceTest {
    @Inject
    private RecipeService recipeService;

    @Inject
    private MongoCollection recipeCollection;

    @Before
    @After
    public void clean() {
        recipeCollection.remove();
    }

    @Test
    void save() {
        recipeService.save(new Recipe(title: 'test recipe'));

        assert recipeCollection.findOne().as(Recipe).title == 'test recipe';
    }

    @Test
    public void findById() {
        recipeService.save(new Recipe(title: 'test recipe',id :'5673cc8644ae4fd141dfced1'));

        assert recipeService.findById('5673cc8644ae4fd141dfced1').title == 'test recipe';
    }

    @Test
    public void findByQuery() {
        5.times { recipeService.save(new Recipe()) };

        assert recipeService.findByQuery(new PageQuery()).size() == 5;
    }

    @Test
    public void 'findByQuery with custom page size'() {
        5.times { recipeService.save(new Recipe()) };

        PageQuery pageQuery = new PageQuery();
        pageQuery.setSize(2);


        assert recipeService.findByQuery(pageQuery).size() == 2;

    }

    @Test
    public void 'findByQuery with tag'() {
        recipeService.save(new Recipe().withTags("tag1"));
        recipeService.save(new Recipe().withTags("tag1"));
        recipeService.save(new Recipe().withTags("tag2"));
        recipeService.save(new Recipe().withTags("tag2"));
        recipeService.save(new Recipe().withTags("tag3"));

        PageQuery pageQuery = new PageQuery();
        pageQuery.setTag("tag1");

        Assert.assertEquals(2, stream(recipeService.findByQuery(pageQuery).spliterator(), false).count());
    }

    @Test
    public void 'find all tags'() {
        recipeService.save(new Recipe().withTags("tag1", "tag2"));
        recipeService.save(new Recipe().withTags("tag2", "tag3"));

        Assert.assertEquals(asList("tag1", "tag2", "tag3"), recipeService.findAllTags());
    }

    @Test
    public void 'findById with invalid id'(){
        Assert.assertNull(recipeService.findById("123pasvalide456"));

    }

    @Test
    public void countByQuery() { // Finir de résoudre ce test /!\
        PageQuery pageQuery = new PageQuery();
        pageQuery.setTag("tag1");

        recipeService.save(new Recipe(tags: ['tag1', 'tag4']));
        recipeService.save(new Recipe().withTags("tag1"));
        recipeService.save(new Recipe().withTags("tag2"));
        recipeService.save(new Recipe().withTags("tag2"));
        recipeService.save(new Recipe().withTags("tag3"));

        Assert.assertEquals(2, recipeService.countByQuery(pageQuery));
    }

}