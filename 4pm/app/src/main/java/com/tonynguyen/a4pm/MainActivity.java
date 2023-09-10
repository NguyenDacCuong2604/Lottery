package com.tonynguyen.a4pm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Document doc;
    CalendarView calendarView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đặt locale tiếng Việt
        Locale vietnameseLocale = new Locale("vi", "VN");
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(vietnameseLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        progressBar = findViewById(R.id.progress);
        // Lấy ngày hiện tại
        Calendar currentDate = Calendar.getInstance();
        // Đặt ngày tối thiểu cho CalendarView là ngày hiện tại
        calendarView.setMaxDate(currentDate.getTimeInMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            connectWeb(setUrl(dayOfMonth, month+1, year));
        }
        );
    }
    private String setUrl(int day, int month, int year){
        return Constant.domain+"/ket-qua-xo-so/ngay-"+day+"-"+month+"-"+year;
    }
    private void connectWeb(String url){
        isLoading(true);
        new ConnectWebTask().execute(url);
    }
    private void onDocumentDownloaded(Document resultDoc) {
        if (resultDoc != null) {
            // Xử lý dữ liệu ở đây với resultDoc (nếu cần)
            Toast.makeText(MainActivity.this, "Connect lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();

            // Chuyển sang LotteryBoard và gửi Document qua Intent
            Intent intent = new Intent(MainActivity.this, LotteryBoard.class);
            intent.putExtra("resultDoc", resultDoc.toString()); // Chuyển Document thành chuỗi
            startActivity(intent);
        } else {
            isLoading(false);
            Toast.makeText(MainActivity.this, "Không truy cập được trang web", Toast.LENGTH_SHORT).show();
        }
    }
    private List<LotteryOffice> getLotteryBoards(Document document){
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
    private void isLoading(boolean progress){
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            calendarView.setVisibility(View.VISIBLE);
        }
    }
    private class ConnectWebTask extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            try {
                Connection con = Jsoup.connect(urls[0]);
                Document doc = con.get();
                if (con.response().statusCode() == 200) {
                    return doc;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document resultDoc) {
            // Trả kết quả Document về cho MainActivity
            onDocumentDownloaded(resultDoc);
        }
    }
}