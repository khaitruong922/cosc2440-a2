package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import s3818074_s3818487.cosc2440a2.controllers.AbstractController;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;
import s3818074_s3818487.cosc2440a2.services.AbstractService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private final String endpoint;

    public AbstractUnitTest(String endpoint) {
        this.endpoint = endpoint;
    }

    abstract T populateData();

    abstract List<T> populateListOfData();

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[POST] Create")
    public void createTest() throws Exception {
        UUID dataId = genUUID();
        T data = populateData();
        data.setId(dataId);

        when(service.add(data)).thenReturn(data);
        Assert.assertEquals(data, service.add(data));

        when(service.getAll()).thenReturn(Collections.singletonList(data));
        Assert.assertEquals(1, service.getAll().size());

        // Assertions
        String jsonRequest = om.writeValueAsString(data);
        mockMvc.perform(
                post("/" + endpoint)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get all")
    public void getAllTest() throws Exception {
        List<T> data = populateListOfData();

        when(service.getAll()).thenReturn(data);
        Assert.assertEquals(data.size(), service.getAll().size());
        Assert.assertEquals(data, service.getAll());

        mockMvc.perform(
                get("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get one by id")
    public void getOneTest() throws Exception {
        UUID dataId = genUUID();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assert.assertEquals(data, service.getById(dataId));

        mockMvc.perform(
                get("/"+ endpoint +"/{id}", dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[DELETE] Delete all")
    public void deleteAllTest() throws Exception {
        List<T> data = populateListOfData();

        when(service.getAll()).thenReturn(data);
        Assert.assertEquals(data.size(), service.getAll().size());
        Assert.assertEquals(data, service.getAll());

        repository.deleteAll();

        when(service.deleteAll()).thenReturn(HttpStatus.OK);
        when(service.getAll()).thenReturn(Collections.emptyList());
        Assert.assertEquals(0, service.getAll().size());

        mockMvc.perform(
                delete("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[DELETE] Delete one")
    public void deleteOneTest() throws Exception {
        UUID dataId = genUUID();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assert.assertEquals(data, service.getById(dataId));

        when(service.deleteById(dataId)).thenReturn(HttpStatus.OK);
        when(service.getById(dataId)).thenReturn(null);
        Assert.assertNull(service.getById(dataId));

        mockMvc.perform(
                delete("/"+endpoint+"/{id}",dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[PUT] Update Order")
    public void updateOneTest() throws Exception {
        UUID dataId = genUUID();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assert.assertEquals(data, service.getById(dataId));

        UUID newId = genUUID();
        data.setId(newId);
        when(service.updateById(data, dataId)).thenReturn(data);
        Assert.assertEquals(data, service.updateById(data,dataId));

        String jsonRequest = om.writeValueAsString(data);
        mockMvc.perform(
                put("/"+endpoint+"/{id}",dataId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[GET] Get with pagination")
    @org.junit.jupiter.api.Order(2)
    public void paging() throws Exception {
        List<T> data = populateListOfData();

        when(service.getAll(0)).thenReturn(data);
        Assert.assertEquals(data.size(), service.getAll(0).size());

        mockMvc.perform(
                get("/" + endpoint + "?page=0").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(
                get("/" + endpoint + "?page=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(
                get("/" + endpoint + "?page=2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }
}
