package com.lxf.mvp;

import android.os.Bundle;

import com.hannesdorfmann.mosby3.base.BaseActivity;

/**
 * Created by 李小凡 on 2018/5/3.
 */

public class TestActivity extends BaseActivity {

    int [] arr = {1,2,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bubbleSort(arr);
        selectionSort(arr);
        insertionSort(arr);
        shellSort(arr);
    }

    private void shellSort(int[] arr) {
    }

    private void insertionSort(int[] arr) {
    }

    private void selectionSort(int[] arr) {
        
    }

    private void bubbleSort(int [] arr) {


    }


}
