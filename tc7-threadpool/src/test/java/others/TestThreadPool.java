package others;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.tomcat.util.threads.TaskQueue;

public class TestThreadPool {

//	private static int produceTaskSleepTime = 2;

//	private static int produceTaskMaxNumber = 3;

	public static void main(String[] args) {

		TaskQueue taskqueue = new TaskQueue(3);
		
		// 构造一个线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 40, 15, TimeUnit.SECONDS,
				taskqueue);

		for (int i = 1; i <= 10; i++) {
			try {
				String task = "task@ " + i;
				System.out.println("创建任务并提交到线程池中：" + task);
				threadPool.execute(new ThreadPoolTask(task));

//				Thread.sleep(produceTaskSleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
