import java.util.Random;
import java.util.concurrent.*;

public class ExampleCallableAndFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Starting");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Finished");
                Random random = new Random();
                int randomValue = random.nextInt(10);
                if(randomValue < 5)
                    throw new Exception("Число меньше пяти");
                return randomValue;
            }
        });
        executorService.shutdown();
        try {
            int result = future.get(); // get дожидается окончания выполнения потока
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) { // выброшенное в потоке исключение отразится в данном месте
            Throwable exc = e.getCause(); // получение исключения
            System.out.println(exc.getMessage()); // вывод исключения на экран
        }
    }
}
