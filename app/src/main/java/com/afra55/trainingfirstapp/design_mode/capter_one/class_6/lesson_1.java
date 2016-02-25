package com.afra55.trainingfirstapp.design_mode.capter_one.class_6;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangshuai in the 8:58 of 2015.11.18 .
 * 找房子处理
 */
public class lesson_1 {

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
        List<Room> mRooms = new ArrayList<>();

        public Mediator() {
            for (int i = 0; i < 5; i++) {
                mRooms.add(new Room(14 + i, (14 + i) * 150));
            }
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
            List<Room> rooms = mediator.getAllRooms();
            for (Room room : rooms) {
                if (isSuitable(room)) {
                    Log.d("Me", "找到一个房间:" + room.toString());
                }
            }
        }

        private boolean isSuitable(Room room) {
            return Math.abs(room.price - roomPrice) < diffPrice
                    && Math.abs(room.area - roomArea) < diffArea;
        }
    }

}

// 我向中介要了房子的信息，然后自己去适配房子，那中介是干什么吃的？不干活还拿钱。我还累的哼哧哼哧的。
// 我和房子的耦合高，倒置中介的功能弱化，三者之间的关系傻乎乎的纠结不清，
// 所以房子适配的功能要丢给中介，我只要中介打交道，给中介需求，让中介去找，然后给我适配的房子。
