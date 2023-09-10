package com.tonynguyen.a4pm;

public class LotteryOffice {
    String province;
    LotteryResults lotteryResults;

    public LotteryOffice(String province, LotteryResults lotteryResults) {
        this.province = province;
        this.lotteryResults = lotteryResults;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public LotteryResults getLotteryResults() {
        return lotteryResults;
    }

    public void setLotteryResults(LotteryResults lotteryResults) {
        this.lotteryResults = lotteryResults;
    }

    public String toString(){
        return this.province+"-"+lotteryResults.toString();
    }
}
