package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.States.ShooterStates;
import frc.robot.Constants;

public class ShooterSubsystem {

    // back spin motors
    private SparkFlex rightBackSpinMotor; 
    private SparkFlex leftBackSpinMotor; 

    // shooter motors  
    private SparkFlex shooterMotor1; 
    private SparkFlex shooterMotor2; 
    private SparkFlex shooterMotor3; 

    // speed of back spin motors and the shooter
    private double backSpinMotorSpeed;
    private double shooterMotorSpeed; 

    // shooter state
    private ShooterStates shooterStates; 

    public ShooterSubsystem() {
        // assign values to the motors
        rightBackSpinMotor = new SparkFlex(Constants.ShooterInfo.RIGHT_BACK_ID, MotorType.kBrushless);
        leftBackSpinMotor = new SparkFlex(Constants.ShooterInfo.LEFT_BACK_ID, MotorType.kBrushless);
        shooterMotor1 = new SparkFlex(Constants.ShooterInfo.FIRST_SHOOTER_ID, MotorType.kBrushless);
        shooterMotor2 = new SparkFlex(Constants.ShooterInfo.SECOND_SHOOTER_ID, MotorType.kBrushless);
        shooterMotor3 = new SparkFlex(Constants.ShooterInfo.THIRD_SHOOTER_ID, MotorType.kBrushless); 

        backSpinMotorSpeed = 0;
        shooterMotorSpeed = 0; 
    }

    // assign the speed of the motors for state
    public void setShooterStates(ShooterStates states) {
        states = shooterStates; 
        switch (states) {
            case SHOOT :{
                setShooterSpeed(0.5, 0.5);
            }
            case STOP :{
                setShooterSpeed(0, 0);
            }
            setSmartDashboardValue();
            break; 
        }
    }

    // set the speed of the motor 
    public void setShooterSpeed(double backSpinMotorSpeed, double shooterMotorSpeed) {
        this.shooterMotorSpeed = shooterMotorSpeed; 
        this.backSpinMotorSpeed = backSpinMotorSpeed; 

        rightBackSpinMotor.set(backSpinMotorSpeed); 
        leftBackSpinMotor.set(backSpinMotorSpeed);
        shooterMotor1.set(shooterMotorSpeed);
        shooterMotor2.set(shooterMotorSpeed);
        shooterMotor3.set(shooterMotorSpeed); 
    }
    
    private void setSmartDashboardValue() {
        SmartDashboard.putNumber("Back Spin Motor Speed: ", backSpinMotorSpeed); 
        SmartDashboard.putNumber("Shooter Spin Motor Speed: ", shooterMotorSpeed);   
        SmartDashboard.putString("Shooter State: ", shooterStates.toString());      
    }

}