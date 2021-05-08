//package com.example.jogo.ServiceImpl;
//
//import com.example.jogo.Entity.User;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.Future;
//
//@Service
//public class UserServiceImpl {
//
//    @Resource
//    private UserRepository userRepository;
//    @Resource
//    private RedisTemplate<String,Object> redisTemplate;
//    private static final String ALL_USERS = "user_list";
//
//    @Override
//    public void addUser(String username, String password) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        userRepository.save(user);
//    }
//
//    @Override
//    public boolean verify(String username,String password){
//        User user = userRepository.findUserByUsernameAndPassword(username,password);
//        return user!=null;
//    }
//
//    @Override
//    public User searchByUsername(String username){
//        List<User> users = userRepository.findUsersByUsername(username);
//        if(null==users||users.size()==0) return null;
//        return users.get(0);
//    }
//
//
//    public List<User> findAll(){
//        long start = System.currentTimeMillis();
//        List<User> users = userRepository.findAll();
//        System.out.println("耗时:"+(System.currentTimeMillis()-start)+"ms");
//        return users;
//    }
//
//
//    @Override
//    @Async
//    public Future<List<User>> asynFindAll(){
//        long start = System.currentTimeMillis();
//        List<User> users = userRepository.findAll();
//        System.out.println("耗时:"+(System.currentTimeMillis()-start)+"ms");
//        return new AsyncResult<>(users);
//    }
//
//    // 导入缓存
//    @Override
//    public boolean loadCache() {
//        List<User> users = this.findAll();
//        redisTemplate.delete(ALL_USERS);
//        redisTemplate.opsForList().leftPushAll(ALL_USERS,users);
//        return true;
//    }
//
//}
