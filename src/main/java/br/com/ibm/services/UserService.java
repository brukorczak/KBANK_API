package br.com.ibm.services;

import br.com.ibm.exception.BadRequestException;
import br.com.ibm.exception.NotFoundException;
import br.com.ibm.persistence.dao.UserDao;
import br.com.ibm.persistence.dto.AddUserDto;
import br.com.ibm.persistence.dto.LoginDto;
import br.com.ibm.persistence.dto.PutUserDto;
import br.com.ibm.persistence.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {
    @Inject
    UserDao userDao;

    public List<User> getUsers() {
        return this.userDao.getAll();
    }

    public void addUser(AddUserDto userData) {

        if (!userDao.isCpfUnique(userData.getCpf())) {
            throw new BadRequestException("CPF já cadastrado.");
        }

        if (!userDao.isPhoneUnique(userData.getPhone())) {
            throw new BadRequestException("Telefone já cadastrado.");
        }

        User user = new User();
        user.setName(userData.getName());
        user.setAge(userData.getAge());
        user.setPhone(userData.getPhone());
        user.setAddress(userData.getAddress());
        user.setCpf(userData.getCpf());
        user.setPassword(userData.getPassword());
        this.userDao.save(user);
    }

    public Optional<User> deleteUser(Long id) {
        Optional<User> userOptional = this.userDao.delete(id);

        if (userOptional.isPresent()) {
            return userOptional;
        } else {
            throw new NotFoundException("Usuário não encontrado para exclusão");
        }
    }

    public void updatedUser(Long id, PutUserDto userData) {
        Optional<User> userOptional = this.userDao.getUserById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(userData.getName());
            existingUser.setAge(userData.getAge());
            existingUser.setPhone(userData.getPhone());
            existingUser.setAddress(userData.getAddress());

            this.userDao.update(existingUser);
        } else {
            throw new BadRequestException("Usuário não encontrado para atualização");
        }
    }

    //login
    public Optional<User> loginUser(LoginDto loginDto) {
        Optional<User> userOptional = userDao.getUserByCpf(loginDto.getCpf());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginDto.getPassword())) {
                return Optional.of(user);
            }
        }
        throw new NotFoundException("Usuário não encontrado");
    }

    public Optional<User> getUserInfoById(Long id) {
        return userDao.getUserInfoById(id);
    }


}
