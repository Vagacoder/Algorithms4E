package javasrc.ch01_2;

import edu.princeton.cs.algs4.StdOut;

public class ex2_6 {

    public static void main(String[] args) {
        String s1 = "actgacg";
        String s2 = "tgacgac";
        StdOut.println(isCircularRotation(s1, s2));
        s1 = "a";
        s2 = "tgacgac";
        StdOut.println(isCircularRotation(s1, s2));
        s1 = "acttacg";
        s2 = "tgacgtc";
        StdOut.println(isCircularRotation(s1, s2));
    }

    private static boolean isCircularRotation(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        for (int i = 0; i < s1.length(); i++) {
            if (s1.equals(s2)) {
                return true;
            } else {
                s1 = s1.charAt(s1.length() - 1) + s1.substring(0, s1.length() - 1);
            }
        }

        return false;
    }
}