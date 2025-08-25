package com.codingrecipe.minielevatorsimulator.service;

import com.codingrecipe.minielevatorsimulator.model.Elevator;
import com.codingrecipe.minielevatorsimulator.model.Direction;

import static java.nio.file.Files.move;

public class ElevatorService {

    private final Elevator elevator;

    public ElevatorService(Elevator elevator) {
        this.elevator = elevator;
    }

    // 엘베 시뮬레이션 진행의 핵심 로직
    public void step() throws InterruptedException {

        // 1. 현재 층이 목적지인지 확인하고 처리
        if (elevator.getDestinations().contains(elevator.getCurrentFloor())) {
            processArrival();
        }

        // 2. 다음 방향 결정
        updateDirecion();

        // 3. 결정된 방향으로 이동
        move();
    }

    // 목적지 도착 시 처리 ( 문 열고 닫기, 목적지 제거 )
    private void processArrival() throws InterruptedException {
        elevator.opesDoors();
        System.out.println(elevator.getCurrentFloor() + "층 도착! /n 문이 열립니다.");
        Thread.sleep(2000);

        elevator.removeDestination(elevator.getCurrentFloor());

        elevator.closeDoors();
        System.out.println("문이 닫힙니다.");
        Thread.sleep(1000);
    }

    // 엘베 다음 방향을 결정
    private void updateDirecion() {
        if (elevator.getDestinations().isEmpty()) {
            elevator.setDirection(Direction.IDLE);
            return;
        }

        Direction currentDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        if (currentDirection == Direction.UP) {
            Integer higherDest = elevator.getDestinations().higher(currentFloor);
            if (higherDest == null) elevator.setDirection(Direction.DOWN);
        } else if (currentDirection == Direction.DOWN) {
            Integer lowerDest = elevator.getDestinations().lower(currentFloor);
            if (lowerDest == null) elevator.setDirection(Direction.UP);
        } else { //IDLE
            int nextDest = elevator.getDestinations().first();
            if (nextDest > currentFloor) elevator.setDirection(Direction.UP);
            else if (nextDest < currentFloor) elevator.setDirection(Direction.DOWN);
        }
    }
    
    // 결정된 방향으로 엘베 이동시킴
    private void move() {
        if (elevator.getDirection() == Direction.UP) {
            elevator.moveUp();
        } else if (elevator.getDirection() == Direction.DOWN) {
            elevator.moveDown();
        }
    }
}
