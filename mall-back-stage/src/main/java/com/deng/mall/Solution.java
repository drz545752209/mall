package com.deng.mall;



public class Solution {

    public static void swap(int[] arr, int i, int j) {
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void downHeap(int[] arr, int index) {
        if (index == 0) {
            return;
        }

        int temp;
        if (arr[index * 2 + 1] > arr[index * 2 + 2]) {
            temp = index * 2 + 1;
        }else {
            temp = index * 2 + 2;
        }

        if (arr[temp] > arr[index]) {
            swap(arr, index, temp);
        }else {
            return;
        }

        downHeap(arr,index/2-1);
    }

    public static void heapSort(int[] arr,int last) {
        for (int i=last/2-1;i>0;i--){
            downHeap(arr,i);
        }
        swap(arr,0,last);
        heapSort(arr,last-1);
    }

    public static void print(int[] arr){
        for (int i:arr){
            System.out.print(arr+" ");
        }
    }

    public static void main(String[] args) {
    }
}