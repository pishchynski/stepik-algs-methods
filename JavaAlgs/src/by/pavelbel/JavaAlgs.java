package by.pavelbel;

import java.text.DecimalFormat;
import java.util.*;

public class JavaAlgs {

    private Scanner sc = new Scanner(System.in);

    private void fibStep(int x, int y, int n) {
        if (n > 2) {
            fibStep(y, x + y, n - 1);
        } else if (n == 0) {
            System.out.println(0);
        } else {
            System.out.println(y);
        }
    }

    void fib() {
        fibStep(1, 1, sc.nextInt());
    }

    void fibLast() {
        int n = sc.nextInt();

        int last_num = 1;
        int prev_last_num = 0;
        int temp;

        for (int i = 1; i < n; ++i) {
            temp = last_num;
            last_num = (prev_last_num + last_num) % 10;
            prev_last_num = temp;
        }

        System.out.println(last_num);
    }

    void fibMod() {
        long n = sc.nextLong();
        int m = sc.nextInt();

        List<Integer> seq = new ArrayList<>();
        seq.add(0);
        seq.add(1);
        seq.add(1);

        int lastRes = 1;
        int prevLastRes = 1;
        int temp;
        boolean isSeqComplete = false;

        for (long i = 2; i < n; ++i) {
            temp = lastRes;
            lastRes = (prevLastRes + lastRes) % m;
            seq.add(lastRes);
            prevLastRes = temp;
            if (prevLastRes == 0 && lastRes == 1) {
                isSeqComplete = true;
                break;
            }
        }

        int seqSize = seq.size() - 2;
        if (isSeqComplete) {
            System.out.println(seq.get((int) (n % seqSize)));
        } else {
            System.out.println(seq.get(seqSize + 1));
        }
    }

