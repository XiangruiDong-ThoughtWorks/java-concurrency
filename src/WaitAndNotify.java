import java.util.LinkedList;
import java.util.List;

public class WaitAndNotify {
    public static void main(String[] args) {
        TaskQueen taskQueen = new TaskQueen();
        Thread taskAdderThread = new TaskAdder(taskQueen);
        Thread taskExecutorThread = new TaskExecutor(taskQueen);
        taskAdderThread.start();
        taskExecutorThread.start();
    }
}

class TaskQueen {
    private final List<Task> taskQueen;
    private static int taskId = 1;

    public TaskQueen() {
        taskQueen = new LinkedList<>();
    }

    public synchronized void addTask() {
        Task task = new Task("Task " + taskId++);
        taskQueen.add(task);
        System.out.println(task.getTaskName() + " : Added");
        this.notifyAll();
    }

    public synchronized Task executeTask() throws InterruptedException {
        while (taskQueen.isEmpty()) {
            System.out.println("I will waiting");
            this.wait();
            System.out.println("I will go on to work");
        }
        return taskQueen.remove(0);
    }
}

class Task {
    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }
}

// Add task into task list per 5 seconds
class TaskAdder extends Thread {
    private final TaskQueen taskQueen;

    public TaskAdder(TaskQueen taskQueen) {
        this.taskQueen = taskQueen;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            taskQueen.addTask();
        }
    }
}

// Execute an task per 3 seconds
class TaskExecutor extends Thread {
    private final TaskQueen taskQueen;

    public TaskExecutor(TaskQueen taskQueen) {
        this.taskQueen = taskQueen;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueen.executeTask();
                System.out.println(task.getTaskName() + " : Started");
                Thread.sleep(3000);
                System.out.println(task.getTaskName() + " : Finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
