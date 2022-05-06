package by.library.yurueu.controller.impl;

import by.library.yurueu.controller.BaseControllerTest;
import by.library.yurueu.dto.BookCopyListDto;
import by.library.yurueu.dto.BookDamageListDto;
import by.library.yurueu.dto.OrderDto;
import by.library.yurueu.dto.OrderListDto;
import by.library.yurueu.dto.OrderSaveDto;
import by.library.yurueu.dto.OrderUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest extends BaseControllerTest {
    @Test
    @WithMockUser(authorities = "admin")
    public void findByIdTest_shouldReturnOrderAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;
        List<BookCopyListDto> books = new ArrayList<>(){{add(BookCopyListDto.builder().id(id).build());}};
        List<BookDamageListDto> damages = new ArrayList<>(){{add(BookDamageListDto.builder().id(id).build());}};

        OrderDto order = OrderDto.builder().id(id).status("ACTIVE").price(123).userId(id)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).bookDamages(damages).bookCopies(books).build();

        //when
        when(orderService.findById(id)).thenReturn(order);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.price").value(123))
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.startDate").value("2003-04-01"))
                .andExpect(jsonPath("$.endDate").value("2003-04-01"))
                .andExpect(jsonPath("$.bookDamages").isArray())
                .andExpect(jsonPath("$.bookCopies").isArray())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void findAllTest_shouldReturnOrdersAndStatus200ForAdmin() throws Exception {
        //given
        OrderListDto order1 = OrderListDto.builder().id(1L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).build();
        OrderListDto order2 = OrderListDto.builder().id(2L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).build();
        List<OrderListDto> orders = new ArrayList<>(){{
            add(order1);
            add(order2);
        }};

        //when
        when(orderService.findAll()).thenReturn(orders);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"))
                .andExpect(jsonPath("$[0].price").value(123))
                .andExpect(jsonPath("$[0].startDate").value("2003-04-01"))
                .andExpect(jsonPath("$[0].endDate").value("2003-04-01"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].status").value("ACTIVE"))
                .andExpect(jsonPath("$[1].price").value(123))
                .andExpect(jsonPath("$[1].startDate").value("2003-04-01"))
                .andExpect(jsonPath("$[1].endDate").value("2003-04-01"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void addTest_shouldReturnOrderAndStatus200ForAdmin() throws Exception {
        //given
        List<Long> bookCopiesId = new ArrayList<>(){{add(1L);}};
        OrderSaveDto orderWithoutId = OrderSaveDto.builder().status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1))
                .bookCopiesId(bookCopiesId).build();
        OrderSaveDto orderWithId = OrderSaveDto.builder().id(3L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1))
                .bookCopiesId(bookCopiesId).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(orderService.add(orderWithoutId)).thenReturn(orderWithId);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapper.writeValueAsString(orderWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.price").value(123))
                .andExpect(jsonPath("$.startDate").value("2003-04-01"))
                .andExpect(jsonPath("$.endDate").value("2003-04-01"))
                .andExpect(jsonPath("$.bookCopiesId").isArray())
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void updateTest_shouldReturnOrderAndStatus200ForAdmin() throws Exception {
        //given
        OrderUpdateDto order = OrderUpdateDto.builder().id(3L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        when(orderService.update(order)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.price").value(123))
                .andExpect(jsonPath("$.startDate").value("2003-04-01"))
                .andExpect(jsonPath("$.endDate").value("2003-04-01"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "admin")
    public void deleteTest_shouldReturnTrueAndStatus200ForAdmin() throws Exception {
        //given
        Long id = 3L;

        //when
        when(orderService.delete(id)).thenReturn(true);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/orders/3"))
                .andExpect(jsonPath("$").value(true))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void findByIdTest_shouldReturnStatus403ForUser() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void findAllTest_shouldReturnStatus403ForUser() throws Exception {
        //given & when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isForbidden())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void addTest_shouldReturnStatus200ForUser() throws Exception {
        //given
        List<Long> bookCopiesId = new ArrayList<>(){{add(1L);}};
        OrderSaveDto orderWithoutId = OrderSaveDto.builder().status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1))
                .bookCopiesId(bookCopiesId).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapper.writeValueAsString(orderWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void updateTest_shouldReturnStatus200ForUser() throws Exception {
        //given
        OrderUpdateDto order = OrderUpdateDto.builder().id(3L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    @WithMockUser(authorities = "user")
    public void deleteTest_shouldReturnStatus200ForUser() throws Exception {
        //given & when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/orders/3"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    public void findByIdTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/3"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAllTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void addTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        List<Long> bookCopiesId = new ArrayList<>(){{add(1L);}};
        OrderSaveDto orderWithoutId = OrderSaveDto.builder().status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1))
                .bookCopiesId(bookCopiesId).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(mapper.writeValueAsString(orderWithoutId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given
        OrderUpdateDto order = OrderUpdateDto.builder().id(3L).status("ACTIVE").price(123)
                .startDate(LocalDate.of(2003,4,1))
                .endDate(LocalDate.of(2003,4,1)).build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/orders")
                        .content(mapper.writeValueAsString(order))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteTest_shouldReturnStatus401ForUnauthorized() throws Exception {
        //given & when & then
        mockMvc.perform(MockMvcRequestBuilders.delete("/orders/3"))
                .andExpect(status().isUnauthorized());
    }
}