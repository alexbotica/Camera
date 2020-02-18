package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard card;
    private boolean power;
    private boolean writing;

    public Camera(Sensor sensor, MemoryCard card) {
        this.sensor = sensor;
        this.card = card;
        this.power = false;
        this.writing = false;
    }

    public void pressShutter() {
        if (power)
        {
            writing = true;
            card.write(sensor.readData(), new WriteCompleteListener() {
                @Override
                public void writeComplete() {
                    writing = false;
                    if (!power)
                        sensor.powerDown();
                }
            });
        }
    }

    public void powerOn() {
        power = true;
        sensor.powerUp();
    }

    public void powerOff() {
        if (!writing)
            sensor.powerDown();
        power = false;
    }
}

