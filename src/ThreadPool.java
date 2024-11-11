public class ThreadPool {
    private int n;
    private int activeThreads = 0;
    private Thread[] waitingThreads;
    private int waitingCount = 0;
    private static ThreadPool instance = null;

    public ThreadPool(int n) {
        this.n = n;
        this.waitingThreads = new Thread[n];
    }
    public static synchronized ThreadPool getInstance(int n){
        if(instance == null){
            instance = new ThreadPool(n);
        }
        return instance;
    }

    public void acquire(Thread thread) {
        synchronized (instance) {
            System.out.println(thread.getName() + ": Calling acquire");
            if (activeThreads < n) {
                activeThreads++;
                System.out.println(thread.getName() + ": Acquired, doing work...");
            }
            else {
                System.out.println(thread.getName() + ": Waiting, too many active threads");
                waitingThreads[waitingCount++] = thread;
                while (isThreadWaiting(thread)) {
                    try {
                        instance.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    public void release() {
        synchronized (instance) {
            activeThreads--;
            System.out.println(Thread.currentThread().getName() + ": Finished work, releasing...");
            if (waitingCount > 0) {
                Thread nextThread = waitingThreads[0];
                System.arraycopy(waitingThreads, 1, waitingThreads, 0, waitingCount - 1);
                waitingThreads[--waitingCount] = null;
                synchronized (nextThread) {
                    nextThread.notify();
                }
            }
            instance.notifyAll();
        }
    }
    private boolean isThreadWaiting(Thread thread) {
        for (int i = 0; i < waitingCount; i++) {
            if (waitingThreads[i] == thread) {
                return true;
            }
        }
        return false;
    }
}