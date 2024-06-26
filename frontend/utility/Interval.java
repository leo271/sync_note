package utility;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Interval {
  public Interval(Runnable runnable, int interval) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Runnable task = new Runnable() {
      public void run() {
        runnable.run();
      }
    };

    // 0秒後に開始し、2秒ごとにtaskを実行
    scheduler.scheduleAtFixedRate(task, 0, interval, TimeUnit.SECONDS);
  }
}
