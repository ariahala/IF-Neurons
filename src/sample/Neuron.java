package sample;

import com.sun.source.tree.SynchronizedTree;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by mac on 7/2/2016 AD.
 */
public class Neuron extends Thread{
    public double u;
    private double timeParticle;
    Circle circle;
    public double uMax;
    public ArrayList<Neuron> Neighbours = new ArrayList<Neuron>();
    public ArrayList<Double> Weights = new ArrayList<Double>();
    public ArrayList<Spike> Spikes = new ArrayList<Spike>();
    public double IExternal;
    private double R;
    private double spikeDuration;
    public int x;
    public int y;
    public Statistic statistic = new Statistic();
    public Color color = new Color(this.statistic.localFrequency(5)/10,0.5,0.5,0.5);

    public Neuron(double r, double spikeDuration, double uMax , double timeParticle , Circle circle ,int x , int y) {
        R = r;
        this.spikeDuration = spikeDuration;
        this.uMax = uMax;
        this.circle = circle;
        this.timeParticle = timeParticle;
        this.u = 0;
        this.IExternal = uMax;
        this.x = x;
        this.y = y;
    }

    public void setIExternal(double IExternal) {
        this.IExternal = IExternal;
    }


    @Override
    public void run() {
        synchronized (this) {
            int ctr = 0;
            while ( true ) {
                ctr = ( ctr + 1 ) % 10;
                if (this.u >= uMax) {
                    this.u = 0;
                    Spikes.add(new Spike(spikeDuration));
                    this.statistic.spike();
                }
                double I = 0;
                for (int i = 0; i < Neighbours.size(); i++) {
                    I += Weights.get(i)*Neighbours.get(i).Spikes.size();

                }
                I += IExternal;
                double f0 = (I * R) - this.u;
                double K1 = (timeParticle * f0) / 2;
                this.u = this.u + timeParticle * (I * R - this.u - K1);



                for (int i = 0; i < Spikes.size(); i++) {
                    if (!Spikes.get(i).update(this.timeParticle)) {
                        Spikes.remove(i);
                        i--;
                    }
                }
                if ( ctr == 1 ) {
                    this.statistic.passTime(this.timeParticle);
                    double temppp = 1 - Math.exp(0 - this.statistic.localFrequency(4)/7);
                    if ( temppp < 0.85 ) {
                        this.color = new Color(temppp, 0, 0, 0.04 + (temppp * 0.96));
                    }else{
                        double ooo =  ( temppp - 0.85 ) * 100 / 15;
                        this.color = new Color(temppp, 0, ooo, 0.04 + (temppp * 0.96));
                    }
                }
                try {
                    sleep(100);
                }catch (Exception e){
                    System.out.println("NOOOOOB!!!");
                }
            }

        }
    }

}
