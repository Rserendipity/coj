import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int len = scanner.nextInt();
            int cnt = 0;
            for (int j = 0; j < len; j++) {
                cnt += scanner.nextInt();
            }
            System.out.println(cnt);
        }
    }
}