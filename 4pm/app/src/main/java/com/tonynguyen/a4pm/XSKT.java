package com.tonynguyen.a4pm;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XSKT {
    public static String domain = "https://xosohomnay.com.vn/";
    public static void main(String[] args) {
        String url = setUrl(5,9,2023);
        System.out.println(url);
        Document document = getDocument(url);
        List<LotteryOffice> list = getProvinces(document);
        if(!list.isEmpty()){
            show(list);
        }
    }
    public static Document getDocument(String url){
        try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();
            if(con.response().statusCode() == 200){
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
    public static String setUrl(int day, int month, int year){
        return domain+"/ket-qua-xo-so/ngay-"+day+"-"+month+"-"+year;
    }
    public static List<LotteryOffice> getProvinces(Document document){
        List<LotteryOffice> provinces = new ArrayList<LotteryOffice>();
        if(document==null) return provinces;
        Element element = document.select("div.kqxs_content").first();
        assert element != null;
        Elements elements = element.select("table.tblKQTinh");
        for (Element province : elements) {
            String provinceName = province.select("td.tentinh span.namelong").text().trim();
            Elements prizes = province.select("tr td");
            LotteryResults lotteryResults = new LotteryResults();
            lotteryResults.setEighthPrize(prizes.select(".giai_tam .dayso").text());
            lotteryResults.setSeventhPrize(prizes.select(".giai_bay .dayso").text());
            Elements sixthPrize = prizes.select(".giai_sau div.dayso");
            for(Element e : sixthPrize){
                lotteryResults.getSixthPrize().add(e.text());
            }
            lotteryResults.setFifthPrize(prizes.select(".giai_nam .dayso").text());
            Elements fourthPrize = prizes.select(".giai_tu div.dayso");
            for(Element e : fourthPrize){
                lotteryResults.getFourthPrize().add(e.text());
            }
            Elements thirdPrize = prizes.select(".giai_ba div.dayso");
            for(Element e : thirdPrize){
                lotteryResults.getThirdPrize().add(e.text());
            }
            lotteryResults.setSecondPrize(prizes.select(".giai_nhi .dayso").text());
            lotteryResults.setFirstPrize(prizes.select(".giai_nhat .dayso").text());
            lotteryResults.setSpecialPrize(prizes.select(".giai_dac_biet .dayso").text());
            LotteryOffice lotteryOffice = new LotteryOffice(provinceName, lotteryResults);
            provinces.add(lotteryOffice);
        }
        return provinces;
    }
    public static void show(List<LotteryOffice> list){
        for(LotteryOffice lotteryOffice: list){
            System.out.println(lotteryOffice.toString());
        }
    }
}
