package com.magus.trainingfirstapp.design_mode.capter_one.class_6;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangshuai in the 8:58 of 2015.11.18 .
 * 找房子处理
 */
public class lesson_2 {

    /* 房子 */
    class Room {
        public float area;
        public float price;

        public Room(float area, float price) {
            this.area = area;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Room [area=" + area + ", price=" + price + "]";
        }
    }

    /* 中介 */
    class Mediator {
        private List<Room> mRooms = new ArrayList<>();

        public Mediator() {
            for (int i = 0; i < 5; i++) {
                mRooms.add(new Room(14 + i, (14 + i) * 150));
            }
        }

        public Room rentOut(float area, float price) {
            for (Room room : getAllRooms()) {
                if (isSuitable(area, price, room)) {
                    return room;
                }
            }
            return null;
        }

        private boolean isSuitable(float area, float price, Room room) {
            return Math.abs(room.price - price) < Me.diffPrice
                    && Math.abs(room.area - area) < Me.diffArea;
        }

        public List<Room> getAllRooms() {
            return mRooms;
        }
    }

    /* 我 */
    class Me {
        public float roomArea;
        public float roomPrice;
        public static final float diffPrice = 100.0001f;
        public static final float diffArea = 0.00001f;

        public void rentRoom(Mediator mediator) {
            Room room = mediator.rentOut(roomArea, roomPrice);
            if (room != null) {
                Log.d("Me", "找到一个房间:" + room.toString());
            }
        }
    }
}

// 最少知识原则：只与直接相关的朋友通信
// 可扩展，高内聚，低耦合