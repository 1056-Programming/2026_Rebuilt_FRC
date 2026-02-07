package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.States.IndexStates;
import frc.robot.subsystems.IndexerSubsystem;

public class IndexerCommands extends Command {
    
    private IndexerSubsystem indexerSubsystem; 
    private IndexStates states; 
    
    public IndexerCommands(IndexerSubsystem valveSubsystem, IndexStates states) {
        this.indexerSubsystem = valveSubsystem; 
        this.states = states;
        
    }

    @Override
    public void initialize() {
        indexerSubsystem.setValveStates(states);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
