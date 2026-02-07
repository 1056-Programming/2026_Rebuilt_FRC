package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.States.IndexStates;;


public class IndexerSubsystem {

    // intialize variables
    private final SparkFlex indexMotor; 
    
    private IndexStates indexStates;
    
    private double indexSpeed; 

    // Constructor 
    public IndexerSubsystem() {
        // Assign values
        indexMotor = new SparkFlex(Constants.ValveInfo.id,MotorType.kBrushless); 

        indexSpeed = 0; 

        setValveStates(indexStates.STOP);
    }

    // we can set the state of the valve
    public void setValveStates(IndexStates states) {
        indexStates = states;
        // sets the speed of the valve depending on the state it is in
        switch (states) {
            case IN :{
                setValveSpeed(0.05); // positive intake for now
            }
            case OUT :{
                setValveSpeed(-0.05); // positive intake for now
            }
            case STOP :{
                setValveSpeed(0.0); // positive intake for now
            }

            setSmartDashboardValue();
            break;
        }
    }

    // sets the speed of the valve
    public void setValveSpeed(double speed) {
        indexMotor.set(speed); 
    }
    
    // inserts all of the values into the smart dashboard
    private void setSmartDashboardValue() {
        SmartDashboard.putNumber("Valve Speed: ", indexSpeed); 
        SmartDashboard.putString("Valve State: ", indexStates.toString());    
    }

}
