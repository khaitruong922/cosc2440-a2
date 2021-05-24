package s3818074_s3818487.cosc2440a2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import s3818074_s3818487.cosc2440a2.controllers.AbstractController;
import s3818074_s3818487.cosc2440a2.models.BaseEntity;
import s3818074_s3818487.cosc2440a2.services.AbstractService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
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
        T data = populateData();

        when(repository.save(data)).thenReturn(data);
        Assertions.assertEquals(data, service.add(data));
        verify(repository, times(1)).save(data);

        // Assertions
        String jsonRequest = om.writeValueAsString(data);
        MvcResult result = mockMvc.perform(
                post("/" + endpoint)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get all")
    public void getAllTest() throws Exception {
        List<T> data = populateListOfData();

        given(repository.findAll()).willReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());

        MvcResult result = mockMvc.perform(
                get("/" + endpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(data.size()))).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get by id")
    public void getByIdTest() throws Exception {
        UUID dataId = uuid();
        T data = populateData();
        data.setId(dataId);

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
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

        when(repository.findAll()).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());
        Assertions.assertEquals(data, service.getAll());

        // when
        repository.deleteAll();
        // then
        verify(repository, times(1)).deleteAll();

        when(repository.findAll()).thenReturn(Collections.emptyList());
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

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
        Assertions.assertEquals(data, service.getById(dataId));

        // when
        repository.deleteById(dataId);
        // then
        verify(repository, times(1)).deleteById(dataId);

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

        when(repository.findById(dataId)).thenReturn(java.util.Optional.of(data));
        Assertions.assertEquals(data, service.getById(dataId));

        UUID newId = uuid();
        data.setId(newId);
        when(repository.save(data)).thenReturn(data);
        Assertions.assertEquals(data, service.updateById(data, dataId));

        String jsonRequest = om.writeValueAsString(data);
        MvcResult result = mockMvc.perform(
                patch("/" + endpoint + "/{id}", dataId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("[GET] Get all in page")
    public void getAllInPageTest() throws Exception {
        List<T> data = populateListOfData();

        when(repository.findAll()).thenReturn(data);
        Assertions.assertEquals(data.size(), service.getAll().size());

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
