package nl._42.restsecure.autoconfigure;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractApplicationContextTest {

    protected AnnotationConfigWebApplicationContext context;
    
    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    protected MockMvc getWebClient(Class<?>... appConfig) {
        loadApplicationContext(appConfig);
        return webAppContextSetup(context)
            .apply(springSecurity())
            .defaultRequest(get("/")
                .contentType(APPLICATION_JSON)
                .with(csrf()))
            .alwaysDo(log())
            .build();
    }

    protected void loadApplicationContext() {
        this.context = load(new AnnotationConfigWebApplicationContext());
    }

    protected void loadApplicationContext(Class<?>... config) {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(config);
        this.context = load(appContext);
    }

    private AnnotationConfigWebApplicationContext load(AnnotationConfigWebApplicationContext applicationContext) {
        applicationContext.register(
                WebMvcAutoConfiguration.class,
                CrowdAuthenticationAutoConfig.class,
                JacksonAutoConfiguration.class,
                HttpMessageConvertersAutoConfiguration.class,
                WebSecurityAutoConfig.class);
        applicationContext.setServletContext(new MockServletContext());
        applicationContext.refresh();
        return applicationContext;
    }
}
