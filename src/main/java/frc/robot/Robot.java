/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

// For Neo motor
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * This is a demo program showing the use of the DifferentialDrive class.
 * Runs the motors with arcade steering.
 */
public class Robot extends TimedRobot {

  private final Spark m_leftMotor = new Spark(Configuration.Ports.LeftMotor);
  private final Spark m_rightMotor = new Spark(Configuration.Ports.RightMotor);
  private final CANSparkMax m_intake = new CANSparkMax(Configuration.Ports.Intake, MotorType.kBrushed);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
  private final Joystick m_cStick = new Joystick(0);

  // For Neo motor
  private SparkMaxPIDController m_pidController;
  private CANSparkMax m_motor;
  private RelativeEncoder m_encoder;

  // For Arm logic, up is true & down is false
  private boolean armPosition = false;

  @Override
  public void robotInit() {
    System.out.println("Robot Init");
    super.robotInit();

    // Initialize Arm motor
    m_motor = new CANSparkMax(Configuration.Ports.Arm, MotorType.kBrushless);
    m_motor.restoreFactoryDefaults();
    m_pidController = m_motor.getPIDController();
    m_encoder = m_motor.getEncoder();

    // Initialize Arm PID Coefficients
    // set PID coefficients
    m_pidController.setP(Configuration.Arm.kP);
    m_pidController.setI(Configuration.Arm.kI);
    m_pidController.setD(Configuration.Arm.kD);
    m_pidController.setIZone(Configuration.Arm.kIz);
    m_pidController.setFF(Configuration.Arm.kFF);
    m_pidController.setOutputRange(Configuration.Arm.kMinOutput, Configuration.Arm.kMaxOutput);
  }

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(m_cStick.getY(), m_cStick.getX());

    // Logic for setting intake/shooting motor speed
    double intake_motor_speed = 0;
    if(m_cStick.getRawButton(Configuration.Intake.IntakeButton)) {
      intake_motor_speed = Configuration.Intake.IntakeSpeed;
    } else if (m_cStick.getRawButton(Configuration.Intake.ShooterButton)) {
      intake_motor_speed = Configuration.Intake.ShooterSpeed;
    } else {
      intake_motor_speed = 0.0;
    }
    m_intake.set(intake_motor_speed);

    // Logic for Arm. Only move if not already in that position
    // TODO: This needs to be looked at!
    if(!armPosition && m_cStick.getRawButton(Configuration.Arm.ButtonUp)) {
      m_pidController.setReference((Configuration.Arm.UpSetpoint/360)*Configuration.Arm.GearRatio, CANSparkMax.ControlType.kPosition);
    } else if (armPosition && m_cStick.getRawButton(Configuration.Arm.ButtonDown)) {
      m_pidController.setReference(-(Configuration.Arm.UpSetpoint/360)*Configuration.Arm.GearRatio, CANSparkMax.ControlType.kPosition);
    }

  }

  @Override
      public void autonomousPeriodic() {
        // TODO: Add code here for autonomous mode
  }

}
