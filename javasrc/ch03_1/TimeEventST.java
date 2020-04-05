package javasrc.ch03_1;

/*
* 3.1.4 Develop Time and Event ADTs that allow processing of data as in the example
illustrated on page 367.

*/

import lib.*;

public class TimeEventST{

    class Time implements Comparable<Time>{
        int hour;
        int minute;
        int second;

        Time(int hour, int minute, int second){
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }

        @Override
        public int compareTo(Time that) {
            if(this.hour < that.hour){
                return -1;
            } else if(this.hour > that.hour){
                return 1;
            }

            if(this.minute < that.minute){
                return -1;
            } else if(this.minute > that.minute){
                return 1;
            }

            if(this.second < that.second){
                return -1;
            } else if(this.second > that.second){
                return 1;
            }
            return 0;
        }

        public String toString(){
            return this.hour + ":" + this.minute + ":" + this.second;
        }
    }

    class Event{
        String location;

        public Event(String location){
            this.location = location;
        }
    }

    private OrderedSequentialSearchST<Time, Event> st;

    public TimeEventST(){
        this.st = new OrderedSequentialSearchST<>();
    }

    public void put(int hour, int minute, int second, String location ){
        Time time = new Time(hour, minute, second);
        Event event = new Event(location);
        this.st.put(time, event);
    }

    public void printAll(){
        for(Time key : this.st.keys()){
            StdOut.println((key.toString()) + "\t" + this.st.get(key).location);
        }
    }

    public static void main(String[] args){
        TimeEventST te = new TimeEventST();
        String[] times = {
            "09:10:11",
            "09:00:59",
            "09:37:44",
            "09:01:10",
            "09:00:13",
            "09:03:13",
            "09:36:14",
            "09:10:25",
            "09:19:32",
            "09:19:46",
            "09:21:05",
            "09:22:43",
            "09:14:25",
            "09:00:00",
            "09:25:52",
            "09:00:03",
            "09:35:21",
            "09:22:54"
        };
        String[] locations = {
            "Chicago",
            "Phoenix",
            "Houston",
            "Chicago",
            "Houston",
            "Chicago",
            "Seattle",
            "Seattle",
            "Phoenix",
            "Chicago",
            "Chicago",
            "Chicago",
            "Seattle",
            "Seattle",
            "Chicago",
            "Chicago",
            "Seattle",
            "Phoneix"
        };
        for(int i=0; i<18; i++){   
            String timestr = times[i];
            String[] timedata = timestr.split(":");
            int hour = Integer.parseInt(timedata[0]);
            int minute = Integer.parseInt(timedata[1]);
            int second = Integer.parseInt(timedata[2]);
            te.put(hour, minute, second, locations[i]);
        }
        te.printAll();
    }

}