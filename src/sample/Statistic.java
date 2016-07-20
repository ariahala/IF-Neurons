package sample;

import java.util.ArrayList;

/**
 * Created by mac on 7/5/2016 AD.
 */
public class Statistic {
    ArrayList<Double> spikeOccured = new ArrayList<Double>();
    private double brainTime = 0;

    public double localFrequency (double timePassed) {
        double temp = 0.000001;
        for (int i = 0 ; i < spikeOccured.size() ; i++ ){
            if ( spikeOccured.get(i) > this.brainTime - timePassed ){
                temp += 1;
            }else{
                spikeOccured.remove(i);
                i--;
            }
        }
        return temp/timePassed;
    }
    public Statistic (){
    }
    public void passTime ( double timeParticle ){
        brainTime += timeParticle;
    }
    public void spike(){
        spikeOccured.add(new Double(brainTime));
    }
}
