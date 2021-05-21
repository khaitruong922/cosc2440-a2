package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
    protected MockMvc mockMvc;

    @Mock
    protected AbstractService<T, UUID> service;

    @Mock
    protected JpaRepository<T, UUID> repository;

    protected ObjectMapper om = new ObjectMapper();

    public void setUp(AbstractController<T, UUID> controller) {
        om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void contextLoads() {
        Assertions.assertNotNull(mockMvc);
    }

    protected UUID uuid() {
        return UUID.randomUUID();
    }

    private final String endpoint;

    public AbstractUnitTest(String endpoint) {
        this.endpoint = endpoint;
    }

    protected abstract T populateData();

    protected abstract List<T> populateListOfData();

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[POST] Add")
    public void addTest() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(service.add(data)).thenReturn(data);
        Assertions.assertEquals(data, service.add(data));

        when(service.getAll()).thenReturn(Collections.singletonList(data));
        Assertions.assertEquals(1, service.getAll().size());

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
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());

        mockMvc.perform(
                get("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get by id")
    public void getByIdTest() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assertions.assertEquals(data, service.getById(dataId));

        mockMvc.perform(
                get("/" + endpoint + "/{id}", dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[DELETE] Delete all")
    public void deleteAllTest() throws Exception {
        List<T> data = populateListOfData();

        when(service.getAll()).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());

        repository.deleteAll();

        when(service.deleteAll()).thenReturn(HttpStatus.OK);
        when(service.getAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, service.getAll().size());

        mockMvc.perform(
                delete("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[DELETE] Delete by id")
    public void deleteByIdTest() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assertions.assertEquals(data, service.getById(dataId));

        when(service.deleteById(dataId)).thenReturn(HttpStatus.OK);
        when(service.getById(dataId)).thenReturn(null);
        Assertions.assertNull(service.getById(dataId));

        mockMvc.perform(
                delete("/" + endpoint + "/{id}", dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[PATCH] Update by id")
    public void updateByIdTest() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(service.getById(dataId)).thenReturn(data);
        Assertions.assertEquals(data, service.getById(dataId));

        UUID newId = uuid();
        data.setId(newId);
        when(service.updateById(data, dataId)).thenReturn(data);
        Assertions.assertEquals(data, service.updateById(data, dataId));

        String jsonRequest = om.writeValueAsString(data);
        mockMvc.perform(
                patch("/" + endpoint + "/{id}", dataId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[GET] Get all in page")
    @org.junit.jupiter.api.Order(2)
    public void getAllInPageTest() throws Exception {
        List<T> data = populateListOfData();

        when(service.getAll(0)).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll(0).size());

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
