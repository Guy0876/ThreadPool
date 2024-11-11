public class Task extends Thread{

    @Override
    public void run()
    {
        super.run();
        System.out.println("calling acquire");
        ThreadPool thp = ThreadPool.getInstance(8);
        thp.acquire(this);
        System.out.println("after  acquire DOING WORK");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FINISHED WORK - RELEASING");
        thp.release();
        System.out.println("AFTER RELEASE");
    }
}
