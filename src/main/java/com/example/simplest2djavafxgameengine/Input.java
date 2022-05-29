package com.example.simplest2djavafxgameengine;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.lang.invoke.SerializedLambda;
import java.security.Key;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Consumer;
public class Input {
    public class KeyInfo{
        boolean set =false;
    }
    HashMap<KeyCode,KeyInfo> keysInfo = new HashMap<KeyCode,KeyInfo>();

    Input(KeyCode[] codesToListen){
        for(KeyCode c : codesToListen){
            keysInfo.put(c,new KeyInfo());
        }
    }
    public void KeyPressed(KeyEvent key){
        if(keysInfo.containsKey(key.getCode())){
            keysInfo.get(key.getCode()).set = true;
        }
    }
    public void KeyPressed(KeyEvent key, Consumer<KeyEvent> lambda){
        if(keysInfo.containsKey(key.getCode())){
            if(!keysInfo.get(key.getCode()).set)
                lambda.accept(key);
            keysInfo.get(key.getCode()).set = true;
        }
    }
    public void KeyReleased(KeyEvent key){
        if(keysInfo.containsKey(key.getCode())){
            keysInfo.get(key.getCode()).set = false;
        }
    }
    public void KeyReleased(KeyEvent key, Consumer<KeyEvent> lambda){
        if(keysInfo.containsKey(key.getCode())){
            if(keysInfo.get(key.getCode()).set)
                lambda.accept(key);
            keysInfo.get(key.getCode()).set = false;
        }
    }

    public boolean IsKeyPressed(KeyCode code){
        if(keysInfo.containsKey(code)){
            return keysInfo.get(code).set;
        }
        return false;
    }
}
