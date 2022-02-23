import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

//  Java,C# & Python:
//        Take the number 192 and multiply it by each of 1, 2, and 3:
//        192 × 1 = 192
//        192 × 2 = 384
//        192 × 3 = 576
//        By concatenating each product, we get the 1 to 9 pandigital, 192384576. We will call 192384576 the concatenated product of 192 and (1,2,3)
//        The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5,giving the pandigital, 918273645, which is the concatenated product of 9 and(1,2,3,4,5).
//        What is the largest 1 to 9 pandigital 9-digit number that can be formed as the concatenated product of an integer with (1,2, ... , n) where n >1?
public class Question1Solution {

    public static final int MAX_DIGITS = 9;

    static Integer getDigits(Integer number) {
        return (int) (Math.log10(number) + 1);
    }

    static Integer getValidPandigital(int i, int n, PandigitalEntry pandigitalEntry) {
        Integer panDigital = -1;    // -1 means invalid
        int digitsSoFar = 0;    // Keep track of how many digits
        List<Integer> numbers = new ArrayList<>();
        for (int setElem=1; setElem<=n; setElem++) {
            Integer number = i * setElem;
            digitsSoFar = digitsSoFar + getDigits(number);
            if (digitsSoFar>MAX_DIGITS) {   // Invalid, the 1-9 Pandigital can not exceed 9 digits
                panDigital = -2;
                pandigitalEntry.setI(i);
                pandigitalEntry.setN(n);
                pandigitalEntry.setPanDigital(panDigital);
                pandigitalEntry.setPanDigitalStr(null);
                return panDigital;
            }
            numbers.add(number);
        }
        if (digitsSoFar<9) {    // Invalid, less than 9 digits
            panDigital = -1; // The resulting number would be too small so not valid
            pandigitalEntry.setI(i);
            pandigitalEntry.setN(n);
            pandigitalEntry.setPanDigital(panDigital);
            pandigitalEntry.setPanDigitalStr(null);
            return panDigital;
        }
        StringBuilder sb = new StringBuilder("");
        for (int idx = 0; idx < numbers.size(); idx++) {
            sb.append(numbers.get(idx));
        }
        String panDigitalStr = sb.toString();
        // Screen our newly formed pandigital string against highest known panditial so far
        if ( panDigitalStr.compareTo(pandigitalEntry.getHighestPandigitalStr()) <0 ) {
            panDigital = -1;
            pandigitalEntry.setI(i);
            pandigitalEntry.setN(n);
            pandigitalEntry.setPanDigital(panDigital);
            pandigitalEntry.setPanDigitalStr(null);
            return panDigital;
        }

        // The following set is used to make sure our 1-9 pandigital will
        // be exactly 9 characters and will not contain 0's.
        // Duplicated characters would cause the set to be less than 9
        // and invalidate the pandigital.
        Set<Character> uniqueCharsMeansPandigital = new TreeSet<>();
        for (int charIdx=0; charIdx<sb.length(); charIdx++) {
            if (sb.charAt(charIdx) == '0') {    // Invalid found a 0
                panDigital = -1;
                pandigitalEntry.setI(i);
                pandigitalEntry.setN(n);
                pandigitalEntry.setPanDigital(panDigital);
                pandigitalEntry.setPanDigitalStr(null);
                return panDigital;
            } else {
                uniqueCharsMeansPandigital.add(sb.charAt(charIdx));
            }
        }
        if (uniqueCharsMeansPandigital.size() == MAX_DIGITS) {  // VALID (Only valid block)
            panDigital = Integer.parseInt(panDigitalStr);
            pandigitalEntry.setI(i);
            pandigitalEntry.setN(n);
            pandigitalEntry.setPanDigital(panDigital);
            pandigitalEntry.setPanDigitalStr(panDigitalStr);
            if (panDigital > pandigitalEntry.getHighestPandigital()) {
                pandigitalEntry.setHighestPandigital(panDigital);
                pandigitalEntry.setHighestPandigitalStr(panDigitalStr);
                System.out.println("Highest pandigital so far: "+pandigitalEntry.getHighestPandigitalStr() +
                        " i="+pandigitalEntry.getI()+ " n="+pandigitalEntry.getN());
            }
        } else {    // Invalid
            panDigital = -1;
            pandigitalEntry.setI(i);
            pandigitalEntry.setN(n);
            pandigitalEntry.setPanDigital(panDigital);
            pandigitalEntry.setPanDigitalStr(null);
        }
        return panDigital;
    }

    static int getMaxIGivenN(int n) {
        for (int i=1; i<987654321; i++) {
            int digitsSoFar=0;
            for (int j=1; j<=n; j++) {
                digitsSoFar = digitsSoFar + getDigits(i*j);
            }
            if (digitsSoFar>MAX_DIGITS) {
                System.out.println("getMaxIGivenN("+n+") =" + i);
                return i;
            }
        }
        return 987654321;
    }

