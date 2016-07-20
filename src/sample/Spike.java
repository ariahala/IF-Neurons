package sample;

/**
 * Created by mac on 7/2/2016 AD.
 */
public class Spike {
    public double timeDuration;

    public Spike(double timeDuration){
        this.timeDuration = timeDuration;
    }
    public boolean update (double timeParticle){
        this.timeDuration -= timeParticle;
        if ( this.timeDuration <= 0 ){
            return false;
        }else{
            return true;
        }
    }
}
