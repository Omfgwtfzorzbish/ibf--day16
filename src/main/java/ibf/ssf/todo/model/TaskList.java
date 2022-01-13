package ibf.ssf.todo.model;

import java.util.HashMap;


public class TaskList {
    public HashMap<String, String> taskList = new HashMap<String, String>();
    private String taskNum;
    private int i = 0; 
    private String username = "";

    public int getI() {
        return this.i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setTaskList(HashMap<String,String> taskList) {
        this.taskList = taskList;
    }

    public String getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public void addToList(String value){
        i++;
        taskList.put(Integer.toString(i)+". ", value);
    }
    
    public void removeFromList(String key){
        taskList.remove(key+". ");
    }

    public HashMap<String,String> getTaskList(){
        return taskList;
    }
    public String getSize(){
       String s = Integer.toString(taskList.size());
        return s;
    }
    public Boolean isEmpty(){
        return taskList.isEmpty();
    }
    
}