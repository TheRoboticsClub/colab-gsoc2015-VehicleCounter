package org.jderobot.test.wearclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jderobot.checkpoint;

/**
 * Created by Shady on 15/08/15.
 */
public class HeatmapInfo implements Serializable {
    public int height;
    public int width;
    public checkpoint[] arr;
    public HeatmapInfo(int h, int w, jderobot.checkpoint[] cpList) {
        height = h;
        width = w;
        arr = new checkpoint[cpList.length];
        for (int i=0; i< cpList.length; i++) {
            arr[i] = new checkpoint(cpList[i].x, cpList[i].y);
        }
    }

    public static byte[] serialize(HeatmapInfo hmInfo) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(hmInfo);
        return out.toByteArray();
    }

    public static HeatmapInfo deserialize(byte[] array) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(array);
        ObjectInputStream is = new ObjectInputStream(in);
        return (HeatmapInfo) is.readObject();
    }

    public class checkpoint implements Serializable {
        public int x;
        public int y;
        public checkpoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
