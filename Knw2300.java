
import java.util.ArrayList;

import rxtxrobot.*;

public class Knw2300 {
    
	static int right = RXTXRobot.MOTOR1;
	static int left = RXTXRobot.MOTOR2;
	
	public static void main(String[] args) {
		
		RXTXRobot r = new ArduinoNano();
		r.setPort("/dev/tty.wch ch341 USB=>RS232 1410");
		
		RXTXRobot r2 = new ArduinoNano();
		r2.setPort("/dev/tty.wch ch341 USB=>RS232 1450");
		
		r.connect();
		r2.connect();
		
		testWater(r);
        //	ping(r);
		//ping2(r2);
		//gotoWall(r,25);
		//findBalls(r);
		//dropLever(r);
		//sprint4(r,r2);
		//leftTurn(r,r2);
		//testTurbidity(r);
		//testSalinity(r);
		//spinnyThingy(r2,5);
        //	stopAtLine(r,r2);
		//r.runEncodedMotor(right, -200, 10000, left, 200, 10000);
		//actuallyGoFuckingStraight(r,r2,25);
		//servo(r);
		//r.runEncodedMotor(right, -300, 330, left, 270, 330);
		//spinnyThingy(r2,5);
		//backToWall(r);
		r.close();
		r2.close();
	}
    
	public static void motor(RXTXRobot r) {
		// Turn for 3 meters
		r.runMotor(right, 50, 30000);
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
	public static void ping2(RXTXRobot r2) {
		// >15cm & <15cm
		final int PING_PIN = 7;
		for (int x=0; x<2; ++x)
		{
            //Read the ping sensor value, which is connected to pin 12
			System.out.println("Response: " + r2.getPing(PING_PIN) + " cm");
			r2.sleep(300);
		}
	}
    
	public static void servo(RXTXRobot r) {
		// Angle specified and then back
		r.attachServo(RXTXRobot.SERVO1, 7);
		//r.moveServo(RXTXRobot.SERVO1, 80);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1,115);
	}
    
	public static void shaftEncoder(RXTXRobot r) {
		// Turn for 500 ticks
		while (true){
			System.out.println(r.getEncodedMotorPosition(right));
			System.out.println(r.getEncodedMotorPosition(left));
			r.sleep(500);
		}
        
	}
    
	public static void backwards(RXTXRobot r, int i) {
		r.runEncodedMotor(right, 200, i, left, -200,i); // Run both motors forward for 3m
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running.
	}
    
	public static void ticks(RXTXRobot r, int x){
		r.runEncodedMotor(right, -225, x, left, 200, x); // Run both motors forward for 3m
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running.
		//r.runMotor(right,0,left,0,0); // Stop both motors
	}
    
	public static void runBothMotors(RXTXRobot r, int x){
		//run motor for x meters
		r.runMotor(right, -175, left, 150, 0);
		while (true){
			int ticks = r.getEncodedMotorPosition(right);
			if(ticks<-500){
				r.runMotor(right,0,left,0,0);
				break;
			}
			System.out.println(ticks);
		}
		//r.runEncodedMotor(right, -150, 1527*x, left, 150, 1527*x); // Run both motors forward for 3m
		//r.sleep(5000); // Pause execution for 5 seconds, but the motors keep running.
		//r.runMotor(right,0,left,0,0); // Stop both motors
	}
    
	public static void bumpSensor(RXTXRobot r) {
		// Motor until bump sensor is tapped
		r.refreshAnalogPins();
		while (true){
			r.refreshAnalogPins();
			System.out.println(r.getAnalogPin(1));
			r.sleep(500);
		}
		//r.getAnalogPin(1);
		//r.getAnalogPin(2);
		//r.getAnalogPin(3);
	}
    
	public static void runBothMotorsBump(RXTXRobot r) {
		// Motor until bump sensor is tapped
		r.runMotor(right, -150, RXTXRobot.MOTOR2, 150, 0); // Run both motors forward indefinitely
		while(true){
			r.refreshAnalogPins();
			int bump0 = r.getAnalogPin(0).getValue();
			//int bump1 = r.getAnalogPin(1).getValue();
			//int bump2 = r.getAnalogPin(2).getValue();
			//int bump3 = r.getAnalogPin(3).getValue();
			/*if(bump0>=10){
             r.runMotor(right,0,left,0,0);
             break;
             }*/
		}
	}
    
	public static void spinnyThingy(RXTXRobot r, int balls){
		for (int i = 0; i<balls;i++){
			r.runEncodedMotor(RXTXRobot.SERVO1,80,85);
			r.sleep(750);
			aWizard();
		}
	}
    
	
	public static int[] testWater(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO1, 7);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(3000);
		int tBalls [] = testTurbidity(r);
		r.sleep(2000);
		int sBalls[] = testSalinity(r);
		r.sleep(3000);
		r.moveServo(RXTXRobot.SERVO1,45);
		r.sleep(3000);
		r.moveServo(RXTXRobot.SERVO1, 115);
		int balls[] = new int[4];
		balls[0] = sBalls[0];
		balls[1] = tBalls[0];
		balls[2] = sBalls[1];
		balls[3] = tBalls[1];
		//r.sleep(1000);
		return balls;
	}

