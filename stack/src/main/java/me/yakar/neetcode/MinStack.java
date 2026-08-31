package me.yakar.neetcode;

import java.util.Stack;

public class MinStack {

    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> minStack = new Stack<>();

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(val);
            minStack.push(val);
            return;
        }
        stack.push(val);
        minStack.push(Math.min(minStack.peek(), val));
    }

    public void pop() {
        if (stack.isEmpty()) {
            return;
        }
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
