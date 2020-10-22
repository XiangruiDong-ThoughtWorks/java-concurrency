import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Boolean> completableFuture = CompletableFuture.supplyAsync(CompletableFutureDemo::RandomResultTask);
        completableFuture.thenAccept(System.out::println);
        completableFuture.exceptionally((exception) -> {
            System.out.println("Exception was caught");
            return false;
        });
        System.out.println("Main thread finished");
        Thread.sleep(3000);
    }

    static Boolean RandomResultTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double randomNumber = Math.random();
        if (randomNumber < 0.4) {
            throw new RuntimeException("The random number is less than 0.4");
        } else {
            return true;
        }

    }

}

