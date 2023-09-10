package com.tonynguyen.a4pm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LotteryResults {
    String eighthPrize;
    String seventhPrize;
    List<String> sixthPrize;
    String fifthPrize;
    List<String> fourthPrize;
    List<String> thirdPrize;
    String secondPrize;
    String firstPrize;
    String specialPrize;

    public LotteryResults(String eighthPrize, String seventhPrize, List<String> sixthPrize, String fifthPrize, List<String> fourthPrize, List<String> thirdPrize, String secondPrize, String firstPrize, String specialPrize) {
        this.eighthPrize = eighthPrize;
        this.seventhPrize = seventhPrize;
        this.sixthPrize = sixthPrize;
        this.fifthPrize = fifthPrize;
        this.fourthPrize = fourthPrize;
        this.thirdPrize = thirdPrize;
        this.secondPrize = secondPrize;
        this.firstPrize = firstPrize;
        this.specialPrize = specialPrize;
    }
    public LotteryResults(){
        this.eighthPrize = "";
        this.seventhPrize = "";
        this.sixthPrize = new ArrayList<String>();
        this.fifthPrize = "";
        this.fourthPrize = new ArrayList<String>();
        this.thirdPrize = new ArrayList<String>();
        this.secondPrize = "";
        this.firstPrize = "";
        this.specialPrize = "";
    }

    public String getEighthPrize() {
        return eighthPrize;
    }

    public void setEighthPrize(String eighthPrize) {
        this.eighthPrize = eighthPrize;
    }

    public String getSeventhPrize() {
        return seventhPrize;
    }

    public void setSeventhPrize(String seventhPrize) {
        this.seventhPrize = seventhPrize;
    }

    public List<String> getSixthPrize() {
        return sixthPrize;
    }

    public void setSixthPrize(List<String> sixthPrize) {
        this.sixthPrize = sixthPrize;
    }

    public String getFifthPrize() {
        return fifthPrize;
    }

    public void setFifthPrize(String fifthPrize) {
        this.fifthPrize = fifthPrize;
    }

    public List<String> getFourthPrize() {
        return fourthPrize;
    }

    public void setFourthPrize(List<String> fourthPrize) {
        this.fourthPrize = fourthPrize;
    }

    public List<String> getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(List<String> thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public String getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        this.secondPrize = secondPrize;
    }

    public String getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        this.firstPrize = firstPrize;
    }

    public String getSpecialPrize() {
        return specialPrize;
    }

    public void setSpecialPrize(String specialPrize) {
        this.specialPrize = specialPrize;
    }
    public String toString(){
        return this.eighthPrize+"\n"+this.seventhPrize+"\n"+this.sixthPrize.toString()+"\n"+
                this.fifthPrize+"\n"+
                this.fourthPrize.toString()+"\n"+this.thirdPrize.toString()+"\n"+this.secondPrize+"\n"+
                this.firstPrize+"\n"+specialPrize;
    }
    public String getListLotterySixth(){
        String text="";
        for(String s : sixthPrize){
            text+=s+"\n";
        }
        return text.substring(0, text.length() - 1);
    }
    public String getListLotteryFourth(){
        String text="";
        for(String s : fourthPrize){
            text+=s+"\n";
        }
        return text.substring(0, text.length() - 1);
    }
    public String getListLotteryThird(){
        String text="";
        for(String s : thirdPrize){
            text+=s+"\n";
        }
        return text.substring(0, text.length() - 1);
    }
}
