package fr.cmm.controller;

import fr.cmm.domain.Recipe;
import fr.cmm.helper.PageQuery;
import fr.cmm.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IndexControllerTestConfig.class)
@WebAppConfiguration
public class IndexControllerTest {
    @Inject
    RecipeService recipeService;

    @Inject
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(wac).build();

        Mockito.reset(recipeService);
    }

    @Test
    public void mentionsLegales() throws Exception {
        mockMvc.perform(get("/mentions-legales"))
                .andExpect(view().name("mentions-legales"));
    }

    @Test
    public void contact() throws Exception {
        mockMvc.perform(get("/contact"))
                .andExpect(view().name("contact"));
    }

    @Test
    public void recette() throws Exception {
        String id = "56375619d4c603aa4eb412af";

        Mockito.when(recipeService.findById(id)).thenReturn(new Recipe());

        mockMvc.perform(get("/recette/" + id))
                .andExpect(status().is(200))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recette"));
    }

    @Test
    public void recettes() throws Exception {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setIndex(2);
        pageQuery.setTag("simon");

        Mockito.when(recipeService.findByQuery(pageQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/recettes?tag=simon&pageIndex=3"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("recettes"));

    }

    @Test
    public void recettesWithBadPageIndex() throws Exception {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setIndex(0);

        Mockito.when(recipeService.findByQuery(pageQuery)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/recettes?pageIndex=-10"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(view().name("recettes"));
    }

    @Test
    public void recettesIsError404() throws Exception {
        String id = "56375";

        Mockito.when(recipeService.findById(id)).thenReturn(null);

        mockMvc.perform(get("/recettes/"+id))
                .andExpect(status().is(404));
    }
}