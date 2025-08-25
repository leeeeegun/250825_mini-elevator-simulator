package com.codingrecipe.minielevatorsimulator.controller;

import com.codingrecipe.minielevatorsimulator.model.Direction;
import com.codingrecipe.minielevatorsimulator.model.Elevator;
import com.codingrecipe.minielevatorsimulator.service.ElevatorService;
import com.codingrecipe.minielevatorsimulator.view.ConsoleView;

import java.util.Scanner;

public class ElevatorController {

    private final Elevator elevator;
    private final ElevatorService elevatorService;
    private final ConsoleView consoleView;
    private final Scanner scanner;

    public ElevatorController(Elevator elevator, ElevatorService elevatorService, ConsoleView consoleView) {
        this.elevator = elevator;
        this.elevatorService = elevatorService;
        this.consoleView = consoleView;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            try {
                // 1. 뷰를 통해 현재 상태를 보여줌
                consoleView.displayStatus();

                // 2. 모델의 상태를 확인하고, 유휴 상태일 때 사용자 입력을 처리
                if (elevator.getDirection() == Direction.IDLE && elevator.getDestinations().isEmpty()) {
                    handleUserInput();
                }

                // 3. 모델에 목적지가 있으면 서비스를 통해 엘리베이터를 움직임
                if (!elevator.getDestinations().isEmpty()) {
                    elevatorService.step();
                }

                Thread.sleep(1000);

            } catch (InterruptedException e) {
                consoleView.printMessage("시뮬레이션이 중단되었습니다.");
                break;
            } catch (QuitException e) {
                break;
            }
        }
        scanner.close();
        consoleView.printMessage("시뮬레이터를 종료합니다.");
    }

    private void handleUserInput() throws QuitException {
        try {
            consoleView.printMessage("\n엘리베이터를 호출하세요. (q: 종료)");
            System.out.print("현재 몇 층에 계신가요? (1-" + elevator.getTotalFloors() + "): ");
            String fromFloorInput = scanner.next();
            if (fromFloorInput.equalsIgnoreCase("q")) throw new QuitException();

            System.out.print("몇 층으로 가시겠어요? (1-" + elevator.getTotalFloors() + "): ");
            String toFloorInput = scanner.next();
            if (toFloorInput.equalsIgnoreCase("q")) throw new QuitException();

            // 4. 입력받은 데이터를 통해 모델의 상태를 변경
            elevator.addDestination(Integer.parseInt(fromFloorInput));
            elevator.addDestination(Integer.parseInt(toFloorInput));

        } catch (NumberFormatException e) {
            consoleView.printMessage("잘못된 입력입니다. 숫자만 입력해주세요.");
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        }
    }

    private static class QuitException extends Exception {}
}

