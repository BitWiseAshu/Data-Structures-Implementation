import java.util.*;

public class HashMapCode {
    static class HashMap<K, V> {
        private class Node {
            K key;
            V value;

            Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int n;
        private int N;
        private LinkedList<Node>[] buckets;

        @SuppressWarnings("unchecked")
        public HashMap(int N) {
            this.N = N;
            this.n = 0;
            this.buckets = new LinkedList[N];
            for (int i = 0; i < N; i++) {
                buckets[i] = new LinkedList<>();
            }
        }

        private int hashFunction(K key) {
            int bucketIndex = key.hashCode();
            return Math.abs(bucketIndex) % N;
        }

        private int searchInLL(K key, int bucketIndex) {
            int i = 0;
            for (Node node : buckets[bucketIndex]) {
                if (node.key.equals(key)) {
                    return i;
                }
                i++;
            }
            return -1;
        }

        @SuppressWarnings("unchecked")
        private void rehash() {
            LinkedList<Node>[] oldBuckets = buckets;
            N = 2 * N;
            n = 0;
            buckets = new LinkedList[N];
            for (int i = 0; i < N; i++) {
                buckets[i] = new LinkedList<>();
            }

            for (LinkedList<Node> bucket : oldBuckets) {
                for (Node node : bucket) {
                    put(node.key, node.value);
                }
            }
        }

        public void put(K key, V value) {
            int bucketIndex = hashFunction(key);
            int dataIndex = searchInLL(key, bucketIndex);

            if (dataIndex == -1) { // key not present
                buckets[bucketIndex].add(new Node(key, value));
                n++;
            } else { // key present
                Node node = buckets[bucketIndex].get(dataIndex);
                node.value = value;
            }

            double lambda = (double) n / N;
            if (lambda > 2.0) { // k == 2.0
                rehash();
            }
        }

        public V get(K key) {
            int bucketIndex = hashFunction(key);
            int dataIndex = searchInLL(key, bucketIndex);
    
            if (dataIndex == -1) { // key not present
                return null;
            } else { // key present
                Node node = buckets[bucketIndex].get(dataIndex);
                return node.value;
            }
        }

        public boolean containsKey(K key) {
            int bucketIndex = hashFunction(key);
            int dataIndex = searchInLL(key, bucketIndex);
            if(dataIndex == -1) {
                return false;
            }
            return true;
        }

        // public void remove(K key) {
        //     int bucketIndex = hashFunction(key);
        //     int dataIndex = searchInLL(key, bucketIndex);
        //     if(dataIndex == -1) {
        //         return;
        //     }
        //     buckets[bucketIndex].remove(dataIndex);
        //     n--;
        // }

        public V remove(K key) {
            int bucketIndex = hashFunction(key);
            int dataIndex = searchInLL(key, bucketIndex);

            if(dataIndex == -1) {
                return null;
            }
            else {
                Node node = buckets[bucketIndex].remove(dataIndex);
                n--;
                return node.value;
            }
        }

        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();

            for (LinkedList<Node> bucket : buckets) {
                for (Node node : bucket) {
                    keys.add(node.key);
                }
            }
            return keys;
        }

        public ArrayList<V> values() {
            ArrayList<V> values = new ArrayList<>();

            for (LinkedList<Node> bucket : buckets) {
                for (Node node : bucket) {
                    values.add(node.value);
                }
            }
            return values;
        }

        public void display() {
            for (LinkedList<Node> bucket : buckets) {
                for (Node node : bucket) {
                    System.out.println(node.key + " -> " + node.value);
                }
            }
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>(4);
        map.put("abc", 1);
        map.put("def", 2);
        map.put("ghi", 3);
        map.put("jkl", 4);
        map.put("mno", 5);
        map.put("pqr", 6);
        map.put("stu", 7);
        map.put("vwx", 8);
        map.put("yz", 9);

        System.out.println(map.get("abc"));

        map.display();
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.containsKey("abc"));
        System.out.println(map.containsKey("def"));
        map.remove("abc");
    }

}