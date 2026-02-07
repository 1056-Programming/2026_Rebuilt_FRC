package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.States.ValveStates;
import frc.robot.subsystems.ValveSubsystem;

public class ValveCommands extends Command {
    
    private ValveSubsystem valveSubsystem; 
    private ValveStates states; 
    
    public ValveCommands(ValveSubsystem valveSubsystem, ValveStates states) {
        this.valveSubsystem = valveSubsystem; 
        this.states = states;
        
    }

    @Override
    public void initialize() {
        valveSubsystem.setValveStates(states);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
