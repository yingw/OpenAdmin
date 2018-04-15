package cn.yinguowei.oadmin.service;

import cn.yinguowei.oadmin.entity.User;
import cn.yinguowei.oadmin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author yinguowei 2017/7/25.
 */
//@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final String USERS_ALL_CACHE = "users";
    private final String USER_BY_ID_CACHE = "userById";
    private final String USER_BY_LOGIN_CACHE = "userByLogin";

    private final UserRepository userRepository;
    private final CacheManager cacheManager;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(USERS_ALL_CACHE)
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        logger.debug(">>>> UserService.findAllUsers >>>>");
        List<User> users = userRepository.findAll();
        logger.debug("<<<< UserService.findAllUsers <<<<");
        return users;
    }

    @Cacheable(value = USER_BY_ID_CACHE, key = "#id")
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        System.out.println(">>>> UserService.getUserById, id = " + id);
        final User user = userRepository.findById(id).get();
        System.out.println("UserService.getUserById <<<<");
        return user;
    }

    @Cacheable(cacheNames = USER_BY_LOGIN_CACHE, key = "#username")
    public User getUserByUsername(String username) {
        System.out.println("UserService.getUserByUsername, username = " + username);
        final User user = userRepository.findOneByUsername(username).get();
        System.out.println("UserService.getUserByUsername");
        return user;
    }
//private const static final String emptyKey = SimpleKey.EMPTY.toString();

    @Caching(put = {
            @CachePut(cacheNames = USER_BY_ID_CACHE, key = "#user.id"),
            @CachePut(cacheNames = USER_BY_LOGIN_CACHE, key = "#user.username")},
            evict = {@CacheEvict(cacheNames = USERS_ALL_CACHE, allEntries = true)})
    public User createOrUpdateUser(User user) {
        // TODO: username PK
        System.out.println(">>>> UserService.createUser, user = " + user);
        userRepository.save(user);
        System.out.println("UserService.createUser <<<<");
        return user;
    }

    public void deleteUser(long id) {
        System.out.println(">>>> UserService.deleteUser");
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            // 手动清除缓存（因为参数里没有 username）
            cacheManager.getCache(USER_BY_ID_CACHE).evict(user.get().getId());
            cacheManager.getCache(USER_BY_LOGIN_CACHE).evict(user.get().getUsername());
            cacheManager.getCache(USERS_ALL_CACHE).clear();
        } else {
            logger.error("Not found");
            // TODO: exception.
        }
        System.out.println("UserService.deleteUser <<<<");
    }
}
