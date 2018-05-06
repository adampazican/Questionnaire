package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConfigReader {
    private HashMap<String, String> cfg;

    public ConfigReader(){
        this.cfg = new HashMap<>();
        this.read("config.txt");
    }

    private void read(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();

            while(line != null){
                String[] lineSplitted = line.split("=");
                this.cfg.put(lineSplitted[0], lineSplitted[1]);
                line = br.readLine();
            }
        }
        catch(FileNotFoundException e){e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
    }

    public String getValue(String key){
        return this.cfg.get(key);
    }
}
