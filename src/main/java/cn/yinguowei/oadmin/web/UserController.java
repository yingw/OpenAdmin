package cn.yinguowei.oadmin.web;

import cn.yinguowei.oadmin.entity.User;
import cn.yinguowei.oadmin.repository.UserRepository;
import cn.yinguowei.oadmin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yin Guo Wei 2018/3/27.
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final UserService userService;
//    private static final String USER_FORM = "user/userForm";
//    private static final String USER_LIST = "user/userList";
//    private static final String USER_GRANT = "user/userGrant";
//    private static final String USER_DETAILS = "user/userDetails";

    public UserController(UserRepository userRepository, UserService userService) {
        this.userService = userService;
        this.userRepository = userRepository;
        logger.debug("UserController.UserController");
    }

    // ?? what for?
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/users")
    public List<User> queryUsers(
            @RequestParam(defaultValue = "") String name) {
        logger.debug(">>>> UserController.queryUsers， name = " + name);
        // TODO find by name
        List<User> users;
        if (name.equals("")) {
            users = userService.findAllUsers();
        } else {
            users = userRepository.findByUsernameLikeOrFullnameLike("%" + name + "%", "%" + name + "%");
        }
        logger.debug("UserController.queryUsers <<<<");
        return users;
    }

//    @ApiOperation(value = "新增保存", notes = "新增用户，并保存入库")
    @PostMapping("/users")
    public User createUser(
//            @ApiParam(name = "用户对象", value = "用户对象，必要字段：姓名、用户名", required = true)
            @RequestBody User user) {
        logger.info("UserController.createUser()： user = [" + user + "]");
//        user.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
//        userRepository.save(user);
        userService.createOrUpdateUser(user);
        return user;
    }

//    @ApiOperation(value = "详情页面")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") long id) {
        System.out.println(">>>> UserController.getUser, id: " + id);
        // TODO test exists
        User user = userService.getUserById(id);
        System.out.println("UserController.getUser <<<<");
//        return new ModelAndView(USER_DETAILS, "user", user);
        return user;
    }

//    @ApiOperation(value = "编辑，保存")
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        // TODO mapper
        User u = userRepository.findById(user.getId()).get();
        u.setUsername(user.getUsername());
        u.setFullname(user.getFullname());
        u.setEmail(user.getEmail());
//        u.setRoles(user.getRoles());
        u.setActive(user.getActive());
        u.setGender(user.getGender());
//        userRepository.save(u);
        userService.createOrUpdateUser(u);
        /*
        userRepository.findOneByUsername(user.getUsername()).ifPresent(u -> {
            u.setStatus(user.getStatus());
            u.setFullname(user.getFullname());
            u.getRoles().clear();
            u.getRoles().addAll(
                    user.getRoles().stream().map(roleRepository::findOneByName)
                            .collect(Collectors.toSet()));
//            u.setRoles(user.getRoles());
            userRepository.save(u);
        });
*/
//                }
//                user.setId(id);
//        userRepository.save(user);
        return u;
    }

//    @ApiOperation(value = "删除用户")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.debug("UserController.deleteUser {}", id);
//        userRepository.deleteById(id);
        userService.deleteUser(id);
//        return "redirect:/users";
    }


//    @ApiOperation(value = "激活用户")
//    @PreAuthorize("hasRole('admin')")
    @PutMapping("/users/{id}/enable")
    @ResponseBody
    public void enableUser(@PathVariable("id") Long id) {
        setUserStatus(id, true);
    }

//    @ApiOperation(value = "关闭用户（非删除）")
    @PutMapping("/users/{id}/disable")
    @ResponseBody
    public void disableUser(@PathVariable("id") Long id) {
        setUserStatus(id, false);
    }

    private void setUserStatus(@PathVariable("id") Long id, boolean active) {
        User user = userRepository.findById(id).get();
        user.setActive(active);
        userRepository.save(user);
    }

    @ResponseBody
    @PostMapping("/users/{id}/grant")
    public User grantUser(@PathVariable Long id, @RequestParam(required = false) String[] roleIds) {
        User user = userService.getUserById(id);

        if (roleIds == null || roleIds.length == 0) {
//            user.getRoles().clear();
            userService.createOrUpdateUser(user);
            return user;
        }
//        Set<Role> roles = new HashSet<>();
//        for (String roleId : roleIds) {
//            if (roleId.equals("")) {
//                continue;
//            }
//            roles.add(roleRepository.findById(Integer.parseInt(roleId)).get());
//        }
//        user.setRoles(roles);
        userService.createOrUpdateUser(user);
        return user;
    }

}