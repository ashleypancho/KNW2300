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
		//followLine(r,100);
		//runBothMotors(r);
		//ping(r);
		//servo(r);
		//ticks(r,300);
		//runBothMotorsBump(r);
		//spinnyThingy(r,6);
		//shaftEncoder(r);
		//testTurbidity(r);
		//releaseTheKraken(r);
		findBridge(r);
		//findWater(r);
		//servo(r);
		//followLine(r,100);
		//leftTurn(r);
		//rightTurn(r);
		//testWater(r);
		//irSensor(r);
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
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(10000);
		//r.moveServo(RXTXRobot.SERVO1, 0);
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
		r.runEncodedMotor(right, -230, 1000, left, 200,1000); // Run both motors forward for 3m 
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running. 
		r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors 
	}

	public static void ticks(RXTXRobot r, int x){
		r.runEncodedMotor(right, -250, x, left, 250, x); // Run both motors forward for 3m 
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running. 
		r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors 
	}

	public static void runBothMotors(RXTXRobot r, int x){
		//run motor for x meters
		r.runMotor(right, -175, left, 150, 0);
		while (true){
			int ticks = r.getEncodedMotorPosition(RXTXRobot.MOTOR1);
			if(ticks<-500){
				r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
				break;
			}
			System.out.println(ticks);
		}
		//r.runEncodedMotor(right, -150, 1527*x, left, 150, 1527*x); // Run both motors forward for 3m 
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running. 
		//r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0); // Stop both motors 
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
	}

	public static void spinnyThingy(RXTXRobot r, int balls){
		int x = balls;
		r.attachMotor(RXTXRobot.MOTOR4,8);
		for (int i = 0; i<x;i++){
			r.runMotor(RXTXRobot.MOTOR4,-88,770);
			r.sleep(500);
		}
	}

	public static void rightTurn(RXTXRobot r){
		r.runEncodedMotor(right, 250, 213, left, 250, 214);
	}
	
	public static void leftTurn(RXTXRobot r){
		r.runEncodedMotor(right, -250, 245, left, -250, 213);
	}


	public static void followLine(RXTXRobot r, int cm){
		final int rightLine = 6;
		final int leftLine = 7;

			r.refreshAnalogPins();
			int rLine = r.getAnalogPin(rightLine).getValue();
			int lLine = r.getAnalogPin(leftLine).getValue();
			System.out.println(rLine + "    " + lLine);
	}
	public static boolean isBlack(){
		return false;
	}
	public static boolean isWhite(){
		return false;
	}

	public static int testSalinity(RXTXRobot r){
		int balls = 0;
		int rawSalinity = r.getConductivity();
		balls = rawSalinity;
		System.out.println(balls);
		return balls;
	}

	public static int testTurbidity(RXTXRobot r){
		int balls = 0;
		final int turbidityPin = 2;
		r.refreshAnalogPins();
		int rawTurbidity = r.getAnalogPin(turbidityPin).getValue();
		balls = rawTurbidity;
		System.out.println(rawTurbidity);
		return balls;
	}
	
	public static void findBridge(RXTXRobot r){
		r.runMotor(RXTXRobot.MOTOR1, -200, RXTXRobot.MOTOR2, 200, 0);
		while(true){
			r.refreshAnalogPins();
			int distance = r.getAnalogPin(3).getValue();
			if (distance >=220){
				r.sleep(1500);
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2,0, 0);
				leftTurn(r);
				break;
			}
			}
		
	}
	
	public static void testWater(RXTXRobot r){
		int sBalls = 0;
		int tBalls = 0;
		r.attachServo(RXTXRobot.SERVO1, 7);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 0);
		//r.sleep(20000);
		testTurbidity(r);
		testSalinity(r);
		r.sleep(5000);
	}
	
	public static void releaseTheKraken(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO1, 10);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 45);
		r.sleep(4000);
	}
	
	public static void findWater(RXTXRobot r){
		final int PING_PIN = 4;
		while(true) 
		{ 
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, -200, RXTXRobot.MOTOR2, 200, 0);
			if(ping<=35){
				r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
				break;
			}
		}
	}
	
	public static void irSensor(RXTXRobot r){
		while(true){
		r.refreshAnalogPins();
		int distance = r.getAnalogPin(3).getValue();
		System.out.println(distance);
		}
	}
	
	public static void doSprint3(RXTXRobot r){
		findWater(r);
		testWater(r);
		leftTurn(r);
		
	}
}