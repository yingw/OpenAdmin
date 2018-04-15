# 开发记录

创建项目，依赖：web, jpa, h2, lombok, devtools

创建简单 User 对象、repository、Service、Controller、DataLoader

审计、缓存

```java
@EnableCaching
@EnableJpaAuditing
```

优化完整缓存
```java
@CacheConfig(cacheNames = "users")
@Cacheable
@Cacheable(key = "'id:' + #id")
@Caching
@CacheEvict
```

切换缓存服务

API 测试（Newman） https://www.getpostman.com/