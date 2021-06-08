package com.chouguting.mipsemulator.hardware;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    //int[] data=new int[1073741824]; JAVA陣列不能宣告太大
    //用MAP來模擬Memory
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    public int getData(int index) {
        Integer value = map.get(index);
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public void setData(int index, int value) {
        map.put(index, value);
    }
}
