package com.codingrecipe.minielevatorsimulator.model;

import java.util.TreeSet;

public class Elevator {

    private final int totalFloors; // 건물 총 층수
    private int currentFloor; //현재 층
    private Direction direction; // 현재 이동 방향
    private TreeSet<Integer> destinations; // 목적지ㅣ 총 몰록
    private boolean doorsOpen; // 문 열림 상태


    // @param totalFloors 건물의 총 층수를 받아 엘베 초기화
    public Elevator(int totalFloors) {
        this.totalFloors = totalFloors;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.destinations = new TreeSet<>();
        this.doorsOpen = false;
    }

    // 상태 변경 메서드 영역
    public void addDestination(int floor) {
        if (floor >= 1 && floor <= this.totalFloors) {
            this.destinations.add(floor);
        }
    }

    public void removeDestination(int floor) {
        this.destinations.remove(floor);
    }

    public void moveUp() {
        if (this.currentFloor < this.totalFloors) this.currentFloor++;
    }

    public void moveDown() {
        if (this.currentFloor > 1) this.currentFloor--;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void openDoors() {
        this.doorsOpen = true;
    }

    public void closeDoors() {
        this.doorsOpen = false;
    }

    // 상태 조회 메서드 영역
    public int getTotalFloors() {
        return totalFloors;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isDoorsOpen() {
        return doorsOpen;
    }

    // 목적지 목록의 복사본을 반환
    public TreeSet<Integer> getDestinations() {
        return new TreeSet<>(destinations);
    }
}
