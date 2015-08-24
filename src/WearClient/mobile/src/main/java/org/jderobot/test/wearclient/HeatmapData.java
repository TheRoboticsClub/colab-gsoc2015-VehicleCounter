package org.jderobot.test.wearclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Shady on 15/08/15.
 */
public class HeatmapData implements Serializable{
    public boolean state;
    public int curcp;
    public int curfreq;
    public float curvel;
    public float poseX;
    public float poseY;

    public HeatmapData(boolean state, int curcp, int curfreq, float curvel, float pX, float pY) {
        this.state = state;
        this.curcp = curcp;
        this.curvel = curvel;
        this.curfreq = curfreq;
        poseX = pX;
        poseY = pY;
    }

    public static byte[] serialize(HeatmapData hmData) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(hmData);
        return out.toByteArray();
    }

    public static HeatmapData deserialize(byte[] array) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(array);
        ObjectInputStream is = new ObjectInputStream(in);
        return (HeatmapData) is.readObject();
    }
}
