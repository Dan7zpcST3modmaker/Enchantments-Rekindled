package net.dan7zpc.enchantments_rekindled.util.color;

public class ColorHelper {
    //idk how to do it properly so ill just make a custom util
    public static float[] hsvToRgb(float h, float s, float v){
        float r = 0, g = 0, b = 0;
        if(s==0){
            r=v;
            g=v;
            b=v;
            return new float[]{r,g,b};
        }
        float h6 = h*6;
        int i = (int) Math.floor(h6);
        float f = h6-i;
        float p = v * (1 - s);
        float q = v * (1 - s * f);
        float t = v * (1 - s * (1 - f));
        switch (i % 6){
            case 0: r = v; g = t; b = p; break;
            case 1: r = q; g = v; b = p; break;
            case 2: r = p; g = v; b = t; break;
            case 3: r = p; g = q; b = v; break;
            case 4: r = t; g = p; b = v; break;
            case 5: r = v; g = p; b = q; break;
        }
        return new float[]{r,g,b};
    }
}
