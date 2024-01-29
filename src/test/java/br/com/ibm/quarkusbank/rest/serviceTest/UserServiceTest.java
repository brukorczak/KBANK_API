package br.com.ibm.quarkusbank.rest.serviceTest;

import br.com.ibm.exception.BadRequestException;
import br.com.ibm.exception.NotFoundException;
import br.com.ibm.persistence.dao.UserDao;
import br.com.ibm.persistence.dto.AddUserDto;
import br.com.ibm.persistence.model.Account;
import br.com.ibm.persistence.model.AccountType;
import br.com.ibm.persistence.model.User;
import br.com.ibm.services.UserService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserDao userDao;

    @Inject
    UserService userService;

    @Test
    @Transactional
    public void testAddUser() {
        // Prepara dados de teste
        AddUserDto userData = new AddUserDto();
        userData.setName("Test User");
        userData.setAge(25);
        userData.setPhone("123456789");
        userData.setAddress("Test Address");
        userData.setAccountType(AccountType.CURRENT);

        // Testa o método addUser
        userService.addUser(userData);

        // Verifica se o usuário foi adicionado ao banco de dados
        User user = userDao.getUserById(1L).orElse(null);
        assertNotNull(user);

        // Verifica se a conta associada também foi adicionada
        assertNotNull(user.getAccount());
    }

//    @Test
//    @Transactional
//    public void testDeleteUser() {
//        // Preparar dados de teste
//        AddUserDto userData = new AddUserDto();
//        userData.setName("Usuário de Teste");
//        userData.setAge(25);
//        userData.setPhone("123456789");
//        userData.setAddress("Endereço de Teste");
//        userData.setAccountType(AccountType.CURRENT);
//
//        // Adicionar um usuário
//        userService.addUser(userData);
//
//        // Deletar o usuário
//        Optional<User> deletedUser = userService.deleteUser(1L);
//
//        // Verificar se o usuário foi deletado
//        assertTrue(deletedUser.isPresent());
//        assertEquals("Usuário de Teste", deletedUser.get().getName());
//    }


    @Test
    @Transactional
    public void testUpdateUser() {
        // Preparar dados de teste
        AddUserDto userData = new AddUserDto();
        userData.setName("Usuário de Teste");
        userData.setAge(25);
        userData.setPhone("123456789");
        userData.setAddress("Endereço de Teste");
        userData.setAccountType(AccountType.CURRENT);

        // Adicionar um usuário
        userService.addUser(userData);

        // Atualizar o usuário
        AddUserDto updatedUserData = new AddUserDto();
        updatedUserData.setName("Usuário Atualizado");
        updatedUserData.setAge(30);
        updatedUserData.setPhone("987654321");
        updatedUserData.setAddress("Endereço Atualizado");

        userService.updatedUser(1L, updatedUserData);

        // Verificar se o usuário foi atualizado
        User updatedUser = userDao.getUserById(1L).orElse(null);
        assertNotNull(updatedUser);
        assertEquals("Usuário Atualizado", updatedUser.getName());
        assertEquals(30, updatedUser.getAge());
        assertEquals("987654321", updatedUser.getPhone());
        assertEquals("Endereço Atualizado", updatedUser.getAddress());
    }

    @Test
    @Transactional
    public void testUpdateUserNotFound() {
        // Tentar atualizar um usuário que não existe
        AddUserDto updatedUserData = new AddUserDto();
        updatedUserData.setName("Usuário Atualizado");
        updatedUserData.setAge(30);
        updatedUserData.setPhone("987654321");
        updatedUserData.setAddress("Endereço Atualizado");

        assertThrows(BadRequestException.class, () -> userService.updatedUser(1L, updatedUserData));
    }

//    @Test
//    @Transactional
//    public void testDeleteUserNotFound() {
//        // Tentar deletar um usuário que não existe
//        assertThrows(NotFoundException.class, () -> userService.deleteUser(1L));
//    }
}
