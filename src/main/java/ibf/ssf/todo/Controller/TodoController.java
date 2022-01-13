package ibf.ssf.todo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import ibf.ssf.todo.Service.ServiceLayer;
import ibf.ssf.todo.model.Task;
import ibf.ssf.todo.model.TaskList;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
//@RequestMapping(path="/task", produces =" MediaType.TEXT_HTML_VALUE")
public class TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
    TaskList taskList = new TaskList(); 
    Task task = new Task();
    String userIn;

    @Autowired
    ServiceLayer service;

    @GetMapping("/")
    public String showForm(Model model){
        
    
        model.addAttribute("tasklist",taskList);
        return "index";
    }

    @PostMapping("/username")
    public String chkStoreUsername(@ModelAttribute TaskList tasklist, Model model){
        userIn = tasklist.getUsername(); logger.info("username entered: /" + userIn +"/ ispresent: " + service.hasKey(userIn));
        if(service.hasKey(userIn)==false)
            {TaskList tasknum = new TaskList(); tasknum.setTaskNum("");
            taskList.setTaskList(new HashMap<String,String>());
            taskList.setI(0);
            
            model.addAttribute("task", task);
            model.addAttribute("tasklist", taskList.getTaskList());
            model.addAttribute("tasknum", tasknum);
                return "response";} else {
                   List<String> redisResponse = service.get(userIn);
                   model.addAttribute("userlist", redisResponse);
                   model.addAttribute("username",userIn);
                    return "userresult";}

    }

    @PostMapping("/add")
    public String submitTask(@ModelAttribute Task task,Model model){
        String userIn = task.getTask();
        logger.info("task added"+task.getTask()); 
        
            if(taskList==null|| taskList.isEmpty())
                {taskList.addToList("Your Todo List");taskList.addToList(userIn);
                }
                else{taskList.addToList(userIn);}
               
                 task.setTask("");
                
                TaskList tasknum = new TaskList(); tasknum.setTaskNum("");

        model.addAttribute("task", task);
        model.addAttribute("tasklist", taskList.getTaskList());
        model.addAttribute("tasknum", tasknum);

        return "response";
    }
    @RequestMapping(value="/deleteorsave", method=RequestMethod.POST, params="action=delete")
    public String deleteTask(@ModelAttribute TaskList tasknum, Model model){
        String userIn = tasknum.getTaskNum(); 
        logger.info("task to delete "+tasknum.getTaskNum());
        
            if(taskList==null || taskList.isEmpty())
                {taskList.addToList("Your Todo List is empty");}
                else{taskList.removeFromList(userIn);}
               
                tasknum.setTaskNum(String.valueOf(1));
        
        model.addAttribute("task", task);        
        model.addAttribute("tasknum", tasknum);
        model.addAttribute("tasklist", taskList.getTaskList());
        return "response";
    }

    @RequestMapping(value="/deleteorsave", method=RequestMethod.POST, params="action=save")
    public String saveTodo(Model model){
        HashMap<String,String> getList = taskList.getTaskList();
        service.save(userIn, getList);
        List<String> redisResponse = service.get(userIn);
                
                TaskList tasknum = new TaskList(); tasknum.setTaskNum("");

        model.addAttribute("userlist", redisResponse);
        model.addAttribute("username", userIn);

        return "userresult";
    }
}
