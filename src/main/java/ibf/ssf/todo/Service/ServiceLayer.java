package ibf.ssf.todo.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ServiceLayer {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLayer.class);   
    @Autowired
    private RepoLayer taskRepo;

    
    public String save(String key, HashMap<String,String> todoList){
        List<String> tasks = new ArrayList<String>(todoList.values());
        Stream<String> stream = tasks.stream();
        String l = stream.collect(Collectors.joining("|"));
        taskRepo.save(key,l);
        return l;
    }

    public List<String> get(String key){
        Optional<String> opt =taskRepo.get(key);
        List<String> list = new ArrayList<String>();
        if (opt.isPresent())
            {String b = opt.get().replace("|", "-"); logger.info("Redis String " + b);
            String[] a = b.split("-");
            for(String s:a)
                {//s.replace("-", "|");
                list.add(s);}}
        return list;
    }

    public boolean hasKey(String key){
        Optional<String>opt = taskRepo.get(key); logger.info("FROM SERVICE haskey: " + key);
        return opt.isPresent();
    }


}
