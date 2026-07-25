package me.yakar.neetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKFrequent {
    public int[] topKFrequent(int[] nums, int k) {
        if (nums == null || k < 1) {
            throw new IllegalArgumentException();
        }

        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.merge(num, 1, Integer::sum));
        }

        if (k > count.size()) {
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];

        count.forEach((num, freq) -> {
            List<Integer> bucket = buckets[freq];
            if (bucket == null) {
                bucket = new ArrayList<>();
                buckets[freq] = bucket;
            }
            bucket.add(num);
        });

        int[] topK = new int[k];
        int index = 0;
        for (int freq = buckets.length - 1; freq > 0; freq--) {
            List<Integer> bucket = buckets[freq];
            if (bucket == null) {
                continue;
            }
            for (int num : bucket) {
                topK[index++] = num;
                if (index == k) {
                    return topK;
                }
            }
        }
        return topK;
    }
}
