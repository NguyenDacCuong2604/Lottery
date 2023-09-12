package com.tonynguyen.a4pm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
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
import java.io.Serializable;
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
        //Notification
        createNotificationChannel();
        setAlarm();

        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        progressBar = findViewById(R.id.progress);
        // Lấy ngày hiện tại
        Calendar currentDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        //Date tư hien tai tro ve truoc 30 ngay
        minDate.add(Calendar.DATE, -30);
        // Đặt ngày tối thiểu cho CalendarView là ngày hiện tại
        calendarView.setMaxDate(currentDate.getTimeInMillis());
        calendarView.setMinDate(minDate.getTimeInMillis());
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                    connectWeb(setUrl(dayOfMonth, month + 1, year));
                }
        );

    }

    private String setUrl(int day, int month, int year) {
        return Constant.domain + "/ket-qua-xo-so/ngay-" + day + "-" + month + "-" + year;
    }

    private void connectWeb(String url) {
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

    @Override
    protected void onResume() {
        super.onResume();
        isLoading(false);
    }

    public void isLoading(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
            calendarView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            calendarView.setVisibility(View.VISIBLE);
        }
    }
    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "4pm";
            String description = "Đã đên lúc xổ số";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("4pm", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 29);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
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