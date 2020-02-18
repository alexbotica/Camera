package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard card;
    private final WriteCompleteListener listener;
    private boolean power;
    private boolean writing;

    public Camera(Sensor sensor, MemoryCard card, WriteCompleteListener listener) {
        this.sensor = sensor;
        this.card = card;
        this.listener = listener;
        this.power = false;
    }

    public void pressShutter() {
        if (power)
        {
            writing = true;
            card.write(sensor.readData(), listener);
        }
    }

    public void powerOn() {
        power = true;
        sensor.powerUp();
    }

    public void powerOff() {
        if (power)
        {
            if (!writing)
                sensor.powerDown();

            power = false;
        }
    }
}

