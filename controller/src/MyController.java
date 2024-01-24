//import com.cyberbotics.webots.controller.Robot;
//import com.cyberbotics.webots.controller.Motor;

import com.cyberbotics.webots.controller.Robot;
import com.cyberbotics.webots.controller.Motor;


public class MyController extends Robot {
    private Motor leftWheel, rightWheel, leftArm, rightArm, leftLeg, rightLeg, leftLeg2, rightLeg2;
    private double noise;

    public MyController(double noise) {
        super();
        this.noise = noise;

        leftWheel = getMotor("left wheel motor");
        rightWheel = getMotor("right wheel motor");
        leftArm = getMotor("left arm motor");
        rightArm = getMotor("right arm motor");
        leftLeg = getMotor("left leg motor");
        rightLeg = getMotor("right leg motor");
        leftLeg2 = getMotor("left leg motor2");
        rightLeg2 = getMotor("right leg motor2");

        leftWheel.setPosition(Double.POSITIVE_INFINITY);
        rightWheel.setPosition(Double.POSITIVE_INFINITY);
        leftArm.setPosition(Double.POSITIVE_INFINITY);
        rightArm.setPosition(Double.POSITIVE_INFINITY);
        leftLeg.setPosition(Double.POSITIVE_INFINITY);
        rightLeg.setPosition(Double.POSITIVE_INFINITY);
        leftLeg2.setPosition(Double.POSITIVE_INFINITY);
        rightLeg2.setPosition(Double.POSITIVE_INFINITY);
    }

    public void run2() {
        int timeStep = (int) Math.round(getBasicTimeStep());
        while(step(timeStep) != -1) {
            double max = leftWheel.getMaxVelocity();
            double max2 = rightWheel.getMaxVelocity();
            rightWheel.setVelocity(-max2);
            leftWheel.setVelocity(max);
            
        }
    }

    public void run() {
        int timeStep = (int) Math.round(getBasicTimeStep());
        while (step(timeStep) != -1) {
            double maxSpeed = leftWheel.getMaxVelocity();
            String[] speeds = getCustomData().split(" ");
            double left = Math.min(Math.max(Double.parseDouble(speeds[0]), -maxSpeed), maxSpeed);
            double right = Math.min(Math.max(Double.parseDouble(speeds[1]), -maxSpeed), maxSpeed);
            double arm1 = Double.parseDouble(speeds[4]);
            double arm2 = Double.parseDouble(speeds[5]);
            double leg2 = Double.parseDouble(speeds[6]);
            double leg1 = Double.parseDouble(speeds[7]);

            leftWheel.setVelocity(left);
            rightWheel.setVelocity(right);
            leftArm.setVelocity(-arm1 / 1.5);
            rightArm.setVelocity(arm2 / 1.5);
            leftLeg.setVelocity(arm1 / 3.5);
            rightLeg.setVelocity(-arm2 / 3.5);
            leftLeg2.setVelocity(leg1 / 1.5);
            rightLeg2.setVelocity(-leg2 / 1.5);
        }
    }

    public double slipNoise(double value) {
        return value * (1 + Math.random() * (2 * noise) - noise);
    }

    public static void main(String[] args) {
        double noise = (args.length > 0) ? Double.parseDouble(args[0]) : 0.0;
        MyController soccerRobot = new MyController(noise);
        soccerRobot.run2();
        System.exit(0);
    }
}
