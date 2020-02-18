package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    Sensor sensor = mock(Sensor.class);
    MemoryCard card = mock(MemoryCard.class);
    Camera cam = new Camera(sensor, card);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        cam.powerOn();

        verify(sensor).powerUp();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        cam.powerOn();
        cam.powerOff();

        verify(sensor).powerDown();
    }

    @Test
    public void testShutterCopiesDataToTheSdWhenPowerOn() {

        when(sensor.readData()).thenReturn(new byte[]{12});

        cam.powerOn();
        cam.pressShutter();

        verify(card).write(eq(new byte[]{12}), any());
    }

    @Test
    public void testShutterPressDoesNothingOnPowerOff() {
        cam.powerOff();
        cam.pressShutter();

        verifyZeroInteractions(card);
    }

    @Test
    public void testSensorIsOnWhenWritingAndCameraIsSwitchedOff() {
        cam.powerOn();
        cam.pressShutter();
        cam.powerOff();

        verify(sensor, never()).powerDown();
    }

    @Test
    public void testSensorIsOffWhenWritingDoneAndCameraIsSwitchedOff() {

        ArgumentCaptor<WriteCompleteListener> aclistener = ArgumentCaptor.forClass(WriteCompleteListener.class);

        doNothing().when(card).write(any(), aclistener.capture());

        cam.powerOn();
        cam.pressShutter();
        cam.powerOff();

        verify(sensor, never()).powerDown();
        aclistener.getValue().writeComplete();
        verify(sensor).powerDown();
    }
}
