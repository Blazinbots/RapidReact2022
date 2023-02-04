package frc.robot;

public class Configuration {

    // XBox controller layout (carry over from 2021 code)
    public class Controller {
        static final int BUTTON_A = 1;
        static final int BUTTON_B = 2;
        static final int BUTTON_X = 3;
        static final int BUTTON_Y = 4;
        static final int BUTTON_LT = 5;
        static final int BUTTON_RT = 6;
        static final int BUTTON_BACK = 7;
        static final int BUTTON_START = 8;
        static final int BUTTON_L_ANALOG = 9;
        static final int BUTTON_R_ANALOG = 10;      
    }

    // PWM ports connected to on RoboRio
    public class Ports {
        static final int LeftMotor = 0;
        static final int RightMotor = 1;
        static final int Intake = 3;
        static final int Arm = 2;
    }

    public class Intake {
        // Set speeds for intake/shooting
        static final double IntakeSpeed = 0.5;
        static final double ShooterSpeed = -0.5;

        // Choose Buttons for intake and shooting
        static final int IntakeButton = Controller.BUTTON_B;
        static final int ShooterButton = Controller.BUTTON_A;
    }

    public class Arm {
        // Gearbox is 16:1 and mechanical cogs are 60:12 or 5:1
        static final int GearRatio = 16 * 5;

        // Reference setpoints in degrees
        static final int DownSetpoint = 0;
        static final int UpSetpoint = 45;

        // Controller gains, PID coefficients
        static final double kP = 0.5; 
        static final double kI = 0.0;
        static final double kD = 0.0; 
        static final double kIz = 0.0; 
        static final double kFF = 0.0; 
        static final double kMaxOutput = 1.0; 
        static final double kMinOutput = -1.0;

        // Arm Buttons
        //static final int ButtonUp = Controller.BUTTON_LT;
        //static final int ButtonDown = Controller.BUTTON_RT;
        static final int ButtonUp = Controller.BUTTON_X;
        static final int ButtonDown = Controller.BUTTON_Y;
    }

}