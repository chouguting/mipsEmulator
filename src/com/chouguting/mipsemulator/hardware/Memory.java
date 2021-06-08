package com.chouguting.mipsemulator.hardware;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    //int[] data=new int[1073741824]; JAVA陣列不能宣告太大
    //用MAP來模擬Memory
    Map<Long, Long> map = new HashMap<Long, Long>();

    public long getData(long index) {
        Long value = map.get(index);
        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public void setData(long index, long value) {
        map.put(index, value);
    }
}
