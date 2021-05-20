package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import s3818074_s3818487.cosc2440a2.controllers.AbstractController;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;
import s3818074_s3818487.cosc2440a2.services.AbstractService;

import java.util.UUID;

public abstract class AbstractUnitTest<T extends BaseEntity> {
    MockMvc mockMvc;

    @Mock
    AbstractService<T, UUID> service;

    @Mock
    JpaRepository<T, UUID> repository;

    ObjectMapper om = new ObjectMapper();

    public void setUp(AbstractController<T, UUID> controller) {
        om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void contextLoads() {
        Assert.assertNotNull(mockMvc);
    }

    UUID genUUID(){
        return UUID.randomUUID();
    }
}
