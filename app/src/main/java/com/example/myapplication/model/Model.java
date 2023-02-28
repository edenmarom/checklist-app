package com.example.myapplication.model;

public class Model {
    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }
    private Model(){
//        for(int i=0;i<20;i++){
//            addList(new ListItem("name " + i));
//        }
    }
//    List<ListItem> data = new LinkedList<>();
//    public List<ListItem> getAllList(){
//        return data;
//    }
//
//    public void addList(ListItem l){
//        data.add(l);
//    }
}
