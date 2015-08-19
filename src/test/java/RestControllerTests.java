import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.javarush.hikarius.testproject.crud.config.ApplicationContextWebMvcConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = ApplicationContextWebMvcConfig.class)
public class RestControllerTests {

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void crudTests() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/crud/all"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/crud/getById/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/crud/getPage/5/2?sortType=desc&sortField=age"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());


        this.mockMvc.perform(MockMvcRequestBuilders.get("/crud/getPage/10/1?ageMoreFilter=5"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