	public static void leftTurn(RXTXRobot r,RXTXRobot r2){
		final int frontLine = 2;
		
		r.runMotor(right, -250, left, -250, 0);
		while(true){
			r2.refreshAnalogPins();
			int i = r2.getAnalogPin(frontLine).getValue();
			System.out.println(r2.getAnalogPin(frontLine).toString());
			if(i<=800){
				
				//r.sleep(100);
				r.runMotor(right, 0, left, 0, 0);
				break;
			}
		}
	}
    
    
	public static void stopAtLine(RXTXRobot r, RXTXRobot r2){
		final int line1 = 1;
		final int line3 = 3;
		while(true){
			r.runMotor(RXTXRobot.MOTOR1, 200,RXTXRobot.MOTOR2, -190, 0);
			r2.refreshAnalogPins();
			System.out.println(r2.getAnalogPin(line1).toString() + "    " +r2.getAnalogPin(line3).toString() );
			if(r2.getAnalogPin(line1).getValue()<=760||r2.getAnalogPin(line3).getValue()<=660){
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
				
				break;
			}
		}
	}
    
	public static int[] testSalinity(RXTXRobot r){
		double salinity = 0;
		
		ArrayList<Integer> salinityList = new ArrayList<Integer>(5);
		
		for (int j = 0; j < 5; j++) {
			int rawSalinity = r.getConductivity();
		//	System.out.println(rawSalinity);
			salinityList.add(rawSalinity);
		}
	//	System.out.println();
		int unadjSum = 0;
		for (int i=0;i<5;i++) {
			unadjSum += salinityList.get(i);
		//	System.out.println(unadjSum);
		}
	//	System.out.println();
		int unadjAvg = unadjSum/5;
		for(int i=0; i<salinityList.size();i++){
			if (Math.abs(salinityList.get(i)-10)>unadjAvg||Math.abs(salinityList.get(i)+10)<unadjAvg){
				salinityList.remove(i);
			}
		}
		int sum = 0;
		salinityList.trimToSize();
		for(int i=0; i<salinityList.size();i++){
			sum += salinityList.get(i);
		}
		
		
		int avgSalinity = sum/(salinityList.size());
		
		salinity = 0.0245*avgSalinity*avgSalinity - 32.3878*avgSalinity + 11161.0768;
		System.out.println("Salinity: " + salinity);
		final double smallBall = 50;
		final double bigBall = 250;
		int[] balls = new int[2];
		
		
		while(salinity>smallBall){
			if (salinity >= smallBall && salinity >= bigBall){
				salinity = salinity - bigBall;
				balls[0]++;
			}else if(salinity<=bigBall&&salinity>=smallBall){
				salinity = salinity - smallBall;
				balls[1]++;
			}
		}
		return balls;
		
		//edited code
		/*int i =0;
         if(i<20) {
         //double salinity = 0;
         int rawSalinity = r.getConductivity();
         System.out.println(rawSalinity);
         i++;
         }*/
		
	}
    
