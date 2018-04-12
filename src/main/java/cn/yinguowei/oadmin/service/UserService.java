package cn.yinguowei.oadmin.service;

import cn.yinguowei.oadmin.entity.User;
import cn.yinguowei.oadmin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yinguowei 2017/7/25.
 */
@Service
@Transactional(rollbackFor = Exception.class)
@CacheConfig(cacheNames = "users")
public class UserService {
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.debug(">>>> UserService.findAllUsers >>>>");
        List<User> users = userRepository.findAll();
        logger.debug("<<<< UserService.findAllUsers <<<<");
        return users;
    }

    @Cacheable(key = "'id:' + #id")
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        System.out.println(">>>> UserService.getUserById");
        System.out.println("id = " + id);
        final User user = userRepository.findById(id).get();
        System.out.println("UserService.getUserById <<<<");
        return user;
    }

    @Cacheable(key = "'username:' + #username")
    public User getUserByUsername(String username) {
        System.out.println("UserService.getUserByUsername");
        System.out.println("username = " + username);
        final User user = userRepository.findOneByUsername(username).get();
        System.out.println("UserService.getUserByUsername");
        return user;
    }

    //    @Cacheable
//    @CacheEvict(allEntries = true)
    @Caching(put = {@CachePut(key = "'id:' + #user.id"), @CachePut(key = "'username:' + #user.username")},
            evict = {@CacheEvict("users")})
    public User createUser(User user) {
        // TODO: username PK
        System.out.println(">>>> UserService.createUser");
        System.out.println("user = " + user);
        userRepository.save(user);
        System.out.println("UserService.createUser <<<<");
        return user;
    }

    //    @CacheEvict(allEntries = true)
    @Caching(put = {@CachePut(key = "'id:' + #user.id"), @CachePut(key = "'username:' + #user.username")},
            evict = {@CacheEvict("users")})
    public User updateUser(User user) {
        System.out.println(">>>> UserService.updateUser");
        System.out.println("user = " + user);
        userRepository.save(user);
        System.out.println("UserService.updateUser <<<<");
        return user;
    }

    @CacheEvict(allEntries = true)
    public void deleteUser(long id) {
        System.out.println(">>>> UserService.deleteUser");
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            logger.error("Not found");
            // TODO: exception.
        }
        System.out.println("UserService.deleteUser <<<<");
    }
}
