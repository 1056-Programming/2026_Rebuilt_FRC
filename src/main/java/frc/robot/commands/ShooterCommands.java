package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.States.ShooterStates;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommands extends Command {
    
    private ShooterSubsystem shooterSubsystem; 
    private ShooterStates states; 

    public ShooterCommands(ShooterSubsystem shooterSubsystem, ShooterStates states) {
        this.shooterSubsystem = shooterSubsystem;
        this.states = states; 
    }

    @Override
    public void initialize() {
        shooterSubsystem.setShooterStates(states);
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
