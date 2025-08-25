package com.codingrecipe.minielevatorsimulator.service;

import com.codingrecipe.minielevatorsimulator.model.Elevator;
import com.codingrecipe.minielevatorsimulator.model.Direction;

import static java.nio.file.Files.move;

public class ElevatorService {

    private final Elevator elevator; // 상태를 변경할 대상인 Elevator 모델

    public ElevatorService(Elevator elevator) {
        this.elevator = elevator;
    }

    public void step() throws InterruptedException {
        // 1단계: 현재 층이 목적지인지 확인하고 도착 처리
        processArrivalIfNeeded();

        // 2단계: 다음 이동 방향을 최신 정보로 업데이트
        updateDirection();

        // 3단계: 결정된 방향으로 엘리베이터 이동
        move();
    }

    private void processArrivalIfNeeded() throws InterruptedException {
        if (elevator.getDestinations().contains(elevator.getCurrentFloor())) {
            elevator.openDoors();
            Thread.sleep(2000);

            elevator.removeDestination(elevator.getCurrentFloor());

            elevator.closeDoors();
            Thread.sleep(1000);
        }
    }

    private void updateDirection() {
        if (elevator.getDestinations().isEmpty()) {
            elevator.setDirection(Direction.IDLE);
            return;
        }

        Direction currentDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        // 위로 가는 중인데 더 이상 올라갈 곳이 없으면 아래로 전환
        if (currentDirection == Direction.UP) {
            if (elevator.getDestinations().higher(currentFloor) == null) {
                elevator.setDirection(Direction.DOWN);
            }
            // 아래로 가는 중인데 더 이상 내려갈 곳이 없으면 위로 전환
        } else if (currentDirection == Direction.DOWN) {
            if (elevator.getDestinations().lower(currentFloor) == null) {
                elevator.setDirection(Direction.UP);
            }
            // 멈춰있을 때, 목적지를 보고 방향 결정
        } else { // IDLE
            int nextDest = elevator.getDestinations().first();
            if (nextDest > currentFloor) {
                elevator.setDirection(Direction.UP);
            } else if (nextDest < currentFloor) {
                elevator.setDirection(Direction.DOWN);
            }
        }
    }

    private void move() {
        if (elevator.getDirection() == Direction.UP) {
            elevator.moveUp();
        } else if (elevator.getDirection() == Direction.DOWN) {
            elevator.moveDown();
        }
    }
}