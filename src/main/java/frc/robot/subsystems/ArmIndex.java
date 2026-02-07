package frc.robot.subsystems;

import com.ctre.phoenix6.sim.TalonFXSimState.MotorType;
import com.revrobotics.spark.SparkFlex;

import frc.robot.Constants;
import frc.robot.States.IndexStates;

public class ArmIndex {
    private final SparkFlex armIndexMotor; 
    private IndexStates armIndexStates; 

    private double armIndexSpeed; 

    public ArmIndex() {
        armIndexMotor = new SparkFlex(Constants.ArmIndexInfo.ARM_INDEX_ID, MotorType.kBrushless); 
    }
    
}
