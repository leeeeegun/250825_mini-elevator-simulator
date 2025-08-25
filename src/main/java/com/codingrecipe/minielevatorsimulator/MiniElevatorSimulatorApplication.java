package com.codingrecipe.minielevatorsimulator;

import com.codingrecipe.minielevatorsimulator.controller.ElevatorController;
import com.codingrecipe.minielevatorsimulator.model.Elevator;
import com.codingrecipe.minielevatorsimulator.service.ElevatorService;
import com.codingrecipe.minielevatorsimulator.view.ConsoleView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniElevatorSimulatorApplication {

    private static final int TOTAL_FLOORS = 10;

    public static void main(String[] args){
        Elevator elevator = new Elevator(TOTAL_FLOORS);
        ConsoleView consoleView = new ConsoleView(elevator);
        ElevatorService elevatorService = new ElevatorService(elevator);
        ElevatorController controller = new ElevatorController(elevator, elevatorService, consoleView);

        controller.run();
    }
}