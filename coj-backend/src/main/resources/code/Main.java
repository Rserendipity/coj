import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder(scanner.next());
            sb = sb.reverse();
            System.out.println(sb.toString());
        }
    }
}