
package knw2300;
import rxtxrobot.*;

public class Knw2300 {
    
	static int right = RXTXRobot.MOTOR1;
	static int left = RXTXRobot.MOTOR2;
	
	public static void main(String[] args) {
		
		RXTXRobot r = new ArduinoNano();
		r.setPort("/dev/tty.wch ch341 USB=>RS232 1410");
		
		RXTXRobot r2 = new ArduinoNano();
		r2.setPort("/dev/tty.wch ch341 USB=>RS232 1420");
		
		r.connect();
		r2.connect();
		
		//findBalls(r);
		//dropLever(r);
		sprint4(r,r2);
		//leftTurn(r,r2);
		//testTurbidity(r);
		//testSalinity(r);
		//spinnyThingy(r2,5);
		//stopAtLine(r,r2);
		//r.runEncodedMotor(right, -200, 10000, left, 200, 10000);
		
		
		
		r.close();
		r2.close();
	}
    
	public static void motor(RXTXRobot r) {
		// Turn for 3 meters
		r.runMotor(right, 50, 30000);
	}
    
	public static void ping(RXTXRobot r) {
		// >15cm & <15cm
		final int PING_PIN = 13;
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
		//r.moveServo(RXTXRobot.SERVO1, 80);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 0);
		r.sleep(2000);
		r.moveServo(RXTXRobot.SERVO1, 180);
		
		//r.moveServo(RXTXRobot.SERVO1, 0);
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
		r.attachMotor(RXTXRobot.MOTOR4,12);
		for (int i = 0; i<balls;i++){
			r.runMotor(RXTXRobot.MOTOR4,65,1400);
			r.sleep(500);
		}
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
			r.runMotor(RXTXRobot.MOTOR1, 200,RXTXRobot.MOTOR2, -200, 0);
			r2.refreshAnalogPins();
			System.out.println(r2.getAnalogPin(line1).toString() + "    " +r2.getAnalogPin(line3).toString() );
			if(r2.getAnalogPin(line1).getValue()<=870||r2.getAnalogPin(line1).getValue()<=870){
				r.sleep(300);
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2, 0, 0);
				break;
			}
		}
		
	}
    
	public static int[] testSalinity(RXTXRobot r){
		double salinity = 0;
		int rawSalinity = r.getConductivity();
		System.out.println(rawSalinity);
		salinity = 0.0372*rawSalinity*rawSalinity - 52.692*rawSalinity + 19255;
		System.out.println(salinity);
		final double smallBall = 0.25;
		final double bigBall = 0.75;
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
	}
    
	public static int[] testTurbidity(RXTXRobot r){
		double turbidity = 0;
		final double smallBall = 0.25;
		final double bigBall = 0.75;
		final int turbidityPin = 2;
		r.refreshAnalogPins();
		int rawTurbidity = r.getAnalogPin(turbidityPin).getValue();
		turbidity = 0.0011*rawTurbidity*rawTurbidity - 1.4026*rawTurbidity + 420.15;
		System.out.println(rawTurbidity);
		System.out.println(turbidity);
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
		r.runMotor(RXTXRobot.MOTOR1, -205, RXTXRobot.MOTOR2, 200, 0);
		while(true){
			r.refreshAnalogPins();
			int distance = r.getAnalogPin(3).getValue();
			if (distance >=220){
				r.sleep(2200);
				r.runMotor(RXTXRobot.MOTOR1, 0, RXTXRobot.MOTOR2,0, 0);
				r.runEncodedMotor(right, -240,213, left, -250, 213);
				break;
			}
		}
		
	}
	
	public static int[] testWater(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO1, 7);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 0);
		//r.sleep(20000);
		int tBalls [] = testTurbidity(r);
		int sBalls[] = testSalinity(r);
		r.sleep(3000);
		r.moveServo(RXTXRobot.SERVO1, 180);
		int balls[] = new int[4];
		balls[0] = sBalls[0];
		balls[1] = sBalls[1];
		balls[2] = tBalls[0];
		balls[3] = tBalls[1];
		//r.sleep(1000);
		return balls;
	}
	
	public static void releaseTheKraken(RXTXRobot r){
		r.attachServo(RXTXRobot.SERVO1, 10);
		r.sleep(1000);
		r.moveServo(RXTXRobot.SERVO1, 45);
		r.sleep(3000);
	}
	
	public static void gotoWall(RXTXRobot r, int i){
		final int PING_PIN = 4;
		while(true)
		{
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, -230, RXTXRobot.MOTOR2, 200, 0);
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
			r.runMotor(RXTXRobot.MOTOR1, -310, RXTXRobot.MOTOR2, 300, 0);
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
	
	
	public static void doSprint3(RXTXRobot r){
		int[] balls;
		gotoWall(r,40);
		balls = testWater(r);
		r.sleep(1000);
		r.runEncodedMotor(right, 250, 450, left, 250, 450); //right
		gotoWall(r,40);
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
	}
	
	public static void goAwayFromWall(RXTXRobot r, int i){
		final int PING_PIN = 4;
		while(true)
		{
			int ping = r.getPing(PING_PIN);
			r.runMotor(RXTXRobot.MOTOR1, 200, RXTXRobot.MOTOR2, -200, 0);
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
		r.runMotor(right, -200, left, 200, 0);
		while(true){
            r.refreshAnalogPins();
            int bump = r.getAnalogPin(bumpPin).getValue();
            System.out.println(r.getAnalogPin(bumpPin).toString());
            
            if(bump==0){
                r.runMotor(right,0,left,0,0);
                break;
            }
            r.sleep(75);
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
	
	
	public static void sprint4(RXTXRobot r, RXTXRobot r2){
		int[] balls;
		gotoWall(r,35);
		balls = testWater(r);
		r.sleep(1000);
		stopAtLine(r,r2);
		r.runEncodedMotor(right, -250, 600);
		findBalls(r);
		spinnyThingy(r2,3);
		goAwayFromWall(r,111);
		r.runEncodedMotor(right, -250, 600);
		r.runEncodedMotor(right, -250, 600);
		findBalls(r);
		spinnyThingy(r2,3);
		goAwayFromWall(r,111);
		r.runEncodedMotor(right, -250, 600);
		stopAtLine(r, r2);
		findBalls(r);
		spinnyThingy(r2,3);
		goAwayFromWall(r,111);
		findBalls(r);
		spinnyThingy(r2,3);
		goAwayFromWall(r,25);
		r.runEncodedMotor(right, 250, 213, left, 250, 213);
		r.runEncodedMotor(right, 250, 213, left, 250, 213);
		findBridge(r);
		gotoWallBridge(r,25);
		r.runEncodedMotor(right, -250, 213, left, -250, 213);
		goAwayFromWall(r,179);
		releaseTheKraken(r);
	}
	
	
}
