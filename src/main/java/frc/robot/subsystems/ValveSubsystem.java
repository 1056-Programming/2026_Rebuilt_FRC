package frc.robot.subsystems;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.States;
import frc.robot.States.ValveStates;


public class ValveSubsystem {

    // intialize variables
    private final SparkFlex valveMotor; 
    private final DutyCycleEncoder valveEncoder; 
    
    private ValveStates valveStates;
    
    private double valveSpeed; 

    // Constructor 
    public ValveSubsystem() {
        // Assign values
        valveMotor = new SparkFlex(Constants.ValveInfo.id,MotorType.kBrushless); 
        valveEncoder = new DutyCycleEncoder(0); 

        valveSpeed = 0; 

        setValveStates(valveStates.STOP);
    }

    // we can set the state of the valve
    public void setValveStates(ValveStates states) {
        valveStates = states;
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
        valveMotor.set(speed); 
    }
    
    // inserts all of the values into the smart dashboard
    private void setSmartDashboardValue() {
        SmartDashboard.putNumber("Valve Speed: ", valveSpeed); 
        SmartDashboard.putString("Valve State: ", valveStates.toString());    
    }

}
