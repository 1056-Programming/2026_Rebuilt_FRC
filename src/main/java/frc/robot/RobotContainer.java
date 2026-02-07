// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.ShooterCommands;
import frc.robot.commands.ValveCommands;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.*;
import frc.robot.States.ShooterStates;
import frc.robot.States.ValveStates;

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.RobotCentric drive = new SwerveRequest.RobotCentric()
            .withDeadband(MaxSpeed * 0.2).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final CommandXboxController driver0 = new CommandXboxController(0);
    private final CommandXboxController driver1 = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    // set up substystem 
    private final ValveSubsystem s_valve = new ValveSubsystem(); 
    private final ShooterSubsystem s_shooter = new ShooterSubsystem();

    // set up intake commands
    private final ValveCommands c_valve_in = new ValveCommands(s_valve, ValveStates.IN);
    private final ValveCommands c_valve_out = new ValveCommands(s_valve, ValveStates.OUT);
    private final ValveCommands c_valve_stop = new ValveCommands(s_valve, ValveStates.STOP);

    // set up shooter commands
    private final ShooterCommands c_shooter_shoot = new ShooterCommands(s_shooter, ShooterStates.SHOOT);
    private final ShooterCommands c_shoot_stop = new ShooterCommands(s_shooter, ShooterStates.STOP); 
 

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-driver0.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-driver0.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-driver0.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        driver0.a().whileTrue(drivetrain.applyRequest(() -> brake));
        driver0.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-driver0.getLeftY(), -driver0.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        driver0.back().and(driver0.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        driver0.back().and(driver0.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        driver0.start().and(driver0.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        driver0.start().and(driver0.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        driver0.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);

        configureDriver1ValveBindings();
        configureDriver1ShooterBindings();
    }

    // set controller bindings for the valve subsystem 
    // ** NEED TO CHANGE BINDINGS LATER **
    private void configureDriver1ValveBindings() {
        driver1.a().onTrue(c_valve_in); 
        driver1.a().onFalse(c_valve_stop);

        driver1.b().onTrue(c_valve_out);
        driver1.b().onFalse(c_valve_stop); 
    }

    // set controller bindings for the shooter subsystem 
    // ** NEED TO CHANGE BINDINGS LATER **
    private void configureDriver1ShooterBindings() {
        driver1.rightBumper().onTrue(c_shooter_shoot);
        driver1.leftBumper().onTrue(c_shoot_stop); 
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
