package major.adam;

public class Series {
    public static long nSum(int n) {
        if (n == 0) return 0;
        return (n + 1) * n / 2;
    }

    public static long factorial(int n) {
        long factorial = 1;
        for (long i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    public static long fibonacci(int n) {
        if (n < 0) return -1;
        if (n == 0) return 0;
        if (n <= 2) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