    PandigitalEntry getLargest1To9Pandigital() {
        TreeSet<PandigitalEntry> panDigitalSet = new TreeSet<PandigitalEntry>();
        PandigitalEntry pandigitalEntry = new PandigitalEntry(-1, null, -1,"0", 0, 0);
        // To reduce run time if we know n has to be at least 2, what are bounds on i
        int maxI = getMaxIGivenN(2);
        for (int i=1; i<=maxI; i++) {
            for (int n=2; n<987654321; n++) {
                Integer panDigital = getValidPandigital(i, n, pandigitalEntry);
                if (panDigital==-1) {
                    continue;
                }
                else if (panDigital==-2) {
                    break;
                } else {
                    panDigitalSet.add(new PandigitalEntry(pandigitalEntry.getPanDigital(), pandigitalEntry.getPanDigitalStr(),
                            pandigitalEntry.getHighestPandigital(), pandigitalEntry.getHighestPandigitalStr(),
                            pandigitalEntry.getI(), pandigitalEntry.getN()));
                    //System.out.println(panDigital);
                }
            }
        }
        return panDigitalSet.first();
    }

    class PandigitalEntry implements Comparable<PandigitalEntry> {
        private int panDigital;
        private String panDigitalStr;
        private int highestPandigital;
        private String highestPandigitalStr;
        private int i;  // The integer i to multiply by below integer set (1...n)
        private int n;  // The set (1...n) integer i is multiplied by to get panDigital;

        public PandigitalEntry(int panDigital, String panDigitalStr, int highestPandigital, String highestPandigitalStr, int i, int n) {
            this.panDigital = panDigital;
            this.panDigitalStr = panDigitalStr;
            this.highestPandigital = highestPandigital;
            this.highestPandigitalStr = highestPandigitalStr;
            this.i = i;
            this.n = n;
        }

        @Override
        public String toString() {
            return "PandigitalEntry{" +
                    "panDigital=" + panDigital +
                    ", panDigitalStr='" + panDigitalStr + '\'' +
                    ", highestPandigital=" + highestPandigital +
                    ", highestPandigitalStr='" + highestPandigitalStr + '\'' +
                    ", i=" + i +
                    ", n=" + n +
                    '}';
        }

        public int getPanDigital() { return panDigital; }
        public void setPanDigital(int panDigital) { this.panDigital = panDigital; }
        public int getHighestPandigital() { return highestPandigital; }
        public String getHighestPandigitalStr() { return highestPandigitalStr; }
        public void setHighestPandigital(int highestPandigital) { this.highestPandigital = highestPandigital; }

        public void setHighestPandigitalStr(String highestPandigitalStr) {
            this.highestPandigitalStr = highestPandigitalStr;
        }
        public String getPanDigitalStr() { return panDigitalStr; }
        public void setPanDigitalStr(String panDigitalStr) { this.panDigitalStr = panDigitalStr; }
        public int getI() { return i; }
        public void setI(int i) { this.i = i; }
        public int getN() { return n; }
        public void setN(int n) { this.n = n; }

        @Override
        public int compareTo(PandigitalEntry pandigitalEntry) {
            if (pandigitalEntry.getPanDigital()<this.getPanDigital()) return -1;
            else if (pandigitalEntry.getPanDigital()>this.getPanDigital()) return 1;
            return 0;
        }
    }

    // To run under bash:
    // javac Question1Solution.java
    // java Question1Solution
    public static void main(String[] args) {
        Question1Solution question1Solution = new Question1Solution();
        PandigitalEntry largestPandigitalEntry = question1Solution.getLargest1To9Pandigital();
        List<Integer> nList = new ArrayList<>();
        for (Integer j=1; j<=largestPandigitalEntry.getN(); j++) {
            nList.add(j);
        }
        System.out.println("The largest 1-9 9 digit pandigital is " + largestPandigitalEntry.getPanDigital() +
                " where integer "+largestPandigitalEntry.getI() + " is created from multiplication/concatenation with/by integer set "+
                nList.toString());
        // This produces the following to stdout:
        //getMaxIGivenN(2) =10000
        //Highest pandigital so far: 123456789 i=1 n=9
        //Highest pandigital so far: 918273645 i=9 n=5
        //Highest pandigital so far: 926718534 i=9267 n=2
        //Highest pandigital so far: 927318546 i=9273 n=2
        //Highest pandigital so far: 932718654 i=9327 n=2
        //The largest 1-9 9 digit pandigital is 932718654where integer 9327 is created from multiplication/concatenation with/by integer set [1, 2]
    }
}
