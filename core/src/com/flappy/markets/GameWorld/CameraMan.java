package com.flappy.markets.GameWorld;

/**
 * Created by norton on 4/18/15.
 */
public class CameraMan {
    public final static int MAX_ZOOM_VELOCITY = 5; // m/s Maximum rate at which the top or bottom of the camera will move
    public final static float CAMERA_LAG = 0.5f; // s Number of seconds that it would take to get the birds to the right spots on the screen

    private GameWorld myWorld;

    private float top;
    private float bottom;

    private float prevTime;

    public CameraMan(GameWorld world){
        this.myWorld = world;

        this.top = desiredTop();
        this.bottom = desiredBottom();
    }

    private float calculateZoomVelocity(float currentAltitude, float desiredAltitude){
        float delta = desiredAltitude - currentAltitude;
        float candidateV = delta / CAMERA_LAG;
        if(Math.abs(candidateV) > MAX_ZOOM_VELOCITY){
            return MAX_ZOOM_VELOCITY * (candidateV > 0 ? 1 : -1);
        }
        return candidateV;
    }

    public void step(float time){
        float elapsed;

        if(prevTime > 0){
            elapsed = time - prevTime;
            this.top = top + v_top() * elapsed;
            this.bottom = bottom + v_bottom() * elapsed;
        }
        this.prevTime = time;

        System.out.println(this);
    }

    private float desiredRange(){
        return desiredTop() - desiredBottom();
    }

    private float desiredTop(){
        return (myWorld.getBirdSpread() / 2) + myWorld.maxBirdAltitude();
    }

    private float desiredBottom(){
        return myWorld.minBirdAltitude() - (myWorld.getBirdSpread() / 2);
    }

    private float v_top(){
        return calculateZoomVelocity(top, desiredTop());
    }

    private float v_bottom(){
        return calculateZoomVelocity(bottom, desiredBottom());
    }

    public String toString(){

        return String.format("top:%s,bottom:%s", top, bottom);
    }
}
