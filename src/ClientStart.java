import java.util.Scanner;

public class ClientStart {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Client c = new Client(scanner.nextLine(), Integer.parseInt(scanner.nextLine()));
        c.start();
    }
}