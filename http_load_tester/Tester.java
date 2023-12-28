package coding_challenges.http_load_tester;

import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.*;

public class Tester {
    public static void main(String[] args) {
        String url = "http://localhost:8080";
        int n = 100;
        int c = 40;

        CopyOnWriteArrayList<Long> totalTime = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Long> firstByteTime = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Long> lastByteTime = new CopyOnWriteArrayList<>();
        Map<Integer, Integer> statusMap = new HashMap<>();
        concurrentRequests(n, c, statusMap, url, firstByteTime, lastByteTime, totalTime);
    }

    public static void concurrentRequests(int n, int c, Map<Integer, Integer> statusMap, String url,
            CopyOnWriteArrayList<Long> firstByteTime, CopyOnWriteArrayList<Long> lastByteTime,
            CopyOnWriteArrayList<Long> totalTime) {
        ExecutorService threadPool = Executors.newFixedThreadPool(c);
        System.out.println(c);
        for (int i = 0; i < n; i++) {
            threadPool.execute(new MakeRequestRunnable(url, firstByteTime, lastByteTime, totalTime, statusMap));
        }
        threadPool.shutdown();
        try {

            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            printResult(statusMap, url, firstByteTime, lastByteTime, totalTime);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static Long calculateAverage(CopyOnWriteArrayList<Long> array) {
        Long sum = (long) 0;
        for (Long i : array) {
            sum += i;
        }
        return sum / array.size();
    }

    public static void printResult(Map<Integer, Integer> statusMap, String url,
            CopyOnWriteArrayList<Long> firstByteTime, CopyOnWriteArrayList<Long> lastByteTime,
            CopyOnWriteArrayList<Long> totalTime) {
        System.out.println("Results: ");
        int success = 0;
        int fail = 0;
        Set<Integer> keys = statusMap.keySet();
        for (int key : keys) {
            if (key / 100 == 2) {
                success += statusMap.get(key);
            } else {
                fail += statusMap.get(key);
            }
        }
        System.out.println("Total requests: " + success);
        System.out.println("Failed requests: " + fail);
        System.out.println("Total Request Time (ms) (Min, Max, Mean).....: " + (Collections.max(totalTime))
                + ", " + (Collections.min(totalTime)) + ", " + (calculateAverage(totalTime)));
        System.out.println("Time to First Byte (ms) (Min, Max, Mean).....: " + (Collections.max(firstByteTime))
                + ", " + (Collections.min(firstByteTime)) + ", " + (calculateAverage(firstByteTime)));
        System.out.println("Time to Last Byte (ms) (Min, Max, Mean)......: " + (Collections.max(lastByteTime))
                + ", " + (Collections.min(lastByteTime)) + ", " + (calculateAverage(lastByteTime)));
    }
}
