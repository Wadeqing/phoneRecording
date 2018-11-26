package com.sinocall.phonerecordera;

import java.util.Stack;

/**
 * Created by qingchao on 2018/3/14.
 *
 */

public class Demo {
    public void main() {
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

    }

    public static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    public void println(Node node) {
        while (node != null) {
            System.out.print("  " + node.val);
            node = node.next;
        }
        System.out.println();
    }

    // 单链表长度
    public int getListLength(Node node) {
        if (node == null) {
            return 0;
        }
        int i = 0;
        while (node != null) {
            node = node.next;
            i++;
        }
        return i;
    }

    public Node reserveList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node rehead = reserveList(head.next); //
        head.next.next = head;
        head.next = null;
        return rehead;
    }

    //从头到尾打印单链表
    public void reversePrintListRec(Node node) {
        if (node == null) {
            return;
        } else {
            reversePrintListRec(node.next);
            System.out.print(node.val + "   ");
        }
    }

    //从头到尾打印单链表
    public void reversePrintListStack(Node head) {
        Stack<Node> nodes = new Stack<>();
        Node cue = head;
        while (cue.next != null) {
            nodes.push(cue);
            cue = cue.next;
        }
        while (nodes.isEmpty()) {
            Node pop = nodes.pop();
            System.out.print("  " + pop.val);
        }
        System.out.println();
    }

    public Node mergeSortedListRec(Node head1, Node head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        Node mergeNode = null;
        if (head1.val < head2.val) {
            mergeNode = head1;
            mergeSortedListRec(head1.next, head2);
        } else {
            mergeNode = head2;
            mergeSortedListRec(head1, head2.next);
        }
        return mergeNode;
    }

    //是否有环
    public boolean hasCycle(Node head) {
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //是否有交点
    public boolean isIntersect(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return false;
        }
        Node tail1 = head1;
        while (tail1.next != null) {
            tail1 = tail1.next;
        }
        Node tail2 = head2;
        while (tail2.next != null) {
            tail2 = tail2.next;
        }
        return tail1 == tail2;
    }


}
