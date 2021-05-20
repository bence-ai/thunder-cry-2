package com.codecool.dungeoncrawl.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class JsonManager {
    public JsonManager() {
    }


    public String serialize(Object obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serializedGson = gson.toJson(obj);
        System.out.println(serializedGson);
        return serializedGson;
    }

    private void writeToJsonFile(String jsonString, File file) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
        writer.write(jsonString);
        writer.close();
    }

    public void saveToProjectFile(File file, Object obj){
        String serializedJson = serialize(obj);
        try {
            writeToJsonFile(serializedJson, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






//    public static void main(String[] args) {
//
//        JsonManager json = new JsonManager();
//        json.serialize();
//    }

}