	public static int[] testTurbidity(RXTXRobot r){
		double turbidity = 0;
		final double smallBall = 5;
		final double bigBall = 50;
		final int turbidityPin = 2;
		ArrayList<Integer> turbidityList = new ArrayList<Integer>(5);
		
		for (int j = 0; j < 5; j++) {
			
			r.refreshAnalogPins();
			int rawTurbidity = r.getAnalogPin(turbidityPin).getValue();
		//	System.out.println(rawTurbidity);
			turbidityList.add(new Integer(rawTurbidity));
		}
		System.out.println();
		int unadjSum = 0;
		for (int i=0; i<5; i++){ 
			unadjSum += turbidityList.get(i);
			//	System.out.println(unadjSum);
		}
		
		
		int unadjAvg = unadjSum/5;
	//	System.out.println();
	//	System.out.println(unadjAvg);
		
		for(int i=0; i< turbidityList.size();i++){
			if (Math.abs(turbidityList.get(i)-15)>unadjAvg||Math.abs(turbidityList.get(i)+15)<unadjAvg){
				turbidityList.remove(i);
			}
		}
		int sum = 0;
		turbidityList.trimToSize();
		for(int i:turbidityList) sum+=i;
		
		int avgTurbidity = sum/(turbidityList.size());
		
		//System.out.println();
		//System.out.println(avgTurbidity);
		//System.out.println();
		turbidity = -0.0005*avgTurbidity*avgTurbidity - .5669*avgTurbidity + 722.28;
		//System.out.println(rawTurbidity);
		System.out.println("Turbidity: " + turbidity);
		int[] balls = new int[2];
		while(turbidity>smallBall){
			if (turbidity >= smallBall && turbidity >= bigBall){
				turbidity = turbidity - bigBall;
				balls[0]++;
			}else if(turbidity<=bigBall&&turbidity>=smallBall){
				turbidity = turbidity - smallBall;
				balls[1]++;
			}
		}
		return balls;
	}
	
