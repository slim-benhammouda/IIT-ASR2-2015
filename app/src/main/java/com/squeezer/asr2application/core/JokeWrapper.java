package com.squeezer.asr2application.core;


import com.google.gson.annotations.SerializedName;

public class JokeWrapper {

    @SerializedName("type")
    private String mType;
    @SerializedName("value")
    private Value mValue;


    public String getJoke() {
        return mValue.getJoke();
    }


    class Value {

        @SerializedName("joke")
        private String mJoke;

        String getJoke(){
            return mJoke;
        }



    }


}
