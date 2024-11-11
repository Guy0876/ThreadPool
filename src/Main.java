// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Task a [] = new Task[8];
        ThreadPool thp = ThreadPool.getInstance(5);
        for (int i = 0; i < 5; i++) {
            a[i] = new Task();
        }
        for (int i = 0; i < 5; i++) {
            a[i].start();
        }
    }
}