    int gcdStep(int a, int b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        } else if (a > b) {
            return gcdStep(a % b, b);
        } else return gcdStep(a, b % a);
    }

    void gcd() {
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.println(gcdStep(a, b));
    }

    private class Pair implements Comparable {
        private int a;
        private int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }

        @Override
        public int compareTo(Object p) {
            return this.b - ((Pair) p).getB();
        }
    }

    private class TreeNode implements Comparable {
        private char letter;
        private int freq;

        private TreeNode left;
        private TreeNode right;

        public TreeNode(char a, int b) {
            this.letter = a;
            this.freq = b;
        }

        public TreeNode(int freq, TreeNode left, TreeNode right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public char getLetter() {
            return letter;
        }

        public int getFreq() {
            return freq;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public boolean isLeaf() {
            return (left == null && right == null);
        }

        @Override
        public int compareTo(Object p) {
            return this.freq - ((TreeNode) p).getFreq();
        }
    }

    private class KnapPair implements Comparable {
        private double w;
        private double rc;

        public KnapPair(int c, int w) {
            this.w = (double) w;
            this.rc = c / this.w;
        }

        public int getW() {
            return (int) w;
        }

        public double getRc() {
            return rc;
        }

        @Override
        public int compareTo(Object o) {
            return -Double.compare(this.rc, ((KnapPair) o).getRc());
        }
    }

    void segmentCover() {
        int n = sc.nextInt();
        List<Pair> segments = new ArrayList<>();

        List<Integer> dots = new ArrayList<>();
        int rb = -1;

        for (int i = 0; i < n; ++i) {
            segments.add(new Pair(sc.nextInt(), sc.nextInt()));
        }

        Collections.sort(segments);

        for (Pair segment : segments) {
            if (rb < segment.getA()) {
                rb = segment.getB();
                dots.add(rb);
            }
        }

        System.out.println(dots.size());
        dots.forEach(i -> System.out.print(i + " "));
    }

    void continuousKnapsack() {
        int n = sc.nextInt();
        int w = sc.nextInt();
        double sumCost = 0;

        List<KnapPair> items = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            items.add(new KnapPair(sc.nextInt(), sc.nextInt()));
        }

        Collections.sort(items);

        int curW;

        for (KnapPair item : items) {
            if (w > 0) {
                curW = Math.min(item.getW(), w);
                sumCost += curW * item.getRc();
                w -= curW;
            }
        }

        DecimalFormat f = new DecimalFormat("##.000");
        System.out.println(f.format(Math.round(sumCost * 1000.) / 1000.));
    }

    void naturalDecomposition() {
        int n = sc.nextInt();
        int fullN = 0;
        int k = 0;

        while (fullN < n) {
            k++;
            fullN = k * (k + 1) / 2;
        }

        List<Integer> res = new ArrayList<>();

        for (int i = 1; i <= k; ++i) {
            if (i != (fullN - n)) {
                res.add(i);
            }
        }

        System.out.println(res.size());

        for (Integer addend : res) {
            System.out.print(addend + " ");
        }
    }

    void treeStep(TreeNode node, String code, HashMap<Character, String> letterCodes) {
        if (node.isLeaf()) {
            if (code.length() == 0) {
                letterCodes.put(node.getLetter(), "0");
            } else {
                letterCodes.put(node.getLetter(), code);
            }
        } else {
            treeStep(node.getLeft(), code + "0", letterCodes);
            treeStep(node.getRight(), code + "1", letterCodes);
        }
    }

    void huffman() {
        String s = sc.nextLine();

        char[] letters = s.toCharArray();
        HashMap<Character, Integer> letterCounts = new HashMap<>();

        for (char letter : letters) {
            letterCounts.put(letter, letterCounts.getOrDefault(letter, 0) + 1);
        }

        PriorityQueue<TreeNode> letterCountsQueue = new PriorityQueue<>();

        letterCounts.forEach((letter, count) -> letterCountsQueue.add(new TreeNode(letter, count)));

        while (letterCountsQueue.size() > 1) {
            TreeNode left = letterCountsQueue.poll();
            TreeNode right = letterCountsQueue.poll();

            letterCountsQueue.add(new TreeNode(left.getFreq() + right.getFreq(), left, right));
        }

        HashMap<Character, String> letterCodes = new HashMap<>();

        treeStep(letterCountsQueue.poll(), "", letterCodes);

        StringBuilder encoded = new StringBuilder();

        for (char letter : letters) {
            encoded.append(letterCodes.get(letter));
        }


        System.out.println(letterCodes.size() + " " + encoded.length());
        letterCodes.forEach((letter, code) -> System.out.println(letter + ": " + code));
        System.out.println(encoded.toString());
    }

    void huffmanDecode() {
        int k = sc.nextInt();
        int ll = sc.nextInt();
        sc.nextLine();

        HashMap<String, String> encoding = new HashMap<>();
        String s;
        String[] encArr;

        for (int i = 0; i < k; ++i) {
            s = sc.nextLine();
            encArr = s.split(": ");
            encoding.put(encArr[1], encArr[0]);
        }

        int l = 0;
        int r = 0;

        String encoded = sc.nextLine();
        StringBuilder decodedBuilder = new StringBuilder();
        int len = encoded.length();

        String temp;
        String letter;

        while (r < len) {
            temp = encoded.substring(l, r + 1);
            letter = encoding.get(temp);
            if (letter != null) {
                decodedBuilder.append(letter);
                l = r + 1;
            }
            r++;
        }

        System.out.print(decodedBuilder.toString());

    }

    class PriorQueue {
        ArrayList<Integer> q = new ArrayList<>();

        private void siftDown() {
            if (q.isEmpty()) {
                return;
            }

            boolean inPlace = false;

            boolean hasLeft;
            boolean hasRight;

            int curInd = 0;

            int curTemp;
            int lChildTemp;
            int rChildTemp;

            while (!inPlace) {
                hasLeft = curInd * 2 + 1 < q.size();
                hasRight = curInd * 2 + 2 < q.size();

                curTemp = q.get(curInd);

                if (hasRight) {
                    lChildTemp = q.get(curInd * 2 + 1);
                    rChildTemp = q.get(curInd * 2 + 2);

                    if (curTemp < lChildTemp || curTemp < rChildTemp) {
                        if (lChildTemp > rChildTemp) {
                            q.set(curInd, lChildTemp);
                            q.set(curInd * 2 + 1, curTemp);
                            curInd = curInd * 2 + 1;
                        } else {
                            q.set(curInd, rChildTemp);
                            q.set(curInd * 2 + 2, curTemp);
                            curInd = curInd * 2 + 2;
                        }
                    } else {
                        inPlace = true;
                    }
                } else if (hasLeft) {
                    lChildTemp = q.get(curInd * 2 + 1);
                    if (curTemp < lChildTemp) {
                        q.set(curInd, lChildTemp);
                        q.set(curInd * 2 + 1, curTemp);
                        curInd = curInd * 2 + 1;
                    } else {
                        inPlace = true;
                    }
                } else {
                    inPlace = true;
                }
            }
        }

        public void printQueue() {
            q.forEach(elem -> System.out.print(elem + " "));
        }

        private void siftUp() {
            boolean inPlace = false;
            int curInd = q.size() - 1;

            int curTemp;
            int parentTemp;

            while (curInd > 0 && !inPlace) {

                curTemp = q.get(curInd);
                parentTemp = q.get((curInd - 1) / 2);

                if (curTemp > parentTemp) {

                    q.set(curInd, parentTemp);
                    q.set((curInd - 1) / 2, curTemp);

                    curInd = (curInd - 1) / 2;
                } else {
                    inPlace = true;
                }
            }

        }

        public void push(int elem) {
            q.add(elem);
            siftUp();

            System.out.println();
            printQueue();
            System.out.println();
        }

        public void poll() {
            if (!q.isEmpty()) {
                System.out.println(q.get(0));
                q.set(0, q.get(q.size() - 1));
                q.remove(q.size() - 1);

                siftDown();
            }
            System.out.println();
            printQueue();
            System.out.println();
        }
    }

    void priorityQueue() {
        PriorQueue pq = new PriorQueue();

        int n = sc.nextInt();
        sc.nextLine();

        String command;
        String[] parsed;


        for (int i = 0; i < n; ++i) {
            command = sc.nextLine();
            parsed = command.split(" ");

            if (parsed.length == 1) {
                pq.poll();
            } else {
                pq.push(Integer.valueOf(parsed[1]));
            }
        }
    }

    void multiBinary() {

    }

    private long unsortedCount = 0;
    private int[] temp;
    private int[] a;

    void merge(int left, int m, int right) {
        if (left >= right) return;

        int lcur = left;
        int rcur = m;

        for (int i = left; i < right; ++i) {
            if (rcur == right || (lcur < m && a[lcur] <= a[rcur])) {
                temp[i] = a[lcur++];
            } else {
                unsortedCount += m - lcur;
                temp[i] = a[rcur++];
            }
        }

        System.arraycopy(temp, left, a, left, right - left);
    }

    void mergeSort(int l, int r) {
        if (r <= l + 1) return;

        int m = (l + r) >> 1;

        mergeSort(l, m);
        mergeSort(m, r);

        merge(l, m, r);
    }

    void unsortedPairs() {
        int n = sc.nextInt();

        a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = sc.nextInt();
        }

        temp = new int[n];

        mergeSort(0, n);

        System.out.print(unsortedCount);
    }

    public static void main(String[] args) {
        JavaAlgs algs = new JavaAlgs();

//        algs.fib();
//        algs.fibLast();
//        algs.fibMod();
//        algs.gcd();
//        algs.segmentCover();
//        algs.continuousKnapsack();
//        algs.naturalDecomposition();
//        algs.huffman();
//        algs.huffmanDecode();
//        algs.unsortedPairs();
        algs.priorityQueue();
    }
}
