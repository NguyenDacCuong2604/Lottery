
package com.tonynguyen.a4pm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class LotteryBoard extends AppCompatActivity {
    TextView dateView, notificationView;
    List<LotteryOffice> lotteryOfficeList;
    TableLayout tableLayout;
    String day;
    TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f); // Thiết lập layout_weight = 1\
    TableRow.LayoutParams paramsPrize = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.7f); // Thiết lập layout_weight = 1\

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_board);
        dateView = findViewById(R.id.date);
        tableLayout = findViewById(R.id.board);
        notificationView = findViewById(R.id.notificationView);
        getDataOfDoc(getDoc());
        drawBoard();
        AppCompatButton button = findViewById(R.id.btnBack);
        button.setOnClickListener((view) -> onBackPressed());
    }

    private void getDataOfDoc(Document document) {
        //setDate
        String date = document.select("h1.pagetitle").first().text();
        dateView.setText(date);
        //setDay
        Element element = document.select("td.thu").first();
        day= element==null ? "":element.text();
        lotteryOfficeList = getProvinces(document);
    }

    private void drawBoard() {
        if(lotteryOfficeList.isEmpty()) return;
        //Header
        drawHeader();
        //EighthPrize
        drawEighthPrize();
        //SeventhPrize
        drawSeventhPrize();
        //SixthPrize
        drawSixthPrize();
        //FifthPrize
        drawFifthPrize();
        //FourthPrize
        drawFourthPrize();
        //ThridPrize
        drawThirdPrize();
        //SecondPrize
        drawSecondPrize();
        //FirstPrize
        drawFirstPrize();
        //SpecialPrize
        drawSpecialPrize();
    }

    private void drawHeader() {
        TableRow header = findViewById(R.id.header);
        TextView dayTextView = new TextView(this);
        dayTextView.setText(day);
        dayTextView.setBackgroundResource(R.drawable.table_border);
        dayTextView.setTextAppearance(R.style.header);
        dayTextView.setGravity(Gravity.CENTER);
        dayTextView.setLayoutParams(paramsPrize);
        header.addView(dayTextView);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getProvince());
            textView.setTextAppearance(R.style.header);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            header.addView(textView);
        }
    }

    private void drawEighthPrize() {
        TableRow eighth = findViewById(R.id.eighthPrize);
        TextView eighthPrizeText = new TextView(this);
        eighthPrizeText.setText("Giải 8");
        eighthPrizeText.setTextAppearance(R.style.prize);
        eighthPrizeText.setGravity(Gravity.CENTER);
        eighthPrizeText.setBackgroundResource(R.drawable.table_border);
        eighthPrizeText.setLayoutParams(paramsPrize);
        eighth.addView(eighthPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getEighthPrize());
            textView.setTextAppearance(R.style.eighthPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            eighth.addView(textView);
        }
    }

    private void drawSeventhPrize() {
        TableRow seventh = findViewById(R.id.seventhPrize);
        TextView seventhPrizeText = new TextView(this);
        seventhPrizeText.setText("Giải 7");
        seventhPrizeText.setTextAppearance(R.style.prize);
        seventhPrizeText.setGravity(Gravity.CENTER);
        seventhPrizeText.setBackgroundResource(R.drawable.table_border);
        seventhPrizeText.setLayoutParams(paramsPrize);
        seventh.addView(seventhPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getSeventhPrize());
            textView.setTextAppearance(R.style.seventhPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            seventh.addView(textView);
        }
    }

    private void drawSixthPrize() {
        TableRow sixthPrize = findViewById(R.id.sixthPrize);
        TextView sixthPrizeText = new TextView(this);
        sixthPrizeText.setText("Giải 6");
        sixthPrizeText.setTextAppearance(R.style.prize);
        sixthPrizeText.setGravity(Gravity.CENTER);
        sixthPrizeText.setBackgroundResource(R.drawable.table_border);
        sixthPrizeText.setLayoutParams(paramsPrize);
        sixthPrize.addView(sixthPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getListLotterySixth());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            sixthPrize.addView(textView);
        }
    }

    private void drawFifthPrize() {
        TableRow fifthPrize = findViewById(R.id.fifthPrize);
        TextView fifthPrizeText = new TextView(this);
        fifthPrizeText.setText("Giải 5");
        fifthPrizeText.setTextAppearance(R.style.prize);
        fifthPrizeText.setGravity(Gravity.CENTER);
        fifthPrizeText.setBackgroundResource(R.drawable.table_border);
        fifthPrizeText.setLayoutParams(paramsPrize);
        fifthPrize.addView(fifthPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getFifthPrize());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            fifthPrize.addView(textView);
        }
    }

    private void drawFourthPrize() {
        TableRow fourthPrize = findViewById(R.id.fourthPrize);
        TextView fourthPrizeText = new TextView(this);
        fourthPrizeText.setText("Giải 4");
        fourthPrizeText.setTextAppearance(R.style.prize);
        fourthPrizeText.setGravity(Gravity.CENTER);
        fourthPrizeText.setBackgroundResource(R.drawable.table_border);
        fourthPrizeText.setLayoutParams(paramsPrize);
        fourthPrize.addView(fourthPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getListLotteryFourth());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            fourthPrize.addView(textView);
        }
    }

    private void drawThirdPrize() {
        TableRow thirdPrize = findViewById(R.id.thirdPrize);
        TextView thirdPrizeText = new TextView(this);
        thirdPrizeText.setText("Giải 3");
        thirdPrizeText.setTextAppearance(R.style.prize);
        thirdPrizeText.setGravity(Gravity.CENTER);
        thirdPrizeText.setBackgroundResource(R.drawable.table_border);
        thirdPrizeText.setLayoutParams(paramsPrize);
        thirdPrize.addView(thirdPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getListLotteryThird());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            thirdPrize.addView(textView);
        }
    }

    private void drawSecondPrize() {
        TableRow secondPrize = findViewById(R.id.secondPrize);
        TextView secondPrizeText = new TextView(this);
        secondPrizeText.setText("Giải 2");
        secondPrizeText.setTextAppearance(R.style.prize);
        secondPrizeText.setGravity(Gravity.CENTER);
        secondPrizeText.setBackgroundResource(R.drawable.table_border);
        secondPrizeText.setLayoutParams(paramsPrize);
        secondPrize.addView(secondPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getSecondPrize());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            secondPrize.addView(textView);
        }
    }

    private void drawFirstPrize() {
        TableRow firstPrize = findViewById(R.id.firstPrize);
        TextView firstPrizeText = new TextView(this);
        firstPrizeText.setText("Giải 1");
        firstPrizeText.setTextAppearance(R.style.prize);
        firstPrizeText.setGravity(Gravity.CENTER);
        firstPrizeText.setBackgroundResource(R.drawable.table_border);
        firstPrizeText.setLayoutParams(paramsPrize);
        firstPrize.addView(firstPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getFirstPrize());
            textView.setTextAppearance(R.style.differentPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            firstPrize.addView(textView);
        }
    }

    private void drawSpecialPrize() {
        TableRow specialPrize = findViewById(R.id.specialPrize);
        TextView specialPrizeText = new TextView(this);
        specialPrizeText.setText("Giải ĐB");
        specialPrizeText.setTextAppearance(R.style.prize);
        specialPrizeText.setGravity(Gravity.CENTER);
        specialPrizeText.setBackgroundResource(R.drawable.table_border);
        specialPrizeText.setLayoutParams(paramsPrize);
        specialPrize.addView(specialPrizeText);
        for (LotteryOffice lotteryOffice : lotteryOfficeList) {
            TextView textView = new TextView(this);
            textView.setText(lotteryOffice.getLotteryResults().getSpecialPrize());
            textView.setTextAppearance(R.style.specialPrize);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.table_border);
            specialPrize.addView(textView);
        }
    }

    private Document getDoc() {
        String resultDocString = getIntent().getStringExtra("resultDoc");
        if (resultDocString != null) {
            return Jsoup.parse(resultDocString);
        } else {
            return null;
        }
    }

    private void showNotification(boolean isShow){
        if(isShow){
            tableLayout.setVisibility(View.INVISIBLE);
            notificationView.setVisibility(View.VISIBLE);
        }
        else{
            tableLayout.setVisibility(View.VISIBLE);
            notificationView.setVisibility(View.INVISIBLE);
        }
    }

    private List<LotteryOffice> getProvinces(Document document) {
        List<LotteryOffice> provinces = new ArrayList<LotteryOffice>();
        if (document == null) return provinces;
        Element element = document.select("div.kqxs_content").first();
        if(element==null){
            showNotification(true);
            return provinces;
        }
        Elements elements = element.select("table.tblKQTinh");
        for (Element province : elements) {
            String provinceName = province.select("td.tentinh span.namelong").text().trim();
            Elements prizes = province.select("tr td");
            LotteryResults lotteryResults = new LotteryResults();
            lotteryResults.setEighthPrize(prizes.select(".giai_tam .dayso").text());
            lotteryResults.setSeventhPrize(prizes.select(".giai_bay .dayso").text());
            Elements sixthPrize = prizes.select(".giai_sau div.dayso");
            for (Element e : sixthPrize) {
                lotteryResults.getSixthPrize().add(e.text());
            }
            lotteryResults.setFifthPrize(prizes.select(".giai_nam .dayso").text());
            Elements fourthPrize = prizes.select(".giai_tu div.dayso");
            for (Element e : fourthPrize) {
                lotteryResults.getFourthPrize().add(e.text());
            }
            Elements thirdPrize = prizes.select(".giai_ba div.dayso");
            for (Element e : thirdPrize) {
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

    private String formatDate(int day, int month, int year) {
        String date = "";
        date += day < 10 ? "0" + day : day;
        date += "/";
        date += month < 10 ? "0" + month : month;
        date += "/";
        date += year;
        return date;
    }

}