package controllers;


import boot.App;
import boot.controllers.UsersRESTController;
import boot.entities.PhoneRecord;
import boot.entities.User;
import boot.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= App.class)
/*Аннотация @WebMvcTest(UsersRESTController.class) создаёт тестовое окружение с настроенным
Spring MVC и входящим в него Jackson, в том виде, в каком они настроены в реальном приложении.*/
@WebMvcTest(UsersRESTController.class)
/**Класс содержит юнит-тесты к методам класса UsersRESTController
 @author Артемьев Р.А.
 @version 03.12.2019 */
public class UsersRESTControllerTest
{
    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllUsers() throws Exception
    {
        User user1 = new User("TEST1");
        User user2 = new User("TEST2");
        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);

         /*Задаём поведения mock объекта usersService: мы говорим, что будет
        вызван метод getAllUsers() и что в ответ он должен вернуть объект expected*/
        when(usersService.getAllUsers()).thenReturn(expected);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testAddUser() throws Exception
    {
        User expected = new User("TEST");
        Long id = expected.getUserId();
        ObjectMapper objectMapper = new ObjectMapper();
        when(usersService.addUser(expected)).thenReturn(id);

        mockMvc.perform(post("/users")
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testGetUserNotExist() throws Exception
    {
        Long id = 2L;
        when(usersService.getUser(id)).thenReturn(null);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().is(404));//Проверяем Http-ответ

    }

    @Test
    public void testGetUser() throws Exception
    {
        User expected = new User("TEST1");
        Long id = expected.getUserId();
        when(usersService.getUser(id)).thenReturn(expected);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testDeleteUserNotExist() throws Exception
    {
        Long id = 2L;
        when(usersService.getUser(id)).thenReturn(null);

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().is(404));//Проверяем Http-ответ

    }

    @Test
    public void testDeleteUser() throws Exception
    {
        User expected = new User("TEST1");
        Long id = expected.getUserId();
        when(usersService.getUser(id)).thenReturn(expected);
        when(usersService.deleteUser(id)).thenReturn(id);

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testUpdateUserNotExist() throws Exception
    {
        User user = new User("TEST");
        ObjectMapper objectMapper = new ObjectMapper();
        when(usersService.updateUser(user)).thenReturn(null);

        mockMvc.perform(put("/users/")//Совершаем тестовый Http-запрос
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));//Проверяем Http-ответ

    }

    @Test
    public void testUpdateUser() throws Exception
    {
        User user = new User("TEST");
        ObjectMapper objectMapper = new ObjectMapper();
        when(usersService.updateUser(user)).thenReturn(user.getUserId());

        mockMvc.perform(put("/users/")//Совершаем тестовый Http-запрос
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(user)));//Конвертируем в json

    }

    @Test
    public void testGetUsersByName() throws Exception
    {
        User user = new User("TEST1");
        List<User> expected = new ArrayList<>();
        expected.add(user);
        String partName = "T1";

        when(usersService.getUsersByName(partName)).thenReturn(expected);

        mockMvc.perform(get("/users/name?partName=" + partName))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testGetUserAllPhoneRecords() throws Exception
    {
        Long id = 1L;
        PhoneRecord record1 = new PhoneRecord("TEST", "TEST");
        PhoneRecord record2 = new PhoneRecord("TEST2", "TEST2");

        List<PhoneRecord> expected = new ArrayList<>();
        expected.add(record1);
        expected.add(record2);

        when(usersService.getUserAllPhoneRecords(id)).thenReturn(expected);

        mockMvc.perform(get("/users/phoneRecords/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testAddUserPhoneRecord() throws Exception
    {
        User user = new User("TEST");
        Long userId = user.getUserId();
        PhoneRecord expected = new PhoneRecord("TEST", "TEST");
        Long id = 1L;
        ObjectMapper objectMapper = new ObjectMapper();

        when(usersService.addUserPhoneRecord(userId, expected)).thenReturn(id);
        expected.setPhoneRecordId(id);

        mockMvc.perform(post("/users/phoneRecord/" + userId)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testGetUserPhoneRecord() throws Exception
    {
        User user = new User("TEST");
        Long userId = user.getUserId();
        PhoneRecord expected = new PhoneRecord("TEST", "TEST");
        Long id = 1L;

        when(usersService.getUserPhoneRecord(userId, id)).thenReturn(expected);
        expected.setPhoneRecordId(id);

        mockMvc.perform(get("/users/phoneRecord/" + userId + "?id=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));
    }

    @Test
    public void testGetUserPhoneRecordNotExist() throws Exception
    {
        Long id = 2L;
        when(usersService.getUserPhoneRecord(id, id)).thenReturn(null);

        mockMvc.perform(get("/users/phoneRecord/" + id + "?id=" + id))
                .andExpect(status().is(404));//Проверяем Http-ответ

    }

    @Test
    public void testDeleteUserPhoneRecord() throws Exception
    {
        Long userId = 1L;
        PhoneRecord expected = new PhoneRecord("TEST", "TEST");
        Long id = 1L;

        when(usersService.getUserPhoneRecord(userId, id)).thenReturn(expected);
        when(usersService.deleteUserPhoneRecord(userId, id)).thenReturn(id);
        expected.setPhoneRecordId(id);

        mockMvc.perform(delete("/users/phoneRecord/" + userId + "?id=" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));
    }

    @Test
    public void testUpdateUserPhoneRecord() throws Exception
    {

        Long userId = 1L;
        PhoneRecord expected = new PhoneRecord("TEST", "TEST");
        Long id = 1L;
        ObjectMapper objectMapper = new ObjectMapper();
        when(usersService.updateUserPhoneRecord(userId, expected)).thenReturn(id);

        mockMvc.perform(put("/users/phoneRecord/" + userId)
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));
    }

    @Test
    public void testGetUserPhoneRecordsByPhoneNumber() throws Exception
    {
        Long userId = 1L;
        PhoneRecord record = new PhoneRecord("TEST2", "TEST2");
        String phoneNumber = record.getPhoneNumber();

        List<PhoneRecord> expected = new ArrayList<>();
        expected.add(record);

        when(usersService.getUserPhoneRecordsByPhoneNumber(
                userId, phoneNumber)).thenReturn(expected);

        mockMvc.perform(get(
                "/users/phoneRecords/phoneNumber/" + userId + "?phoneNumber=" + phoneNumber))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(expected)));
    }
    }
