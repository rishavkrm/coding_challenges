package coding_challenges.http_load_tester;
import java.io.InputStream;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;

public class MakeRequestRunnable implements Runnable {
    private String url;
    private CopyOnWriteArrayList<Long> totalTimeList;
    private CopyOnWriteArrayList<Long> firstByteTime;
    private CopyOnWriteArrayList<Long> lastByteTime;
    private Map<Integer, Integer> statusMap;

    public MakeRequestRunnable(String url, CopyOnWriteArrayList<Long> firstByteTime,
            CopyOnWriteArrayList<Long> lastByteTime, CopyOnWriteArrayList<Long> totalTimeList,
            Map<Integer, Integer> statusMap) {
        this.url = url;
        this.totalTimeList = totalTimeList;
        this.lastByteTime = lastByteTime;
        this.firstByteTime = firstByteTime;
        this.statusMap = statusMap;
    }

    public void run() {
        try {
            URL url = new URL(this.url);
            Long t0 = System.currentTimeMillis();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            int byteRead;
            Long t1 = System.currentTimeMillis();
            Long tl = t1;
            while ((byteRead = inputStream.read()) != -1) {
                tl = System.currentTimeMillis() - tl;
            }
            Long tn = System.currentTimeMillis();
            connection.disconnect();
            totalTimeList.add(tn - t0);
            firstByteTime.add(t1 - t0);
            lastByteTime.add(tl);

            statusMap.merge(connection.getResponseCode(), 1, Integer::sum);
        } catch (Exception e) {
            statusMap.merge(400, 1, Integer::sum);
            e.getStackTrace();
        }
    }

}
