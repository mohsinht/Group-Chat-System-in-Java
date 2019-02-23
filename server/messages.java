package server;

public class messages { //Circular data structure that only saves last 10 messages.
    String[] entries = new String[10];
    int start = 0;
    int size = 0;
    int index = 0;
    boolean k = false;
    public void addMsg(String msg){
        entries[index] = msg;
        index++;
        index = index%10;
        size++;
        if(size>10){
            start++;
            start = start%10;
        }
    }
    public String getMsg(int i){
        if(entries[i%10] == null){
            return "";
        }
        return entries[i%10];
    }
    public int size(){
        return size;
    }
    public int start(){
        return start;
    }
    
    public messages(){}
}
