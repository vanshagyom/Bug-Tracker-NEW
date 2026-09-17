package com.practice.OneYear.service.userService;

import com.practice.OneYear.dto.userDTOS.UserInputDTO;
import com.practice.OneYear.dto.userDTOS.UserOutputDTO;
import com.practice.OneYear.entity.User;
import com.practice.OneYear.entity.enums.UserRole;
import com.practice.OneYear.mapper.userMapper.UserMapper;
import com.practice.OneYear.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

//    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserOutputDTO> allUsers() {
//        logger.info("Got all users from DB.");
//        logger.error("Got all users from DB.");
//        logger.warn("Got all users from DB.");
//        logger.debug("Got all users from DB.");
//        logger.trace("Got all users from DB.");

        log.info("Got all users from DB.");
        log.error("Got all users from DB.");
        log.warn("Got all users from DB.");
        log.debug("Got all users from DB.");
        log.trace("Got all users from DB.");

        List<User> all = userRepository.findAll();
        List<UserOutputDTO> list = all.stream().map(x -> userMapper.toOutputDTOfromEntity(x)).toList();
        return list;
    }

    @Override
    public UserOutputDTO addUser(UserInputDTO userInputDTO, UserRole userRole) {
        User user = userMapper.toEntityFromInputDTO(userInputDTO);
        user.setUserRole(userRole);
        User save = userRepository.save(user);
        return userMapper.toOutputDTOfromEntity(save);
    }
}
