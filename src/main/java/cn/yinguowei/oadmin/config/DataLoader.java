package cn.yinguowei.oadmin.config;

import cn.yinguowei.oadmin.entity.User;
import cn.yinguowei.oadmin.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Component
public class DataLoader implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final UserRepository userRepository;

    DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.debug("DataLoader.run: args = {}", strings.toString());
        /* User */
        userRepository.save(new User("yinguowei", "Yin Guo Wei", "yinguowei@gmail.com"));
        userRepository.save(new User("jojo", "Yin Xiao Qin", "yinxiaoqin2009@gmail.com"));
        userRepository.save(new User("anna", "Qian Yan", "annaleaf@qq.com"));
    }

}