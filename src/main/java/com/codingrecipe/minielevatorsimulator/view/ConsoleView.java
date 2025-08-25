package com.codingrecipe.minielevatorsimulator.view;

import com.codingrecipe.minielevatorsimulator.model.Elevator;

public class ConsoleView {

    private final Elevator elevator;

    public ConsoleView(Elevator elevator) {
        this.elevator = elevator;
    }

    public void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i=0; i<50; i++) {
                System.out.println();
            }
        }
    }

    public void displayStatus() {
        clearScreen();
        System.out.println("🏢 엘리베이터 시뮬레이터 (MVC + Service) 🏢");
        System.out.println("---------------------------------");

        for (int floor = elevator.getTotalFloors(); floor >= 1; floor--) {
            if (elevator.getCurrentFloor() == floor) {
                String doorStatus = elevator.isDoorsOpen() ? "[]" : "■";
                System.out.printf("%2d층 | [%s E %s] <-- 엘리베이터 (%s)%n",
                        floor, doorStatus, doorStatus, elevator.getDirection());
            } else {
                System.out.printf("%2d층 |%n", floor);
            }
        }
        System.out.println("---------------------------------");
        System.out.println("현재 목적지: " + (elevator.getDestinations().isEmpty() ? "없음" : elevator.getDestinations()));
        System.out.println("---------------------------------");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