	public static void findBridge(RXTXRobot r){
		r.runMotor(RXTXRobot.MOTOR1, -200, RXTXRobot.MOTOR2, 200, 0);
		while(true){
			r.refreshAnalogPins();
			int distance = r.getAnalogPin(3).getValue();
			if (distance >=220){
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2,0, 0);
				
				break;
			}
		}
		
	}
    
	
	public static void releaseTheKraken(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO2, 10);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO2, 45);
		r.sleep(3000);
		r.moveServo(RXTXRobot.SERVO2, 90);
	}
	
	public static void gotoWall(RXTXRobot r, RXTXRobot r2,int i){
		final int PING_PIN = 4;
		while(true)
		{
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, -220, RXTXRobot.MOTOR2, 220, 0);
			if(ping<=i){
				r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
				break;
			}
		}
	}
	
	public static void gotoWallSlow(RXTXRobot r, RXTXRobot r2,int i){
		final int PING_PIN = 7;
		while(true)
		{
			int ping = r2.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, -170, RXTXRobot.MOTOR2, 145, 0);
			if(ping<=i){
				r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
				break;
			}
		}
	}
    
	public static void gotoWallBridge(RXTXRobot r, int i){
		final int PING_PIN = 4;
		while(true)
		{
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, -300, RXTXRobot.MOTOR2, 270, 0);
			if(ping<=i){
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
	
	public static void lineSensor(RXTXRobot r){
		final int rightLine = 6;
		final int leftLine = 7;
		while (true){
			try{
				r.refreshAnalogPins();
				System.out.println(r.getAnalogPin(leftLine).toString() + "     " + r.getAnalogPin(rightLine).toString());
			}catch(Exception err){
				System.out.println("ERROR");
			}
		}
	}
	
	
	/*
	 *  public static void doSprint3(RXTXRobot r){
     int[] balls;
     gotoWall(r,r2,40);
     balls = testWater(r);
     r.sleep(1000);
     r.runEncodedMotor(right, 250, 450, left, 250, 450); //right
     gotoWall(r,r2,40);
     r.runEncodedMotor(right, 250, 450, left, 250, 450);
     r.runEncodedMotor(right, -215, 1300, left, 200, 1300); //ticks 500
     r.runEncodedMotor(right, 250, 450, left, 250, 450);
     findBridge(r);
     r.runEncodedMotor(right, -250, 1800, left, 225, 1800);
     r.runEncodedMotor(right, -250, 250, left, -250, 250);
     backwards(r,100);
     releaseTheKraken(r);
     /*int[] balls;
     gotoWall(r,40);
     balls = testWater(r);
     r.sleep(1000);
     r.runEncodedMotor(right, -250, 480, left, -250, 480); //left
     gotoWall(r,24);
     r.runEncodedMotor(right, -250, 490, left, -250, 490);//left
     r.runEncodedMotor(right, -225, 80, left, 250, 200);
     r.runEncodedMotor(right, -225, 225, left, 250, 80);
     //r.runEncodedMotor(right, -225, 80, left, 250, 200);
     //r.runEncodedMotor(right, -225, 250, left, 200, 250); //ticks 300
     if(balls[0]<=10){
     spinnyThingy(r,5);
     }else{
     spinnyThingy(r,5);
     }
     r.runEncodedMotor(right, -225, 435, left, 200, 435);
     if(balls[1]<=10){
     spinnyThingy(r,5);
     }else{
     spinnyThingy(r,5);
     }
     r.runEncodedMotor(right, -225, 100, left, 200, 100);
     r.runEncodedMotor(right, -250, 480, left, -250, 480);
     gotoWall(r,30);
     r.runEncodedMotor(right, -250, 480, left, -250, 480);
     if(balls[3]<=10){
     spinnyThingy(r,5);
     }else{
     spinnyThingy(r,5);
     }
     r.runEncodedMotor(right, -230, 435, left, 190, 435);
     if(balls[2]<=10){
     spinnyThingy(r,5);
     }else{
     spinnyThingy(r,5);
     }
     r.runEncodedMotor(right, -250, 450, left, -250, 450);
     r.runEncodedMotor(right, -250, 450, left, -250, 450);
     r.runEncodedMotor(right, -225, 500, left, 200, 450); //ticks 500
     r.runEncodedMotor(right, 250, 450, left, 250, 450);
     findBridge(r);
     r.runEncodedMotor(right, -250, 1000, left, 225, 1000);
     r.runEncodedMotor(right, -250, 250, left, -250, 250);
     backwards(r,100);
     releaseTheKraken(r);
     /*testSalinity(r);     TESTS
     //servo(r);
     //testTurbidity(r);
     //r.runEncodedMotor(right, -250, 213, left, -250, 213);
     //r.runEncodedMotor(right, 250, 213, left, 250, 213);
     //spinnyThingy(r,5);
     //releaseTheKraken(r);*/
    //spinnyThingy(r,5);
    //servo(r);
    
    
    //doSprint3(r);
    
    //testWater(r);
    
    //testSalinity(r);
    //testTurbidity(r);
    
    //	r.runEncodedMotor(right, -250, 213, left, -250, 213);
    
    //r.runMotor(RXTXRobot.MOTOR1, 200, 5000);
    
    //r.runEncodedMotor(right, -200, 500, left, 200, 500);
    //leftTurn(r);
    //rightTurn(r);
	//}
	
	public static void goAwayFromWall(RXTXRobot r, int i){
		final int PING_PIN = 4;
		while(true)
		{
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, 250, RXTXRobot.MOTOR2, -220, 0);
			if(ping>=i){
				r.runMotor(RXTXRobot.MOTOR1,0,RXTXRobot.MOTOR2,0,0);
				break;
			}
		}
	}
	
	public static void findDispenser(RXTXRobot r, RXTXRobot r2){
		final int sidePingPin = 13;
		r.runMotor(RXTXRobot.MOTOR1, 200, RXTXRobot.MOTOR2, -200, 0);
		while(true){
			if(r.getDigitalPin(sidePingPin).getValue()<130){
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
				r.runEncodedMotor(right, -250, 520, left, -250, 520);
				break;
			}
		}
	}
	public static void findBalls(RXTXRobot r){
		final int bumpPin = 1;
		r.runMotor(right, -150, left, 145, 0);
		while(true){
            r.refreshAnalogPins();
            int bump = r.getAnalogPin(bumpPin).getValue();
            System.out.println(r.getAnalogPin(bumpPin).toString());
            
            if(bump==0){
                r.runMotor(right,0,left,0,0);
                break;
            }
		}
	}
	
	public static void dropLever(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO3, 9);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO3,0);
		r.sleep(800);
		r.moveServo(RXTXRobot.SERVO3,180);
		r.sleep(10000);
		//r.moveServo(RXTXRobot.SERVO3,0);
		//r.sleep(1000);
		
		
	}
	public static void dropLever2(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO3, 9);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO3,0);
		r.sleep(800);
		r.moveServo(RXTXRobot.SERVO3,180);
		r.sleep(10000);
	}
	
	
	public static void sprint4(RXTXRobot r, RXTXRobot r2){
		r.attachServo(RXTXRobot.SERVO2, 10);
		r.attachServo(RXTXRobot.SERVO1, 7);
		r.moveServo(RXTXRobot.SERVO1,115);
		
		int[] balls;
		
		//gotoWall(r,r2,34);
		//adjustAtWall(r,r2,2);
		balls = testWater(r);
		r.sleep(500);
		/*
		//stopAtLine(r,r2);
		goAwayFromWall(r,78);		//If line sensor doesn't work use this
		adjustAtWall(r,r2,4);
		//r.runEncodedMotor(right, 230, 90, left, -230, 90);
		r.runEncodedMotor(right, -250,225);
		adjustAtWall(r,r2,3);
		gotoWall(r,r2,28);
		adjustAtWall(r,r2,2);
		findBalls(r);
		r.sleep(100);
		//if(balls[0]>10){
        //    spinnyThingy(r2,3);
		//}else{
			spinnyThingy(r2,3);
		//}
		r.sleep(100);
		//r.runEncodedMotor(right,250,100,left,-200,100);
		adjustAtWall(r,r2,2);
		goAwayFromWall(r,100);		//If line sensor doesn't work use this
		//stopAtLine(r,r2);
		adjustAtWall(r,r2,2);
		r.runEncodedMotor(left, 250, 220);
		adjustAtWall(r,r2,2);
		//r.runEncodedMotor(right, 200, 30, left, -180, 30);
		r.runEncodedMotor(left, 250, 220);
		
		adjustAtWall(r,r2,2);
		gotoWall(r,r2,65);
		adjustAtWall(r,r2,2);
		gotoWall(r,r2,28);
		adjustAtWall(r,r2,2);
		
		findBalls(r);
        //	if(balls[1]>10){
		//	spinnyThingy(r2,3);
		//}else{
        spinnyThingy(r2,3);
		//}
		r.sleep(100);
		
		r.runEncodedMotor(right,250,100,left,-250,100);
		adjustAtWall(r,r2,2);
		
		goAwayFromWall(r,90);
		r.runEncodedMotor(left, -250, 225);
		
		adjustAtWall(r,r2,2);
		goAwayFromWall(r,70);
		//stopAtLine(r,r2);
		adjustAtWall(r,r2,3);
		goAwayFromWall(r,110);
		adjustAtWall(r,r2,5);
		//r.runEncodedMotor(right, 250, 80, left, -250, 80);
		//stopAtLine(r,r2);
		
		//goAwayFromWall(r,103);
		r.runEncodedMotor(right, 300, 30, left, -300, 30);
		r.runEncodedMotor(right, -250, 250);
		r.runEncodedMotor(right,-250,135, left, 250,135);
		adjustAtWall(r,r2,2);
		gotoWall(r,r2,27);
		adjustAtWall(r,r2,2);
		//r.runEncodedMotor(right, -250, 10);
		findBalls(r);
		//if(balls[2]>10){
        //spinnyThingy(r2,3);
		//}else{
        spinnyThingy(r2,3);
		//}
		r.sleep(100);
		
		//r.runEncodedMotor(right, 250, 10);
		r.runEncodedMotor(right,250,100,left,-250,100);
		adjustAtWall(r,r2,2);
		goAwayFromWall(r,90);
		r.runEncodedMotor(left, 250, 220);
		r.runEncodedMotor(right,250,30,left,-250,30);
		adjustAtWall(r,r2,3);
        //	r.runEncodedMotor(right, 200, 8, left, -200, 8);
		r.runEncodedMotor(left, 250, 220);
		adjustAtWall(r,r2,2);
		gotoWall(r,r2,65);
		adjustAtWall(r,r2,2);
		gotoWall(r,r2,27);
		adjustAtWall(r,r2,2);
		findBalls(r);
		//if(balls[3]>10){
        //spinnyThingy(r2,3);
		//}else{
        spinnyThingy(r2,3);
		//}
		r.sleep(100);
		r.runEncodedMotor(right,250,100,left,-250,100);
		adjustAtWall(r,r2,2);
		goAwayFromWall(r,25);
		//
		adjustAtWall(r,r2,2);
		r.runEncodedMotor(left, 250, 220);
		r.runEncodedMotor(right, 250, 225);
		findBridge(r);
		adjustAtWall(r,r2,2);
		r.runEncodedMotor(right, -200, 65, left, 200, 65);
		adjustAtWall(r,r2,2);
		
		r.runEncodedMotor(right, -250,225);
		
		r.runEncodedMotor(right, -300, 350, left, 300, 350);
		r.sleep(2000);
		gotoWallSlow(r,r2,45);
		r.sleep(500);
		r.runEncodedMotor(right, -200, 80);
		r.runEncodedMotor(left, -400, 260);
		r.runEncodedMotor(right, 200, 30);
		adjustAtWall(r,r2,3);
		goAwayFromWall(r,165);
		releaseTheKraken(r);
		
		findBridge(r);
       //  r.runEncodedMotor(right, -230, 15, left, 190, 15);
         r.runEncodedMotor(right, 200, 15, left, -200, 15);
		 r.runEncodedMotor(right, -400, 210);
         r.sleep(500);
        // backToWall(r);
         r.runEncodedMotor(right, -285, 300, left, 330, 300);
         r.sleep(500);
         r.runEncodedMotor(right, -200, 150, left, 200, 150);
         gotoWall(r,r2,150);
         adjustAtWall(r,r2,5);
         gotoWall(r,r2,100);
         adjustAtWall(r,r2,3);
//When on right side use this
        /* r.runEncodedMotor(right, -250, 225);	
         adjustAtWall(r,r2,3);
         gotoWall(r,r2,60);
         adjustAtWall(r,r2,2);
         r.runEncodedMotor(left, 250, 220);
         adjustAtWall(r,r2,2);
         gotoWall(r,r2,30);
         adjustAtWall(r,r2,2);
         r.runEncodedMotor(right, 250, 225);
         dropLever(r);
 //When on left side use this
         r.runEncodedMotor(left, -250, 220);	
         adjustAtWall(r,r2,3);
         gotoWall(r,r2,60);
         adjustAtWall(r,r2,2);
         r.runEncodedMotor(right, 250, 225);
         adjustAtWall(r,r2,2);
         gotoWall(r,r2,30);
         adjustAtWall(r,r2,2);
         r.runEncodedMotor(right, -250, 225);
         dropLever2(r);*/
         
	}
	
	public static void darkMagic(){
		tittySprinkles();
	}
	
	public static void tittySprinkles(){
		
	}
	
	public static void aWizard(){
		castSpell();
	}
	
	public static void castSpell(){
		
	}
	
	public static void adjustAtWall(RXTXRobot r, RXTXRobot r2, int i){
		final int lPing = 4;
		final int rPing = 7;
		while(true){
			r2.refreshDigitalPins();
			r.refreshDigitalPins();
			tittySprinkles();
			System.out.println(r.getPing(lPing)+"     "+r2.getPing(rPing));
			if(amIFuckingStraight(r.getPing(lPing),r2.getPing(rPing),i)==1){
				break;
			}else if(amIFuckingStraight(r.getPing(lPing),r2.getPing(rPing),i)==3){
				r.runEncodedMotor(left, -260,10);
			}else if(amIFuckingStraight(r.getPing(lPing),r2.getPing(rPing),i)==2){
				r.runEncodedMotor(right,260,10);
			}
			
		}
	}
	
	public static int amIFuckingStraight(int leftPing, int rightPing, int i){
		if((leftPing - rightPing<=i&&leftPing-rightPing>0)||(leftPing - rightPing>=-i&&leftPing-rightPing<0)){
			darkMagic();
			return 1;
		}else if(leftPing - rightPing>i&&leftPing-rightPing>0){
			return 2;
		}else if(leftPing - rightPing<i&&leftPing-rightPing<0){
			return 3;
		}else{
			return 1;
		}
	}

	public static void backToWall(RXTXRobot r){
		r.runMotor(right, 150, left, -145, 0);
		while(true){
			r.refreshAnalogPins();
			int bump2 = r.getAnalogPin(0).getValue();
			System.out.println(r.getAnalogPin(0).toString());
        
			if(bump2==0){
				r.runMotor(right,0,left,0,0);
				break;
			}
		}

	}
}
