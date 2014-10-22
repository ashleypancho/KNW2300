/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package knw2300;
import rxtxrobot.*;
/**
 *
 * @author Ashley
 */
public class Robot {
    
	 static int right = RXTXRobot.MOTOR1;
	 static int left = RXTXRobot.MOTOR2;
    public static void main(String[] args) {        
        RXTXRobot r = new ArduinoNano();
        r.setPort("/dev/tty.wch ch341 USB=>RS232 1420");
        r.connect(); 
        //runBothMotors(r);
        //ping(r);
        servo(r);
        //shaftEncoder(r);
        //runBothMotorsBump(r);
        //bumpSensor(r);
        //runBothMotors(r);
        //ping(r);
        r.close(); 
    }
    
    public static void motor(RXTXRobot r) {
        // Turn for 3 meters
        r.runMotor(RXTXRobot.MOTOR1, 50, 30000);
    }
    
    public static void ping(RXTXRobot r) {
        // >15cm & <15cm
        final int PING_PIN = 4;
        for (int x=0; true; ++x) 
	{ 
            //Read the ping sensor value, which is connected to pin 12 
            System.out.println("Response: " + r.getPing(PING_PIN) + " cm"); 
            r.sleep(300); 
	}
    }
    
    public static void servo(RXTXRobot r) {
        // Angle specified and then back
        r.attachServo(RXTXRobot.SERVO1, 7);
        r.sleep(1000);
        r.moveServo(RXTXRobot.SERVO1, 135);
        r.sleep(1000);
        r.moveServo(RXTXRobot.SERVO1, 90);
    }
    
    public static void shaftEncoder(RXTXRobot r) {
        // Turn for 500 ticks
        while (true){
        	System.out.println(r.getEncodedMotorPosition(RXTXRobot.MOTOR1));
            System.out.println(r.getEncodedMotorPosition(RXTXRobot.MOTOR2));
            r.sleep(500);
        }
    	
    }
    
    public static void runBothMotors(RXTXRobot r) {
        r.runEncodedMotor(right, -165, 1610, left, 140, 1000); // Run both motors forward for 3m 
        r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running. 
        r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors 
    }
    
    public static void runBothMotors(RXTXRobot r, int x){
    	//run motor for x meters
        r.runEncodedMotor(right, -150, 1527*x, left, 150, 1527*x); // Run both motors forward for 3m 
	r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running. 
	r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors 
    }
    
    public static void bumpSensor(RXTXRobot r) {
        // Motor until bump sensor is tapped
        r.refreshAnalogPins();
        while (true){
        	r.refreshAnalogPins();
        	System.out.println(r.getAnalogPin(0));
        	r.sleep(500);
        }
        //r.getAnalogPin(1);
        //r.getAnalogPin(2);
        //r.getAnalogPin(3);
    }
    
    public static void runBothMotorsBump(RXTXRobot r) {
        // Motor until bump sensor is tapped        
        r.runMotor(RXTXRobot.MOTOR1, -150, RXTXRobot.MOTOR2, 150, 0); // Run both motors forward indefinitely 
        while(true){	
        	r.refreshAnalogPins();
        	int bump0 = r.getAnalogPin(0).getValue();
            //int bump1 = r.getAnalogPin(1).getValue();
            //int bump2 = r.getAnalogPin(2).getValue();
            //int bump3 = r.getAnalogPin(3).getValue();
        	if(bump0>=10){
        		r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
        		break;
        	}
        }
    	public static void runCollector(RXTXRobot r, int balls){
    		for (int i = 0; i<balls;i++){
    			r.runMotor(RXTXRobot.MOTOR1,88,760);
    			r.sleep(500);
    		}
}
}