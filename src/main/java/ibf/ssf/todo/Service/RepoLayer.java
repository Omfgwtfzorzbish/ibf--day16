package ibf.ssf.todo.Service;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RepoLayer {
    
    @Autowired
    @Qualifier("redislinkappconfig")
    private RedisTemplate<String,String> template;

    public void save(String key,String value){
        template.opsForValue().set(key, value); 
    }

    public Optional<String> get(String key){
        return Optional.ofNullable(template.opsForValue().get(key));
    }
}
