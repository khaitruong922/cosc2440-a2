package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import s3818074_s3818487.cosc2440a2.controllers.AbstractController;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;
import s3818074_s3818487.cosc2440a2.services.AbstractService;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractUnitTest<T extends BaseEntity> {
    protected MockMvc mockMvc;

    protected JpaRepository<T, UUID> repository;

    protected AbstractService<T, UUID> service;

    protected ObjectMapper om = new ObjectMapper();

    public void setUp(AbstractController<T, UUID> controller, AbstractService<T, UUID> service, JpaRepository<T, UUID> repository) {
        om.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        this.repository = repository;
        this.service = service;;
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

    protected final String endpoint;

    public AbstractUnitTest(String endpoint) {
        this.endpoint = endpoint;
    }

    protected abstract T populateData();

    protected abstract List<T> populateListOfData();

    @Test
    @DisplayName("[POST] Add")
    public void addTest() {
        T data = populateData();

        // Assertions
        when(repository.save(data)).thenReturn(data);
        Assertions.assertEquals(data, service.add(data));
    }

    @Test
    @DisplayName("[POST][WEB] Add")
    public void addTestWebLayer() throws Exception {
        T data = populateData();

        String jsonRequest = om.writeValueAsString(data);
        mockMvc.perform(
                post("/" + endpoint)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[GET] Get all")
    public void getAllTest() {
        List<T> data = populateListOfData();

        given(repository.findAll()).willReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());
    }

    @Test
    @DisplayName("[GET][WEB] Get all")
    public void getAllTestWebLayer() throws Exception {
        List<T> data = populateListOfData();

        given(repository.findAll()).willReturn(data);
        mockMvc.perform(
                get("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(data.size()))).andReturn();
    }

    @Test
    @DisplayName("[GET] Get by id")
    public void getByIdTest() {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
        Assertions.assertEquals(data, service.getById(dataId));
    }

    @Test
    @DisplayName("[GET][WEB] Get by id")
    public void getByIdTestWebLayer() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        mockMvc.perform(
                get("/" + endpoint + "/{id}", dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[DELETE] Delete all")
    public void deleteAllTest() {
        List<T> data = populateListOfData();

        when(repository.findAll()).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());

        // when
        repository.deleteAll();
        // then
        verify(repository, times(1)).deleteAll();

        when(repository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, service.getAll().size());
    }

    @Test
    @DisplayName("[DELETE][WEB] Delete all")
    public void deleteAllTestWebLayer() throws Exception {
        List<T> data = populateListOfData();

        mockMvc.perform(
                delete("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[DELETE] Delete by id")
    public void deleteByIdTest() {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
        Assertions.assertEquals(data, service.getById(dataId));

        // when
        repository.deleteById(dataId);
        // then
        verify(repository, times(1)).deleteById(dataId);
    }

    @Test
    @DisplayName("[DELETE][WEB] Delete by id")
    public void deleteByIdTestWebLayer() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        mockMvc.perform(
                delete("/" + endpoint + "/{id}", dataId).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[PATCH] Update by id")
    public void updateByIdTest() {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
        Assertions.assertEquals(data, service.getById(dataId));

        UUID newId = uuid();
        data.setId(newId);
        when(repository.save(data)).thenReturn(data);
        Assertions.assertEquals(data, service.updateById(data, dataId));
    }

    @Test
    @DisplayName("[PATCH][WEB] Update by id")
    public void updateByIdTestWebLayer() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(repository.findById(data.getId())).thenReturn(java.util.Optional.of(data));

        String jsonRequest = om.writeValueAsString(data);
        mockMvc.perform(
                patch("/" + endpoint + "/{id}", dataId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @DisplayName("[PATCH][WEB] Update method throws data not found!")
    public void updateByIdTestWebLayerThrowDataNotFound(String name) {
        try {
            UUID dataId = uuid();
            T data = populateData();
            data.setId(dataId);

            String jsonRequest = om.writeValueAsString(data);
            mockMvc.perform(
                    patch("/" + endpoint + "/{id}", dataId)
                            .content(jsonRequest)
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk()).andReturn();
        } catch (Exception e){
            Assertions.assertEquals(e.getMessage(),
                    "Request processing failed; nested exception is java.lang.RuntimeException: " +name+ " not found!");
        }

    }

    @Test
    @DisplayName("[GET] Get all in page")
    public void getAllInPageTest() {
        List<T> data = populateListOfData();

        when(repository.findAll()).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
    }

    @Test
    @DisplayName("[GET][WEB] Get all in page")
    public void getAllInPageTestWebLayer() throws Exception {
        mockMvc.perform(
                get("/" + endpoint ).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }
}
