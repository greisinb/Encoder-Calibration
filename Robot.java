// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick; //imports the libary for joystick control
import com.revrobotics.CANSparkMax; //imports the libary for Spark Max control over CAN
import com.revrobotics.CANSparkMaxLowLevel; //additional functionality for Spark motor controllers
import com.revrobotics.CANSparkMaxLowLevel.MotorType; //allows for brushless motor control
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  private RelativeEncoder armEncoder;
 public boolean calState; //stores the state of caliberation
  
  final Joystick leftStick = new Joystick(0); //creates a new joystick named leftStick and conects it to USB port 0
  final CANSparkMax armPiston = new CANSparkMax(6, MotorType.kBrushless); //creates and names a motor controller CAN ID 6 and makes it brushless
 DigitalInput calSwitch = new DigitalInput(0);
 
  

  @Override
  public void robotInit() {
   armEncoder = armPiston.getEncoder();
   calState=false;

  }

  @Override
  public void robotPeriodic() {
SmartDashboard.putNumber("Position", armEncoder.getPosition());
SmartDashboard.putBoolean("Calibration State", calState);
SmartDashboard.putBoolean("Switch Output", calSwitch.get());



  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    while(calState==false){
      armPiston.set(.2);
      if(calSwitch.get()==true){
        calState=true;
        armPiston.set(0);
        armEncoder.setPosition(0);
      }
    }
    armPiston.set(-leftStick.getY()); //sets the armPiston motor to Y axis on the joystick
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
