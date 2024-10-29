import java.math.BigInteger;
import java.util.*;

public class PolynomialInterpolation {
    public static void main(String[] args) {
        // Test Case 1
        Map<String, Object> testCase1 = new HashMap<>();
        Map<String, Integer> keys1 = new HashMap<>();
        keys1.put("n", 4);
        keys1.put("k", 3);
        testCase1.put("keys", keys1);
        
        Map<String, String[]> points1 = new HashMap<>();
        points1.put("1", new String[]{"10", "4"});
        points1.put("2", new String[]{"2", "111"});
        points1.put("3", new String[]{"10", "12"});
        points1.put("6", new String[]{"4", "213"});
        testCase1.put("points", points1);
        
        // Test Case 2
        Map<String, Object> testCase2 = new HashMap<>();
        Map<String, Integer> keys2 = new HashMap<>();
        keys2.put("n", 10);
        keys2.put("k", 7);
        testCase2.put("keys", keys2);
        
        Map<String, String[]> points2 = new HashMap<>();
        points2.put("1", new String[]{"6", "13444211440455345511"});
        points2.put("2", new String[]{"15", "aed7015a346d63"});
        points2.put("3", new String[]{"15", "6aeeb69631c227c"});
        points2.put("4", new String[]{"16", "e1b5e05623d881f"});
        points2.put("5", new String[]{"8", "316034514573652620673"});
        points2.put("6", new String[]{"3", "2122212201122002221120200210011020220200"});
        points2.put("7", new String[]{"3", "20120221122211000100210021102001201112121"});
        points2.put("8", new String[]{"6", "20220554335330240002224253"});
        points2.put("9", new String[]{"12", "45153788322a1255483"});
        points2.put("10", new String[]{"7", "1101613130313526312514143"});
        testCase2.put("points", points2);
        
        processTestCase(testCase1, "Test Case 1");
        processTestCase(testCase2, "Test Case 2");
    }
    
    private static void processTestCase(Map<String, Object> testCase, String caseName) {
        @SuppressWarnings("unchecked")
        Map<String, Integer> keys = (Map<String, Integer>) testCase.get("keys");
        @SuppressWarnings("unchecked")
        Map<String, String[]> points = (Map<String, String[]>) testCase.get("points");
        
        int n = keys.get("n");
        int k = keys.get("k");
        
        BigInteger[][] coordinates = new BigInteger[k][2];
        int index = 0;
        
        // Process first k points
        for (int i = 1; i <= n && index < k; i++) {
            String key = String.valueOf(i);
            if (points.containsKey(key)) {
                String[] point = points.get(key);
                int base = Integer.parseInt(point[0]);
                String value = point[1];
                
                coordinates[index][0] = BigInteger.valueOf(i);
                coordinates[index][1] = new BigInteger(value, base);
                index++;
            }
        }
        
        BigInteger secret = calculateSecret(coordinates);
        System.out.println("Secret for " + caseName + ": " + secret);
    }
    
    private static BigInteger calculateSecret(BigInteger[][] points) {
        BigInteger result = BigInteger.ZERO;
        int k = points.length;
        
        for (int i = 0; i < k; i++) {
            BigInteger term = points[i][1];
            BigInteger denominator = BigInteger.ONE;
            
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    // Numerator: -x_j
                    BigInteger numerator = points[j][0].negate();
                    // Denominator: (x_i - x_j)
                    denominator = denominator.multiply(points[i][0].subtract(points[j][0]));
                    term = term.multiply(numerator);
                }
            }
            
            // Divide the term by the denominator
            term = term.divide(denominator);
            result = result.add(term);
        }
        
        return result;
    }
}